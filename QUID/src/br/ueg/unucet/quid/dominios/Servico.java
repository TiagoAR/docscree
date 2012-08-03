package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Versionavel;

/**Classe que representa um servico par o framework
 * @author QUID
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="servico")
public class Servico extends Versionavel {
	
	/**
	 * Atributo que identifica se um servico esta ativo ou inativo no framework.
	 */
	private Boolean ativo;
	/**
	 * Atributo que identifica se um servico esta no status de aguardando remocao
	 */
	private Boolean aguardandoRemocao;
	
	//GETTES AND SETTERS
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getAguardandoRemocao() {
		return aguardandoRemocao;
	}
	public void setAguardandoRemocao(Boolean aguardandoRemocao) {
		this.aguardandoRemocao = aguardandoRemocao;
	}
	
		
}
