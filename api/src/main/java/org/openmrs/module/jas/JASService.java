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

package org.openmrs.module.jas;

import java.util.Collection;
import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
public interface JASService extends OpenmrsService {
	
	/**
	 * Store
	 */
	@Transactional(readOnly = true)
	public List<JASStore> listStore(int min, int max) throws APIException;
	
	@Transactional(readOnly = true)
	public List<JASStore> listAllStore() throws APIException;
	
	@Transactional(readOnly = true)
	public List<JASStore> listMainStore() throws APIException;
	
	@Transactional(readOnly = false)
	public JASStore saveStore(JASStore store) throws APIException;
	
	@Transactional(readOnly = true)
	public int countListStore() throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreById(Integer id) throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreByName(String name) throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreByRole(String role) throws APIException;
	
	@Transactional(readOnly = true)
	public JASStore getStoreByCollectionRole(List<Role> roles) throws APIException;
	
	@Transactional(readOnly = false)
	public void deleteStore(JASStore store) throws APIException;
	
	@Transactional(readOnly = true)
	public List<JASStore> listStoreByMainStore(Integer mainStoreid, boolean bothMainStore) throws APIException;
	
	/**
	 * ItemCategory
	 */
	
	public List<JASItemCategory> listItemCategory(String name, int min, int max) throws APIException;
	
	public List<JASItemCategory> findItemCategory(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASItemCategory saveItemCategory(JASItemCategory category) throws APIException;
	
	public int countListItemCategory(String name) throws APIException;
	
	public JASItemCategory getItemCategoryById(Integer id) throws APIException;
	
	public JASItemCategory getItemCategoryByName(String name) throws APIException;
	
	public void deleteItemCategory(JASItemCategory category) throws APIException;
	
	/**
	 * ItemSubCategory
	 */
	
	public List<JASItemSubCategory> listItemSubCategory(String name, int min, int max) throws APIException;
	
	public List<JASItemSubCategory> findItemSubCategory(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASItemSubCategory saveItemSubCategory(JASItemSubCategory subCategory) throws APIException;
	
	public int countListItemSubCategory(String name) throws APIException;
	
	public JASItemSubCategory getItemSubCategoryById(Integer id) throws APIException;
	
	public JASItemSubCategory getItemSubCategoryByName(Integer categoryId, String name) throws APIException;
	
	public void deleteItemSubCategory(JASItemSubCategory subCategory) throws APIException;
	
	public List<JASItemSubCategory> listSubCatByCat(Integer categoryId) throws APIException;
	
	/**
	 * ItemSpecification
	 */
	
	public List<JASItemSpecification> listItemSpecification(String name, int min, int max) throws APIException;
	
	public List<JASItemSpecification> findItemSpecification(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASItemSpecification saveItemSpecification(JASItemSpecification specification) throws APIException;
	
	public int countListItemSpecification(String name) throws APIException;
	
	public JASItemSpecification getItemSpecificationById(Integer id) throws APIException;
	
	public JASItemSpecification getItemSpecificationByName(String name) throws APIException;
	
	public void deleteItemSpecification(JASItemSpecification specification) throws APIException;
	
	/**
	 * ItemUnit
	 */
	
	public List<JASItemUnit> listItemUnit(String name, int min, int max) throws APIException;
	
	public List<JASItemUnit> findItemUnit(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASItemUnit saveItemUnit(JASItemUnit unit) throws APIException;
	
	public int countListItemUnit(String name) throws APIException;
	
	public JASItemUnit getItemUnitById(Integer id) throws APIException;
	
	public JASItemUnit getItemUnitByName(String name) throws APIException;
	
	public void deleteItemUnit(JASItemUnit unit) throws APIException;
	
	/**
	 * Item
	 */
	
	public List<JASItem> listItem(Integer categoryId, String name, int min, int max) throws APIException;
	
	public List<JASItem> findItem(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASItem saveItem(JASItem item) throws APIException;
	
	public int countListItem(Integer categoryId, String name) throws APIException;
	
	public JASItem getItemById(Integer id) throws APIException;
	
	public JASItem getItemByName(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public void deleteItem(JASItem item) throws APIException;
	
	public List<JASItem> findItem(Integer categoryId, String name) throws APIException;
	
	public int countItem(Integer categoryId, Integer unitId, Integer subCategoryId, Integer specificationId)
	                                                                                                        throws APIException;
	
	/**
	 * Drug
	 */
	
	public List<JASDrug> listDrug(Integer categoryId, String name, int min, int max) throws APIException;
	
	public List<JASDrug> findDrug(Integer categoryId, String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASDrug saveDrug(JASDrug drug) throws APIException;
	
	public int countListDrug(Integer categoryId, String name) throws APIException;
	
	public JASDrug getDrugById(Integer id) throws APIException;
	
	public JASDrug getDrugByName(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public void deleteDrug(JASDrug drug) throws APIException;
	
	public int countListDrug(Integer categoryId, Integer unitId, Integer formulationId) throws APIException;
	
	/**
	 * DrugCategory
	 */
	
	public List<JASDrugCategory> listDrugCategory(String name, int min, int max) throws APIException;
	
	public List<JASDrugCategory> findDrugCategory(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws APIException;
	
	public int countListDrugCategory(String name) throws APIException;
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws APIException;
	
	public JASDrugCategory getDrugCategoryByName(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws APIException;
	
	/**
	 * DrugFormulation
	 */
	
	public List<JASDrugFormulation> listDrugFormulation(String name, int min, int max) throws APIException;
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws APIException;
	
	public int countListDrugFormulation(String name) throws APIException;
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws APIException;
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws APIException;
	
	public JASDrugFormulation getDrugFormulation(String name, String dozage) throws APIException;
	
	@Transactional(readOnly = false)
	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws APIException;
	
	/**
	 * DrugUnit
	 */
	public List<JASDrugUnit> listDrugUnit(String name, int min, int max) throws APIException;
	
	public List<JASDrugUnit> findDrugUnit(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws APIException;
	
	public int countListDrugUnit(String name) throws APIException;
	
	public JASDrugUnit getDrugUnitById(Integer id) throws APIException;
	
	public JASDrugUnit getDrugUnitByName(String name) throws APIException;
	
	@Transactional(readOnly = false)
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws APIException;
	
	/**
	 * StoreDrugTransaction
	 */
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId,
	                                                              String description, String fromDate, String toDate,
	                                                              int min, int max) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws APIException;
	
	public int countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws APIException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws APIException;
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws APIException;
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer categoryId,
	                                                                          String drugName, String formulationName,
	                                                                          String fromDate, String toDate, int min,
	                                                                          int max) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail)
	                                                                                                                         throws APIException;
	
	public int countStoreDrugTransactionDetail(Integer storeId, Integer categoryId, String drugName, String formulationName,
	                                           String fromDate, String toDate) throws APIException;
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                          Integer formulationId, boolean haveQuantity)
	                                                                                                                      throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId, Collection<Integer> drugs,
	                                                                 Collection<Integer> formulations) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(Integer transactionId) throws APIException;
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId, Integer drugId, Integer formulationId) throws APIException;
	
	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
	                                     String toDate, boolean isExpiry) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId, String drugName,
	                                                                String fromDate, String toDate, boolean isExpiry,
	                                                                int min, int max) throws APIException;
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer drugId,
	                                                                          Integer formulationId, Integer isExpiry)
	                                                                                                                  throws APIException;
	
	public int checkExistDrugTransactionDetail(Integer drugId) throws APIException;
	
	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId, String name, String fromDate, String toDate, int min,
	                                                  int max) throws APIException;
	
	public int countStoreDrugIssue(Integer storeId, String name, String fromDate, String toDate) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws APIException;
	
	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws APIException;
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugIssueDetailId) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugIssueDetail)
	                                                                                                     throws APIException;
	
	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws APIException;
	
	//change code
	/**
	 * StoreItem
	 */
	
	public List<JASStoreItem> listStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty,
	                                        int min, int max) throws APIException;
	
	public int countStoreItem(Integer storeId, Integer categoryId, String itemName, Integer reorderQty) throws APIException;
	
	public JASStoreItem getStoreItemById(Integer id) throws APIException;
	
	public JASStoreItem getStoreItem(Integer storeId, Integer itemId, Integer specificationId) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItem saveStoreItem(JASStoreItem StoreItem) throws APIException;
	
	/**
	 * StoreItemTransaction
	 */
	
	public List<JASStoreItemTransaction> listStoreItemTransaction(Integer transactionType, Integer storeId,
	                                                              String description, String fromDate, String toDate,
	                                                              int min, int max) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItemTransaction saveStoreItemTransaction(JASStoreItemTransaction storeTransaction) throws APIException;
	
	public int countStoreItemTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
	                                     String toDate) throws APIException;
	
	public JASStoreItemTransaction getStoreItemTransactionById(Integer id) throws APIException;
	
	public JASStoreItemTransaction getStoreItemTransactionByParentId(Integer parentId) throws APIException;
	
	/**
	 * StoreItemTransactionDetail
	 */
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer categoryId,
	                                                                          String itemName, String specificationName,
	                                                                          String fromDate, String toDate, int min,
	                                                                          int max) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItemTransactionDetail saveStoreItemTransactionDetail(JASStoreItemTransactionDetail storeTransactionDetail)
	                                                                                                                         throws APIException;
	
	public int countStoreItemTransactionDetail(Integer storeId, Integer categoryId, String itemName,
	                                           String specificationName, String fromDate, String toDate) throws APIException;
	
	public JASStoreItemTransactionDetail getStoreItemTransactionDetailById(Integer id) throws APIException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                          Integer specificationId, boolean haveQuantity)
	                                                                                                                        throws APIException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemAvaiable(Integer storeId, Collection<Integer> items,
	                                                                 Collection<Integer> specifications) throws APIException;
	
	public Integer sumStoreItemCurrentQuantity(Integer storeId, Integer itemId, Integer specificationId) throws APIException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer transactionId) throws APIException;
	
	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
	                                              String toDate) throws APIException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
	                                                                         String itemName, String fromDate,
	                                                                         String toDate, int min, int max)
	                                                                                                         throws APIException;
	
	public List<JASStoreItemTransactionDetail> listStoreItemTransactionDetail(Integer storeId, Integer itemId,
	                                                                          Integer specificationId, int min, int max)
	                                                                                                                    throws APIException;
	
	public int checkExistItemTransactionDetail(Integer itemId) throws APIException;
	
	/**
	 * JASStoreItemIndent
	 */
	
	public List<JASStoreItemIndent> listStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate,
	                                                    int min, int max) throws APIException;
	
	public int countStoreItemIndent(Integer StoreId, String name, String fromDate, String toDate) throws APIException;
	
	public List<JASStoreItemIndent> listSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate,
	                                                       String toDate, int min, int max) throws APIException;
	
	public int countSubStoreItemIndent(Integer storeId, String name, Integer status, String fromDate, String toDate)
	                                                                                                                throws APIException;
	
	public List<JASStoreItemIndent> listMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId,
	                                                        String name, Integer status, String fromDate, String toDate,
	                                                        int min, int max) throws APIException;
	
	public int countMainStoreItemIndent(Integer id, Integer mainStoreId, Integer subStoreId, String name, Integer status,
	                                    String fromDate, String toDate) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItemIndent saveStoreItemIndent(JASStoreItemIndent storeItemIndent) throws APIException;
	
	public JASStoreItemIndent getStoreItemIndentById(Integer id) throws APIException;
	
	/**
	 * JASStoreItemIndentDetail
	 */
	
	public List<JASStoreItemIndentDetail> listStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName,
	                                                                String itemName, String fromDate, String toDate,
	                                                                int min, int max) throws APIException;
	
	public int countStoreItemIndentDetail(Integer storeId, Integer categoryId, String indentName, String itemName,
	                                      String fromDate, String toDate) throws APIException;
	
	public List<JASStoreItemIndentDetail> listStoreItemIndentDetail(Integer indentId) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItemIndentDetail saveStoreItemIndentDetail(JASStoreItemIndentDetail storeItemIndentDetail)
	                                                                                                         throws APIException;
	
	public JASStoreItemIndentDetail getStoreItemIndentDetailById(Integer id) throws APIException;
	
	public int checkExistItemIndentDetail(Integer itemId) throws APIException;
	
	/**
	 * JASStoreItemAccount
	 */
	public List<JASStoreItemAccount> listStoreItemAccount(Integer storeId, String name, String fromDate, String toDate,
	                                                      int min, int max) throws APIException;
	
	public int countStoreItemAccount(Integer storeId, String name, String fromDate, String toDate) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItemAccount saveStoreItemAccount(JASStoreItemAccount issue) throws APIException;
	
	public JASStoreItemAccount getStoreItemAccountById(Integer id) throws APIException;
	
	/**
	 * JASStoreItemAccountDetail
	 */
	public List<JASStoreItemAccountDetail> listStoreItemAccountDetail(Integer storeItemAccountDetailId) throws APIException;
	
	@Transactional(readOnly = false)
	public JASStoreItemAccountDetail saveStoreItemAccountDetail(JASStoreItemAccountDetail storeItemAccountDetail)
	                                                                                                             throws APIException;
	
	public JASStoreItemAccountDetail getStoreItemAccountDetailById(Integer id) throws APIException;
	
}
