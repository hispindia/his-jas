package org.openmrs.module.jas.web.controller.property.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugCategory;

public class DrugCategoryPropertyEditor extends PropertyEditorSupport{
	private Log log = LogFactory.getLog(this.getClass());
	public DrugCategoryPropertyEditor() {
	}
	public void setAsText(String text) throws IllegalArgumentException {
		JASService jASService = (JASService) Context.getService(JASService.class);
		if (text != null && text.trim().length() > 0 ) {
			try {
				setValue(jASService.getDrugCategoryById(NumberUtils.toInt(text)));
			}
			catch (Exception ex) {
				log.error("Error setting text: " + text, ex);
				throw new IllegalArgumentException("DrugCategory not found: " + ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		JASDrugCategory s = (JASDrugCategory) getValue();
		if (s == null ) {
			return null; 
		} else {
			return s.getId()+"";
		}
	}
}
