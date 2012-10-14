package br.ueg.unucet.docscree.interfaces;

import java.util.Set;

import br.ueg.unucet.quid.dominios.EquipeUsuario;

/**
 * Interface para ser implementado no gerenciador da visão do caso de uso Manter Equipe
 * 
 * @author Diego
 *
 */
public interface IEquipeVisao {
	
	/**
	 * Método que adiciona usuário a equipe (lista de equipeUsuário).
	 */
	public void acaoAdicionarUsuario();
	
	/**
	 * Método que remove o usuário da equipe (lista de equipeUsuário).
	 */
	public void acaoRemoverUsuario();
	
	/**
	 * Método que retorna a lista de equipeUsuário
	 * 
	 * @return Set<EquipeUsuario> equipesUsuario
	 */
	public Set<EquipeUsuario> getFldListaEquipeUsuario();
}
