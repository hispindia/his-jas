package org.openmrs.module.jas.web.controller.drug;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DrugFormulationValidator implements Validator {

	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return JASDrugFormulation.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	JASDrugFormulation formulation = (JASDrugFormulation) command;
    	
    	if( StringUtils.isBlank(formulation.getName())){
    		error.reject("jas.drugFormulation.name.required");
    	}
    	if( StringUtils.isBlank(formulation.getDozage())){
    		error.reject("jas.drugFormulation.dozage.required");
    	}
    	JASService jASService = (JASService) Context.getService(JASService.class);
    	JASDrugFormulation formulationE = jASService.getDrugFormulation(formulation.getName() ,formulation.getDozage());
    	if(formulation.getId() != null){
    		if(formulationE != null && formulationE.getId().intValue() != formulation.getId().intValue()){
    			error.reject("jas.drugFormulation.dozage.existed");
    		}
    	}else{
    		if(formulationE != null){
    	    		error.reject("jas.drugFormulation.dozage.existed");
    		}
    	}
    	
    	
    }

}