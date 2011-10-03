package org.openmrs.module.jas.web.controller.drug;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DrugCategoryValidator implements Validator {

	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return JASDrugCategory.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	JASDrugCategory category = (JASDrugCategory) command;
    	
    	if( StringUtils.isBlank(category.getName())){
    		error.reject("jas.drugCategory.name.required");
    	}
    	JASService jASService = (JASService) Context.getService(JASService.class);
    	JASDrugCategory categoryE = jASService.getDrugCategoryByName(category.getName());
    	if(category.getId() != null){
    		if(categoryE != null && categoryE.getId().intValue() != category.getId().intValue()){
    			error.reject("jas.drugCategory.name.existed");
    		}
    	}else{
    		if(categoryE != null){
    	    		error.reject("jas.drugCategory.name.existed");
    		}
    	}
    	
    	
    }

}