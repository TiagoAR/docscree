package br.ueg.unucet.quid.extensao.dominios;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

/**
 * Classe que representa o Membro dentro do framework. O membro e a intancia do TIpoMembro com seus atributos preenchidos acrescido as informacoes de posicionamento.
 * @author QUID
 *
 */
public class Membro extends Identificavel{
	
	/**
	 * TipoMembroModelo que representa o Membro.
	 */
	private ITipoMembroModelo tipoMembroModelo;
	/**
	 * Posicao X do membro na interface.
	 */
	@Transient
	private Integer x;
	/**
	 * Posicao Y do membro na interface.
	 */
	@Transient
	private Integer y;
	/**
	 * Comprimento do componente em que o membro esta inserido.
	 */
	@Transient
	private Integer comprimento;
	/**
	 * Altura do componente em que o membro esta inserido.
	 */
	@Transient	
	private Integer altura;
	

	public ITipoMembroModelo getTipoMembroModelo() {
		return tipoMembroModelo;
	}

	public void setTipoMembroModelo(ITipoMembroModelo tipoMembroModelo) {
		this.tipoMembroModelo = tipoMembroModelo;
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
