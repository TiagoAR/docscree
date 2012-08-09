package br.ueg.unucet.docscree.visao.compositor;

import java.util.List;

import br.ueg.unucet.quid.dominios.Equipe;
import br.ueg.unucet.quid.dominios.Modelo;
import br.ueg.unucet.quid.dominios.Projeto;

public interface IProjetoVisao {

	public void salvarSessaoProjeto();
	
	public List<Projeto> getListaProjetos();
	
	public List<Equipe> getListaEquipes();
	
	public List<Modelo> getListaModelos();

}
