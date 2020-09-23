package br.appjsf4.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.appjsf4.dao.UsuarioDAO;
import br.appjsf4.model.Usuario;
import br.appjsf4.util.appjsf4Exception;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	@Inject
	private UsuarioDAO dao;
	private String usuarioLogado;

	@PostConstruct
	public void start() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		if (!(usuarioLogado == null)) {
			this.setUsuarioLogado(usuarioLogado.getNome());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	// ACTIONS
	public void editar(Usuario usuario) {
		this.usuario = usuario;
	}

	public void gravar() throws appjsf4Exception {
		if (this.usuario.getId() == null) {
			dao.salvar(usuario);
		} else {
			dao.atualizar(usuario);
		}
		this.usuario = new Usuario();
	}
	
	public void excluir(Usuario usuario) throws appjsf4Exception{
		dao.excluir(usuario);
	}
	
	public List<Usuario> getLista() throws appjsf4Exception{
		return dao.lista();
	}
	
	public String logIn() throws appjsf4Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		usuarioLogado = usuario.getNome();
		
		boolean exist = dao.confirmarUsuario(this.usuario);
		if(exist) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "/home?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return "/index?faces-redirect=true";
	}
	
	public String logOff() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "/index?faces-redirect=true";
	}

}
