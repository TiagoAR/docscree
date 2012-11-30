package br.ueg.unucet.quid.servicos;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.interfaces.IArtefatoPreenchidoControle;
import br.ueg.unucet.quid.interfaces.IArtefatoPreenchidoServico;

@Service("ArtefatoPreenchidoServico")
public class ArtefatoPreenchidoServico extends GenericoServico<ArtefatoPreenchido> implements IArtefatoPreenchidoServico<ArtefatoPreenchido>{

	@Autowired
	private IArtefatoPreenchidoControle<ArtefatoPreenchido, Long> artefatoPreenchidoControle;
	
	@Override
	public Retorno<String, Collection<ArtefatoPreenchido>> pesquisarArtefatoPreenchido(
			ArtefatoPreenchido artefatoPreenchido) {
		Retorno<String, Collection<ArtefatoPreenchido>> retorno = new Retorno<String, Collection<ArtefatoPreenchido>>();
		try {
			Collection<ArtefatoPreenchido> collection = this.artefatoPreenchidoControle.pesquisarArtefatoPreenchido(artefatoPreenchido);
			if (collection != null) {
				retorno.setSucesso(true);
				retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, collection);
			} else {
				retorno.setSucesso(false);
				retorno.setMensagem("Não foi possível listar os Artefatos Preenchidos");
			}
		} catch (QuidExcessao e) {
			construirRetornoErro(e, TipoErroEnum.ERRO_FATAL, retorno);
		}
		return retorno;
	}
	
	@Override
	public Retorno<String, Artefato> obterArtefatoModelo(ArtefatoPreenchido artefatoPreenchido) {
		Retorno<String, Artefato> retorno = new Retorno<String, Artefato>();
		try {
			Artefato artefato = this.artefatoPreenchidoControle.obterArtefato(artefatoPreenchido);
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, artefato);
		} catch (QuidExcessao e) {
			construirRetornoErro(e, TipoErroEnum.ERRO_FATAL, retorno);
		}
		return retorno;
	}

}
