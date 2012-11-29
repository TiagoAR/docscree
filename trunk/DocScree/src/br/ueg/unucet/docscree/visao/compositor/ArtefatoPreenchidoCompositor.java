package br.ueg.unucet.docscree.visao.compositor;


import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.annotation.Scope;

import br.ueg.unucet.docscree.controladores.ArtefatoControle;
import br.ueg.unucet.docscree.modelo.MembroDocScree;
import br.ueg.unucet.docscree.modelo.Mensagens;
import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;
import br.ueg.unucet.quid.extensao.dominios.Membro;

@SuppressWarnings({ "rawtypes", "unchecked" })
@org.springframework.stereotype.Component
@Scope("session")
public class ArtefatoPreenchidoCompositor extends
		SuperArtefatoCompositor<ArtefatoControle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7603950653212496809L;

	/**
	 * @see br.ueg.unucet.docscree.visao.compositor.SuperArtefatoCompositor#carregarArtefato()
	 */
	@Override
	public boolean carregarArtefato() {
		if( !super.carregarArtefato()) {
			getControle().setMensagens(new Mensagens());
			getControle().getMensagens().getListaMensagens().add("É necessário escolher um ArtefatoModelo para preencher!");
			getControle().getMensagens().setTipoMensagem(TipoMensagem.ERRO);
			mostrarMensagem(false);
		}
		return true;
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

	@Override
	protected void inicializarTelasMapeadores() {
		this.areaWindowArtefato = null;
		setMapaMembrosAdicionados(new HashMap<String, MembroDocScree>());
	}

	@Override
	protected void lancarMembroAVisualizacao(boolean novo,
			boolean abrindoArtefato) {
		adicionarComponenteAoArtefato(getTipoMembroVisaoSelecionado(), getIdMembro(), novo, abrindoArtefato);
		setTipoMembroVisaoSelecionado(null);
		super.binder.loadAll();
	}
	
	public void acaoMapearArtefato() {
		super.binder.saveAll();
		boolean valido = true;
		setMembros(new ArrayList<Membro>());
		for (MembroDocScree membroDocScree : getMapaMembrosAdicionados().values()) {
			Object valorVisualizacao = membroDocScree.getTipoMembroVisao().getValorVisualizacao(getComponentePorId(membroDocScree.getIdComponente(), getWindowArtefato()));
			if (membroDocScree.getTipoMembroVisao().isEntradaValida(valorVisualizacao)) {
				membroDocScree.getTipoMembroVisao().getMembro().getTipoMembroModelo().setValor(valorVisualizacao);
				getMembros().add(membroDocScree.getTipoMembroVisao().getMembro());
			} else {
				valido = false;
				//TODO informar entrada inválida -- Isso é serviço de validação...
				break;
			}
		}
		if (valido) {
			try {
				getControle().fazerAcao("chamarServicoPersistencia", (SuperCompositor) this);
			} catch (Exception e) {
				
			}
		}
	}

}
