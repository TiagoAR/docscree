package br.ueg.unucet.docscree.utilitarios;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import br.ueg.unucet.docscree.modelo.ArtefatoBloqueado;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Usuario;

public class BloquearArtefatoControle {
	
	private static long DEFAULT_DELAY = 1800000;
	
	private static BloquearArtefatoControle instancia = null;
	private Map<String, ArtefatoBloqueado> listaArtefatoBloqueados;
	
	private BloquearArtefatoControle() {
		this.listaArtefatoBloqueados = new HashMap<String, ArtefatoBloqueado>();
	}
	
	public static BloquearArtefatoControle obterInstancia() {
		if (instancia == null) {
			instancia = new BloquearArtefatoControle();
		}
		return instancia;
	}
	
	public boolean isArtefatoBloqueado(String nomeArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(nomeArtefato)) {
			return true;
		}
		return false;
	}
	
	public boolean isArtefatoBloqueado(Artefato verificador) {
		if (this.listaArtefatoBloqueados.containsKey(verificador.getNome())) {
			return true;
		}
		return false;
	}
	
	public boolean isArtefatoBloqueado(Artefato verificador, Usuario usuarioArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(verificador.getNome())) {
			if (!this.listaArtefatoBloqueados.get(verificador.getNome()).getUsuarioArtefato().equals(usuarioArtefato)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isArtefatoBloqueado(String nomeArtefato, Usuario usuarioArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(nomeArtefato)) {
			if (!this.listaArtefatoBloqueados.get(nomeArtefato).getUsuarioArtefato().equals(usuarioArtefato)) {
				return true;
			}
		}
		return false;
	}
	
	public void adicionarBloqueioArtefato(Artefato artefato, Usuario usuarioArtefato) {
		ArtefatoBloqueado novo = new ArtefatoBloqueado();
		novo.setArtefato(artefato);
		novo.setUsuarioArtefato(usuarioArtefato);
		novo.setTimer(gerarTimer(artefato.getNome()));
		System.out.println("Gerado Task");
		listaArtefatoBloqueados.put(artefato.getNome(), novo);
	}
	
	public boolean renovarBloqueio(Artefato artefato, Usuario usuarioArtefato) {
		if (this.listaArtefatoBloqueados.containsKey(artefato.getNome())) {
			if (this.listaArtefatoBloqueados.get(artefato.getNome()).getUsuarioArtefato().equals(usuarioArtefato)) {
				listaArtefatoBloqueados.get(artefato.getNome()).getTimer().cancel();
				listaArtefatoBloqueados.get(artefato.getNome()).setTimer(gerarTimer(artefato.getNome()));
				return true;
			}
		}
		return false;
	}
	
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
