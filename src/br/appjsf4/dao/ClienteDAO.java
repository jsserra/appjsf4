package br.appjsf4.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.appjsf4.model.Cliente;
import br.appjsf4.util.appjsf4Exception;

public class ClienteDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager entityManager;
	
	private GeneralDAO<Cliente> dao;
	
	@PostConstruct
	void start() {
		this.dao = new GeneralDAO<Cliente>(Cliente.class, this.entityManager);
	}
	
	public void salvar(Cliente cliente) throws appjsf4Exception{
		dao.salvar(cliente);			
	}
	
	public void atualizar(Cliente cliente) throws appjsf4Exception{
		dao.atualizar(cliente);
	}
	
	public void remover(Cliente cliente) throws appjsf4Exception{
		dao.excluir(cliente);
	}
	
	public List<Cliente> listar() throws appjsf4Exception{
		return dao.listar();
	}
	
	public Cliente buscarId(Integer id) throws appjsf4Exception{
		return dao.buscarId(id);
	}
	

}
