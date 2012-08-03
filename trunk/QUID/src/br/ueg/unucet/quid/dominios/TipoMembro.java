package br.ueg.unucet.quid.dominios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.ueg.unucet.quid.enums.TipoTipoMembroEnum;
import br.ueg.unucet.quid.extensao.dominios.Versionavel;

/**
 * Classe que representa a abstracao do TipoMembro no framework.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tipo_membro")
public class TipoMembro extends Versionavel{
	
	/**
	 * Atributo que informa se um TipoMembro e modelo ou visao.
	 */
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private TipoTipoMembroEnum tipoTipoMembro;
	/**
	 * Atributo que informa se um TipoMembro esta ativo ou inativo no framework.
	 */
	private Boolean ativo;
	/**
	 * Abributo que informa se um TipoMembro esta no status de aguardando remocao.
	 */
	private Boolean aguardandoExclusao;
	
	//GETTES AND SETTERS
	public TipoTipoMembroEnum getTipoTipoMembro() {
		return tipoTipoMembro;
	}
	public void setTipoTipoMembro(TipoTipoMembroEnum tipoTipoMembro) {
		this.tipoTipoMembro = tipoTipoMembro;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getAguardandoExclusao() {
		return aguardandoExclusao;
	}
	public void setAguardandoExclusao(Boolean aguardandoExclusao) {
		this.aguardandoExclusao = aguardandoExclusao;
	}
	
	
	
	
}
