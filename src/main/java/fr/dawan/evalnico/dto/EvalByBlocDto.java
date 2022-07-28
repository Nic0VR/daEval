package fr.dawan.evalnico.dto;

import fr.dawan.evalnico.entities.BlocCompetences;

public class EvalByBlocDto {

	private BlocCompetences blocCompetences;
	private double moyenne;
	private double moyennePromo;

	public BlocCompetences getBlocCompetences() {
		return blocCompetences;
	}

	public void setBlocCompetences(BlocCompetences blocCompetences) {
		this.blocCompetences = blocCompetences;
	}

	public double getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(double moyenne) {
		this.moyenne = moyenne;
	}

	public double getMoyennePromo() {
		return moyennePromo;
	}

	public void setMoyennePromo(double moyennePromo) {
		this.moyennePromo = moyennePromo;
	}

}
