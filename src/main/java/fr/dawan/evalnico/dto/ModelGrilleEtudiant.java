package fr.dawan.evalnico.dto;

import fr.dawan.evalnico.entities.Niveau;

public class ModelGrilleEtudiant {

	private FormationDto formation;
	private InterventionDto intervention;
	private PositionnementDto positionnement;
	private String nomCompletFormateur;
	private NiveauDto niveauDebut;
	private NiveauDto niveauFin;
	public FormationDto getFormation() {
		return formation;
	}
	public void setFormation(FormationDto formation) {
		this.formation = formation;
	}
	public InterventionDto getIntervention() {
		return intervention;
	}

	public NiveauDto getNiveauDebut() {
		return niveauDebut;
	}
	public void setNiveauDebut(NiveauDto niveauDebut) {
		this.niveauDebut = niveauDebut;
	}
	public NiveauDto getNiveauFin() {
		return niveauFin;
	}
	public void setNiveauFin(NiveauDto niveauFin) {
		this.niveauFin = niveauFin;
	}
	public void setIntervention(InterventionDto intervention) {
		this.intervention = intervention;
	}
	public PositionnementDto getPositionnement() {
		return positionnement;
	}
	public void setPositionnement(PositionnementDto positionnement) {
		this.positionnement = positionnement;
	}
	public String getNomCompletFormateur() {
		return nomCompletFormateur;
	}
	public void setNomCompletFormateur(String nomCompletFormateur) {
		this.nomCompletFormateur = nomCompletFormateur;
	}
	
	
}
