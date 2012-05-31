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

package org.openmrs.module.jas.impl;

import java.util.Collection;
import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jas.model.JASStoreItem;
import org.openmrs.module.jas.model.JASStoreItemAccount;
import org.openmrs.module.jas.model.JASStoreItemAccountDetail;
import org.openmrs.module.jas.model.JASStoreItemIndent;
import org.openmrs.module.jas.model.JASStoreItemIndentDetail;
import org.openmrs.module.jas.model.JASStoreItemTransaction;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemCategory;
import org.openmrs.module.jas.model.JASItemSpecification;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.model.JASItemUnit;
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
public class JASServiceImpl extends BaseOpenmrsService implements JASService {
	
	public JASServiceImpl() {
	}
	
	protected JASDAO dao;
	
	public void setDao(JASDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * STORE
	 */
	public List<JASStore> listStore(int min, int max) throws APIException {
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
	
	public JASStore getStoreByCollectionRole(List<Role> roles) throws APIException {
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
	
	public List<JASStore> listStoreByMainStore(Integer mainStoreid, boolean bothMainStore) throws APIException {
		return dao.listStoreByMainStore(mainStoreid, bothMainStore);
	}
	
	/**
	 * ItemCategory
	 */
	
	public List<JASItemCategory> listItemCategory(String name, int min, int max) throws APIException {
		return dao.listItemCategory(name, min, max);
	}
	
	public List<JASItemCategory> findItemCategory(String name) throws APIException {
		return dao.findItemCategory(name);
	}
	
	public JASItemCategory saveItemCategory(JASItemCategory category) throws APIException {
		return dao.saveItemCategory(category);
	}
	
	public int countListItemCategory(String name) throws APIException {
		return dao.countListItemCategory(name);
	}
	
	public JASItemCategory getItemCategoryById(Integer id) throws APIException {
		return dao.getItemCategoryById(id);
	}
	
	public JASItemCategory getItemCategoryByName(String name) throws APIException {
		return dao.getItemCategoryByName(name);
	}
	
	public void deleteItemCategory(JASItemCategory category) throws APIException {
		dao.deleteItemCategory(category);
	}
	
	/**
	 * ItemSubCategory
	 */
	
	public List<JASItemSubCategory> listItemSubCategory(String name, int min, int max) throws APIException {
		return dao.listItemSubCategory(name, min, max);
	}
	
	public List<JASItemSubCategory> findItemSubCategory(String name) throws APIException {
		return dao.findItemSubCategory(name);
	}
	
	public JASItemSubCategory saveItemSubCategory(JASItemSubCategory subCategory) throws APIException {
		return dao.saveItemSubCategory(subCategory);
	}
	
	public int countListItemSubCategory(String name) throws APIException {
		return dao.countListItemSubCategory(name);
	}
	
	public JASItemSubCategory getItemSubCategoryById(Integer id) throws APIException {
		return dao.getItemSubCategoryById(id);
	}
	
	public JASItemSubCategory getItemSubCategoryByName(Integer categoryId, String name) throws APIException {
		return dao.getItemSubCategoryByName(categoryId, name);
	}
	
	public void deleteItemSubCategory(JASItemSubCategory subCategory) throws APIException {
		dao.deleteItemSubCategory(subCategory);
	}
	
	public List<JASItemSubCategory> listSubCatByCat(Integer categoryId) throws APIException {
		return dao.listSubCatByCat(categoryId);
	}
	
	/**
	 * ItemSpecification
	 */
	
	public List<JASItemSpecification> listItemSpecification(String name, int min, int max) throws APIException {
		return dao.listItemSpecification(name, min, max);
	}
	
	public List<JASItemSpecification> findItemSpecification(String name) throws APIException {
		return dao.findItemSpecification(name);
	}
	
	public JASItemSpecification saveItemSpecification(JASItemSpecification specification) throws APIException {
		return dao.saveItemSpecification(specification);
	}
	
	public int countListItemSpecification(String name) throws APIException {
		return dao.countListItemSpecification(name);
	}
	
	public JASItemSpecification getItemSpecificationById(Integer id) throws APIException {
		return dao.getItemSpecificationById(id);
	}
	
	public JASItemSpecification getItemSpecificationByName(String name) throws APIException {
		return dao.getItemSpecificationByName(name);
	}
	
	public void deleteItemSpecification(JASItemSpecification specification) throws APIException {
		dao.deleteItemSpecification(specification);
	}
	
	/**
	 * ItemUnit
	 */
	
	public List<JASItemUnit> listItemUnit(String name, int min, int max) throws APIException {
		return dao.listItemUnit(name, min, max);
	}
	
	public List<JASItemUnit> findItemUnit(String name) throws APIException {
		return dao.findItemUnit(name);
	}
	
	public JASItemUnit saveItemUnit(JASItemUnit unit) throws APIException {
		return dao.saveItemUnit(unit);
	}
	
	public int countListItemUnit(String name) throws APIException {
		return dao.countListItemUnit(name);
	}
	
	public JASItemUnit getItemUnitById(Integer id) throws APIException {
		return dao.getItemUnitById(id);
	}
	
	public JASItemUnit getItemUnitByName(String name) throws APIException {
		return dao.getItemUnitByName(name);
	}
	
	public void deleteItemUnit(JASItemUnit unit) throws APIException {
		dao.deleteItemUnit(unit);
	}
	
	/**
	 * Item
	 */
	
	public List<JASItem> listItem(Integer categoryId, String name, int min, int max) throws APIException {
		return dao.listItem(categoryId, name, min, max);
	}
	
	public List<JASItem> findItem(String name) throws APIException {
		return dao.findItem(name);
	}
	
	public JASItem saveItem(JASItem item) throws APIException {
		return dao.saveItem(item);
	}
	
	public int countListItem(Integer categoryId, String name) throws APIException {
		return dao.countListItem(categoryId, name);
	}
	
	public JASItem getItemById(Integer id) throws APIException {
		return dao.getItemById(id);
	}
	
	public JASItem getItemByName(String name) throws APIException {
		return dao.getItemByName(name);
	}
	
	public void deleteItem(JASItem item) throws APIException {
		dao.deleteItem(item);
	}
	
	public List<JASItem> findItem(Integer categoryId, String name) throws APIException {
		return dao.findItem(categoryId, name);
	}
	
	public int countItem(Integer categoryId, Integer unitId, Integer subCategoryId, Integer specificationId)
	                                                                                                        throws DAOException {
		return dao.countItem(categoryId, unitId, subCategoryId, specificationId);
	}
	
	/**
	 * Drug
	 */
	
	public List<JASDrug> listDrug(Integer categoryId, String name, int min, int max) throws APIException {
		return dao.listDrug(categoryId, name, min, max);
	}
	
	public List<JASDrug> findDrug(Integer categoryId, String name) throws APIException {
		return dao.findDrug(categoryId, name);
	}
	
	public JASDrug saveDrug(JASDrug drug) throws APIException {
		return dao.saveDrug(drug);
	}
	
	public int countListDrug(Integer categoryId, String name) throws APIException {
		return dao.countListDrug(categoryId, name);
	}
	
	public JASDrug getDrugById(Integer id) throws APIException {
		return dao.getDrugById(id);
	}
	
	public JASDrug getDrugByName(String name) throws APIException {
		return dao.getDrugByName(name);
	}
	
	public void deleteDrug(JASDrug drug) throws APIException {
		dao.deleteDrug(drug);
	}
	
	public int countListDrug(Integer categoryId, Integer unitId, Integer formulationId) throws APIException {
		// TODO Auto-generated method stub
		return dao.countListDrug(categoryId, unitId, formulationId);
	}
	
	/**
	 * DrugCategory
	 */
	
	public List<JASDrugCategory> listDrugCategory(String name, int min, int max) throws APIException {
		return dao.listDrugCategory(name, min, max);
	}
	
	public List<JASDrugCategory> findDrugCategory(String name) throws APIException {
		return dao.findDrugCategory(name);
	}
	
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws APIException {
		return dao.saveDrugCategory(drugCategory);
	}
	
	public int countListDrugCategory(String name) throws APIException {
		return dao.countListDrugCategory(name);
	}
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws APIException {
		return dao.getDrugCategoryById(id);
	}
	
	public JASDrugCategory getDrugCategoryByName(String name) throws APIException {
		return dao.getDrugCategoryByName(name);
	}
	
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws APIException {
		dao.deleteDrugCategory(drugCategory);
	}
	
	/**
	 * DrugFormulation
	 */
	
	public List<JASDrugFormulation> listDrugFormulation(String name, int min, int max) throws APIException {
		return dao.listDrugFormulation(name, min, max);
	}
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws APIException {
		return dao.findDrugFormulation(name);
	}
	
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws APIException {
		return dao.saveDrugFormulation(drugFormulation);
	}
	
	public int countListDrugFormulation(String name) throws APIException {
		return dao.countListDrugFormulation(name);
	}
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws APIException {
		return dao.getDrugFormulationById(id);
	}
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws APIException {
		return dao.getDrugFormulationByName(name);
	}
	
	public JASDrugFormulation getDrugFormulation(String name, String dozage) throws APIException {
		return dao.getDrugFormulation(name, dozage);
	}
	
	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws APIException {
		dao.deleteDrugFormulation(drugFormulation);
	}
	
	/**
	 * DrugUnit
	 */
	
	public List<JASDrugUnit> listDrugUnit(String name, int min, int max) throws APIException {
		return dao.listDrugUnit(name, min, max);
	}
	
	public List<JASDrugUnit> findDrugUnit(String name) throws APIException {
		return dao.findDrugUnit(name);
	}
	
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws APIException {
		return dao.saveDrugUnit(drugUnit);
	}
	
	public int countListDrugUnit(String name) throws APIException {
		return dao.countListDrugUnit(name);
	}
	
	public JASDrugUnit getDrugUnitById(Integer id) throws APIException {
		return dao.getDrugUnitById(id);
	}
	
	public JASDrugUnit getDrugUnitByName(String name) throws APIException {
		return dao.getDrugUnitByName(name);
	}
	
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws APIException {
		dao.deleteDrugUnit(drugUnit);
	}
	
	/**
	 * StoreDrugTransaction
	 */
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId,
	                                                              String description, String fromDate, String toDate,
	                                                              int min, int max) throws APIException {
		return dao.listStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate, min, max);
	}
	
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws APIException {
		return dao.saveStoreDrugTransaction(storeTransaction);
	}
	
	public int countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws APIException {
		return dao.countStoreDrugTransaction(transactionType, storeId, description, fromDate, toDate);
	}
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws APIException {
		return dao.getStoreDrugTransactionById(id);
	}
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws APIException {
		return dao.getStoreDrugTransactionByParentId(parentId);
	}
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer categoryId,
	                                                                          String drugName, String formulationName,
	                                                                          String fromDate, String toDate, int min,
	                                                                          int max) throws APIException {
		return dao
		        .listStoreDrugTransactionDetail(storeId, categoryId, drugName, formulationName, fromDate, toDate, min, max);
	}
	
	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail)
	                                                                                                                         throws APIException {
		return dao.saveStoreDrugTransactionDetail(storeTransactionDetail);
	}
	
	public int countStoreDrugTransactionDetail(Integer storeId, Integer categoryId, String drugName, String formulationName,
	                                           String fromDate, String toDate) throws APIException {
		return dao.countStoreDrugTransactionDetail(storeId, categoryId, drugName, formulationName, fromDate, toDate);
	}
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws APIException {
		return dao.getStoreDrugTransactionDetailById(id);
	}
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                          Integer formulationId, boolean haveQuantity)
	                                                                                                                      throws APIException {
		return dao.listStoreDrugTransactionDetail(storeId, drugId, formulationId, haveQuantity);
	}
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId, Integer drugId, Integer formulationId) throws APIException {
		return dao.sumCurrentQuantityDrugOfStore(storeId, drugId, formulationId);
	}
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId, Collection<Integer> drugs,
	                                                                 Collection<Integer> formulations) throws APIException {
		// TODO Auto-generated method stub
		return dao.listStoreDrugAvaiable(storeId, drugs, formulations);
	}
	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(Integer transactionId) throws APIException {
		// TODO Auto-generated method stub
		return dao.listTransactionDetail(transactionId);
	}
	
	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
	                                     String toDate, boolean isExpiry) throws APIException {
		// TODO Auto-generated method stub
		return dao.countViewStockBalance(storeId, categoryId, drugName, fromDate, toDate, isExpiry);
	}
	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId, String drugName,
	                                                                String fromDate, String toDate, boolean isExpiry,
	                                                                int min, int max) throws APIException {
		// TODO Auto-generated method stub
		return dao.listViewStockBalance(storeId, categoryId, drugName, fromDate, toDate, isExpiry, min, max);
	}
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                          Integer formulationId, Integer isExpriry)
	                                                                                                                   throws APIException {
		// TODO Auto-generated method stub
		return dao.listStoreDrugTransactionDetail(storeId, drugId, formulationId, isExpriry);
	}
	
	public int checkExistDrugTransactionDetail(Integer drugId) throws APIException {
		// TODO Auto-generated method stub
		return dao.checkExistDrugTransactionDetail(drugId);
	}
	
	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId, String name, String fromDate, String toDate, int min,
	                                                  int max) throws APIException {
		return dao.listStoreDrugIssue(storeId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreDrugIssue(Integer storeId, String name, String fromDate, String toDate) throws APIException {
		return dao.countStoreDrugIssue(storeId, name, fromDate, toDate);
	}
	
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws APIException {
		return dao.saveStoreDrugIssue(bill);
	}
	
	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws APIException {
		return dao.getStoreDrugIssueById(id);
	}
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugIssueDetailId) throws APIException {
		return dao.listStoreDrugIssueDetail(storeDrugIssueDetailId);
	}
	
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugIssueDetail)
	                                                                                                     throws APIException {
		return dao.saveStoreDrugIssueDetail(storeDrugIssueDetail);
	}
	
	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws APIException {
		return dao.getStoreDrugIssueDetailById(id);
	}
	
	//change code
	/**
	 * StoreItem
	 */
	
	public List<JASStoreItem> listStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty,
	                                        int min, int max) throws APIException {
		return dao.listStoreItem(storeId, categoryId, itemName, reorderQty, min, max);
	}
	
	public int countStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty) throws APIException {
		return dao.countStoreItem(storeId, categoryId, itemName, reorderQty);
	}
	
	public JASStoreItem getStoreItemById(Integer id) throws APIException {
		return dao.getStoreItemById(id);
	}
	
	public JASStoreItem getStoreItem(Integer storeId, Integer itemId, Integer specificationId) throws APIException {
		return dao.getStoreItem(storeId, itemId, specificationId);
	}
	
	public JASStoreItem saveStoreItem(JASStoreItem StoreItem) throws APIException {
		return dao.saveStoreItem(StoreItem);
	}
	
	/**
	 * StoreItemTransaction
	 */
	
	public List<JASStoreItemTransaction> listStoreItemTransaction(Integer transactionType, Integer storeId,
	                                                              String description, String fromDate, String toDate,
	                                                              int min, int max) throws APIException {
		return dao.listStoreItemTransaction(transactionType, storeId, description, fromDate, toDate, min, max);
	}
	
	public JASStoreItemTransaction saveStoreItemTransaction(JASStoreItemTransaction storeTransaction) throws APIException {
		return dao.saveStoreItemTransaction(storeTransaction);
	}
	
	public int countStoreItemTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws APIException {
		return dao.countStoreItemTransaction(transactionType, storeId, description, fromDate, toDate);
	}
	
	public JASStoreItemTransaction getStoreItemTransactionById(Integer id) throws APIException {
		return dao.getStoreItemTransactionById(id);
	}
	
	public JASStoreItemTransaction getStoreItemTransactionByParentId(Integer parentId) throws APIException {
		return dao.getStoreItemTransactionByParentId(parentId);
	}
	
	/**
	 * StoreItemTransactionDetail
	 */
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer categoryId,
	                                                                          String itemName, String specificationName,
	                                                                          String fromDate, String toDate, int min,
	                                                                          int max) throws APIException {
		return dao.listStoreItemTransactionDetail(storeId, categoryId, itemName, specificationName, fromDate, toDate, min,
		    max);
	}
	
	public JASStoreItemTransactionDetail saveStoreItemTransactionDetail(JASStoreItemTransactionDetail storeTransactionDetail)
	                                                                                                                         throws APIException {
		return dao.saveStoreItemTransactionDetail(storeTransactionDetail);
	}
	
	public int countStoreItemTransactionDetail(Integer storeId, Integer categoryId, String itemName,
	                                           String specificationName, String fromDate, String toDate) throws APIException {
		return dao.countStoreItemTransactionDetail(storeId, categoryId, itemName, specificationName, fromDate, toDate);
	}
	
	public JASStoreItemTransactionDetail getStoreItemTransactionDetailById(Integer id) throws APIException {
		return dao.getStoreItemTransactionDetailById(id);
	}
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                          Integer specificationId, boolean haveQuantity)
	                                                                                                                        throws APIException {
		return dao.listStoreItemTransactionDetail(storeId, itemId, specificationId, haveQuantity);
	}
	
	public List<JASStoreItemTransactionDetail> listStoreItemAvaiable(Integer storeId, Collection<Integer> items,
	                                                                 Collection<Integer> specifications) throws APIException {
		return dao.listStoreItemAvaiable(storeId, items, specifications);
	}
	
	public Integer sumStoreItemCurrentQuantity(Integer storeId, Integer itemId, Integer specificationId) throws APIException {
		return dao.sumStoreItemCurrentQuantity(storeId, itemId, specificationId);
	}
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer transactionId) throws APIException {
		return dao.listStoreItemTransactionDetail(transactionId);
	}
	
	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
	                                              String toDate) throws APIException {
		return dao.countStoreItemViewStockBalance(storeId, categoryId, itemName, fromDate, toDate);
	}
	
	public List<JASStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
	                                                                         String itemName, String fromDate,
	                                                                         String toDate, int min, int max)
	                                                                                                         throws APIException {
		return dao.listStoreItemViewStockBalance(storeId, categoryId, itemName, fromDate, toDate, min, max);
	}
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                          Integer specificationId, int min, int max)
	                                                                                                                    throws APIException {
		return dao.listStoreItemTransactionDetail(storeId, itemId, specificationId, min, max);
	}
	
	public int checkExistItemTransactionDetail(Integer itemId) throws APIException {
		// TODO Auto-generated method stub
		return dao.checkExistItemTransactionDetail(itemId);
	}
	
	/**
	 * JASStoreItemIndent
	 */
	
	public List<JASStoreItemIndent> listStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate,
	                                                    int min, int max) throws APIException {
		return dao.listStoreItemIndent(StoreId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate) throws APIException {
		return dao.countStoreItemIndent(StoreId, name, fromDate, toDate);
	}
	
	public List<JASStoreItemIndent> listSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate,
	                                                       String toDate, int min, int max) throws APIException {
		return dao.listSubStoreItemIndent(storeId, name, status, fromDate, toDate, min, max);
	}
	
	public int countSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate, String toDate)
	                                                                                                                throws APIException {
		return dao.countSubStoreItemIndent(storeId, name, status, fromDate, toDate);
	}
	
	public List<JASStoreItemIndent> listMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId,
	                                                        String name, Integer status, String fromDate, String toDate,
	                                                        int min, int max) throws APIException {
		return dao.listMainStoreItemIndent(id, mainStoreId, subStoreId, name, status, fromDate, toDate, min, max);
	}
	
	public int countMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId, String name, Integer status,
	                                    String fromDate, String toDate) throws APIException {
		return dao.countMainStoreItemIndent(id, mainStoreId, subStoreId, name, status, fromDate, toDate);
	}
	
	public JASStoreItemIndent saveStoreItemIndent(JASStoreItemIndent storeItemIndent) throws APIException {
		return dao.saveStoreItemIndent(storeItemIndent);
	}
	
	public JASStoreItemIndent getStoreItemIndentById(Integer id) throws APIException {
		return dao.getStoreItemIndentById(id);
	}
	
	/**
	 * JASStoreItemIndentDetail
	 */
	
	public List<JASStoreItemIndentDetail> listStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName,
	                                                                String itemName, String fromDate, String toDate,
	                                                                int min, int max) throws APIException {
		return dao.listStoreItemIndentDetail(storeId, categoryId, indentName, itemName, fromDate, toDate, min, max);
	}
	
	public int countStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName, String itemName,
	                                      String fromDate, String toDate) throws APIException {
		return dao.countStoreItemIndentDetail(storeId, categoryId, indentName, itemName, fromDate, toDate);
	}
	
	public List<JASStoreItemIndentDetail> listStoreItemIndentDetail(Integer indentId) throws APIException {
		return dao.listStoreItemIndentDetail(indentId);
	}
	
	public JASStoreItemIndentDetail saveStoreItemIndentDetail(JASStoreItemIndentDetail storeItemIndentDetail)
	                                                                                                         throws APIException {
		return dao.saveStoreItemIndentDetail(storeItemIndentDetail);
	}
	
	public JASStoreItemIndentDetail getStoreItemIndentDetailById(Integer id) throws APIException {
		return dao.getStoreItemIndentDetailById(id);
	}
	
	public int checkExistItemIndentDetail(Integer itemId) throws APIException {
		// TODO Auto-generated method stub
		return dao.checkExistItemIndentDetail(itemId);
	}
	
	/**
	 * JASStoreItemAccount
	 */
	public List<JASStoreItemAccount> listStoreItemAccount(Integer storeId, String name, String fromDate, String toDate,
	                                                      int min, int max) throws APIException {
		return dao.listStoreItemAccount(storeId, name, fromDate, toDate, min, max);
	}
	
	public int countStoreItemAccount(Integer storeId, String name, String fromDate, String toDate) throws APIException {
		return dao.countStoreItemAccount(storeId, name, fromDate, toDate);
	}
	
	public JASStoreItemAccount saveStoreItemAccount(JASStoreItemAccount issue) throws APIException {
		return dao.saveStoreItemAccount(issue);
	}
	
	public JASStoreItemAccount getStoreItemAccountById(Integer id) throws APIException {
		return dao.getStoreItemAccountById(id);
	}
	
	/**
	 * JASStoreItemAccountDetail
	 */
	public List<JASStoreItemAccountDetail> listStoreItemAccountDetail(Integer storeItemAccountDetailId) throws APIException {
		return dao.listStoreItemAccountDetail(storeItemAccountDetailId);
	}
	
	public JASStoreItemAccountDetail saveStoreItemAccountDetail(JASStoreItemAccountDetail storeItemAccountDetail)
	                                                                                                             throws APIException {
		return dao.saveStoreItemAccountDetail(storeItemAccountDetail);
	}
	
	public JASStoreItemAccountDetail getStoreItemAccountDetailById(Integer id) throws APIException {
		return dao.getStoreItemAccountDetailById(id);
	}
	
}
