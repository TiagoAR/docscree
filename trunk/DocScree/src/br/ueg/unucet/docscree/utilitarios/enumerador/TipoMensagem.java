package br.ueg.unucet.docscree.utilitarios.enumerador;

/**
 * Enumerador dos tipos de mensagens
 * 
 * @author Diego
 *
 */
public enum TipoMensagem {
	
    ATENCAO(0, "ATENÇÃO"),
    ERRO(1, "ERRO"),
    QUESTAO(2, "INFORMAÇÃO"),
    SUCESSO(4, "SUCESSO");
    
    /**
     * Identificador
     */
    private Integer id;
    /**
     * Valor da descrição do enumerador
     */
    private String descricao;

    /**
     * Construtor do TipoMensagem
     * 
     * @param id
     * @param descricao
     */
    TipoMensagem(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    /**
     * Método que traz a descrição do enumerador
     * 
     * @param id
     * @return descricao
     */
    public String getDescricao(String id) {
        for (TipoMensagem enums : TipoMensagem.values()) {
            if (enums.getId().equals(id)) {
                return enums.getDescricao();
            }
        }
        return null;
    }

    /**
     * Método que traz o Enumerador atravez de seu identificador
     * 
     * @param id
     * @return TipoMensagem
     */
    public static TipoMensagem get(Integer id) {
        for (TipoMensagem gp : TipoMensagem.values()) {
            if (gp.getId().equals(id)) {
                return gp;
            }
        }
        return null;
    }

	/**
	 * @return o id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id o id a ser setado
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return a descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao a descricao a ser setada
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
