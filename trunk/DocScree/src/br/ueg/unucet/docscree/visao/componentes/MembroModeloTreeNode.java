package br.ueg.unucet.docscree.visao.componentes;

import org.zkoss.zul.DefaultTreeNode;

import br.ueg.unucet.docscree.modelo.MembroModelo;

public class MembroModeloTreeNode extends DefaultTreeNode<MembroModelo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8571345271208006125L;
	private boolean aberto = true;
	
	public MembroModeloTreeNode(MembroModelo data,
			DefaultTreeNode<MembroModelo>[] children) {
		super(data, children);
	}

	public MembroModeloTreeNode(MembroModelo data,
			DefaultTreeNode<MembroModelo>[] children, boolean open) {
		super(data, children);
		setAberto(open);
	}

	public MembroModeloTreeNode(MembroModelo data) {
		super(data);
	}

	/**
	 * @return boolean o(a) aberto
	 */
	public boolean isAberto() {
		return aberto;
	}

	/**
	 * @param aberto o(a) aberto a ser setado(a)
	 */
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

}
