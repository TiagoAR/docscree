package br.ueg.unucet.quid.interfaces;

import java.io.File;
import java.util.Collection;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Categoria;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.ItemModelo;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

/**
 * Interface de comunicacao do framework. Nela estao especificados todas as operacoes que podem
 * ser realizadas pelo framework.
 * @author QUID
 *
 */
public interface IQUID {
	
	 /**Metodo responsavel por mapear um artefato para o framework. 
	 * @param artefato Artefato que sera persistido no framewok.
	 * @return Retorno da execucao da acao
	 */
	Retorno<String, Collection<String>> mapearArtefato(Artefato artefato);
	
	/**Realiza a pesquisa de um artefato a partir dos atributos informados. (Busca Parcial %valor%)
	 * @param categoria Categoria que pertence o artefato.
	 * @param nome Nome do artefato
	 * @param descricao descricao do artefato.
	 * @return
	 */
	Retorno<String, Collection<Artefato>> pesquisarArtefato(Categoria categoria, String nome, String descricao);
	
	/**Remove um artefato do framework.
	 * @param artefato Artefato que sera removido.
	 * @return Retorno da execucao da acao.
	 */
	Retorno removerArtefato(Artefato artefato);
	
	/**Altera um artefato no framework.
	 * @param artefato Artefato a ser alterado.
	 * @return Retorno da execucao da acao.
	 */
	Retorno alterarArtefato(Artefato artefato);
	
	/**Metodo responsavel por retornar uma nova instancia de um artefato.
	 * @return Instancia do artefato.
	 */
	Artefato getInstanciaArtefato();
	
	Retorno<String, ITipoMembroModelo> getInstanciaTipoMembroModelo(String nome, Integer versao);
	
	/**Pesquisa uma lista de membros a partir do TipoMembro informado.
	 * @param tipoMembro TipoMembro a ser pesquisado.
	 * @return Retorno execucao acao.	
	 */
	Retorno<String, Collection<Membro>> getListaMembro(ITipoMembroModelo tipoMembro);
	
	/**Retorna a lista de tipoMembro modelo cadastrado no framework
	 * @return Retorno da pesquisa
	 */
	Retorno<String, Collection<ITipoMembroModelo>> listaTipoMembroModelo();
	
	/**Retorna a lista de tipoMembro modelo cadastrado no framework
	 * @return Retorno da pesquisa
	 */
	Retorno<String, Collection<ITipoMembroVisao>> listaTipoMembroVisao();
	
	/**Mapeia um novo membro para o framework
	 * @param membro Membro a ser mapeado
	 * @return Retorno execucao acao.
	 */
	Retorno<Object, Object> mapearMembro(Membro membro);
	
	/**Metodo responsavel por alterar um membro cadastrado no framework
	 * @param membro Membro a ser alterado.
	 * @return Retorno execucao acao.
	 */
	Retorno<Object, Object> alterarMembro(Membro membro);
	
	/**Clona um artefato do framework.
	 * @param artefatoAClonar Artefato a ser clonado.
	 * @param nome Nome do novo arteafato clonado
	 * @param descricao Descricao do novo artefato clonado
	 * @return Retorno da execucao
	 */
	Retorno clonarArtefato(IArtefato artefatoAClonar, String nome, String descricao);
	
	/**Metodo responsavel por solicitar a interface de preenchimento do artefato.
	 * @param artefato Artefato a ser preenchido.
	 * @param projeto Projeto em que esta sendo preenchido o artefato.
	 * @param tecnologiVisao Tecnologia da visao que esta sendo utilizada.
	 * @return Retorno execucao.
	 */
	Retorno getInterfacePreenchimento(IArtefato artefato, Projeto projeto, String tecnologiVisao); 
	
	/**Metodo responsavel por cancelar o preenchimento do artefato em um documento.
	 * @param artefato Artefato a ser cancelado o preenchimento.
	 * @param projeto Projeto em que o artefato esta sendo preenchido.
	 * @return Retorno execucao.
	 */
	Retorno cancelarPreenchimentoArtefato(IArtefato artefato, Projeto projeto);
	 
	/**Metodo responsavel por gerar a interface de visualizacao do artefato.
	 * @param artefato Artefato que sera visualizado
	 * @return Retorno execucao.
	 */
	Retorno getInterfaceVisualizacao(IArtefato artefato);
	
	/**Metodo responsavel por retornar os servicos de publicacao cadastrados no framework
	 * @return Lista de servicos de publicacao
	 */
	Collection<IServico> getServicoPublicacao();
	
	/**Publica um artefato do framework.
	 * @param artefato Artefato a ser publicado
	 * @param servicoPublicacao Servico de publicacao de artefato.
	 * @return Retorno execucao acao.
	 */
	Retorno publicarArtefato(IArtefato artefato, IServico servicoPublicacao);
	
	/**Metodo que realiza a busca do TipoMembro visao a partir do tipomembro modelo
	 * @param tipoMembroModelo TipoMembroModelo a procurar
	 * @return Retorno execucao
	 */
	Retorno<String, ITipoMembroVisao> getTipoMembroVisao(ITipoMembroModelo tipoMembroModelo);
	
	Retorno<String, ITipoMembroModelo> getTipoMembroModelo(ITipoMembroVisao tipoMembroVisao);
	
	/**Mapeia os jar dos arquivos dos TipoMembro para o framework
	 * @param arquivos Arquivos jars a serem mapeados
	 * @return Retorno execucao acao.
	 */
	Retorno<File, String> mapearArquivosTipoMembro(File ... arquivos);
	
	/**Mapeia os jar dos arquivos dos Servicos para o framework
	 * @param arquivos Arquivos jars a serem mapeados
	 * @return Retorno execucao acao.
	 */
	Retorno<File, String> mapearArquivosServicos(File ... arquivos);
	 
	/**Retorna uma versao do TipoMembro no framework caso exista.
	 * @param tipoMembro TipoMembro a ser voltado a versao.
	 * @return Retorno execucao.
	 */
	Retorno voltarVersaoTipoMembro(ITipoMembro tipoMembro);
	
	/**Retnora uma versao do Servico no framework caso exista.
	 * @param servico Servico a ser voltado a versao
	 * @return Retorno execucao servico.
	 */
	Retorno voltarVersaoServico(IServico servico);
	
	
	/**Metodo responsavel por realizar a instancia do modelo.
	 * @param nome Nome do modelo que sera criado.
	 * @param descricao Descricao do modelo que sera criado.
	 * @return Modelo instanciado pelo framework.
	 */
	Retorno<String, Modelo> getInstanciaModelo(String nome, String descricao);
	
	/**Metodo responsavel por mapear um modelo para o framework
	 * @param modelo Modelo que sera mapeado.
	 * @return Retorno execucao acao.
	 */
	Retorno<Object, Object> mapearModelo(Modelo modelo);
	
	/**
	 * Metodo responsavel por criar um novo Item do modelo com base no artefato.
	 * @param artefato Artefato para o qual sera contruido o modelo.
	 * @return Instancia de novo ItemModelo ou falha na execucao com retorno da mensagem do erro ocorrido.
	 */
	Retorno<String, ItemModelo> criarItemModelo(Artefato artefato);
	
	/**
	 * Metodo responsvael por realizar a removcao de um de um modelo 
	 * @param artefato Artefato que sera removido do modelo.
	 * @param modelo Modelo para qual o artefato sera removido.
	 * @return Retorno da execucao da operacao.
	 */
	Retorno<Object, Object> removerArtefatoModelo(Artefato artefato, Modelo modelo);
	
	/**
	 * Metodo que realiza a verificacao dos campos do ItemModelo.
	 * @param itemModelo ItemModelo que sera verificado os campos.
	 * @param modelo Modelo para qual pertence o ItemModelo.
	 * @return Retorno execucao da validacao.
	 */
	Retorno<String, Collection<String>> validarItemModelo(ItemModelo itemModelo, Modelo modelo);
	
	/**
	 * Metodo que realiza a inclusao de um ItemModelo no modelo, realizando a verificacao de campos obrigatorios.
	 * @param modelo Modelo que sera inserido o ItemModelo
	 * @param itemModelo ItemModelo que sera inserido dentro do modelo.
	 * @return Retorno da execucao da inclusao do modelo.
	 */
	Retorno<String, Collection<String>> incluirItemModelo(Modelo modelo, ItemModelo itemModelo);
	
	/**Metodo que retorna a lista de parametros de um servico
	 * @param servico Servico que sera procurado os parametros
	 * @return Parametros de preenchimento
	 */
	Retorno<String, Collection<IParametro<?>>> listaParametrosServico(IServico servico);
	
	/**Metodo responsavel por retornar uma instancia do Usuario
	 * @return instancia do Usuaio
	 */
	Retorno<String, Usuario> getInstanciaUsuario();
	
	/**Metodo responsavel por realizar a insercao de um usaurio no framework
	 * @return Retorno execucao
	 */
	Retorno<String, Collection<String>> inserirUsuario(Usuario usuario);
	
	/**Metodo responsavel  por realizar a alteracao de um usuario no sistema
	 * @return Retorno execucao
	 */
	Retorno<String, Collection<String>> alterarUsuario(Usuario usuario);
	
	/**Metodo utilizado para realizar a pesquisa de usuarios cadastrados no framework
	 * @return Retorno execucao
	 */
	Retorno<String, Collection<Usuario>> pesquisarUsuario(Usuario usuario);

	 /**Metodo responsavel por realizar a insersao de uma equipe no frmaework
	 * @param equipe Equipe que sera inserida
	 * @return Retorno execucao
	 */
	Retorno<String, Collection<String>> inserirEquipe(Equipe equipe);
	
	Retorno<String, Collection<Equipe>> pesquisarEquipe(Equipe equipe);
	 
	/**Metodo responsavel por realizar a alteracao de uma equipe no frmaework
	 * @param equipe Equipe que sera alterada
	 * @return Retorno execucao
	 */
	Retorno<String, Collection<String>> alterarEquipe(Equipe equipe);
	
	/**Metodo responsavel por retornar uma lista de servicos cadastrados no framework
	 * @return Retorno execucao
	 */
	Retorno<String, Collection<IServico>> listarServicos();
	
	Retorno<Object, Object> inserirProjeto(Projeto projeto);
	Retorno<Object, Object> alterarProjeto(Projeto projeto);
	Retorno<String, Collection<Projeto>> pesquisarProjeto(Projeto projeto);
	Retorno<String, Collection<Modelo>> listarModelo();
	Retorno<String, Collection<Membro>> pesquisarMembro(String nomeMembro, ITipoMembroModelo tipoMembro);

	Retorno<Object, Object> removerMembro(Membro membro);
}
