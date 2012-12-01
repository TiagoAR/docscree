package br.ueg.unucet.plugin.comboboxvisao1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.zkoss.zkplus.databind.BindingListModelListModel;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Textbox;

import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Interface do ComboBox, contém os componentes que serão exibidos
 * no Descritor de Tela e como setar e obter seu valor.
 * 
 * @author Diego
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ComboBoxInterface implements IComponenteInterface {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 344518551310671764L;
	/**
	 * Componente de Preenchimento do ComboBox
	 */
	private org.zkoss.zul.Combobox combobox;
	/**
	 * Componente de Visualização do ComboBox
	 */
	private Label label;
	
	/**
	 * Construtor DEFAULT
	 */
	public ComboBoxInterface() {
		this.combobox = new org.zkoss.zul.Combobox();
		this.label = new Label("");
	}
	
	/**
	 * Método que retorna o componente de preenchimento
	 * 
	 * @return Textbox componente
	 */
	public Textbox getPreenchimento(ComboBox combo) {
		Collection<String> comboParams = new ArrayList<String>();
		comboParams.add("Selecione");
		System.out.println("GetPreenchimento:"+comboParams.size());
		Collection params = combo.getListaParametros();
		Iterator<IParametro<String>> iterator = params.iterator();
		while (iterator.hasNext()) {
			IParametro<String> param = iterator.next();
			if (param.getNome().equals(ComboBox.LISTA_VALORES.toString())) {
				if (param.getValor() != null) {
					String[] valores = param.getValor().split(",");
					for (int i = 0; i < valores.length; i++) {
						comboParams.add(valores[i]);
						System.out.println("Parametro("+i+"):"+valores[i]);
						//this.combobox.getItems().add(new Comboitem(valores[i]));
					}
				}
			}
		}
		this.combobox.setModel(new BindingListModelListModel<>(new ListModelArray<>(comboParams.toArray())));
		return this.combobox;
	}
	
	/**
	 * Método que retorna o componente de visualização
	 * 
	 * @return Label componente
	 */
	public Label getVisualizacao() {
		return this.label;
	}
	
	@Override
	public void setValor(Object valor) {
		if(valor!=null){
			this.combobox.setText(valor.toString());
			this.label.setValue(valor.toString());
		}
	}
	
	@Override
	public Object getValor() {
		return this.combobox.getText();
	}

	@Override
	public Object getValor(Object componente) {
		return ((org.zkoss.zul.Combobox)componente).getText();
	}
}