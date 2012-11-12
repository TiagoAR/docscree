package br.ueg.unucet.docscree.controladores;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.docscree.visao.compositor.TipoMembroCompositor;
import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Controlador específico do TipoMembro, faz upload de TipoMembros
 * 
 * @author Diego
 *
 */
@SuppressWarnings("unchecked")
public class TipoMembroControle extends SuperControle {

	/**
	 * Método que mapeia arquivo jar do TipoMembro ao framework
	 * 
	 * @return
	 */
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
