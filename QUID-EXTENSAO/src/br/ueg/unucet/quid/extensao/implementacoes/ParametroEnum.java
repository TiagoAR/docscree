package br.ueg.unucet.quid.extensao.implementacoes;

/**
 * Classe que representa um parametro do Tipo enum
 * @author QUID
 *
 * @param <T>
 */
public class ParametroEnum<T extends Enum<T>> extends Parametro {

	private static final long serialVersionUID = 7859591330844087667L;

	public ParametroEnum(Class<T> classe) {
		super(classe);
	}

	protected Enum cast(String valor2) {
		Enum[] enums = (Enum[]) getClasse().getEnumConstants();
		int i = 0;
		while (!enums[i].toString().equalsIgnoreCase(valor2))
			i++;
		return enums[i];
	}

}