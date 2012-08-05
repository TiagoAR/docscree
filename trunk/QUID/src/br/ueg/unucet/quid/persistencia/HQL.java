package br.ueg.unucet.quid.persistencia;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Classe responsavel por realizar o mapeamento generico das consultas HQL com base no objeto que se deseja pesquisar.
 * @author Johnys
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class HQL<T> {
	/**
	 * Classe que a ser pesquisada.
	 */
	private Class<?> classProcurada;
	/**
	 * Lista de atributos que irao pertencer a clausula select da consulta HQL.
	 */
	private List<String> atributos;
	/**
	 * Lista de condicoes que a HQL ira pesquisar.
	 */
	private List<String> condicoes;
	/**
	 * Fila de criterias que ocorrem sobre a consulta.
	 */
	private Criteria criteria;
	/**
	 * Lista de tabelas que pertencem a consulta HQL.
	 */
	private List<String> tabelas;
	/**
	 * Lista dos atributos que fazem parte da clausula order by.
	 */
	private List<String> ordenamentos;
	
	/**
	 * Mapeamento dos objetos que sao utilizados na pesquisa da HQL.
	 */
	private Map<String, Object> mapeamentoObjetos;
	/**
	 * Mapeamento dos MappingClass para cada classe pertencente aos objetos mapeados da consulta HQL.
	 */
	private Map<String, MappingClass> mapeamentoClasses;
	/**
	 * Objeto pai sobre o qual sera ralizado a consulta.
	 */
	private Object objetoPai;

	public HQL(Class<?> classeProcurada) {
		this.classProcurada = classeProcurada;
		this.atributos = new ArrayList<String>();
		this.condicoes = new ArrayList<String>();
		this.ordenamentos = new ArrayList<String>();
		this.tabelas = new ArrayList<String>();
		this.mapeamentoObjetos = new HashMap<String, Object>();
		this.mapeamentoClasses = new HashMap<String, MappingClass>();
	}

	/**
	 * Metodo responsavel por setar os atributos do objeto que sera retornado na consulta.
	 * @param colunas Atributos que serao retornados
	 * @return Instancia da HQL.
	 */
	public HQL<T> atributos(String... colunas) {
		for (String coluna : colunas) {
			this.atributos.add(coluna);
		}
		return this;
	}

	/**
	 * Metodo responsavel por setar os atributos que serao condicoes para a consulta HQL.
	 * @param condicoes Condicoes de busca
	 * @return Intancia da HQL.
	 */
	public HQL<T> condicoes(String... condicoes) {
		for (String condicao : condicoes) {
			this.condicoes.add(condicao);
		}
		return this;
	}

	/**
	 * Metodo que seta as tabelas que participam da consulta HQL.
	 * @param tabelas Tabelas que participal da consulta.
	 * @return Instancia da HQL.
	 */
	public HQL<T> froms(String... tabelas) {
		for (String tabela : tabelas) {
			this.tabelas.add(tabela);
			mapeamentoObjeto(tabela);
		}
		return this;
	}
	
	/**
	 * Realiza a reinstanciacao dos objetos do mapeamento para que seja populado por uma nova linha da consulta. 
	 */
	private void reinstanciarObjeto(){
		try{
		reinstanciarObjetoPai();
		for(String tabela : this.tabelas){
			if(tabela.contains("."))
			reinstanciar(tabela);			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que realiza a reinstanciacao do Objeto pai.
	 * @throws Exception Excessao caso aja falha na instanciacao do objeto.
	 */
	private void reinstanciarObjetoPai() throws Exception {
		String[] nomeTabelaPai = getNomeEAlias(this.tabelas.get(0));
		this.mapeamentoObjetos.remove(nomeTabelaPai[1]);
		this.objetoPai = classProcurada.newInstance();
		this.mapeamentoObjetos.put(nomeTabelaPai[1], this.objetoPai);
	}

	/**
	 * Metodo que reintancia um objeto pertecente a uma tabela advinda de um relacionamento com o objeto pai.
	 * @param table Tabela que pertence o objeto.
	 * @throws Exception Excessao em caso na falha da instanciacao.
	 */
	private void reinstanciar(String table)throws Exception {
		String[] nomesInternos = getNomeEAlias(table);
		String[] nomesTabelas = getNomePaiENomeObjeto(nomesInternos[0]);
		MappingClass mapeamentoClasse = this.mapeamentoClasses.get(nomesTabelas[0]);
		MappingField mapeamentoAtributo = mapeamentoClasse.getMapeamentoAtributoPorNomeField(nomesTabelas[1]);
		Object objetoPai = this.mapeamentoObjetos.get(nomesTabelas[0]);
		Object objetoFilho = mapeamentoAtributo.getClassType().newInstance();
		mapeamentoAtributo.setValue(objetoPai, objetoFilho );
		this.mapeamentoObjetos.remove(nomesInternos[1]);
		this.mapeamentoObjetos.put(nomesInternos[1], objetoFilho);
	}

	/**
	 * Metodo que realiza o mapemento da tabela a partir se seu nome. A cada ponto encontrado dentro do nome da tabela e feito uma queda de nivel, procurando o objeto filho ate
	 * se chegar no ultimo campo que e o atributo desejado. A cada quebra de nivel uma nova tabela e mapeada juntamente com seu objeto.
	 * @param tabela Tabela que esta sendo mapeada.
	 */
	private void mapeamentoObjeto(String tabela) {
		try{
		if (tabela.contains(".")) {
			mapearObjetoInterno(tabela);
		} else {
			mappingObjetoPai(tabela);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que mapeia a intancia do objeto da tabela pai.
	 * @param tabela Tabela do objeto pai.
	 * @throws InstantiationException Excessao advinda da instanciacao do objeto pai.
	 * @throws IllegalAccessException Excessao advinda da instanciacao do objeto pai.
	 */
	private void mappingObjetoPai(String tabela)
			throws InstantiationException, IllegalAccessException {
		String[] names = getNomeEAlias(tabela);
		this.mapeamentoClasses.put(names[1], ContainerMapeamentoClasse.getInstancia()
				.getMapeamentoClasse(classProcurada));

	}

	/**
	 * Metodo que mapeia um objeto interno do objeto em relacao a um nivel de mapeamento ex entidade1.entidade2.campo.
	 * @param table Tabela que esta sendo mapeada.
	 * @throws IllegalArgumentException Excessao na instanciacao do objeto.
	 * @throws SecurityException Excessao na instanciacao do objeto.
	 * @throws IllegalAccessException Excessao na instanciacao do objeto.
	 * @throws InvocationTargetException Excessao na instanciacao do objeto.
	 * @throws NoSuchMethodException Excessao na instanciacao do objeto.
	 * @throws InstantiationException Excessao na instanciacao do objeto.
	 */
	private void mapearObjetoInterno(String table)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		String[] nomesObjetoInterno = getNomeEAlias(table);
		String[] nomePaiEAtributo = getNomePaiENomeObjeto(nomesObjetoInterno[0]);
		MappingClass mapeamentoClassePai = this.mapeamentoClasses.get(nomePaiEAtributo[0]);
		MappingField mapeamentoAtributo = mapeamentoClassePai.getMapeamentoAtributoPorNomeField(nomePaiEAtributo[1]);
		this.mapeamentoClasses.put(nomesObjetoInterno[1], ContainerMapeamentoClasse.getInstancia().getMapeamentoClasse(mapeamentoAtributo.getClassType()));

	}

	/**
	 * Metodo que divide a o nome da tabela em um vetor de 2 string. A primeira posicao contem o nome do objeto pai, e o segunda o restante da tabela.
	 * @param tabela Nome da tabela que esta sendo mapeada.
	 * @return Vetor de strings com os valores.
	 */
	private String[] getNomePaiENomeObjeto(String tabela) {
		int pos = tabela.indexOf(".");
		return new String[] { tabela.substring(0, pos), tabela.substring(pos + 1) };
	}

	/**
	 * Metodo que serpara o nome da tabela e seu alias em um vetor de duas posicoes. A primeira contem o nome da tabela. A segunda contem o seu alias.
	 * @param tabela Nome da tabela juntamente com seu alias.
	 * @return Vetor de strings com os campos validados.
	 */
	private String[] getNomeEAlias(String tabela) {
		int pos = tabela.indexOf(" as ");
		return new String[] { tabela.substring(0, pos),
				tabela.substring(pos + 4, tabela.length()) };
	}

	/**
	 * Metodo que seta os ordenamentros da consulta HQL.
	 * @param ordenamentos String com os campos de ordenamento
	 * @return Instancia da classe HQL.
	 */
	public HQL<T> ordenamento(String... ordenamentos) {
		if (ordenamentos != null) 
		for (String ordenamento : ordenamentos) {
			this.ordenamentos.add(ordenamento);
		}
		return this;
	}

	/**
	 * Metodo responsavel por realizar a montagem da clausula da SELECT da consulta HQL.
	 * @return String String com a clausula de select.
	 */
	private String atributos() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (String atributo : this.atributos) {
			sb.append(atributo);
			sb.append(", ");
		}
		return sb.substring(0, sb.length() - 2);

	}

	/**Metodo responsavel por realizar a montagem da clausula FROM da consulta HQL.
	 * @return String da clausula FROM.
	 */
	private String tabelas() {
		String aux = "";
		if (!this.tabelas.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(" FROM ");
			for (String tabela : this.tabelas) {
				sb.append(tabela);
				sb.append(" left join ");
			}
			aux = sb.substring(0, sb.length() - 11);
		}
		return aux;
	}
	
	/**
	 * Metodo responsavel por montar a clausula WHERE da consulta HQL com base na classe criteria ou nas condicoes.
	 * @return String da clausula WHERE.
	 */
	private String getSqlCondicao(){
		String aux;
		if (criteria == null){
			aux = condicao();
		}else{
			aux = " WHERE " + this.criteria.getSql();
		}
		return aux;
	}

	/**
	 * Metodo responsavel por montar a clausula de WHERE com base no vetor de condicoes.
	 * @return String da clausula WHERE.
	 */
	private String condicao() {
		String aux = "";
		if (!this.condicoes.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(" WHERE ");
			for (String condicao : this.condicoes) {
				sb.append(condicao);
				sb.append(" and ");
			}
			aux = sb.substring(0, sb.length() - 5);

		}
		return aux;

	}

	/**
	 * Metodo responsavel por montar a clausula ORDER BY da consulta HQL.
	 * @return String da clausula WHERE.
	 */
	private String ordenamento() {
		String aux = "";
		if (!this.ordenamentos.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(" ORDER BY ");
			for (String ordenamento : this.ordenamentos) {
				sb.append(ordenamento);
				sb.append(", ");
			}
			aux = sb.substring(0, sb.length() - 2);
		}
		return aux;
	}

	/**
	 * Metodo que constrou a consulta HQL para ser executada.
	 * @return String da consulta HQL.
	 */
	public String construir() {
		checarColunas();
		StringBuilder sb = new StringBuilder();
		sb.append(atributos());
		sb.append(tabelas());
		sb.append(getSqlCondicao());
		sb.append(ordenamento());
		return sb.toString();
	}

	/**
	 * Metodo que itera os atributos para realizar o remapeamento das colunas.
	 */
	private void checarColunas() {
		List<String> novosAtributos = new ArrayList<String>();
		for(String atributo : this.atributos){
			novosAtributos.add(mapearAtributo(atributo));
		}
		this.atributos = novosAtributos;
		
	}

	/**
	 * Metodo que verifica se um determinado atributo contem multiplos atributos ou de e de mapeamento simples.
	 * @param atributo Atributo a ser verificado a multiplicidade
	 * @return String no mapeamento do ultimo altributo a ser mapeado.
	 */
	private String mapearAtributo(String atributo) {
		String aux = atributo.replaceFirst(".", "");
		if(aux.contains(".")){
			atributo = mapearMultiplosAtributos(atributo);
		}
		return atributo;
		
		
	}

	/**
	 * Metodo que procura os dois ultimos atributos da string do atributo. 
	 * @param atributo Atributo a ser mapeado.
	 * @return Os dois ultimos atributos do atributo.
	 */
	private String mapearMultiplosAtributos(String atributo) {
		String finalAtributo = atributo;
		int pos = atributo.indexOf('.'); 
		while(pos != -1){
			String classePai = atributo.substring(0, pos);
			String restanteAtributo = atributo.substring(pos + 1, atributo.length());
			int pos2 = restanteAtributo.indexOf('.');
			if(pos2 != -1){
				String classeFilho = restanteAtributo.substring(0, pos2);
				checarClasseFilho(classePai, classeFilho);
				atributo = atributo.substring(pos + 1, atributo.length());
				finalAtributo = atributo;
				pos = atributo.indexOf('.');
			}else{
				pos = -1;
			}
		}
		return finalAtributo;
	}

	/**
	 * Metodo que realiza o mapeamento da classe filho desde que ela esteje no mapemamento de classes.
	 * @param classePai Classe do objeto pai.
	 * @param classeFilho Classe do objeto filho.
	 */
	private void checarClasseFilho(String classePai, String classeFilho) {
		if(!mapeamentoClasses.containsKey(classeFilho)){
			mapearClasseFilho(classePai, classeFilho);
		}
		
	}

	/**
	 * Metodo responsavel por mapear a classe filha juntamente com a classe pai montando a condicao tabelaPai.tabelaFilha as tabelafilha
	 * @param classePai Classe pai a ser mapeada
	 * @param classeFilho Classe filha a ser mapeada
	 */
	private void mapearClasseFilho(String classePai, String classeFilho) {
		MappingClass mapeamentoClassePai = mapeamentoClasses.get(classePai);
		MappingField mapemamentoAtributo = mapeamentoClassePai.getMapeamentoAtributoPorNomeField(classeFilho);
		Class<?> classeFilha = mapemamentoAtributo.getClassType();
		this.mapeamentoClasses.put(classeFilho, ContainerMapeamentoClasse.getInstancia().getMapeamentoClasse(classeFilha));
		StringBuilder sb = new StringBuilder();
		sb.append(mapeamentoClassePai);
		sb.append(".");
		sb.append(classeFilho);
		sb.append(" as ");
		sb.append(classeFilho);
		this.tabelas.add(sb.toString());
	}

	/**
	 * Metodo responsavel por carregar o retorno da execucao da HQL para uma lista de objetos.
	 * @param registros Resultado da execucao da HQL.
	 * @return Lista de objetos
	 * @throws Exception Excessao na montagem da lista.
	 */
	public List<T> carregar(List<Object[]> registros) throws Exception {
		List<T> list = new ArrayList<T>();
		if (registros != null) {
		try {
			for (Object[] objeto : registros) {
				reinstanciarObjeto();
				list.add(carregarObjeto(objeto));
			} 
		} catch (ClassCastException e) {
			for (Object objeto : registros) {
				Object[] objetoVetor = new Object[1];
				objetoVetor[0] = objeto;
				reinstanciarObjeto();
				list.add(carregarObjeto(objetoVetor));
			} 
		}
		} 
		return list;
	}

	/**
	 * Metodo que carrega um vetor de objetos decorrente a uma linha da consulta HQL para seu respectivo valor dentro do objeto
	 * @param objetos Vetor de objetos com os valores trazidos do banco.
	 * @return Objeto carregados os campos.
	 * @throws Exception Excessao caso aja falha na insecao de algum campo dentro do objeto.
	 */
	private T carregarObjeto(Object[] objetos) throws Exception {
		for (int i = 0; i < this.atributos.size(); i++) {
			setValor(objetos[i], this.atributos.get(i));

		}
		return (T) this.objetoPai;
	}

	/**
	 * Metodo que insere um valor oriundo da consulta HQL dentro de do atributo do objeto.
	 * @param registro Valor da consulta a ser inserida
	 * @param campo Campo do objeto  a ser inserido o valor.
	 * @throws IllegalArgumentException Excessao caso aja fala na insercao do valor.
	 * @throws SecurityException Excessao caso aja fala na insercao do valor.
	 * @throws IllegalAccessException Excessao caso aja fala na insercao do valor.
	 * @throws InvocationTargetException Excessao caso aja fala na insercao do valor.
	 * @throws NoSuchMethodException Excessao caso aja fala na insercao do valor.
	 * @throws InstantiationException Excessao caso aja fala na insercao do valor.
	 */
	private void setValor(Object registro, String campo)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		if (registro != null) {
			String nomes[] = getNomePaiENomeObjeto(campo);
			MappingClass mapeadorClasse = this.mapeamentoClasses.get(nomes[0]);
			Object objetoSet = this.mapeamentoObjetos.get(nomes[0]);
			MappingField mapeamentoAtributo = mapeadorClasse.getMapeamentoAtributoPorNomeField(nomes[1]);
			mapeamentoAtributo.setValue(objetoSet, registro);
		}

	}
	
	public void setCriteria(Criteria criteria){
		this.criteria = criteria;
	}
	
	public Criteria getCriteria(){
		return this.criteria;
	}
	
	public List<String> getTables(){
		return this.tabelas;
	}
}
