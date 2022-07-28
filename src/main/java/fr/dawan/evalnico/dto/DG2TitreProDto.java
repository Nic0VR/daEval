package fr.dawan.evalnico.dto;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class DG2TitreProDto implements Serializable {
	
	@JsonProperty(value = "full_alias")
	private String fullAlias;
	
	private float score;
	
	private DG2TrainingDto training;

	public String getFullAlias() {
		return fullAlias;
	}

	public void setFullAlias(String fullAlias) {
		this.fullAlias = fullAlias;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public DG2TrainingDto getTraining() {
		return training;
	}

	public void setTraining(DG2TrainingDto training) {
		this.training = training;
	}

	
	
	

}
