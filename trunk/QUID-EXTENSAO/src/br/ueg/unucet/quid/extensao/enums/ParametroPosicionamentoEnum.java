package br.ueg.unucet.quid.extensao.enums;

/**
 * Enum que identifica os tipos de posicionamento dos componentes na tela.
 * @author QUID
 *
 */
public enum ParametroPosicionamentoEnum {

	CENTRALIZAR {
		@Override
		public String toString() {
			return "center";
		}
	},
	DIREITA {
		@Override
		public String toString() {
			return "right";
		}
	},
	ESQUERDA {
		@Override
		public String toString() {
			return "left";
		}
	}
}
