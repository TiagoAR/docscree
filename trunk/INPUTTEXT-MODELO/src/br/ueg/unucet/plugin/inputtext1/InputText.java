package br.ueg.unucet.plugin.inputtext1;

import java.util.Collection;

import br.ueg.unucet.quid.extensao.enums.DominioEntradaEnum;
import br.ueg.unucet.quid.extensao.implementacoes.SuperTipoMembroModelo;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;


public class InputText extends SuperTipoMembroModelo implements ITipoMembroModelo {

	
	public InputText(){
		setNome("inputtext");
		setDescricao("entrada de testo");
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
		// TODO Auto-generated method stub
		return null;
	}
	

}
