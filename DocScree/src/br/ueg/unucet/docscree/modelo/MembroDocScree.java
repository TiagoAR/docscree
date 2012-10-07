package br.ueg.unucet.docscree.modelo;

import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

/**
 * 
 * 
 * @author Diego
 *
 */
public class MembroDocScree {

	//TODO Tirar membro e jogar para a visão.
	private SuperTipoMembroVisaoZK<?> tipoMembroVisao;
	private Membro membroQUID;
	private String idComponente;
	
	public MembroDocScree() {
	}
	
	public MembroDocScree(SuperTipoMembroVisaoZK<?> tipoMembroVisao, Membro membro, String idComponente) {
		this.tipoMembroVisao = tipoMembroVisao;
		this.membroQUID = membro;
		this.idComponente = idComponente;
	}

	/**
	 * @return SuperTipoMembroVisaoZK<?> o(a) tipoMembroVisao
	 */
	public SuperTipoMembroVisaoZK<?> getTipoMembroVisao() {
		return tipoMembroVisao;
	}

	/**
	 * @param SuperTipoMembroVisaoZK
	 *            <?> o(a) tipoMembroVisao a ser setado(a)
	 */
	public void setTipoMembroVisao(SuperTipoMembroVisaoZK<?> tipoMembroVisao) {
		this.tipoMembroVisao = tipoMembroVisao;
	}

	/**
	 * @return Membro o(a) membroQUID
	 */
	public Membro getMembroQUID() {
		return membroQUID;
	}

	/**
	 * @param Membro
	 *            o(a) membroQUID a ser setado(a)
	 */
	public void setMembroQUID(Membro membroQUID) {
		this.membroQUID = membroQUID;
	}

	/**
	 * @return String o(a) idComponente
	 */
	public String getIdComponente() {
		return idComponente;
	}

	/**
	 * @param String o(a) idComponente a ser setado(a)
	 */
	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idComponente == null) ? 0 : idComponente.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MembroDocScree))
			return false;
		MembroDocScree other = (MembroDocScree) obj;
		if (idComponente == null) {
			if (other.idComponente != null)
				return false;
		} else if (!idComponente.equals(other.idComponente))
			return false;
		return true;
	}

}
