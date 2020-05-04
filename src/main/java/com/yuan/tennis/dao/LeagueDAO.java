package com.yuan.tennis.dao;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.League;
import com.yuan.tennis.model.persistent.SearchLeagueView;
import com.yuan.tennis.ws.exception.AppValidationException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class LeagueDAO
{

    private static final Logger logger = Logger.getLogger(LeagueDAO.class.getName());

    public League getLeague(long id)
    {
        logger.info("Getting league");
        EntityManager em = Startup.getEntityManager();
        try
        {
            return em.find(League.class, id);
        }
        finally
        {
            em.close();
        }
    }


    public League getLeagueByName(String name)
    {
        logger.info("Getting league by name");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Session session = em.unwrap(Session.class);
            Criteria criteria = session.createCriteria(League.class);
            return (League) criteria.add(Restrictions.eq("name", name)).uniqueResult();
        }
        finally
        {
            em.close();
        }
    }


    public void addLeague(League league) throws AppValidationException
    {
        logger.info("Adding league");

        EntityManager em = Startup.getEntityManager();


        try
        {
            em.getTransaction().begin();
            em.persist(league);
            em.getTransaction().commit();
        } catch (RollbackException e)
        {
            logger.info("Caught exception, " + e.getCause());
            throw new AppValidationException("Duplicate league error, please choose a different league name");
        } finally
        {
            em.close();
        }

    }

    public void updateLeague(League league)
    {
        logger.info("Updating league");
        EntityManager em = Startup.getEntityManager();
        try
        {
            League l = em.find(League.class, league.getId());
            em.getTransaction().begin();
            l.setName(league.getName());
            l.setCity(league.getCity());
            l.setState(league.getState());
            l.setMode(league.getMode());
            l.setPlayerCount(league.getPlayerCount());
            l.setCurrentSeasonId(league.getCurrentSeasonId());
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public void updateLeagueDesc(League league)
    {
        logger.info("Updating league, description only.");
        EntityManager em = Startup.getEntityManager();
        try
        {
            League l = em.find(League.class, league.getId());
            em.getTransaction().begin();
            l.setName(league.getName());
            l.setCity(league.getCity());
            l.setState(league.getState());
            l.setMode(league.getMode());
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public void deleteLeague(long id)
    {
        logger.info("Deleting league");
        EntityManager em = Startup.getEntityManager();
        try
        {
            League league = em.find(League.class, id);

            em.getTransaction().begin();
            em.remove(league);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public List<SearchLeagueView> searchLeagues(String name, String city, String state) throws AppValidationException
    {
        logger.info("Getting all searchLeagueView based on search criteria, name: " + name + ", city: " + city + ", state: " + state);

        EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = null;
            if (state.trim().length() > 0)
            {
                q = em.createQuery("SELECT l FROM SearchLeagueView l where l.name like :name and l.city like :city and l.state = :state")
                        .setParameter("name", "%" + name + "%")
                        .setParameter("city", "%" + city + "%")
                        .setParameter("state", state);
            }
            else
            {
                q = em.createQuery("SELECT l FROM SearchLeagueView l where l.name like :name and l.city like :city")
                        .setParameter("name", "%" + name + "%")
                        .setParameter("city", "%" + city + "%");
            }

            List<SearchLeagueView> leagues = q.getResultList();
            if (leagues.size() == 0)
            {
                throw new AppValidationException("Cannot find a league satisfying the search criteria");
            }
            return leagues;
        }
        finally
        {
            em.close();
        }
    }

    public List<League> getLeagues()
    {
        logger.info("Getting all leagues");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = em.createQuery("SELECT p FROM League p");
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Long getLeagueNumber()
    {
        logger.info("Getting league number");

        EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = em.createQuery("SELECT count(l) from League l");
            return (Long) q.getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
}
