package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoMembro;

/**
 * Interface que representa as operacoes de manipulacao da entidade ArtefatoMembro no controle.
 * @author QUID
 *
 */
public interface IArtefatoMembroControle<T, oid> extends IControle<T, oid>{
	
	/**Metodo responsavel por realizar a pesquisa dos Membros de um artefato
	 * @param artefato Artefato a ser procurado
	 * @return Lista de ArtefatosMembro do artefato
	 */
	Collection<ArtefatoMembro> pesquisarMembrosArtefatos(Artefato artefato);

}
