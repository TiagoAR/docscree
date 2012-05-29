package br.ueg.unucet.quid.persistencia;


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsavel por mapear um atributo de uma classe.
 * @author QUID
 *
 */
public class MappingField implements Serializable{
	
	/**
	 * Atributo que define se o atributo contem anotations mapeaveis.
	 */
	private boolean containsAnnotations;
	/**
	 * Atributo que define o mapeamento dos anotations dos atributos da classe.
	 */
	private Map<Class<?>, Annotation> annotations;
	/**
	 * Atributo que define o atributo pai do atributo que esta sendo mapeado. Esse atributo pai e necessario para o remapeamento do objeto original.
	 */
	private MappingField fielAncestor;
	/**
	 * Atributo que define a classe do atributo que esta sendo mapeado.
	 */
	private Class<?> classType;
	/**
	 * Atributo que define a classe do atributo pai que foi mapeado.
	 */
	private Class<?> classFather;
	/**
	 * Atributo que define o metodo get do atributo que esta sendo mapeado.
	 */
	private Method methodGet;
	/**
	 * Atributo que define o metodo set do atributo que esta sendo mapeado.
	 */
	private Method methodSet;
	/**
	 * Atributo que define o nome do atributo que esta sendo mapeado.
	 */
	private String nameField;
	
	public MappingField(Field field, boolean containsAnnotaions, Class<?> classFather){
		this.containsAnnotations = containsAnnotaions;
		this.classFather = classFather;
		try{
			mapping(field);
		}catch (Exception e) {}
	}
	
	public MappingField(Method methodGet, Method methodSet, boolean containsAnnotations, Class<?> classType) {
		this.classType = classType;
		this.methodGet = methodGet;
		this.methodSet = methodSet;
		this.containsAnnotations = containsAnnotations;
	}
	
	/**
	 * Metodo responsavel por pegar o anotation do atributo com base em sua classe.
	 * @param annotationClass Classe do anotation que sera obtido.
	 * @return O objeto do anotation caso ele exista no atributo ou null caso contrario.
	 */
	public Annotation getAnnotation(Class<?> annotationClass){
		Annotation annotation = null;
		if (annotations.containsKey(annotationClass)){
			annotation = annotations.get(annotationClass);
		}
		return annotation;
	}
	
	/**
	 * Metodo responsavel por pegar o valor do atributo de um objeto que seje instancia da classe de mapeamento.
	 * @param object Objeto pelo qual sera pego o atributo.
	 * @return Valor do atributo do objeto.
	 * @throws IllegalArgumentException Excessao caso ocorra alguma falha no momento de pegar o valor do atributo.
	 * @throws IllegalAccessException Excessao caso ocorra alguma falha no momento de pegar o valor do atributo.
	 * @throws SecurityException Excessao caso ocorra alguma falha no momento de pegar o valor do atributo.
	 * @throws InvocationTargetException Excessao caso ocorra alguma falha no momento de pegar o valor do atributo.
	 * @throws NoSuchMethodException Excessao caso ocorra alguma falha no momento de pegar o valor do atributo.
	 */
	public Object getValue(Object object) throws IllegalArgumentException, IllegalAccessException, SecurityException, InvocationTargetException, NoSuchMethodException{
		if(this.fielAncestor != null){
			object = this.fielAncestor.getValue(object);
		}
		if(object != null)
			object = getValueObject(object);
		return object;
	}
	
	/**
	 * Metodo que cria uma nova instancia da classe do tipo do atributo mapeado.
	 * @return Objeto da classe do tipo do atributo.
	 * @throws InstantiationException Excessao caso haja alguma falha na instanciacao da classe.
	 * @throws IllegalAccessException Excessao caso haja alguma falha na instanciacao da classe.
	 */
	private Object createInstance() throws InstantiationException, IllegalAccessException {
		return classType.newInstance();
	}

	/**
	 * Metodo que invoca o metodo get mapeado na classe sobre o objeto para pegar o valor que ele contem.
	 * @param object Objeto para o qual o metodo get sera invocado.
	 * @return Objeto do valor do atributo.
	 * @throws IllegalArgumentException Excessao ao invocar metodo sobre o objeto.
	 * @throws IllegalAccessException Excessao ao invocar metodo sobre o objeto.
	 * @throws InvocationTargetException Excessao ao invocar metodo sobre o objeto.
	 * @throws SecurityException Excessao ao invocar metodo sobre o objeto.
	 * @throws NoSuchMethodException Excessao ao invocar metodo sobre o objeto.
	 */
	private Object getValueObject(Object object) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Object value = this.methodGet.invoke(object, null);
		return value;
	}
	
	/**
	 * Metodo responsavel por atribuir um valor no objeto ao atributo mapeado.
	 * @param object Objeto em que o atributo sera injetado.
	 * @param value Valor que sera colocado no atributo.
	 * @throws IllegalArgumentException Excessao de erro no momento de atribuicao do valor ao atributo.
	 * @throws IllegalAccessException Excessao de erro no momento de atribuicao do valor ao atributo.
	 * @throws SecurityException Excessao de erro no momento de atribuicao do valor ao atributo.
	 * @throws InvocationTargetException Excessao de erro no momento de atribuicao do valor ao atributo.
	 * @throws NoSuchMethodException Excessao de erro no momento de atribuicao do valor ao atributo.
	 * @throws InstantiationException Excessao de erro no momento de atribuicao do valor ao atributo.
	 */
	public void setValue(Object object, Object value) throws IllegalArgumentException, IllegalAccessException, SecurityException, InvocationTargetException, NoSuchMethodException, InstantiationException{
		if(this.fielAncestor != null){
			object = this.fielAncestor.getValueForSetValue(object);
		}
		setValueObject(object, value);
		
	}
	
	/**
	 * Metodo responsavel pegar um valor no atributo mapeado, reinstanciando os atributos ancestrais caso eles estejem nulos.
	 * @param object Objeto para o qual sera pego o valor.
	 * @return  Objeto do atributo que foi mapeado.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Object getValueForSetValue(Object object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SecurityException, InvocationTargetException, NoSuchMethodException{
		Object object2 = null;
		if(this.fielAncestor != null){
			object = this.fielAncestor.getValueForSetValue(object);
		}
		if(object != null){
			object2 = object;
			object = getValueObject(object);
		}
		if(object == null){
			object = createInstance();
			setValueObject(object2, object);
		}
		return object;
	}
	
	/**
	 * Metodo responsavel por setar um valor dentro do atributo.
	 * @param object Objeto para o qual o atributo sera setado
	 * @param value Valor que sera setado dentro do atributo. Este valor devera ser do mesmo tipo do atributo.
	 * @throws IllegalArgumentException Excessao caso haja falha na insercao do valor dentro do atributo do objeto.
	 * @throws IllegalAccessException Excessao caso haja falha na insercao do valor dentro do atributo do objeto.
	 * @throws InvocationTargetException Excessao caso haja falha na insercao do valor dentro do atributo do objeto.
	 * @throws SecurityException Excessao caso haja falha na insercao do valor dentro do atributo do objeto.
	 * @throws NoSuchMethodException Excessao caso haja falha na insercao do valor dentro do atributo do objeto.
	 */
	private void setValueObject(Object object, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		this.methodSet.invoke(object, value);
	}
	
	/**
	 * Metodo que realiza o mapeamento do atributo da classe. Esse mapeamento armazena o nome do atributo, sua classe, seus anotations.
	 * Tambem é mapeados seus respectivos metodos get e set.
	 * @param field Atributo da classe que esta sendo mapeada.
	 * @throws SecurityException Excessao de violacao de atribuicao do valor.
	 * @throws NoSuchMethodException Excessao caso nao seje encontrado o metodo get ou metodo set desse atributo dentro da classe.
	 */
	private void mapping(Field field) throws SecurityException, NoSuchMethodException{
		this.nameField = field.getName();
		this.classType = field.getType();
		annotations = new HashMap<Class<?>, Annotation>();
		Annotation list[] = field.getAnnotations();
		for (Annotation annotation : list) {
			annotations.put(annotation.annotationType(), annotation);
		}
		mappingMethods();
	}

	public MappingField getFielAncestor() {
		return fielAncestor;
	}

	public void setFielAncestor(MappingField fielAncestor) {
		this.fielAncestor = fielAncestor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public MappingField clone(){
		MappingField mappingField = new MappingField(this.methodGet, this.methodSet, this.containsAnnotations, this.classType);
		mappingField.setAnnotations(this.annotations);
		mappingField.setClassFather(this.classFather);
		mappingField.setFielAncestor(this.fielAncestor);
		mappingField.setNameField(this.nameField);
		return mappingField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + (containsAnnotations ? 1231 : 1237);
		result = prime * result
				+ ((fielAncestor == null) ? 0 : fielAncestor.hashCode());
		result = prime * result
				+ ((methodGet == null) ? 0 : methodGet.hashCode());
		result = prime * result
				+ ((methodSet == null) ? 0 : methodSet.hashCode());
		result = prime * result
				+ ((nameField == null) ? 0 : nameField.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappingField other = (MappingField) obj;
		if (annotations == null) {
			if (other.annotations != null)
				return false;
		} else if (!annotations.equals(other.annotations))
			return false;
		if (containsAnnotations != other.containsAnnotations)
			return false;
		if (fielAncestor == null) {
			if (other.fielAncestor != null)
				return false;
		} else if (!fielAncestor.equals(other.fielAncestor))
			return false;
		if (methodGet == null) {
			if (other.methodGet != null)
				return false;
		} else if (!methodGet.equals(other.methodGet))
			return false;
		if (methodSet == null) {
			if (other.methodSet != null)
				return false;
		} else if (!methodSet.equals(other.methodSet))
			return false;
		if (nameField == null) {
			if (other.nameField != null)
				return false;
		} else if (!nameField.equals(other.nameField))
			return false;
		return true;
	}

	public Map<Class<?>, Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<Class<?>, Annotation> annotations) {
		this.annotations = annotations;
	}
	
	/**
	 * Metodo responsavel por mapear os metodos get e set do atributo que esta sendo mapeado.
	 * @throws SecurityException Excessao de violacao de acesso ao atributo.
	 * @throws NoSuchMethodException Excessao caso nao exista o metodo set ou metodo get dentro da classe.
	 */
	private void mappingMethods() throws SecurityException, NoSuchMethodException{
		mappingMethodGet();
		mappingMethodSet();
	}
	
	/**
	 * Metodo responsavel por realizar o mapeamento do metodo get do atributo.
	* @throws SecurityException Excessao de violacao de acesso ao atributo.
	 * @throws NoSuchMethodException Excessao caso nao exista o metodo set ou metodo get dentro da classe.
	 */
	private void mappingMethodGet() throws SecurityException, NoSuchMethodException{
		String aux = upFirstLetter();
		String methodGet = "get" + aux;
		this.methodGet = this.classFather.getMethod(methodGet);
	}
	
	/**
	 * Metodo responsavel por realizar o mapeamento do metodo set do atributo.
	 * @throws SecurityException Excessao de violacao de acesso ao atributo.
	 * @throws NoSuchMethodException Excessao caso nao exista o metodo set ou metodo get dentro da classe.
	 */
	private void mappingMethodSet() throws SecurityException, NoSuchMethodException{
		String aux = upFirstLetter();
		String methodSet = "set" + aux;
		this.methodSet = this.classFather.getMethod(methodSet, this.classType);
	}
	
	/**
	 * Metodo responsavel por retornar o nome do atributo com sua primeira letra em maiusculo para realizar o mapeamento dos metodos get e set.
	 * @return String contendo o nome do atributo com a primeira letra em maiusculo.
	 */
	private String upFirstLetter(){
		String firstLetter = nameField.substring(0,1);
		firstLetter = firstLetter.toUpperCase();
		return firstLetter + nameField.substring(1,nameField.length());
	}
	
	//GETTES AND SETTERS

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
	}

	public Class<?> getClassFather() {
		return classFather;
	}

	public void setClassFather(Class<?> classFather) {
		this.classFather = classFather;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	
	
	
}
