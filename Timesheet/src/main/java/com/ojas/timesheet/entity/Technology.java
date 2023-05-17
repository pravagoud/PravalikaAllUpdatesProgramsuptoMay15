package com.ojas.timesheet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/*
 * @Author
 * @Akshaya Mahanty
 * */

@Entity
@Table(name = "technology")
public class Technology {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "MODIFY_DATE")
	private Date modifyDate;
	@Column(name = "RECDELETE")
	private int softdelete;

	public Technology() {
		super();
	}

	public Technology(int id, String name, Date createDate, Date modifyDate, int softdelete) {
		super();
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.softdelete = softdelete;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getSoftdelete() {
		return softdelete;
	}

	public void setSoftdelete(int softdelete) {
		this.softdelete = softdelete;
	}

	@Override
	public String toString() {
		return "Technology [id=" + id + ", name=" + name + ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ ", softdelete=" + softdelete + "]";
	}

	
}
