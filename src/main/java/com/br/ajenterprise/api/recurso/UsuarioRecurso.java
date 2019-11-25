package com.br.ajenterprise.api.recurso;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.ajenterprise.api.modelo.Usuario;
import com.br.ajenterprise.api.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRecurso {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@GetMapping
	public List<Usuario> buscarUsuarios(){
		return usuarioRepositorio.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario pUsuario, HttpServletResponse pResponse) {
		Usuario lUsuario = usuarioRepositorio.save(pUsuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(lUsuario.getCodigo()).toUri();
		pResponse.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(lUsuario);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarUsuario(@PathVariable Long codigo) {
		Optional<Usuario> lUsuario = usuarioRepositorio.findById(codigo);
		
		return lUsuario.isPresent() ? ResponseEntity.ok(lUsuario) : ResponseEntity.notFound().build() ;
	}

}
