package br.ueg.unucet.quid.dominios;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ueg.unucet.quid.extensao.dominios.Identificavel;

/**
 * Entidade que representa um modelo dentro do framework.
 * @author QUID
 *
 */
@Entity
@Table(name="modelo")
public class Modelo extends Identificavel{
	
	/**
	 * Lista de itemmodelo que compoem o modelo.
	 */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="modelo_codigo", nullable=false)
	private Collection<ItemModelo> itemModelo;

	//GETTERS AND SETTERS
	public Collection<ItemModelo> getItemModelo() {
		return itemModelo;
	}

	public void setItemModelo(Collection<ItemModelo> itemModelo) {
		this.itemModelo = itemModelo;
	}
	
	
}
