package br.insper.iam.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> getEventos(@RequestParam(required = false) String acao) {
        return eventoService.getEventos(acao);
    }

    @PostMapping
    public Evento saveEvento(@RequestBody Evento evento) {
        return eventoService.saveEvento(evento);
    }
}
