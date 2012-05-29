package br.ueg.unucet.quid.excessoes;

/**Classe responsavel por informar os erros de verificacao de jar
 * @author Johnys
 *
 */
public class VerificadorJarExcessao extends QuidExcessao{

	private static final long serialVersionUID = 1L;
	public VerificadorJarExcessao(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public VerificadorJarExcessao(String mensagem, Throwable erro){
		super(mensagem,erro);
	}

	/**
	 * 
	 */

}
