package br.ueg.unucet.docscree.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation para serem associadas aos atributos da visão, informando
 * se representam entidades, e assim o controlador fazer a devida leitura das mesmas.
 * 
 * @author Diego
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AtributoVisao {
	
	/**
	 * Representa o nome do atributo, deve ser o mesmo valor do campo da classe.
	 * 
	 * @return String nome do atributo
	 */
	String nome();
	
	/**
	 * Informa se campo é associado a uma entidade ou não.
	 * 
	 * @return boolean isCampoEntidade se é campo de entidade ou não
	 */
	boolean isCampoEntidade() default false;
	
	/**
	 * 
	 * @return String nomeBundle representa chave do bundle.
	 */
	String nomeCampoBundle();

}
