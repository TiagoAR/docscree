package br.ueg.unucet.quid.dominios;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Entidade que representa uma equipe dentro do framework.
 * @author QUID
 *
 */
@Entity
@Table(name="equipe")
public class Equipe extends Persistivel{
	
	/**
	 * Nome da equipe.
	 */
	private String nome;
	/**
	 * Lista de usuarios que pertence a equipe.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "equipe_usuario", joinColumns = @JoinColumn(name = "equipe_codigo"), inverseJoinColumns = @JoinColumn(name = "usuario_codigo"))
	private Collection<Usuario> usuarios;
	
	//GETTERS AND SETTERS
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
	
}
