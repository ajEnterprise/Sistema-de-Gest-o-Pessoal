package com.br.ajenterprise.api.recurso;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.br.ajenterprise.api.dto.CursoDto;
import com.br.ajenterprise.api.modelo.Curso;
import com.br.ajenterprise.api.servico.CursoServico;

@RestController
@RequestMapping("/cursos")
public class CursoRecurso {
	
	@Autowired
	private CursoServico cursoServico;

	@GetMapping
	public Page<Curso> buscarCursos(CursoDto pCurso, Pageable pageable){
		return cursoServico.buscarCursos(pCurso, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Curso> salvar(@Valid @RequestBody Curso pCurso, HttpServletResponse pResponse) {
		Curso lCurso = cursoServico.salvar(pCurso);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(lCurso.getCodigo()).toUri();
		pResponse.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(lCurso);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarCurso(@PathVariable Long codigo) {
		Curso lCurso = cursoServico.buscarCurso(codigo);
		
		return ResponseEntity.ok(lCurso);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		cursoServico.remover(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<?> alterar(@PathVariable Long codigo, @Valid @RequestBody Curso pCurso) {
		Curso lCurso = cursoServico.alterar(codigo, pCurso);
		return ResponseEntity.ok(lCurso);
	}
	
	@PutMapping("/{codigo}/nota}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> lancarNota(@PathVariable Long codigo, @Valid @RequestBody Float nota) {
		Curso lCurso = cursoServico.lancarNota(codigo, nota);
		return ResponseEntity.ok(lCurso);
	}

}
