package br.ueg.unucet.quid.servicos;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.excessoes.UsuarioExcessao;
import br.ueg.unucet.quid.interfaces.IUsuarioControle;
import br.ueg.unucet.quid.interfaces.IUsuarioServico;

@Service("UsuarioServico")
public class UsuarioServico extends GenericoServico<Usuario> implements IUsuarioServico<Usuario> {

	@Autowired
	private IUsuarioControle<Usuario, Long> usuarioControle;
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IUsuarioServico#inserir(java.lang.Object)
	 */
	@Override
	public Retorno<String, Collection<String>> inserir(Usuario usuario) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		try {
			this.usuarioControle.inserir(usuario);
			retorno.setSucesso(true);
		} catch (QuidExcessao e) {
			retorno = (Retorno<String, Collection<String>>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
			if(UsuarioServico.class.isInstance(e)){
				UsuarioExcessao usuarioExcessao = (UsuarioExcessao) e;
				retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, usuarioExcessao.getAtributosNaoInformados());
				retorno.setTipoErro(TipoErroEnum.ERRO_SIMPLES);
			}
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IUsuarioServico#pesquisar(java.lang.Object)
	 */
	@Override
	public Retorno<String, Collection<Usuario>> pesquisar(Usuario usuario) {
		Retorno<String, Collection<Usuario>> retorno = new Retorno<String, Collection<Usuario>>();
		Collection<Usuario> lista = this.usuarioControle.pesquisarPorRestricao(usuario, new String[]{"usuario.codigo","usuario.senha","usuario.nome", "usuario.email", "usuario.status", "usuario.perfilAcesso"});
		if(lista == null || lista.isEmpty()){
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMensagensUtil.getValor("lista_vazia"));
			retorno.setTipoErro(TipoErroEnum.INFORMATIVO);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
		}else{
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
		}
		return retorno;
	}
	
	
	

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IUsuarioServico#alterar(java.lang.Object)
	 */
	@Override
	public Retorno<String, Collection<String>> alterar(Usuario usuario) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		try {
			this.usuarioControle.alterar(usuario);
			retorno.setSucesso(true);
		} catch (QuidExcessao e) {
			retorno = (Retorno<String, Collection<String>>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
			if(UsuarioServico.class.isInstance(e)){
				UsuarioExcessao usuarioExcessao = (UsuarioExcessao) e;
				retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, usuarioExcessao.getAtributosNaoInformados());
			}
		}
		return retorno;
	}

	public IUsuarioControle<Usuario, Long> getUsuarioControle() {
		return usuarioControle;
	}

	public void setUsuarioControle(IUsuarioControle<Usuario, Long> usuarioControle) {
		this.usuarioControle = usuarioControle;
	}
	
	

	


}
