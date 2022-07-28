package fr.dawan.evalnico.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DG2LocationDto implements Serializable{

	private String slug;
	private String name;
	
	@JsonIgnore private String address;
	@JsonIgnore private double latitude;
	@JsonIgnore private double longitude;
	@JsonIgnore private String zipCode;
	@JsonIgnore private String city;
	@JsonIgnore private String country;
	@JsonIgnore private String furtherInfo;
	@JsonIgnore private String  mapUrl;
	@JsonIgnore private String pictureFile;
	@JsonIgnore private boolean office;
	@JsonIgnore private boolean isPmi;
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFurtherInfo() {
		return furtherInfo;
	}
	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getPictureFile() {
		return pictureFile;
	}
	public void setPictureFile(String pictureFile) {
		this.pictureFile = pictureFile;
	}
	public boolean isOffice() {
		return office;
	}
	public void setOffice(boolean office) {
		this.office = office;
	}
	public boolean getIsPmi() {
		return isPmi;
	}
	public void setIsPmi(boolean isPmi) {
		this.isPmi = isPmi;
	}
	
	
}
