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
 * Classe de controle superior, tem o Serviço do Quid como principal método que
 * é acionado para executar qualquer ação de responsabilidade do framework
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SuperControle {

	private HashMap<String, Object> mapaAtributos;

	protected List<String> listaMensagensErro;

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
				String mensagemErro = "Erro ao chamar método, contate o administrador do sistema.";
				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					mensagemErro += "\nExceção: " + e.getMessage();
				}
				this.listaMensagensErro.add(mensagemErro);
				e.printStackTrace();
			}
		}
		return resultado;
	}

	/**
	 * Método executado antes de chamar a ação principal do controlador, se for
	 * retornado false cancela as ações seguintes.
	 * 
	 * @return boolean
	 */
	protected boolean preAcao() {
		return true;
	}

	/**
	 * Método executado depois de chamar a ação principal do controlador.
	 * 
	 * @return boolean
	 */
	protected boolean posAcao() {
		return true;
	}

	/**
	 * Retorna o mapa de atributos vindo da visão
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
