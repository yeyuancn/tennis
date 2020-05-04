package com.yuan.tennis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import com.yuan.tennis.model.persistent.League;
import com.yuan.tennis.ws.exception.AppValidationException;
import org.apache.log4j.Logger;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.Player;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class PlayerDAO
{

    private static final Logger logger = Logger.getLogger(PlayerDAO.class.getName());

    public Player getPlayer(long id)
    {
        logger.info("Getting player");
        EntityManager em = Startup.getEntityManager();
        try
        {
            return em.find(Player.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public void addPlayer(Player player) throws AppValidationException
    {
        logger.info("Adding player");
        EntityManager em = Startup.getEntityManager();

        try
        {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
        }catch (RollbackException e)
        {
            logger.info("Caughter exception, " + e.getCause());
            throw new AppValidationException("The player is already in the league");
        }
        finally
        {
            em.close();
        }

    }

    public void updatePlayer(Player player) throws AppValidationException
    {
        logger.info("Updating player");
        EntityManager em = Startup.getEntityManager();
        Player p = em.find(Player.class, player.getId());
        try
        {
            em.getTransaction().begin();
            p.setFirstName(player.getFirstName());
            p.setLastName(player.getLastName());
            p.setLevel(player.getLevel());
            p.setIsAdmin(player.getIsAdmin());
            p.setIsActive(player.getIsActive());
            em.getTransaction().commit();

        }
        finally
        {
            em.close();
        }

    }

    public void deletePlayer(long id)
    {
        logger.info("Deleting player");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Player player = em.find(Player.class, id);
            em.getTransaction().begin();
            em.remove(player);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    public Player getPlayerByAcctIdAndLeagueId(long acctId, long leagueId)
    {
        logger.info("Getting unique player associated with the account and league");
        EntityManager em = Startup.getEntityManager();
        try
        {
            String query = "SELECT p FROM Player p WHERE p.acctId = :acctId and p.leagueId = :leagueId";

            return (Player) em.createQuery(query)
                    .setParameter("acctId", acctId)
                    .setParameter("leagueId", leagueId)
                    .getSingleResult();
        }
        finally
        {
            em.close();
        }
    }

    public Player getOwnerByLeagueId(long leagueId)
    {
        logger.info("Getting unique owner of the league");
        EntityManager em = Startup.getEntityManager();
        try
        {
            String query = "SELECT p FROM Player p WHERE p.leagueId = :leagueId and p.isOwner = true";

            return (Player) em.createQuery(query)
                    .setParameter("leagueId", leagueId)
                    .getSingleResult();
        }
        finally
        {
            em.close();
        }
    }

    public List<Player> getPlayersByAcctId(long acctId)
    {
        logger.info("Getting all active players associated with the account");
        EntityManager em = Startup.getEntityManager();
        try
        {
            String query = "SELECT p FROM Player p WHERE p.acctId = :acctId";

            return em.createQuery(query)
                    .setParameter("acctId", acctId)
                    .getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public List<Player> getPlayers(long leagueId, boolean activeOnly)
    {
        logger.info("Getting all players");
        EntityManager em = Startup.getEntityManager();
        try
        {
            String query = null;

            if (activeOnly)
            {
                query = "SELECT p FROM Player p WHERE p.leagueId = :leagueId and p.isActive = true";
            }
            else
            {
                query = "SELECT p FROM Player p WHERE p.leagueId = :leagueId order by p.isActive";
            }
            return em.createQuery(query)
                    .setParameter("leagueId", leagueId)
                    .getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Long getPlayerNumber()
    {
        logger.info("Getting players number");

        EntityManager em = Startup.getEntityManager();
        try
        {
            Query q = em.createQuery("SELECT count(p) from Player p");
            return (Long) q.getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
}
