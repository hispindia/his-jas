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
import org.openmrs.module.jas.model.JASItemUnit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASItemUnitFormController")
@RequestMapping("/module/jas/itemUnit.form")
public class ItemUnitFormController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("itemUnit") JASItemUnit itemUnit,
	                        @RequestParam(value = "itemUnitId", required = false) Integer id, Model model) {
		if (id != null) {
			itemUnit = Context.getService(JASService.class).getItemUnitById(id);
			model.addAttribute("itemUnit", itemUnit);
		}
		return "/module/jas/item/itemUnit";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("itemUnit") JASItemUnit itemUnit, BindingResult bindingResult,
	                       HttpServletRequest request, SessionStatus status) {
		new ItemUnitValidator().validate(itemUnit, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/jas/item/itemUnit";
			
		} else {
			JASService jasService = (JASService) Context.getService(JASService.class);
			//save store
			itemUnit.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			itemUnit.setCreatedOn(new Date());
			jasService.saveItemUnit(itemUnit);
			status.setComplete();
			return "redirect:/module/jas/itemUnitList.form";
		}
	}
	
}
