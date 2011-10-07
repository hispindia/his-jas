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


package org.openmrs.module.jas.web.controller.global;

import java.util.HashMap;

/**
 * <p> Class: SubStoreSingleton </p>
 * <p> Package: org.openmrs.module.jas.web.controller.substore </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Dec 28, 2010 10:11:47 PM </p>
 * <p> Update date: Dec 28, 2010 10:11:47 PM </p>
 **/
public class StoreSingleton {
	private static StoreSingleton instance = null;
	   public static final StoreSingleton getInstance(){
		   if (instance == null) {
		         instance = new StoreSingleton();
		      }
		      return instance;
	   }
	   private static HashMap<String, Object> hash;
	   public static HashMap<String, Object> getHash() {
			if( hash == null )
				hash = new HashMap<String, Object>();
			return hash;
		}
}
