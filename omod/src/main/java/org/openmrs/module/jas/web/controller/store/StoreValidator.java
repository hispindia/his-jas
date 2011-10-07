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
