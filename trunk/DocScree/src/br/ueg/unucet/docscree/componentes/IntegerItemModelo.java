package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Intbox;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.interfaces.IModeloDominio;
import br.ueg.unucet.docscree.visao.compositor.ModeloCompositor;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public class IntegerItemModelo implements IModeloDominio {

	/**
	 * @see IComponenteDominio#getComponente(IParametro, String)
	 */
	@Override
	public HtmlBasedComponent getComponente(ModeloCompositor visao, String width) {
		Intbox intbox = new Intbox();
		intbox.setWidth(width);
		return intbox;
	}

	/**
	 * @see IComponenteDominio#getValor(HtmlBasedComponent)
	 */
	@Override
	public Object getValor(HtmlBasedComponent componente) {
		Object valor = 0;
		try {
			valor = ((Intbox) componente).getValue();
		} catch (Exception e) {
		}
		return valor;
	}
	
	/**
	 * @see IComponenteDominio#setValor(HtmlBasedComponent, Object)
	 */
	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null) {
			((Intbox) componente).setValue(Integer.valueOf(valor.toString()));
		}
	}
	
	/**
	 * @see IComponenteDominio#getValorClass()
	 */
	@Override
	public Class<?> getValorClass() {
		return Integer.class;
	}

}
