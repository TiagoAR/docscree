package br.ueg.unucet.docscree.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.quid.anotations.SemComponente;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.enums.MultiplicidadeEnum;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public class MembroModelo extends Persistivel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5793496649609686591L;
	public static final String PARAMETRO_NOME = "NOME";
	
	/**
	 * Artefato que compoem o item do modelo.
	 */
	@SemComponente
	private Artefato artefato;
	/**
	 * Grau do item modelo no modelo.
	 */
	private Integer grau;
	/**
	 * Ordem o item modelo no modelo.
	 */
	private Integer ordem;
	/**
	 * Ordem o item modelo pai a este item modelo. 0 caso o item nao possuia pai.
	 */
	@SemComponente
	private Integer ordemPai;
	/**
	 * Multiplicidade do item modelo no modelo (um, muitos).
	 */
	private MultiplicidadeEnum multiplicidade;
	/**
	 * Ordem de preenchimento do itemmodelo no modelo.
	 */
	private Integer ordemPreenchimento;
	
	/**
	 * Bytes dos parametros do ITipoMembro.
	 */
	private Collection<IParametro<?>> listaParametros;
	
	public MembroModelo() {
		iniciarParametros();
	}
	
	private void iniciarParametros() {
		List<IParametro<?>> lista = new ArrayList<IParametro<?>>();
		lista.add(getParametroNome());
		setListaParametros(lista);
	}
	
	private IParametro<String> getParametroNome() {
		Parametro<String> parametro = new Parametro<String>(String.class);
		parametro.setNome(PARAMETRO_NOME);
		parametro.setRotulo("Nome");
		parametro.setObrigatorio(true);
		return parametro;
	}
	
	/**
	 * Metodo que pega o parametro a partir de seu nome.
	 * @param nome Nome do parametro.
	 * @return Parametro especificado pelo nome.
	 */
	protected IParametro<?> getParametroPorNome(String nome) {
		IParametro<?> param = null;
		for (IParametro<?> p : getListaParametros()) {
			if (p.getNome().equals(nome)) {
				param = p;
				break;
			}
		}
		return param;
	}

	/**
	 * @return Artefato o(a) artefato
	 */
	public Artefato getArtefato() {
		return artefato;
	}

	/**
	 * @param artefato
	 *            o(a) artefato a ser setado(a)
	 */
	public void setArtefato(Artefato artefato) {
		this.artefato = artefato;
		getParametroPorNome(PARAMETRO_NOME).setValor(this.artefato.getNome());
	}

	/**
	 * @return Integer o(a) grau
	 */
	public Integer getGrau() {
		return grau;
	}

	/**
	 * @param grau
	 *            o(a) grau a ser setado(a)
	 */
	public void setGrau(Integer grau) {
		this.grau = grau;
	}

	/**
	 * @return Integer o(a) ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem
	 *            o(a) ordem a ser setado(a)
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return Integer o(a) ordemPai
	 */
	public Integer getOrdemPai() {
		return ordemPai;
	}

	/**
	 * @param ordemPai
	 *            o(a) ordemPai a ser setado(a)
	 */
	public void setOrdemPai(Integer ordemPai) {
		this.ordemPai = ordemPai;
	}

	/**
	 * @return MultiplicidadeEnum o(a) multiplicidade
	 */
	public MultiplicidadeEnum getMultiplicidade() {
		return multiplicidade;
	}

	/**
	 * @param multiplicidade
	 *            o(a) multiplicidade a ser setado(a)
	 */
	public void setMultiplicidade(MultiplicidadeEnum multiplicidade) {
		this.multiplicidade = multiplicidade;
	}

	/**
	 * @return Integer o(a) ordemPreenchimento
	 */
	public Integer getOrdemPreenchimento() {
		return ordemPreenchimento;
	}

	/**
	 * @param ordemPreenchimento
	 *            o(a) ordemPreenchimento a ser setado(a)
	 */
	public void setOrdemPreenchimento(Integer ordemPreenchimento) {
		this.ordemPreenchimento = ordemPreenchimento;
	}

	/**
	 * @return Collection<IParametro<?>> o(a) listaParametros
	 */
	public Collection<IParametro<?>> getListaParametros() {
		return listaParametros;
	}

	/**
	 * @param listaParametros o(a) listaParametros a ser setado(a)
	 */
	public void setListaParametros(Collection<IParametro<?>> listaParametros) {
		this.listaParametros = listaParametros;
	}

}
