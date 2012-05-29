package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.excessoes.ModeloExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOModelo;
import br.ueg.unucet.quid.interfaces.IModeloControle;
/**
 * Classe responsavel por realizar as operacoes sobre a entidade Modelo
 * @author QUID
 *
 */
@Service("ModeloControle")
public class ModeloControle extends GenericControle<Modelo, Long> implements IModeloControle<Modelo,Long>{

	/**
	 * Atributo modelo para o qual se esta realizando as operacoes
	 */
	private Modelo modelo;
	/**
	 * Atributo que realiza as operacoes de persistencia da entidade
	 */
	@Autowired
	private IDAOModelo<Modelo, Long> daoModelo;
	
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#antesInserir(java.lang.Object)
	 */
	public boolean antesInserir(Modelo modelo) throws QuidExcessao{
		this.modelo = modelo;
		verificarModelo();
		verificarItensModelo();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloControle#verificarDuplicidade(java.lang.Object)
	 */
	@Override
	public boolean verificarDuplicidade(Modelo modelo) {
		Modelo modeloBusca = new Modelo();
		modeloBusca.setNome(modelo.getNome());
		Collection<Modelo> lista = pesquisarPorRestricao(modeloBusca, new String[]{"nome", "codigo"});
		return verificarLista(modelo, lista);
	}
	
	/**
	 * Metodo responsavel por verifica se um determinado modelo esta cadastrado dentro de uma lista de modelos
	 * a partir de seu nome
	 * @param modelo2 Modelo que sera verificado o cadastro
	 * @param lista Lista de Modelos
	 * @return True caso o nome do modelo se encotre dentro la lista, false caso contrario.
	 */
	private boolean verificarLista(Modelo modelo2, Collection<Modelo> lista) {
		boolean retorno = true;
		if(!lista.isEmpty()){
			for(Modelo modeloLista :lista){
				if(modeloLista.getNome().equals(modelo2.getNome())){
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}
	

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IModeloControle#validarItemModelo(br.ueg.unucet.quid.dominios.ItemModelo)
	 */
	@Override
	public Collection<String> validarItemModelo(ItemModelo itemModelo) {
		return verificarItemModelo(itemModelo);
	}

	/**
	 * Metodo que verifica se os campos do Modelo foram informados.
	 * @throws ModeloExcessao Excessao caso o algum campo obrigatorio nao for informado.
	 */
	private void verificarModelo() throws ModeloExcessao{
		Collection<String> erros = new ArrayList<String>();
		if(modelo.getNome() == null || modelo.getNome().equals("")){
			erros.add("nome");
		}
		if(modelo.getDescricao() == null || modelo.getDescricao().equals("")){
			erros.add("descricao");
		}
		if(modelo.getItemModelo() == null || modelo.getItemModelo().isEmpty()){
			erros.add("itemmodelo");
		}
		if(!erros.isEmpty()){
			throw new ModeloExcessao(propertiesMessagesUtil.getValor("modelo_valores_invalidos"), erros);
		}
		
	}
	
	
	/**
	 * Metodo que verifica os dados dos ItensModelos que se encontram dentro do modelo
	 * @throws ModeloExcessao Excessao caso algum ItemModelo esteje com valores errados / invalidos.
	 */
	private void verificarItensModelo() throws ModeloExcessao{
		Map<ItemModelo, Collection<String>> erros = new HashMap<ItemModelo, Collection<String>>();
		for(ItemModelo itemModelo :modelo.getItemModelo()){
			if(itemModelo.getArtefato() == null || itemModelo.getArtefato().getCodigo() == null){
				Collection<String> retorno = verificarItemModelo(itemModelo);
				if(!retorno.isEmpty()){
					erros.put(itemModelo, retorno);
				}
			}
		}
		if(!erros.isEmpty()){
			throw new ModeloExcessao(propertiesMessagesUtil.getValor("itemmodelo_valor_invalido"), erros);
		}
	}
	
	/**
	 * Metodo que verifica os campos obrigatorios de um ItemModelo
	 * @param itemModelo ItemModelo para o qual sera verificado os dados.
	 * @return Lista contendo os campos obrigatorios nao informados
	 */
	private Collection<String> verificarItemModelo(ItemModelo itemModelo){
		Collection<String> parametros = new ArrayList<String>();
		if(itemModelo.getArtefato() == null || itemModelo.getArtefato().getCodigo() == null){
			parametros.add("artefato");
		}
		if(itemModelo.getGrau() == null){
			parametros.add("grau");
		}
		if(itemModelo.getOrdem() == null){
			parametros.add("ordem");
		}
		if(itemModelo.getMultiplicidade() == null){
			parametros.add("multiplicidade");
		}
		if(itemModelo.getOrdemPai() == null){
			parametros.add("ordemPai");
		}
		return parametros;
	}
	
	@Override
	public IDAO<Modelo, Long> getDao() {
		return this.daoModelo;
	}
	
	//GETTES AND SETTERS
	public IDAOModelo<Modelo, Long> getDaoModelo() {
		return daoModelo;
	}
	public void setDaoModelo(IDAOModelo<Modelo, Long> daoModelo) {
		this.daoModelo = daoModelo;
	}


	

	
}
