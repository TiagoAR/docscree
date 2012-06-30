package br.ueg.unucet.docscree.visao.compositor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.EquipeControle;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PapelUsuario;

/**
 * Classe da visão que representa o caso de uso Manter Equipe; Composer da
 * Equipe no ZK
 * 
 * @author Diego
 * 
 */
@SuppressWarnings({ "rawtypes" })
@Component
@Scope("session")
public class EquipeCompositor extends GenericoCompositor<EquipeControle> {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 7456067099003306099L;

	@AtributoVisao(isCampoEntidade = true, nome = "nome")
	private String fldNome;

	@AtributoVisao(isCampoEntidade = true, nome = "usuarios")
	private List<Usuario> listaUsuario;

	private List<String> listaPapeis = null;

	@Override
	public Class getTipoEntidade() {
		return Equipe.class;
	}

	@Override
	protected void limparCampos() {
		setFldNome("");
		setListaUsuario(new ArrayList<Usuario>());
	}

	@Override
	protected void limparFiltros() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void acaoFiltrar() {
		// TODO Auto-generated method stub

	}

	public List<Usuario> listarUsuarios() {
		return super.getControle().listarUsuarios();
	}

	public List<String> getListaPapeis() {
		if (this.listaPapeis == null) {
			this.listaPapeis = new ArrayList<String>();
			for (PapelUsuario papelUsuario : PapelUsuario.values()) {
				this.listaPapeis.add(papelUsuario.getDescricao());
			}
		}
		return this.listaPapeis;
	}
	
	public void teste() {
		super.getControle().acaoSalvar();
	}

	/**
	 * @return o(a) fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param fldNome
	 *            o(a) fldNome a ser setado(a)
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return o(a) listaUsuario
	 */
	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	/**
	 * @param listaUsuario
	 *            o(a) listaUsuario a ser setado(a)
	 */
	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

}
