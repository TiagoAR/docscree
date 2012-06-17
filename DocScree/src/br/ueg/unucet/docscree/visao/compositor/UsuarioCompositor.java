package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleListModel;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.UsuarioControle;
import br.ueg.unucet.docscree.visao.ILogar;
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
public class UsuarioCompositor extends GenericoCompositor<UsuarioControle> implements ILogar {

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

	private String filtroCodigo = "";
	private String filtroNome = "";
	private String filtroEmail = "";
	private String filtroPerfil = "";
	
	private Boolean exibirInativos = new Boolean(false);

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
	protected void limparCampos() {
		setCodigo(null);
		setFldConfirmarSenha("");
		setFldEmail("");
		setFldNome("");
		setFldPerfilAcesso("");
		setFldSenha("");
		setFldStatus(Boolean.TRUE);
		super.binder.loadAll();
	}
	
	@Override
	protected void limparFiltros() {
		setFiltroCodigo("");
		setFiltroEmail("");
		setFiltroNome("");
		setFiltroPerfil("");
		
		super.binder.loadAll();
	}

	/**
	 * Método que executa a ação de Desativar Usuário e mostra mensagem de
	 * sucesso ou erro após ação.
	 */
	public void acaoDesativar() {
		try {
			super.binder.saveAll();
			super.getControle().setarEntidadeVisao(this);
			int index = super.getListaEntidade().indexOf(super.getEntidade());
			setFldStatus(Boolean.FALSE);
			super.getControle().fazerAcao("salvar", (SuperCompositor) this);
			((Usuario) super.getListaEntidade().get(index)).setStatus(StatusEnum.INATIVO);
			acaoFiltrar();
			super.binder.loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que executa a ação de Listar e mostra mensagem de sucesso ou erro
	 * após ação.
	 */
	public void acaoEditar() {
		super.binder.saveAll();
		super.getControle().setarEntidadeVisao(this);
		super.fecharModalLista();
		super.binder.loadAll();
	}

	public void acaoFiltrar() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		for (Object objeto : super.getListaEntidade()) {
			Usuario usuario = (Usuario) objeto;
			if (usuario.getCodigo().toString().trim().toLowerCase()
					.contains(getFiltroCodigo().trim().toLowerCase())
					&& usuario.getNome().trim().toLowerCase()
							.contains(getFiltroNome().trim().toLowerCase())
					&& usuario.getEmail().trim().toLowerCase()
							.contains(getFiltroEmail().trim().toLowerCase())
					&& usuario.getPerfilAcesso().toString().trim()
							.toLowerCase()
							.contains(getFiltroPerfil().trim().toLowerCase())) {
				listaUsuarios.add(usuario);
			}
		}
		List<org.zkoss.zk.ui.Component> listaChildren = super.component.getChildren();
		for (org.zkoss.zk.ui.Component componente : listaChildren) {
			if (componente.getId().equalsIgnoreCase("windowMensagens")) {
				Listbox listbox = (Listbox) componente.getFirstChild();
				ListModel listModel = new BindingListModelListModel<Usuario>(new SimpleListModel<Usuario>(listaUsuarios));
				listbox.setModel(listModel);
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.docscree.visao.compositor.GenericoCompositor#acaoListar()
	 */
	@Override
	public void acaoListar() {
		super.acaoListar();
		setExibirInativos(!getExibirInativos());
		verificarExibicaoInativos();
	}
	
	/**
	 * @see br.ueg.unucet.docscree.visao.ILogar#acaoLogar()
	 */
	@Override
	public void acaoLogar() {
		setFldConfirmarSenha(null);
		setFldNome(null);
		setFldPerfilAcesso(null);
		setFldStatus(Boolean.TRUE);
		try {
			boolean resultado = super.getControle().fazerAcao("logar", (SuperCompositor) this);
			if (resultado) {
				setFldEmail(null);
				setFldSenha(null);
				this.salvarSessaoUsuario(super.getControle().getUsuarioLogado());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see br.ueg.unucet.docscree.visao.ILogar#salvarSessaoUsuario(Usuario usuario)
	 */
	@Override
	public void salvarSessaoUsuario(Usuario usuario) {
		Executions.getCurrent().getSession().setAttribute("usuario", usuario);
		Executions.sendRedirect("pages/usuario.zul");
	}
	
	/**
	 * Método que verifica se é para exibir os usuários inativos ou não, caso positivo
	 * percorre a lista de usuários e adiciona a janela de listagem, caso contrário os remove.
	 * 
	 */
	public void verificarExibicaoInativos() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		//fazer savelAll em vez de troca de parametros
		setExibirInativos(!getExibirInativos());
		if (!getExibirInativos()) {
			for (Object objeto : super.getListaEntidade()) {
				Usuario usuario = (Usuario) objeto;
				if (usuario.getStatus().equals(StatusEnum.ATIVO)) {
					listaUsuarios.add(usuario);
				}
			}
		} else {
			listaUsuarios = (List<Usuario>) super.getListaEntidade();
		}
		List<org.zkoss.zk.ui.Component> listaChildren = super.component.getChildren();
		for (org.zkoss.zk.ui.Component componente : listaChildren) {
			if (componente.getId().equalsIgnoreCase("windowMensagens")) {
				Listbox listbox = (Listbox) componente.getFirstChild();
				ListModel listModel = new BindingListModelListModel<Usuario>(new SimpleListModel<Usuario>(listaUsuarios));
				listbox.removeAttribute("model");
				listbox.setModel(listModel);
				break;
			}
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
	 * @param fldStatus
	 *            o(a) fldStatus a ser setado(a)
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
	 * @param fldPerfilAcesso
	 *            o(a) flsPerfilAcesso a ser setado(a)
	 */
	public void setFldPerfilAcesso(String fldPerfilAcesso) {
		this.fldPerfilAcesso = fldPerfilAcesso;
	}

	/**
	 * @return o(a) filtroCodigo
	 */
	public String getFiltroCodigo() {
		return filtroCodigo;
	}

	/**
	 * @param filtroCodigo
	 *            o(a) filtroCodigo a ser setado(a)
	 */
	public void setFiltroCodigo(String filtroCodigo) {
		this.filtroCodigo = filtroCodigo;
	}

	/**
	 * @return o(a) filtroNome
	 */
	public String getFiltroNome() {
		return filtroNome;
	}

	/**
	 * @param filtroNome
	 *            o(a) filtroNome a ser setado(a)
	 */
	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	/**
	 * @return o(a) filtroEmail
	 */
	public String getFiltroEmail() {
		return filtroEmail;
	}

	/**
	 * @param filtroEmail
	 *            o(a) filtroEmail a ser setado(a)
	 */
	public void setFiltroEmail(String filtroEmail) {
		this.filtroEmail = filtroEmail;
	}

	/**
	 * @return o(a) filtroPerfil
	 */
	public String getFiltroPerfil() {
		return filtroPerfil;
	}

	/**
	 * @param filtroPerfil
	 *            o(a) filtroPerfil a ser setado(a)
	 */
	public void setFiltroPerfil(String filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}

	/**
	 * @return o(a) exibirInativos
	 */
	public Boolean getExibirInativos() {
		return exibirInativos;
	}

	/**
	 * @param exibirInativos o(a) exibirInativos a ser setado(a)
	 */
	public void setExibirInativos(Boolean exibirInativos) {
		this.exibirInativos = exibirInativos;
	}
}
