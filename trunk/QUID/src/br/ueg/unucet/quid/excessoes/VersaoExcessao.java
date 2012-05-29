package br.ueg.unucet.quid.excessoes;

/**
 * Classe que representa as execessoes disparadas pelo versionador.
 * @author QUID
 *
 */
public class VersaoExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VersaoExcessao(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public VersaoExcessao(String mensagem, Throwable erro){
		super(mensagem, erro);
	}

}
