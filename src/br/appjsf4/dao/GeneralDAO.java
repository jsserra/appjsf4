package br.appjsf4.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;

import br.appjsf4.model.Usuario;
import br.appjsf4.util.appjsf4Exception;

public class GeneralDAO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	private EntityManager entityManager;

	public GeneralDAO(Class<T> classe, EntityManager entityManager) {
		super();
		this.classe = classe;
		this.entityManager = entityManager;
	}

	public void salvar(T tipo) throws appjsf4Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(tipo);
			entityManager.getTransaction().commit();
		} catch (HibernateException he) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new appjsf4Exception("Erro na gravação no Banco appJsf4: " + he.getMessage());
		}
	}

	public void excluir(T tipo) throws appjsf4Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(tipo);
			entityManager.getTransaction().commit();
		} catch (HibernateException he) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().getRollbackOnly();
			}
			throw new appjsf4Exception("Erro de exclusão no banco appJsf4: " + he.getMessage());
		}
	}

	public void atualizar(T tipo) throws appjsf4Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(tipo);
			entityManager.getTransaction().commit();
		} catch (HibernateException he) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new appjsf4Exception("Erro de atualização no banco appJsf4: " + he.getMessage());
		}
	}

	public List<T> listar() throws appjsf4Exception {
		List<T> lista = null;
		try {
			entityManager.getTransaction().begin();
			CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classe);
			query.select(query.from(classe));
			lista = entityManager.createQuery(query).getResultList();
			entityManager.getTransaction().commit();
		} catch (HibernateException he) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new appjsf4Exception("Erro ao listar dados do banco appJsf4: " + he.getMessage());
		}
		return lista;
	}

	public T buscarId(Integer id) throws appjsf4Exception {
		T tipo = null;
		try {
			entityManager.getTransaction().begin();
			tipo = entityManager.find(classe, id);
			entityManager.getTransaction().commit();
		} catch (HibernateException he) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new appjsf4Exception("Erro de consulta no banco appJsf4: " + he.getMessage());
		}
		return tipo;
	}

	@SuppressWarnings("unused")
	public boolean confirmarUsuario(Usuario usuario) throws appjsf4Exception {
		String hql = " select u from Usuario u where u.nome = :nome and u.senha = :senha";
		try {
			entityManager.getTransaction().begin();
			try {
				TypedQuery<Usuario> query = entityManager.createQuery(hql, Usuario.class);
				query.setParameter("nome", usuario.getNome());
				query.setParameter("senha", usuario.getSenha());
				Usuario resultado = query.getSingleResult();
			} catch (NoResultException ex) {
				return false;
			}
			entityManager.getTransaction().commit();
		} catch (HibernateException he) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new appjsf4Exception("Erro de gravação do usuário: " + he.getMessage());
		}
		return true;
	}

}
