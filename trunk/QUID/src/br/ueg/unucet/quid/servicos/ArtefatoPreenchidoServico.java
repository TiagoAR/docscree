package br.ueg.unucet.quid.servicos;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.interfaces.IArtefatoPreenchidoControle;
import br.ueg.unucet.quid.interfaces.IArtefatoPreenchidoServico;

@Service("ArtefatoPreenchidoServico")
public class ArtefatoPreenchidoServico extends GenericoServico<ArtefatoPreenchido> implements IArtefatoPreenchidoServico<ArtefatoPreenchido>{

	@Autowired
	private IArtefatoPreenchidoControle<ArtefatoPreenchido, Long> artefatoPreenchidoControle;
	
	@Override
	public Retorno<String, Collection<ArtefatoPreenchido>> pesquisarArtefatoPreenchido(
			ArtefatoPreenchido artefatoPreenchido) {
		//this.artefatoPreenchidoControle.
		return null;
	}

}
