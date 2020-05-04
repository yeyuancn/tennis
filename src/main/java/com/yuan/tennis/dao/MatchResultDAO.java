package com.yuan.tennis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.MatchResult;
import com.yuan.tennis.model.persistent.MatchResultView;
import static com.yuan.tennis.ws.util.AppConstants.ALL_SEASONS_ID;

public class MatchResultDAO {
	
	private static final Logger logger = Logger.getLogger(MatchResultDAO.class.getName());

    public MatchResult getMatchResult(long id) 
    {
    	logger.info("Getting matchResult");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            return em.find(MatchResult.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public void addMatchResult(MatchResult matchResult)
    {
    	logger.info("Adding matchResult");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(matchResult);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
    
   
    public void deleteMatchResult(long id) 
    {
    	logger.info("Deleting matchResult");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            MatchResult matchResult = em.find(MatchResult.class, id);

            em.getTransaction().begin();
            em.remove(matchResult);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
    
    public List<MatchResultView> getMatchResultViews(long leagueId, long seasonId)
    {   	
    	logger.info("Getting all matchResults");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = null;
            if (seasonId == ALL_SEASONS_ID)
            {
                q = em.createQuery("SELECT u FROM MatchResultView u where u.leagueId = :leagueId " +
                        "order by record_time desc")
                        .setParameter("leagueId", leagueId);
            }
            else
            {
                q = em.createQuery("SELECT u FROM MatchResultView u where u.leagueId = :leagueId " +
                        "and u.seasonId = :seasonId order by record_time desc")
                        .setParameter("leagueId", leagueId)
                        .setParameter("seasonId", seasonId);
            }
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }


    public List<MatchResultView> getMatchResultViewsForPlayer(long playerId, long seasonId)
    {
        logger.info("Getting all matchResults for a player");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = null;
            if (seasonId == ALL_SEASONS_ID)
            {
                q = em.createQuery("SELECT u FROM MatchResultView u where u.winnerId = :playerId " +
                        "or u.loserId = :playerId order by record_time desc")
                        .setParameter("playerId", playerId);
            }
            else
            {
                q = em.createQuery("SELECT u FROM MatchResultView u where (u.winnerId = :playerId " +
                        "or u.loserId = :playerId) and u.seasonId = :seasonId order by record_time desc")
                        .setParameter("playerId", playerId)
                        .setParameter("seasonId", seasonId);
            }
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Long getMatchResultNumber()
    {
        logger.info("Getting matchresult number");

        EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = em.createQuery("SELECT count(m) from MatchResult m");
            return (Long) q.getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
}
