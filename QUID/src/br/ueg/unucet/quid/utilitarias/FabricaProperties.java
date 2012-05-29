package br.ueg.unucet.quid.utilitarias;

import java.io.Serializable;

import br.ueg.unucet.quid.configuracoes.MarcadorConfiguracoes;


/**
 * Classe responsavel por mapear os leitores de propriedades do framework.
 * @author QUID
 *
 */
public class FabricaProperties implements Serializable{
	
	/**
	 * Metodo responsavel por ler o arquivo de Configuracoes do framework.
	 * @return Leitor do arquivo de configuracoes.
	 */
	public static LeitoraPropertiesUtil loadConfiguracoes(){
		LeitoraPropertiesUtil properties = new LeitoraPropertiesUtil(MarcadorConfiguracoes.class, "configuracoes.properties");	
		return properties;
	}
	
	/**
	 * Metodo responsavel por realizar a leitura do arquivo de Mensagens do framework.
	 * @return Leitor do arquivo de mensagens.
	 */
	public static LeitoraPropertiesUtil loadMessages(){
		LeitoraPropertiesUtil properties = new LeitoraPropertiesUtil(MarcadorConfiguracoes.class, " mensagens.properties");	
		return properties;
	}
	
}
