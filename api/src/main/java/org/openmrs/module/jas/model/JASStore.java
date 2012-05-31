/**
 *  Copyright 2010 Health Information Systems Project of India
 *
 *  This file is part of Jas module.
 *
 *  Jas module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Jas module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jas module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.jas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.openmrs.Role;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStore;

public class JASStore implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Date createdOn;
	
	private String createdBy;
	
	private Role role;
	
	private Boolean retired = false;
	
	private String code;
	
	private JASStore parent;
	
	private Set<JASStore> subStores;
	
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
	
	public JASStore getParent() {
		return parent;
	}
	
	public Set<JASStore> getSubStores() {
		return subStores;
	}
	
	public void setSubStores(Set<JASStore> subStores) {
		this.subStores = subStores;
	}
	
	public void setParent(JASStore parent) {
		this.parent = parent;
	}
	
}
