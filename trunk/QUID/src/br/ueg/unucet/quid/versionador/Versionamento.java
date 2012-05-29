package br.ueg.unucet.quid.versionador;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ueg.unucet.quid.enums.VersionamentoEnum;
import br.ueg.unucet.quid.excessoes.VerificadorJarExcessao;
import br.ueg.unucet.quid.excessoes.VersaoExcessao;
import br.ueg.unucet.quid.extensao.interfaces.IVersionavel;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;
import br.ueg.unucet.quid.utilitarias.ManipuladoraArquivoUtil;


/**Classe responsavel por determinar se um arquivo e uma nova versão, nova revisao ou o mesmo arquivo.
 * @author QUID
 *
 */
public class Versionamento {
	
	/**
	 * Diretorio de manipulacao dos arquivos que serao versionados
	 */
	private String diretorioManipulacao;
	/**
	 * Diretorio que armazena os arquivos versionados.
	 */
	private String diretorioVersionamento;
	/**
	 * Mapeamento dos aquivos versionados pelo seu nome.
	 */
	private Map<String, IVersionavel> arquivosVersionados;
	/**
	 * Mapeamento dos arquivos File a partir de suas classe Iversinavel
	 */
	private Map<IVersionavel, File> arquivosVersionadosFile;
	/**
	 * Mapeamento das classes principais dor arquivos jar a patir de seus nomes.
	 */
	private Map<String, Class<?>> classesVersionadas;
	/**
	 * Mapeamento das versioes dos arquivos.
	 */
	private Map<String, List<Integer>> versoesArquivos;
	/**
	 * Leitor do arquivo de mensagens do framework.
	 */
	private LeitoraPropertiesUtil propertiesMessages;
	/**
	 * Leitor do arquivo de configuracoes do framework.
	 */
	private LeitoraPropertiesUtil propertiesConfiguracoes;
	/**
	 *Verificador de jar do diretorio de versionamento. 
	 */
	private VerificadoraJar verificadorJarDiretorioVersionamento;
	/**
	 * Verificador dos novos jar.
	 */
	private VerificadoraJar verificadorJarNovoarquivo;
	/**
	 * Arquivo atual que sera verionado.
	 */
	private File arquivoAVersionar;
	/**
	 * Intancia da classe principal do arquivo jar.
	 */
	private IVersionavel objetoVersionado;
	/**
	 * Classe principal do arquivo jar.
	 */
	private Class<?> classObjetoVersionado;
	
	public Versionamento(String diretorioManipulacao) throws VersaoExcessao{
		this.diretorioManipulacao = diretorioManipulacao;
		verificarDiretorioManipulacao();
		init();
	}
	
	
	/**Metodo que verifica a versão de um jar
	 * @param arquivo Arquivo jar a ser versionado
	 * @return O tipo de versão do arquivo
	 * @throws VersaoExcessao Caso o arquivo esteje com formato incorreto, dispara excessão informando o tipo do erro que o arquivo contem
	 */
	public VersionamentoEnum versionarArquivo(File arquivo) throws VersaoExcessao{
		this.arquivoAVersionar = arquivo;
		initVersionadorArquivo();
		return versionarArquivo();
	}
	
	/**Metodo que realiza o versionamento de uma nova revisao de um arquivo
	 * Ele remove o arquivo da revisao antiga do diretorio de trabalho e e coloca no diretorio de versionamento
	 * e recoloca o novo arquivo no diretorio de versionamento
	 * @param arquivo Arquivo novo a ser versionado
	 * @param iversionavel Identificador do arquivo a ser versionado
	 */
	public void versionarArquivoNovaRevisao(File arquivo, IVersionavel iversionavel){
		IVersionavel versaoAntiga = this.arquivosVersionados.get(iversionavel.getNome() + iversionavel.getVersao());
		File arquivoAntigo = this.arquivosVersionadosFile.get(versaoAntiga);
		try {
			ManipuladoraArquivoUtil.copiarArquivo(arquivoAntigo.getPath(), diretorioVersionamento + "/" + versaoAntiga.getNome() +"-"+ versaoAntiga.getVersao() + "-" +versaoAntiga.getRevisao() + ".jar");
			ManipuladoraArquivoUtil.copiarArquivo(arquivo.getPath(),diretorioManipulacao + "/" + iversionavel.getNome() +"-"+ iversionavel.getVersao() + "-" +iversionavel.getRevisao() + ".jar");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * Metodo que mapeia um arquivo e sua respectiva intancia IVersionavel.
	 * @param arquivo Arquivo jar.
	 * @param iversionavel Intancia da classe principal do arquivo jar.
	 */
	public void inserirArquivoDiretorioVersionamento(File arquivo, IVersionavel iversionavel){
		try {
			ManipuladoraArquivoUtil.copiarArquivo(arquivo.getAbsolutePath(),this.diretorioVersionamento + "/" + iversionavel.getNome() + "-" + iversionavel.getVersao() + "-" + iversionavel.getRevisao() + ".jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo responsavel por verificar se os atributos necessarios para se realizar o versionamento estao foram informados, posteriormente e chamado o metodo que verifica o tipo de versao do arquivo jar.
	 * @return Tipo da versionamento do arquivo jar.
	 * @throws VersaoExcessao Excessao caso algum atributo necessario ao versionamento nao esteja informado.
	 */
	private VersionamentoEnum versionarArquivo() throws VersaoExcessao {
		try {
			if(this.verificadorJarNovoarquivo.verificarArquivoJar(this.arquivoAVersionar.getAbsolutePath())){
				this.objetoVersionado = this.verificadorJarNovoarquivo.getObjectVersionavel();
				verificarObjetoVersionado();
				this.classObjetoVersionado = this.verificadorJarNovoarquivo.getClassePrincipalJar();
			}else{
				throw new VersaoExcessao(this.verificadorJarNovoarquivo.getMotivoErroConteudo());
			}
		} catch (VerificadorJarExcessao e) {
			throw new VersaoExcessao(this.propertiesMessages.getValor("erro_versionar_arquivo"), e);
		}
		return verificarTipoVersao();
	}


	/**
	 * Metodo que verifica os atributos da instancia da classe principal do objeto a ser versionado.
	 * @throws VersaoExcessao Excessao caso algum atributo da classe principal do jar nao esteje informado.
	 */
	private void verificarObjetoVersionado()throws VersaoExcessao {
		String aux = this.objetoVersionado.getNome();
		checarAtributo(aux, propertiesMessages.getValor("erro_nome_identificador_componente_nao_informando"));
		aux = this.objetoVersionado.getDescricao();
		checarAtributo(aux, propertiesMessages.getValor("erro_descricao_componente_nao_informando"));
		aux = this.objetoVersionado.getVersao().toString();
		checarAtributo(aux, propertiesMessages.getValor("erro_versao_componente_nao_informando"));
		aux = this.objetoVersionado.getRevisao().toString();
		checarAtributo(aux, propertiesMessages.getValor("erro_revisao_componente_nao_informando"));
		
		
	}
	
	/**
	 * Metodo que verifica se determinado String e nula ou vazia.
	 * @param valor String a ser verificada.
	 * @param mensagem Mensagem a ser disparada caso o valor String seje vazio.
	 * @throws VersaoExcessao Excessao caso o valor seje vazia.
	 */
	private void checarAtributo(String valor, String mensagem) throws VersaoExcessao{
		if(valor == null || valor.equals("")){
			throw new VersaoExcessao(mensagem);
		}
	}


	/**
	 * Metodo que descobre a versao e a revisao do arquvo jar e chama o metodo verificador do tipo de versao do arquivo.
	 * @return Tipo de versionamentodo arquivo.
	 */
	private VersionamentoEnum verificarTipoVersao() {
		String nome = this.objetoVersionado.getNome();
		Integer versao = this.objetoVersionado.getVersao();
		Integer revisao = this.objetoVersionado.getRevisao();
		return descobrirTipoVersionamento(nome, versao, revisao);
	}


	/**
	 * Metodo que descobre o tipo de versionamento do arquivo com base em seu numero de versao e revisao.
	 * @param nome Nome do componente a ser versionado.
	 * @param versao Versao do componente
	 * @param revisao Revisao do componente
	 * @return Tipo de versionamento do arquivo.
	 */
	private VersionamentoEnum descobrirTipoVersionamento(String nome,
			Integer versao, Integer revisao) {
		VersionamentoEnum tipoVersao = null;
		String identificador = nome + versao.toString();
		if(this.versoesArquivos.containsKey(identificador)){
			List<Integer> versoes = this.versoesArquivos.get(identificador);
			if(versoes.contains(versao)){
				IVersionavel versionavel = this.arquivosVersionados.get(identificador);
				if(versionavel.getRevisao() == revisao){
					tipoVersao = VersionamentoEnum.MESMA_VERSAO;
				}else{
					tipoVersao = VersionamentoEnum.NOVA_REVISAO;
				}
			}else{
				tipoVersao = VersionamentoEnum.NOVA_VERSAO;
			}
		}else{
			tipoVersao = VersionamentoEnum.NOVA_VERSAO;
		}
		return tipoVersao;
	}


	/**
	 * Metodo que inicializa o verificador de novos jar.
	 */
	private void initVersionadorArquivo() {
		try {
			this.verificadorJarNovoarquivo = new VerificadoraJar();
		} catch (VerificadorJarExcessao e) {
			// Esta excessao jamais sera lancada, pois ja ouve a verificação desta classe
		}
	}


	/**
	 * Metodo que verifica o diretorio de manipulacao dos jar e valido.
	 * @throws VersaoExcessao Excessao caso o diretorio de validacao do jar nao seja valido.
	 */
	private void verificarDiretorioManipulacao() throws VersaoExcessao{
		if(this.diretorioManipulacao == null || this.diretorioManipulacao.equalsIgnoreCase("")){
			throw new VersaoExcessao(propertiesMessages.getValor("erro_diretorio_manipulacao_nao_informado"));
		}
		File file = new File(this.diretorioManipulacao);
		try {
			file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new VersaoExcessao(propertiesMessages.getValor("erro_diretorio_manipulacao_invalido"), e);
		}
		if(!file.isDirectory()){
			throw new VersaoExcessao(propertiesMessages.getValor("erro_diretorio_manipulacao_nao_e_diretorio"));
		}
	}

	/**
	 * Metodo que inicializa os atributos da classe de versionamento.
	 * @throws VersaoExcessao Excessao na inicializacao dos componentes de versionamento.
	 */
	private void init() throws VersaoExcessao {
		this.propertiesMessages = FabricaProperties.loadMessages();
		this.propertiesConfiguracoes = FabricaProperties.loadConfiguracoes();
		this.arquivosVersionados = new HashMap<String, IVersionavel>();
		this.versoesArquivos = new HashMap<String, List<Integer>>();
		this.classesVersionadas = new HashMap<String, Class<?>>();
		this.diretorioVersionamento = initDiretorioVersionamento();
		this.verificadorJarDiretorioVersionamento = initVerificadorJar();
		this.arquivosVersionadosFile = new HashMap<IVersionavel, File>();
		lerClassesVersionadas();
	}
	
	/**
	 * Metodo responsavel por descobrir o diretorio de versiomanento do framework, e verificar se ele e valido.
	 * @return Diretorio de versionamento.
	 * @throws VersaoExcessao Excessao caso o diretorio de versionamento nao seje informado ou seje invalido.
	 */
	private String initDiretorioVersionamento() throws VersaoExcessao{
		String diretorio = propertiesConfiguracoes.getValor("diretorio_versionamento");
		if(diretorio == null || diretorio.equals("")){
			throw new VersaoExcessao(propertiesMessages.getValor("erro_diretorio_versionamento_nao_informando"));
		}
		File f = new File(diretorio);
		try {
			f.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new VersaoExcessao(this.propertiesMessages.getValor("erro_diretorio_versionamento_invalido"));
		}
		if(!f.isDirectory()){
			throw new VersaoExcessao(this.propertiesMessages.getValor("erro_diretorio_versinamento_nao_e_diretorio"));
		}
		return diretorio;
	}


	/**
	 * Metodo que inicializa o verificador de jar.
	 * @return Intancia do verificador de jar.
	 * @throws VersaoExcessao Excessao na inicializacao do verificador de jar.
	 */
	private VerificadoraJar initVerificadorJar() throws VersaoExcessao {
		VerificadoraJar verificador = null;
		try {
			verificador = new VerificadoraJar();
		} catch (VerificadorJarExcessao e) {
			throw new VersaoExcessao(this.propertiesMessages.getValor("erro_verificador_versionamento"), e);
		}
		return verificador;
	}


	/**
	 * Metodo que realiza a leitura das classes versionadas dentro do diretorio de manipulacao.
	 * @throws VersaoExcessao Excessao na leitura dos jar dentro do diretorio de manipulacao.
	 */
	private void lerClassesVersionadas() throws VersaoExcessao {
		File file = new File(this.diretorioManipulacao);
		File files[] = file.listFiles();
		for (File fileAVersionar : files) {
			lerJar(fileAVersionar);
		}
		
	}

	/**
	 * Metodo que realiza a leitura de um jar e o mapeia para o versionador.
	 * @param fileAVersionar Arquivo jar a ser mapeado.
	 * @throws VersaoExcessao Excessao no mapeamento do jar.
	 */
	private void lerJar(File fileAVersionar) throws VersaoExcessao {
		try {
			if(this.verificadorJarDiretorioVersionamento.verificarArquivoJar(fileAVersionar.getPath())){
				mapearVersionamento(fileAVersionar);
			}else{
				throw new VersaoExcessao(fileAVersionar.getName() + " " + this.verificadorJarDiretorioVersionamento.getMotivoErroConteudo());
			}
		} catch (VerificadorJarExcessao e) {
			throw new VersaoExcessao(this.propertiesMessages.getValor("erro_versionador_diretorio_versionamento"), e);
		}
		
	}


	/**
	 * Metodo que mapeia a versao do arquivo jar e o armazena dentro do mapeamento do versionamento.
	 * @param fileAVersionar Arquivo a ser mapeado.
	 */
	private void mapearVersionamento(File fileAVersionar) {
		IVersionavel versionavel = this.verificadorJarDiretorioVersionamento.getObjectVersionavel();
		String nome = versionavel.getNome();
		Integer versao = versionavel.getVersao();
		nome += versao.toString();
		this.arquivosVersionados.put(nome, versionavel);
		this.classesVersionadas.put(nome, this.verificadorJarDiretorioVersionamento.getClassePrincipalJar());
		this.arquivosVersionadosFile.put(versionavel, fileAVersionar);
		if(this.versoesArquivos.containsKey(nome)){
			this.versoesArquivos.get(nome).add(versao);
		}else{
			List<Integer> list = new ArrayList<Integer>();
			list.add(versao);
			this.versoesArquivos.put(nome, list);
		}
	}

	//GETTES AND SETTERS

	public Class<?> getClassObjetoVersionado() {
		return classObjetoVersionado;
	}


	public void setClassObjetoVersionado(Class<?> classObjetoVersionado) {
		this.classObjetoVersionado = classObjetoVersionado;
	}


	public Map<String, Class<?>> getClassesVersionadas() {
		return classesVersionadas;
	}


	public void setClassesVersionadas(Map<String, Class<?>> classesVersionadas) {
		this.classesVersionadas = classesVersionadas;
	}


	public String getDiretorioVersionamento() {
		return diretorioVersionamento;
	}


	public void setDiretorioVersionamento(String diretorioVersionamento) {
		this.diretorioVersionamento = diretorioVersionamento;
	}


	public VerificadoraJar getVerificadorJarNovoarquivo() {
		return verificadorJarNovoarquivo;
	}


	public void setVerificadorJarNovoarquivo(
			VerificadoraJar verificadorJarNovoarquivo) {
		this.verificadorJarNovoarquivo = verificadorJarNovoarquivo;
	}
	
	
	
}
