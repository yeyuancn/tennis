package com.yuan.tennis.ws.util.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class InvitationMailUtil
{
    private static final Logger logger = Logger.getLogger(InvitationMailUtil.class.getName());
    // for marketing email
    private static final String emailAcct = "justtennisleague2@gmail.com";
    private static final String emailPassword = "just_tennis";


    public static void sendEmail(String toAddr, String subject, String content)
    {
        new Mailer(toAddr, subject, content).start();
    }

    static class Mailer extends Thread
    {
        private String toAddr;
        private String subject;
        private String content;

        public Mailer(String toAddr, String subject, String content)
        {
            this.toAddr = toAddr;
            this.subject = subject;
            this.content = content;
        }

        public void run()
        {
            Session session = getSession();
            try
            {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(emailAcct));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddr));
                message.setSubject(subject);

                // Create the message part
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                // Fill the message
                messageBodyPart.setText(content,"UTF-8","html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                // Put parts in message
                message.setContent(multipart);
                Transport.send(message);

                logger.info("Email sent to " + toAddr);
            }
            catch (MessagingException e)
            {
                logger.error("Failed to send Email to " + toAddr, e);
            }
        }
    }


    private static Session getSession()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailAcct, emailPassword);
                    }
                });
        return session;
    }

}