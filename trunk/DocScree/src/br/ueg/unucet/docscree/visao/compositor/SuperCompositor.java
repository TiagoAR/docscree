package br.ueg.unucet.docscree.visao.compositor;

import java.lang.reflect.Type;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

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
	private Component component;

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
		comp.setAttribute("gerenciador", this, true);

		this.component = comp;

		this.binder = new AnnotateDataBinder(component);
		this.binder.setLoadOnSave(false);
		this.binder.loadAll();
	}
	
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
			Type genericSuperclass = this.getClass().getGenericSuperclass();
			Class<?> classe = this.getClass();
			while (!(genericSuperclass instanceof ParameterizedTypeImpl)) {
				classe = classe.getSuperclass();
				genericSuperclass = classe.getGenericSuperclass();
			}
			ParameterizedTypeImpl classeRfe = (ParameterizedTypeImpl) genericSuperclass;
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
	 * Método que retorna o usuário salvo na sessão
	 * 
	 * @return Object usuário
	 */
	public Object getUsuarioSessao() {
		return Executions.getCurrent().getSession().getAttribute("usuario");
	}
	
	/**
	 * Método que retorna o projeto salvo na sessão
	 * 
	 * @return Object projeto
	 */
	public Object getProjetoSessao() {
		return Executions.getCurrent().getSession().getAttribute("projeto");
	}
	
	/**
	 * Método que gera a mensagem de Erro, varre a lista de mensagens e a joga na visão.
	 * 
	 */
	protected void gerarMensagemErro(Window mensagens) {
		String caminhoImagem = "/imagens/ico_warning_48.png";
		if (getControle().getMensagens().getTipoMensagem() == TipoMensagem.ERRO) {
			caminhoImagem = "/imagens/ico_block_48.png";
		}
		Image image = new Image(caminhoImagem);
		image.setParent(mensagens);
		image.setStyle("float: left;");
		mensagens.appendChild(image);
		List<String> listaMensagens = getControle().getMensagens()
				.getListaMensagens();
		for (int i = 0; i < listaMensagens.size(); i++) {
			Label label = new Label(listaMensagens.get(i).trim());
			label.setStyle("display: block;");
			label.setParent(mensagens);
			mensagens.appendChild(label);
		}
	}
	
	/**
	 * Método que instancia janela de mensagens de sucesso e a mostra.
	 * 
	 */
	protected void gerarMensagemSucesso(Window mensagens) {
		Image image = new Image("/imagens/ico_success_48.png");
		image.setParent(mensagens);
		image.setStyle("float: left;");
		Label label = new Label("Ação Executada!");
		label.setParent(mensagens);
		mensagens.appendChild(image);
		mensagens.appendChild(label);
	}
	
	/**
	 * Método mostrar mensagem na tela, ou de sucesso ou de erro.
	 * 
	 * @param pResultado o resultado da ação
	 */
	protected void mostrarMensagem(boolean pResultado) {
		Window mensagens = this.gerarWindowMensagem();
		if (pResultado) {
			mensagens.doOverlapped();
			mensagens.setPosition("top, right");
			gerarMensagemSucesso(mensagens);
		} else {
			mensagens.doModal();
			mensagens.setPosition("center");
			gerarMensagemErro(mensagens);
			adicionarBotaoFechar(mensagens);
		}
	}
	
	/**
	 * Método que gerar a Window de mensagens, verificando se já existe um e a fechando e
	 * criando uma nova.
	 * 
	 * @return Window de mensagens
	 */
	protected Window gerarWindowMensagem() {
		Window mensagens;
		Component componentePai = this.getComponent();
		if (this.getComponent().hasFellow("windowAvisos")) {
			componentePai.getParent();
			mensagens = (Window) this.getComponent().getFellow("windowAvisos");
			mensagens.detach();
		}
		mensagens = new Window();
		
		this.aplicarTimerFechamento(mensagens);
		
		mensagens.setId("windowAvisos");		

		mensagens.setParent(componentePai);
		mensagens.setVisible(true);
		return mensagens;
	}
	
	private void adicionarBotaoFechar(Window mensagens) {
		Button button = new Button();
		button.setLabel("Ok");
		button.setParent(mensagens);
		button.addEventListener("onClick", new SerializableEventListener() {
			private static final long serialVersionUID = -4218067107434932638L;

			@Override
			public void onEvent(Event event) throws Exception {
            	event.getTarget().getParent().setVisible(false); 
            	event.getTarget().getParent().detach();
			}
		});
	}
	
	/**
	 * Método que aplica Timer para fechar a janela em 4,5 segundos.
	 * 
	 * @param mensagens
	 */
	private void aplicarTimerFechamento(Window mensagens) {
		Timer t = new Timer();
		t.setDelay(10000);
		t.setParent(mensagens);
		t.addEventListener("onTimer",new SerializableEventListener() {
			private static final long serialVersionUID = 7868086951980855931L;
			public void onEvent(Event event) throws Exception {
            	event.getTarget().getParent().setVisible(false); 
            	event.getTarget().getParent().detach();
            }
		});
	}
	
	protected Row gerarRow(org.zkoss.zk.ui.Component[] componente) {
		Row row = new Row();
		for (Component component : componente) {
			row.appendChild(component);
		}
		return row;
	}
	
	protected Label gerarLabel(String valor) {
		Label label = new Label();
		label.setValue(valor+":");
		return label;
	}
	
	public IComponenteDominio getInstanciaComponente(IParametro<?> parametro) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String nomeClass = "";
		if (parametro.getDominioEntrada() == null) {
			nomeClass = "br.ueg.unucet.docscree.componentes.ListaDominioComponente";
		} else {
			String valorDominio = parametro.getDominioEntrada().toString();
			nomeClass = "br.ueg.unucet.docscree.componentes." + valorDominio.substring(0, 1).toUpperCase() + valorDominio.substring(1).toLowerCase() + "Componente";
		}
		return (IComponenteDominio) Class.forName(nomeClass).newInstance();
	}
	
	/**
	 * Método que força a atualização de algum campo/label ou componente qualquer
	 * que não é atualizado usando o loadAll do bind
	 */
	protected void forcarAtualizacaoCampos() {
		
	}

	/**
	 * Seta o contolador
	 * 
	 * @param pControle
	 */
	protected void setControle(E pControle) {
		this.gControle = pControle;
	}

	/**
	 * @return o(a) component
	 */
	protected Component getComponent() {
		return component;
	}

	/**
	 * @param component o(a) component a ser setado(a)
	 */
	protected void setComponent(Component component) {
		this.component = component;
	}

}
