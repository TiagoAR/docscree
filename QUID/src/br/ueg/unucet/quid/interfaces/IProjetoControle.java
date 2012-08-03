package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.excessoes.ProjetoExcessao;

/**
 * Inteface que definem as operacoes sobre o controlador do Projeto.
 * @author QUID
 *
 * @param <T> Classe que representa a entidade Projeto no framework.
 * @param <oid>Classe que representa a chava primaria da entidade.
 */
public interface IProjetoControle<T, oid> extends IControle<T, oid>{
	
	/**
	 * Metodo responsavel por realizar o bloqueio de um projeto dentro do framework. Apos ser bloqueado o projeto nao podera ser
	 * preenchido por nenhum usuario.
	 * @param projeto Projeto que sera bloqueado.
	 * @throws ProjetoExcessao Excessao caso aja falha no bloqueio do artefato.
	 */
	void bloquearProjeto(Projeto projeto) throws ProjetoExcessao;
	
	Collection<Projeto> pesquisarProjeto(Projeto projeto);

}
