package br.ueg.unucet.docscree.utilitarios;

import java.io.File;

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
	
	public static String adicionarBarraFinalString(String s){
		if (!s.endsWith(File.separator))
			return s+= File.separator;
		return s;
	}
}
