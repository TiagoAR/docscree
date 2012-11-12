package br.ueg.unucet.docscree.utilitarios;

import java.util.TimerTask;

/**
 * Classe que representa a Task (Thread) para o Bloqueio de um Artefato
 * contém o Timer para executar a ação de desbloqueio após inatividade
 * 
 * @author Diego
 *
 */
public abstract class TaskBloqueio extends TimerTask {

	/**
	 * nome do Artefato para ser desbloqueado após inatividade
	 */
	private String nomeArtefato;
	
	/**
	 * Default construtor
	 * 
	 * @param nomeArtefato
	 */
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
	 * @param nomeArtefato o(a) nomeArtefato a ser setado(a)
	 */
	public void setNomeArtefato(String nomeArtefato) {
		this.nomeArtefato = nomeArtefato;
	}
	
}
