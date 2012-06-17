package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.excessoes.UsuarioExcessao;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOUsuario;
import br.ueg.unucet.quid.interfaces.IUsuarioControle;

/**
 * Classe responsavel por realizar as operacoes sobre a entidade usuario.
 * @author QUID
 *
 */
@Service("UsuarioControle")
public class UsuarioControle extends GenericControle<Usuario, Long> implements IUsuarioControle<Usuario, Long>{

	/**
	 * Atributo responsavel por realizar as operacoes de persistenca do usuario.
	 */
	@Autowired
	private IDAOUsuario<Usuario, Long> daoUsuario;
	
	
	@Override
	public IDAO<Usuario, Long> getDao() {
		return this.daoUsuario;
	}


	
	
	/**
	 * Metodo que verifica se os atributos obrigatorios do usuario foram informados.
	 * @param usuario Usuario que sera verificado os atributos.
	 * @throws UsuarioExcessao Excessao caso exista atributos nao informados.
	 */
	public void verificarCamposObrigatorios(Usuario usuario) throws UsuarioExcessao{
		Collection<String> camposNaoInformados = new ArrayList<String>();
		if(usuario.getNome() == null || usuario.getNome().equals("")){
			camposNaoInformados.add("nome");
		}
		if(usuario.getSenha() == null || usuario.getSenha().equals("")){
			camposNaoInformados.add("senha");
		}
		if(usuario.getEmail() == null || usuario.getEmail().equals("")){
			camposNaoInformados.add("e-mail");
		}
		if(usuario.getPerfilAcesso() == null){
			camposNaoInformados.add("Nivel de Acesso");
		}
		if(!camposNaoInformados.isEmpty()){
			throw new UsuarioExcessao(propertiesMessagesUtil.getValor("erro_atributo_nao_informado"), camposNaoInformados);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#antesInserir(java.lang.Object)
	 */
	public boolean antesInserir(Usuario usuario) throws QuidExcessao{
		verificarCamposObrigatorios(usuario);
		Usuario usuarioAnalise = new Usuario();
		usuarioAnalise.setEmail(usuario.getEmail());
		verificarCadastroDuplicado(usuarioAnalise);
		return true;
	}

	
	/**
	 * Metodo que verifica se um determinado usuario ja esta cadastrado no framework.
	 * @param usuario Usuario que sera verificado o cadastro duplicado no framework.
	 * @throws UsuarioExcessao Excessao caso o usuario ja esteje cadastrado no framework.
	 */
	private void verificarCadastroDuplicado(Usuario usuario) throws UsuarioExcessao {
		if(isCadastrada(usuario, new String[]{"usuario.codigo", "usuario.email"} )){
			throw new UsuarioExcessao(propertiesMessagesUtil.getValor("usuario_cadastrado"));
		}
		
	}


	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#antesAlterar(java.lang.Object)
	 */
	public boolean antesAlterar(Usuario usuario) throws QuidExcessao{
		verificarCamposObrigatorios(usuario);
		return true;
	}
	
	//GETTERS AND SETTERS
	
	public IDAOUsuario<Usuario, Long> getDaoUsuario() {
		return daoUsuario;
	}


	public void setDaoUsuario(IDAOUsuario<Usuario, Long> daoUsuario) {
		this.daoUsuario = daoUsuario;
	}
	

}
