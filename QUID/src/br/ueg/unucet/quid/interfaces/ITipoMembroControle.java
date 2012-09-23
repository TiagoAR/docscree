package br.ueg.unucet.quid.interfaces;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import br.ueg.unucet.quid.dominios.TipoMembro;
import br.ueg.unucet.quid.excessoes.TipoMembroExcessao;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

/**
 * Classe que representa as operacoes do controle sobre a entidade TipoMembro.
 * @author QUID
 *
 * @param <T>Entidade que representa o Membro dentro do framework.
 * @param <oid>Classe da chave primaria da entidade.
 */
public interface ITipoMembroControle<T, oid> extends IControle<T, oid> {
	
	/**Metodo que realiza o mapeamento de novos TipoMembros para o framework
	 * @param arquivos Arquivos que serao mapeados
	 * @return Map de acoes que ocorreram sobre o arquivos
	 * @throws TipoMembroExcessao Excessao de mapeamento dos servicos
	 */
	Map<File, String> mapearTiposMembro(File[] arquivos) throws TipoMembroExcessao;
	
	/**Metodo que retorna uma instancia do TipoMembro modelo
	 * @param tipoMembro TipoMembro cadastrado no framework
	 * @return Instancia do TipoMembro modelo
	 */
	ITipoMembroModelo getIntanciaTipoMembroControle(TipoMembro tipoMembro);
	
	/**Metodo que retorna uma instancia do TipoMembro visao
	 * @param tipoMembro TipoMembro cadastrado no framework
	 * @return Instancia do TipoMembro visao
	 */
	ITipoMembroVisao getInstanciaTipoMembroVisao(TipoMembro tipoMembro);
	
	/**Metodo responsavel por retornar uma instancia do TipoMembro cadastrado no framework
	 * com base na interface ITIpoMembro
	 * @param iTipoMembro ITipoMembro que se procura a interface
	 * @return Insntancia do TipoMembro cadastrado no framework
	 */
	TipoMembro getTipoMembro(ITipoMembro iTipoMembro);
	
	/**Metodo responsavel por retornar uma lista de TiposMembroModelo cadastrados no framework
	 * @return Lista de TiposMemboVisao Cadastrados
	 */
	Collection<ITipoMembroModelo> listaTipoMembroModelo();
	
	/**Metodo resposavel por retornar uma lista de TiposMembroVisao cadastrados no framework
	 * @return Lista de TiposMembroVisao cadastrados
	 */
	Collection<ITipoMembroVisao> listaTipoMembroVisao();
	
	/**Metodo responsavel por retorna um tipo membro visao de um tipomembro modelo.
	 * @param tipoMembroModelo Tipo membro modelo a se procura
	 * @return TipoMembro visao correspondente
	 */
	ITipoMembroVisao getTipoMembroVisao(ITipoMembroModelo tipoMembroModelo);
	
	ITipoMembroModelo getTipoMembroModelo(ITipoMembroVisao tipoMembroVisao);
	
	/**Metodo responsavel por retornar um tipo membro modelo a partir de seu nome e sua versao
	 * @param nome Nome do tipoMembro modelo
	 * @param versao Versao do tipoMembro modelo
	 * @return intancia do tipoMembr modelo
	 */
	ITipoMembroModelo getTipoMembroModelo(String nome, Integer versao);

}
