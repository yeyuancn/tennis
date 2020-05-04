package com.yuan.tennis.ws;

import com.yuan.tennis.dao.LeagueDAO;
import com.yuan.tennis.dao.PlayerDAO;
import com.yuan.tennis.dao.SeasonDAO;
import com.yuan.tennis.model.persistent.League;
import com.yuan.tennis.model.persistent.SearchLeagueView;
import com.yuan.tennis.model.persistent.Season;
import com.yuan.tennis.ws.exception.AppValidationException;
import com.yuan.tennis.ws.util.AppConstants;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;


// @Path here defines class level path. Identifies the URI path that
// a resource class will serve requests for.
@Path("LeagueService")
public class LeagueService
{
	private static final Logger logger = Logger.getLogger(LeagueService.class.getName());
	
	private LeagueDAO leagueDao = new LeagueDAO();

	private SeasonDAO seasonDao = new SeasonDAO();

	@GET
	@Path("/league/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public League getLeague(@PathParam("i") long id)
	{
		League l =  leagueDao.getLeague(id);
		return l;
	}

	@POST
	@Path("/addLeague")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public League addLeague(League league) throws AppValidationException
	{
        league.setCreateTime(new Date());
		league.setPlayerCount(0);
		leagueDao.addLeague(league);
		// have to retrieve league by name to get auto-gene id;
		league = leagueDao.getLeagueByName(league.getName());

		Season s = new Season();
		s.setLeagueId(league.getId());
		s.setName(AppConstants.DEFAULT_SEASON_NAME);
        s.setMatchPlayed(0);
		s.setCreateTime(new Date());

		seasonDao.addSeason(s);

		s = seasonDao.getSeasonByLeagueIdAndName(league.getId(), AppConstants.DEFAULT_SEASON_NAME);

		league.setCurrentSeasonId(s.getId());
		leagueDao.updateLeague(league);

		return league;
	}

	@POST
	@Path("/searchLeague")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchLeagueView[] searchLeague(League league) throws AppValidationException
	{
		List<SearchLeagueView> leagues = leagueDao.searchLeagues(league.getName(), league.getCity(), league.getState());
		return leagues.toArray(new SearchLeagueView[leagues.size()]);
	}

	@POST
	@Path("/updateLeagueDesc")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateLeagueDesc(League u)
	{
		leagueDao.updateLeagueDesc(u);
	}
	
	@DELETE
	@Path("/deleteLeague/{i}")
	public void deleteLeague(@PathParam("i") long id)
	{
		leagueDao.deleteLeague(id);
	}


	@GET
	@Path("/leagues")
	@Produces(MediaType.APPLICATION_JSON)
	public League[] getLeagues()
	{
		List<League> leagues = leagueDao.getLeagues();
		return leagues.toArray(new League[leagues.size()]);
	}

	@GET
	@Path("/getLeagueNumber")
	@Produces(MediaType.TEXT_PLAIN)
	public Long getLeagueNumber()
	{
		return leagueDao.getLeagueNumber();
	}

}