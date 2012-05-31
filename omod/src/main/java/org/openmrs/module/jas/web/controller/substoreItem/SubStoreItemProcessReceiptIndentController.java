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

package org.openmrs.module.jas.web.controller.substoreItem;

import java.math.BigDecimal;
import java.util.ArrayList;
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

@Controller("JASSubStoreItemProcessReceiptIndentController")
@RequestMapping("/module/jas/subStoreItemProcessIndent.form")
public class SubStoreItemProcessReceiptIndentController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String sendIndent(@RequestParam(value = "indentId", required = false) Integer id, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASStoreItemIndent indent = jasService.getStoreItemIndentById(id);
		if (indent != null && indent.getSubStoreStatus() != 3 && indent.getMainStoreStatus() != 3) {
			return "redirect:/module/jas/subStoreIndentItemList.form";
		}
		List<JASStoreItemIndentDetail> listItemNeedProcess = jasService.listStoreItemIndentDetail(id);
		//Collection<Integer> formulationIds = new ArrayList<Integer>();
		//Collection<Integer> ItemIds = new ArrayList<Integer>();
		model.addAttribute("listItemNeedProcess", listItemNeedProcess);
		model.addAttribute("indent", indent);
		return "/module/jas/substoreItem/subStoreItemProcessIndent";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpServletRequest request, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		Integer indentId = NumberUtils.toInt(request.getParameter("indentId"));
		JASStoreItemIndent indent = jasService.getStoreItemIndentById(indentId);
		List<JASStoreItemIndentDetail> listIndentDetail = jasService.listStoreItemIndentDetail(indentId);
		
		JASStore subStore = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		List<JASStoreItemTransactionDetail> refundItemList = jasService.listStoreItemTransactionDetail(indent
		        .getTransaction().getId());
		
		if ("1".equals(request.getParameter("refuse"))) {
			if (indent != null) {
				indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[3]);
				indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[3]);
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
			
			if (refundItemList != null && refundItemList.size() > 0) {
				JASStoreItemTransaction transaction = new JASStoreItemTransaction();
				transaction.setStore(subStore.getParent());
				transaction.setDescription("REFUND ITEM BC SUBSTORE REFUSE " + DateUtils.getDDMMYYYY());
				transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
				transaction.setCreatedBy("System");
				transaction.setCreatedOn(new Date());
				transaction = jasService.saveStoreItemTransaction(transaction);
				
				for (JASStoreItemTransactionDetail refund : refundItemList) {
					
					Date date = new Date();
					Integer sumTotalQuantity = jasService
					        .sumStoreItemCurrentQuantity(subStore.getParent().getId(), refund.getItem().getId(),
					            refund.getSpecification() != null ? refund.getSpecification().getId() : null);
					JASStoreItemTransactionDetail transDetail = new JASStoreItemTransactionDetail();
					transDetail.setTransaction(transaction);
					transDetail.setItem(refund.getItem());
					transDetail.setCompanyName(refund.getCompanyName());
					transDetail.setCreatedOn(date);
					transDetail.setDateManufacture(refund.getDateManufacture());
					transDetail.setSpecification(refund.getSpecification());
					transDetail.setUnitPrice(refund.getUnitPrice());
					transDetail.setVAT(refund.getVAT());
					transDetail.setParent(refund);
					transDetail.setReceiptDate(date);
					transDetail.setQuantity(refund.getIssueQuantity());
					/* Money moneyUnitPrice = new Money(refund.getUnitPrice());
					 Money vATUnitPrice = new Money(refund.getVAT());
					 Money m = moneyUnitPrice.plus(vATUnitPrice);
					 Money totl = m.times(refund.getIssueQuantity());
					 transDetail.setTotalPrice(totl.getAmount());*/
					/*
					 Money moneyUnitPrice = new Money(refund.getUnitPrice());
					 Money totl = moneyUnitPrice.times(refund.getIssueQuantity());
					
					totl = totl.plus(totl.times(refund.getVAT().divide(new BigDecimal(100))));
					*/
					BigDecimal moneyUnitPrice = refund.getUnitPrice().multiply(new BigDecimal(refund.getIssueQuantity()));
					moneyUnitPrice = moneyUnitPrice
					        .add(moneyUnitPrice.multiply(refund.getVAT().divide(new BigDecimal(100))));
					transDetail.setTotalPrice(moneyUnitPrice);
					
					transDetail.setCurrentQuantity(refund.getIssueQuantity());
					transDetail.setIssueQuantity(0);
					transDetail.setOpeningBalance(sumTotalQuantity);
					transDetail.setClosingBalance(sumTotalQuantity + refund.getIssueQuantity());
					jasService.saveStoreItemTransactionDetail(transDetail);
					try {
						Thread.sleep(2000);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			return "redirect:/module/jas/subStoreIndentItemList.form";
		}
		//save here 
		
		JASStoreItemTransaction transaction = new JASStoreItemTransaction();
		transaction.setStore(subStore);
		transaction.setDescription("RECEIPT " + DateUtils.getDDMMYYYY());
		transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
		transaction.setCreatedBy("System");
		transaction.setCreatedOn(new Date());
		transaction = jasService.saveStoreItemTransaction(transaction);
		
		for (JASStoreItemTransactionDetail refund : refundItemList) {
			
			Date date = new Date();
			Integer specificationId = refund.getSpecification() != null ? refund.getSpecification().getId() : null;
			Integer sumTotalQuantity = jasService.sumStoreItemCurrentQuantity(subStore.getId(), refund.getItem().getId(),
			    specificationId);
			JASStoreItemTransactionDetail transDetail = new JASStoreItemTransactionDetail();
			transDetail.setTransaction(transaction);
			transDetail.setItem(refund.getItem());
			transDetail.setCompanyName(refund.getCompanyName());
			transDetail.setCreatedOn(date);
			transDetail.setDateManufacture(refund.getDateManufacture());
			transDetail.setSpecification(refund.getSpecification());
			transDetail.setUnitPrice(refund.getUnitPrice());
			transDetail.setVAT(refund.getVAT());
			transDetail.setParent(refund);
			transDetail.setQuantity(refund.getIssueQuantity());
			transDetail.setReceiptDate(date);
			
			/*Money moneyUnitPrice = new Money(refund.getUnitPrice());
			Money vATUnitPrice = new Money(refund.getVAT());
			Money m = moneyUnitPrice.plus(vATUnitPrice);
			Money totl = m.times(refund.getIssueQuantity());
			transDetail.setTotalPrice(totl.getAmount());*/
			
			/*Money moneyUnitPrice = new Money(refund.getUnitPrice());
			Money totl = moneyUnitPrice.times(refund.getIssueQuantity());
			
			totl = totl.plus(totl.times(refund.getVAT().divide(new BigDecimal(100),2)));
			transDetail.setTotalPrice(totl.getAmount());*/
			
			BigDecimal moneyUnitPrice = refund.getUnitPrice().multiply(new BigDecimal(refund.getIssueQuantity()));
			moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(refund.getVAT().divide(new BigDecimal(100))));
			transDetail.setTotalPrice(moneyUnitPrice);
			
			transDetail.setQuantity(refund.getIssueQuantity());
			transDetail.setCurrentQuantity(refund.getIssueQuantity());
			transDetail.setIssueQuantity(0);
			transDetail.setOpeningBalance(sumTotalQuantity);
			transDetail.setClosingBalance(sumTotalQuantity + refund.getIssueQuantity());
			jasService.saveStoreItemTransactionDetail(transDetail);
			try {
				Thread.sleep(2000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//indent.setMainStoreStatus(ActionValue.INDENT_MAINSTORE[2]);
		indent.setSubStoreStatus(ActionValue.INDENT_SUBSTORE[4]);
		jasService.saveStoreItemIndent(indent);
		return "redirect:/module/jas/subStoreIndentItemList.form";
		
	}
}
