package br.ueg.unucet.quid.excessoes;

import java.util.Collection;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o usuario.
 * @author QUID
 *
 */
public class UsuarioExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5766106471123103345L;
	/**
	 * Lista de atributos nao informados do usuario.
	 */
	private Collection<String> atributosNaoInformados;
	public UsuarioExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioExcessao(String mensagem){
		super(mensagem);
	}
	
	public UsuarioExcessao(String mensagem, Collection<String> atributosNaoInformados){
		super(mensagem);
		this.atributosNaoInformados = atributosNaoInformados;
	}
	
	public Collection<String> getAtributosNaoInformados(){
		return this.atributosNaoInformados;
	}
	
	

	

}
