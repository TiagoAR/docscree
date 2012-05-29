package br.ueg.unucet.quid.servicos;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.interfaces.IModeloControle;
import br.ueg.unucet.quid.interfaces.IModeloServico;

@Service("ModeloServico")
public class ModeloServico extends GenericoServico<Modelo> implements IModeloServico<Modelo>{
	
	@Autowired
	private IModeloControle<Modelo, Long> modeloControle;
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloServico#criarModelo(java.lang.String, java.lang.String)
	 */
	@Override
	public Retorno<String, Modelo> criarModelo(String nome, String descricao) {
		Modelo modelo = new Modelo();
		modelo.setNome(nome);
		modelo.setDescricao(descricao);
		Retorno<String, Modelo> retorno = new Retorno<String, Modelo>();
		if(modeloControle.verificarDuplicidade(modelo)){
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMensagensUtil.getValor("modelo_duplicado_framework"));
		}else{
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, modelo);
		}
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloServico#criarItemModelo(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	public Retorno<String, ItemModelo> criarItemModelo(Artefato artefato) {
		ItemModelo itemModelo = new ItemModelo();
		itemModelo.setArtefato(artefato);
		Retorno<String, ItemModelo> retorno = new Retorno<String, ItemModelo>();
		retorno.setSucesso(true);
		retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, itemModelo);
		return retorno;
	}


	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloServico#incluirItemModelo(br.ueg.unucet.quid.dominios.Modelo, br.ueg.unucet.quid.dominios.ItemModelo)
	 */
	@Override
	public Retorno<String, Collection<String>> incluirItemModelo(Modelo modelo, ItemModelo itemModelo) {
		Collection<String> erros = this.modeloControle.validarItemModelo(itemModelo);
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		if(erros.isEmpty()){
			erros = verificarItemModeloNoModelo(modelo, itemModelo);
			if(erros.isEmpty()){
			retorno.setSucesso(true);
			modelo.getItemModelo().add(itemModelo);
			}else{
				retorno.setSucesso(false);
				retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO,erros);
			}
		}else{
			retorno.setSucesso(false);
			retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, erros);
			retorno.setMensagem(propertiesMensagensUtil.getValor("parametros_itemmodelo_nao_informados"));
		}
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloServico#validarItemModelo(br.ueg.unucet.quid.dominios.ItemModelo, br.ueg.unucet.quid.dominios.Modelo)
	 */
	@Override
	public Retorno<String, Collection<String>> validarItemModelo(ItemModelo itemModelo, Modelo modelo) {
		Collection<String> erros = this.modeloControle.validarItemModelo(itemModelo);
		if(erros.isEmpty()){
			erros = verificarItemModeloNoModeloSemDuplicacao(modelo, itemModelo);
		}
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		if(erros.isEmpty()){
			retorno.setSucesso(true);
		}else{
			retorno.setSucesso(false);
			retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, erros);
		}
		return retorno;
	}

	
	
	/**
	 * Metodo responsavel por verificar a duplicidade de cadastro de um itemmodelo no modelo.
	 * @param modelo Modelo que esta sendo verificado.
	 * @param itemModelo ItemModelo que sera inserido no modelo.
	 * @return Lista de erros encontrados na verificacao.
	 */
	private Collection<String> verificarItemModeloNoModeloSemDuplicacao(Modelo modelo, ItemModelo itemModelo) {
		Collection<String> erros = new ArrayList<String>();
		boolean existeGrauAnterior = false;
		boolean existeOrdemAnterior = false;
		boolean existeOrdemPai = false;
		for(ItemModelo itemModeloVerificar: modelo.getItemModelo()){
			if(itemModeloVerificar.getGrau() == itemModelo.getGrau() - 1){
				existeGrauAnterior = true;
				if(itemModeloVerificar.getOrdem() == itemModelo.getOrdemPai()){
					existeOrdemPai = true;
				}
			}
			if(itemModeloVerificar.getGrau() == itemModelo.getGrau()){
				if(itemModeloVerificar.getOrdem() == itemModelo.getOrdem() - 1){
					existeOrdemAnterior = true;
				}
			}
			
		}
		if(!existeGrauAnterior){
			erros.add(propertiesMensagensUtil.getValor("nao_existe_grau_anterior_modelo"));
		}
		if(!existeOrdemAnterior){
			erros.add(propertiesMensagensUtil.getValor("nao_exite_ordem_anterior_modelo"));
		}
		if(!existeOrdemPai){
			erros.add(propertiesMensagensUtil.getValor("nao_existe_artefato_pai_modelo"));
		}
		return erros;
	}

	/**
	 * Metodo responsavel por realizar a verificacao do itemmodelo dentro do modelo.
	 * @param modelo Modelo que sera verificado.
	 * @param itemModelo ItemModelo que sera inserido no modelo.
	 * @return Lista de erros encontrados na verificacao.
	 */
	private Collection<String> verificarItemModeloNoModelo(Modelo modelo, ItemModelo itemModelo) {
		Collection<String> erros = new ArrayList<String>();
		boolean existeGrauAnterior = false;
		boolean existeOrdemAnterior = false;
		boolean existeOrdemPai = false;
		boolean ordemIgual = false;
		for(ItemModelo itemModeloVerificar: modelo.getItemModelo()){
			if(itemModeloVerificar.getGrau() == itemModelo.getGrau() - 1){
				existeGrauAnterior = true;
				if(itemModeloVerificar.getOrdem() == itemModelo.getOrdemPai()){
					existeOrdemPai = true;
				}
			}
			if(itemModeloVerificar.getGrau() == itemModelo.getGrau()){
				if(itemModeloVerificar.getOrdem() == itemModelo.getOrdem() - 1){
					existeOrdemAnterior = true;
				}else{
					if(itemModeloVerificar.getOrdem() == itemModelo.getOrdem()){
						ordemIgual  = true;
					}
				}
			}
			
		}
		if(!existeGrauAnterior){
			erros.add(propertiesMensagensUtil.getValor("nao_existe_grau_anterior_modelo"));
		}
		if(!existeOrdemAnterior){
			erros.add(propertiesMensagensUtil.getValor("nao_exite_ordem_anterior_modelo"));
		}
		if(!existeOrdemPai){
			erros.add(propertiesMensagensUtil.getValor("nao_existe_artefato_pai_modelo"));
		}
		if(ordemIgual){
			erros.add(propertiesMensagensUtil.getValor("itemmodelo_mesma_ordem"));
		}
		return erros;
	}

	public IModeloControle<Modelo, Long> getModeloControle() {
		return modeloControle;
	}
	
	public void setModeloControle(IModeloControle<Modelo, Long> modeloControle) {
		this.modeloControle = modeloControle;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloServico#removerArtefatoModelo(br.ueg.unucet.quid.dominios.Artefato, br.ueg.unucet.quid.dominios.Modelo)
	 */
	@Override
	public Retorno<Object, Object> removerArtefatoModelo(Artefato artefato, Modelo modelo) {
		boolean encontrou = false;
		ItemModelo itemModeloRemover = null;
		for (ItemModelo itemModelo : modelo.getItemModelo()) {
			if(itemModelo.getArtefato().getCodigo() == artefato.getCodigo()){
				encontrou = true;
				itemModeloRemover = itemModelo;
				break;
			}
		}
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		if(encontrou){
			retorno = removerItemModelo(modelo ,itemModeloRemover);
		}else{
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMensagensUtil.getValor("artefato_nao_esta_em_modelo"));
		}
		return retorno;
	}

	/**
	 * Metodo responsavel por remover um ItemModelo do modelo.
	 * @param modelo Modelo para qual sera removido o ItemModelo.
	 * @param itemModeloRemover ItemModelo que sera removido do modelo.
	 * @return Objeto retorno da execucao da operacao.
	 */
	private Retorno<Object, Object> removerItemModelo(Modelo modelo, ItemModelo itemModeloRemover) {
		boolean contemFilho = false;
		for (ItemModelo itemModelo : modelo.getItemModelo()) {
			if(itemModelo.getGrau() == itemModeloRemover.getGrau() + 1){
				if(itemModelo.getOrdemPai() == itemModeloRemover.getOrdem()){
					contemFilho = true;
					break;
				}
			}
		}
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		if(contemFilho){
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMensagensUtil.getValor("item_modelo_com_filhos"));
		}else{
			retorno.setSucesso(true);
			modelo.getItemModelo().remove(itemModeloRemover);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloServico#mapearModelo(br.ueg.unucet.quid.dominios.Modelo)
	 */
	@Override
	public Retorno<Object, Object> mapearModelo(Modelo modelo) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			this.modeloControle.inserir(modelo);
			retorno.setSucesso(true);
		} catch (QuidExcessao e) {
			retorno = (Retorno<Object, Object>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
		}
		return retorno;
	}

	


	
	
	
	
}
