/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.jas.db;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASDrugUnit;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugIssue;
import org.openmrs.module.jas.model.JASStoreDrugIssueDetail;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;


/**
 *
 */
public interface JASDAO {
	
	/**
	 * STORE 
	 */
	public void setSessionFactory(SessionFactory sessionFactory) throws DAOException;

    public List<JASStore> listStore(int min, int max) throws DAOException;
	
	public JASStore saveStore(JASStore store) throws DAOException;

	public int countListStore()  throws DAOException;
	
	public JASStore getStoreById(Integer id) throws DAOException;
	
	public JASStore getStoreByRole(String role) throws DAOException;
	
	public JASStore getStoreByCollectionRole(List<Role> roles) throws DAOException;
	
	public List<JASStore> listMainStore() throws DAOException;
	
	public void deleteStore(JASStore store) throws DAOException;
	
	public List<JASStore> listAllStore() throws DAOException;
	
	public JASStore getStoreByName(String name) throws DAOException;
	
	public List<JASStore> listStoreByMainStore(Integer storeid , boolean bothMainStore) throws DAOException ;
	
	
	
	/**
	 * Drug
	 */
	 
	
	public List<JASDrug> listDrug(Integer categoryId, String name ,int min, int max) throws DAOException;
	
	
	public List<JASDrug> findDrug(Integer categoryId,String name) throws DAOException;
	
	public JASDrug saveDrug(JASDrug drug) throws DAOException;

	
	public int countListDrug(Integer categoryId, String name)  throws DAOException;
	
	public int countListDrug(Integer categoryId, Integer unitId,  Integer formulationId)  throws DAOException;
	
	
	public JASDrug getDrugById(Integer id) throws DAOException;
	
	
	public JASDrug getDrugByName(String name) throws DAOException;
	
	public void deleteDrug(JASDrug drug) throws DAOException;
	
	
	/**
	 * DrugCategory
	 */
	 
	
	public List<JASDrugCategory> listDrugCategory(String name ,int min, int max) throws DAOException;
	
	
	public List<JASDrugCategory> findDrugCategory(String name) throws DAOException;
	
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws DAOException;

	
	public int countListDrugCategory(String name)  throws DAOException;
	
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws DAOException;
	
	
	public JASDrugCategory getDrugCategoryByName(String name) throws DAOException;
	
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws DAOException;
	
	/**
	 * DrugFormulation
	 */
	 
	
	public List<JASDrugFormulation> listDrugFormulation(String name ,int min, int max) throws DAOException;
	
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws DAOException;
	
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws DAOException;

	
	public int countListDrugFormulation(String name)  throws DAOException;
	
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws DAOException;
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws DAOException;
	
	public JASDrugFormulation getDrugFormulation(String name ,String dozage) throws DAOException;
	
	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws DAOException;
	
	/**
	 * DrugUnit
	 */
	 
	
	public List<JASDrugUnit> listDrugUnit(String name ,int min, int max) throws DAOException;
	
	
	public List<JASDrugUnit> findDrugUnit(String name) throws DAOException;
	
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws DAOException;

	
	public int countListDrugUnit(String name)  throws DAOException;
	
	
	public JASDrugUnit getDrugUnitById(Integer id) throws DAOException;
	
	
	public JASDrugUnit getDrugUnitByName(String name) throws DAOException;
	
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws DAOException;
	
	
	
	/**
	 * StoreDrugTransaction
	 */ 
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate ,int min, int max) throws DAOException;
	
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws DAOException;

	public int countStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate)  throws DAOException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws DAOException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws DAOException;
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,  Integer categoryId,String drugName,String formulationName, String fromDate, String toDate ,int min, int max) throws DAOException;
	
	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail) throws DAOException;

	public int countStoreDrugTransactionDetail(Integer storeId,  Integer categoryId,String drugName,String formulationName, String fromDate, String toDate )  throws DAOException;
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, boolean haveQuantity) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId,Collection<Integer> drugs,Collection<Integer> formulations) throws DAOException;
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId,Integer drugId,Integer formulationId) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(Integer transactionId) throws DAOException;
	
	public Integer countViewStockBalance(Integer storeId,Integer categoryId, String drugName , String fromDate , String toDate, boolean isExpiry) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(Integer storeId,Integer categoryId, String drugName , String fromDate , String toDate, boolean isExpiry,int min, int max) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, Integer isExpriry) throws DAOException;
	
	public int checkExistDrugTransactionDetail(Integer drugId)  throws DAOException;
	
	
	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId,String  name, String fromDate, String toDate, int min, int max) throws DAOException;
	
	public int countStoreDrugIssue(Integer storeId,String  name, String fromDate, String toDate)  throws DAOException;
	
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws DAOException;
	
	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws DAOException;
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugPatientDetailId) throws DAOException;
	
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugPatientDetail) throws DAOException;

	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws DAOException;
	
	
}

