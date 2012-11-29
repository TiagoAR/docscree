package br.ueg.unucet.quid.dominios;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ueg.unucet.quid.enums.TipoErroEnum;
import br.ueg.unucet.quid.excessoes.ArtefatoExcessao;
import br.ueg.unucet.quid.extensao.dominios.Identificavel;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.interfaces.IArtefato;
import br.ueg.unucet.quid.interfaces.IArtefatoControle;
import br.ueg.unucet.quid.utilitarias.FabricaProperties;
import br.ueg.unucet.quid.utilitarias.LeitoraPropertiesUtil;

/**
 * Classe que representa a entidade artefato dentro do framework. Alem de ser
 * uma classe bean, ela e composta de alguns metodos de manipulacao dos servicos
 * e TiposMembros que sao adicionados dentro dela.
 * 
 * @author QUID
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "artefato")
@Service("Artefato")
@Scope("prototype")
public class Artefato extends Identificavel implements IArtefato {

	private int altura;

	private int largura;

	/**
	 * Categoria que pertence o artefato.
	 */
	@ManyToOne
	@JoinColumn(name = "categoria_codigo", insertable = true, updatable = true)
	private Categoria categoria;
	/**
	 * Titulo do artefato.
	 */
	@Transient
	private String titulo;
	/**
	 * Membros que compoem o artefato.
	 */
	@Transient
	private Collection<Membro> membros;
	/**
	 * Servicos que compoem o artefato.
	 */
	@Transient
	private Collection<IServico> servicos;
	/**
	 * Atributo que realiza as operacoes sobre o artefato.
	 */
	@Transient
	@Autowired
	private IArtefatoControle<Artefato, Long> artefatoControle;
	/**
	 * Atributo que realiza a leitura das propriedades do arquivo
	 * mensagens.properties.
	 */
	@Transient
	private LeitoraPropertiesUtil propertiesMessagesUtil = FabricaProperties
			.loadMessages();

	/**
	 * Contrutor padrao da classe que inicializa a lista de membros e servidos
	 * do artefato.
	 */
	public Artefato() {
		this.membros = new ArrayList<Membro>();
		this.servicos = new ArrayList<IServico>();
	}

	public Collection<IServico> getServicos() {
		return servicos;
	}

	public void setServicos(Collection<IServico> servicos) {
		this.servicos = servicos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.unucet.quid.interfaces.IArtefato#getListaMembro()
	 */
	@Override
	public Collection<Membro> getListaMembro() {
		return this.membros;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IArtefato#setListaMembro(java.util.Collection
	 * )
	 */
	@Override
	public void setListaMembro(Collection<Membro> listaTipoMembroModelo) {
		this.membros = listaTipoMembroModelo;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IArtefato#addMembro(br.ueg.unucet.quid.
	 * extensao.dominios.Membro)
	 */
	@Override
	public Retorno<String, Collection<String>> addMembro(Membro membro) {
		Retorno<String, Collection<String>> retorno = new Retorno<String, Collection<String>>();
		try {
			artefatoControle.verificarMembro(membro);
			membros.add(membro);
			retorno.setSucesso(true);
		} catch (ArtefatoExcessao e) {
			retorno.setErro(e);
			retorno.setMensagem(e.getMessage());
			retorno.adicionarParametro(Retorno.PARAMERTO_LISTA,
					e.getParametrosInvalidos());
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.ERRO_SIMPLES);
		}
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IArtefato#removerMembro(br.ueg.unucet.quid
	 * .extensao.dominios.Membro)
	 */
	@Override
	public Retorno<Object, Object> removerMembro(Membro membro) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		if (membros.contains(membro)) {
			retorno.setSucesso(true);
			membros.remove(membro);
		} else {
			retorno.setSucesso(false);
			retorno.setMensagem(propertiesMessagesUtil
					.getValor("membro_nao_encontrado_nesse_artefato"));
		}
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.unucet.quid.interfaces.IArtefato#getListaServico()
	 */
	@Override
	public Collection<IServico> getListaServico() {
		return this.servicos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IArtefato#setListaServico(java.util.Collection
	 * )
	 */
	@Override
	public void setListaServico(Collection<IServico> listaServico) {
		this.servicos = listaServico;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IArtefato#addServico(br.ueg.unucet.quid
	 * .extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<String, Collection<String>> addServico(IServico servico) {
		Retorno<String, Collection<String>> retorno = this.artefatoControle
				.verificarServico(servico);
		if (retorno.isSucesso()) {
			this.servicos.add(servico);
		}
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.unucet.quid.interfaces.IArtefato#removerServico(br.ueg.unucet.
	 * quid.extensao.interfaces.IServico)
	 */
	@Override
	public Retorno<String, String> removerServico(IServico servico) {
		Retorno<String, String> retorno = new Retorno<String, String>();
		if (this.membros.contains(servico)) {
			this.membros.remove(servico);
			artefatoControle.reorganizarServico(servico);
			retorno.setSucesso(true);
		} else {
			retorno.setSucesso(false);
			retorno.setTipoErro(TipoErroEnum.ERRO_SIMPLES);
			retorno.setMensagem(propertiesMessagesUtil
					.getValor("falha_remocao_servico"));
		}
		return retorno;
	}

	@SuppressWarnings({"rawtypes" })
	@Override
	public Retorno<Object, Object> executaServico(String nomeServico, Collection<IParametro<?>> parametros) {
		Retorno<Object, Object> retorno = new Retorno<Object, Object>();
		try {
			IServico servico = (IServico) Class.forName("br.ueg.unucet.quid.servicosquid." + nomeServico).newInstance();
			servico.setListaParametros(parametros);
			Collection<IParametro> executaAcao = servico.executaAcao();
			retorno.setSucesso(true);
			retorno.adicionarParametro(Retorno.PARAMETRO_LISTA_PARAMETRO_SERVICO, executaAcao);
		} catch (Exception e) {
			retorno.setSucesso(false);
			retorno.setErro(e);
			retorno.setMensagem("Não foi possível encontrar a classe do Serviço");
			retorno.setTipoErro(TipoErroEnum.ERRO_FATAL);
		} 
		return retorno;
	}

	// GETTERS AND SETTERS

	@Override
	public String getTitulo() {
		return this.titulo;
	}

	@Override
	public void setTitulo(String titulo) {
		this.titulo = titulo;

	}

	@Override
	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Collection<Membro> getMembros() {
		return membros;
	}

	public void setMembros(Collection<Membro> membros) {
		this.membros = membros;
	}

	public IArtefatoControle<Artefato, Long> getArtefatoControle() {
		return artefatoControle;
	}

	public void setArtefatoControle(
			IArtefatoControle<Artefato, Long> artefatoControle) {
		this.artefatoControle = artefatoControle;
	}

	/**
	 * @return the altura
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * @param altura
	 *            the altura to set
	 */
	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * @return the largura
	 */
	public int getLargura() {
		return largura;
	}

	/**
	 * @param largura
	 *            the largura to set
	 */
	public void setLargura(int largura) {
		this.largura = largura;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.getNome() == null) ? 0 : this.getNome().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Artefato))
			return false;
		Artefato other = (Artefato) obj;
		if (this.getNome() == null) {
			if (other.getNome() != null)
				return false;
		} else if (!this.getNome().equals(other.getNome()))
			return false;
		return true;
	}

}
