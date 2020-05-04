package com.yuan.tennis.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yuan.tennis.dao.LeagueDAO;
import com.yuan.tennis.dao.SeasonDAO;
import com.yuan.tennis.model.persistent.Season;
import com.yuan.tennis.ws.exception.AppValidationException;
import org.apache.log4j.Logger;

import com.yuan.tennis.dao.MatchResultDAO;
import com.yuan.tennis.dao.PlayerResultDAO;
import com.yuan.tennis.model.persistent.MatchResult;
import com.yuan.tennis.model.persistent.MatchResultView;
import com.yuan.tennis.model.persistent.PlayerResultView;
import com.yuan.tennis.ws.util.MatchResultHelper;


// @Path here defines class level path. Identifies the URI path that
// a resource class will serve requests for.
@Path("MatchResultService")
public class MatchResultService
{
	private static final Logger logger = Logger.getLogger(MatchResultService.class.getName());

	private LeagueDAO leagueDao = new LeagueDAO();

    private SeasonDAO seasonDao = new SeasonDAO();

	private MatchResultDAO matchResultDao = new MatchResultDAO();
	
	private PlayerResultDAO playerResultDao = new PlayerResultDAO();
	
	@GET
	@Path("/matchResult/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public MatchResult getMatchResult(@PathParam("i") long id)
	{
		return matchResultDao.getMatchResult(id);
	}

	@POST
	@Path("/addMatchResult")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addMatchResult(MatchResult result) throws AppValidationException
	{
		logger.info(result.getWinnerId() + " " + result.getLoserId() + " "
				+ result.getSet1Score() + " " + result.getSet2Score() + " " + result.getSet3Score()
				+ result.getMatchDate());

		long seasonId = leagueDao.getLeague(result.getLeagueId()).getCurrentSeasonId();
        Season s = seasonDao.getSeason(seasonId);
        s.setMatchPlayed(s.getMatchPlayed() + 1);
        seasonDao.updateSeason(s);

		result.setSeasonId(seasonId);
		result = MatchResultHelper.decorateMatchResult(result);
		logger.info("updating match result table");
		matchResultDao.addMatchResult(result);
		logger.info("updating player result table");
		playerResultDao.addMatchResult(result);
		logger.info("done updating results");
	}
		
	
	@DELETE
	@Path("/deleteMatchResult/{i}")
	public void deleteMatchResult(@PathParam("i") long id) throws AppValidationException
	{
		MatchResult result = matchResultDao.getMatchResult(id);

        long seasonId = leagueDao.getLeague(result.getLeagueId()).getCurrentSeasonId();
        Season s = seasonDao.getSeason(seasonId);
        s.setMatchPlayed(s.getMatchPlayed() - 1);
        seasonDao.updateSeason(s);

		playerResultDao.deleteMatchResult(result);
		matchResultDao.deleteMatchResult(id);
	}

	@GET
	@Path("/matchResults/{leagueId}/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public MatchResultView[] getMatchResultViews(@PathParam("leagueId") long leagueId, @PathParam("seasonId") long seasonId)
	{
		List<MatchResultView> matchResultViews = matchResultDao.getMatchResultViews(leagueId, seasonId);
		return matchResultViews.toArray(new MatchResultView[matchResultViews.size()]);
	}

	@GET
	@Path("/matchResultsForPlayer/{playerId}/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public MatchResultView[] getMatchResultViewsForPlayer(@PathParam("playerId") long playerId, @PathParam("seasonId") long seasonId)
	{
		List<MatchResultView> matchResultViews = matchResultDao.getMatchResultViewsForPlayer(playerId, seasonId);
		return matchResultViews.toArray(new MatchResultView[matchResultViews.size()]);
	}

	@GET
	@Path("/playerResults/{leagueId}/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayerResultView[] getPlayerResultViews(@PathParam("leagueId") long leagueId, @PathParam("seasonId") long seasonId)
	{
		List<PlayerResultView> playerResultViews = playerResultDao.getPlayerResultViews(leagueId, seasonId);
		return playerResultViews.toArray(new PlayerResultView[playerResultViews.size()]);
	}

    @GET
    @Path("/currentPlayerResults/{leagueId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlayerResultView[] getPlayerResultViews(@PathParam("leagueId") long leagueId)
    {
        long seasonId = leagueDao.getLeague(leagueId).getCurrentSeasonId();

        List<PlayerResultView> playerResultViews = playerResultDao.getPlayerResultViews(leagueId, seasonId);
        return playerResultViews.toArray(new PlayerResultView[playerResultViews.size()]);
    }

}