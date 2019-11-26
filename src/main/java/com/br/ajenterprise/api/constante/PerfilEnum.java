package com.br.ajenterprise.api.constante;

import lombok.Getter;

@Getter
public enum PerfilEnum {
	
	ADMINISTRADOR("Administrador"),
	GESTOR("Gestor"),
	USUARIO("usuário");

	private String descricao;
	
	private PerfilEnum(String descricao) {
		this.descricao = descricao;
	}

}
