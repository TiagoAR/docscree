package br.ueg.unucet.docscree.controladores;

import java.lang.reflect.Method;
import java.util.HashMap;

import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.visao.compositor.SuperComposer;
import br.ueg.unucet.quid.interfaces.IQUID;
import br.ueg.unucet.quid.servicos.QuidService;

/**
 * Classe de controle genérico, tem o Serviço do Quid como principal método que
 * é acionado para executar qualquer ação de responsabilidade do framework
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SuperControle {

	private HashMap<String, Object> mapaAtributos;

	/**
	 * Método que retorna instancia do serviço do QUID framework.
	 * 
	 * @return IQUID interface que representa o seriço do QUID
	 */
	public IQUID getFramework() {
		return (IQUID) QuidService.obterInstancia();
	}

	/**
	 * Método responsável por executar qualquer ação, a partir dele que chama o
	 * método específico para comunicar com o framework.
	 * 
	 * @param String
	 *            acao nome do método que deve ser executado
	 * @return boolean se ação foi excutada ou não
	 * @throws Exception 
	 */
	public boolean fazerAcao(String pAcao, SuperComposer<SuperControle> pVisao) throws Exception {
		boolean resultado = false;
		try {
			setMapaAtributos(Reflexao.gerarMapeadorAtributos(pVisao));
			Class classeRef = this.getClass();
			preAcao();
			String nomeAcao = "acao" + pAcao.substring(0, 1).toUpperCase()
					+ pAcao.substring(1).toLowerCase();
			Method metodo = classeRef.getMethod(nomeAcao, HashMap.class);
			resultado = (Boolean) metodo.invoke(this, getMapaAtributos());
		} catch (Exception e) {
			throw new Exception("Erro ao chamar método, contate o administrador do sistema." + "\nExceção: " + e.getMessage());
		}
		posAcao();
		return resultado;
	}

	protected void preAcao() {

	}

	protected void posAcao() {

	}

	protected HashMap<String, Object> getMapaAtributos() {
		return mapaAtributos;
	}

	protected void setMapaAtributos(HashMap<String, Object> mapaAtributos) {
		this.mapaAtributos = mapaAtributos;
	}

}
