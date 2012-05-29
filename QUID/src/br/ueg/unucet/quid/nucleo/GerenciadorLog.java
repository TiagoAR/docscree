package br.ueg.unucet.quid.nucleo;

import br.ueg.unucet.quid.enums.NivelLogEnum;

/**
 * Classe responsavel por gerenciar os logs gerados no framework.
 * @author QUID
 *
 */
public class GerenciadorLog {
	
	
	public static GerenciadorLog gerenciadorLog;
	
	private GerenciadorLog(){}
	
	public static GerenciadorLog getInstanciaAtual(){
		if(gerenciadorLog == null){
			gerenciadorLog = new GerenciadorLog();
		}
		return gerenciadorLog;
	}
	
	public void registrarLog(String log, NivelLogEnum nivelLog){
		System.out.println(log);
	}
	
	public void exibirAcao(String acao){
		
	}

}
