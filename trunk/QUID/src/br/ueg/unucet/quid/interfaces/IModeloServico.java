package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Interface responsavel por definir as operacoes que ocorrem sobre o servico do Modelo
 * @author QUID
 *
 * @param <T>Entidade que representa o modelo no framework.
 */
public interface IModeloServico<T> extends IServico<T>{
	/**
	 * Metodo responsavel por criar uma nova instancia do Modelo para ser preenchido.
	 * @param nome Nome do modelo que sera criado.
	 * @param descricao Descricao do modelo que sera criado
	 * @return Retorno da execucao do metodo com a instancia do modelo ou falha na execucao caso ocorra algum problema.
	 */
	Retorno<String, T>criarModelo(String nome, String descricao);
	/**
	 * Metodo responsavel por realizar a criacao de um novo ItemModelo a partir do artefato.
	 * @param artefato Artefato para o qual sera criado o item modelo.
	 * @return Instancia do ItemModelo ou falha na execucao caso ocorra algum erro.
	 */
	Retorno<String, ItemModelo> criarItemModelo(Artefato artefato);
	/**
	 * Metodo responsavel por inserir um ItemModelo dentro do modelo.
	 * @param modelo Modelo que sera inserido o ItemModelo
	 * @param itemModelo ItemModelo que sera inserido dentro do modelo.
	 * @return Sucesso de execucao, ou lista de parametros nao informados do ItemModelo
	 */
	Retorno<String, Collection<String>> incluirItemModelo(Modelo modelo, ItemModelo itemModelo);
	/**
	 * Metodo responsavel por remover um artefato do Modelo. 
	 * @param artefato Artefato que sera removido do modelo.
	 * @param modelo Modelo para qual o artefato sera removido.
	 * @return Sucesso de execucao removendo o artefato do modelo ou falha na execucao retornando a mensagem do problema.
	 */
	Retorno<Object, Object> removerArtefatoModelo(Artefato artefato, Modelo modelo);
	/**
	 * Metodo responsavel por verificar se os campos informaos de um ItemModelo estao correstos.
	 * @param itemModelo ItemModelo que sera validado.
	 * @param modelo Modelo que pertence o ItemModelo
	 * @return Sucesso na execucao ou lista de campos nao informados / invalidos do ItemModelo.
	 */
	Retorno<String, Collection<String>> validarItemModelo(ItemModelo itemModelo, Modelo modelo);
	/**
	 * Metodo responsavel por mapear um novo Modelo para o framework.
	 * @param modelo Modelo que sera mapeado.
	 * @return Sucesso de execucao ou falha na execucao com mensagem informando o tipo da falha.
	 */
	Retorno<Object, Object> mapearModelo(Modelo modelo);
	Retorno<String, Collection<Modelo>> listarModelos();
}
