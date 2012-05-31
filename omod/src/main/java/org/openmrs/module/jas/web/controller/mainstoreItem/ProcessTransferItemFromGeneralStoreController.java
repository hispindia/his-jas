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

package org.openmrs.module.jas.web.controller.mainstoreItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItem;
import org.openmrs.module.jas.model.JASStoreItemIndent;
import org.openmrs.module.jas.model.JASStoreItemIndentDetail;
import org.openmrs.module.jas.model.JASStoreItemTransaction;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASProcessTransferItemFromGeneralStoreController")
@RequestMapping("/module/jas/mainStoreItemProcessIndent.form")
public class ProcessTransferItemFromGeneralStoreController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String sendIndent(@RequestParam(value = "indentId", required = false) Integer id, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASStoreItemIndent indent = jasService.getStoreItemIndentById(id);
		JASStore mainStore = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		if (indent != null && indent.getSubStoreStatus() == 2 && indent.getMainStoreStatus() == 1) {
			List<JASStoreItemIndentDetail> listItemNeedProcess = jasService.listStoreItemIndentDetail(id);
			Collection<Integer> specificationIds = new ArrayList<Integer>();
			Collection<Integer> itemIds = new ArrayList<Integer>();
			for (JASStoreItemIndentDetail t : listItemNeedProcess) {
				if (t.getSpecification() != null) {
					specificationIds.add(t.getSpecification().getId());
				}
				itemIds.add(t.getItem().getId());
			}
			//need change it in future
			List<JASStoreItemTransactionDetail> transactionAvaiableOfMainStore = jasService.listStoreItemAvaiable(
			    mainStore.getId(), itemIds, null);
			//System.out.println("transactionAvaiableOfMainStore: "+transactionAvaiableOfMainStore);
			List<JASStoreItemIndentDetail> listItemTP = new ArrayList<JASStoreItemIndentDetail>();
			for (JASStoreItemIndentDetail t : listItemNeedProcess) {
				if (transactionAvaiableOfMainStore != null && transactionAvaiableOfMainStore.size() > 0) {
					for (JASStoreItemTransactionDetail trDetail : transactionAvaiableOfMainStore) {
						if (t.getSpecification() != null) {
							if (trDetail.getSpecification() != null) {
								if (t.getItem().getId() == trDetail.getItem().getId()
								        && t.getSpecification().getId() == trDetail.getSpecification().getId()) {
									t.setMainStoreTransfer(trDetail.getCurrentQuantity());
								}
							} else {
								continue;
							}
						} else {
							if (t.getItem().getId() == trDetail.getItem().getId() && trDetail.getSpecification() == null) {
								t.setMainStoreTransfer(trDetail.getCurrentQuantity());
							}
						}
					}
				} else {
					t.setMainStoreTransfer(0);
				}
				listItemTP.add(t);
			}
			//System.out.println("listItemTP: "+listItemTP);
			//need change in future this
			//System.out.println("truong hop bo het: "+jasService.listStoreDrugAvaiable(mainStore.getId(), null, null));
			model.addAttribute("listItemNeedProcess", listItemTP);
			model.addAttribute("indent", indent);
			return "/module/jas/mainstoreItem/mainStoreItemProcessIndent";
		}
		
		return "redirect:/module/jas/transferItemFromGeneralStore.form";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		JASService jasService = (JASService) Context.getService(JASService.class);
		Integer indentId = NumberUtils.toInt(request.getParameter("indentId"));
		JASStoreItemIndent indent = jasService.getStoreItemIndentById(indentId);
		List<JASStoreItemIndentDetail> listIndentDetail = jasService.listStoreItemIndentDetail(indentId);
		if ("1".equals(request.getParameter("refuse"))) {
			if (indent != null) {
				indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[1]);
				indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[5]);
				jasService.saveStoreItemIndent(indent);
				
				for (JASStoreItemIndentDetail t : listIndentDetail) {
					Integer specificationId = t.getSpecification() != null ? t.getSpecification().getId() : null;
					JASStoreItem storeItem = jasService.getStoreItem(indent.getStore().getId(), t.getItem().getId(),
					    specificationId);
					if (storeItem != null) {
						storeItem.setStatusIndent(0);
						jasService.saveStoreItem(storeItem);
					}
					
				}
			}
			return "redirect:/module/jas/transferItemFromGeneralStore.form";
		}
		//validate here 
		
		Collection<Integer> specificationIds = new ArrayList<Integer>();
		Collection<Integer> itemIds = new ArrayList<Integer>();
		for (JASStoreItemIndentDetail t : listIndentDetail) {
			if (t.getSpecification() != null) {
				specificationIds.add(t.getSpecification().getId());
			}
			itemIds.add(t.getItem().getId());
		}
		JASStore mainStore = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		List<JASStoreItemTransactionDetail> transactionAvaiableOfMainStore = jasService.listStoreItemAvaiable(
		    mainStore.getId(), itemIds, null);
		
		boolean passTransfer = true;
		List<Integer> quantityTransfers = new ArrayList<Integer>();
		//get available quantity mainstore have on hand
		List<JASStoreItemIndentDetail> listItemTP = new ArrayList<JASStoreItemIndentDetail>();
		for (JASStoreItemIndentDetail t : listIndentDetail) {
			int temp = NumberUtils.toInt(request.getParameter(t.getId() + ""));
			//get to return view value
			quantityTransfers.add(temp);
			if (transactionAvaiableOfMainStore != null && transactionAvaiableOfMainStore.size() > 0) {
				for (JASStoreItemTransactionDetail trDetail : transactionAvaiableOfMainStore) {
					
					if (t.getSpecification() != null) {
						if (trDetail.getSpecification() != null) {
							if (t.getItem().getId() == trDetail.getItem().getId()
							        && t.getSpecification().getId() == trDetail.getSpecification().getId()) {
								t.setMainStoreTransfer(trDetail.getCurrentQuantity());
								if (temp > trDetail.getCurrentQuantity() || temp < 0) {
									//System.out.println("nhay avo day 1");
									errors.add("jas.indent.error.quantity");
									break;
								}
							}
						} else {
							continue;
						}
					} else {
						if (t.getItem().getId() == trDetail.getItem().getId() && trDetail.getSpecification() == null) {
							t.setMainStoreTransfer(trDetail.getCurrentQuantity());
							if (temp > trDetail.getCurrentQuantity() || temp < 0) {
								//System.out.println("temp: "+temp+" itemName: "+t.getItem().getName()+" current quantity: "+trDetail.getCurrentQuantity());
								//System.out.println("nhay avo day 2");
								errors.add("jas.indent.error.quantity");
								break;
							}
						}
					}
					
				}
			} else {
				//System.out.println("nhay avo day 3");
				errors.add("jas.indent.error.quantity");
				break;
			}
			if (temp > 0) {
				passTransfer = false;
			}
			listItemTP.add(t);
		}
		if (passTransfer) {
			// System.out.println("nhay avo day 4");
			errors.add("jas.indent.error.transfer");
		}
		if (errors != null && errors.size() > 0) {
			model.addAttribute("listItemNeedProcess", listItemTP);
			model.addAttribute("indent", indent);
			model.addAttribute("errors", errors);
			model.addAttribute("quantityTransfers", quantityTransfers);
			return "/module/jas/mainstoreItem/mainStoreItemProcessIndent";
		}
		
		//create transaction
		JASStoreItemTransaction transaction = new JASStoreItemTransaction();
		transaction.setDescription("TRANSFER ITEM SYSTEM AUTO " + DateUtils.getDDMMYYYY());
		transaction.setStore(mainStore);
		transaction.setTypeTransaction(ActionValue.TRANSACTION[1]);
		transaction.setCreatedOn(new Date());
		transaction.setCreatedBy("System");
		transaction = jasService.saveStoreItemTransaction(transaction);
		
		for (JASStoreItemIndentDetail t : listIndentDetail) {
			int temp = NumberUtils.toInt(request.getParameter(t.getId() + ""), 0);
			//System.out.println("temp : "+temp);
			t.setMainStoreTransfer(temp);
			if (temp > 0) {
				//sum currentQuantity of drugId, formulationId of store
				Integer specificationId = t.getSpecification() != null ? t.getSpecification().getId() : null;
				Integer totalQuantity = jasService.sumStoreItemCurrentQuantity(mainStore.getId(), t.getItem().getId(),
				    specificationId);
				//list all transaction detail with condition dateExpiry > now() , drugId = ? , formulationId = ? of mainstore
				List<JASStoreItemTransactionDetail> listTransactionAvailableMS = jasService.listStoreItemTransactionDetail(
				    mainStore.getId(), t.getItem().getId(), specificationId, true);
				JASStoreItemTransactionDetail transDetail = new JASStoreItemTransactionDetail();
				transDetail.setTransaction(transaction);
				JASStoreItem storeItem = jasService.getStoreItem(mainStore.getId(), t.getItem().getId(), specificationId);
				for (JASStoreItemTransactionDetail trDetail : listTransactionAvailableMS) {
					Integer x = trDetail.getCurrentQuantity() - temp;
					if (x >= 0) {
						Date date = new Date();
						//update current quantity of mainstore in transactionDetail
						trDetail.setCurrentQuantity(x);
						jasService.saveStoreItemTransactionDetail(trDetail);
						
						transDetail.setItem(trDetail.getItem());
						transDetail.setCompanyName(trDetail.getCompanyName());
						transDetail.setCreatedOn(date);
						transDetail.setDateManufacture(trDetail.getDateManufacture());
						transDetail.setSpecification(trDetail.getSpecification());
						transDetail.setUnitPrice(trDetail.getUnitPrice());
						transDetail.setVAT(trDetail.getVAT());
						transDetail.setParent(trDetail);
						transDetail.setReceiptDate(date);
						
						/* Money moneyUnitPrice = new Money(trDetail.getUnitPrice());
						 Money vATUnitPrice = new Money(trDetail.getVAT());
						 Money m = moneyUnitPrice.plus(vATUnitPrice);
						 Money totl = m.times(temp);
						 transDetail.setTotalPrice(totl.getAmount());*/
						
						/* Money moneyUnitPrice = new Money(trDetail.getUnitPrice());
						 Money totl = moneyUnitPrice.times(temp);
						
						totl = totl.plus(totl.times(trDetail.getVAT().divide(new BigDecimal(100))));
						transDetail.setTotalPrice(totl.getAmount());*/
						
						BigDecimal moneyUnitPrice = trDetail.getUnitPrice().multiply(new BigDecimal(temp));
						moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(trDetail.getVAT().divide(
						    new BigDecimal(100))));
						transDetail.setTotalPrice(moneyUnitPrice);
						
						transDetail.setQuantity(0);
						transDetail.setCurrentQuantity(0);
						transDetail.setIssueQuantity(temp);
						transDetail.setOpeningBalance(totalQuantity);
						transDetail.setClosingBalance(totalQuantity - temp);
						jasService.saveStoreItemTransactionDetail(transDetail);
						
						//save last to StoreDrug
						storeItem.setOpeningBalance(totalQuantity);
						storeItem.setClosingBalance(totalQuantity - temp);
						storeItem.setIssueQuantity(storeItem.getIssueQuantity() + temp);
						storeItem.setCurrentQuantity(totalQuantity - temp);
						jasService.saveStoreItem(storeItem);
						
						//create transactionDetail for transfer
						try {
							Thread.sleep(2000);
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					} else {
						Date date = new Date();
						//truong hop mot transactionDetail be hon cai can transfer.
						transDetail.setIssueQuantity(trDetail.getCurrentQuantity());
						trDetail.setCurrentQuantity(0);
						jasService.saveStoreItemTransactionDetail(trDetail);
						
						/*Money moneyUnitPrice = new Money(trDetail.getUnitPrice());
						Money vATUnitPrice = new Money(trDetail.getVAT());
						Money m = moneyUnitPrice.plus(vATUnitPrice);
						Money totl = m.times(transDetail.getIssueQuantity());
						transDetail.setTotalPrice(totl.getAmount());*/
						/*
						 Money moneyUnitPrice = new Money(trDetail.getUnitPrice());
						 Money totl = moneyUnitPrice.times(temp);
						
						totl = totl.plus(totl.times(trDetail.getVAT().divide(new BigDecimal(100))));
						transDetail.setTotalPrice(totl.getAmount());*/
						
						BigDecimal moneyUnitPrice = trDetail.getUnitPrice().multiply(
						    new BigDecimal(transDetail.getIssueQuantity()));
						moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(trDetail.getVAT().divide(
						    new BigDecimal(100))));
						transDetail.setTotalPrice(moneyUnitPrice);
						
						transDetail.setCurrentQuantity(0);
						transDetail.setOpeningBalance(totalQuantity);
						
						transDetail.setItem(trDetail.getItem());
						transDetail.setCompanyName(trDetail.getCompanyName());
						transDetail.setCreatedOn(date);
						transDetail.setDateManufacture(trDetail.getDateManufacture());
						transDetail.setSpecification(trDetail.getSpecification());
						transDetail.setUnitPrice(trDetail.getUnitPrice());
						transDetail.setVAT(trDetail.getVAT());
						transDetail.setParent(trDetail);
						transDetail.setReceiptDate(date);
						
						transDetail.setClosingBalance(totalQuantity - transDetail.getIssueQuantity());
						jasService.saveStoreItemTransactionDetail(transDetail);
						totalQuantity -= transDetail.getIssueQuantity();
						temp -= transDetail.getIssueQuantity();
						try {
							Thread.sleep(2000);
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//create transactionDetail for transfer
					}
					
				}
				
			}
			jasService.saveStoreItemIndentDetail(t);
		}
		//System.out.println("den day roi");
		//add issue transaction from general store 
		indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[2]);
		indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[2]);
		indent.setTransaction(transaction);
		jasService.saveStoreItemIndent(indent);
		return "redirect:/module/jas/transferItemFromGeneralStore.form";
		
	}
}
