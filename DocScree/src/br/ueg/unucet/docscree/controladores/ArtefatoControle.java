package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.utilitarios.BloquearArtefatoControle;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.SuperArtefatoCompositor;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;
import br.ueg.unucet.quid.servicosquid.ServicoPersistencia;

/**
 * Controlador específico do caso de uso Montar ArtefatoModelo
 * 
 * @author Diego
 *
 */
@SuppressWarnings("rawtypes")
public class ArtefatoControle extends SuperArtefatoControle{

	/**
	 * @see br.ueg.unucet.docscree.interfaces.ICRUDControle#acaoSalvar()
	 */
	@Override
	public boolean acaoSalvar() {
		if (this.isUsuarioMontador()) {
			Retorno<String, Collection<String>> retorno;
			Artefato instanciaArtefato = super.getFramework().getInstanciaArtefato();
			instanciaArtefato.setNome(super.getEntidade().getNome());
			instanciaArtefato.setDescricao(super.getEntidade().getDescricao());
			instanciaArtefato.setAltura(super.getEntidade().getAltura());
			instanciaArtefato.setLargura(super.getEntidade().getLargura());
			retorno = super.getFramework().mapearArtefato(instanciaArtefato);
			if (retorno.isSucesso()) {
				Artefato artefatoLancao = lancarArtefatoNaVisao((SuperArtefatoCompositor) getVisao());
				BloquearArtefatoControle.obterInstancia().adicionarBloqueioArtefato(artefatoLancao, getUsuarioLogado());
			}
			return super.montarRetorno(retorno);
		}
		return false;
	}

	/**
	 * Método que abre um ArtefatoModelo, joga a entidade para a visão para montagem
	 * 
	 * @return boolean se ação foi executada ou não
	 */
	public boolean acaoAbrirArtefato() {
		setarEntidadeVisao(getVisao());
		boolean membrosMapeados = ((SuperArtefatoCompositor) getVisao()).mapearMembrosAoArtefato();
		if (membrosMapeados) {
			return true;
		} else {
			getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			getMensagens().getListaMensagens().add("O ArtefatoModelo contém TipoMembros que não pertencem ao DocScree, impossível abrí-lo!");
			return false;
		}
	}


	/**
	 * @see br.ueg.unucet.docscree.controladores.GenericoControle#executarListagem()
	 */
	@Override
	protected Retorno<String, Collection<Artefato>> executarListagem() {
		return getFramework().pesquisarArtefato(null, null, null);
	}
	
	/**
	 * Método que mapea um Membro, ou seja, faz a persistência de um novo Membro no framework,
	 * ainda não o relacionamento com o ArtefatoModelo
	 * 
	 * @return boolean se a ação foi executada
	 */
	public boolean acaoMapearMembro() {
		if (this.acaoRenovarBloqueio()) {
			Retorno<Object, Object> retorno = null;
			SuperTipoMembroVisaoZK<?> tipoMembroVisao = getTipoMembroVisao();
			retorno = getFramework().mapearMembro(tipoMembroVisao.getMembro());
			if (retorno.isSucesso()) {
				Retorno<String, Collection<Membro>> retornoPesquisa = getFramework().pesquisarMembro(tipoMembroVisao.getMembro().getNome(), tipoMembroVisao.getMembro().getTipoMembroModelo());
				Collection<Membro> collection = retornoPesquisa.getParametros().get(Retorno.PARAMERTO_LISTA);
				Membro membroMapeado = collection.iterator().next();
				tipoMembroVisao.getMembro().setCodigo(membroMapeado.getCodigo());
				return true;
			} else {
				getMensagens().getListaMensagens().add(retorno.getMensagem());
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Método que altera o Membro já persistido no framework
	 * 
	 * @return boolean se ação foi executada
	 */
	public boolean acaoAlterarMembro() {
		if (this.acaoRenovarBloqueio()) {
			Retorno<Object, Object> retorno = null;
			retorno = getFramework().alterarMembro(getTipoMembroVisao().getMembro());
			return montarRetornoObject(retorno);
		} else {
			return false;
		}
	}
	
	/**
	 * Ação que remove membro do framework
	 * 
	 * @return boolean se ação foi executada
	 */
	public boolean acaoRemoverMembro() {
		if (this.acaoRenovarBloqueio()) {
			Retorno<Object,Object> retorno = getFramework().removerMembro(getTipoMembroVisao().getMembro());
			return montarRetornoObject(retorno);
		} else {
			return false;
		}
	}
	
	/**
	 * Método que monta a mensagem de retorno através de um Retorno<Object, Object> vindo do framework
	 * 
	 * @param retorno vindo do framework
	 * @return boolean resultado do Retorno
	 */
	private boolean montarRetornoObject(Retorno<Object, Object> retorno) {
		if (retorno.isSucesso()) {
			return true;
		} else {
			getMensagens().getListaMensagens().add(retorno.getMensagem());
			return false;
		}
	}
	
	public boolean acaoChamarServicoPersistencia() {
		
		Collection<IParametro<?>> parametros = new ArrayList<IParametro<?>>();
		parametros.add(criarParametroArtefatoModelo());
		parametros.add(criarParametroProjeto());
		parametros.add(criarParametroUsuario());
		parametros.add(criarParametroLong(ServicoPersistencia.PARAMETRO_ID_ARTEFATO_PREENCHIDO, null));
		parametros.add(criarParametroInteiro(ServicoPersistencia.PARAMETRO_VERSAO, 1));
		parametros.add(criarParametroInteiro(ServicoPersistencia.PARAMETRO_REVISAO, 1));
		Retorno<Object,Object> retorno = getEntidade().executaServico("ServicoPersistenciaBD", parametros);
		if (retorno.isSucesso()) {
			return true;
		}
		return false;
	}
	
	protected IParametro<Artefato> criarParametroArtefatoModelo() {
		Parametro<Artefato> parametro = new Parametro<Artefato>(Artefato.class);
		parametro.setNome(IServico.PARAMETRO_ARTEFATO_MODELO);
		parametro.setValorClass(getEntidade());
		return parametro;
	}
	
	protected IParametro<Usuario> criarParametroUsuario() {
		Parametro<Usuario> parametro = new Parametro<Usuario>(Usuario.class);
		parametro.setNome(IServico.PARAMETRO_USUARIO);
		parametro.setValorClass(getUsuarioLogado());
		return parametro;
	}
	
	protected IParametro<Projeto> criarParametroProjeto() {
		Parametro<Projeto> parametro = new Parametro<Projeto>(Projeto.class);
		parametro.setNome(IServico.PARAMETRO_PROJETO);
		parametro.setValorClass(getProjeto());
		return parametro;
	}
	
	protected IParametro<Integer> criarParametroInteiro(String nome, Integer valor) {
		Parametro<Integer> parametro = new Parametro<Integer>(Integer.class);
		parametro.setNome(nome);
		parametro.setValorClass(valor);
		return parametro;
	}
	
	protected IParametro<Long> criarParametroLong(String nome, Long valor) {
		Parametro<Long> parametro = new Parametro<Long>(Long.class);
		parametro.setNome(nome);
		parametro.setValorClass(valor);
		return parametro;
	}
	
	/**
	 * Retorna o SuperTipoMembroVisaoZK que foi selecionado na visão através do mapa de atributos
	 * 
	 * @return SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado
	 */
	private SuperTipoMembroVisaoZK<?> getTipoMembroVisao() {
		return (SuperTipoMembroVisaoZK<?>) getMapaAtributos().get("tipoMembroVisaoSelecionado");
	}
	
	/**
	 * Método que verifica se é possivel executar a montagem do ArtefatoModelo
	 * 
	 * @return boolean se o usuário tem papel de Montador
	 */
	public boolean acaoMontarArtefato() {
		return super.isUsuarioMontador();
	}
	
	/**
	 * Método que verifica se é o usuário é preenchedor
	 * 
	 * @return boolean se o usuário tem papel de Preenchedor
	 */
	public boolean acaoPreencherArtefato() {
		return super.isUsuarioPreenchedor();
	}
	
	/**
	 * Método que mapeia ArtefatoModelo, ou seja, altera o ArtefatoModelo e gera relacionamentos com os Serviços
	 * e Membros adicionados a ele.
	 * 
	 * @return boolean se ação foi executada
	 */
	public boolean acaoMapearArtefato() {
		boolean resultado = true;
		if (getEntidade().getCategoria() != null) {
			if (getEntidade().getCategoria().getCodigo() == null) {
				getEntidade().setCategoria(null);
			}
		}
		getFramework().alterarArtefato(getEntidade());
		return resultado;
	}
	
	/**
	 * Método que retorna um SuperTipoMembroVisaoZK a partir de um Membro
	 * 
	 * @param membro
	 * @return SuperTipoMembroVisaoZK do Membro
	 */
	public SuperTipoMembroVisaoZK<?> getTipoMembroVisaoPeloMembro(Membro membro) {
		Retorno<String,ITipoMembroVisao> retorno = getFramework().getTipoMembroVisao(membro.getTipoMembroModelo());
		if (retorno.isSucesso()) {
			ITipoMembroVisao tipoMembroVisao = retorno.getParametros().get(Retorno.PARAMETRO_NOVA_INSTANCIA);
			if (tipoMembroVisao instanceof SuperTipoMembroVisaoZK) {
				return (SuperTipoMembroVisaoZK) tipoMembroVisao;
			}
		}
		return null;
	}
	
	/**
	 * Método que retorna a Coleção de TipoMembroVisao do Framework, sem converter em SuperTipoMembroVisaoZK
	 * ou analisar se é um TipoMembroVisao do DocScree
	 * 
	 * @return Coleção de ITipoMembroVisao
	 */
	public Collection<ITipoMembroVisao> getMapaTipoMembrosVisao() {
		Retorno<String,Collection<ITipoMembroVisao>> retorno = super.getFramework().listaTipoMembroVisao();
		Collection<ITipoMembroVisao> lista = new ArrayList<ITipoMembroVisao>();
		if (retorno.isSucesso()) {
			lista = (Collection<ITipoMembroVisao>) retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		}
		return lista;
	}
	
	/**
	 * Método que retorna os TipoMembroVisao pertecentes ao DocScree cadastrados no Framework.
	 * 
	 * @return List<SuperTipoMembroVisaoZK>
	 */
	public List<SuperTipoMembroVisaoZK> listarTipoMembrosVisao() {
		List<SuperTipoMembroVisaoZK> lista = new ArrayList<SuperTipoMembroVisaoZK>();
		for (ITipoMembroVisao tipoMembroVisao : getMapaTipoMembrosVisao()) {
			if (tipoMembroVisao instanceof SuperTipoMembroVisaoZK) {
				lista.add((SuperTipoMembroVisaoZK) tipoMembroVisao);
			}
		}
		return lista;
	}
	
	/**
	 * Método que lista os ArtefatosModelos cadastrados no Framework para abri-lo e editá-lo.
	 * 
	 * @return List<Artefato> 
	 */
	public List<Artefato> listarArtefatosModelo() {
		Retorno<String,Collection<Artefato>> retorno = executarListagem();
		if (retorno.isSucesso()) {
			//TODO tratar para listar somentes ArtefatosModelo não preenchido.
			Collection<Artefato> collection = retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
			return new ArrayList<Artefato>(collection);
		} else {
			return new ArrayList<Artefato>();
		}
	}
	
	/**
	 * Método que lista os ArtefatosModelos cadastrados no Projeto.
	 * 
	 * @return List<Artefato> 
	 */
	public List<Artefato> listarArtefatosModeloPorProjeto(Projeto projeto) {
		List<Artefato> lista = new ArrayList<Artefato>();
		for (ItemModelo itemModelo : projeto.getModelo().getItemModelo()) {
			Retorno<String,Collection<Artefato>> retorno = getFramework().pesquisarArtefato(itemModelo.getArtefato());
			if (retorno.isSucesso()) {
				lista.addAll(retorno.getParametros().get(Retorno.PARAMERTO_LISTA));
			}
		}
		return lista;
	}

}
