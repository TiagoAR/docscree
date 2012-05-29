package br.ueg.unucet.quid.servicos;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.interfaces.IServico;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;

public class GenericoServico<T> implements IServico<T> {
	protected LeitoraPropertiesUtil propertiesMensagensUtil = FabricaProperties.loadMessages();

	/**
	 * Metodo responsavel por construir o objeto de retorno padrao para uma determinada execessao.
	 * @param erro Erro que foi disparado.
	 * @param tipoerro Tipo do erro.
	 * @param retorno Objeto retorno que sera setado as informacoes do erro.
	 * @return Objeto retorno setado as informacoes.
	 */
	protected Retorno<?, ?> construirRetornoErro(Throwable erro, TipoErroEnum tipoerro, Retorno<?, ?> retorno){
		retorno.setErro(erro);
		retorno.setMensagem(erro.getMessage());
		retorno.setSucesso(false);
		retorno.setTipoErro(tipoerro);
		return retorno;
	}
}
