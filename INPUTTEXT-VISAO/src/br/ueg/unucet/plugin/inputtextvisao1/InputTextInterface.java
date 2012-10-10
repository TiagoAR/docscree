package br.ueg.unucet.plugin.inputtextvisao1;

import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;

/**
 * Interface do InputText, contém os componentes que serão exibidos
 * no Descritor de Tela e como setar e obter seu valor.
 * 
 * @author Diego
 *
 */
public class InputTextInterface implements IComponenteInterface {
	
	/**
	 * Componente de Preenchimento do InputText
	 */
	private Textbox textbox;
	/**
	 * Componente de Visualização do InputText
	 */
	private Label label;
	
	/**
	 * Construtor DEFAULT
	 */
	public InputTextInterface() {
		this.textbox = new Textbox("");
		this.label = new Label("");
	}

	/**
	 * Método que retorna o componente de preenchimento
	 * 
	 * @return Textbox componente
	 */
	public Textbox getPreenchimento() {
		return this.textbox;
	}
	
	/**
	 * Método que retorna o componente de visualização
	 * 
	 * @return Label componente
	 */
	public Label getVisualizacao() {
		return this.label;
	}
	
	@Override
	public void setValor(Object valor) {
		this.textbox.setText(valor.toString());
		this.label.setValue(valor.toString());
	}
	
	@Override
	public void getValor() {
		this.textbox.getText();
	}
}