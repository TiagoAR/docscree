package br.ueg.unucet.plugin.inputtextvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

public class InputText extends SuperTipoMembro implements ITipoMembroVisao {
	
	private InputTextInterface inputInterface;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	public InputText() {
		this.inputInterface = new InputTextInterface();
		setDescricao("Visão Padrão");
	}

	@Override
	public Integer getVersao() {
		return 1;
	}

	@Override
	public Integer getRevisao() {
		return 1;
	}

	@Override
	public String getNome() {
		return "inputvisao";
	}

	@Override
	public String getPlataforma() {
		return "ZK";
	}

	@Override
	public Icon getImagemIlustrativa() {
		return new ImageIcon(getClass().getResource("/input.png"));
	}

	@Override
	public Object getVisaoPreenchimento() {
		return inputInterface;
	}

	@Override
	public Object getVisaoVisualizacao() {
		return inputInterface;
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
