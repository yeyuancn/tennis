package com.yuan.tennis.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.yuan.tennis.model.persistent.Player;

public class JPATest {
     private static final String PERSISTENCE_UNIT_NAME = "yuan-tennis";
     private static EntityManagerFactory factory;

     public static void main(String[] args) {
          factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
          EntityManager em = factory.createEntityManager();
          new JPATest().getByName(em);
          em.close();
     }

     public void add(EntityManager em)
     {
         // Create new user
         em.getTransaction().begin();
         Player player = new Player();
         player.setFirstName("AAA");
         player.setLastName("BBB");
         em.persist(player);
         em.getTransaction().commit();
     }
     
     public void getAll(EntityManager em)
     {
         // Read the existing entries and write to console
         Query q = em.createQuery("SELECT p FROM Player p");
         List<Player> playerList = q.getResultList();
         for (Player player : playerList) {
              System.out.println(player.getFirstName());
         }
         System.out.println("Size: " + playerList.size());
     }
     
     public void getByName(EntityManager em)
     {
    	 String email = "yeyuancn2@yahoo.com";
    	 String password = "1234";
         // Read the existing entries and write to console
         List<Player> players = em.createQuery(
     		    "SELECT p FROM Player p WHERE u.email = :email and u.password = :password")
     		    .setParameter("email", email)
     		    .setParameter("password", password)
     		    .getResultList();

         for (Player player : players) {
              System.out.println(player.getFirstName());
         }
     }
}