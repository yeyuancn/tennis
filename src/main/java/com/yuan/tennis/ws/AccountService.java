package com.yuan.tennis.ws;

import com.yuan.tennis.dao.AccountDAO;
import com.yuan.tennis.dao.LeagueDAO;
import com.yuan.tennis.dao.PlayerDAO;
import com.yuan.tennis.dao.PlayerResultDAO;
import com.yuan.tennis.model.persistent.*;
import com.yuan.tennis.ws.exception.AppValidationException;
import com.yuan.tennis.ws.util.AppConstants;
import com.yuan.tennis.ws.util.email.template.PasswordResetEmail;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// @Path here defines class level path. Identifies the URI path that
// a resource class will serve requests for.
@Path("AccountService")
public class AccountService
{
	private static final Logger logger = Logger.getLogger(AccountService.class.getName());
	
	private AccountDAO accountDao = new AccountDAO();

	private static Random random = new Random();
	
	@GET
	@Path("/account/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("i") long id)
	{
		return accountDao.getAccount(id);
	}
		
	@POST
	@Path("/updateAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateAccount(Account a) throws AppValidationException
	{
		accountDao.updateAccount(a);
	}

	@POST
	@Path("/resetPasswordRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	public void resetPasswordRequest(String email) throws AppValidationException
	{
		int resetKey = random.nextInt(10000);
		Account account = accountDao.resetPasswordRequest(email, resetKey);
		new PasswordResetEmail(account.getEmail(), account.getId(), resetKey, account.getFirstName()).sendEmail();
	}

	@GET
	@Path("/accountByEmail/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccountByEmail(@PathParam("email") String email)
	{
		return accountDao.getAccountByEmail(email);
	}

	@GET
	@Path("/verifyResetPassword/{accountId}/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccountByIdAndKey(@PathParam("accountId") long accountId, @PathParam("key") int key) throws AppValidationException
	{
		return accountDao.getAccountByIdAndKey(accountId, key);
	}

	@POST
	@Path("/updatePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updatePassword(Account account) throws AppValidationException
	{
		accountDao.updatePassword(account);
	}
}