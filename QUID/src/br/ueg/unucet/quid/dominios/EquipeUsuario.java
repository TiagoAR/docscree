package br.ueg.unucet.quid.dominios;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.unucet.quid.enums.PapelUsuario;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Tabela relacionamento entre equipe e usu�rio
 * 
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "equipe_usuario")
public class EquipeUsuario extends Persistivel {
	
	/**
	 * A equipe que � relacionada
	 */
	@ManyToOne(fetch = FetchType.EAGER)  
	@JoinColumn(name = "equipe_codigo", nullable = false)
	private Equipe equipe;
	/**
	 * O usu�rio que � relacionado
	 */
	@ManyToOne(fetch = FetchType.EAGER)  
	@JoinColumn(name = "usuario_codigo", nullable = false)
	private Usuario usuario;
	/**
	 * Papel que o usu�rio desempenha na equipe
	 */
	@Enumerated(EnumType.STRING)
	private PapelUsuario papelUsuario;

	/**
	 * @return the equipe
	 */
	public Equipe getEquipe() {
		return equipe;
	}

	/**
	 * @param equipe
	 *            the equipe to set
	 */
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the papelUsuario
	 */
	public PapelUsuario getPapelUsuario() {
		return papelUsuario;
	}

	/**
	 * @param papelUsuario
	 *            the papelUsuario to set
	 */
	public void setPapelUsuario(PapelUsuario papelUsuario) {
		this.papelUsuario = papelUsuario;
	}

}
