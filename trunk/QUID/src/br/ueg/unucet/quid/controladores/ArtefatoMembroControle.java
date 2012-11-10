package br.ueg.unucet.quid.controladores;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoMembro;
import br.ueg.unucet.quid.interfaces.IArtefatoMembroControle;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoMembro;

/**
 * Classe responsavel por realizar as operacoes sobre o Membro.
 * @author QUID
 *
 */
@Service("ArtefatoMembroControle")
public class ArtefatoMembroControle extends GenericControle<ArtefatoMembro, Long> implements IArtefatoMembroControle<ArtefatoMembro, Long>{

	/**
	 * Atributo responsavel por realizar as acoes de persistencia do Membro
	 */
	@Autowired
	private IDAOArtefatoMembro<ArtefatoMembro, Long> daoArtefatoMembro;
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoMembroControle#pesquisarMembrosArtefatos(br.ueg.unucet.quid.dominios.Artefato)
	 */
	public Collection<ArtefatoMembro> pesquisarMembrosArtefatos(Artefato artefato){
		ArtefatoMembro artefatoMembro = new ArtefatoMembro();
		artefatoMembro.setArtefato(artefato);
		return pesquisarPorRestricao(artefatoMembro, new String[]{"artefatomembro.codigo", "artefatomembro.artefato.codigo","artefatomembro.x","artefatomembro.altura", 
				"artefatomembro.comprimento","artefatomembro.y","artefatomembro.membroFramework.codigo","artefatomembro.membroFramework.nome","artefatomembro.membroFramework.descricao",
				"artefatomembro.membroFramework.tipoMembro.codigo", "artefatomembro.membroFramework.tipoMembro.nome", "artefatomembro.membroFramework.tipoMembro.versao", 
				"artefatomembro.membroFramework.parametros"});
	}
	
	
	@Override
	public IDAO<ArtefatoMembro, Long> getDao() {
		return this.daoArtefatoMembro;
	}
	
	
	//GETTERS AND SETTERS
	public IDAOArtefatoMembro<ArtefatoMembro, Long> getDaoArtefatoMembro() {
		return daoArtefatoMembro;
	}
	public void setDaoArtefatoMembro(IDAOArtefatoMembro<ArtefatoMembro, Long> daoArtefatoMembro) {
		this.daoArtefatoMembro = daoArtefatoMembro;
	}

	
}
