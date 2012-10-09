package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.modelo.MembroDocScree;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.ArtefatoCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.PapelUsuario;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

public class ArtefatoControle extends GenericoControle<Artefato> {

	@Override
	public boolean acaoSalvar() {
		if (this.isUsuarioMontador()) {
			Retorno<String, Collection<String>> retorno;
			if (super.getEntidade().getCodigo() == null) {
				Artefato instanciaArtefato = super.getFramework().getInstanciaArtefato();
				instanciaArtefato.setNome(super.getEntidade().getNome());
				instanciaArtefato.setDescricao(super.getEntidade().getDescricao());
				instanciaArtefato.setAltura(super.getEntidade().getAltura());
				instanciaArtefato.setLargura(super.getEntidade().getLargura());
				retorno = super.getFramework().mapearArtefato(instanciaArtefato);
			} else {
				retorno = super.getFramework().alterarArtefato(super.getEntidade());
			}
			if (retorno.isSucesso()) {
				lancarArtefatoNaVisao((ArtefatoCompositor) getVisao());
			}
			return super.montarRetorno(retorno);
		}
		return false;
	}

	@Override
	public boolean acaoExcluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Retorno<String, Collection<Artefato>> executarListagem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean acaoMapearMembroAoArtefato() {
		boolean retorno = true;
		if (this.isUsuarioMontador()) {
			if (getEntidade().getArtefatoControle() == null) {
				getEntidade().setArtefatoControle(getFramework().getInstanciaArtefato().getArtefatoControle());
			}
			@SuppressWarnings("unchecked")
			List<MembroDocScree> listaMembros = (List<MembroDocScree>) getMapaAtributos().get("listaMembrosDocScree");
			for (MembroDocScree membroDocScree : listaMembros) {
				Retorno<String, Collection<String>> retornoAdicao = getEntidade().addMembro(membroDocScree.getMembroQUID());
				if (!retornoAdicao.isSucesso()) {
					retorno = false;
					getMensagens().getListaMensagens().add(2, retornoAdicao.getMensagem());
					break;
				}
			}
			if (!retorno) {
				for (MembroDocScree membroDocScree : listaMembros) {
					Retorno<Object, Object> retornoRemover = getEntidade().removerMembro(membroDocScree.getMembroQUID());
					if (!retornoRemover.isSucesso()) {
						break;
					}
				}
				getMensagens().setTipoMensagem(TipoMensagem.ERRO);
				getMensagens().getListaMensagens().add(0, "Houve problema de inserção de algum Membro, verifique o erro e tente novamente");
				getMensagens().getListaMensagens().add(1, "Mensagem do framework:");
			}
		}
		return retorno;
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
	
	public Collection<ITipoMembroVisao> getMapaTipoMembrosVisao() {
		Retorno<String,Collection<ITipoMembroVisao>> retorno = super.getFramework().listaTipoMembroVisao();
		Collection<ITipoMembroVisao> lista = new ArrayList<ITipoMembroVisao>();
		if (retorno.isSucesso()) {
			lista = (Collection<ITipoMembroVisao>) retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		}
		return lista;
	}
	
	@SuppressWarnings("rawtypes")
	public List<SuperTipoMembroVisaoZK> listarTipoMembrosVisao() {
		List<SuperTipoMembroVisaoZK> lista = new ArrayList<SuperTipoMembroVisaoZK>();
		for (ITipoMembroVisao tipoMembroVisao : getMapaTipoMembrosVisao()) {
			if (tipoMembroVisao instanceof SuperTipoMembroVisaoZK) {
				Retorno<String, ITipoMembroModelo> retorno = getFramework().getTipoMembroModelo(tipoMembroVisao);
				ITipoMembroModelo tipoMembroModelo = retorno.getParametros().get(Retorno.PARAMETRO_NOVA_INSTANCIA);
				Membro membro = new Membro();
				membro.setTipoMembroModelo(tipoMembroModelo);
				((SuperTipoMembroVisaoZK<?> )tipoMembroVisao).setMembro(membro);
				lista.add((SuperTipoMembroVisaoZK) tipoMembroVisao);
			}
		}
		return lista;
		
	}
	
	public Collection<Membro> listarMembrosPorTipoMembro(ITipoMembroModelo tipoMembro) {
		Collection<Membro> lista = new ArrayList<Membro>();
		Retorno<String, Collection<Membro>> retorno = getFramework().getListaMembro(tipoMembro);
		if (retorno.isSucesso()) {
			lista = retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		}
		return lista;
	}

	protected void lancarArtefatoNaVisao(ArtefatoCompositor visao) {
		Retorno<String,Collection<Artefato>> retorno = getFramework().pesquisarArtefato(getEntidade().getCategoria(), getEntidade().getNome(), getEntidade().getDescricao());
		Collection<Artefato> collection = retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		Artefato artefato = collection.iterator().next();
		
		//TODO listagem não traz o artefato controle
		artefato.setArtefatoControle(getFramework().getInstanciaArtefato().getArtefatoControle());
		
		visao.setNome(artefato.getNome());
		visao.setDescricao(artefato.getDescricao());
		visao.setCodigo(artefato.getCodigo());
		visao.setArtefatoControle(artefato.getArtefatoControle());
		visao.setCategoria(artefato.getCategoria());
		visao.setMembros(artefato.getMembros());
		visao.setServicos(artefato.getServicos());
		visao.setTitulo(artefato.getTitulo());
	}

}
