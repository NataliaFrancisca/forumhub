package br.com.nat.forumhub.controller;

import br.com.nat.forumhub.domain.resposta.RespostaAtualizada;
import br.com.nat.forumhub.domain.resposta.RespostaDadosDetalhados;
import br.com.nat.forumhub.domain.resposta.RespostaRegistro;
import br.com.nat.forumhub.domain.resposta.RespostaService;
import br.com.nat.forumhub.domain.usuario.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    private Usuario acessarUsuario(Authentication auth){
        return (Usuario) auth.getPrincipal();
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid RespostaRegistro respostaRegistro, UriComponentsBuilder uriBuilder, Authentication auth){
        var usuario = this.acessarUsuario(auth);
        var resposta = this.respostaService.registrar(respostaRegistro, usuario);

        var uri = uriBuilder
                .path("/topicos/{id}")
                .buildAndExpand(resposta.id())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id){
        var resposta = this.respostaService.buscar(id);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping
    public ResponseEntity<Page<RespostaDadosDetalhados>> buscarTodos(
            @RequestParam Long topicoId,
            @PageableDefault(size = 5, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable paginacao
            ){
        var page = this.respostaService.buscarTodas(paginacao, topicoId);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid RespostaAtualizada respostaAtualizada, Authentication auth){
        var usuario = this.acessarUsuario(auth);
        var resposta = this.respostaService.editar(respostaAtualizada, usuario);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id, Authentication auth){
        var usuario = this.acessarUsuario(auth);
        this.respostaService.deletar(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
