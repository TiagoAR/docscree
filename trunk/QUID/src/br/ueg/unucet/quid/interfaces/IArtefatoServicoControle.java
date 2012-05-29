package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoServico;

/**
 * Interface que representa as operacoes de manipulacao da entidade ArtefatoServico no controlador.
 * @author QUID
 *
 */
public interface IArtefatoServicoControle<T, oid> extends IControle<T, oid>{
	
	/**Metodo responsavel por pesquisar os ArtefatosServico de um artefato
	 * @param artefato Artefao a ser pesquisado
	 * @return Lista de ArtefatosServicos do artefato
	 */
	Collection<ArtefatoServico> pesquisarArtefatosServicoArtefato(Artefato artefato);
}
