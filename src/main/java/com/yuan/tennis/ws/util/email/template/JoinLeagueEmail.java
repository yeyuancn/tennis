package com.yuan.tennis.ws.util.email.template;

/**
 * Created by V644593 on 8/16/2016.
 */
public class JoinLeagueEmail extends SiteEmail
{
    public JoinLeagueEmail(String emailAddr, String leagueName, String name)
    {
        this.setToAddr(emailAddr);
        this.setSubject("You just joined a league");
        String actualContent = emailTemplate.replace("[name]", name).replace("[leagueName]", leagueName);
        this.setHtmlContent(actualContent);
    }

    private static String emailTemplate = "Hi [name], <br><br>" +
            "Welcome to JustTennisLeague.com. <br><br>" +
            "Congratulations! You have just joined a league - '[leagueName]'. <br><br>" +
            "Enjoy playing matches in the league. Don't forget to invite more players to join the league! <br><br>" +
            "Regards, <br>" +
            "<a href=\"http://www.justtennisleague.com\" target=\"_blank\">justtennisleague.com</a>";

}
