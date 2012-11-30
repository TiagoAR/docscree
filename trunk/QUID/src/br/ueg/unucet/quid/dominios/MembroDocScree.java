package br.ueg.unucet.quid.dominios;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

/**
 * POJO que representa o Membro do DocScree
 * contendo instancia do Membro do QUID, o TipoMembro-Visão do Membro
 * e o ID do componente que foi adicionado ao Artefato
 * 
 * @author Diego
 *
 */
public class MembroDocScree {

	/**
	 * TipoMembro-Visão do Membro
	 */
	private SuperTipoMembroVisaoZK<?> tipoMembroVisao;
	/**
	 * ID do componente adicionado no Artefato
	 */
	private String idComponente;
	
	/**
	 * Construtor Default
	 */
	public MembroDocScree() {
	}
	
	/**
	 * Construtor que seta as variáveis da classe através dos parâmetros
	 * 
	 * @param tipoMembroVisao
	 * @param idComponente
	 */
	public MembroDocScree(SuperTipoMembroVisaoZK<?> tipoMembroVisao, String idComponente) {
		this.tipoMembroVisao = tipoMembroVisao;
		this.idComponente = idComponente;
	}

	/**
	 * @return SuperTipoMembroVisaoZK o(a) tipoMembroVisao
	 */
	public SuperTipoMembroVisaoZK<?> getTipoMembroVisao() {
		return tipoMembroVisao;
	}

	/**
	 * @param tipoMembroVisao o(a) tipoMembroVisao a ser setado(a)
	 */
	public void setTipoMembroVisao(SuperTipoMembroVisaoZK<?> tipoMembroVisao) {
		this.tipoMembroVisao = tipoMembroVisao;
	}

	/**
	 * @return String o(a) idComponente
	 */
	public String getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente o(a) idComponente a ser setado(a)
	 */
	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	/**
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

	/**
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
