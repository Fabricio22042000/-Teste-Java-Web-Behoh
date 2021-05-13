package com.inscricaobehoh.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inscricaobehoh.event.RecursoCriadoEvent;
import com.inscricaobehoh.model.Evento;
import com.inscricaobehoh.repository.EventoRepository;
import com.inscricaobehoh.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoResources {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	

	
	@PostMapping
	public ResponseEntity<Evento> salvar(@Valid @RequestBody Evento evento, HttpServletResponse response) {
		evento = eventoRepository.save(evento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, evento.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(evento);
	}
	
	@GetMapping
	public ResponseEntity<List<Evento>> listar() {
		List<Evento> listaEventos = eventoRepository.findAll();
		return ResponseEntity.ok(listaEventos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Evento> findById(@PathVariable Long id) {
		Evento evento = eventoRepository.findById(id).orElse(null);
		if(evento == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(evento);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Evento> atualizar(@PathVariable Long id, @Valid @RequestBody Evento evento){
		Evento eventoSalvo = eventoService.atualizar(id, evento);
		return ResponseEntity.ok(eventoSalvo);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		eventoService.atualizarPropriedadeAtivo(id, ativo);
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		eventoRepository.deleteById(id);
	}
	
	
}
