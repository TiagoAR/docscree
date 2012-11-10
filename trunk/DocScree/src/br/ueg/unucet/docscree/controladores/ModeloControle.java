package br.ueg.unucet.docscree.controladores;

import java.util.Collection;

import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Retorno;

public class ModeloControle extends GenericoControle<Modelo> {

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
	protected Retorno<String, Collection<Modelo>> executarListagem() {
		// TODO Auto-generated method stub
		return null;
	}

}
