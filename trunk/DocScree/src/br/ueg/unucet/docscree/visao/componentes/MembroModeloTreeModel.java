package br.ueg.unucet.docscree.visao.componentes;

import org.zkoss.zul.DefaultTreeModel;

import br.ueg.unucet.docscree.modelo.MembroModelo;

public class MembroModeloTreeModel extends DefaultTreeModel<MembroModelo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4498400566868961514L;
	
	MembroModeloTreeNode raiz;

	public MembroModeloTreeModel(MembroModeloTreeNode root) {
		super(root);
		raiz = root;
	}
	
	/**
     * remove the nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code>
     * 
     * @param parent
     *            The parent of nodes are removed
     * @param indexFrom
     *            the lower index of the change range
     * @param indexTo
     *            the upper index of the change range
     * @throws IndexOutOfBoundsException
     *             - indexFrom < 0 or indexTo > number of parent's children
     */
    public void remove(MembroModeloTreeNode parent, int indexFrom, int indexTo) throws IndexOutOfBoundsException {
        for (int i = indexTo; i >= indexFrom; i--)
            try {
                parent.getChildren().remove(i);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
    }
 
    public void remove(MembroModeloTreeNode target) throws IndexOutOfBoundsException {
        int index = 0;
        MembroModeloTreeNode parent = null;
        // find the parent and index of target
        parent = procurarPai(raiz, target);
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index).equals(target)) {
                break;
            }
        }
        remove(parent, index, index);
    }
 
    /**
     * insert new nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code> by new nodes <code>newNodes</code>
     * 
     * @param parent
     *            The parent of nodes are inserted
     * @param indexFrom
     *            the lower index of the change range
     * @param indexTo
     *            the upper index of the change range
     * @param newNodes
     *            New nodes which are inserted
     * @throws IndexOutOfBoundsException
     *             - indexFrom < 0 or indexTo > number of parent's children
     */
    public void insert(MembroModeloTreeNode parent, int indexFrom, int indexTo, MembroModeloTreeNode[] newNodes)
            throws IndexOutOfBoundsException {
        for (int i = indexFrom; i <= indexTo; i++) {
            try {
                parent.getChildren().add(i, newNodes[i - indexFrom]);
            } catch (Exception exp) {
                throw new IndexOutOfBoundsException("Out of bound: " + i + " while size=" + parent.getChildren().size());
            }
        }
    }
 
    /**
     * append new nodes which parent is <code>parent</code> by new nodes
     * <code>newNodes</code>
     * 
     * @param parent
     *            The parent of nodes are appended
     * @param newNodes
     *            New nodes which are appended
     */
    public void add(MembroModeloTreeNode parent, MembroModeloTreeNode[] newNodes) {
        for (int i = 0; i < newNodes.length; i++) {
            parent.getChildren().add(newNodes[i]);
        }
    }
    
    public void add(MembroModeloTreeNode parent, MembroModeloTreeNode newNode) {
    	parent.getChildren().add(newNode);
    }
 
    private MembroModeloTreeNode procurarPai(MembroModeloTreeNode node, MembroModeloTreeNode target) {
        if (node.getChildren() != null && node.getChildren().contains(target)) {
            return node;
        } else {
            int size = getChildCount(node);
            for (int i = 0; i < size; i++) {
            	MembroModeloTreeNode parent = procurarPai((MembroModeloTreeNode) getChild(node, i), target);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }

}
