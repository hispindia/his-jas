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
package org.openmrs.module.jas.impl;

import java.util.Collection;
import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.db.JASDAO;
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
public class JASServiceImpl extends BaseOpenmrsService implements JASService{

    public JASServiceImpl(){
    }
    
    protected JASDAO dao;
	
	public void setDao(JASDAO dao) {
		this.dao = dao;
	}

	/**
	 * STORE 
	 */
	public List<JASStore> listStore(int min, int max)
			throws APIException {
		return dao.listStore(min, max);
	}

	public JASStore saveStore(JASStore store) throws APIException {
		return dao.saveStore(store);
	}

	public int countListStore() throws APIException {
		return dao.countListStore();
	}

	public JASStore getStoreById(Integer id) throws APIException {
		return dao.getStoreById(id);
	}

	public JASStore getStoreByRole(String role) throws APIException {
		return dao.getStoreByRole(role);
	}
	
	public JASStore getStoreByCollectionRole(List<Role> roles) throws APIException{
		return dao.getStoreByCollectionRole(roles);
	}

	public List<JASStore> listMainStore() throws APIException {
		return dao.listMainStore();
	}

	public void deleteStore(JASStore store) throws APIException {
		dao.deleteStore(store);
	}

	public List<JASStore> listAllStore() throws APIException {
		return dao.listAllStore();
	}

	public JASStore getStoreByName(String name) throws APIException {
		return dao.getStoreByName(name);
	}
	
	public List<JASStore> listStoreByMainStore(Integer mainStoreid,boolean bothMainStore) throws APIException{
		return dao.listStoreByMainStore(mainStoreid ,bothMainStore);
	}
	
	
	
	/**
	 * Drug
	 */
	 
	
	public List<JASDrug> listDrug(Integer categoryId, String name ,int min, int max) throws APIException{
		return dao.listDrug(categoryId, name, min, max);
	}
	
	
	public List<JASDrug> findDrug(Integer categoryId ,String name) throws APIException{
		return dao.findDrug(categoryId,name);
	}
	
	public JASDrug saveDrug(JASDrug drug) throws APIException{
		return dao.saveDrug(drug);
	}

	
	public int countListDrug(Integer categoryId, String name)  throws APIException{
		return dao.countListDrug(categoryId , name);
	}
	
	
	public JASDrug getDrugById(Integer id) throws APIException{
		return dao.getDrugById(id);
	}
	
	
	public JASDrug getDrugByName(String name) throws APIException{
		return dao.getDrugByName(name);
	}
	
	public void deleteDrug(JASDrug drug) throws APIException{
		dao.deleteDrug(drug);
	}
	
	
	public int countListDrug(Integer categoryId, Integer unitId,
			Integer formulationId) throws APIException {
		// TODO Auto-generated method stub
		return dao.countListDrug(categoryId, unitId, formulationId);
	}

	/**
	 * DrugCategory
	 */
	 
	
	public List<JASDrugCategory> listDrugCategory(String name ,int min, int max) throws APIException{
		return dao.listDrugCategory(name, min, max);
	}
	
	
	public List<JASDrugCategory> findDrugCategory(String name) throws APIException{
		return dao.findDrugCategory(name);
	}
	
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws APIException{
		return dao.saveDrugCategory(drugCategory);
	}

	
	public int countListDrugCategory(String name)  throws APIException{
		return dao.countListDrugCategory(name);
	}
	
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws APIException{
		return dao.getDrugCategoryById(id);
	}
	
	
	public JASDrugCategory getDrugCategoryByName(String name) throws APIException{
		return dao.getDrugCategoryByName(name);
	}
	
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws APIException{
		dao.deleteDrugCategory(drugCategory);
	}
	
	/**
	 * DrugFormulation
	 */
	 
	
	public List<JASDrugFormulation> listDrugFormulation(String name ,int min, int max) throws APIException{
		return dao.listDrugFormulation(name, min, max);
	}
	
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws APIException{
		return dao.findDrugFormulation(name);
	}
	
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws APIException{
		return dao.saveDrugFormulation(drugFormulation);
	}

	
	public int countListDrugFormulation(String name)  throws APIException{
		return dao.countListDrugFormulation(name);
	}
	
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws APIException{
		return dao.getDrugFormulationById(id);
	}
	
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws APIException{
		return dao.getDrugFormulationByName(name);
	}
	
	
	
	public JASDrugFormulation getDrugFormulation(String name ,String dozage)
			throws APIException {
		return dao.getDrugFormulation(name, dozage);
	}

	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws APIException{
		dao.deleteDrugFormulation(drugFormulation);
	}
	
	/**
	 * DrugUnit
	 */
	 
	
	public List<JASDrugUnit> listDrugUnit(String name ,int min, int max) throws APIException{
		return dao.listDrugUnit(name, min, max);
	}
	
	
	public List<JASDrugUnit> findDrugUnit(String name) throws APIException{
		return dao.findDrugUnit(name);
	}
	
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws APIException{
		return dao.saveDrugUnit(drugUnit);
	}

	
	public int countListDrugUnit(String name)  throws APIException{
		return dao.countListDrugUnit(name);
	}
	
	
	public JASDrugUnit getDrugUnitById(Integer id) throws APIException{
		return dao.getDrugUnitById(id);
	}
	
	
	public JASDrugUnit getDrugUnitByName(String name) throws APIException{
		return dao.getDrugUnitByName(name);
	}
	
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws APIException{
		 dao.deleteDrugUnit(drugUnit);
	}
	
	
	/**
	 * StoreDrugTransaction
	 */ 
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate ,int min, int max) throws APIException{
		return dao.listStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate, min, max);
	}
	
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws APIException{
		return dao.saveStoreDrugTransaction(storeTransaction);
	}

	public int countStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate)  throws APIException{
		return dao.countStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate);
	}
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws APIException{
		return dao.getStoreDrugTransactionById(id);
	}
	
	
	
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(
			Integer parentId) throws APIException {
		return dao.getStoreDrugTransactionByParentId(parentId);
	}

	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer categoryId,String drugName,String formulationName, String fromDate, String toDate ,int min, int max) throws APIException{
		return dao.listStoreDrugTransactionDetail(storeId, categoryId, drugName, formulationName, fromDate, toDate, min, max);
	}
	
	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail) throws APIException{
		return dao.saveStoreDrugTransactionDetail(storeTransactionDetail);
	}

	public int countStoreDrugTransactionDetail(Integer storeId,Integer categoryId,String drugName,String formulationName, String fromDate, String toDate )  throws APIException{
		return dao.countStoreDrugTransactionDetail(storeId, categoryId, drugName, formulationName, fromDate, toDate);
	}
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws APIException{
		return dao.getStoreDrugTransactionDetailById(id);
	}
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, boolean haveQuantity) throws APIException{
		return dao.listStoreDrugTransactionDetail(storeId, drugId, formulationId, haveQuantity);
	}
	
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId, Integer drugId, Integer formulationId) throws APIException {
		return dao.sumCurrentQuantityDrugOfStore(storeId, drugId, formulationId);
	}

	
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(
			Integer storeId, Collection<Integer> drugs,
			Collection<Integer> formulations) throws APIException {
		// TODO Auto-generated method stub
		return dao.listStoreDrugAvaiable(storeId, drugs, formulations);
	}

	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(
			Integer transactionId) throws APIException {
		// TODO Auto-generated method stub
		return dao.listTransactionDetail(transactionId);
	}

	
	
	public Integer countViewStockBalance(Integer storeId, Integer categoryId,
			String drugName, String fromDate, String toDate, boolean isExpiry) throws APIException {
		// TODO Auto-generated method stub
		return dao.countViewStockBalance(storeId, categoryId, drugName, fromDate, toDate, isExpiry);
	}

	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(
			Integer storeId, Integer categoryId, String drugName,
			String fromDate, String toDate, boolean isExpiry,int min, int max) throws APIException {
		// TODO Auto-generated method stub
		return dao.listViewStockBalance(storeId, categoryId, drugName, fromDate, toDate, isExpiry,min,max);
	}

	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(
			Integer storeId, Integer drugId, Integer formulationId, Integer isExpriry)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.listStoreDrugTransactionDetail(storeId, drugId, formulationId, isExpriry);
	}
	
	

	
	public int checkExistDrugTransactionDetail(Integer drugId)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.checkExistDrugTransactionDetail(drugId);
	}

	

	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId,String  name, String fromDate, String toDate, int min, int max) throws APIException{
		return dao.listStoreDrugIssue(storeId,  name, fromDate, toDate, min, max);
	}
	
	public int countStoreDrugIssue(Integer storeId,String  name, String fromDate, String toDate)  throws APIException{
		return dao.countStoreDrugIssue(storeId,  name, fromDate, toDate);
	}
	
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws APIException{
		return dao.saveStoreDrugIssue(bill);
	}
	
	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws APIException{
		return dao.getStoreDrugIssueById(id);
	}
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugIssueDetailId) throws APIException{
		return dao.listStoreDrugIssueDetail(storeDrugIssueDetailId);
	}
	
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugIssueDetail) throws APIException{
		return dao.saveStoreDrugIssueDetail(storeDrugIssueDetail);
	}

	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws APIException{
		return dao.getStoreDrugIssueDetailById(id);
	}
	
	
	
	
}
