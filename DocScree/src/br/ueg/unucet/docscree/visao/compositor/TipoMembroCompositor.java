package br.ueg.unucet.docscree.visao.compositor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;

import br.ueg.unucet.docscree.anotacao.AtributoVisao;
import br.ueg.unucet.docscree.controladores.TipoMembroControle;
import br.ueg.unucet.docscree.modelo.ArquivoCarregado;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.UploadArquivo;

/**
 * Compositor da tela de upload de TipoMembro
 * 
 * @author Diego
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
@Scope("session")
public class TipoMembroCompositor extends
		SuperCompositor<TipoMembroControle> {

	/**
	 * DEFAULT SERIAL ID
	 */
	private static final long serialVersionUID = 955019688639995178L;
	private static final String pendente = "Pendente!";
	
	/**
	 * Mapa de arquivos a serem salvos no framework
	 */
	@AtributoVisao(isCampoEntidade= false, nome="tipoMembros")
	private Map<File, String> tipoMembros = new HashMap<File, String>();
	
	/**
	 * Arquivo selecionado para ser removido
	 */
	private ArquivoCarregado arquivoSelecionado;

	/**
	 * Efetua o upload do arquivo para o servidor
	 * @param event
	 */
	public void uploadTipoMembro(UploadEvent event) {
		try {
			String pasta = UploadArquivo.criarDiretorio(((ServletContext)Executions.getCurrent().getDesktop().getWebApp().getServletContext()).getRealPath("/"), "temp");
			Media[] medias = event.getMedias();
			boolean resultado = true;
			super.getControle().setMensagens(new Mensagens());
			for (Media media : medias) {
				String mediaName = media.getName();
				if (mediaName.substring(mediaName.length() - 3, mediaName.length()).equalsIgnoreCase("jar")) {
					String arquivo = UploadArquivo.adicionarBarraFinalString(pasta)
							+  media.getName();
					if (arquivo != null) {
						FileOutputStream fos = new FileOutputStream(arquivo);
						fos.write(media.getByteData());
						fos.flush();
						fos.close();
						getTipoMembros().put(new File(arquivo), pendente);
					}
				} else {
					resultado = false;
					super.getControle().getMensagens().getListaMensagens().add("O " + mediaName + " não é um arquivo .jar");
				}
			}
			super.mostrarMensagem(resultado);
			super.binder.loadAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mapea a lista de arquivos de TipoMembro para o framework
	 */
	public void mapearTipoMembro() {
		try {
			boolean fazerAcao = getControle().fazerAcao("mapearTipoMembro", (SuperCompositor) this);
			super.mostrarMensagem(fazerAcao);
			super.binder.loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove o arquivo do TipoMembro selecionado
	 */
	public void removerTipoMembro() {
		try {
			super.binder.saveAll();
			getTipoMembros().remove(getArquivoSelecionado().getFile());
			super.binder.loadAll();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Cria lista dos arquivos a serem mapeados
	 * @return arquivoCarregados lista dos ArquivosCarregados
	 */
	public List<ArquivoCarregado> getArquivosCarregados() {
		List<ArquivoCarregado> arquivoCarregados = new ArrayList<ArquivoCarregado>();
		for (File arquivo : getTipoMembros().keySet()) {
			ArquivoCarregado arquivoCarregado = new ArquivoCarregado();
			arquivoCarregado.setFile(arquivo);
			arquivoCarregado.setNomeArquivo(arquivo.getName());
			arquivoCarregado.setSituacao(getTipoMembros().get(arquivo));
			arquivoCarregados.add(arquivoCarregado);
		}
		return arquivoCarregados;
	}
	
	/**
	 * Cancela ação de mapeamento do TipoMembro
	 */
	public void acaoCancelar() {
		setTipoMembros(new HashMap<File, String>());
		super.binder.loadAll();
	}

	/**
	 * @return Map<File,String> o(a) tipoMembros
	 */
	public Map<File, String> getTipoMembros() {
		return tipoMembros;
	}

	/**
	 * @param tipoMembros o(a) tipoMembros a ser setado(a)
	 */
	public void setTipoMembros(Map<File, String> tipoMembros) {
		this.tipoMembros = tipoMembros;
	}

	/**
	 * @return ArquivoCarregado o(a) arquivoSelecionado
	 */
	public ArquivoCarregado getArquivoSelecionado() {
		return arquivoSelecionado;
	}

	/**
	 * @param arquivoSelecionado o(a) arquivoSelecionado a ser setado(a)
	 */
	public void setArquivoSelecionado(ArquivoCarregado arquivoSelecionado) {
		this.arquivoSelecionado = arquivoSelecionado;
	}

}
