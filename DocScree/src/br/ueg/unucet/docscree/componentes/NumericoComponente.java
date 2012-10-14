package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Spinner;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Componente para exibição e preenchimento de parâmetros com Domínio do Tipo Numérico
 * 
 * @author Diego
 *
 */
public class NumericoComponente implements IComponenteDominio {

	/**
	 * @see IComponenteDominio#getComponente(IParametro, String)
	 */
	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro, String width) {
		Spinner spinner = new Spinner();
		spinner.setWidth(width);
		spinner.setTooltiptext("Digite aqui o "+ parametro.getRotulo());
		return spinner;
	}

	/**
	 * @see IComponenteDominio#getValor(HtmlBasedComponent)
	 */
	@Override
	public Object getValor(HtmlBasedComponent componente) {
		Object valor = 0;
		try {
			valor = ((Spinner) componente).getValue();
		} catch (Exception e) {
		}
		return valor;
	}
	
	/**
	 * @see IComponenteDominio#setValor(HtmlBasedComponent, Object)
	 */
	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null)
			((Spinner) componente).setValue(Integer.valueOf(valor.toString()));
	}
	
	/**
	 * @see IComponenteDominio#getValorClass()
	 */
	@Override
	public Class<?> getValorClass() {
		return Integer.class;
	}

}
