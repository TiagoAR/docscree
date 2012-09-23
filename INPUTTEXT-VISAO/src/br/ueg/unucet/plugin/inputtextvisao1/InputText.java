package br.ueg.unucet.plugin.inputtextvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

public class InputText extends SuperTipoMembroVisaoZK<InputTextInterface> {
	
//ter identificar unico na visão para chamar o que foi adicionado
	/**
	 * 
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	public InputText() {
		this.componente =  new InputTextInterface();
		setDescricao("Visão Padrão");
		setVersao(1);
		setRevisao(6);
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
		return getComponente().getVisualizacao();
	}

	@Override
	public Object getVisaoVisualizacao() {
		return getComponente().getVisualizacao();
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

}
