package br.ueg.unucet.docscree.visao.compositor;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Executions;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.MembroDocScree;

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
