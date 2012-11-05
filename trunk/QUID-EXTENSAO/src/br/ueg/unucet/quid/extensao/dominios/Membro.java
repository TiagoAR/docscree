package br.ueg.unucet.quid.extensao.dominios;

import javax.persistence.Transient;

import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

/**
 * Classe que representa o Membro dentro do framework. O membro e a intancia do TIpoMembro com seus atributos preenchidos acrescido as informacoes de posicionamento.
 * @author QUID
 *
 */
@SuppressWarnings("serial")
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Membro: " + getNome();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getNome() == null) ? 0 : getNome().hashCode());
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
		if (!(obj instanceof Membro))
			return false;
		Membro other = (Membro) obj;
		if (getNome() == null) {
			if (other.getNome() != null)
				return false;
		} else if (!getNome().equals(other.getNome()))
			return false;
		return true;
	}
	
}
