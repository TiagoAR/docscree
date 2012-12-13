package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelArray;

import br.ueg.unucet.docscree.interfaces.IModeloDominio;
import br.ueg.unucet.docscree.visao.compositor.ModeloCompositor;

public class EnumItemModelo implements IModeloDominio{

	@Override
	public HtmlBasedComponent getComponente(ModeloCompositor visao, String width) {
		Combobox combo = new Combobox();
		combo.setWidth(width);
		combo.setModel(new BindingListModelListModel<>(new ListModelArray<>(visao.getMultiplicidadeEnum())));
		return combo;
	}

	@Override
	public Object getValor(HtmlBasedComponent componente) {
		Comboitem selectedItem = ((Combobox) componente).getSelectedItem();
		if (selectedItem != null) {
			return selectedItem.getValue();
		}
		return null;
	}

	@Override
	public void setValor(HtmlBasedComponent componente, Object valor) {
		if (valor != null) {
			Combobox combobox = (Combobox) componente;
			combobox.setValue(String.valueOf(valor));
		}
	}

	@Override
	public Class<?> getValorClass() {
		return Enum.class;
	}

}
