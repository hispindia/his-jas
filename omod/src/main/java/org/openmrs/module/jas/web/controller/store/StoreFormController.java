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

package org.openmrs.module.jas.web.controller.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.util.Action;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.web.controller.property.editor.RolePropertyEditor;
import org.openmrs.module.jas.web.controller.property.editor.StorePropertyEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASstoreFormController")
@RequestMapping("/module/jas/store.form")
public class StoreFormController {
	 Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("store") JASStore store, @RequestParam(value="storeId",required=false) Integer id, Model model) {
		if( id != null ){
			store = Context.getService(JASService.class).getStoreById(id);
			model.addAttribute("store",store);
		}
		List<Action> listIsDrug = ActionValue.getListIsDrug();
		model.addAttribute("listIsDrug",listIsDrug);
		return "/module/jas/store/form";
	}
	
	@ModelAttribute("roles")
	public List<Role> populateRoles(HttpServletRequest request) {
 
	
		//List<Role> listRole = Context.getUserService().getAllRoles();
		
		//return listRole;
		
		JASService jASService = (JASService) Context.getService(JASService.class);
		String storeId = request.getParameter("storeId");
		String role = "";
		JASStore store = null;
		if( storeId != null )
		{
			store = jASService.getStoreById(NumberUtils.toInt(storeId));
			if(store != null){
				role = store.getRole() != null ? store.getRole().getRole() : "";
			}
			
		}
		List<Role> roles = Context.getUserService().getAllRoles();
		List<Role> listRole = new ArrayList<Role>();
		listRole.addAll(roles);
	    List<JASStore> stores = jASService.listAllStore();
	    //System.out.println("stores: "+stores);
	    //System.out.println("users: "+users);
	    if(!CollectionUtils.isEmpty(roles) && !CollectionUtils.isEmpty(stores)){
		    for( Role roleX : roles ){
		    	for( JASStore s : stores )	{
		    		if( s.getRole() != null && s.getRole().getRole().equals(roleX.getRole()) && !roleX.getRole().equals(role)){
		    			listRole.remove(roleX);
		    		}
		    	}
		    }
	    }
		return listRole;
		
		
		
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor(JASConstants.TRUE,JASConstants.FALSE, true));
		binder.registerCustomEditor(java.lang.Integer.class,new CustomNumberEditor(java.lang.Integer.class, true));
		//binder.registerCustomEditor(java.util.List.class,new CustomCollectionEditor(java.util.List.class, true));
		binder.registerCustomEditor(JASStore.class, new StorePropertyEditor());
		binder.registerCustomEditor(Role.class, new RolePropertyEditor());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("store") JASStore store, BindingResult bindingResult, HttpServletRequest request, SessionStatus status) {
		new StoreValidator().validate(store, bindingResult);
		//storeValidator.validate(store, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "/module/jas/store/form";
			
		}else{
			JASService jASService = (JASService) Context
			.getService(JASService.class);
			//save store
			store.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			store.setCreatedOn(new Date());
			jASService.saveStore(store);
			
			status.setComplete();
			return "redirect:/module/jas/storeList.form";
		}
	}
	
}
