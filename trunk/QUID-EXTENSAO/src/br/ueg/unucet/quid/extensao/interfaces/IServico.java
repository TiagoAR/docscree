package br.ueg.unucet.quid.extensao.interfaces;

import java.util.Collection;

/**
 * Classe que representa o um servido dentro do framework.
 * @author QUID
 *
 */
public interface IServico extends IVersionavel{
	

	
	/** Retona o TipoMembro modelo que representa o servico, caso ele necessite. 
	 * @return ITipoMembroModelo que representa o artefato.
	 */
	ITipoMembroModelo getTipoMembroModelo();
	
	/**Metodo responsavel por injetar a intancia do TipoMembro modelo dentro do
	 * servico caso o parametro nomeServico seja informado
	 * @return
	 */
	void setTipoMembroModelo(ITipoMembroModelo tipoMembroModelo);
	
	/**Metodo responsavel por pegar o nome do tipomembro modelo
	 * caso o servico necessite
	 * @return
	 */
	String getNomeTipoMembroModelo();
	
	/**Metodo que retorna a versao do tipomembro modelo do servico caso ele necessite
	 * @return Numero da versao do tipoMembro
	 */
	Integer getVersaoTipoMembroModelo();
	/** Realiza validacao dos parametros de excucao de um servico
	 * @param parametros Parametros de execucao de um servico
	 * @return True se os parametros sao validos para execucao, false caso contrario
	 */
	boolean isListaParametrosValidos();
	
	/**Metodo que verifica se uma lista de parametros e compativel 
	 * @return True se a lista de parametros for compativel, e false caso contrario
	 */
	boolean isListaParametrosCompativeis();
	
	
	/** Realiza a execucao da acao do servico. 
	 * @param parametros Parametros da execucao do servico.
	 * @return Parametros de retorno da execucao do servico.
	 */
	Collection<IParametro> executaAcao();
	
	/**Retorna lista de parametros do servico
	 * @return Lista de parametros do servico
	 */
	Collection<IParametro<?>> getListaParametros();
	
	/**Metodo responsavel por realizar a atribuicao da lista de prametros do membro
	 * @param lista Lista de paramaetros do TipoMembro
	 */
	
	/**Metodo que retnorna a lista de nomes dos parametros invalidos
	 * @return Lista de parametros invalidos
	 */
	Collection<String> getNomesParametrosInvalidos();
	
	
	/**Metodo responsavel atribuir u a lista de parametros ao framework
	 * @param lista
	 */
	void setListaParametros(Collection<IParametro<?>> lista);
	
	/**Retorna servico anterior a fila de execucao caso exista e null caso contrario
	 * @return Servico anterior a ser executado.
	 */
	IServico getAnterior();
	
	/**Retorna o proximo servico a ser executado caso exista e null caso contrario
	 * @return Proximo servico a ser executado
	 */
	IServico getProximo();
	
	void setAnterior(IServico servico);
	
	void setProximo(IServico servico);
	
	boolean isAnteriorObrigatorio();
	
	void setAnteriroObrigatorio(boolean obrigatorio);
	
	
}
