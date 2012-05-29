package br.ueg.unucet.quid.interfaces;

import java.io.File;
import java.util.Collection;

import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

/**
 * Classe que representa as operacoes de servico sobre a entidade TipoMembroServico.
 * @author QUID
 *
 * @param <T>Entidade que representa o TipoMembroServico dentro do framework. 
 */
public interface ITipoMembroServico<T> extends IServico<T> {
	
	/** Metodo responsavel por realizar o mapeamento dos arquivos dos TipoMembro no framework
	 * @param arquivos Arquivos .jar dos TiposMembros que serao mapeados
	 * @return Retorno de arquivo mapeado
	 */
	Retorno<File, String> mapear(File[] arquivos);
	
	/**Metodo responsavel por retornar uma lista de TiposMembro modelos cadastrados no framework
	 * @return Retorno de pesquisa
	 */
	Retorno<String, Collection<ITipoMembroModelo>> listarTipoMembroModelo();
	
	/**Metodo responsavel por retornar uma lista de TiposMembro visao cadastrados no framework
	 * @return Retorno de pesquisa
	 */
	Retorno<String, Collection<ITipoMembroVisao>> listarTipoMembroVisao();
	
	/**Metodo responsavel por retorna um tipomembro visao a partir do seu modelo
	 * @param tipoMembroModelo Tipomembro modelo a procurar
	 * @return retorno execucao
	 */
	Retorno<String, ITipoMembroVisao> getTipoMembroVisao(ITipoMembroModelo tipoMembroModelo);
	
	Retorno<String, ITipoMembroModelo> getInstancia(String nome, Integer versao);
}
