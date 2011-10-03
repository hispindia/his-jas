package org.openmrs.module.jas.web.controller.transaction;

import java.util.ArrayList;
import java.util.Date;

import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.jas.JASService;
import org.openmrs.module.jas.model.JASStore;
import org.openmrs.module.jas.model.JASStoreDrugIssue;
import org.openmrs.module.jas.web.controller.global.StoreSingleton;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("FindPatientIssueDrugController")
@RequestMapping("/module/jas/patientIssueDrug.form")
public class FindPatientIssueDrugController {

	@RequestMapping(method = RequestMethod.GET)
	public String firstView(Model model, @RequestParam(value="patientId",required=false)  Integer patientId) {
		
		if(patientId != null && patientId > 0){
			Patient patient = Context.getPatientService().getPatient(patientId);
			if(patient != null){
				JASStoreDrugIssue issue = new JASStoreDrugIssue();
				JASService jasService = (JASService) Context.getService(JASService.class);
				int userId = Context.getAuthenticatedUser().getId();
				JASStore subStore =  jasService.getStoreByCollectionRole(new ArrayList<Role>(Context.getAuthenticatedUser().getAllRoles()));
				issue.setCreatedBy(Context.getAuthenticatedUser().getGivenName());
				issue.setCreatedOn(new Date());
				issue.setStore(subStore);
				issue.setIdentifier(patient.getPatientIdentifier().getIdentifier());
				issue.setName(patient.getGivenName()+" "+patient.getMiddleName()+" "+patient.getFamilyName());
				issue.setPatient(patient);
				String fowardParam = "issueDrug_"+userId;
				StoreSingleton.getInstance().getHash().put(fowardParam,issue);
				return "redirect:/module/jas/issueDrugForm.form";
			}
			
		}
		return "/module/jas/transaction/patientIssueDrug";
	}
	
}
