package br.ueg.unucet.quid.teste;



import java.io.File;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.interfaces.IQUID;
import br.ueg.unucet.quid.servicos.QuidService;

public class Teste {

	public static void main(String args[]){
		IQUID 	quid = QuidService.obterInstancia();
		File f = new File("D:/teste/inputtext.jar");
		Retorno<File, String> retorno = quid.mapearArquivosTipoMembro(f);
		System.out.println("");
	}
}
