package br.ueg.unucet.quid.excessoes;

import java.util.Collection;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o artefato.
 * @author QUID
 *
 */
public class ArtefatoExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2390324799821775494L;
	/**
	 * Atributo que representa os nomes dos parametros invalidos do artefato.
	 */
	private Collection<String> parametrosInvalidos;
	
	
	public ArtefatoExcessao(String mensagem){
		super(mensagem);
	}
	public ArtefatoExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
	}
	
	public ArtefatoExcessao(String mensagem, Collection<String> parametrosInvalidos){
		super(mensagem);
		this.parametrosInvalidos = parametrosInvalidos;
	}
	
	//GETTES AND SETTERS
	public Collection<String> getParametrosInvalidos(){
		return this.parametrosInvalidos;
	}
	

}
