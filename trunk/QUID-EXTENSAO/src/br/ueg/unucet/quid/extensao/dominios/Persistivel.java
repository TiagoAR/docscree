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

}
