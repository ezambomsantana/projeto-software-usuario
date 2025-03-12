package br.insper.iam.usuario.service;

import br.insper.iam.usuario.CountUsuarioDTO;
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

        // preparacao
        Mockito.when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        // chamada
        List<Usuario> usuarios = usuarioService.getUsuarios();

        //verificacaoes
        Assertions.assertEquals(0, usuarios.size());
    }

    @Test
    void test_saveUsuarioSuccessfully() {

        Usuario usuario = new Usuario();
        usuario.setEmail("a@a.com");
        usuario.setNome("Teste");

        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioReturn = usuarioService.saveUsuario(usuario);

        Assertions.assertEquals("a@a.com", usuarioReturn.getEmail());
        Assertions.assertEquals("Teste", usuarioReturn.getNome());

    }

    @Test
    void test_saveUsuarioErrorEmailIsNull() {

        Usuario usuario = new Usuario();
        usuario.setNome("a@a.com");

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> usuarioService.saveUsuario(usuario));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

    }

    @Test
    void test_saveUsuarioErrorNomeIsNull() {

        Usuario usuario = new Usuario();
        usuario.setEmail("Teste");

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> usuarioService.saveUsuario(usuario));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

    }

    @Test
    void test_deleteSuccessfully() {

        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("aa@aa.com");

        Mockito.when(usuarioRepository.findByEmail("aa@aa.com")).thenReturn(usuario);
        Mockito.doNothing().when(usuarioRepository).delete(usuario);

        usuarioService.deleteUsuario("aa@aa.com");

        Mockito.verify(usuarioRepository, Mockito.times(1))
                .delete(usuario);

    }

    @Test
    void test_findUsuarioByEmailSuccessfully() {

        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("aa@aa.com");

        Mockito.when(usuarioRepository.findByEmail("aa@aa.com")).thenReturn(usuario);

        Usuario usuarioResp = usuarioService.findUsuarioByEmail("aa@aa.com");

        Assertions.assertEquals(usuario.getEmail(), usuarioResp.getEmail());
        Assertions.assertEquals(usuario.getNome(), usuarioResp.getNome());

    }

    @Test
    void test_findUsuarioByEmailWhenEmailIsInvalid() {

        Mockito.when(usuarioRepository.findByEmail("aa@aa.com")).thenReturn(null);

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> usuarioService.findUsuarioByEmail("aa@aa.com"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void test_countUsuarioSuccessfully() {

        Mockito.when(usuarioRepository.count()).thenReturn(10l);

        CountUsuarioDTO countUsuarioDTO = usuarioService.countUsuarios();
        Assertions.assertEquals(10l, countUsuarioDTO.count());
    }


}
