package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zul.Messagebox;

import br.ueg.unucet.docscree.annotation.AtributoVisao;
import br.ueg.unucet.docscree.controladores.UsuarioControle;
import br.ueg.unucet.docscree.utilitarios.Conversor;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PerfilAcessoEnum;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

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

	private static final long serialVersionUID = -8506722128070841379L;

	/* Atributos da entidade */
	@AtributoVisao(isCampoEntidade = true, nome = "nome", nomeCampoBundle = "usuario_campo_nome")
	private String fldNome;

	@AtributoVisao(isCampoEntidade = true, nome = "senha", nomeCampoBundle = "usuario_campo_senha")
	private String fldSenha;

	@AtributoVisao(isCampoEntidade = false, nome = "confirmarSenha", nomeCampoBundle = "usuario_campo_confirmar_senha")
	private String fldConfirmarSenha;

	@AtributoVisao(isCampoEntidade = true, nome = "email", nomeCampoBundle = "usuario_campo_email")
	private String fldEmail;

	@AtributoVisao(isCampoEntidade = true, nome = "perfilAcesso", nomeCampoBundle = "usuario_campo_perfil_acesso")
	private PerfilAcessoEnum fldPerfilAcesso;

	@AtributoVisao(isCampoEntidade = true, nome = "status", nomeCampoBundle = "usuario_campo_status")
	private StatusEnum fldStatus;

	private String status;
	private String perfilAcesso;

	/* Fim atributos da entidade */

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

	public void acaoSalvar() {
		try {
			if (prepararDados()) {
				boolean resultado = this.getControle().fazerAcao("salvar", (SuperCompositor) this);
				if (resultado) {
					gerarMensagemSucesso();
				} else {
					gerarMensagemErro();
				}
			}
		} catch (Exception e) {

		}
	}

	private boolean prepararDados() {
		boolean retorno = false;
		try {
			setFldPerfilAcesso(Conversor.castParaEnum(PerfilAcessoEnum.class,
					getPerfilAcesso()));
			if (getStatus() == null || getStatus().isEmpty()) {
				setFldStatus(StatusEnum.ATIVO);
			} else {
				setFldStatus(Conversor.castParaEnum(StatusEnum.class,
						getStatus()));
			}
			retorno = true;
		} catch (Exception e) {
			Messagebox.show("Não foi escolhido um Perfil de Acesso Válido!\nSelecione um perfil!", "Erro", Messagebox.OK, Messagebox.ERROR);
		} 
		return retorno;
	}
	
	private void gerarMensagemSucesso() {
		System.out.println("Deu certo!");
	}
	
	private void gerarMensagemErro() {
		String mensagem = "";
		for (String mensagemParcial : super.getControle().getListaMensagensErro()) {
			mensagem+= mensagemParcial + "\n";
		}
		Messagebox.show(mensagem, "Atenção", Messagebox.OK, Messagebox.INFORMATION);
	}

	public List<String> getListaPerfil() {
		ArrayList<String> listaPerfil = new ArrayList<>();
		for (PerfilAcessoEnum perfil : PerfilAcessoEnum.values()) {
			listaPerfil.add(perfil.toString());
		}
		return listaPerfil;
	}

	/* GETTERS AND SETTERS */

	/**
	 * @return String o(a) fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param String
	 *            o(a) fldNome a ser setado(a)
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
	 * @param String
	 *            o(a) fldSenha a ser setado(a)
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
	 * @param String
	 *            o(a) fldConfirmarSenha a ser setado(a)
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
	 * @param String
	 *            o(a) fldEmail a ser setado(a)
	 */
	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}

	/**
	 * @return PerfilAcessoEnum o(a) fldPerfilAcesso
	 */
	public PerfilAcessoEnum getFldPerfilAcesso() {
		return fldPerfilAcesso;
	}

	/**
	 * @param PerfilAcessoEnum
	 *            o(a) fldPerfilAcesso a ser setado(a)
	 */
	public void setFldPerfilAcesso(PerfilAcessoEnum fldPerfilAcesso) {
		this.fldPerfilAcesso = fldPerfilAcesso;
	}

	/**
	 * @return StatusEnum o(a) fldStatus
	 */
	public StatusEnum getFldStatus() {
		return fldStatus;
	}

	/**
	 * @param StatusEnum
	 *            o(a) fldStatus a ser setado(a)
	 */
	public void setFldStatus(StatusEnum fldStatus) {
		this.fldStatus = fldStatus;
	}

	/**
	 * @return String o(a) status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param String
	 *            o(a) status a ser setado(a)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return String o(a) perfilAcesso
	 */
	public String getPerfilAcesso() {
		return perfilAcesso;
	}

	/**
	 * @param String
	 *            o(a) perfilAcesso a ser setado(a)
	 */
	public void setPerfilAcesso(String perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
}
