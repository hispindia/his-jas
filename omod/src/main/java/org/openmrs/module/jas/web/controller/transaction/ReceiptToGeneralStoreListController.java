package org.openmrs.module.jas.web.controller.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.util.ActionValue;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASReceiptToGeneralStoreListController")
@RequestMapping("/module/jas/receiptsToGeneralStoreList.form")
public class ReceiptToGeneralStoreListController {
	@RequestMapping(method = RequestMethod.GET)
	public String list( @RequestParam(value="pageSize",required=false)  Integer pageSize, 
            @RequestParam(value="currentPage",required=false)  Integer currentPage,
            @RequestParam(value="receiptName",required=false)  String receiptName,
            @RequestParam(value="fromDate",required=false)  String fromDate,
            @RequestParam(value="toDate",required=false)  String toDate,
            Map<String, Object> model, HttpServletRequest request
	) {
	 JASService jASService = (JASService) Context.getService(JASService.class);
	JASStore store = jASService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
	 
	 int total = jASService.countStoreDrugTransaction(ActionValue.TRANSACTION[0], store.getId(), receiptName, fromDate, toDate);
	 String temp = "";
	 if(receiptName != null){	
			if(StringUtils.isBlank(temp)){
				temp = "?receiptName="+receiptName;
			}else{
				temp +="&receiptName="+receiptName;
			}
	}
		if(!StringUtils.isBlank(fromDate)){	
			if(StringUtils.isBlank(temp)){
				temp = "?fromDate="+fromDate;
			}else{
				temp +="&fromDate="+fromDate;
			}
	}
		if(!StringUtils.isBlank(toDate)){	
			if(StringUtils.isBlank(temp)){
				temp = "?toDate="+toDate;
			}else{
				temp +="&toDate="+toDate;
			}
	}
		
		
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request)+temp , pageSize, currentPage, total );
		List<JASStoreDrugTransaction> transactions = jASService.listStoreDrugTransaction(ActionValue.TRANSACTION[0], store.getId(), receiptName, fromDate, toDate,pagingUtil.getStartPos(), pagingUtil.getPageSize());
		model.put("receiptName", receiptName );
		model.put("fromDate", fromDate );
		model.put("toDate", toDate );
		model.put("pagingUtil", pagingUtil );
		model.put("transactions", transactions );
	 return "/module/jas/transaction/receiptsToGeneralStoreList";
	 
	}
}