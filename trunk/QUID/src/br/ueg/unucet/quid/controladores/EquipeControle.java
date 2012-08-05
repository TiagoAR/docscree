package br.ueg.unucet.quid.controladores;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.excessoes.EquipeExcessao;
import br.ueg.unucet.quid.excessoes.QuidExcessao;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;
import br.ueg.unucet.quid.interfaces.IDAO;
import br.ueg.unucet.quid.interfaces.IDAOEquipe;
import br.ueg.unucet.quid.interfaces.IEquipeControle;

/**
 * Classe responsavel por realizar as operacoes sobre EQUIPE
 * @author QUID
 *
 */
@Service("EquipeControle")
public class EquipeControle extends GenericControle<Equipe, Long> implements IEquipeControle<Equipe, Long>{

	/**
	 * Atributo responsavel por realizar as operacoes de persistencia.
	 */
	@Autowired
	private IDAOEquipe<Equipe, Long> daoEquipe;
	
	/**
	 * Atriburo que representa a equipe atual sobre o qual esta se realizando as operacoes.
	 */
	private Equipe equipe;
	
	@Override
	public IDAO<Equipe, Long> getDao() {
		return this.daoEquipe;
	}

	public IDAOEquipe<Equipe, Long> getDaoEquipe() {
		return daoEquipe;
	}

	public void setDaoEquipe(IDAOEquipe<Equipe, Long> daoEquipe) {
		this.daoEquipe = daoEquipe;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#antesInserir(java.lang.Object)
	 */
	public boolean antesInserir(Equipe equipe) throws QuidExcessao{
		this.equipe = equipe;
		verificarEquipe();
		verificarUsuarios();
		verificarDuplicidadeEquipe();
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#antesAlterar(java.lang.Object)
	 */
	public boolean antesAlterar(Equipe equipe) throws QuidExcessao{
		this.equipe = equipe;
		verificarEquipe();
		verificarUsuarios();
		return true;
	}

	/**
	 * Metodo responsavel por realizar a verificação de duplicidade de cadastro de uma equipe.
	 * Tal verificação e feita 
	 * @throws EquipeExcessao Escessao caso a equipe ja esteja cadastrada no framework
	 */
	private void verificarDuplicidadeEquipe() throws EquipeExcessao {
		Equipe equipeBusca = new Equipe();
		equipeBusca.setNome(equipe.getNome());
		if(isCadastrada(equipeBusca, new String[]{"equipe.codigo", "equipe.nome"})){
			throw new EquipeExcessao(propertiesMessagesUtil.getValor("equipe_cadastrada"));
		}
	}

	/**
	 * Metodo responsavel por realizar a verificacao dos usuarios dentro de uma equipe, para verificar se todos estao ativos ou se existe algum usuario na equipe.
	 * @throws EquipeExcessao excessao caso exista algum usuario inativo na equipe, ou se a equipe nao tiver nenhum usuário.
	 */
	private void verificarUsuarios() throws EquipeExcessao {
		Collection<String> usuariosInativos = new ArrayList<String>();
		if(equipe.getEquipeUsuarios()!= null){
			for (EquipeUsuario equipeUsuario : equipe.getEquipeUsuarios()) {
				if(equipeUsuario.getUsuario().getStatus() == StatusEnum.INATIVO){
					usuariosInativos.add(equipeUsuario.getUsuario().getNome());
				}
			}
		}
		if(!usuariosInativos.isEmpty()){
			throw new EquipeExcessao(propertiesMessagesUtil.getValor("erro_usuarios_inativos_equipe"), usuariosInativos);
		}
		
	}

	/**
	 * Metodo responsavel por verificar o campo nome da equipe foi informado.
	 * @throws EquipeExcessao Excessão caso o nome da equipe nao for informado.
	 */
	private void verificarEquipe()throws EquipeExcessao {
		Collection<String> camposobrigatorios = new ArrayList<String>();
		if(equipe.getNome() == null || equipe.getNome().equals("")){
			camposobrigatorios.add("nome");
			EquipeExcessao equipeExcessao = new EquipeExcessao(propertiesMessagesUtil.getValor("erro_atributo_nao_informado"), camposobrigatorios);
			throw equipeExcessao;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.controladores.GenericControle#verificarListaCadastrada(java.util.Collection)
	 */
	public boolean verificarListaCadastrada(Collection<Equipe> lista){
		boolean result = super.verificarListaCadastrada(lista);
		if(result){
			result = false;
			for (Equipe equipe : lista) {
				if(equipe.getNome().equals(this.equipe.getNome())){
					result = true;
				}
			}
		}
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.interfaces.IEquipeControle#pesquisarEquipe(java.lang.Object)
	 */
	public Collection<Equipe> pesquisarEquipe(Equipe equipe){
		Collection<Equipe> equipes = pesquisarPorRestricao(equipe, new String[]{"equipe.codigo", "equipe.nome"});
		Collection<Equipe> retorno = new ArrayList<Equipe>();
		for (Equipe equipe2 : equipes) {
			equipe2 = getPorId(Equipe.class, equipe2.getCodigo());
			retorno.add(equipe2);
		}
		return retorno;
	}
	
	
	
}
