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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASConstants;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemCategory;
import org.openmrs.module.jas.model.JASItemSpecification;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.model.JASItemUnit;
import org.openmrs.module.jas.util.Action;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.web.controller.property.editor.ItemCategoryPropertyEditor;
import org.openmrs.module.jas.web.controller.property.editor.ItemSubCategoryPropertyEditor;
import org.openmrs.module.jas.web.controller.property.editor.ItemUnitPropertyEditor;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
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

@Controller("JASItemFormController")
@RequestMapping("/module/jas/item.form")
public class ItemFormController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("item") JASItem item,
	                        @RequestParam(value = "itemId", required = false) Integer id, Model model) {
		if (id != null) {
			JASService jasService = (JASService) Context.getService(JASService.class);
			item = jasService.getItemById(id);
			int countItemInTransactionDetail = jasService.checkExistItemTransactionDetail(item.getId());
			int countItemInIndentDetail = jasService.checkExistItemIndentDetail(item.getId());
			if (countItemInIndentDetail > 0 || countItemInTransactionDetail > 0) {
				model.addAttribute("delete", "This item is used in receipt or issue , you can't edit it");
			}
			model.addAttribute("item", item);
		}
		return "/module/jas/item/item";
	}
	
	/*@ModelAttribute("categories")
	public List<JASItemCategory> populateCategories() {
	
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemCategory> categories = jasService.findItemCategory("");
		return categories;
	}*/
	@ModelAttribute("subCategories")
	public List<JASItemSubCategory> populateSubCategories() {
		
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSubCategory> subCategories = jasService.findItemSubCategory("");
		return subCategories;
	}
	
	@ModelAttribute("specifications")
	public List<JASItemSpecification> populateFormulation() {
		
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemSpecification> specifications = jasService.findItemSpecification("");
		return specifications;
	}
	
	@ModelAttribute("units")
	public List<JASItemUnit> populateUnit() {
		JASService jasService = (JASService) Context.getService(JASService.class);
		List<JASItemUnit> units = jasService.findItemUnit("");
		return units;
	}
	
	@ModelAttribute("attributes")
	public List<Action> populateAttribute() {
		List<Action> attributes = ActionValue.getListItemAttribute();
		return attributes;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(JASItemCategory.class, new ItemCategoryPropertyEditor());
		binder.registerCustomEditor(JASItemSubCategory.class, new ItemSubCategoryPropertyEditor());
		binder.registerCustomEditor(java.lang.Integer.class, new CustomNumberEditor(java.lang.Integer.class, true));
		binder.registerCustomEditor(JASItemUnit.class, new ItemUnitPropertyEditor());
		binder.registerCustomEditor(Set.class, "specifications", new CustomCollectionEditor(
		                                                                                    Set.class) {
			
			JASService jasService = (JASService) Context.getService(JASService.class);
			
			protected Object convertElement(Object element) {
				Integer specificationId = null;
				if (element instanceof Integer)
					specificationId = (Integer) element;
				else if (element instanceof String)
					specificationId = NumberUtils.toInt((String) element, 0);
				return specificationId != null ? jasService.getItemSpecificationById(specificationId) : null;
			}
		});
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("item") JASItem item, BindingResult bindingResult, HttpServletRequest request,
	                       SessionStatus status) {
		new ItemValidator().validate(item, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/jas/item/item";
			
		} else {
			JASService jasService = (JASService) Context.getService(JASService.class);
			item.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			item.setCreatedOn(new Date());
			item.setCategory(item.getSubCategory().getCategory());
			jasService.saveItem(item);
			status.setComplete();
			return "redirect:/module/jas/itemList.form";
		}
	}
}
