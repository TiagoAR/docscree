package br.ueg.unucet.docscree.interfaces;

import java.util.List;

import br.ueg.unucet.quid.dominios.Projeto;

/**
 * Interface para ser implementada pelo gerenciador de visão do Modal de Abrir Projeto
 * 
 * @author Diego
 *
 */
public interface IAbrirProjetoVisao {
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

}
