package br.ueg.unucet.docscree.visao.compositor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.modelo.MembroDocScree;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;

@SuppressWarnings("rawtypes")
@Component
@Scope("session")
public class ArtefatoCompositor extends GenericoCompositor<ArtefatoControle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3331698085154478299L;
	private static final Integer LARGURA_MAXIMA = 800;
	private static final String WIDTH = "140px;";
	private static final String PARAMETROALTURA = "idParametroAltura";
	private static final String PARAMETROCOMPRIMENTO = "idParametroComprimento";
	private static final String PARAMETROPOSX = "idParametroX";
	private static final String PARAMETROPOSY = "idParametroY";
	private static final String PARAMETRONOME = "idParametroNome";
	private static final String PARAMETRODESC = "idParametroDescricao";
	private static final String ESTILODIV = " position: absolute; display: table; ";
	private static final String ESTILOCOMPONENTE = " padding: 0px; margin: 0px; padding-top: 1px;";

	private Window areaMontagemWindow = null;
	private Window areaVisualizacaoWindow = null;
	protected AnnotateDataBinder binderPaleta;
	protected AnnotateDataBinder binderArtefato;
	protected AnnotateDataBinder binderVisualizao;
	protected AnnotateDataBinder binderMembro;
	private Integer larguraTotalMembro;
	@AtributoVisao(nome="tipoMembroVisaoSelecionado", isCampoEntidade = false)
	private SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado;
	@AtributoVisao(nome="listaMembrosDocScree", isCampoEntidade = false)
	private Map<String, MembroDocScree> mapaMembrosAdicionados;
	
	@AtributoVisao(nome="nome", isCampoEntidade = true)
	private String nome;
	@AtributoVisao(nome="descricao", isCampoEntidade = true)
	private String descricao;
	@AtributoVisao(nome="categoria", isCampoEntidade = true)
	private Categoria categoria;
	@AtributoVisao(nome="titulo", isCampoEntidade = true)
	private String titulo;
	@AtributoVisao(nome="membros", isCampoEntidade = true)
	private Collection<Membro> membros;
	@AtributoVisao(nome="servicos", isCampoEntidade = true)
	private Collection<IServico> servicos;
	@AtributoVisao(nome="artefatoControle", isCampoEntidade = true)
	private IArtefatoControle<Artefato, Long> artefatoControle;
	@AtributoVisao(nome="altura", isCampoEntidade = true)
	private int altura;
	@AtributoVisao(nome="largura", isCampoEntidade = true)
	private int largura;

	@Override
	public Class getTipoEntidade() {
		return Artefato.class;
	}

	@Override
	protected void limparCampos() {
		getComponent().detach();
		Executions.sendRedirect("/pages/montar-artefato.zul");
	}

	@Override
	protected void limparFiltros() {
		// TODO Auto-generated method stub

	}

	@Override
	public void acaoFiltrar() {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
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

	@SuppressWarnings("unchecked")
	public void acaoAbrir() {
		boolean retorno = false;
		inicializarVariaveisNovoArtefato();
		try {
			retorno = super.getControle().fazerAcao("montarArtefato", (SuperCompositor) this);
			if (retorno) {
				org.zkoss.zk.ui.Component comp = Executions.createComponents(
						"/componentes/modalNovoArtefato.zul", null, null);

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
	}
	
	private void inicializarVariaveisNovoArtefato() {
		this.setCodigo(null);
		this.setLargura(0);
		this.setAltura(0);
		this.setNome("");
		this.setDescricao("");
	}
	
	private void inicializarVariaveisCriarArtefato() {
		this.areaMontagemWindow = null;
		this.areaVisualizacaoWindow = null;
		this.binderArtefato = null;
		this.binderVisualizao = null;
		setMapaMembrosAdicionados(new HashMap<String, MembroDocScree>());
	}
	
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
	
	@SuppressWarnings("unchecked")
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
	
	private void limparTodaTela() {
		List<org.zkoss.zk.ui.Component> children = getComponent().getChildren();
		for (org.zkoss.zk.ui.Component component : children) {
			if (component.getId() == null || !component.getId().equals("windowAvisos")) {
				component.setVisible(false);
			}
		}
	}
	
	private void exibirMensagemErro(String mensagem) {
		Mensagens mensagens = new Mensagens();
		mensagens.setTipoMensagem(TipoMensagem.ERRO);
		mensagens.getListaMensagens().add(mensagem);
		getControle().setMensagens(mensagens);
		super.mostrarMensagem(false);
	}
	
	public void carregarArtefato() {
		getWindowArtefato().setWidth(String.valueOf(getLargura())+"px");
		getWindowArtefato().setHeight(String.valueOf(getAltura())+"px");
		getWindowVisualizacaoArtefato().setWidth(String.valueOf(getLargura())+"px");
		getWindowVisualizacaoArtefato().setHeight(String.valueOf(getAltura())+"px");
		super.binder.loadAll();
	}

	public List<SuperTipoMembroVisaoZK> getListaTipoMembrosVisao() {
		return super.getControle().listarTipoMembrosVisao();
	}

	private HtmlBasedComponent gerarNovaInstancia(String idComponente, Membro membro) {
		Div div = null;
		try {
			HtmlBasedComponent novaInstancia = (HtmlBasedComponent) getTipoMembroVisaoSelecionado().getVisaoPreenchimento().getClass().newInstance();
			novaInstancia.setId(idComponente);
			novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getCss(membro) + ESTILOCOMPONENTE);
			div = new Div();
			div.setId(idComponente.replace("Componente", "Grid"));
			div.setWidth(String.valueOf(membro.getComprimento())+"px");
			div.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + ESTILODIV + "cursor: pointer;");
			setarEventDiv(div);
			div.appendChild(novaInstancia);
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return div;
	}

	private HtmlBasedComponent gerarNovaInstanciaVisualizacao(String idComponente, Membro membro) {
		Div div = null;
		try {
			HtmlBasedComponent novaInstancia = (HtmlBasedComponent) getTipoMembroVisaoSelecionado().getVisaoVisualizacao().getClass().newInstance();
			novaInstancia.setId(idComponente);
			novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getCss(membro) + ESTILOCOMPONENTE);
			div = new Div();
			div.setId(idComponente.replace("Visualizacao", "Grid"));
			div.setWidth(String.valueOf(membro.getComprimento())+"px");
			div.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + ESTILODIV + "cursor: pointer;");
			setarEventDiv(div);
			div.appendChild(novaInstancia);
			getTipoMembroVisaoSelecionado().getVisualizacaoExemplo(novaInstancia, "Exemplo");
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return div;
	}
	
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

	protected Window getWindowArtefato() {
		if (this.areaMontagemWindow == null) {
			this.areaMontagemWindow = (Window) getComponent().getFellow(
					"windowArtefato");
		}
		return this.areaMontagemWindow;
	}
	
	protected Window getWindowVisualizacaoArtefato() {
		if (this.areaVisualizacaoWindow == null) {
			this.areaVisualizacaoWindow = (Window) getComponent().getFellow(
					"windowArtefatoVisualizacao");
		}
		return this.areaVisualizacaoWindow;
	}
	
	protected AnnotateDataBinder getBinderArtefato() {
		if (this.binderArtefato == null) {
			this.binderArtefato = new AnnotateDataBinder(getWindowArtefato());
		}
		return this.binderArtefato;
	}
	
	protected AnnotateDataBinder getBinderVisualizacaoArtefato() {
		if (this.binderVisualizao == null) {
			this.binderVisualizao = new AnnotateDataBinder(getWindowVisualizacaoArtefato());
		}
		return this.binderVisualizao;
	}
	
	protected AnnotateDataBinder getBinderPaleta() {
		if (this.binderPaleta == null) {
			this.binderPaleta = new AnnotateDataBinder(getWindowPaleta());
		}
		return this.binderPaleta;
	}
	
	protected Window getWindowPaleta() {
		return (Window) getComponent().getFellow("windowPaleta");
	}

	public void gerarPaletaParametros(boolean isNovo) {
		super.binder.saveAll();
		if (this.getTipoMembroVisaoSelecionado() != null) {
			
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
				windowPaleta.appendChild(gerarButtonRemover());
			}
			getBinderPaleta().loadAll();
		}
	}
	
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
					getComponentePaletaPorId("gridListaMembros").setVisible(true);
					getBinderPaleta().loadAll();
				} else {
					getComponentePaletaPorId("gridListaMembros").setVisible(false);
					getBinderPaleta().loadAll();
				}
			}
			
		});
		rows.appendChild(gerarRow(checkbox));
		grid.appendChild(rows);
		return grid;
	}
	
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
					getInstanciaComponente(parametro).setValor(getComponentePaletaPorId("PARAMETRO"+parametro.getNome()), parametro.getValor());
				}
				getBinderPaleta().loadAll();
			}
			
		});
		rows.appendChild(gerarRow(combobox));
		return grid;
	}
	
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
	
	@SuppressWarnings("unchecked")
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
						adicionarMembroAoArtefato(getIdMembro());
						adicionarMembroAVisualizacao(getIdMembro());
						setTipoMembroVisaoSelecionado(null);
						getWindowPaleta().getChildren().clear();
						getBinderPaleta().loadAll();
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
	
	private String getIdMembro() {
		return getTipoMembroVisaoSelecionado().getNome()+String.valueOf(getTipoMembroVisaoSelecionado().getMembro().getCodigo());
	}
	
	@SuppressWarnings("unchecked")
	public void removerMembro() {
		try {
			boolean retorno = this.getControle().fazerAcao("removerMembro", (SuperCompositor) this);
			if (retorno) {
				removerMembroAlterados(getIdMembro());
				getMapaMembrosAdicionados().remove("Componente" + getIdMembro());
				setTipoMembroVisaoSelecionado(null);
				getWindowPaleta().getChildren().clear();
				getBinderPaleta().loadAll();
			} 
			super.mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chamarMensagemErro() {
		getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
		super.mostrarMensagem(false);
		super.binder.loadAll();
	}
	
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
	
	private boolean puxarValorParametrosModelo() {
		@SuppressWarnings("unchecked")
		Collection<IParametro<?>> listaParametros = this.getTipoMembroVisaoSelecionado().getListaParametros();
		boolean parametrosObrigatoriosPreenchidos = true;
		for (IParametro<?> parametro : listaParametros) {
			Object objeto = getParametroModeloPorId("PARAMETRO"+parametro.getNome(), parametro);
			if (objeto != null && !objeto.toString().isEmpty() && objeto.toString() != String.valueOf(0)) {
				parametro.setValor(String.valueOf(objeto));
			} else if (parametro.isObrigatorio()) {
				parametrosObrigatoriosPreenchidos = false;
				getControle().getMensagens().getListaMensagens().add("O parâmetro " + parametro.getRotulo()+ " é obrigatório!");
			}
		}
		return parametrosObrigatoriosPreenchidos;
	}
	
	private void adicionarMembroAoArtefato(String nomeParcial) {
		lancarMembroAoArtefato(nomeParcial, true);
	}
	
	private void adicionarMembroAVisualizacao(String nomeParcial) {
		String idVisualizacao = "Visualizacao" + nomeParcial;
		HtmlBasedComponent componenteVisualizacao = gerarNovaInstanciaVisualizacao(idVisualizacao, getTipoMembroVisaoSelecionado().getMembro());
		if (componenteVisualizacao != null) {
			componenteVisualizacao.setParent(getWindowVisualizacaoArtefato());
			getWindowVisualizacaoArtefato().appendChild(componenteVisualizacao);
			getBinderVisualizacaoArtefato().loadAll();
		} 
	}
	
	private void lancarMembroAoArtefato(String nomeParcial, boolean isNovo) {
		String idComponente = "Componente" + nomeParcial;
		HtmlBasedComponent componente = gerarNovaInstancia("Componente" + nomeParcial, getTipoMembroVisaoSelecionado().getMembro());
		if (componente != null) {
			if (isNovo) {
				this.getMapaMembrosAdicionados().put(idComponente, new MembroDocScree(getTipoMembroVisaoSelecionado(), idComponente));
			} else {
				this.getMapaMembrosAdicionados().get(idComponente).setTipoMembroVisao(getTipoMembroVisaoSelecionado());
			}
			componente.setParent(getWindowArtefato());
			getWindowArtefato().appendChild(componente);
			getBinderArtefato().loadAll();
			getWindowPaleta().getChildren().clear();
			getBinderPaleta().loadAll();
		} 
	}
	
	private void removerMembroAlterados(String nomeParcial) {
		String idGrid = "Grid" + nomeParcial;
		
		getWindowArtefato().getFellow(idGrid).detach();
		getWindowVisualizacaoArtefato().getFellow(idGrid).detach();
	}
	
	private Object getParametroMembroPorId(String id) {
		HtmlBasedComponent componente = getComponentePaletaPorId(id);
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
	
	private Object getParametroModeloPorId(String id, IParametro<?> parametro) {
		HtmlBasedComponent componente = getComponentePaletaPorId(id);
		try {
			IComponenteDominio componenteDominio = super.getInstanciaComponente(parametro);
			return componenteDominio.getValor(componente);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setarParametroModeloPorId(String id, IParametro<?> parametro) {
		HtmlBasedComponent componente = getComponentePaletaPorId(id);
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
	
	private HtmlBasedComponent getComponentePaletaPorId(String id) {
		return (HtmlBasedComponent) getWindowPaleta().getFellow(id);
	}
	
	private Grid gerarGridPropriedades() {
		Grid grid = new Grid();
		grid.setHeight("400px;");
		Rows rows = new Rows();
		grid.appendChild(rows);
		gerarParametrosMembro(rows);
		gerarParametrosTipoMembro(rows);
		return grid;
	}
	
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
	
	private void setarValoresParametrosTipoMembro() {
		for (IParametro<?> parametro : getTipoMembroVisaoSelecionado().getMembro().getTipoMembroModelo().getListaParametros()) {
			setarParametroModeloPorId("PARAMETRO"+parametro.getNome(), parametro);
		}
	}
	
	private void gerarParametrosTipoMembro(Rows rows) {
		@SuppressWarnings("unchecked")
		Collection<IParametro<?>> listaParametros = this.getTipoMembroVisaoSelecionado().getListaParametros();
		for (IParametro<?> parametro : listaParametros) {
			HtmlBasedComponent componenteDominio = null;
			rows.appendChild(gerarRow(gerarLabel(parametro.getRotulo())));
			try {
				componenteDominio = super.getComponentePorDominio(parametro, WIDTH);
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
			if (componenteDominio == null) {
				componenteDominio = this.gerarTextBoxGenerico();
			}
			componenteDominio.setId("PARAMETRO"+parametro.getNome());
			rows.appendChild(gerarRow(componenteDominio));
		}
	}
	
	private void gerarParametrosMembro(Rows rows) {
		rows.appendChild(gerarRow(gerarLabel("Nome do membro")));
		rows.appendChild(gerarRow(gerarTextbox(PARAMETRONOME, "Digite o Nome do Membro")));
		rows.appendChild(gerarRow(gerarLabel("Descrição do membro")));
		rows.appendChild(gerarRow(gerarTextbox(PARAMETRODESC, "Digite a Descrição do Membro")));
		rows.appendChild(gerarRow(gerarLabel("Posição X")));
		rows.appendChild(gerarRow(gerarSpinner(PARAMETROPOSX, "Digite a Posição X", 0)));
		rows.appendChild(gerarRow(gerarLabel("Posição Y")));
		rows.appendChild(gerarRow(gerarSpinner(PARAMETROPOSY, "Digite a Posição Y", 0)));
		rows.appendChild(gerarRow(gerarLabel("Altura")));
		rows.appendChild(gerarRow(gerarSpinner(PARAMETROALTURA, "Digite a altura", 0)));
		rows.appendChild(gerarRow(gerarLabel("Comprimento")));
		rows.appendChild(gerarRow(gerarSpinner(PARAMETROCOMPRIMENTO, "Digite o Comprimento", 300)));
		Separator separator = new Separator();
		separator.setStyle("border-top: 1px solid; width: 100%;");
		rows.appendChild(gerarRow(separator));
	}
	
	private Row gerarRow(org.zkoss.zk.ui.Component componente) {
		Row row = new Row();
		row.appendChild(componente);
		return row;
	}
	
	private Label gerarLabel(String valor) {
		Label label = new Label();
		label.setValue(valor+":");
		return label;
	}
	
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
	
	private Textbox gerarTextbox(String id, String tooltip) {
		
		Textbox textbox = new Textbox();
		textbox.setId(id);
		textbox.setWidth(WIDTH);
		textbox.setTooltiptext(tooltip);
		return textbox;
	}
	
	private Textbox gerarTextBoxGenerico() {
		Textbox textbox = new Textbox();
		textbox.setWidth(WIDTH);
		return textbox;
	}
	
	/* GETTERS AND SETTERS */

	/**
	 * @return SuperTipoMembroVisaoZK o(a) tipoMembroVisaoSelecionado
	 */
	public SuperTipoMembroVisaoZK getTipoMembroVisaoSelecionado() {
		return tipoMembroVisaoSelecionado;
	}

	/**
	 * @param SuperTipoMembroVisaoZK
	 *            o(a) tipoMembroVisaoSelecionado a ser setado(a)
	 */
	public void setTipoMembroVisaoSelecionado(
			SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado) {
		this.tipoMembroVisaoSelecionado = tipoMembroVisaoSelecionado;
	}

	/**
	 * @return String o(a) nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param String o(a) nome a ser setado(a)
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return String o(a) descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param String o(a) descricao a ser setado(a)
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return Categoria o(a) categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param Categoria o(a) categoria a ser setado(a)
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return String o(a) titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param String o(a) titulo a ser setado(a)
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return Collection<Membro> o(a) membros
	 */
	public Collection<Membro> getMembros() {
		return membros;
	}

	/**
	 * @param Collection<Membro> o(a) membros a ser setado(a)
	 */
	public void setMembros(Collection<Membro> membros) {
		this.membros = membros;
	}

	/**
	 * @return Collection<IServico> o(a) servicos
	 */
	public Collection<IServico> getServicos() {
		return servicos;
	}

	/**
	 * @param Collection<IServico> o(a) servicos a ser setado(a)
	 */
	public void setServicos(Collection<IServico> servicos) {
		this.servicos = servicos;
	}

	/**
	 * @return IArtefatoControle<Artefato,Long> o(a) artefatoControle
	 */
	public IArtefatoControle<Artefato, Long> getArtefatoControle() {
		return artefatoControle;
	}

	/**
	 * @param IArtefatoControle<Artefato,Long> o(a) artefatoControle a ser setado(a)
	 */
	public void setArtefatoControle(
			IArtefatoControle<Artefato, Long> artefatoControle) {
		this.artefatoControle = artefatoControle;
	}

	/**
	 * @return int o(a) altura
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * @param int o(a) altura a ser setado(a)
	 */
	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * @return int o(a) largura
	 */
	public int getLargura() {
		return largura;
	}

	/**
	 * @param int o(a) largura a ser setado(a)
	 */
	public void setLargura(int largura) {
		this.largura = largura;
	}

	/**
	 * @return Map<String,MembroDocScree> o(a) mapaMembrosAdicionados
	 */
	public Map<String, MembroDocScree> getMapaMembrosAdicionados() {
		return mapaMembrosAdicionados;
	}

	/**
	 * @param Map<String,MembroDocScree> o(a) mapaMembrosAdicionados a ser setado(a)
	 */
	public void setMapaMembrosAdicionados(
			Map<String, MembroDocScree> mapaMembrosAdicionados) {
		this.mapaMembrosAdicionados = mapaMembrosAdicionados;
	}

}
