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

package org.openmrs.module.jas.web.controller.property.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItemUnit;

public class ItemUnitPropertyEditor extends PropertyEditorSupport {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public ItemUnitPropertyEditor() {
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		JASService jasService = (JASService) Context.getService(JASService.class);
		if (text != null && text.trim().length() > 0) {
			try {
				setValue(jasService.getItemUnitById(NumberUtils.toInt(text)));
			}
			catch (Exception ex) {
				log.error("Error setting text: " + text, ex);
				throw new IllegalArgumentException("ItemUnit not found: " + ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		JASItemUnit s = (JASItemUnit) getValue();
		if (s == null) {
			return null;
		} else {
			return s.getId() + "";
		}
	}
}
