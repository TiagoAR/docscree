package br.ueg.unucet.quid.controladores;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoServico;
import br.ueg.unucet.quid.interfaces.IArtefatoServicoControle;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoServico;

/**
 * @author QUID
 *Classe responsavel por realizar as operacoes sobre a enditdade ArtefatoServico
 */
@Service("ArtefatoServicoControle")
public class ArtefatoServicoControle extends GenericControle<ArtefatoServico, Long> implements IArtefatoServicoControle<ArtefatoServico, Long>{

	/**
	 * Atributo responsavel por realizar as operacoes de persistencia.
	 */
	@Autowired
	private IDAOArtefatoServico<ArtefatoServico, Long> daoArtefatoServico;
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IArtefatoServicoControle#pesquisarArtefatosServicoArtefato(br.ueg.unucet.quid.dominios.Artefato)
	 */
	public Collection<ArtefatoServico> pesquisarArtefatosServicoArtefato(Artefato artefato){
		ArtefatoServico artefatoServico = new ArtefatoServico();
		artefatoServico.setArtefato(artefato);
		return pesquisarPorRestricao(artefatoServico, new String[]{"artefatoservico.codigo","artefatoservico.parametrosServico","artefatoservico.parametrosTipoMembro","artefatoservico.artefato.codigo","artefatoservico.servico.codigo",
				"artefatoservico.servico.nome","artefatoservico.servico.versao", "artefatoservico.servico.revisao", "artefatoservico.servico.descricao", "artefatoservico.anteriorObrigatorio"
				,"artefatoservico.servicoAnterior.codigo","artefatoservico.servicoAnterior.nome" ,"artefatoservico.servicoAnterior.versao", "artefatoservico.servicoAnterior.revisao"
				,"artefatoservico.servicoProximo.codigo","artefatoservico.servicoProximo.nome" ,"artefatoservico.servicoProximo.versao", "artefatoservico.servicoProximo.revisao"});
	}
	
	
	@Override
	public IDAO<ArtefatoServico, Long> getDao() {
		return daoArtefatoServico;
	}
	
	//GETTERS AND SETTERS

	public IDAOArtefatoServico<ArtefatoServico, Long> getDaoArtefatoServico() {
		return daoArtefatoServico;
	}

	public void setDaoArtefatoServico(IDAOArtefatoServico<ArtefatoServico, Long> daoArtefatoServico) {
		this.daoArtefatoServico = daoArtefatoServico;
	}

	
}
