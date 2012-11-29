package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Textbox;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Componente para exibição e preenchimento de parâmetros com Domínio do Tipo Caracteres
 * 
 * @author Diego
 *
 */
public class ComboalfanumericoComponente implements IComponenteDominio {

	/**
	 * @see IComponenteDominio#getComponente(IParametro, String)
	 */
	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro, String width) {
		Textbox textbox = new Textbox();
		textbox.setWidth(width);
		textbox.setTooltiptext("Digite aqui o "+ parametro.getRotulo());
		return textbox; 
	}

	/**
	 * @see IComponenteDominio#getValor(HtmlBasedComponent)
	 */
	@Override
	public Object getValor(HtmlBasedComponent componente) {
		return ((Textbox) componente).getValue();
	}

	/**
	 * @see IComponenteDominio#getValorClass()
	 */
	@Override
	public Class<?> getValorClass() {
		return String.class;
	}

	/**
	 * @see IComponenteDominio#setValor(HtmlBasedComponent, Object)
	 */
	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null)
			((Textbox) componente).setValue(String.valueOf(valor));
	}
}