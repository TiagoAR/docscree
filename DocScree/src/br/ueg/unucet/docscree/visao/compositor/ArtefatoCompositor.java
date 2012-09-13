package br.ueg.unucet.docscree.visao.compositor;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zul.Window;

import sun.awt.image.ToolkitImage;
import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroVisaoZK;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

@SuppressWarnings("rawtypes")
@Component
@Scope("session")
public class ArtefatoCompositor extends GenericoCompositor<ArtefatoControle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3331698085154478299L;

	private Window areaMontagemWindow = null;
	private SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado;

	@Override
	public Class getTipoEntidade() {
		return Artefato.class;
	}

	@Override
	protected void limparCampos() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void limparFiltros() {
		// TODO Auto-generated method stub

	}

	@Override
	public void acaoFiltrar() {
		// TODO Auto-generated method stub

	}

	public List<SuperTipoMembroVisaoZK> getListaTipoMembrosVisao() {
		return super.getControle().listarTipoMembrosVisao();
	}

	public void adicionarTipoMembro() {
		if (this.getTipoMembroVisaoSelecionado() != null) {
			org.zkoss.zk.ui.Component componente = (org.zkoss.zk.ui.Component) getTipoMembroVisaoSelecionado()
					.getVisaoVisualizacao();
			componente.setParent(getWindowArtefato());
			getWindowArtefato().appendChild(componente);
			super.binder.loadAll();
		}
	}

	public BufferedImage getConverterImagem(ITipoMembroVisao imagemIlustrativa) {
		@SuppressWarnings("unused")
		String teste = "teste";
		return ((ToolkitImage) ((ImageIcon) imagemIlustrativa
				.getImagemIlustrativa()).getImage()).getBufferedImage();
	}

	public Window getWindowArtefato() {
		if (this.areaMontagemWindow == null) {
			this.areaMontagemWindow = (Window) getComponent().getFellow(
					"windowArtefato");
		}
		return this.areaMontagemWindow;
	}

	/**
	 * @return SuperTipoMembroVisaoZK o(a) tipoMembroVisaoSelecionado
	 */
	public SuperTipoMembroVisaoZK getTipoMembroVisaoSelecionado() {
		return tipoMembroVisaoSelecionado;
	}

	/**
	 * @param SuperTipoMembroVisaoZK
	 *            o(a) tipoMembroVisaoSelecionado a ser setado(a)
	 */
	public void setTipoMembroVisaoSelecionado(
			SuperTipoMembroVisaoZK tipoMembroVisaoSelecionado) {
		this.tipoMembroVisaoSelecionado = tipoMembroVisaoSelecionado;
	}

}
