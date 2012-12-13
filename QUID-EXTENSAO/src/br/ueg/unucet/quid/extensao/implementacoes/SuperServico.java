package br.ueg.unucet.quid.extensao.implementacoes;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;

/**
 * Classe abstrata que representa os Serviços que podem ser executados pelo QUID
 * 
 * @author Diego
 *
 */
@SuppressWarnings("rawtypes")
public abstract class SuperServico implements IServico {

	/**
	 * Lista de parametros sobre o Serviço.
	 */
	private Collection<IParametro<?>> listaParametros;

	/**
	 * Construtor default
	 */
	public SuperServico() {
		setListaParametros(new ArrayList<IParametro<?>>());
		inicializacaoConstrutor();
	}

	/**
	 * @see IServico#executaAcao()
	 */
	@Override
	public Collection<IParametro> executaAcao() {
		Collection<IParametro> retorno = new ArrayList<IParametro>();
		IParametro<Boolean> parametroResultado = getParametroRespostaResultado();
		retorno.add(parametroResultado);
		try {
			Collection<IParametro> acao = efetuaAcao();
			if (acao != null) {
				retorno.addAll(acao);
			}
			parametroResultado.setValorClass(Boolean.TRUE);
		} catch (Exception e) {
			parametroResultado.setValorClass(Boolean.FALSE);
			IParametro<Exception> parametroExcecao = getParametroRespostaExcecao();
			parametroExcecao.setValorClass(e);
			retorno.add(parametroExcecao);
		}
		return retorno;
	}

	/**
	 * 
	 * @return retorna o Parametro que representa o resultado da execução de um Serviço
	 */
	protected IParametro<Boolean> getParametroRespostaResultado() {
		return getParametroBoolean(PARAMETRO_RESPOSTA_RESULTADO, "Resultado",
				true);
	}

	/**
	 * 
	 * @return parametro que representa a exceção causada pela execução do Serviço
	 */
	protected IParametro<Exception> getParametroRespostaExcecao() {
		Parametro<Exception> parametro = new Parametro<Exception>(
				Exception.class);
		parametro.setNome(PARAMETRO_RESPOSTA_EXCECAO);
		parametro.setRotulo("Exceção");
		return parametro;
	}

	/**
	 * Metodo que inicaliza um parametro do tipo boolean.
	 * 
	 * @param nome
	 *            Nome do parametro.
	 * @param rotulo
	 *            Rotulo do parametro.
	 * @param obrigatorio
	 *            True caso o parametro seja obrigatorio, false caso contrario.
	 * @return Instancia do parametro boolean.
	 */
	protected IParametro<Boolean> getParametroBoolean(String nome,
			String rotulo, Boolean obrigatorio) {
		ParametroBoolean parametro = new ParametroBoolean(Boolean.class);
		parametro.setNome(nome);
		parametro.setRotulo(rotulo);
		parametro.setObrigatorio(obrigatorio);
		Collection<String> entrada = new ArrayList<String>();
		parametro.setListDominioTipo(entrada);
		return parametro;
	}

	/**
	 * Metodo que pega o parametro a partir de seu nome.
	 * 
	 * @param nome
	 *            Nome do parametro.
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
	 * Método chamado dentro do ExecutarServico, responsável por executar a ação específica do Serviço
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract Collection<IParametro> efetuaAcao() throws Exception;

	/**
	 * Método chamado dentro do construtor do Serviço,
	 * serve para inicializar variáveis e outras operações
	 */
	protected abstract void inicializacaoConstrutor();

	// GETTERS AND SETTERS

	@Override
	public Collection<IParametro<?>> getListaParametros() {
		return this.listaParametros;
	}

	@Override
	public void setListaParametros(Collection<IParametro<?>> lista) {
		this.listaParametros = lista;
	}

	public Object getArtefatoModelo() {
		return this.getParametroPorNome(PARAMETRO_ARTEFATO_MODELO).getValor();
	}

	public Object getProjetoEscolhido() {
		return this.getParametroPorNome(PARAMETRO_PROJETO).getValor();
	}

	public Object getUsuario() {
		return this.getParametroPorNome(PARAMETRO_USUARIO).getValor();
	}

}
