package br.ueg.unucet.quid.interfaces;

import br.ueg.unucet.quid.extensao.enums.MultiplicidadeEnum;





/**
 * Interface que define as operacoes basicas sobre a entidade ITemModelo
 * @author QUID
 *
 */
public interface IItemModelo {

	/**
	 * Retorna o Artefato do ItemModelo
	 * 
	 * @return Artefato que representa o ItemModelo
	 */
	IArtefato getArtefato();

	/**
	 * Atribui um artefato ao ItemModelo
	 * 
	 * @param artefato
	 *            Artefato do ItemModelo
	 */

	void setArtefato(IArtefato artefato);

	/**
	 * Retorna o grau do ItemModelo no modelo.
	 * 
	 * @return Numero do grau do artefato no modelo.
	 */
	Integer getGrau();

	/**
	 * Atribui a grau do ItemModelo no modelo.
	 * 
	 * @param grau
	 */
	void setGrau(Integer grau);

	/**
	 * Retorna a ordem do ItemModelo no artefato. Para dois artefatos de mesmo
	 * grau, a ordem define quem vem primeiro.
	 * 
	 * @return Numero da ordem do ItemModelo no modelo.
	 */
	Integer getOrdem();

	/**
	 * Atribui a ordem do ItemModelo no modelo.
	 * 
	 * @param ordem
	 *            Ordem do ItemModelo no modelo
	 */
	void setOrderm(Integer ordem);

	/**
	 * Retorna a multiplicidade do ItemModelo no modelo. A multiplicidade indica
	 * quantas vezes o artefato se repete no documento.
	 * 
	 * @return Multiplicidade do ItemModelo.
	 */
	MultiplicidadeEnum getMultiplicidade();

	/**
	 * Atribui a multiplicidade do ItemModelo no modelo.
	 * 
	 * @param multiplicidade
	 *            Multiplicidade do ItemModelo.
	 */
	void setMultiplicidade(MultiplicidadeEnum multiplicidade);

	/**
	 * Retorna a ordem de preenchimento do ItemModelo no modelo, idependente da
	 * ordem de apresentacao. Artefatos auto-criados (Indices, Listas, etc) nao
	 * tem ordem de preenchimento.
	 * 
	 * @return Numero da ordem de preenchimento.
	 */
	Integer getOrdemPreenchimento();

	/**
	 * Atribui a ordem de preenchimento a um ItemModelo.
	 * 
	 * @param ordemPreenchimento
	 *            Ordem de preenchimento do ItemModelo no modelo.
	 */
	void setOrdemPreenchimento(Integer ordemPreenchimento);

}
