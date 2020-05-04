package com.yuan.tennis.ws;

import com.yuan.tennis.dao.AccountDAO;
import com.yuan.tennis.dao.MessageDAO;
import com.yuan.tennis.dao.PlayerDAO;
import com.yuan.tennis.model.MessageWrapper;
import com.yuan.tennis.model.persistent.Account;
import com.yuan.tennis.model.persistent.Message;
import com.yuan.tennis.model.persistent.MessageView;
import com.yuan.tennis.model.persistent.Player;
import com.yuan.tennis.ws.exception.AppException;
import com.yuan.tennis.ws.util.email.MailUtil;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;


// @Path here defines class level path. Identifies the URI path that
// a resource class will serve requests for.
@Path("MessageService")
public class MessageService
{
	private static final Logger logger = Logger.getLogger(MessageService.class.getName());
	
	private MessageDAO messageDao = new MessageDAO();

	private PlayerDAO playerDao = new PlayerDAO();

	private AccountDAO accountDao = new AccountDAO();

	@GET
	@Path("/message/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("i") long id)
	{
		return messageDao.getMessage(id);
	}

	@POST
	@Path("/saveMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void saveMessage(MessageWrapper m) throws AppException
	{
		//logger.info(m.getContent() + " " + m.getFromPlayerId() + " " + m.getToPlayerIds());
		if (m.getContent() == null || m.getContent().trim().length() == 0)
		{
			throw new AppException("Email content can not be empty");
		}
		if (m.getFromPlayerId() == null || m.getFromPlayerId().trim().length() == 0)
		{
			throw new AppException("From player id can not be empty");
		}
		if (m.getToPlayerIds() == null || m.getToPlayerIds().trim().length() == 0)
		{
			throw new AppException("Target player id can not be empty");
		}
		try
		{
			String[] toPlayers = m.getToPlayerIds().split(":");
			long fromPlayerId = Long.valueOf(m.getFromPlayerId().trim());
			String content = m.getContent().trim();
			for (String p : toPlayers)
			{
				if (p.length() != 0)
				{
					long toPlayerId = Long.valueOf(p.trim());
					Message message = new Message();
					message.setContent(content);
					message.setFromPlayerId(fromPlayerId);
					message.setToPlayerId(toPlayerId);
					message.setMessageTime(new Date());
					messageDao.addMessage(message);
					Player toPlayer = playerDao.getPlayer(toPlayerId);
					Account acct = accountDao.getAccount(toPlayer.getAcctId());


					//Asynchro call to send out an Email.
					MailUtil.sendPlayerEmail(acct.getEmail(), toPlayer.getFirstName(), content);
				}
			}
			logger.info("Saved message!");
		}
		catch (NumberFormatException nfe)
		{
			logger.error("Error paring player id.", nfe);
			throw new AppException("Error while paring player id.");
		}
	}

	@POST
	@Path("/sendComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sendComment(MessageWrapper m) throws AppException
	{
		//logger.info(m.getContent() + " " + m.getFromPlayerId() + " " + m.getToPlayerIds());
		if (m.getContent() == null || m.getContent().trim().length() == 0)
		{
			throw new AppException("Email content can not be empty");
		}
		if (m.getCommentEmailAddr() == null || m.getCommentEmailAddr().trim().length() == 0)
		{
			throw new AppException("Email address can not be empty");
		}

			try
			{
				logger.info("Sending comment Email from " + m.getCommentEmailAddr() + ", content " + m.getContent());
				//Asynchro call to send out an Email.
				MailUtil.sendCommentEmail(m.getCommentEmailAddr(), m.getContent());

			}
			catch (Exception e)
			{
				throw new AppException("Error while sending the comment email");
			}
	}
	
	@DELETE
	@Path("/deleteMessageByFrom/{i}")
	public void deleteMessageByFrom(@PathParam("i") long id)
	{
		messageDao.deleteMessage(id, true);
	}

	@DELETE
	@Path("/deleteMessageByTo/{i}")
	public void deleteMessageByTo(@PathParam("i") long id)
	{
		messageDao.deleteMessage(id, false);
	}

	@GET
	@Path("/messagesToPlayer/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageView[] getMessagesToPlayer(@PathParam("i") long toPlayerId)
	{
		List<MessageView> messages = messageDao.getMessagesToPlayer(toPlayerId);
		return messages.toArray(new MessageView[messages.size()]);
	}

	@GET
	@Path("/messagesFromPlayer/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public MessageView[] getMessagesFromPlayer(@PathParam("i") long fromPlayerId)
	{
		List<MessageView> messages = messageDao.getMessagesFromPlayer(fromPlayerId);
		return messages.toArray(new MessageView[messages.size()]);
	}
}