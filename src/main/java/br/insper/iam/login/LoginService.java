package br.insper.iam.login;

import br.insper.iam.usuario.Usuario;
import br.insper.iam.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RedisTemplate<String, Usuario> tokens;

    public String login(LoginDTO loginDTO) {

        Usuario usuario = usuarioService.validateUser(
                loginDTO.email(), loginDTO.password());

        String token = UUID.randomUUID().toString();

        tokens.opsForValue().set(token, usuario);

        return token;

    }

    public Usuario validateToken(String token) {
        if (tokens.opsForValue().get(token) == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return tokens.opsForValue().get(token);
    }
}
