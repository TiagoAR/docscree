package br.ueg.unucet.quid.servicosquid;

import java.util.ArrayList;

import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.ValoresArtefato;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.utilitarias.SerializadorObjetoUtil;


public class ServicoPersistenciaBD extends ServicoPersistencia {
	
	public static final String NOME_SERVIÇO = "SERVICO_PERSITENCIA_BANCO_DADOS";
	
	public ServicoPersistenciaBD() {
		super();
	}

	@Override
	protected boolean salvarMembros(ArtefatoPreenchido artefatoPreenchido) throws QuidExcessao {
		ValoresArtefato valor;
		artefatoPreenchido.setValoresArtefatos(new ArrayList<ValoresArtefato>());
		for (Membro membro : getArtefatoModelo().getMembros()) {
			valor = new ValoresArtefato();
			valor.setMembro(membro.getCodigo());
			valor.setValor(SerializadorObjetoUtil.toByteArray(membro.getTipoMembroModelo().getValor()));
			artefatoPreenchido.getValoresArtefatos().add(valor);
		}
		getArtefatoPreenchidoControle().alterar(artefatoPreenchido);
		return true;
	}

	@Override
	public String getNome() {
		return NOME_SERVIÇO;
	}

	@Override
	public String getDescricao() {
		return "Serviço que efetua Persistência de Artefato Preenchido em Banco de Dados";
	}


}
