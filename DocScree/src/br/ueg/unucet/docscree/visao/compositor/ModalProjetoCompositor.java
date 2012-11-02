package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;

import br.ueg.unucet.docscree.controladores.ModalProjetoControle;
import br.ueg.unucet.docscree.interfaces.IAbrirProjetoVisao;
import br.ueg.unucet.quid.dominios.Projeto;

@Component
@Scope("session")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModalProjetoCompositor extends
		GenericoCompositor<ModalProjetoControle> implements IAbrirProjetoVisao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8461942723989650170L;

	public void acaoAbrirProjeto() {
		super.binder.saveAll();
		try {
			boolean retorno = super.getControle().fazerAcao("abrirProjeto",
					(SuperCompositor) this);
			if (retorno) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void salvarSessaoProjeto() {
		Executions.getCurrent().getSession()
				.setAttribute("projeto", super.getEntidade());
	}

	@Override
	public List<Projeto> getListaProjetos() {
		try {
			boolean resultado = super.getControle().fazerAcao("listar",
					(SuperCompositor) this);
			if (resultado) {
				this.setListaEntidade(super.getControle().getLista());
			}
		} catch (Exception e) {
		}
		return (List<Projeto>) this.getListaEntidade();
	}

	@Override
	public Class getTipoEntidade() {
		return Projeto.class;
	}

	@Override
	protected void limparCampos() {
	}

	@Override
	protected void limparFiltros() {
	}

	@Override
	public void acaoFiltrar() {
	}

}
