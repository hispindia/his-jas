/**
 * <p> File: org.openmrs.module.jas.web.controller.mainstore.AddDescriptionSlipController.java </p>
 * <p> Project: jas-omod </p>
 * <p> Copyright (c) 2011 CHT Technologies. </p>
 * <p> All rights reserved. </p>
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 6, 2011 1:37:59 PM </p>
 * <p> Update date: Jan 6, 2011 1:37:59 PM </p>
 **/

package org.openmrs.module.jas.web.controller.mainstoreItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItem;
import org.openmrs.module.jas.model.JASStoreItemTransaction;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p> Class: AddDescriptionSlipController </p>
 * <p> Package: org.openmrs.module.jas.web.controller.mainstore </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 6, 2011 1:37:59 PM </p>
 * <p> Update date: Jan 6, 2011 1:37:59 PM </p>
 **/

@Controller("JASAddDescriptionItemSlipController")
@RequestMapping("/module/jas/itemAddDescriptionReceiptSlip.form")
public class AddDescriptionSlipController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(Model model) {
		return "/module/jas/mainstoreItem/itemAddDescriptionReceiptSlip";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		String description = request.getParameter("description");
		JASService jasService = (JASService) Context.getService(JASService.class);
		Date date = new Date();
		int userId = Context.getAuthenticatedUser().getId();
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		
		JASStoreItemTransaction transaction = new JASStoreItemTransaction();
		transaction.setDescription(description);
		transaction.setCreatedOn(date);
		transaction.setStore(store);
		transaction.setTypeTransaction(ActionValue.TRANSACTION[0]);
		transaction.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
		transaction = jasService.saveStoreItemTransaction(transaction);
		
		String fowardParam = "itemReceipt_"+userId;
		List<JASStoreItemTransactionDetail> list = (List<JASStoreItemTransactionDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		if(list != null && list.size() > 0){
			for(int i=0;i< list.size();i++){
				JASStoreItemTransactionDetail transactionDetail = list.get(i);
				//save total first
				Integer specificationId = transactionDetail.getSpecification() != null ?transactionDetail.getSpecification().getId() : null;
				JASStoreItem storeItem = jasService.getStoreItem(store.getId(), transactionDetail.getItem().getId(), specificationId);
				if(storeItem == null){
					storeItem = new JASStoreItem();
					storeItem.setCurrentQuantity(transactionDetail.getQuantity());
					storeItem.setReceiptQuantity(transactionDetail.getQuantity());
					storeItem.setItem(transactionDetail.getItem());
					storeItem.setSpecification(transactionDetail.getSpecification());
					storeItem.setStore(store);
					storeItem.setStatusIndent(0);
					storeItem.setReorderQty(0);
					storeItem.setOpeningBalance(0);
					storeItem.setClosingBalance(transactionDetail.getQuantity());
					storeItem.setStatus(0);
					storeItem.setReorderQty(transactionDetail.getItem().getReorderQty());
					storeItem = jasService.saveStoreItem(storeItem);
					
				}else{
					storeItem.setOpeningBalance(storeItem.getClosingBalance());
					storeItem.setClosingBalance(storeItem.getClosingBalance()+transactionDetail.getQuantity());
					storeItem.setCurrentQuantity(storeItem.getCurrentQuantity() + transactionDetail.getQuantity());
					storeItem.setReceiptQuantity(transactionDetail.getQuantity());
					storeItem.setReorderQty(transactionDetail.getItem().getReorderQty());
					storeItem = jasService.saveStoreItem(storeItem);
				}
				//save transactionDetail
				transactionDetail.setOpeningBalance(storeItem.getOpeningBalance());
				transactionDetail.setClosingBalance(storeItem.getClosingBalance());
				transactionDetail.setTransaction(transaction);
				jasService.saveStoreItemTransactionDetail(transactionDetail);
			}
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "itemReceiptsToGeneralStoreList.form");
		}else{
			model.addAttribute("message", "Sorry don't have any receipt to save");
			model.addAttribute("urlS", "itemReceiptsToGeneralStore.form");
		}
	 return "/module/jas/thickbox/success";
	}
}
