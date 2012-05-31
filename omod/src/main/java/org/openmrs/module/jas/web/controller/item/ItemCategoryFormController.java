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

package org.openmrs.module.jas.web.controller.item;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItemCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASItemCategoryFormController")
@RequestMapping("/module/jas/itemCategory.form")
public class ItemCategoryFormController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("itemCategory") JASItemCategory itemCategory,
	                        @RequestParam(value = "itemCategoryId", required = false) Integer id, Model model) {
		if (id != null) {
			itemCategory = Context.getService(JASService.class).getItemCategoryById(id);
			model.addAttribute("itemCategory", itemCategory);
		}
		return "/module/jas/item/itemCategory";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("itemCategory") JASItemCategory itemCategory, BindingResult bindingResult,
	                       HttpServletRequest request, SessionStatus status) {
		new ItemCategoryValidator().validate(itemCategory, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/jas/item/itemCategory";
			
		} else {
			JASService jasService = (JASService) Context.getService(JASService.class);
			//save store
			itemCategory.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			itemCategory.setCreatedOn(new Date());
			jasService.saveItemCategory(itemCategory);
			status.setComplete();
			return "redirect:/module/jas/itemCategoryList.form";
		}
	}
	
}
