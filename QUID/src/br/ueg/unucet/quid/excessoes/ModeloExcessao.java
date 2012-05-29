package br.ueg.unucet.quid.excessoes;

import java.util.Collection;
import java.util.Map;

import br.ueg.unucet.quid.dominios.ItemModelo;

/**
 * Classe que representa as execessoes disparadas pela classes que manipulam o modelo.
 * @author QUID
 *
 */
public class ModeloExcessao extends QuidExcessao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 277569090224319563L;
	/**
	 * Atributo que informa os parametros nao informados para cada Item do modelo
	 */
	private Map<ItemModelo, Collection<String>> parametros;
	/**
	 * Atributo que informa os parametors nao informados para o modelo.
	 */
	private Collection<String> parametros2;
	
	public ModeloExcessao(String mensagem, Throwable erro) {
		super(mensagem, erro);
		// TODO Auto-generated constructor stub
	}
	
	public ModeloExcessao(String mensagem){
		super(mensagem);
	}
	
	public ModeloExcessao(String mensagem, Map<ItemModelo, Collection<String>> parametros){
		super(mensagem);
		this.parametros = parametros;
	}
	
	public ModeloExcessao(String mensagem, Collection<String> parametros){
		super(mensagem);
		this.parametros2 = parametros;
	}
	
	public Map<ItemModelo, Collection<String>> getParametrosItemsModelo(){
		return this.parametros;
	}
	
	public Collection<String> getParametrosModelo(){
		return this.parametros2;
	}
	
}
