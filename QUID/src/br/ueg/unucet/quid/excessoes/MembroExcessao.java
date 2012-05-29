package br.ueg.unucet.quid.excessoes;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o MEMBRO.
 * @author QUID
 *
 */
public class MembroExcessao extends QuidExcessao{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1119167741434978479L;
	
	/**
	 * Constantes que representam o tipo do erro emitido pela classe de controle do membro.
	 */
	public static final int NOME_MEMBRO_AUSENTE = 1;
	public static final int DESCRIAO_MEMBRO_AUSENTE = 2;
	public static final int TIPO_MEMBRO_AUSENTE = 3;
	public static final int PARAMETROS_TIPO_MEMBRO_INVALIDOS = 3;
	public static final int MEMBRO_AUSENTE = 4;
	public static final int MEMBRO_DUPLICADO = 5;
	private int tipoErro;
	
	public MembroExcessao(String mensagem){
		super(mensagem);
	}
	
	public MembroExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
		// TODO Auto-generated constructor stub
	}
	
	public MembroExcessao(String mensagem, int tipoErro){
		super(mensagem);
		this.tipoErro = tipoErro;
	}
	
	public MembroExcessao(String mensagem, Throwable erro, int tipoErro){
		super(mensagem, erro);
		this.tipoErro = tipoErro;
	}
	
	public int getTipoErro(){
		return this.tipoErro;
	}
	
}
