package com.yuan.tennis.dao;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.Season;
import com.yuan.tennis.ws.exception.AppValidationException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class SeasonDAO
{

    private static final Logger logger = Logger.getLogger(SeasonDAO.class.getName());

    public Season getSeason(long id)
    {
        logger.info("Getting Season");
        EntityManager em = Startup.getEntityManager();
        try
        {
            return em.find(Season.class, id);
        }
        finally
        {
            em.close();
        }
    }


    public List<Season> getSeasonsByLeagueId(long leagueId)
    {
        logger.info("Getting seasons by league id");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Session session = em.unwrap(Session.class);
            Criteria criteria = session.createCriteria(Season.class);
            return (List<Season>) criteria.add(Restrictions.eq("leagueId", leagueId)).list();
        }
        finally
        {
            em.close();
        }
    }

    public Season getSeasonByLeagueIdAndName(long leagueId, String name)
    {
        logger.info("Getting season by league id and name");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Session session = em.unwrap(Session.class);
            Criteria criteria = session.createCriteria(Season.class);
            return (Season) criteria.add(Restrictions.eq("leagueId", leagueId))
                    .add(Restrictions.eq("name", name))
                    .uniqueResult();
        }
        finally
        {
            em.close();
        }
    }

    public void addSeason(Season season) throws AppValidationException
    {
        logger.info("Adding season");

        EntityManager em = Startup.getEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(season);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public void updateSeason(Season season)
    {
        logger.info("Updating season");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Season s = em.find(Season.class, season.getId());

            em.getTransaction().begin();
            s.setName(season.getName());
            s.setMatchPlayed(season.getMatchPlayed());
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public void summarizeSeason(Season season) throws AppValidationException
    {
        logger.info("Updating season");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Season s = em.find(Season.class, season.getId());
            em.getTransaction().begin();
            s.setName(season.getName());
            s.setSummarizeTime(season.getSummarizeTime());
            em.getTransaction().commit();
        } catch (RollbackException e)
        {
            logger.info("Caught exception, " + e.getCause());
            throw new AppValidationException("Duplicate season name, please choose a different season name");
        } finally
        {
            em.close();
        }
    }



    public void deleteSeason(long id)
    {
        logger.info("Deleting season");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Season season = em.find(Season.class, id);
            em.getTransaction().begin();
            em.remove(season);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

}
