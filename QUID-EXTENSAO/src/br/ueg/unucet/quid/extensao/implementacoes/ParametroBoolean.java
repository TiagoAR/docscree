package br.ueg.unucet.quid.extensao.implementacoes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe que especifica um parametro do tipo boolean.
 * @author QUID
 *
 */
public class ParametroBoolean extends Parametro<Boolean> {

	private static final long serialVersionUID = 6907100042108774904L;
	private final String SIM = "Sim";
	private final String NAO = "Não";
	private Collection<String> parametros;
		
	public ParametroBoolean(Class<Boolean> classe) {
		super(classe);
	}
	
	public Collection<String> getListaDominioTipo(){
		if(parametros == null){
			initParametros();
		}
		return parametros;
	}

	private void initParametros() {
		this.parametros = new ArrayList<String>(0);
		parametros.add(SIM);
		parametros.add(NAO);
		
	}

	protected Boolean cast(String valor2) {
		Boolean valorSetar = false;
		if (valor2.equalsIgnoreCase(SIM)) {
			valorSetar = true;
		}
		return valorSetar;
	}

	protected String recast(Boolean valor) {
		
		if (valor != null && valor)
			return SIM;
		return NAO;
	}
}
