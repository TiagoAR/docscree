package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Retorno;

public interface IArtefatoPreenchidoServico<T> extends IServico<T> {
	
	public Retorno<String, Collection<T>> pesquisarArtefatoPreenchido(T artefatoPreenchido);

}
