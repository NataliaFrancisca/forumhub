package br.com.nat.forumhub.controller;

import br.com.nat.forumhub.domain.curso.CursoRegistro;
import br.com.nat.forumhub.domain.curso.CursoService;
import br.com.nat.forumhub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid CursoRegistro cursoRegistro, UriComponentsBuilder uriBuilder, Authentication auth){
        var usuario = (Usuario) auth.getPrincipal();
        var curso = this.cursoService.registrar(cursoRegistro, usuario);

        var uri = uriBuilder
                .path("/cursos/{id}")
                .buildAndExpand(curso.id())
                .toUri();

        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id){
        var curso = this.cursoService.buscar(id);
        return ResponseEntity.ok(curso);
    }
}
