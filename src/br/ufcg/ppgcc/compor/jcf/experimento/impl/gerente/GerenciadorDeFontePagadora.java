package br.ufcg.ppgcc.compor.jcf.experimento.impl.gerente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufcg.ppgcc.compor.jcf.experimento.fachada.ExcecaoImpostoDeRenda;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FontePagadora;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Titular;
import br.ufcg.ppgcc.compor.jcf.experimento.util.Validacao;

public class GerenciadorDeFontePagadora {

	private Map<Titular, List<FontePagadora>> fontesPersistidas;

	public GerenciadorDeFontePagadora() {
		this.fontesPersistidas = new HashMap<Titular, List<FontePagadora>>();
	}

	public void criarFontePagadora(Titular titular, FontePagadora fonte) {
		this.validaCriacao(titular, fonte);
		
		List<FontePagadora> fontesDoTitular = new ArrayList<FontePagadora>();
		
		if (this.fontesPersistidas.containsKey(titular)
				&& this.fontesPersistidas.get(titular) != null) {
			fontesDoTitular = this.fontesPersistidas.get(titular);
		}

		for (FontePagadora fonteEach : fontesDoTitular) {
			if (fonteEach.equals(fonte)) {
				return;
			}
		}
		fontesDoTitular.add(fonte);
		this.fontesPersistidas.put(titular, fontesDoTitular);
	}

	private void validaCriacao(Titular titular, FontePagadora fonte) {
		Validacao validacao = new Validacao();
		
		if(fonte == null){
			throw new ExcecaoImpostoDeRenda("Fonte está nulo");
		}
		
		if(fonte.getNome() == null || fonte.getNome().isEmpty()) {
			throw new ExcecaoImpostoDeRenda("Fonte sem nome");
		}

		if (fonte.getCpfCnpj() == null || fonte.getCpfCnpj().isEmpty()
				|| !validacao.cpfOuCnpj(fonte.getCpfCnpj())) {
			throw new ExcecaoImpostoDeRenda("Fonte sem cnpj/cpf");
		}

		if (fonte.getRendimentoRecebidos() <= 0) {
			throw new ExcecaoImpostoDeRenda("Fonte sem rendimentos recebidos");
		}

	}

	public List<FontePagadora> obterFontes(Titular titular) {
		return this.fontesPersistidas.get(titular);
	}
}
