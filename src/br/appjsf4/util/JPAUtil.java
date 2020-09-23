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
			System.out.println("Erro de criação da JPA " + e);
		}		
	}
	
	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		System.out.println("gerou a seção JPA");
		return entityManagerFactory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager entityManager) {
		try {
			entityManager.close();
		}catch(Exception e) {
			System.out.println("Erro ao fechar seção JPA " + e);
		}
	}
	
}
