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
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;
import org.openmrs.module.jas.util.DateUtils;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASitemReceiptFormController")
@RequestMapping("/module/jas/itemReceiptsToGeneralStore.form")
public class ReceiptFormController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> listCategory = jasService.listItemSubCategory("", 0, 0);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("categoryId", categoryId);
		if (categoryId != null && categoryId > 0) {
			List<JASItem> items = jasService.findItem(categoryId, null);
			model.addAttribute("items", items);
			
		}
		model.addAttribute("date", new Date());
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		model.addAttribute("store", store);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemReceipt_" + userId;
		List<JASStoreItemTransactionDetail> list = (List<JASStoreItemTransactionDetail>) StoreSingleton.getInstance()
		        .getHash().get(fowardParam);
		model.addAttribute("listReceipt", list);
		
		return "/module/jas/mainstoreItem/itemReceiptsToGeneralStore";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> listCategory = jasService.listItemSubCategory("", 0, 0);
		model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"), 0);
		int specification = NumberUtils.toInt(request.getParameter("specification"), 0);
		int itemId = NumberUtils.toInt(request.getParameter("itemId"), 0);
		int quantity = NumberUtils.toInt(request.getParameter("quantity"), 0);
		BigDecimal VAT = NumberUtils.createBigDecimal(request.getParameter("VAT"));
		BigDecimal unitPrice = NumberUtils.createBigDecimal(request.getParameter("unitPrice"));
		String batchNo = request.getParameter("batchNo");
		String companyName = request.getParameter("companyName");
		String dateManufacture = request.getParameter("dateManufacture");
		String receiptDate = request.getParameter("receiptDate");
		//System.out.println("itemName: "+itemName);
		JASItem item = jasService.getItemById(itemId);
		
		if (item == null) {
			errors.add("jas.receiptItem.Item.required");
			model.addAttribute("category", category);
			model.addAttribute("specification", specification);
			model.addAttribute("ItemId", itemId);
			model.addAttribute("quantity", quantity);
			model.addAttribute("VAT", VAT);
			model.addAttribute("batchNo", batchNo);
			model.addAttribute("unitPrice", unitPrice);
			model.addAttribute("companyName", companyName);
			model.addAttribute("dateManufacture", dateManufacture);
			model.addAttribute("companyName", companyName);
			model.addAttribute("receiptDate", receiptDate);
			return "/module/jas/mainstoreItem/itemReceiptsToGeneralStore";
		} else if (CollectionUtils.isNotEmpty(item.getSpecifications()) && specification == 0) {
			errors.add("jas.receiptItem.specification.required");
			return "/module/jas/mainstoreItem/itemReceiptsToGeneralStore";
		}
		
		JASStoreItemTransactionDetail transactionDetail = new JASStoreItemTransactionDetail();
		transactionDetail.setItem(item);
		transactionDetail.setSpecification(jasService.getItemSpecificationById(specification));
		transactionDetail.setCompanyName(companyName);
		transactionDetail.setCurrentQuantity(quantity);
		transactionDetail.setQuantity(quantity);
		transactionDetail.setUnitPrice(unitPrice);
		transactionDetail.setVAT(VAT);
		transactionDetail.setIssueQuantity(0);
		transactionDetail.setCreatedOn(new Date());
		transactionDetail.setReceiptDate(DateUtils.getDateFromStr(receiptDate));
		transactionDetail.setDateManufacture(DateUtils.getDateFromStr(dateManufacture));
		/*Money moneyUnitPrice = new Money(unitPrice);
		Money vATUnitPrice = new Money(VAT);
		Money m = moneyUnitPrice.plus(vATUnitPrice);
		Money totl = m.times(quantity);
		transactionDetail.setTotalPrice(totl.getAmount());*/
		
		/*Money moneyUnitPrice = new Money(unitPrice);
		Money totl = moneyUnitPrice.times(quantity);
		totl = totl.plus(totl.times(VAT.divide(new BigDecimal(100) , 2)));*/
		
		BigDecimal moneyUnitPrice = unitPrice.multiply(new BigDecimal(quantity));
		moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(VAT.divide(new BigDecimal(100))));
		transactionDetail.setTotalPrice(moneyUnitPrice);
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemReceipt_" + userId;
		List<JASStoreItemTransactionDetail> list = (List<JASStoreItemTransactionDetail>) StoreSingleton.getInstance()
		        .getHash().get(fowardParam);
		if (list == null) {
			list = new ArrayList<JASStoreItemTransactionDetail>();
		}
		list.add(transactionDetail);
		//System.out.println("list receipt: "+list);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		//model.addAttribute("listReceipt", list);
		return "redirect:/module/jas/itemReceiptsToGeneralStore.form";
	}
}
