package br.ueg.unucet.docscree.visao.compositor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
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
import br.ueg.unucet.docscree.modelo.MembroDocScree;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
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
	private int contador = 0;
	private List<MembroDocScree> listaMembrosAdicionados = new ArrayList<MembroDocScree>();
	private SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado;

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
		 Window window = null;
		try {
			HtmlBasedComponent novaInstancia = (HtmlBasedComponent) getTipoMembroVisaoSelecionado().getVisaoVisualizacao().getClass().newInstance();
			novaInstancia.setId(idComponente);
			novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getCss(membro) + " padding: 0px; margin: 0px; padding-top: 1px;");
			window = new Window();
			window.setBorder(false);
			window.setId(idComponente.replace("Componente", "Grid"));
			window.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 3) + " position: relative;");
			window.appendChild(novaInstancia);
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return window;
	}

	public Window getWindowArtefato() {
		if (this.areaMontagemWindow == null) {
			this.areaMontagemWindow = (Window) getComponent().getFellow(
					"windowArtefato");
		}
		return this.areaMontagemWindow;
	}
	
	public void gerarPaletaParametros() {
		if (this.getTipoMembroVisaoSelecionado() != null) {
			Window windowPaleta = (Window) getComponent().getFellow("windowPaleta");
			windowPaleta.getChildren().clear();
			
			windowPaleta.appendChild(gerarGridPropriedades());
			windowPaleta.appendChild(gerarButtonPropriedades(true));
			super.binder.loadAll();
		}
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
		getTipoMembroVisaoSelecionado().getMembro().setNome(String.valueOf(getParametroPorId(PARAMETRONOME)));
		getTipoMembroVisaoSelecionado().getMembro().setAltura(Integer.valueOf(String.valueOf(getParametroPorId(PARAMETROALTURA))));
		getTipoMembroVisaoSelecionado().getMembro().setComprimento(Integer.valueOf(String.valueOf(getParametroPorId(PARAMETROCOMPRIMENTO))));
		getTipoMembroVisaoSelecionado().getMembro().setDescricao(String.valueOf(getParametroPorId(PARAMETRODESC)));
		getTipoMembroVisaoSelecionado().getMembro().setX(Integer.valueOf(String.valueOf(getParametroPorId(PARAMETROPOSX))));
		getTipoMembroVisaoSelecionado().getMembro().setY(Integer.valueOf(String.valueOf(getParametroPorId(PARAMETROPOSY))));
		new Runnable() {
			
			@Override
			public void run() {
				getControle().getFramework().mapearMembro(getTipoMembroVisaoSelecionado().getMembro());
			}
		}.run();
		this.adicionarMembroAoArtefato(getTipoMembroVisaoSelecionado().getMembro());
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
		// TODO to mexendo aqui
	}
	
	// TODO jogar para controle
	private Object getParametroPorId(String parametro) {
		org.zkoss.zk.ui.Component componente = getComponent().getFellow("windowPaleta").getFellow(parametro);
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
	
	// TODO rever component
	private Grid gerarGridPropriedades() {
		Grid grid = new Grid();
		grid.setHeight("400px;");
		Rows rows = new Rows();
		grid.appendChild(rows);
		gerarParametrosMembro(rows);
		@SuppressWarnings("unchecked")
		Collection<IParametro<?>> listaParametros = this.getTipoMembroVisaoSelecionado().getListaParametros();
		for (IParametro<?> parametro : listaParametros) {
			DominioEntradaEnum dominioEntrada = parametro.getDominioEntrada();
			HtmlBasedComponent componenteDominio = null;
			rows.appendChild(gerarRow(gerarLabel(parametro.getRotulo())));
			if (dominioEntrada != null) {
				try {
					componenteDominio = super.getComponentePorDominio(parametro, WIDTH);
				} catch (ClassNotFoundException e) {
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				}
			} else {
				Collection<String> listaDominioTipo = parametro.getListaDominioTipo();
				if (!listaDominioTipo.isEmpty()) {
					Combobox combobox = new Combobox();
					combobox.setModel(new BindingListModelListModel<>(new ListModelArray<>(listaDominioTipo.toArray())));
					combobox.setWidth(WIDTH);
					componenteDominio = combobox;
				} else {
					componenteDominio = new Checkbox();
					
				}
			}
			if (componenteDominio == null) {
				componenteDominio = this.gerarTextBoxGenerico();
			}
			componenteDominio.setId("PARAMETRO"+parametro.getNome());
			rows.appendChild(gerarRow(componenteDominio));
		}
		
		return grid;
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

}
