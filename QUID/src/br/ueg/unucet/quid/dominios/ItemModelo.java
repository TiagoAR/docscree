package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.enums.MultiplicidadeEnum;

/**
 * Entidade que representa um item de um modelo.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="item_modelo")
public class ItemModelo extends Persistivel{
	
	/**
	 * Artefato que compoem o item do modelo.
	 */
	@ManyToOne
	private Artefato artefato;
	/**
	 * Grau do item modelo no modelo.
	 */
	private Integer grau;
	/**
	 * Ordem o item modelo no modelo.
	 */
	private Integer ordem;
	/**
	 * Ordem o item modelo pai a este item modelo. 0 caso o item nao possuia pai.
	 */
	private Integer ordemPai;
	/**
	 * Multiplicidade do item modelo no modelo (um, muitos).
	 */
	@Enumerated(EnumType.STRING)
	private MultiplicidadeEnum multiplicidade;
	/**
	 * Ordem de preenchimento do itemmodelo no modelo.
	 */
	private Integer ordemPreenchimento;
	
	//GETTERS AND SETTERS
	public Artefato getArtefato() {
		return artefato;
	}
	public void setArtefato(Artefato artefato) {
		this.artefato = artefato;
	}
	public Integer getGrau() {
		return grau;
	}
	public void setGrau(Integer grau) {
		this.grau = grau;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public MultiplicidadeEnum getMultiplicidade() {
		return multiplicidade;
	}
	public void setMultiplicidade(MultiplicidadeEnum multiplicidade) {
		this.multiplicidade = multiplicidade;
	}
	public Integer getOrdemPreenchimento() {
		return ordemPreenchimento;
	}
	public void setOrdemPreenchimento(Integer ordemPreenchimento) {
		this.ordemPreenchimento = ordemPreenchimento;
	}
	public Integer getOrdemPai() {
		return ordemPai;
	}
	public void setOrdemPai(Integer ordemPai) {
		this.ordemPai = ordemPai;
	}
	
	

}
