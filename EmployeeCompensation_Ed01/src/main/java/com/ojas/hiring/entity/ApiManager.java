package com.ojas.hiring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ApiManager")
@Table(name = "ApiManager")
public class ApiManager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String apiName;
	private String apiUrl;
	private String apiMode;
	private String publishedDate;
	private int visibility;
	public ApiManager() {
		super();
	}
	public ApiManager(String apiName, String apiUrl, String apiMode, String publishedDate, int visibility) {
		super();
		this.apiName = apiName;
		this.apiUrl = apiUrl;
		this.apiMode = apiMode;
		this.publishedDate = publishedDate;
		this.visibility = visibility;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getApiMode() {
		return apiMode;
	}
	public void setApiMode(String apiMode) {
		this.apiMode = apiMode;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	@Override
	public String toString() {
		return "ApiManager [id=" + id + ", apiName=" + apiName + ", apiUrl=" + apiUrl + ", apiMode=" + apiMode
				+ ", publishedDate=" + publishedDate + ", visibility=" + visibility + "]";
	}

}
