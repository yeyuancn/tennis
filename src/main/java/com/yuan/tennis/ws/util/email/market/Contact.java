package com.yuan.tennis.ws.util.email.market;

/**
 * Created by v644593 on 7/11/2016.
 */
public class Contact
{

        String name;
        String email;

        public Contact(String name, String email)
        {
            this.name = name;
            this.email = email;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }
}
