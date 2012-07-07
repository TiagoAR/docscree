package br.ueg.unucet.quid.dominios;

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
 * Tabela relacionamento entre equipe e usuário
 * 
 * @author Diego
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "equipe_usuario")
public class EquipeUsuario extends Persistivel {
	
	/**
	 * A equipe que é relacionada
	 */
	@ManyToOne(fetch = FetchType.EAGER)  
	@JoinColumn(name = "equipe_codigo", nullable = false)
	private Equipe equipe;
	/**
	 * O usuário que é relacionado
	 */
	@ManyToOne(fetch = FetchType.EAGER)  
	@JoinColumn(name = "usuario_codigo", nullable = false)
	private Usuario usuario;
	/**
	 * Papel que o usuário desempenha na equipe
	 */
	@Enumerated(EnumType.STRING)
	private PapelUsuario papelUsuario;
	
	public EquipeUsuario() {
		this.equipe = new Equipe();
		this.usuario = new Usuario();
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipeUsuario other = (EquipeUsuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
