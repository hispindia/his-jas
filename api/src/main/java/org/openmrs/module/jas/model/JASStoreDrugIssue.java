package org.openmrs.module.jas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Patient;

public class JASStoreDrugIssue implements  Serializable {
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private JASStore store;
	 private String name;
	 private String prescription;
	 private Date createdOn;
	 private String createdBy;
	 private Patient patient;
	 private String identifier;
	 private String billNumber;
	 private BigDecimal total;
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
		return StringUtils.isNotEmpty(name) ?  name : "Unknow";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
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
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	/*public String getPatientCategory(){
		return PatientUtil.getPatientCategory(patient);
	}*/
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	 
	 
}
