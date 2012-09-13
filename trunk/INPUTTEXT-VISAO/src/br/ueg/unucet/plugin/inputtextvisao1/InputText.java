package br.ueg.unucet.plugin.inputtextvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

public class InputText extends SuperTipoMembroVisaoZK {
	
	private InputTextInterface inputInterface;
//ter identificar unico na vis�o para chamar o que foi adicionado
	/**
	 * 
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	public InputText() {
		this.inputInterface = new InputTextInterface();
		setDescricao("Vis�o Padr�o");
	}

	@Override
	public Integer getVersao() {
		return 1;
	}

	@Override
	public Integer getRevisao() {
		return 3;
	}

	@Override
	public String getNome() {
		return "inputtextvisao";
	}

	@Override
	public Icon getImagemIlustrativa() {
		return new ImageIcon(getClass().getResource("/input.png"));
	}

	@Override
	public Object getVisaoPreenchimento() {
		return inputInterface.getVisualizacao();
	}

	@Override
	public Object getVisaoVisualizacao() {
		return inputInterface.getVisualizacao();
	}

	@Override
	public boolean isEntradaValida(Object valor) {
		return valor instanceof String;
	}

	@Override
	public String getEventoMascara() {
		return null;
	}

	@Override
	public String getNomeTipoMembroModelo() {
		return "inputtext";
	}

	@Override
	public void setValor(Object valor) {
		super.setValor(valor);
		this.inputInterface.setValor(valor.toString());
	}

}
