package br.ueg.unucet.quid.extensao.implementacoes;

import br.ueg.unucet.quid.extensao.enums.ParametroCorEnum;
import br.ueg.unucet.quid.extensao.enums.ParametroPosicionamentoEnum;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Classe responsavel por representar a estrutura basica dos TipoMembroVisaoJSF.
 * @author QUID
 *
 */
public class SuperTipoMembroVisaoJSF extends SuperTipoMembro {

	private static final long serialVersionUID = 7989618814607596082L;

	/**
	 * Metodo que pega o CSS com base nos atributos padroes da classe SuperTipoMembroModelo.
	 * @returnString com o CSS>
	 */
	public String getCss() {
		StringBuilder sb = new StringBuilder();
		sb.append(converterPosicionamento((IParametro<ParametroPosicionamentoEnum>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_POSICIONAMENTO)));
		sb.append(converterSublinhado((IParametro<Boolean>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_SUBLINHADO)));
		sb.append(converterItalico((IParametro<Boolean>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_ITALICO)));
		sb.append(converterNegrito((IParametro<Boolean>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_NEGRITO)));
		sb.append(converterTamanhoFonte(getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_TAMANHO_FONT)));
		sb.append(converterFamiliaFonte(getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_FONT)));
		sb.append(converterCorFonte((IParametro<ParametroCorEnum>) getParametroPorNome(SuperTipoMembroModelo.PARAMETRO_COR)));
		sb.append(" width: 100%; display: block; ");
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
		if (p.getValor() != null)
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

	public boolean isDataValida(String data) {

		return false;
	}

	public boolean isMoedaValida(String moeda) {

		return false;
	}
}