package br.ueg.unucet.quid.utilitarias;

import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.enums.NivelLogEnum;
import br.ueg.unucet.quid.nucleo.GerenciadorLog;

/**
 * Classe responsavel por realizar a serializacoes de objetos dentro do framework
 * @author Quid
 *
 */
@Service("serializador")
public class FabricaSerializacao {
	
	/**
	 * Cache dos objetos serializados.
	 */
	private CacheUtil<Integer, byte[]> cacheSerializacoes;
	/**
	 * Cache dos objetos deserializados.
	 */
	private CacheUtil<Integer, Object> cacheObjetos;
	/**
	 * Objeto singletone da fabrica de serializacao.
	 */
	private static FabricaSerializacao fabricaSerializacao;
	
	public static FabricaSerializacao obterInstancia(){
		if(fabricaSerializacao == null){
			fabricaSerializacao = new FabricaSerializacao();
		}
		return fabricaSerializacao;
	}
	
	private FabricaSerializacao(){
		int tamanhoCache = getTamanhoCache();
		cacheSerializacoes = new CacheUtil<Integer, byte[]>(tamanhoCache);
		cacheObjetos = new CacheUtil<Integer, Object>(tamanhoCache);
	}

	/**
	 * Metodo responsavel por pegar o tamanho do cache do arquivo de configuracoes do framework.
	 * @return Tamanho do cache a ser armazeando.
	 */
	private int getTamanhoCache() {
		int tamanho;
		LeitoraPropertiesUtil propertiesMessage = FabricaProperties.loadMessages();
		String tamanhoCacheCOnfiguracao = FabricaProperties.loadConfiguracoes().getValor("tamanho_cache");
		if(tamanhoCacheCOnfiguracao == null || tamanhoCacheCOnfiguracao.equals("")) {
			GerenciadorLog.getInstanciaAtual().registrarLog(propertiesMessage.getValor("valor_cache_nao_informado"), NivelLogEnum.LOG_EXCESSAO);
			tamanho = 100;
		}else{
			try{
				tamanho = Integer.valueOf(tamanhoCacheCOnfiguracao);
			}catch(Exception e){
				tamanho = 100;
				GerenciadorLog.getInstanciaAtual().registrarLog(propertiesMessage.getValor("valor_cache_valor_invalido"), NivelLogEnum.LOG_EXCESSAO);
			}
		}
		
		return tamanho;
	}
	
	/**
	 * Metodo responsavel por realizar a serializacao de um objeto.
	 * Antes se ser serializaco, e solicidado um hashCode do objeto e verificado dentro do cache de serializacao.
	 * Caso o hash se encotre dentro do cache e retornado uma a serializacao armazenada no cache, caso contrario
	 * e realizado a serializacao e armazenada do cache.
	 * @param objeto Objeto a ser serializado
	 * @return Vetor de bytes da serializacao do objeto.
	 */
	public byte[] serializarObjeto(Object objeto){
		Integer hash = objeto.hashCode();
		if(cacheSerializacoes.containsKey(hash)){
			return cacheSerializacoes.get(hash);
		}else{
			byte[] serializacao = SerializadorObjetoUtil.toByteArray(objeto);
			cacheSerializacoes.put(hash, serializacao);
			return serializacao;
		}
	}
	
	/**
	 * Metodo que deserializa um vetor de bytes para um objeto.
	 * Antes de ser deserializado e solicitado um hashCode o vetor de bytes e verificado dentro do cache de objetos deserializados.
	 * Caso se encontre o hash, e pegado o objeto deserializado do cache, caso contrario, deserializa o objeto e o armazena dentro do cache. 
	 * @param bytes Vetor de bytes que sera deserializado.
	 * @return Objeto deserializado.
	 */
	public Object obterObjeto(byte[] bytes){
		Integer hash = bytes.hashCode();
		if(cacheObjetos.containsKey(hash)){
			return cacheObjetos.get(hash);
		}else{
			Object object = SerializadorObjetoUtil.toObject(bytes);
			cacheObjetos.put(hash, object);
			return object;
		}
	}
	

}
