package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Identificavel;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Entidade que representa um projeto dentro do framework.
 * 
 * @author QUID
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "projeto")
public class Projeto extends Identificavel {
	/**
	 * Modelo base para a o preenchimento do projeto.
	 */
	@ManyToOne()
	private Modelo modelo;
	/**
	 * Equipe que pode realizar o preenchimento do projeto.
	 */
	@ManyToOne()
	private Equipe equipe;
	/**
	 * Status do projeto(ativo, inativo)
	 */
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	// GETTERS AND SETTERS
	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
