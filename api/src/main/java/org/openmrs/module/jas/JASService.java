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

package org.openmrs.module.jas;

import java.util.Collection;
import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASDrugUnit;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugIssue;
import org.openmrs.module.jas.model.JASStoreDrugIssueDetail;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */
@Transactional
public interface JASService extends OpenmrsService{

	
	/**
	 * Store
	 */
	@Transactional(readOnly = true)
	public List<JASStore> listStore(int min, int max) throws APIException;
	
	@Transactional(readOnly = true)
	public List<JASStore> listAllStore() throws APIException;
	
	@Transactional(readOnly = true)
	public List<JASStore> listMainStore() throws APIException;
	
	@Transactional(readOnly=false)
	public JASStore saveStore(JASStore store) throws APIException;

	@Transactional(readOnly = true)
	public int countListStore()  throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreById(Integer id) throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreByName(String name) throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreByRole(String role) throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreByCollectionRole(List<Role> roles) throws APIException;
	
	@Transactional(readOnly=false)
	public void deleteStore(JASStore store) throws APIException;
	
	@Transactional(readOnly = true)
	public List<JASStore> listStoreByMainStore(Integer mainStoreid,boolean bothMainStore) throws APIException;
	
	
	
	/**
	 * Drug
	 */
	 
	
	public List<JASDrug> listDrug(Integer categoryId,  String name ,int min, int max) throws APIException;
	
	public List<JASDrug> findDrug(Integer categoryId,String name) throws APIException;
	
	@Transactional(readOnly=false)
	public JASDrug saveDrug(JASDrug drug) throws APIException;

	public int countListDrug(Integer categoryId, String name)  throws APIException;
	
	public JASDrug getDrugById(Integer id) throws APIException;
	
	public JASDrug getDrugByName(String name) throws APIException;
	
	@Transactional(readOnly=false)
	public void deleteDrug(JASDrug drug) throws APIException;
	
	public int countListDrug(Integer categoryId, Integer unitId,  Integer formulationId)  throws APIException;
	
	
	/**
	 * DrugCategory
	 */
	 
	
	public List<JASDrugCategory> listDrugCategory(String name ,int min, int max) throws APIException;
	
	public List<JASDrugCategory> findDrugCategory(String name) throws APIException;
	
	@Transactional(readOnly=false)
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws APIException;

	public int countListDrugCategory(String name)  throws APIException;
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws APIException;
	
	public JASDrugCategory getDrugCategoryByName(String name) throws APIException;
	
	@Transactional(readOnly=false)
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws APIException;
	
	/**
	 * DrugFormulation
	 */
	 
	
	public List<JASDrugFormulation> listDrugFormulation(String name ,int min, int max) throws APIException;
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws APIException;
	
	@Transactional(readOnly=false)
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws APIException;

	public int countListDrugFormulation(String name)  throws APIException;
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws APIException;
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws APIException;
	
	public JASDrugFormulation getDrugFormulation(String name ,String dozage) throws APIException;
	
	@Transactional(readOnly=false)
	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws APIException;
	
	/**
	 * DrugUnit
	 */
	public List<JASDrugUnit> listDrugUnit(String name ,int min, int max) throws APIException;
	
	public List<JASDrugUnit> findDrugUnit(String name) throws APIException;
	
	@Transactional(readOnly=false)
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws APIException;
	
	public int countListDrugUnit(String name)  throws APIException;
	
	public JASDrugUnit getDrugUnitById(Integer id) throws APIException;
	
	public JASDrugUnit getDrugUnitByName(String name) throws APIException;
	
	@Transactional(readOnly=false)
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws APIException;
	
	
	
	/**
	 * StoreDrugTransaction
	 */ 
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate ,int min, int max) throws APIException;
	
	@Transactional(readOnly=false)
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws APIException;

	public int countStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate)  throws APIException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws APIException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws APIException;
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,  Integer categoryId,String drugName,String formulationName, String fromDate, String toDate ,int min, int max) throws APIException;
	
	@Transactional(readOnly=false)
	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail) throws APIException;

	public int countStoreDrugTransactionDetail(Integer storeId,  Integer categoryId,String drugName,String formulationName, String fromDate, String toDate )  throws APIException;
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, boolean haveQuantity) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId,Collection<Integer> drugs,Collection<Integer> formulations) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(Integer transactionId) throws APIException;
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId,Integer drugId,Integer formulationId) throws APIException;
	
	public Integer countViewStockBalance(Integer storeId,Integer categoryId, String drugName , String fromDate, String toDate, boolean isExpiry) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(Integer storeId,Integer categoryId, String drugName , String fromDate, String toDate,boolean isExpiry ,int min, int max) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, Integer isExpiry) throws APIException;
	
	public int checkExistDrugTransactionDetail(Integer drugId)  throws APIException;
	
	
	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId,String name, String fromDate, String toDate, int min, int max) throws APIException;
	
	public int countStoreDrugIssue(Integer storeId,String name, String fromDate, String toDate)  throws APIException;
	
	@Transactional(readOnly=false)
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws APIException;
	
	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws APIException;
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugIssueDetailId) throws APIException;
	
	@Transactional(readOnly=false)
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugIssueDetail) throws APIException;

	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws APIException;
	
	
	
}
