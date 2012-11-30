package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.Retorno;

public interface IArtefatoPreenchidoServico<T> extends IServico<T> {
	
	public Retorno<String, Collection<T>> pesquisarArtefatoPreenchido(T artefatoPreenchido);

	Retorno<String, Artefato> obterArtefatoModelo(
			ArtefatoPreenchido artefatoPreenchido);

}
