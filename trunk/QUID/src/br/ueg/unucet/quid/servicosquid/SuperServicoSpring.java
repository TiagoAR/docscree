package br.ueg.unucet.quid.servicosquid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ueg.unucet.quid.dominios.Artefato;
import br.ueg.unucet.quid.dominios.Projeto;
import br.ueg.unucet.quid.dominios.Usuario;
import br.ueg.unucet.quid.extensao.implementacoes.SuperServico;

public abstract class SuperServicoSpring extends SuperServico {

	protected ApplicationContext appContext;

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperServico#inicializacaoConstrutor()
	 */
	@Override
	protected void inicializacaoConstrutor() {
		this.appContext = new ClassPathXmlApplicationContext("/br/ueg/unucet/quid/configuracoes/applicationContext.xml");
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperServico#getArtefatoModelo()
	 */
	@Override
	public Artefato getArtefatoModelo() {
		return (Artefato) super.getArtefatoModelo();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperServico#getProjetoEscolhido()
	 */
	@Override
	public Projeto getProjetoEscolhido() {
		return (Projeto) super.getProjetoEscolhido();
	}

	/* (non-Javadoc)
	 * @see br.ueg.unucet.quid.extensao.implementacoes.SuperServico#getUsuario()
	 */
	@Override
	public Usuario getUsuario() {
		return (Usuario) super.getUsuario();
	}
	

}
