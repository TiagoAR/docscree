package br.ueg.unucet.docscree.utilitarios;

import javax.swing.ImageIcon;

import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import sun.awt.image.ToolkitImage;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

public class TipoMembroRenderer implements RowRenderer<ITipoMembroVisao> {

	@Override
	public void render(Row row, ITipoMembroVisao data, int index)
			throws Exception {
		Image image = new Image();
		String nome = data.getNome();
		image.setContent(  ((ToolkitImage)  ((ImageIcon)data.getImagemIlustrativa()).getImage() ).getBufferedImage()   );
		image.setTooltiptext(nome);
		row.appendChild(image);
		Label label = new Label(nome);
		label.setTooltiptext(nome);
		row.appendChild(label);
	}

}
