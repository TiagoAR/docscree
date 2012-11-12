package br.ueg.unucet.docscree.controladores;

import java.util.Collection;
import java.util.Iterator;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.docscree.utilitarios.Conversor;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.docscree.visao.compositor.UsuarioCompositor;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.excessoes.UsuarioExcessao;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Controlador específico de Usuários
 * 
 * @author Diego
 * 
 */
public class UsuarioControle extends GenericoControle<Usuario> {

	/**
	 * Método sobrescrito para validar os dados a serem preenchidos do usuário
	 * Recebe os parâmetros da visão e os seta de forma correta na entidade.
	 * 
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao(String)
	 */
	@Override
	protected boolean preAcao(String action) {
		boolean retorno = true;
		if (action.equalsIgnoreCase("salvar")
				|| action.equalsIgnoreCase("excluir")) {
			try {
				getEntidade().setPerfilAcesso(
						Conversor.castParaEnum(
								PerfilAcessoEnum.class,
								(String) super.getMapaAtributos().get(
										"perfilAcesso")));
			} catch (Exception e) {
				super.getMensagens()
						.getListaMensagens()
						.add("Não foi escolhido um Perfil de Acesso Válido!\nSelecione um perfil!");
				super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
				retorno = false;
			}
			boolean status = (boolean) super.getMapaAtributos().get("status");
			StatusEnum statusEnum;
			if (status) {
				statusEnum = StatusEnum.ATIVO;
			} else {
				statusEnum = StatusEnum.INATIVO;
			}
			super.getEntidade().setStatus(statusEnum);
			if (super.getEntidade().getSenha() != null
					&& !super
							.getEntidade()
							.getSenha()
							.equals((String) super.getMapaAtributos().get(
									"confirmarSenha"))) {
				super.mensagens
						.getListaMensagens()
						.add("O campo Confirmar Senha não confere com a Senha digitada, verifique!");
				retorno = false;
			}
		}
		return retorno;
	}

	/**
	 * Ação Salvar usuário, se usuário for salvo retorna true, caso contrário
	 * preenche lista de mensagens de erro e retorna false.
	 * 
	 * @return boolean se ação foi executada
	 */
	@Override
	public boolean acaoSalvar() {
		if (!super.isUsuarioComum() 
				|| (getUsuarioLogado().getCodigo().equals(super.getEntidade().getCodigo()) 
						&& getUsuarioLogado().getPerfilAcesso().equals(super.getEntidade().getPerfilAcesso()))) {
			if (!(super.isUsuarioGerente() && super.getEntidade().getPerfilAcesso().equals(PerfilAcessoEnum.ADMINISTRADOR))) {
				Retorno<String, Collection<String>> retorno;
				if (super.getEntidade().getCodigo() == null) {
					retorno = super.getFramework().inserirUsuario(super.getEntidade());
				} else {
					retorno = super.getFramework().alterarUsuario(super.getEntidade());
				}
				return super.montarRetorno(retorno);
			} else {
				montarMensagemErroPermissao("Gerente");
				return false;
			}
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}
	
	/**
	 * Método que efetua a ação de editar o próprio usuário. 
	 * Efetua update no banco e altera dados na sessão
	 * 
	 * @return boolean se método foi executado
	 */
	public boolean acaoEditarProprioUsuario() {
		if (super.getEntidade().getSenha().equals(super.getUsuarioLogado().getSenha())) {
			if (super.getMapaAtributos().get("status").equals(Boolean.TRUE)) {
				((UsuarioCompositor) getVisao()).setFldSenha((String) super.getMapaAtributos().get(
						"confirmarSenha"));
				return true;
			} else {
				getMensagens().getListaMensagens().add("Não é possível desativar o próprio usuário!");
			}
		} else {
			getMensagens().getListaMensagens().add("Senha Anterior não confere, tente novamente.");
		}
		getMensagens().setTipoMensagem(TipoMensagem.ERRO);
		return false;
	}
	
	/**
	 * @see GenericoControle#executarListagem()
	 */
	@Override
	protected Retorno<String, Collection<Usuario>> executarListagem() {
		return super.getFramework().pesquisarUsuario(new Usuario());
	}

	/**
	 * @see GenericoControle#acaoListar()
	 */
	@Override
	public boolean acaoListar() {
		if (!isUsuarioComum()) {
			return super.acaoListar();
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	/**
	 * @see GenericoControle#acaoExcluir()
	 */
	@Override
	public boolean acaoExcluir() {
		if (!isUsuarioComum()) {
			Retorno<String, Collection<String>> retorno;
			Usuario usuarioInativar = super.getEntidade();
			usuarioInativar.setStatus(StatusEnum.INATIVO);
			retorno = super.getFramework().alterarUsuario(usuarioInativar);
			return super.montarRetorno(retorno);
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	/**
	 * @see ICRUDControle#setarEntidadeVisao(SuperCompositor)
	 */
	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		UsuarioCompositor visao = (UsuarioCompositor) pVisao;
		Usuario usuarioSelecionado = (Usuario) visao.getEntidade();
		visao.setCodigo(usuarioSelecionado.getCodigo());
		visao.setFldSenha(usuarioSelecionado.getSenha());
		visao.setFldNome(usuarioSelecionado.getNome());
		boolean ativo = usuarioSelecionado.getStatus().equals(StatusEnum.ATIVO);
		visao.setFldStatus(ativo);
		visao.setFldConfirmarSenha(usuarioSelecionado.getSenha());
		visao.setFldEmail(usuarioSelecionado.getEmail());
		visao.setFldPerfilAcesso(usuarioSelecionado.getPerfilAcesso()
				.toString());
	}

	/**
	 * @see GenericoControle#montarMensagemErro(Retorno)
	 */
	@Override
	protected void montarMensagemErro(
			Retorno<String, Collection<String>> retorno) {
		String mensagemErro;
		Throwable erro = retorno.getErro();
		mensagemErro = erro.getMessage();
		if (erro instanceof UsuarioExcessao) {
			if (((UsuarioExcessao) erro).getAtributosNaoInformados() != null) {
				Iterator<String> iterador = ((UsuarioExcessao) erro)
						.getAtributosNaoInformados().iterator();
				if (iterador.hasNext()) {
					mensagemErro += ": " + iterador.next();
				}
				while (iterador.hasNext()) {
					String campoNaoInformado = (String) iterador.next();
					mensagemErro += ", " + campoNaoInformado;
				}
			}
		}
		super.mensagens.getListaMensagens().add(mensagemErro);

	}

	/**
	 * Método que executa a ação de Logar, verifica se e-mail e senha existem no
	 * banco de dados
	 * 
	 * @return boolean resultado se ação foi executada.
	 */
	public boolean acaoLogar() {
		boolean resultado = false;
		Usuario usuario = super.getEntidade();
		if (usuario.getEmail() == null) {
			usuario.setEmail("");
		}
		if (usuario.getSenha() == null) {
			usuario.setSenha("");
		}
		Retorno<String, Collection<Usuario>> retorno = super.getFramework()
				.pesquisarUsuario(super.getEntidade());
		if (retorno.isSucesso()) {
			Collection<Usuario> listaUsuario = retorno.getParametros().get(
					Retorno.PARAMERTO_LISTA);
			Usuario usuarioComparar = listaUsuario.iterator().next();
			if (!listaUsuario.isEmpty()
					&& usuarioComparar.getEmail().equals(usuario.getEmail())
					&& usuarioComparar.getSenha().equals(usuario.getSenha())) {
				if (usuarioComparar.getStatus().equals(StatusEnum.ATIVO)) {
					try {
						resultado = true;
						setUsuarioLogado(usuarioComparar);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					super.mensagens.getListaMensagens().add("O Usuário não está ativo!");
				}
			} else {
				super.mensagens.getListaMensagens().add("É necessário especificar um e-mail e senha cadastrados para logar!");
			}
		} else {
			super.mensagens.getListaMensagens().add(retorno.getMensagem());
		}
		return resultado;
	}
}
