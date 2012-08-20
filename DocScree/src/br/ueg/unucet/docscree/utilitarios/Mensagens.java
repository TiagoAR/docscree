package br.ueg.unucet.docscree.utilitarios;

import java.util.ArrayList;
import java.util.List;

import br.ueg.unucet.docscree.utilitarios.enumerador.TipoMensagem;

/**
 * Classe POJO que representa as mensagem, é onde definido o tipo da mensagem e
 * contém a String da mensagem.
 * 
 * @author Diego
 * 
 */
public class Mensagens {

	/**
	 * Lista de mensagens geradas durante a ação.
	 */
	private List<String> listaMensagens;
	/**
	 * Define qual é o tipo principal de mensagens contidas.
	 */
	private TipoMensagem tipoMensagem;
	
	/**
	 * Construtor que inicializa as variáveis.
	 */
	public Mensagens() {
		this.listaMensagens = new ArrayList<String>();
		this.tipoMensagem = TipoMensagem.ATENCAO;
	}

	/**
	 * @return o(a) listaMensagens
	 */
	public List<String> getListaMensagens() {
		return listaMensagens;
	}

	/**
	 * @param listaMensagens o(a) listaMensagens a ser setado(a)
	 */
	public void setListaMensagens(List<String> listaMensagens) {
		this.listaMensagens = listaMensagens;
	}

	/**
	 * @return o(a) tipoMensagem
	 */
	public TipoMensagem getTipoMensagem() {
		return tipoMensagem;
	}

	/**
	 * @param tipoMensagem
	 *            o(a) tipoMensagem a ser setado(a)
	 */
	public void setTipoMensagem(TipoMensagem tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}

}
