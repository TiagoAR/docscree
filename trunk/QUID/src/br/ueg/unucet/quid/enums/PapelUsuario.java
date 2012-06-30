package br.ueg.unucet.quid.enums;

/**
 * Enum que representa os papeis de usuários no framework
 * 
 * @author Diego
 *
 */
public enum PapelUsuario {

	DESENVOLVEDOR(0, "Desenvolvedor"),
	GERENTE(1, "Gerente"),
	MONTADOR(2, "Montador"),
	PREENCHEDOR(3, "Preenchedor");
	
	/**
	 * id do enumerador
	 */
	private Integer id;
	/**
	 * descrição do enumerador
	 */
	private String descricao;
	
	/**
	 * Defaul Construtor
	 * @param id
	 * @param descricao
	 */
	PapelUsuario(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	/**
	 * Retorna enumerador pelo ID passado
	 * 
	 * @param id
	 * @return enum
	 */
    public static PapelUsuario get(Integer id) {
        for (PapelUsuario pu : PapelUsuario.values()) {
            if (pu.getId().equals(id)) {
                return pu;
            }
        }
        return null;
    }

    /**
     * Busca descrição do enumerador pelo seu ID
     * 
     * @param id
     * @return descrição do enum
     */
    public String getDescricao(String id) {
        for (PapelUsuario enums : PapelUsuario.values()) {
            if (enums.getId().equals(id)) {
                return enums.getDescricao();
            }
        }
        return null;
    }

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}
