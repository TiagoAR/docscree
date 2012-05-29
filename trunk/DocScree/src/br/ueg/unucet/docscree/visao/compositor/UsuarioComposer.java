package br.ueg.unucet.docscree.visao.compositor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.unucet.docscree.annotation.AtributoVisao;
import br.ueg.unucet.docscree.controladores.UsuarioControle;
import br.ueg.unucet.quid.dominios.Usuario;

/**
 * Classe da visão que representa o caso de uso Manter usuário;
 * Composer do Usuário no ZK.
 * 
 * @author Diego
 *
 */
@SuppressWarnings("rawtypes")
@Component
@Scope("session")
public class UsuarioComposer extends SuperComposer<UsuarioControle> {

	private static final long serialVersionUID = -8506722128070841379L;
	
	/* Atributos da entidade */
	@AtributoVisao(isCampoEntidade = true, nome="nome", nomeCampoBundle="usuario_campo_nome")
	private String fldNome;

	@AtributoVisao(isCampoEntidade = true, nome="senha", nomeCampoBundle="usuario_campo_senha")
	private String fldSenha;
	
	@AtributoVisao(isCampoEntidade = false, nome="confirmarSenha", nomeCampoBundle="usuario_campo_confirmar_senha")
	private String fldConfirmarSenha;

	@AtributoVisao(isCampoEntidade = true, nome="email", nomeCampoBundle="usuario_campo_email")
	private String fldEmail;

	@AtributoVisao(isCampoEntidade = true, nome="perfilAcesso", nomeCampoBundle="usuario_campo_perfil_acesso")
	private String fldPerfilAcesso;

	@AtributoVisao(isCampoEntidade = true, nome="status", nomeCampoBundle="usuario_campo_status")
	private int fldStatus;
	/* Fim atributos da entidade */

	/* (non-Javadoc)
	 * @see br.ueg.unucet.docscree.visao.composer.SuperComposer#doAfterCompose(org.zkoss.zk.ui.Component)
	 */
	@Override
	public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Override
	public Class getTipoEntidade() {
		return Usuario.class;
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKey(Object primaryKey) {
		// TODO Auto-generated method stub
		
	}
	
	public void teste() {
		System.out.println(getFldNome());
	}
	
	public void acaoSalvar() {
		
	}
	
	/* GETTERS AND SETTERS */

	/**
	 * @return String o(a) fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param String o(a) fldNome a ser setado(a)
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return String o(a) fldSenha
	 */
	public String getFldSenha() {
		return fldSenha;
	}

	/**
	 * @param String o(a) fldSenha a ser setado(a)
	 */
	public void setFldSenha(String fldSenha) {
		this.fldSenha = fldSenha;
	}


	/**
	 * @return String o(a) fldConfirmarSenha
	 */
	public String getFldConfirmarSenha() {
		return fldConfirmarSenha;
	}

	/**
	 * @param String o(a) fldConfirmarSenha a ser setado(a)
	 */
	public void setFldConfirmarSenha(String fldConfirmarSenha) {
		this.fldConfirmarSenha = fldConfirmarSenha;
	}

	/**
	 * @return String o(a) fldEmail
	 */
	public String getFldEmail() {
		return fldEmail;
	}

	/**
	 * @param String o(a) fldEmail a ser setado(a)
	 */
	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}

	/**
	 * @return int o(a) fldStatus
	 */
	public int getFldStatus() {
		return fldStatus;
	}

	/**
	 * @param int o(a) fldStatus a ser setado(a)
	 */
	public void setFldStatus(int fldStatus) {
		this.fldStatus = fldStatus;
	}

	/**
	 * @return String o(a) fldPerfilAcesso
	 */
	public String getFldPerfilAcesso() {
		return fldPerfilAcesso;
	}

	/**
	 * @param String o(a) fldPerfilAcesso a ser setado(a)
	 */
	public void setFldPerfilAcesso(String fldPerfilAcesso) {
		this.fldPerfilAcesso = fldPerfilAcesso;
	}
}
