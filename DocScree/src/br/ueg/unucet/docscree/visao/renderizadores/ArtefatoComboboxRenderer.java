package br.ueg.unucet.docscree.visao.renderizadores;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.ueg.unucet.quid.dominios.Artefato;

/**
 * Renderiza o Objeto Artefato sobre um comboBox
 * 
 * @author Diego
 *
 */
@Deprecated
public class ArtefatoComboboxRenderer implements ComboitemRenderer<Artefato>{

	@Override
	public void render(Comboitem comboitem, Artefato artefato, int index)
			throws Exception {
		comboitem.setLabel(artefato.getNome());
		comboitem.setValue(artefato);
	}

}
