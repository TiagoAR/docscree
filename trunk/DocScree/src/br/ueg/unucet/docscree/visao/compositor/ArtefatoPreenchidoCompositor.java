package br.ueg.unucet.docscree.visao.compositor;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.MembroDocScree;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

@SuppressWarnings({ "rawtypes", "unchecked" })
@org.springframework.stereotype.Component
@Scope("session")
public class ArtefatoPreenchidoCompositor extends
		SuperArtefatoCompositor<ArtefatoControle> {
	
	@AtributoVisao(isCampoEntidade=false, nome="artefatoPreenchidoAberto")
	private ArtefatoPreenchido artefatoPreenchidoAberto;
	@AtributoVisao(isCampoEntidade=false, nome="gerarRevisao")
	private Boolean gerarRevisao = Boolean.TRUE;
	@AtributoVisao(isCampoEntidade=false, nome="mapaValores")
	private Map<String, Object> mapaValores;
	
	protected AnnotateDataBinder binderVisualizao;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7603950653212496809L;

	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperArtefatoCompositor#carregarArtefato()
	 */
	@Override
	public boolean carregarArtefato() {
		this.setArtefatoPreenchidoAberto(null);
		this.setGerarRevisao(Boolean.TRUE);
		if (Executions.getCurrent().getSession().hasAttribute("ArtefatoPreenchidoAbrir")) {
			this.setArtefatoPreenchidoAberto((ArtefatoPreenchido) Executions.getCurrent().getSession().getAttribute("ArtefatoPreenchidoAbrir"));
			Executions.getCurrent().getSession().removeAttribute("ArtefatoPreenchidoAbrir");
			Executions.getCurrent().getSession().setAttribute("ArtefatoAbrir", getControle().obterArtefatoModelo(getArtefatoPreenchidoAberto()));
		}
		if( !super.carregarArtefato()) {
			getControle().setMensagens(new Mensagens());
			getControle().getMensagens().getListaMensagens().add("É necessário escolher um ArtefatoModelo para preencher!");
			getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			mostrarMensagem(false);
		}
		return true;
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

	@Override
	protected void inicializarTelasMapeadores() {
		this.areaWindowArtefato = null;
		setMapaMembrosAdicionados(new HashMap<String, MembroDocScree>());
	}

	@Override
	protected void lancarMembroAVisualizacao(boolean novo,
			boolean abrindoArtefato) {
		adicionarComponenteAoArtefato(getTipoMembroVisaoSelecionado(), getIdMembro(), novo, abrindoArtefato);
		setTipoMembroVisaoSelecionado(null);
		super.binder.loadAll();
	}
	
	public void acaoMapearArtefato() {
		super.binder.saveAll();
		try {
			setMapaValores(new HashMap<String, Object>());
			for (MembroDocScree membroDocScree : getMapaMembrosAdicionados().values()) {
				Object valorVisualizacao = membroDocScree.getTipoMembroVisao().getValorVisualizacao(getComponentePorId(membroDocScree.getIdComponente(), getWindowArtefato()));
				getMapaValores().put(membroDocScree.getIdComponente(), valorVisualizacao);
			}
			boolean valido = getControle().fazerAcao("chamarServicoValidacao",  (SuperCompositor) this);
			if (valido) {
				boolean acao = getControle().fazerAcao("chamarServicoPersistencia", (SuperCompositor) this);
				if (acao) {
					getComponent().getChildren().clear();
					// TODO redirecionar para index
				} else {
					mostrarMensagem(acao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abrirWindowVisualizacao() {
		getWindowVisualizacaoArtefato().getChildren().clear();
		for (Membro membro : getMembros()) {
			// TODO criar método único ver GERARPALETATIPOMEMBRO..
			SuperTipoMembroVisaoZK<?> superTipoMembroVisaoZK = getControle().getTipoMembroVisaoPeloMembro(membro);
			SuperTipoMembroVisaoZK<?> novaInstancia = null;
			try {
				novaInstancia = superTipoMembroVisaoZK.getClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (novaInstancia != null) {
				novaInstancia.setMembro(membro);
				setTipoMembroVisaoSelecionado(novaInstancia);
				adicionarMembroAVisualizacao(getIdMembro());// TODO se precisar analisar
				setTipoMembroVisaoSelecionado(null);
				
			}
		}
		getBinderVisualizacaoArtefato().loadAll();
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperArtefatoCompositor#gerarNovaInstanciaVisualizacao(java.lang.String, br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	@Override
	protected HtmlBasedComponent gerarNovaInstanciaVisualizacao(
			String idComponente, Membro membro) {
		HtmlBasedComponent div = super.gerarNovaInstanciaVisualizacao(idComponente, membro);
		getTipoMembroVisaoSelecionado().setValor(getTipoMembroVisaoSelecionado().getValorVisualizacao(getWindowArtefato().getFellow(idComponente.replace("Visualizacao", "Componente"))));
		return div;
	}

	protected AnnotateDataBinder getBinderVisualizacaoArtefato() {
		if (this.binderVisualizao == null) {
			this.binderVisualizao = new AnnotateDataBinder(getWindowVisualizacaoArtefato());
		}
		return this.binderVisualizao;
	}
	
	public Boolean getCheckboxVisible() {
		return this.getArtefatoPreenchidoAberto() != null;
	}

	/**
	 * @return ArtefatoPreenchido o(a) artefatoPreenchidoAberto
	 */
	public ArtefatoPreenchido getArtefatoPreenchidoAberto() {
		return artefatoPreenchidoAberto;
	}

	/**
	 * @param artefatoPreenchidoAberto o(a) artefatoPreenchidoAberto a ser setado(a)
	 */
	public void setArtefatoPreenchidoAberto(ArtefatoPreenchido artefatoPreenchidoAberto) {
		this.artefatoPreenchidoAberto = artefatoPreenchidoAberto;
	}

	/**
	 * @return Boolean o(a) gerarRevisao
	 */
	public Boolean getGerarRevisao() {
		return gerarRevisao;
	}

	/**
	 * @param gerarRevisao o(a) gerarRevisao a ser setado(a)
	 */
	public void setGerarRevisao(Boolean gerarRevisao) {
		this.gerarRevisao = gerarRevisao;
	}

	/**
	 * @return Map<String,Object> o(a) mapaValores
	 */
	public Map<String, Object> getMapaValores() {
		return mapaValores;
	}

	/**
	 * @param mapaValores o(a) mapaValores a ser setado(a)
	 */
	public void setMapaValores(Map<String, Object> mapaValores) {
		this.mapaValores = mapaValores;
	}

}
