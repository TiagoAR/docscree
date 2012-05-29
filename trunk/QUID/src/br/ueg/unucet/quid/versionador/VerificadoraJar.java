package br.ueg.unucet.quid.versionador;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import br.ueg.unucet.quid.enums.NivelLogEnum;
import br.ueg.unucet.quid.excessoes.VerificadorJarExcessao;
import br.ueg.unucet.quid.extensao.interfaces.IVersionavel;
import br.ueg.unucet.quid.nucleo.GerenciadorLog;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraJarUtil;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;

/**Classe responsavel por verificar se um jar é válido segundo a arquitetura
 * @author QUID
 *
 */
public class VerificadoraJar {
	
	/**
	 * Tempo maximo de execucao do processo do verificador do jar, para o jar ser considerado invalido.
	 */
	private static final Long tempoMaximoExecucaoPadrao =  new Long(1000);
	/**
	 * Atributo que define a classe principal do arquivo jar.
	 */
	private static final String atributoClassePricipalPadrao = "Main-Class";
	/**
	 * Atributo que define o nome do arquivo manifest dentro do jar.
	 */
	private static final String atributoManifestPadrao = "MANIFEST.MF";
	/**
	 * Leitor do arquivo de configuracoes do framework.
	 */
	private LeitoraPropertiesUtil propertiesConfiguracoesUtil;
	/**
	 * Leitor do arquivo de mensagens dentro do framework.
	 */
	private LeitoraPropertiesUtil propertiesMensagensUtil;
	/**
	 * Tempo maximo de execucao do processo de verificacao do jar para este ser considerado invalido.
	 */
	private Long tempoMaximoExecucao;
	/**
	 * String contendo o comando executado para verificar o arquivo jar.
	 */
	private String comando;
	/**
	 * Caminho do jar a ser verificado.
	 */
	private String caminhoAtualJarValidar;
	/**
	 * Processo verificador do jar.
	 */
	private Process processoVerificador;
	/**
	 * Jar a ser verificado.
	 */
	private JarFile jar;
	/**
	 * Caso haja erro no jar, armazena-se aqui qual o erro no conteudo do jar.
	 */
	private String motivoErroConteudo;
	/**
	 * Atributo que define a classe principal do arquivo jar.
	 */
	private String atributoClassePrincipal;
	/**
	 * Atributo que define o arquivo manifest do arquivo jar.
	 */
	private String atributoManifest;
	/**
	 * Nome da classe principal dentro do arquivo jar.
	 */
	private String nomeClassePrincipalJar;
	/**
	 * Leitor de jar.
	 */
	private LeitoraJarUtil leitorJar;
	/**
	 * Classe principal do arquivo jar.
	 */
	private Class<?> classePrincipalJar;
	/**
	 * Intancia da classe principal que implementa IVersionavel.
	 */
	private IVersionavel instanciaClassePrincipal;
	/**
	 * Nome do componte definido pelo jar
	 */
	private String nomeComponente;
	/**
	 * Versao do componente que se encotra dentro do jar.
	 */
	private Integer versaoComponente;
	/**
	 * Diretorio onde se encontra as bibliotecas auxiliares do arquivo jar.
	 */
	private String diretorioBiblioteca;
	
	
	public VerificadoraJar() throws VerificadorJarExcessao{
		propertiesConfiguracoesUtil =  FabricaProperties.loadConfiguracoes();
		propertiesMensagensUtil = FabricaProperties.loadMessages();
		init();
	}
	
	/**Metodo responsavel por verificar se um jar segue o padrao definido pelo framework.
	 * @param caminhoJar Path onde se encontra o arquivo jar.
	 * @return true caso o jar seje valido, false caso contrario.
	 * @throws VerificadorJarExcessao 
	 */
	public boolean verificarArquivoJar(String caminhoJar) throws VerificadorJarExcessao{
		this.caminhoAtualJarValidar = caminhoJar;
		return verificarArquivoConsole() && verificarConteudoJar();
		
	}
	
	/**Metodo que retorna o tipo de erro que foi encontrado dentro do arquivo.
	 * @return O tipo de erro do arquivo
	 */
	public String getMotivoErroConteudo(){
		return this.motivoErroConteudo;
	}
	
	/**Metodo que retorna a classe principal do arquivo jar.
	 * @return Classe principal do arquivo jar
	 */
	public Class<?> getClassePrincipalJar(){
		return this.classePrincipalJar;
	}
	
	/**Metodo que retorna o objeto da classe principal do jar
	 * @return Objeto da classe principal do jar
	 */
	public IVersionavel getObjectVersionavel(){
		return this.instanciaClassePrincipal;
	}
	
	/**
	 * Metodo responsavel por verificar se o conteudo do jar esta correto.
	 * @return True caso o conteudo do jar esteje correto, false caso contrario.
	 */
	private boolean verificarConteudoJar() {
		try {
			jar = new JarFile(caminhoAtualJarValidar);
		} catch (IOException e) {
			//nunca ocorrera esta excessao
		}
		return verifiarManifestJar() && verificarClassePrincipalJar() && verificarClasseInstanciadaJar();
	}

	/**
	 * Metodo que verifica se a classe principal do arquivo jar implementa IVersionavel, se ela pode instanciada e se seu pacote segue a nomenclatura definida pelo framework.
	 * @return True caso a verificacao esteje correta, false caso contrario.
	 */
	private boolean verificarClasseInstanciadaJar() {
		boolean resultado = false;
		if(IVersionavel.class.isAssignableFrom(classePrincipalJar)){
			resultado = verificarInstanciaClassePrincipal() && verificarNomenclaturaPacotes();
		}else{
			this.motivoErroConteudo = propertiesMensagensUtil.getValor("erro_arquivo_nao_versionavel");
		}
		return resultado;
	}

	/**
	 * Metodo responsavel por verificar se os pacotes dentro do arquivo jar seguem a nomenclatura definida pelo framework.
	 * @returnTrue caso a nomenclatura esteje correta, false caso contrario.
	 */
	private boolean verificarNomenclaturaPacotes() {
		this.nomeComponente = instanciaClassePrincipal.getNome();
		this.versaoComponente = instanciaClassePrincipal.getVersao();
		return verificarPacotes();
		
	}

	/**
	 * Metodo responsavel por verificar todos os pacotes dentro do arquivo jar seguem a nomenclatura definida pelo framework,
	 * @return true caso a nomenclatura esteje correta, false caso contrario.
	 */
	private boolean verificarPacotes() {
		boolean resultado = true;
		try{
			Collection<Class<?>> classesJar = leitorJar.listarClassesJarAdicionado(jar);
			for (Class<?> classe : classesJar) {
				if(!verificarPacoteClasse(classe)){
					motivoErroConteudo = propertiesMensagensUtil.getValor("nomenclatura_pacote_errada");
					resultado = false;
					break;
				}
			}
		}catch (Exception e) {
			// esta excessao nunca ira ocorrer pois ja foi validada pelo outro arquivo
		}
		return resultado;
	}

	/**
	 * Metodo que verifica se o pacote de uma classe segue o padrao definido pelo framework.
	 * @param classe Classe a ser verificada a nomenclatura do pacote.
	 * @returnTrue caso a nomemclatura esteje correta, false caso contrario.
	 */
	private boolean verificarPacoteClasse(Class<?> classe) {
		String nomeClasse = classe.getName();
		nomeClasse = nomeClasse.substring(0, nomeClasse.lastIndexOf("."));
		return nomeClasse.indexOf(nomeComponente + versaoComponente.toString()) != -1;
	}

	/**
	 * Metodo que verifica se a classe principal do arquivo jar pode ser intanciada.
	 * @return True caso seje possivel realizar a instanciaçao, false caso contrario/
	 */
	private boolean verificarInstanciaClassePrincipal() {
		this.instanciaClassePrincipal = null;
		try {
			this.instanciaClassePrincipal = (IVersionavel) classePrincipalJar.newInstance();
		} catch (Exception e) {
			motivoErroConteudo = propertiesMensagensUtil.getValor("erro_instancia_classe_principal");
		} 
		return this.instanciaClassePrincipal != null;
	}

	/**
	 * Metodo responsavel por chamar o mapeamento do jar e a verificacao da classePrincipal do jar.
	 * @return True caso a verificacao esteje correta, false caso contrario.
	 */
	private boolean verificarClassePrincipalJar() {
		boolean resultado = false;
		try {
			leitorJar.addArquivo(caminhoAtualJarValidar);
			resultado = validarClassePrincipal();
		} catch (MalformedURLException e) {
			// numca ira dar essa excessao neste ponto
		}
		return resultado;
	}

	/**
	 * Metodo responsavel por pegar a instancia da classe principal do arquivo jar.
	 * @return True caso a classe principal do jar exista dentro de seu conteudo, false caso contrario.
	 */
	private boolean validarClassePrincipal() {
		boolean resultado = false;
		try {
			this.classePrincipalJar = leitorJar.lerClass(nomeClassePrincipalJar + ".class");
			resultado = true;
		} catch (ClassNotFoundException e) {
			motivoErroConteudo = propertiesMensagensUtil.getValor("erro_classe_informada_manifest_nao_existe_jar");
		}
		return resultado;
	}

	/**
	 * Metodo resposavel por tentar pegar o arquivo manifest.mf dentro do arquivo jar e o atributo que define a classe principal do arquivo jar.
	 * @return True caso seje possivel pegar os atributos, false caso contrario.
	 */
	private boolean verifiarManifestJar() {
		boolean resultado = true;
		try{
			Manifest manifest = jar.getManifest();
			Attributes atributos = manifest.getMainAttributes();
			this.nomeClassePrincipalJar = atributos.getValue(atributoClassePrincipal);
			if(this.nomeClassePrincipalJar == null || this.nomeClassePrincipalJar.equalsIgnoreCase("")){
				resultado = false;
				motivoErroConteudo = propertiesMensagensUtil.getValor("atributo_classe_principal_nao_informado");
			}
		}catch (IOException e) {
			resultado = false;
			motivoErroConteudo = propertiesMensagensUtil.getValor("erro_arquivo_jar_nao_contem_manifest");
		}
		return resultado;
	}

	/**
	 * Metodo responsavel por verificar o arquivo jar chamando a o processo de verificacao do jar informado no arquivo de configuracao.
	 * @return True caso a verificao do arquivo esteje correta, false caso contraio.
	 * @throws VerificadorJarExcessao
	 */
	private boolean verificarArquivoConsole() throws VerificadorJarExcessao{
		String comando = prepararComando();
		boolean retorno = false;
		try{
			this.processoVerificador = Runtime.getRuntime().exec(comando);
			retorno = verificarProcesso();
		}catch (IOException e) {
			throw new VerificadorJarExcessao(propertiesMensagensUtil.getValor("erro_comando_verificacao_jar_invalido"), e);
		}catch (InterruptedException e) {
			throw new VerificadorJarExcessao(propertiesMensagensUtil.getValor("erro_contador_verificador_jar"), e);
		}
		if(!retorno){
			motivoErroConteudo = this.propertiesMensagensUtil.getValor("erro_arquivo_jar_invalido");
		}
		return retorno;
	}
	
	/**
	 * Metodo que monitora o processo de verificacao do jar.
	 * Para o jar ser valido, o processo deve retornar 1 em sua execucao.
	 * Caso o processo retorne 0 ou estore o tempo definido para execucao, o jar e dado como invalido.
	 * @return True caso o processo retorne 1 em sua execucao ou false caso o processo retorne 0 ou estore o tempo de execucao.
	 * @throws InterruptedException
	 */
	private boolean verificarProcesso() throws InterruptedException {
		Long contador = tempoMaximoExecucao;
		int resultado = 1;
		while(contador > 0){
			try{
				resultado = processoVerificador.exitValue();
				break;
			}catch (Exception e) {
				Thread.sleep(20);
				contador -= 20;
			}
		}
		processoVerificador.destroy();
		return resultado == 0;
	}

	/**
	 * Metodo que prepara o comando de verificacao do arquivo jar.
	 * @return String contendo o comando que verifica o arquivo jar.
	 */
	private String prepararComando(){
		StringBuilder sb = new StringBuilder();
		sb.append(comando);
		sb.append(" ");
		sb.append("\"");
		sb.append(caminhoAtualJarValidar);
		sb.append("\"");
		return sb.toString();
	}
	
	/**
	 * Metodo que inicializa os atributos da classe de Verificacao do Jar.
	 * @throws VerificadorJarExcessao Falha na inicializacao dos atributos.
	 */
	private void init() throws VerificadorJarExcessao{
		this.tempoMaximoExecucao = lerTempoMaximoExecucao();
		this.comando = lerComandoProcessoVerificadorJar();
		this.atributoClassePrincipal = lerAtributoClassePrincipal();
		this.atributoManifest = lerAtributoManifest();
		this.diretorioBiblioteca = lerAtributoDiretorioBiblioteca();
		initLeitorJar();
	}
	

	/**
	 * Metodo que inicializa o leitor de jar.
	 */
	private void initLeitorJar() {
		this.leitorJar = new LeitoraJarUtil(new URL[]{});
	}

	/**
	 * Metodo que encontra o atributo que define o nome do arquivo manifest dentro do jar.
	 * @return String do atributo manifest.
	 */
	private String lerAtributoManifest() {
		String atributo = propertiesConfiguracoesUtil.getValor("atributo_manifest");
		if(atributo == null || atributo.equalsIgnoreCase("")){
			atributo = this.atributoManifestPadrao;
			GerenciadorLog.getInstanciaAtual().registrarLog(propertiesMensagensUtil.getValor("aviso_atributo_manifest_nao_informado"), NivelLogEnum.LOG_EXCESSAO);
		}
		return atributo;
	}

	/**
	 * Metodo que encontra o atributo que define o atributo dentro do manifest.mf que define a classe principal do arquivo jar.
	 * @return String contendo o nome do atributo.
	 */
	private String lerAtributoClassePrincipal() {
		String atributo = propertiesConfiguracoesUtil.getValor("atributo_class_principal");
		if(atributo == null || atributo.equalsIgnoreCase("")){
			GerenciadorLog.getInstanciaAtual().registrarLog(propertiesMensagensUtil.getValor("aviso_atributo_classe_principal_nao_utilizado"), NivelLogEnum.LOG_EXCESSAO);
			atributo = atributoClassePricipalPadrao;
		}
		return atributo;
	}
	

	/**
	 * Metodo que encontra o tempo maximo de execucao do processo de verificacao do jar.
	 * @return Tempo maximo de execucao do processo de verificacao do jar.
	 */
	private Long lerTempoMaximoExecucao(){
		String tempo = propertiesConfiguracoesUtil.getValor("tempo_maximo_execucao_processo_verificar_jar");
		Long aux = null;
		if(tempo != null && !tempo.equalsIgnoreCase("")){
			try{
				aux = Long.valueOf(tempo);
			}catch (Exception e) {
				GerenciadorLog.getInstanciaAtual().registrarLog(propertiesMensagensUtil.getValor("aviso_tempo_execucao_processo_valor_invalido"), NivelLogEnum.LOG_EXCESSAO);
			}
		}else{
			GerenciadorLog.getInstanciaAtual().registrarLog(propertiesMensagensUtil.getValor("aviso_tempo_execucao_processo_nao_informado"), NivelLogEnum.LOG_EXCESSAO);
		}
		if(aux == null) aux = tempoMaximoExecucaoPadrao;
		return aux;
	}
	
	/**
	 * Metodo que encontra o comando de verificacao do jar.
	 * @return String contendo o comando de verificacao do jar.
	 * @throws VerificadorJarExcessao Excessao caso o comando nao tenha sido informado no arquivo de configuracao do framework.
	 */
	private String lerComandoProcessoVerificadorJar() throws VerificadorJarExcessao{
		String comando = propertiesConfiguracoesUtil.getValor("comando_verificar_jar");
		if(comando == null || comando.equalsIgnoreCase("")){
			throw new VerificadorJarExcessao(propertiesMensagensUtil.getValor("erro_comando_verificacao_jar_nao_informado"));
		}
		return comando;
	}
	
	/**
	 * Metodo que encontra o diretorio onde se encontram as bibliotecas necessarias para verificar os jar.
	 * @return String do diretorio das bibliotecas.
	 * @throws VerificadorJarExcessao Excessao caso o diretorio nao tenha sido informado no arquivo de configuracao do framework
	 */
	private String lerAtributoDiretorioBiblioteca() throws VerificadorJarExcessao {
		String diretorioBiblioteca = propertiesConfiguracoesUtil.getValor("diretorio_jar_extensao");
		if(diretorioBiblioteca == null || diretorioBiblioteca.equalsIgnoreCase("")){
			throw new VerificadorJarExcessao(propertiesMensagensUtil.getValor("diretorio_biblioteca_extensao_nao_informado"));
		}
		return diretorioBiblioteca;
	}
	
	//GETTES AND SETTERS

	public LeitoraJarUtil getLeitorJar() {
		return leitorJar;
	}

	public void setLeitorJar(LeitoraJarUtil leitorJar) {
		this.leitorJar = leitorJar;
	}
	
	
	
	
	

}
