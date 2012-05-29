package br.ueg.unucet.docscree.controladores;

import br.ueg.unucet.quid.dominios.Usuario;

public class UsuarioControle extends GenericoControle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao()
	 */
	@Override
	protected boolean preAcao() {
		if (this.getUsuario().getSenha().equals(
				super.getMapaAtributos().get("confirmarSenha").toString())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean acaoSalvar() {
		super.getFramework().inserirUsuario(this.getUsuario());
		return true;
	}

	private Usuario getUsuario() {
		return (Usuario) super.getMapaAtributos().get("entidade");
	}

}
