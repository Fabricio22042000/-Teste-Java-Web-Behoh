package com.inscricaobehoh.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.inscricaobehoh.model.Usuario;
import com.inscricaobehoh.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Usuario atualizar(Long id, Usuario usuario) {
		Usuario usuarioBanco = buscarUsuarioPeloId(id);
		
		BeanUtils.copyProperties(usuario, usuarioBanco, "id");
		return usuarioRepository.save(usuarioBanco);
	}

	private Usuario buscarUsuarioPeloId(Long id) {
		Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
		
		if(usuarioBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioBanco;
	}
}
