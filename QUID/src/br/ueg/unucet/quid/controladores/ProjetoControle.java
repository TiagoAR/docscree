package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.excessoes.ProjetoExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOProjeto;
import br.ueg.unucet.quid.interfaces.IProjetoControle;

/**
 * Classe responsavel por realizar as operacoes sobre a entidade Projeto
 * @author QUID
 *
 */
@Service
public class ProjetoControle extends GenericControle<Projeto, Long> implements IProjetoControle<Projeto, Long>{

	/**
	 * Atributo responsavel por realizar as operacoes de persistencia da entidade
	 */
	@Autowired
	private IDAOProjeto<Projeto, Long> daoProjeto;
	/**
	 * Projeto para o qual se esta realizando as operacoes
	 */
	private Projeto projeto;
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#antesInserir(java.lang.Object)
	 */
	public boolean antesInserir(Projeto projeto) throws QuidExcessao{
		this.projeto = projeto;
		verificarProjeto();
		verificarEquipe();
		Projeto projetoBusca = new Projeto();
		projetoBusca.setNome(projeto.getNome());
		if(isCadastrada(projetoBusca, new String[]{"projeto.codigo", "projeto.nome"})){
			throw new ProjetoExcessao(propertiesMessagesUtil.getValor("projeto_cadastrado"));
		}
		return true;
	}
	
	private void verificarProjeto() throws QuidExcessao {
		if (this.projeto.getNome() == null || this.projeto.getNome().isEmpty()) {
			ProjetoExcessao projetoExcessao = new ProjetoExcessao(propertiesMessagesUtil.getValor("erro_atributo_nao_informado") + ": nome");
			throw projetoExcessao;
		}
	}
	
	private void verificarEquipe() throws QuidExcessao {
		if(this.projeto.getEquipe()!= null && this.projeto.getEquipe().getStatus().equals(StatusEnum.INATIVO)){
			throw new ProjetoExcessao("Deve-se escolher uma equipe ativa!");
		}
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#verificarListaCadastrada(java.util.Collection)
	 */
	public boolean verificarListaCadastrada(Collection<Projeto> lista){
		boolean duplicado = false;
		for (Projeto projetoLista : lista) {
			if(this.projeto.getNome().equals(projetoLista.getNome())){
				duplicado = true;
			}
		}
		return duplicado;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IProjetoControle#bloquearProjeto(br.ueg.unucet.quid.dominios.Projeto)
	 */
	@Override
	public void bloquearProjeto(Projeto projeto) throws ProjetoExcessao {
		projeto.setStatus(StatusEnum.INATIVO);
		try {
			alterar(projeto);
		} catch (QuidExcessao e) {
			throw new ProjetoExcessao(e.getMessage(), e);
		}
		
	}
	
	@Override
	public Collection<Projeto> pesquisarProjeto(Projeto projeto){
		Collection<Projeto> projetos = pesquisarPorRestricao(projeto, new String[]{"projeto.codigo"});
		Collection<Projeto> retorno = new ArrayList<Projeto>();
		for (Projeto projeto2 : projetos) {
			projeto2 = getPorId(Projeto.class, projeto2.getCodigo());
			retorno.add(projeto2);
		}
		return retorno;
	}
	
	@Override
	public IDAO<Projeto, Long> getDao() {
		return this.daoProjeto;
	}
	
	//GETTERS AND SETTERS
	public IDAOProjeto<Projeto, Long> getDaoProjeto() {
		return daoProjeto;
	}
	public void setDaoProjeto(IDAOProjeto<Projeto, Long> daoProjeto) {
		this.daoProjeto = daoProjeto;
	}

	
	

}
