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
@Table(name="player_result_view")
public class PlayerResultView implements Serializable
{

	public PlayerResultView() {
	}

	private long leagueId;
	private long seasonId;
	private String firstName;
	private String lastName;
	private boolean isActive;
	private long playerId;
	private int matchWon;
	private int matchLost;
	private BigDecimal matchWonPercent;
	private int gameWon;
	private int gameLost;
	private BigDecimal gameWonPercent;

	@Column(name = "league_id")
	public long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}

	@Column(name = "season_id")
	public long getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(long seasonId) {
		this.seasonId = seasonId;
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

	@Column(name = "is_active")
	public boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	@Id
	@Column(name = "player_id")
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
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


	@Column(name = "match_won_percent")
	public BigDecimal getMatchWonPercent() {
		return matchWonPercent;
	}

	public void setMatchWonPercent(BigDecimal matchWonPercent) {
		this.matchWonPercent = matchWonPercent;
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

	@Column(name = "game_won_percent")
	public BigDecimal getGameWonPercent() {
		return gameWonPercent;
	}

	public void setGameWonPercent(BigDecimal gameWonPercent) {
		this.gameWonPercent = gameWonPercent;
	}

}
