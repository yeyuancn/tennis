package com.yuan.tennis.ws.util.email.template;

/**
 * Created by V644593 on 8/16/2016.
 */
public class JoinLeagueOwnerEmail extends SiteEmail
{
    public JoinLeagueOwnerEmail(String emailAddr, String leagueName, String ownerName, String playerName)
    {
        this.setToAddr(emailAddr);
        this.setSubject("New player joined your league");
        String actualContent = emailTemplate.replace("[name]", ownerName)
                .replace("[playerName]", playerName)
                .replace("[leagueName]", leagueName);
        this.setHtmlContent(actualContent);
    }

    private static String emailTemplate = "Hi [name], <br><br>" +
            "Greeting from JustTennisLeague.com! <br><br>" +
            "Great news! A new player just joined your league - '[leagueName]'. <br><br>" +
            "Please say hi to '[playerName]' and don't forget to invite more players to join the league! <br><br>" +
            "Regards, <br>" +
            "<a href=\"http://www.justtennisleague.com\" target=\"_blank\">justtennisleague.com</a>";

}
