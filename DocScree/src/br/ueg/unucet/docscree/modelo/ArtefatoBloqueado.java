package br.ueg.unucet.docscree.modelo;

import java.util.Timer;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Usuario;

public class ArtefatoBloqueado {
	
	private Timer timer;
	private Artefato artefato;
	private Usuario usuarioArtefato;
	
	public ArtefatoBloqueado() {
	}
	
	public ArtefatoBloqueado(Artefato artefato) {
		this.artefato = artefato;
	}

	/**
	 * @return Timer o(a) timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @param Timer
	 *            o(a) timer a ser setado(a)
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @return Artefato o(a) artefato
	 */
	public Artefato getArtefato() {
		return artefato;
	}

	/**
	 * @param Artefato
	 *            o(a) artefato a ser setado(a)
	 */
	public void setArtefato(Artefato artefato) {
		this.artefato = artefato;
	}

	/**
	 * @return Usuario o(a) usuarioArtefato
	 */
	public Usuario getUsuarioArtefato() {
		return usuarioArtefato;
	}

	/**
	 * @param Usuario o(a) usuarioArtefato a ser setado(a)
	 */
	public void setUsuarioArtefato(Usuario usuarioArtefato) {
		this.usuarioArtefato = usuarioArtefato;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artefato == null) ? 0 : artefato.hashCode());
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
		if (!(obj instanceof ArtefatoBloqueado))
			return false;
		ArtefatoBloqueado other = (ArtefatoBloqueado) obj;
		if (artefato == null) {
			if (other.artefato != null)
				return false;
		} else if (!artefato.equals(other.artefato))
			return false;
		return true;
	}

}
