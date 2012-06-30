package br.ueg.unucet.quid.extensao.dominios;

import javax.persistence.MappedSuperclass;

import br.ueg.unucet.quid.extensao.interfaces.IIdentificavel;

/**
 * Classe que implementa a interface IIdentificavel do framework.
 * @author QUID
 *
 */
@MappedSuperclass
public class Identificavel extends Persistivel implements IIdentificavel{

	/**
	 * Nome da classe Identificavel.
	 */
	private String nome;
	/**
	 * Descricao da classe Identificavel.
	 */
	private String descricao;
	
	@Override
	public String getNome() {
		return this.nome;
	}

	@Override
	public String getDescricao() {
		return this.descricao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
