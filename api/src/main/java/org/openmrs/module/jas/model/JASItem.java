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

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.jas.model.JASItemCategory;
import org.openmrs.module.jas.model.JASItemSpecification;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.model.JASItemUnit;
import org.openmrs.module.jas.util.ActionValue;

public class JASItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private JASItemUnit unit;
	
	private JASItemCategory category;
	
	private JASItemSubCategory subCategory;
	
	private Set<JASItemSpecification> specifications;
	
	private Date createdOn;
	
	private String createdBy;
	
	private int attribute;
	
	private Integer reorderQty;
	
	private Integer consumption;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNameShort() {
		return StringUtils.isNotBlank(name) && name.length() > 30 ? name.substring(0, 30) + "..." : name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public JASItemUnit getUnit() {
		return unit;
	}
	
	public void setUnit(JASItemUnit unit) {
		this.unit = unit;
	}
	
	public JASItemCategory getCategory() {
		return category;
	}
	
	public void setCategory(JASItemCategory category) {
		this.category = category;
	}
	
	public JASItemSubCategory getSubCategory() {
		return subCategory;
	}
	
	public void setSubCategory(JASItemSubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
	public Set<JASItemSpecification> getSpecifications() {
		return specifications;
	}
	
	public void setSpecifications(Set<JASItemSpecification> specifications) {
		this.specifications = specifications;
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
	
	public int getAttribute() {
		return attribute;
	}
	
	public String getAttributeName() {
		return ActionValue.getItemAttribute(attribute);
	}
	
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
	
	public Integer getConsumption() {
		return consumption;
	}
	
	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}
	
	public Integer getReorderQty() {
		return reorderQty;
	}
	
	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}
	
}
