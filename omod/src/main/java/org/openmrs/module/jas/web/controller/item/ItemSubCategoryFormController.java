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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItemCategory;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.web.controller.property.editor.ItemCategoryPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASItemSubCategoryFormController")
@RequestMapping("/module/jas/itemSubCategory.form")
public class ItemSubCategoryFormController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("itemSubCategory") JASItemSubCategory itemSubCategory,
	                        @RequestParam(value = "itemSubCategoryId", required = false) Integer id, Model model) {
		if (id != null) {
			itemSubCategory = Context.getService(JASService.class).getItemSubCategoryById(id);
			model.addAttribute("itemSubCategory", itemSubCategory);
		}
		return "/module/jas/item/itemSubCategory";
	}
	
	@ModelAttribute("categories")
	public List<JASItemCategory> populateCategories() {
		
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemCategory> categories = jasService.findItemCategory("");
		return categories;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(JASItemCategory.class, new ItemCategoryPropertyEditor());
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("itemSubCategory") JASItemSubCategory itemSubCategory,
	                       BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new ItemSubCategoryValidator().validate(itemSubCategory, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/jas/item/itemSubCategory";
			
		} else {
			JASService jasService = (JASService) Context.getService(JASService.class);
			//save store
			itemSubCategory.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			itemSubCategory.setCreatedOn(new Date());
			jasService.saveItemSubCategory(itemSubCategory);
			status.setComplete();
			return "redirect:/module/jas/itemSubCategoryList.form";
		}
	}
	
}
