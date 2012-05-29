package br.ueg.unucet.quid.persistencia;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**Conteiner de mapeamento de classes, feito para realizar o mapeamento das classes
 * e armazenar em um cach para utilizacao futura
 * @author 
 *
 */
public class ContainerMapeamentoClasse implements Serializable{
	
	private static ContainerMapeamentoClasse mapeadorClasse;
	
	private Map<Class<?>, MappingClass> mapemento;
	
	private ContainerMapeamentoClasse(){
		mapemento = new HashMap<Class<?>, MappingClass>();
	}
	
	public static ContainerMapeamentoClasse getInstancia(){
		if(mapeadorClasse == null)
				mapeadorClasse = new ContainerMapeamentoClasse();
		return mapeadorClasse; 
	}
	
	/**Retorna um mapeamento de uma classe
	 * @param classeAMapear Classe a ser mapeada
	 * @return Mapeamento da classe
	 */
	public MappingClass getMapeamentoClasse(Class<?> classeAMapear){
		if(! mapemento.containsKey(classeAMapear))
			 novoMapeamento(classeAMapear);
		return mapemento.get(classeAMapear).clone();
	}
	
	/**
	 * Metodo que mapeia uma nova classe para o conteiner de mapeamento.
	 * @param classAMapear
	 */
	private void novoMapeamento(Class<?> classAMapear){
		mapemento.put(classAMapear,new MappingClass(classAMapear));
	}
	

}
