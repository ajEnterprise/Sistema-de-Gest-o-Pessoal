package com.br.ajenterprise.api.repositorio;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.br.ajenterprise.api.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepositoryImplementation<Usuario, Long>{

}
