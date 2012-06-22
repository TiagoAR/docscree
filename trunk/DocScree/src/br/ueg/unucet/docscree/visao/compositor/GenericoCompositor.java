package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.SimpleListModel;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.GenericoControle;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;
import br.ueg.unucet.quid.extensao.interfaces.IPersistivel;

/**
 * Compositor Genérico, contém métodos comuns a todos compositores que são caso de uso tipo CRUD.
 * 
 * @author Diego
 *
 * @param <E>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class GenericoCompositor<E extends GenericoControle> extends SuperCompositor<E> {

	/**
	 * Default serial do compositor
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Primary key da entidade
	 */
	@AtributoVisao(isCampoEntidade = true, nome= "codigo", nomeCampoBundle="codigo")
	protected Long codigo = null;
	
	/**
	 * listagem da entidade contendo todos os objetos
	 */
	protected List<?> listaEntidade;
	
	/**
	 * Lista do que está sendo exibido na janela de listagem na visão.
	 * Componente Model do ListBox
	 */
	protected BindingListModelListModel<?> listaEntidadeModelo;
	
	/**
	 * Representa o objeto entidade selecionado na listagem
	 */
	protected Persistivel entidade;

	/**
	 * Retorna a classe que representa a entidade.
	 * 
	 * @return Class classe da entidade
	 */
	public abstract Class getTipoEntidade();
	
	/* Métodos abstratos */
	
	/**
	 * Método que limpa os campos da tela.
	 * 
	 */
	protected abstract void limparCampos();
	
	/**
	 * Método que limpa os campos de filtragem da lista.
	 */
	protected abstract void limparFiltros();
	
	/**
	 * Método que filtra a lista em exibição.
	 */
	protected abstract void acaoFiltrar();

	/* Fim dos métodos abstratos*/
	
	/**
	 * Método que cria nova instancia da entidade.
	 * 
	 * @return IPersistivel entidade
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public IPersistivel<?> novaEntidade() throws InstantiationException,
			IllegalAccessException {
		return (IPersistivel<?>) getTipoEntidade().newInstance();
	}

	/**
	 * Método que executa a ação de Salvar Usuário e mostra mensagem de sucesso ou erro após ação
	 */
	public void acaoSalvar() {
		try {
			boolean resultado = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			if (resultado) {
				limparCampos();
			}
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que executa a ação de Listar Usuário e mostra mensagem de sucesso ou erro após ação.
	 */
	public void acaoListar() {
		try {
			limparFiltros();
			boolean resultado = super.getControle().fazerAcao("listar", (SuperCompositor) this);
			if (resultado) {
				this.setListaEntidade(super.getControle().getLista());
				Component componente = super.getComponent().getFellow("windowLista");
				componente.setVisible(true);
				setListaEntidadeModelo(new BindingListModelListModel(new SimpleListModel(super.getControle().getLista())));
				super.binder.loadAll();
				acaoFiltrar();
			} else {
				setListaEntidadeModelo(null);
			}
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que executa a ação de Listar e mostra mensagem de sucesso ou erro
	 * após ação.
	 */
	public void acaoEditar() {
		super.binder.saveAll();
		super.getControle().setarEntidadeVisao(this);
		this.fecharModalLista();
		super.binder.loadAll();
	}
	
	/**
	 * Método que executa a ação de exclusão da entidade, apos execução mostra
	 * mensagem informando sobre resultado da ação.
	 */
	public void acaoExcluir() {
		try {
			super.binder.saveAll();
			super.getControle().setarEntidadeVisao(this);
			int index = this.getListaEntidade().indexOf(this.getEntidade());
			boolean retorno = super.getControle().fazerAcao("excluir", (SuperCompositor) this);
			if (retorno) {
				atualizarEntidadeExcluida(index);
			}
			super.mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Atualiza a lista de entidade excluindo a entidade selecionada,
	 * não sendo necessário fazer nova listagem.
	 * 
	 * @param index indíce da entidade
	 */
	protected void atualizarEntidadeExcluida(int index) {
		this.getListaEntidade().remove(index);
		acaoFiltrar();
		super.binder.loadAll();
	}
	
	/**
	 * Método que fecha a janela de listagem.
	 */
	public void fecharModalLista() {
		super.getComponent().getFellow("windowLista").setVisible(false);
		limparFiltros();
	}
	
	//Segue Getters and Setters

	/**
	 * @return o(a) listaEntidade
	 */
	public List<?> getListaEntidade() {
		return listaEntidade;
	}

	/**
	 * @param listaEntidade o(a) listaEntidade a ser setado(a)
	 */
	public void setListaEntidade(List<?> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}

	/**
	 * @return o(a) entidade
	 */
	public Persistivel getEntidade() {
		return entidade;
	}

	/**
	 * @param entidade o(a) entidade a ser setado(a)
	 */
	public void setEntidade(Persistivel entidade) {
		this.entidade = entidade;
	}

	/**
	 * @return o(a) codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo o(a) codigo a ser setado(a)
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return o(a) listaEntidadeModelo
	 */
	public BindingListModelListModel<?> getListaEntidadeModelo() {
		return listaEntidadeModelo;
	}

	/**
	 * @param listaEntidadeModelo o(a) listaEntidadeModelo a ser setado(a)
	 */
	public void setListaEntidadeModelo(
			BindingListModelListModel<?> listaEntidadeModelo) {
		this.listaEntidadeModelo = listaEntidadeModelo;
	}
}
