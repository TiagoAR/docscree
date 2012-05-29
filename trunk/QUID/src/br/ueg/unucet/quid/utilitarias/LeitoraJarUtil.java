package br.ueg.unucet.quid.utilitarias;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Classe responsavel por realizar o mapeamendo de um diretorio contendo jars para o classpath da aplicacao.
 * @author Quid
 *
 */
public class LeitoraJarUtil extends URLClassLoader{
	
	/**
	 * Lista de arquivos que foram importados para o classPath da aplicacao.
	 */
	private List<URL> listArquivosImportados;
	/**
	 * ClassLoader da aplicacao.
	 */
	private ClassLoader classLoader;
	

	public LeitoraJarUtil(URL[] urls) {
		super(urls, getTCL());
		this.listArquivosImportados = new ArrayList<URL>();
		// TODO Auto-generated constructor stub
	}
	
	public static ClassLoader getTCL() {
	       return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
	          public Object run() {
	              return Thread.currentThread().getContextClassLoader();
	          }
	       });
	  }
	
	/**
	 * Metodo que mapeia um arquivo para detro do classPath da aplicacao.
	 * @param arquivo URL do arquivo que sera mapeado.
	 */
	public void addArquivo(URL arquivo) {
		if(!this.listArquivosImportados.contains(arquivo)){
			super.addURL(arquivo);
			this.listArquivosImportados.add(arquivo);
		}
	}
	
	
	/** Metodo responsavel por mapear um path para aplicacao
	 * @param diretorio Diretorio onde se encontram os jar
	 * @throws MalformedURLException Erro de URL nao encontrada.
	 */
	public void lerDiretorio(String diretorio) throws MalformedURLException{
		File local = new File(diretorio);
		File jars[] = local.listFiles();
		for(File file :jars){
			addArquivo(file.toURI().toURL());
		}
	}
	
	/** Metodo responsavel por listar todas as classes de um diretorio que contenha arquivos .jar
	 * @param diretorio Diretorio dos arquivos .jar
	 * @return Lista de Classes que se encontram no direotior
	 * @throws IOException Excessao de erro de entrada e saida ocasionada quando diretorio nao for informado.
	 * @throws ClassNotFoundException
	 */
	public Collection<Class<?>> listarClassesDiretorio(String diretorio) throws IOException, ClassNotFoundException{
		File local = new File(diretorio);
		File jars[] = local.listFiles();
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		for(File file : jars){
			classes.addAll(listarClassesJar(file));
		}
		return classes;
	}
	
	/** Metodo responsavel por listar as classes de um arquivo .jar
	 * @param arquivo Diretorio do arquivo .jar
	 * @return Lista de classes do arquivo .jar
	 * @throws IOException Erro de entrada e  saida ocasionado por nao encontrar o local especificado
	 * @throws ClassNotFoundException
	 */
	public Collection<Class<?>> listarClassesJar(String arquivo) throws IOException, ClassNotFoundException{
		addArquivo(arquivo);
		JarFile jar = new JarFile(arquivo);	
		return listarClassesJar(jar);
		
	}
	
	public Collection<Class<?>> listarClassesJarAdicionado(JarFile jar) throws ClassNotFoundException{
		return listarClassesJar(jar);
	}
	
	/**Adiciona um novo arquivo ao path do URLClassLoader
	 * @param arquivo
	 * @throws MalformedURLException
	 */
	public void addArquivo(String arquivo) throws MalformedURLException{
		addArquivo((new File(arquivo)).toURI().toURL());
	}
	
	/**
	 * Metodo que realiza a leitura de uma classe a partir do seu nome dentro do arquivo jar.
	 * @param caminhoClassJar String contendo o caminho da classe dentro do jar.
	 * @return Intancia da classe que se esta procurando.
	 * @throws ClassNotFoundException Excessao caso nao aja a classe especificada.
	 */
	public Class<?> lerClass(String caminhoClassJar) throws ClassNotFoundException{
		String nomeClass = converteDiretorioEmPacote(caminhoClassJar);
		return loadClass(nomeClass);
	}
	
	
	
	/** Metodo responsavel por listar as classes de um arquivo .jar
	 * @param arquivo File que indica o arquivo .jar
	 * @return Lista de classes do arquivo .jar
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Collection<Class<?>> listarClassesJar(File arquivo) throws IOException, ClassNotFoundException{
		JarFile jar = new JarFile(arquivo);
		return listarClassesJar(jar);
	}
	
	/**
	 * Metodo que realiza a leitura da classe principal que se encontra dentro de um arquivo jar.
	 * Essa classe principal e a especificaca dentro do arquivo MANIFEST.MF no atributo Main-Class.
	 * @param diretorioArquivo Diretorio do arquivo jar a ser pego a classe principal.
	 * @return Intancia da classe principal do arquivo jar.
	 * @throws IOException Excessao na importacao do arquivo jar.
	 */
	public Class<?> getClassePrincipalJar(String diretorioArquivo) throws IOException{
		JarFile jar = new JarFile(diretorioArquivo);
		String nomeClasse = getClassePrincipalManifest();
		return getClassePorDiretorio(nomeClasse);
	}


	private String getClassePrincipalManifest() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo responsavel por listar todas as classes que estao dentro do arquivo jar.
	 * @param jar Jar para qual sera listado as classes.
	 * @return Lista de classes que contem o jar.
	 * @throws ClassNotFoundException Falha ao pegar uma classe dentro do jar.
	 */
	private Collection<Class<?>> listarClassesJar(JarFile jar) throws ClassNotFoundException{
		Enumeration<JarEntry> arquivos = jar.entries();
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		while(arquivos.hasMoreElements()){
			JarEntry jarEntry = arquivos.nextElement();
			Class<?> classe = getClassePorDiretorio(jarEntry.getName()); 
			if(classe != null){
				classes.add(classe);
			}
		}
		return classes;
	}
	
	
	/**
	 * Metodo responsavel por pegar uma classe a partir do diretorio dela dentro do arquivo jar.
	 * @param diretorio Diretorio da classe dentro do arquivo jar.
	 * @return Intancia da classe.
	 */
	private Class<?> getClassePorDiretorio(String diretorio) {
		String nomeClasse = converteDiretorioEmPacote(diretorio);
		Class<?> classe = null;
		try {
			classe = loadClass(nomeClasse, false);
		} catch (ClassNotFoundException e) {}
		return classe;
	}
	
	/**
	 * Metodo responsavel por subistituir os / por . do caminho da classe dentro do jar, para que posteriormente seje possivel
	 * realizar a sua instanciacao.
	 * @param diretorio String contendo o caminho a partir de diretorio da classe.
	 * @return String substituida os / por .
	 */
	public String converteDiretorioEmPacote(String diretorio){
		diretorio = diretorio.replaceAll("/", ".");
		diretorio = diretorio.substring(0, diretorio.length() - 6);
		return diretorio;
	}
	
	
	

}
