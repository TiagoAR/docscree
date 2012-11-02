package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.docscree.interfaces.IAbrirProjetoVisao;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

public class ModalProjetoControle extends GenericoControle<Projeto> {

	@Override
	public boolean acaoSalvar() {
		return false;
	}

	@Override
	public boolean acaoExcluir() {
		return false;
	}

	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		
	}

	@Override
	protected Retorno<String, Collection<Projeto>> executarListagem() {
		Projeto projeto = new Projeto();
		projeto.setStatus(StatusEnum.ATIVO);
		if (!super.isUsuarioAdmin()) {
			Retorno<String, Collection<Projeto>> retorno;
			Collection<Projeto> colecao = new ArrayList<Projeto>();
			for (EquipeUsuario equipeUsuario : super.getUsuarioLogado().getEquipeUsuarios()) {
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
		return super.getFramework().pesquisarProjeto(projeto);
	}
	
	/**
	 * Método que "Abre" o Projeto, associando a instancia do projeto escolhido a sessão do usuário
	 * 
	 * @return
	 */
	public boolean acaoAbrirProjeto() {
		IAbrirProjetoVisao projetoVisao = (IAbrirProjetoVisao) super.getMapaAtributos().get("visao");
		projetoVisao.salvarSessaoProjeto();
		return true;
	}

}
