package br.ueg.unucet.docscree.controladores;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.interfaces.IQUID;
import br.ueg.unucet.quid.servicos.QuidService;

/**
 * Classe de controle superior, tem o Servi�o do Quid como principal m�todo que
 * � acionado para executar qualquer a��o de responsabilidade do framework
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SuperControle {

	private HashMap<String, Object> mapaAtributos;

	protected List<String> listaMensagensErro;

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
	public boolean fazerAcao(String pAcao, SuperCompositor<SuperControle> pVisao)
			throws Exception {
		this.listaMensagensErro = new ArrayList<String>();
		boolean resultado = false;
		try {
			setMapaAtributos(Reflexao.gerarMapeadorAtributos(pVisao));
			Class classeRef = this.getClass();
			if (!preAcao()) {
				return false;
			}
			String nomeAcao = "acao" + pAcao.substring(0, 1).toUpperCase()
					+ pAcao.substring(1).toLowerCase();
			Method metodo = classeRef.getMethod(nomeAcao);
			resultado = (Boolean) metodo.invoke(this);
			if (!posAcao()) {
				return false;
			}
		} catch (Exception e) {
			if (listaMensagensErro.isEmpty()) {
				String mensagemErro = "Erro ao chamar m�todo, contate o administrador do sistema.";
				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					mensagemErro += "\nExce��o: " + e.getMessage();
				}
				this.listaMensagensErro.add(mensagemErro);
				e.printStackTrace();
			}
		}
		return resultado;
	}

	/**
	 * M�todo executado antes de chamar a a��o principal do controlador, se for
	 * retornado false cancela as a��es seguintes.
	 * 
	 * @return boolean
	 */
	protected boolean preAcao() {
		return true;
	}

	/**
	 * M�todo executado depois de chamar a a��o principal do controlador.
	 * 
	 * @return boolean
	 */
	protected boolean posAcao() {
		return true;
	}

	/**
	 * Retorna o mapa de atributos vindo da vis�o
	 * 
	 * @return HashMap<String, Object> mapa de atributos
	 */
	protected HashMap<String, Object> getMapaAtributos() {
		return mapaAtributos;
	}

	/**
	 * Seta o mapa de atributos
	 * 
	 * @param HashMap
	 *            <String, Object> mapa de atributos
	 */
	protected void setMapaAtributos(HashMap<String, Object> mapaAtributos) {
		this.mapaAtributos = mapaAtributos;
	}

	/**
	 * @return List<String> o(a) listaMensagensErro
	 */
	public List<String> getListaMensagensErro() {
		return listaMensagensErro;
	}

}
