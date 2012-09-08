package br.ueg.unucet.plugin.inputtextvisao1;

import java.util.Collection;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroVisao;

public class InputText extends SuperTipoMembro implements ITipoMembroVisao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7574516772274099933L;
	
	public InputText() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setListaParametros(Collection<IParametro<?>> parametros) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<IParametro<?>> getListaParametros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getVersao() {
		return 1;
	}

	@Override
	public Integer getRevisao() {
		return 1;
	}

	@Override
	public String getNome() {
		return "inputvisao";
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlataforma() {
		return "ZK";
	}

	@Override
	public Icon getImagemIlustrativa() {
		return new ImageIcon(getClass().getResource("/input.png"));
	}

	@Override
	public Object getVisaoPreenchimento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVisaoVisualizacao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEntradaValida(Object valor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEventoMascara() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNomeTipoMembroModelo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValor(Object valor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDescricao(String descricao) {
		// TODO Auto-generated method stub
		
	}

}
