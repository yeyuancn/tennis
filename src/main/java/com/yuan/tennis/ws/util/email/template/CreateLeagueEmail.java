package com.yuan.tennis.ws.util.email.template;

/**
 * Created by V644593 on 8/16/2016.
 */
public class CreateLeagueEmail extends SiteEmail
{
    public CreateLeagueEmail(String emailAddr, String leagueName, String name)
    {
        this.setToAddr(emailAddr);
        this.setSubject("You just created a league");
        String actualContent = emailTemplate.replace("[name]", name).replace("[leagueName]", leagueName);
        this.setHtmlContent(actualContent);
    }

    private static String emailTemplate = "Hi [name], <br><br>" +
            "Welcome to JustTennisLeague.com. <br><br>" +
            "Congratulations! You have just created your own league - '[leagueName]'. <br><br>" +
            "Now it's time to invite more players to join your league! <br><br>" +
            "Regards, <br>" +
            "<a href=\"http://www.justtennisleague.com\" target=\"_blank\">justtennisleague.com</a>";

}
