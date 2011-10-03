package org.openmrs.module.jas.web.controller.drug;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugUnit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DrugUnitValidator implements Validator {

	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return JASDrugUnit.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	JASDrugUnit unit = (JASDrugUnit) command;
    	
    	if( StringUtils.isBlank(unit.getName())){
    		error.reject("jas.drugUnit.name.required");
    	}
    	JASService jASService = (JASService) Context.getService(JASService.class);
    	JASDrugUnit unitE = jASService.getDrugUnitByName(unit.getName());
    	if(unit.getId() != null){
    		if(unitE != null && unitE.getId().intValue() != unit.getId().intValue()){
    			error.reject("jas.drugUnit.name.existed");
    		}
    	}else{
    		if(unitE != null){
    	    		error.reject("jas.drugUnit.name.existed");
    		}
    	}
    	
    	
    }

}