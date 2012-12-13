package br.ueg.unucet.docscree.visao.compositor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.MembroDocScree;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;

/**
 * Compositor que representa a página de montar Artefato Modelo
 * 
 * @author Diego
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
@Scope("session")
public class ArtefatoCompositor extends SuperArtefatoCompositor<ArtefatoControle> {

	/**
	 * DEFAULT SERIAL ID
	 */
	private static final long serialVersionUID = 3331698085154478299L;

	/**
	 * Binder da Paleta
	 */
	protected AnnotateDataBinder binderPaleta;
	/**
	 * Binder da área de Montagem de Preenchimento
	 */
	protected AnnotateDataBinder binderArtefato;
	/**
	 * Binde da área de Montagem de Visualização
	 */
	protected AnnotateDataBinder binderVisualizao;
	/**
	 * Binder do Paleta dos Membros
	 */
	protected AnnotateDataBinder binderMembro;
	/**
	 * Atributo que representa a largura máxima permitida para adicionar um Membro
	 */
	private Integer larguraTotalMembro;
	
	/**
	 * Artefato que deve ser aberto ao carregar a página (carregar ArtefatoModelo)
	 */
	private Artefato artefatoAAbrir;
	/**
	 * Código do Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private Long codigoAntigo;
	/**
	 * Nome do Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private String nomeAntigo;
	/**
	 * Descrição do Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private String descricaoAntiga;
	/**
	 * Altura do Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private int alturaAntiga;
	/**
	 * Largura do Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private int larguraAntiga;
	
	/**
	 * Representa o Execution Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private Execution executionAntigo;
	/**
	 * Representa o Componente contendo todas os Membros associado ao Artefato Antigo caso ocorra o cancelamento de alguma ação do Menu
	 */
	private org.zkoss.zk.ui.Component componenteAntigo;
	/**
	 * Representa o antigo Binder da tela
	 */
	private AnnotateDataBinder binderAntigo;

	/**
	 * Não tem campos para serem limpos nesse caso de uso. Chama método responsável para
	 * redirecionar para página de montar-artefato.zul
	 * 
	 * @see GenericoCompositor#limparCampos()
	 */
	@Override
	protected void limparCampos() {
		getComponent().getChildren().clear();
		abrirTelaMontarArtefato();
	}

	@Override
	@Deprecated
	protected void limparFiltros() {

	}

	@Deprecated
	@Override
	public void acaoFiltrar() {

	}
	
	/**
	 * Método que redireciona para a página de montagem do Artefato
	 */
	private void abrirTelaMontarArtefato() {
		//getComponent().detach();
		Executions.sendRedirect("/pages/montar-artefato.zul");
	}
	
	/**
	 * 
	 * 
	 * @return lista de ArtefatosModelo salvo no framework
	 */
	public List<Artefato> getListaArtefatosModelo() {
		return getControle().listarArtefatosModelo();
	}
	
	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperCompositor#gerarWindowMensagem()
	 */
	@Override
	protected Window gerarWindowMensagem() {
		org.zkoss.zk.ui.Component componenteInicial = super.getComponent();
		while (super.getComponent() != null && !(super.getComponent() instanceof Div)) {
			super.setComponent(super.getComponent().getParent());
		}
		if (super.getComponent() == null) {
			super.setComponent(componenteInicial);
		}
		return super.gerarWindowMensagem();
	}
	
	/**
	 * Método que cria Modal de uma janela e a joga sobre o componente
	 * 
	 * @param zulModal
	 * @return retorno se ação ocorreu com sucesso
	 */
	private boolean exibirModalArtefatoModelo(String zulModal) {
		boolean retorno = false;
		try {
			retorno = super.getControle().fazerAcao("montarArtefato", (SuperCompositor) this);
			if (retorno) {
				org.zkoss.zk.ui.Component comp = Executions.createComponents(
						zulModal, null, null);

				if (comp instanceof Window) {
					((Window) comp).doModal();
				}
			} else {
				super.mostrarMensagem(retorno);
			}
		} catch (Exception e) {
			super.getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			super.getControle().getMensagens().getListaMensagens().add("Não foi possível executar a ação!");
			super.mostrarMensagem(retorno);
		}
		return retorno;
		
	}

	/**
	 * Método que exibe Modal para Criar um novo Artefato Modelo
	 */
	public void acaoNovoArtefatoModelo() {
		salvarTelaArtefato();
		inicializarVariaveisNovoArtefato();
		this.exibirModalArtefatoModelo("/componentes/modalNovoArtefato.zul");
	}
	
	/**
	 * Exibe Modal para Abrir um ArtefatoModelo já criado
	 */
	public void acaoSelecionarArtefatoModelo() {
		salvarTelaArtefato();
		setArtefatoAAbrir(null);
		this.exibirModalArtefatoModelo("/componentes/modalAbrirArtefato.zul");
	}
	
	/**
	 * Método que guarda a Tela em execução em memória para restaurá-la caso a ação solicitada seja cancelada
	 */
	private void salvarTelaArtefato() {
		binderArtefato = null;
		binderMembro = null;
		binderPaleta = null;
		binderVisualizao = null;
		setBinderAntigo(binder);
		setComponenteAntigo(getComponent());
		setExecutionAntigo(execution);
		
		setCodigoAntigo(getCodigo());
		setAlturaAntiga(getAltura());
		setLarguraAntiga(getLargura());
		setNomeAntigo(getNome());
		setDescricaoAntiga(getDescricao());
	}
	
	/**
	 * Método que fecha o Modal em exibição e restaura componentes da Tela anterior]
	 */
	public void acaoFecharModal() {
		String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
		getComponent().detach();
		if (requestPath.contains("montar-artefato")) {
			execution = getExecutionAntigo();
			super.setComponent(getComponenteAntigo());
			binder = getBinderAntigo();
			
			this.setCodigo(getCodigoAntigo());
			this.setLargura(getLarguraAntiga());
			this.setAltura(getAlturaAntiga());
			this.setNome(getNomeAntigo());
			this.setDescricao(getDescricaoAntiga());
		}
	}
	
	/**
	 * Método responsável por inicializar todas as variáveis necessárias para criar um novo ArtefatoModelo
	 */
	private void inicializarVariaveisNovoArtefato() {
		this.setCodigo(null);
		this.setLargura(0);
		this.setAltura(0);
		this.setNome("");
		this.setDescricao("");
	}
	
	/**
	 * Método responsável por inicializar as variáveis para abrir um ArtefatoModelo
	 */
	private void inicializarVariaveisCriarArtefato() {
		this.inicializarTelasMapeadores();
		setMembros(new ArrayList<Membro>());
		setServicos(new ArrayList<IServico>());
	}
	
	/**
	 * Responsável por exibir o ArtefatoModelo criado no Modal
	 */
	public void exibirNovoArtefato() {
		super.binder.saveAll();
		inicializarVariaveisCriarArtefato();
		if (getAltura() > 0 
				&& getLargura() > 0 ) {
			
			if (getNome() != null && !getNome().isEmpty()
					&& getDescricao() != null && !getDescricao().isEmpty()) {
				if (Integer.valueOf(getLargura()).compareTo(LARGURA_MAXIMA) < 1 ) {
					new Runnable() {
						
						@Override
						public void run() {
							acaoSalvar();
						}
					}.run();
				} else {
					this.exibirMensagemErro("Largura máxima do Artefato foi ultrapassada, o valor deve ser menor ou igual a " + LARGURA_MAXIMA.toString());
				}
			} else {
				this.exibirMensagemErro("É obrigatório o preenchimento do Nome e Descrição do Artefato");
			}
		} else {
			this.exibirMensagemErro("É obrigatório o preenchimento da Largura e Altura do Artefato");
		}
	}
	
	/**
	 * Responsável por Carregar e Exibir o ArtefatoModelo selecionado a ser aberto
	 */
	public void abrirArtefatoModelo() {
		try {
			Persistivel antigoArtefato = getEntidade();
			setEntidade(getArtefatoAAbrir());
			boolean retorno = getControle().fazerAcao("renovarBloqueio", (SuperCompositor) this);
			if (retorno) {
				abrirTelaMontarArtefato();
				Executions.getCurrent().getSession().setAttribute("ArtefatoAbrir", getEntidade());
				retorno = true;
			} else {
				setEntidade(antigoArtefato);
			}
			mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.binder.loadAll();
	}
	
	/**
	 * Responsável por Mapear ArtefatoModelo, criando um novo para ser exibido ou o alterando para adicionar lista de Membros
	 */
	public void acaoMapearArtefato() {
		try {
			boolean retorno = getControle().fazerAcao("mapearArtefato", (SuperCompositor) this);
			if (retorno) {
				limparTodaTela();
				super.binder.loadAll();
			}
			this.mostrarMensagem(retorno);
		} catch (Exception e) {
		}
	}
	
	/**
	 * @see SuperArtefatoCompositor#inicializarTelasMapeadores()
	 */
	protected void inicializarTelasMapeadores() {
		this.areaWindowArtefato = null;
		this.areaVisualizacaoWindow = null;
		this.binderArtefato = null;
		this.binderVisualizao = null;
		setMapaMembrosAdicionados(new HashMap<String, MembroDocScree>());
	}
	
	/**
	 * Método que limpa toda a tela de Montar Artefato, retirando todos componentes associados
	 */
	private void limparTodaTela() {
		List<org.zkoss.zk.ui.Component> children = getComponent().getChildren();
		for (org.zkoss.zk.ui.Component component : children) {
			if (component.getId() == null || !component.getId().equals("windowAvisos")) {
				component.setVisible(false);
			}
		}
	}
	
	/**
	 * Responsável por criar Modal de erro para o usuário
	 * 
	 * @param mensagem
	 */
	private void exibirMensagemErro(String mensagem) {
		Mensagens mensagens = new Mensagens();
		mensagens.setTipoMensagem(TipoMensagem.ERRO);
		mensagens.getListaMensagens().add(mensagem);
		getControle().setMensagens(mensagens);
		super.mostrarMensagem(false);
	}

	/**
	 * Retorna lista de TipoMembro-Visão cadastrados no Framework para o DocScree
	 * 
	 * @return lista dos TipoMembro visão cadastrados
	 */
	public List<SuperTipoMembroVisaoZK> getListaTipoMembrosVisao() {
		return super.getControle().listarTipoMembrosVisao();
	}

	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperArtefatoCompositor#gerarNovaInstancia(java.lang.String, br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	@Override
	protected HtmlBasedComponent gerarNovaInstancia(String idComponente,
			Membro membro) {
		HtmlBasedComponent novaInstancia = super.gerarNovaInstancia(idComponente, membro);
		novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + ESTILODIV + "cursor: pointer;");
		setarEventDiv((Div) novaInstancia);
		return novaInstancia;
	}

	/**
	 * @see SuperArtefatoCompositor#gerarNovaInstanciaVisualizacao(String, Membro)
	 */
	@Override
	protected HtmlBasedComponent gerarNovaInstanciaVisualizacao(String idComponente, Membro membro) {
		HtmlBasedComponent novaInstancia = super.gerarNovaInstanciaVisualizacao(idComponente, membro);
		setarEventDiv((Div) novaInstancia);
		getTipoMembroVisaoSelecionado().getVisualizacaoExemplo(novaInstancia.getFirstChild(), "Exemplo");
		return novaInstancia;
	}
	
	/**
	 * Método responsável por setar evento onClick na Div
	 * 
	 * @param div
	 */
	private void setarEventDiv(Div div) {
		div.addEventListener("onClick", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				String idComponente = event.getTarget().getId();
				idComponente = idComponente.replace("Grid", "Componente");
				MembroDocScree membroDocScree = getMapaMembrosAdicionados().get(idComponente);
				setTipoMembroVisaoSelecionado(membroDocScree.getTipoMembroVisao());
				gerarPaletaParametros(false);
			}
			
		});
	}
	
	/**
	 * 
	 * @return binderArtefato
	 */
	protected AnnotateDataBinder getBinderArtefato() {
		if (this.binderArtefato == null) {
			this.binderArtefato = new AnnotateDataBinder(getWindowArtefato());
		}
		return this.binderArtefato;
	}
	
	/**
	 * 
	 * @return binderVisualizao
	 */
	protected AnnotateDataBinder getBinderVisualizacaoArtefato() {
		if (this.binderVisualizao == null) {
			this.binderVisualizao = new AnnotateDataBinder(getWindowVisualizacaoArtefato());
		}
		return this.binderVisualizao;
	}
	
	/**
	 * 
	 * @return binderPaleta
	 */
	protected AnnotateDataBinder getBinderPaleta() {
		if (this.binderPaleta == null) {
			this.binderPaleta = new AnnotateDataBinder(getWindowPaleta());
		}
		return this.binderPaleta;
	}
	
	/**
	 * 
	 * @return window da Paleta
	 */
	protected Window getWindowPaleta() {
		return (Window) getComponent().getFellow("windowPaleta");
	}
	
	/**
	 * 
	 * @return window modal de Abrir ArtefatoModelo
	 */
	protected Window getWindowAbrirArtefatoModelo() {
		return (Window) getComponent().getFellow("modalAbrirArtefato");
	}

	/**
	 * Método que gerar a Paleta de Parâmetros de acordo com o TipoMembro-Visão selecionado
	 * 
	 * @param isNovo se é para um Membro novo ou não
	 */
	public void gerarPaletaParametros(boolean isNovo) {
		if (isNovo) {
			super.binder.saveAll();
		}
		if (this.getTipoMembroVisaoSelecionado() != null) {
			try {
				Membro membro = getTipoMembroVisaoSelecionado().getMembro();
				this.setTipoMembroVisaoSelecionado(getTipoMembroVisaoSelecionado().getClass().newInstance());
				getTipoMembroVisaoSelecionado().setMembro(membro);
			} catch (Exception e) {
				
			}
			Window windowPaleta = getWindowPaleta();
			windowPaleta.getChildren().clear();
			
			if (isNovo) {
				SuperTipoMembroVisaoZK<?> novaInstancia = null;
				try {
					novaInstancia = getTipoMembroVisaoSelecionado().getClass().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				setTipoMembroVisaoSelecionado(novaInstancia);
				
				getTipoMembroVisaoSelecionado().setMembro(getControle().getMembroDoTipoMembro(getTipoMembroVisaoSelecionado()));
				windowPaleta.appendChild(gerarGridOpcao());
				windowPaleta.appendChild(gerarGridMembros());
			}
			windowPaleta.appendChild(gerarGridPropriedades());
			if (!isNovo) {
				setarValoresParametrosMembro();
				setarValoresParametrosTipoMembro();
			}
			windowPaleta.appendChild(gerarButtonPropriedades(isNovo));
			if (!isNovo) {
				windowPaleta.appendChild(new Separator());
				windowPaleta.appendChild(gerarButtonRemover());
			}
			getBinderPaleta().loadAll();
		}
	}
	
	/**
	 * Responsável por gerar a Grid de opções de reaproveitamento de Membros
	 * 
	 * @return grid
	 */
	private Grid gerarGridOpcao() {
		Grid grid = new Grid();
		Rows rows = new Rows();
		Checkbox checkbox = new Checkbox("Reaproveitar parâmetros de um Membro");
		checkbox.addEventListener("onCheck", new EventListener<CheckEvent>() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(CheckEvent event) throws Exception {
				if (event.isChecked()) {
					getComponentePorId("gridListaMembros", getWindowPaleta()).setVisible(true);
					getBinderPaleta().loadAll();
				} else {
					getComponentePorId("gridListaMembros", getWindowPaleta()).setVisible(false);
					getBinderPaleta().loadAll();
				}
			}
			
		});
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {checkbox}));
		grid.appendChild(rows);
		return grid;
	}
	
	/**
	 * Respónsavel por gerar a Grid com os parâmetros a serem preenchidos do MEMBRO
	 * 
	 * @return grid dos membros
	 */
	private Grid gerarGridMembros() {
		Grid grid = new Grid();
		grid.setVisible(false);
		grid.setId("gridListaMembros");
		Rows rows = new Rows();
		grid.appendChild(rows);
		Combobox combobox = new Combobox();
		combobox.setWidth(WIDTH);
		combobox.setModel(new BindingListModelListModel<>(new ListModelArray<>(getControle().listarMembrosPorTipoMembro(getTipoMembroVisaoSelecionado().getMembro().getTipoMembroModelo()).toArray())));
		combobox.addEventListener("onSelect", new EventListener<SelectEvent<Combobox, Membro>>() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(SelectEvent<Combobox, Membro> event)
					throws Exception {
				Membro membro = event.getSelectedObjects().iterator().next();
				Collection<IParametro<?>> listaParametros = membro.getTipoMembroModelo().getListaParametros();
				for (IParametro<?> parametro : listaParametros) {
					getInstanciaComponente(parametro).setValor(getComponentePorId("PARAMETRO"+parametro.getNome(), getWindowPaleta()), parametro.getValor());
				}
				getBinderPaleta().loadAll();
			}
			
		});
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {combobox}));
		return grid;
	}
	
	/**
	 * Gerar o botão para Remover o Membro selecionado
	 * 
	 * @return button de remoção
	 */
	private Button gerarButtonRemover() {
		Button button = new Button();
		button.setLabel("Remover Membro");
		button.addEventListener("onClick", new EventListener<Event>() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(final Event event) throws Exception {
				new Runnable() {
					
					@Override
					public void run() {
						removerMembro();
					}
				}.run();
			}
			
		});
		return button;
	}
	
	/**
	 * Responsável por gerar o botão para adicionar/alterar o Membro
	 * 
	 * @param isNovo se o botão é para um Membro novo ou não
	 * @return button gerado
	 */
	private Button gerarButtonPropriedades(boolean isNovo) {
		Button button = new Button();
		String labelButton;
		if (isNovo) {
			labelButton = "Inserir";
		} else {
			labelButton = "Alterar";
		}
		button.setLabel(labelButton + " Membro");
		button.addEventListener("onClick", new EventListener<Event>() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(final Event event) throws Exception {
				new Runnable() {
					
					@Override
					public void run() {
						gerarMembro(((Button) event.getTarget()).getLabel().contains("Inserir"));
					}
				}.run();
			}
			
		});
		return button;
	}
	
	/**
	 * Método responsável por gerar/alterar um Membro e o lançar a tela de Preenchimento e Visualização
	 * 
	 * @param isNovo
	 */
	public void gerarMembro(boolean isNovo) {
		getControle().setMensagens(new Mensagens());
		if (puxarValorParametrosMembro() &&  puxarValorParametrosModelo()) {
			if (this.larguraTotalMembro.compareTo(getLargura()) < 0) {
				boolean retorno;
				try {
					if (isNovo) {
						retorno = this.getControle().fazerAcao("mapearMembro", (SuperCompositor) this);
					} else {
						retorno = this.getControle().fazerAcao("alterarMembro", (SuperCompositor) this);
						removerMembroAlterados(getIdMembro());
					}
					if (retorno) {
						lancarMembroAVisualizacao(isNovo, false);
					}
					mostrarMensagem(retorno);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				exibirMensagemErro("Somatória da largura do membro ("+ this.larguraTotalMembro +") deve ser menor que: " + String.valueOf(getLargura()));
			}
		} else {
			chamarMensagemErro();
		}
	}
	
	/**
	 * @see SuperArtefatoCompositor#lancarMembroAVisualizacao(boolean, boolean)
	 */
	public void lancarMembroAVisualizacao(boolean novo, boolean abrindoArtefato) {
		lancarMembroAoArtefato(getIdMembro(), novo, abrindoArtefato);
		lancarMembroAVisualizacao(getIdMembro());
		setTipoMembroVisaoSelecionado(null);
		getWindowPaleta().getChildren().clear();
		getBinderPaleta().loadAll();
		
	}
	
	/**
	 * Método responsável por remover o Membro do framework
	 */
	public void removerMembro() {
		try {
			boolean retorno = this.getControle().fazerAcao("removerMembro", (SuperCompositor) this);
			if (retorno) {
				removerMembroAlterados(getIdMembro());
				getMapaMembrosAdicionados().remove("Componente" + getIdMembro());
				getMembros().remove(getTipoMembroVisaoSelecionado().getMembro());
				setTipoMembroVisaoSelecionado(null);
				getWindowPaleta().getChildren().clear();
				getBinderPaleta().loadAll();
			} 
			super.mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método responsável por pedir para o componente exibir a mensagem de erro.
	 */
	private void chamarMensagemErro() {
		getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
		super.mostrarMensagem(false);
		super.binder.loadAll();
	}
	
	/**
	 * Método que traz o valor inteiro de um componente 
	 * 
	 * @param parametroID
	 * @param nomeParametro
	 * @param camposInformados
	 * @param validarMaior0
	 * @return objeto valor do parametro
	 */
	private Integer puxarValorInteiro(String parametroID, String nomeParametro, Boolean camposInformados, boolean validarMaior0) {
		Object objeto = getParametroMembroPorId(parametroID);
		if (objeto != null && !objeto.toString().isEmpty()) {
			Integer retorno = Integer.valueOf(String.valueOf(objeto));
			if (retorno > 0 || !validarMaior0) {
				return retorno;
			} else {
				getControle().getMensagens().getListaMensagens().add("O campo "+nomeParametro+" deve ser maior que 0");
			}
		} else {
			getControle().getMensagens().getListaMensagens().add("É necessário informar o campo " + nomeParametro);
		}
		camposInformados = Boolean.FALSE;
		return 0;
	}
	
	/**
	 * Método responsável pegar todos os valores associados aos parâmetros do Membro
	 * 
	 * @return camposInformados se todos Parâmetros obrigatórios foram informados
	 */
	private boolean puxarValorParametrosMembro() {
		Object objeto = null;
		Integer valorInteiro;
		larguraTotalMembro = 0;
		//Verifica campos obrigatórios não validados pelo QUID (altura e comprimento)
		Boolean camposInformados = Boolean.TRUE;
		if ((objeto = getParametroMembroPorId(PARAMETRONOME)) != null) {
			getTipoMembroVisaoSelecionado().getMembro().setNome(String.valueOf(objeto));
		}
		valorInteiro = puxarValorInteiro(PARAMETROALTURA, "Altura", camposInformados, true);
		if (valorInteiro > 0) {
			getTipoMembroVisaoSelecionado().getMembro().setAltura(valorInteiro);
		} 
		valorInteiro = puxarValorInteiro(PARAMETROCOMPRIMENTO, "Comprimento", camposInformados, true);
		if (valorInteiro > 0) {
			getTipoMembroVisaoSelecionado().getMembro().setComprimento(valorInteiro);
			larguraTotalMembro += valorInteiro;
		} 
		if ((objeto = getParametroMembroPorId(PARAMETRODESC)) != null ) {
			getTipoMembroVisaoSelecionado().getMembro().setDescricao(String.valueOf(objeto));
		}
		valorInteiro = puxarValorInteiro(PARAMETROPOSX, "Posição X", camposInformados, false);
		getTipoMembroVisaoSelecionado().getMembro().setX(valorInteiro);
		larguraTotalMembro += valorInteiro;
		
		getTipoMembroVisaoSelecionado().getMembro().setY(puxarValorInteiro(PARAMETROPOSY, "Posição Y", camposInformados, false));
		
		return camposInformados.booleanValue();
	}
	
	/**
	 * Método que puxa todos os valores dos parâmetros do TipoMembro
	 * 
	 * @return parametrosObrigatoriosPreenchidos se parametros obrigatórios foram preenchidos
	 */
	// TODO dar uma olhada
	private boolean puxarValorParametrosModelo() {
		super.setarValorAListaParametros(this.getTipoMembroVisaoSelecionado().getListaParametros(), getWindowPaleta());
		
		Collection<IParametro<?>> listaParametros = this.getTipoMembroVisaoSelecionado().getListaParametros();
		boolean parametrosObrigatoriosPreenchidos = true;
		for (IParametro<?> parametro : listaParametros) {
			if (parametro.isObrigatorio() && parametro.getValor() == null) {
				parametrosObrigatoriosPreenchidos = false;
				getControle().getMensagens().getListaMensagens().add("O parâmetro " + parametro.getRotulo()+ " é obrigatório!");
			}
		}
		return parametrosObrigatoriosPreenchidos;
	}
	
	/**
	 * Método responsável por lançar o Membro ao Artefato, adicionando o componente a entidade
	 * 
	 * @param nomeParcial
	 * @param isNovo
	 * @param abrindoArtefato
	 */
	private void lancarMembroAoArtefato(String nomeParcial, boolean isNovo, boolean abrindoArtefato) {
		adicionarComponenteAoArtefato(getTipoMembroVisaoSelecionado(), nomeParcial, isNovo, abrindoArtefato);
		getBinderArtefato().loadAll();
	}
	
	/**
	 * Método responsável por lançar o Membro a tela de visualização do Artefato
	 * 
	 * @param nomeParcial
	 */
	private void lancarMembroAVisualizacao(String nomeParcial) {
		adicionarMembroAVisualizacao(nomeParcial);
		getBinderVisualizacaoArtefato().loadAll();
	}
	
	/**
	 * Método responsável por remover os Membros antigos, o qual seus valores foram alterados.
	 * 
	 * @param nomeParcial
	 */
	private void removerMembroAlterados(String nomeParcial) {
		String idGrid = "Grid" + nomeParcial;
		
		getWindowArtefato().getFellow(idGrid).detach();
		getWindowVisualizacaoArtefato().getFellow(idGrid).detach();
	}
	
	/**
	 * Método que puxa o Componente do Parametro do TipoMembro através de seu ID
	 * 
	 * @param id
	 * @return componente representando o Parametro do Membro
	 */
	private Object getParametroMembroPorId(String id) {
		HtmlBasedComponent componente = getComponentePorId(id, getWindowPaleta());
		Object resultado = "";
		try {
			resultado = Reflexao.getValorObjeto(componente, "value");
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		return resultado;
	}
	
	/**
	 * Método responsável por setar o valor do componente de um Parâmetro do TipoMembro
	 * 
	 * @param id
	 * @param parametro
	 */
	private void setarParametroModeloPorId(String id, IParametro<?> parametro) {
		HtmlBasedComponent componente = getComponentePorId(id, getWindowPaleta());
		try {
			IComponenteDominio componenteDominio = super.getInstanciaComponente(parametro);
			componenteDominio.setValor(componente, parametro.getValor());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que gerar a Grid de Propriedades, ou Paleta do TipoMembro
	 * 
	 * @return grid das propriedades
	 */
	private Grid gerarGridPropriedades() {
		Grid grid = new Grid();
		grid.setHeight("400px;");
		Rows rows = new Rows();
		grid.appendChild(rows);
		gerarParametrosMembro(rows);
		gerarParametrosTipoMembro(rows, getTipoMembroVisaoSelecionado(), WIDTH);
		return grid;
	}
	
	/**
	 * Método responsável por setar os valores aos componentes do Parametros do Membro
	 */
	private void setarValoresParametrosMembro() {
		Textbox textbox = (Textbox) getWindowPaleta().getFellow(PARAMETRONOME);
		textbox.setDisabled(true);
		textbox.setValue(getTipoMembroVisaoSelecionado().getMembro().getNome());
		textbox = (Textbox) getWindowPaleta().getFellow(PARAMETRODESC);
		textbox.setDisabled(true);
		textbox.setValue(getTipoMembroVisaoSelecionado().getMembro().getDescricao());
		
		Spinner spinner = (Spinner) getWindowPaleta().getFellow(PARAMETROALTURA);
		spinner.setValue(getTipoMembroVisaoSelecionado().getMembro().getAltura());
		spinner = (Spinner) getWindowPaleta().getFellow(PARAMETROCOMPRIMENTO);
		spinner.setValue(getTipoMembroVisaoSelecionado().getMembro().getComprimento());
		spinner = (Spinner) getWindowPaleta().getFellow(PARAMETROPOSX);
		spinner.setValue(getTipoMembroVisaoSelecionado().getMembro().getX());
		spinner = (Spinner) getWindowPaleta().getFellow(PARAMETROPOSY);
		spinner.setValue(getTipoMembroVisaoSelecionado().getMembro().getY());
	}
	
	/**
	 * Método responsável por setar os valores de todos os componentes que representam os parâmetros do TipoMembro
	 * usado quando abre um ArtefatoModelo
	 */
	private void setarValoresParametrosTipoMembro() {
		for (IParametro<?> parametro : getTipoMembroVisaoSelecionado().getMembro().getTipoMembroModelo().getListaParametros()) {
			setarParametroModeloPorId("PARAMETRO"+parametro.getNome(), parametro);
		}
	}
	
	/**
	 * Método responsável por adicionar a Rows vinda como parâmetro os componentes do Parametro do Membro
	 * 
	 * @param rows
	 */
	private void gerarParametrosMembro(Rows rows) {
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarLabel("Nome do membro")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarTextbox(PARAMETRONOME, "Digite o Nome do Membro")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarLabel("Descrição do membro")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarTextbox(PARAMETRODESC, "Digite a Descrição do Membro")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarLabel("Posição X")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarSpinner(PARAMETROPOSX, "Digite a Posição X", 0)}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarLabel("Posição Y")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarSpinner(PARAMETROPOSY, "Digite a Posição Y", 0)}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarLabel("Altura")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarSpinner(PARAMETROALTURA, "Digite a altura", 0)}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarLabel("Comprimento")}));
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {gerarSpinner(PARAMETROCOMPRIMENTO, "Digite o Comprimento", 300)}));
		Separator separator = new Separator();
		separator.setStyle("border-top: 1px solid; width: 100%;");
		rows.appendChild(gerarRow(new org.zkoss.zk.ui.Component[] {separator}));
	}
	
	/**
	 * Método que gerar Spinner através dos parâmetros
	 * 
	 * @param id
	 * @param tooltip
	 * @param maxlength
	 * @return spinner
	 */
	private Spinner gerarSpinner(String id, String tooltip, int maxlength) {
		Spinner spinner = new Spinner();
		spinner.setId(id);
		spinner.setTooltiptext(tooltip);
		if (maxlength > 0) {
			spinner.setMaxlength(maxlength);
		}
		spinner.setWidth(WIDTH);
		return spinner;
	}
	
	/**
	 * Método que cria um Textbox através dos Parâmetros
	 * 
	 * @param id
	 * @param tooltip
	 * @return textbox
	 */
	private Textbox gerarTextbox(String id, String tooltip) {
		
		Textbox textbox = new Textbox();
		textbox.setId(id);
		textbox.setWidth(WIDTH);
		textbox.setTooltiptext(tooltip);
		return textbox;
	}
	
	/* GETTERS AND SETTERS */

	/**
	 * @return Artefato o(a) artefatoAAbrir
	 */
	public Artefato getArtefatoAAbrir() {
		return artefatoAAbrir;
	}

	/**
	 * @param artefatoAAbrir o(a) artefatoAAbrir a ser setado(a)
	 */
	public void setArtefatoAAbrir(Artefato artefatoAAbrir) {
		this.artefatoAAbrir = artefatoAAbrir;
	}
	
	/**
	 * @return String o(a) nomeAntigo
	 */
	public String getNomeAntigo() {
		return nomeAntigo;
	}

	/**
	 * @param nomeAntigo o(a) nomeAntigo a ser setado(a)
	 */
	public void setNomeAntigo(String nomeAntigo) {
		this.nomeAntigo = nomeAntigo;
	}

	/**
	 * @return String o(a) descricaoAntiga
	 */
	public String getDescricaoAntiga() {
		return descricaoAntiga;
	}

	/**
	 * @param descricaoAntiga o(a) descricaoAntiga a ser setado(a)
	 */
	public void setDescricaoAntiga(String descricaoAntiga) {
		this.descricaoAntiga = descricaoAntiga;
	}

	/**
	 * @return int o(a) alturaAntiga
	 */
	public int getAlturaAntiga() {
		return alturaAntiga;
	}

	/**
	 * @param alturaAntiga o(a) alturaAntiga a ser setado(a)
	 */
	public void setAlturaAntiga(int alturaAntiga) {
		this.alturaAntiga = alturaAntiga;
	}

	/**
	 * @return int o(a) larguraAntiga
	 */
	public int getLarguraAntiga() {
		return larguraAntiga;
	}

	/**
	 * @param larguraAntiga o(a) larguraAntiga a ser setado(a)
	 */
	public void setLarguraAntiga(int larguraAntiga) {
		this.larguraAntiga = larguraAntiga;
	}

	/**
	 * @return Execution o(a) executionAntigo
	 */
	public Execution getExecutionAntigo() {
		return executionAntigo;
	}

	/**
	 * @param executionAntigo o(a) executionAntigo a ser setado(a)
	 */
	public void setExecutionAntigo(Execution executionAntigo) {
		this.executionAntigo = executionAntigo;
	}

	/**
	 * @return org.zkoss.zk.ui.Component o(a) componenteAntigo
	 */
	public org.zkoss.zk.ui.Component getComponenteAntigo() {
		return componenteAntigo;
	}

	/**
	 * @param componenteAntigo o(a) componenteAntigo a ser setado(a)
	 */
	public void setComponenteAntigo(org.zkoss.zk.ui.Component componenteAntigo) {
		this.componenteAntigo = componenteAntigo;
	}

	/**
	 * @return AnnotateDataBinder o(a) binderAntigo
	 */
	public AnnotateDataBinder getBinderAntigo() {
		return binderAntigo;
	}

	/**
	 * @param binderAntigo o(a) binderAntigo a ser setado(a)
	 */
	public void setBinderAntigo(AnnotateDataBinder binderAntigo) {
		this.binderAntigo = binderAntigo;
	}

	/**
	 * @return Long o(a) codigoAntigo
	 */
	public Long getCodigoAntigo() {
		return codigoAntigo;
	}

	/**
	 * @param codigoAntigo o(a) codigoAntigo a ser setado(a)
	 */
	public void setCodigoAntigo(Long codigoAntigo) {
		this.codigoAntigo = codigoAntigo;
	}

}
