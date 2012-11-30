package br.ueg.unucet.plugin.statictextvisao1;

import java.util.Collection;
import java.util.Iterator;

import org.zkoss.zul.Label;

import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Interface do InputText, contém os componentes que serão exibidos
 * no Descritor de Tela e como setar e obter seu valor.
 * 
 * @author Diego
 *
 */
public class StaticTextInterface implements IComponenteInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 344518551310671764L;	
	
	/**
	 * Componente de Visualização do StaticText
	 */
	private Label label;
	
	/**
	 * Construtor DEFAULT
	 */
	public StaticTextInterface() {
		this.label = new Label("Valor Padrão");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getStaticTextValue(StaticText text){

		Collection params = text.getListaParametros();
		Iterator<IParametro<String>> iterator = params.iterator();
		while (iterator.hasNext()) {
			IParametro<String> param = iterator.next();
			if (param.getNome().equals(StaticText.TEXTO_FIXO.toString())) {
				if (param.getValor() != null) {
					return param.getValor();					
				}
			}
		}
		return StaticText.VALOR_PADRAO;
	}
	/**
	 * Método que retorna o componente de preenchimento
	 * 
	 * @return Textbox componente
	 */
	public Label getPreenchimento(StaticText text) {
		return this.getVisualizacao(text);
	}
	
	/**
	 * Método que retorna o componente de visualização
	 * 
	 * @return Label componente
	 */
	public Label getVisualizacao(StaticText text) {
		this.label.setValue(this.getStaticTextValue(text));							
		return this.label;
	}
	
	@Override
	public void setValor(Object valor) {
		//
	}
	
	@Override
	public Object getValor() {
		return null;
	}

	@Override
	public Object getValor(Object componente) {
		return null;
	}
}