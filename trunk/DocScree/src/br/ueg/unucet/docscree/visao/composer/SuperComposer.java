package br.ueg.unucet.docscree.visao.composer;

import java.lang.reflect.Field;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

import br.ueg.unucet.docscree.controladores.SuperControle;
import br.ueg.unucet.quid.extensao.interfaces.IPersistivel;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class SuperComposer<E extends SuperControle> extends
		GenericForwardComposer {

	/**
	 * Defaul Serial para o Composer
	 */
	private static final long serialVersionUID = 1L;

	private E gControle;
	private List<?> listaEntidade;

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

	public IPersistivel<?> novaEntidade() throws InstantiationException,
			IllegalAccessException {
		return (IPersistivel<?>) getTipoEntidade().newInstance();
	}

	public abstract Class getTipoEntidade();

	public abstract Object getPrimaryKey();

	public abstract void setPrimaryKey(Object primaryKey);

	protected E getControle() throws InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		if (this.gControle == null) {
			gControle = (E) getTipoControle().newInstance();
		}
		return gControle;
	}

	protected void setControle(E pControle) {
		this.gControle = pControle;
	}

	public Class getTipoControle() throws NoSuchFieldException,
			SecurityException {
		Class classeRfe = this.getClass();
		Field field = classeRfe.getField("gControle");
		return field.getType();
	}

	public List<?> getListaEntidade() {
		return listaEntidade;
	}

	public void setListaEntidade(List<?> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}

}
