package br.ueg.unucet.docscree.visao.compositor;

import java.util.Collection;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.dominios.MembroDocScree;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class SuperArtefatoCompositor<E extends ArtefatoControle> extends GenericoCompositor<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8004333841881889693L;
	protected static final Integer LARGURA_MAXIMA = 800;
	protected static final String WIDTH = "140px;";
	protected static final String PARAMETROALTURA = "idParametroAltura";
	protected static final String PARAMETROCOMPRIMENTO = "idParametroComprimento";
	protected static final String PARAMETROPOSX = "idParametroX";
	protected static final String PARAMETROPOSY = "idParametroY";
	protected static final String PARAMETRONOME = "idParametroNome";
	protected static final String PARAMETRODESC = "idParametroDescricao";
	protected static final String ESTILODIV = " position: absolute; display: table ";
	protected static final String ESTILOCOMPONENTE = " padding: 0px; margin: 0px; padding-top: 1px;";

	/**
	 * Nome do Artefato
	 */
	@AtributoVisao(nome="nome", isCampoEntidade = true)
	private String nome;
	/**
	 * Descrição do Artefato
	 */
	@AtributoVisao(nome="descricao", isCampoEntidade = true)
	private String descricao;
	/**
	 * Categoria do ARtefato
	 */
	@AtributoVisao(nome="categoria", isCampoEntidade = true)
	private Categoria categoria = null;
	/**
	 * Título do Artefato
	 */
	@AtributoVisao(nome="titulo", isCampoEntidade = true)
	private String titulo;
	/**
	 * Lista de Membros do Artefato
	 */
	@AtributoVisao(nome="membros", isCampoEntidade = true)
	private Collection<Membro> membros;
	/**
	 * Lista de Serviços do ARtefato
	 */
	@AtributoVisao(nome="servicos", isCampoEntidade = true)
	private Collection<IServico> servicos;
	/**
	 * instancia do Controlador que fica dentro do Artefato
	 */
	@AtributoVisao(nome="artefatoControle", isCampoEntidade = true)
	private IArtefatoControle<Artefato, Long> artefatoControle;
	/**
	 * Altura do artefato
	 */
	@AtributoVisao(nome="altura", isCampoEntidade = true)
	private int altura;
	/**
	 * largura do artefato
	 */
	@AtributoVisao(nome="largura", isCampoEntidade = true)
	private int largura;
	
	/**
	 * Mapa dos TipoMembro-visão mapeados ao Artefato
	 */
	@AtributoVisao(nome="listaMembrosDocScree", isCampoEntidade = false)
	protected Map<String, MembroDocScree> mapaMembrosAdicionados;
	/**
	 * TipoMembro-visão selecionado
	 */
	@AtributoVisao(nome="tipoMembroVisaoSelecionado", isCampoEntidade = false)
	protected SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado;

	/**
	 * Área de montagem - preenchimento
	 */
	protected Window areaWindowArtefato = null;
	/**
	 * Área de montagem - visuazliação
	 */
	protected Window areaVisualizacaoWindow = null;
	
	/**
	 * Mapea Membro ao Artefato
	 * 
	 * @return
	 */
	public boolean mapearMembrosAoArtefato() {
		this.inicializarTelasMapeadores();
		boolean retorno = true;
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
				lancarMembroAVisualizacao(true, true);
			} else {
				retorno = false;
				break;
			}
		}
		return retorno;
	}
	
	/**
	 * Método que carrega a tela de Artefato, abrindo um ou limpando para um novo
	 * @return
	 */
	public boolean carregarArtefato() {
		boolean retorno = false;
		if (Executions.getCurrent().getSession().hasAttribute("ArtefatoAbrir")) {
			setEntidade((Persistivel) Executions.getCurrent().getSession().getAttribute("ArtefatoAbrir"));
			Executions.getCurrent().getSession().removeAttribute("ArtefatoAbrir");
			try {
				executarAbrirArtefato();
			} catch (Exception e) {
				e.printStackTrace();
			}
			retorno = true;
		}
		super.binder.loadAll();
		return retorno;
	}
	
	/**
	 * Chama método do controlador para mapear os dados do Artefato sobre a visão
	 * 
	 * @throws Exception
	 */
	protected void executarAbrirArtefato() throws Exception {
		getControle().fazerAcao("abrirArtefato", (SuperCompositor) this);
	}
	
	/**
	 * Adiciona TipoMembro-visão a tela de preenchimento
	 * 
	 * @param tipoMembro
	 * @param nomeParcial
	 * @param isNovo
	 * @param abrindoArtefato
	 */
	protected void adicionarComponenteAoArtefato(SuperTipoMembroVisaoZK<?> tipoMembro, String nomeParcial, boolean isNovo, boolean abrindoArtefato) {
		String idComponente = "Componente" + nomeParcial;
		HtmlBasedComponent novaInstancia = gerarNovaInstancia(idComponente, tipoMembro.getMembro());
		if (novaInstancia != null) {
			if (isNovo) {
				this.getMapaMembrosAdicionados().put(idComponente, new MembroDocScree(tipoMembro, idComponente));
			} else {
				this.getMapaMembrosAdicionados().get(idComponente).setTipoMembroVisao(tipoMembro);
				getMembros().remove(tipoMembro.getMembro());
			}
			if (!abrindoArtefato) {
				this.getMembros().add(tipoMembro.getMembro());
			} else {
				tipoMembro.setValor(tipoMembro.getMembro().getTipoMembroModelo().getValor());
			}
			novaInstancia.setParent(getWindowArtefato());
			getWindowArtefato().appendChild(novaInstancia);
		}
	}
	
	/**
	 * Adiciona TipoMembro-visão selecionado a tela de visualização
	 * @param nomeParcial
	 */
	protected void adicionarMembroAVisualizacao(String nomeParcial) {
		String idVisualizacao = "Visualizacao" + nomeParcial;
		HtmlBasedComponent componenteVisualizacao = gerarNovaInstanciaVisualizacao(idVisualizacao, getTipoMembroVisaoSelecionado().getMembro());
		if (componenteVisualizacao != null) {
			componenteVisualizacao.setParent(getWindowVisualizacaoArtefato());
			getWindowVisualizacaoArtefato().appendChild(componenteVisualizacao);
		} 
	}

	/**
	 * 
	 * @param idComponente
	 * @param membro
	 * @return novaInstancia do TipoMembro-visão para preenchimento a ser adicionado
	 */
	protected HtmlBasedComponent gerarNovaInstancia(String idComponente, Membro membro) {
		Div div = null;
		try {
			HtmlBasedComponent novaInstancia = (HtmlBasedComponent) getTipoMembroVisaoSelecionado().getVisaoPreenchimento();
			novaInstancia.setId(idComponente);
			novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getCss(membro) + ESTILOCOMPONENTE);
			div = new Div();
			div.setId(idComponente.replace("Componente", "Grid"));
			div.setWidth(String.valueOf(membro.getComprimento())+"px");
			div.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + ESTILODIV);
			novaInstancia.setParent(div);
			div.appendChild(novaInstancia);
		} catch (Exception e) {
		}
		return div;
	}

	/**
	 * @param idComponente
	 * @param membro
	 * @return novaInstancia do TipoMembro-visão para visualização a ser adicionado
	 */
	protected HtmlBasedComponent gerarNovaInstanciaVisualizacao(String idComponente, Membro membro) {
		Div div = null;
		try {
			HtmlBasedComponent novaInstancia = (HtmlBasedComponent) getTipoMembroVisaoSelecionado().getVisaoVisualizacao();
			novaInstancia.setId(idComponente);
			novaInstancia.setStyle(getTipoMembroVisaoSelecionado().getCss(membro) + ESTILOCOMPONENTE);
			div = new Div();
			div.setId(idComponente.replace("Visualizacao", "Grid"));
			div.setWidth(String.valueOf(membro.getComprimento())+"px");
			div.setStyle(getTipoMembroVisaoSelecionado().getPosicionamento(membro, 1) + ESTILODIV);
			div.appendChild(novaInstancia);
		} catch (Exception e) {
		}
		return div;
	}
	
	/**
	 * Método que inicializa os componentes responsáveis por controlar a exibição visão e mapeamento de Membros
	 */
	protected abstract void inicializarTelasMapeadores();
	/**
	 * Método responsável por lançar Membro a área de montagem (visualização e preenchimento)
	 * 
	 */
	protected abstract void lancarMembroAVisualizacao(boolean novo, boolean abrindoArtefato);
	
	/**
	 * @see GenericoCompositor#getTipoEntidade()
	 */
	@Override
	public Class getTipoEntidade() {
		return Artefato.class;
	}
	
	/**
	 * 
	 * @return string css da largura
	 */
	public String getLarguraString() {
		return String.valueOf(getLargura()) + "px"; 
	}
	
	/**
	 * 
	 * @return string css da altura
	 */
	
	public String getAlturaString() {
		return String.valueOf(getAltura()) + "px";
	}

	/**
	 * 
	 * @return areaWindowArtefato
	 */
	protected Window getWindowArtefato() {
		if (this.areaWindowArtefato == null) {
			this.areaWindowArtefato = (Window) getComponent().getFellow(
					"windowArtefato");
		}
		return this.areaWindowArtefato;
	}
	
	/**
	 *  
	 * @return areaVisualizacaoWindow
	 */
	protected Window getWindowVisualizacaoArtefato() {
		if (this.areaVisualizacaoWindow == null) {
			this.areaVisualizacaoWindow = (Window) getComponent().getFellow(
					"windowArtefatoVisualizacao");
		}
		return this.areaVisualizacaoWindow;
	}
	
	/**
	 * 
	 * @return ID do Membro selecionado
	 */
	protected String getIdMembro() {
		return getTipoMembroVisaoSelecionado().getNome()+String.valueOf(getTipoMembroVisaoSelecionado().getMembro().getCodigo());
	}

	//GETTERS AND SETTERS
	/**
	 * @return String o(a) nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome o(a) nome a ser setado(a)
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
	 * @param descricao o(a) descricao a ser setado(a)
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
	 * @param categoria o(a) categoria a ser setado(a)
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
	 * @param titulo o(a) titulo a ser setado(a)
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
	 * @param membros o(a) membros a ser setado(a)
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
	 * @param servicos o(a) servicos a ser setado(a)
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
	 * @param artefatoControle o(a) artefatoControle a ser setado(a)
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
	 * @param altura o(a) altura a ser setado(a)
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
	 * @param largura o(a) largura a ser setado(a)
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
	 * @param mapaMembrosAdicionados o(a) mapaMembrosAdicionados a ser setado(a)
	 */
	public void setMapaMembrosAdicionados(
			Map<String, MembroDocScree> mapaMembrosAdicionados) {
		this.mapaMembrosAdicionados = mapaMembrosAdicionados;
	}

	/**
	 * @return SuperTipoMembroVisaoZK o(a) tipoMembroVisaoSelecionado
	 */
	public SuperTipoMembroVisaoZK getTipoMembroVisaoSelecionado() {
		return tipoMembroVisaoSelecionado;
	}

	/**
	 * @param tipoMembroVisaoSelecionado
	 *            o(a) tipoMembroVisaoSelecionado a ser setado(a)
	 */
	public void setTipoMembroVisaoSelecionado(
			SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado) {
		this.tipoMembroVisaoSelecionado = tipoMembroVisaoSelecionado;
	}

}
