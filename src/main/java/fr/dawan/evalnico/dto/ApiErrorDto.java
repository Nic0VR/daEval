package fr.dawan.evalnico.dto;

import java.io.Serializable;

public class ApiErrorDto implements Serializable {

	private int errorCode;
	private String message;
	private String path;
	
	/**
	 * @return errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}
}