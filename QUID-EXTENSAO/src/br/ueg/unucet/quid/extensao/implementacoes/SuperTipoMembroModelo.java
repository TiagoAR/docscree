package br.ueg.unucet.quid.extensao.implementacoes;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
import br.ueg.unucet.quid.extensao.enums.ParametroCorEnum;
import br.ueg.unucet.quid.extensao.enums.ParametroPosicionamentoEnum;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.utilitarias.ListagemGenericaEnum;

/**
 * Classe responsavel por representar a estrutura basica dos TiposMembros modelo. Nessa
 * estrutura basica serao implementados os parametros principais dos
 * TiposMembro, como font, cor, etc
 * 
 * @author QUID
 * 
 */
public abstract class SuperTipoMembroModelo extends SuperTipoMembro {

	protected Integer marcador;
	public static final String PARAMETRO_COR = "COR";
	public static final String PARAMETRO_FONT = "FONT";
	public static final String PARAMETRO_TAMANHO_FONT = "TAMANHO_FONT";
	public static final String PARAMETRO_NEGRITO = "NEGRITO";
	public static final String PARAMETRO_ITALICO = "ITALICO";
	public static final String PARAMETRO_SUBLINHADO = "SUBLINHADO";
	public static final String PARAMETRO_POSICIONAMENTO = "POSICIONAMENTO";

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembro#getListaParametros()
	 */
	public Collection<IParametro<?>> getListaParametros() {
		if (super.getListaParametros() == null || super.getListaParametros().isEmpty()) {
			initParametrosComuns();
			Collection<?> parametrosAdicionais = getParametrosAdicionais();
			if (parametrosAdicionais != null)
				super.getListaParametros().addAll(getParametrosAdicionais());
		}
		return super.getListaParametros();
	}

	/**
	 * Metodo que inicializa os parametros do SuperTipoMembro.
	 * Esses parametros sao: Cor, Fonte, TamanhoFonte, Negrito, Italico, Sublinhadoe e Posicionamento.
	 */
	private void initParametrosComuns() {
		Collection<IParametro<?>> parametrosIniciais = new ArrayList<IParametro<?>>();
		parametrosIniciais.add(getParametroCor());
		parametrosIniciais.add(getParametroFont());
		parametrosIniciais.add(getParametroTamanhoFont());
		parametrosIniciais.add(getParametroNegrito());
		parametrosIniciais.add(getParametroItalico());
		parametrosIniciais.add(getParametroSublinhado());
		parametrosIniciais.add(getParametroPosicionamento());
		setListaParametros(parametrosIniciais);
	}

	/**
	 * Metodo que instancia o parametro de posicionamento.
	 * @return Instancia o parametro de posicionamento.
	 */
	private IParametro<ParametroPosicionamentoEnum> getParametroPosicionamento() {
		ParametroEnum<ParametroPosicionamentoEnum> parametro = new ParametroEnum<ParametroPosicionamentoEnum>(
				ParametroPosicionamentoEnum.class);
		parametro.setNome(PARAMETRO_POSICIONAMENTO);
		parametro.setRotulo("Posicionamento");
		parametro.setObrigatorio(false);
		parametro.setValor(ParametroPosicionamentoEnum.ESQUERDA.toString());
		Collection<String> entrada = new ArrayList<String>();
		ListagemGenericaEnum listaUtil = new ListagemGenericaEnum(ParametroPosicionamentoEnum.class);
		entrada = listaUtil.getList();
		parametro.setListDominioTipo(entrada);
		return parametro;
	}

	/**
	 * Metodo que instancia o parametro sublinhado.
	 * @return Instancia o parametro sublinhado.
	 */
	private IParametro<Boolean> getParametroSublinhado() {
		return getParametroBoolean(PARAMETRO_SUBLINHADO, "Sublinhado", false);
	}

	/**
	 * Metodo que instancia o parametro Italico.
	 * @return Instancia o parametro Italico.
	 */
	private IParametro<Boolean> getParametroItalico() {
		return getParametroBoolean(PARAMETRO_ITALICO, "Italico", false);
	}
	
	
	/**
	 * Metodo que instancia o parametro Negrito.
	 * @return Instancia o parametro negrito.
	 */
	private IParametro<Boolean> getParametroNegrito() {
		return getParametroBoolean(PARAMETRO_NEGRITO, "Negrito", false);
	}

	/**
	 * Metodo que inicaliza um parametro do tipo boolean.
	 * @param nome Nome do parametro.
	 * @param rotulo Rotulo do parametro.
	 * @param obrigatorio True caso o parametro seja obrigatorio, false caso contrario.
	 * @return Instancia do parametro boolean.
	 */
	protected IParametro<Boolean> getParametroBoolean(String nome, String rotulo, Boolean obrigatorio) {
		ParametroBoolean parametro = new ParametroBoolean(Boolean.class);
		parametro.setNome(nome);
		parametro.setRotulo(rotulo);
		parametro.setObrigatorio(obrigatorio);
		Collection<String> entrada = new ArrayList<String>();
		parametro.setListDominioTipo(entrada);
		return parametro;
	}

	/**
	 * Metodo que instancia o parametro de Tamanho de Fonte.
	 * @return Instancia o parametro de Tamanho de Fonte.
	 */
	private IParametro<?> getParametroTamanhoFont() {
		ParametroInteiro parametro = new ParametroInteiro(Integer.class);
		parametro.setNome(PARAMETRO_TAMANHO_FONT);
		parametro.setRotulo("Tamanho Fonte");
		parametro.setDominioEntrada(DominioEntradaEnum.NUMERICO);
		parametro.setObrigatorio(true);
		return parametro;
	}

	/**
	 * Metodo que instancia o parametro de Estilo de Fonte.
	 * @return Instancia o parametro de Estilo de Fonte.
	 */
	private IParametro<?> getParametroFont() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontes = ge.getAvailableFontFamilyNames();
		Parametro<String> parametro = new Parametro<String>(String.class);
		parametro.setNome(PARAMETRO_FONT);
		parametro.setRotulo("Fonte");
		parametro.setObrigatorio(true);
		parametro.setValor("Arial");
		Collection<String> listaFontes = new ArrayList<String>();
		for (String fonte : fontes) {
			listaFontes.add(fonte);
		}
		parametro.setListDominioTipo(listaFontes);
		return parametro;
	}

	/**
	 * Metodo que instancia o parametro de Cor.
	 * @return Instancia o parametro de Cor.
	 */
	private IParametro<ParametroCorEnum> getParametroCor() {
		ParametroEnum<ParametroCorEnum> parametro = new ParametroEnum<ParametroCorEnum>(ParametroCorEnum.class);
		parametro.setObrigatorio(true);
		parametro.setNome(this.PARAMETRO_COR);
		parametro.setRotulo("Cor");
		parametro.setValor(ParametroCorEnum.BLACK.toString());
		Collection<String> entrada = new ArrayList<String>();
		ListagemGenericaEnum listaUtil = new ListagemGenericaEnum(ParametroCorEnum.class);
		entrada = listaUtil.getList();
		parametro.setListDominioTipo(entrada);
		return parametro;
	}

	/**
	 * Metodo que deve ser implementado pelas classes filhas.
	 * Este metodo especifica os parametros adicionais que os TiposMembroModelo necessitam.
	 * @return Lista de Parametros adicionais.
	 */
	public abstract Collection<IParametro<?>> getParametrosAdicionais();
}
