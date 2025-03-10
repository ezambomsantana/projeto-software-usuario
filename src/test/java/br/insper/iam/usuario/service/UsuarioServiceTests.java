package br.insper.iam.usuario.service;

import br.insper.iam.usuario.Usuario;
import br.insper.iam.usuario.UsuarioRepository;
import br.insper.iam.usuario.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTests {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void test_findAllUsuariosWhenUsuariosIsEmpty() {

        Mockito.when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        List<Usuario> usuarios = usuarioService.getUsuarios();

        Assertions.assertEquals(0, usuarios.size());
    }

    @Test
    void test_saveUsuarioSuccessfully(){
        Usuario usuario = new Usuario();
        usuario.setEmail("a@a.com");
        usuario.setNome("Teste");

        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioReturn = usuarioService.saveUsuario(usuario);

        Assertions.assertEquals("a@a.com", usuarioReturn.getEmail());
        Assertions.assertEquals("Teste", usuarioReturn.getNome());
    }

    @Test
    void test_saveUsuarioErrorEmailIsNull(){
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> usuarioService.saveUsuario(usuario));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
        void test_saveUsuarioErrorNameIsNull(){
            Usuario usuario = new Usuario();
            usuario.setEmail("a@a.com");

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> usuarioService.saveUsuario(usuario));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void test_findUsuarioByEmail(){
        Mockito.when(usuarioRepository.findByEmail("aasd")).thenReturn(null);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> usuarioService.findUsuarioByEmail("aasd"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    }

    @Test
    void test_deleteSuccessfully(){

        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("a@a.com");
        Mockito.when(usuarioRepository.findByEmail("a@a.com")).thenReturn(usuario);
        Mockito.doNothing().when(usuarioRepository).delete(usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(usuario);
    }
}
