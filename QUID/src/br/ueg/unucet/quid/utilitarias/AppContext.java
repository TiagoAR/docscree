package br.ueg.unucet.quid.utilitarias;

import org.springframework.context.ApplicationContext;

public class AppContext { 
	 
    private static ApplicationContext ctx; 
 
    /**
     * Injected from the class "ApplicationContextProvider" which is automatically
     * loaded during Spring-Initialization.
     */ 
    public static void setApplicationContext(ApplicationContext applicationContext) { 
        ctx = applicationContext; 
    }
    
    /**
     * Metodo que retorna a instancia do ApplicationContext do Spring.
     * @return
     */
    public static ApplicationContext getApplicationContext(){
    	return ctx;
    }
}