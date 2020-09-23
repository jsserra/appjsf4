package br.appjsf4.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory;
	
	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("appjsf4");
		}catch (Exception e) {
			System.out.println("Erro de cria��o da JPA " + e);
		}		
	}
	
	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		System.out.println("gerou a se��o JPA");
		return entityManagerFactory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager entityManager) {
		try {
			entityManager.close();
		}catch(Exception e) {
			System.out.println("Erro ao fechar se��o JPA " + e);
		}
	}
	
}
