package br.insper.iam.usuario.controller;

import br.insper.iam.usuario.Usuario;
import br.insper.iam.usuario.UsuarioController;
import br.insper.iam.usuario.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTests {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    private MockMvc mockMvc;


    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(usuarioController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void test_GetUsuarios() throws Exception {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario("João", "joao@example.com"),
                new Usuario("Maria", "maria@example.com")
        );


        Mockito.when(usuarioService.getUsuarios()).thenReturn(usuarios);


        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(usuarios)));
    }

    @Test
    void test_PostUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@teste.com");

        Mockito.when(usuarioService.saveUsuario(usuario))
                .thenReturn(usuario);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/usuario")
                            .content(objectMapper.writeValueAsString(usuario))
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(usuario)));

    }
}
