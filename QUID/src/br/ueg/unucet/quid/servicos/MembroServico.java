package br.ueg.unucet.quid.servicos;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.MembroFramework;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.MembroExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.interfaces.IMembroFrameworkControle;
import br.ueg.unucet.quid.interfaces.IMembroServico;

@Service("MembroServico")
public class MembroServico extends GenericoServico<MembroFramework> implements IMembroServico<MembroFramework>{

	@Autowired
	private IMembroFrameworkControle<MembroFramework, Long> membroControle;
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroServico#inserir(br.ueg.unucet.quid.extensao.dominios.Membro)
	 */
	@Override
	public Retorno<Object, Object> inserir(Membro membro) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			membroControle.inserir(membro);
			retorno.setSucesso(true);
		} catch (MembroExcessao e) {
			retorno = (Retorno<Object, Object>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
		}
		return retorno;
	}
	
	@Override
	public Retorno<Object, Object> alterar(Membro membro) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			membroControle.alterar(membro);
			retorno.setSucesso(true);
		} catch (MembroExcessao e) {
			retorno = (Retorno<Object, Object>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
		}
		return retorno;
	}
	
	@Override
	public Retorno<Object, Object> remover(Membro membro) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			membroControle.remover(membro);
			retorno.setSucesso(true);
		} catch (MembroExcessao e) {
			retorno = (Retorno<Object, Object>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroServico#pesquisarMembro(java.lang.String, br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	@Override
	public Retorno<String, Collection<Membro>> pesquisarMembro(String nome, ITipoMembroModelo tipoMembroModelo) {
		Retorno<String, Collection<Membro>> retorno = new Retorno<String, Collection<Membro>>();
		try {
			Collection<Membro> lista = membroControle.pesquisar(nome, tipoMembroModelo);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
			if(lista.isEmpty()){
				retorno.setSucesso(false);
				retorno.setMensagem(propertiesMensagensUtil.getValor("lista_vazia"));
			}else{
				retorno.setSucesso(true);
			}
		} catch (MembroExcessao e) {
			retorno = (Retorno<String, Collection<Membro>>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA,new ArrayList<Membro>());
		}
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IMembroServico#pesquisarMembro(br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	@Override
	public Retorno<String, Collection<Membro>> pesquisarMembro(ITipoMembroModelo tipoMembroModelo) {
		Retorno<String, Collection<Membro>> retorno = new Retorno<String, Collection<Membro>>();
		try {
			Collection<Membro> lista = membroControle.pesquisar(tipoMembroModelo);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
			if(lista.isEmpty()){
				retorno.setSucesso(false);
				retorno.setMensagem(propertiesMensagensUtil.getValor("lista_vazia"));
			}else{
				retorno.setSucesso(true);
			}
		} catch (MembroExcessao e) {
			retorno = (Retorno<String, Collection<Membro>>) construirRetornoErro(e, TipoErroEnum.ERRO_SIMPLES, retorno);
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA,new ArrayList<Membro>());
		}
		return retorno;
	}
	
	//GETTERS AND SETTERS

	public IMembroFrameworkControle<MembroFramework, Long> getMembroControle() {
		return membroControle;
	}

	public void setMembroControle(IMembroFrameworkControle<MembroFramework, Long> membroControle) {
		this.membroControle = membroControle;
	}

	
	

}
