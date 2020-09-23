package br.appjsf4.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.appjsf4.dao.ProdutoDAO;
import br.appjsf4.model.Produto;
import br.appjsf4.util.appjsf4Exception;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Produto produto = new Produto(0.0, 0.0);
	private Integer produtoId;
	private Double produtoQtd;
	
	@Inject
	private ProdutoDAO dao;
	
	//ATRIBUTS
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Integer getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	public Double getProdutoQtd() {
		return produtoQtd;
	}
	public void setProdutoQtd(Double produtoQtd) {
		this.produtoQtd = produtoQtd;
	}
	
	//ACTIONS
	public void editar(Produto produto) {
		this.produto = produto;
	}
	
	public void gravar() throws appjsf4Exception{
		if(this.produto.getId() == null) {
			dao.salvar(produto);
		}else {
			if(this.produto != null) {
				if(this.produto.getQtd() <= this.produtoQtd) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					facesContext.addMessage("Erro!",new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"A quantidade do produdo não pode ser menor ou igual do que já era",""));
				}else {
					dao.atualizar(this.produto);
				}
			}else {
					dao.atualizar(this.produto);
				}
			}		
		this.produto = new Produto(0.0, 0.0);
	}
	
	public void apagar(Produto produto) throws appjsf4Exception{
		dao.excluir(produto);
	}
	
	public List<Produto> getLista() throws appjsf4Exception{
		return dao.listar();
	}
	
	public void atualizarProduto(AjaxBehaviorEvent e) throws appjsf4Exception{
		produto = dao.buscarId(this.produtoId);
		this.produtoQtd = produto.getQtd();
	}  

}
