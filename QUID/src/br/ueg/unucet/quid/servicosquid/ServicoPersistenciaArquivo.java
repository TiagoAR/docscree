package br.ueg.unucet.quid.servicosquid;

import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.excessoes.QuidExcessao;


public class ServicoPersistenciaArquivo extends ServicoPersistencia {
	
	public static final String NOME_SERVI�O = "SERVICO_PERSITENCIA_ARQUIVO";
	
	public ServicoPersistenciaArquivo() {
		super();
	}

	@Override
	protected boolean salvarMembros(ArtefatoPreenchido artefatoPreenchido) throws QuidExcessao{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNome() {
		return NOME_SERVI�O;
	}

	@Override
	public String getDescricao() {
		return "Servi�o que efetua Persist�ncia de Artefato Preenchido em Arquivo";
	}

}
