package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.MembroFramework;
import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.excessoes.MembroExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOMembroFramework;
import br.ueg.unucet.quid.interfaces.IMembroFrameworkControle;
import br.ueg.unucet.quid.interfaces.ITipoMembroControle;
import br.ueg.unucet.quid.utilitarias.SerializadorObjetoUtil;

/**
 * Classe responsavel realizar as operacoes sobre a entidade MembroFramework, que representa um mapeamento dos Membros cadastrados
 * no framework.
 * @author QUID
 *
 */
@Service("MembroFrameworkControle")
public class MembroFrameworkControle extends GenericControle<MembroFramework, Long> implements IMembroFrameworkControle<MembroFramework, Long>{

	
	/**
	 * Atributo responsavel por realizar as operacoes de persistencia sobre a entidade MembroFramework
	 */
	@Autowired
	private IDAOMembroFramework<MembroFramework, Long> daoMembro;
	/**
	 * Atributo responsavel por realizar as operacoes do TipoMembro no framework
	 */
	@Autowired
	private ITipoMembroControle<TipoMembro, Long> tipoMembroControle;
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#getDao()
	 */
	@Override
	public IDAO<MembroFramework, Long> getDao() {
		return this.daoMembro;
	}

	

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroFrameworkControle#inserir(br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=Exception.class)
	public void inserir(Membro membro) throws MembroExcessao{
		validarMembro(membro);
		MembroFramework membroFramework = transformarMembro(membro);
		try {
			inserir(membroFramework);
		} catch (QuidExcessao e) {
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_insercao"));
		}
		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=Exception.class)
	public void alterar(Membro membro) throws MembroExcessao{
		validarMembroAlterar(membro);
		MembroFramework membroFramework = transformarMembro(membro);
		try {
			alterar(membroFramework);
		} catch (QuidExcessao e) {
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_insercao"));
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroFrameworkControle#pesquisar(java.lang.String, br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	public Collection<Membro> pesquisar(String nome, ITipoMembroModelo tipoMembro) throws MembroExcessao{
		verificarParametrosPesquisa(nome, tipoMembro);
		MembroFramework membroFramework = new MembroFramework();
		membroFramework.setNome(nome);
		if(tipoMembro != null)
		membroFramework.setTipoMembro(getTipoMembroModelo(tipoMembro));
		Collection<MembroFramework> lista = pesquisarPorRestricao(membroFramework, getColunasMembroFramework());
		return converterListaMembroFramework(lista);
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroFrameworkControle#pesquisar(br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	public Collection<Membro> pesquisar(ITipoMembroModelo tipoMembroModelo) throws MembroExcessao {
		MembroFramework membroFramework = new MembroFramework();
		membroFramework.setTipoMembro(getTipoMembroModelo(tipoMembroModelo));
		Collection<MembroFramework> lista = pesquisarPorRestricao(membroFramework, getColunasMembroFramework());
		return converterListaMembroFramework(lista);
	}
	
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroFrameworkControle#transformarMembroFramework(java.util.Collection)
	 */
	@Override
	public Collection<MembroFramework> transformarMembroFramework(Collection<Membro> membros) throws MembroExcessao {
		Collection<MembroFramework> membrosFramework = new ArrayList<MembroFramework>();
		for (Membro membro : membros) {
			MembroFramework membroFramework = transformarMembroFramework(membro); 
			membrosFramework.add(membroFramework);
		}
		return membrosFramework;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroFrameworkControle#transformarMembroFramework(br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	public MembroFramework transformarMembroFramework(Membro membro) throws MembroExcessao{
		MembroFramework membroFramework = transformarMembro(membro);
		verificarCadastroMembroFramework(membroFramework);
		return membroFramework;
	}
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroFrameworkControle#transformarMembroFramework(br.ueg.unucet.quid.dominios.MembroFramework)
	 */
	public Membro transformarMembroFramework(MembroFramework membroFramework){
		membroFramework = getPorId(MembroFramework.class, membroFramework.getCodigo());
		return construirMembro(membroFramework);
	}
	

	/**
	 * Metodo responsavel por verificar se algum parametro de pesquisa do TipoMembro foi informado.
	 * @param nome Nome do TipoMembro que sera pesquisado.
	 * @param tipoMembro TipoMembro que sera realizado a pesquisa sobre seus atributos.
	 * @throws MembroExcessao Excessao caso nao tenha nenhum parametro de pesquisa informado.
	 */
	private void verificarParametrosPesquisa(String nome, ITipoMembroModelo tipoMembro) throws MembroExcessao {
		if((nome == null || nome.equals(""))&& tipoMembro == null){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("nenhum_parametro_de_pesquisa_informado"));
		}
		
	}
	
	
	/**
	 * Metodo responsavel por realizar a conversao de uma lista de MembroFramework para uma lista de Membros
	 * @param lista Liata de objetos MembroFramework que sera convertida 
	 * @return Lista de Membros
	 */
	private Collection<Membro> converterListaMembroFramework(Collection<MembroFramework> lista) {
		Collection<Membro> membros = new ArrayList<Membro>();
		for (MembroFramework membroFramework : lista) {
			Membro membro = construirMembro(membroFramework);
			membros.add(membro);
		}
		return membros;
	}
	
	/**
	 * Metodo que constroi um Membro a partir de seu MembroFramework
	 * @param membroFramework Objeto MembroFramework que sera construido o objeto Membro
	 * @return O objeto Membro
	 */
	@SuppressWarnings("unchecked")
	private Membro construirMembro(MembroFramework membroFramework) {
		Membro membro = new Membro();
		membro.setCodigo(membroFramework.getCodigo());
		membro.setNome(membroFramework.getNome());
		membro.setDescricao(membroFramework.getDescricao());
		ITipoMembroModelo tipoMembroModelo = this.tipoMembroControle.getIntanciaTipoMembroControle(membroFramework.getTipoMembro());
		tipoMembroModelo.setListaParametros((Collection<IParametro<?>>) SerializadorObjetoUtil.toObject(membroFramework.getParametros()));
		membro.setTipoMembroModelo(tipoMembroModelo);
		return membro;
	}
	

	
	/**
	 * Metodo que retorna as colunas de busta do MembroFramework
	 * @return
	 * 
	 */
	private String[] getColunasMembroFramework(){
		return new String[]{"membroframework.codigo","membroframework.nome","membroframework.parametros" ,"membroframework.descricao","membroframework.tipoMembro.codigo","membroframework.tipoMembro.nome","membroframework.tipoMembro.versao","membroframework.tipoMembro.revisao"};
	}
	

	/**
	 * Metodo que verifica se os parametros obrigatorios do membro foram informados.
	 * @param membro Membro para qual sera verificado os parametros
	 * @throws MembroExcessao Excessao caso algum campo obrigatorio nao foi informado
	 */
	private void validarMembro(Membro membro) throws MembroExcessao {
		validarMembroAlterar(membro);
		if(membroDuplicado(membro)){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_membro_duplicado_framework"),MembroExcessao.MEMBRO_DUPLICADO);
		}
		
	}
	
	private void validarMembroAlterar(Membro membro) throws MembroExcessao {
		if(membro == null){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_inserir_membro_membro_nao_informado"), MembroExcessao.MEMBRO_AUSENTE);
		}
		if(membro.getNome() == null || membro.getNome().equals("")){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_inserir_membro_nome_membro_nao_informado"), MembroExcessao.NOME_MEMBRO_AUSENTE);
		}
		if(membro.getDescricao() == null || membro.getDescricao().equals("")){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_inserir_membro_descricao_membro_nao_informado"), MembroExcessao.DESCRIAO_MEMBRO_AUSENTE);
		}
		if(membro.getTipoMembroModelo() == null){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_inserir_membro_tipomembro_membro_nao_informado"), MembroExcessao.TIPO_MEMBRO_AUSENTE);
		}
		if(!membro.getTipoMembroModelo().isParametrosValidos()){
			throw new MembroExcessao(this.propertiesMessagesUtil.getValor("erro_inserir_membro_parametros_tipomembro_incorretos"), MembroExcessao.PARAMETROS_TIPO_MEMBRO_INVALIDOS);
		}
	}
	
	/**
	 * Metodo que realiza a verificacao de duplicidade de cadastro do membro no framework
	 * @param membro Membro que sera verificado a duplicidade de cadastro
	 * @return True caso o membro ja esteje cadastrado no framework e false caso contrario
	 */
	private boolean membroDuplicado(Membro membro) {
		MembroFramework membroFramework = new MembroFramework();
		membroFramework.setNome(membro.getNome());
		//membroFramework.setDescricao(membro.getDescricao());
		boolean retorno = isCadastrada(membroFramework, new String[]{"membroframework.codigo", "membroframework.nome"});
		return retorno;
	}
	
	/**
	 * Metodo que verifica o cadastro do MembroFramework e caso ele nao esteje cadastrado no framework, realiza sua insercao
	 * @param membroFramework Membro que sera realizado a verificacao do cadastro
	 */
	private void verificarCadastroMembroFramework(MembroFramework membroFramework){
		MembroFramework membroVerificador = new MembroFramework();
		membroVerificador.setNome(membroFramework.getNome());
		membroVerificador.setDescricao(membroFramework.getDescricao());
		if(!isCadastrada(membroVerificador, new String[]{"membroframework.codigo", "membroframework.nome"})){
			try {
				inserir(membroFramework);
			} catch (QuidExcessao e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

	/**
	 * Metodo que realiza a conversao de um Membro para um MembroFramework
	 * @param membro Membro que sera realizado a conversao
	 * @return Objeto MembroFramework
	 */
	private MembroFramework transformarMembroPersistencia(Membro membro){
		MembroFramework membroFramework = new MembroFramework();
		membroFramework.setNome(membro.getNome());
		membroFramework.setDescricao(membro.getDescricao());
		return membroFramework;
		
	}
	
	/**
	 * Metodo que realiza a conversao de um Membro para um membroFramework, trazendo todas suas informacoes
	 * @param membro Membro que sera convertido
	 * @return Objeto MembroFramework
	 */
	private MembroFramework transformarMembro(Membro membro) {
		MembroFramework membroFramework = transformarMembroPersistencia(membro);
		membroFramework.setTipoMembro(getTipoMembroModelo(membro.getTipoMembroModelo()));
		membroFramework.setParametros(SerializadorObjetoUtil.toByteArray(membro.getTipoMembroModelo().getListaParametros()));
		return membroFramework;
	}

	
	//GETTERS AND SETTERS

	private TipoMembro getTipoMembroModelo(ITipoMembroModelo tipoMembroModelo) {
		return tipoMembroControle.getTipoMembro(tipoMembroModelo);
	}
	
	public IDAOMembroFramework<MembroFramework, Long> getDaoMembro() {
		return daoMembro;
	}

	public void setDaoMembro(IDAOMembroFramework<MembroFramework, Long> daoMembro) {
		this.daoMembro = daoMembro;
	}

}
