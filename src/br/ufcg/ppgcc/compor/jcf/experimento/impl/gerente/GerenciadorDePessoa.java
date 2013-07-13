package br.ufcg.ppgcc.compor.jcf.experimento.impl.gerente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Dependente;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.ExcecaoImpostoDeRenda;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Pessoa;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Titular;
import br.ufcg.ppgcc.compor.jcf.experimento.util.Validacao;

public class GerenciadorDePessoa {

	private List<Titular> titularesPersistidos;
	private Map<Titular, List<Dependente>> dependentesPersistidos;

	public GerenciadorDePessoa() {
		this.titularesPersistidos = new ArrayList<Titular>();
		this.dependentesPersistidos = new HashMap<Titular, List<Dependente>>();
	}

	public void criarTitular(Titular titular) {
		this.validaCriacao(titular);
		this.titularesPersistidos.add(titular);
	}

	private void validaCriacao(Titular titular) {
		Validacao validacao = new Validacao();

		if (titular.getNome() == null || titular.getNome().isEmpty()) {
			throw new ExcecaoImpostoDeRenda("Titular sem nome");
		}

		if (titular.getCpf() == null || titular.getCpf().isEmpty()
				|| !validacao.cpf(titular.getCpf())) {
			throw new ExcecaoImpostoDeRenda("Titular sem cpj");
		}

	}

	public List<Titular> listarTitulares() {
		List<Titular> titulares = new ArrayList<Titular>();

		for (Pessoa pessoa : this.titularesPersistidos) {
			if (pessoa instanceof Titular) {
				Titular titularCast = (Titular) pessoa;
				titulares.add(titularCast);
			}
		}
		return titulares;
	}

	public void criarDependente(Titular titular, Dependente dependente) {
		this.validaCriacao(titular, dependente);
		if (this.titularesPersistidos.contains(titular)) {
			List<Dependente> dependentesDoTitular = new ArrayList<Dependente>();

			if (this.dependentesPersistidos.containsKey(titular)
					&& this.dependentesPersistidos.get(titular) != null) {
				dependentesDoTitular = this.dependentesPersistidos.get(titular);
			}

			if (dependentesDoTitular.contains(dependente)) {
				return;
			}
			dependentesDoTitular.add(dependente);
			this.dependentesPersistidos.put(titular, dependentesDoTitular);
		}
	}

	private void validaCriacao(Titular titular, Dependente dependente) {
		Validacao validacao = new Validacao();
		if (dependente == null) {
			throw new ExcecaoImpostoDeRenda("Dependente está null");
		}

		if (dependente.getCpf() == null || dependente.getCpf().isEmpty() || !validacao.cpf(dependente.getCpf())) {
			throw new ExcecaoImpostoDeRenda("Dependente sem cpf");
		}

		if (dependente.getNome() == null || dependente.getNome().isEmpty()) {
			throw new ExcecaoImpostoDeRenda("Dependente sem nome");
		}

		if (dependente.getTipo() <= 0) {
			throw new ExcecaoImpostoDeRenda("Dependente sem tipo");
		}
	}

	public List<Dependente> listarDependentes(Titular titular) {
		return this.dependentesPersistidos.get(titular);
	}

}
