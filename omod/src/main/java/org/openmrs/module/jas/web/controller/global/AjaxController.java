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
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemSpecification;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItemAccount;
import org.openmrs.module.jas.model.JASStoreItemAccountDetail;
import org.openmrs.module.jas.model.JASStoreItemIndent;
import org.openmrs.module.jas.model.JASStoreItemIndentDetail;
import org.openmrs.module.jas.model.JASStoreItemTransaction;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
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
	public String drugByCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrug> drugs = jASService.listDrug(categoryId, null, 0, 0);
		model.addAttribute("drugs", drugs);
		return "/module/jas/autocomplete/drugByCategory";
	}
	
	@RequestMapping("/module/jas/drugByCategoryForIssue.form")
	public String drugByCategoryForIssue(@RequestParam(value = "categoryId", required = false) Integer categoryId,
	                                     Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrug> drugs = jASService.listDrug(categoryId, null, 0, 0);
		model.addAttribute("drugs", drugs);
		return "/module/jas/autocomplete/drugByCategoryForIssue";
	}
	
	@RequestMapping("/module/jas/formulationByDrug.form")
	public String formulationByDrug(@RequestParam(value = "drugName", required = false) String drugName, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASDrug drug = jASService.getDrugByName(drugName);
		if (drug != null) {
			List<JASDrugFormulation> formulations = new ArrayList<JASDrugFormulation>(drug.getFormulations());
			model.addAttribute("formulations", formulations);
		}
		return "/module/jas/autocomplete/formulationByDrug";
	}
	
	@RequestMapping("/module/jas/clearSlip.form")
	public String clearSlip(@RequestParam(value = "action", required = false) String name, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "reipt_" + userId;
		if ("1".equals(name)) {
			//Clear slip 
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/jas/receiptsToGeneralStore.form";
		}
		return "/module/jas/transaction/addDescriptionReceiptSlip";
	}
	
	@RequestMapping("/module/jas/listReceiptDrug.form")
	public String listReceiptDrugAvailable(@RequestParam(value = "drugName", required = false) String drugName,
	                                       @RequestParam(value = "formulationId", required = false) Integer formulationId,
	                                       Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASDrug drug = jASService.getDrugByName(drugName);
		JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		if (store != null && drug != null && formulationId != null) {
			List<JASStoreDrugTransactionDetail> listReceiptDrug = jASService.listStoreDrugTransactionDetail(store.getId(),
			    drug.getId(), formulationId, true);
			//check that drug is issued before
			int userId = Context.getAuthenticatedUser().getId();
			
			String fowardParamDrug = "issueDrugDetail_" + userId;
			List<JASStoreDrugIssueDetail> listDrug = (List<JASStoreDrugIssueDetail>) StoreSingleton.getInstance().getHash()
			        .get(fowardParamDrug);
			List<JASStoreDrugTransactionDetail> listReceiptDrugReturn = new ArrayList<JASStoreDrugTransactionDetail>();
			if (CollectionUtils.isNotEmpty(listDrug)) {
				if (CollectionUtils.isNotEmpty(listReceiptDrug)) {
					for (JASStoreDrugTransactionDetail drugDetail : listReceiptDrug) {
						for (JASStoreDrugIssueDetail drugPatient : listDrug) {
							if (drugDetail.getId().equals(drugPatient.getTransactionDetail().getId())) {
								drugDetail.setCurrentQuantity(drugDetail.getCurrentQuantity() - drugPatient.getQuantity());
							}
							
						}
						if (drugDetail.getCurrentQuantity() > 0) {
							listReceiptDrugReturn.add(drugDetail);
						}
					}
				}
			}
			
			if (CollectionUtils.isEmpty(listReceiptDrugReturn) && CollectionUtils.isNotEmpty(listReceiptDrug)) {
				listReceiptDrugReturn.addAll(listReceiptDrug);
			}
			
			model.addAttribute("listReceiptDrug", listReceiptDrugReturn);
		}
		
		return "/module/jas/autocomplete/listReceiptDrug";
	}
	
	@RequestMapping("/module/jas/formulationByDrugForIssue.form")
	public String formulationByDrugForIssueDrug(@RequestParam(value = "drugName", required = false) String drugName,
	                                            Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASDrug drug = jASService.getDrugByName(drugName);
		if (drug != null) {
			List<JASDrugFormulation> formulations = new ArrayList<JASDrugFormulation>(drug.getFormulations());
			model.addAttribute("formulations", formulations);
		}
		return "/module/jas/autocomplete/formulationByDrugForIssue";
	}
	
	@RequestMapping("/module/jas/processIssueDrug.form")
	public synchronized String processIssueDrug(@RequestParam(value = "action", required = false) Integer action,
	                                            @RequestParam(value = "patientName", required = false) String patientName,
	                                            HttpServletRequest request, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueDrugDetail_" + userId;
		JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash().remove("issueDrug_" + userId);
			return "redirect:/module/jas/issueDrugForm.form";
		}
		if ("post".equalsIgnoreCase(request.getMethod()) && action == 0) {
			List<JASStoreDrugIssueDetail> list = (List<JASStoreDrugIssueDetail>) StoreSingleton.getInstance().getHash()
			        .get(fowardParam);
			JASStoreDrugIssue issueDrug = (JASStoreDrugIssue) StoreSingleton.getInstance().getHash()
			        .get("issueDrug_" + userId);
			Date tmp = new Date();
			if (issueDrug == null) {
				
				issueDrug = new JASStoreDrugIssue();
				issueDrug.setName(patientName);
				issueDrug.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				issueDrug.setCreatedOn(tmp);
				issueDrug.setStore(store);
				//save
				issueDrug = jASService.saveStoreDrugIssue(issueDrug);
				
				//update bill number =-=
				//jASService.saveStoreDrugIssue(issueDrug);
				StoreSingleton.getInstance().getHash().put("issueDrug_" + userId, issueDrug);
			}
			if (issueDrug != null && list != null && list.size() > 0) {
				
				Date date = new Date();
				//create transaction issue from substore
				JASStoreDrugTransaction transaction = new JASStoreDrugTransaction();
				transaction.setDescription("ISSUE DRUG " + DateUtils.getDDMMYYYY());
				transaction.setStore(store);
				transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
				transaction.setCreatedOn(date);
				transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				transaction = jASService.saveStoreDrugTransaction(transaction);
				
				//issueDrug = jASService.saveStoreDrugIssue(issueDrug);
				BigDecimal total = new BigDecimal(0);
				for (JASStoreDrugIssueDetail pDetail : list) {
					Date date1 = new Date();
					try {
						Thread.sleep(2000);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Integer totalQuantity = jASService.sumCurrentQuantityDrugOfStore(store.getId(), pDetail
					        .getTransactionDetail().getDrug().getId(), pDetail.getTransactionDetail().getFormulation()
					        .getId());
					int t = totalQuantity - pDetail.getQuantity();
					JASStoreDrugTransactionDetail drugTransactionDetail = jASService
					        .getStoreDrugTransactionDetailById(pDetail.getTransactionDetail().getId());
					pDetail.getTransactionDetail().setCurrentQuantity(
					    drugTransactionDetail.getCurrentQuantity() - pDetail.getQuantity());
					
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
				issueDrug.setBillNumber(DateUtils.getYYMMDD(tmp) + issueDrug.getId());
				total = total.setScale(0, BigDecimal.ROUND_HALF_UP);
				issueDrug.setTotal(total);
				issueDrug = jASService.saveStoreDrugIssue(issueDrug);
				//StoreSingleton.getInstance().getHash().remove(fowardParam);
				//StoreSingleton.getInstance().getHash().remove("issueDrug_"+userId);
			}
			model.addAttribute("issueId", issueDrug.getId());
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash().remove("issueDrug_" + userId);
			return "/module/jas/transaction/processIssueDrug";
		}
		return null;
	}
	
	@RequestMapping("/module/jas/viewStockBalanceDetail.form")
	public String viewStockBalanceDetail(@RequestParam(value = "drugId", required = false) Integer drugId,
	                                     @RequestParam(value = "formulationId", required = false) Integer formulationId,
	                                     @RequestParam(value = "expiry", required = false) Integer expiry, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		List<JASStoreDrugTransactionDetail> listViewStockBalance = jASService.listStoreDrugTransactionDetail(store.getId(),
		    drugId, formulationId, expiry);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/jas/transaction/viewStockBalanceDetail";
	}
	
	@RequestMapping("/module/jas/issueDrugDettail.form")
	public String viewDetailIssueDrug(@RequestParam(value = "issueId", required = false) Integer issueId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugIssueDetail> listDrugIssue = jASService.listStoreDrugIssueDetail(issueId);
		model.addAttribute("listDrugIssue", listDrugIssue);
		
		return "/module/jas/transaction/issueDrugDettail";
	}
	
	@RequestMapping("/module/jas/issueDrugPrint.form")
	public String issueDrugPrint(@RequestParam(value = "issueId", required = false) Integer issueId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugIssueDetail> listDrugIssue = jASService.listStoreDrugIssueDetail(issueId);
		model.addAttribute("listDrugIssue", listDrugIssue);
		if (CollectionUtils.isNotEmpty(listDrugIssue)) {
			model.addAttribute("issueDrugIssue", listDrugIssue.get(0).getStoreDrugIssue());
			
		}
		return "/module/jas/transaction/issueDrugPrint";
	}
	
	@RequestMapping("/module/jas/drugReceiptDetail.form")
	public String drugReceiptDetail(@RequestParam(value = "receiptId", required = false) Integer receiptId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugTransactionDetail> transactionDetails = jASService.listTransactionDetail(receiptId);
		if (!CollectionUtils.isEmpty(transactionDetails)) {
			model.addAttribute("store", transactionDetails.get(0).getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0).getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/jas/transaction/receiptsToGeneralStoreDetail";
	}
	
	@RequestMapping("/module/jas/receiptDetailPrint.form")
	public String printReceiptDetail(@RequestParam(value = "receiptId", required = false) Integer receiptId, Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASStoreDrugTransactionDetail> transactionDetails = jASService.listTransactionDetail(receiptId);
		if (!CollectionUtils.isEmpty(transactionDetails)) {
			model.addAttribute("store", transactionDetails.get(0).getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0).getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/jas/transaction/receiptsToGeneralStorePrint";
	}
	
	@RequestMapping(value = "/module/jas/viewStockBalanceExpiry.form", method = RequestMethod.GET)
	public String viewStockBalanceExpiry(@RequestParam(value = "pageSize", required = false) Integer pageSize,
	                                     @RequestParam(value = "currentPage", required = false) Integer currentPage,
	                                     @RequestParam(value = "categoryId", required = false) Integer categoryId,
	                                     @RequestParam(value = "drugName", required = false) String drugName,
	                                     @RequestParam(value = "fromDate", required = false) String fromDate,
	                                     @RequestParam(value = "toDate", required = false) String toDate,
	                                     Map<String, Object> model, HttpServletRequest request) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		
		int total = jASService.countViewStockBalance(store.getId(), categoryId, drugName, fromDate, toDate, true);
		String temp = "";
		if (categoryId != null) {
			temp = "?categoryId=" + categoryId;
		}
		
		if (drugName != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?drugName=" + drugName;
			} else {
				temp += "&drugName=" + drugName;
			}
		}
		if (fromDate != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?fromDate=" + fromDate;
			} else {
				temp += "&fromDate=" + fromDate;
			}
		}
		if (toDate != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?toDate=" + toDate;
			} else {
				temp += "&toDate=" + toDate;
			}
		}
		
		PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage, total);
		List<JASStoreDrugTransactionDetail> stockBalances = jASService.listViewStockBalance(store.getId(), categoryId,
		    drugName, fromDate, toDate, true, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<JASDrugCategory> listCategory = jASService.listDrugCategory("", 0, 0);
		model.put("categoryId", categoryId);
		model.put("drugName", drugName);
		model.put("fromDate", fromDate);
		model.put("toDate", toDate);
		model.put("pagingUtil", pagingUtil);
		model.put("stockBalances", stockBalances);
		model.put("listCategory", listCategory);
		model.put("store", store);
		
		return "/module/jas/transaction/viewStockBalanceExpiry";
		
	}
	
	@RequestMapping("/module/jas/removeObjectFromList.form")
	public String removeObjectFromList(@RequestParam(value = "position") Integer position,
	                                   @RequestParam(value = "check") Integer check, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		
		String fowardParam1 = "issueItemDetail_" + userId;
		String fowardParam2 = "subStoreIndentItem_" + userId;
		String fowardParam5 = "issueDrugDetail_" + userId;
		String fowardParam6 = "itemReceipt_" + userId;
		String fowardParam7 = "reipt_" + userId;
		List list = null;
		switch (check) {
			case 1:
				//process fowardParam1
				list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash().get(fowardParam1);
				if (CollectionUtils.isNotEmpty(list)) {
					JASStoreItemAccountDetail a = (JASStoreItemAccountDetail) list.get(position);
					//System.out.println("a fowardParam1: "+a.getTransactionDetail().getItem().getName());
					list.remove(a);
				}
				StoreSingleton.getInstance().getHash().put(fowardParam1, list);
				return "redirect:/module/jas/subStoreIssueItemForm.form";
			case 2:
				//process fowardParam2
				list = (List<JASStoreItemIndentDetail>) StoreSingleton.getInstance().getHash().get(fowardParam2);
				if (CollectionUtils.isNotEmpty(list)) {
					JASStoreItemIndentDetail a = (JASStoreItemIndentDetail) list.get(position);
					//System.out.println("a fowardParam2: "+a.getItem().getName());
					list.remove(a);
				}
				StoreSingleton.getInstance().getHash().put(fowardParam2, list);
				return "redirect:/module/jas/subStoreIndentItem.form";
				
			case 5:
				//process fowardParam5
				list = (List<JASStoreDrugIssueDetail>) StoreSingleton.getInstance().getHash().get(fowardParam5);
				if (CollectionUtils.isNotEmpty(list)) {
					JASStoreDrugIssueDetail a = (JASStoreDrugIssueDetail) list.get(position);
					//System.out.println("fowardParam5 a drug : "+a.getTransactionDetail().getDrug().getName());
					list.remove(a);
				}
				StoreSingleton.getInstance().getHash().put(fowardParam5, list);
				return "redirect:/module/jas/issueDrugForm.form";
			case 6:
				//process fowardParam6
				list = (List<JASStoreItemTransactionDetail>) StoreSingleton.getInstance().getHash().get(fowardParam6);
				if (CollectionUtils.isNotEmpty(list)) {
					JASStoreItemTransactionDetail a = (JASStoreItemTransactionDetail) list.get(position);
					//System.out.println("fowardParam6 a item : "+a.getItem().getName());
					list.remove(a);
				}
				StoreSingleton.getInstance().getHash().put(fowardParam6, list);
				return "redirect:/module/jas/itemReceiptsToGeneralStore.form";
			case 7:
				//process fowardParam7
				list = (List<JASStoreDrugTransactionDetail>) StoreSingleton.getInstance().getHash().get(fowardParam7);
				if (CollectionUtils.isNotEmpty(list)) {
					JASStoreDrugTransactionDetail a = (JASStoreDrugTransactionDetail) list.get(position);
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
	
	/**
	 * items
	 */
	@RequestMapping("/module/jas/itemBySubCategory.form")
	public String itemByCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItem> items = jasService.listItem(categoryId, null, 0, 0);
		model.addAttribute("items", items);
		return "/module/jas/autocomplete/itemBySubCategory";
	}
	
	@RequestMapping("/module/jas/itemBySubCategoryForIssue.form")
	public String itemBySubCategoryForIssue(@RequestParam(value = "categoryId", required = false) Integer categoryId,
	                                        Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItem> items = jasService.listItem(categoryId, null, 0, 0);
		model.addAttribute("items", items);
		return "/module/jas/autocomplete/itemBySubCategoryForIssue";
	}
	
	@RequestMapping("/module/jas/specificationByItem.form")
	public String specificationByItem(@RequestParam(value = "itemId", required = false) Integer itemId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASItem item = jasService.getItemById(itemId);
		if (item != null) {
			List<JASItemSpecification> specifications = new ArrayList<JASItemSpecification>(item.getSpecifications());
			model.addAttribute("specifications", specifications);
		}
		return "/module/jas/autocomplete/specificationByItem";
	}
	
	@RequestMapping("/module/jas/itemClearSlip.form")
	public String clearSlipItem(@RequestParam(value = "action", required = false) String name, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemReceipt_" + userId;
		if ("1".equals(name)) {
			//Clear slip 
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/jas/itemReceiptsToGeneralStore.form";
		}
		return "/module/jas/mainstoreItem/itemAddDescriptionReceiptSlip";
	}
	
	@RequestMapping("/module/jas/itemClearPurchaseOrder.form")
	public String itemClearPurchase(@RequestParam(value = "action", required = false) String name, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemPurchase_" + userId;
		if ("1".equals(name)) {
			//Clear slip 
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/jas/itemPurchaseOrderForGeneralStore.form";
		}
		return "/module/jas/mainstoreItem/itemPurchaseOrderForGeneralStore";
	}
	
	@RequestMapping("/module/jas/itemClearSubStoreIndent.form")
	public String itemClearIndent(@RequestParam(value = "action", required = false) String name, Model model) {
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "subStoreIndentItem_" + userId;
		if ("1".equals(name)) {
			//Clear slip 
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			return "redirect:/module/jas/subStoreIndentItem.form";
		}
		return "/module/jas/substoreItem/subStoreIndentItem";
	}
	
	@RequestMapping("/module/jas/indentItemDetail.form")
	public String detailSubStoreItemIndent(@RequestParam(value = "indentId", required = false) Integer indentId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASStoreItemIndentDetail> listIndentDetail = jasService.listStoreItemIndentDetail(indentId);
		//JASStoreItemIndent indent = jasService.getStoreItemIndentById(indentId);
		model.addAttribute("listIndentDetail", listIndentDetail);
		/*if(indent != null && indent.getTransaction() != null){
			List<JASStoreItemTransactionDetail> listTransactionDetail = jasService.listStoreItemTransactionDetail(indent.getTransaction().getId());
			model.addAttribute("listTransactionDetail", listTransactionDetail);
		}*/
		model.addAttribute("store", !CollectionUtils.isEmpty(listIndentDetail) ? listIndentDetail.get(0).getIndent()
		        .getStore() : null);
		model.addAttribute("date", !CollectionUtils.isEmpty(listIndentDetail) ? listIndentDetail.get(0).getIndent()
		        .getCreatedOn() : null);
		return "/module/jas/autocomplete/indentItemDetail";
	}
	
	@RequestMapping("/module/jas/sentItemIndentToMainStore.form")
	public String sendItemIndentToMainStore(@RequestParam(value = "indentId", required = false) Integer indentId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASStoreItemIndent indent = jasService.getStoreItemIndentById(indentId);
		if (indent != null) {
			indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[1]);
			indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[0]);
			jasService.saveStoreItemIndent(indent);
		}
		return "redirect:/module/jas/subStoreIndentItemList.form";
	}
	
	@RequestMapping("/module/jas/listReceiptItem.form")
	public String listReceiptItemAvailable(@RequestParam(value = "itemId", required = false) Integer itemId,
	                                       @RequestParam(value = "specificationId", required = false) Integer specificationId,
	                                       Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASItem item = jasService.getItemById(itemId);
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		if (store != null && item != null) {
			Integer sumReceiptItem = jasService.sumStoreItemCurrentQuantity(store.getId(), item.getId(), specificationId);
			
			int userId = Context.getAuthenticatedUser().getId();
			String fowardParam = "issueItemDetail_" + userId;
			List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash()
			        .get(fowardParam);
			if (CollectionUtils.isNotEmpty(list)) {
				for (JASStoreItemAccountDetail itemAccount : list) {
					if (itemAccount.getTransactionDetail().getItem().getId().equals(itemId)) {
						if (specificationId != null) {
							if (itemAccount.getTransactionDetail().getSpecification() != null
							        && itemAccount.getTransactionDetail().getSpecification().getId().equals(specificationId)) {
								sumReceiptItem -= itemAccount.getQuantity();
							}
						} else {
							if (itemAccount.getTransactionDetail().getSpecification() == null) {
								sumReceiptItem -= itemAccount.getQuantity();
							}
						}
					}
				}
			}
			
			model.addAttribute("sumReceiptItem", sumReceiptItem);
		}
		
		return "/module/jas/autocomplete/listReceiptItem";
	}
	
	@RequestMapping("/module/jas/specificationByItemForIssue.form")
	public String specificationByItemForIssueItem(@RequestParam(value = "itemId", required = false) Integer itemId,
	                                              Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASItem item = jasService.getItemById(itemId);
		if (item != null) {
			if (item.getSpecifications() != null && item.getSpecifications().size() > 0) {
				List<JASItemSpecification> specifications = new ArrayList<JASItemSpecification>(item.getSpecifications());
				model.addAttribute("specifications", specifications);
				return "/module/jas/autocomplete/specificationByItemForIssue";
			} else {
				JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
				        .getAllRoles()));
				if (store != null) {
					Integer sumReceiptItem = jasService.sumStoreItemCurrentQuantity(store.getId(), item.getId(), null);
					
					int userId = Context.getAuthenticatedUser().getId();
					String fowardParam = "issueItemDetail_" + userId;
					List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance()
					        .getHash().get(fowardParam);
					if (CollectionUtils.isNotEmpty(list)) {
						for (JASStoreItemAccountDetail itemAccount : list) {
							if (itemAccount.getTransactionDetail().getItem().getId().equals(itemId)) {
								if (itemAccount.getTransactionDetail().getSpecification() == null) {
									sumReceiptItem -= itemAccount.getQuantity();
								}
								
							}
						}
					}
					
					model.addAttribute("sumReceiptItem", sumReceiptItem);
				}
				return "/module/jas/autocomplete/listReceiptItem";
			}
		}
		
		return "/module/jas/autocomplete/specificationByItemForIssue";
	}
	
	@RequestMapping("/module/jas/processIssueItem.form")
	public String processIssueItem(@RequestParam(value = "action", required = false) Integer action, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueItemDetail_" + userId;
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		if (action == 1) {
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash().remove("issueItem_" + userId);
			return "redirect:/module/jas/subStoreIssueItemForm.form";
		}
		List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		JASStoreItemAccount issueItemAccount = (JASStoreItemAccount) StoreSingleton.getInstance().getHash()
		        .get("issueItem_" + userId);
		if (issueItemAccount != null && list != null && list.size() > 0) {
			
			Date date = new Date();
			//create transaction issue from substore
			JASStoreItemTransaction transaction = new JASStoreItemTransaction();
			transaction.setDescription("ISSUE ITEM " + DateUtils.getDDMMYYYY());
			transaction.setStore(store);
			transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
			transaction.setCreatedOn(date);
			transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			transaction = jasService.saveStoreItemTransaction(transaction);
			
			issueItemAccount = jasService.saveStoreItemAccount(issueItemAccount);
			for (JASStoreItemAccountDetail pDetail : list) {
				Date date1 = new Date();
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Integer specificationId = pDetail.getTransactionDetail().getSpecification() != null ? pDetail
				        .getTransactionDetail().getSpecification().getId() : null;
				Integer totalQuantity = jasService.sumStoreItemCurrentQuantity(store.getId(), pDetail.getTransactionDetail()
				        .getItem().getId(), specificationId);
				int t = totalQuantity - pDetail.getQuantity();
				JASStoreItemTransactionDetail itemTransactionDetail = jasService.getStoreItemTransactionDetailById(pDetail
				        .getTransactionDetail().getId());
				pDetail.getTransactionDetail().setCurrentQuantity(
				    itemTransactionDetail.getCurrentQuantity() - pDetail.getQuantity());
				//System.out.println("get current quantity: "+pDetail.getTransactionDetail().getCurrentQuantity());
				//System.out.println("total quantity: "+totalQuantity);
				jasService.saveStoreItemTransactionDetail(pDetail.getTransactionDetail());
				
				//save transactiondetail first
				JASStoreItemTransactionDetail transDetail = new JASStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				transDetail.setCurrentQuantity(0);
				transDetail.setIssueQuantity(pDetail.getQuantity());
				transDetail.setOpeningBalance(totalQuantity);
				transDetail.setClosingBalance(t);
				transDetail.setQuantity(0);
				transDetail.setVAT(pDetail.getTransactionDetail().getVAT());
				transDetail.setUnitPrice(pDetail.getTransactionDetail().getUnitPrice());
				transDetail.setItem(pDetail.getTransactionDetail().getItem());
				transDetail.setSpecification(pDetail.getTransactionDetail().getSpecification());
				transDetail.setCompanyName(pDetail.getTransactionDetail().getCompanyName());
				transDetail.setDateManufacture(pDetail.getTransactionDetail().getDateManufacture());
				transDetail.setReceiptDate(pDetail.getTransactionDetail().getReceiptDate());
				transDetail.setCreatedOn(date1);
				
				//-------------
				//Money moneyUnitPrice = new Money(pDetail.getTransactionDetail().getUnitPrice());
				//Money vATUnitPrice = new Money(pDetail.getTransactionDetail().getVAT());
				//Money m = moneyUnitPrice.plus(vATUnitPrice);
				//Money totl = m.times( pDetail.getQuantity());
				//transDetail.setTotalPrice(totl.getAmount());
				//-----------------
				
				/*Money moneyUnitPrice = new Money(pDetail.getTransactionDetail().getUnitPrice());
				Money totl = moneyUnitPrice.times(pDetail.getQuantity());
				
				totl = totl.plus(totl.times(pDetail.getTransactionDetail().getVAT().divide(new BigDecimal(100),2)));
				transDetail.setTotalPrice(totl.getAmount());*/
				
				BigDecimal moneyUnitPrice = pDetail.getTransactionDetail().getUnitPrice()
				        .multiply(new BigDecimal(pDetail.getQuantity()));
				moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(pDetail.getTransactionDetail().getVAT()
				        .divide(new BigDecimal(100))));
				transDetail.setTotalPrice(moneyUnitPrice);
				
				transDetail.setParent(pDetail.getTransactionDetail());
				transDetail = jasService.saveStoreItemTransactionDetail(transDetail);
				
				pDetail.setItemAccount(issueItemAccount);
				pDetail.setTransactionDetail(transDetail);
				//save issue to patient detail
				jasService.saveStoreItemAccountDetail(pDetail);
				//save issues transaction detail
				
			}
			
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			StoreSingleton.getInstance().getHash().remove("issueItem_" + userId);
		}
		
		return "redirect:/module/jas/subStoreIssueItemList.form";
	}
	
	@RequestMapping("/module/jas/itemViewStockBalanceDetail.form")
	public String itemViewStockBalanceDetail(@RequestParam(value = "itemId", required = false) Integer itemId,
	                                         @RequestParam(value = "specificationId", required = false) Integer specificationId,
	                                         Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		List<JASStoreItemTransactionDetail> listViewStockBalance = jasService.listStoreItemTransactionDetail(store.getId(),
		    itemId, specificationId, 0, 0);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/jas/mainstoreItem/itemViewStockBalanceDetail";
	}
	
	@RequestMapping("/module/jas/itemViewStockBalanceSubStoreDetail.form")
	public String itemViewStockBalanceSubStoreDetail(@RequestParam(value = "itemId", required = false) Integer itemId,
	                                                 @RequestParam(value = "specificationId", required = false) Integer specificationId,
	                                                 Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		List<JASStoreItemTransactionDetail> listViewStockBalance = jasService.listStoreItemTransactionDetail(store.getId(),
		    itemId, specificationId, 0, 0);
		model.addAttribute("listViewStockBalance", listViewStockBalance);
		return "/module/jas/substoreItem/itemViewStockBalanceDetail";
	}
	
	@RequestMapping("/module/jas/subStoreIssueItemDettail.form")
	public String viewDetailIssueItem(@RequestParam(value = "issueId", required = false) Integer issueId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASStoreItemAccountDetail> listItemIssue = jasService.listStoreItemAccountDetail(issueId);
		model.addAttribute("listItemIssue", listItemIssue);
		if (CollectionUtils.isNotEmpty(listItemIssue)) {
			model.addAttribute("issueItemAccount", listItemIssue.get(0).getItemAccount());
			model.addAttribute("date", listItemIssue.get(0).getItemAccount().getCreatedOn());
		}
		return "/module/jas/substoreItem/subStoreIssueItemDettail";
	}
	
	@RequestMapping("/module/jas/subCatByCat.form")
	public String getSubCatByCat(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> subCategories = jasService.listSubCatByCat(categoryId);
		model.addAttribute("subCategories", subCategories);
		return "/module/jas/item/subCatByCat";
	}
	
	@RequestMapping("/module/jas/itemReceiptDetail.form")
	public String itemReceiptDetail(@RequestParam(value = "receiptId", required = false) Integer receiptId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASStoreItemTransactionDetail> transactionDetails = jasService.listStoreItemTransactionDetail(receiptId);
		if (!CollectionUtils.isEmpty(transactionDetails)) {
			model.addAttribute("store", transactionDetails.get(0).getTransaction().getStore());
			model.addAttribute("date", transactionDetails.get(0).getTransaction().getCreatedOn());
		}
		model.addAttribute("transactionDetails", transactionDetails);
		return "/module/jas/mainstoreItem/itemReceiptsToGeneralStoreDetail";
	}
	
	public static void main(String[] args) {
		BigDecimal t = new BigDecimal(6.496);
		System.out.println(t.setScale(2, BigDecimal.ROUND_UP));
	}
}
