package br.ueg.unucet.quid.interfaces;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import br.ueg.unucet.quid.dominios.Servico;
import br.ueg.unucet.quid.excessoes.ServicoExcessao;
import br.ueg.unucet.quid.extensao.interfaces.IServico;

/**
 * Interface que define as operacoes dos servicos da entidade Servico
 * @author QUID
 *
 * @param <T> Entidade que representa o servico no framework. 
 * @param <oid> Classe da chave primaria.
 */
public interface IServicoControle<T, oid> extends IControle<T, oid>{
	
	
	/**Metodo que retorna uma instancia de um servico mapeado no framework
	 * @param servico Servico mapeado no framework
	 * @return Instancia a classe de servico
	 */
	IServico getInstanciaServico(Servico servico);
	
	/**Metodo responsavel por mapear os servicos 
	 * @param arquivos
	 * @return
	 * @throws ServicoExcessao Excessao na inicializacao do servico
	 */
	Map<File, String> mapearServicos(File[] arquivos) throws ServicoExcessao;
	
	/**Metodo responsavel por realizar a remocao do servico do framework
	 * @param servico Servico a ser removido
	 * @throws ServicoExcessao Excessao caso aja alguma falha na exclusao
	 */
	void remover(IServico iServico) throws ServicoExcessao;
	
	/**Metodo que retorna a lista de servicos cadastrados no framework
	 * @return Lista de Servicos cadastrados 
	 */
	Collection<IServico> listaServicosCadastrados();
	
	/**
	 * Metodo que mapeia um servico a partir de sua classe IServico
	 * @param iservico IServico  que sera mapeado
	 * @return Objeto servico construido a partir do IServico.
	 */
	Servico getMapeamentoIServico(IServico iservico);
	
}
