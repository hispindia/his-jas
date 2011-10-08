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

package org.openmrs.module.jas.extension.html;

import java.util.ArrayList;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.Extension;
import org.openmrs.module.jas.JASConstants;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.web.extension.AdministrationSectionExt;
import org.openmrs.module.web.extension.LinkExt;

/**
 * This class defines the links that will appear on the administration page
 * 
 * This extension is enabled by defining (uncommenting) it in the 
 * /metadata/config.xml file. 
 */
public class JASHeader extends LinkExt {

	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see AdministrationSectionExt#getRequiredPrivilege()
	 */
	@Override
	public String getRequiredPrivilege() {
		return JASConstants.STORE_VIEW;
	}

	/**
	 * @see org.openmrs.module.web.extension.LinkExt#getLabel()
	 */
	@Override
	public String getLabel() {
		try {
			JASService jASService = (JASService) Context.getService(JASService.class);
			JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
			if(store!=null && !store.getRetired()){
				return store.getName();	
			}else{
				return "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return "";
	}

	/** 
	 * @see org.openmrs.module.web.extension.LinkExt#getUrl()
	 */
	@Override
	public String getUrl() {
		return "module/jas/main.form";	
	}
}
