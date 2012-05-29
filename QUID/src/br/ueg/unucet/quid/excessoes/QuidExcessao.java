package br.ueg.unucet.quid.excessoes;

/**
 * Classe base de excessoes disparadas pelo framework QUID. Todas as demais excessoes devem extender esta classe.
 * @author QUID
 *
 */
public class QuidExcessao extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2235324869497377613L;
	
	public QuidExcessao(String mensagem){
		super(mensagem);
	}
	
	public QuidExcessao(String mensagem, Throwable erro){
		super(mensagem, erro);
	}
	

}
