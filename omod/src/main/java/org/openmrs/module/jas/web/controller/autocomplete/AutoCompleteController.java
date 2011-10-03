package org.openmrs.module.jas.web.controller.autocomplete;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
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

