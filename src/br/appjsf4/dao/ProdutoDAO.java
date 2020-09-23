package br.appjsf4.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.appjsf4.model.Produto;
import br.appjsf4.util.appjsf4Exception;

public class ProdutoDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager entityManager;
	
	private GeneralDAO<Produto> dao;
	
	@PostConstruct
	void start() {
		dao = new GeneralDAO<Produto>(Produto.class, this.entityManager);
	}
	
	public void salvar(Produto produto) throws appjsf4Exception{
		this.dao.salvar(produto);
	}
	
	public void atualizar(Produto produto) throws appjsf4Exception{
		this.dao.atualizar(produto);
	}

	public void excluir(Produto produto) throws appjsf4Exception{
		this.dao.excluir(produto);
	}
	
	public List<Produto> listar() throws appjsf4Exception{
		return this.dao.listar();
	}
	
	public Produto buscarId(Integer id) throws appjsf4Exception{
		return this.dao.buscarId(id);
	}
}
