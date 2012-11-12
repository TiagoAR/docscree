package br.ueg.unucet.docscree.utilitarios;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import br.ueg.unucet.docscree.modelo.ArtefatoBloqueado;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Usuario;

/**
 * Classe responsável por controlar o bloqueio do ArtefatoModelo
 * deve ser única no servidor para impedir acesso em máquinas diferentes ao mesmo ArtefatoModelo
 * 
 * @author Diego
 *
 */
public class BloquearArtefatoControle {
	
	/**
	 * Tempo permitido de inatividade
	 */
	private static long DEFAULT_DELAY = 1800000;
	
	/**
	 * Instancia da classe - Singleton
	 */
	private static BloquearArtefatoControle instancia = null;
	/**
	 * HashMap contendo os ArtefatosBloqueados
	 */
	private Map<String, ArtefatoBloqueado> listaArtefatoBloqueados;
	
	/**
	 * Construtor default que inicializa a lista de ArtefatosModelos bloqueados
	 */
	private BloquearArtefatoControle() {
		this.listaArtefatoBloqueados = new HashMap<String, ArtefatoBloqueado>();
	}
	
	/**
	 * Método que retorna instancia da classe - Padrão Singleton
	 * 
	 * @return BloquearArtefatoControle instancia da classe
	 */
	public static BloquearArtefatoControle obterInstancia() {
		if (instancia == null) {
			instancia = new BloquearArtefatoControle();
		}
		return instancia;
	}
	
	/**
	 * Método que verifica se o ArtefatoModelo está bloqueado
	 * 
	 * @param nomeArtefato a ser pesquisado se está bloqueado
	 * @return boolean se ArtefatoModelo está bloqueado
	 */
	public boolean isArtefatoBloqueado(String nomeArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(nomeArtefato)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica se ArtefatoModelo está bloqueado
	 * 
	 * @param verificador ArtefatoModelo a verificar
	 * @return boolean se ArtefatoModelo está bloqueado
	 */
	public boolean isArtefatoBloqueado(Artefato verificador) {
		if (this.listaArtefatoBloqueados.containsKey(verificador.getNome())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica se ArtefatoModelo está bloqueado para aquele usuário
	 * 
	 * @param verificador ArtefatoModelo a ser verificado
	 * @param usuarioArtefato Usuário a verificar se o ArtefatoModelo está bloqueado para ele
	 * @return boolean se ArtefatoModelo está bloqueado para o usuário
	 */
	public boolean isArtefatoBloqueado(Artefato verificador, Usuario usuarioArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(verificador.getNome())) {
			if (!this.listaArtefatoBloqueados.get(verificador.getNome()).getUsuarioArtefato().equals(usuarioArtefato)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método que verifica se ArtefatoModelo está bloqueado para aquele usuário
	 * 
	 * @param nomeArtefato nome do Artefato a ser pesquisado
	 * @param usuarioArtefato Usuário a verificar se o ArtefatoModelo está bloqueado para ele
	 * @return boolean se ArtefatoModelo está bloqueado para o usuário
	 */
	public boolean isArtefatoBloqueado(String nomeArtefato, Usuario usuarioArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(nomeArtefato)) {
			if (!this.listaArtefatoBloqueados.get(nomeArtefato).getUsuarioArtefato().equals(usuarioArtefato)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método que adiciona um bloqueio ao ArtefatoModelo para o usuário
	 * 
	 * @param artefato ArtefatoModelo a ser bloqueado
	 * @param usuarioArtefato Usuario para associação do bloqueio
	 */
	public void adicionarBloqueioArtefato(Artefato artefato, Usuario usuarioArtefato) {
		ArtefatoBloqueado novo = new ArtefatoBloqueado();
		novo.setArtefato(artefato);
		novo.setUsuarioArtefato(usuarioArtefato);
		novo.setTimer(gerarTimer(artefato.getNome()));
		System.out.println("Gerado Task");
		listaArtefatoBloqueados.put(artefato.getNome(), novo);
	}
	
	/**
	 * Método que renovo bloqueio para um ArtefatoModelo e seu usuário.
	 * Caso não tenha bloqueio para o ArtefatoModelo é criado um.
	 * 
	 * @param artefato ArtefatoModelo a ser renovado bloqueio
	 * @param usuarioArtefato usuário a ser associado o bloqueio
	 * @return boolean se bloqueio foi renovado
	 */
	public boolean renovarBloqueio(Artefato artefato, Usuario usuarioArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(artefato.getNome())) {
			if (this.listaArtefatoBloqueados.get(artefato.getNome()).getUsuarioArtefato().equals(usuarioArtefato)) {
				listaArtefatoBloqueados.get(artefato.getNome()).getTimer().cancel();
				listaArtefatoBloqueados.get(artefato.getNome()).setTimer(gerarTimer(artefato.getNome()));
				return true;
			}
		} else {
			adicionarBloqueioArtefato(artefato, usuarioArtefato);
			return true;
		}
		return false;
	}
	
	/**
	 * Método que gera Timer para desbloquear ArtefatoModelo após tempo de inatividade
	 * 
	 * @param nomeArtefato nome do ArtefatoModelo para ser gerado Timer
	 * @return Timer instanciado
	 */
	private Timer gerarTimer(String nomeArtefato) {
		Timer timer = new Timer();
		TimerTask task = new TaskBloqueio(nomeArtefato) {
			
			@Override
			public void run() {
				System.out.println("Executando remoção");
				if (listaArtefatoBloqueados.containsKey(getNomeArtefato())) {
					listaArtefatoBloqueados.remove(getNomeArtefato());
					System.out.println("Artefato removido");
				}
			}
		};
		timer.schedule(task, DEFAULT_DELAY);
		return timer;
	}

}
