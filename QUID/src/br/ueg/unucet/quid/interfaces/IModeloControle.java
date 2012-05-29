package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.ItemModelo;

/**
 * Interface responsavel por definir as operacoes do controlador do Modelo
 * @author QUID
 *
 * @param <T> Entidade que representa o Modelo no framework
 * @param <oid> Classe da chave primaria da Entidade
 */
public interface IModeloControle<T, oid> extends IControle<T, oid> {
	
	/**
	 * Metodo responsavel por verificar a duplicidade de cadastro do cadastro do modelo a partir de seu nome.
	 * @param modelo Modelo que sera verificado a duplicidade
	 * @return True caso o modelo esteje duplicado e false caso contrario.
	 */
	boolean verificarDuplicidade(T modelo);
	/**
	 * Metodo responsvael por validar os campos obrigatorios do ItemModelo
	 * @param itemModelo ItemModelo que sera verificado os campos obrigatorios.
	 * @return Lista
	 */
	Collection<String> validarItemModelo(ItemModelo itemModelo);

}
