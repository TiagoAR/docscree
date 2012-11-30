package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Usuario;

@org.springframework.stereotype.Component
@Scope("session")
public class ModalArtefatoPreenchidoCompositor extends
		SuperCompositor<ArtefatoControle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5181980815085895670L;
	
	private String nomeSelecionar = "Selecione o ArtefatoModelo";
	
	private Artefato artefatoAAbrir;
	private ArtefatoPreenchido artefatoPreenchidoAAbrir;
	
	private List<Artefato> listaArtefatoModelo;
	private List<ArtefatoPreenchido> listaArtefatoPreenchido;
	
	private Grid gridArtefatoModelo;
	private Grid gridArtefatoPreenchido;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void acaoAbrirPreencherArtefato() {
		boolean retorno = false;
		if (getProjetoSessao() != null) {
			try {
				//Necessário setar o usuário por que quando se chama fazerAcao pega o Usuario via Singleton, se houver 
				//modificação na equipe do usuário não fará efeito dentro do método, por isso deve ser atualizado o usuarioLogado
				super.getControle().setUsuarioLogado((Usuario) super.getUsuarioSessao());
				retorno = super.getControle().fazerAcao("preencherArtefato", (SuperCompositor) this);
				if (retorno) {
					org.zkoss.zk.ui.Component comp = Executions.createComponents(
							"/componentes/modalPreencherArtefato.zul", null, null);
	
					if (comp instanceof Window) {
						((Window) comp).doModal();
					}
				} else {
					super.mostrarMensagem(retorno);
				}
			} catch (Exception e) {
				super.getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
				super.getControle().getMensagens().getListaMensagens().add("Não foi possível executar a ação!");
				super.mostrarMensagem(retorno);
			}
		} else {
			super.getControle().setMensagens(new Mensagens());
			super.getControle().getMensagens().getListaMensagens().add("É necessário Abrir um Projeto para acessar a funcionalidade!");
			super.mostrarMensagem(retorno);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperCompositor#gerarWindowMensagem()
	 */
	@Override
	protected Window gerarWindowMensagem() {
		org.zkoss.zk.ui.Component componenteInicial = super.getComponent();
		while (super.getComponent() != null && !(super.getComponent() instanceof Div)) {
			super.setComponent(super.getComponent().getParent());
		}
		if (super.getComponent() == null) {
			super.setComponent(componenteInicial);
		}
		return super.gerarWindowMensagem();
	}
	
	public void abrirArtefatoModelo() {
		Radio radioModelo = (Radio) getComponent().getFellow("radioArtefatoModelo");
		if (radioModelo.isChecked()) {
			Executions.getCurrent().getSession().setAttribute("ArtefatoAbrir", getArtefatoAAbrir());
		} else {
			Executions.getCurrent().getSession().setAttribute("ArtefatoPreenchidoAbrir", getArtefatoPreenchidoAAbrir());
		}
		if (getArtefatoAAbrir() != null || getArtefatoPreenchidoAAbrir() != null) {
			setArtefatoAAbrir(null);
			setArtefatoPreenchidoAAbrir(null);
			Executions.sendRedirect("/pages/preencher-artefato.zul");
		} else {
			getControle().setMensagens(new Mensagens());
			getControle().getMensagens().getListaMensagens().add("É necessário escolher um Artefato para preencher!");
			mostrarMensagem(false);
		}
		anularComponentes();
	}
	
	private void anularComponentes() {
		this.listaArtefatoModelo = null;
		this.listaArtefatoPreenchido = null;
		this.gridArtefatoModelo = null;
		this.gridArtefatoPreenchido = null;
	}
	
	public void checkarArtefatoModelo() {
		getGridArtefatoModelo().setVisible(true);
		getGridArtefatoPreenchido().setVisible(false);
		setNomeSelecionar("Selecione o ArtefatoModelo");
		super.binder.loadAll();
	}
	
	public void checkarArtefatoPreenchido() {
		getGridArtefatoModelo().setVisible(false);
		getGridArtefatoPreenchido().setVisible(true);
		setNomeSelecionar("Selecione o Artefato Preenchido");
		super.binder.loadAll();
	}
	
	public void acaoFecharModal() {
		anularComponentes();
		getComponent().detach();
	}
	
	public List<Artefato> getListaArtefatoModelo() {
		if (this.listaArtefatoModelo == null) {
			this.listaArtefatoModelo = getControle().listarArtefatosModeloPorProjeto((Projeto) getProjetoSessao());
		}
		return this.listaArtefatoModelo;
	}

	public List<ArtefatoPreenchido> getListaArtefatoPreenchido() {
		if (this.listaArtefatoPreenchido == null) {
			this.listaArtefatoPreenchido = getControle().listarArtefatosPreenchidosProjeto((Projeto)getProjetoSessao());
		}
		return this.listaArtefatoPreenchido;
	}

	/**
	 * @return Grid o(a) gridArtefatoModelo
	 */
	public Grid getGridArtefatoModelo() {
		if (this.gridArtefatoModelo == null) {
			this.gridArtefatoModelo = (Grid) getComponent().getFellow("gridArtefatoModelo");
		}
		return this.gridArtefatoModelo;
	}

	/**
	 * @return Grid o(a) gridArtefatoPreenchido
	 */
	public Grid getGridArtefatoPreenchido() {
		if (this.gridArtefatoPreenchido == null) {
			this.gridArtefatoPreenchido = (Grid) getComponent().getFellow("gridArtefatoPreenchido");
		}
		return gridArtefatoPreenchido;
	}

	/**
	 * @return Artefato o(a) artefatoAAbrir
	 */
	public synchronized Artefato getArtefatoAAbrir() {
		return artefatoAAbrir;
	}

	/**
	 * @param artefatoAAbrir o(a) artefatoAAbrir a ser setado(a)
	 */
	public synchronized void setArtefatoAAbrir(Artefato artefatoAAbrir) {
		this.artefatoAAbrir = artefatoAAbrir;
	}

	/**
	 * @return ArtefatoPreenchido o(a) artefatoPreenchidoAAbrir
	 */
	public ArtefatoPreenchido getArtefatoPreenchidoAAbrir() {
		return artefatoPreenchidoAAbrir;
	}

	/**
	 * @param artefatoPreenchidoAAbrir o(a) artefatoPreenchidoAAbrir a ser setado(a)
	 */
	public void setArtefatoPreenchidoAAbrir(ArtefatoPreenchido artefatoPreenchidoAAbrir) {
		this.artefatoPreenchidoAAbrir = artefatoPreenchidoAAbrir;
	}

	/**
	 * @return String o(a) nomeSelecionar
	 */
	public String getNomeSelecionar() {
		return nomeSelecionar;
	}

	/**
	 * @param nomeSelecionar o(a) nomeSelecionar a ser setado(a)
	 */
	public void setNomeSelecionar(String nomeSelecionar) {
		this.nomeSelecionar = nomeSelecionar;
	}

}
