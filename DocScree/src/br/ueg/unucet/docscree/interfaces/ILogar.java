package br.ueg.unucet.docscree.interfaces;

import br.ueg.unucet.quid.dominios.Usuario;

public interface ILogar {
	
	/**
	 * Método que chama o controle para executar a ação de logar usuário
	 * 
	 */
	public void acaoLogar();
	
	/**
	 * Método que adiciona o usuário logado a sessão.
	 * 
	 * @param usuario
	 */
	public void salvarSessaoUsuario(Usuario usuario);

}
