package br.ueg.unucet.quid.utilitarias;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Classe responsavel por realzar a leitura de um arquivo .properties
 * @author QUID
 *
 */
@SuppressWarnings("serial")
public class LeitoraPropertiesUtil implements Serializable {
	
	/**
	 * Objeto manipulador do arquivo .properties
	 */
	private Properties props;
	/**
	 * Classe que marca o diretorio do arquivo .properties
	 */
	private Class<?> classMarcador;
	

	
	public LeitoraPropertiesUtil(Class<?> classMarcador, String nameProperties) {
		props = new Properties();
		this.classMarcador = classMarcador;
		InputStream in = classMarcador.getResourceAsStream(nameProperties);
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Metodo responsavel por realizar a leitura de um valor dentro do arquivo .properties
	 * @param chave Chave que sera lida dentro do arquivo .properties
	 * @returnValor da chave dentro do arquivo.
	 */
	public String getValor(String chave) {
		return (String) props.getProperty(chave);
	}
}