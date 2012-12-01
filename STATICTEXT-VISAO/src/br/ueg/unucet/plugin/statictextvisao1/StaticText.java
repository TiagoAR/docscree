package br.ueg.unucet.plugin.statictextvisao1;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.zkoss.zul.Label;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;

/**
 * TipoMembro-Visão StaticText
 * Classe responsável por representar o TipoMembro-Visão
 * contém implementações dos métodos do SuperTipoMembroVisaoZK
 * 
 * @author Diego
 *
 */
public class StaticText extends SuperTipoMembroVisaoZK<StaticTextInterface> {
	
	public static final String TEXTO_FIXO = "TEXTO_FIXO";
	
	public static final String VALOR_PADRAO = "Texto fixo";
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	/**
	 * Construtor DEFAULT, seta os dados que grava no banco e que são necessários
	 * para obter o componente de visualização
	 */
	public StaticText() {
		this.componente =  new StaticTextInterface();
		setDescricao("Visão Padrão");
		setVersao(1);
		setRevisao(4);
	}

	@Override
	public String getNome() {
		return "statictextvisao";
	}

	@Override
	public Icon getImagemIlustrativa() {
		return new ImageIcon(getClass().getResource("/label.png"));
	}

	@Override
	public Object getVisaoPreenchimento() {
		Label preenchimento = getComponente().getPreenchimento(this);
		preenchimento.setValue(String.valueOf(getParametroPorNome(TEXTO_FIXO).getValor()));
		return preenchimento;
	}

	@Override
	public Object getVisaoVisualizacao() {
		Label preenchimento = getComponente().getVisualizacao(this);
		preenchimento.setValue(String.valueOf(getParametroPorNome(TEXTO_FIXO).getValor()));
		return preenchimento;
	}
	
	@Override
	public Object getVisualizacaoExemplo(Object componente, Object valorExemplo) {
		Label visualizacao = (Label) componente;
		visualizacao.setValue(String.valueOf(getParametroPorNome(TEXTO_FIXO).getValor()));
		return visualizacao;
	}

	@Override
	public boolean isEntradaValida(Object valor) {
		return true;
	}

	@Override
	public String getEventoMascara() {
		return null;
	}

	@Override
	public String getNomeTipoMembroModelo() {
		return "statictext";
	}

	@Override
	public Object getValorVisualizacao(Object componente) {
		return this.componente.getValor(componente);
	}

}
