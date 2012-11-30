package br.ueg.unucet.quid.servicosquid;

import java.util.ArrayList;
import java.util.Collection;

import br.ueg.unucet.quid.dominios.MembroDocScree;
import br.ueg.unucet.quid.extensao.dominios.Membro;
import br.ueg.unucet.quid.extensao.implementacoes.Parametro;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;
import br.ueg.unucet.quid.extensao.interfaces.IServico;
import br.ueg.unucet.quid.extensao.interfaces.ITipoMembroModelo;

public class ServicoValidacaoSimples extends ServicoValidacao {

	@Override
	public ITipoMembroModelo getTipoMembroModelo() {
		return null;
	}

	@Override
	public void setTipoMembroModelo(ITipoMembroModelo tipoMembroModelo) {
	}

	@Override
	public String getNomeTipoMembroModelo() {
		return null;
	}

	@Override
	public Integer getVersaoTipoMembroModelo() {
		return null;
	}

	@Override
	public boolean isListaParametrosValidos() {
		return true;
	}

	@Override
	public boolean isListaParametrosCompativeis() {
		return true;
	}

	@Override
	public Collection<String> getNomesParametrosInvalidos() {
		return null;
	}

	@Override
	public IServico getAnterior() {
		return null;
	}

	@Override
	public IServico getProximo() {
		return null;
	}

	@Override
	public void setAnterior(IServico servico) {
	}

	@Override
	public void setProximo(IServico servico) {
	}

	@Override
	public boolean isAnteriorObrigatorio() {
		return false;
	}

	@Override
	public void setAnteriroObrigatorio(boolean obrigatorio) {
	}

	@Override
	public Integer getVersao() {
		return 1;
	}

	@Override
	public Integer getRevisao() {
		return 1;
	}

	@Override
	public String getNome() {
		return "Serviço de Validação Simples";
	}

	@Override
	public String getDescricao() {
		return "Serviço que valida os valores a serem associados aos Membros";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Collection<IParametro> efetuaAcao() throws Exception {
		Collection<IParametro> resposta = new ArrayList<IParametro>();
		Collection<Membro> lista = new ArrayList<Membro>();
		Collection<String> listaErros = new ArrayList<String>();
		Parametro<Collection> parametroErro = new Parametro<Collection>(Collection.class);
		parametroErro.setNome(PARAMETRO_LISTA_ERROS);
		parametroErro.setValorClass(listaErros);
		Parametro<Collection> parametroResposta = new Parametro<Collection>(Collection.class);
		parametroResposta.setNome(PARAMETRO_MEMBROS_RESULTADO);
		parametroResposta.setValorClass(lista);
		resposta.add(parametroResposta);
		resposta.add(parametroErro);
		for (MembroDocScree membroDocScree : getMapaMembros().values()) {
			if (membroDocScree.getTipoMembroVisao().isEntradaValida(getMapaValoresMembros().get(membroDocScree.getIdComponente()))) {
				membroDocScree.getTipoMembroVisao().getMembro().getTipoMembroModelo().setValor(getMapaValoresMembros().get(membroDocScree.getIdComponente()));
				lista.add(membroDocScree.getTipoMembroVisao().getMembro());
			} else {
				listaErros.add("O valor do Membro " + membroDocScree.getTipoMembroVisao().getMembro().getNome() + " é inválido");
			}
		}
		return resposta;
	}

}
