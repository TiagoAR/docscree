package br.ueg.unucet.quid.extensao.dominios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import br.ueg.unucet.quid.extensao.interfaces.IPersistivel;

/**
 * Classe que implementa a Inteface IPersistivel.
 * @author QUID
 *
 */
@MappedSuperclass
public class Persistivel implements IPersistivel<Long>{
	/**
	 * Codigo de identificacao.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@Override
	public Long getCodigo() {
		return this.codigo;
	}

	@Override
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCodigo() == null) ? 0 : getCodigo().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Persistivel)) {
			return false;
		}
		Persistivel other = (Persistivel) obj;
		if (getCodigo() == null) {
			if (other.getCodigo() != null) {
				return false;
			}
		} else if (!getCodigo().equals(other.getCodigo())) {
			return false;
		}
		return true;
	}
	
	

}
