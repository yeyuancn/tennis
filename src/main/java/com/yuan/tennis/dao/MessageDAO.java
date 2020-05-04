package com.yuan.tennis.dao;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.Message;
import com.yuan.tennis.model.persistent.MessageView;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MessageDAO {
	
	private static final Logger logger = Logger.getLogger(MessageDAO.class.getName());
 // chagne
    public Message getMessage(long id) 
    {
    	logger.info("Getting message");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            return em.find(Message.class, id);
        }
        finally
        {
            em.close();
        }
    }
    
    public List<MessageView> getMessagesToPlayer(long toPlayerId)
    {
    	logger.info("Getting messages to player id: " + toPlayerId);
    	EntityManager em = Startup.getEntityManager();
        try
        {
            return em.createQuery(
                    "SELECT m FROM MessageView m WHERE m.toPlayerId = :toPlayerId and m.removedByTo = FALSE order by message_time desc")
                    .setParameter("toPlayerId", toPlayerId)
                    .getResultList();

        }
        finally
        {
            em.close();
        }
    }

    public List<MessageView> getMessagesFromPlayer(long fromPlayerId)
    {
        logger.info("Getting messages from player id: " + fromPlayerId);
        EntityManager em = Startup.getEntityManager();
        try
        {
            return em.createQuery(
                    "SELECT m FROM MessageView m WHERE m.fromPlayerId = :fromPlayerId and m.removedByFrom = FALSE order by message_time desc")
                    .setParameter("fromPlayerId", fromPlayerId)
                    .getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public void addMessage(Message message)
    {
    	logger.info("Adding message");
    	EntityManager em = Startup.getEntityManager();
    	try
        {
            em.getTransaction().begin();
            em.persist(message);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    
    public void deleteMessage(long id, boolean byFrom)
    {
    	logger.info("Mark a message as deleted by the from user or to user");
    	EntityManager em = Startup.getEntityManager();
        try
        {
            Message message = em.find(Message.class, id);

            em.getTransaction().begin();
            if (byFrom)
            {
                message.setRemovedByFrom(true);
            }
            else
            {
                message.setRemovedByTo(true);
            }
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
}
