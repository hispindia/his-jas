package org.openmrs.module.jas.web.controller.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASDrug;
import org.openmrs.module.jas.model.JASDrugCategory;
import org.openmrs.module.jas.model.JASDrugFormulation;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugIssue;
import org.openmrs.module.jas.model.JASStoreDrugIssueDetail;
import org.openmrs.module.jas.model.JASStoreDrugTransactionDetail;
import org.openmrs.module.jas.util.DateUtils;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("JASIssueDrugFormController")
@RequestMapping("/module/jas/issueDrugForm.form")
public class IssueDrugFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(
			@RequestParam(value="issueDrugId",required=false)  Integer issueDrugId,
			Model model) {
	 JASService jasService = (JASService) Context.getService(JASService.class);
	 //JASStore store =  jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		/*if(store != null && store.getParent() != null && store.getIsDrug() != 1){
			return "redirect:/module/jas/subStoreIssueDrugAccountForm.form";
		}*/
	 
	 List<JASDrugCategory> listCategory = jasService.findDrugCategory("");
	 model.addAttribute("listCategory", listCategory);
	 
	 model.addAttribute("date",new Date());
	 
 	 int userId = Context.getAuthenticatedUser().getId();
	 String fowardParam = "issueDrugDetail_"+userId;
	 List<JASStoreDrugIssueDetail> list = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
	 JASStoreDrugIssue issueDrugPatient = (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get("issueDrug_"+userId);
	 model.addAttribute("listPatientDetail", list);
	 if(CollectionUtils.isNotEmpty(list)){
		 BigDecimal total = new BigDecimal(0);
		 for(JASStoreDrugIssueDetail issue : list){
			 total = total.add(issue.getTotalPrice());
		 }
		 total = total.setScale(2, BigDecimal.ROUND_UP);
		 model.addAttribute("total", total);
	 }
	 model.addAttribute("issueDrugPatient", issueDrugPatient);
	 if(issueDrugId != null && issueDrugId > 0){
		 StoreSingleton.getInstance().getHash().remove(fowardParam);
		 StoreSingleton.getInstance().getHash().remove("issueDrug_"+userId);
		 model.addAttribute("issueDrugId", issueDrugId);
		 model.addAttribute("billNo", DateUtils.getYYMMDD()+issueDrugId);
	 }
	 return "/module/jas/transaction/issueDrugForm";
	 
	}
	@RequestMapping(method = RequestMethod.POST)
	public synchronized String submit(HttpServletRequest request, Model model) {
		List<String> errors = new ArrayList<String>();
		int userId = Context.getAuthenticatedUser().getId();
		JASService jasService = (JASService) Context.getService(JASService.class);
		Integer formulation = NumberUtils.toInt(request.getParameter("formulation"),0);
		String drugName = request.getParameter("drugName");
		
		JASDrug drug = jasService.getDrugByName(drugName);
		if(drug == null){
			errors.add("jas.issueDrug.drug.required");
			
		}
		JASDrugFormulation formulationO = jasService.getDrugFormulationById(formulation);
		if(formulationO == null)
		{
			errors.add("jas.receiptDrug.formulation.required");
		}
		if(formulationO != null && drug != null && !drug.getFormulations().contains(formulationO))
		{
			errors.add("jas.receiptDrug.formulation.notCorrect");
		}
		if(CollectionUtils.isNotEmpty(errors)){
			
			model.addAttribute("errors", errors);
			String fowardParam = "issueDrugDetail_"+userId;
			List<JASStoreDrugIssueDetail> list = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
			StoreSingleton.getInstance().getHash().put(fowardParam, list);
			JASStoreDrugIssue issueDrugPatient = (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get("issueDrug_"+userId);
			model.addAttribute("issueDrugPatient", issueDrugPatient);
			model.addAttribute("listPatientDetail", list);
			return "/module/jas/transaction/issueDrugForm";
		}
		
		
		
		JASStore store =  jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
		List<Integer> listIssueQty = new ArrayList<Integer>();
		List<JASStoreDrugTransactionDetail> listReceiptDrug = jasService.listStoreDrugTransactionDetail(store.getId(), drug.getId(), formulation, true);
		boolean checkCorrect = true;
		if(listReceiptDrug != null){
			model.addAttribute("listReceiptDrug", listReceiptDrug);
			for(JASStoreDrugTransactionDetail t: listReceiptDrug){
				
				Integer temp = NumberUtils.toInt(request.getParameter(t.getId()+"") , 0);
				//System.out.println(" transaction detail "+t.getId() +" : "+temp);
				if(temp > 0){
					checkCorrect = false;
				}else{
					temp = 0;
				}
				listIssueQty.add(temp);
				if(temp > t.getCurrentQuantity()){
					errors.add("jas.issueDrug.quantity.lessthanQuantity.required");
				}
			}
		}else{
			errors.add("jas.issueDrug.drug.required");
		}
		if(checkCorrect){
			errors.add("jas.issueDrug.quantity.required");
		}
		if(errors != null && errors.size() > 0){
			
			model.addAttribute("formulation", formulation);
			model.addAttribute("listIssueQty", listIssueQty);
			model.addAttribute("errors", errors);
			String fowardParam = "issueDrugDetail_"+userId;
			List<JASStoreDrugIssueDetail> list = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
			StoreSingleton.getInstance().getHash().put(fowardParam, list);
			 JASStoreDrugIssue issueDrugPatient = (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get("issueDrug_"+userId);
			 model.addAttribute("issueDrugPatient", issueDrugPatient);
			model.addAttribute("listPatientDetail", list);
			return "/module/jas/transaction/issueDrugForm";
		}
		
		
		String fowardParam = "issueDrugDetail_"+userId;
		List<JASStoreDrugIssueDetail> list = (List<JASStoreDrugIssueDetail> )StoreSingleton.getInstance().getHash().get(fowardParam);
		List<JASStoreDrugIssueDetail> listExt = new ArrayList<JASStoreDrugIssueDetail>();
		if(list == null){
			list = new ArrayList<JASStoreDrugIssueDetail>();
		}else{
			listExt = new ArrayList<JASStoreDrugIssueDetail>(list);
		}
		for(JASStoreDrugTransactionDetail t: listReceiptDrug){
			Integer temp = NumberUtils.toInt(request.getParameter(t.getId()+"") , 0);
			if(temp > 0){
				//System.out.println("temp add vao issue : "+temp);
				
				if(CollectionUtils.isNotEmpty(list)){
					for(int i=0;i<list.size();i++){
						JASStoreDrugIssueDetail dtail = list.get(i);
						if(t.getId().equals(dtail.getTransactionDetail().getId())){
							listExt.remove(i);
							temp += dtail.getQuantity();
							break;
						}
					}
				}
				
				JASStoreDrugIssueDetail issueDrugDetail = new JASStoreDrugIssueDetail();
				issueDrugDetail.setTransactionDetail(t);
				issueDrugDetail.setQuantity(temp);
				issueDrugDetail.setVAT(t.getVAT());
				issueDrugDetail.setUnitPrice(t.getUnitPrice());
				issueDrugDetail.setOtherTaxes(t.getOtherTaxes());
				//BigDecimal moneyUnitPrice = issueDrugDetail.getRate().multiply(new BigDecimal(temp));
				//moneyUnitPrice = moneyUnitPrice.add(moneyUnitPrice.multiply(t.getVAT().divide(new BigDecimal(100))));
				BigDecimal tot = issueDrugDetail.getRate().multiply(new BigDecimal(temp));
				tot = tot.setScale(2, BigDecimal.ROUND_UP);
				issueDrugDetail.setTotalPrice(tot);
				listExt.add(issueDrugDetail);
			}
		}
		StoreSingleton.getInstance().getHash().put(fowardParam, listExt);
		JASStoreDrugIssue issueDrugPatient = (JASStoreDrugIssue )StoreSingleton.getInstance().getHash().get("issueDrug_"+userId);
		//model.addAttribute("issueDrugPatient", issueDrugPatient);
		//model.addAttribute("listPatientDetail", list);
	 return "redirect:/module/jas/issueDrugForm.form";
	}
	
}
