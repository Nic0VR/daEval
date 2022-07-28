package fr.dawan.evalnico.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PromotionDto implements Serializable{

	private long id;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private long titreProfessionnelId;
	private int version;
	private long villeId;
	private List<Long> etudiantsId;
	
	public int getAnnee() {
		return this.dateFin.getYear();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	public LocalDate getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public long getTitreProfessionnelId() {
		return titreProfessionnelId;
	}
	public void setTitreProfessionnelId(long titreProfessionnelId) {
		this.titreProfessionnelId = titreProfessionnelId;
	}
	public long getVilleId() {
		return villeId;
	}
	public void setVilleId(long villeId) {
		this.villeId = villeId;
	}

	public void setEtudiantsId(List<Long> etudiantsId) {
		this.etudiantsId = etudiantsId;
	}
	public List<Long> getEtudiantsId() {
		return etudiantsId;
	}
	
	
	
}
