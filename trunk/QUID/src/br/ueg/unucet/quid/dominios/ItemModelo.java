package br.ueg.unucet.quid.dominios;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ueg.unucet.quid.anotations.SemComponente;
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
	@SemComponente
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
	@SemComponente
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
	
	/**
	 * Bytes dos parametros do ITipoMembro.
	 */
	private byte[] parametros;
	
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
	/**
	 * @return the parametros
	 */
	public byte[] getParametros() {
		return parametros;
	}
	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(byte[] parametros) {
		this.parametros = parametros;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((artefato == null) ? 0 : artefato.hashCode());
		result = prime * result + ((getCodigo() == null) ? 0 : getCodigo().hashCode());
		result = prime * result + ((grau == null) ? 0 : grau.hashCode());
		result = prime * result
				+ ((multiplicidade == null) ? 0 : multiplicidade.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		result = prime * result
				+ ((ordemPai == null) ? 0 : ordemPai.hashCode());
		result = prime
				* result
				+ ((ordemPreenchimento == null) ? 0 : ordemPreenchimento
						.hashCode());
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
		if (!(obj instanceof ItemModelo))
			return false;
		ItemModelo other = (ItemModelo) obj;
		if (getCodigo() != null && !getCodigo().equals(new Long(0)) 
				&& other.getCodigo() != null && !other.getCodigo().equals(new Long(0))) {
			if (getCodigo().equals(other.getCodigo())) {
				return true;
			} else {
				return false;
			}
		}
		if (artefato == null) {
			if (other.artefato != null)
				return false;
		} else if (!artefato.equals(other.artefato))
			return false;
		if (getCodigo() == null) {
			if (other.getCodigo() != null)
				return false;
		} else if (!getCodigo().equals(other.getCodigo()))
			return false;
		if (grau == null) {
			if (other.grau != null)
				return false;
		} else if (!grau.equals(other.grau))
			return false;
		if (multiplicidade != other.multiplicidade)
			return false;
		if (ordem == null) {
			if (other.ordem != null)
				return false;
		} else if (!ordem.equals(other.ordem))
			return false;
		if (ordemPai == null) {
			if (other.ordemPai != null)
				return false;
		} else if (!ordemPai.equals(other.ordemPai))
			return false;
		if (ordemPreenchimento == null) {
			if (other.ordemPreenchimento != null)
				return false;
		} else if (!ordemPreenchimento.equals(other.ordemPreenchimento))
			return false;
		return true;
	}
	
	
	

}
