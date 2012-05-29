package br.ueg.unucet.quid.controladores;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.interfaces.IControle;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;

/**
 * Classe responsavel por realizar as operacoes basicas sobre as entidades que serao persistidas no framework 
 * @author QUID
 *
 * @param <T> Entidade que sera persistida
 * @param <oid> Tipo da chave identificadora(ID) da entidade que sera persistida 
 */
public abstract class GenericControle<T, oid> implements IControle<T, oid> {

	/**
	 * Interface generica de gerenciamento das operacoes do crud.
	 */
	private IDAO<T, oid> dao;
	/**
	 * Atributo que define se ocorreu sucesso em determinada operacao. Esse atributo e utilizado 
	 * pelas classes filhas para o controle do fluxo de execucao.
	 */
	private boolean sucesso;
	/**
	 * Atributo responsavel por realizar a leitura do mensagens.properties
	 */
	protected LeitoraPropertiesUtil propertiesMessagesUtil = FabricaProperties.loadMessages();
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#salvar(java.lang.Object)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void inserir(T entidade) throws QuidExcessao {
		if(antesInserir(entidade)){
			this.dao = getDao();
			dao.inserir(entidade);
			depoisInserir();
		}
		
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#alterar(java.lang.Object)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void alterar(T entidade) throws QuidExcessao {
		if(antesAlterar(entidade)){
			this.dao = getDao();
			dao.alterar(entidade);
			depoisAlterar();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#deletar(java.lang.Class, java.lang.Object)
	 */
	@Override
	public void remover(Class<T> classe, oid id) throws QuidExcessao{
		if(antesRemover()){
			this.dao = getDao();
			dao.remover(classe, id);
			depoisRemover();
		}
		
	}
	

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#getTodos()
	 */
	@Override
	public Collection<T> listar() {
		this.dao = getDao();
		return dao.listar();
	}
	
	

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#getPorId(java.lang.Class, java.lang.Object)
	 */
	@Override
	public T getPorId(Class<T> classe, oid id) {
		this.dao = getDao();
		return dao.getPorId(classe, id);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#getPorRestricao(java.lang.Object, java.lang.String[])
	 */
	@Override
	public Collection<T> pesquisarPorRestricao(T entidade, String[] colunas) {
		this.dao = getDao();
		return dao.pesquisarPorRestricao(entidade, colunas);
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#getPorRestricao(java.lang.Object, java.lang.String[], java.lang.String[])
	 */
	@Override
	public Collection<T> pesquisarPorRestricao(T entidade, String[] colunas,
			String[] ordenamento) {
		this.dao = getDao();
		return dao.pesquisarPorRestricao(entidade, colunas, ordenamento);
	}


	
	/**Metodo que e chamado antes de se realizar a acao de salvar.
	 * Ele deve ser sobrescrito para restringir a acao de salvar.
	 * @return True para realizar a acao de salvar, False caso contrario
	 */

	public boolean antesInserir(T entidade) throws QuidExcessao{
		return true;
	}
	
	/**
	 * Metodo que e chamado apos a realizacao da acao de salvar.
	 * Ele deve ser sobrescrito para se inserir acoes apos a persistencia da entidade
	 */
	public void depoisInserir(){}
	
	/**Metodo que e chamado antes de se realizar a acao de aletar.
	 * Ele deve ser sobrescrito para restringir a acao de alterar.
	 * @return True para realizar a acao de alterar, False caso contrario.
	 */
	public boolean antesAlterar(T entidade) throws QuidExcessao{
		return true;
	}
	
	/**
	 * Metodo que e chamado apos a realizacao da acao de alterar.
	 * Ele deve ser sobrescrito para se inserir acoes apos a alteracao da entidade
	 */
	public void depoisAlterar(){}
	
	/**Metodo que e chamado antes de se realizar a acao de deletar.
	 * Ele deve ser sobrescrito para restringir a acao de deletar.
	 * @return True para realizar a acao de deletar, False caso contrario
	 */
	public boolean antesRemover() throws QuidExcessao{
		return true;
	}
	
	/**
	 * Metodo que e chamado apos a realizacao da acao de deletar.
	 * Ele deve ser sobrescrito para se inserir acoes apos a delecao da entidade
	 */
	public void depoisRemover(){}
	
	/**Metodo que realiza a injecao da dependencia do DAO dentro do controlador
	 * @return Instancia do DAO especifica da classe
	 */
	public abstract IDAO<T, oid> getDao();
	
	/**
	 * Metodo que realiza a construcao do objeto de retorno de operacao com falha com base no erro disparado.
	 * @param retorno Objeto de retorno de execucao de operacao
	 * @param erro Throwable que originou o erro
	 * @param tipoErroEnum Nivel do erro disparado
	 * @return O objeto retorno preenchido
	 */
	protected Retorno<?, ?> construirRetornoErro(Retorno<?,?> retorno, Throwable erro, TipoErroEnum tipoErroEnum){
		retorno.setErro(erro);
		retorno.setMensagem(erro.getMessage());
		retorno.setSucesso(false);
		retorno.setTipoErro(tipoErroEnum);
		return retorno;
	}
	
	/**
	 * Metodo responsavel por verificar se uma determinada entidade esta cadastrada no framework.
	 * Para essa verificacao e feito uma pesquisa da entidade no framework e posteriormente verificado a duplicidade do cadastro.
	 * @param entidade Entidade que sera verificada o cadastro.
	 * @param colunasBusca Colunas de busca da entidade no banco. As colunas representam quais informacoes da entidade que devem ser
	 * trazidas do banco para se realizar a comparacao de duplicidade.
	 * @return True caso a entidade ja esteja cadastrada no framework e false caso contrario
	 */
	public boolean isCadastrada(T entidade, String[] colunasBusca){
		Collection<T> lista = pesquisarPorRestricao(entidade, colunasBusca);
		return verificarListaCadastrada(lista);
	}
	
	/**
	 * Metodo responsavel por realizar a verificacao de a entidade esta cadastrada.
	 * Este metodo e uma resolucao simples da operacao de verificacao de duplicidade atravez da verificacao se a lista esta vazia ou nao.
	 * Caso as classes filhas desejem, elas podem reimplementar esse metodo especificando os parametros de verificacao se determinada entidade
	 * ja esta cadastrada no framework
	 * @param lista
	 * @return
	 */
	public boolean verificarListaCadastrada(Collection<T> lista){
		boolean result = false;
		if(lista != null){
			result = !lista.isEmpty();
		}
		return result;
	}
	
	//GETTERS AND SETTERS
	public boolean isSucesso(){
		return this.sucesso;
	}
	
	public void setSucesso(boolean sucesso){
		this.sucesso = sucesso;
	}
	
	
	

}
