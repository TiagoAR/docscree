package br.ueg.unucet.docscree.visao.componentes;

import org.zkoss.zul.DefaultTreeNode;

import br.ueg.unucet.docscree.modelo.MembroModelo;

/**
 *	Representa o Nó a ser visualizado sobre a árvore na visão ZK
 * 
 * @author Diego
 *
 */
public class MembroModeloTreeNode extends DefaultTreeNode<MembroModelo> {

	/**
	 * DEFAULT SERIAL ID
	 */
	private static final long serialVersionUID = -8571345271208006125L;
	/**
	 * Atributo que representa se o nó deve aparecer aberto ou não
	 */
	private boolean aberto = true;
	
	/**
	 * Construtor 1
	 * 
	 * @param data
	 * @param children
	 */
	public MembroModeloTreeNode(MembroModelo data,
			DefaultTreeNode<MembroModelo>[] children) {
		super(data, children);
	}

	/**
	 * Construtor 2
	 * 
	 * @param data
	 * @param children
	 * @param open
	 */
	public MembroModeloTreeNode(MembroModelo data,
			DefaultTreeNode<MembroModelo>[] children, boolean open) {
		super(data, children);
		setAberto(open);
	}

	/**
	 * Construtor principal
	 * 
	 * @param data
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MembroModeloTreeNode))
			return false;
		MembroModeloTreeNode other = (MembroModeloTreeNode) obj;
		if (getData() == null) {
			if (other.getData() != null)
				return false;
		} else if (!getData().equals(other.getData()))
			return false;
		return true;
	}

}
