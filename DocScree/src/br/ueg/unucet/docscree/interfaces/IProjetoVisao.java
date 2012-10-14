package br.ueg.unucet.docscree.interfaces;

import java.util.List;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;

/**
 * Interface para ser implementado no gerenciador da visão do caso de uso Manter Projeto
 * 
 * @author Diego
 *
 */
public interface IProjetoVisao {

	/**
	 * Método responsável por salvar o projeto selecionado a sessão para ser acessado
	 * posteriormente
	 */
	public void salvarSessaoProjeto();
	/**
	 * Responsável por trazer a Lista de Projetos
	 * 
	 * @return List<Projeto> lista de projetos
	 */
	public List<Projeto> getListaProjetos();
	/**
	 * Responsável por trazer a listagem de Equipes
	 * 
	 * @return List<Equipe> lista de equipes
	 */
	public List<Equipe> getListaEquipes();
	/**
	 * Responsávle por trazer a listagem de Modelos
	 * 
	 * @return List<Modelo> lista de modelos
	 */
	public List<Modelo> getListaModelos();

}
