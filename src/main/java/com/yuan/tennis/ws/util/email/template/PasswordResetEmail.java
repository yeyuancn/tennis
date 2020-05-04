package com.yuan.tennis.ws.util.email.template;

/**
 * Created by V644593 on 8/16/2016.
 */
public class PasswordResetEmail extends SiteEmail
{
    public PasswordResetEmail(String emailAddr, long accountId, int passwordKey, String name)
    {
        this.setToAddr(emailAddr);
        this.setSubject("Your password reset request");
        String link = "http://www.justtennisleague.com/resetPassword.html?" + "accountId=" + accountId + "&key=" + passwordKey;
        String actualContent = emailTemplate.replace("[name]", name).replace("[link]", link);
        this.setHtmlContent(actualContent);

    }

    private static String emailTemplate = "Hi [name], <br><br>" +
            "We've received your request to reset your account password. <br><br>" +
            "Please click on the following link to reset the password. <br><br>" +
            "<a href=\"[link]\" target=\"_blank\">Reset Account Password</a><br><br>" +
            "Regards, <br>" +
            "<a href=\"http://www.justtennisleague.com\" target=\"_blank\">justtennisleague.com</a>";

}
