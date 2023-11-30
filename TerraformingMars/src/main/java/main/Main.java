package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import Models.Corporations;
import Models.Games;
import Models.Makers;
import Models.Players;
import Models.TypeMaker;

public class Main {
	
	static Session session;
	static SessionFactory sessionFactory;
	static ServiceRegistry serviceRegistry;
	
	public static synchronized SessionFactory getSessionFactory() 
	{
		try 
		{
			if(sessionFactory == null) 
			{
				StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
				Metadata metaData  = new MetadataSources(standardRegistry).getMetadataBuilder().build();
				sessionFactory = metaData.getSessionFactoryBuilder().build();
			}
			return sessionFactory;
			
		} 
		catch (Throwable ex) 
		{
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		
		session = getSessionFactory().openSession();
		session.beginTransaction();
		
		Corporations coor1 = new Corporations("Renfe", "Va muy mal, pero quiere invadir Marte", 1, null);
		Players j1 = new Players("Jose", 1, coor1, null, null);
		Set<Players> pl = new HashSet<Players>();
		pl.add(j1);
		Games gm = new Games(1, 14, 54, LocalDateTime.now(), LocalDateTime.now(),pl, j1);
		Makers mk = new Makers("Mk1", "No apto para todas las edades", 3, TypeMaker.Ciutat);
		
		session.persist(coor1);
		session.persist(j1);
		session.persist(gm);
		session.persist(mk);
		
		session.getTransaction().commit();
				
		
	}
}
