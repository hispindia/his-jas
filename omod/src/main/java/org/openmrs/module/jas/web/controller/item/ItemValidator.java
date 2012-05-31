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
import org.openmrs.module.jas.model.JASItem;
import org.springframework.validation.Errors;

public class ItemValidator {
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return JASItem.class.equals(clazz);
	}
	
	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object command, Errors error) {
		JASItem item = (JASItem) command;
		
		if (StringUtils.isBlank(item.getName())) {
			error.reject("jas.item.name.required");
		}
		/*if( item.getCategory() == null){
			error.reject("jas.item.category.required");
		}*/
		if (item.getSubCategory() == null) {
			error.reject("jas.item.subCategory.required");
		}
		if (item.getUnit() == null) {
			error.reject("jas.item.unit.required");
		}
		JASService jasService = (JASService) Context.getService(JASService.class);
		JASItem itemE = jasService.getItemByName(item.getName());
		if (item.getId() != null) {
			int countItemInTransactionDetail = jasService.checkExistItemTransactionDetail(item.getId());
			int countItemInIndentDetail = jasService.checkExistItemIndentDetail(item.getId());
			if (countItemInIndentDetail > 0 || countItemInTransactionDetail > 0) {
				item.setSpecifications(jasService.getItemById(item.getId()).getSpecifications());
			}
			if (itemE != null) {
				if (itemE.getId().intValue() != item.getId().intValue()) {
					error.reject("jas.item.name.existed");
				}
			}
		} else {
			if (itemE != null) {
				error.reject("jas.item.name.existed");
			}
		}
		
	}
}
