package br.ueg.unucet.docscree.controladores;

import java.lang.reflect.Method;
import java.util.HashMap;

import br.ueg.unucet.docscree.utilitarios.Mensagens;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.interfaces.IQUID;
import br.ueg.unucet.quid.servicos.QuidService;

/**
 * Classe de controle superior, tem o Serviço do Quid como principal método que
 * é acionado para executar qualquer ação de responsabilidade do framework.
 * Tem métodos comuns a todos os controladores, persistiveis ou não.
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SuperControle {

	/**
	 * Contém os atributos da visão
	 */
	private HashMap<String, Object> mapaAtributos;

	/**
	 * Guarda mensagens de sucessos personalizadas ou de erros da execução da ação.
	 */
	protected Mensagens mensagens;

	/**
	 * Método que retorna instancia do serviço do QUID framework.
	 * 
	 * @return IQUID interface que representa o seriço do QUID
	 */
	public IQUID getFramework() {
		return (IQUID) QuidService.obterInstancia();
	}
	
	/**
	 * Método que verifica se o usuário é do tipo Administrador
	 * @return boolean se o usário é Administrador ou não
	 */
	protected boolean isUsuarioAdmin() {
		if (((Usuario) this.getMapaAtributos().get("usuarioLogado")).getPerfilAcesso().equals(PerfilAcessoEnum.ADMINISTRADOR)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica se o usuário é do tipo Gerente
	 * @return boolean se o usário é Gerente ou não
	 */	
	protected boolean isUsuarioGerente() {
		if (((Usuario) this.getMapaAtributos().get("usuarioLogado")).getPerfilAcesso().equals(PerfilAcessoEnum.GERENTE)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método que verifica se o usuário é do tipo Usuário
	 * @return boolean se o usário é Usuário ou não
	 */
	protected boolean isUsuarioComum() {
		if (((Usuario) this.getMapaAtributos().get("usuarioLogado")).getPerfilAcesso().equals(PerfilAcessoEnum.USUARIO)) {
			return true;
		}
		return false;
	}

	/**
	 * Método responsável por executar qualquer ação, a partir dele que chama o
	 * método específico para comunicar com o framework.
	 * 
	 * @param pAcao
	 *            nome do método que deve ser executado
	 * @param pVisao visão que chamou a ação
	 * @return resultado se ação foi excutada ou não
	 * @throws Exception
	 */
	public boolean fazerAcao(String pAcao, SuperCompositor<SuperControle> pVisao)
			throws Exception {
		this.mensagens = new Mensagens();
		boolean resultado = false;
		try {
			setMapaAtributos(Reflexao.gerarMapeadorAtributos(pVisao));
			getMapaAtributos().put("usuarioLogado", (Usuario) pVisao.getUsuarioSessao());
			Class classeRef = this.getClass();
			if (!preAcao(pAcao)) {
				return false;
			}
			String nomeAcao = "acao" + pAcao.substring(0, 1).toUpperCase()
					+ pAcao.substring(1);
			Method metodo = classeRef.getMethod(nomeAcao);
			resultado = (Boolean) metodo.invoke(this);
			if (!posAcao(pAcao)) {
				return false;
			}
		} catch (Exception e) {
			if (this.mensagens.getListaMensagens().isEmpty()) {
				String mensagemErro = "Erro ao chamar método, contate o administrador do sistema.";
				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					mensagemErro += "\nExceção: " + e.getMessage();
				}
				this.mensagens.getListaMensagens().add(mensagemErro);
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
	protected boolean preAcao(String action) {
		return true;
	}

	/**
	 * Método executado depois de chamar a ação principal do controlador.
	 * 
	 * @return boolean
	 */
	protected boolean posAcao(String action) {
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
	 * @param mapaAtributos
	 */
	protected void setMapaAtributos(HashMap<String, Object> mapaAtributos) {
		this.mapaAtributos = mapaAtributos;
	}

	/**
	 * @return o(a) mensagens
	 */
	public Mensagens getMensagens() {
		return mensagens;
	}

}
