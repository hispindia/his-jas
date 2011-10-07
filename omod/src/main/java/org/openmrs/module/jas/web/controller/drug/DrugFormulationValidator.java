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
