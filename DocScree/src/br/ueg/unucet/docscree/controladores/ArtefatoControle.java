package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
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
		// TODO Auto-generated method stub
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
	
	public boolean acaoMontarArtefato() {
		if (super.listarPapeisDoUsuario().contains(PapelUsuario.MONTADOR)) {
			return true;
		} else {
			getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			getMensagens().getListaMensagens().add("Somente usuário cadastrados como Montador na equipe podem acessar a funcionalidade!");
			return false;
		}
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

}
