package br.appjsf4.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.appjsf4.model.Pedido;
import br.appjsf4.util.appjsf4Exception;

public class PedidoDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager entityManager;
	
	private GeneralDAO<Pedido> dao;
	
	@PostConstruct
	void start() {
		dao = new GeneralDAO<Pedido>(Pedido.class, this.entityManager);
	}
	
	public void salvar(Pedido pedido) throws appjsf4Exception{
		this.dao.salvar(pedido);
	}
	
	public void atualizar(Pedido pedido) throws appjsf4Exception{
		this.dao.atualizar(pedido);
	}

	public void excluir(Pedido pedido) throws appjsf4Exception{
		this.dao.excluir(pedido);
	}
	
	public List<Pedido> listar() throws appjsf4Exception{
		return this.dao.listar();
	}
	
	public Pedido buscarId(Integer id) throws appjsf4Exception{
		return this.dao.buscarId(id);
	}
}
