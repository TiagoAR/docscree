package br.ueg.unucet.quid.teste;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;




public class Teste {

	public static void main(String args[]){
		Parametro<Artefato> parametro = new Parametro<Artefato>(Artefato.class);
		parametro.setNome("par");
		parametro.setRotulo("Artefato Modelo");
		
		System.out.println();
	}
}
