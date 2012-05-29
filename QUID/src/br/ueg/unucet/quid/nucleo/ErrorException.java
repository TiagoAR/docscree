package br.ueg.unucet.quid.nucleo;

import br.ueg.unucet.quid.enums.TipoErroEnum;



/**
 * Classe responsavel por expressar as execessoes de erro geradas pelo framework. 
 * @author QUID
 *
 */
@SuppressWarnings("serial")
public class ErrorException extends Exception {

	/**
	 * Tipo de erro gerado.
	 */
	private TipoErroEnum erro;
	/**
	 * Mensagem gerada decorrente a excessao.
	 */
	private String message;

	public ErrorException(){
	}

	public ErrorException(TipoErroEnum type){
		super();
		setErro(type);
	}

	public ErrorException(TipoErroEnum type, String message){
		this.message = message;
		setErro(type);
	}

	public TipoErroEnum getErro() {
		return erro;
	}

	public void setErro(TipoErroEnum erro) {
		this.erro = erro;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}	
}
