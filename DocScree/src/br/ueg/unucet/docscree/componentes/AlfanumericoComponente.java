package br.ueg.unucet.docscree.componentes;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Componente para exibição e preenchimento de parâmetros com Domínio do Tipo Alfanumérico
 * 
 * @author Diego
 *
 */
public class AlfanumericoComponente extends CaracteresComponente {
	
	/**
	 * String válidas que podem ser inseridas no componente
	 */
	private static final String CHARVALIDOS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.,-";

	/**
	 * @see br.ueg.unucet.docscree.componentes.CaracteresComponente#getComponente(br.ueg.unucet.quid.extensao.interfaces.IParametro, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HtmlBasedComponent getComponente(IParametro<?> parametro, String width) {
		Textbox textbox = (Textbox) super.getComponente(parametro, width);
		textbox.addEventListener("onChanging", new EventListener() {

			/**
			 * Método que executa o evento de verificar se o caracter digitado é válido
			 * quando é alterado o valor do Textbox (onChanging)
			 * 
			 * @param event
			 * @throws Exception
			 */
			@Override
			public void onEvent(Event event) throws Exception {
				String string = ((Textbox) event.getTarget()).getText();
				StringBuilder novaPalavra = new StringBuilder();
				for (char caracter : string.toCharArray()) {
					String caracterString = String.valueOf(caracter);
					if (CHARVALIDOS.contains(caracterString)) {
						novaPalavra.append(caracterString);
					}
				}
				((Textbox) event.getTarget()).setText(novaPalavra.toString());
			}
			
		});
		return textbox;
	}
	
}
