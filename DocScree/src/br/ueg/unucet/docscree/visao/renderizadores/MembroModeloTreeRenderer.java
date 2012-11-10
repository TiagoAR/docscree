package br.ueg.unucet.docscree.visao.renderizadores;

import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import br.ueg.unucet.docscree.modelo.MembroModelo;
import br.ueg.unucet.docscree.visao.componentes.MembroModeloTreeNode;

public class MembroModeloTreeRenderer implements TreeitemRenderer<MembroModeloTreeNode> {

	@Override
	public void render(Treeitem treeItem, MembroModeloTreeNode treeNode, int index)
			throws Exception {
		MembroModeloTreeNode itemModeloNo = treeNode;
		MembroModelo itemModelo = (MembroModelo) itemModeloNo.getData();
        Treerow dataRow = new Treerow();
        dataRow.setParent(treeItem);
        treeItem.setValue(itemModeloNo);
        treeItem.setOpen(itemModeloNo.isAberto());

        Hlayout hl = new Hlayout();
        String indice = String.valueOf(itemModelo.getGrau()).trim();
        if (itemModelo.getOrdem() > 0) {
        	indice += "." + String.valueOf(itemModelo.getOrdem()).trim();
        }
        hl.appendChild(new Label(indice));
        hl.appendChild(new Label(itemModelo.getArtefato().getNome()));
        hl.setSclass("h-inline-block");
        Treecell treeCell = new Treecell();
        treeCell.appendChild(hl);
        dataRow.setDraggable("true");
        dataRow.appendChild(treeCell);
    }
	
}