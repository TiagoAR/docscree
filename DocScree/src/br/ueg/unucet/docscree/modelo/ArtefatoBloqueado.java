package br.ueg.unucet.docscree.modelo;

import java.util.Timer;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Usuario;

/**
 * POJO que representa um ArtefatoModelo bloqueado para abrir/editar
 * 
 * @author Diego
 *
 */
public class ArtefatoBloqueado {
	
	/**
	 * Timer que executará desbloqueio do ArtefatoModelo após inatividade
	 */
	private Timer timer;
	/**
	 * ArtefatoModelo bloqueado
	 */
	private Artefato artefato;
	/**
	 * Usuário para qual o ArtefatoModelo está bloqueado, usuário que poderá abrir/editar
	 */
	private Usuario usuarioArtefato;
	
	/**
	 * Default construtor
	 */
	public ArtefatoBloqueado() {
	}
	
	/**
	 * Construtor que associa ArtefatoModelo
	 * 
	 * @param artefato
	 */
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
	 * @param timer
	 *            o(a) Timer a ser setado(a)
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
	 * @param artefato
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
	 * @param usuarioArtefato o(a) Usuario a ser setado(a)
	 */
	public void setUsuarioArtefato(Usuario usuarioArtefato) {
		this.usuarioArtefato = usuarioArtefato;
	}

	/**
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

	/**
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
