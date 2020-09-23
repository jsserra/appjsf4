package br.appjsf4.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.appjsf4.model.Usuario;
import br.appjsf4.util.appjsf4Exception;

public class UsuarioDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager entityManager;
	
	private GeneralDAO<Usuario> dao;
	
	@PostConstruct
	void start() {
		this.dao = new GeneralDAO<Usuario>(Usuario.class, this.entityManager);
	}
	
	public void salvar(Usuario usuario) throws appjsf4Exception{
		this.dao.salvar(usuario);
	}
	
	public void excluir(Usuario usuario) throws appjsf4Exception{
		this.dao.excluir(usuario);
	}
	
	public void atualizar(Usuario usuario) throws appjsf4Exception{
		this.dao.atualizar(usuario);
	}
	
	public List<Usuario> lista() throws appjsf4Exception{
		return this.dao.listar();
	}
	
	public Usuario buscarId(Integer id) throws appjsf4Exception{
		return this.dao.buscarId(id);
	}
	
	public boolean confirmarUsuario(Usuario usuario) throws appjsf4Exception{
		return this.dao.confirmarUsuario(usuario);
	}

}
