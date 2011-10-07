/**
 *  Copyright 2011 Health Information Systems Project of India
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

import org.apache.commons.lang.StringUtils;

/**
 * <p> Class: JASStoreDrugTransaction </p>
 * <p> Package: org.openmrs.module.jas.model </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 5, 2011 1:28:02 PM </p>
 * <p> Update date: Jan 5, 2011 1:28:02 PM </p>
 **/
public class JASStoreDrugTransactionDetail implements  Serializable {

	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private JASStoreDrugTransaction transaction;
	 private JASDrug drug;
	 private JASDrugFormulation formulation;
	 private Integer quantity ;
	 private Integer currentQuantity ;
	 private Integer issueQuantity;
	 private BigDecimal unitPrice;
	 private BigDecimal totalPrice;
	 private BigDecimal VAT;
	 private BigDecimal otherTaxes;
	 private BigDecimal rate;
	 //Receipt VAT and Other Taxes
	 private String batchNo;
	 private String companyName ;
	 private Date dateManufacture;
	 private Date dateExpiry;
	 private Date createdOn;
	 
	 private long openingBalance;
	 private long closingBalance;
	 
	 private JASStoreDrugTransactionDetail parent;
	 private Set<JASStoreDrugTransactionDetail> subDetails;
	 
	 private Date receiptDate;
	 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public JASStoreDrugTransaction getTransaction() {
		return transaction;
	}
	public void setTransaction(JASStoreDrugTransaction transaction) {
		this.transaction = transaction;
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
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyNameShort() {
		return StringUtils.isNotBlank(companyName) && companyName.length() > 10 ?companyName.substring(0, 7)+"..." : companyName;
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
	public Date getDateExpiry() {
		return dateExpiry;
	}
	public void setDateExpiry(Date dateExpiry) {
		this.dateExpiry = dateExpiry;
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
	public JASDrug getDrug() {
		return drug;
	}
	public void setDrug(JASDrug drug) {
		this.drug = drug;
	}
	public JASDrugFormulation getFormulation() {
		return formulation;
	}
	public void setFormulation(JASDrugFormulation formulation) {
		this.formulation = formulation;
	}
	public JASStoreDrugTransactionDetail getParent() {
		return parent;
	}
	public void setParent(JASStoreDrugTransactionDetail parent) {
		this.parent = parent;
	}
	public Set<JASStoreDrugTransactionDetail> getSubDetails() {
		return subDetails;
	}
	public void setSubDetails(Set<JASStoreDrugTransactionDetail> subDetails) {
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
	public BigDecimal getRate() {
		if(otherTaxes == null){
			otherTaxes = new BigDecimal(0);
		}
		rate = unitPrice.add(unitPrice.multiply(VAT.add(otherTaxes)).divide(new BigDecimal(100)));
		rate = rate.setScale(2, BigDecimal.ROUND_UP);
		return rate;
	}
	public BigDecimal getOtherTaxes() {
		return otherTaxes;
	}
	public void setOtherTaxes(BigDecimal otherTaxes) {
		this.otherTaxes = otherTaxes;
	}
	 
	 
	 
}
