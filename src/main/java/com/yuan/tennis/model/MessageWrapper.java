package com.yuan.tennis.model;

/**
 * Created by v644593 on 11/4/2015.
 */
public class MessageWrapper
{
    private String fromPlayerId;
    private String toPlayerIds;
    private String content;
    private String commentEmailAddr;

    public MessageWrapper()
    {
    }

    public String getFromPlayerId()
    {
        return fromPlayerId;
    }

    public void setFromPlayerId(String fromPlayerId)
    {
        this.fromPlayerId = fromPlayerId;
    }

    public String getToPlayerIds()
    {
        return toPlayerIds;
    }

    public void setToPlayerIds(String toPlayerIds)
    {
        this.toPlayerIds = toPlayerIds;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCommentEmailAddr()
    {
        return commentEmailAddr;
    }

    public void setCommentEmailAddr(String commentEmailAddr)
    {
        this.commentEmailAddr = commentEmailAddr;
    }
}
