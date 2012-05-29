package br.ueg.unucet.quid.dominios;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.ueg.unucet.quid.enums.TipoErroEnum;

/**Classe responsavel por represetar os parametros de retorno de execução do framework
 * @author Johnys
 *
 */
public class Retorno<K, V> implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4614000980358138834L;
	/**
	 * Constante que identifica o parametro LISTA quando adicionado na lista de parametros.
	 */
	public static final String PARAMERTO_LISTA = "LIST";
	/**
	 * Constante que identifica o parametro TIPO_MEMBRO quando adicionado no retorno.
	 */
	public static final String PARAMETRO_LISTA_PARAMETROS_TIPOMEMBRO = "PARAMETROS_TIPOMEMBRO";
	/**
	 * Constante que identifica o parametro lista de parametros de servicos quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_LISTA_PARAMETRO_SERVICO = "PARAMETRO_SERVICO";
	/**
	 * Constante que identifica o parametro artefato quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_ARTEFATO = "ARTEFATO";
	/**
	 * Constante que identifica o parametro membro quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_MEMBRO = "MEMBRO";
	/**
	 * Constante que identifica o parametro artefato clonado quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_ARTEFATO_CLONADO = "ARTEFATO_CLONADO";
	/**
	 * Constante que identifica o parametro interface de preenchimento quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_INTERFACE_PREENCHIMENTO = "INTERFACE_PREENCHIMENTO";
	/**
	 * Constante que identifica o parametro interface de visualizacao quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_INTERFACE_VISUALIZACAO = "INTERFACE_VISUALIZACAO";
	/**
	 * Constante que identifica o parametro "parametro nao informado ou invalido" quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_NAO_INFORMADO_INVALIDO = "PARAMETRO_NAO_INFORMADO";
	/**
	 * Constante que identifica o parametro nova instancia de um objeto quando adicionado na lista de parametros.
	 */
	public static final String PARAMETRO_NOVA_INSTANCIA="NOVA_INSTANCIA";
	/**
	 * Atributo que identifica se o retorno foi de sucesso de execucao ou falha.
	 */
	private boolean sucesso;
	/**
	 * Mensagem quando o retorno e do tipo falha de execucao.
	 */
	private String mensagem;
	/**
	 * Throwable que originou a falha na execucao.
	 */
	private Throwable erro;
	/**
	 * Atributo que identifica a gravidade do erro ocorrido.
	 */
	private TipoErroEnum tipoErro;
	/**
	 * Lista de parametros do retorno.
	 */
	private Map<K, V> parametros;
	
	public Retorno(){
		parametros = new HashMap<K, V>();
	}
	
	public Retorno(boolean sucesso){
		this.sucesso = sucesso;
	}
	
	public Retorno(boolean sucesso, Map<K, V> parametros){
		this.sucesso = sucesso;
		this.parametros = parametros;
	}
	
	public Retorno(boolean sucesso, String mensagem, TipoErroEnum tipoErro){
		this.sucesso = sucesso;
		this.mensagem = mensagem;
		this.tipoErro = tipoErro;
	}
	
	public Retorno(boolean sucesso, String mensagem, TipoErroEnum tipoErro, Map<K, V> parametros){
		this.sucesso = sucesso;
		this.mensagem = mensagem;
		this.tipoErro = tipoErro;
		this.parametros = parametros;
	}
	
	//GETTES AND SETTERS
	
	public boolean isSucesso() {
		return sucesso;
	}
	
	public void adicionarParametro(K chave, V valor){
		this.parametros.put(chave, valor);
	}
	
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public TipoErroEnum getTipoErro() {
		return tipoErro;
	}
	public void setTipoErro(TipoErroEnum tipoErro) {
		this.tipoErro = tipoErro;
	}
	public Map<K, V> getParametros() {
		return parametros;
	}
	public void setParametros(Map<K, V> parametros) {
		this.parametros = parametros;
	}

	public Throwable getErro() {
		return erro;
	}

	public void setErro(Throwable erro) {
		this.erro = erro;
	}
	
	
	

}
