package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;



import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void whenCadastroCozinhaComDadosCorretos_ThenDeveAtribuirId() {
		// cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testarCadastroCozinhaSemNome() {
		// cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		cadastroCozinha.excluir(1L);
	}

	@Test(expected = CozinhaNaoEncontradaException.class)
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		cadastroCozinha.excluir(5L);
	}

}
