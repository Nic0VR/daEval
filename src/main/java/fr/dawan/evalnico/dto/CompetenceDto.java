package fr.dawan.evalnico.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

public class CompetenceDto implements Serializable {

	private long id;
	private String titre;
	private String description;
	private long blocCompetencesId ;
	private int version;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public long getBlocCompetencesId() {
		return blocCompetencesId;
	}
	public void setBlocCompetencesId(long blocCompetencesId) {
		this.blocCompetencesId = blocCompetencesId;
	}
	
	
}
