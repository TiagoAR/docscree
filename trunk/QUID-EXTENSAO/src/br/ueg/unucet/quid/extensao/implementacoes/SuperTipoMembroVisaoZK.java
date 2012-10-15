package br.ueg.unucet.quid.extensao.implementacoes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Collection;

import javax.swing.ImageIcon;

import sun.awt.image.ToolkitImage;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.enums.ParametroCorEnum;
import br.ueg.unucet.quid.extensao.enums.ParametroPosicionamentoEnum;
import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

public abstract class SuperTipoMembroVisaoZK<E extends IComponenteInterface> extends SuperTipoMembro implements ITipoMembroVisao {
	
	protected Membro membro;
	protected E componente;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1555313869957854697L;
	
	@Override
	public String getPlataforma() {
		return "ZK";
	}
	
	public BufferedImage getImagemZK() {
		ImageIcon icone = (ImageIcon) getImagemIlustrativa();
		if (icone != null) {
			return ((ToolkitImage) icone.getImage()).getBufferedImage();
		}
		return gerarImagemNula();
	}
	
	private BufferedImage gerarImagemNula() {
		BufferedImage bi = new BufferedImage(25, 25, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setStroke(new BasicStroke(5));
        Line2D line = new Line2D.Double(3, 3, 24, 24);
        g2d.setColor(Color.white);
        g2d.draw(line);
        return bi;
	}
	
	/**
	 * Metodo que pega o CSS com base nos atributos padroes da classe SuperTipoMembroModelo.
	 * @returnString com o CSS>
	 */
	@SuppressWarnings("unchecked")
	public String getCss() {
		StringBuilder sb = new StringBuilder();
		sb.append(converterPosicionamento((IParametro<ParametroPosicionamentoEnum>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_POSICIONAMENTO)));
		sb.append(converterSublinhado((IParametro<Boolean>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_SUBLINHADO)));
		sb.append(converterItalico((IParametro<Boolean>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_ITALICO)));
		sb.append(converterNegrito((IParametro<Boolean>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_NEGRITO)));
		sb.append(converterTamanhoFonte(getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_TAMANHO_FONT)));
		sb.append(converterFamiliaFonte(getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_FONT)));
		sb.append(converterCorFonte((IParametro<ParametroCorEnum>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_COR)));
		return sb.toString();
	}
	
	public String getCss(Membro membro) {
		StringBuilder sb = new StringBuilder();
		sb.append(getCss());
		sb.append(getPosicionamento(membro, 0));
		return sb.toString();
	}
	
	public String getPosicionamento(Membro membro, Integer addPadding) {
		StringBuilder sb = new StringBuilder();
		sb.append(converterAltura(membro.getAltura() + addPadding));
		sb.append(converterComprimento(membro.getComprimento() + addPadding));
		sb.append(converterPosX(membro.getX()));
		sb.append(converterPosY(membro.getY()));
		return sb.toString();
	}

	/**
	 * Metodo que pega o parametro a partir de seu nome.
	 * @param nome Nome do parametro.
	 * @return Parametro especificado pelo nome.
	 */
	protected IParametro<?> getParametroPorNome(String nome) {
		IParametro<?> param = null;
		for (IParametro<?> p : getListaParametros()) {
			if (p.getNome().equals(nome)) {
				param = p;
				break;
			}
		}
		return param;
	}
	
	private String converterAltura(Integer valor) {
		if (valor > 0)
			return "height: " + String.valueOf(valor) + "px; ";
		return " ";
	}
	
	private String converterComprimento(Integer valor) {
		if (valor > 0)
			return "width: " + String.valueOf(valor) + "px; ";
		return " ";
	}
	
	private String converterPosX(Integer valor) {
		if (valor > 0)
			return "left: " + String.valueOf(valor) + "px; ";
		return " ";
	}
	
	private String converterPosY(Integer valor) {
		if (valor > 0)
			return "top: " + String.valueOf(valor) + "px; ";
		return " ";
	}

	/**
	 * Metodo que converte o parametro de posicionamento em CSS.
	 * @param p Parametro de posicionamento a ser convertido.
	 * @return String do CSS.
	 */
	private String converterPosicionamento(IParametro<ParametroPosicionamentoEnum> p) {
		if (p.getValor() != null)
			return "text-align: " + p.getValorString() + "; ";
		return "text-align: left; ";
	}

	/**
	 * Metodo que converte o parametro sublinhado em CSS.
	 * @param p Parametro sublinhado a ser convertido.
	 * @return String do CSS.
	 */
	private String converterSublinhado(IParametro<Boolean> p) {
		if (p.getValor() != null && p.getValor())
			return " text-decoration: underline; ";
		return "";
	}

	/**
	 * Metodo que converte o parametro italico em CSS.
	 * @param p Parametro italico a ser convertido.
	 * @return String do CSS.
	 */
	private String converterItalico(IParametro<Boolean> p) {
		if (p.getValor() != null && p.getValor())
			return " font-style: italic; ";
		return "";
	}

	/**
	 * Metodo que converte o parametro negrito em CSS.
	 * @param p Parametro negrito a ser convertido.
	 * @return String do CSS.
	 */
	private String converterNegrito(IParametro<Boolean> p) {
		if (p.getValor() != null && p.getValor())
			return " font-weight: bold; ";
		return "";
	}
	
	/**
	 * Metodo que converte o parametro tamanho de fonte em CSS.
	 * @param p Parametro tamanho de fonte a ser convertido.
	 * @return String do CSS.
	 */
	private String converterTamanhoFonte(IParametro<?> p) {
		if (p.getValor() != null && !String.valueOf(p.getValor()).equals(String.valueOf(0)))
			return " font-size: " + p.getValor() + "px; ";
		return "font-size: 10px; ";
	}

	
	/**
	 * Metodo que converte o parametro fonte em CSS.
	 * @param p Parametro fonte a ser convertido.
	 * @return String do CSS.
	 */
	private String converterFamiliaFonte(IParametro<?> p) {
		if (p.getValor() != null)
			return " font-family: '" + p.getValor() + "'; ";
		return "";
	}

	/**
	 * Metodo que converte o parametro cor em CSS.
	 * @param p Parametro cor a ser convertido.
	 * @return String do CSS.
	 */
	private String converterCorFonte(IParametro<ParametroCorEnum> p) {
		if (p.getValor() != null)
			return " color: " + p.getValorString() + "; ";
		return "";
	}

	@Override
	public void setValor(Object valor) {
		this.membro.getTipoMembroModelo().setValor(valor);
		this.componente.setValor(valor);
	}
	
	@Override
	public Object getValor() {
		return this.membro.getTipoMembroModelo().getValor();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembro#getListaParametros()
	 */
	@Override
	public Collection<IParametro<?>> getListaParametros() {
		return this.membro.getTipoMembroModelo().getListaParametros();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembro#setListaParametros(java.util.Collection)
	 */
	@Override
	public void setListaParametros(Collection<IParametro<?>> parametros) {
		this.membro.getTipoMembroModelo().setListaParametros(parametros);
	}
	
	public abstract Object getVisualizacaoExemplo(Object componente, Object valorExemplo);

	/**
	 * @return the membro
	 */
	public Membro getMembro() {
		return membro;
	}

	/**
	 * @param membro the membro to set
	 */
	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	/**
	 * @return the componente
	 */
	protected E getComponente() {
		return componente;
	}

}
