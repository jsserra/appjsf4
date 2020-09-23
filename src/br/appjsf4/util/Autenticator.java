package br.appjsf4.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.appjsf4.model.Usuario;

public class Autenticator implements PhaseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent evento) {
	
		FacesContext context = evento.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		
		if("/index.xhtml".equals(nomePagina)) {
			return;
		}
		
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if(usuarioLogado != null) {
			return;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/index?faces-redirect=true");
		context.getRenderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
