package br.ueg.unucet.quid.controladores;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.enums.TipoTipoMembroEnum;
import br.ueg.unucet.quid.enums.VersionamentoEnum;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.excessoes.TipoMembroExcessao;
import br.ueg.unucet.quid.excessoes.VersaoExcessao;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOTipoMembro;
import br.ueg.unucet.quid.interfaces.ITipoMembroControle;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;
import br.ueg.unucet.quid.utilitarias.ManipuladoraArquivoUtil;
import br.ueg.unucet.quid.versionador.Versionamento;

/**
 * Classe responsavel por realizar as acoes sobre os TipoMembro
 * 
 * @author QUID
 * 
 */
@Service("TipoMembroControle")
@Scope("singleton")
public class TipoMembroControle extends GenericControle<TipoMembro, Long> implements ITipoMembroControle<TipoMembro, Long> {

	/**
	 * Atributo responsavel por realizar as operacoes de versionamento TipoMembro
	 */
	private Versionamento versionamento;
	/**
	 * Diretorio de armazenamento dos TiposMembros
	 */
	private String diretorioArmazenamentoTipoMembro;
	/**
	 * Atributo responsavel por realizar a leitura do arquivo de configuracoes.properties
	 */
	private LeitoraPropertiesUtil propertiesConfiguracoesUtil;
	/**
	 * Atributo que realiza um mapeamento entre as classes ITipoMembro modelo que serao mapeadas e seus respectivos arquivos
	 */
	private Map<ITipoMembroModelo, File> tiposMembroModeloAMapear;
	/**
	* Atributo que realiza um mapeamento entre as classes ITipoMembro visao que serao mapeadas e seus respectivos arquivos 
	 */
	private Map<ITipoMembroVisao, File> tiposMembroVisaoAMapear;
	/**
	 * Atributo que realiza um mapeamento entre os arquivos que serao mapeados e as suas classes ITipoMembro modelo
	 */
	private Map<File, ITipoMembroModelo> tiposMembroModeloAMapearFile;
	/**
	 * Atributo que realiza um mapeamento entre os arquivos que serao mapeados e as suas classes ITipoMembro visao
	 */
	private Map<File, ITipoMembroVisao> tiposMembroVisaoAMapearFile;
	/**
	 * Atributo que relaciona os nomes dos TiposMembros modelo mapeados com seus respectivos nomes.
	 */
	private Map<String, ITipoMembroModelo> tiposMembroModeloMapeados;
	/**
	 * Atributo que relaciona os nomes dos TiposMembros visao mapeados com seus respectivos nomes.
	 */
	private Map<String, ITipoMembroVisao> tiposMembroVisaoMapeados;
	/**
	 * Arquivos que serao importados
	 */
	private File[] arquivosAMapear;
	/**
	 * Atributo que relaciona os arquivos que serao mapeados com seus respectivos tipos de versionamento
	 */
	private Map<File, VersionamentoEnum> tipoVersionamentoArquivos;
	/**
	 * Atributo responsvavel por realizar a verificacao do espelho entre os TiposMembro modelo e visao
	 */
	private EspelhoControle espelhoControle;
	/**
	 * Atributo que armazena as informacoes sobre a importacao de cada arquivo.
	 */
	private Map<File, String> informacoesArquivos;
	/**
	 * Atributo responsavel por realizar as operacoes de persistencia do Membro
	 */
	@Autowired
	private IDAOTipoMembro<TipoMembro, Long> daoTipoMembro;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.ITipoMembroControle#mapearTiposMembro(java
	 * .io.File[])
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<File, String> mapearTiposMembro(File[] arquivos) throws TipoMembroExcessao {
		this.arquivosAMapear = arquivos;
		setSucesso(true);
		this.tiposMembroModeloAMapear = new HashMap<ITipoMembroModelo, File>();
		this.tiposMembroVisaoAMapear = new HashMap<ITipoMembroVisao, File>();
		this.tipoVersionamentoArquivos = new HashMap<File, VersionamentoEnum>();
		this.informacoesArquivos = new HashMap<File, String>();
		verificarArquivos();
		mapearTiposMembros();
		if (isSucesso()) {
			init();
		}
		return this.informacoesArquivos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.ITipoMembroControle#getTipoMembroModelo
	 * (java.lang.String, java.lang.Integer)
	 */
	@Override
	public ITipoMembroModelo getTipoMembroModelo(String nome, Integer versao) {
		try {
			return this.tiposMembroModeloMapeados.get(nome + versao).getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroControle#
	 * getIntanciaTipoMembroControle(br.ueg.unucet.quid.dominios.TipoMembro)
	 */
	@Override
	public ITipoMembroModelo getIntanciaTipoMembroControle(TipoMembro tipoMembro) {
		try {
			return this.tiposMembroModeloMapeados.get(tipoMembro.getNome() + tipoMembro.getVersao()).getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.ITipoMembroControle#getInstanciaTipoMembroVisao
	 * (br.ueg.unucet.quid.dominios.TipoMembro)
	 */
	@Override
	public ITipoMembroVisao getInstanciaTipoMembroVisao(TipoMembro tipoMembro) {
		try {
			return this.tiposMembroVisaoMapeados.get(tipoMembro.getNome() + tipoMembro.getVersao()).getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.ITipoMembroControle#getTipoMembro(br.ueg
	 * .unucet.quid.extensao.interfaces.ITipoMembro)
	 */
	@Override
	public TipoMembro getTipoMembro(ITipoMembro iTipoMembro) {
		TipoMembro tipoMembro = new TipoMembro();
		tipoMembro.setNome(iTipoMembro.getNome());
		tipoMembro.setVersao(iTipoMembro.getVersao());
		tipoMembro.setRevisao(iTipoMembro.getRevisao());
		return getTipoMembroCadastrado(tipoMembro);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroControle#listaTipoMembroModelo()
	 */
	@Override
	public Collection<ITipoMembroModelo> listaTipoMembroModelo() {
		return this.tiposMembroModeloMapeados.values();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroControle#listaTipoMembroVisao()
	 */
	@Override
	public Collection<ITipoMembroVisao> listaTipoMembroVisao() {
		return this.tiposMembroVisaoMapeados.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.ITipoMembroControle#getTipoMembroVisao(
	 * br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	@Override
	public ITipoMembroVisao getTipoMembroVisao(ITipoMembroModelo tipoMembroModelo) {
		String nome = tipoMembroModelo.getNome();
		for (ITipoMembroVisao tipoMembroVisao : this.tiposMembroVisaoMapeados.values()) {
			if (tipoMembroVisao.getNomeTipoMembroModelo().equals(nome)) {
				return tipoMembroVisao;
			}
		}
		return null;
	}
	
	@Override
	public ITipoMembroModelo getTipoMembroModelo(ITipoMembroVisao tipoMembroVisao) {
		for (ITipoMembroModelo tipoMembroModelo : this.tiposMembroModeloMapeados.values()) {
			if (tipoMembroVisao.getNomeTipoMembroModelo().equals(tipoMembroModelo.getNome())) {
				try {
					return tipoMembroModelo.getClass().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * Metodo responsavel por realizar a inicializacao dos: diretorio de versionamento, arquivo de configuracoes, versionador e o verificador de espelho. Ele tambem e responsavel por carregar
	 * os tiposmembro modelo e visao cadastrados
	 * @throws TipoMembroExcessao Excessao caso aja algum problema na inicializacao dos componentes
	 */
	@PostConstruct
	private void init() throws TipoMembroExcessao {
		this.propertiesConfiguracoesUtil = FabricaProperties.loadConfiguracoes();
		initDiretorioArmazenamentoTipoMembro();
		verificarDelecaoArquivosRevisionados();
		initVersionador();
		initTiposMembrosMapeados();
		initControladorEspelhos();

	}

	/**
	 * Metodo responsavel por verificar o diretorio de versionamento informado dentro do arquivo de configuracoes.properties, verificando se ele foi informado
	 * e se ele e valido
	 * @throws TipoMembroExcessao Excessao caso o diretorio nao seja informado ou seja invalido.
	 */
	private void initDiretorioArmazenamentoTipoMembro() throws TipoMembroExcessao {
		this.diretorioArmazenamentoTipoMembro = propertiesConfiguracoesUtil.getValor("diretorio_tipomembro");
		if (this.diretorioArmazenamentoTipoMembro == null || this.diretorioArmazenamentoTipoMembro.equals("")) {
			throw new TipoMembroExcessao(propertiesMessagesUtil.getValor("erro_diretorio_armazenamento_tipomembro_nao_informado"));
		}
		File f = new File(this.diretorioArmazenamentoTipoMembro);
		try {
			f.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new TipoMembroExcessao(propertiesMessagesUtil.getValor("erro_diretorio_armazenamento_tipo_membro_invalido"));
		}
		if (!f.isDirectory()) {
			throw new TipoMembroExcessao(propertiesMessagesUtil.getValor("erro_diretorio_armazenamento_tipomembro_nao_diretorio"));
		}
	}

	/**
	 * Metodo responsavel por inicializar o versinador de arquivos.
	 * @throws TipoMembroExcessao Excessao na inicializacao do versionador.
	 */
	private void initVersionador() throws TipoMembroExcessao {
		try {
			this.versionamento = new Versionamento(this.diretorioArmazenamentoTipoMembro);
		} catch (VersaoExcessao e) {
			throw new TipoMembroExcessao(propertiesMessagesUtil.getValor("erro_inicializar_versionador_tipomembro"), e);
		}
	}

	/**
	 * Metodo responsavel por carregar os TiposMembros encontrados no diretorio de armazenamento
	 */
	private void initTiposMembrosMapeados() {
		Map<String, Class<?>> classesVersionadas = versionamento.getClassesVersionadas();
		this.tiposMembroModeloMapeados = new HashMap<String, ITipoMembroModelo>();
		this.tiposMembroVisaoMapeados = new HashMap<String, ITipoMembroVisao>();
		mapearTiposMembrosMapeados(classesVersionadas.values());

	}

	/**
	 * Metodo que realiza a divisao dos TiposMembro encontrados no diretorio de armazenamento em visao e modelo, realizando a instanciacao das classes.
	 * @param classesVersionadas Classes encotradas dentro dos arquivos mapeados no diretorio.
	 */
	private void mapearTiposMembrosMapeados(Collection<Class<?>> classesVersionadas) {
		for (Class<?> classe : classesVersionadas) {
			try {
				if (ITipoMembroModelo.class.isAssignableFrom(classe)) {
					ITipoMembroModelo tipoMembroModelo = (ITipoMembroModelo) classe.newInstance();
					this.tiposMembroModeloMapeados.put(tipoMembroModelo.getNome() + tipoMembroModelo.getVersao(), tipoMembroModelo);
				} else {
					ITipoMembroVisao tipoMembroVisao = (ITipoMembroVisao) classe.newInstance();
					this.tiposMembroVisaoMapeados.put(tipoMembroVisao.getNome() + tipoMembroVisao.getVersao(), tipoMembroVisao);
				}
			} catch (Exception e) {
				// essa excessao jamais ira ocorrer pois ja foi validada pelo
				// versionador
			}
		}

	}

	/**
	 * Metodo que instancia o controlador de espelhos.
	 */
	private void initControladorEspelhos() {
		this.espelhoControle = new EspelhoControle(this.tiposMembroModeloMapeados.values());

	}

	/**
	 * Metodo que reuni as operacoes de verificacao dos arquivos a serem importados no framework.
	 */
	private void verificarArquivos() {
		for (File arquivo : this.arquivosAMapear) {
			verificarArquivo(arquivo);
		}
		if (isSucesso()) {
			verificarDuplicidadeCadastro();
		}
		if (isSucesso()) {
			verificarEspelhos();
		}

	}

	/**
	 * Metodo que verifica a duplicidade de cadastro do TipoMembro que serao importados para o framework.
	 */
	private void verificarDuplicidadeCadastro() {
		Map<String, File> tiposMembroAMapear = verificarDuplicidadeCadastroTipoMembroModelo();
		verificaDuplicidadeCadastroTipoMembroVisao(tiposMembroAMapear);
	}

	/**
	 * Metodo que verifica a duplicidade de cadastro dos TipoMembro visao com base nos arquivos mapeados e nos arquivos que serao importados.
	 * @param tiposMembroAMapear Excessao caso exista alguma duplicidade de cadastro.
	 */
	private void verificaDuplicidadeCadastroTipoMembroVisao(Map<String, File> tiposMembroAMapear) {
		Set<ITipoMembroVisao> set = this.tiposMembroVisaoAMapear.keySet();
		for (ITipoMembroVisao iTipoMembroVisao : set) {
			if (tiposMembroAMapear.containsKey(iTipoMembroVisao.getNome() + iTipoMembroVisao.getVersao() + iTipoMembroVisao.getRevisao())) {
				setSucesso(false);
				this.informacoesArquivos.put(this.tiposMembroVisaoAMapear.get(iTipoMembroVisao), this.propertiesMessagesUtil.getValor("erro_arquivo_duplicado_importacao"));
				if (!this.informacoesArquivos.containsKey(tiposMembroAMapear.get(iTipoMembroVisao.getNome()))) {
					this.informacoesArquivos.put(tiposMembroAMapear.get(iTipoMembroVisao.getNome()), this.propertiesMessagesUtil.getValor("erro_arquivo_duplicado_importacao"));
				}
			} else {
				tiposMembroAMapear.put(iTipoMembroVisao.getNome() + iTipoMembroVisao.getVersao() + iTipoMembroVisao.getRevisao(), this.tiposMembroVisaoAMapear.get(iTipoMembroVisao));
			}
		}
	}

	/**
	 * Metodo que verifica a duplicidade de cadastro dos TipoMembro modelo com base nos arquivos mapeados e nos arquivos que serao importados.
	 *@return Map dos arquivos duplicados.
	 */
	private Map<String, File> verificarDuplicidadeCadastroTipoMembroModelo() {
		Map<String, File> tiposMembroAMapear = new HashMap<String, File>();
		Set<ITipoMembroModelo> set = this.tiposMembroModeloAMapear.keySet();
		for (ITipoMembroModelo iTipoMembroModelo : set) {
			if (tiposMembroAMapear.containsKey(iTipoMembroModelo.getNome() + iTipoMembroModelo.getVersao() + iTipoMembroModelo.getRevisao())) {
				setSucesso(false);
				this.informacoesArquivos.put(this.tiposMembroModeloAMapear.get(iTipoMembroModelo), this.propertiesMessagesUtil.getValor("erro_arquivo_duplicado_importacao"));
				if (!this.informacoesArquivos.containsKey(tiposMembroAMapear.get(iTipoMembroModelo.getNome()))) {
					this.informacoesArquivos.put(tiposMembroAMapear.get(iTipoMembroModelo.getNome()), this.propertiesMessagesUtil.getValor("erro_arquivo_duplicado_importacao"));
				}
			} else {
				tiposMembroAMapear.put(iTipoMembroModelo.getNome() + iTipoMembroModelo.getVersao() + iTipoMembroModelo.getRevisao(), this.tiposMembroModeloAMapear.get(iTipoMembroModelo));
			}
		}
		return tiposMembroAMapear;
	}

	/**
	 * Metodo que realiza a verificacao dos espelhos do TipoMembro visao para o TipoMembro modelo.
	 */
	private void verificarEspelhos() {
		Collection<ITipoMembroVisao> espelhosIncorretos = this.espelhoControle.checarEspelho(this.tiposMembroModeloAMapear.keySet(), this.tiposMembroVisaoAMapear.keySet());
		for (ITipoMembroVisao iTipoMembroVisao : espelhosIncorretos) {
			setSucesso(false);
			this.informacoesArquivos.put(this.tiposMembroVisaoAMapear.get(iTipoMembroVisao), propertiesMessagesUtil.getValor("erro_espelho_incorreto"));
		}
	}

	/**
	 * Metodo que realiza a verificacao do tipo de versionamento do arquivo, armazenando uma informacao dentro do map de informacoes caso aja alguma falha no versionador.
	 * @param arquivo Arquivo a ser verificado o versionamento.
	 */
	private void verificarArquivo(File arquivo) {
		try {
			VersionamentoEnum tipoVersionamento = versionamento.versionarArquivo(arquivo);
			this.tipoVersionamentoArquivos.put(arquivo, tipoVersionamento);
			mapearClasseTipoMembro(arquivo, versionamento.getClassObjetoVersionado());
		} catch (VersaoExcessao e) {
			setSucesso(false);
			this.informacoesArquivos.put(arquivo, e.getMessage());
		} catch (TipoMembroExcessao ex) {
			setSucesso(false);
			this.informacoesArquivos.put(arquivo, ex.getMessage());
		}

	}

	/**
	 * Metodo que identifica o tipo do TIpoMembro (visao e modelo) e chama o metodo que realiza o mapeamento da classe.  
	 * @param arquivo Arquivo do TipoMembro a ser pameado
	 * @param classObjetoVersionado Classe do arquivo a ser mapeada
	 * @throws TipoMembroExcessao Excessao caso a classe do arquivo nao implemente as interfaces ITipoMembroModelo ou ITipoMembroVisao
	 */
	private void mapearClasseTipoMembro(File arquivo, Class<?> classObjetoVersionado) throws TipoMembroExcessao {
		if (ITipoMembroModelo.class.isAssignableFrom(classObjetoVersionado)) {
			mapearClasseTipoMembroModelo(arquivo, classObjetoVersionado);
		} else {
			if (ITipoMembroVisao.class.isAssignableFrom(classObjetoVersionado)) {
				mapearClasseTipoMembroVisao(arquivo, classObjetoVersionado);
			} else {
				throw new TipoMembroExcessao(this.propertiesMessagesUtil.getValor("erro_tipomembro_nao_implementa_nenhuma_inteface"));
			}
		}

	}

	/**
	 * Metodo que instancia a classe do objeto e armazena ela dentro do map de tiposMembroVisaoAMapear
	 * @param arquivo Arquivo da ser mapeado
	 * @param classObjetoVersionado Classe do arquivo
	 */
	private void mapearClasseTipoMembroVisao(File arquivo, Class<?> classObjetoVersionado) {
		try {
			this.tiposMembroVisaoAMapear.put((ITipoMembroVisao) classObjetoVersionado.newInstance(), arquivo);
		} catch (Exception e) {
			// esse erro jamais ira acontecer pois ja foi verificado pelo
			// versionador
		}

	}

	/**
	 * Metodo que instancia a classe do objeto e armazena ela dentro do map de tiposMembroModeloAMapear
	 * @param arquivo Arquivo da ser mapeado
	 * @param classObjetoVersionado Classe do arquivo
	 */
	private void mapearClasseTipoMembroModelo(File arquivo, Class<?> classObjetoVersionado) {
		try {
			this.tiposMembroModeloAMapear.put((ITipoMembroModelo) classObjetoVersionado.newInstance(), arquivo);
		} catch (Exception e) {
			// esse erro jamais ira aconteer pois ja foi verificado pelo
			// versionador
		}
	}

	/**
	 * Metodo que realiza o mapeamento dos Arquivos depois que eles forem verificados.
	 */
	private void mapearTiposMembros() {
		if (isSucesso()) {
			remapearTiposMembros();
			Set<File> arquivos = this.tipoVersionamentoArquivos.keySet();
			for (File arquivo : arquivos) {
				mapearTipoMembro(arquivo);
			}
		}

	}

	/**
	 * Metodo que realiza o mapeamento inverso dos TipoMembro modelo e TiposMembro visao.
	 * Este remapamento conta na inversao dos atributos:  Map<ITipoMembroModelo, File> para Map<File, ITipoMembroModelo> e Map<ITipoMembroVisao, File> para Map<File, ITipoMembroVisao>
	 */
	private void remapearTiposMembros() {
		remapearTiposMembrosVisao();
		remapearTiposMembroModelo();
	}

	/**
	 * Metodo que realiza a inversao do  Map<ITipoMembroModelo, File> para Map<File, ITipoMembroModelo>
	 */
	private void remapearTiposMembroModelo() {
		this.tiposMembroModeloAMapearFile = new HashMap<File, ITipoMembroModelo>();
		Set<ITipoMembroModelo> tiposMembros = this.tiposMembroModeloAMapear.keySet();
		for (ITipoMembroModelo iTipoMembroModelo : tiposMembros) {
			this.tiposMembroModeloAMapearFile.put(this.tiposMembroModeloAMapear.get(iTipoMembroModelo), iTipoMembroModelo);
		}

	}

	/**
	 * Metodo que realiza a inversao do Map<ITipoMembroVisao, File> para Map<File, ITipoMembroVisao>
	 */
	private void remapearTiposMembrosVisao() {
		this.tiposMembroVisaoAMapearFile = new HashMap<File, ITipoMembroVisao>();
		Set<ITipoMembroVisao> tiposMembros = this.tiposMembroVisaoAMapear.keySet();
		for (ITipoMembroVisao iTipoMembroVisao : tiposMembros) {
			this.tiposMembroVisaoAMapearFile.put(this.tiposMembroVisaoAMapear.get(iTipoMembroVisao), iTipoMembroVisao);
		}

	}

	/**
	 * Metodo que verifica o tipo de versionamento do arquivo do tipomembro e chama o metodo responsavel por realizar o mapeamento.
	 * @param arquivo Arquivo que sera verificado o tipo de versionamento.
	 */
	private void mapearTipoMembro(File arquivo) {
		VersionamentoEnum tipoVersionamento = this.tipoVersionamentoArquivos.get(arquivo);
		if (tipoVersionamento == VersionamentoEnum.NOVA_VERSAO) {
			mapearNovaVersaoTipoMembro(arquivo);
		} else {
			if (tipoVersionamento == VersionamentoEnum.NOVA_REVISAO) {
				mapearNovaRevisao(arquivo);
			} else {
				informarArquivoJaMapeado(arquivo);
			}
		}

	}

	/**
	 * Metodo que realiza o mapeamento de uma nova revisao de um arquivo.
	 * @param arquivo Arquivo que sera mapeado.
	 */
	private void mapearNovaRevisao(File arquivo) {
		ITipoMembro tipoMembro = null;
		if (this.tiposMembroModeloAMapearFile.containsKey(arquivo)) {
			tipoMembro = this.tiposMembroModeloAMapearFile.get(arquivo);
			atualizarTipoMembroFramework(tipoMembro);
			mapearITipoMembro(tipoMembro, TipoTipoMembroEnum.MODELO);
			this.versionamento.versionarArquivoNovaRevisao(arquivo, tipoMembro);
		} else {
			tipoMembro = this.tiposMembroVisaoAMapearFile.get(arquivo);
			atualizarTipoMembroFramework(tipoMembro);
			mapearITipoMembro(tipoMembro, TipoTipoMembroEnum.VISAO);
			this.versionamento.versionarArquivoNovaRevisao(arquivo, tipoMembro);
		}
		this.informacoesArquivos.put(arquivo, this.propertiesMessagesUtil.getValor("nova_revisao"));

	}

	/**
	 * Metodo que atualiza o cadastro do TipoMembro no framework para aguardando remocao, para que na proxima inicalizacao do controlador ele possa ser removido
	 * @param tipoMembro TipoMembro a ser atualizado o cadastro no framework.
	 */
	private void atualizarTipoMembroFramework(ITipoMembro tipoMembro) {
		TipoMembro tipoMembroAtualizar = new TipoMembro();
		tipoMembroAtualizar.setNome(tipoMembro.getNome());
		tipoMembroAtualizar.setVersao(tipoMembro.getVersao());
		tipoMembroAtualizar.setAtivo(true);
		tipoMembroAtualizar = getTipoMembroCadastrado(tipoMembroAtualizar);
		tipoMembroAtualizar.setAtivo(false);
		tipoMembroAtualizar.setAguardandoExclusao(true);
		try {
			alterar(tipoMembroAtualizar);
		} catch (QuidExcessao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que carrega todas as informacoes de um TipoMembro cadastrado no framework.
	 * @param tipoMembro TipoMembro que sera pesquisado, com o atributo nome informado.
	 * @return Objeto TipoMembro com todos os parametros preenchidos.
	 */
	private TipoMembro getTipoMembroCadastrado(TipoMembro tipoMembro) {
		Collection<TipoMembro> list = getListaTipoMembroCadastro(tipoMembro);
		return list.iterator().next();
	}

	/**
	 * Metodo que realiza a pesquisa de um TipoMembro cadastrado dentro do framework.
	 * @param tipoMembro TipoMembro que sera realizada a pesquisa.
	 * @return Lista de TipoMembro encontrados no framework.
	 */
	private Collection<TipoMembro> getListaTipoMembroCadastro(TipoMembro tipoMembro) {
		return pesquisarPorRestricao(tipoMembro, new String[] { "tipomembro.codigo", "tipomembro.descricao", "tipomembro.nome", "tipomembro.versao", "tipomembro.revisao", "tipomembro.tipoTipoMembro", "tipomembro.aguardandoExclusao",
				"tipomembro.ativo" });
	}

	/**
	 * Metodo que indentifica o tipo do TipoMembro e chama o metodo para realizar o mapeamento de uma nova versao do TipoMembro.
	 * @param arquivo Arquivo do TipoMembro que sera versionado.
	 */
	private void mapearNovaVersaoTipoMembro(File arquivo) {
		if (this.tiposMembroModeloAMapearFile.containsKey(arquivo)) {
			mapearNovaVersaoTipoMembroModelo(arquivo);
		} else {
			mapearNovaVersaoTipoVisao(arquivo);
		}

	}

	/**Metodo que realiza o mapeamento de uma nova versao de um arquivo TipoMembro visao.
	 * @param arquivo Arquivo TipoMembro visao que sera mapeado.
	 */
	private void mapearNovaVersaoTipoVisao(File arquivo) {
		TipoMembro tipoMembro = construirTipoMembro(this.tiposMembroVisaoAMapearFile.get(arquivo), TipoTipoMembroEnum.VISAO);
		realizarMapeamento(arquivo, tipoMembro, this.propertiesMessagesUtil.getValor("nova_versao_mapeada"));
	}

	/**Metodo que realiza o mapeamento de uma nova versao de um arquivo TipoMembro modelo.
	 * @param arquivo Arquivo TipoMembro modelo que sera mapeado.
	 */
	private void mapearNovaVersaoTipoMembroModelo(File arquivo) {
		TipoMembro tipoMembro = construirTipoMembro(this.tiposMembroModeloAMapearFile.get(arquivo), TipoTipoMembroEnum.MODELO);
		realizarMapeamento(arquivo, tipoMembro, this.propertiesMessagesUtil.getValor("nova_versao_mapeada"));
	}

	/**
	 * Metodo que realiza a copia do arquivo a ser versionado para o diretorio de armazenamento e posteriormente insere o TipoMembro dentro do framework.
	 * @param arquivo Arquivo a ser mapeado.
	 * @param tipoMembro Objeto TipoMembro que sera mapeado
	 * @param informacao Informacao de sucesso de mapeamento do TipoMembro
	 */
	private void realizarMapeamento(File arquivo, TipoMembro tipoMembro, String informacao) {
		try {
			ManipuladoraArquivoUtil.copiarArquivo(arquivo.getPath(), this.diretorioArmazenamentoTipoMembro + "/" + tipoMembro.getNome() + "-" + tipoMembro.getVersao() + "-" + tipoMembro.getRevisao() + ".jar");
			try {
				inserir(tipoMembro);
			} catch (QuidExcessao e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.informacoesArquivos.put(arquivo, informacao);
		} catch (IOException e) {
			this.informacoesArquivos.put(arquivo, this.propertiesMessagesUtil.getValor("erro_mudar_arquivo_local"));
			setSucesso(false);
		}

	}

	/**
	 * Metodo que controi o objeto TipoMembro a partir da objeto da classe ITipoMembro e posteriormente chama o metodo de insercao do TipoMembro no framework.
	 * @param tipoMembro Objeto ITipoMembro que sera mapeado
	 * @param tipoTipoMembro Tipo do tipoMembro (modelo ou visao)
	 */
	private void mapearITipoMembro(ITipoMembro tipoMembro, TipoTipoMembroEnum tipoTipoMembro) {
		TipoMembro mapeamento = construirTipoMembro(tipoMembro, tipoTipoMembro);
		mapeamento.setAtivo(true);
		mapeamento.setAguardandoExclusao(false);
		try {
			inserir(mapeamento);
		} catch (QuidExcessao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que constroi um TipoMembro a partir do Objeto ITipoMembro e o tipo do TipoMembro
	 * @param iTipoMembro Objeto ITipoMembro que sera mapeado
	 * @param tipoTipoMembro Tipo do TipoMembro (visao, modelo)
	 * @return Objeto TipoMembro construido
	 */
	private TipoMembro construirTipoMembro(ITipoMembro iTipoMembro, TipoTipoMembroEnum tipoTipoMembro) {
		TipoMembro tipoMembro = new TipoMembro();
		tipoMembro.setNome(iTipoMembro.getNome());
		tipoMembro.setDescricao(iTipoMembro.getDescricao());
		tipoMembro.setVersao(iTipoMembro.getVersao());
		tipoMembro.setRevisao(iTipoMembro.getRevisao());
		tipoMembro.setTipoTipoMembro(tipoTipoMembro);
		tipoMembro.setAguardandoExclusao(false);
		tipoMembro.setAtivo(true);
		return tipoMembro;
	}

	/**
	 * Metodo responsavel por verificar se existem arquivo com o status aguardando remocao.
	 */
	private void verificarDelecaoArquivosRevisionados() {
		TipoMembro tipoMembro = new TipoMembro();
		tipoMembro.setAguardandoExclusao(true);
		Collection<TipoMembro> listExclusao = this.getListaTipoMembroCadastro(tipoMembro);
		deletarArquivosRevisionados(listExclusao.iterator());
	}

	/**
	 * Metodo responsavel por realizar a delecao dos TiposMembro que estao com o status aguardando remocao. Essa remocao e feita versionando o arquivo e alterando o status do TipoMembro no framework.
	 * @param listaDelecao
	 */
	private void deletarArquivosRevisionados(Iterator<TipoMembro> listaDelecao) {
		while (listaDelecao.hasNext()) {
			TipoMembro tipoMembroDeletar = listaDelecao.next();
			removerArquivoTipoMembro(tipoMembroDeletar);
		}

	}

	/**
	 * Metodo responsavel por remover o arquivo do TipoMembro apos ele ter sido versionado.
	 * @param tipoMembroDeletar Objeto do TipoMembro a ser removido.
	 */
	private void removerArquivoTipoMembro(TipoMembro tipoMembroDeletar) {
		File f = new File(diretorioArmazenamentoTipoMembro + "/" + tipoMembroDeletar.getNome() + "-" + tipoMembroDeletar.getVersao() + "-" + tipoMembroDeletar.getRevisao() + ".jar");
		if (f.delete()) {
			atualizarDadosTipoMembroDeletado(tipoMembroDeletar);
		}

	}

	
	/**
	 * Metodo responsavel por alterar o status aguardando remocao do TipoMembro para false.  
	 * @param tipoMembroDeletar Objeto TipoMembro a ser alterado o status
	 */
	private void atualizarDadosTipoMembroDeletado(TipoMembro tipoMembroDeletar) {
		tipoMembroDeletar.setAguardandoExclusao(false);
		try {
			alterar(tipoMembroDeletar);
		} catch (QuidExcessao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que armazena a informaca de tipomembro ja mapeado no map de informacoes dos arquivos.
	 * @param arquivo
	 */
	private void informarArquivoJaMapeado(File arquivo) {
		this.informacoesArquivos.put(arquivo, propertiesMessagesUtil.getValor("tipo_membro_ja_mapeado"));
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#getDao()
	 */
	@Override
	public IDAO<TipoMembro, Long> getDao() {
		return this.daoTipoMembro;
	}
	
	//GETTERS AND SETTERS
	
	public IDAOTipoMembro<TipoMembro, Long> getDaoTipoMembro() {
		return daoTipoMembro;
	}

	public void setDaoTipoMembro(IDAOTipoMembro<TipoMembro, Long> daoTipoMembro) {
		this.daoTipoMembro = daoTipoMembro;
	}

}
