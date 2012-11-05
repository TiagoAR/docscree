package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.docscree.utilitarios.BloquearArtefatoControle;
import br.ueg.unucet.docscree.visao.compositor.ArtefatoCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperArtefatoCompositor;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

public class SuperArtefatoControle extends GenericoControle<Artefato> {

	@Override
	public boolean acaoSalvar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acaoExcluir() {
		return false;
	}

	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		SuperArtefatoCompositor<?> visao = (SuperArtefatoCompositor<?>) pVisao;
		Artefato artefatoSelecionado = (Artefato) visao.getEntidade();
		visao.setCodigo(artefatoSelecionado.getCodigo());
		visao.setNome(artefatoSelecionado.getNome());
		visao.setDescricao(artefatoSelecionado.getDescricao());
		visao.setCategoria(artefatoSelecionado.getCategoria());
		visao.setAltura(artefatoSelecionado.getAltura());
		visao.setLargura(artefatoSelecionado.getLargura());
		visao.setTitulo(artefatoSelecionado.getTitulo());
		visao.setArtefatoControle(artefatoSelecionado.getArtefatoControle());
		visao.setMembros(artefatoSelecionado.getMembros());
		visao.setServicos(artefatoSelecionado.getServicos());
	}

	@Override
	protected Retorno<String, Collection<Artefato>> executarListagem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean acaoRenovarBloqueio() {
		if (BloquearArtefatoControle.obterInstancia().renovarBloqueio(getEntidade(), getUsuarioLogado())) {
			return true;
		} else {
			getMensagens().getListaMensagens().add("O Artefato que está tentando modificar não está mais bloqueado para seu usuário, tente abrí-lo novamente!");
			return false;
		}
	}

	protected Artefato lancarArtefatoNaVisao(ArtefatoCompositor visao) {
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
		visao.setEntidade(artefato);
		
		return artefato;
	}
	
	public Membro getMembroDoTipoMembro(ITipoMembroVisao tipoMembro) {
		Retorno<String, ITipoMembroModelo> retorno = getFramework().getTipoMembroModelo(tipoMembro);
		ITipoMembroModelo tipoMembroModelo = retorno.getParametros().get(Retorno.PARAMETRO_NOVA_INSTANCIA);
		Membro membro = new Membro();
		membro.setTipoMembroModelo(tipoMembroModelo);
		return membro;
	}
	
	public Collection<Membro> listarMembrosPorTipoMembro(ITipoMembroModelo tipoMembro) {
		Collection<Membro> lista = new ArrayList<Membro>();
		Retorno<String, Collection<Membro>> retorno = getFramework().getListaMembro(tipoMembro);
		if (retorno.isSucesso()) {
			lista = retorno.getParametros().get(Retorno.PARAMERTO_LISTA);
		}
		return lista;
	}

}
