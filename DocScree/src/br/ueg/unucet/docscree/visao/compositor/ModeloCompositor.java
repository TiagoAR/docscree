package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ModeloControle;
import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.modelo.MembroModelo;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.visao.componentes.MembroModeloTreeModel;
import br.ueg.unucet.docscree.visao.componentes.MembroModeloTreeNode;
import br.ueg.unucet.docscree.visao.renderizadores.MembroModeloTreeRenderer;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.enums.MultiplicidadeEnum;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

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
	@AtributoVisao(isCampoEntidade=false, nome="membroModeloSelecionado")
	private MembroModelo membroModeloSelecionado;
	
	private MembroModeloTreeModel modelItemModelo;
	private MembroModeloTreeRenderer treeRenderer = null;
	
	private AnnotateDataBinder binderModalArtefatoModelo = null;
	
	private Long codigoAntigo;
	private String nomeAntigo;
	private String descricaoAntiga;

	private AnnotateDataBinder binderAntigo;
	private Execution executionAntigo;
	private org.zkoss.zk.ui.Component componenteAntigo;
	

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
		setModelItemModelo(new MembroModeloTreeModel(new MembroModeloTreeNode(null, new MembroModeloTreeNode[] {})));
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
	
	public void carregarDados() {
		if (Executions.getCurrent().getSession().hasAttribute("ModeloAbrir")) {
			setArtefatoModeloSelecionado(null);
			setModelItemModelo(new MembroModeloTreeModel(new MembroModeloTreeNode(null, new MembroModeloTreeNode[] {})));
			setItensModelo(new HashMap<String, MembroModelo>());
			setEntidade((Persistivel) Executions.getCurrent().getSession().getAttribute("ModeloAbrir"));
			Executions.getCurrent().getSession().removeAttribute("ModeloAbrir");
			try {
				acaoExibirModelo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.binder.loadAll();
		} else {
			limparCampos();
		}
	}
	
	public void acaoNovoModelo() {
		Executions.sendRedirect("/pages/modelo.zul");
	}
	
	public void acaoSelecionarAbrirModelo() {
		salvarTela();
		setEntidade(null);
		this.exibirModalModelo("/componentes/modalAbrirModelo.zul");
	}
	
	public void exibirModalModelo(String zulModal) {
		org.zkoss.zk.ui.Component comp = Executions.createComponents(
				zulModal, null, null);

		if (comp instanceof Window) {
			((Window) comp).doModal();
		}
	}
	
	private void salvarTela() {
		codigoAntigo = getCodigo();
		nomeAntigo = fldNome;
		descricaoAntiga = fldDescricao;
		binderAntigo = binder;
		componenteAntigo = getComponent();
		executionAntigo = execution;
		
		binderModalArtefatoModelo = null;
	}
	
	public void acaoFecharModal() {
		String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
		getComponent().detach();
		if (requestPath.contains("modelo")) {
			execution = executionAntigo;
			super.setComponent(componenteAntigo);
			binder = binderAntigo;
			
			this.setCodigo(codigoAntigo);
			this.setFldNome(nomeAntigo);
			this.setFldDescricao(descricaoAntiga);
		}
	}
	
	public void abrirModelo (){
		Executions.sendRedirect("/pages/modelo.zul");
		Executions.getCurrent().getSession().setAttribute("ModeloAbrir", getEntidade());
	}
	
	public void mapearItensModelo() {
		List<MembroModelo> listaItemModelo = new ArrayList<MembroModelo>(getItensModelo().values());
		Collections.sort(listaItemModelo, new Comparator<MembroModelo>() {

			@Override
			public int compare(MembroModelo o1, MembroModelo o2) {
				if (o1 != null && o2 != null
						&& o1.getGrau() != null && o2.getOrdem() != null) {
					int comparador = o1.getGrau().compareTo(o2.getGrau());
					if (comparador == 0) {
						comparador = o1.getOrdem().compareTo(o2.getOrdem());
					}
					return comparador;
				}
				return 0;
			}
		});
		for (MembroModelo membroModelo : listaItemModelo) {
			renderizarNaArvore(membroModelo);
		}
	}
	
	private void acaoExibirModelo() throws Exception {
		getControle().fazerAcao("abrirModelo", (SuperCompositor) this);
	}
	
	/**
	 * Método que executa a ação do botão Adicionar, exibindo modal para preencher os dados do Item Modelo
	 */
	public void acaoAdicionarArtefatoModelo() {
		if (getArtefatoModeloSelecionado() != null) {
			setMembroModeloSelecionado(criarItemModeloDoArtefato());
			for (IParametro<?> parametro : getMembroModeloSelecionado().getListaParametros()) {
				try {
					String idComponente = "PARAMETRO"+parametro.getNome();
					String idRow = "ROW" + parametro.getNome();
					if (getRowsItemModelo().hasFellow(idRow)) {
						getRowsItemModelo().getFellow(idRow).detach();
					}
					HtmlBasedComponent componente = super.getComponentePorDominio(parametro, null);
					componente.setId(idComponente);
					IComponenteDominio componenteDominio = super.getInstanciaComponente(parametro);
					componenteDominio.setValor(componente, parametro.getValor());
					Row row = super.gerarRow(new org.zkoss.zk.ui.Component[] {super.gerarLabel(parametro.getRotulo()), componente});
					row.setId(idRow);
					getRowsItemModelo().appendChild(row);
				} catch (Exception e) {
					//Se ocorrer exception é erro de implementação do Desenvolvedor do TipoMembro/MembroModelo
				}
			}
			abrirModalItemModelo();
			getBinderItemModelo().loadAll();
		} else {
			Mensagens mensagens = new Mensagens();
			mensagens.getListaMensagens().add("É necessário escolher um ArtefatoModelo para adicionar");
			getControle().setMensagens(mensagens);
			mostrarMensagem(false);
		}
	}
	
	public void acaoAdicionarItemModelo() {
		super.binder.saveAll();
		super.setarValorAListaParametros(getMembroModeloSelecionado().getListaParametros(), getModalItemModelo());
		boolean retorno = false;
		try {
			retorno = getControle().fazerAcao("validarItemModelo", (SuperCompositor)this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!getItensModelo().containsKey(gerarKeyMembroModelo(getMembroModeloSelecionado()))) {
			if (retorno && renderizarNaArvore(getMembroModeloSelecionado())) {
				getItensModelo().put(gerarKeyMembroModelo(getMembroModeloSelecionado()), getMembroModeloSelecionado());
				getModalItemModelo().setVisible(false);
				setMembroModeloSelecionado(new MembroModelo());
			} else {
				retorno = false;
			}
		} else {
			retorno = false;
			getControle().getMensagens().getListaMensagens().add("Já contém Grau e Ordem especificado na árvore, tente outro");
		}
		mostrarMensagem(retorno);
	}
	
	public void acaoRemoverItemModelo() {
		Set<TreeNode<MembroModelo>> selecionado = getModelItemModelo().getSelection();
		for (TreeNode<MembroModelo> treeNode : selecionado) {
			getModelItemModelo().remove((MembroModeloTreeNode) treeNode);
			getItensModelo().remove(gerarKeyMembroModelo(treeNode.getData()));
			if (treeNode.getChildren() != null) {
				for (TreeNode<MembroModelo> filho : treeNode.getChildren()) {
					getItensModelo().remove(gerarKeyMembroModelo(filho.getData()));
				}
			}
		}
		super.binder.loadAll();
	}
	
	private boolean renderizarNaArvore(MembroModelo itemModelo) {
		if (itemModelo.getOrdem() == 0) {
			getModelItemModelo().add(getModelItemModelo().getRaiz(), new MembroModeloTreeNode(itemModelo, new MembroModeloTreeNode[] {}));
		} else {
			int indice = getModelItemModelo().getIndexOfChild(getModelItemModelo().getRaiz(), new MembroModeloTreeNode(getItensModelo().get(itemModelo.getGrau()+ "-0")));
			if (indice > -1) {
				MembroModeloTreeNode pai = (MembroModeloTreeNode) getModelItemModelo().getChild(getModelItemModelo().getRaiz(), indice);
				getModelItemModelo().add(pai, new MembroModeloTreeNode(itemModelo));
			} else {
				getControle().getMensagens().getListaMensagens().add("Deve ser criado o pai do nó com mesmo grau e ordem \"0\"");
				return false;
			}
		}
		return true;
	}
	
	private MembroModelo criarItemModeloDoArtefato() {
		MembroModelo modelo = new MembroModelo();
		modelo.setArtefato(getArtefatoModeloSelecionado());
		modelo.setOrdemPai(0);
		return modelo;
	}
	
	public void abrirModalItemModelo() {
		getModalItemModelo().doModal();
	}
	
	public String gerarKeyMembroModelo(MembroModelo itemModelo) {
		return itemModelo.getGrau() + "-" + itemModelo.getOrdem();
	}
	
	public BindingListModelListModel<Artefato> getModelArtefatoModelo() {
		return new BindingListModelListModel<Artefato>(new SimpleListModel<Artefato>(new ArrayList(getListaArtefatos())));
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
	
	public List<Modelo> getListaModelos() {
		return getControle().getListaModelos();
	}
	
	/**
	 * Método que retorna listagem de ArtefatosModelo
	 * 
	 * @return Collection lista de ArtefatosModelo
	 */
	public Collection<Artefato> getListaArtefatos() {
		return getControle().listarArtefatosModelo();
	}
	
	public Rows getRowsItemModelo() {
		return (Rows) getModalItemModelo().getFellow("rowsItemModelo");
	}
	
	public Window getModalItemModelo() {
		return (Window) getComponent().getFellow("modalItemModelo");
	}
	
	public AnnotateDataBinder getBinderItemModelo() {
		if (this.binderModalArtefatoModelo == null) {
			this.binderModalArtefatoModelo = new AnnotateDataBinder(getModalItemModelo());
		}
		return this.binderModalArtefatoModelo;
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
