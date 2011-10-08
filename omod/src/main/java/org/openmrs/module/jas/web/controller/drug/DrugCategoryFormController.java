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

package org.openmrs.module.jas.web.controller.drug;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASDrugCategoryFormController")
@RequestMapping("/module/jas/drugCategory.form")
public class DrugCategoryFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("drugCategory") JASDrugCategory drugCategory, @RequestParam(value="drugCategoryId",required=false) Integer id, Model model) {
		if( id != null ){
			drugCategory = Context.getService(JASService.class).getDrugCategoryById(id);
			model.addAttribute("drugCategory",drugCategory);
		}
		return "/module/jas/drug/drugCategory";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("drugCategory") JASDrugCategory drugCategory, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new DrugCategoryValidator().validate(drugCategory, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/jas/drug/drugCategory";
			
		}else{
			JASService jASService = (JASService) Context
			.getService(JASService.class);
			//save store
			drugCategory.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			drugCategory.setCreatedOn(new Date());
			jASService.saveDrugCategory(drugCategory);
			status.setComplete();
			return "redirect:/module/jas/drugCategoryList.form";
		}
	}
	
}
