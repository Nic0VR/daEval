package fr.dawan.evalnico.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import fr.dawan.evalnico.enums.Type;

public class EpreuveDto implements Serializable {

	private long id;
	private String titre;

	private String type;
	
	private long blocCompetencesId;

	private int version;
	
	private String description;
	
	
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

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getBlocCompetencesId() {
		return blocCompetencesId;
	}
	public void setBlocCompetencesId(long blocCompetencesId) {
		this.blocCompetencesId = blocCompetencesId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
