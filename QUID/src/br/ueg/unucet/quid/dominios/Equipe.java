package br.ueg.unucet.quid.dominios;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Entidade que representa uma equipe dentro do framework.
 * 
 * @author QUID
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "equipe")
public class Equipe extends Persistivel {

	/**
	 * Nome da equipe.
	 */
	private String nome;
	/**
	 * Status da equipe no sistema (ativo, inativo)
	 */
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	/**
	 * Lista de equipe usuarios que pertence a equipe.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="equipe")
	private Set<EquipeUsuario> equipeUsuarios;

	// GETTERS AND SETTERS
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	/**
	 * @return the equipeUsuarios
	 */
	public Set<EquipeUsuario> getEquipeUsuarios() {
		return equipeUsuarios;
	}

	/**
	 * @param equipeUsuarios the equipeUsuarios to set
	 */
	public void setEquipeUsuarios(Set<EquipeUsuario> equipeUsuarios) {
		this.equipeUsuarios = equipeUsuarios;
	}
}
