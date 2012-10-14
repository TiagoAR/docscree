package br.ueg.unucet.docscree.interfaces;

import br.ueg.unucet.quid.dominios.Usuario;

/**
 * Interface a ser implementada no gerenciador da visão responsável por efetuar o login
 * 
 * @author Diego
 *
 */
public interface ILogar {
	
	/**
	 * Método que chama o controle para executar a ação de logar usuário
	 * 
	 */
	public void acaoLogar();
	
	/**
	 * Método que desloga usuário
	 */
	public void acaoDeslogar();
	
	/**
	 * Método que adiciona o usuário logado a sessão.
	 * 
	 * @param usuario
	 */
	public void salvarSessaoUsuario(Usuario usuario);

}
