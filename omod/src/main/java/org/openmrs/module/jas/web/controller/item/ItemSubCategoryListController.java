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
import org.openmrs.module.jas.model.JASItemSubCategory;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASItemSubCategoryListController")
@RequestMapping("/module/jas/itemSubCategoryList.form")
public class ItemSubCategoryListController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(method = RequestMethod.POST)
	public String delete(@RequestParam("ids") String[] ids, HttpServletRequest request) {
		String temp = "";
		HttpSession httpSession = request.getSession();
		try {
			JASService jasService = (JASService) Context.getService(JASService.class);
			if (ids != null && ids.length > 0) {
				for (String sId : ids) {
					JASItemSubCategory itemSubCategory = jasService.getItemSubCategoryById(NumberUtils.toInt(sId));
					int countItem = jasService.countItem(null, null, itemSubCategory.getId(), null);
					if (itemSubCategory != null && countItem == 0) {
						jasService.deleteItemSubCategory(itemSubCategory);
					} else {
						//temp += "We can't delete subCategory="+itemSubCategory.getName()+" because that subCategory is using please check <br/>";
						temp = "This sub-category/sub-categories cannot be deleted as it is in use";
					}
				}
			}
		}
		catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Can not delete itemSubCategory ");
			log.error(e);
		}
		httpSession
		        .setAttribute(WebConstants.OPENMRS_MSG_ATTR, StringUtils.isBlank(temp) ? "itemSubCategory.deleted" : temp);
		
		return "redirect:/module/jas/itemSubCategoryList.form";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "searchName", required = false) String searchName,
	                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
	                   @RequestParam(value = "currentPage", required = false) Integer currentPage,
	                   Map<String, Object> model, HttpServletRequest request) {
		
		JASService jasService = Context.getService(JASService.class);
		
		int total = jasService.countListItemSubCategory(searchName);
		String temp = "";
		if (!StringUtils.isBlank(searchName)) {
			temp = "?searchName=" + searchName;
		}
		PagingUtil pagingUtil = new PagingUtil(RequestUtil.getCurrentLink(request) + temp, pageSize, currentPage, total);
		
		List<JASItemSubCategory> itemSubCategorys = jasService.listItemSubCategory(searchName, pagingUtil.getStartPos(),
		    pagingUtil.getPageSize());
		
		model.put("itemSubCategorys", itemSubCategorys);
		model.put("searchName", searchName);
		model.put("pagingUtil", pagingUtil);
		
		return "/module/jas/item/itemSubCategoryList";
	}
}
