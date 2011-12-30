
/**
 *  Copyright 2010 Health Information Systems Project of India
 *
 *  This file is part of jas-omod module.
 *
 *  jas-omod module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  jas-omod module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with jas-omod module.  If not, see <http://www.gnu.org/licenses/>
 *
 **/

package org.openmrs.module.jas.web.controller.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugIssue;
import org.openmrs.module.jas.model.JASStoreDrugIssueDetail;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.util.DateUtils;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p> Class: EditBillController </p>
 * <p> Package: org.openmrs.module.jas.web.controller.transaction </p> 
 * <p> Author: Nguyen manh chuyen(Email: chuyennmth@gmail.com) </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Dec 21, 2011 3:09:02 PM </p>
 * <p> Update date: Dec 21, 2011 3:09:02 PM </p>
 **/
@Controller("EditBillControllerJAS")
public class EditBillController {
	@RequestMapping(value="/module/jas/editBill.form",method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="billId",required=false)  Integer billId,
			Model model) {
	List<JASStoreDrugIssueDetail> listIssueDetailExt = null;
	JASStoreDrugIssue issueDrugPatient = null;
	BigDecimal total = new BigDecimal(0);	
	 int userId = Context.getAuthenticatedUser().getId();
	 String billDetail = "editBillDetail_"+userId;
	 String bill = "editBill_"+userId;
	 listIssueDetailExt =  (List<JASStoreDrugIssueDetail>)StoreSingleton.getInstance().getHash().get(billDetail);
	 issueDrugPatient =  (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get(bill);
	 JASService jasService = (JASService) Context.getService(JASService.class);
	 if(CollectionUtils.isEmpty(listIssueDetailExt) || issueDrugPatient == null){
		 List<JASStoreDrugIssueDetail> listIssueDetail = jasService.listStoreDrugIssueDetail(billId);
		 if(CollectionUtils.isEmpty(listIssueDetail)){
			 System.out.println("listIssueDetail is null so redirect to list bill");
			 return "redirect:/module/jas/issueDrugList.form";
		 }
		 listIssueDetailExt = new ArrayList<JASStoreDrugIssueDetail>();
		 
		 for(JASStoreDrugIssueDetail t : listIssueDetail){
			 JASStoreDrugIssueDetail issueDetail = t;
			 issueDetail.setVAT(t.getTransactionDetail().getVAT());
			 issueDetail.setUnitPrice(t.getTransactionDetail().getUnitPrice());
			 issueDetail.setOtherTaxes(t.getTransactionDetail().getOtherTaxes());
			 
			 BigDecimal tot = t.getRate().multiply(new BigDecimal(t.getQuantity()-t.getReturnQuantity()));
			 tot = tot.setScale(2, BigDecimal.ROUND_UP);
			 issueDetail.setTotalPrice(tot);
			 total = total.add(tot);
			 listIssueDetailExt.add(issueDetail);
		 }
		
		 StoreSingleton.getInstance().setEdit(false);
		 StoreSingleton.getInstance().getHash().put(billDetail, listIssueDetailExt);
		 issueDrugPatient = jasService.getStoreDrugIssueById(billId);
		 StoreSingleton.getInstance().getHash().put(bill, issueDrugPatient);
	 }else{
		 for(JASStoreDrugIssueDetail t : listIssueDetailExt){
			 BigDecimal tot = t.getRate().multiply(new BigDecimal(t.getQuantity() - t.getTempReturnQuantity() - t.getReturnQuantity()));
			 tot = tot.setScale(2, BigDecimal.ROUND_UP);
			 total = total.add(tot);
		 }
		 
	 }
	 model.addAttribute("edit",  StoreSingleton.getInstance().isEdit());
	 model.addAttribute("total", total);
	 model.addAttribute("issueDrugPatient", issueDrugPatient);
	 model.addAttribute("listPatientDetail", listIssueDetailExt);
	 return "/module/jas/transaction/editBill";
	 
	}
	@RequestMapping(value="/module/jas/cancelEditBill.form",method = RequestMethod.GET)
	public String cancelEditBill(
			Model model) {
		 int userId = Context.getAuthenticatedUser().getId();
		  String billDetail = "editBillDetail_"+userId;
		  String bill = "editBill_"+userId;
		  StoreSingleton.getInstance().getHash().remove(bill);
		  StoreSingleton.getInstance().getHash().remove(billDetail);
		  System.out.println("Cancel edit bill return list issue!");
		 return "redirect:/module/jas/issueDrugList.form";
	}
		@RequestMapping(value="/module/jas/editBill.form",method = RequestMethod.POST)
		public String addDrugToBill(
				HttpServletRequest request,
				Model model) {
		  List<String> errors = new ArrayList<String>();
		  int userId = Context.getAuthenticatedUser().getId();
		  BigDecimal total = new BigDecimal(0);	
		  String billDetail = "editBillDetail_"+userId;
		  String bill = "editBill_"+userId;
		  List<JASStoreDrugIssueDetail> listIssueDetail =  (List<JASStoreDrugIssueDetail>)StoreSingleton.getInstance().getHash().get(billDetail);
		  JASStoreDrugIssue issueDrugPatient =  (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get(bill);
		  
		  if(CollectionUtils.isEmpty(listIssueDetail)){
			  return "redirect:/module/jas/issueDrugList.form";
		  }
		  
		  for(JASStoreDrugIssueDetail t : listIssueDetail){
				 BigDecimal tot = t.getRate().multiply(new BigDecimal(t.getQuantity() - t.getTempReturnQuantity() - t.getReturnQuantity()));
				 tot = tot.setScale(2, BigDecimal.ROUND_UP);
				 total = total.add(tot);
			 }
		  
		  JASService jasService = (JASService) Context.getService(JASService.class);
			Integer formulation = NumberUtils.toInt(request.getParameter("formulation"),0);
			String drugName = request.getParameter("drugName");
			
			JASDrug drug = jasService.getDrugByName(drugName);
			if(drug == null){
				errors.add("jas.issueDrug.drug.required");
				
			}
			JASDrugFormulation formulationO = jasService.getDrugFormulationById(formulation);
			if(formulationO == null)
			{
				errors.add("jas.receiptDrug.formulation.required");
			}
			if(formulationO != null && drug != null && !drug.getFormulations().contains(formulationO))
			{
				errors.add("jas.receiptDrug.formulation.notCorrect");
			}
			if(CollectionUtils.isNotEmpty(errors)){
				
				model.addAttribute("errors", errors);
				model.addAttribute("edit",  StoreSingleton.getInstance().isEdit());
				 model.addAttribute("total", total);
				 model.addAttribute("issueDrugPatient", issueDrugPatient);
				 model.addAttribute("listPatientDetail", listIssueDetail);
				return "/module/jas/transaction/editBill";
			}
			JASStore store =  jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
			List<Integer> listIssueQty = new ArrayList<Integer>();
			List<JASStoreDrugTransactionDetail> listReceiptDrug = jasService.listStoreDrugTransactionDetail(store.getId(), drug.getId(), formulation, true);
			boolean checkCorrect = true;
			if(listReceiptDrug != null){
				model.addAttribute("listReceiptDrug", listReceiptDrug);
				for(JASStoreDrugTransactionDetail t: listReceiptDrug){
					
					Integer temp = NumberUtils.toInt(request.getParameter(t.getId()+"") , 0);
					//System.out.println(" transaction detail "+t.getId() +" : "+temp);
					if(temp > 0){
						checkCorrect = false;
					}else{
						temp = 0;
					}
					listIssueQty.add(temp);
					if(temp > t.getCurrentQuantity()){
						errors.add("jas.issueDrug.quantity.lessthanQuantity.required");
					}
				}
			}else{
				errors.add("jas.issueDrug.drug.required");
			}
			if(checkCorrect){
				errors.add("jas.issueDrug.quantity.required");
			}
			if(errors != null && errors.size() > 0){
				
				model.addAttribute("errors", errors);
				 model.addAttribute("issueDrugPatient", issueDrugPatient);
				model.addAttribute("listPatientDetail", listIssueDetail);
				return "/module/jas/transaction/editBill";
			}
			
			List<JASStoreDrugIssueDetail> listExt = new ArrayList<JASStoreDrugIssueDetail>();
			listExt = new ArrayList<JASStoreDrugIssueDetail>(listIssueDetail);
			
			for(JASStoreDrugTransactionDetail t: listReceiptDrug){
				Integer temp = NumberUtils.toInt(request.getParameter(t.getId()+"") , 0);
				if(temp > 0){
					//System.out.println("temp add vao issue : "+temp);
						for(int i=0;i<listIssueDetail.size();i++){
							JASStoreDrugIssueDetail dtail = listIssueDetail.get(i);
							if(t.getId().equals(dtail.getTransactionDetail().getId()) && listExt.get(i).getId() == null){
								listExt.remove(i);
								temp += dtail.getQuantity();
								break;
							}
						}
					
					
					JASStoreDrugIssueDetail issueDrugDetail = new JASStoreDrugIssueDetail();
					issueDrugDetail.setTransactionDetail(t);
					issueDrugDetail.setQuantity(temp);
					issueDrugDetail.setVAT(t.getVAT());
					issueDrugDetail.setUnitPrice(t.getUnitPrice());
					issueDrugDetail.setOtherTaxes(t.getOtherTaxes());
					issueDrugDetail.setStoreDrugIssue(issueDrugPatient);
					//BigDecimal moneyUnitPrice = issueDrugDetail.getRate().multiply(new BigDecimal(temp));
					//moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(t.getVAT().divide(new BigDecimal(100))));
					BigDecimal tot = issueDrugDetail.getRate().multiply(new BigDecimal(temp));
					tot = tot.setScale(2, BigDecimal.ROUND_UP);
					issueDrugDetail.setTotalPrice(tot);
					listExt.add(issueDrugDetail);
				}
			}
			StoreSingleton.getInstance().setEdit(true);
			StoreSingleton.getInstance().getHash().put(billDetail, listExt);
		  
			
			
			return "redirect:/module/jas/editBill.form";
		}
	@RequestMapping(value="/module/jas/finishEditBill.form",method = RequestMethod.POST)
	public String finishEditBill(
			Model model) {
		  int userId = Context.getAuthenticatedUser().getId();
		  String billDetail = "editBillDetail_"+userId;
		  String bill = "editBill_"+userId;
		  JASService jasService = (JASService) Context.getService(JASService.class);
		  List<JASStoreDrugIssueDetail> listIssueDetail =  (List<JASStoreDrugIssueDetail>)StoreSingleton.getInstance().getHash().get(billDetail);
		  JASStoreDrugIssue issueDrugPatient =  (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get(bill);
		  JASStore store =  jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		  if(StoreSingleton.getInstance().isEdit()){
			  
			  //return drug to transaction, 
			  
			  //create transaction
			  Date date = new Date();
			 //create transaction receipt drug
			 JASStoreDrugTransaction transaction = new JASStoreDrugTransaction();
			 transaction.setDescription("REFUND DRUG "+DateUtils.getDDMMYYYY()+" "+issueDrugPatient.getBillNumber());
			 transaction.setStore(store);
			 transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
			 transaction.setCreatedOn(date);
			 transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			 transaction = jasService.saveStoreDrugTransaction(transaction);
			 
			 JASStoreDrugTransaction transactionIssue = new JASStoreDrugTransaction();
			 transactionIssue.setDescription("ISSUE DRUG "+DateUtils.getDDMMYYYY()+" "+issueDrugPatient.getBillNumber());
			 transactionIssue.setStore(store);
			 transactionIssue.setTypeTransaction(ActionValue.TRANSACTION[1]);
			 transactionIssue.setCreatedOn(date);
			 transactionIssue.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			 
			 boolean checkIssue = true;
			
			 //create transaction detail
			 BigDecimal totalPrice = new BigDecimal(0);
			 for(JASStoreDrugIssueDetail tD : listIssueDetail){
				 if(tD.getTempReturnQuantity() > 0){
					 //this for refund drug on bill
					 int total = jasService.sumCurrentQuantityDrugOfStore(store.getId(),tD.getTransactionDetail().getDrug().getId(),tD.getTransactionDetail().getFormulation().getId());
					 JASStoreDrugTransactionDetail transDetail = new  JASStoreDrugTransactionDetail();
					 //transDetail = tD.getTransactionDetail();
					 transDetail.setTransaction(transaction);
					 transDetail.setOpeningBalance(total);
					 transDetail.setClosingBalance(total + tD.getTempReturnQuantity());
					 transDetail.setQuantity(tD.getTempReturnQuantity());
					 transDetail.setCurrentQuantity(tD.getTempReturnQuantity());
					 transDetail.setIssueQuantity(0);
					 transDetail.setVAT(tD.getTransactionDetail().getVAT());
					 transDetail.setUnitPrice(tD.getTransactionDetail().getUnitPrice());
					 transDetail.setDrug(tD.getTransactionDetail().getDrug());
					 transDetail.setFormulation(tD.getTransactionDetail().getFormulation());
					 transDetail.setBatchNo(tD.getTransactionDetail().getBatchNo());
					 transDetail.setCompanyName(tD.getTransactionDetail().getCompanyName());
					 transDetail.setDateManufacture(tD.getTransactionDetail().getDateManufacture());
					 transDetail.setDateExpiry(tD.getTransactionDetail().getDateExpiry());
					 transDetail.setReceiptDate(tD.getTransactionDetail().getReceiptDate());
					 transDetail.setCreatedOn(date);
					 transDetail.setOtherTaxes(tD.getTransactionDetail().getOtherTaxes());
					 BigDecimal tot = transDetail.getRate().multiply(new BigDecimal(tD.getTempReturnQuantity()));
					 tot = tot.setScale(2, BigDecimal.ROUND_UP);
					 transDetail.setTotalPrice(tot);
					 jasService.saveStoreDrugTransactionDetail(transDetail);
					 
					 
					 //save StoreIssueDrugDetail
					 tD.setReturnQuantity(tD.getReturnQuantity() + tD.getTempReturnQuantity());
					 jasService.saveStoreDrugIssueDetail(tD);
					 
				 }
				 if(tD.getId() == null){
					 //this add new issue drug
					 if(checkIssue){
						 transactionIssue = jasService.saveStoreDrugTransaction(transactionIssue);
						 checkIssue = false;
					 }
					 //create transaction detail for issue on current bill
					 
					    Integer totalQuantity = jasService.sumCurrentQuantityDrugOfStore(store.getId(),tD.getTransactionDetail().getDrug().getId(), tD.getTransactionDetail().getFormulation().getId());
						int t = totalQuantity - tD.getQuantity();
						JASStoreDrugTransactionDetail drugTransactionDetail = jasService.getStoreDrugTransactionDetailById(tD.getTransactionDetail().getId());
						tD.getTransactionDetail().setCurrentQuantity(drugTransactionDetail.getCurrentQuantity() - tD.getQuantity());
						
						jasService.saveStoreDrugTransactionDetail(tD.getTransactionDetail());
						
						//save transactiondetail first
						JASStoreDrugTransactionDetail transDetailIssue = new JASStoreDrugTransactionDetail();
						transDetailIssue.setTransaction(transactionIssue);
						transDetailIssue.setCurrentQuantity(0);
						transDetailIssue.setIssueQuantity(tD.getQuantity());
						transDetailIssue.setOpeningBalance(totalQuantity);
						transDetailIssue.setClosingBalance(t);
						transDetailIssue.setQuantity(0);
						transDetailIssue.setVAT(tD.getTransactionDetail().getVAT());
						transDetailIssue.setUnitPrice(tD.getTransactionDetail().getUnitPrice());
						transDetailIssue.setDrug(tD.getTransactionDetail().getDrug());
						transDetailIssue.setFormulation(tD.getTransactionDetail().getFormulation());
						transDetailIssue.setBatchNo(tD.getTransactionDetail().getBatchNo());
						transDetailIssue.setCompanyName(tD.getTransactionDetail().getCompanyName());
						transDetailIssue.setDateManufacture(tD.getTransactionDetail().getDateManufacture());
						transDetailIssue.setDateExpiry(tD.getTransactionDetail().getDateExpiry());
						transDetailIssue.setReceiptDate(tD.getTransactionDetail().getReceiptDate());
						transDetailIssue.setCreatedOn(date);
						transDetailIssue.setOtherTaxes(tD.getTransactionDetail().getOtherTaxes());
						
						BigDecimal tmp =  tD.getTransactionDetail().getRate().multiply(new BigDecimal(tD.getQuantity())) ;
						transDetailIssue.setTotalPrice(tmp);
						//totalPrice = totalPrice.add(tmp);
						transDetailIssue.setParent(tD.getTransactionDetail());
						transDetailIssue = jasService.saveStoreDrugTransactionDetail(transDetailIssue);
					 //save StoreIssueDrugDetail
					 tD.setCreatedOn(date);
					 jasService.saveStoreDrugIssueDetail(tD);
					 try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
				 totalPrice = totalPrice.add(tD.getTransactionDetail().getRate().multiply(new BigDecimal(tD.getQuantity() - tD.getReturnQuantity())));
			 }
			 //update issueDrugdetail and issueDrug.
			 issueDrugPatient.setUpdateTimes(issueDrugPatient.getUpdateTimes() + 1);
			 totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_UP);
			 issueDrugPatient.setTotal(totalPrice);
			 issueDrugPatient = jasService.saveStoreDrugIssue(issueDrugPatient);
			 
			  
			  
			  model.addAttribute("issueId", issueDrugPatient.getId());
			  System.out.println("Finish edit the bill!");
			  StoreSingleton.getInstance().setEdit(false);
			  StoreSingleton.getInstance().getHash().remove(bill);
			  StoreSingleton.getInstance().getHash().remove(billDetail);
			  return "/module/jas/transaction/processIssueDrug";
		  }
		  return null;
	}
	
	@RequestMapping(value="/module/jas/addReturnDrug.form",method = RequestMethod.GET)
	public String addReturnDrug(
			HttpServletRequest request, Model model) {
		  int userId = Context.getAuthenticatedUser().getId();
		  Integer name = NumberUtils.toInt(request.getParameter("name"), 0);
		  Integer value = NumberUtils.toInt(request.getParameter("value"), 0);
		  String billDetail = "editBillDetail_"+userId;
		  List<JASStoreDrugIssueDetail> listIssueDetail = null;
		  List<JASStoreDrugIssueDetail> listIssueDetailExt = new ArrayList<JASStoreDrugIssueDetail>();
		  listIssueDetail =  (List<JASStoreDrugIssueDetail>)StoreSingleton.getInstance().getHash().get(billDetail);
		  for( JASStoreDrugIssueDetail t : listIssueDetail){
			  if(name!= 0 && name.equals(t.getId()) && value != t.getTempReturnQuantity()){
				  t.setTempReturnQuantity(value);
			  }
			  listIssueDetailExt.add(t);
		  }
		  StoreSingleton.getInstance().setEdit(true);
		  StoreSingleton.getInstance().getHash().put(billDetail, listIssueDetailExt);
		 return "redirect:/module/jas/editBill.form";
	}
	@RequestMapping(value="/module/jas/printEditBill.form",method = RequestMethod.GET)
	public String printEditBill(
			HttpServletRequest request, Model model) {
		  int userId = Context.getAuthenticatedUser().getId();
		  Integer name = NumberUtils.toInt(request.getParameter("name"), 0);
		  Integer value = NumberUtils.toInt(request.getParameter("value"), 0);
		  String billDetail = "editBillDetail_"+userId;
		  List<JASStoreDrugIssueDetail> listIssueDetail = null;
		  List<JASStoreDrugIssueDetail> listIssueDetailExt = new ArrayList<JASStoreDrugIssueDetail>();
		  listIssueDetail =  (List<JASStoreDrugIssueDetail>)StoreSingleton.getInstance().getHash().get(billDetail);
		  for( JASStoreDrugIssueDetail t : listIssueDetail){
			  if(name!= 0 && name.equals(t.getId()) && value != t.getTempReturnQuantity()){
				  t.setTempReturnQuantity(value);
			  }
			  listIssueDetailExt.add(t);
		  }
		  StoreSingleton.getInstance().setEdit(true);
		  StoreSingleton.getInstance().getHash().put(billDetail, listIssueDetailExt);
		 return "/module/jas/printEditBill";
	}
	
}
