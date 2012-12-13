package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Textbox;

import br.ueg.unucet.docscree.interfaces.IModeloDominio;
import br.ueg.unucet.docscree.visao.compositor.ModeloCompositor;

public class StringItemModelo implements IModeloDominio {

	@Override
	public Object getValor(HtmlBasedComponent componente) {
		return ((Textbox) componente).getValue();
	}

	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null) {
			((Textbox) componente).setValue(String.valueOf(valor));
		}
	}

	@Override
	public Class<?> getValorClass() {
		return String.class;
	}

	@Override
	public HtmlBasedComponent getComponente(ModeloCompositor visao,
			String width) {
		Textbox textbox = new Textbox();
		textbox.setWidth(width);
		return textbox;
	}

}
