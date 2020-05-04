package com.yuan.tennis.model.persistent;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="player_result")
public class PlayerResult implements Serializable
{

	public PlayerResult() {
	}

	private long id;

	private long playerId;

	private long seasonId;

	private int matchWon;

	private int matchLost;
	
	private BigDecimal matchWonPercent;
	
	private int gameWon;
	
	private int gameLost;
	
	private BigDecimal gameWonPercent;

	@Id
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	@Column(name = "player_id")
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	@Column(name = "season_id")
	public long getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(long seasonId) {
		this.seasonId = seasonId;
	}

	@Column(name = "match_won")
	public int getMatchWon() {
		return matchWon;
	}

	public void setMatchWon(int matchWon) {
		this.matchWon = matchWon;
	}

	@Column(name = "match_lost")
	public int getMatchLost() {
		return matchLost;
	}

	public void setMatchLost(int matchLost) {
		this.matchLost = matchLost;
	}

	@Column(name = "game_won")
	public int getGameWon() {
		return gameWon;
	}

	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}

	@Column(name = "game_lost")
	public int getGameLost() {
		return gameLost;
	}

	public void setGameLost(int gameLost) {
		this.gameLost = gameLost;
	}		

	@Column(name = "match_won_percent")
	public BigDecimal getMatchWonPercent() {
		return matchWonPercent;
	}

	public void setMatchWonPercent(BigDecimal matchWonPercent) {
		this.matchWonPercent = matchWonPercent;
	}

	@Column(name = "game_won_percent")
	public BigDecimal getGameWonPercent() {
		return gameWonPercent;
	}

	public void setGameWonPercent(BigDecimal gameWonPercent) {
		this.gameWonPercent = gameWonPercent;
	}
}
