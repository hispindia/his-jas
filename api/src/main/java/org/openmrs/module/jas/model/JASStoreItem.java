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
import org.openmrs.module.jas.model.JASStore;

public class JASStoreItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private JASStore store;
	
	private JASItem item;
	
	private JASItemSpecification specification;
	
	private long currentQuantity;
	
	private long receiptQuantity;
	
	private long issueQuantity;
	
	private Integer statusIndent;
	
	private Integer reorderQty;
	
	private Integer status;
	
	private Date createdOn;
	
	private long openingBalance;
	
	private long closingBalance;
	
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
	
	public long getCurrentQuantity() {
		return currentQuantity;
	}
	
	public void setCurrentQuantity(long currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	
	public long getReceiptQuantity() {
		return receiptQuantity;
	}
	
	public void setReceiptQuantity(long receiptQuantity) {
		this.receiptQuantity = receiptQuantity;
	}
	
	public long getIssueQuantity() {
		return issueQuantity;
	}
	
	public void setIssueQuantity(long issueQuantity) {
		this.issueQuantity = issueQuantity;
	}
	
	public Integer getStatusIndent() {
		return statusIndent;
	}
	
	public String getStatusIndentName() {
		if (closingBalance < reorderQty) {
			return "Reorder";
		}
		return " ";
	}
	
	public void setStatusIndent(Integer statusIndent) {
		this.statusIndent = statusIndent;
	}
	
	public Integer getReorderQty() {
		return reorderQty;
	}
	
	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}
	
	public long getOpeningBalance() {
		return openingBalance;
	}
	
	public void setOpeningBalance(long openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	public long getClosingBalance() {
		return closingBalance;
	}
	
	public void setClosingBalance(long closingBalance) {
		this.closingBalance = closingBalance;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
