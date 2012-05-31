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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreItemIndent;
import org.openmrs.module.jas.util.Action;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASSubStoreIndentItemListController")
@RequestMapping("/module/jas/subStoreIndentItemList.form")
public class SubStoreIndentItemListController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String showList(@RequestParam(value = "statusId", required = false) Integer statusId,
	                       @RequestParam(value = "indentName", required = false) String indentName,
	                       @RequestParam(value = "fromDate", required = false) String fromDate,
	                       @RequestParam(value = "toDate", required = false) String toDate,
	                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
	                       @RequestParam(value = "currentPage", required = false) Integer currentPage, Model model,
	                       HttpServletRequest request) {
		JASService jasService = Context.getService(JASService.class);
		JASStore subStore = jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser()
		        .getAllRoles()));
		
		String temp = "";
		if (!StringUtils.isBlank(indentName)) {
			temp = "?indentName=" + indentName;
		}
		
		if (statusId != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?statusId=" + statusId;
			} else {
				temp += "&statusId=" + statusId;
			}
		}
		if (!StringUtils.isBlank(fromDate)) {
			if (StringUtils.isBlank(temp)) {
				temp = "?fromDate=" + fromDate;
			} else {
				temp += "&fromDate=" + fromDate;
			}
		}
		if (!StringUtils.isBlank(toDate)) {
			if (StringUtils.isBlank(temp)) {
				temp = "?toDate=" + toDate;
			} else {
				temp += "&toDate=" + toDate;
			}
		}
		int total = jasService.countSubStoreItemIndent(subStore.getId(), indentName, statusId, fromDate, toDate);
		//System.out.println("total: "+total);
		PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage, total);
		List<JASStoreItemIndent> listIndent = jasService.listSubStoreItemIndent(subStore.getId(), indentName, statusId,
		    fromDate, toDate, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<Action> listSubStoreStatus = ActionValue.getListIndentSubStore();
		model.addAttribute("listSubStoreStatus", listSubStoreStatus);
		model.addAttribute("listIndent", listIndent);
		model.addAttribute("indentName", indentName);
		model.addAttribute("statusId", statusId);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("pagingUtil", pagingUtil);
		model.addAttribute("store", subStore);
		return "/module/jas/substoreItem/subStoreIndentItemList";
	}
}
