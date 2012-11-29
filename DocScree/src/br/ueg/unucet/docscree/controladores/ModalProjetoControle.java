package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.ueg.unucet.docscree.interfaces.IAbrirProjetoVisao;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Controle sobre o Modal de Abertura de Projeto
 * 
 * @author Diego
 *
 */
public class ModalProjetoControle extends SuperControle {


	public Retorno<String, Collection<Projeto>> listar(Object usuario) {
		Projeto projeto = new Projeto();
		projeto.setStatus(StatusEnum.ATIVO);
		Usuario usuarioSessao = (Usuario) usuario;
		if (!usuarioSessao.getPerfilAcesso().equals(PerfilAcessoEnum.ADMINISTRADOR)) {
			Retorno<String, Collection<Projeto>> retorno;
			Collection<Projeto> colecao = new ArrayList<Projeto>();
			for (EquipeUsuario equipeUsuario : usuarioSessao.getEquipeUsuarios()) {
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
			if (projeto.getEquipe() == null) {
				Retorno<String, Collection<Projeto>> retornoVazio = new Retorno<String, Collection<Projeto>>(true, new HashMap<String, Collection<Projeto>> ());
				retornoVazio.getParametros().put(Retorno.PARAMERTO_LISTA, new ArrayList<Projeto>());
				return retornoVazio;
			}
		}
		return super.getFramework().pesquisarProjeto(projeto);
	}
	
	/**
	 * Método que "Abre" o Projeto, associando a instancia do projeto escolhido a sessão do usuário
	 * 
	 * @return boolean se ação foi executada
	 */
	public boolean acaoAbrirProjeto() {
		IAbrirProjetoVisao projetoVisao = (IAbrirProjetoVisao) super.getMapaAtributos().get("visao");
		projetoVisao.salvarSessaoProjeto();
		return true;
	}

}
