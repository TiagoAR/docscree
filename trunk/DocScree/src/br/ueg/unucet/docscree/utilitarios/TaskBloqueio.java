package br.ueg.unucet.docscree.utilitarios;

import java.util.TimerTask;

public abstract class TaskBloqueio extends TimerTask {

	private String nomeArtefato;
	
	public TaskBloqueio(String nomeArtefato) {
		this.nomeArtefato = nomeArtefato;
	}

	/**
	 * @return String o(a) nomeArtefato
	 */
	public String getNomeArtefato() {
		return nomeArtefato;
	}

	/**
	 * @param String o(a) nomeArtefato a ser setado(a)
	 */
	public void setNomeArtefato(String nomeArtefato) {
		this.nomeArtefato = nomeArtefato;
	}
	
}
