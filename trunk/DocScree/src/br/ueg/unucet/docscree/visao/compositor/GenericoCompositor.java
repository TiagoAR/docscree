package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleListModel;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.GenericoControle;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.interfaces.IPersistivel;

/**
 * Compositor Genérico, contém métodos comuns a todos compositores que são caso de uso tipo CRUD.
 * 
 * @author Diego
 *
 * @param <E>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class GenericoCompositor<E extends GenericoControle> extends SuperCompositor<E> {

	/**
	 * Default serial do compositor
	 */
	private static final long serialVersionUID = 1L;
	
	@AtributoVisao(isCampoEntidade = true, nome= "codigo", nomeCampoBundle="codigo")
	protected Long codigo = null;
	
	protected List<?> listaEntidade;
	
	protected boolean listando = false;
	
	protected Persistivel entidade;

	/**
	 * Retorna a classe que representa a entidade.
	 * 
	 * @return Class classe da entidade
	 */
	public abstract Class getTipoEntidade();
	
	/**
	 * Método que limpa os campos da tela.
	 * 
	 */
	protected abstract void limparCampos();
	
	protected abstract void limparFiltros();

	/**
	 * Método que cria nova instancia da entidade.
	 * 
	 * @return IPersistivel entidade
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public IPersistivel<?> novaEntidade() throws InstantiationException,
			IllegalAccessException {
		return (IPersistivel<?>) getTipoEntidade().newInstance();
	}

	/**
	 * Método que executa a ação de Salvar Usuário e mostra mensagem de sucesso ou erro após ação
	 */
	public void acaoSalvar() {
		try {
			boolean resultado = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			if (resultado) {
				limparCampos();
			}
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que executa a ação de Listar Usuário e mostra mensagem de sucesso ou erro após ação.
	 */
	public void acaoListar() {
		try {
			limparFiltros();
			boolean resultado = super.getControle().fazerAcao("listar", (SuperCompositor) this);
			this.setListaEntidade(super.getControle().getLista());
			List<Component> listaChildren = super.component.getChildren();
			//super.component.getFellow(id)
			//getFellow
			for (Component componente : listaChildren) {
				if (componente.getId().equalsIgnoreCase("windowMensagens")) {
					componente.setVisible(true);
					Listbox listbox = (Listbox) componente.getFirstChild();
					ListModel listModel = new BindingListModelListModel(new SimpleListModel(super.getControle().getLista()));
					listbox.setModel(listModel);
					break;
				}
			}
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fecharModalLista() {
		List<Component> listaChildren = super.component.getChildren();
		for (Component componente : listaChildren) {
			if (componente.getId().equalsIgnoreCase("windowMensagens")) {
				componente.setVisible(false);
				break;
			}
		}
		limparFiltros();
	}

	/**
	 * @return o(a) listaEntidade
	 */
	public List<?> getListaEntidade() {
		return listaEntidade;
	}

	/**
	 * @param listaEntidade o(a) listaEntidade a ser setado(a)
	 */
	public void setListaEntidade(List<?> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}

	/**
	 * @return o(a) listando
	 */
	public boolean isListando() {
		return listando;
	}

	/**
	 * @param listando o(a) listando a ser setado(a)
	 */
	public void setListando(boolean listando) {
		this.listando = listando;
	}

	/**
	 * @return o(a) entidade
	 */
	public Persistivel getEntidade() {
		return entidade;
	}

	/**
	 * @param entidade o(a) entidade a ser setado(a)
	 */
	public void setEntidade(Persistivel entidade) {
		this.entidade = entidade;
	}

	/**
	 * @return o(a) codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo o(a) codigo a ser setado(a)
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Método para criar janela modal de lista
	 * 
	 * @param titulo Título da janela
	 * @param botoes Contém label dos botões
	 * @param colunas Contém label das colunas
	 * @param checkBoxAtivo se deve aparecer checkBox de exibição de entidades ativos.
	 *//*
	protected void criarModalLista(String titulo, String[] botoes, String[] colunas, boolean checkBoxAtivo) {
		this.modalLista.setId("modalList");
		this.modalLista.setTitle(titulo);
		this.modalLista.setClosable(true);
		this.modalLista.setMode(Window.MODAL);
		this.modalLista.setWidth("750px");
		this.component.getParent().appendChild(this.modalLista);
		this.modalLista.setParent(this.component.getParent());
		this.modalLista.appendChild(criarDiv(this.modalLista, botoes, colunas));
		this.modalLista.doModal();
	}
	
	protected Div criarDiv(Component pai, String[] botoes, String[] colunas) {
		Div div = new Div();
		div.setId("divLista");
		div.setParent(pai);
		div.appendChild(criarListBox(div, botoes, colunas));
		return div;
	}
	
	protected Listbox criarListBox(Component pai, String[] botoes, String[] colunas) {
		Listbox listbox = new Listbox();
		listbox.setId("listBoxList");
		listbox.setParent(pai);
		//listbox.appendChild(criarAuxHead());
		listbox.appendChild(criarListHead(listbox, botoes, colunas));
		listbox.appendChild(criarListItem(listbox, colunas));
		return listbox;
	}
	
	protected Listhead criarListHead(Component pai, String[] botoes, String[] colunas) {
		Listhead listhead = new Listhead();
		listhead.setId("listHeadList");
		listhead.setParent(pai);
		for (String coluna : colunas) {
			listhead.appendChild(criarListHeader(pai, coluna));
		}
		for (String botao : botoes) {
			listhead.appendChild(criarListHeader(pai, botao));
		}
		return listhead;
	}
	
	protected Listheader criarListHeader(Component pai, String coluna) {
		Listheader listheader = new Listheader();
		listheader.setParent(pai);
		listheader.setLabel(coluna);
		return listheader;
	}
	
	protected Listitem criarListItem(Component pai, String[] colunas) {
		Listitem listitem = new Listitem();
		listitem.setValue("@{each}");
		
		return listitem;
	}*/
	
	
	
	

}
