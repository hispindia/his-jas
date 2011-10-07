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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p> Class: AddDescriptionSlipController </p>
 * <p> Package: org.openmrs.module.jas.web.controller.transaction </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 6, 2011 1:37:59 PM </p>
 * <p> Update date: Jan 6, 2011 1:37:59 PM </p>
 **/

@Controller("JASAddDescriptionSlipController")
@RequestMapping("/module/jas/addDescriptionReceiptSlip.form")
public class AddDescriptionSlipController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(Model model) {
		return "/module/jas/transaction/addDescriptionReceiptSlip";
	}
	@RequestMapping(method = RequestMethod.POST)
	public synchronized String  submit(
			@RequestParam(value="description",required=false) String description,
			Model model) {
		JASService jASService = (JASService) Context.getService(JASService.class);
		Date date = new Date();
		int userId = Context.getAuthenticatedUser().getId();
		JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));;
		
		JASStoreDrugTransaction transaction = new JASStoreDrugTransaction();
		transaction.setDescription(description);
		transaction.setCreatedOn(date);
		transaction.setStore(store);
		transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
		transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
		transaction = jASService.saveStoreDrugTransaction(transaction);
		
		String fowardParam = "reipt_"+userId;
		List<JASStoreDrugTransactionDetail> list = (List<JASStoreDrugTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list != null && list.size() > 0){
			for(int i=0;i< list.size();i++){
				JASStoreDrugTransactionDetail transactionDetail = list.get(i);
				int total = jASService.sumCurrentQuantityDrugOfStore(store.getId(),transactionDetail.getDrug().getId(),transactionDetail.getFormulation().getId());
				//save total first
				//System.out.println("transactionDetail.getDrug(): "+transactionDetail.getDrug());
				//System.out.println("transactionDetail.getFormulation(): "+transactionDetail.getFormulation());
				
				//save transactionDetail
				transactionDetail.setOpeningBalance(total);
				transactionDetail.setClosingBalance(total + transactionDetail.getQuantity());
				transactionDetail.setTransaction(transaction);
				jASService.saveStoreDrugTransactionDetail(transactionDetail);
			}
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "receiptsToGeneralStoreList.form");
		}else{
			model.addAttribute("message", "Sorry don't have any receipt to save");
			model.addAttribute("urlS", "receiptsToGeneralStore.form");
		}
	 return "/module/jas/thickbox/success";
	}
}
