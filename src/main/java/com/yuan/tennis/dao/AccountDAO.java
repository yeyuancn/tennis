package com.yuan.tennis.dao;

import com.yuan.tennis.misc.Startup;
import com.yuan.tennis.model.persistent.Account;
import com.yuan.tennis.model.persistent.Player;
import com.yuan.tennis.ws.exception.AppValidationException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AccountDAO
{

    private static final Logger logger = Logger.getLogger(AccountDAO.class.getName());

    public Account getAccount(long id)
    {
        logger.info("Getting account");
        EntityManager em = Startup.getEntityManager();
        try
        {
            return em.find(Account.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public Account login(String email, String password) throws AppValidationException
    {
        logger.info("Getting account: " + email + ", " + password);
        EntityManager em = Startup.getEntityManager();

        try
        {
            Account account =  (Account) em.createQuery("SELECT a FROM Account a WHERE a.email = :email and a.password = :password")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            updateAccountLogin(account);
            return account;
        } catch (NoResultException nre)
        {
            logger.error("Failed at login due to invalid email/password" );
            throw new AppValidationException("Invalid email/password.");
        }
        finally
        {
            em.close();
        }
    }

    public Account getAccountByEmail(String email)
    {
        logger.info("Getting account by email");
        EntityManager em = Startup.getEntityManager();
        try
        {
            Session session = em.unwrap(Session.class);
            Criteria criteria = session.createCriteria(Account.class);
            return (Account) criteria.add(Restrictions.eq("email", email)).uniqueResult();
        }
        finally
        {
            em.close();
        }
    }

    public void addAccount(Account account) throws AppValidationException
    {
        logger.info("Adding account");

        EntityManager em = Startup.getEntityManager();

        try
        {
            em.getTransaction().begin();
            account.setLoginCount(1);
            account.setLastLogin(new Date());
            em.persist(account);
            em.getTransaction().commit();
        } catch (RollbackException e)
        {
            logger.info("Caught exception, " + e.getCause());
            throw new AppValidationException("Duplicate account error, please choose a different email");
        } finally
        {
            em.close();
        }
    }

    public void updateAccount(Account account) throws AppValidationException
    {
        logger.info("Updating account");
        EntityManager em = Startup.getEntityManager();
        Account a = em.find(Account.class, account.getId());
        try
        {
            em.getTransaction().begin();
            a.setFirstName(account.getFirstName());
            a.setLastName(account.getLastName());
            a.setEmail(account.getEmail());
            a.setLevel(account.getLevel());
            a.setPassword(account.getPassword());
            em.getTransaction().commit();
        } catch (RollbackException e)
        {
            logger.info("Caught exception, " + e.getCause());
            throw new AppValidationException("Duplicate account error, please choose a different email");
        } finally
        {
            em.close();
        }
    }

    private void updateAccountLogin(Account account) throws AppValidationException
    {
        logger.info("Increment account login info");
        EntityManager em = Startup.getEntityManager();
        Account a = em.find(Account.class, account.getId());
        try
        {
            em.getTransaction().begin();
            a.setLastLogin(new Date());
            a.setLoginCount(a.getLoginCount() + 1);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
    }

    public Account resetPasswordRequest(String email, int resetKey) throws AppValidationException
    {
        logger.info("Reset password request for email: " + email);
        if (email == null)
        {
            throw new AppValidationException("Invalid email.");
        }
        email = email.trim();
        EntityManager em = Startup.getEntityManager();
        try
        {
            Account account =  (Account) em.createQuery("SELECT a FROM Account a WHERE a.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();

            em.getTransaction().begin();
            account.setResetKey(resetKey);
            em.getTransaction().commit();

            return account;

        } catch (NoResultException nre)
        {
            logger.error("No account with the email exists" );
            throw new AppValidationException("Cannot find account associated with the email, please verify your account email.");
        }
        finally
        {
            em.close();
        }
    }

    public Account getAccountByIdAndKey(long accountId, int key) throws AppValidationException
    {
        logger.info("Getting account id: " + accountId + ", key " + key);
        EntityManager em = Startup.getEntityManager();
        try
        {
            return (Account) em.createQuery("SELECT a FROM Account a WHERE a.id = :acctId and a.resetKey = :resetKey")
                    .setParameter("acctId", accountId)
                    .setParameter("resetKey", key)
                    .getSingleResult();
        } catch (NoResultException nre)
        {
            logger.error("Failed to get account based on id and key" );
            throw new AppValidationException("Invalid id/key pair for account.");
        }
        finally
        {
            em.close();
        }
    }

    public void updatePassword(Account account) throws AppValidationException
    {
        logger.info("Updating account password only");
        EntityManager em = Startup.getEntityManager();
        Account a = em.find(Account.class, account.getId());
        try
        {
            em.getTransaction().begin();
            a.setPassword(account.getPassword());
            a.setResetKey(null);
            em.getTransaction().commit();
        } catch (RollbackException e)
        {
            logger.info("Caught exception, " + e.getCause());
            throw new AppValidationException(e.getCause().toString());
        } finally
        {
            em.close();
        }
    }
}
