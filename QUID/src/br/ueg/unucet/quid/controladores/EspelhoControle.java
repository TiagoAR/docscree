package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

/**Classe responsavel por verificar o espelho das classes do ITipoMembroVisao com as
 * classes ITipoMembroModelo
 * @author QUID
 * 
 */
public class EspelhoControle {
	
	/**
	 * Atributo que armazena os TiposMembroModelo cadastrados no framework
	 */
	private Collection<ITipoMembroModelo> tiposMembroModelo;
	
	/**
	 * Atributo que armazena os TiposMembroVisao que nao foram encontrados espelhos correspondentes
	 */
	private Collection<ITipoMembroVisao> tiposMembrosVisaoSemEspelho;
	
	/**
	 * Atributo que armazena os TiposMembroModelo que serao importados para o framework
	 */
	private Collection<ITipoMembroModelo> tiposMembroModeloAImportar;
	
	/**
	 * Atributo que armazena os TiposMembroVisao que serao importados para o framework
	 */
	private Collection<ITipoMembroVisao> tiposMembrosVisaoAImportar;
	
	public EspelhoControle(Collection<ITipoMembroModelo> tiposMembroModelo){
		this.tiposMembroModelo = tiposMembroModelo;
	}
	
	/**Metodo responsavel por realizar a verificacao do espelho entre as classes.
	 * Caso aja algum espelho invalido, ele uma lista das classes ITIpoMembroVisao 
	 * que nao possuem espelho
	 * @param tiposMembrosModeloAImportar TipoMembroModelo que irao ser importados.
	 * @param tiposMembroVisaoAImportar TiposMembroVisao que sera verificado o espelho
	 * @return Lista de ITipoMembroVisao sem espelho
	 */
	public 	Collection<ITipoMembroVisao> checarEspelho(Collection<ITipoMembroModelo> tiposMembrosModeloAImportar, Collection<ITipoMembroVisao> tiposMembroVisaoAImportar){
		this.tiposMembrosVisaoSemEspelho = new ArrayList<ITipoMembroVisao>();
		this.tiposMembroModeloAImportar = tiposMembrosModeloAImportar;
		this.tiposMembrosVisaoAImportar = tiposMembroVisaoAImportar;
		checarEspelho();
		return this.tiposMembrosVisaoSemEspelho;
	}
	
	/**Metodo responsavel por realizar a verificacao do espelho entre as classes 
	 * Caso aja algum espelho invalido, ele uma lista das classes ITIpoMembroVisao 
	 * que nao possuem espelho
	 * @param tiposMembroVisao TiposMembroVisao a serem verificados o espelho
	 * @return Lista de TipoMembroVisao que nao possuem o espelho
	 */
	public Collection<ITipoMembroVisao> checarEspelho(Collection<ITipoMembroVisao> tiposMembroVisao){
		this.tiposMembrosVisaoSemEspelho = new ArrayList<ITipoMembroVisao>();
		this.tiposMembrosVisaoAImportar = tiposMembroVisao;
		this.tiposMembroModeloAImportar = new ArrayList<ITipoMembroModelo>();
		checarEspelho();
		return this.tiposMembrosVisaoSemEspelho;
	}
	
	/**
	 * Metodo que uni a lista de TiposMembroModelo a importar com a lista de TipoMembroModelo
	 * cadastrados, para que nao se necessite realizar outra pesquisa no banco apos a insercao dos TipoMembroModelo
	 */
	public void unirTipoMembroModelo(){
		this.tiposMembroModelo.addAll(this.tiposMembroModeloAImportar);
	}

	/**
	 * Metodo que realiza a checagem do espelho do TipoMembro visao, procurando um correspondente na
	 * lista de TiposMembroModelo cadastrados no framework ou na lista de TiposMembroModemo a importar no framework.
	 */
	private void checarEspelho() {
		for (ITipoMembroVisao tipoMembroVisao : this.tiposMembrosVisaoAImportar) {
			String nomeTipoMembroModelo = tipoMembroVisao.getNomeTipoMembroModelo();
			if(!checarNomeTipoMembroModeloCadastrados(nomeTipoMembroModelo)){
				if(!checarNomeTipoMembroModeloAImportar(nomeTipoMembroModelo)){
					this.tiposMembrosVisaoSemEspelho.add(tipoMembroVisao);
				}
			}
		}
		
	}

	/**
	 * Metodo que verifica se um determinado nome do TipoMembroModelo se encontra dentro da lista de TiposMembroModelo
	 * cadastrados no framework.
	 * @param nomeTipoMembroModelo Nome do TipoMembroModelo a procurar
	 * @return True caso o nome do TipoMembroModelo se encontre dentro da lista, ou false caso contrario.
	 */
	private boolean checarNomeTipoMembroModeloAImportar(String nomeTipoMembroModelo) {
		boolean result = false;
		for(ITipoMembroModelo tipoMembroModelo: this.tiposMembroModelo){
			if(tipoMembroModelo.getNome().equals(nomeTipoMembroModelo)){
				result = true;
				break;
			}
		}
		return result;
	}

	
	/**
	 * Metodo que verifica se um determinado nome do TipoMembroModelo se encontra dentro da lista de TiposMembroModelo
	 * a importar para o framework.
	 * @param nomeTipoMembroModelo Nome do TipoMembroModelo a procurar
	 * @return True caso o nome do TipoMembroModelo se encontre dentro da lista, ou false caso contrario.
	 */
	private boolean checarNomeTipoMembroModeloCadastrados(String nomeTipoMembroModelo) {
		boolean result = false;
		for(ITipoMembroModelo tipoMembroModelo: this.tiposMembroModeloAImportar){
			if(tipoMembroModelo.getNome().equals(nomeTipoMembroModelo)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	//GETTERS AND SETTERS

	public Collection<ITipoMembroModelo> getTiposMembroModelo() {
		return tiposMembroModelo;
	}

	public void setTiposMembroModelo(Collection<ITipoMembroModelo> tiposMembroModelo) {
		this.tiposMembroModelo = tiposMembroModelo;
	}
	
	
}
