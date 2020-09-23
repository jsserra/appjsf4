package br.appjsf4.util;

import javax.persistence.EntityManager;

import br.appjsf4.model.Cliente;
import br.appjsf4.model.Produto;
import br.appjsf4.model.Usuario;

public class PovoaDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		System.out.println("Inicamos a geração das Tabelas!");
		
		Cliente juliano = new Cliente("Juliano Sales", "092.956.217-86");
		Cliente romuel = new Cliente("R0muel Dias", "111.222.333-44");
		Cliente maria = new Cliente("Maria de Bié", "987.456.122-00");
		
		em.persist(juliano);
		em.persist(romuel);
		em.persist(maria);
		System.out.println("Gravou os clientes!");
		
		Produto livro = new Produto("Livro", "und", 10.00, 100.00);
		Produto caderno = new Produto("Caderno", "und", 43.00, 12.80);
		Produto papel = new Produto("Papel", "pct", 100.00, 18.90);
		Produto tinta = new Produto("Tinta Guache", "lt", 9.50, 3.30);
		Produto borracha = new Produto("Borracha", "und", 1000.00, 6.30);
		Produto lapis = new Produto("Lápis", "und", 150.00, 5.50);
		
		em.persist(livro);
		em.persist(caderno);
		em.persist(papel);
		em.persist(tinta);
		em.persist(borracha);
		em.persist(lapis);
		System.out.println("Gravou os Produtos!");
		
		Usuario usuario = new Usuario("jsserra", "1234");
		em.persist(usuario);
		System.out.println("Gravou usuário!");
		
		em.getTransaction().commit();
		em.close();
		System.out.println("Finalizou a geração das tabelas");
		
		System.exit(0);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
