package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ueg.unucet.docscree.interfaces.ICRUDControle;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.extensao.dominios.Persistivel;

/**
 * Controle Genérico, extende SuperControle e contém operações básicas de todos os controladores persistíveis
 * 
 * @author Diego
 *
 * @param <E>
 */
@SuppressWarnings({"unchecked"})
public abstract class GenericoControle<E extends Persistivel> extends SuperControle implements ICRUDControle {
	
	/**
	 * Contém a lista de entidade, setado quando chamado a ação listar.
	 */
	protected List<?> lista;
	
	/**
	 * Método que retorna a entidade do mapeador de atributos.
	 * 
	 * @return E entidade
	 */
	protected E getEntidade() {
		return (E) super.getMapaAtributos().get("entidade");
	}

	/**
	 * Método que adiciona as mensagens de erro através do retorno da ação
	 * 
	 * @param retorno Retorno da ação
	 */
	protected abstract void montarMensagemErro(Retorno<String, Collection<String>> retorno);

	/**
	 * @return o(a) lista
	 */
	public List<?> getLista() {
		return lista;
	}

	/**
	 * @param lista o(a) lista a ser setado(a)
	 */
	public void setLista(List<?> lista) {
		this.lista = lista;
	}
	
	protected boolean isUsuarioAdmin() {
		if (((Usuario) super.getMapaAtributos().get("usuarioLogado")).getPerfilAcesso().equals(PerfilAcessoEnum.ADMINISTRADOR)) {
			return true;
		}
		return false;
	}
	
	protected boolean isUsuarioGerente() {
		if (((Usuario) super.getMapaAtributos().get("usuarioLogado")).getPerfilAcesso().equals(PerfilAcessoEnum.GERENTE)) {
			return true;
		}
		return false;
	}
	
	protected boolean isUsuarioComum() {
		if (((Usuario) super.getMapaAtributos().get("usuarioLogado")).getPerfilAcesso().equals(PerfilAcessoEnum.USUARIO)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean acaoListar() {
		Retorno<String, Collection<E>> retorno = executarListagem();
		if (retorno.isSucesso()) {
			Collection<E> listaEntidades = retorno.getParametros().get(
					Retorno.PARAMERTO_LISTA);
			this.setLista(new ArrayList<E>(listaEntidades));
			return true;
		} else {
			super.mensagens.getListaMensagens().add(retorno.getMensagem());
			return false;
		}
	}
	
	protected abstract Retorno<String, Collection<E>> executarListagem();
	
	protected void montarMensagemErroPermissao(String tipoUsuario) {
		super.mensagens.getListaMensagens().add("O tipo de usuário "+tipoUsuario+" não tem permissão para executar a ação!");
	}
	
	protected boolean montarRetorno(Retorno<String, Collection<String>> retorno) {
		if (retorno.isSucesso()) {
			return true;
		} else {
			montarMensagemErro(retorno);
			return false;
		}
	}
}
