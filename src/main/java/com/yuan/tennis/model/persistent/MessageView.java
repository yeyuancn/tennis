package com.yuan.tennis.model.persistent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "message_view")
public class MessageView implements Serializable
{

    public MessageView()
    {
    }

    private long fromPlayerId;

    private String fromFirstName;

    private String fromLastName;

    private long toPlayerId;

    private String toFirstName;

    private String toLastName;

    private String content;

    private Date messageTime;

    private boolean removedByFrom;

    private boolean removedByTo;

    private long id;

    @Column(name = "from_player_id")
    public long getFromPlayerId()
    {
        return fromPlayerId;
    }


    public void setFromPlayerId(long fromPlayerId)
    {
        this.fromPlayerId = fromPlayerId;
    }

    @Column(name = "to_player_id")
    public long getToPlayerId()
    {
        return toPlayerId;
    }


    public void setToPlayerId(long toPlayerId)
    {
        this.toPlayerId = toPlayerId;
    }

    @Column(name = "content")
    public String getContent()
    {
        return content;
    }


    public void setContent(String content)
    {
        this.content = content;
    }

    @Column(name = "message_time")
    public Date getMessageTime()
    {
        return messageTime;
    }


    public void setMessageTime(Date messageTime)
    {
        this.messageTime = messageTime;
    }

    @Id
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "from_first_name")
    public String getFromFirstName()
    {
        return fromFirstName;
    }


    public void setFromFirstName(String fromFirstName)
    {
        this.fromFirstName = fromFirstName;
    }

    @Column(name = "from_last_name")
    public String getFromLastName()
    {
        return fromLastName;
    }


    public void setFromLastName(String fromLastName)
    {
        this.fromLastName = fromLastName;
    }

    @Column(name = "to_first_name")
    public String getToFirstName()
    {
        return toFirstName;
    }


    public void setToFirstName(String toFirstName)
    {
        this.toFirstName = toFirstName;
    }

    @Column(name = "to_last_name")
    public String getToLastName()
    {
        return toLastName;
    }


    public void setToLastName(String toLastName)
    {
        this.toLastName = toLastName;
    }

    @Column(name = "removed_by_from")
    public boolean getRemovedByFrom()
    {
        return removedByFrom;
    }

    public void setRemovedByFrom(boolean removedByFrom)
    {
        this.removedByFrom = removedByFrom;
    }

    @Column(name = "removed_by_to")
    public boolean getRemovedByTo()
    {
        return removedByTo;
    }

    public void setRemovedByTo(boolean removedByTo)
    {
        this.removedByTo = removedByTo;
    }
}
