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

import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;

/**
 * This class defines the links that will appear on the administration page under the
 * "basicmodule.title" heading. This extension is enabled by defining (uncommenting) it in the
 * /metadata/config.xml file.
 */
public class AdminList extends AdministrationSectionExt {
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "jas.title";
	}
	
	/**
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getLinks()
	 */
	public Map<String, String> getLinks() {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("module/jas/storeList.form", "jas.store.manage");
		map.put("module/jas/itemCategoryList.form", "jas.item.manage");
		map.put("module/jas/drugCategoryList.form", "jas.drug.manage");
		
		return map;
	}
	
}
