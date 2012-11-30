package br.ueg.unucet.plugin.imagem1;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

@SuppressWarnings("serial")
public class Imagem extends SuperTipoMembroModelo implements
		ITipoMembroModelo {
	/**
	 * 
	 */
	public static final String IMAGEM_LARGURA = "IMAGEM_LARGURA";
	public static final String IMAGEM_ALTURA  = "IMAGEM_ALTURA";
	
	public Imagem(){
		setNome("imagem");
		setDescricao("Upload de Imagem");
		setVersao(1);
		setRevisao(1);
	}

	@Override
	public Integer getTipoDadoPersistencia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEstatico() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMascara() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DominioEntradaEnum getDominio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValorValido(Object valor) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isIndexavel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParametrosValidos() {
		
		return true;
	}

	@Override
	public Collection<String> getNomesParametrosInvalidos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IParametro<?>> getParametrosAdicionais() {
		Parametro<Integer> parametro = new Parametro<Integer>(Integer.class);
		parametro.setNome(IMAGEM_LARGURA);
		parametro.setRotulo("Largura da Imagem");
		parametro.setDominioEntrada(DominioEntradaEnum.NUMERICO);
		parametro.setObrigatorio(true);
		
		Collection<IParametro<?>> parametrosImagem = new ArrayList<IParametro<?>>();
		parametrosImagem.add(parametro);
		
		parametro = new Parametro<Integer>(Integer.class);
		parametro.setNome(IMAGEM_ALTURA);
		parametro.setRotulo("Autura da Imagem");
		parametro.setDominioEntrada(DominioEntradaEnum.NUMERICO);
		parametro.setObrigatorio(true);
		
		parametrosImagem.add(parametro);
		
		
		return parametrosImagem;
	}

}
