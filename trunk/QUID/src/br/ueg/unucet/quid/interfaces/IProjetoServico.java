package br.ueg.unucet.quid.interfaces;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;

/**
 * Classe responsavel por definir as operacoes sobre o servico do Projeto.
 * @author QUID
 *
 * @param <T>Classe da chave primaria da entidade.
 */
public interface IProjetoServico<T> extends IServico<T> {
	/**
	 * Metodo que insere um novo projeto no framework.
	 * @param nome Nome do projeto.
	 * @param descricao Descricao do Projeto.
	 * @param modelo Modelo que o projeto sera escrito.
	 * @param equipe Equipe que ira preencher o projeto.
	 * @return Retorno da execucao da operacao de insercao.
	 */
	Retorno<Object, Object> inserir(String nome, String descricao, Modelo modelo, Equipe equipe);
	/**
	 * Metodo responsavel por realizar o bloqueio de um projeto dentro do framework. Apos ser bloqueado o projeto nao podera ser
	 * preenchido por nenhum usuario.
	 * @param projeto Projeto que sera bloqueado.
	 * @return Retorno da execucao da acao de bloqueio do projeto.
	 */
	Retorno<Object, Object> bloquearProjeto(Projeto projeto);
}
