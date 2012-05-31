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

import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItemTransaction;
import org.openmrs.module.jas.util.ActionValue;

public class JASStoreItemIndent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private JASStore store;
	
	private String name;
	
	private Date createdOn;
	
	private Integer subStoreStatus;
	
	private Integer mainStoreStatus;
	
	private JASStoreItemTransaction transaction;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public JASStore getStore() {
		return store;
	}
	
	public void setStore(JASStore store) {
		this.store = store;
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
	
	public Integer getSubStoreStatus() {
		return subStoreStatus;
	}
	
	public void setSubStoreStatus(Integer subStoreStatus) {
		this.subStoreStatus = subStoreStatus;
	}
	
	public Integer getMainStoreStatus() {
		return mainStoreStatus;
	}
	
	public String getMainStoreStatusName() {
		return ActionValue.getIndentMainbStoreName(mainStoreStatus);
	}
	
	public String getSubStoreStatusName() {
		return ActionValue.getIndentSubStoreName(subStoreStatus);
	}
	
	public void setMainStoreStatus(Integer mainStoreStatus) {
		this.mainStoreStatus = mainStoreStatus;
	}
	
	public JASStoreItemTransaction getTransaction() {
		return transaction;
	}
	
	public void setTransaction(JASStoreItemTransaction transaction) {
		this.transaction = transaction;
	}
	
}
