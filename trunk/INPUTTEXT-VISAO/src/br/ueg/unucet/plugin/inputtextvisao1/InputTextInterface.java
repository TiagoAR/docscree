package br.ueg.unucet.plugin.inputtextvisao1;

import org.zkoss.zul.Textbox;

import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;

public class InputTextInterface implements IComponenteInterface {
	
	private Textbox textbox;
	
	//pedir nova instanciar
	
	public InputTextInterface() {
		this.textbox = new Textbox("");
	}

	public Textbox getVisualizacao() {
		return this.textbox;
	}
	
	@Override
	public void setValor(Object valor) {
		this.textbox.setText(valor.toString());
	}
	
	@Override
	public void getValor() {
		this.textbox.getText();
	}
}
