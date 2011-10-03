package org.openmrs.module.jas.model;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.Role;

public class JASStore implements  Serializable {

	
	 private static final long serialVersionUID = 1L;
	  private Integer id;
	  private String name;
	  private Date createdOn;
	  private String createdBy;
	  private Role role;
	  private Boolean retired = false;
	  private String code;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	  
	  
	  
}
