package com.inscricaobehoh.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.inscricaobehoh.model.Evento;
import com.inscricaobehoh.repository.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	
	public Evento atualizar(Long id, Evento evento) {
		Evento eventoBanco = buscarEventoPeloId(id);
		
		BeanUtils.copyProperties(evento, eventoBanco, "id");
		return eventoRepository.save(eventoBanco);
	}


	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Evento evento = buscarEventoPeloId(id);
		evento.setAtivo(ativo);
		eventoRepository.save(evento);
	}
	
	private Evento buscarEventoPeloId(Long id) {
		Evento eventoBanco = eventoRepository.findById(id).orElse(null);
		
		if(eventoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return eventoBanco;
	}
	
}
