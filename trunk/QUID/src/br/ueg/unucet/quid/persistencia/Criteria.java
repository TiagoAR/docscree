package br.ueg.unucet.quid.persistencia;

import java.io.Serializable;

/**
 * Classe responsavel por represetar as condicoes da consulta HQL.
 * @author QUID
 *
 */
public class Criteria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6032332200685541642L;
	/**
	 * Nome do atributo do objeto que se ira realizar o criterio de pesquisa.
	 */
	private String nomeColuna;
	/**
	 * Atributo que define o tipo de pesquisa que ocorrera sobre a coluna.
	 */
	private String tipoCriteria;
	/**
	 * Vetor dos parametros que serao feitos as condicoes.
	 */
	private Object[] valores;
	/**
	 * Atributo que realiza a concatenacao de criterias a fim de formar uma fila de criterias.
	 */
	private Criteria proxima;
	
	public static final String LIKE = "like '%%campo0%'";
	public static final String EQUAL = "= '%campo0'";
	public static final String BETWEEN = "between '%campo0' and '%campo1'";
	
	
	public Criteria(String tipoCriteria, String nomeColuna, Object ... valores){
		this.nomeColuna = nomeColuna;
		this.tipoCriteria = tipoCriteria;
		this.valores = valores;
	}
	
	public Criteria(String nomeColuna, Object ... valores){
		this.nomeColuna = nomeColuna;
		this.valores = valores;
	}
	
	/**Retona a sql de condicao da coluna informada
	 * @return String de sql
	 */
	public String getSql(){
		String aux = montarSql();
		aux = popularCampos(aux);
		if(this.proxima != null){
			aux = aux + " and " + this.proxima.getSql();
		}
		return aux;
	}
	
	/**Concatena outra criteria para formar um conjunto de criterias.
	 * @param criteria Criteria a ser concatenada.
	 */
	public void concatenarCriteria(Criteria criteria){
		if(this.proxima == null){
			this.proxima = criteria;
		}else{
			this.proxima.concatenarCriteria(criteria);
		}
	}
	
	/**
	 * Metodo que realiza a montagem da sql com base na coluna, nos valores e no tipo de criteria.
	 * @return HQL do criterio.
	 */
	private String montarSql(){
		if(tipoCriteria == null){
		   selectTipoCriteria();
		}
			return this.nomeColuna + " " + this.tipoCriteria;
	}
	
	/**
	 * Metodo que verifica o tipo de criteria que com base na classe dos valores.
	 */
	private void selectTipoCriteria() {
		Object value = valores[0];
		if (value instanceof String) {
			tipoCriteria = LIKE;
		}else{
			tipoCriteria = EQUAL;
		}
	}

	/**
	 * Metodo que popula a HQL com os valores informados.
	 * @param sql SQl que sera populada.
	 * @return SQL com os valores populados.
	 */
	private String popularCampos(String sql){
		Integer i = 0;
		for (Object value : valores) {
			if(value.getClass().isEnum()){
				System.out.println(value.getClass().getCanonicalName());
			}
			sql = sql.replaceAll("%campo"+i.toString(),value.toString());
			i++;
		}
		return sql;
	}
	
	

}
