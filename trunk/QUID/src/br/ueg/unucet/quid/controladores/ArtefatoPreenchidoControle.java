package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.interfaces.IArtefatoPreenchidoControle;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoPreenchido;
import br.ueg.unucet.quid.servicos.QuidService;

@Service("ArtefatoPreenchidoControle")
public class ArtefatoPreenchidoControle extends GenericControle<ArtefatoPreenchido, Long> implements IArtefatoPreenchidoControle<ArtefatoPreenchido, Long> {

	@Autowired
	private IDAOArtefatoPreenchido<ArtefatoPreenchido, Long> daoArtefatoPreenchido;
	
	QuidService quidService = QuidService.obterInstancia();
	
	@Override
	public IDAO<ArtefatoPreenchido, Long> getDao() {
		return this.daoArtefatoPreenchido;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#salvar(java.lang.Object)
	 */
	@Override
	@Transactional(value = "transactionManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void inserir(ArtefatoPreenchido entidade) throws QuidExcessao {
		if(antesInserir(entidade)){
			getDao().inserir(entidade);
			depoisInserir();
		}
		
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IControle#alterar(java.lang.Object)
	 */
	@Override
	@Transactional(value = "transactionManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void alterar(ArtefatoPreenchido entidade) throws QuidExcessao {
		if(antesAlterar(entidade)){
			getDao().alterar(entidade);
			depoisAlterar();
		}
		
	}
	
	public Collection<ArtefatoPreenchido> pesquisarArtefatoPreenchido(ArtefatoPreenchido entidade) throws QuidExcessao {
		//FILTRAR PARA LISTAR ULTIMA VERSAO, VERSÃO MAIS REVISÃO
		Map<String, ArtefatoPreenchido> mapa = new HashMap<String, ArtefatoPreenchido>();
		Collection<ArtefatoPreenchido> lista = pesquisarPorRestricao(entidade, new String[]{"artefatoPreenchido.codigo"});
		Collection<ArtefatoPreenchido> retorno = new ArrayList<ArtefatoPreenchido>();
		for (ArtefatoPreenchido artefatoPreenchido : lista) {
			artefatoPreenchido = getPorId(ArtefatoPreenchido.class, artefatoPreenchido.getCodigo());
			String id = String.valueOf(artefatoPreenchido.getArtefato()) + "-" + String.valueOf(artefatoPreenchido.getModelo());
			if (mapa.containsKey(id)) {
				ArtefatoPreenchido velho = mapa.get(id);
				if (velho.getVersao().compareTo(artefatoPreenchido.getVersao()) >= 0) {
					if (velho.getRevisao().compareTo(artefatoPreenchido.getRevisao()) < 0) {
						retorno.remove(velho);
						retorno.add(artefatoPreenchido);
						mapa.put(id, artefatoPreenchido);
					}
				} else {
					
				}
			}
			retorno.add(artefatoPreenchido);
		}
		return retorno;
	}

}
