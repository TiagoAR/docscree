package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.quid.extensao.interfaces.IPersistivel;

/**
 * Compositor superior, contém métodos comum a todos os compositores
 * 
 * @author Diego
 * 
 * @param <SuperControle>
 *            Controle específico de cada compositor, deve herdar SuperControle
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class SuperCompositor<E extends SuperControle> extends
		GenericForwardComposer {

	/**
	 * Defaul Serial para o Composer
	 */
	private static final long serialVersionUID = 1L;

	public E gControle;
	private List<?> listaEntidade;

	protected Window modalSucesso;

	/**
	 * usado para pegar chamar o zk para passar os parametros da visão para o
	 * composer
	 */
	protected AnnotateDataBinder binder;
	protected Component component;
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zkoss.zk.ui.util.GenericForwardComposer#doAfterCompose(org.zkoss.
	 * zk.ui.Component)
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		processRecursive(comp, this);
		comp.setAttribute("controller", this, true);

		this.component = comp;

		this.binder = new AnnotateDataBinder(component);
		this.binder.setLoadOnSave(false);
		this.binder.loadAll();
	}

	protected void processRecursive(Component comp, Object composer) {
		Selectors.wireComponents(comp, composer, false);
		Selectors.wireEventListeners(comp, composer);

		List<Component> compList = comp.getChildren();
		for (Component vComp : compList) {
			if (vComp instanceof Window) {
				processRecursive(vComp, composer);
			}
		}
	}

	/**
	 * Método que cria nova instancia da entidade.
	 * 
	 * @return IPersistivel entidade
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public IPersistivel<?> novaEntidade() throws InstantiationException,
			IllegalAccessException {
		return (IPersistivel<?>) getTipoEntidade().newInstance();
	}

	public abstract Class getTipoEntidade();

	public abstract Object getPrimaryKey();

	public abstract void setPrimaryKey(Object primaryKey);

	/**
	 * Método que retorna o GenericoControle específico da entidade.
	 * 
	 * @return SuperControle controle
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	protected E getControle() {
		if (this.gControle == null) {
			ParameterizedTypeImpl classeRfe = (ParameterizedTypeImpl) this
					.getClass().getGenericSuperclass();
			Class controle = (Class) classeRfe.getActualTypeArguments()[0];
			try {
				this.gControle = (E) controle.newInstance();
			} catch (Exception e) {
				Messagebox.show("Erro ao criar controlador\nContate o Administrador do sistema", "Erro Grave", Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
			} 
		}
		return this.gControle;
	}

	/**
	 * Seta o contolador
	 * 
	 * @param SuperControle pControle
	 */
	protected void setControle(E pControle) {
		this.gControle = pControle;
	}

	/**
	 * @return List<?> o(a) listaEntidade
	 */
	public List<?> getListaEntidade() {
		return listaEntidade;
	}

	/**
	 * @param List
	 *            <?> o(a) listaEntidade a ser setado(a)
	 */
	public void setListaEntidade(List<?> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}

}
