package br.appjsf4.bean;

import java.io.Serializable;
import java.util.List;


import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.appjsf4.dao.ClienteDAO;
import br.appjsf4.model.Cliente;
import br.appjsf4.util.appjsf4Exception;

@Named
@ViewScoped
public class ClienteBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177142735005167659L;
	
	private Cliente cliente = new Cliente();
	
	@Inject
	private ClienteDAO dao;	
	private Integer clienteId;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Integer getClienteId() {
		return clienteId;
	}
	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}
	
	public void editar(Cliente cliente){
		this.cliente = cliente;
	}
	
	public void gravar() throws appjsf4Exception{
		if(this.cliente.getId() == null) {
			dao.salvar(cliente);
		}else {
			dao.atualizar(cliente);
		}
		this.cliente = new Cliente();
	}
	
	public void apagar(Cliente cliente) throws appjsf4Exception{
		dao.remover(cliente);
	}
	
	public List<Cliente> getLista() throws appjsf4Exception{
		return dao.listar();
	}
	
	public void atualizar(AjaxBehaviorEvent e) throws appjsf4Exception{
		cliente = dao.buscarId(this.clienteId);
	}

}
