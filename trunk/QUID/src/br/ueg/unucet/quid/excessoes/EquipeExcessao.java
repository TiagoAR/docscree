package br.ueg.unucet.quid.excessoes;

import java.util.Collection;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o equipe.
 * @author QUID
 *
 */
public class EquipeExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1641821038316235943L;
	/**
	 * Atributo que representa os nomes dos campos obrigatorios da equipe.
	 */
	private Collection<String> camposObrigatorios;

	public EquipeExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
		// TODO Auto-generated constructor stub
	}
	
	public EquipeExcessao(String mensagem){
		super(mensagem);
	}

	public EquipeExcessao(String mensagem, Collection<String> camposObrigatorios){
		super(mensagem);
		this.camposObrigatorios = camposObrigatorios;
	}
	
	public Collection<String> getCamposObrigatorios(){
		return this.camposObrigatorios;
	}
}
