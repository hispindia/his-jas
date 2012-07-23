/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of JAS module.
 *
 *  JAS module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  JAS module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with JAS module.  If not, see <http://www.gnu.org/licenses/>.
 *
*/
JAS={
		checkValue : function()
		{
			var form = jQuery("#form");
			if( jQuery("input[type='checkbox']:checked",form).length > 0 )
			{ 
				if(confirm("Are you sure?"))
				{
					form.submit();
				}
			}
			else
			{
				alert("Please choose objects for deleting");
				return false;
			}
		},
		search : function(url, value){
			ACT.go(url+"?"+value+"="+jQuery("#"+value).val());
		},
		checkValueExt : function(thiz, value)
		{
			if(parseInt(jQuery(thiz).val()) > parseInt(value)){
				alert('Issue quantity is greater that available quantity!');
				jQuery(thiz).val("");
				jQuery(thiz).focus();
			}
		},
		removeObject : function(position, check)
		{
			if(confirm("Are you want to remove this?")){
				ACT.go("removeObjectFromList.form?position="+position+"&check="+check);
			}
		},
		printDiv : function ()
		{
		  	jQuery("div#printDiv").printArea({mode:"popup",popClose:true,popTitle: "Support by HISP india(hispindia.org)"});
		},
		initTableHover : function()
		{
			jQuery("tr").each(function(){
				if( jQuery(this).hasClass("evenRow") || jQuery(this).hasClass("oddRow") )
				{
					jQuery(this).hover(
							function(){jQuery(this).addClass("hover");},
							function(){jQuery(this).removeClass("hover");}
							);
				}
			});
		},
		initDialog : function(divId,dTitle,dWidth)
		{
			jQuery('#'+divId).dialog({
				autoOpen: false,
				modal: true,
				title: dTitle,
				width: dWidth
				
			});
		}
};

ITEM={
		listSubCatByCat : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "subCatByCat.form"
								,data: ({categoryId :x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divSubCat").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		onChangeAttribute : function(thiz)
		{
			if(jQuery(thiz).val() == 1){
				if(jQuery("#reorderQty").val()==0){
					jQuery("#reorderQty").val("");
				}
				jQuery(".depentOnAttribute").show();
			}else{
				jQuery("#reorderQty").val(0);
				jQuery(".depentOnAttribute").hide();
			}
		},
		searchItem : function(thiz)
		{
			var itemName = jQuery("#searchName").val();
			var categoryId = jQuery("#categoryId").val();
			ACT.go("itemList.form?categoryId="+categoryId+"&searchName="+itemName);
		}
	};
DRUG = {
		onChangeAttribute : function(thiz)
		{
			if(jQuery(thiz).val() == 1){
				if(jQuery("#reorderQty").val()==0){
					jQuery("#reorderQty").val("");
				}
				jQuery(".depentOnAttribute").show();
			}else{
				jQuery("#reorderQty").val(0);
				jQuery(".depentOnAttribute").hide();
			}
		},
		searchDrug : function(thiz)
		{
			var drugName = jQuery("#searchName").val();
			var categoryId = jQuery("#categoryId").val();
			ACT.go("drugList.form?categoryId="+categoryId+"&searchName="+drugName);
		}
};
STORE={
		
		goToAdd : function()
		{
			ACT.go('store.form');	
		},
		edit : function(id)
		{
			ACT.go('store.form?storeId='+id);
		}	
};
RECEIPT={
		onChangeCategory : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "drugByCategory.form"
								,data: ({categoryId: x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divDrug").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		checkVAT : function(thiz)
		{
			var VAT = jQuery(thiz).val();
			if(VAT != undefined && VAT != '' && VAT != null){
				if(VAT.indexOf('-') != -1){
					alert('VAT cant be negative number');
					jQuery(thiz).val("");
					jQuery(thiz).focus();
				}
			}
		},
		detailReceiptDrug : function(receiptId)
		{
			if(SESSION.checkSession()){
				
				var data = jQuery.ajax(
						{
							type:"GET"
							,url: "drugReceiptDetail.form"
							,data: ({receiptId: receiptId})	
							,async: false
							, cache : false
						}).responseText;
				if(data != undefined  && data != null && data != ''){
					jQuery("#mycontent").html(data);
					
					JAS.initDialog('mycontent', 'Detail receipt', '80%');
					
					jQuery('#mycontent').dialog('open');
				}else{
					alert('Please refresh page!');
				}
				
				
			}
		},
		printReceiptDrug : function(receiptId)
		{
			if(SESSION.checkSession()){
				
				var data = jQuery.ajax(
						{
							type:"GET"
							,url: "receiptDetailPrint.form"
							,data: ({receiptId: receiptId})	
							,async: false
							, cache : false
						}).responseText;
				if(data != undefined  && data != null && data != ''){
					jQuery("#mycontent").html(data);
					JAS.printDiv();
				}else{
					alert('Please refresh page!');
				}
			}
		},
		onChangeCategoryItem : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "itemBySubCategory.form"
								,data: ({categoryId: x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divItem").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		onBlurItem : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "specificationByItem.form"
								,data: ({itemId: x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divSpecification").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		onBlur : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "formulationByDrug.form"
								,data: ({drugName: x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divFormulation").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		printDiv : function ()
		{
		  	jQuery("div#printDiv").printArea({mode:"popup",popClose:true,popTitle: "Support by HISP india(hispindia.org)"});
		},
		receiptSlip : function(action){
				if(action == 0){
					if(SESSION.checkSession()){
						url = "addDescriptionReceiptSlip.form?action="+action+"&keepThis=false&TB_iframe=true&height=200&width=460";
						tb_show("Add description for this slip....",url,false);
						
					}
				}else{
					if( confirm("Are you want to clear this slip?")){
						ACT.go("clearSlip.form?action="+action);
					}
				}
		},
		receiptSlipItem : function(action){
			if(action == 0){
				if(SESSION.checkSession()){
					url = "itemAddDescriptionReceiptSlip.form?action="+action+"&keepThis=false&TB_iframe=true&height=200&width=450";
					tb_show("Add description for this slip....",url,false);
				}
			}else{
				if( confirm("Are you want to clear this slip?")){
					ACT.go("itemClearSlip.form?action="+action);
				}
			}
		}
		
};

ISSUE={
		onChangeCategory : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "drugByCategoryForIssue.form"
								,data: ({categoryId: x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divDrug").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		
		onBlur : function(x)
		{
			x = x.toString();
			if(x != null && x != '' ){
				if(SESSION.checkSession()){
		
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "formulationByDrugForIssue.form"
								,data: ({drugName :x})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divFormulation").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		
		checkQtyBeforeIssue : function(thiz){

			if(parseInt(jQuery("#issueItemQuantity").val()) > parseInt(jQuery("#currentQuantity").val())){
				alert('Issue quantity must less than current quantity');
				return false;
			}
			jQuery('#formIssueItem').submit();
		},
		onBlurPatient : function(thiz)
		{
			var x = jQuery(thiz).val();
			if(x != undefined  && x != null && x != '' ){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "autoCompletePatientList.form"
								,data: ({searchPatient :x})	
								,async: false
								, cache : false
							}).responseText;
					
					if(data != undefined  && data != null && data != ''){
						jQuery("#divShowPatients").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
			
		},
		formulationOnChange : function(thiz){
			var formulationId = jQuery(thiz).val();
			var drugName = jQuery("#drugName").val();
			if(formulationId != '' && drugName != ''){
				if(SESSION.checkSession()){
					var data = jQuery.ajax(
							{
								type:"GET"
								,url: "listReceiptDrug.form"
								,data: ({drugName: drugName,formulationId: formulationId})	
								,async: false
								, cache : false
							}).responseText;
					if(data != undefined  && data != null && data != ''){
						jQuery("#divDrugAvailable").html(data);
					}else{
						alert('Please refresh page!');
					}
				}
			}
		},
		
		findPatient : function()
		{
			if(SESSION.checkSession()){
				url = "patientIssueDrug.form?keepThis=false&TB_iframe=true&height=500&width=800";
				tb_show("...",url,false);
			}
		},
		addPatient : function(url)
		{
			if(SESSION.checkSession()){
				self.parent.tb_remove();
				self.parent.ACT.go(url);
			}
		},
		createAccount : function()
		{
			if(SESSION.checkSession()){
				url = "createAccountIssueItem.form?keepThis=false&TB_iframe=true&height=300&width=450";
				tb_show("Create account issue item....",url,false);
			}
		},
		createPatientName : function()
		{
			
			if(SESSION.checkSession()){
				jQuery("#bttprocess").val("Wait a moment!");
				jQuery("#bttprocess").attr("disabled","disabled");
				jQuery("#bttclear").attr("disabled","disabled");
				url = "patientName.form?keepThis=false&TB_iframe=true&height=300&width=550";
				tb_show("Patient name....",url,false);
			}
		},
		processSlip : function(data){
			if(data == 1){
				if( confirm("Are you want to clear this?")){
					ACT.go("processIssueDrug.form?action="+data);
				}
			}else{
				
				if(SESSION.checkSession()){
					//alert('data: '+jQuery("#patientName").val());
					
					self.parent.tb_remove();
					self.parent.ACT.go("processIssueDrug.form?action=0&patientName="+jQuery("#patientName").val());
					
				}
				
				
			}
			
		},
		
		detailIssueDrug : function(id){
			
			if(SESSION.checkSession()){
				
				var data = jQuery.ajax(
						{
							type:"GET"
							,url: "issueDrugDettail.form"
							,data: ({issueId: id})	
							,async: false
							, cache : false
						}).responseText;
				if(data != undefined  && data != null && data != ''){
					jQuery("#mycontent").html(data);
					
					JAS.initDialog('mycontent', 'Detail issue', '80%');
					
					jQuery('#mycontent').dialog('open');
				}else{
					alert('Please refresh page!');
				}
				
				
			}
			
		},
		printIssueDrug : function(id){
			if(SESSION.checkSession()){
				
				var data = jQuery.ajax(
						{
							type:"GET"
							,url: "issueDrugPrint.form"
							,data: ({issueId: id})	
							,async: false
							, cache : false
						}).responseText;
				if(data != undefined  && data != null && data != ''){		
					jQuery("#mycontent").html(data);
					JAS.printDiv();
				}else{
					alert('Please refresh page!');
				}
				
				
			}
			
		},
		processIssueDrug : function()
		{
			jQuery('#addPatientIssue').dialog('open');

		}
};
STOCKBALLANCE = {
		back : function()
		{
			history.back(-1);
		},
		detail : function(drugId, formulationId)
		{
		
			ACT.go("viewStockBalanceDetail.form?drugId="+drugId+"&formulationId="+formulationId);
		},
		detailExpiry : function(drugId, formulationId)
		{
			ACT.go("viewStockBalanceDetail.form?drugId="+drugId+"&formulationId="+formulationId+"&expiry=1");
		},
		goToTranser : function(url)
		{
			ACT.go(url);
		}
		
};
