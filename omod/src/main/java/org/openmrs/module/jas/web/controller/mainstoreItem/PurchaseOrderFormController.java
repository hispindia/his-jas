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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.model.JASStoreItemIndentDetail;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASitemPurchaseOrderFormController")
@RequestMapping("/module/jas/itemPurchaseOrderForGeneralStore.form")
public class PurchaseOrderFormController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> listCategory = jasService.listItemSubCategory("", 0, 0);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("categoryId", categoryId);
		
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemPurchase_" + userId;
		List<JASStoreItemIndentDetail> list = (List<JASStoreItemIndentDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		model.addAttribute("listPurchase", list);
		
		return "/module/jas/mainstoreItem/itemPurchaseOrderForGeneralStore";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> listCategory = jasService.listItemSubCategory("", 0, 0);
		model.addAttribute("listCategory", listCategory);
		int category = NumberUtils.toInt(request.getParameter("category"), 0);
		int specification = NumberUtils.toInt(request.getParameter("specification"), 0);
		String itemName = request.getParameter("itemName");
		int quantity = NumberUtils.toInt(request.getParameter("quantity"), 0);
		
		JASItem item = jasService.getItemByName(itemName);
		if (item == null) {
			errors.add("jas.purchase.item.required");
			model.addAttribute("category", category);
			model.addAttribute("specification", specification);
			model.addAttribute("itemName", itemName);
			model.addAttribute("quantity", quantity);
			
			return "/module/jas/mainstoreItem/itemPurchaseOrderForGeneralStore";
		}
		
		JASStoreItemIndentDetail indentDetail = new JASStoreItemIndentDetail();
		indentDetail.setItem(item);
		indentDetail.setSpecification(jasService.getItemSpecificationById(specification));
		indentDetail.setQuantity(quantity);
		int userId = Context.getAuthenticatedUser().getId();
		String fowardParam = "itemPurchase_" + userId;
		List<JASStoreItemIndentDetail> list = (List<JASStoreItemIndentDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		if (list == null) {
			list = new ArrayList<JASStoreItemIndentDetail>();
		}
		list.add(indentDetail);
		StoreSingleton.getInstance().getHash().put(fowardParam, list);
		model.addAttribute("listPurchase", list);
		return "/module/jas/mainstoreItem/itemPurchaseOrderForGeneralStore";
	}
}
