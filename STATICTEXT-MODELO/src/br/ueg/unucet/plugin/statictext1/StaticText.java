package br.ueg.unucet.plugin.statictext1;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;


@SuppressWarnings("serial")
public class StaticText extends SuperTipoMembroModelo implements ITipoMembroModelo {

	public static final String TEXTO_FIXO = "TEXTO_FIXO";
	
	public StaticText(){
		setNome("statictext");
		setDescricao("texto fixo");
		setVersao(1);
		setRevisao(1);
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo#getTipoDadoPersistencia()
	 */
	@Override
	public Integer getTipoDadoPersistencia() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo#isEstatico()
	 */
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Collection<String> getNomesParametrosInvalidos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IParametro<?>> getParametrosAdicionais() {
		Parametro<String> parametro = new Parametro<String>(String.class);
		parametro.setNome(TEXTO_FIXO);
		parametro.setRotulo("Texto a ser exibido");
		parametro.setDominioEntrada(DominioEntradaEnum.CARACTERES);
		parametro.setObrigatorio(true);
		
		Collection<IParametro<?>> parametrosCombo = new ArrayList<IParametro<?>>();
		parametrosCombo.add(parametro);
		return parametrosCombo;
	}
	

}
