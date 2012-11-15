package br.ueg.unucet.quid.extensao.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.extensao.interfaces.IParametro;

public interface IContemParametro {
	
	Collection<IParametro<?>> getListaParametros();
	void setListaParametros(Collection<IParametro<?>> parametros);

}
