package br.ueg.unucet.docscree.componentes;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelArray;

import br.ueg.unucet.docscree.interfaces.IModeloDominio;
import br.ueg.unucet.docscree.modelo.MembroModelo;
import br.ueg.unucet.docscree.visao.compositor.ModeloCompositor;
import br.ueg.unucet.docscree.visao.renderizadores.ArtefatoComboboxRenderer;
import br.ueg.unucet.quid.dominios.Artefato;

public class ArtefatoItemModelo implements IModeloDominio{

	@Override
	public HtmlBasedComponent getComponente(ModeloCompositor visao, String width) {
		Combobox combo = new Combobox();
		combo.setWidth(width);
		combo.setItemRenderer(new ArtefatoComboboxRenderer());
		List<Artefato> lista = new ArrayList<Artefato>();
		for (MembroModelo item : visao.getItensModelo().values()) {
			lista.add(item.getArtefato());
		}
		combo.setModel(new BindingListModelListModel<Artefato>(new ListModelArray<Artefato>(lista)));
		if (lista.isEmpty()) {
			combo.setDisabled(true);
		}
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
			Comboitem item = new Comboitem();
			Artefato artefatoValor = (Artefato) valor;
			item.setLabel(artefatoValor.getNome());
			item.setValue(artefatoValor);
			combobox.setSelectedItem(item);
		}
	}

	@Override
	public Class<?> getValorClass() {
		return Artefato.class;
	}

}
