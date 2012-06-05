package br.ueg.unucet.docscree.visao.compositor;

import br.ueg.unucet.docscree.controladores.GenericoControle;

/**
 * Compositor Genérico, contém métodos comuns a todos compositores que são caso de uso tipo CRUD.
 * 
 * @author Diego
 *
 * @param <E>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class GenericoCompositor<E extends GenericoControle> extends SuperCompositor<E> {

	/**
	 * Default serial do compositor
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Método que retorna chave primária
	 * 
	 * @return Object PK
	 */
	public abstract Object getPrimaryKey();

	/**
	 * Método que seta a chave primária
	 * 
	 * @param primaryKey
	 */
	public abstract void setPrimaryKey(Object primaryKey);

	/**
	 * Método que executa a ação de Salvar Usuário e mostra mensagem de sucesso ou erro após ação
	 */
	public void acaoSalvar() {
		try {
			boolean resultado = super.getControle().fazerAcao("salvar",
					(SuperCompositor) this);
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que executa a ação de Listar Usuário e mostra mensagem de sucesso ou erro após ação.
	 */
	public void acaoListar() {
		try {
			boolean resultado = super.getControle().fazerAcao("listar", (SuperCompositor) this);
			this.setListaEntidade(super.getControle().getLista());
			super.mostrarMensagem(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
