package br.ueg.unucet.quid.dominios;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Entidade que representa um usuario no sistema.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
public class Usuario extends Persistivel {

	/**
	 * Nome do usuario.
	 */
	private String nome;
	/**
	 * Senha de acesso ao framework.
	 */
	private String senha;
	/**
	 * Email do usuario.
	 */
	private String email;
	/**
	 * Status do usuario no sistema (ativo, inativo)
	 */
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	/**
	 * Atributo que informa o perfil de acesso do usuario no sistema.
	 */
	@Enumerated(EnumType.STRING)
	private PerfilAcessoEnum perfilAcesso;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="usuario")
	private Set<EquipeUsuario> equipeUsuarios;

	public Usuario() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PerfilAcessoEnum getPerfilAcesso() {
		return perfilAcesso;
	}

	public void setPerfilAcesso(PerfilAcessoEnum perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
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