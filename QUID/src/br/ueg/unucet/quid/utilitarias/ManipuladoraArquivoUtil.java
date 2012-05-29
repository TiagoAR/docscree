package br.ueg.unucet.quid.utilitarias;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Classe responsavel por realizar as operacoes sobre arquivos.
 * @author QUID
 *
 */
public class ManipuladoraArquivoUtil {
	
	/**
	 * Metodo responsavel por realizar a copia de um arquivo.
	 * @param arquivoOrigem Arquivo que sera copiado.
	 * @param arquivoDestino Destino do arquivo a ser copiado.
	 * @throws IOException Excessao de IO na leitura do arquivo ou na sua escrita.
	 */
	public static void copiarArquivo(String arquivoOrigem, String arquivoDestino)throws IOException {	
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(arquivoOrigem));  
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(arquivoDestino)));           
        byte[] buffer = new byte[1024];  
        int length;  
        while ((length = input.read(buffer)) > 0) {  
            output.write(buffer, 0, length);  
        }  
          
        output.close();  
        input.close();  
	}

}
