package br.ueg.unucet.quid.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Johnys
 *	Classe responsavel por definir se uma classe nao sera mapeada MapeadorDeClasses de validacao e 
 * pesquisa.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoAnotations {

}
