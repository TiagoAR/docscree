package br.ueg.quid.verificadorjar.principal;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class LeitoraJarUtil extends URLClassLoader{

	public LeitoraJarUtil(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}
	
	public void addArquivo(URL arquivo) {
		super.addURL(arquivo);
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
		addArquivo((new File(arquivo)).toURI().toURL());
		JarFile jar = new JarFile(arquivo);	
		return listarClassesJar(jar);
		
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
	
	public boolean verificarArquivoJar(String arquivo){
		try{
			JarFile jar = new JarFile(arquivo);
			Enumeration<JarEntry> en = jar.entries();
			while(en.hasMoreElements()){
				JarEntry entry = en.nextElement();
				String nome = entry.getName();
				byte[] b = entry.getExtra();
				if(nome.endsWith(".class")){
				Class<?> classe = defineClass(b, 0, b.length);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
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
	
	
	private Class<?> getClassePorDiretorio(String diretorio) {
		String nomeClasse = converteDiretorioEmPacote(diretorio);
		Class<?> classe = null;
		try {
			classe = loadClass(nomeClasse, false);
		} catch (ClassNotFoundException e) {}
		return classe;
	}
	
	private String converteDiretorioEmPacote(String diretorio){
		diretorio = diretorio.replaceAll("/", ".");
		diretorio = diretorio.substring(0, diretorio.length() - 6);
		return diretorio;
	}
	
	
	

}
