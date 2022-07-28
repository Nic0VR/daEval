package fr.dawan.evalnico.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
@Entity
public class Promotion implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	@ManyToOne
	private TitreProfessionnel titreProfessionnel;
	
	
	@ManyToMany(mappedBy="promotions")
	private List<Etudiant> etudiants;
	@ManyToOne
	private Ville ville;
	@Version
	private int version;

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
	public TitreProfessionnel getTitreProfessionnel() {
		return titreProfessionnel;
	}
	public void setTitreProfessionnel(TitreProfessionnel titreProfessionnel) {
		this.titreProfessionnel = titreProfessionnel;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Ville getVille() {
		return ville;
	}
	public void setVille(Ville ville) {
		this.ville = ville;
	}
	public List<Etudiant> getEtudiants() {
		return etudiants;
	}
	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}
	
	public List<Long> getEtudiantsId(){
		List<Long> etudiantsId = new ArrayList<Long>();
		if(etudiants != null) {
			for(Etudiant etu : etudiants) {
	        	if(etu!=null)
					etudiantsId.add(etu.getId());
			}
		}
		return etudiantsId;
	}

	public void addEtudiant(Etudiant etudiant) {
		if(!this.etudiants.contains(etudiant)) {
			this.etudiants.add(etudiant);
			etudiant.getPromotions().add(this);
		}
		
	}
	
	public void removeEtudiant(Etudiant etudiant) {
		this.etudiants.remove(etudiant);
		etudiant.getPromotions().remove(this);
	}
}
