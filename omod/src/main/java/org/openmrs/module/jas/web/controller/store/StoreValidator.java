package org.openmrs.module.jas.web.controller.store;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class StoreValidator implements Validator {

	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return JASStore.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	
    	JASStore store = (JASStore) command;
    	
    	if( StringUtils.isBlank(store.getName())){
    		error.reject("jas.store.name.required");
    	}
    	if(store.getRole() == null){
    		error.reject("jas.store.role.required");
    	}
    	/*if( StringUtils.isBlank(store.getCode())){
    		error.reject("jas.store.code.required");
    	}*/
    	JASService jASService = (JASService) Context.getService(JASService.class);
    	JASStore storeE = jASService.getStoreByName(store.getName());
    	if(store.getId() != null){
    		if(storeE != null && storeE.getId().intValue() != store.getId().intValue()){
    			error.reject("jas.store.name.existed");
    		}
    	}else{
    		if(storeE != null){
    	    		error.reject("jas.store.name.existed");
    		}
    		int totalStore = jASService.countListStore();
    		GlobalProperty gpOPDEncounterType = Context.getAdministrationService().getGlobalPropertyObject("jas.maxJAS");
        	int maxJAS = NumberUtils.toInt(gpOPDEncounterType.getPropertyValue(), 1);
        	if(totalStore >= maxJAS){
        		error.reject("jas.store.maximum.required");
        	}
    	}
    	
    	
    	
    }

}