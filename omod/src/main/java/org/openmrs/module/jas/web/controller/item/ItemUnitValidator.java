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

package org.openmrs.module.jas.web.controller.item;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItemUnit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItemUnitValidator implements Validator {
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return JASItemUnit.class.equals(clazz);
	}
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object command, Errors error) {
		JASItemUnit unit = (JASItemUnit) command;
		
		if (StringUtils.isBlank(unit.getName())) {
			error.reject("jas.itemUnit.name.required");
		}
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASItemUnit unitE = jasService.getItemUnitByName(unit.getName());
		if (unit.getId() != null) {
			if (unitE != null && unitE.getId().intValue() != unit.getId().intValue()) {
				error.reject("jas.itemUnit.name.existed");
			}
		} else {
			if (unitE != null) {
				error.reject("jas.itemUnit.name.existed");
			}
		}
		
	}
	
}
