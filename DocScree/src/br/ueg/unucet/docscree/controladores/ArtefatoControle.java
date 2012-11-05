package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.utilitarios.BloquearArtefatoControle;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.ArtefatoCompositor;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.PapelUsuario;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

/**
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
				Artefato artefatoLancao = lancarArtefatoNaVisao((ArtefatoCompositor) getVisao());
				BloquearArtefatoControle.obterInstancia().adicionarBloqueioArtefato(artefatoLancao, getUsuarioLogado());
			}
			return super.montarRetorno(retorno);
		}
		return false;
	}

	
	public boolean acaoAbrirArtefato() {
		setarEntidadeVisao(getVisao());
		boolean membrosMapeados = ((ArtefatoCompositor) getVisao()).mapearMembrosAoArtefato();
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
	
	public boolean acaoAlterarMembro() {
		if (this.acaoRenovarBloqueio()) {
			Retorno<Object, Object> retorno = null;
			retorno = getFramework().alterarMembro(getTipoMembroVisao().getMembro());
			return montarRetornoObject(retorno);
		} else {
			return false;
		}
	}
	
	public boolean acaoRemoverMembro() {
		if (this.acaoRenovarBloqueio()) {
			Retorno<Object,Object> retorno = getFramework().removerMembro(getTipoMembroVisao().getMembro());
			return montarRetornoObject(retorno);
		} else {
			return false;
		}
	}
	
	private boolean montarRetornoObject(Retorno<Object, Object> retorno) {
		if (retorno.isSucesso()) {
			return true;
		} else {
			getMensagens().getListaMensagens().add(retorno.getMensagem());
			return false;
		}
	}
	
	private SuperTipoMembroVisaoZK<?> getTipoMembroVisao() {
		return (SuperTipoMembroVisaoZK<?>) getMapaAtributos().get("tipoMembroVisaoSelecionado");
	}
	
	protected boolean isUsuarioMontador() {
		if (super.listarPapeisDoUsuario().contains(PapelUsuario.MONTADOR)) {
			return true;
		} else {
			getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			getMensagens().getListaMensagens().add("Somente usuário cadastrados como Montador na equipe podem acessar a funcionalidade!");
			return false;
		}
	}
	
	public boolean acaoMontarArtefato() {
		return isUsuarioMontador();
	}
	
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
	
	public Collection<ITipoMembroVisao> getMapaTipoMembrosVisao() {
		Retorno<String,Collection<ITipoMembroVisao>> retorno = super.getFramework().listaTipoMembroVisao();
		Collection<ITipoMembroVisao> lista = new ArrayList<ITipoMembroVisao>();
		if (retorno.isSucesso()) {
			lista = (Collection<ITipoMembroVisao>) retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		}
		return lista;
	}
	
	public List<SuperTipoMembroVisaoZK> listarTipoMembrosVisao() {
		List<SuperTipoMembroVisaoZK> lista = new ArrayList<SuperTipoMembroVisaoZK>();
		for (ITipoMembroVisao tipoMembroVisao : getMapaTipoMembrosVisao()) {
			if (tipoMembroVisao instanceof SuperTipoMembroVisaoZK) {
				lista.add((SuperTipoMembroVisaoZK) tipoMembroVisao);
			}
		}
		return lista;
	}
	
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

}
