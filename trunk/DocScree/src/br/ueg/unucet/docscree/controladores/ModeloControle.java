package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.docscree.modelo.MembroModelo;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Controlador específico para o caso de uso Manter Modelo
 * 
 * @author Diego
 *
 */
public class ModeloControle extends GenericoControle<Modelo> {

	/**
	 * @see ICRUDControle#acaoSalvar()
	 */
	@Override
	public boolean acaoSalvar() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see GenericoControle#executarListagem()
	 */
	@Override
	protected Retorno<String, Collection<Modelo>> executarListagem() {
		// TODO Auto-generated method stub
		return null;
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
	 * @return
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
