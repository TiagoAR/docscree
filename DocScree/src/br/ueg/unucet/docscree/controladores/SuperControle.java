package br.ueg.unucet.docscree.controladores;

import java.lang.reflect.Method;
import java.util.HashMap;

import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.visao.compositor.SuperComposer;
import br.ueg.unucet.quid.interfaces.IQUID;
import br.ueg.unucet.quid.servicos.QuidService;

/**
 * Classe de controle gen�rico, tem o Servi�o do Quid como principal m�todo que
 * � acionado para executar qualquer a��o de responsabilidade do framework
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SuperControle {

	private HashMap<String, Object> mapaAtributos;

	/**
	 * M�todo que retorna instancia do servi�o do QUID framework.
	 * 
	 * @return IQUID interface que representa o seri�o do QUID
	 */
	public IQUID getFramework() {
		return (IQUID) QuidService.obterInstancia();
	}

	/**
	 * M�todo respons�vel por executar qualquer a��o, a partir dele que chama o
	 * m�todo espec�fico para comunicar com o framework.
	 * 
	 * @param String
	 *            acao nome do m�todo que deve ser executado
	 * @return boolean se a��o foi excutada ou n�o
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
			throw new Exception("Erro ao chamar m�todo, contate o administrador do sistema." + "\nExce��o: " + e.getMessage());
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
