package br.ueg.unucet.quid.dominios;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe que representa o mapeamento ArtefatoServico dentro do framework.
 * @author QUID
 *
 */
@Entity
@Table(name="artefato_servico")
public class ArtefatoServico {
	/**
	 * Codigo identificador do mapeamento
	 */
	@Id
	private Long codigo;
	/**
	 * Artefato do mapeamento.
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	private Artefato artefato;
	/**
	 * Servico do mapeamento.
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	private Servico servico;
	/**
	 * Servico anterior que sera executado antes do atributo servico.
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	private Servico servicoAnterior;
	/**
	 * Servico posterior que sera executa depois do atributo servico.
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	private Servico servicoProximo;
	/**
	 * Atributo que informa de o servico anterior e obrigatoria a execucao. Caso seje o atributo servico
	 * so sera executado caso o servico anterior retorne sucesso de execucao.
	 */
	private boolean anteriorObrigatorio;
	/**
	 * Vetor de bytes dos parametros do servico.
	 */
	private byte[] parametrosServico;
	
	/**
	 * Vetor de bytes dos parametros do TipoMembro referente ao servico.
	 */
	private byte[] parametrosTipoMembro;
	
	//GETTERS AND SETTERS
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Artefato getArtefato() {
		return artefato;
	}
	public void setArtefato(Artefato artefato) {
		this.artefato = artefato;
	}
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public byte[] getParametrosServico() {
		return parametrosServico;
	}
	public void setParametrosServico(byte[] parametrosServico) {
		this.parametrosServico = parametrosServico;
	}
	public byte[] getParametrosTipoMembro() {
		return parametrosTipoMembro;
	}
	public void setParametrosTipoMembro(byte[] parametrosTipoMembro) {
		this.parametrosTipoMembro = parametrosTipoMembro;
	}
	public Servico getServicoAnterior() {
		return servicoAnterior;
	}
	public void setServicoAnterior(Servico servicoAnterior) {
		this.servicoAnterior = servicoAnterior;
	}
	public Servico getServicoProximo() {
		return servicoProximo;
	}
	public void setServicoProximo(Servico servicoProximo) {
		this.servicoProximo = servicoProximo;
	}
	public boolean isAnteriorObrigatorio() {
		return anteriorObrigatorio;
	}
	public void setAnteriorObrigatorio(boolean anteriorObrigatorio) {
		this.anteriorObrigatorio = anteriorObrigatorio;
	}
	
	
	
}
