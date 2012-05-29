package br.ueg.unucet.docscree.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation para serem associadas aos atributos da vis�o, informando
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
	 * Informa se campo � associado a uma entidade ou n�o.
	 * 
	 * @return boolean isCampoEntidade se � campo de entidade ou n�o
	 */
	boolean isCampoEntidade() default false;
	
	/**
	 * 
	 * @return String nomeBundle representa chave do bundle.
	 */
	String nomeCampoBundle();

}
