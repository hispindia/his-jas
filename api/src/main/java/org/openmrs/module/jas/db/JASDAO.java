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

