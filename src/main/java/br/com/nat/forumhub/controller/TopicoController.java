package br.com.nat.forumhub.controller;

import br.com.nat.forumhub.domain.topico.*;
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
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid TopicoRegistro topicoRegistro, UriComponentsBuilder uriBuilder, Authentication auth){
        var usuario = (Usuario) auth.getPrincipal();
        var topico = this.topicoService.registrar(topicoRegistro, usuario);

        var uri = uriBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topico.id())
                .toUri();

        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id){
        var topico = this.topicoService.buscar(id);
        return ResponseEntity.ok(topico);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDadosDetalhadosComRespostas>> buscarTodas(
            @RequestParam Long cursoId,
            @PageableDefault(size = 5, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable paginacao){
        var page = this.topicoService.buscarTodos(paginacao, cursoId);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid TopicoAtualizado topicoAtualizado, Authentication auth){
        var usuario = (Usuario) auth.getPrincipal();
        var topico = this.topicoService.editar(topicoAtualizado, usuario);
        return ResponseEntity.ok(topico);
    }

    @PatchMapping
    @Transactional
    public ResponseEntity atualizarStatus(@RequestBody @Valid TopicoStatusAtualizado topicoStatusAtualizado, Authentication auth){
        var usuario = (Usuario) auth.getPrincipal();
        var topico = this.topicoService.atualizarStatus(topicoStatusAtualizado, usuario);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id, Authentication auth){
        var usuario = (Usuario) auth.getPrincipal();
        this.topicoService.deletar(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
