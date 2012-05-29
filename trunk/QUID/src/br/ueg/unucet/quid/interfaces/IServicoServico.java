package br.ueg.unucet.quid.interfaces;

import java.io.File;
import java.util.Collection;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;


/**
 * Classe responsavel por definir as operacoes do servico sobre a entidade Servico
 * @author QUID
 *
 * @param <T> Entidade 
 */
public interface IServicoServico<T> extends br.ueg.unucet.quid.interfaces.IServico<T> {
	
	/**Metodo responsavel por realizar o mapamento de novos servicos para o framework.
	 * @param arquivos Arquivos que serao mapeados 
	 * @return Retorno da execucao com os arquivos que foram mapeados e suas respectivas mensagens de como foi o mapeamento.
	 */
	Retorno<File, String> mepearServicos(File[] arquivos);
	
	/**
	 * Metodo responsavel por remover um servico cadastrado no framework.
	 * @param iservico Servico que sera removido.
	 * @return Retorno da execucao da remocao do servico.
	 */
	Retorno<Object, Object> removerServico(br.ueg.unucet.quid.extensao.interfaces.IServico iservico);
	
	/**
	 * Metodo que lista os servicos cadastrados no framework.
	 * @return Lista de servicos cadastrados no framework.
	 */
	Retorno<String, Collection<br.ueg.unucet.quid.extensao.interfaces.IServico>> listaServicos();
	
	/**
	 * Metodo responsavel por listar os parametros que contem um servico.
	 * @param servico IServico que sera pesquisado os parametros.
	 * @return Lista de parametros do servico.
	 */
	Retorno<String, Collection<IParametro<?>>> listaParametrosServico(IServico servico);
	
	/**
	 * Metodo que verifica os parametros de um servico, retornando uma lista contendo os parametros ivalidos / nao preenchidos.
	 * @param servico IServico que sera validado os parametros.
	 * @return Retorno da execucao da validacao.
	 */
	Retorno <String, Collection<String>> verificarParametrosServico(IServico servico);
}
