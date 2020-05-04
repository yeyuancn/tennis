package com.yuan.tennis.model.persistent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="match_result_view")
public class MatchResultView implements Serializable
{

	public MatchResultView() {
	}

    private long id;

    private long leagueId;

    private long seasonId;

	private long winnerId;

	private String winnerFName;
	
	private String winnerLName;
	
	private long loserId;
	
	private String loserFName;
	
	private String loserLName;
	
	private String set1Score;

	private String set2Score;
	
	private String set3Score;

	private String matchScore;

	private int enterByWinner;

	private Date matchDate;

	private Date recordTime;

    private String matchMemo;

    @Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Column(name = "winner_id")
	public long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(long winnerId) {
		this.winnerId = winnerId;
	}

	@Column(name = "loser_id")
	public long getLoserId() {
		return loserId;
	}

	public void setLoserId(long loserId) {
		this.loserId = loserId;
	}
	
	@Column(name = "winner_first_name")
	public String getWinnerFName() {
		return winnerFName;
	}

	public void setWinnerFName(String winnerFName) {
		this.winnerFName = winnerFName;
	}

	@Column(name = "winner_last_name")
	public String getWinnerLName() {
		return winnerLName;
	}

	public void setWinnerLName(String winnerLName) {
		this.winnerLName = winnerLName;
	}

	@Column(name = "loser_first_name")
	public String getLoserFName() {
		return loserFName;
	}

	public void setLoserFName(String loserFName) {
		this.loserFName = loserFName;
	}

	@Column(name = "loser_last_name")
	public String getLoserLName() {
		return loserLName;
	}

	public void setLoserLName(String loserLName) {
		this.loserLName = loserLName;
	}

	@Column(name = "set1_score")
	public String getSet1Score() {
		return set1Score;
	}

	public void setSet1Score(String set1Score) {
		this.set1Score = set1Score;
	}

	@Column(name = "set2_score")
	public String getSet2Score() {
		return set2Score;
	}

	public void setSet2Score(String set2Score) {
		this.set2Score = set2Score;
	}

	@Column(name = "set3_score")
	public String getSet3Score() {
		return set3Score;
	}

	public void setSet3Score(String set3Score) {
		this.set3Score = set3Score;
	}


	@Column(name = "match_score")
	public String getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
	}

	@Column(name = "enter_by_winner")
	public int getEnterByWinner()
	{
		return enterByWinner;
	}

	public void setEnterByWinner(int enterByWinner)
	{
		this.enterByWinner = enterByWinner;
	}

	@Column(name = "record_time")
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "match_date")
	public Date getMatchDate()
	{
		return matchDate;
	}

	public void setMatchDate(Date matchDate)
	{
		this.matchDate = matchDate;
	}
    
    @Column(name = "match_memo")
    public String getMatchMemo()
    {
        return matchMemo;
    }

    public void setMatchMemo(String matchMemo)
    {
        this.matchMemo = matchMemo;
    }
}
