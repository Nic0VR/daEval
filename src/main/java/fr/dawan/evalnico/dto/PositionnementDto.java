package fr.dawan.evalnico.dto;

import javax.persistence.ManyToOne;
import javax.persistence.Version;

import fr.dawan.evalnico.entities.Etudiant;
import fr.dawan.evalnico.entities.Intervention;
import fr.dawan.evalnico.entities.Niveau;

public class PositionnementDto {
	
	private long id;
	private int version;
	private long etudiantId;
	private long interventionId;
	private long niveauDebutId;
	private long niveauFinId;
	
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
	public long getEtudiantId() {
		return etudiantId;
	}
	public void setEtudiantId(long etudiantId) {
		this.etudiantId = etudiantId;
	}
	public long getInterventionId() {
		return interventionId;
	}
	public void setInterventionId(long interventionId) {
		this.interventionId = interventionId;
	}
	public long getNiveauDebutId() {
		return niveauDebutId;
	}
	public void setNiveauDebutId(long niveauDebutId) {
		this.niveauDebutId = niveauDebutId;
	}
	public long getNiveauFinId() {
		return niveauFinId;
	}
	public void setNiveauFinId(long niveauFinId) {
		this.niveauFinId = niveauFinId;
	}
	
	
}
