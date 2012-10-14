package br.ueg.unucet.docscree.utilitarios;

import java.io.File;

/**
 * Classe responsável por fazer o upload do arquivo para o servidor
 * 
 * @author Diego
 *
 */
public class UploadArquivo {

	/**
	 * Cria um diretorio para o caminho e nome do diretorio passado como
	 * parametro
	 * 
	 * @param caminho
	 *            do diretorio
	 * @param diretorio
	 *            nome do diretorio
	 * @return true se conseguir criar ou ja existir o diretorio false se nao
	 *         conseguir criar
	 */
	public static String criarDiretorio(String caminho, String diretorio) {
		StringBuilder sb = new StringBuilder();
		sb.append(UploadArquivo.adicionarBarraFinalString(caminho));
		sb.append(UploadArquivo.adicionarBarraFinalString(diretorio));
		File f = new File(sb.toString());
		if (!f.isFile())
			if (f.isDirectory())
				return sb.toString();
			else if (f.mkdir())
				return sb.toString();
		return null;
	}
	
	/**
	 * Método que adiciona barra de separação de pasta no final da palavra
	 * 
	 * @param s string do caminho
	 * @return String com barra adicionada ao final
	 */
	public static String adicionarBarraFinalString(String s){
		if (!s.endsWith(File.separator))
			return s+= File.separator;
		return s;
	}
}
