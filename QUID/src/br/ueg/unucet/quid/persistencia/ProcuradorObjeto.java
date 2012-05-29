package br.ueg.unucet.quid.persistencia;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.utilitarias.EmptyValue;



/**
 * Classe responsavel por realizar a busca de um objeto no banco de dados a partir dos seus atributos preenchidos.
 * @author QUID
 *
 * @param <T> Classe do objeto que sera procurado.
 */
public class ProcuradorObjeto<T> {
	
	/**
	 * Objeto que esta sendo procurado.
	 */
	private Object objectFinder;
	/**
	 * Objeto HQL que realiza a consulta do objeto.
	 */
	private HQL<T> hql;
	/**
	 * Objeto que mapeia a classe do objeto que sera procurada.
	 */
	private MappingClass mappingClass;
	/**
	 * Nome dado a tabela pai do objeto que esta sendo procurado.
	 */
	private String aliasTableFather;
	/**
	 * Lista de criterios de busca do objeto. Esses criterios de busca irao formar a clausua where da consulta HQL.
	 * Sua estrutura e montada a partir dos atributos preenchidos do objectFinder.
	 */
	private List<Criteria> criterias;
	
	public ProcuradorObjeto(Object objectFinder){
		this.objectFinder = objectFinder;
		this.hql = new HQL<T>(objectFinder.getClass());
		this.mappingClass = ContainerMapeamentoClasse.getInstancia().getMapeamentoClasse(objectFinder.getClass());
		criterias = new ArrayList<Criteria>();
	}
	
	/**
	 * Metodo responsavel por realizar a busca dos objetos. Essa busca e feita a partir dos campos preenchidos do objetcFinder.
	 * @param idao Classe que realiza as operacoes de persistencia.
	 * @param orders Atributos pelos quais a consulta sera organizada.
	 * @param colluns Atributos que sera trazidos da consulta HQL.
	 * @return Lista de objetos encontrados no banco.
	 * @throws Exception Excessao gerado pela procura do objeto.
	 */
	public List<T> getByFinder(IDAO idao,String[] orders, String ...colluns) throws Exception{
		setInformationsHql(orders, colluns);
		prepareCriteria();
		return executeQuery(idao);
	}
	
	/**
	 * Metodo responsavel por realizar a busca dos objetos. Essa busca e feita a partir dos campos preenchidos do objetcFinder.
	 * @param idao Classe que realiza as operacoes de persistencia.
	 * @param colluns Atributos que sera trazidos da consulta HQL.
	 * @return Lista de objetos encontrados no banco.
	 * @throws Exception Excessao gerado pela procura do objeto.
	 */
	public List<T> getByFinder(IDAO idao, String ...colluns) throws Exception{
		setInformationsHql(colluns);
		prepareCriteria();
		return executeQuery(idao);
	}
	
	/**
	 * Metodo responsavel por setar as colunas de busca dentro do objeto criteria, e chamar os metodos de mapeamento da classe pai do objeto e seus atributos.
	 * @param colluns Atributos que serao trazidos da consulta HQL.
	 * @throws Exception Excessao na execucao do metodo.
	 */
	private void setInformationsHql(String ...colluns) throws Exception{
		hql.atributos(colluns);
		mappingClassFather();
		mappingObject(mappingClass.getListMappingField());
	}
	
	/**
	 * Metodo responsavel por setar as colunas de busca e as colunas de ordenamento dentro do objeto criteira. 
	 * @param orders String contendo os atributos de ordenamento da HQL.
	 * @param colluns Atributos que serao trazidos da consulta HQL.
	 * @throws Exception Excessao na execucao do metodo.
	 */
	private void setInformationsHql(String[] orders, String ...colluns) throws Exception{
		setInformationsHql(colluns);
		hql.ordenamento(orders);
	}
		

	/**
	 * Metodo responsavel por concatenar as criterias obtidas do mapeamento em um estrutura de fila, fornecida pala classe criteria.
	 */
	private void prepareCriteria() {
		if(!criterias.isEmpty()){
			Criteria criteria = criterias.get(0);
			for(int i = 1; i<criterias.size(); i++){
				criteria.concatenarCriteria(criterias.get(i));
			}
			hql.setCriteria(criteria);
		}	
	}

	/**
	 * Metodo responsavel por realizar o mapeamento da classe do objeto que esta sendo procurado.
	 */
	private void mappingClassFather() {
		String nameClassFather = objectFinder.getClass().getSimpleName();
		hql.froms(nameClassFather + " as " + nameClassFather.toLowerCase());
		aliasTableFather = nameClassFather.toLowerCase();
	}

	/**
	 * Metodo responsavel por pegar a consulta HQL da classe hql e a executar 
	 * dentro do objeto idao. Apos a consulta o retorno e tratado pelo metodo carregar, que constroi os objetos
	 * vindo do banco de dados.
	 * @param idao Objeto de persistencia que ira executar a consulta HQL
	 * @return Lista de objetos encontrados no banco de dados.
	 * @throws Exception Excessao caso haja falha na consulta.
	 */
	private List<T> executeQuery(IDAO idao) throws Exception {
		String sql = hql.construir();
		return hql.carregar(idao.executeQuery(sql));
	}
	

	/**
	 * Metodo responsavel por chamar o mapeamento de cada atributo de um objeto. Esses atributos estao mapeados na classe MappingField.
	 * @param fields Atributos que serao mapeados.
	 * @throws IllegalArgumentException Excessao gerada no mapeamento dos atributos.
	 * @throws SecurityException Excessao gerada no mapeamento dos atributos.
	 * @throws IllegalAccessException Excessao gerada no mapeamento dos atributos.
	 * @throws InvocationTargetException Excessao gerada no mapeamento dos atributos.
	 * @throws NoSuchMethodException Excessao gerada no mapeamento dos atributos.
	 */
	private void mappingObject(	Collection<MappingField> fields) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for (MappingField mappingField : fields) {
			checkMappingField(mappingField);
		}
		
	}

	/**
	 * Metodo responsavel por verificar se um determinado atributo não e uma lista para posteriormente realizar o mapeamento do atributo.
	 * @param mappingField Atributo que sera mapeado na consulta HQL.
	 */
	private void checkMappingField(MappingField mappingField) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(!mappingField.getClassType().isAssignableFrom(List.class)){
			mappingField(mappingField);
		}
	}
	
	/**
	 * Metodo responsavel por pegar o valor dentro do objeto de busca e verificar e suas anotacoes.
	 * Caso aja uma anotacao de OneToOnte ou OneToMany do JPA o metodo chama o mapeamento da classe interna do atributo
	 * que ira procurar os atribus dentro da classe filha para montar a consulta HQL.
	 * Caso não aja, ele verifica se o atributo encontrado dentro da classe nao e vazio e chama o mapeamento do atributo para a consulta HQL.
	 * @param mappingField Atributo que sera mapeado.
	 */
	private void mappingField(MappingField mappingField) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object value = mappingField.getValue(objectFinder);
			OneToOne oneToOne =(OneToOne) mappingField.getAnnotation(OneToOne.class);
			ManyToOne manyToOne = (ManyToOne) mappingField.getAnnotation(ManyToOne.class);
			if((oneToOne == null)&&(manyToOne == null)){
				if(!EmptyValue.isEmpty(value)){
					finderField(mappingField, value);
				}
			}else{
				mappingInternalClassDomain(mappingField);
			}
		
	}

	/**
	 * Metodo responsavel por mapear uma classe filha do objeto que esta sendo procurando.
	 * @param mappingField Atributo que contem a classe filha que esta sendo mapeada.
	 * @throws IllegalArgumentException
	 */
	private void mappingInternalClassDomain(MappingField mappingField) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		MappingClass mappingClass = ContainerMapeamentoClasse.getInstancia().getMapeamentoClasse(mappingField.getClassType());
		String nameTableSon = mappingField.getNameField();
		hql.froms(aliasTableFather +"." + nameTableSon + " as " + nameTableSon);
		String auxTableFather = this.aliasTableFather;
		this.aliasTableFather = nameTableSon;
		Object newObjectSearc = mappingField.getValue(this.objectFinder);
		Object aux = this.objectFinder;
		this.objectFinder = newObjectSearc;
		mappingObject(mappingClass.getListMappingField());
		this.objectFinder = aux;
		this.aliasTableFather = auxTableFather;
	}

	/**
	 * Metodo que cria uma criteria a partir de um artributo que sera mapeado.
	 * @param mappingField Atributo que esta sendo mapeado para consulta HQL.
	 * @param value Valor do atributo que esta sendo mapeado.
	 */
	private void finderField(MappingField mappingField, Object value) {
		if(mappingField.getFielAncestor() == null){
			Criteria criteria = new Criteria(aliasTableFather + "." + mappingField.getNameField(), value);
			criterias.add(criteria);
		}else{
			finderFieldAncestor(mappingField, value);
		}
		
	}

	/**
	 * Metodo que realizar o mapeamento de um atributo que tenha classes ancestrais. 
	 * Entendamos classes ancestrais como classes que estejam dentro do objeto por ex Pessoa.Endereco.Cidade.descricao.
	 * O atributo descricao tem as classes ancestrais cidade, endereco dentro do objeto pesquisado pessoa, logo deve-se
	 * montar os aliazes das consultas ate chegarem o atributo final.
	 * @param mappingField Atributo que esta sendo mapeado para consulta HQL.
	 * @param value Valor do atributo que esta sendo mapeado.
	 */
	private void finderFieldAncestor(MappingField mappingField, Object value) {
		String aliasAncestor = mappingField.getFielAncestor().getNameField();
		Criteria criteria = new Criteria(Criteria.EQUAL, aliasAncestor.toLowerCase() + "." + mappingField.getNameField(), value);
		criterias.add(criteria);
		while(mappingField.getFielAncestor() != null){
			mappingField = mappingField.getFielAncestor();
			String nameField = mappingField.getNameField();
			nameField = nameField + " as " + nameField.toLowerCase();
			if(!hql.getTables().contains(nameField)){
				hql.froms(nameField);
			}
		}
		
	}
	
	

}
