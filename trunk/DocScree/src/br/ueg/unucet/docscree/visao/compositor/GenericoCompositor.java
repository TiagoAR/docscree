package br.ueg.unucet.docscree.visao.compositor;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.controladores.GenericoControle;

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
	
	protected Window modalLista = null;

	/**
	 * Método que retorna chave primária
	 * 
	 * @return Object PK
	 */
	public abstract Object getPrimaryKey();

	/**
	 * Método que seta a chave primária
	 * 
	 * @param primaryKey
	 */
	public abstract void setPrimaryKey(Object primaryKey);

	/**
	 * Método que executa a ação de Salvar Usuário e mostra mensagem de sucesso ou erro após ação
	 */
	public void acaoSalvar() {
		try {
			boolean resultado = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
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
			boolean resultado = super.getControle().fazerAcao("listar", (SuperCompositor) this);
			this.setListaEntidade(super.getControle().getLista());
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para criar janela modal de lista
	 * 
	 * @param titulo Título da janela
	 * @param botoes Contém label dos botões
	 * @param colunas Contém label das colunas
	 * @param checkBoxAtivo se deve aparecer checkBox de exibição de entidades ativos.
	 */
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

	/**
	 * @return o(a) modalLista
	 */
	public Window getModalLista() {
		return modalLista;
	}

	/**
	 * @param modalLista o(a) modalLista a ser setado(a)
	 */
	public void setModalLista(Window modalLista) {
		this.modalLista = modalLista;
	}
	
	

}
