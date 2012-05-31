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
	
	public int countListStore() throws DAOException;
	
	public JASStore getStoreById(Integer id) throws DAOException;
	
	public JASStore getStoreByRole(String role) throws DAOException;
	
	public JASStore getStoreByCollectionRole(List<Role> roles) throws DAOException;
	
	public List<JASStore> listMainStore() throws DAOException;
	
	public void deleteStore(JASStore store) throws DAOException;
	
	public List<JASStore> listAllStore() throws DAOException;
	
	public JASStore getStoreByName(String name) throws DAOException;
	
	public List<JASStore> listStoreByMainStore(Integer storeid, boolean bothMainStore) throws DAOException;
	
	/**
	 * ItemCategory
	 */
	
	public List<JASItemCategory> listItemCategory(String name, int min, int max) throws DAOException;
	
	public List<JASItemCategory> findItemCategory(String name) throws DAOException;
	
	public JASItemCategory saveItemCategory(JASItemCategory category) throws DAOException;
	
	public int countListItemCategory(String name) throws DAOException;
	
	public JASItemCategory getItemCategoryById(Integer id) throws DAOException;
	
	public JASItemCategory getItemCategoryByName(String name) throws DAOException;
	
	public void deleteItemCategory(JASItemCategory category) throws DAOException;
	
	/**
	 * ItemSubCategory
	 */
	
	public List<JASItemSubCategory> listItemSubCategory(String name, int min, int max) throws DAOException;
	
	public List<JASItemSubCategory> findItemSubCategory(String name) throws DAOException;
	
	public List<JASItemSubCategory> listSubCatByCat(Integer categoryId) throws DAOException;
	
	public JASItemSubCategory saveItemSubCategory(JASItemSubCategory subCategory) throws DAOException;
	
	public int countListItemSubCategory(String name) throws DAOException;
	
	public JASItemSubCategory getItemSubCategoryById(Integer id) throws DAOException;
	
	public JASItemSubCategory getItemSubCategoryByName(Integer categoryId, String name) throws DAOException;
	
	public void deleteItemSubCategory(JASItemSubCategory subCategory) throws DAOException;
	
	/**
	 * ItemSpecification
	 */
	
	public List<JASItemSpecification> listItemSpecification(String name, int min, int max) throws DAOException;
	
	public List<JASItemSpecification> findItemSpecification(String name) throws DAOException;
	
	public JASItemSpecification saveItemSpecification(JASItemSpecification specification) throws DAOException;
	
	public int countListItemSpecification(String name) throws DAOException;
	
	public JASItemSpecification getItemSpecificationById(Integer id) throws DAOException;
	
	public JASItemSpecification getItemSpecificationByName(String name) throws DAOException;
	
	public void deleteItemSpecification(JASItemSpecification specification) throws DAOException;
	
	/**
	 * ItemUnit
	 */
	
	public List<JASItemUnit> listItemUnit(String name, int min, int max) throws DAOException;
	
	public List<JASItemUnit> findItemUnit(String name) throws DAOException;
	
	public JASItemUnit saveItemUnit(JASItemUnit unit) throws DAOException;
	
	public int countListItemUnit(String name) throws DAOException;
	
	public JASItemUnit getItemUnitById(Integer id) throws DAOException;
	
	public JASItemUnit getItemUnitByName(String name) throws DAOException;
	
	public void deleteItemUnit(JASItemUnit unit) throws DAOException;
	
	/**
	 * Item
	 */
	
	public List<JASItem> listItem(Integer categoryId, String name, int min, int max) throws DAOException;
	
	public List<JASItem> findItem(String name) throws DAOException;
	
	public JASItem saveItem(JASItem item) throws DAOException;
	
	public int countListItem(Integer categoryId, String name) throws DAOException;
	
	public JASItem getItemById(Integer id) throws DAOException;
	
	public JASItem getItemByName(String name) throws DAOException;
	
	public void deleteItem(JASItem item) throws DAOException;
	
	public List<JASItem> findItem(Integer categoryId, String name) throws DAOException;
	
	public int countItem(Integer categoryId, Integer unitId, Integer subCategoryId, Integer specificationId)
	                                                                                                        throws DAOException;
	
	/**
	 * Drug
	 */
	
	public List<JASDrug> listDrug(Integer categoryId, String name, int min, int max) throws DAOException;
	
	public List<JASDrug> findDrug(Integer categoryId, String name) throws DAOException;
	
	public JASDrug saveDrug(JASDrug drug) throws DAOException;
	
	public int countListDrug(Integer categoryId, String name) throws DAOException;
	
	public int countListDrug(Integer categoryId, Integer unitId, Integer formulationId) throws DAOException;
	
	public JASDrug getDrugById(Integer id) throws DAOException;
	
	public JASDrug getDrugByName(String name) throws DAOException;
	
	public void deleteDrug(JASDrug drug) throws DAOException;
	
	/**
	 * DrugCategory
	 */
	
	public List<JASDrugCategory> listDrugCategory(String name, int min, int max) throws DAOException;
	
	public List<JASDrugCategory> findDrugCategory(String name) throws DAOException;
	
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws DAOException;
	
	public int countListDrugCategory(String name) throws DAOException;
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws DAOException;
	
	public JASDrugCategory getDrugCategoryByName(String name) throws DAOException;
	
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws DAOException;
	
	/**
	 * DrugFormulation
	 */
	
	public List<JASDrugFormulation> listDrugFormulation(String name, int min, int max) throws DAOException;
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws DAOException;
	
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws DAOException;
	
	public int countListDrugFormulation(String name) throws DAOException;
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws DAOException;
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws DAOException;
	
	public JASDrugFormulation getDrugFormulation(String name, String dozage) throws DAOException;
	
	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws DAOException;
	
	/**
	 * DrugUnit
	 */
	
	public List<JASDrugUnit> listDrugUnit(String name, int min, int max) throws DAOException;
	
	public List<JASDrugUnit> findDrugUnit(String name) throws DAOException;
	
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws DAOException;
	
	public int countListDrugUnit(String name) throws DAOException;
	
	public JASDrugUnit getDrugUnitById(Integer id) throws DAOException;
	
	public JASDrugUnit getDrugUnitByName(String name) throws DAOException;
	
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws DAOException;
	
	/**
	 * StoreDrugTransaction
	 */
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId,
	                                                              String description, String fromDate, String toDate,
	                                                              int min, int max) throws DAOException;
	
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws DAOException;
	
	public int countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws DAOException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws DAOException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws DAOException;
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer categoryId,
	                                                                          String drugName, String formulationName,
	                                                                          String fromDate, String toDate, int min,
	                                                                          int max) throws DAOException;
	
	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail)
	                                                                                                                         throws DAOException;
	
	public int countStoreDrugTransactionDetail(Integer storeId, Integer categoryId, String drugName, String formulationName,
	                                           String fromDate, String toDate) throws DAOException;
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                          Integer formulationId, boolean haveQuantity)
	                                                                                                                      throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId, Collection<Integer> drugs,
	                                                                 Collection<Integer> formulations) throws DAOException;
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId, Integer drugId, Integer formulationId) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(Integer transactionId) throws DAOException;
	
	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
	                                     String toDate, boolean isExpiry) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId, String drugName,
	                                                                String fromDate, String toDate, boolean isExpiry,
	                                                                int min, int max) throws DAOException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                          Integer formulationId, Integer isExpriry)
	                                                                                                                   throws DAOException;
	
	public int checkExistDrugTransactionDetail(Integer drugId) throws DAOException;
	
	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId, String name, String fromDate, String toDate, int min,
	                                                  int max) throws DAOException;
	
	public int countStoreDrugIssue(Integer storeId, String name, String fromDate, String toDate) throws DAOException;
	
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws DAOException;
	
	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws DAOException;
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugPatientDetailId) throws DAOException;
	
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugPatientDetail)
	                                                                                                       throws DAOException;
	
	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws DAOException;
	
	//change from here
	
	/**
	 * StoreItem
	 */
	
	public List<JASStoreItem> listStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty,
	                                        int min, int max) throws DAOException;
	
	public int countStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty) throws DAOException;
	
	public JASStoreItem getStoreItemById(Integer id) throws DAOException;
	
	public JASStoreItem getStoreItem(Integer storeId, Integer itemId, Integer specificationId) throws DAOException;
	
	public JASStoreItem saveStoreItem(JASStoreItem StoreItem) throws DAOException;
	
	/**
	 * StoreItemTransaction
	 */
	
	public List<JASStoreItemTransaction> listStoreItemTransaction(Integer transactionType, Integer storeId,
	                                                              String description, String fromDate, String toDate,
	                                                              int min, int max) throws DAOException;
	
	public JASStoreItemTransaction saveStoreItemTransaction(JASStoreItemTransaction storeTransaction) throws DAOException;
	
	public int countStoreItemTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws DAOException;
	
	public JASStoreItemTransaction getStoreItemTransactionById(Integer id) throws DAOException;
	
	public JASStoreItemTransaction getStoreItemTransactionByParentId(Integer parentId) throws DAOException;
	
	/**
	 * StoreItemTransactionDetail
	 */
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer categoryId,
	                                                                          String itemName, String specificationName,
	                                                                          String fromDate, String toDate, int min,
	                                                                          int max) throws DAOException;
	
	public JASStoreItemTransactionDetail saveStoreItemTransactionDetail(JASStoreItemTransactionDetail storeTransactionDetail)
	                                                                                                                         throws DAOException;
	
	public int countStoreItemTransactionDetail(Integer storeId, Integer categoryId, String itemName,
	                                           String specificationName, String fromDate, String toDate) throws DAOException;
	
	public JASStoreItemTransactionDetail getStoreItemTransactionDetailById(Integer id) throws DAOException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                          Integer specificationId, boolean haveQuantity)
	                                                                                                                        throws DAOException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemAvaiable(Integer storeId, Collection<Integer> items,
	                                                                 Collection<Integer> specifications) throws DAOException;
	
	public Integer sumStoreItemCurrentQuantity(Integer storeId, Integer itemId, Integer specificationId) throws DAOException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer transactionId) throws DAOException;
	
	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
	                                              String toDate) throws DAOException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
	                                                                         String itemName, String fromDate,
	                                                                         String toDate, int min, int max)
	                                                                                                         throws DAOException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                          Integer specificationId, int min, int max)
	                                                                                                                    throws DAOException;
	
	public int checkExistItemTransactionDetail(Integer itemId) throws DAOException;
	
	/**
	 * JASStoreItemIndent
	 */
	
	public List<JASStoreItemIndent> listStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate,
	                                                    int min, int max) throws DAOException;
	
	public int countStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate) throws DAOException;
	
	public List<JASStoreItemIndent> listSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate,
	                                                       String toDate, int min, int max) throws DAOException;
	
	public int countSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate, String toDate)
	                                                                                                                throws DAOException;
	
	public List<JASStoreItemIndent> listMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId,
	                                                        String name, Integer status, String fromDate, String toDate,
	                                                        int min, int max) throws DAOException;
	
	public int countMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId, String name, Integer status,
	                                    String fromDate, String toDate) throws DAOException;
	
	public JASStoreItemIndent saveStoreItemIndent(JASStoreItemIndent storeItemIndent) throws DAOException;
	
	public JASStoreItemIndent getStoreItemIndentById(Integer id) throws DAOException;
	
	/**
	 * JASStoreItemIndentDetail
	 */
	
	public List<JASStoreItemIndentDetail> listStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName,
	                                                                String itemName, String fromDate, String toDate,
	                                                                int min, int max) throws DAOException;
	
	public int countStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName, String itemName,
	                                      String fromDate, String toDate) throws DAOException;
	
	public List<JASStoreItemIndentDetail> listStoreItemIndentDetail(Integer indentId) throws DAOException;
	
	public JASStoreItemIndentDetail saveStoreItemIndentDetail(JASStoreItemIndentDetail StoreItemIndentDetail)
	                                                                                                         throws DAOException;
	
	public JASStoreItemIndentDetail getStoreItemIndentDetailById(Integer id) throws DAOException;
	
	public int checkExistItemIndentDetail(Integer itemId) throws DAOException;
	
	/**
	 * JASStoreItemAccount
	 */
	public List<JASStoreItemAccount> listStoreItemAccount(Integer storeId, String name, String fromDate, String toDate,
	                                                      int min, int max) throws DAOException;
	
	public int countStoreItemAccount(Integer storeId, String name, String fromDate, String toDate) throws DAOException;
	
	public JASStoreItemAccount saveStoreItemAccount(JASStoreItemAccount issue) throws DAOException;
	
	public JASStoreItemAccount getStoreItemAccountById(Integer id) throws DAOException;
	
	/**
	 * JASStoreItemAccountDetail
	 */
	public List<JASStoreItemAccountDetail> listStoreItemAccountDetail(Integer storeItemAccountDetailId) throws DAOException;
	
	public JASStoreItemAccountDetail saveStoreItemAccountDetail(JASStoreItemAccountDetail storeItemAccountDetail)
	                                                                                                             throws DAOException;
	
	public JASStoreItemAccountDetail getStoreItemAccountDetailById(Integer id) throws DAOException;
	
}
