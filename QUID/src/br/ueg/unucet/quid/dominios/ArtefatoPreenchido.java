package br.ueg.unucet.quid.dominios;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Identificavel;

/**
 * POJO que representa o ArtefatoModelo com seus valores a ser persistido como
 * ArtefatoPreenchido no Servi�o de Persist�ncia
 * 
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="artefato_preenchido")
public class ArtefatoPreenchido extends Identificavel {
	
	/**
	 * ID do Artefato
	 */
	private Long artefato;
	/**
	 * ID do Modelo
	 */
	private Long modelo;
	/**
	 * ID do usu�rio que solicitou a a��o
	 */
	private Long usuario;
	/**
	 * Vers�o do ArtefatoPreenchido
	 */
	private Integer versao;
	/**
	 * Revis�o do ArtefatoPreenchido
	 */
	private Integer revisao;
	
	/**
	 * Cole��o de Valores dos Membros associados ao Artefato
	 */
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval= true)
	@JoinColumn(name="id_preenchimento", nullable=false)
	private Collection<ValoresArtefato> valoresArtefatos;

	/**
	 * @return the artefato
	 */
	public Long getArtefato() {
		return artefato;
	}

	/**
	 * @param artefato
	 *            the artefato to set
	 */
	public void setArtefato(Long artefato) {
		this.artefato = artefato;
	}

	/**
	 * @return the modelo
	 */
	public Long getModelo() {
		return modelo;
	}

	/**
	 * @param modelo
	 *            the modelo to set
	 */
	public void setModelo(Long modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the usuario
	 */
	public Long getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the versao
	 */
	public Integer getVersao() {
		return versao;
	}

	/**
	 * @param versao
	 *            the versao to set
	 */
	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	/**
	 * @return the revisao
	 */
	public Integer getRevisao() {
		return revisao;
	}

	/**
	 * @param revisao
	 *            the revisao to set
	 */
	public void setRevisao(Integer revisao) {
		this.revisao = revisao;
	}

	/**
	 * @return the valoresArtefatos
	 */
	public Collection<ValoresArtefato> getValoresArtefatos() {
		return valoresArtefatos;
	}

	/**
	 * @param valoresArtefatos the valoresArtefatos to set
	 */
	public void setValoresArtefatos(Collection<ValoresArtefato> valoresArtefatos) {
		this.valoresArtefatos = valoresArtefatos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getNome() + " - Vers�o: " + getVersao();
	}

}
