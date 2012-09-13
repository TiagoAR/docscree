package br.ueg.unucet.docscree.controladores;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.docscree.visao.compositor.TipoMembroCompositor;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.TipoMembro;

@SuppressWarnings("unchecked")
public class TipoMembroControle extends GenericoControle<TipoMembro> {

	@Override
	public boolean acaoSalvar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acaoExcluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Retorno<String, Collection<TipoMembro>> executarListagem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean acaoMapearTipoMembro() {
		Map<File, String> tipoMembros = (Map<File, String>) getMapaAtributos().get("tipoMembros");
		if (!tipoMembros.isEmpty()) {
			File[] files = new File[tipoMembros.size()];
			int i = 0;
			for (Iterator<File> iterator = tipoMembros.keySet().iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();
				files[i] = file;
				i++;
			}
			Retorno<File, String> mapearArquivosTipoMembro = super.getFramework().mapearArquivosTipoMembro(files);
			((TipoMembroCompositor)super.getVisao()).setTipoMembros(mapearArquivosTipoMembro.getParametros());
			return true;
		} else {
			super.getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			super.getMensagens().getListaMensagens().add("É necessário selecionar TipoMembros para mapeá-los");
		}
		return false;
	}

}
