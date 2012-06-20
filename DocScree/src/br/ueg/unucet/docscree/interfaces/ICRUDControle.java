package br.ueg.unucet.docscree.interfaces;

import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;

/**
 * Interface para controladores que tenha que executar ação de CRUD para a entidade
 * 
 * @author Diego
 *
 */
public interface ICRUDControle {
	
	/**
	 * Executa ação de salvar, verifica se é uma nova entidade ou uma atualização
	 * e chama o QUID Framework para executar o serviço
	 * 
	 * @return boolean se ação foi executada ou não.
	 */
	public boolean acaoSalvar();
	/**
	 * Executa ação de Listar, chama o QUID Framework para executar o serviço
	 * 
	 * @return boolean se ação foi executada ou não.
	 */
	public boolean acaoListar();
	/**
	 * Executa ação de excluir, chama o QUID Framework para executar o serviço
	 * 
	 * @return boolean se ação foi executada ou não.
	 */
	public boolean acaoExcluir();
	
	/**
	 * Método que mapeia a entidade selecionada aos campos da visão.
	 * 
	 * @param pVisao instancia da visão que chamou a ação.
	 */
	public void setarEntidadeVisao(SuperCompositor<?> pVisao);

}
