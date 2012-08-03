package br.ueg.unucet.quid.servicos;

import java.io.File;
import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.MembroFramework;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Servico;
import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;
import br.ueg.unucet.quid.interfaces.IArtefato;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;
import br.ueg.unucet.quid.interfaces.IEquipeSevico;
import br.ueg.unucet.quid.interfaces.IMembroServico;
import br.ueg.unucet.quid.interfaces.IModelo;
import br.ueg.unucet.quid.interfaces.IModeloServico;
import br.ueg.unucet.quid.interfaces.IProjetoServico;
import br.ueg.unucet.quid.interfaces.IQUID;
import br.ueg.unucet.quid.interfaces.IServicoServico;
import br.ueg.unucet.quid.interfaces.ITipoMembroServico;
import br.ueg.unucet.quid.interfaces.IUsuarioServico;

public class QuidService implements IQUID{
	
	private static QuidService quidService;
	private ApplicationContext appContext;
	
	private QuidService(){
		this.appContext = new ClassPathXmlApplicationContext("/br/ueg/unucet/quid/configuracoes/applicationContext.xml");
	}
	
	public static QuidService obterInstancia(){
		if(quidService == null){
			quidService = new QuidService();
		}
		return quidService;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#mapearArtefato(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	public Retorno<String, Collection<String>> mapearArtefato(Artefato artefato) {
		IArtefatoControle<Artefato, Long> artefatoControle = (IArtefatoControle<Artefato, Long>) appContext.getBean("ArtefatoControle");
		return artefatoControle.mapearArtefato(artefato);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#pesquisarArtefato(br.ueg.unucet.quid.dominios.Categoria, java.lang.String, java.lang.String)
	 */
	@Override
	public Retorno<String, Collection<Artefato>> pesquisarArtefato(Categoria categoria, String nome, String descricao) {
		IArtefatoControle<Artefato, Long> artefatoControle = (IArtefatoControle<Artefato, Long>) appContext.getBean("ArtefatoControle");
		return artefatoControle.pesquisarArtefato(categoria, nome, descricao);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#removerArtefato(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	public Retorno removerArtefato(Artefato artefato) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#alterarArtefato(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	public Retorno alterarArtefato(Artefato artefato) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getInstanciaArtefato()
	 */
	@Override
	public Artefato getInstanciaArtefato() {
		return (Artefato) appContext.getBean("Artefato");
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#mapearMembro(br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	@Override
	public Retorno<Object, Object> mapearMembro(Membro membro) {
		IMembroServico<MembroFramework> membroServico = (IMembroServico<MembroFramework>) appContext.getBean("MembroServico");
		return membroServico.inserir(membro);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#alterarMembro(br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	@Override
	public Retorno alterarMembro(Membro membro) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#clonarArtefato(br.ueg.unucet.quid.interfaces.IArtefato, java.lang.String, java.lang.String)
	 */
	@Override
	public Retorno clonarArtefato(IArtefato artefatoAClonar, String nome, String descricao) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getInterfacePreenchimento(br.ueg.unucet.quid.interfaces.IArtefato, br.ueg.unucet.quid.dominios.Projeto, java.lang.String)
	 */
	@Override
	public Retorno getInterfacePreenchimento(IArtefato artefato, Projeto projeto, String tecnologiVisao) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#cancelarPreenchimentoArtefato(br.ueg.unucet.quid.interfaces.IArtefato, br.ueg.unucet.quid.dominios.Projeto)
	 */
	@Override
	public Retorno cancelarPreenchimentoArtefato(IArtefato artefato, Projeto projeto) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getInterfaceVisualizacao(br.ueg.unucet.quid.interfaces.IArtefato)
	 */
	@Override
	public Retorno getInterfaceVisualizacao(IArtefato artefato) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getServicoPublicacao()
	 */
	@Override
	public Collection<IServico> getServicoPublicacao() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#publicarArtefato(br.ueg.unucet.quid.interfaces.IArtefato, br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno publicarArtefato(IArtefato artefato, IServico servicoPublicacao) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#mapearArquivosTipoMembro(java.io.File[])
	 */
	@Override
	public Retorno<File, String> mapearArquivosTipoMembro(File... arquivos) {
		ITipoMembroServico<TipoMembro> servicoTipoMembro = (ITipoMembroServico<TipoMembro>) appContext.getBean("TipoMembroServico");
		return servicoTipoMembro.mapear(arquivos);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#mapearArquivosServicos(java.io.File[])
	 */
	@Override
	public Retorno<File, String> mapearArquivosServicos(File... arquivos) {
		IServicoServico<Servico> servicoServico = (IServicoServico<Servico>) appContext.getBean("ServicoServico");
		return servicoServico.mepearServicos(arquivos);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#voltarVersaoTipoMembro(br.ueg.unucet.quid.extensao.interfaces.ITipoMembro)
	 */
	@Override
	public Retorno voltarVersaoTipoMembro(ITipoMembro tipoMembro) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#voltarVersaoServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno voltarVersaoServico(IServico servico) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getListaMembro(br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	@Override
	public Retorno<String, Collection<Membro>> getListaMembro(ITipoMembroModelo tipoMembro) {
		IMembroServico<MembroFramework> servico = (IMembroServico<MembroFramework>) appContext.getBean("MembroServico");
		return servico.pesquisarMembro(tipoMembro);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#listaTipoMembroModelo()
	 */
	@Override
	public Retorno<String, Collection<ITipoMembroModelo>> listaTipoMembroModelo() {
		ITipoMembroServico<TipoMembro> servicoTipoMembro = (ITipoMembroServico<TipoMembro>) appContext.getBean("TipoMembroServico");
		return servicoTipoMembro.listarTipoMembroModelo();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#listaTipoMembroVisao()
	 */
	@Override
	public Retorno<String, Collection<ITipoMembroVisao>> listaTipoMembroVisao() {
		ITipoMembroServico<TipoMembro> servicoTipoMembro = (ITipoMembroServico<TipoMembro>) appContext.getBean("TipoMembroServico");
		return servicoTipoMembro.listarTipoMembroVisao();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#listaParametrosServico(br.ueg.unucet.quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<String, Collection<IParametro<?>>> listaParametrosServico(IServico servico) {
		IServicoServico<Servico> servicoServico = (IServicoServico<Servico>) appContext.getBean("ServicoServico");
		return servicoServico.listaParametrosServico(servico);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getInstanciaUsuario()
	 */
	@Override
	public Retorno<String, Usuario> getInstanciaUsuario() {
		Retorno<String, Usuario> retorno = new Retorno<String, Usuario>();
		retorno.setSucesso(true);
		retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, new Usuario());
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#inserirUsuario(br.ueg.unucet.quid.dominios.Usuario)
	 */
	@Override
	public Retorno<String, Collection<String>> inserirUsuario(Usuario usuario) {
		IUsuarioServico<Usuario> usuarioServico = (IUsuarioServico<Usuario>) appContext.getBean("UsuarioServico");
		return usuarioServico.inserir(usuario);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#alterarUsuario(br.ueg.unucet.quid.dominios.Usuario)
	 */
	@Override
	public Retorno<String, Collection<String>> alterarUsuario(Usuario usuario) {
		IUsuarioServico<Usuario> usuarioServico = (IUsuarioServico<Usuario>) appContext.getBean("UsuarioServico");
		return usuarioServico.alterar(usuario);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#pesquisarUsuario(br.ueg.unucet.quid.dominios.Usuario)
	 */
	@Override
	public Retorno<String, Collection<Usuario>> pesquisarUsuario(Usuario usuario) {
		IUsuarioServico<Usuario> usuarioServico = (IUsuarioServico<Usuario>) appContext.getBean("UsuarioServico");
		return usuarioServico.pesquisar(usuario);
	}

	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#inserirEquipe(br.ueg.unucet.quid.dominios.Equipe)
	 */
	@Override
	public Retorno<String, Collection<String>> inserirEquipe(Equipe equipe) {
		IEquipeSevico<Equipe> equipeServico = (IEquipeSevico<Equipe>) appContext.getBean("EquipeServico");
		return equipeServico.inserir(equipe);
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#pesquisarEquipe(br.ueg.unucet.quid.dominios.Equipe)
	 */
	@Override
	public Retorno<String, Collection<Equipe>> pesquisarEquipe(Equipe equipe) {
		IEquipeSevico<Equipe> equipeServico = (IEquipeSevico<Equipe>) appContext.getBean("EquipeServico");
		return equipeServico.pequisarEquipes(equipe); 
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#alterarEquipe(br.ueg.unucet.quid.dominios.Equipe)
	 */
	@Override
	public Retorno<String, Collection<String>> alterarEquipe(Equipe equipe) {
		IEquipeSevico<Equipe> equipeServico = (IEquipeSevico<Equipe>) appContext.getBean("EquipeServico");
		return equipeServico.alterar(equipe);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getTipoMembroVisao(br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	@Override
	public Retorno<String, ITipoMembroVisao> getTipoMembroVisao(ITipoMembroModelo tipoMembroModelo) {
		ITipoMembroServico<TipoMembro> servicoTipoMembro = (ITipoMembroServico<TipoMembro>) appContext.getBean("TipoMembroServico");
		return servicoTipoMembro.getTipoMembroVisao(tipoMembroModelo);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getInstanciaModelo(java.lang.String, java.lang.String)
	 */
	@Override
	public Retorno<String, Modelo> getInstanciaModelo(String nome, String descricao) {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.criarModelo(nome, descricao);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#mapearModelo(br.ueg.unucet.quid.dominios.Modelo)
	 */
	@Override
	public Retorno<Object, Object> mapearModelo(Modelo modelo) {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.mapearModelo(modelo);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#criarItemModelo(br.ueg.unucet.quid.dominios.Artefato)
	 */
	@Override
	public Retorno<String, ItemModelo> criarItemModelo(Artefato artefato) {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.criarItemModelo(artefato);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#removerArtefatoModelo(br.ueg.unucet.quid.dominios.Artefato, br.ueg.unucet.quid.dominios.Modelo)
	 */
	@Override
	public Retorno<Object, Object> removerArtefatoModelo(Artefato artefato, Modelo modelo) {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.removerArtefatoModelo(artefato, modelo);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#validarItemModelo(br.ueg.unucet.quid.dominios.ItemModelo, br.ueg.unucet.quid.dominios.Modelo)
	 */
	@Override
	public Retorno<String, Collection<String>> validarItemModelo(ItemModelo itemModelo, Modelo modelo) {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.validarItemModelo(itemModelo, modelo);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#incluirItemModelo(br.ueg.unucet.quid.dominios.Modelo, br.ueg.unucet.quid.dominios.ItemModelo)
	 */
	@Override
	public Retorno<String, Collection<String>> incluirItemModelo(Modelo modelo, ItemModelo itemModelo) {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.incluirItemModelo(modelo, itemModelo);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#listarServicos()
	 */
	@Override
	public Retorno<String, Collection<IServico>> listarServicos() {
		IServicoServico<Servico> servicoServico = (IServicoServico<Servico>) appContext.getBean("ServicoServico");
		return servicoServico.listaServicos();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IQUID#getInstanciaTipoMembroModelo(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Retorno<String, ITipoMembroModelo> getInstanciaTipoMembroModelo(String nome, Integer versao) {
		ITipoMembroServico<TipoMembro> tipoMembroServico = (ITipoMembroServico<TipoMembro>) appContext.getBean("TipoMembroServico");
		return tipoMembroServico.getInstancia(nome, versao);
	}

	@Override
	public Retorno<Object, Object> alterarProjeto(Projeto projeto) {
		IProjetoServico<Projeto> projetoServico = (IProjetoServico<Projeto>) appContext.getBean("ProjetoServico");
		return projetoServico.alterar(projeto);
	}

	@Override
	public Retorno<String, Collection<Projeto>> pesquisarProjeto(Projeto projeto) {
		IProjetoServico<Projeto> projetoServico = (IProjetoServico<Projeto>) appContext.getBean("ProjetoServico");
		return projetoServico.pesquisar(projeto);
	}
	
	public Retorno<Object, Object> inserirProjeto(Projeto projeto) {
		IProjetoServico<Projeto> projetoServico = (IProjetoServico<Projeto>) appContext.getBean("ProjetoServico");
		return projetoServico.inserir(projeto);
	}
	
	public Retorno<String, Collection<Modelo>> listarModelo() {
		IModeloServico<Modelo> modeloServico = (IModeloServico<Modelo>) appContext.getBean("ModeloServico");
		return modeloServico.listarModelos();
	}

}
