package com.yuan.tennis.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yuan.tennis.dao.AccountDAO;
import com.yuan.tennis.dao.LeagueDAO;
import com.yuan.tennis.model.AccountWrapper;
import com.yuan.tennis.model.persistent.Account;
import com.yuan.tennis.model.persistent.League;
import com.yuan.tennis.ws.exception.AppValidationException;
import com.yuan.tennis.ws.util.AppConstants;
import com.yuan.tennis.ws.util.email.template.CreateLeagueEmail;
import com.yuan.tennis.ws.util.email.template.JoinLeagueEmail;
import com.yuan.tennis.ws.util.email.template.JoinLeagueOwnerEmail;
import org.apache.log4j.Logger;

import com.yuan.tennis.dao.PlayerDAO;
import com.yuan.tennis.dao.PlayerResultDAO;
import com.yuan.tennis.model.persistent.Player;
import com.yuan.tennis.model.persistent.PlayerResult;


// @Path here defines class level path. Identifies the URI path that
// a resource class will serve requests for.
@Path("PlayerService")
public class PlayerService
{
	private static final Logger logger = Logger.getLogger(PlayerService.class.getName());
	
	private PlayerDAO playerDao = new PlayerDAO();

	private AccountDAO accountDao = new AccountDAO();

	private LeagueDAO leagueDao = new LeagueDAO();

	private PlayerResultDAO playerResultDao = new PlayerResultDAO();
	
	@GET
	@Path("/player/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Player getPlayer(@PathParam("i") long id)
	{
		return playerDao.getPlayer(id);
	}

	@POST
	@Path("/addAccountAndPlayer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Player addAccountAndPlayer(AccountWrapper aw) throws AppValidationException
	{
		Player p = aw.getPlayer();
		Account a = aw.getAccount();

		System.out.println("Adding player and account : Details - active " + p.getIsActive()
            + " admin " + p.getIsAdmin() + " owner " + p.getIsOwner() );

		accountDao.addAccount(a);

		Account account = accountDao.getAccountByEmail(a.getEmail());

		p.setAcctId(account.getId());
		return this.addPlayer(p);
	}

	@POST
	@Path("/addPlayer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Player addPlayer(Player p) throws AppValidationException
	{
		System.out.println("Adding player : Details - active " + p.getIsActive()
				+ " admin " + p.getIsAdmin() + " owner " + p.getIsOwner() );

		playerDao.addPlayer(p);
		Player player = playerDao.getPlayerByAcctIdAndLeagueId(p.getAcctId(), p.getLeagueId());

		League l = leagueDao.getLeague(player.getLeagueId());
		l.setPlayerCount(l.getPlayerCount() + 1);
		leagueDao.updateLeague(l);

		// sent out registration emails:
		if (p.getIsOwner())
		{
			// new owner
			new CreateLeagueEmail(p.getEmail(), l.getName(), p.getFirstName()).sendEmail();
		}
		else
		{
			// new player, send two emails:
			new JoinLeagueEmail(p.getEmail(), l.getName(), p.getFirstName()).sendEmail();

			Player leagueOwner = playerDao.getOwnerByLeagueId(l.getId());
			Account ownerAccount = accountDao.getAccount(leagueOwner.getAcctId());
			new JoinLeagueOwnerEmail(ownerAccount.getEmail(), l.getName(), leagueOwner.getFirstName(), p.getFirstName()).sendEmail();
		}

		long seasonId = l.getCurrentSeasonId();
		// create the default player result. current season and overall.
		PlayerResult playerResult = new PlayerResult();
		playerResult.setPlayerId(player.getId());
		playerResult.setGameWonPercent(new BigDecimal(0));
		playerResult.setMatchWonPercent(new BigDecimal(0));
		playerResult.setSeasonId(seasonId);
		playerResultDao.addPlayerResult(playerResult);
		playerResult.setSeasonId(AppConstants.ALL_SEASONS_ID);
		playerResultDao.addPlayerResult(playerResult);
		return player;
	}

	@POST
	@Path("/updatePlayer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updatePlayer(Player u) throws AppValidationException
	{
		playerDao.updatePlayer(u);
	}
	
	@DELETE
	@Path("/deletePlayer/{i}")
	public void deletePlayer(@PathParam("i") long id)
	{
		playerDao.deletePlayer(id);
	}

	@GET
	@Path("/allOtherPlayers/{leagueId}/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Player[] getAllOtherActivePlayers(@PathParam("leagueId") long leagueId, @PathParam("i") long id)
	{
		List<Player> players = playerDao.getPlayers(leagueId, true);
		List<Player> result = new ArrayList<Player>();
		for (Player u: players)
		{
			if (u.getId() != id)
			{
				result.add(u);
			}
		}
		return result.toArray(new Player[result.size()]);
	}

	@GET
	@Path("/players/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountWrapper[] getAllPlayers(@PathParam("i") long leagueId)
	{
		List<Player> players = playerDao.getPlayers(leagueId, false);
		List<AccountWrapper> result = new ArrayList<AccountWrapper>();
		players.forEach( p -> {
			Account a = accountDao.getAccount(p.getAcctId());
			result.add(new AccountWrapper(a, p));
		});

		return result.toArray(new AccountWrapper[result.size()]);
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Player[] login(Account a) throws AppValidationException
	{
		Account account = accountDao.login(a.getEmail(), a.getPassword());
		List<Player> players = playerDao.getPlayersByAcctId(account.getId());
		List<Player> result = new ArrayList<Player>();
		players.forEach(p -> {
			if (p.getIsActive())
			{
				League l = leagueDao.getLeague(p.getLeagueId());
				p.setLeagueName(l.getName());
				logger.info("Set league name for player: " + l.getName());
				result.add(p);
			}
		});
		if (result.size() == 0)
		{
			logger.error("Failed at login due to no active account found");
			throw new AppValidationException("Your account is deactivated, please contact with league admin.");
		}
		return result.toArray(new Player[result.size()]);
	}

	@POST
	@Path("/loginSimple")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Account loginSimple(Account a) throws AppValidationException
	{
		return accountDao.login(a.getEmail(), a.getPassword());
	}
}