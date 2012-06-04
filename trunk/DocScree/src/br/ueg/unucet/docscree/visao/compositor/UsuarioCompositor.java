package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.unucet.docscree.annotation.AtributoVisao;
import br.ueg.unucet.docscree.controladores.UsuarioControle;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;

/**
 * Classe da visão que representa o caso de uso Manter usuário; Composer do
 * Usuário no ZK.
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
@Scope("session")
public class UsuarioCompositor extends SuperCompositor<UsuarioControle> {

	/**
	 * Default Serial
	 */
	private static final long serialVersionUID = -8506722128070841379L;

	/* Atributos para mapeamento/Inicio dos atributos da entidade */
	/**
	 * Campo nome
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "nome", nomeCampoBundle = "usuario_campo_nome")
	private String fldNome;
	/**
	 * Campo senha
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "senha", nomeCampoBundle = "usuario_campo_senha")
	private String fldSenha;
	/**
	 * Campo confirmar senha
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "confirmarSenha", nomeCampoBundle = "usuario_campo_confirmar_senha")
	private String fldConfirmarSenha;
	/**
	 * Campo E-mail
	 */
	@AtributoVisao(isCampoEntidade = true, nome = "email", nomeCampoBundle = "usuario_campo_email")
	private String fldEmail;
	/* Fim dos atributos da entidade */
	/**
	 * Campo Status
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "status", nomeCampoBundle = "usuario_campo_status")
	private Boolean fldStatus = new Boolean(true);
	/**
	 * Campo Perfil de Acesso
	 */
	@AtributoVisao(isCampoEntidade = false, nome = "perfilAcesso", nomeCampoBundle = "usuario_campo_perfil_acesso")
	private String fldPerfilAcesso;

	/* Fim atributos */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.docscree.visao.composer.SuperComposer#doAfterCompose(org
	 * .zkoss.zk.ui.Component)
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

	/**
	 * Método que executa a ação de Salvar Usuário e mostra mensagem de sucesso ou erro após ação
	 */
	public void acaoSalvar() {
		try {
			boolean resultado = this.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que retorna os tipos de perfis de acesso existentes no enumerador.
	 * 
	 * @return listaPerfil
	 */
	public List<String> getListaPerfil() {
		ArrayList<String> listaPerfil = new ArrayList<>();
		for (PerfilAcessoEnum perfil : PerfilAcessoEnum.values()) {
			listaPerfil.add(perfil.toString());
		}
		return listaPerfil;
	}

	/* GETTERS AND SETTERS */

	/**
	 * @return the fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param fldNome
	 *            the fldNome to set
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return the fldSenha
	 */
	public String getFldSenha() {
		return fldSenha;
	}

	/**
	 * @param fldSenha
	 *            the fldSenha to set
	 */
	public void setFldSenha(String fldSenha) {
		this.fldSenha = fldSenha;
	}

	/**
	 * @return the fldConfirmarSenha
	 */
	public String getFldConfirmarSenha() {
		return fldConfirmarSenha;
	}

	/**
	 * @param fldConfirmarSenha
	 *            the fldConfirmarSenha to set
	 */
	public void setFldConfirmarSenha(String fldConfirmarSenha) {
		this.fldConfirmarSenha = fldConfirmarSenha;
	}

	/**
	 * @return the fldEmail
	 */
	public String getFldEmail() {
		return fldEmail;
	}

	/**
	 * @param fldEmail
	 *            the fldEmail to set
	 */
	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}

	/**
	 * @return o(a) fldStatus
	 */
	public Boolean getFldStatus() {
		return fldStatus;
	}

	/**
	 * @param fldStatus o(a) fldStatus a ser setado(a)
	 */
	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}

	/**
	 * @return o(a) fldPerfilAcesso
	 */
	public String getFldPerfilAcesso() {
		return fldPerfilAcesso;
	}

	/**
	 * @param fldPerfilAcesso o(a) flsPerfilAcesso a ser setado(a)
	 */
	public void setFldPerfilAcesso(String fldPerfilAcesso) {
		this.fldPerfilAcesso = fldPerfilAcesso;
	}
}
