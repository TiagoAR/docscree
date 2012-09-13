package br.ueg.unucet.plugin.inputtextvisao1;

import org.zkoss.zul.Textbox;

public class InputTextInterface {
	
	private Textbox textbox;
	
	//pedir nova instanciar
	
	public InputTextInterface() {
		this.textbox = new Textbox("");
	}

	public Textbox getVisualizacao() {
		return this.textbox;
	}
	
	public void setValor(String valor) {
		this.textbox.setText(valor);
	}
	
	public void getValor() {
		this.textbox.getText();
	}
}
