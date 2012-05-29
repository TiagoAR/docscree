package br.ueg.unucet.quid.servicos;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.EquipeExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.interfaces.IEquipeControle;
import br.ueg.unucet.quid.interfaces.IEquipeSevico;

@Service("EquipeServico")
public class EquipeServico extends GenericoServico<Equipe> implements IEquipeSevico<Equipe>{

	@Autowired
	private IEquipeControle<Equipe, Long> equipeControle;
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IEquipeSevico#inserir(java.lang.Object)
	 */
	@Override
	public Retorno<String, Collection<String>> inserir(Equipe equipe) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		try {
			equipeControle.inserir(equipe);
			retorno.setSucesso(true);
		} catch (QuidExcessao e) {
			retorno = (Retorno<String, Collection<String>>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
			if(EquipeExcessao.class.isInstance(e)){
				EquipeExcessao equipeExcessao = (EquipeExcessao) e;
				retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, equipeExcessao.getCamposObrigatorios());
			}
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IEquipeSevico#alterar(java.lang.Object)
	 */
	@Override
	public Retorno<String, Collection<String>> alterar(Equipe equipe) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		try {
			equipeControle.alterar(equipe);
			retorno.setSucesso(true);
		} catch (QuidExcessao e) {
			retorno = (Retorno<String, Collection<String>>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
			if(EquipeExcessao.class.isInstance(e)){
				EquipeExcessao equipeExcessao = (EquipeExcessao) e;
				retorno.adicionarParametro(Retorno.PARAMETRO_NAO_INFORMADO_INVALIDO, equipeExcessao.getCamposObrigatorios());
			}
		}
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IEquipeSevico#pequisarEquipes(java.lang.Object)
	 */
	public Retorno<String, Collection<Equipe>> pequisarEquipes(Equipe equipe){
		Retorno<String, Collection<Equipe>> retorno = new Retorno<String, Collection<Equipe>>();
		Collection<Equipe> equipes = this.equipeControle.pesquisarEquipe(equipe);
		if(equipes.isEmpty()){
			retorno.setSucesso(false);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, equipes);
			retorno.setMensagem("NENHUM USUARIO ENCONTRADO");
		}else{
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA,	equipes);
		}
		return retorno;
	}
	
	//*GETTERS AND SETTERS

	public IEquipeControle<Equipe, Long> getEquipeControle() {
		return equipeControle;
	}

	public void setEquipeControle(IEquipeControle<Equipe, Long> equipeControle) {
		this.equipeControle = equipeControle;
	}
	
	

}
