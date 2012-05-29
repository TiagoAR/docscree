package br.ueg.unucet.quid.versionador;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import br.ueg.unucet.quid.utilitarias.LeitoraJarUtil;
import br.ueg.unucet.quid.utilitarias.ManipuladorClassesUtil;

/**Classe responsavel por mapear um diretorio para aplicacao.
 * Ao carregar o diretorio, a classe armazena as classes encontradas no diretorio
 * @author QUID
 *
 */
public class MapeadoraDiretorio {
	
	/**
	 * Diretorio que sera mapeado.
	 */
	private String diretorio;
	/**
	 * Leitor de jar.
	 */
	protected LeitoraJarUtil leitorJarUtil;
	/**
	 * Lista de classes encontradas dentro do diretorio.
	 */
	protected Collection<Class<?>> classesDiretorio;
	
	public MapeadoraDiretorio(String diretorio) throws IOException, ClassNotFoundException{
		setDiretorio(diretorio);
		carregarDiretorioAplicacao();
		carregarClassesDiretorio();
	}
	
	/**
	 * Metodo responsavel por realizar o carregamento do diretorio informado.
	 * @throws MalformedURLException
	 */
	private void carregarDiretorioAplicacao() throws MalformedURLException{
		leitorJarUtil = new LeitoraJarUtil(new URL[]{});
		leitorJarUtil.lerDiretorio(getDiretorio());
	}
	
	/**
	 * Metodo que solicita o carregamento das classes ao leitor de jar e as armazena no atributo da classe.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void carregarClassesDiretorio() throws IOException, ClassNotFoundException{
		this.classesDiretorio = leitorJarUtil.listarClassesDiretorio(diretorio);
	}
	
	
	/**
	 * Metodo que pega as classes que assinam determinada interface dentro do diretorio.
	 * @param interfaceAssinada Interface que as classes devem assinar.
	 * @return Lista de classes que assinam a interface.
	 */
	protected Collection<Class<?>> getClassesAssinanteInterface(Class<?> interfaceAssinada){
		return ManipuladorClassesUtil.getClassesAssinanteInterface(this.classesDiretorio, interfaceAssinada);
	}
	
	
	//GETTERS AND SETTERS.
	
	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public LeitoraJarUtil getLeitorJarUtil() {
		return leitorJarUtil;
	}

	public void setLeitoraJarUtil(LeitoraJarUtil leitoraDeJar) {
		this.leitorJarUtil = leitoraDeJar;
	}
	
	
	

}
