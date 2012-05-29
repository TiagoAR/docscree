package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.excessoes.ArtefatoExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
/**
 * Interface que representa as operacoes de manipulacao do artefato no controlador.
 * @author QUID
 *
 */
public interface IArtefatoControle<T, oid> extends IControle<T, oid>{
	
	/**Metodo responsavel por verificar se os tipo membro esta com os parametros validos
	 * @param membro Membro que sera validado
	 * @throws ArtefatoExcessao	excessao caso aja falha na verificacao 
	 */
	void verificarMembro(Membro membro) throws ArtefatoExcessao;

	/**Metodo responsavel por reorganizar a lista de servicos do artefato quando ocorre a remocao do mesmo do artefato
	 * @param servico Servico que foi removido do artefato
	 */
	void reorganizarServico(IServico servico);

	/**Metodo que verifica os parametros do servico
	 * @param servico Servico para qual sera verificado  os parametros
	 * @return Mapeamento de nomes dos parametros invalidos
	 */
	Retorno<String, Collection<String>> verificarServico(IServico servico);
	
	/**Metodo responsavel por realizar a execucao de um servico sobre um artefato
	 * @param artefato Artefato para o qual o servico sera executado
	 * @param servico Servico que sera executado
	 * @return Retorno da execucao do servico
	 */
	Retorno<Object, Object> executarServico(Artefato artefato, IServico servico);
	
	/**Metodo responsavel por realizar o mapeamento de um artefato no framework
	 * @param artefato Artefato que sera mapeado
	 * @return Retorno da execucao do mapeamento do artefato
	 */
	Retorno<String, Collection<String>> mapearArtefato(Artefato artefato);
	
	/**Metodo responsavel por realizar a pesquisa do artefato no framework
	 * @param categoria Categoria que pertence o artefato
	 * @param nome Nome do artefato
	 * @param descricao Descricao do artefato
	 * @return Retorno da busca
	 */
	Retorno<String, Collection<Artefato>> pesquisarArtefato(Categoria categoria, String nome, String descricao);

	/**Metodo responsavel por realizar o carregamento dos servicos e Membros de um artefato
	 * @param artefato Artefato a ser carregado
	 * @return Artefato carregado suas informacoes
	 */
	Artefato carregarArtefato(Artefato artefato);
}
