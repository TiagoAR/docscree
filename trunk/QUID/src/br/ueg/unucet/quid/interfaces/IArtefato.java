package br.ueg.unucet.quid.interfaces;

import java.util.Collection;

import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IIdentificavel;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IPersistivel;
import br.ueg.unucet.quid.extensao.interfaces.IServico;


/**
 * Interface que representa as operacoes de manipulacao do artefato.
 * @author QUID
 *
 */
public interface IArtefato extends IIdentificavel, IPersistivel<Long> {

	/**
	 * Retorna a lista de TipoMembro modelo que compoem o artefato.
	 * 
	 * @return Lista de TipoMembroModelo
	 */
	Collection<Membro> getListaMembro();

	/**
	 * Atribui uma lista de TipoMembroModelo a um artefato.
	 * 
	 * @param listaTipoMembroModelo
	 *            Lista de TipoMembroModelo que compoem o artefato.
	 */
	void setListaMembro(
			Collection<Membro> listaTipoMembroModelo);
	
	/**Adiciona um membro ao Artefato
	 * @param membro Membro a ser adicionado
	 */
	Retorno<String, Collection<String>> addMembro(Membro membro);
	
	
	/**Remove um membro de um Artefato.
	 * @param membro Membro a ser removido do artefato.
	 */
	Retorno<Object, Object> removerMembro(Membro membro); 
	/**
	 * Retorna a lista de servicos do artefato.
	 * 
	 * @return lista de servicos do artefato
	 */
	Collection<IServico> getListaServico();

	/**
	 * Atribui uma lista de servicos a um artefato. Toda lista de servicos
	 * contem o servico de validacao de membros obrigatorios. A sequencia da
	 * lista sera a sequencia de execucao dos servicos.
	 * 
	 * @param listaServico Lista de servicos do artefato
	 */
	void setListaServico(Collection<IServico> listaServico);
	
	/**Adiciona um novo servico ao artefato.
	 * @param servico Servico a ser adicionado no artefato.
	 * @return Retorno da execucao
	 */
	Retorno<String, Collection<String>> addServico(IServico servico);
	
	/**Remove um servido de um artefato
	 * @param servico Servico a ser removido.
	 * @return Retorno da execucao.
	 */
	Retorno<String, String> removerServico(IServico servico);
	
	/** Executa a acao de um servico atravez de seu nome.
	 * Para o servico inicial, o unico parametro de execucao sera a instancia corrente do artefato.
	 * O acao de pegar o resultado de um servico e encaminhar para outro dever
	 * ser implementada aqui.
	 * @param nomeServico Nome do servico que sera executado.
	 * @param parametros Parametros do Serviço
	 */
	Retorno<Object, Object> executaServico(String nomeServico, Collection<IParametro<?>> parametros);

	/** Retorna o titulo do artefato. Esse titulo identifica duas instancias diferentes de um mesmo artefato.
	 * @return Titulo do artefato
	 */
	String getTitulo();

	/** Atribui um titulo a um artefato.
	 * @param titulo Titulo do artefato
	 */
	void setTitulo(String titulo);

	/** Retorna a categoria que o artefato pertence.
	 * @return Categoria do artefato
	 */
	Categoria getCategoria();
	
	/**Metodo que atribui uma categoria ao artefato
	 * @param categoria Categoria que pertence o artefato
	 */
	void setCategoria(Categoria categoria);
}
