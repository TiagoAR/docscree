package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

/**
 * Inteface que define as operacoes da camada de servico sobre a entidade Membro.
 * @author QUID
 *
 * @param <T> Classe que representa a entidade Membro.
 */
public interface IMembroServico<T> extends IServico<T> {
	/**
	 * Metodo responsavel por realizar a insercao de um novo Membro no framework.
	 * @param equipe Menbro que sera inserida.
	 * @return Retorno da execucao da operacao.
	 */
	Retorno<Object, Object> inserir(Membro membro);
	
	Retorno<Object, Object> alterar(Membro membro);

	Retorno<Object, Object> remover(Membro membro);
	
	/**
	 * Metodo que realiza a pesquisa de membros cadastrados no framework com base em seu nome ou no seu TipoMembroModelo
	 * @param nome Nome do Membro que se deseja pesquisar
	 * @param tipoMembroModelo TipoMembroModelo que se deseja pesquisar
	 * @return Retorno execucao da operacao.
	 */
	Retorno<String, Collection<Membro>> pesquisarMembro(String nome, ITipoMembroModelo tipoMembroModelo);
	/**
	 * Metodo responsavel por realizar a pesquisa de membros cadastrados no framework a partir de seu ITipoMembroModelo.
	 * @param tipoMembroModelo Objeto ITipoMembro que sera realizado a pesquisa dos membros.
	 * @return Lista de Membros que contem o objeto ITipoMembroModelo como base.
	 */
	Retorno<String, Collection<Membro>> pesquisarMembro(ITipoMembroModelo tipoMembroModelo);
}
