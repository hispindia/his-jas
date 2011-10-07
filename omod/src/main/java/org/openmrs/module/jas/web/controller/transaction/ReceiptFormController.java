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

package org.openmrs.module.jas.web.controller.transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.openmrs.module.jas.util.DateUtils;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("JASReceiptFormController")
@RequestMapping("/module/jas/receiptsToGeneralStore.form")
public class ReceiptFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			Model model) {
	 
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "reipt_"+userId;
	 List<JASStoreDrugTransactionDetail> list = (List<JASStoreDrugTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 model.addAttribute("listReceipt", list);
	 
	 return "/module/jas/transaction/receiptsToGeneralStore";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		JASService jASService = (JASService) Context.getService(JASService.class);
		 List<JASDrugCategory> listCategory = jASService.findDrugCategory("");
		 model.addAttribute("listCategory", listCategory);
		int formulation = NumberUtils.toInt(request.getParameter("formulation"),0);
		String drugName = request.getParameter("drugName");
		int quantity = NumberUtils.toInt(request.getParameter("quantity"),0);
		BigDecimal VAT = NumberUtils.createBigDecimal(request.getParameter("VAT"));
		BigDecimal otherTaxes = NumberUtils.createBigDecimal(request.getParameter("otherTaxes"));
		BigDecimal unitPrice =  NumberUtils.createBigDecimal(request.getParameter("unitPrice"));
		String batchNo = request.getParameter("batchNo");
		String companyName = request.getParameter("companyName");
		String dateManufacture = request.getParameter("dateManufacture");
		String dateExpiry = request.getParameter("dateExpiry");
		String receiptDate = request.getParameter("receiptDate");
		if(!StringUtils.isBlank(dateManufacture)){
			Date dateManufac = DateUtils.getDateFromStr(dateManufacture);
			Date dateExpi = DateUtils.getDateFromStr(dateExpiry);
			if(dateManufac.after(dateExpi)  ){
				errors.add("jas.receiptDrug.manufacNeedLessThanExpiry");
			}
		}
		JASDrugFormulation formulationO = jASService.getDrugFormulationById(formulation);
		if(formulationO == null)
		{
			errors.add("jas.receiptDrug.formulation.required");
		}
		JASDrug drug = jASService.getDrugByName(drugName);
		if(drug == null){
			errors.add("jas.receiptDrug.drug.required");
		}
		if(formulationO != null && drug != null && !drug.getFormulations().contains(formulationO))
		{
			errors.add("jas.receiptDrug.formulation.notCorrect");
		}
		if(CollectionUtils.isNotEmpty(errors)){
			model.addAttribute("errors", errors);
			model.addAttribute("formulation", formulation);
			model.addAttribute("drugName", drugName);
			model.addAttribute("quantity", quantity);
			model.addAttribute("VAT", VAT);
			model.addAttribute("batchNo", batchNo);
			model.addAttribute("unitPrice", unitPrice);
			model.addAttribute("companyName", companyName);
			model.addAttribute("dateManufacture", dateManufacture);
			model.addAttribute("companyName", companyName);
			model.addAttribute("dateExpiry", dateExpiry);
			
			return "/module/jas/transaction/receiptsToGeneralStore";
		}
		
		//GlobalProperty gpOPDEncounterType = Context.getAdministrationService().getGlobalPropertyObject("jas.defaultVAT");
    	//BigDecimal maxJAS = NumberUtils.createBigDecimal(gpOPDEncounterType.getPropertyValue());
		
		JASStoreDrugTransactionDetail transactionDetail = new JASStoreDrugTransactionDetail();
		transactionDetail.setDrug(drug);
		transactionDetail.setFormulation(jASService.getDrugFormulationById(formulation));
		transactionDetail.setBatchNo(batchNo);
		transactionDetail.setCompanyName(companyName);
		transactionDetail.setCurrentQuantity(quantity);
		transactionDetail.setQuantity(quantity);
		transactionDetail.setUnitPrice(unitPrice);
		transactionDetail.setVAT(VAT);
		transactionDetail.setOtherTaxes(otherTaxes);
		transactionDetail.setIssueQuantity(0);
		transactionDetail.setCreatedOn(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			transactionDetail.setDateExpiry(formatter.parse(dateExpiry+" 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		VAT = VAT.add(otherTaxes); 
		transactionDetail.setDateManufacture(DateUtils.getDateFromStr(dateManufacture));
		transactionDetail.setReceiptDate(DateUtils.getDateFromStr(receiptDate));
		
		
		/*Money moneyUnitPrice = new Money(unitPrice);
		Money totl = moneyUnitPrice.times(quantity);
		totl = totl.plus(totl.times((double)VAT/100));
		transactionDetail.setTotalPrice(totl.getAmount());*/
		
		BigDecimal moneyUnitPrice = unitPrice.multiply(new BigDecimal(quantity));
		
		moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(VAT.divide(new BigDecimal(100))));
		moneyUnitPrice = moneyUnitPrice.setScale(2, BigDecimal.ROUND_UP);
		transactionDetail.setTotalPrice(moneyUnitPrice);
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "reipt_"+userId;
		List<JASStoreDrugTransactionDetail> list = (List<JASStoreDrugTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list == null){
			list = new ArrayList<JASStoreDrugTransactionDetail>();
		}
		list.add(transactionDetail);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		//model.addAttribute("listReceipt", list);
	 return "redirect:/module/jas/receiptsToGeneralStore.form";
	}
}
