package br.ueg.unucet.docscree.visao.compositor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.modelo.MembroDocScree;
import br.ueg.unucet.docscree.utilitarios.Mensagens;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

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

	private Window areaMontagemWindow = null;
	protected AnnotateDataBinder binderPaleta;
	protected AnnotateDataBinder binderArtefato;
	protected AnnotateDataBinder binderMembro;
	private List<MembroDocScree> listaMembrosAdicionados = new ArrayList<MembroDocScree>();
	private SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado;
	private String alturaArtefato;
	private String larguraArtefato;
	private Integer larguraTotalMembro;
	private int contador = 0;

	@Override
	public Class getTipoEntidade() {
		return Artefato.class;
	}

	@Override
	protected void limparCampos() {
		// TODO Auto-generated method stub

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
	
	public void exibirNovoArtefato() {
		super.binder.saveAll();
		this.areaMontagemWindow = null;
		org.zkoss.zk.ui.Component alturaArtefatoComponente = getComponent().getFellow("alturaArtefato");
		org.zkoss.zk.ui.Component larguraArtefatoComponente = getComponent().getFellow("larguraArtefato");
		if (alturaArtefatoComponente != null 
				&& larguraArtefatoComponente != null 
				&& getComponent().getFellow("nomeArtefato")!= null 
				&& getComponent().getFellow("descricaoArtefato") != null) {
			
			setLarguraArtefato( ((Intbox)larguraArtefatoComponente).getText());
			setAlturaArtefato(((Intbox)alturaArtefatoComponente).getText());
			if (!((Textbox) getComponent().getFellow("nomeArtefato")).getText().isEmpty()
					&& !((Textbox) getComponent().getFellow("descricaoArtefato")).getText().isEmpty()) {
				if (Integer.valueOf(getLarguraArtefato()).compareTo(LARGURA_MAXIMA) < 1 ) {
					Executions.sendRedirect("/pages/montar-artefato.zul");
					getComponent().detach();
					new Runnable() {
						
						@SuppressWarnings("unchecked")
						@Override
						public void run() {
							Artefato artefato = new Artefato();
							artefato.setNome(((Textbox) getComponent().getFellow("nomeArtefato")).getText());
							artefato.setDescricao(((Textbox) getComponent().getFellow("descricaoArtefato")).getText());
							setEntidade(artefato);
							try {
								boolean resultado = getControle().fazerAcao("salvar", (SuperCompositor) ArtefatoCompositor.this);
								mostrarMensagem(resultado);
							} catch (Exception e) {
								e.printStackTrace();
								exibirMensagemErro("Não foi possível executar a ação!");
							}
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
	
	private void exibirMensagemErro(String mensagem) {
		Mensagens mensagens = new Mensagens();
		mensagens.setTipoMensagem(TipoMensagem.ERRO);
		mensagens.getListaMensagens().add(mensagem);
		getControle().setMensagens(mensagens);
		super.mostrarMensagem(false);
	}
	
	public void carregarArtefato() {
		getWindowArtefato().setWidth(getLarguraArtefato()+"px");
		getWindowArtefato().setHeight(getAlturaArtefato()+"px");
		super.binder.loadAll();
	}

	public List<SuperTipoMembroVisaoZK> getListaTipoMembrosVisao() {
		return super.getControle().listarTipoMembrosVisao();
	}

	// TODO rever
	private HtmlBasedComponent gerarNovaInstancia(String idComponente, Membro membro) {
		 Div div = null;
		try {
			HtmlBasedComponent novaInstancia = (HtmlBasedComponent) getTipoMembroVisaoSelecionado().getVisaoVisualizacao().getClass().newInstance();
			novaInstancia.setId(idComponente);
			novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getCss(membro) + " padding: 0px; margin: 0px; padding-top: 1px;");
			div = new Div();
			div.setId(idComponente.replace("Componente", "Grid"));
			div.setWidth(String.valueOf(membro.getComprimento())+"px");
			div.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + " position: absolute; display: table;");
			div.appendChild(novaInstancia);
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return div;
	}

	protected Window getWindowArtefato() {
		if (this.areaMontagemWindow == null) {
			this.areaMontagemWindow = (Window) getComponent().getFellow(
					"windowArtefato");
		}
		return this.areaMontagemWindow;
	}
	
	protected AnnotateDataBinder getBinderArtefato() {
		if (this.binderArtefato == null) {
			this.binderArtefato = new AnnotateDataBinder(getWindowArtefato());
		}
		return this.binderArtefato;
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

	public void gerarPaletaParametros() {
		super.binder.saveAll();
		if (this.getTipoMembroVisaoSelecionado() != null) {
			Window windowPaleta = getWindowPaleta();
			windowPaleta.getChildren().clear();
			
			windowPaleta.appendChild(gerarGridOpcao());
			windowPaleta.appendChild(gerarGridMembros());
			windowPaleta.appendChild(gerarGridPropriedades());
			windowPaleta.appendChild(gerarButtonPropriedades());
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
	
	@SuppressWarnings("unchecked")
	private Button gerarButtonPropriedades() {
		Button button = new Button();
		button.setLabel("Inserir Membro");
		button.addEventListener("onClick", new EventListener() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(Event arg0) throws Exception {
				new Runnable() {
					
					@Override
					public void run() {
						gerarMembro();
					}
				}.run();
			}
			
		});
		return button;
	}
	
	public void gerarMembro() {
		getControle().setMensagens(new Mensagens());
		if (puxarValorParametrosMembro() && puxarValorParametrosModelo()) {
			if (this.larguraTotalMembro.compareTo(Integer.valueOf(getLarguraArtefato())) < 0) {
				Retorno<Object, Object> retorno = null;
				retorno = getControle().getFramework().mapearMembro(getTipoMembroVisaoSelecionado().getMembro());
				if (retorno.isSucesso()) {
					adicionarMembroAoArtefato(getTipoMembroVisaoSelecionado().getMembro());
					setTipoMembroVisaoSelecionado(null);
					getWindowPaleta().getChildren().clear();
					getBinderPaleta().loadAll();
					mostrarMensagem(true);
				} else {
					exibirMensagemErro(retorno.getMensagem());
				}
			} else {
				exibirMensagemErro("Somatória da largura do membro ("+ this.larguraTotalMembro +") deve ser menor que: " + getLarguraArtefato());
			}
		} else {
			chamarMensagemErro();
		}
	}
	
	private void chamarMensagemErro() {
		getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
		super.mostrarMensagem(false);
		super.binder.loadAll();
	}
	
	private boolean puxarValorParametrosMembro() {
		Object objeto = null;
		larguraTotalMembro = 0;
		//Verifica campos obrigatórios não validados pelo QUID
		boolean camposInformados = true;
		if ((objeto = getParametroMembroPorId(PARAMETRONOME)) != null) {
			getTipoMembroVisaoSelecionado().getMembro().setNome(String.valueOf(objeto));
		}
		if ((objeto = getParametroMembroPorId(PARAMETROALTURA)) != null && !objeto.toString().isEmpty()) {
			Integer valor = Integer.valueOf(String.valueOf(objeto));
			if (valor > 0) {
				getTipoMembroVisaoSelecionado().getMembro().setAltura(valor);
			} else {
				camposInformados = false;
				getControle().getMensagens().getListaMensagens().add("A Altura deve ser maior que 0");
			}
		} else {
			camposInformados = false;
			getControle().getMensagens().getListaMensagens().add("É necessário informar a Altura");
		}
		if ((objeto = getParametroMembroPorId(PARAMETROCOMPRIMENTO)) != null && !objeto.toString().isEmpty()) {
			Integer valor = Integer.valueOf(String.valueOf(objeto));
			if (valor > 0) {
				larguraTotalMembro += valor;
				getTipoMembroVisaoSelecionado().getMembro().setComprimento(valor);
			} else {
				camposInformados = false;
				getControle().getMensagens().getListaMensagens().add("O Comprimento deve ser maior que 0");
			}
		} else {
			camposInformados = false;
			getControle().getMensagens().getListaMensagens().add("É necessário informar o Comprimento");
		}
		if ((objeto = getParametroMembroPorId(PARAMETRODESC)) != null ) {
			getTipoMembroVisaoSelecionado().getMembro().setDescricao(String.valueOf(objeto));
		}
		if ((objeto = getParametroMembroPorId(PARAMETROPOSX)) != null && !objeto.toString().isEmpty()) {
			int posXMembro = Integer.valueOf(String.valueOf(objeto));
			larguraTotalMembro += posXMembro;
			getTipoMembroVisaoSelecionado().getMembro().setX(posXMembro);
		} else {
			camposInformados = false;
			getControle().getMensagens().getListaMensagens().add("É necessário informar a Posição X");
		}
		if ((objeto = getParametroMembroPorId(PARAMETROPOSY)) != null && !objeto.toString().isEmpty()) {
			getTipoMembroVisaoSelecionado().getMembro().setY(Integer.valueOf(String.valueOf(objeto)));
		} else {
			camposInformados = false;
			getControle().getMensagens().getListaMensagens().add("É necessário informar a Posição Y");
		}
		return camposInformados;
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
	
	private void adicionarMembroAoArtefato(Membro membro) {
		String idComponente = "Componente"+getTipoMembroVisaoSelecionado().getNome()+String.valueOf(contador++);
		HtmlBasedComponent componente = gerarNovaInstancia(idComponente, membro);
		if (componente != null) {
			this.listaMembrosAdicionados.add(new MembroDocScree(getTipoMembroVisaoSelecionado(), membro, idComponente));
			componente.setParent(getWindowArtefato());
			getWindowArtefato().appendChild(componente);
			getBinderArtefato().loadAll();
			getWindowPaleta().getChildren().clear();
			getBinderPaleta().loadAll();
		} 
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
	 * @return String o(a) alturaArtefato
	 */
	public String getAlturaArtefato() {
		return alturaArtefato;
	}

	/**
	 * @param String o(a) alturaArtefato a ser setado(a)
	 */
	public void setAlturaArtefato(String alturaArtefato) {
		this.alturaArtefato = alturaArtefato;
	}

	/**
	 * @return String o(a) larguraArtefato
	 */
	public String getLarguraArtefato() {
		return larguraArtefato;
	}

	/**
	 * @param String o(a) larguraArtefato a ser setado(a)
	 */
	public void setLarguraArtefato(String larguraArtefato) {
		this.larguraArtefato = larguraArtefato;
	}

}
