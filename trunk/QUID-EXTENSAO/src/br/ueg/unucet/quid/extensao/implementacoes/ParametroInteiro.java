package br.ueg.unucet.quid.extensao.implementacoes;

/**
 * Classe que representa um parametro do tipo inteiro.
 * @author QUID
 *
 */
public class ParametroInteiro extends Parametro<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4040852713192082852L;

	public ParametroInteiro(Class<Integer> classe) {
		super(classe);
		// TODO Auto-generated constructor stub
	}
	
	protected Integer cast(String valor2) {
		Integer retorno = 10;
		if(valor2 != null && !valor2.equals("")){
			try{
				retorno = Integer.valueOf(valor2);
			}catch (Exception e) {
			}
		}
		return retorno;
	}
	
	protected String recast(Integer valor) {
		if(valor != null)
		return valor.toString();
		else
			return "10";
	}
}
