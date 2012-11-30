package br.ueg.unucet.quid.servicosquid;

import java.util.Map;

import br.ueg.unucet.quid.dominios.MembroDocScree;

@SuppressWarnings("unchecked")
public abstract class ServicoValidacao extends SuperServicoSpring {

	public static final String PARAMETRO_VALORES_MEMBROS = "VALORES_MEMBROS";
	public static final String PARAMETRO_MEMBROS = "MEMBROS";
	public static final String PARAMETRO_LISTA_ERROS = "LISTA_ERROS";
	public static final String PARAMETRO_MEMBROS_RESULTADO = "MEMBROS_RESULTADO";
	
	public ServicoValidacao() {
		super();
	}
	
	public Map<String, MembroDocScree> getMapaMembros() {
		return (Map<String, MembroDocScree>) this.getParametroPorNome(PARAMETRO_MEMBROS).getValor();
	}
	
	public Map<String, Object> getMapaValoresMembros() {
		return (Map<String, Object>) this.getParametroPorNome(PARAMETRO_VALORES_MEMBROS).getValor();
	}
	
}
