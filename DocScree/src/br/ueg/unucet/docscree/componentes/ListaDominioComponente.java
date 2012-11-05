package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelArray;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Componente para exibição e preenchimento de parâmetros que contém uma Lista de Domínios
 * ou seja, não tem um Domínio, mas contem uma lista para escolha do valor
 * 
 * @author Diego
 *
 */
public class ListaDominioComponente implements IComponenteDominio {

	/**
	 * @see IComponenteDominio#getComponente(IParametro, String)
	 */
	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro,
			String width) {
		Combobox combobox = new Combobox();
		combobox.setWidth(width);
		combobox.setTooltiptext("Escolha o "+ parametro.getRotulo());
		combobox.setModel(new BindingListModelListModel<>(new ListModelArray<>(parametro.getListaDominioTipo().toArray())));
		return combobox;
	}

	/**
	 * @see IComponenteDominio#getValor(HtmlBasedComponent)
	 */
	@Override
	public Object getValor(HtmlBasedComponent componente) {
		Comboitem selectedItem = ((Combobox) componente).getSelectedItem();
		if (selectedItem != null) {
			return selectedItem.getValue();
		}
		return null;
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
		if (valor != null) {
			Combobox combobox = (Combobox) componente;
			combobox.setValue(String.valueOf(valor));
//			ListModel<?> model = combobox.getModel();
//			if (valor.getClass() == boolean.class || valor.getClass() == Boolean.class) {
//				valor = Boolean.valueOf(valor.toString()).equals(Boolean.TRUE) ? "Sim" : "Não";
//			}
//			int indice = 0;
//			for (int i = 0; i < model.getSize(); i++) {
//				if (String.valueOf(model.getElementAt(i)).equals(String.valueOf(valor))) {
//					indice = i;
//					break;
//				}
//			}
//			combobox.setSelectedIndex(indice);
		}
	}

}
