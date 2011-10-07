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

package org.openmrs.module.jas.db.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
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
import org.openmrs.module.jas.util.ActionValue;

/**
 * Hibernate specific Idcards database methods
 */
public class HibernateJASDAO implements JASDAO {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<JASStore> listStore(int min, int max)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		criteria.setFirstResult(min).setMaxResults(max);
		List<JASStore> l = criteria.list();
		
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<JASStore> listMainStore() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		criteria.add(Restrictions.isNull("parent"));
		List<JASStore> l = criteria.list();
		//System.out.println("listMainStore>>l: "+l);
		return l;
	}

	public JASStore saveStore(JASStore store) throws DAOException {
		return (JASStore) sessionFactory.getCurrentSession().merge(store); 
	}

	public int countListStore() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}

	public JASStore getStoreById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		criteria.add(Restrictions.eq("id", id));
		JASStore store = (JASStore) criteria.uniqueResult();
		return store;
	}

	public JASStore getStoreByName(String name) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		criteria.add(Restrictions.eq("name", name));
		JASStore store = (JASStore) criteria.uniqueResult();
		return store;
	}

	public JASStore getStoreByRole(String role) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		criteria.add(Restrictions.eq("role", role));
		criteria.setMaxResults(1);
		List<JASStore> list = criteria.list();
		return CollectionUtils.isEmpty(list)? null : list.get(0);
	}
	
	public JASStore getStoreByCollectionRole(List<Role> roles) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		criteria.add(Restrictions.in("role", roles));
		criteria.setMaxResults(1);
		List<JASStore> list = criteria.list();
		return CollectionUtils.isEmpty(list)? null : list.get(0);
		
	}

	public void deleteStore(JASStore store) throws DAOException {
		sessionFactory.getCurrentSession().delete(store);
	}
	@SuppressWarnings("unchecked")
	public List<JASStore> listAllStore() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class);
		List<JASStore> list = criteria.list();
		return list;
	}
	
	public List<JASStore> listStoreByMainStore(Integer mainStoreid,boolean bothMainStore) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStore.class,"store");
		if(bothMainStore){
		criteria.add(Restrictions.or(Restrictions.eq("store.parent.id", mainStoreid),Restrictions.eq("store.id", mainStoreid)));
		}else{
			criteria.add(Restrictions.eq("store.parent.id", mainStoreid));
		}
		List<JASStore> l = criteria.list();
		return l;
	}
	
	/**
	 * Drug
	 */
	 
	
	public List<JASDrug> listDrug(Integer categoryId, String name ,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrug.class,"drug");
		if(StringUtils.isNotBlank(name)){
			criteria.add(Restrictions.like("drug.name", "%"+name+"%"));
		}
		if(categoryId != null){
			criteria.add(Restrictions.eq("drug.category.id",categoryId));
		}
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<JASDrug> l = criteria.list();
		
		return l;
	}
	
	
	public List<JASDrug> findDrug(Integer categoryId,String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrug.class,"drug");
		if(categoryId != null){
			criteria.add(Restrictions.eq("drug.category.id",categoryId));
		}
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drug.name", "%"+name+"%"));
		}
		List<JASDrug> l = criteria.list();
		
		return l;
	}
	
	public JASDrug saveDrug(JASDrug drug) throws DAOException{
		return(JASDrug) sessionFactory.getCurrentSession().merge(drug);
	}

	
	public int countListDrug(Integer categoryId, String name)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrug.class,"drug");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drug.name", "%"+name+"%"));
		}
		if(categoryId != null){
			criteria.add(Restrictions.eq("drug.category.id",categoryId));
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	public int countListDrug(Integer categoryId, Integer unitId,  Integer formulationId)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrug.class,"drug");
		if(categoryId != null && categoryId > 0){
			criteria.add(Restrictions.eq("drug.category.id",categoryId));
		}
		if(unitId != null && unitId > 0){
			criteria.add(Restrictions.eq("drug.unit.id",unitId));
		}
		
		if(formulationId != null && formulationId > 0){
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
			.createCriteria("drug.formulations", Criteria.LEFT_JOIN)
			.add(Restrictions.eq("id",formulationId));
		}
		
		//.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	public JASDrug getDrugById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrug.class,"drug")
		.add(Restrictions.eq("drug.id", id));
		return (JASDrug) criteria.uniqueResult();
	}
	
	
	public JASDrug getDrugByName(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrug.class,"drug")
		.add(Restrictions.eq("drug.name", name));
		return (JASDrug) criteria.uniqueResult();
	}
	
	public void deleteDrug(JASDrug drug) throws DAOException{
		sessionFactory.getCurrentSession().delete(drug);
	}
	
	
	/**
	 * DrugCategory
	 */
	 
	
	public List<JASDrugCategory> listDrugCategory(String name ,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugCategory.class,"drugCategory");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drugCategory.name", "%"+name+"%"));
		}
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<JASDrugCategory> l = criteria.list();
		
		return l;
	}
	
	
	public List<JASDrugCategory> findDrugCategory(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugCategory.class,"drugCategory");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drugCategory.name", "%"+name+"%"));
		}
		List<JASDrugCategory> l = criteria.list();
		
		return l;
	}
	
	public JASDrugCategory saveDrugCategory(JASDrugCategory drugCategory) throws DAOException{
		return (JASDrugCategory) sessionFactory.getCurrentSession().merge(drugCategory);
	}

	
	public int countListDrugCategory(String name)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugCategory.class,"drugCategory");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drugCategory.name", "%"+name+"%"));
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	
	public JASDrugCategory getDrugCategoryById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugCategory.class,"drugCategory")
		.add(Restrictions.eq("drugCategory.id", id));
		return (JASDrugCategory) criteria.uniqueResult();
	}
	
	
	public JASDrugCategory getDrugCategoryByName(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugCategory.class,"drugCategory")
		.add(Restrictions.eq("drugCategory.name", name));
		return (JASDrugCategory) criteria.uniqueResult();
	}
	
	public void deleteDrugCategory(JASDrugCategory drugCategory) throws DAOException{
		sessionFactory.getCurrentSession().delete(drugCategory);
	}
	
	/**
	 * DrugFormulation
	 */
	 
	
	public List<JASDrugFormulation> listDrugFormulation(String name ,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugFormulation.class,"drugFormulation");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drugFormulation.name", "%"+name+"%"));
		}
		criteria.setFirstResult(min).setMaxResults(max);
		List<JASDrugFormulation> l = criteria.list();
		
		return l;
	}
	
	
	public List<JASDrugFormulation> findDrugFormulation(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugFormulation.class,"drugFormulation");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.eq("drugFormulation.name", "%"+name+"%"));
		}
		List<JASDrugFormulation> l = criteria.list();
		
		return l;
	}
	
	public JASDrugFormulation saveDrugFormulation(JASDrugFormulation drugFormulation) throws DAOException{
		return (JASDrugFormulation)sessionFactory.getCurrentSession().merge(drugFormulation);
	}

	
	public int countListDrugFormulation(String name)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugFormulation.class,"drugFormulation");
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.like("drugFormulation.name", "%"+name+"%"));
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	
	public JASDrugFormulation getDrugFormulationById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugFormulation.class,"drugFormulation")
		.add(Restrictions.eq("drugFormulation.id", id));
		return (JASDrugFormulation) criteria.uniqueResult();
	}
	
	
	public JASDrugFormulation getDrugFormulationByName(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugFormulation.class,"drugFormulation")
		.add(Restrictions.eq("drugFormulation.name", name));
		return (JASDrugFormulation) criteria.uniqueResult();
	}
	
	public JASDrugFormulation getDrugFormulation(String name , String dozage) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugFormulation.class,"drugFormulation")
		.add(Restrictions.eq("drugFormulation.dozage", dozage))
		.add(Restrictions.eq("drugFormulation.name", name));
		return (JASDrugFormulation) criteria.uniqueResult();
	}
	
	public void deleteDrugFormulation(JASDrugFormulation drugFormulation) throws DAOException{
		sessionFactory.getCurrentSession().delete(drugFormulation);
	}
	
	/**
	 * DrugUnit
	 */
	 
	
	public List<JASDrugUnit> listDrugUnit(String name ,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugUnit.class,"drugUnit");
		if(StringUtils.isNotBlank(name)){
			criteria.add(Restrictions.like("drugUnit.name", "%"+name+"%"));
		}
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<JASDrugUnit> l = criteria.list();
		return l;
	}
	
	
	public List<JASDrugUnit> findDrugUnit(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugUnit.class,"drugUnit");
		if(StringUtils.isNotBlank(name)){
			criteria.add(Restrictions.like("drugUnit.name", "%"+name+"%"));
		}
		List<JASDrugUnit> l = criteria.list();
		
		return l;
	}
	
	public JASDrugUnit saveDrugUnit(JASDrugUnit drugUnit) throws DAOException{
		return (JASDrugUnit) sessionFactory.getCurrentSession().merge(drugUnit);
	}

	
	public int countListDrugUnit(String name)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugUnit.class,"drugUnit");
		if(StringUtils.isNotBlank(name)){
			criteria.add(Restrictions.like("drugUnit.name", "%"+name+"%"));
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	
	public JASDrugUnit getDrugUnitById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugUnit.class,"drugUnit");
		criteria.add(Restrictions.eq("drugUnit.id", id));
		return (JASDrugUnit) criteria.uniqueResult();
	}
	
	
	public JASDrugUnit getDrugUnitByName(String name) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASDrugUnit.class,"drugUnit");
		criteria.add(Restrictions.eq("drugUnit.name", name));
		return (JASDrugUnit) criteria.uniqueResult();
	}
	
	
	
	/**
	 * StoreDrugTransaction
	 */ 
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate ,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransaction.class);
		if(storeId != null){
			criteria.add(Restrictions.eq("store.id", storeId));
		}
		if(!StringUtils.isBlank(description)){
			criteria.add(Restrictions.like("description", "%"+description+"%"));
		}
		if(transactionType != null){
			criteria.add(Restrictions.eq("typeTransaction", transactionType));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startToDate)), Restrictions.le(
						"createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startToDate)), Restrictions.le(
						"createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		criteria.addOrder(Order.desc("createdOn"));
		List<JASStoreDrugTransaction> l = criteria.list();
		
		return l;
	}
	
	public List<JASStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate ) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransaction.class);
		if(storeId != null){
			criteria.add(Restrictions.eq("store.id", storeId));
		}
		if(!StringUtils.isBlank(description)){
			criteria.add(Restrictions.like("description", "%"+description+"%"));
		}
		if(transactionType != null){
			criteria.add(Restrictions.eq("typeTransaction", transactionType));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startToDate)), Restrictions.le(
						"createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startToDate)), Restrictions.le(
						"createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		List<JASStoreDrugTransaction> l = criteria.list();
		
		return l;
	}
	
	public JASStoreDrugTransaction saveStoreDrugTransaction(JASStoreDrugTransaction storeTransaction) throws DAOException{
		return (JASStoreDrugTransaction)sessionFactory.getCurrentSession().merge(storeTransaction);
	}

	public int countStoreDrugTransaction(Integer transactionType,Integer storeId, String description, String fromDate, String toDate)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransaction.class);
		if(storeId != null){
			criteria.add(Restrictions.eq("store.id", storeId));
		}
		if(!StringUtils.isBlank(description)){
			criteria.add(Restrictions.like("description", "%"+description+"%"));
		}
		if(transactionType != null){
			criteria.add(Restrictions.eq("typeTransaction", transactionType));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startToDate)), Restrictions.le(
						"createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"createdOn", formatter.parse(startToDate)), Restrictions.le(
						"createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	public JASStoreDrugTransaction getStoreDrugTransactionById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransaction.class,"storeDrugTransaction");
		criteria.add(Restrictions.eq("storeDrugTransaction.id", id));
		return (JASStoreDrugTransaction) criteria.uniqueResult();
	}
	
	public JASStoreDrugTransaction getStoreDrugTransactionByParentId(Integer parentId) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransaction.class,"storeDrugTransaction")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.createAlias("storeDrugTransaction.parent", "parent");
		criteria.add(Restrictions.eq("parent.id", parentId));
		return (JASStoreDrugTransaction) criteria.uniqueResult();
	}
	
	/**
	 * StoreDrugTransactionDetail
	 */
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, Integer categoryId,String drugName,String formulationName, String fromDate, String toDate ,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class, "transactionDetail")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.createAlias("transactionDetail.transaction", "transaction")
		.createAlias("transactionDetail.drug", "drug")
		.createAlias("transactionDetail.formulation", "formulation");
		criteria.add(Restrictions.eq("transaction.typeTransaction", ActionValue.TRANSACTION[0]));
		if(storeId != null){
			criteria.add(Restrictions.eq("transaction.store.id", storeId));
		}
		if(categoryId != null){
			criteria.add(Restrictions.eq("drug.category.id", categoryId));
		}
		if(!StringUtils.isBlank(drugName)){
			criteria.add(Restrictions.like("drug.name", "%"+drugName+"%"));
		}
		if(!StringUtils.isBlank(formulationName)){
			criteria.add(Restrictions.like("formulation.name", "%"+formulationName+"%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transaction.createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"transaction.createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventoryStoreTransactionItemType1>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transaction.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transaction.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventoryStoreTransactionItemType2>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transaction.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transaction.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventoryStoreTransactionItemType3>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<JASStoreDrugTransactionDetail> l = criteria.list();
		
		return l;
	}
	
	
	public List<JASStoreDrugTransactionDetail> listTransactionDetail(
			Integer transactionId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class, "transactionDetail")
		.add(Restrictions.eq("transactionDetail.transaction.id", transactionId));
		return criteria.list();
	}

	public JASStoreDrugTransactionDetail saveStoreDrugTransactionDetail(JASStoreDrugTransactionDetail storeTransactionDetail) throws DAOException{
		return (JASStoreDrugTransactionDetail) sessionFactory.getCurrentSession().merge(storeTransactionDetail);
	}

	public int countStoreDrugTransactionDetail(Integer storeId,  Integer categoryId,String drugName,String formulationName, String fromDate, String toDate )  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class, "transactionDetail")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.createAlias("transactionDetail.transaction", "transaction")
		.createAlias("transactionDetail.drug", "drug")
		.createAlias("transactionDetail.formulation", "formulation");
		criteria.add(Restrictions.eq("transaction.typeTransaction", ActionValue.TRANSACTION[0]));
		if(storeId != null){
			criteria.add(Restrictions.eq("transaction.store.id", storeId));
		}
		if(categoryId != null){
			criteria.add(Restrictions.eq("drug.category.id", categoryId));
		}
		if(!StringUtils.isBlank(drugName)){
			criteria.add(Restrictions.like("drug.name", "%"+drugName+"%"));
		}
		if(!StringUtils.isBlank(formulationName)){
			criteria.add(Restrictions.like("formulation.name", "%"+formulationName+"%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transaction.createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"transaction.createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventoryStoreTransactionItemType1>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transaction.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transaction.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventoryStoreTransactionItemType2>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transaction.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transaction.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventoryStoreTransactionItemType3>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	public JASStoreDrugTransactionDetail getStoreDrugTransactionDetailById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail");
		criteria.add(Restrictions.eq("transactionDetail.id", id));
		return (JASStoreDrugTransactionDetail) criteria.uniqueResult();
	}
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, boolean haveQuantity) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail")
		.createAlias("transactionDetail.transaction", "transaction")
		.add(Restrictions.eq("transaction.store.id", storeId))
		.add(Restrictions.eq("transactionDetail.drug.id", drugId))
		.add(Restrictions.eq("transactionDetail.formulation.id", formulationId))
		.add(Restrictions.eq("transaction.typeTransaction", ActionValue.TRANSACTION[0]));
		
		String date = formatterExt.format(new Date());
		String startFromDate = date + " 00:00:00";
		
		if(haveQuantity){
			criteria.add(Restrictions.gt("transactionDetail.currentQuantity", 0));
			try {
				criteria.add(Restrictions.ge("transactionDetail.dateExpiry", formatter.parse(startFromDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.addOrder(Order.asc("transactionDetail.dateExpiry"));
		List<JASStoreDrugTransactionDetail> l = criteria.list();
		return l;
	}
	
	public List<JASStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId,Integer drugId,Integer formulationId, Integer isExpiry) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail")
		.createAlias("transactionDetail.transaction", "transaction")
		.add(Restrictions.eq("transaction.store.id", storeId))
		.add(Restrictions.eq("transactionDetail.drug.id", drugId))
		.add(Restrictions.eq("transactionDetail.formulation.id", formulationId));
		criteria.addOrder(Order.desc("transactionDetail.createdOn"));
		if(isExpiry != null && isExpiry == 1)
		{
			criteria.add(Restrictions.lt("transactionDetail.dateExpiry", new Date()));
		}else{
			criteria.add(Restrictions.ge("transactionDetail.dateExpiry", new Date()));
		}
		List<JASStoreDrugTransactionDetail> l = criteria.list();
		return l;
	}
	
	
	
	public Integer sumCurrentQuantityDrugOfStore(Integer storeId,Integer drugId,Integer formulationId) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail")
		.createAlias("transactionDetail.transaction", "transaction")
		.add(Restrictions.eq("transaction.store.id", storeId))
		.add(Restrictions.eq("transaction.typeTransaction", ActionValue.TRANSACTION[0]))
		.add(Restrictions.eq("transactionDetail.drug.id", drugId))
		.add(Restrictions.eq("transactionDetail.formulation.id", formulationId));
		
		criteria.add(Restrictions.gt("transactionDetail.currentQuantity", 0));
		criteria.add(Restrictions.gt("transactionDetail.dateExpiry", new Date()));
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.sum("currentQuantity"));
		criteria.setProjection(proList);
		Object l = criteria.uniqueResult();
		return l != null ? (Integer)l : 0 ;
	}
	public List<JASStoreDrugTransactionDetail> listStoreDrugAvaiable(Integer storeId,Collection<Integer> drugs,Collection<Integer> formulations) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail")
		.createAlias("transactionDetail.transaction", "transaction")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("drug")).add(Projections.groupProperty("formulation")).add(Projections.sum("currentQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id",storeId));
		if(drugs != null){
			criteria.createCriteria("transactionDetail.drug", Criteria.INNER_JOIN)
			.add(Restrictions.in("id", drugs));
		}
		criteria.add(Restrictions.eq("transaction.typeTransaction", ActionValue.TRANSACTION[0]));
		if(formulations != null){
			criteria.createCriteria("transactionDetail.formulation", Criteria.INNER_JOIN)
			.add(Restrictions.in("id", formulations));
		}
		criteria.setProjection(proList);
		criteria.add(Restrictions.ge("transactionDetail.dateExpiry", new Date()));
		List<Object> lst = criteria.list();
		if (lst == null || lst.size() == 0)
			return null;
		List<JASStoreDrugTransactionDetail> list = new ArrayList<JASStoreDrugTransactionDetail>();
		//System.out.println("lst size: "+lst.size());
		for(int i=0;i<lst.size();i++){
			Object[] row = (Object[])lst.get(i);
			JASStoreDrugTransactionDetail tDetail = new JASStoreDrugTransactionDetail();
			tDetail.setDrug((JASDrug)row[0]);
			tDetail.setFormulation((JASDrugFormulation)row[1]);
			tDetail.setCurrentQuantity((Integer)row[2]);
			list.add(tDetail);
			//System.out.println("I: "+i+" drug: "+tDetail.getDrug().getName()+" formulation: "+tDetail.getFormulation().getName()+" quantity: "+tDetail.getCurrentQuantity());
		}
		return list;
	}
	
	public List<JASStoreDrugTransactionDetail> listViewStockBalance(Integer storeId,Integer categoryId, String drugName ,String fromDate , String toDate, boolean isExpiry,int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail")
		.createAlias("transactionDetail.transaction", "transaction")
		.createAlias("transactionDetail.drug", "drugAlias")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("drug")).add(Projections.groupProperty("formulation")).add(Projections.sum("currentQuantity")).add(Projections.sum("quantity")).add(Projections.sum("issueQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id",storeId));
		if(categoryId != null){
			criteria.add(Restrictions.eq("drugAlias.category.id",categoryId));
		}
		if(!StringUtils.isBlank(drugName)){
			criteria.add(Restrictions.like("drugAlias.name","%"+drugName+"%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transactionDetail.createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"transactionDetail.createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transactionDetail.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transactionDetail.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transactionDetail.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transactionDetail.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventorySubStoreIndent>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		if(isExpiry)
		{
			criteria.add(Restrictions.lt("transactionDetail.dateExpiry", new Date()));
		}else{
			criteria.add(Restrictions.ge("transactionDetail.dateExpiry", new Date()));
		}
		criteria.setProjection(proList);
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<Object> lst = criteria.list();
		if (lst == null || lst.size() == 0)
			return null;
		List<JASStoreDrugTransactionDetail> list = new ArrayList<JASStoreDrugTransactionDetail>();
		for(int i=0;i<lst.size();i++){
			Object[] row = (Object[])lst.get(i);
			JASStoreDrugTransactionDetail tDetail = new JASStoreDrugTransactionDetail();
			tDetail.setDrug((JASDrug)row[0]);
			tDetail.setFormulation((JASDrugFormulation)row[1]);
			tDetail.setCurrentQuantity((Integer)row[2]);
			tDetail.setQuantity((Integer)row[3]);
			tDetail.setIssueQuantity((Integer)row[4]);
			list.add(tDetail);
		}
		
		return list;
	}
	
	public Integer countViewStockBalance(Integer storeId,Integer categoryId, String drugName ,String fromDate , String toDate, boolean isExpiry) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class,"transactionDetail")
		.createAlias("transactionDetail.transaction", "transaction")
		.createAlias("transactionDetail.drug", "drugAlias");

		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("drug")).add(Projections.groupProperty("formulation")).add(Projections.sum("currentQuantity")).add(Projections.sum("quantity")).add(Projections.sum("issueQuantity"));
		criteria.add(Restrictions.eq("transaction.store.id",storeId));
		if(categoryId != null){
			criteria.add(Restrictions.eq("drugAlias.category.id",categoryId));
		}
		if(!StringUtils.isBlank(drugName)){
			criteria.add(Restrictions.like("drugAlias.name","%"+drugName+"%"));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transactionDetail.createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"transactionDetail.createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transactionDetail.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transactionDetail.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listSubStoreIndent>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"transactionDetail.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"transactionDetail.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listInventorySubStoreIndent>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		if(isExpiry)
		{
			criteria.add(Restrictions.lt("transactionDetail.dateExpiry", new Date()));
		}else{
			criteria.add(Restrictions.ge("transactionDetail.dateExpiry", new Date()));
		}
		criteria.setProjection(proList);
		List<Object> list = criteria.list();
		Number total = 0;
		if(!CollectionUtils.isEmpty(list)){
			total = (Number)list.size();
		}
		return total.intValue();
	}
	
	public int checkExistDrugTransactionDetail(Integer drugId)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugTransactionDetail.class, "transactionDetail")
		.add(Restrictions.eq("transactionDetail.drug.id", drugId));
		Number rs =  (Number) criteria.setProjection( Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	
	
	/**
	 * JASStoreDrugIssue
	 */
	public List<JASStoreDrugIssue> listStoreDrugIssue(Integer storeId,String  name, String fromDate, String toDate, int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugIssue.class, "bill")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.createAlias("bill.store", "store");
		if(storeId != null){
			criteria.add(Restrictions.eq("store.id", storeId));
		}
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.or(Restrictions.like("bill.billNumber", "%"+name+"%"), Restrictions.like("bill.name", "%"+name+"%")));
		}
		
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"bill.createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"bill.createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listBill>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"bill.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"bill.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listBill>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"bill.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"bill.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("listBill>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		criteria.addOrder(Order.desc("bill.createdOn"));
		criteria.setFirstResult(min).setMaxResults(max);
		List<JASStoreDrugIssue> l = criteria.list();
		return l;
	}
	
	public int countStoreDrugIssue(Integer storeId,String  name, String fromDate, String toDate)  throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugIssue.class, "bill")
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.createAlias("bill.store", "store")
		.setProjection(Projections.rowCount());

		
		
		if(storeId != null){
			criteria.add(Restrictions.eq("store.id", storeId));
		}
		if(!StringUtils.isBlank(name)){
			criteria.add(Restrictions.or(Restrictions.like("bill.billNumber", "%"+name+"%"), Restrictions.like("bill.name", "%"+name+"%")));
		}
		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"bill.createdOn", formatter.parse(startFromDate)), Restrictions.le(
						"bill.createdOn", formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("countBill>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"bill.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"bill.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("countBill>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		} 
		else if (!StringUtils.isBlank(fromDate) && !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"bill.createdOn", formatter.parse(startToDate)), Restrictions.le(
						"bill.createdOn", formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("countBill>>Error convert date: "+ e.toString());
				e.printStackTrace();
			}
		}
		Number rs =  (Number) criteria.uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	public JASStoreDrugIssue saveStoreDrugIssue(JASStoreDrugIssue bill) throws DAOException{
		return (JASStoreDrugIssue) sessionFactory.getCurrentSession().merge(bill);
	}

	public JASStoreDrugIssue getStoreDrugIssueById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugIssue.class, "drugIssue");
		criteria.add(Restrictions.eq("drugIssue.id", id));
		return(JASStoreDrugIssue)criteria.uniqueResult();
	}
	
	/**
	 * JASStoreDrugIssueDetail
	 */
	public List<JASStoreDrugIssueDetail> listStoreDrugIssueDetail(Integer storeDrugPatientId) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugIssueDetail.class, "billDetail")
		.createAlias("billDetail.storeDrugIssue", "storeDrugIssue")
		.add(Restrictions.eq("storeDrugIssue.id",storeDrugPatientId));
		List<JASStoreDrugIssueDetail> l = criteria.list();
		return l;
	}
	
	public JASStoreDrugIssueDetail saveStoreDrugIssueDetail(JASStoreDrugIssueDetail storeDrugPatientDetail) throws DAOException{
		return (JASStoreDrugIssueDetail) sessionFactory.getCurrentSession().merge(storeDrugPatientDetail);
	}

	public JASStoreDrugIssueDetail getStoreDrugIssueDetailById(Integer id) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JASStoreDrugIssueDetail.class, "billDetail")
		.add(Restrictions.eq("billDetail.id",id));
		
		return (JASStoreDrugIssueDetail) criteria.uniqueResult();
	}
	
	public void deleteDrugUnit(JASDrugUnit drugUnit) throws DAOException{
		sessionFactory.getCurrentSession().delete(drugUnit);
	}
	//============================================================================
	
	
	
	
}
