package org.openmrs.module.jas.web.controller.drug;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugUnit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASDrugUnitFormController")
@RequestMapping("/module/jas/drugUnit.form")
public class DrugUnitFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("drugUnit") JASDrugUnit drugUnit, @RequestParam(value="drugUnitId",required=false) Integer id, Model model) {
		if( id != null ){
			drugUnit = Context.getService(JASService.class).getDrugUnitById(id);
			model.addAttribute("drugUnit",drugUnit);
		}
		return "/module/jas/drug/drugUnit";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("drugUnit") JASDrugUnit drugUnit, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new DrugUnitValidator().validate(drugUnit, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/jas/drug/drugUnit";
			
		}else{
			JASService jASService = (JASService) Context.getService(JASService.class);
			drugUnit.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			drugUnit.setCreatedOn(new Date());
			jASService.saveDrugUnit(drugUnit);
			status.setComplete();
			return "redirect:/module/jas/drugUnitList.form";
		}
	}
	
}
