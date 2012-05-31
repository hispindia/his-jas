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

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItemAccount;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller("JASCreateAccountIssueItemController")
@RequestMapping("/module/jas/createAccountIssueItem.form")
public class CreateAccountIssueItemController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("issue") JASStoreItemAccount issue, Model model) {
		return "/module/jas/substoreItem/createAccountIssueItem";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("issue") JASStoreItemAccount issue, BindingResult bindingResult,
	                       HttpServletRequest request, SessionStatus status, Model model) {
		new IssueItemAccountValidator().validate(issue, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/module/jas/substoreItem/createAccountIssueItem";
			
		} else {
			
			JASService jasService = (JASService) Context.getService(JASService.class);
			int userId = Context.getAuthenticatedUser().getId();
			JASStore subStore = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
			        .getAllRoles()));
			issue.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
			issue.setCreatedOn(new Date());
			issue.setStore(subStore);
			status.setComplete();
			
			String fowardParam = "issueItem_" + userId;
			StoreSingleton.getInstance().getHash().put(fowardParam, issue);
			
			model.addAttribute("message", "Succesfully");
			model.addAttribute("urlS", "subStoreIssueItemForm.form");
			
			return "/module/jas/thickbox/success";
		}
	}
}
