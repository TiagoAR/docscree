package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.ProjetoCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

public class ProjetoControle extends GenericoControle<Projeto> {

	/* (non-Javadoc)
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao(java.lang.String)
	 */
	@Override
	protected boolean preAcao(String action) {

		if (action.equalsIgnoreCase("salvar")) {
			boolean status = (boolean) super.getMapaAtributos().get("status");
			StatusEnum statusEnum;
			if (status) {
				statusEnum = StatusEnum.ATIVO;
			} else {
				statusEnum = StatusEnum.INATIVO;
			}
			super.getEntidade().setStatus(statusEnum);
			Collection<String> camposNaoInformados = new ArrayList<String>();
			if (super.getEntidade().getEquipe() == null 
					|| super.getEntidade().getEquipe().getCodigo() == null 
					|| super.getEntidade().getEquipe().getCodigo() == 0) {
				camposNaoInformados.add("equipe");
			}
			if (super.getEntidade().getModelo() == null 
					|| super.getEntidade().getModelo().getCodigo() == null 
					|| super.getEntidade().getModelo().getCodigo() == 0) {
				camposNaoInformados.add("modelo");
			}
			if (!camposNaoInformados.isEmpty()) {
				StringBuilder str = new StringBuilder();
				str.append("Atributos não informados:");
				for (String string : camposNaoInformados) {
					str.append(" "+ string);
				}
				super.getMensagens().getListaMensagens().add(str.toString());
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean acaoSalvar() {
		if (!super.isUsuarioComum()) {
			Retorno<Object, Object> retorno;
			Projeto entidade = super.getEntidade();
			if (super.isUsuarioGerente()) {
				boolean mesmaEquipe = false;
				for (EquipeUsuario equipeUsuario : super.getUsuarioLogado().getEquipeUsuarios()) {
					if (equipeUsuario.getEquipe().equals(entidade.getEquipe())) {
						mesmaEquipe = true;
						break;
					}
				}
				if (!mesmaEquipe) {
					super.getMensagens().getListaMensagens().add("Gerentes só podem criar projetos para suas equipes!");
					super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
					return false;
				}
			}
			if (entidade.getCodigo() == null || entidade.getCodigo() == 0) {
				retorno = super.getFramework().inserirProjeto(entidade);
			} else {
				retorno = super.getFramework().alterarProjeto(entidade);
			}
			if (retorno.isSucesso()) {
				return true;
			} else {
				String mensagemErro = retorno.getMensagem();
				super.mensagens.getListaMensagens().add(mensagemErro);
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean acaoExcluir() {
		if (super.isUsuarioAdmin()) {
			super.getEntidade().setStatus(StatusEnum.INATIVO);
			Retorno<Object, Object> retorno = super.getFramework().alterarProjeto(super.getEntidade());
			if (retorno.isSucesso()) {
				return true;
			} else {
				super.getMensagens().getListaMensagens().add(retorno.getMensagem());
				return false;
			}
		}
		super.getMensagens().getListaMensagens().add("É necessário ser Administrador para executar a ação");
		return false;
	}

	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		ProjetoCompositor visao = (ProjetoCompositor) pVisao;
		Projeto projeto = (Projeto) visao.getEntidade();
		visao.setFldEquipe(projeto.getEquipe());
		visao.setFldModelo(projeto.getModelo());
		visao.setFldNome(projeto.getNome());
	}

	@Override
	protected Retorno<String, Collection<Projeto>> executarListagem() {
		if (!super.isUsuarioAdmin()) {
			Retorno<String, Collection<Projeto>> retorno;
			Collection<Projeto> colecao = new ArrayList<Projeto>();
			for (EquipeUsuario equipeUsuario : super.getUsuarioLogado().getEquipeUsuarios()) {
				Projeto projeto = new Projeto();
				projeto.setEquipe(new Equipe());
				projeto.getEquipe().setCodigo(equipeUsuario.getEquipe().getCodigo());
				retorno = super.getFramework().pesquisarProjeto(projeto);
				if (retorno.isSucesso()) {
					colecao.addAll(retorno.getParametros().get(
							Retorno.PARAMERTO_LISTA));
				}
				retorno = new Retorno<String, Collection<Projeto>>();
				retorno.setSucesso(true);
				retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, colecao);
				return retorno;
			}
		}
		return super.getFramework().pesquisarProjeto(new Projeto());
	}
	
	public List<Equipe> listarEquipes(Object objeto) {
		Usuario usuario = (Usuario) objeto;
		if (usuario.getPerfilAcesso().equals(PerfilAcessoEnum.GERENTE)) {
			List<Equipe> colecao = new ArrayList<Equipe>();
			for (EquipeUsuario equipeUsuario : usuario.getEquipeUsuarios()) {
				Equipe equipePesquisa = new Equipe();
				equipePesquisa.setCodigo(equipeUsuario.getEquipe().getCodigo());
				Retorno<String, Collection<Equipe>> retorno = super.getFramework().pesquisarEquipe(equipePesquisa);
				if (retorno.isSucesso()) {
					colecao.addAll(retorno.getParametros().get(
							Retorno.PARAMERTO_LISTA));
				}
			}
			return colecao;
		}
		Retorno<String, Collection<Equipe>> retorno = super.getFramework().pesquisarEquipe(new Equipe());
		return new ArrayList<Equipe>(retorno.getParametros().get(Retorno.PARAMERTO_LISTA));
	}
	
	public List<Modelo> listarModelos() {
		Retorno<String, Collection<Modelo>> retorno = super.getFramework().listarModelo();
		if (retorno.isSucesso()) {
			return new ArrayList<Modelo>(retorno.getParametros().get(Retorno.PARAMERTO_LISTA));
		}
		return null;
	}

}
