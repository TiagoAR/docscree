package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.controladores.ModalProjetoControle;
import br.ueg.unucet.docscree.interfaces.IAbrirProjetoVisao;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Compositor que representa o Modal para Abrir projeto
 * 
 * @author Diego
 *
 */
@Component
@Scope("session")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModalProjetoCompositor extends
		SuperCompositor<ModalProjetoControle> implements IAbrirProjetoVisao {

	/**
	 * DEFAULT SERIAL ID
	 */
	private static final long serialVersionUID = 8461942723989650170L;
	
	/**
	 * instancia da entidade
	 */
	private Projeto entidade;
	/**
	 * listagem de projetos possíveis a serem abertos
	 */
	private List<Projeto> listaEntidade;

	/**
	 * Método que abre Projeto, adicionando-o a sessão
	 */
	public void acaoAbrirProjeto() {
		super.binder.saveAll();
		try {
			boolean retorno = super.getControle().fazerAcao("abrirProjeto",
					(SuperCompositor) this);
			if (retorno) {
				setEntidade(null);
				mostrarMensagem(retorno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperCompositor#gerarWindowMensagem()
	 */
	@Override
	protected Window gerarWindowMensagem() {
		org.zkoss.zk.ui.Component componenteInicial = super.getComponent();
		while (super.getComponent() != null && !(super.getComponent() instanceof Div)) {
			super.setComponent(super.getComponent().getParent());
		}
		if (super.getComponent() == null) {
			super.setComponent(componenteInicial);
		}
		return super.gerarWindowMensagem();
	}

	/**
	 * @see IAbrirProjetoVisao#salvarSessaoProjeto()
	 */
	@Override
	public void salvarSessaoProjeto() {
		Executions.getCurrent().getSession()
				.setAttribute("projeto", this.getEntidade());
	}

	/**
	 * @see IAbrirProjetoVisao#getListaProjetos()
	 */
	@Override
	public List<Projeto> getListaProjetos() {
		try {
			Retorno<String,Collection<Projeto>> resultado = super.getControle().listar(getUsuarioSessao());
			if (resultado.isSucesso()) {
				this.setListaEntidade(new ArrayList<Projeto>(resultado.getParametros().get(Retorno.PARAMERTO_LISTA)));
			}
		} catch (Exception e) {
		}
		return (List<Projeto>) this.getListaEntidade();
	}


	/**
	 * @return Projeto o(a) entidade
	 */
	public synchronized Projeto getEntidade() {
		return entidade;
	}

	/**
	 * @param entidade o(a) entidade a ser setado(a)
	 */
	public synchronized void setEntidade(Projeto entidade) {
		this.entidade = entidade;
	}

	/**
	 * @return List<Projeto> o(a) listaEntidade
	 */
	public synchronized List<Projeto> getListaEntidade() {
		return listaEntidade;
	}

	/**
	 * @param listaEntidade o(a) listaEntidade a ser setado(a)
	 */
	public synchronized void setListaEntidade(List<Projeto> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}

}
