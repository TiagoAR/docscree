package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ProjetoControle;
import br.ueg.unucet.docscree.interfaces.IProjetoVisao;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Classe da visão que representa o caso de uso Manter projeto; Composer do
 * Projeto no ZK
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
@Scope("session")
public class ProjetoCompositor extends GenericoCompositor<ProjetoControle>
		implements IProjetoVisao {

	// Campos da entidade
	/**
	 * Campo nome da entidade
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "nome")
	private String fldNome;
	/**
	 * Campo descrição da entidade
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "descricao")
	private String fldDescricao;
	/**
	 * Representa a equipe da entidade
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "equipe")
	private Equipe fldEquipe = new Equipe();
	/**
	 * Representa o modelo da entidade
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "modelo")
	private Modelo fldModelo = new Modelo();
	/**
	 * Campo status da entidade
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "status")
	private Boolean fldStatus = Boolean.TRUE;
	// Fim dos campos da entidade||Inicio campos de filtro
	/**
	 * Campo de filtro através do código
	 */
	private String filtroCodigo = "";
	/**
	 * Campo de filtro através do nome
	 */
	private String filtroNome = "";
	/**
	 * Campo de filtro através da equipe
	 */
	private String filtroEquipe = "";
	/**
	 * Campo de filtro através do modelo
	 */
	private String filtroModelo = "";

	/**
	 * Serial ID Default
	 */
	private static final long serialVersionUID = -8315698304806410765L;

	/**
	 * @see GenericoCompositor#getTipoEntidade()
	 */
	@Override
	public Class getTipoEntidade() {
		return Projeto.class;
	}

	/**
	 * @see GenericoCompositor#limparCampos()
	 */
	@Override
	protected void limparCampos() {
		setFldEquipe(new Equipe());
		setFldModelo(new Modelo());
		setFldNome("");
		setFldDescricao("");
		setFldStatus(Boolean.TRUE);
		super.binder.loadAll();
	}

	/**
	 * @see GenericoCompositor#limparFiltros()
	 */
	@Override
	protected void limparFiltros() {
		setFiltroCodigo("");
		setFiltroEquipe("");
		setFiltroModelo("");
		setFiltroNome("");
	}

	/**
	 * @see GenericoCompositor#acaoFiltrar()
	 */
	@Override
	public void acaoFiltrar() {
		List<Projeto> listaProjeto = new ArrayList<Projeto>();
		super.binder.saveAll();
		for (Object objeto : super.getListaEntidade()) {
			Projeto projeto = (Projeto) objeto;
			if (String.valueOf(projeto.getCodigo()).trim().toLowerCase()
					.contains(getFiltroCodigo().trim().toLowerCase())
					&& projeto.getNome().trim().toLowerCase()
							.contains(getFiltroNome().trim().toLowerCase())
					&& projeto.getEquipe().getNome().toLowerCase().trim()
							.contains(getFiltroEquipe().trim().toLowerCase())
					&& projeto.getModelo().getNome().toLowerCase().trim()
							.contains(getFiltroModelo().trim().toLowerCase())) {
				if (getExibirInativos()
						|| projeto.getStatus().toString().toLowerCase()
								.equals("ativo")) {
					listaProjeto.add(projeto);
				}
			}
		}
		super.setListaEntidadeModelo(new BindingListModelListModel<Projeto>(
				new SimpleListModel<Projeto>(listaProjeto)));
		super.binder.loadAll();
	}

	/**
	 * @see GenericoCompositor#acaoSalvar()
	 */
	public void acaoSalvar() {
		super.binder.saveAll();
		try {
			boolean retorno = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			if (retorno) {
				limparCampos();
			}
			super.mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void acaoAbrir() {
		org.zkoss.zk.ui.Component comp = Executions.createComponents(
				"/componentes/modalProjeto.zul", null, null);

		if (comp instanceof Window) {
			((Window) comp).doModal();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.unucet.docscree.visao.compositor.GenericoCompositor#
	 * atualizarEntidadeExcluida(int)
	 */
	@Override
	protected void atualizarEntidadeExcluida(int index) {
		((Projeto) this.getListaEntidade().get(index))
				.setStatus(StatusEnum.INATIVO);
		acaoFiltrar();
		super.binder.loadAll();
	}

	public Window criarJanela() {
		return null;
	}

	/**
	 * Método que retorna as equipes cadastradas de acordo com o nível de
	 * permissão do usuário
	 * 
	 * @return List<Equipe> equipes cadastradas
	 */
	@Override
	public List<Equipe> getListaEquipes() {
		return super.getControle().listarEquipes(super.getUsuarioSessao());
	}

	/**
	 * Método que retorna a lista de modelos cadastrados
	 * 
	 * @return List<Modelo> modelos cadastrados
	 */
	@Override
	public List<Modelo> getListaModelos() {
		return super.getControle().listarModelos();
	}

	/**
	 * @return String o(a) fldNome
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
	 * @return String o(a) fldDescricao
	 */
	public String getFldDescricao() {
		return fldDescricao;
	}

	/**
	 * @param fldDescricao
	 *            o(a) fldDescricao a ser setado(a)
	 */
	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}

	/**
	 * @return Equipe o(a) fldEquipe
	 */
	public Equipe getFldEquipe() {
		return fldEquipe;
	}

	/**
	 * @param fldEquipe
	 *            o(a) fldEquipe a ser setado(a)
	 */
	public void setFldEquipe(Equipe fldEquipe) {
		this.fldEquipe = fldEquipe;
	}

	/**
	 * @return Modelo o(a) fldModelo
	 */
	public Modelo getFldModelo() {
		return fldModelo;
	}

	/**
	 * @param fldModelo
	 *            o(a) fldModelo a ser setado(a)
	 */
	public void setFldModelo(Modelo fldModelo) {
		this.fldModelo = fldModelo;
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

	/**
	 * @return String o(a) filtroEquipe
	 */
	public String getFiltroEquipe() {
		return filtroEquipe;
	}

	/**
	 * @param filtroEquipe
	 *            o(a) filtroEquipe a ser setado(a)
	 */
	public void setFiltroEquipe(String filtroEquipe) {
		this.filtroEquipe = filtroEquipe;
	}

	/**
	 * @return String o(a) filtroModelo
	 */
	public String getFiltroModelo() {
		return filtroModelo;
	}

	/**
	 * @param filtroModelo
	 *            o(a) filtroModelo a ser setado(a)
	 */
	public void setFiltroModelo(String filtroModelo) {
		this.filtroModelo = filtroModelo;
	}

}
