package br.ueg.unucet.quid.utilitarias;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Classe responsavel por ser o provedor do ApplicationContext dentro do framework.
 * @author QUID
 *
 */
public class ProvedorApplicationContext implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		AppContext.setApplicationContext(arg0);

	}

}
