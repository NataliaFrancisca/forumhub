package br.com.nat.forumhub.domain.usuario;

import br.com.nat.forumhub.domain.usuario.autenticacao.UsuarioAutenticacao;
import br.com.nat.forumhub.domain.usuario.validacoes.ValidadorDadosSaoUnicos;
import br.com.nat.forumhub.infra.security.DadosTokenJWT;
import br.com.nat.forumhub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidadorDadosSaoUnicos dadosSaoUnicos;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    public DadosTokenJWT login(UsuarioAutenticacao usuario){
        try{
            var token = new UsernamePasswordAuthenticationToken(
                    usuario.email(),
                    usuario.senha()
            );

            var auth = this.manager.authenticate(token);
            var tokenJWT = this.tokenService.gerarToken((Usuario) auth.getPrincipal());

            return new DadosTokenJWT(tokenJWT);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public UsuarioDadosDetalhados registrar(UsuarioRegistro usuarioRegistro){
        this.dadosSaoUnicos.validar(usuarioRegistro);

        Usuario usuario = new Usuario(usuarioRegistro);
        usuario.setSenha(passwordEncoder.encode(usuarioRegistro.senha()));

        return new UsuarioDadosDetalhados(this.usuarioRepository.save(usuario));
    }

    public UsuarioDadosDetalhados atualizar(UsuarioAtualizado usuarioAtualizado, Usuario usuarioRequisicao){
        var usuario = this.usuarioRepository.getReferenceById(usuarioRequisicao.getId());
        usuario.atualizar(usuarioAtualizado);
        return new UsuarioDadosDetalhados(usuario);
    }

    public void deletar(Usuario usuarioRequisicao){
        this.usuarioRepository.deleteById(usuarioRequisicao.getId());
    }

    public UsuarioDadosDetalhados buscar(Long id, Usuario usuarioRequisicao) {
        if (!Objects.equals(id, usuarioRequisicao.getId())){
            throw new AccessDeniedException("Você não tem permissão para acessar esses dados.");
        }

        var usuario = this.usuarioRepository.getReferenceById(usuarioRequisicao.getId());
        return new UsuarioDadosDetalhados(usuario);
    }
}
