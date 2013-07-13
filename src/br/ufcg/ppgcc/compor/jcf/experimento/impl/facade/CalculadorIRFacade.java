package br.ufcg.ppgcc.compor.jcf.experimento.impl.facade;

import java.util.ArrayList;
import java.util.List;

import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Dependente;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FachadaExperimento;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.FontePagadora;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.GastoDedutivel;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Pessoa;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Resultado;
import br.ufcg.ppgcc.compor.jcf.experimento.fachada.Titular;
import br.ufcg.ppgcc.compor.jcf.experimento.impl.gerente.GerenciadorDeFontePagadora;
import br.ufcg.ppgcc.compor.jcf.experimento.impl.gerente.GerenciadorDePessoa;
import br.ufcg.ppgcc.compor.jcf.experimento.util.CalculoImpostoRenda;

public class CalculadorIRFacade implements FachadaExperimento {

	private GerenciadorDePessoa gerenciadorDePessoa;
	private GerenciadorDeFontePagadora gerenciadorDeFontePagadora;

	public CalculadorIRFacade() {
		this.gerenciadorDePessoa = new GerenciadorDePessoa();
		this.gerenciadorDeFontePagadora = new GerenciadorDeFontePagadora();
	}

	@Override
	public void criarNovoTitular(Titular titular) {
		if (titular != null) {
			this.gerenciadorDePessoa.criarTitular(titular);
		}
	}

	@Override
	public List<Titular> listarTitulares() {
		return this.gerenciadorDePessoa.listarTitulares();
	}

	@Override
	public void criarFontePagadora(Titular titular, FontePagadora fonte) {
		this.gerenciadorDeFontePagadora.criarFontePagadora(titular, fonte);

	}

	@Override
	public Resultado declaracaoCompleta(Titular titular) {
		CalculoImpostoRenda calculadorImposto = new CalculoImpostoRenda();
		Resultado resultado = new Resultado();

		List<FontePagadora> fontesPagadoras = this.gerenciadorDeFontePagadora.obterFontes(titular);

		double valor = calculadorImposto.totalRecebido(fontesPagadoras);
		valor = calculadorImposto.descontoDependentes(valor, this.listarDependentes(titular));
		resultado.setImpostoDevido(calculadorImposto.impostoDevido(valor));
		return resultado;
	}

	@Override
	public void criarDependente(Titular titular, Dependente dependente) {
		this.gerenciadorDePessoa.criarDependente(titular, dependente);

	}

	@Override
	public List<FontePagadora> listarFontes(Titular titular) {
		return this.gerenciadorDeFontePagadora.obterFontes(titular);
	}

	@Override
	public List<Dependente> listarDependentes(Titular titular) {
		List<Dependente> dependentes = this.gerenciadorDePessoa.listarDependentes(titular);
		
		if(dependentes == null){
			dependentes = new ArrayList<Dependente>();
		}
		return dependentes;
	}

	@Override
	public void criarGastoDedutivel(Titular titular, Pessoa realizador,
			GastoDedutivel gastoDedutivel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GastoDedutivel> listarGastosDedutiveis(Titular titular,
			Pessoa realizador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado relatorioSimplificado(Titular titular) {
		// TODO Auto-generated method stub
		return null;
	}

}
