package br.com.nat.forumhub.controller;

import br.com.nat.forumhub.domain.usuario.Usuario;
import br.com.nat.forumhub.domain.usuario.UsuarioAtualizado;
import br.com.nat.forumhub.domain.usuario.UsuarioRegistro;
import br.com.nat.forumhub.domain.usuario.UsuarioService;
import br.com.nat.forumhub.domain.usuario.autenticacao.UsuarioAutenticacao;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UsuarioAutenticacao usuario){
        var resposta = this.usuarioService.login(usuario);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity registro(@RequestBody @Valid UsuarioRegistro usuario, UriComponentsBuilder uriBuilder){
        System.out.println("dados registro: " + usuario.email());
        var resposta = this.usuarioService.registrar(usuario);

        var uri = uriBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(resposta.id())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id, Authentication auth){
        var usuarioAutenticado = (Usuario) auth.getPrincipal();
        var usuario = this.usuarioService.buscar(id, usuarioAutenticado);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid UsuarioAtualizado usuarioAtualizado, Authentication auth){
        var usuarioAutenticado = (Usuario) auth.getPrincipal();
        var usuario = this.usuarioService.atualizar(usuarioAtualizado, usuarioAutenticado);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deletar(Authentication auth){
        var usuarioAutenticado = (Usuario) auth.getPrincipal();
        this.usuarioService.deletar(usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }
}
