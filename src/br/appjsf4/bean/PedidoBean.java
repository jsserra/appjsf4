package br.appjsf4.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.appjsf4.dao.ClienteDAO;
import br.appjsf4.dao.PedidoDAO;
import br.appjsf4.dao.ProdutoDAO;
import br.appjsf4.model.Cliente;
import br.appjsf4.model.ItensPedido;
import br.appjsf4.model.Pedido;
import br.appjsf4.model.Produto;
import br.appjsf4.util.appjsf4Exception;

@Named
@ViewScoped
public class PedidoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Pedido pedido;
	private Cliente cliente;
	private ItensPedido itensPedido;
	private Produto produto;

	private Integer pedidoIdNovo = 0;
	private Date data = new Date();
	private Integer clienteId;
	private Double valorTotal = 0.0;
	private List<ItensPedido> listaItens = new ArrayList<>();
	private Integer produtoId;
	private Double produtoQtd;
	private Double produtoValor;

	@Inject
	private PedidoDAO daoPedido;

	@Inject
	private ClienteDAO daoCliente;

	@Inject
	private ProdutoDAO daoProduto;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ItensPedido getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(ItensPedido itensPedido) {
		this.itensPedido = itensPedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getPedidoIdNovo() {
		return pedidoIdNovo;
	}

	public void setPedidoIdNovo(Integer pedidoIdNovo) {
		this.pedidoIdNovo = pedidoIdNovo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItensPedido> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItensPedido> listaItens) {
		this.listaItens = listaItens;
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

	public Double getProdutoValor() {
		return produtoValor;
	}

	public void setProdutoValor(Double produtoValor) {
		this.produtoValor = produtoValor;
	}

	// ACTIONS
	public List<Pedido> getLista() throws appjsf4Exception {
		return daoPedido.listar();
	}

	public List<Cliente> getListaClientes() throws appjsf4Exception {
		return daoCliente.listar();
	}

	public List<Produto> getListaProdutos() throws appjsf4Exception {
		return daoProduto.listar();
	}

	public String gravar() throws appjsf4Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (cliente.getNome() != null) {

			if (!this.listaItens.isEmpty()) {

				pedido.setData(data);
				pedido.setCliente(cliente);
				pedido.setItensPedido(new HashSet<>(this.listaItens));
				pedido.setValorTotal(this.valorTotal);

				daoPedido.salvar(pedido);
				baixarEstoque(pedido);

				pedido = new Pedido();
				cliente = new Cliente();
				produto = new Produto();
				itensPedido = new ItensPedido();

				this.listaItens = new ArrayList<>();

				return "/pedido/pedidoLista?faces-redirect=true";
			} else {

				facesContext.addMessage("Lista de Produtos",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lista de produtos esta vazia", ""));
				return "";
			}
		} else {

			facesContext.addMessage("Lista de Clientes",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não podemos processar pedido sem cliente", ""));
			return "";
		}
	}

	public void apagar(Pedido pedido) throws appjsf4Exception {
		daoPedido.excluir(pedido);
		retornoEstoque(pedido);
	}

	public String editar(Pedido pedido) throws appjsf4Exception {

		Boolean on = true;
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("pedido", pedido);
		flash.put("on", on);

		return "/pedido/pedidoPrint?faces-redirect=true";
	}

	public void carregar() throws appjsf4Exception {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		Boolean on = (Boolean) flash.get("on");

		if (on != null && on == true) {
			this.pedido = (Pedido) flash.get("pedido");

			this.pedidoIdNovo = this.pedido.getId();
			this.data = this.pedido.getData();
			this.clienteId = this.pedido.getCliente().getId();
			cliente = daoCliente.buscarId(clienteId);

			this.listaItens = new ArrayList<>(this.pedido.getItensPedido());

			this.valorTotal = this.pedido.getValorTotal();

		} else {
			// inicializa objetos
			pedido = new Pedido();
			cliente = new Cliente();
			produto = new Produto();
			itensPedido = new ItensPedido();

			// inicializa variáveis
			this.pedidoIdNovo = (daoPedido.listar().size() + 1);
			this.data = new Date();
			this.valorTotal = 0.00;
			this.listaItens = new ArrayList<>();
		}
	}

	public void adicionarProdutosNaLista() throws appjsf4Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		Produto produtoSelecionado = daoProduto.buscarId(this.produtoId);
		this.produtoValor = produtoSelecionado.getValor();
		Double produtoQtdEstoque = produtoSelecionado.getQtd();

		this.valorTotal = this.valorTotal + this.produtoQtd * this.produtoValor;

		if (produtoQtdEstoque >= this.produtoQtd) {

			itensPedido = new ItensPedido(produtoSelecionado, produtoQtd, produtoValor);

			this.listaItens.add(itensPedido);

			this.produtoId = null;
			this.produtoQtd = null;
			this.produtoValor = null;
		} else {
			facesContext.addMessage("Erro!",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"A QTD do produto em esotoque é menor do que asolicitada! " + "\n só existem "
									+ produtoQtdEstoque + "unidades do produto.",
							""));
		}
	}
	
	public void removerProdutoDaLista(ItensPedido item) {
		this.valorTotal = this.valorTotal - (item.getQtd() * item.getValorNovo());
		this.listaItens.remove(item);
	}
	
	public void atualizarProduto(AjaxBehaviorEvent e) throws appjsf4Exception{
		produto = daoProduto.buscarId(this.produtoId);
	}
	
	public void atualizarCliente(AjaxBehaviorEvent e) throws appjsf4Exception{
		cliente = daoCliente.buscarId(this.clienteId);
	}
	
	public void baixarEstoque(Pedido pedido) throws appjsf4Exception{
		this.listaItens = new ArrayList<>(pedido.getItensPedido());
		for(ItensPedido itensPedido : listaItens) {
			
			Integer idProduto = itensPedido.getProduto().getId();
			Double qtdProduto = itensPedido.getQtd();
			
			Produto produtoNovo = daoProduto.buscarId(idProduto);
			produtoNovo.setQtd(produtoNovo.getQtd() - qtdProduto);
			daoProduto.atualizar(produtoNovo);
		}
		
	}

	public void retornoEstoque(Pedido pedido) throws appjsf4Exception {
		this.listaItens = new ArrayList<>(pedido.getItensPedido());
		
		for(ItensPedido itensPedido: listaItens) {
			Integer idProduto = itensPedido.getProduto().getId();
			Double qtdProduto = itensPedido.getQtd();
			
			Produto produtoNovo = daoProduto.buscarId(idProduto);
			produtoNovo.setQtd(produtoNovo.getQtd() + qtdProduto);
			daoProduto.atualizar(produtoNovo);
			
		}
		

	}
	
}
