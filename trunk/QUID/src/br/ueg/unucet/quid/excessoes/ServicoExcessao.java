package br.ueg.unucet.quid.excessoes;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o Servico.
 * @author QUID
 *
 */
public class ServicoExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServicoExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
		// TODO Auto-generated constructor stub
	}
	
	public ServicoExcessao(String mensagem){
		super(mensagem);
	}

}
