package fr.dawan.evalnico.dto;

public class DG2TrainingDto {

	private String title;
	 private String duration;
	 private String description = null;
	 private String slug;
	 private String alias;
	 private String fullAlias;
	 private float standardPrice;
	 private float customPrice;
	 private float customPriceExtra;
	 private float remotelyPrice;
	 private String objectives;
	 private String prerequisites;
	 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFullAlias() {
		return fullAlias;
	}
	public void setFullAlias(String fullAlias) {
		this.fullAlias = fullAlias;
	}
	public float getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(float standardPrice) {
		this.standardPrice = standardPrice;
	}
	public float getCustomPrice() {
		return customPrice;
	}
	public void setCustomPrice(float customPrice) {
		this.customPrice = customPrice;
	}
	public float getCustomPriceExtra() {
		return customPriceExtra;
	}
	public void setCustomPriceExtra(float customPriceExtra) {
		this.customPriceExtra = customPriceExtra;
	}
	public float getRemotelyPrice() {
		return remotelyPrice;
	}
	public void setRemotelyPrice(float remotelyPrice) {
		this.remotelyPrice = remotelyPrice;
	}
	public String getObjectives() {
		return objectives;
	}
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}
	public String getPrerequisites() {
		return prerequisites;
	}
	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

}
