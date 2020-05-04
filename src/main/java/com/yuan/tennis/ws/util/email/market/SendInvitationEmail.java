package com.yuan.tennis.ws.util.email.market;

import com.yuan.tennis.ws.util.email.InvitationMailUtil;
import com.yuan.tennis.ws.util.email.market.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v644593 on 6/30/2016.
 */
public class SendInvitationEmail
{

    public static void main(String[] args) throws Exception{
        String contentFile = "C:\\email_content.txt";
        String coachFile = "C:\\email_contacts.txt";

        String emailSub = "Tool for the new season";
        String emailContent = readContent(contentFile);
        List<Contact> contactList = readContacts(coachFile);
        contactList.forEach(c -> {
            String actualContent = emailContent.replace("[name]", c.getName());
            try
            {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            System.out.println("Sending email to " + c.getEmail());
            InvitationMailUtil.sendEmail(c.getEmail(), emailSub, actualContent);
        });
    }


    private static List<Contact> readContacts(String fileName) throws IOException {

        List<Contact> contactList = new ArrayList<Contact>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = br.readLine();

            while (line != null) {
                line = line.trim();
                if (line.length() > 0)
                {
                    String[] parts = line.split(" ");
                    Contact c = new Contact(parts[0], parts[1]);
                    contactList.add(c);
                }
                line = br.readLine();
            }
            return contactList;
        } finally {
            br.close();
        }
    }

    private static String readContent(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
