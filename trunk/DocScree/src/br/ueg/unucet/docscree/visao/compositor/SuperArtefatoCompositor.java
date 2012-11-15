package br.ueg.unucet.docscree.visao.compositor;

import java.util.Collection;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;

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
	protected static final String ESTILODIV = " position: absolute; display: table; ";
	protected static final String ESTILOCOMPONENTE = " padding: 0px; margin: 0px; padding-top: 1px;";

	@AtributoVisao(nome="nome", isCampoEntidade = true)
	private String nome;
	@AtributoVisao(nome="descricao", isCampoEntidade = true)
	private String descricao;
	@AtributoVisao(nome="categoria", isCampoEntidade = true)
	private Categoria categoria = null;
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

}
