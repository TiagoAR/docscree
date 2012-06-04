package br.ueg.unucet.docscree.controladores;

import java.util.Collection;
import java.util.Iterator;

import br.ueg.unucet.docscree.utilitarios.Conversor;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.excessoes.UsuarioExcessao;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Controlador específico de Usuários
 * 
 * @author Diego
 *
 */
public class UsuarioControle extends GenericoControle<Usuario> {

	/**
	 * Método sobrescrito para validar os dados a serem preenchidos do usuário
	 * Recebe os parâmetros da visão e os seta de forma correta na entidade.
	 * 
	 * @see br.ueg.unucet.docscree.controladores.SuperControle#preAcao()
	 */
	@Override
	protected boolean preAcao() {
		boolean retorno = true;
		try {
			getEntidade().setPerfilAcesso(
					Conversor.castParaEnum(
							PerfilAcessoEnum.class,
							(String) super.getMapaAtributos().get(
									"perfilAcesso")));
		} catch (Exception e) {
			super.getMensagens()
					.getListaMensagens()
					.add("N�o foi escolhido um Perfil de Acesso V�lido!\nSelecione um perfil!");
			super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			retorno = false;
		}
		boolean status = (boolean) super.getMapaAtributos().get("status");
		StatusEnum statusEnum;
		if (status) {
			statusEnum = StatusEnum.ATIVO;
		} else {
			statusEnum = StatusEnum.INATIVO;
		}
		super.getEntidade().setStatus(statusEnum);
		if (super.getEntidade().getSenha() != null
				&& !super
						.getEntidade()
						.getSenha()
						.equals((String) super.getMapaAtributos().get(
								"confirmarSenha"))) {
			super.mensagens
					.getListaMensagens()
					.add("O campo Confirmar Senha não confere com a Senha digitada, verifique!");
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Ação Salvar usuário, se usuário for salvo retorna true, caso contrário
	 * preenche lista de mensagens de erro e retorna false.
	 * 
	 * @return boolean se ação foi executada
	 */
	public boolean acaoSalvar() {
		Retorno<String, Collection<String>> retorno = super.getFramework()
				.inserirUsuario(super.getEntidade());
		if (retorno.isSucesso()) {
			return true;
		} else {
			String mensagemErro;
			Throwable erro = retorno.getErro();
			mensagemErro = erro.getMessage();
			if (erro instanceof UsuarioExcessao) {
				if (((UsuarioExcessao) erro).getAtributosNaoInformados() != null) {
					Iterator<String> iterador = ((UsuarioExcessao) erro)
							.getAtributosNaoInformados().iterator();
					if (iterador.hasNext()) {
						mensagemErro += ": " + iterador.next();
					}
					while (iterador.hasNext()) {
						String campoNaoInformado = (String) iterador.next();
						mensagemErro += ", " + campoNaoInformado;
					}
				}
			}
			super.mensagens.getListaMensagens().add(mensagemErro);
			return false;
		}
	}
}
