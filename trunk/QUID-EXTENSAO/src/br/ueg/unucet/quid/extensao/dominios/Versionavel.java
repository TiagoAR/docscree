package br.ueg.unucet.quid.extensao.dominios;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import br.ueg.unucet.quid.extensao.interfaces.IVersionavel;

/**
 * Classe que implementa a interface IVersionavel.
 * @author QUID
 *
 */
@MappedSuperclass
public class Versionavel extends Identificavel implements IVersionavel{
	
	/**
	 * Atributo que identifica a versao.
	 */
	@Column(nullable=false)
	private Integer versao;
	/**
	 * Atributo que identifica a revisao.
	 */
	@Column(nullable=false)
	private Integer revisao;
	
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
	
	

}
