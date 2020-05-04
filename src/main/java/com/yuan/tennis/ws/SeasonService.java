package com.yuan.tennis.ws;

import com.yuan.tennis.dao.LeagueDAO;
import com.yuan.tennis.dao.PlayerDAO;
import com.yuan.tennis.dao.PlayerResultDAO;
import com.yuan.tennis.dao.SeasonDAO;
import com.yuan.tennis.model.persistent.*;
import com.yuan.tennis.ws.exception.AppValidationException;
import com.yuan.tennis.ws.util.AppConstants;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


// @Path here defines class level path. Identifies the URI path that
// a resource class will serve requests for.
@Path("SeasonService")
public class SeasonService
{
	private static final Logger logger = Logger.getLogger(SeasonService.class.getName());
	
	private SeasonDAO seasonDao = new SeasonDAO();
    private LeagueDAO leagueDao = new LeagueDAO();
    private PlayerResultDAO playerResultDao = new PlayerResultDAO();
    private PlayerDAO playerDao = new PlayerDAO();

	@GET
	@Path("/season/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Season getSeason(@PathParam("i") long id)
	{
		Season l =  seasonDao.getSeason(id);
		return l;
	}

	@GET
	@Path("/summarizeSeason/{leagueId}/{name}")
	public void summarizeSeason(@PathParam("leagueId") long leagueId, @PathParam("name") String name) throws AppValidationException
	{
        League l = leagueDao.getLeague(leagueId);
        Season season = seasonDao.getSeason(l.getCurrentSeasonId());

        // summrize existing season
        season.setName(name);
        season.setSummarizeTime(new Date());
		seasonDao.summarizeSeason(season);

        // add a new default season
		Season s = new Season();
		s.setName(AppConstants.DEFAULT_SEASON_NAME);
		s.setCreateTime(new Date());
        s.setMatchPlayed(0);
        s.setLeagueId(leagueId);
		seasonDao.addSeason(s);

		s = seasonDao.getSeasonByLeagueIdAndName(leagueId, AppConstants.DEFAULT_SEASON_NAME);

        // get all players and generate default scores for the season created
        List<Player> players = playerDao.getPlayers(leagueId, false);
        for (Player p: players)
        {
            PlayerResult playerResult = new PlayerResult();
            playerResult.setPlayerId(p.getId());
            playerResult.setGameWonPercent(new BigDecimal(0));
            playerResult.setMatchWonPercent(new BigDecimal(0));
            playerResult.setSeasonId(s.getId());
            playerResultDao.addPlayerResult(playerResult);
        }

		l.setCurrentSeasonId(s.getId());
		leagueDao.updateLeague(l);
	}


	@POST
	@Path("/updateSeason")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateSeason(Season u)
	{
		seasonDao.updateSeason(u);
	}

    @DELETE
    @Path("/deleteSeason/{i}")
    public void deleteSeason(@PathParam("i") long seasonId) throws AppValidationException
    {
        seasonDao.deleteSeason(seasonId);
        playerResultDao.removeEmptyRecords(seasonId);
    }

	@GET
	@Path("/seasons/{leagueId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Season[] getSeasonsByLeagueId(@PathParam("leagueId") long leagueId)
	{
		List<Season> seasons = seasonDao.getSeasonsByLeagueId(leagueId);

		List<Season> sortedSeason = seasons.stream()
				.sorted((e1, e2) -> Long.compare(-e1.getId(), -e2.getId()))
				.collect(Collectors.toList());

		return sortedSeason.toArray(new Season[sortedSeason.size()]);
	}

    @GET
    @Path("/seasons_all/{leagueId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Season[] getSeasonsByLeagueIdWithAll(@PathParam("leagueId") long leagueId)
    {

        List<Season> seasons = seasonDao.getSeasonsByLeagueId(leagueId);


        List<Season> sortedSeason = seasons.stream()
                .sorted((e1, e2) -> Long.compare(-e1.getId(), -e2.getId()))
                .collect(Collectors.toList());

        Season allSeason = new Season();
        allSeason.setName("All Seasons");
        allSeason.setId(AppConstants.ALL_SEASONS_ID);

        sortedSeason.add(0, allSeason);
        return sortedSeason.toArray(new Season[sortedSeason.size()]);
    }
}