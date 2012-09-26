package br.ueg.unucet.docscree.visao.compositor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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

import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.interfaces.IComponenteDominio;
import br.ueg.unucet.docscree.modelo.MembroDocScree;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
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
	private Boolean atualizarMembro = Boolean.FALSE;
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
			div.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + " position: relative; display: table;");
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
		if (this.getTipoMembroVisaoSelecionado() != null) {
			Window windowPaleta = getWindowPaleta();
			windowPaleta.getChildren().clear();
			
			windowPaleta.appendChild(gerarGridOpcao());
			windowPaleta.appendChild(gerarGridMembros());
			windowPaleta.appendChild(gerarGridPropriedades());
			windowPaleta.appendChild(gerarButtonPropriedades(true));
			getBinderPaleta().loadAll();
		}
	}
	
	private Grid gerarGridOpcao() {
		Grid grid = new Grid();
		Rows rows = new Rows();
		Checkbox checkbox = new Checkbox("Usar Membro salvo");
		checkbox.addEventListener("onCheck", new EventListener<CheckEvent>() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(CheckEvent event) throws Exception {
				if (event.isChecked()) {
					System.out.println("Checkado");
				} else {
				}
			}
			
		});
		rows.appendChild(gerarRow(checkbox));
		grid.appendChild(rows);
		return grid;
	}
	
	private Grid gerarGridMembros() {
		Grid grid = new Grid();
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
				setValorParametroMembroPorId(PARAMETRONOME, membro.getNome());
				setValorParametroMembroPorId(PARAMETRODESC, membro.getDescricao());
				Collection<IParametro<?>> listaParametros = membro.getTipoMembroModelo().getListaParametros();
				for (IParametro<?> parametro : listaParametros) {
					getInstanciaComponente(parametro).setValor(getComponentePaletaPorId("PARAMETRO"+parametro.getNome()), parametro.getValor());
				}
				getBinderPaleta().loadAll();
			}
			
		});
		rows.appendChild(gerarRow(combobox));
		Checkbox checkbox = new Checkbox("Alterar Membro quando inserir ao Artefato");
		checkbox.setChecked(getAtualizarMembro());
		checkbox.addEventListener("onCheck", new EventListener<CheckEvent>() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(CheckEvent event) throws Exception {
				if (event.isChecked()) {
					setAtualizarMembro(Boolean.TRUE);
				} else {
					setAtualizarMembro(Boolean.FALSE);
				}
			}
			
		});
		rows.appendChild(gerarRow(checkbox));
		return grid;
	}
	
	@SuppressWarnings("unchecked")
	private Button gerarButtonPropriedades(final boolean isSalvar) {
		Button button = new Button();
		if (isSalvar) {
			button.setLabel("Inserir Membro");
		}
		button.addEventListener("onClick", new EventListener() {

			/* (non-Javadoc)
			 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
			 */
			@Override
			public void onEvent(Event arg0) throws Exception {
				new Runnable() {
					
					@Override
					public void run() {
						gerarMembro(isSalvar);
					}
				}.run();
			}
			
		});
		return button;
	}
	
	public void gerarMembro(boolean isSalvar) {
		puxarValorParametrosMembro();
		puxarValorParametrosModelo();
		new Runnable() {
			
			@Override
			public void run() {
				Retorno<Object, Object> retorno = getControle().getFramework().mapearMembro(getTipoMembroVisaoSelecionado().getMembro());
				if (retorno.isSucesso()) {
					//setTipoMembroVisaoSelecionado(null);
				} else {
					
				}
			}
		}.run();
		this.adicionarMembroAoArtefato(getTipoMembroVisaoSelecionado().getMembro());
	}
	
	private void puxarValorParametrosMembro() {
		getTipoMembroVisaoSelecionado().getMembro().setNome(String.valueOf(getParametroMembroPorId(PARAMETRONOME)));
		getTipoMembroVisaoSelecionado().getMembro().setAltura(Integer.valueOf(String.valueOf(getParametroMembroPorId(PARAMETROALTURA))));
		getTipoMembroVisaoSelecionado().getMembro().setComprimento(Integer.valueOf(String.valueOf(getParametroMembroPorId(PARAMETROCOMPRIMENTO))));
		getTipoMembroVisaoSelecionado().getMembro().setDescricao(String.valueOf(getParametroMembroPorId(PARAMETRODESC)));
		getTipoMembroVisaoSelecionado().getMembro().setX(Integer.valueOf(String.valueOf(getParametroMembroPorId(PARAMETROPOSX))));
		getTipoMembroVisaoSelecionado().getMembro().setY(Integer.valueOf(String.valueOf(getParametroMembroPorId(PARAMETROPOSY))));
	}
	
	private void puxarValorParametrosModelo() {
		@SuppressWarnings("unchecked")
		Collection<IParametro<?>> listaParametros = this.getTipoMembroVisaoSelecionado().getListaParametros();
		for (IParametro<?> parametro : listaParametros) {
			parametro.setValor(String.valueOf(getParametroModeloPorId("PARAMETRO"+parametro.getNome(), parametro)));
		}
	}
	
	private void adicionarMembroAoArtefato(Membro membro) {
		String idComponente = "Componente"+getTipoMembroVisaoSelecionado().getNome()+String.valueOf(contador++);
		HtmlBasedComponent componente = gerarNovaInstancia(idComponente, membro);
		if (componente != null) {
			this.listaMembrosAdicionados.add(new MembroDocScree(getTipoMembroVisaoSelecionado(), membro, idComponente));
			componente.setParent(getWindowArtefato());
			getWindowArtefato().appendChild(componente);
			super.binder.loadAll();
		}
	}
	
	private Object getParametroMembroPorId(String id) {
		HtmlBasedComponent componente = getComponentePaletaPorId(id);
		Object resultado = "";
		try {
			resultado = Reflexao.getValorObjeto(componente, "value");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	private void setValorParametroMembroPorId(String id, Object valor) {
		HtmlBasedComponent componente = getComponentePaletaPorId(id);
		try {
			Reflexao.setValorObjeto("value", String.class, valor, componente);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
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
	 * @return Boolean o(a) atualizarMembro
	 */
	public Boolean getAtualizarMembro() {
		return atualizarMembro;
	}

	/**
	 * @param Boolean o(a) atualizarMembro a ser setado(a)
	 */
	public void setAtualizarMembro(Boolean atualizarMembro) {
		this.atualizarMembro = atualizarMembro;
	}

}
