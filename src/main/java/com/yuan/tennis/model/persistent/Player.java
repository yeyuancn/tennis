package com.yuan.tennis.model.persistent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="player")
public class Player implements Serializable
{

	public Player() {
	}

	private Long acctId;

	private Long leagueId;

	private String firstName;

	private String lastName;
	
	private String level;

	private boolean isActive;

	private boolean isOwner;

	private boolean isAdmin;
	
	private long id;

	private String leagueName;

	private String email;

	@Transient
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Transient
	public String getLeagueName()
	{
		return leagueName;
	}

	@Transient
	public void setLeagueName(String leagueName)
	{
		this.leagueName = leagueName;
	}
	@Column(name = "acct_id")
	public Long getAcctId()
	{
		return acctId;
	}

	public void setAcctId(Long acctId)
	{
		this.acctId = acctId;
	}

	@Column(name = "league_id")
	public Long getLeagueId()
	{
		return leagueId;
	}

	public void setLeagueId(Long leagueId)
	{
		this.leagueId = leagueId;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "is_active")
	public boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	@Column(name = "is_owner")
	public boolean getIsOwner()
	{
		return isOwner;
	}

	public void setIsOwner(boolean isOwner)
	{
		this.isOwner = isOwner;
	}

	@Column(name = "is_admin")
	public boolean getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
