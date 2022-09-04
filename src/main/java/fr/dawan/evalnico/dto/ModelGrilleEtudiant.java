package fr.dawan.evalnico.dto;

public class ModelGrilleEtudiant {

	private FormationDto formation;
	private InterventionDto intervention;
	private PositionnementDto positionnement;
	private String nomCompletFormateur;
	
	public FormationDto getFormation() {
		return formation;
	}
	public void setFormation(FormationDto formation) {
		this.formation = formation;
	}
	public InterventionDto getIntervention() {
		return intervention;
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
