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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemSpecification;
import org.openmrs.module.jas.model.JASStoreItemTransaction;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;

public class JASStoreItemTransactionDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private JASStoreItemTransaction transaction;
	
	private JASItem item;
	
	private JASItemSpecification specification;
	
	private Integer quantity;
	
	private Integer currentQuantity;
	
	private Integer issueQuantity;
	
	private BigDecimal unitPrice;
	
	private BigDecimal totalPrice;
	
	private BigDecimal VAT;
	
	private String companyName;
	
	private Date dateManufacture;
	
	private Date createdOn;
	
	private long openingBalance;
	
	private long closingBalance;
	
	private JASStoreItemTransactionDetail parent;
	
	private Set<JASStoreItemTransactionDetail> subDetails;
	
	private Date receiptDate;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public BigDecimal getVAT() {
		return VAT;
	}
	
	public void setVAT(BigDecimal vAT) {
		VAT = vAT;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Date getDateManufacture() {
		return dateManufacture;
	}
	
	public void setDateManufacture(Date dateManufacture) {
		this.dateManufacture = dateManufacture;
	}
	
	public Date getReceiptDate() {
		return receiptDate;
	}
	
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getCurrentQuantity() {
		return currentQuantity;
	}
	
	public void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	
	public JASStoreItemTransactionDetail getParent() {
		return parent;
	}
	
	public void setParent(JASStoreItemTransactionDetail parent) {
		this.parent = parent;
	}
	
	public Set<JASStoreItemTransactionDetail> getSubDetails() {
		return subDetails;
	}
	
	public void setSubDetails(Set<JASStoreItemTransactionDetail> subDetails) {
		this.subDetails = subDetails;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	
	public Integer getIssueQuantity() {
		return issueQuantity;
	}
	
	public void setIssueQuantity(Integer issueQuantity) {
		this.issueQuantity = issueQuantity;
	}
	
	public JASStoreItemTransaction getTransaction() {
		return transaction;
	}
	
	public void setTransaction(JASStoreItemTransaction transaction) {
		this.transaction = transaction;
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
