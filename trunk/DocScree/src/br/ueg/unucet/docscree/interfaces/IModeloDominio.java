package br.ueg.unucet.docscree.interfaces;

import org.zkoss.zk.ui.HtmlBasedComponent;

import br.ueg.unucet.docscree.visao.compositor.ModeloCompositor;

public interface IModeloDominio {
	
	/**
	 * Método que retorna o componente ZK para exibição no Descritor de tela
	 * 
	 * @param width largura do componente
	 * @return HtmlBasedComponent componente para ser visualizado
	 */
	HtmlBasedComponent getComponente(ModeloCompositor visao, String width);
	/**
	 * Método que retorna o valor do Componente representado na visão
	 * 
	 * @param componente instancia do componente na visão
	 * @return Object valor preenchido/selecionado
	 */
	Object getValor(HtmlBasedComponent componente);
	/**
	 * Método que seta o valor da representação do Componente na visão
	 * 
	 * @param componente instancia do componente na visão
	 * @param valor a ser setado no componente
	 */
	void setValor(HtmlBasedComponent componente, Object valor);
	/**
	 * Retorna a classe do valor do componente
	 * 
	 * @return Class do valor do componente
	 */
	Class<?> getValorClass();

}
