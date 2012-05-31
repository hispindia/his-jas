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
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItemSubCategoryValidator implements Validator {
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return JASItemSubCategory.class.equals(clazz);
	}
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object command, Errors error) {
		JASItemSubCategory subCategory = (JASItemSubCategory) command;
		
		if (StringUtils.isBlank(subCategory.getName())) {
			error.reject("jas.itemSubCategory.name.required");
		}
		if (subCategory.getCategory() == null) {
			error.reject("jas.itemSubCategory.category.required");
		}
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASItemSubCategory subCategoryE = jasService.getItemSubCategoryByName(subCategory.getCategory().getId(),
		    subCategory.getName());
		if (subCategory.getId() != null) {
			if (subCategoryE != null && subCategoryE.getId().intValue() != subCategory.getId().intValue()) {
				error.reject("jas.itemSubCategory.name.existed");
			}
		} else {
			if (subCategoryE != null) {
				error.reject("jas.itemSubCategory.name.existed");
			}
		}
		
	}
	
}
