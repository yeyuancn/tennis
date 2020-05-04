package com.yuan.tennis.ws.util.email.template;

import com.yuan.tennis.ws.util.email.MailUtil;

/**
 * Created by V644593 on 8/16/2016.
 */
public abstract class SiteEmail
{
    private String toAddr;
    private String subject;
    private String htmlContent;

    public String getToAddr()
    {
        return toAddr;
    }

    public void setToAddr(String toAddr)
    {
        this.toAddr = toAddr;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getHtmlContent()
    {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent)
    {
        this.htmlContent = htmlContent;
    }

    public void sendEmail()
    {
        MailUtil.sendEmail(toAddr, subject, htmlContent);
    }

}
