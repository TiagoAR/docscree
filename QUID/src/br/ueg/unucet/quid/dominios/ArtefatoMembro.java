package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Classe que representa o mapeamento ArtefatoMembro no framework.
 * @author QUID
 *
 */
@Entity
@Table(name="artefato_tipomembro")
public class ArtefatoMembro extends Persistivel{
	
	/**
	 * Artefato do mapeamento.
	 */
	@ManyToOne
	private Artefato artefato;
	/**
	 * Membro framework do mapeamento.
	 */
	@ManyToOne
	private MembroFramework membroFramework;
	/**
	 * Posicao x do membro dentro do artefato
	 */
	private Integer x;
	/**
	 * Posicao y do membro dentro do artefato
	 */
	private Integer y;
	/**
	 * Comprimento do conteiner do membro dentro do artefato.
	 */
	private Integer comprimento;
	/**
	 * Altura do conteiner do membro dentro do artefato.
	 */
	private Integer altura;
	
	
	//GETTERS AND SETTERS
	public Artefato getArtefato() {
		return artefato;
	}
	public void setArtefato(Artefato artefato) {
		this.artefato = artefato;
	}
	public MembroFramework getMembroFramework() {
		return membroFramework;
	}
	public void setMembroFramework(MembroFramework membroFramework) {
		this.membroFramework = membroFramework;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getComprimento() {
		return comprimento;
	}
	public void setComprimento(Integer comprimento) {
		this.comprimento = comprimento;
	}
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	
	

}
