package br.ueg.unucet.quid.extensao.implementacoes;

import java.util.Collection;

import br.ueg.unucet.quid.extensao.dominios.Identificavel;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.IVersionavel;

/**
 * Classe que implementa as operacoes basicas sobre o TipoMembro.
 * @author QUID
 *
 */
public abstract class SuperTipoMembro extends Identificavel implements ITipoMembro, IVersionavel {
	
	/**
	 * Lista de parametros sobre o TipoMembro.
	 */
	private Collection<IParametro<?>> listaParametros;
	/**
	 * Versao do TipoMembro.
	 */
	private Integer versao;
	/**
	 * Revisao do TipoMembro.
	 */
	private Integer revisao;
	/**
	 * Valor a ser armazenado do TipoMembro.
	 */
	private Object valor;
	
	public Collection<IParametro<?>> getListaParametros(){
		return this.listaParametros;
	}
	
	public void setListaParametros(Collection<IParametro<?>> parametros){
		this.listaParametros = parametros;
	}
	
	@Override
	public Integer getVersao() {
		return this.versao;
	}

	@Override
	public Integer getRevisao() {
		return this.revisao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public void setRevisao(Integer revisao) {
		this.revisao = revisao;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}
	
	
	
	
}
