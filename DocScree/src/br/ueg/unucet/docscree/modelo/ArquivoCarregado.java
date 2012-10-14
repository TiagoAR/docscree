package br.ueg.unucet.docscree.modelo;

import java.io.File;

/**
 * POJO que representa o Arquivo a ser carregado (ou já carregado) do TipoMembro
 * a ser mapeado no framework
 * 
 * @author Diego
 * 
 */
public class ArquivoCarregado {

	/**
	 * Arquivo carregado no servidor
	 */
	private File file;
	/**
	 * Nome do arquivo (Identificador)
	 */
	private String nomeArquivo;
	/**
	 * Situação do mapeamento do arquivo no framework
	 */
	private String situacao;

	/**
	 * @return File o(a) file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param File
	 *            o(a) file a ser setado(a)
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return String o(a) nomeArquivo
	 */
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	/**
	 * @param String
	 *            o(a) nomeArquivo a ser setado(a)
	 */
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	/**
	 * @return String o(a) situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * @param String
	 *            o(a) situacao a ser setado(a)
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
