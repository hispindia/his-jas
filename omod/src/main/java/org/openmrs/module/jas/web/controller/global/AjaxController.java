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

package org.openmrs.module.jas.web.controller.global;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugIssue;
import org.openmrs.module.jas.model.JASStoreDrugIssueDetail;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.util.DateUtils;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASAjaxGlobalController")
public class AjaxController {
	
	@RequestMapping("/module/jas/drugByCategory.form")
	public String drugByCategory(@RequestParam(value="categoryId",required=false) Integer categoryId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrug> drugs = jASService.listDrug(categoryId, null, 0, 0);
		model.addAttribute("drugs", drugs);
		return "/module/jas/autocomplete/drugByCategory";
	}
	@RequestMapping("/module/jas/drugByCategoryForIssue.form")
	public String drugByCategoryForIssue(@RequestParam(value="categoryId",required=false) Integer categoryId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrug> drugs = jASService.listDrug(categoryId, null, 0, 0);
		model.addAttribute("drugs", drugs);
		return "/module/jas/autocomplete/drugByCategoryForIssue";
	}
	
	
	@RequestMapping("/module/jas/formulationByDrug.form")
	public String formulationByDrug(@RequestParam(value="drugName",required=false) String drugName, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASDrug drug = jASService.getDrugByName(drugName);
		if(drug != null){
			List<JASDrugFormulation> formulations = new ArrayList<JASDrugFormulation>(drug.getFormulations());
			model.addAttribute("formulations", formulations);
		}
		return "/module/jas/autocomplete/formulationByDrug";
	}
	
	
	@RequestMapping("/module/jas/clearSlip.form")
	public String clearSlip(@RequestParam(value="action",required=false) String name, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "reipt_"+userId;
		if("1".equals(name)){
			//Clear slip 
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/jas/receiptsToGeneralStore.form";
		}
		return "/module/jas/transaction/addDescriptionReceiptSlip";
	}

	@RequestMapping("/module/jas/listReceiptDrug.form")
	public String listReceiptDrugAvailable(@RequestParam(value="drugName",required=false) String drugName,@RequestParam(value="formulationId",required=false) Integer formulationId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASDrug drug = jASService.getDrugByName(drugName);
		JASStore store =  jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		if(store != null && drug != null && formulationId != null){
			List<JASStoreDrugTransactionDetail> listReceiptDrug = jASService.listStoreDrugTransactionDetail(store.getId(), drug.getId(), formulationId, true);
			//check that drug is issued before
			int userId = Context.getAuthenticatedUser().getId();
			
			String fowardParamDrug = "issueDrugDetail_"+userId;
			 List<JASStoreDrugIssueDetail> listDrug = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParamDrug);
			 List<JASStoreDrugTransactionDetail> listReceiptDrugReturn = new ArrayList<JASStoreDrugTransactionDetail>();
			if(CollectionUtils.isNotEmpty(listDrug)){ 
				if(CollectionUtils.isNotEmpty(listReceiptDrug)){
					for(JASStoreDrugTransactionDetail drugDetail : listReceiptDrug){
						for(JASStoreDrugIssueDetail drugPatient : listDrug)
						{
							if(drugDetail.getId().equals(drugPatient.getTransactionDetail().getId())){
								drugDetail.setCurrentQuantity(drugDetail.getCurrentQuantity() - drugPatient.getQuantity());
							}
							
						}
						if(drugDetail.getCurrentQuantity() > 0){
							listReceiptDrugReturn.add(drugDetail);
						}
					}
				}
			}
			
			
			if(CollectionUtils.isEmpty(listReceiptDrugReturn) && CollectionUtils.isNotEmpty(listReceiptDrug))
			{
				listReceiptDrugReturn.addAll(listReceiptDrug);
			}
			
			model.addAttribute("listReceiptDrug", listReceiptDrugReturn);
		}
		
		return "/module/jas/autocomplete/listReceiptDrug";
	}
	
	
	@RequestMapping("/module/jas/formulationByDrugForIssue.form")
	public String formulationByDrugForIssueDrug(@RequestParam(value="drugName",required=false) String drugName, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASDrug drug = jASService.getDrugByName(drugName);
		if(drug != null){
			List<JASDrugFormulation> formulations = new ArrayList<JASDrugFormulation>(drug.getFormulations());
			model.addAttribute("formulations", formulations);
		}
		return "/module/jas/autocomplete/formulationByDrugForIssue";
	}
	
	
	@RequestMapping("/module/jas/processIssueDrug.form")
	public synchronized String processIssueDrug( 
			@RequestParam(value="action",required=false)  Integer action,
			@RequestParam(value="patientName",required=false)  String patientName,
			HttpServletRequest request,
			Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueDrugDetail_"+userId;
		JASStore store =  jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		if(action == 1){
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash().remove("issueDrug_"+userId);
			return "redirect:/module/jas/issueDrugForm.form";
		}
		if("post".equalsIgnoreCase(request.getMethod()) && action == 0){
			List<JASStoreDrugIssueDetail> list = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
			JASStoreDrugIssue issueDrug = (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get("issueDrug_"+userId);
			Date tmp = new Date();
			if(issueDrug == null){
				
				issueDrug = new JASStoreDrugIssue();
				issueDrug.setName(patientName);
				issueDrug.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				issueDrug.setCreatedOn(tmp);
				issueDrug.setStore(store);
				//save
				issueDrug = jASService.saveStoreDrugIssue(issueDrug);
				
				//update bill number =-=
				//jASService.saveStoreDrugIssue(issueDrug);
				StoreSingleton.getInstance().getHash().put("issueDrug_"+userId, issueDrug);
			}
			if(issueDrug != null && list != null && list.size() > 0){
				
				Date date = new Date();
				//create transaction issue from substore
				 JASStoreDrugTransaction transaction = new JASStoreDrugTransaction();
				 transaction.setDescription("ISSUE DRUG "+DateUtils.getDDMMYYYY());
				 transaction.setStore(store);
				 transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
				 transaction.setCreatedOn(date);
				 transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				 transaction = jASService.saveStoreDrugTransaction(transaction);
				 
				 
				
				//issueDrug = jASService.saveStoreDrugIssue(issueDrug);
				BigDecimal total = new BigDecimal(0);
				for(JASStoreDrugIssueDetail pDetail : list){
					Date date1 = new Date();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Integer totalQuantity = jASService.sumCurrentQuantityDrugOfStore(store.getId(),pDetail.getTransactionDetail().getDrug().getId(), pDetail.getTransactionDetail().getFormulation().getId());
					int t = totalQuantity - pDetail.getQuantity();
					JASStoreDrugTransactionDetail drugTransactionDetail = jASService.getStoreDrugTransactionDetailById(pDetail.getTransactionDetail().getId());
					pDetail.getTransactionDetail().setCurrentQuantity(drugTransactionDetail.getCurrentQuantity() - pDetail.getQuantity());
					
					jASService.saveStoreDrugTransactionDetail(pDetail.getTransactionDetail());
					
					//save transactiondetail first
					JASStoreDrugTransactionDetail transDetail = new JASStoreDrugTransactionDetail();
					transDetail.setTransaction(transaction);
					transDetail.setCurrentQuantity(0);
					transDetail.setIssueQuantity(pDetail.getQuantity());
					transDetail.setOpeningBalance(totalQuantity);
					transDetail.setClosingBalance(t);
					transDetail.setQuantity(0);
					transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
					transDetail.setUnitPrice(pDetail.getTransactionDetail().getUnitPrice());
					transDetail.setDrug(pDetail.getTransactionDetail().getDrug());
					transDetail.setFormulation(pDetail.getTransactionDetail().getFormulation());
					transDetail.setBatchNo(pDetail.getTransactionDetail().getBatchNo());
					transDetail.setCompanyName(pDetail.getTransactionDetail().getCompanyName());
					transDetail.setDateManufacture(pDetail.getTransactionDetail().getDateManufacture());
					transDetail.setDateExpiry(pDetail.getTransactionDetail().getDateExpiry());
					transDetail.setReceiptDate(pDetail.getTransactionDetail().getReceiptDate());
					transDetail.setCreatedOn(date1);
					transDetail.setOtherTaxes(pDetail.getTransactionDetail().getOtherTaxes());
					
					//BigDecimal moneyUnitPrice = pDetail.getTransactionDetail().getUnitPrice().multiply(new BigDecimal(pDetail.getQuantity()));
					//moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(pDetail.getRate().divide(new BigDecimal(100))));
					transDetail.setTotalPrice(pDetail.getTotalPrice());
					total = total.add(pDetail.getTotalPrice());
					transDetail.setParent(pDetail.getTransactionDetail());
					transDetail = jASService.saveStoreDrugTransactionDetail(transDetail);
					//total = total.setScale(2, BigDecimal.ROUND_UP);
					
					pDetail.setStoreDrugIssue(issueDrug);
					pDetail.setTransactionDetail(transDetail);
					//save issue to patient detail
					jASService.saveStoreDrugIssueDetail(pDetail);
					//save issues transaction detail
					
				}
				issueDrug.setBillNumber(DateUtils.getYYMMDD(tmp)+issueDrug.getId());
				total = total.setScale(0, BigDecimal.ROUND_HALF_UP);
				issueDrug.setTotal(total);
				issueDrug = jASService.saveStoreDrugIssue(issueDrug);
				//StoreSingleton.getInstance().getHash().remove(fowardParam);
				//StoreSingleton.getInstance().getHash().remove("issueDrug_"+userId);
			}
			model.addAttribute("issueId", issueDrug.getId());
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash().remove("issueDrug_"+userId);
			return "/module/jas/transaction/processIssueDrug";
		}
		return null;
	}
	
	
	
	
	
	@RequestMapping("/module/jas/viewStockBalanceDetail.form")
	public String viewStockBalanceDetail( @RequestParam(value="drugId",required=false)  Integer drugId,@RequestParam(value="formulationId",required=false)  Integer formulationId,@RequestParam(value="expiry",required=false)  Integer expiry, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASStore store =  jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		List<JASStoreDrugTransactionDetail> listViewStockBalance = jASService.listStoreDrugTransactionDetail(store.getId(), drugId, formulationId , expiry);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/jas/transaction/viewStockBalanceDetail";
	}
	
	
	
	@RequestMapping("/module/jas/issueDrugDettail.form")
	public String viewDetailIssueDrug( @RequestParam(value="issueId",required=false)  Integer issueId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugIssueDetail> listDrugIssue = jASService.listStoreDrugIssueDetail(issueId);
		model.addAttribute("listDrugIssue", listDrugIssue);
		
		return "/module/jas/transaction/issueDrugDettail";
	}
	@RequestMapping("/module/jas/issueDrugPrint.form")
	public String issueDrugPrint( @RequestParam(value="issueId",required=false)  Integer issueId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugIssueDetail> listDrugIssue = jASService.listStoreDrugIssueDetail(issueId);
		model.addAttribute("listDrugIssue", listDrugIssue);
		if(CollectionUtils.isNotEmpty(listDrugIssue)){
			model.addAttribute("issueDrugIssue", listDrugIssue.get(0).getStoreDrugIssue());
		
		}
		return "/module/jas/transaction/issueDrugPrint";
	}
	
	
	@RequestMapping("/module/jas/drugReceiptDetail.form")
	public String drugReceiptDetail( @RequestParam(value="receiptId",required=false)  Integer receiptId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugTransactionDetail> transactionDetails = jASService.listTransactionDetail(receiptId);
		if(!CollectionUtils.isEmpty(transactionDetails)){
			model.addAttribute("store", transactionDetails.get(0).getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0).getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/jas/transaction/receiptsToGeneralStoreDetail";
	}
	@RequestMapping("/module/jas/receiptDetailPrint.form")
	public String printReceiptDetail( @RequestParam(value="receiptId",required=false)  Integer receiptId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugTransactionDetail> transactionDetails = jASService.listTransactionDetail(receiptId);
		if(!CollectionUtils.isEmpty(transactionDetails)){
			model.addAttribute("store", transactionDetails.get(0).getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0).getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/jas/transaction/receiptsToGeneralStorePrint";
	}
	
	
	
	@RequestMapping(value="/module/jas/viewStockBalanceExpiry.form",method = RequestMethod.GET)
	public String viewStockBalanceExpiry( @RequestParam(value="pageSize",required=false)  Integer pageSize, 
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
            @RequestParam(value="categoryId",required=false)  Integer categoryId,
            @RequestParam(value="drugName",required=false)  String drugName,
            @RequestParam(value="fromDate",required=false)  String fromDate,
            @RequestParam(value="toDate",required=false)  String toDate,
            Map<String, Object> model, HttpServletRequest request
	) {
	 JASService jASService = (JASService) Context.getService(JASService.class);
	 JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
	 
	 int total = jASService.countViewStockBalance(store.getId(), categoryId, drugName,  fromDate, toDate , true);
	 String temp = "";
		if(categoryId != null){	
				temp = "?categoryId="+categoryId;
		}
		
		if(drugName != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?drugName="+drugName;
			}else{
				temp +="&drugName="+drugName;
			}
		}
		if(fromDate != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?fromDate="+fromDate;
			}else{
				temp +="&fromDate="+fromDate;
			}
		}
		if(toDate != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?toDate="+toDate;
			}else{
				temp +="&toDate="+toDate;
			}
		}
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
		List<JASStoreDrugTransactionDetail> stockBalances = jASService.listViewStockBalance(store.getId(), categoryId, drugName,  fromDate, toDate, true, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<JASDrugCategory> listCategory = jASService.listDrugCategory("", 0, 0);
		model.put("categoryId", categoryId );
		model.put("drugName", drugName );
		model.put("fromDate", fromDate );
		model.put("toDate", toDate );
		model.put("pagingUtil", pagingUtil );
		model.put("stockBalances", stockBalances );
		model.put("listCategory", listCategory );
		model.put("store", store );
		
		return "/module/jas/transaction/viewStockBalanceExpiry";
		
	 
	}
	@RequestMapping("/module/jas/removeObjectFromList.form")
	public String removeObjectFromList( @RequestParam(value="position")  Integer position,@RequestParam(value="check")  Integer check, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		

		String fowardParam5 = "issueDrugDetail_"+userId;
		String fowardParam7 = "reipt_"+userId;
		List list = null;
		switch (check){
		
		
		case 5:
			//process fowardParam5
			list = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParam5);
			if(CollectionUtils.isNotEmpty(list)){
				JASStoreDrugIssueDetail a = (JASStoreDrugIssueDetail)list.get(position);
				//System.out.println("fowardParam5 a drug : "+a.getTransactionDetail().getDrug().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam5, list);
			return "redirect:/module/jas/issueDrugForm.form";
		case 7:
			//process fowardParam7
			list = (List<JASStoreDrugTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam7);
			if(CollectionUtils.isNotEmpty(list)){
				JASStoreDrugTransactionDetail a = (JASStoreDrugTransactionDetail)list.get(position);
				//System.out.println("fowardParam7 a drug : "+a.getDrug().getName());
				list.remove(a);
			}
			StoreSingleton.getInstance().getHash().put(fowardParam7, list);
			return "redirect:/module/jas/receiptsToGeneralStore.form";
		default: 
		}
		
		
		return "redirect:/module/jas/main.form";
	}
	@RequestMapping("/module/jas/patientName.form")
	public String patientName() {
		return "/module/jas/transaction/patientName";
	}
	public static void main(String[] args) {
		BigDecimal t = new BigDecimal(6.496);
		System.out.println(t.setScale(2,BigDecimal.ROUND_UP));
	}
}
