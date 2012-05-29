package br.ueg.unucet.quid.excessoes;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o projeto.
 * @author QUID
 *
 */
public class ProjetoExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3295489682196156661L;

	public ProjetoExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
	}
	
	public ProjetoExcessao(String mensagem){
		super(mensagem);
	}

}
