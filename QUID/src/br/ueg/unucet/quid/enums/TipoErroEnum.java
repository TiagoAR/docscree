package br.ueg.unucet.quid.enums;

import br.ueg.unucet.quid.configuracoes.MarcadorConfiguracoes;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;


/**
 * Enum que representa o nivel dos erros disparados pelo framework.
 * @author QUID
 *
 */
public enum TipoErroEnum {
	INFORMATIVO {
		public String toString() {
			LeitoraPropertiesUtil leitor = new LeitoraPropertiesUtil(MarcadorConfiguracoes.class, "mensagens.properties");
			return (String) leitor.getValor("tipoInfo");
		}

	},
	ALERTA {
		public String toString() {
			LeitoraPropertiesUtil loader = new LeitoraPropertiesUtil(MarcadorConfiguracoes.class, "mensagens.properties");
			return (String) loader.getValor("tipoWarn");
		}
	}

	,
	ERRO_SIMPLES {
		public String toString() {
			LeitoraPropertiesUtil loader = new LeitoraPropertiesUtil(MarcadorConfiguracoes.class, "mensagens.properties");
			return (String) loader.getValor("tipoError");
		}
	}

	,
	ERRO_FATAL {
		public String toString() {
			LeitoraPropertiesUtil loader = new LeitoraPropertiesUtil(MarcadorConfiguracoes.class, "mensagens.properties");
			return (String) loader.getValor("tipoFatal");
		}
	}

}
