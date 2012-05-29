package br.ueg.unucet.quid.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.ProjetoExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;
import br.ueg.unucet.quid.interfaces.IProjetoControle;
import br.ueg.unucet.quid.interfaces.IProjetoServico;

@Service("ProjetoServico")
public class ProjetoServico extends GenericoServico<Projeto> implements IProjetoServico<Projeto>{

	@Autowired
	private IProjetoControle<Projeto, Long> projetoControle;
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IProjetoServico#inserir(java.lang.String, java.lang.String, br.ueg.unucet.quid.dominios.Modelo, br.ueg.unucet.quid.dominios.Equipe)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Retorno<Object, Object> inserir(String nome, String descricao, Modelo modelo, Equipe equipe) {
		Projeto projeto = new Projeto();
		projeto.setNome(nome);
		projeto.setDescricao(descricao);
		projeto.setModelo(modelo);
		projeto.setEquipe(equipe);
		projeto.setStatus(StatusEnum.ATIVO);
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			this.projetoControle.inserir(projeto);
			retorno.setSucesso(true);
		} catch (QuidExcessao e) {
			retorno = (Retorno<Object, Object>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IProjetoServico#bloquearProjeto(br.ueg.unucet.quid.dominios.Projeto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Retorno<Object, Object> bloquearProjeto(Projeto projeto) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			this.projetoControle.bloquearProjeto(projeto);
			retorno.setSucesso(true);
		} catch (ProjetoExcessao e) {
			retorno = (Retorno<Object, Object>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
		}
		return retorno;
	}
	
	//GETTERS AND SETTERS

	public IProjetoControle<Projeto, Long> getProjetoControle() {
		return projetoControle;
	}

	public void setProjetoControle(IProjetoControle<Projeto, Long> projetoControle) {
		this.projetoControle = projetoControle;
	}
	
	

}
