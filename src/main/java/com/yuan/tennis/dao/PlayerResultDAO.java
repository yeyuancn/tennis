package com.yuan.tennis.dao;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.MatchResult;
import com.yuan.tennis.model.persistent.PlayerResult;
import com.yuan.tennis.model.persistent.PlayerResultView;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.yuan.tennis.ws.util.AppConstants.ALL_SEASONS_ID;

public class PlayerResultDAO {
	
	private static final Logger logger = Logger.getLogger(PlayerResultDAO.class.getName());

    public PlayerResult getPlayerResult(long playerId, long seasonId)
    {
    	logger.info("Getting playerResult");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            Session session = em.unwrap(Session.class);
            Criteria criteria = session.createCriteria(PlayerResult.class);
            return (PlayerResult) criteria.add(Restrictions.eq("playerId", playerId))
                    .add(Restrictions.eq("seasonId", seasonId)).uniqueResult();
        }
        finally
        {
            em.close();
        }
    }
    
    public PlayerResult addPlayerResult(PlayerResult playerResult)
    {
    	logger.info("Persisting playerResult, add player result");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(playerResult);
            em.getTransaction().commit();
            return playerResult;
        }
        finally
        {
            em.close();
        }
    }

    public void updatePlayerResult(PlayerResult playerResult)
    {
        logger.info("Updating playerresult");
        EntityManager em = Startup.getEntityManager();
        try
        {
            PlayerResult pr = em.find(PlayerResult.class, playerResult.getId());

            em.getTransaction().begin();
            pr.setGameWon(playerResult.getGameWon());
            pr.setGameLost(playerResult.getGameLost());
            pr.setGameWonPercent(playerResult.getGameWonPercent());
            pr.setMatchWon(playerResult.getMatchWon());
            pr.setMatchLost(playerResult.getMatchLost());
            pr.setMatchWonPercent(playerResult.getMatchWonPercent());
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
   
    public void deletePlayerResult(long id) 
    {
    	logger.info("Deleting playerResult");
    	EntityManager em = Startup.getEntityManager();
    	try
        {
            PlayerResult playerResult = em.find(PlayerResult.class, id);

            em.getTransaction().begin();
            em.remove(playerResult);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public List<PlayerResultView> getPlayerResultViews(long leagueId, long seasonId)
    {   	
    	logger.info("Getting all playerResultView for the league/season");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = em.createQuery("SELECT p FROM PlayerResultView p where p.leagueId = :leagueId " +
                    "and p.seasonId = :seasonId order by match_won desc, " +
                    "match_won_percent desc, game_won_percent desc, match_lost desc")
                    .setParameter("leagueId", leagueId)
                    .setParameter("seasonId", seasonId);
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public void addMatchResult(MatchResult matchResult)
    {
        logger.info("Updating player match result when a new match result added");
        // handle the winner result; Adding it to current season and overall result!!!
        List<PlayerResult> winnerResults = new ArrayList<PlayerResult>();
        winnerResults.add(getPlayerResult(matchResult.getWinnerId(), matchResult.getSeasonId()));
        winnerResults.add(getPlayerResult(matchResult.getWinnerId(), ALL_SEASONS_ID));

        for (PlayerResult pr : winnerResults)
        {
            pr.setGameWon(pr.getGameWon() + matchResult.getGameWon());
            pr.setGameLost(pr.getGameLost() + matchResult.getGameLost());
            pr.setMatchWon(pr.getMatchWon() + 1);
            pr.setMatchWonPercent(new BigDecimal(pr.getMatchWon() * 100.0 / (pr.getMatchWon() + pr.getMatchLost())).setScale(1, BigDecimal.ROUND_UP));
            pr.setGameWonPercent(new BigDecimal(pr.getGameWon() * 100.0 / (pr.getGameWon() + pr.getGameLost())).setScale(1, BigDecimal.ROUND_UP));
            updatePlayerResult(pr);
        }


        // handle the loser result; Adding it to current season and overall result!!!
        List<PlayerResult> loserResults = new ArrayList<PlayerResult>();
        loserResults.add(getPlayerResult(matchResult.getLoserId(), matchResult.getSeasonId()));
        loserResults.add(getPlayerResult(matchResult.getLoserId(), ALL_SEASONS_ID));

        for (PlayerResult pr : loserResults)
        {
            pr.setGameWon(pr.getGameWon() + matchResult.getGameLost());
            pr.setGameLost(pr.getGameLost() + matchResult.getGameWon());
            pr.setMatchLost(pr.getMatchLost() + 1);
            pr.setMatchWonPercent(new BigDecimal(pr.getMatchWon() * 100.0 / (pr.getMatchWon() + pr.getMatchLost())).setScale(1, BigDecimal.ROUND_UP));
            pr.setGameWonPercent(new BigDecimal(pr.getGameWon() * 100.0 / (pr.getGameWon() + pr.getGameLost())).setScale(1, BigDecimal.ROUND_UP));
            updatePlayerResult(pr);
        }

    }

    public void deleteMatchResult(MatchResult matchResult)
    {
        logger.info("Updating player match result when a match result deleted");

        // handle the winner result; update this season and overall results
        List<PlayerResult> winnerResults = new ArrayList<PlayerResult>();
        winnerResults.add(getPlayerResult(matchResult.getWinnerId(), matchResult.getSeasonId()));
        winnerResults.add(getPlayerResult(matchResult.getWinnerId(), ALL_SEASONS_ID));

        for (PlayerResult pr : winnerResults)
        {
            pr.setGameWon(pr.getGameWon() - matchResult.getGameWon());
            pr.setGameLost(pr.getGameLost() - matchResult.getGameLost());
            pr.setMatchWon(pr.getMatchWon() - 1);
            if (pr.getMatchWon() == 0)
            {
                pr.setMatchWonPercent(new BigDecimal(0));
            }
            else
            {
                pr.setMatchWonPercent(new BigDecimal(pr.getMatchWon() * 100.0 / (pr.getMatchWon() + pr.getMatchLost())).setScale(1, BigDecimal.ROUND_UP));
            }

            if (pr.getGameWon() == 0)
            {
                pr.setGameWonPercent(new BigDecimal(0));
            }
            else
            {
                pr.setGameWonPercent(new BigDecimal(pr.getGameWon() * 100.0 / (pr.getGameWon() + pr.getGameLost())).setScale(1, BigDecimal.ROUND_UP));
            }
            updatePlayerResult(pr);
        }


        // handle the loser result; update this season and overall results
        List<PlayerResult> loserResults = new ArrayList<PlayerResult>();
        loserResults.add(getPlayerResult(matchResult.getLoserId(), matchResult.getSeasonId()));
        loserResults.add(getPlayerResult(matchResult.getLoserId(), ALL_SEASONS_ID));

        for (PlayerResult pr : loserResults)
        {
            pr.setGameWon(pr.getGameWon() - matchResult.getGameLost());
            pr.setGameLost(pr.getGameLost() - matchResult.getGameWon());
            pr.setMatchLost(pr.getMatchLost() - 1);

            if (pr.getMatchWon() == 0)
            {
                pr.setMatchWonPercent(new BigDecimal(0));
            }
            else
            {
                pr.setMatchWonPercent(new BigDecimal(pr.getMatchWon() * 100.0 / (pr.getMatchWon() + pr.getMatchLost())).setScale(1, BigDecimal.ROUND_UP));
            }
            if (pr.getGameWon() == 0)
            {
                pr.setGameWonPercent(new BigDecimal(0));
            }
            else
            {
                pr.setGameWonPercent(new BigDecimal(pr.getGameWon() * 100.0 / (pr.getGameWon() + pr.getGameLost())).setScale(1, BigDecimal.ROUND_UP));
            }

            updatePlayerResult(pr);
        }

    }

    public void removeEmptyRecords(long seasonId)
    {
        logger.info("Delete empty player results for the season");
        EntityManager em = Startup.getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM PlayerResult p WHERE p.seasonId = :seasonId " +
                    "AND (p.matchWon = 0 and p.matchLost = 0)")
                    .setParameter("seasonId", seasonId)
                    .executeUpdate();
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
}
