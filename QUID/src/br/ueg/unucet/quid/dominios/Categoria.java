package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Entidade que representa a categoria do artefato.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="categoria")
public class Categoria extends Persistivel {
	
	/**
	 * Descricao da categoria.
	 */
	private String descricao;
	
	//GETTERS AND SETTERS

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
