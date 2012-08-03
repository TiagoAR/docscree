package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.ProjetoControle;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;

/**
 * @author Diego
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Component
@Scope("session")
public class ProjetoCompositor extends GenericoCompositor<ProjetoControle> {
	
	@AtributoVisao(isCampoEntidade= true, nome= "nome")
	private String fldNome;
	@AtributoVisao(isCampoEntidade= true, nome= "equipe")
	private Equipe fldEquipe = new Equipe();
	@AtributoVisao(isCampoEntidade= true, nome= "modelo")
	private Modelo fldModelo = new Modelo();
	@AtributoVisao(isCampoEntidade= false, nome= "status")
	private Boolean fldStatus = Boolean.TRUE;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8315698304806410765L;

	@Override
	public Class getTipoEntidade() {
		return Projeto.class;
	}

	@Override
	protected void limparCampos() {
		setFldEquipe(new Equipe());		
		setFldModelo(new Modelo());
		setFldNome("");
		setFldStatus(Boolean.TRUE);
	}

	@Override
	protected void limparFiltros() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acaoFiltrar() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see GenericoCompositor#acaoSalvar()
	 */
	public void acaoSalvar() {
		super.binder.saveAll();
		try {
			boolean retorno = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			if (retorno) {
				limparCampos();
			}
			super.mostrarMensagem(retorno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Equipe> getListaEquipes() {
		return super.getControle().listarEquipes(super.getUsuarioSessao());
	}
	
	public List<Modelo> getListaModelos() {
		return super.getControle().listarModelos();
	}
	
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
	 * @return Equipe o(a) fldEquipe
	 */
	public Equipe getFldEquipe() {
		return fldEquipe;
	}

	/**
	 * @param Equipe o(a) fldEquipe a ser setado(a)
	 */
	public void setFldEquipe(Equipe fldEquipe) {
		this.fldEquipe = fldEquipe;
	}

	/**
	 * @return Modelo o(a) fldModelo
	 */
	public Modelo getFldModelo() {
		return fldModelo;
	}

	/**
	 * @param Modelo o(a) fldModelo a ser setado(a)
	 */
	public void setFldModelo(Modelo fldModelo) {
		this.fldModelo = fldModelo;
	}

	/**
	 * @return Boolean o(a) fldStatus
	 */
	public Boolean getFldStatus() {
		return fldStatus;
	}

	/**
	 * @param Boolean o(a) fldStatus a ser setado(a)
	 */
	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}

}
