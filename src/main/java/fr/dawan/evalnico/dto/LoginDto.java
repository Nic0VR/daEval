package fr.dawan.evalnico.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginDto implements Serializable {
	
	private String email;
	private String motDePasse;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	
	
}
