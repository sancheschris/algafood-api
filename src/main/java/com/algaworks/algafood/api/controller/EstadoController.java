package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable Long estadoId) {

        return cadastroEstadoService.buscaOuFalha(estadoId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {

        try {
            return cadastroEstadoService.salvar(estado);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@RequestBody Estado estado, @PathVariable Long estadoId) {

        try {
            Estado estadoAtual = cadastroEstadoService.buscaOuFalha(estadoId);

            BeanUtils.copyProperties(estado, estadoAtual, "id");

            return cadastroEstadoService.salvar(estadoAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
            estadoRepository.deleteById(estadoId);
    }
}
