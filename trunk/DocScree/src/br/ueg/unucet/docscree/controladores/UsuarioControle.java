package br.ueg.unucet.docscree.controladores;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;

public class UsuarioControle extends GenericoControle<Usuario> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao()
	 */
	@Override
	protected boolean preAcao() {
		if (super.getEntidade().getSenha().equals(
				super.getMapaAtributos().get("confirmarSenha").toString())) {
			return true;
		} else {
			super.listaMensagensErro.add("O campo Confirmar Senha n�o confere com a Senha digitada, verifique!");
			return false;
		}
	}

	/**
	 * A��o Salvar usu�rio, se usu�rio for salvo retorna true, caso contr�rio preenche lista de mensagens
	 * de erro e retorna false.
	 * 
	 * @return boolean a��o executada?
	 */
	public boolean acaoSalvar() {
		Retorno<String, Collection<String>> retorno = super.getFramework().inserirUsuario(super.getEntidade());
		if (retorno.isSucesso()) {
			return true;
		} else {
			super.listaMensagensErro.add(retorno.getMensagem());
			Collection<String> campoNaoInformado = retorno.getParametros().get(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO);
			for (String string : campoNaoInformado) {
				super.listaMensagensErro.add(string);
			}
			return false;
		}
	}

}
