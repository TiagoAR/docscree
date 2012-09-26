package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Textbox;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public class CaracteresComponente implements IComponenteDominio {

	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro, String width) {
		Textbox textbox = new Textbox();
		textbox.setWidth(width);
		textbox.setTooltiptext("Digite aqui o "+ parametro.getRotulo());
		return textbox;
	}

	@Override
	public Object getValor(HtmlBasedComponent componente) {
		return ((Textbox) componente).getValue();
	}

	@Override
	public Class<?> getValorClass() {
		return String.class;
	}

	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null)
			((Textbox) componente).setValue(String.valueOf(valor));
	}
}