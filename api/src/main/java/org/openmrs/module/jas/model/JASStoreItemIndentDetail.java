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

import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemSpecification;
import org.openmrs.module.jas.model.JASStoreItemIndent;

public class JASStoreItemIndentDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private JASStoreItemIndent indent;
	
	private JASItem item;
	
	private JASItemSpecification specification;
	
	private Integer quantity;
	
	private Integer mainStoreTransfer;
	
	private Date createdOn;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Integer getMainStoreTransfer() {
		return mainStoreTransfer;
	}
	
	public void setMainStoreTransfer(Integer mainStoreTransfer) {
		this.mainStoreTransfer = mainStoreTransfer;
	}
	
	public JASStoreItemIndent getIndent() {
		return indent;
	}
	
	public void setIndent(JASStoreItemIndent indent) {
		this.indent = indent;
	}
	
	public JASItem getItem() {
		return item;
	}
	
	public void setItem(JASItem item) {
		this.item = item;
	}
	
	public JASItemSpecification getSpecification() {
		return specification;
	}
	
	public void setSpecification(JASItemSpecification specification) {
		this.specification = specification;
	}
	
}
