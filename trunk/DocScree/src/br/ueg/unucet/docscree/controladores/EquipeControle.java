package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.docscree.interfaces.IEquipeVisao;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.EquipeCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PapelUsuario;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Controle específico de Equipes
 * 
 * @author Diego
 * 
 */
public class EquipeControle extends GenericoControle<Equipe> {

	/**
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao(java.lang.
	 *      String)
	 */
	@Override
	protected boolean preAcao(String action) {
		if (action.equalsIgnoreCase("salvar")) {
			StatusEnum status = StatusEnum.ATIVO;
			if (((Boolean) super.getMapaAtributos().get("status"))
					.equals(Boolean.FALSE)) {
				status = StatusEnum.INATIVO;
			}
			super.getEntidade().setStatus(status);
			if (super.getEntidade().getEquipeUsuarios().isEmpty()) {
				super.getMensagens().getListaMensagens()
						.add("É necessário associar usuários a equipe");
				super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
				return false;
			}
			for (EquipeUsuario equipeUsuario : super.getEntidade()
					.getEquipeUsuarios()) {
				equipeUsuario.setEquipe(super.getEntidade());
			}
		}
		return true;
	}

	/**
	 * Método que salva uma equipe (edição ou inserção)
	 * 
	 * @return boolean retorno da ação com sucesso
	 */
	@Override
	public boolean acaoSalvar() {
		if (!super.isUsuarioComum()) {
				Retorno<String, Collection<String>> retorno;
				Equipe entidade = super.getEntidade();
				boolean contemGerente = this.contemGerente(entidade);
				if (entidade.getCodigo() == null) {
					if (!contemGerente) {
						EquipeUsuario usuarioGerente = new EquipeUsuario();
						usuarioGerente.setUsuario(super.getUsuarioLogado());
						usuarioGerente.setPapelUsuario(PapelUsuario.GERENTE);
						usuarioGerente.setEquipe(entidade);
						entidade.getEquipeUsuarios().add(usuarioGerente);
					}
					retorno = super.getFramework().inserirEquipe(
							entidade);
				} else {
					if (contemGerente) {
						if (super.isUsuarioAdmin()
								|| super.isMesmaEquipe(entidade)) {
							retorno = super.getFramework().alterarEquipe(
									entidade);
						} else {
							super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
							super.getMensagens()
									.getListaMensagens()
									.add("Gerente só pode editar equipes que faça parte");
							return false;
						}
					} else {
						super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
						super.getMensagens()
								.getListaMensagens()
								.add("Não é permitido existir Equipe sem Usuário com papel de Gerente!");
						return false;
					}
				}
			return super.montarRetorno(retorno);
		} else {
			super.montarMensagemErroPermissao("Usuário");
			return false;
		}
	}
	
	private boolean contemGerente(Equipe equipe) {
		for (EquipeUsuario iterador : equipe.getEquipeUsuarios()) {
			if (iterador.getPapelUsuario().equals(PapelUsuario.GERENTE)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método que efetua a ação de listagem de equipe
	 * 
	 * @return boolean se ação foi executada com sucesso
	 */
	@Override
	public boolean acaoListar() {
		if (!super.isUsuarioComum()) {
			return super.acaoListar();
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	/**
	 * Método que lista os usuários cadastrados e estão ativos.
	 * 
	 * @return List<Usuario> usuários ativos no sistema
	 */
	public List<Usuario> listarUsuarios() {
		Usuario usuario = new Usuario();
		usuario.setStatus(StatusEnum.ATIVO);
		Retorno<String, Collection<Usuario>> retorno = super.getFramework()
				.pesquisarUsuario(usuario);
		if (retorno.isSucesso()) {
			Collection<Usuario> listaEntidades = retorno.getParametros().get(
					Retorno.PARAMERTO_LISTA);
			return new ArrayList<Usuario>(listaEntidades);
		} else {
			return new ArrayList<Usuario>();
		}
	}

	/**
	 * Método que adiciona um usuário válido com seu papel já associado a equipe
	 * que será salva
	 * 
	 * @return boolean retorno se ação foi executada com sucesso
	 */
	public boolean acaoAdicionarEquipeUsuario() {
		boolean retorno = true;
		EquipeUsuario equipeUsuario = (EquipeUsuario) super.getMapaAtributos()
				.get("equipeUsuario");
		if (equipeUsuario.getPapelUsuario() == null
				|| equipeUsuario.getPapelUsuario().toString().isEmpty()) {
			super.getMensagens().getListaMensagens()
					.add("É necessário definir o papel do usuário!");
			super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			retorno = false;
		}
		if (equipeUsuario.getUsuario() == null
				|| equipeUsuario.getUsuario().getCodigo() == null
				|| equipeUsuario.getUsuario().getCodigo()
						.equals(Long.valueOf(0))) {
			super.getMensagens().getListaMensagens()
					.add("É necessário escolher um usuário válido!");
			super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			retorno = false;
		}
		if (retorno) {
			IEquipeVisao visao = (IEquipeVisao) super.getMapaAtributos().get(
					"visao");
			boolean contemUsuario = false;
			for (EquipeUsuario equipeUsuarioAdicionado : visao.getFldListaEquipeUsuario()) {
				if (equipeUsuarioAdicionado.getUsuario().equals(equipeUsuario.getUsuario()) 
						&& equipeUsuarioAdicionado.getPapelUsuario().equals(equipeUsuario.getPapelUsuario())) {
					contemUsuario = true;
					break;
				}
			}
			if (!contemUsuario) {
				visao.getFldListaEquipeUsuario().add(equipeUsuario);
				visao.getFldListaEquipeUsuario();
			} else {
				super.getMensagens().getListaMensagens()
						.add("Usuário já consta na lista com o papel escolhido!");
				retorno = false;
			}
		}
		return retorno;
	}

	/**
	 * @see GenericoControle#acaoExcluir()
	 */
	@Override
	public boolean acaoExcluir() {
		if (!super.isUsuarioComum()) {
			if (super.isUsuarioAdmin() || super.isMesmaEquipe(super.getEntidade())) {
				Retorno<String, Collection<String>> retorno;
				Equipe equipeInativar = super.getEntidade();
				equipeInativar.setStatus(StatusEnum.INATIVO);
				retorno = super.getFramework().alterarEquipe(equipeInativar);
				return super.montarRetorno(retorno);
			} else {
				super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
				super.getMensagens().getListaMensagens().add("Gerente só pode inativar equipe a que ele pertença");
				return false;
			}
		} else {
			super.montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	/**
	 * @see ICRUDControle#setarEntidadeVisao(SuperCompositor)
	 */
	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		EquipeCompositor visao = (EquipeCompositor) pVisao;
		Equipe equipeSelecionada = (Equipe) visao.getEntidade();
		visao.setCodigo(equipeSelecionada.getCodigo());
		visao.setFldNome(equipeSelecionada.getNome());
		boolean ativo = equipeSelecionada.getStatus().equals(StatusEnum.ATIVO);
		visao.setFldStatus(ativo);
		visao.setFldListaEquipeUsuario(equipeSelecionada.getEquipeUsuarios());
	}

	/**
	 * Executa a listagem de equipe via framework.
	 * 
	 * @return Retorno<String, Collection<Equipe>> retorno do framework
	 */
	@Override
	protected Retorno<String, Collection<Equipe>> executarListagem() {
		if (super.isUsuarioGerente()) {
			Retorno<String, Collection<Equipe>> retorno;
			Collection<Equipe> colecao = new ArrayList<Equipe>();
			for (EquipeUsuario equipeUsuario : super.getUsuarioLogado().getEquipeUsuarios()) {
				Equipe equipePesquisa = new Equipe();
				equipePesquisa.setCodigo(equipeUsuario.getEquipe().getCodigo());
				retorno = super.getFramework().pesquisarEquipe(equipePesquisa);
				if (retorno.isSucesso()) {
					colecao.addAll(retorno.getParametros().get(
							Retorno.PARAMERTO_LISTA));
				}
			}
			retorno = new Retorno<String, Collection<Equipe>>();
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, colecao);
			return retorno;
		}
		return super.getFramework().pesquisarEquipe(new Equipe());
	}

}
