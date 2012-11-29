package br.ueg.unucet.quid.controladores;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.Servico;
import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.enums.VersionamentoEnum;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.excessoes.ServicoExcessao;
import br.ueg.unucet.quid.excessoes.VersaoExcessao;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOServico;
import br.ueg.unucet.quid.interfaces.IServicoControle;
import br.ueg.unucet.quid.interfaces.ITipoMembroControle;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;
import br.ueg.unucet.quid.utilitarias.ManipuladoraArquivoUtil;
import br.ueg.unucet.quid.versionador.Versionamento;

/**
 * Classe responsavel por realizar as operacoes sobre a entidade Servico
 * @author QUID
 *
 */
@Service("ServicoControle")
@Scope("singleton")
public class ServicoControle extends GenericControle<Servico, Long> implements IServicoControle<Servico, Long> {

	/**
	 * Atributo responsavel por realizar o versionamento do Servico
	 */
	private Versionamento versionador;
	/**
	 * Atributo responsavel por realizar a leitura de propriedades dentro do arquivo configuracoes.properties
	 */
	private LeitoraPropertiesUtil propertiesConfiguracoesUtil;
	
	/**
	 * Atributo que armazena um relacionamento entre os arquivos a serem mapeados com os suas classes IServico
	 */
	private Map<File, IServico> servicosAMapear;
	/**
	 * Atributo que armazena um relacionamento entre as classes IServicos a serem mapeadas e seus respectivos arquivos
	 */
	private Map<IServico, File> servicosAMapearFile;
	/**
	 * Atributo que armazena as informacoes de processamento para cada arquivo a ser mapeado.
	 */
	private Map<File, String> informacoesArquivos;
	/**
	 * Vetor de arquivos que serao mapeados
	 */
	private File[] arquivosAMapear;
	/**
	 * Atributo que armazena um relacionamento entre o nome do servico e sua respectiva classe IServico
	 */
	private Map<String, IServico> servicosMapeados;
	/**
	 * Atributo que armazena um relacionamento entre os arquivos a serem importados e o tipo de versionamento que ocorrera sobre ele.
	 */
	private Map<File, VersionamentoEnum> tipoVersionamentoArquivos;
	/**
	 * Atributo que armazena o diretorio de armazenamento dos servicos
	 */
	private String diretorioArmazenamentoServico;
	/**
	 * Atributo que realiza as operacoes de persistencia da entidade servico
	 */
	@Autowired
	private IDAOServico<Servico, Long> daoServico;
	/**
	 * Atributo responsavel por realizar as operacoes sobre o Membro.
	 */
	@Autowired
	private ITipoMembroControle<TipoMembro, Long> tipoMembroControle;

	/**
	 * Metodo responsavel por realizar a inicializacao dos: Arquivo de configuracao, o versionador de arquivos
	 * e os servicos que ja foram mapeados para o framework.
	 * Este metodo tambem realiza a verificacao do diretorio de armazenamento dos servicos.
	 * @throws ServicoExcessao Excessao caso ocorra alguma falha na inicializacao
	 */
	@PostConstruct
	private void init() throws ServicoExcessao {
		this.propertiesConfiguracoesUtil = FabricaProperties.loadConfiguracoes();
		initDiretorioArmazenamentoServico();
		verificarDelecaoDeArquivos();
		initVersionador();
		initServicosMapeados();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IServicoControle#getInstanciaServico(br
	 * .ueg.unucet.quid.dominios.Servico)
	 */
	public IServico getInstanciaServico(Servico servico) {
		return this.servicosMapeados.get(servico.getNome() + servico.getVersao());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IServicoControle#listaServicosCadastrados()
	 */
	public Collection<IServico> listaServicosCadastrados() {
		return this.servicosMapeados.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IServicoControle#mapearServicos(java.io
	 * .File[])
	 */
	@Transactional(value = "transactionManager1", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<File, String> mapearServicos(File[] arquivos) throws ServicoExcessao {
		this.arquivosAMapear = arquivos;
		setSucesso(true);
		this.servicosAMapear = new HashMap<File, IServico>();
		this.servicosAMapearFile = new HashMap<IServico, File>();
		this.tipoVersionamentoArquivos = new HashMap<File, VersionamentoEnum>();
		this.informacoesArquivos = new HashMap<File, String>();
		verificarArquivos();
		mapearArquivos();
		if (isSucesso()) {
			init();
		}
		return this.informacoesArquivos;
	}

	@Transactional(value = "transactionManager1", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void remover(IServico iServico) throws ServicoExcessao {
		Servico servico = construirServico(iServico);
		servico = getListaServicosCadastrados(servico).iterator().next();
		verificarServicoASerRemovido(servico);
		realizarRastreabilidade(servico);
		IServico iservico = this.servicosMapeados.get(servico.getNome() + servico.getVersao());
		File arquivo = new File(this.diretorioArmazenamentoServico + "/" + servico.getNome() + "-" + servico.getVersao() + "-" + servico.getRevisao() + ".jar");
		this.versionador.inserirArquivoDiretorioVersionamento(arquivo, iservico);
		atualizarStatusParaRemocaoFramework(iservico);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IServicoControle#getMapeamentoIServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Servico getMapeamentoIServico(IServico iservico) {
		Servico servico = construirServico(iservico);
		servico = getListaServicosCadastrados(servico).iterator().next();
		return servico;
	}

	/**
	 * Metodo responsavel por verificar se o servico a ser removido contem todas os atributos obrigatorios para identificacao do objeto para remocao
	 * @param servico Servico a ser verificado os dados
	 * @throws ServicoExcessao Excessao caso algum atributo nao esteje informado
	 */
	private void verificarServicoASerRemovido(Servico servico) throws ServicoExcessao {
		if (servico == null) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("servico_a_ser_removido_nao_informado"));
		}
		if (servico.getNome() == null || servico.getNome().equals("")) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("servico_removido_nao_informado_nome"));
		}
		if (servico.getVersao() == null) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("servico_removido_versao_nao_informada"));
		}
		if (servico.getRevisao() == null) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("servico_removido_revisao_nao_informada"));
		}

	}

	/**
	 * Metodo responsavel por realizar a rastreabilidade de um servido dentro do framework
	 * @param servico Servico a ser realizado a rastreabilidade
	 */
	private void realizarRastreabilidade(Servico servico) {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo responsavel chamar o metodo de mapeamento para cada arquivo a ser mapeado
	 */
	private void mapearArquivos() {
		if (isSucesso()) {
			for (File file : this.arquivosAMapear) {
				mapearArquivo(file);
			}
		}

	}

	/**
	 * Metodo que verifica o tipo de versionamento do arquivo e chama o metodo especifico de versionamento
	 * @param file
	 */
	private void mapearArquivo(File file) {
		VersionamentoEnum tipoVersionamento = this.tipoVersionamentoArquivos.get(file);
		if (tipoVersionamento == VersionamentoEnum.NOVA_VERSAO) {
			mapearNovaVersao(file);
		} else {
			if (tipoVersionamento == VersionamentoEnum.NOVA_REVISAO) {
				mapearNovaRevisao(file);
			} else {
				this.informacoesArquivos.put(file, propertiesMessagesUtil.getValor("servico_ja_mapeado"));
			}
		}

	}

	/**
	 * Metodo que realiza o mapeamento de uma nova revisao de um arquivo
	 * @param arquivo Arquivo a ser mapeado
	 */
	private void mapearNovaRevisao(File arquivo) {
		IServico servicoNovo = this.servicosAMapear.get(arquivo);
		atualizarStatusParaRemocaoFramework(servicoNovo);
		mapearNovoServico(servicoNovo);
		this.versionador.versionarArquivoNovaRevisao(arquivo, servicoNovo);
		this.informacoesArquivos.put(arquivo, this.propertiesMessagesUtil.getValor("nova_revisao"));
	}

	/**
	 * Metodo responsavel por realivar a construcao de um objeto de servico a partir de sua classe IServico
	 * @param iservico Objeto IServico que sera construido a classe servico
	 * @return Objeto servico construido
	 */
	private Servico construirServico(IServico iservico) {
		Servico servico = new Servico();
		servico.setNome(iservico.getNome());
		servico.setVersao(iservico.getVersao());
		servico.setRevisao(iservico.getRevisao());
		servico.setDescricao(iservico.getDescricao());
		return servico;
	}

	/**
	 * Metodo responsavel por setar o status de um servico para aguardando remocao, para que na proxima vez que que o controlador ser inicializado, o servico ser removido.
	 * @param servicoNovo Servico a ser removido
	 */
	private void atualizarStatusParaRemocaoFramework(IServico servicoNovo) {
		Servico servicoAntigo = construirServico(servicoNovo);
		servicoAntigo.setAtivo(true);
		servicoAntigo = getListaServicosCadastrados(servicoAntigo).iterator().next();
		servicoAntigo.setAguardandoRemocao(true);
		servicoAntigo.setAtivo(false);
		try {
			alterar(servicoAntigo);
		} catch (QuidExcessao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que realiza o mapeamento de uma nova versao de um arquivo
	 * @param file Arquivo a ser mapeado
	 */
	private void mapearNovaVersao(File file) {
		IServico iservico = this.servicosAMapear.get(file);
		try {
			ManipuladoraArquivoUtil.copiarArquivo(file.getPath(), this.diretorioArmazenamentoServico + "/" + iservico.getNome() + "-" + iservico.getVersao() + "-" + iservico.getRevisao() + ".jar");
			mapearNovoServico(iservico);
			this.informacoesArquivos.put(file, this.propertiesMessagesUtil.getValor("nova_versao_mapeada"));
		} catch (IOException e) {
			this.informacoesArquivos.put(file, this.propertiesMessagesUtil.getValor("erro_mudar_arquivo_local"));
		}

	}

	/**
	 * Metodo responsavel por realizar o cadastro de um novo servico no framework. Este cadastro transforma o objeto IServico em um objeto servico identificavel pelo framework
	 * @param iservico IServico a ser mapeado. 
	 */
	private void mapearNovoServico(IServico iservico) {
		Servico servico = construirServico(iservico);
		servico.setAguardandoRemocao(false);
		servico.setAtivo(true);
		try {
			inserir(servico);
		} catch (QuidExcessao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que itera o vetor de arquivos a serem mapedos e chama o metodo de verificacao de versionamento para cada arquivo arquivo e caso 
	 * os arquivos sejem versionados com sucesso, chama a rotina de verificacao de duplicidade de cadastro.
	 */
	private void verificarArquivos() {
		for (File arquivo : this.arquivosAMapear) {
			versionarArquivo(arquivo);
		}
		if (isSucesso()) {
			verificarDuplicidadeArquivos();
		}

	}

	/**
	 * Metodo responsavel pela verificacao de dualidade de servicos dentro do map de arquivos se serao versionados. Essa verificacao busca nomes iguais dentro de classes IServicos
	 * dentro do map.
	 */
	private void verificarDuplicidadeArquivos() {
		Map<String, File> servicosAMapear = new HashMap<String, File>();
		Set<IServico> set = this.servicosAMapearFile.keySet();
		for (IServico iservico : set) {
			if (servicosAMapear.containsKey(iservico.getNome() + iservico.getVersao() + iservico.getRevisao())) {
				setSucesso(false);
				this.informacoesArquivos.put(this.servicosAMapearFile.get(iservico), this.propertiesMessagesUtil.getValor("erro_arquivo_duplicado_importacao"));
				if (!this.informacoesArquivos.containsKey(servicosAMapear.get(iservico.getNome()))) {
					this.informacoesArquivos.put(servicosAMapear.get(iservico.getNome()), this.propertiesMessagesUtil.getValor("erro_arquivo_duplicado_importacao"));
				}
			} else {
				servicosAMapear.put(iservico.getNome() + iservico.getVersao() + iservico.getRevisao(), this.servicosAMapearFile.get(iservico));
			}
		}
	}

	/**
	 * Metodo que chama o objeto de versionamento para verificar o tipo de versiomento do arquivo a ser importado. Caso aje algum problema no versionamento sera armazenado
	 * uma informacao dentro do map de informacoes dos arquivos.
	 * @param arquivo Arquivo a ser versionado.
	 */
	private void versionarArquivo(File arquivo) {
		try {
			VersionamentoEnum tipoVersionamento = this.versionador.versionarArquivo(arquivo);
			if (IServico.class.isAssignableFrom(this.versionador.getClassObjetoVersionado())) {
				this.tipoVersionamentoArquivos.put(arquivo, tipoVersionamento);
				try {
					IServico servico = (IServico) this.versionador.getClassObjetoVersionado().newInstance();
					String nome = servico.getNomeTipoMembroModelo();
					Integer versao = servico.getVersaoTipoMembroModelo();
					ITipoMembroModelo membroModelo = this.tipoMembroControle.getTipoMembroModelo(nome, versao);
					if(membroModelo != null){
						this.servicosAMapear.put(arquivo, servico);
						this.servicosAMapearFile.put(servico, arquivo);
					}else{
						setSucesso(false);
						this.informacoesArquivos.put(arquivo, propertiesMessagesUtil.getValor("framework_nao_contem_tipo_membro_modelo_mapeado"));
					}
					
				} catch (Exception e) {
					// esssa excessao ja foi tratada pelo versionador
				}
			} else {
				this.informacoesArquivos.put(arquivo, propertiesMessagesUtil.getValor("class_principal_nao_implementa_iservico"));
				setSucesso(false);
			}
		} catch (VersaoExcessao e) {
			this.informacoesArquivos.put(arquivo, e.getMessage());
			setSucesso(false);
		}

	}

	/**
	 * Metodo responsavel por reunir os metodos que escluem arquivos que estao aguardando remocao do framework. 
	 */
	private void verificarDelecaoDeArquivos() {
		Servico servico = new Servico();
		servico.setAguardandoRemocao(true);
		Collection<Servico> servicosASerRemovidos = getListaServicosCadastrados(servico);
		removerServicos(servicosASerRemovidos);
	}

	/**
	 * Metodo que itera a lista de servicos a serem removidos removendo os arquivos de suas pastas.
	 * @param servicosASerRemovidos Listas de servicos a serem removidos.
	 */
	private void removerServicos(Collection<Servico> servicosASerRemovidos) {
		for (Servico servico : servicosASerRemovidos) {
			File f = new File(this.diretorioArmazenamentoServico + "/" + servico.getNome() + "-" + servico.getVersao() + "-" + servico.getRevisao() + ".jar");
			if (f.delete()) {
				atualizarServicoRemovido(servico);
			}
		}

	}

	/**
	 * Metodo responsavel por atualizar o status removido do servico dentro do framework.
	 * @param servico Servico a ser alterado o status.
	 */
	private void atualizarServicoRemovido(Servico servico) {
		servico.setAtivo(false);
		servico.setAguardandoRemocao(false);
		try {
			alterar(servico);
		} catch (QuidExcessao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que pesquisa os servicos cadastrados no framework, com base nos parametros informados do objeto
	 * @param servico Servico a ser pesquisado
	 * @return Lista de servicos encontrados no framework
	 */
	private Collection<Servico> getListaServicosCadastrados(Servico servico) {
		return pesquisarPorRestricao(servico, new String[] { "servico.codigo", "servico.nome", "servico.versao", "servico.descricao", "servico.revisao", "servico.ativo", "servico.aguardandoRemocao", });
	}

	/**
	 * Metodo que inicializa a inicializacao dos servicos mapeados dentro do framework, descobrinco quais servicos estao mapeados a partir do objeto versionador.
	 */
	private void initServicosMapeados() {
		Map<String, Class<?>> classesMapeadas = this.versionador.getClassesVersionadas();
		this.servicosMapeados = new HashMap<String, IServico>();
		instanciarClassesServicos(classesMapeadas.values());
	}

	/**
	 * Metodo que realiza a instancializacao das classes fornecidas no metodo initServicosMapeados
	 * @param values Lista de classes que serao instancidas
	 */
	private void instanciarClassesServicos(Collection<Class<?>> values) {
		for (Class<?> classe : values) {
			try {
				IServico servico = (IServico) classe.newInstance();
				servico = setarTipoMembroModeloNoServico(servico);
				this.servicosMapeados.put(servico.getNome() + servico.getVersao(), servico);
			} catch (Exception e) {
				// Essa excessao jamais ira ocorrer pois ja foi verificada pelo
				// versionador
			}
		}

	}

	/**
	 * Metodo que procura o tipoMembro modelo do servico especificado e o seta dentro da classe de servico.
	 * @param servico Servico para qual sera procurado o TipoMembro modelo
	 * @return Objeto IServico com o TipoMembro setado
	 */
	private IServico setarTipoMembroModeloNoServico(IServico servico) {
		String nomeTipoMembroModelo = servico.getNomeTipoMembroModelo();
		if(nomeTipoMembroModelo != null && !nomeTipoMembroModelo.equals("")){
			servico.setTipoMembroModelo(this.tipoMembroControle.getTipoMembroModelo(servico.getNomeTipoMembroModelo(), servico.getVersaoTipoMembroModelo()));
		}
		return servico;
	}

	/**
	 * Metodo responsavel por inicializar o objeto de versionamento.
	 * @throws ServicoExcessao Excessao caso aja falha na inicializacao do versionador 
	 */
	private void initVersionador() throws ServicoExcessao {
		try {
			this.versionador = new Versionamento(this.diretorioArmazenamentoServico);
		} catch (VersaoExcessao e) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("falha_inicializar_servico_versioamento_servico"), e);
		}

	}

	/**
	 * Metodo responsavel por verificar o diretorio de versionamento informado no arquivo de configuracoes.
	 * @throws ServicoExcessao Excessao caso o diretorio informado no arquivo de configuracoes seje invalido / nao informado.
	 */
	private void initDiretorioArmazenamentoServico() throws ServicoExcessao {
		this.diretorioArmazenamentoServico = this.propertiesConfiguracoesUtil.getValor("diretorio_servico");
		if (this.diretorioArmazenamentoServico == null || this.diretorioArmazenamentoServico.equals("")) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("diretorio_armazenamto_servico_nao_informado"));
		}
		File f = new File(this.diretorioArmazenamentoServico);
		try {
			f.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("diretorio_armazenamento_servico_invalido"));
		}
		if (!f.isDirectory()) {
			throw new ServicoExcessao(this.propertiesMessagesUtil.getValor("diretorio_armazenameto_servico_nao_diretorio"));
		}
	}

	public IDAOServico<Servico, Long> getDaoServico() {
		return daoServico;
	}
	
	//GETTERS AND SETTERS

	public void setDaoServico(IDAOServico<Servico, Long> daoServico) {
		this.daoServico = daoServico;
	}

	@Override
	public IDAO<Servico, Long> getDao() {
		return this.daoServico;
	}

	public ITipoMembroControle<TipoMembro, Long> getTipoMembroControle() {
		return tipoMembroControle;
	}

	public void setTipoMembroControle(ITipoMembroControle<TipoMembro, Long> tipoMembroControle) {
		this.tipoMembroControle = tipoMembroControle;
	}
	
	

}
