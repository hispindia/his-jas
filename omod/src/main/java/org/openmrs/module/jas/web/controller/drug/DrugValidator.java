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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.springframework.validation.Errors;

public class DrugValidator {
	/**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
    	return JASDrug.class.equals(clazz);
    }

	/**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object command, Errors error) {
    	JASDrug drug = (JASDrug) command;
    	
    	if( StringUtils.isBlank(drug.getName())){
    		error.reject("jas.drug.name.required");
    	}
    	if( drug.getCategory() == null){
    		error.reject("jas.drug.category.required");
    	}
    	if( drug.getDrugCore() == null){
    		error.reject("jas.drug.drug.required");
    	}
    	if( CollectionUtils.isEmpty(drug.getFormulations()) && drug.getId() == null){
    		error.reject("jas.drug.formulation.required");
    	}
    	if( drug.getUnit() == null ){
    		error.reject("jas.drug.unit.required");
    	}
    	JASService jASService = (JASService) Context.getService(JASService.class);
    	JASDrug drugE = jASService.getDrugByName(drug.getName());
    	if(drug.getId() != null){
    		int  countDrugInTransactionDetail = jASService.checkExistDrugTransactionDetail(drug.getId());
			if(countDrugInTransactionDetail >0){
				drug.setFormulations(jASService.getDrugById(drug.getId()).getFormulations());
			}
    		if(drugE != null ){
    			if(drugE.getId().intValue() != drug.getId().intValue()){
    				error.reject("jas.drug.name.existed");
    			}
    		}
    	}else{
    		if(drugE != null){
    	    		error.reject("jas.drug.name.existed");
    		}
    	}
    	
    	
    }
}
