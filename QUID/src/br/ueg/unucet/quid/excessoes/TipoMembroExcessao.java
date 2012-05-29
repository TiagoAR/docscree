package br.ueg.unucet.quid.excessoes;

/**Classe responsavel por  mapear as excessoes referente a manipulacao dos
 * TipoMembros
 * @author QUID
 *
 */
public class TipoMembroExcessao extends QuidExcessao{

	private static final long serialVersionUID = 1L;

	public TipoMembroExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
		// TODO Auto-generated constructor stub
	}
	
	public TipoMembroExcessao(String mensagem){
		super(mensagem);
	}

}
