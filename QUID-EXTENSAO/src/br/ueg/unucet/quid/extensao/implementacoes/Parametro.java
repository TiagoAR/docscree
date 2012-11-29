package br.ueg.unucet.quid.extensao.implementacoes;

import java.lang.reflect.Constructor;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Classe que identifica um parametro para o TipoMembro do framework.
 * @author QUID
 *
 * @param <T> Classe do parametro.
 */
@SuppressWarnings("serial")
public class Parametro<T> implements IParametro<T> {

	/**
	 * Nome do parametro.
	 */
	private String nome;
	/**
	 * Valor do parametro.
	 */
	private T valor;
	/**
	 * Classe que representa o parametro.
	 */
	private Class<T> classe;
	/**
	 * Dominio de entrada do parametro.
	 */
	private DominioEntradaEnum dominioEntrada;
	/**
	 * Lista de dominio de entrada. Caso seje informada, ela sera mostrada na tela para o usuario selecionar.
	 */
	private Collection<String> listaDominioTipo;
	/**
	 * Informa se o campo e obrigatorio.
	 */
	private boolean obrigatorio;
	/**
	 * Rotulo do parametro na paleta de visualizacao.
	 */
	private String rotulo;

	public Parametro(Class<T> classe) {
		this.classe = classe;
	}

	@Override
	public String getNome() {
		return this.nome;
	}

	@Override
	public T getValor() {
		return this.valor;
	}

	@Override
	public String getValorString() {
		return recast(getValor());
	}

	@Override
	public boolean isObrigatorio() {
		return this.obrigatorio;
	}

	@Override
	public Collection<String> getListaDominioTipo() {
		return this.listaDominioTipo;
	}

	@Override
	public DominioEntradaEnum getDominioEntrada() {
		return this.dominioEntrada;
	}

	public void setListDominioTipo(Collection<String> listaDominioTipo) {
		this.listaDominioTipo = listaDominioTipo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDominioEntrada(DominioEntradaEnum dominioEntrada) {
		this.dominioEntrada = dominioEntrada;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dominioEntrada == null) ? 0 : dominioEntrada.hashCode());
		result = prime * result + ((listaDominioTipo == null) ? 0 : listaDominioTipo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (obrigatorio ? 1231 : 1237);
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Parametro other = (Parametro) obj;
		if (dominioEntrada != other.dominioEntrada)
			return false;
		if (listaDominioTipo == null) {
			if (other.listaDominioTipo != null)
				return false;
		} else if (!listaDominioTipo.equals(other.listaDominioTipo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (obrigatorio != other.obrigatorio)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String getRotulo() {
		return this.rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	@Override
	public void setValor(String valor) {
		this.valor = cast(valor);

	}

	/**
	 * Metodo responsavel por realizar o cast do valor string informado pelo usuario para o tipo do parametro.
	 * @param valor2 Valor a ser feito o cast.
	 * @return Objeto do valor a ser feito o cast.
	 */
	@SuppressWarnings("unchecked")
	protected T cast(String valor2) {
		T valorSetar = null;
		try {
			Constructor<?> construtor = classe.getConstructor(String.class);
			valorSetar = (T) construtor.newInstance(valor2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valorSetar;
	}

	protected String recast(T valor) {
		if (valor != null)
			return valor.toString();
		else
			return "";
	}

	// GETTERS SETTERS
	public Class<T> getClasse() {
		return classe;
	}

	public void setClasse(Class<T> classe) {
		this.classe = classe;
	}

	@Override
	public void setValorClass(T valor) {
		this.valor = valor;
	}
}
