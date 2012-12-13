package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.docscree.modelo.MembroModelo;
import br.ueg.unucet.docscree.visao.compositor.ModeloCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.utilitarias.SerializadorObjetoUtil;

/**
 * Controlador específico para o caso de uso Manter Modelo
 * 
 * @author Diego
 *
 */
@SuppressWarnings("unchecked")
public class ModeloControle extends GenericoControle<Modelo> {

	/**
	 * @see ICRUDControle#acaoSalvar()
	 */
	@Override
	public boolean acaoSalvar() {
		if (super.isUsuarioMontador()) {
			Map<String, MembroModelo> itensModelo = (Map<String, MembroModelo>) getMapaAtributos().get("itemModelo");
			Collection<ItemModelo> listaItens = new ArrayList<ItemModelo>();
			for (MembroModelo itemModelo : itensModelo.values()) {
				listaItens.add(transformarItemModelo(itemModelo));
			}
			getEntidade().setItemModelo(listaItens);
			Retorno<Object,Object> retorno;
			if (getEntidade().getCodigo() == null || getEntidade().getCodigo() == 0) {
				retorno = getFramework().mapearModelo(getEntidade());
			} else {
				retorno = getFramework().alterarModelo(getEntidade());
			}
			if (retorno.isSucesso()) {
				return true;
			} else {
				getMensagens().getListaMensagens().add(retorno.getMensagem());
			}
		}
		return false;
	}

	/**
	 * @see ICRUDControle#acaoSalvar()
	 */
	@Override
	public boolean acaoExcluir() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see ICRUDControle#setarEntidadeVisao(SuperCompositor)
	 */
	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		ModeloCompositor visao = (ModeloCompositor) pVisao;
		Modelo entidade = (Modelo) visao.getEntidade();
		visao.setCodigo(entidade.getCodigo());
		visao.setFldDescricao(entidade.getDescricao());
		visao.setFldNome(entidade.getNome());
		for (ItemModelo itemModelo : entidade.getItemModelo()) {
			MembroModelo membroModelo = new MembroModelo();
			membroModelo.setArtefato(itemModelo.getArtefato());
			membroModelo.setCodigo(itemModelo.getCodigo());
			membroModelo.setGrau(itemModelo.getGrau());
			membroModelo.setMultiplicidade(itemModelo.getMultiplicidade());
			membroModelo.setOrdem(itemModelo.getOrdem());
			membroModelo.setOrdemPai(itemModelo.getOrdemPai());
			membroModelo.setOrdemPreenchimento(itemModelo.getOrdemPreenchimento());
			membroModelo.setListaParametros((Collection<IParametro<?>>) SerializadorObjetoUtil.toObject(itemModelo.getParametros()));
			visao.getItensModelo().put(visao.gerarKeyMembroModelo(membroModelo), membroModelo);
		}
	}

	/**
	 * @see GenericoControle#executarListagem()
	 */
	@Override
	protected Retorno<String, Collection<Modelo>> executarListagem() {
		return getFramework().listarModelo();
	}
	
	public boolean acaoAbrirModelo() {
		setarEntidadeVisao(getVisao());
		((ModeloCompositor) getVisao()).mapearItensModelo();
		return true;
	}
	
	public List<Modelo> getListaModelos() {
		Retorno<String, Collection<Modelo>> retorno = executarListagem();
		List<Modelo> lista = null;
		if (retorno.isSucesso()) {
			lista = (List<Modelo>) retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		}
		return lista;
	}
	
	/**
	 * Método que transforma o MembroModelo em um ItemModelo
	 * 
	 * @param membroModelo
	 * @return item representando a conversão do MembroModelo
	 */
	private ItemModelo transformarItemModelo(MembroModelo membroModelo) {
		ItemModelo item = new ItemModelo();
		item.setCodigo(membroModelo.getCodigo());
		item.setArtefato(membroModelo.getArtefato());
		item.setGrau(membroModelo.getGrau());
		item.setMultiplicidade(membroModelo.getMultiplicidade());
		item.setOrdem(membroModelo.getOrdem());
		item.setOrdemPai(membroModelo.getOrdemPai());
		item.setOrdemPreenchimento(membroModelo.getOrdemPreenchimento());
		item.setParametros(SerializadorObjetoUtil.toByteArray(membroModelo.getListaParametros()));
		return item;
	}
	
	/**
	 * Método que executa a listagem de ArtefatosModelo para serem escolhidos e adicionados ao Modelo
	 * 
	 * @return Collection de ArtefatoModelo
	 */
	public Collection<Artefato> listarArtefatosModelo() {
		Collection<Artefato> lista;
		Retorno<String, Collection<Artefato>> retorno = getFramework().pesquisarArtefato(null, null, null);
		if (retorno.isSucesso()) {
			lista = retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		} else {
			lista = new ArrayList<Artefato>();
		}
		return lista;
	}
	
	/**
	 * Método responsável por validar os campos do Item Modelo
	 * 
	 * @return retorno se ação foi executada
	 */
	public boolean acaoValidarItemModelo() {
		MembroModelo membroModelo = (MembroModelo) super.getMapaAtributos().get("membroModeloSelecionado");
		boolean retorno = true;
		if (membroModelo.getGrau() == null || membroModelo.getGrau() <= 0) {
			retorno = false;
			getMensagens().getListaMensagens().add("É necessário especificar um Grau para o Item Modelo!");
		}
		if (membroModelo.getMultiplicidade() == null) {
			retorno = false;
			getMensagens().getListaMensagens().add("É obrigatório a especificação da Multiplicidade!");
		}
		if (membroModelo.getOrdem() == null || membroModelo.getOrdem() < 0) {
			retorno = false;
			getMensagens().getListaMensagens().add("É necessário especificar a Ordem do Item Modelo!");
		}
		if (membroModelo.getOrdemPreenchimento() == null || membroModelo.getOrdemPreenchimento() < 0)  {
			retorno = false;
			getMensagens().getListaMensagens().add("A Ordem de Preenchimento deve ser um valor positivo!");
		}
		for (IParametro<?> parametro : membroModelo.getListaParametros()) {
			if (parametro.isObrigatorio() && parametro.getValor() == null || String.valueOf(parametro.getValor()).isEmpty()) {
				getMensagens().getListaMensagens().add("O parâmetro "+parametro.getRotulo()+" é obrigatório!");
			}
		}
		return retorno;
	}

}
