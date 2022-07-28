package fr.dawan.evalnico.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Formation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false,unique=true)
	private String slug;
	
	@Column(nullable = false, unique = true)
	private String titre;
	
	private int duree;
	
	@Column(columnDefinition = "TEXT")
	private String objectifsPedagogiques;
	@Version
	private int version;
	
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
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
	
	
}
