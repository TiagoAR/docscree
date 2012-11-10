package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleListModel;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ModeloControle;
import br.ueg.unucet.docscree.modelo.MembroModelo;
import br.ueg.unucet.docscree.visao.componentes.MembroModeloTreeModel;
import br.ueg.unucet.docscree.visao.componentes.MembroModeloTreeNode;
import br.ueg.unucet.docscree.visao.renderizadores.MembroModeloTreeRenderer;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.extensao.enums.MultiplicidadeEnum;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
@Scope("session")
public class ModeloCompositor extends GenericoCompositor<ModeloControle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525900582986637489L;
	
	//Atributos do BEAN
	@AtributoVisao(isCampoEntidade=true, nome="nome")
	private String fldNome;
	@AtributoVisao(isCampoEntidade=true, nome="descricao")
	private String fldDescricao;
	@AtributoVisao(isCampoEntidade=false, nome="itemModelo")
	private Map<String, MembroModelo> itensModelo;
	//Atributos auxiliares para visão
	@AtributoVisao(isCampoEntidade=false, nome="artefatoModeloSelecionado")
	private Artefato artefatoModeloSelecionado;
	
	private MembroModelo membroModeloSelecionado;
	
	private MembroModeloTreeModel modelItemModelo = new MembroModeloTreeModel(new MembroModeloTreeNode(null));
	private MembroModeloTreeRenderer treeRenderer = null;
	

	@Override
	public Class<?> getTipoEntidade() {
		return Modelo.class;
	}

	@Override
	protected void limparCampos() {
		setCodigo(null);
		setFldNome("");
		setFldDescricao("");
		setItensModelo(new HashMap<String, MembroModelo>());
		setArtefatoModeloSelecionado(null);
		setModelItemModelo(new MembroModeloTreeModel(new MembroModeloTreeNode(null)));
		super.binder.loadAll();
	}

	@Override
	protected void limparFiltros() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acaoFiltrar() {
		// TODO Auto-generated method stub
		
	}
	
	public void acaoAdicionarArtefatoModelo() {
		setMembroModeloSelecionado(criarItemModeloDoArtefato());
		//TODO abrir aqui
	}
	
	private MembroModelo criarItemModeloDoArtefato() {
		MembroModelo modelo = new MembroModelo();
		modelo.setArtefato(getArtefatoModeloSelecionado());
		modelo.setOrdemPai(0);
		return modelo;
	}
	
	public BindingListModelListModel<Artefato> getModelArtefatoModelo() {
		return new BindingListModelListModel<Artefato>(new SimpleListModel<Artefato>(new ArrayList()));
	}
	
	public MembroModeloTreeRenderer getItemModeloTreeRenderer() {
		if (this.treeRenderer == null) {
			this.treeRenderer = new MembroModeloTreeRenderer();
		}
		return this.treeRenderer;
	}

	public MultiplicidadeEnum[] getMultiplicidadeEnum() {
		return MultiplicidadeEnum.values();
	}
	
	public Rows getRowsItemModelo() {
		return (Rows) getComponent().getFellow("modalItemModelo").getFellow("rowsItemModelo");
	}
	
	//GETTERS AND SETTERS
	/**
	 * @return String o(a) fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param fldNome o(a) fldNome a ser setado(a)
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return String o(a) fldDescricao
	 */
	public String getFldDescricao() {
		return fldDescricao;
	}

	/**
	 * @param fldDescricao o(a) fldDescricao a ser setado(a)
	 */
	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}

	/**
	 * @return Artefato o(a) artefatoModeloSelecionado
	 */
	public Artefato getArtefatoModeloSelecionado() {
		return artefatoModeloSelecionado;
	}

	/**
	 * @param artefatoModeloSelecionado o(a) artefatoModeloSelecionado a ser setado(a)
	 */
	public void setArtefatoModeloSelecionado(Artefato artefatoModeloSelecionado) {
		this.artefatoModeloSelecionado = artefatoModeloSelecionado;
	}

	/**
	 * @return ItemModeloTreeModel o(a) modelItemModelo
	 */
	public MembroModeloTreeModel getModelItemModelo() {
		return modelItemModelo;
	}

	/**
	 * @param modelItemModelo o(a) modelItemModelo a ser setado(a)
	 */
	public void setModelItemModelo(MembroModeloTreeModel modelItemModelo) {
		this.modelItemModelo = modelItemModelo;
	}

	/**
	 * @return Map<String,MembroModelo> o(a) itensModelo
	 */
	public Map<String, MembroModelo> getItensModelo() {
		return itensModelo;
	}

	/**
	 * @param itensModelo o(a) itensModelo a ser setado(a)
	 */
	public void setItensModelo(Map<String, MembroModelo> itensModelo) {
		this.itensModelo = itensModelo;
	}

	/**
	 * @return MembroModelo o(a) membroModeloSelecionado
	 */
	public MembroModelo getMembroModeloSelecionado() {
		return membroModeloSelecionado;
	}

	/**
	 * @param membroModeloSelecionado o(a) membroModeloSelecionado a ser setado(a)
	 */
	public void setMembroModeloSelecionado(MembroModelo membroModeloSelecionado) {
		this.membroModeloSelecionado = membroModeloSelecionado;
	}

}
