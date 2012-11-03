package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoMembro;
import br.ueg.unucet.quid.dominios.ArtefatoServico;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.dominios.MembroFramework;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Servico;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.ArtefatoExcessao;
import br.ueg.unucet.quid.excessoes.MembroExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;
import br.ueg.unucet.quid.interfaces.IArtefatoMembroControle;
import br.ueg.unucet.quid.interfaces.IArtefatoServicoControle;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOArtefato;
import br.ueg.unucet.quid.interfaces.IMembroFrameworkControle;
import br.ueg.unucet.quid.interfaces.IServicoControle;
import br.ueg.unucet.quid.interfaces.IServicoServico;
import br.ueg.unucet.quid.utilitarias.FabricaSerializacao;

/**
 * @author QUID
 * Classe responsavel por realizar as acoes sobre o artefato.
 */
@Service("ArtefatoControle")
public class ArtefatoControle extends GenericControle<Artefato, Long> implements IArtefatoControle<Artefato, Long>{


	/**
	 *	Atributo responsavel por realizar acoes de persistencia do artefato.
	 */
	@Autowired
	private IDAOArtefato<Artefato, Long> daoArtefato;
	/**
	 * Atributo resposavel por realizar as operacoes sobre o membro.
	 */
	@Autowired
	private IMembroFrameworkControle<MembroFramework, Long> controleMembro;
	/**
	 * Atributo responsavel por realizar operacoes sobre o servico. Tais operacoes 
	 * englobam o processamento e o tratamento do retorno. 
	 */
	@Autowired
	private IServicoServico<Servico> servicoServico;
	/**
	 * Atributo responsavel por realizar as operacoes sobre o servico. Tais operacoes englobam somente o processamento.
	 */
	@Autowired
	private IServicoControle<Servico, Long> servicoControle;
	/**
	 * Atributo responsavel por realizar as operacoes sobre o artefato. Tais operacoes englobam somre o processamento.
	 */
	@Autowired
	private IArtefatoMembroControle<ArtefatoMembro, Long> artefatoMembroControle;
	/**
	 * Atributo responsavel por realizar as operacoes sobre o artefato. Tais operacoes englobam o processamento o o tratamento do retorno.
	 */
	@Autowired
	private IArtefatoServicoControle<ArtefatoServico, Long> artefatoServicoControle;
	
	/**
	 * Atributo responsavel por representar o artefato que esta sendo manipulado
	 */
	private Artefato artefato;
	
	
	@Override
	public IDAO<Artefato, Long> getDao() {
		return daoArtefato;
	}
	
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#verificarMembro(br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	public void verificarMembro(Membro membro) throws ArtefatoExcessao {
		if(!membro.getTipoMembroModelo().isParametrosValidos()){
			ArtefatoExcessao excessao = new ArtefatoExcessao(propertiesMessagesUtil.getValor("parametros_invalidos_membro"), membro.getTipoMembroModelo().getNomesParametrosInvalidos());
			throw excessao;
		}else{
			if(membro.getX() == null || membro.getY() == null || membro.getAltura() == null || membro.getComprimento() == null){
				ArtefatoExcessao excessao = new ArtefatoExcessao(propertiesMessagesUtil.getValor("erro_posicionamento_membro_nao_informado"));
				throw excessao;
			}
		}
		
	}

	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#verificarServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	public Retorno<String, Collection<String>> verificarServico(IServico servico) {
		return servicoServico.verificarParametrosServico(servico);
	}
	
	/**
	 * Metodo que verifica se os parametros do artefato estao corretos/invalidos
	 * @param artefato Artefato que sera verificado
	 * @return Os campos dos artefatos que estao incorretos/invalidos
	 */
	private Retorno<String, Collection<String>> verificarArtefato(Artefato artefato, boolean novo) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		Collection<String> parametros = new ArrayList<String>();
		retorno.setSucesso(true);
		if(artefato.getNome() == null || artefato.getNome().equals("")){
			parametros.add("nome");
		}
		if(artefato.getDescricao() == null || artefato.getDescricao().equals("")){
			parametros.add("descricao");
		}
		if(!parametros.isEmpty()){
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMessagesUtil.getValor("erro_parametros_nao_informados"));
			retorno.setTipoErro(TipoErroEnum.ERRO_SIMPLES);
			retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, parametros);
		}else
		if(novo && artefatoDuplicado(artefato)){
			retorno.setSucesso(false);
			retorno.setMensagem(this.propertiesMessagesUtil.getValor("erro_artefato_duplicado"));
			retorno.setTipoErro(TipoErroEnum.ERRO_SIMPLES);
		}
		return retorno;
		
		
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#executarServico(br.ueg.unucet.quid.dominios.Artefato, br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<Object, Object> executarServico(Artefato artefato, IServico servico) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Metodo responsavel por verificar se um artefato ja esta cadastrado no framework. Essa verificação e feita com base nos
	 * campos preenchidos pelo usuario.
	 * @param artefato Artefato que sera verificado a duplicidade
	 * @return True caso o artato esteje duplicado no framework, e false caso contrario.
	 */
	private boolean artefatoDuplicado(Artefato artefato) {
		Artefato artefatoBusca = new Artefato();
		artefatoBusca.setNome(artefato.getNome());
		Collection<Artefato> lista = pesquisarPorRestricao(artefatoBusca, new String[]{"artefato.codigo", "artefato.nome"});
		return (lista != null) &&(!lista.isEmpty());
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#reorganizarServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@SuppressWarnings("null")
	@Override
	public void reorganizarServico(IServico servico) {
		IServico servicoAnterior = servico.getAnterior();
		IServico servicoProximo = servico.getProximo();
		if(servicoAnterior != null){
			if(servicoProximo != null){
				servicoProximo.setAnterior(servicoAnterior);
				servicoAnterior.setProximo(servicoProximo);
			}else{
				servicoProximo.setAnterior(null);
			}
		}else{
			servicoAnterior.setProximo(null);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#mapearArtefato(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Retorno<String, Collection<String>> mapearArtefato(Artefato artefato) {
		this.artefato = artefato;
		Retorno<String,Collection<String>> retorno = mapear(artefato);
		return retorno;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Retorno<String, Collection<String>> alterarArtefato(Artefato artefato) {
		this.artefato = artefato;
		Retorno<String,Collection<String>> retorno;
		retorno = alterarMapeamento(artefato);
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#pesquisarArtefato(br.ueg.unucet.quid.dominios.Categoria, java.lang.String, java.lang.String)
	 */
	@Override
	public Retorno<String, Collection<Artefato>> pesquisarArtefato(Categoria categoria, String nome, String descricao) {
		Artefato artefato = new Artefato();
		artefato.setNome(nome);
		artefato.setDescricao(descricao);
		artefato.setCategoria(categoria);
		return realizarPesquisa(artefato);
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoControle#carregarArtefato(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	public Artefato carregarArtefato(Artefato artefato) {
		Long codigo = artefato.getCodigo();
		this.artefato = getPorId(Artefato.class, codigo);
		carregarServicos();
		carregarMembros();
		return this.artefato;
	}
	
	/**
	 * Metodo carrega os membros do artefato para que eles possam ser setados dentro do artefato.
	 */
	private void carregarMembros() {
		Artefato artefatoBusca = new Artefato();
		artefatoBusca.setCodigo(this.artefato.getCodigo());
		Collection<ArtefatoMembro> artefatosMembro = this.artefatoMembroControle.pesquisarMembrosArtefatos(artefatoBusca);
		setarMembrosNoArtefato(artefatosMembro);
	}
	
	/**
	 * Metodo que seta os membros cadastrados dentro do artefato.
	 * @param artefatosMembro Lista de membros cadastrados no framework.
	 */
	private void setarMembrosNoArtefato(Collection<ArtefatoMembro> artefatosMembro) {
		Collection<Membro> membros = new ArrayList<Membro>();
		for (ArtefatoMembro artefatoMembro : artefatosMembro) {
			Membro membro = this.controleMembro.transformarMembroFramework(artefatoMembro.getMembroFramework());
			membro.setX(artefatoMembro.getX());
			membro.setY(artefatoMembro.getY());
			membro.setAltura(artefatoMembro.getAltura());
			membro.setComprimento(artefatoMembro.getComprimento());
			membros.add(membro);
		}
		this.artefato.setListaMembro(membros);
		
	}
	
	
	/**
	 * Metodo responsavel por realizar o carregamento dos servicos que foram cadastrados para uma artefato.
	 */
	private void carregarServicos() {
		Artefato artefatoBusca = new Artefato();
		artefatoBusca.setCodigo(artefato.getCodigo());
		ArtefatoServico artefatoServico = new ArtefatoServico();
		artefatoServico.setArtefato(artefatoBusca);
		Collection<ArtefatoServico> listArtefatoServico = this.artefatoServicoControle.pesquisarArtefatosServicoArtefato(artefatoBusca);
		setarServicosNoArtefato(listArtefatoServico);
		organizarServicosArtefatos();
	}
	
	
	/**
	 * Metodo responsavel por montar a fila de servicos dentro do artefato.
	 */
	private void organizarServicosArtefatos() {
		for(IServico servico :this.artefato.getListaServico()){
			if(servico.getAnterior() != null){
				IServico servicoAnterior = procurarServicoArtefato(servico.getAnterior());
				servico.setAnterior(servicoAnterior);
			}
			if(servico.getProximo() != null){
				IServico servicoProximo = procurarServicoArtefato(servico.getProximo());
				servico.setProximo(servicoProximo);
			}
		}
		
	}
	
	
	/**
	 * Metodo reponsavel por procurar um servico dentro do artefato
	 * @param servicoAProcurar Servico que esta sendo procurado dentro do artefato
	 * @return O servico que esta se procurando
	 */
	private IServico procurarServicoArtefato(IServico servicoAProcurar) {
		for(IServico servico :this.artefato.getListaServico()){
			if(servico.getNome().equals(servicoAProcurar.getNome())){
				return servico;
			}
		}
		return null;
	}
	
	
	/**
	 * Metodo responsavel por realizar o carregamento dos servicos do artefato a partir do mapeamento do framework ArtfatoServico
	 * @param listArtefatoServico Lista de mapeamentos ArtefatoServico
	 */
	@SuppressWarnings("unchecked")
	private void setarServicosNoArtefato(Collection<ArtefatoServico> listArtefatoServico) {
		Collection<IServico> servicos = new ArrayList<IServico>();
		for(ArtefatoServico artefatoServico :listArtefatoServico){
			IServico servico = this.servicoControle.getInstanciaServico(artefatoServico.getServico());
			servico.setListaParametros((Collection<IParametro<?>>) FabricaSerializacao.obterInstancia().obterObjeto(artefatoServico.getParametrosServico()));
			servico.setAnteriroObrigatorio(artefatoServico.getAnteriorObrigatorio());
			if(artefatoServico.getServicoAnterior() != null && artefatoServico.getServicoAnterior().getCodigo() != null){
				servico.setAnterior(this.servicoControle.getInstanciaServico(artefatoServico.getServicoAnterior()));
			}
			if(artefatoServico.getServicoProximo() != null && artefatoServico.getServicoProximo().getCodigo() != null){
				servico.setProximo(this.servicoControle.getInstanciaServico(artefatoServico.getServicoProximo()));
			}
			servicos.add(servico);
			
		}
		this.artefato.setListaServico(servicos);
		
	}
	
	/**
	 * Metodo responsavel por realizar uma pesquisa de artefatos cadastrados no framework com base nos campos preenchidos na instancia do artefato.
	 * @param artefato2 Artefato com os campos de busca preenchidos
	 * @return Retorno de uma lista de artefatos encontrados
	 */
	private Retorno<String, Collection<Artefato>> realizarPesquisa(Artefato artefato2) {
		Collection<Artefato> lista = pesquisarPorRestricao(artefato2, new String[]{"artefato.nome", "artefato.codigo", "artefato.descricao","artefato.categoria.codigo","artfato.categoria.descricao"});
		Retorno<String, Collection<Artefato>> retorno = new Retorno<String, Collection<Artefato>>();
		if(lista == null || lista.isEmpty()){
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMessagesUtil.getValor("lista_vazia"));
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, new ArrayList<Artefato>());
		}else{
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
		}
		return retorno;
	}
	
	/**
	 * Metodo responsavel por realizar a verificacao do artefato para a realizacao mapeamento do artefato para o framework.
	 * @param artefatoAMapear Artefato a ser mapeado no framework.
	 * @return Retorno da execucao da verificacao e do mapeamento caso a verificacao esteje correta.
	 */
	@SuppressWarnings("unchecked")
	private Retorno<String, Collection<String>> mapear(Artefato artefatoAMapear) {
		Retorno<String, Collection<String>> retorno = verificarArtefato(artefatoAMapear, true);
		if(retorno.isSucesso()){
			try {
				retorno = mapearArtefatoFramework();
			} catch (QuidExcessao e) {
				retorno = (Retorno<String, Collection<String>>) construirRetornoErro(retorno, null, TipoErroEnum.ERRO_SIMPLES);
			}
		}
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	private Retorno<String, Collection<String>> alterarMapeamento(Artefato artefatoAMapear) {
		Retorno<String, Collection<String>> retorno = verificarArtefato(artefatoAMapear, false);
		if(retorno.isSucesso()){
			try {
				retorno = alterarArtefatoFramework();
			} catch (QuidExcessao e) {
				retorno = (Retorno<String, Collection<String>>) construirRetornoErro(retorno, null, TipoErroEnum.ERRO_SIMPLES);
			}
		}
		return retorno;
	}
	
	
	/**Metoro responsavel por realizar o mapeamento do artefato para o framework, armazenado o artefato e gerando os mapeamento ArtefatoMembro e ArtefatoServico
	 * @return Retorno da execucao do mapeamento.
	 * @throws QuidExcessao Excessao disparada quando ocorre alguma falha no mapeamento do artefato.
	 */
	private Retorno<String, Collection<String>> mapearArtefatoFramework() throws QuidExcessao{
		inserir(artefato);
		Collection<ArtefatoMembro> mapeamentoArtefatoMembro = gerarMapeamentoArtefatoMembro();
		Collection<ArtefatoServico> mapeamentoArtefatoServico = gerarMapeamentoArtfatoServico();
		for(ArtefatoMembro artefatoMembro : mapeamentoArtefatoMembro){
			artefatoMembroControle.inserir(artefatoMembro);
		}
		for(ArtefatoServico artefatoServico : mapeamentoArtefatoServico){
			artefatoServicoControle.inserir(artefatoServico);
		}
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		retorno.setSucesso(true);
		return retorno;
	}
	
	private Retorno<String, Collection<String>> alterarArtefatoFramework() throws QuidExcessao{
		alterar(artefato);
		Collection<ArtefatoMembro> mapeamentoArtefatoMembro = gerarMapeamentoArtefatoMembro();
		Collection<ArtefatoServico> mapeamentoArtefatoServico = gerarMapeamentoArtfatoServico();
		for (ArtefatoMembro artefatoMembro : artefatoMembroControle.pesquisarMembrosArtefatos(artefato)) {
			artefatoMembroControle.remover(ArtefatoMembro.class, artefatoMembro.getCodigo());
		}
		for (ArtefatoServico artefatoServico : artefatoServicoControle.pesquisarArtefatosServicoArtefato(artefato)) {
			artefatoServicoControle.remover(ArtefatoServico.class, artefatoServico.getCodigo());
		}
		for(ArtefatoMembro artefatoMembro : mapeamentoArtefatoMembro){
			artefatoMembroControle.inserir(artefatoMembro);
		}
		for(ArtefatoServico artefatoServico : mapeamentoArtefatoServico){
			artefatoServicoControle.inserir(artefatoServico);
		}
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		retorno.setSucesso(true);
		return retorno;
	}
	
	/**
	 * Metodo responsavel por realizar o mapeamento ArtefatoServico do artefato que esta sendo mapeado.
	 * Esse mapeamento e realizado para armazenar os servicos que o artefato possui.
	 * @return Lista de mapeamentos ArtefatoServico do artefato que esta sendo mapeado. 
	 */
	private Collection<ArtefatoServico> gerarMapeamentoArtfatoServico() {
		Collection<ArtefatoServico> mapeamento = new ArrayList<ArtefatoServico>();
		for(IServico iservico :artefato.getListaServico()){
			ArtefatoServico artefatoServico = new ArtefatoServico();
			artefatoServico.setArtefato(artefato);
			artefatoServico.setServico(servicoControle.getMapeamentoIServico(iservico));
			if(iservico.getAnterior() != null){
				artefatoServico.setServicoAnterior(servicoControle.getMapeamentoIServico(iservico.getAnterior()));
			}
			if(iservico.getProximo() != null){
				artefatoServico.setServicoProximo(servicoControle.getMapeamentoIServico(iservico.getProximo()));
			}
			artefatoServico.setAnteriorObrigatorio(iservico.isAnteriorObrigatorio());
			artefatoServico.setParametrosServico(FabricaSerializacao.obterInstancia().serializarObjeto(iservico.getListaParametros()));
			if(iservico.getTipoMembroModelo() != null && iservico.getTipoMembroModelo().getListaParametros() != null){
				artefatoServico.setParametrosTipoMembro(FabricaSerializacao.obterInstancia().serializarObjeto(iservico.getTipoMembroModelo().getListaParametros()));
			}
			mapeamento.add(artefatoServico);
		}
		return mapeamento;
	}
	
	/**
	 * Metodo responsavel por gerar o mapeamento ArtefatoMembro dos membros do artefato que esta sendo mapeado.
	 * Esse mapeamento e realizado para armazenar os membros que o artefato possui.
	 * @return
	 * @throws ArtefatoExcessao
	 */
	private Collection<ArtefatoMembro> gerarMapeamentoArtefatoMembro()throws ArtefatoExcessao {
		Collection<ArtefatoMembro> mapeamentos = new ArrayList<ArtefatoMembro>();
		try {
			for (Membro membro : artefato.getListaMembro()) {
				ArtefatoMembro artefatoTipoMembro = new ArtefatoMembro();
				artefatoTipoMembro.setArtefato(artefato);
				artefatoTipoMembro.setMembroFramework(controleMembro.transformarMembroFramework(membro));
				artefatoTipoMembro.setX(membro.getX());
				artefatoTipoMembro.setY(membro.getY());
				artefatoTipoMembro.setAltura(membro.getAltura());
				artefatoTipoMembro.setComprimento(membro.getComprimento());
				mapeamentos.add(artefatoTipoMembro);
			}
		} catch (MembroExcessao e) {
			throw new ArtefatoExcessao(e.getMessage(), e);
		}
		return mapeamentos;
	}
	
	//GETERS AND SETTERS
	public IMembroFrameworkControle<MembroFramework, Long> getControleMembro() {
		return controleMembro;
	}
	public void setControleMembro(IMembroFrameworkControle<MembroFramework, Long> controleMembro) {
		this.controleMembro = controleMembro;
	}
	public IServicoServico<Servico> getServicoServico() {
		return servicoServico;
	}
	public void setServicoServico(IServicoServico<Servico> servicoServico) {
		this.servicoServico = servicoServico;
	}
	public IServicoControle<Servico, Long> getServicoControle() {
		return servicoControle;
	}
	public void setServicoControle(IServicoControle<Servico, Long> servicoControle) {
		this.servicoControle = servicoControle;
	}
	public IArtefatoMembroControle<ArtefatoMembro, Long> getArtefatoMembroControle() {
		return artefatoMembroControle;
	}
	public void setArtefatoMembroControle(IArtefatoMembroControle<ArtefatoMembro, Long> artefatoMembroControle) {
		this.artefatoMembroControle = artefatoMembroControle;
	}
	public IArtefatoServicoControle<ArtefatoServico, Long> getArtefatoServicoControle() {
		return artefatoServicoControle;
	}
	public void setArtefatoServicoControle(IArtefatoServicoControle<ArtefatoServico, Long> artefatoServicoControle) {
		this.artefatoServicoControle = artefatoServicoControle;
	}
	
	public IDAOArtefato<Artefato, Long> getDaoArtefato() {
		return daoArtefato;
	}
	public void setDaoArtefato(IDAOArtefato<Artefato, Long> daoArtefato) {
		this.daoArtefato = daoArtefato;
	}

	
	
	
	
	
	
	

}
