package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.StatusEnum;
import br.ueg.unucet.quid.extensao.interfaces.IIdentificavel;



/**
 * Classe responsavel por definir a interface padrao para operacao de um modelo.
 * @author QUID
 *
 */
public interface IModelo extends IIdentificavel {

	/**
	 * Retorna a lista de ItemModelo que compoem o Modelo.
	 * 
	 * @return Lista de ItemModelo
	 */
	Collection<IItemModelo> getListaItemModelo();

	/**
	 * Atribui a lista de ItemModelo no modelo.
	 * 
	 * @param listaItemModelo
	 *            Lista de ItemModelo
	 */
	void setListaItemModelo(Collection<IItemModelo> listaItemModelo);

	/**
	 * Retorna o status em que o modelo se encontra. Se o modelo estiver com o
	 * status INATIVO, ele nao podera ser preenchido por nenhum usuario.
	 * 
	 * @return Status do modelo.
	 */
	StatusEnum getStatus();

	/**
	 * Atribui um status a um modelo.
	 * 
	 * @param status
	 *            Status do Modelo.
	 */
	void setStatus(StatusEnum status);
}
