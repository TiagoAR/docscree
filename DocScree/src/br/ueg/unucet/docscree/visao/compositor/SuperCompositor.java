package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;

/**
 * Compositor superior, contém métodos comuns a todos os compositores
 * 
 * @author Diego
 * 
 * @param <E>
 *            Controle específico de cada compositor, deve herdar SuperControle
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class SuperCompositor<E extends SuperControle> extends
		GenericForwardComposer {

	/**
	 * Defaul Serial para o Composer
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Chama e seta valores para a visão.
	 */
	protected AnnotateDataBinder binder;
	/**
	 * Representa o componente, associado ao apply do componente.
	 */
	protected Component component;

	/**
	 * Controlador específico
	 */
	public E gControle;

	/**
	 * Janela de exibição de mensagens de sucesso
	 */
	protected Window modalSucesso = null;
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zkoss.zk.ui.util.GenericForwardComposer#doAfterCompose(org.zkoss.
	 * zk.ui.Component)
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		processRecursive(comp, this);
		comp.setAttribute("controller", this, true);

		this.component = comp;

		this.binder = new AnnotateDataBinder(component);
		this.binder.setLoadOnSave(false);
		this.binder.loadAll();
	}
	
	//verificar se pode tirar
	/**
	 * Processa recursivamente os componentes filhos disparando e asscoiando listenner
	 * e associando os compositores dentro deles.
	 * 
	 * @param comp
	 * @param composer
	 */
	protected void processRecursive(Component comp, Object composer) {
		Selectors.wireComponents(comp, composer, false);
		Selectors.wireEventListeners(comp, composer);

		List<Component> compList = comp.getChildren();
		for (Component vComp : compList) {
			if (vComp instanceof Window) {
				processRecursive(vComp, composer);
			}
		}
	}

	/**
	 * Método que retorna o Controle específico da entidade.
	 * 
	 * @return SuperControle controle
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	protected E getControle() {
		if (this.gControle == null) {
			ParameterizedTypeImpl classeRfe = (ParameterizedTypeImpl) this
					.getClass().getGenericSuperclass();
			Class controle = (Class) classeRfe.getActualTypeArguments()[0];
			try {
				this.gControle = (E) controle.newInstance();
			} catch (Exception e) {
				Messagebox.show("Erro ao criar controlador\nContate o Administrador do sistema", "Erro Grave", Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
			} 
		}
		return this.gControle;
	}
	
	/**
	 * Método que gera a mensagem de Erro, varre a lista de mensagens e a joga na visão.
	 * 
	 */
	protected void gerarMensagemErro() {
		String mensagem = "";
		List<String> listaMensagens = getControle().getMensagens()
				.getListaMensagens();
		for (int i = 0; i < listaMensagens.size() - 1; i++) {
			mensagem += listaMensagens.get(i) + "\n";
		}
		mensagem += listaMensagens.get(listaMensagens.size() - 1);
		String tipoMessagebox = Messagebox.INFORMATION;
		if (getControle().getMensagens().getTipoMensagem() == TipoMensagem.ERRO) {
			tipoMessagebox = Messagebox.ERROR;
		}
		Messagebox.show(mensagem, getControle().getMensagens()
				.getTipoMensagem().getDescricao(), Messagebox.OK,
				tipoMessagebox);
	}
	
	/**
	 * Método que instancia janela de mensagens de sucesso e a mostra.
	 * 
	 */
	protected void gerarMensagemSucesso() {
		this.component.getChildren();
		for (Component window : this.component.getParent().getChildren()) {
			if (window.getId().equalsIgnoreCase("teste")) {
				this.modalSucesso = (Window) window;
				break;
			}
		}
		Label mensagemSucesso = new Label("Deu certo e vai fica certo!!!!");
		mensagemSucesso.setId("labelTeste");
	/*	ThreadVisibilidade teste = new ThreadVisibilidade();
		teste.run();*/
//		this.modalSucesso = new Window();
		this.modalSucesso.removeChild(mensagemSucesso);
		this.modalSucesso.appendChild(mensagemSucesso);
		this.modalSucesso.setVisible(true);
	//	this.component.getParent().
//		this.component.getParent().appendChild(this.modalSucesso);
//		this.modalSucesso = new Window();
//		this.modalSucesso.setBorder(true);
//		this.modalSucesso.setZIndex(99);
//		this.modalSucesso.setStyle("float: right; text-align: right;");
//		this.modalSucesso.setWidth("300px");
////		this.modalSucesso.doModal();
//		this.modalSucesso.setVisible(true);
//		this.component.appendChild(modalSucesso);
		System.out.println("Deu Certo!");
	}
	
	/**
	 * Método mostrar mensagem na tela, ou de sucesso ou de erro.
	 * 
	 * @param pResultado o resultado da ação
	 */
	protected void mostrarMensagem(boolean pResultado) {
		if (pResultado) {
			gerarMensagemSucesso();
		} else {
			gerarMensagemErro();
		}
	}

	/**
	 * Seta o contolador
	 * 
	 * @param pControle
	 */
	protected void setControle(E pControle) {
		this.gControle = pControle;
	}

}
