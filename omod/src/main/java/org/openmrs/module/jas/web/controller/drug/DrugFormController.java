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
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASConstants;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASDrugUnit;
import org.openmrs.module.jas.util.Action;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.web.controller.property.editor.DrugCategoryPropertyEditor;
import org.openmrs.module.jas.web.controller.property.editor.DrugCorePropertyEditor;
import org.openmrs.module.jas.web.controller.property.editor.DrugUnitPropertyEditor;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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

@Controller("JASDrugFormController")
@RequestMapping("/module/jas/drug.form")
public class DrugFormController {
Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("drug") JASDrug drug, @RequestParam(value="drugId",required=false) Integer id, Model model) {
		if( id != null ){
			JASService jASService = (JASService) Context.getService(JASService.class);
			int  countDrugInTransactionDetail = jASService.checkExistDrugTransactionDetail(id);
			
			if( countDrugInTransactionDetail >0){
				model.addAttribute("delete", "This drug is used in receipt or issue , you can't edit it");
			}
			
			drug = Context.getService(JASService.class).getDrugById(id);
			model.addAttribute("drug",drug);
		}
		return "/module/jas/drug/drug";
	}
	@ModelAttribute("categories")
	public List<JASDrugCategory> populateCategories() {
 
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrugCategory> categories = jASService.findDrugCategory("");
		return categories;
	}
	@ModelAttribute("formulations")
	public List<JASDrugFormulation> populateFormulation() {
 
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrugFormulation> formulations = jASService.findDrugFormulation("");
		return formulations;
	}
	@ModelAttribute("units")
	public List<JASDrugUnit> populateUnit() {
		JASService jASService = (JASService) Context.getService(JASService.class);
		List<JASDrugUnit> units = jASService.findDrugUnit("");
		return units;
	}
	@ModelAttribute("attributes")
	public List<Action> populateAttribute() {
		List<Action> attributes = ActionValue.getListDrugAttribute();
		return attributes;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor(JASConstants.TRUE,JASConstants.FALSE, true));
		binder.registerCustomEditor(Drug.class, new DrugCorePropertyEditor());
		binder.registerCustomEditor(JASDrugCategory.class, new DrugCategoryPropertyEditor());
		binder.registerCustomEditor(JASDrugUnit.class, new DrugUnitPropertyEditor());
		binder.registerCustomEditor(Set.class, "formulations",new CustomCollectionEditor(Set.class){
			JASService jASService = (JASService) Context.getService(JASService.class);
			protected Object convertElement(Object element)
			    {
				  Integer formulationId = null;
			      if (element instanceof Integer)
			    	  formulationId = (Integer) element;
			      else if (element instanceof String)
			    	  formulationId = NumberUtils.toInt((String) element , 0);
			      return formulationId != null ? jASService.getDrugFormulationById(formulationId) : null;
			    }
		});
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("drug") JASDrug drug, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new DrugValidator().validate(drug, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/jas/drug/drug";
			
		}else{
			JASService jASService = (JASService) Context.getService(JASService.class);
			drug.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			drug.setCreatedOn(new Date());
			jASService.saveDrug(drug);
			status.setComplete();
			return "redirect:/module/jas/drugList.form";
		}
	}
}
