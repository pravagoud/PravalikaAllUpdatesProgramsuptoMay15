package com.ojas.timesheet.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;

@Entity
@Table(name = "FILE_DATA")
@Builder
public class FileData {

	private Long id;
	private String name;
	private String type;
	private byte[] approvedFile;
	private int timesheet_Id;

	public FileData() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FILE_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FILE_TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Lob
	@Column(name = "APPROVED_FILE")
	public byte[] getApprovedFile() {
		return approvedFile;
	}

	public void setApprovedFile(byte[] approvedFile) {
		this.approvedFile = approvedFile;
	}
	
	@Column(name = "timesheet_Id")
	public int getTimesheet_Id() {
		return timesheet_Id;
	}

	public void setTimesheet_Id(int timesheet_Id) {
		this.timesheet_Id = timesheet_Id;
	}

	public FileData(Long id, String name, String type, byte[] approvedFile, int timesheet_Id) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.approvedFile = approvedFile;
		this.timesheet_Id = timesheet_Id;
	}

	@Override
	public String toString() {
		return "FileData [id=" + id + ", name=" + name + ", type=" + type + ", approvedFile="
				+ Arrays.toString(approvedFile) + ", timesheet_Id=" + timesheet_Id + "]";
	}
	
	
}
