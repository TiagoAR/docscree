package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

	private Usuario usuarioLogado = null;

	/**
	 * Método sobrescrito para validar os dados a serem preenchidos do usuário
	 * Recebe os parâmetros da visão e os seta de forma correta na entidade.
	 * 
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao()
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
		if (!super.isUsuarioComum() || ((Usuario) super.getMapaAtributos().get("usuarioLogado")).getCodigo().equals(super.getEntidade().getCodigo())) {
			Retorno<String, Collection<String>> retorno;
			if (super.getEntidade().getCodigo() == null) {
				retorno = super.getFramework().inserirUsuario(super.getEntidade());
			} else {
				retorno = super.getFramework().alterarUsuario(super.getEntidade());
			}
			if (retorno.isSucesso()) {
				return true;
			} else {
				montarMensagemErro(retorno);
				return false;
			}
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	@Override
	public boolean acaoListar() {
		if (!isUsuarioComum()) {
			Retorno<String, Collection<Usuario>> retorno = super.getFramework()
					.pesquisarUsuario(new Usuario());
			if (retorno.isSucesso()) {
				Collection<Usuario> listaUsuario = retorno.getParametros().get(
						Retorno.PARAMERTO_LISTA);
				super.setLista(new ArrayList<Usuario>(listaUsuario));
				return true;
			} else {
				super.mensagens.getListaMensagens().add(retorno.getMensagem());
				return false;
			}
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	@Override
	public boolean acaoExcluir() {
		if (!isUsuarioComum()) {
			Retorno<String, Collection<String>> retorno;
			Usuario usuarioInativar = super.getEntidade();
			usuarioInativar.setStatus(StatusEnum.INATIVO);
			retorno = super.getFramework().alterarUsuario(usuarioInativar);
			if (retorno.isSucesso()) {
				return true;
			} else {
				montarMensagemErro(retorno);
				return false;
			}
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

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
	
	protected void montarMensagemErroPermissao(String tipoUsuario) {
		super.mensagens.getListaMensagens().add("O tipo de usuário "+tipoUsuario+" não tem permissão para executar a ação!");

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
				try {
					resultado = true;
					setUsuarioLogado(listaUsuario.iterator().next());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				super.mensagens.getListaMensagens().add("É necessário especificar um e-mail e senha cadastrados para logar!");
			}
		} else {
			super.mensagens.getListaMensagens().add(retorno.getMensagem());
		}
		return resultado;
	}
	
	public boolean acaoAtualizarUsuarioLogado() {
		boolean resultado = false;
		Usuario usuario = new Usuario();
		usuario.setCodigo(((Usuario) super.getMapaAtributos().get("usuarioLogado")).getCodigo());
		Retorno<String, Collection<Usuario>> retorno = super.getFramework()
				.pesquisarUsuario(usuario);
		if (retorno.isSucesso()) {
			Collection<Usuario> listaUsuario = retorno.getParametros().get(
					Retorno.PARAMERTO_LISTA);
			setUsuarioLogado(listaUsuario.iterator().next());
		} else {
			super.mensagens.getListaMensagens().add("Problema em atualizar o nome de usuário, tente novamente!");
		}
		return resultado;
		
	}

	/**
	 * @return o(a) usuarioLogado
	 */
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	/**
	 * @param usuarioLogado
	 *            o(a) usuarioLogado a ser setado(a)
	 */
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
