package org.openmrs.module.jas.web.controller.store;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugTransaction;
import org.openmrs.module.jas.util.PagingUtil;
import org.openmrs.module.jas.util.RequestUtil;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("JASstoreListController")
@RequestMapping("/module/jas/storeList.form")
public class StoreListController {
	 Log log = LogFactory.getLog(this.getClass());
	@RequestMapping(method=RequestMethod.POST)
    public String deleteStores(@RequestParam("ids") String[] ids,HttpServletRequest request){
		String temp = "";
    	HttpSession httpSession = request.getSession();
		Integer storeId  = null;
		try{
			JASService jASService = (JASService)Context.getService(JASService.class);
			if( ids != null && ids.length > 0 ){
				for(String sId : ids )
				{
					storeId = Integer.parseInt(sId);
					JASStore store = jASService.getStoreById(storeId);
					List<JASStoreDrugTransaction> listdrugTransaction = jASService.listStoreDrugTransaction(null, storeId, "", "", "", 0, 1);
					if( store!= null  && CollectionUtils.isEmpty(listdrugTransaction) )
					{
						jASService.deleteStore(store);
					}else{
						//temp += "We can't delete store="+store.getName()+" because that store is using please check <br/>";
						temp = "This store/stores cannot be deleted as it is in use";
					}
				}
			}
		}catch (Exception e) {
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
			"Can not delete store ");
			log.error(e);
		}
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, StringUtils.isBlank(temp) ?  "store.deleted" : temp);
    	
    	return "redirect:/module/jas/storeList.form";
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public String listStore(@RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         Map<String, Object> model, HttpServletRequest request){
		
		JASService jASService = Context.getService(JASService.class);
		
		int total = jASService.countListStore();
		
		PagingUtil pagingUtil = new PagingUtil( RequestUtil.getCurrentLink(request) , pageSize, currentPage, total );
		
		List<JASStore> stores = jASService.listStore(pagingUtil.getStartPos(), pagingUtil.getPageSize());
		
		model.put("stores", stores );
		
		model.put("pagingUtil", pagingUtil);
		
		return "/module/jas/store/list";
	}
}
