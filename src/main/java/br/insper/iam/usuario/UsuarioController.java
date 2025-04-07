package br.insper.iam.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/teste")
    public String teste(){
        return "TESTE";
    }

    @GetMapping
    public List<Usuario> getUsuarios(
            @RequestHeader String user,
            @RequestHeader String password
    ) {

        usuarioService.validateUser(user, password);

        return usuarioService.getUsuarios();
    }

    @GetMapping("/{email}")
    public Usuario getUsuario(@PathVariable String email) {
        return usuarioService.findUsuarioByEmail(email);
    }

    @PostMapping
    public Usuario saveUsuario(@RequestBody Usuario usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("/{email}")
    public void deleteUsuario(@PathVariable String email) {
        usuarioService.deleteUsuario(email);
    }

    @GetMapping("/usuario/count")
    public CountUsuarioDTO countUsuario() {
        return usuarioService.countUsuarios();
    }
}
