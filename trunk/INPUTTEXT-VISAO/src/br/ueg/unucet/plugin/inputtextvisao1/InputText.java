package br.ueg.unucet.plugin.inputtextvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.zkoss.zul.Label;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

/**
 * TipoMembro-Visão InputText
 * Classe responsável por representar o TipoMembro-Visão
 * contém implementações dos métodos do SuperTipoMembroVisaoZK
 * 
 * @author Diego
 *
 */
public class InputText extends SuperTipoMembroVisaoZK<InputTextInterface> {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	/**
	 * Construtor DEFAULT, seta os dados que grava no banco e que são necessários
	 * para obter o componente de visualização
	 */
	public InputText() {
		this.componente =  new InputTextInterface();
		setDescricao("Visão Padrão");
		setVersao(1);
		setRevisao(7);
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
		return getComponente().getPreenchimento();
	}

	@Override
	public Object getVisaoVisualizacao() {
		return getComponente().getVisualizacao();
	}
	
	@Override
	public Object getVisualizacaoExemplo() {
		Label visualizacao = getComponente().getVisualizacao();
		visualizacao.setValue("Exemplo");
		return visualizacao;
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
