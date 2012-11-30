package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.excessoes.QuidExcessao;

public interface IArtefatoPreenchidoControle<T, oid>  extends IControle<T, oid> {
	
	Collection<ArtefatoPreenchido> pesquisarArtefatoPreenchido(ArtefatoPreenchido entidade) throws QuidExcessao;

	Artefato obterArtefato(ArtefatoPreenchido artefatoPreenchido)
			throws QuidExcessao;

}
