package br.ueg.unucet.quid.extensao.utilitarias;

import java.util.Date;
import java.util.List;

import br.ueg.unucet.quid.extensao.interfaces.IListavel;

/**
 * SuperClasse que implementa as operacoes basicas sobre a interface IListavel.
 * @author QUID
 *
 * @param <T>Classe que sera listada.
 */
public abstract class SuperListavelGenerico<T extends Object> implements IListavel<T> {

	/**
	 * Identifica se a lista armazenada precisa ser renovada.
	 */
	private boolean old;
	/**
	 * Controlador do tempo.
	 */
	private long time; 
	/**
	 * Lista dos objetos.
	 */
	private List<T> list;
	/**
	 * Maximo de tempo de armazenamento de uma lista sem ela ser considerada velha.
	 */
	private static final long MAXTIME = 7000;

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.interfaces.IListavel#isOld()
	 */
	public boolean isOld() {
		return (list == null) || old || compareTime();
	}

	/**
	 * Metodo que define se existe uma lista armazenada em cache.
	 * @return
	 */
	private boolean haveList() {
		return list == null;
	}

	/**
	 * Metodo que compara o tempo corrente com o tempo em que foi feita a ultima renovacao da lista de objetos
	 * para verificar se a lista necessita ser renovada.
	 * @return True caso a diferenca de tempo entre o atributo time e o tempo currente do sistema for maior que o MAXTIME,
	 * false caso contrario.
	 */
	private boolean compareTime() {
		long dataCorrent = (new Date()).getTime();
		return ((dataCorrent - time) > MAXTIME);
	}

	/**
	 * Metodo que realiza a renovacao da lista de objetos e reseta o tempo de busca da lista.
	 */
	private void reNew() {
		this.list = getList();
		this.old = false;
		this.time = (new Date()).getTime();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.interfaces.IListavel#getComboList()
	 */
	public List<T> getComboList() {
		if (haveList() || isOld())
			reNew();
		return this.list;
	}

	protected abstract List<T> getList();

}
