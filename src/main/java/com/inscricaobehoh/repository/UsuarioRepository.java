package com.inscricaobehoh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inscricaobehoh.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
}
