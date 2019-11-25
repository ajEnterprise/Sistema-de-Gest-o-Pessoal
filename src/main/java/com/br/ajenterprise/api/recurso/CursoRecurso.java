package com.br.ajenterprise.api.recurso;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.ajenterprise.api.modelo.Curso;
import com.br.ajenterprise.api.repositorio.CursoRepositorio;

@RestController
@RequestMapping("/cursos")
public class CursoRecurso {
	
	@Autowired
	private CursoRepositorio cursoRepositorio;

	@GetMapping
	public List<Curso> buscarCursos(){
		return cursoRepositorio.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Curso> salvar(@Valid @RequestBody Curso pCurso, HttpServletResponse pResponse) {
		Curso lCurso = cursoRepositorio.save(pCurso);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(lCurso.getCodigo()).toUri();
		pResponse.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(lCurso);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarCurso(@PathVariable Long codigo) {
		Optional<Curso> lCurso = cursoRepositorio.findById(codigo);
		
		return lCurso.isPresent() ? ResponseEntity.ok(lCurso) : ResponseEntity.notFound().build() ;
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		cursoRepositorio.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Curso pCurso) {
		Optional<Curso> lCurso = cursoRepositorio.findById(codigo);
		if(lCurso == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(pCurso, lCurso, "codigo");
		cursoRepositorio.save(lCurso.get());
		
		return ResponseEntity.ok(lCurso);
	}

}
