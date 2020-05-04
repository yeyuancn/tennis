package com.yuan.tennis.model.persistent;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
@Entity
@Table(name="league")
public class League implements Serializable
{

	public League() {
	}

	private String name;

	private String city;

	private String state;

	private String mode;

	private Date createTime;

	private long id;

	private long playerCount;

	private long currentSeasonId;

	@Column(name = "current_season_id")
	public long getCurrentSeasonId() {
		return currentSeasonId;
	}

	public void setCurrentSeasonId(long currentSeasonId) {
		this.currentSeasonId = currentSeasonId;
	}

	@Column(name = "player_cnt")
	public long getPlayerCount()
	{
		return playerCount;
	}

	public void setPlayerCount(long playerCount)
	{
		this.playerCount = playerCount;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "city")
	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@Column(name = "state")
	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	@Column(name = "mode")
	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
