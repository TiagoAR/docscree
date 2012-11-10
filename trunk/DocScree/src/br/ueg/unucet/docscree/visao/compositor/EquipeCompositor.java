package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.SimpleListModel;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.EquipeControle;
import br.ueg.unucet.docscree.interfaces.IEquipeVisao;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PapelUsuario;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Classe da visão que representa o caso de uso Manter Equipe; Composer da
 * Equipe no ZK
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
@Scope("session")
public class EquipeCompositor extends GenericoCompositor<EquipeControle>
		implements IEquipeVisao {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 7456067099003306099L;

	/**
	 * Campo nome da equipe
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "nome")
	private String fldNome;

	/**
	 * Campo status da equipe
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "status")
	private Boolean fldStatus = Boolean.TRUE;

	/**
	 * lista de usuários a serem associados a equipe
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "equipeUsuarios")
	private Set<EquipeUsuario> fldListaEquipeUsuario;

	/**
	 * Relacionamento de Equipe usuário a ser inserido na lista de usuários da
	 * equipe
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "equipeUsuario")
	private EquipeUsuario equipeUsuario = new EquipeUsuario();

	/**
	 * EquipeUsuário da lista de relacionamento selecionado
	 */
	private EquipeUsuario equipeUsuarioSelecionado;

	/**
	 * BindingList do ZK para exibir lista de EquipeUsuario na visão
	 */
	private BindingListModelListModel<EquipeUsuario> modelEquipeUsuario;

	/**
	 * Filtro da lista de equipe para código
	 */
	private String filtroCodigo = "";

	/**
	 * Filtro da lista de equipe para nome
	 */
	private String filtroNome = "";

	/**
	 * @see GenericoCompositor#getTipoEntidade()
	 */
	@Override
	public Class getTipoEntidade() {
		return Equipe.class;
	}

	/**
	 * @see GenericoCompositor#limparCampos()
	 */
	@Override
	protected void limparCampos() {
		setFldNome("");
		setCodigo(null);
		setFldStatus(Boolean.TRUE);
		setFldListaEquipeUsuario(new HashSet<EquipeUsuario>());
		super.binder.loadAll();
	}

	/**
	 * @see GenericoCompositor#limparFiltros()
	 */
	@Override
	protected void limparFiltros() {
		setFiltroCodigo("");
		setFiltroNome("");
	}

	/**
	 * @see GenericoCompositor#acaoFiltrar()
	 */
	@Override
	public void acaoFiltrar() {
		List<Equipe> listaEquipe = new ArrayList<Equipe>();
		super.binder.saveAll();
		for (Object objeto : super.getListaEntidade()) {
			Equipe equipe = (Equipe) objeto;
			if (equipe.getCodigo().toString().trim().toLowerCase()
					.contains(getFiltroCodigo().trim().toLowerCase())
					&& equipe.getNome().trim().toLowerCase()
							.contains(getFiltroNome().trim().toLowerCase())) {
				if (getExibirInativos()
						|| equipe.getStatus().toString().toLowerCase()
								.equals("ativo"))
					listaEquipe.add(equipe);
			}
		}
		super.setListaEntidadeModelo(new BindingListModelListModel<Equipe>(
				new SimpleListModel<Equipe>(listaEquipe)));
		super.binder.loadAll();
	}

	/**
	 * @see IEquipeVisao#acaoAdicionarUsuario()
	 */
	@Override
	public void acaoAdicionarUsuario() {
		super.binder.saveAll();
		try {
			boolean retorno = super.getControle().fazerAcao(
					"adicionarEquipeUsuario", (SuperCompositor) this);
			if (retorno) {
				this.setEquipeUsuario(new EquipeUsuario());
				super.binder.loadAll();
			} else {
				super.mostrarMensagem(retorno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.GenericoCompositor#atualizarEntidadeExcluida(int)
	 */
	@Override
	protected void atualizarEntidadeExcluida(int index) {
		((Equipe) this.getListaEntidade().get(index))
				.setStatus(StatusEnum.INATIVO);
		acaoFiltrar();
		super.binder.loadAll();
	}

	/**
	 * @see br.ueg.unucet.docscree.interfaces.IEquipeVisao#acaoRemoverUsuario()
	 */
	@Override
	public void acaoRemoverUsuario() {
		super.binder.saveAll();
		if (getEquipeUsuarioSelecionado() != null
				&& getEquipeUsuarioSelecionado().getUsuario().getCodigo() > 0) {
			this.getFldListaEquipeUsuario().remove(
					this.getEquipeUsuarioSelecionado());
			super.binder.loadAll();
		}
	}

	/**
	 * @see GenericoCompositor#acaoSalvar()
	 */
	public void acaoSalvar() {
		super.binder.loadAll();
		try {
			boolean retorno = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			if (retorno) {
				limparCampos();
				super.getControle().acaoAtualizarUsuarioLogado();
				this.salvarSessaoUsuario(super.getControle().getUsuarioLogado());
			}
			super.mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Caso seja alterado a equipe do usuário, salva dados do mesmo atualizado na sessão.
	 * 
	 * @param usuario
	 */
	public void salvarSessaoUsuario(Usuario usuario) {
		Executions.getCurrent().getSession().setAttribute("usuario", usuario);
	}

	/**
	 * Método que retorna a lista de usuários ativos no sistema
	 * 
	 * @return List<Usuario>
	 */
	public List<Usuario> getListaUsuarios() {
		return super.getControle().listarUsuarios();
	}

	/**
	 * Método que retorna os papeis de usuário
	 * 
	 * @return PapelUsuario[]
	 */
	public PapelUsuario[] getListaPapeis() {
		return PapelUsuario.values();
	}

	/**
	 * @return o(a) fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param fldNome
	 *            o(a) fldNome a ser setado(a)
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return Boolean o(a) fldStatus
	 */
	public Boolean getFldStatus() {
		return fldStatus;
	}

	/**
	 * @param fldStatus
	 *            o(a) fldStatus a ser setado(a)
	 */
	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}

	/**
	 * @return Set<EquipeUsuario> o(a) fldListaEquipeUsuario
	 */
	public Set<EquipeUsuario> getFldListaEquipeUsuario() {
		if (this.fldListaEquipeUsuario == null) {
			this.fldListaEquipeUsuario = new HashSet<EquipeUsuario>();
		}
		return fldListaEquipeUsuario;
	}

	/**
	 * @param listaEquipeUsuario o(a) fldListaEquipeUsuario a ser setado(a)
	 */
	public void setFldListaEquipeUsuario(Set<EquipeUsuario> listaEquipeUsuario) {
		this.fldListaEquipeUsuario = listaEquipeUsuario;
	}

	/**
	 * @return EquipeUsuario o(a) equipeUsuario
	 */
	public EquipeUsuario getEquipeUsuario() {
		return equipeUsuario;
	}

	/**
	 * @param equipeUsuario
	 *            o(a) equipeUsuario a ser setado(a)
	 */
	public void setEquipeUsuario(EquipeUsuario equipeUsuario) {
		this.equipeUsuario = equipeUsuario;
	}

	/**
	 * @return BindingListModelListModel<EquipeUsuario> o(a) modelEquipeUsuario
	 */
	public BindingListModelListModel<EquipeUsuario> getModelEquipeUsuario() {
		this.modelEquipeUsuario = new BindingListModelListModel<EquipeUsuario>(
				new SimpleListModel<EquipeUsuario>(getFldListaEquipeUsuario()
						.toArray(new EquipeUsuario[0])));
		return modelEquipeUsuario;
	}

	/**
	 * @param modelEquipeUsuario o(a) modelEquipeUsuario a ser setado(a)
	 */
	public void setModelEquipeUsuario(
			BindingListModelListModel<EquipeUsuario> modelEquipeUsuario) {
		this.modelEquipeUsuario = modelEquipeUsuario;
	}

	/**
	 * @return EquipeUsuario o(a) equipeUsuarioSelecionado
	 */
	public EquipeUsuario getEquipeUsuarioSelecionado() {
		return equipeUsuarioSelecionado;
	}

	/**
	 * @param equipeUsuarioSelecionado
	 *            o(a) equipeUsuarioSelecionado a ser setado(a)
	 */
	public void setEquipeUsuarioSelecionado(
			EquipeUsuario equipeUsuarioSelecionado) {
		this.equipeUsuarioSelecionado = equipeUsuarioSelecionado;
	}

	/**
	 * @return String o(a) filtroCodigo
	 */
	public String getFiltroCodigo() {
		return filtroCodigo;
	}

	/**
	 * @param filtroCodigo
	 *            o(a) filtroCodigo a ser setado(a)
	 */
	public void setFiltroCodigo(String filtroCodigo) {
		this.filtroCodigo = filtroCodigo;
	}

	/**
	 * @return String o(a) filtroNome
	 */
	public String getFiltroNome() {
		return filtroNome;
	}

	/**
	 * @param filtroNome
	 *            o(a) filtroNome a ser setado(a)
	 */
	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

}
