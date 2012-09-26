package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Spinner;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public class NumericoComponente implements IComponenteDominio {

	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro, String width) {
		Spinner spinner = new Spinner();
		spinner.setWidth(width);
		spinner.setTooltiptext("Digite aqui o "+ parametro.getRotulo());
		return spinner;
	}

	@Override
	public Object getValor(HtmlBasedComponent componente) {
		return ((Spinner) componente).getValue();
	}
	
	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null)
			((Spinner) componente).setValue(Integer.valueOf(valor.toString()));
	}
	
	@Override
	public Class<?> getValorClass() {
		return Integer.class;
	}

}
