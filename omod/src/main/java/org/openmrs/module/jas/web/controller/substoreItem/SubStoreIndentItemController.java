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
import org.openmrs.module.jas.model.JASStoreItemIndentDetail;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASSubStoreIndentItemController")
@RequestMapping("/module/jas/subStoreIndentItem.form")
public class SubStoreIndentItemController {
	
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
		
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		model.addAttribute("store", store);
		model.addAttribute("date", new Date());
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "subStoreIndentItem_" + userId;
		List<JASStoreItemIndentDetail> list = (List<JASStoreItemIndentDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		model.addAttribute("listIndent", list);
		
		return "/module/jas/substoreItem/subStoreIndentItem";
		
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
		
		JASItem item = jasService.getItemById(itemId);
		if (item == null) {
			errors.add("jas.indent.item.required");
			model.addAttribute("category", category);
			model.addAttribute("formulation", specification);
			model.addAttribute("itemId", itemId);
			model.addAttribute("quantity", quantity);
			model.addAttribute("errors", errors);
			return "/module/jas/substore/subStoreIndentItem";
		} else if (CollectionUtils.isNotEmpty(item.getSpecifications()) && specification == 0) {
			errors.add("jas.receiptItem.specification.required");
			return "/module/jas/substore/subStoreIndentItem";
		}
		
		JASStoreItemIndentDetail indentDetail = new JASStoreItemIndentDetail();
		indentDetail.setItem(item);
		indentDetail.setSpecification(jasService.getItemSpecificationById(specification));
		indentDetail.setQuantity(quantity);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "subStoreIndentItem_" + userId;
		List<JASStoreItemIndentDetail> list = (List<JASStoreItemIndentDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		
		List<JASStoreItemIndentDetail> listExt = null;
		if (list == null) {
			listExt = list = new ArrayList<JASStoreItemIndentDetail>();
		} else {
			listExt = new ArrayList<JASStoreItemIndentDetail>(list);
		}
		for (int i = 0; i < list.size(); i++) {
			JASStoreItemIndentDetail d = list.get(i);
			
			if (d.getItem().getId().equals(indentDetail.getItem().getId()))
				if ((d.getSpecification() != null && d.getSpecification().getId()
				        .equals(indentDetail.getSpecification().getId()))
				        || (d.getSpecification() == null && indentDetail.getSpecification() == null)) {
					indentDetail.setQuantity(indentDetail.getQuantity() + d.getQuantity());
					listExt.remove(i);
					break;
				}
		}
		
		listExt.add(indentDetail);
		
		StoreSingleton.getInstance().getHash().put(fowardParam, listExt);
		//model.addAttribute("listIndent", list);
		return "redirect:/module/jas/subStoreIndentItem.form";
	}
}
