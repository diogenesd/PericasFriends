package com.bluecontinental.pericasfriends;

/**
 * @author Di�genes Ademir Domingos
 *
 * FUNDA��O UNIVERSIDADE REGIONAL DE BLUMENAU
 * CENTRO DE CI�NCIAS EXATAS E NATURAIS
 * DEPARTAMENTO DE SISTEMAS E COMPUTA��O
 * PR�TICA EM REDES DE COMPUTADORES
 * PROF. FRANCISCO ADELL P�RICAS
 * 5� Laborat�rio: Software Cliente de Troca de Mensagens com Jogo
 * 
 * Classe Bean do cliente de acesso ao servidor
 */
public class Cliente {
	
	/**
	 * Identifica��o do cliente
	 */
	public String id;
	/**
	 * Senha de acesso ao servidor
	 */
	public String password;
	
	public Cliente() {
		super();
	}
	public Cliente(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
	// GETS, SETTERS AND TOSTRING
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", password=" + password + "]";
	}
	
}
