package com.yuan.tennis.model.persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
@Entity
@Table(name="season")
public class Season implements Serializable
{

	public Season() {
	}

	private long id;

	private long leagueId;

	private String name;

    private long matchPlayed;

	private Date createTime;

	private Date summarizeTime;

	@Column(name = "league_id")
	public long getLeagueId()
	{
		return leagueId;
	}

	public void setLeagueId(long leagueId)
	{
		this.leagueId = leagueId;
	}

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "match_played")
    public long getMatchPlayed()
    {
        return matchPlayed;
    }

    public void setMatchPlayed(long matchPlayed)
    {
        this.matchPlayed = matchPlayed;
    }

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "summarize_time")
	public Date getSummarizeTime() {
		return summarizeTime;
	}

	public void setSummarizeTime(Date summarizeTime) {
		this.summarizeTime = summarizeTime;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
