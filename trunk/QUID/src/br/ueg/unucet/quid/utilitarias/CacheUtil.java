package br.ueg.unucet.quid.utilitarias;

import java.util.LinkedHashMap;

/**
 * Classe responsavel por realizar o cache de elementos.
 * @author QUID
 *
 * @param <K>Chave da consulta dos elementos.
 * @param <V>Valor armazenado.
 */
public class CacheUtil<K, V> extends LinkedHashMap<K, V>{
	
	protected int maximoElementos;
	
	public CacheUtil(int maximoElementos){
		super(maximoElementos, 0.75f, true);
		this.maximoElementos = maximoElementos;
	}
	
	 /**
	  *Metodo que verifica se e necessario remover um elemento do cache.
	 * @return
	 */
	protected boolean removeEldestEntry() {  
	        return (this.size() > maximoElementos);  
	 }

}
