package br.ueg.unucet.quid.extensao.interfaces;

import javax.swing.Icon;

/**
 * Inteface que representa um TipoMembroVisao no framework.
 * @author QUID
 *
 */
public interface ITipoMembroVisao extends ITipoMembro {

	/**
	 * Retorna o nome da plataforma em que o TipoMembro visao foi implementado.
	 * 
	 * @return Identificador da plataforma
	 */
	String getPlataforma();

	/**
	 * Retorna imagem ilustrativa que sera mostrada ao usuario na montagem do
	 * artefato.
	 * 
	 * @return Icon ilustrativo
	 */
	Icon getImagemIlustrativa();

	/**
	 * Retorna um objeto especifico da visao, que permite o usuario preencher os
	 * dados
	 * 
	 * @return Objeto que represente a visao de preenchimento do TipoMembro
	 */
	Object getVisaoPreenchimento();

	/** Retorna um objeto especifico da visao, que permite o usuario visualizar os 
	 * dados preenchidos
	 * @return Objeto que represente a  visao de preenchimento do TipoMembro
	 */
	Object getVisaoVisualizacao();

	/** Verifica se uma entrada feita pelo usuario e valida
	 * @param valor Valor informado pelo usuario
	 * @return True para validacao correta ou false caso contrario
	 */
	boolean isEntradaValida(Object valor);
	
	/** Retorna o nome do evento no qual a mascara sera executada
	 * @return Nome do evento
	 */
	String getEventoMascara();
	
	/**Metodo que identifica o nome do TipoMembro modelo que o TipoMembro visao da suporte
	 * @return Nome do TipoMembro modelo.
	 */
	String getNomeTipoMembroModelo();
	
	void setValor(Object valor);
	
	void setDescricao(String descricao);
}
