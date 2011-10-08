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

package org.openmrs.module.jas.web.controller.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASViewStockBalanceController")
@RequestMapping("/module/jas/viewStockBalance.form")
public class ViewStockBalanceController {
	@RequestMapping(method = RequestMethod.GET)
	public String list( @RequestParam(value="pageSize",required=false)  Integer pageSize, 
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
            @RequestParam(value="categoryId",required=false)  Integer categoryId,
            @RequestParam(value="drugName",required=false)  String drugName,
            @RequestParam(value="fromDate",required=false)  String fromDate,
            @RequestParam(value="toDate",required=false)  String toDate,
            Map<String, Object> model, HttpServletRequest request
	) {
	 JASService jASService = (JASService) Context.getService(JASService.class);
	JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
	 
	 int total = jASService.countViewStockBalance(store.getId(), categoryId, drugName, fromDate, toDate , false);
	 String temp = "";
		if(categoryId != null){	
				temp = "?categoryId="+categoryId;
		}
		
		if(drugName != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?drugName="+drugName;
			}else{
				temp +="&drugName="+drugName;
			}
	}
		if(fromDate != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?fromDate="+fromDate;
			}else{
				temp +="&fromDate="+fromDate;
			}
	}
		if(toDate != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?toDate="+toDate;
			}else{
				temp +="&toDate="+toDate;
			}
	}
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
		List<JASStoreDrugTransactionDetail> stockBalances = jASService.listViewStockBalance(store.getId(), categoryId, drugName, fromDate, toDate, false, pagingUtil.getStartPos(), pagingUtil.getPageSize());
		List<JASDrugCategory> listCategory = jASService.listDrugCategory("", 0, 0);
		model.put("categoryId", categoryId );
		model.put("drugName", drugName );
		model.put("fromDate", fromDate );
		model.put("toDate", toDate );
		model.put("pagingUtil", pagingUtil );
		model.put("stockBalances", stockBalances );
		model.put("listCategory", listCategory );
	 return "/module/jas/transaction/viewStockBalance";
	 
	}
}
