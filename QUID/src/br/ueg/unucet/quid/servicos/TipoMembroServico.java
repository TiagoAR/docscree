package br.ueg.unucet.quid.servicos;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.TipoMembroExcessao;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;
import br.ueg.unucet.quid.interfaces.ITipoMembroControle;
import br.ueg.unucet.quid.interfaces.ITipoMembroServico;

@Service("TipoMembroServico")
public class TipoMembroServico extends GenericoServico<TipoMembro> implements ITipoMembroServico<TipoMembro>{
	
	@Autowired
	private ITipoMembroControle<TipoMembro, Long> controleTipoMembro;

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroServico#mapear(java.io.File[])
	 */
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroServico#mapear(java.io.File[])
	 */
	@Override
	public Retorno<File, String> mapear(File[] arquivos) {
		Retorno<File, String> retorno = new Retorno<File, String>();
		Map<File, String> arquivosMapeados = null;
		try {
			arquivosMapeados = controleTipoMembro.mapearTiposMembro(arquivos);
			retorno.setSucesso(controleTipoMembro.isSucesso());
			retorno.setParametros(arquivosMapeados);
		} catch (TipoMembroExcessao e) {
			retorno.setErro(e);
			retorno.setSucesso(false);
			retorno.setMensagem(e.getMessage());
			retorno.setTipoErro(TipoErroEnum.ERRO_FATAL);
		}
		return retorno;
	}
	
	public ITipoMembroControle<TipoMembro, Long> getControleTipoMembro() {
		return controleTipoMembro;
	}

	public void setControleTipoMembro(
			ITipoMembroControle<TipoMembro, Long> controleTipoMembro) {
		this.controleTipoMembro = controleTipoMembro;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroServico#listarTipoMembroModelo()
	 */
	@Override
	public Retorno<String, Collection<ITipoMembroModelo>> listarTipoMembroModelo() {
		Retorno<String, Collection<ITipoMembroModelo>> retorno = new Retorno<String, Collection<ITipoMembroModelo>>();
		Collection<ITipoMembroModelo> lista = controleTipoMembro.listaTipoMembroModelo();
		retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
		if(lista.isEmpty()){
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.INFORMATIVO);
			retorno.setMensagem(propertiesMensagensUtil.getValor("lista_vazia"));
		}else{
			retorno.setSucesso(true);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroServico#listarTipoMembroVisao()
	 */
	@Override
	public Retorno<String, Collection<ITipoMembroVisao>> listarTipoMembroVisao() {
		Retorno<String, Collection<ITipoMembroVisao>> retorno = new Retorno<String, Collection<ITipoMembroVisao>>();
		Collection<ITipoMembroVisao> lista = controleTipoMembro.listaTipoMembroVisao();
		retorno.adicionarParametro(Retorno.PARAMERTO_LISTA, lista);
		if(lista.isEmpty()){
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.INFORMATIVO);
			retorno.setMensagem(propertiesMensagensUtil.getValor("lista_vazia"));
		}else{
			retorno.setSucesso(true);
		}
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroServico#getTipoMembroVisao(br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo)
	 */
	@Override
	public Retorno<String, ITipoMembroVisao> getTipoMembroVisao(ITipoMembroModelo tipoMembroModelo) {
		Retorno<String, ITipoMembroVisao> retorno = new Retorno<String, ITipoMembroVisao>();
		ITipoMembroVisao tipoMembroVisao = this.controleTipoMembro.getTipoMembroVisao(tipoMembroModelo);
		retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, tipoMembroVisao);
		if(tipoMembroVisao == null){
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMensagensUtil.getValor("tipomembro_modelo_sem_representante_na_visao"));
		}else{
			retorno.setSucesso(true);
		}
		
		return retorno;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.ITipoMembroServico#getInstancia(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Retorno<String, ITipoMembroModelo> getInstancia(String nome, Integer versao) {
		Retorno<String, ITipoMembroModelo> retorno = new Retorno<String, ITipoMembroModelo>();
		ITipoMembroModelo tipoMembro = this.controleTipoMembro.getTipoMembroModelo(nome, versao);
		retorno.adicionarParametro(Retorno.PARAMETRO_NOVA_INSTANCIA, tipoMembro);
		retorno.setSucesso(true);
		return retorno;
	}

}
