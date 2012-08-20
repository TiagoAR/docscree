package br.ueg.unucet.docscree.utilitarios;

import java.io.File;

public class ArquivoCarregado {
	
	private File file;
	private String nomeArquivo;
	private String situacao;
	/**
	 * @return File o(a) file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param File o(a) file a ser setado(a)
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
	 * @param String o(a) nomeArquivo a ser setado(a)
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
	 * @param String o(a) situacao a ser setado(a)
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	

}
