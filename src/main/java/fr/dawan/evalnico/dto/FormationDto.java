package fr.dawan.evalnico.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;


public class FormationDto implements Serializable {


	private long id;
	private String titre;
	private int duree;
	private String slug;
	private String objectifsPedagogiques;
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
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public String getObjectifsPedagogiques() {
		return objectifsPedagogiques;
	}
	public void setObjectifsPedagogiques(String objectifsPedagogiques) {
		this.objectifsPedagogiques = objectifsPedagogiques;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	
}
