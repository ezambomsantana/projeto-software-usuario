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


}
