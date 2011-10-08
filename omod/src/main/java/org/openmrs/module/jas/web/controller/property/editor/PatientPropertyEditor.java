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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class PatientPropertyEditor extends PropertyEditorSupport {
	private Log log = LogFactory.getLog(this.getClass());
	public PatientPropertyEditor() {
	}
	public void setAsText(String text) throws IllegalArgumentException {
		
		if (text != null && text.trim().length() > 0 ) {
			try {
				//System.out.println("text: "+text);
				
				List<Patient> patientsList = Context.getPatientService().getPatients( text );
				if(patientsList != null && patientsList.size() > 0){
					//System.out.println("get day ne: "+patientsList.get(0));
					setValue(patientsList.get(0));
				}else{
					setValue(null);
				}
			}
			catch (Exception ex) {
				log.error("Error setting text: " + text, ex);
				throw new IllegalArgumentException("Patient not found: " + ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		Patient s = (Patient) getValue();
		if (s == null ) {
			return null; 
		} else {
			return s.getPatientIdentifier().getIdentifier();
		}
	}
}
