package br.ueg.unucet.quid.utilitarias;

import java.io.Serializable;

import br.ueg.unucet.quid.dominios.Usuario;


/**
 * Classe responsavel por realizar a manipulacao do usuario dentro da sessao do spring security.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
public class LoginBean implements Serializable {

	private Usuario usuario;
	
	public LoginBean() {
    }
	
	public Usuario checkUsuarioController() {
		Usuario usuario = new Usuario();
//		SecurityContext context = SecurityContextHolder.getContext();
//		if (context instanceof SecurityContext) {
//			Authentication authentication = context.getAuthentication();
//			if (authentication instanceof Authentication) {
//				usuario.setUsername( ((User) authentication.getPrincipal()).getUsername());
//			}
//		}
		return usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
 
}