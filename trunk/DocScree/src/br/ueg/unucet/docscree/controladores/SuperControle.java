package br.ueg.unucet.docscree.controladores;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.Reflexao;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PapelUsuario;
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
	 * Guarda instancia do usuário logado para análise de acesso do usuário
	 */
	protected Usuario usuarioLogado = null;

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
	 * Método que verifica se o usuário logado pertence a equipe passada como parâmetro.
	 * 
	 * @param pEquipe equipe a ser verificada
	 * @return boolean se o usuário logado pertence ou não a equipe
	 */
	protected boolean isMesmaEquipe(Equipe pEquipe) {
		for (EquipeUsuario equipeUsuario : ((Usuario) this.getMapaAtributos().get("usuarioLogado")).getEquipeUsuarios()) {
			if (equipeUsuario.getEquipe().equals(pEquipe)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método que lista os papeis do usuário logado para as equipes que está associado
	 * 
	 * @return List<PapelUsuario> listaPapelUsuario
	 */
	protected List<PapelUsuario> listarPapeisDoUsuario() {
		List<PapelUsuario> listaPapelUsuario = new ArrayList<PapelUsuario>();
		for (EquipeUsuario equipeUsuario : ((Usuario) this.getMapaAtributos().get("usuarioLogado")).getEquipeUsuarios()) {
			PapelUsuario papelUsuario = equipeUsuario.getPapelUsuario();
			if (papelUsuario != null) {
				listaPapelUsuario.add(papelUsuario);
			}
		}
		return listaPapelUsuario;
	}
	
	/**
	 * Método que retorna o usuário logado
	 * @return Usuario usuario logado
	 */
	public Usuario getUsuarioLogado() {
		if (this.usuarioLogado == null) {
			this.usuarioLogado = (Usuario) this.getMapaAtributos().get("usuarioLogado");
		}
		return this.usuarioLogado;
	}
	
	/**
	 * Método que retorna o projeto salvo na sessão
	 * 
	 * @return Projeto
	 */
	protected Projeto getProjeto() {
		return (Projeto) this.getMapaAtributos().get("projetoAberto");
	}

	/**
	 * Método que traz instancia da visão que está chamando o método fazerAcao()
	 * 
	 * @return SuperCompositor visão
	 */
	protected SuperCompositor getVisao() {
		return (SuperCompositor) this.getMapaAtributos().get("visao");
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
			getMapaAtributos().put("projetoAberto", (Projeto) pVisao.getProjetoSessao());
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
	 * Método que atualiza as informações do usuário logado após ser editado
	 * 
	 * @return boolean se ação foi executada com sucesso
	 */
	public boolean acaoAtualizarUsuarioLogado() {
		boolean resultado = false;
		Usuario usuario = new Usuario();
		usuario.setCodigo(((Usuario) this.getMapaAtributos().get("usuarioLogado")).getCodigo());
		Retorno<String, Collection<Usuario>> retorno = this.getFramework()
				.pesquisarUsuario(usuario);
		if (retorno.isSucesso()) {
			Collection<Usuario> listaUsuario = retorno.getParametros().get(
					Retorno.PARAMERTO_LISTA);
			setUsuarioLogado(listaUsuario.iterator().next());
		} else {
			this.mensagens.getListaMensagens().add("Problema em atualizar o nome de usuário, tente novamente!");
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

	/**
	 * @param Mensagens o(a) mensagens a ser setado(a)
	 */
	public void setMensagens(Mensagens mensagens) {
		this.mensagens = mensagens;
	}

	/**
	 * @param Usuario o(a) usuarioLogado a ser setado(a)
	 */
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
