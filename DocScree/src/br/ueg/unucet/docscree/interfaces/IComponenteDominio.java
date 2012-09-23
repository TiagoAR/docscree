package br.ueg.unucet.docscree.interfaces;

import org.zkoss.zk.ui.HtmlBasedComponent;

import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public interface IComponenteDominio {
	
	HtmlBasedComponent getComponente(IParametro<?> parametro, String width);
	Object getValor(HtmlBasedComponent componente);
	Class<?> getValorClass();

}
