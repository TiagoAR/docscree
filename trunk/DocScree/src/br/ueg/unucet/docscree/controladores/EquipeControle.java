package br.ueg.unucet.docscree.controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import br.ueg.unucet.docscree.visao.compositor.SuperCompositor;
import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.EquipeUsuario;
import br.ueg.unucet.quid.dominios.Retorno;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.enums.PapelUsuario;
import br.ueg.unucet.quid.extensao.enums.StatusEnum;

/**
 * Controle específico de Equipes
 * 
 * @author Diego
 * 
 */
public class EquipeControle extends GenericoControle<Equipe> {

	private UsuarioControle usuarioControle = new UsuarioControle();

	@Override
	public boolean acaoSalvar() {
		Equipe equipe = new Equipe();
		equipe.setNome("Teste");
		equipe.setStatus(StatusEnum.ATIVO);
		List<EquipeUsuario> listaEquipesUsuarios = new ArrayList<EquipeUsuario>();
		Collection<Usuario> listaUsuarios = super.getFramework()
				.pesquisarUsuario(new Usuario()).getParametros()
				.get(Retorno.PARAMERTO_LISTA);
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getStatus().equals(StatusEnum.ATIVO)) {
				EquipeUsuario equipeUsuario = new EquipeUsuario();
				//usuario.getEquipesUsuario().add(equipeUsuario);
				equipeUsuario.setUsuario(usuario);
				equipeUsuario.setEquipe(equipe);
				equipeUsuario.setPapelUsuario(PapelUsuario.PREENCHEDOR);
				listaEquipesUsuarios.add(equipeUsuario);
			}
		}
		HashSet<EquipeUsuario> hashSet = new HashSet<EquipeUsuario>();
		hashSet.addAll(listaEquipesUsuarios);
		equipe.setEquipeUsuarios(hashSet);
		Retorno<String, Collection<String>> retorno;
		retorno = super.getFramework().inserirEquipe(equipe);
		if (retorno.isSucesso()) {
			return true;
		} else {
			return false;
		}
		/*
		 * if (!super.isUsuarioComum()) { Retorno<String, Collection<String>>
		 * retorno; if (super.getEntidade().getCodigo() == null) { retorno =
		 * super.getFramework().inserirEquipe(super.getEntidade()); } else {
		 * retorno = super.getFramework().alterarEquipe(super.getEntidade()); }
		 * return super.montarRetorno(retorno); } else {
		 * montarMensagemErroPermissao("Usuário"); return false; }
		 */
	}

	@Override
	public boolean acaoListar() {
		if (!super.isUsuarioComum()) {
			return super.acaoListar();
		} else {
			montarMensagemErroPermissao("Usuário");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios() {
		if (this.usuarioControle.acaoListar()) {
			return (List<Usuario>) this.usuarioControle.getLista();
		} else {
			return new ArrayList<Usuario>();
		}
	}

	@Override
	public boolean acaoExcluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setarEntidadeVisao(SuperCompositor<?> pVisao) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void montarMensagemErro(
			Retorno<String, Collection<String>> retorno) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Retorno<String, Collection<Equipe>> executarListagem() {
		return super.getFramework().pesquisarEquipe(new Equipe());
	}

}
