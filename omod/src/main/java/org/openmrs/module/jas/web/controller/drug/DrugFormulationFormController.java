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

package org.openmrs.module.jas.web.controller.drug;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASDrugFormulationFormController")
@RequestMapping("/module/jas/drugFormulation.form")
public class DrugFormulationFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("drugFormulation") JASDrugFormulation drugFormulation, @RequestParam(value="drugFormulationId",required=false) Integer id, Model model) {
		if( id != null ){
			drugFormulation = Context.getService(JASService.class).getDrugFormulationById(id);
			model.addAttribute("drugFormulation",drugFormulation);
		}
		return "/module/jas/drug/drugFormulation";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("drugFormulation") JASDrugFormulation drugFormulation, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new DrugFormulationValidator().validate(drugFormulation, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/jas/drug/drugFormulation";
			
		}else{
			JASService jASService = (JASService) Context
			.getService(JASService.class);
			//save store
			drugFormulation.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			drugFormulation.setCreatedOn(new Date());
			jASService.saveDrugFormulation(drugFormulation);
			status.setComplete();
			return "redirect:/module/jas/drugFormulationList.form";
		}
	}
	
}
