package br.ueg.unucet.plugin.combobox1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

@SuppressWarnings("serial")
public class ComboBox extends SuperTipoMembroModelo implements
		ITipoMembroModelo {
	/**
	 * 
	 */
	public static final String LISTA_VALORES = "LISTA_VALORES";
	
	public ComboBox(){
		setNome("combobox");
		setDescricao("Caixa de selecao de opcao");
		setVersao(1);
		setRevisao(2);
	}

	@Override
	public Integer getTipoDadoPersistencia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEstatico() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMascara() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DominioEntradaEnum getDominio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValorValido(Object valor) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isIndexavel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParametrosValidos() {
		System.out.println("isParametrosValidos()");
		boolean valid = false;
		@SuppressWarnings("rawtypes")
		Collection params = this.getListaParametros();
		System.out.println("LenghtParametors:"+params.size());
		System.out.println("params:"+params);
		@SuppressWarnings("unchecked")
		Iterator<IParametro<String>> iterator = params.iterator();
		while (iterator.hasNext()) {			
			IParametro<String> param = iterator.next();
			System.out.println(param);
			if (param.getNome().equals(ComboBox.LISTA_VALORES.toString())) {
				if (param.getValor() != null) {
					String[] valores = param.getValor().split(",");
					if ( valores.length>1) {
						System.out.println("valores:"+valores);
						valid=true;						
					}
				}
			}
		}
		return valid;
	}

	@Override
	public Collection<String> getNomesParametrosInvalidos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IParametro<?>> getParametrosAdicionais() {
		Parametro<String> parametro = new Parametro<String>(String.class);
		parametro.setNome(LISTA_VALORES);
		parametro.setRotulo("Lista de valores(,)");
		parametro.setDominioEntrada(DominioEntradaEnum.COMBOALFANUMERICO);
		parametro.setObrigatorio(true);
		
		Collection<IParametro<?>> parametrosCombo = new ArrayList<IParametro<?>>();
		parametrosCombo.add(parametro);
		return parametrosCombo;
	}

}
