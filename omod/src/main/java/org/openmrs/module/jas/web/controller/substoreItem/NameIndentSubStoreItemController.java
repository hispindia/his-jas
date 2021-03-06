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

package org.openmrs.module.jas.web.controller.substoreItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItemIndent;
import org.openmrs.module.jas.model.JASStoreItemIndentDetail;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASNameIndentSubStoreItemController")
@RequestMapping("/module/jas/itemAddNameIndentSlip.form")
public class NameIndentSubStoreItemController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@RequestParam(value = "send", required = false) String send, Model model) {
		model.addAttribute("send", send);
		return "/module/jas/substoreItem/itemAddNameIndentSlip";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model model) {
		String indentName = request.getParameter("indentName");
		JASService jasService = (JASService) Context.getService(JASService.class);
		Date date = new Date();
		int userId = Context.getAuthenticatedUser().getId();
		JASStore store = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		
		JASStoreItemIndent indent = new JASStoreItemIndent();
		indent.setName(indentName);
		indent.setCreatedOn(date);
		indent.setStore(store);
		
		if (!StringUtils.isBlank(request.getParameter("send"))) {
			indent.setMainStoreStatus(1);
			indent.setSubStoreStatus(2);
		} else {
			indent.setMainStoreStatus(0);
			indent.setSubStoreStatus(1);
		}
		
		indent = jasService.saveStoreItemIndent(indent);
		
		String fowardParam = "subStoreIndentItem_" + userId;
		List<JASStoreItemIndentDetail> list = (List<JASStoreItemIndentDetail>) StoreSingleton.getInstance().getHash()
		        .get(fowardParam);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				JASStoreItemIndentDetail indentDetail = list.get(i);
				indentDetail.setCreatedOn(date);
				indentDetail.setIndent(indent);
				jasService.saveStoreItemIndentDetail(indentDetail);
			}
			StoreSingleton.getInstance().getHash().remove(fowardParam);
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "subStoreIndentItemList.form");
		} else {
			model.addAttribute("message", "Sorry don't have any purchase to save");
			model.addAttribute("urlS", "subStoreIndentItem.form");
		}
		return "/module/jas/thickbox/success";
	}
}
