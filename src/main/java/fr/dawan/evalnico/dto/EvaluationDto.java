package fr.dawan.evalnico.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
public class EvaluationDto implements Serializable{


	private long id;
	
	private long epreuveId;
	private double note;
	private long etudiantId;

	private int version;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public double getNote() {
		return note;
	}
	public void setNote(double note) {
		this.note = note;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public long getEpreuveId() {
		return epreuveId;
	}
	public void setEpreuveId(long epreuveId) {
		this.epreuveId = epreuveId;
	}
	public long getEtudiantId() {
		return etudiantId;
	}
	public void setEtudiantId(long etudiantId) {
		this.etudiantId = etudiantId;
	}
	
	
}
