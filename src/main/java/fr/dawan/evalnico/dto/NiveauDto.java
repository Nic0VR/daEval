package fr.dawan.evalnico.dto;

public class NiveauDto {
	private long id;
	private int version;
	private String description;
	private String codeCouleurHexa;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodeCouleurHexa() {
		return codeCouleurHexa;
	}
	public void setCodeCouleurHexa(String codeCouleurHexa) {
		this.codeCouleurHexa = codeCouleurHexa;
	}
	
	
}
