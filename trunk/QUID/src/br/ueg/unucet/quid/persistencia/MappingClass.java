package br.ueg.unucet.quid.persistencia;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.ueg.unucet.quid.anotations.NoAnotations;


/**
 * Classe responsavel por realizar o mapeamento de uma classe a partir de seus fields e os anotations sobre seus fields.
 * @author QUID
 *
 */
public class MappingClass implements Serializable{
	
	/**
	 * Classe que esta sendo mapeada.
	 */
	private Class<?> classMapping;
	/**
	 * Atributo que relaciona os nome dos fiels da classe juntamente com a classe de mapeamento.
	 */
	private Map<String, MappingField> listField;
	/**
	 * Atributo que identifica se uma classe possui atributos a serem mapeados.
	 */
	private boolean containsAnnotationsField;
	
	public MappingClass(Class<?> classMapping){
		setClassMapping(classMapping); 
	}
	
	private MappingClass(Class<?> classMapping, boolean containsAnnotationField){
		this.classMapping = classMapping;
		this.containsAnnotationsField = containsAnnotationField;
	}
	
	/**
	 * Metodo que chama o mapeamento dos atributos de uma classe de suas super classes.
	 */
	private void mapping(){
		listField = new HashMap<String, MappingField>();
		Class<?> aux = this.classMapping;
		do{
			mappingClass(aux);
		}while((aux = aux.getSuperclass()) != null);
	}
	
	/**
	 * Metodo que realiza o mapeamento atributos de uma classe.
	 * @param classMapping Classe que sera mapeada seus atributos.
	 */
	private void mappingClass(Class<?> classMapping){
		Field fields[] = classMapping.getDeclaredFields();
		for (Field field : fields) {
			if(!field.getName().equalsIgnoreCase("serialVersionUID"))
			listField.put(field.getName(), new MappingField(field, this.containsAnnotationsField, classMapping));
		}
	}
	
	/**
	 * Metodo que chama o carregamento dos anotations escritos sobre a classe de mapeamento.
	 */
	protected void loadAnnotationsClass(){
		loadContainsAnnotationsField();
	}
	
	/**
	 * Metodo que verifica se a classe contem o anotation NoAnotations. Caso a classe possuia atributo containsAnnotation e marcado como false, caso contrario true.
	 */
	protected void loadContainsAnnotationsField(){
		NoAnotations noAnotations = this.classMapping.getAnnotation(NoAnotations.class);
		this.containsAnnotationsField = (noAnotations == null);
	}
	
	public Class<?> getClassMapping() {
		return classMapping;
	}
	
	/**
	 * Metodo que seta uma classe de mapeamento. Ao se setar gera-se um novo mapeamento da classe.
	 * @param classMapping Classe que sera setada dentro do mapeamento.
	 */
	public void setClassMapping(Class<?> classMapping) {
		this.classMapping = classMapping;
		loadAnnotationsClass();
		mapping();
	}
	
	public Collection<MappingField> getListMappingField(){
		return this.listField.values();
	}
	
	/**
	 * Metodo que retorna um mapeamento de um atributo (MappingField) a partir de seu nome.
	 * @param name Nome do atributo que esta sendo procurado.
	 * @return Mapping field do atributo procurado.
	 */
	public MappingField getMapeamentoAtributoPorNomeField(String name){
		MappingField mappingField = null;
		if(this.listField.containsKey(name))
			mappingField = this.listField.get(name);
		return mappingField;
	}
	public void setListField(Map<String, MappingField> listField){
		this.listField = listField;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public MappingClass clone(){
		MappingClass mapping = new MappingClass(this.classMapping, this.containsAnnotationsField);
		mapping.setListField(cloneListField());
		return mapping;
	}
	
	/**
	 * Metodo responsavel por clonar a lista de mapeamento dos atributos da classe.
	 * @return Map da clonagem dos MappingFields
	 */
	public Map<String, MappingField> cloneListField(){
		Map<String, MappingField> list = new HashMap<String, MappingField>();
		for (String field : this.listField.keySet()) {
			list.put(field, this.listField.get(field).clone());
		}
		return list;
	}
	
	
	
}
