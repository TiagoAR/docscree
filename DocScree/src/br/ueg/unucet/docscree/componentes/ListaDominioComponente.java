package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelArray;

import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public class ListaDominioComponente implements IComponenteDominio {

	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro,
			String width) {
		Combobox combobox = new Combobox();
		combobox.setWidth(width);
		combobox.setTooltiptext("Escolha o "+ parametro.getRotulo());
		combobox.setModel(new BindingListModelListModel<>(new ListModelArray<>(parametro.getListaDominioTipo().toArray())));
		return combobox;
	}

	@Override
	public Object getValor(HtmlBasedComponent componente) {
		return ((Combobox) componente).getSelectedItem().getValue();
	}

	@Override
	public Class<?> getValorClass() {
		return String.class;
	}

}
