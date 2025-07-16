package br.com.nat.forumhub.infra.security;

import br.com.nat.forumhub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try{
            var alg = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("API forumhub")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .sign(alg);
        }catch (JWTCreationException ex){
            throw new RuntimeException("Erro ao gerar token JWT.", ex.getCause());
        }
    }

    public String getSubject(String tokenJWT){
        try{
            Algorithm alg = Algorithm.HMAC256(this.secret);

            return JWT.require(alg)
                    .withIssuer("API forumhub")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTVerificationException ex){
            throw new BadCredentialsException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusDays(14).toInstant(ZoneOffset.of("-03:00"));
    }
}
