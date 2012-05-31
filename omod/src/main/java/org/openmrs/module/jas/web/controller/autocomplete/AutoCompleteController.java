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

package org.openmrs.module.jas.web.controller.autocomplete;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller("JASAutoCompleteController")
public class AutoCompleteController {
		@RequestMapping("/module/jas/autoCompleteDrugCoreList.form")
		public String drugCore(@RequestParam(value="q",required=false) String name, Model model) {
			List<Drug> drugs = new ArrayList<Drug>();
			if(!StringUtils.isBlank(name)){
				drugs = Context.getConceptService().getDrugs(name);
			}else{
				drugs = Context.getConceptService().getAllDrugs();
			}
				model.addAttribute("drugs",drugs);
			return "/module/jas/autocomplete/autoCompleteDrugCoreList";
		}
		@RequestMapping("/module/jas/autoCompleteDrugList.form")
		public String drug(@RequestParam(value="q",required=false) String name, Model model) {
			 JASService jasService = (JASService) Context.getService(JASService.class);
			 List<JASDrug> drugs = jasService.findDrug(null, name);
			 model.addAttribute("drugs",drugs);
			return "/module/jas/autocomplete/autoCompleteDrugList";
		}
		
		@RequestMapping("/module/jas/autoCompleteItemList.form")
		public String item(@RequestParam(value="term",required=false) String name,@RequestParam(value="categoryId",required=false) Integer categoryId, Model model) {
			JASService jasService = (JASService) Context.getService(JASService.class);
			List<JASItem> items = jasService.findItem(categoryId, name);
			model.addAttribute("items",items);
			return "/module/jas/autocomplete/autoCompleteItemList";
		}
		@RequestMapping("/module/jas/checkSession.form")
		public String checkSession(HttpServletRequest request,Model model) {
			 if( Context.getAuthenticatedUser() != null &&  Context.getAuthenticatedUser().getId() != null){
				 model.addAttribute("session","1");
			 }else{
				 model.addAttribute("session","0");
			 }
			
			return "/module/jas/session/checkSession";
		}

		
	}

