package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.ArtefatoPreenchido;
import br.ueg.unucet.quid.dominios.ValoresArtefato;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;
import br.ueg.unucet.quid.interfaces.IArtefatoPreenchidoControle;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOArtefatoPreenchido;
import br.ueg.unucet.quid.utilitarias.SerializadorObjetoUtil;

@Service("ArtefatoPreenchidoControle")
public class ArtefatoPreenchidoControle extends GenericControle<ArtefatoPreenchido, Long> implements IArtefatoPreenchidoControle<ArtefatoPreenchido, Long> {

	@Autowired
	private IDAOArtefatoPreenchido<ArtefatoPreenchido, Long> daoArtefatoPreenchido;
	
	@Autowired
	private IArtefatoControle<Artefato, Long> artefatoControle;
	
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
	
	@Override
	public Collection<ArtefatoPreenchido> pesquisarArtefatoPreenchido(ArtefatoPreenchido entidade) throws QuidExcessao {
		Map<String, ArtefatoPreenchido> mapa = new HashMap<String, ArtefatoPreenchido>();
		Collection<ArtefatoPreenchido> lista = pesquisarPorRestricao(entidade, new String[]{"artefatopreenchido.codigo"});
		Collection<ArtefatoPreenchido> retorno = new ArrayList<ArtefatoPreenchido>();
		for (ArtefatoPreenchido artefatoPreenchido : lista) {
			artefatoPreenchido = getPorId(ArtefatoPreenchido.class, artefatoPreenchido.getCodigo());
			String id = String.valueOf(artefatoPreenchido.getArtefato()) + "-" + String.valueOf(artefatoPreenchido.getModelo());
			if (mapa.containsKey(id)) {
				ArtefatoPreenchido velho = mapa.get(id);
				if (velho.getVersao().compareTo(artefatoPreenchido.getVersao()) < 0 
						|| (velho.getVersao().compareTo(artefatoPreenchido.getVersao()) == 0 
								&& velho.getRevisao().compareTo(artefatoPreenchido.getRevisao()) < 0)) {
					retorno.remove(velho);
					retorno.add(artefatoPreenchido);
					mapa.put(id, artefatoPreenchido);
				}
			} else {
				mapa.put(id, artefatoPreenchido);
				retorno.add(artefatoPreenchido);
			}
		}
		return retorno;
	}
	
	@Override
	public Artefato obterArtefato(ArtefatoPreenchido artefatoPreenchido) throws QuidExcessao {
		Artefato artefato = new Artefato();
		artefato.setCodigo(artefatoPreenchido.getArtefato());
		Artefato artefatoCarregado = this.artefatoControle.carregarArtefato(artefato);
		List<ValoresArtefato> lista = (List<ValoresArtefato>) artefatoPreenchido.getValoresArtefatos();
		// TODO melhorar desempenho
		ValoresArtefato valoresArtefato = null;
		for (Membro membro : artefatoCarregado.getMembros()) {
			for (ValoresArtefato valor : lista) {
				if (valor.getMembro().equals(membro.getCodigo())) {
					valoresArtefato = valor;
					break;
				}
			}
			if (valoresArtefato != null) {
				lista.remove(valoresArtefato);
				membro.getTipoMembroModelo().setValor(SerializadorObjetoUtil.toObject(valoresArtefato.getValor()));
			}
		}
		return artefatoCarregado;
	}

}
