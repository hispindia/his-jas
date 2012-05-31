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

package org.openmrs.module.jas.web.controller.item;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASItem;
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASItemListController")
@RequestMapping("/module/jas/itemList.form")
public class ItemListController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.POST)
	public String delete(@RequestParam("ids") String[] ids, HttpServletRequest request) {
		String temp = "";
		HttpSession httpSession = request.getSession();
		try {
			JASService jasService = (JASService) Context.getService(JASService.class);
			if (ids != null && ids.length > 0) {
				for (String sId : ids) {
					JASItem item = jasService.getItemById(NumberUtils.toInt(sId));
					int countItemInTransactionDetail = jasService.checkExistItemTransactionDetail(item.getId());
					int countItemInIndentDetail = jasService.checkExistItemIndentDetail(item.getId());
					
					if (item != null && countItemInIndentDetail == 0 && countItemInTransactionDetail == 0) {
						jasService.deleteItem(item);
					} else {
						//temp += "We can't delete item="+item.getName()+" because that item is using please check <br/>";
						temp = "This item/items cannot be deleted as it is in use";
					}
				}
			}
		}
		catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Can not delete item ");
			log.error(e);
		}
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, StringUtils.isBlank(temp) ? "item.deleted" : temp);
		
		return "redirect:/module/jas/itemList.form";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "searchName", required = false) String searchName,
	                   @RequestParam(value = "categoryId", required = false) Integer categoryId,
	                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
	                   @RequestParam(value = "currentPage", required = false) Integer currentPage,
	                   Map<String, Object> model, HttpServletRequest request) {
		
		JASService jasService = Context.getService(JASService.class);
		
		int total = jasService.countListItem(categoryId, searchName);
		String temp = "";
		if (!StringUtils.isBlank(searchName)) {
			temp = "?searchName=" + searchName;
		}
		if (categoryId != null) {
			if (StringUtils.isBlank(temp)) {
				temp = "?categoryId=" + categoryId;
			} else {
				temp += "&categoryId=" + categoryId;
			}
		}
		PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage, total);
		
		List<JASItem> items = jasService
		        .listItem(categoryId, searchName, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<JASItemSubCategory> categories = jasService.listItemSubCategory("", 0, 0);
		model.put("items", items);
		model.put("categories", categories);
		model.put("categoryId", categoryId);
		model.put("searchName", searchName);
		model.put("pagingUtil", pagingUtil);
		
		return "/module/jas/item/itemList";
	}
}
