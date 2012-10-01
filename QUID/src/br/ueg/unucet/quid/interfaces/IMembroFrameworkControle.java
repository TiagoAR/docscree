package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.MembroFramework;
import br.ueg.unucet.quid.excessoes.MembroExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

/**
 * Interface responsavel por definir as operacoes do controlador sobre a entidade MembroFramework.
 * @author QUID
 *
 * @param <T>Classe que representa a entidade MembroFramework
 * @param <oid> Classe da chave primaria da entidade.
 */
public interface IMembroFrameworkControle<T, oid> extends IControle<T, oid> {
	
	/**Realiza o mapeamento de um novo membro para o framework
	 * @param membro Membro que sera mapeado
	 * @throws MembroExcessao Excessao caso aja alguma falha na insersao do membro
	 */
	void inserir(Membro membro) throws MembroExcessao;
	
	void alterar(Membro membro) throws MembroExcessao;
	
	/**Realiza a pesquisa dos membros cadastrados no framework
	 * @param nome Nome do membro a ser pesquisado. (Pesquisa parcial)
	 * @param tipoMembro TipoMembro a ser pesquisado
	 * @return Lista de membros encontrados com essas caracteristicas
	 * @throws MembroExcessao Excessao caso aja alguma falha na pesquisa
	 */
	Collection<Membro> pesquisar(String nome, ITipoMembroModelo tipoMembro) throws MembroExcessao;
	
	Collection<Membro> pesquisar(ITipoMembroModelo tipoMembroModelo) throws MembroExcessao;
	
	/**Metodo responsavel por realizar a tranformacao de um conjunto de Membros, em um conjunto de membros que podem ser mapeados pelo framework
	 * @param membros Membros a serem mapeados
	 * @return Colecao de membros para serem persistidos por alguma rotina do framework
	 * @throws MembroExcessao Excessao caso aja alguma falha de conversao
	 */
	Collection<MembroFramework> transformarMembroFramework(Collection<Membro> membros) throws MembroExcessao;

	/**Metodo responsavel por transformar um membro em um mapeamento do membro no framework
	 * @param membro Membro a ser transformado
	 * @return Mapeamento do membro no framework
	 * @throws MembroExcessao Excessao caso aja alguma falha de conversao
	 */
	MembroFramework transformarMembroFramework(Membro membro) throws MembroExcessao;
	
	/**Metodo responsavel por transformar um membro mapeado no framework em um membro
	 * @param membroFramework Membro a ser mapeado
	 * @return Membro mapeado
	 */
	Membro transformarMembroFramework(MembroFramework membroFramework);
}
