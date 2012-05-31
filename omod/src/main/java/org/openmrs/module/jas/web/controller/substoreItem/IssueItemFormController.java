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
import org.openmrs.module.jas.model.JASStoreItemAccount;
import org.openmrs.module.jas.model.JASStoreItemAccountDetail;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASIssueItemFormController")
@RequestMapping("/module/jas/subStoreIssueItemForm.form")
public class IssueItemFormController {
	
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
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "issueItemDetail_" + userId;
		List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		JASStoreItemAccount issueItemAccount = (JASStoreItemAccount) StoreSingleton.getInstance().getHash()
		        .get("issueItem_" + userId);
		model.addAttribute("listAccountDetail", list);
		model.addAttribute("issueItemAccount", issueItemAccount);
		return "/module/jas/substoreItem/subStoreIssueItemForm";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> listCategory = jasService.listItemSubCategory("", 0, 0);
		model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"), 0);
		Integer specification = NumberUtils.toInt(request.getParameter("specification"), 0);
		Integer itemId = NumberUtils.toInt(request.getParameter("itemId"), 0);
		int userId = Context.getAuthenticatedUser().getId();
		Integer issueItemQuantity = NumberUtils.toInt(request.getParameter("issueItemQuantity"), 0);
		JASItem item = jasService.getItemById(itemId);
		
		if (item == null || (item.getSpecifications() != null && item.getSpecifications().size() > 0 && specification <= 0)) {
			errors.add("jas.issueItem.quantity.required");
			model.addAttribute("errors", errors);
			model.addAttribute("category", category);
			String fowardParam = "issueItemDetail_" + userId;
			List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash()
			        .get(fowardParam);
			JASStoreItemAccount issueItemAccount = (JASStoreItemAccount) StoreSingleton.getInstance().getHash()
			        .get("issueItem_" + userId);
			model.addAttribute("issueItemAccount", issueItemAccount);
			model.addAttribute("listAccountDetail", list);
			return "/module/jas/substoreItem/subStoreIssueItemForm";
		}
		
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		
		Integer sumCurrentOfItem = jasService.sumStoreItemCurrentQuantity(store.getId(), item.getId(), specification);
		if (sumCurrentOfItem == 0 || issueItemQuantity <= 0) {
			errors.add("jas.issueItem.quantity.required");
		}
		if (sumCurrentOfItem < issueItemQuantity) {
			errors.add("jas.issueItem.quantity.lessthanQuantity.required");
		}
		
		if (errors != null && errors.size() > 0) {
			String fowardParam = "issueItemDetail_" + userId;
			List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash()
			        .get(fowardParam);
			JASStoreItemAccount issueItemAccount = (JASStoreItemAccount) StoreSingleton.getInstance().getHash()
			        .get("issueItem_" + userId);
			model.addAttribute("issueItemAccount", issueItemAccount);
			model.addAttribute("listAccountDetail", list);
			model.addAttribute("category", category);
			model.addAttribute("specification", specification);
			model.addAttribute("issueItemQuantity", issueItemQuantity);
			model.addAttribute("itemId", itemId);
			model.addAttribute("errors", errors);
			return "/module/jas/substoreItem/subStoreIssueItemForm";
		}
		
		String fowardParam = "issueItemDetail_" + userId;
		List<JASStoreItemAccountDetail> list = (List<JASStoreItemAccountDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		
		List<JASStoreItemTransactionDetail> listReceiptItem = jasService.listStoreItemTransactionDetail(store.getId(),
		    item.getId(), specification, true);
		if (list == null) {
			list = new ArrayList<JASStoreItemAccountDetail>();
		}
		List<JASStoreItemAccountDetail> listExt = new ArrayList<JASStoreItemAccountDetail>(list);
		if (CollectionUtils.isNotEmpty(list)) {
			for (JASStoreItemAccountDetail tDetail : list) {
				if (tDetail.getTransactionDetail().getItem().getId().equals(item.getId())) {
					if (tDetail.getTransactionDetail().getSpecification() != null && specification != null) {
						if (tDetail.getTransactionDetail().getSpecification().getId().equals(specification)) {
							issueItemQuantity += tDetail.getQuantity();
							listExt.remove(tDetail);
						}
					} else {
						if (tDetail.getTransactionDetail().getSpecification() == null) {
							issueItemQuantity += tDetail.getQuantity();
							listExt.remove(tDetail);
						}
					}
				}
			}
		}
		
		for (JASStoreItemTransactionDetail t : listReceiptItem) {
			JASStoreItemAccountDetail issueItemDetail = new JASStoreItemAccountDetail();
			if (t.getItem().getId() == item.getId()) {
				if (t.getCurrentQuantity() >= issueItemQuantity) {
					issueItemDetail.setTransactionDetail(t);
					issueItemDetail.setQuantity(issueItemQuantity);
					listExt.add(issueItemDetail);
					break;
				} else {
					issueItemDetail.setTransactionDetail(t);
					issueItemDetail.setQuantity(t.getCurrentQuantity());
					issueItemQuantity -= t.getCurrentQuantity();
					listExt.add(issueItemDetail);
					
				}
				
			}
		}
		StoreSingleton.getInstance().getHash().put(fowardParam, listExt);
		JASStoreItemAccount issueItemAccount = (JASStoreItemAccount) StoreSingleton.getInstance().getHash()
		        .get("issueItem_" + userId);
		// model.addAttribute("issueItemAccount", issueItemAccount);
		//model.addAttribute("listAccountDetail", list);
		return "redirect:/module/jas/subStoreIssueItemForm.form";
	}
}
