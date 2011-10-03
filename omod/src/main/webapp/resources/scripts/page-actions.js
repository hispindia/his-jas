

var EVT =
{
	ready : function()
	{
		/**
		 * Page Actions
		 */
		
		var enableCheck = true;
		var pageId = jQuery("form").attr("id");
		if(enableCheck && pageId != undefined  && pageId != null && pageId !=''  && eval("CHECK." + pageId))
		{
			eval("CHECK." + jQuery("form").attr("id") + "()");
		}
		
		jQuery('.date-pick').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true});

		/**
		 * Ajax Indicator when send and receive data
		 */
		if(jQuery.browser.msie)
		{
			jQuery.ajaxSetup({cache: false});
		}
	
	}
};

var CHECK = 
{
	
	jasStore : function()
	{
		var validator = jQuery("#jasStore").validate(
		{
			event : "blur",
			rules : 
			{
				"name" : { required : true},
				"code" : { required : true},
				"role" : { required : true},
				"isDrug" : { required : true}
			
			}
		});
	},
	drugCategory : function()
	{
		var validator = jQuery("#drugCategory").validate(
		{
			event : "blur",
			rules : 
			{
				"name" : { required : true}
			
			}
		});
	},
	drugUnit : function()
	{
		var validator = jQuery("#drugUnit").validate(
		{
			event : "blur",
			rules : 
			{
				"name" : { required : true}
			
			}
		});
	},
	drugFormulation : function()
	{
		var validator = jQuery("#drugFormulation").validate(
		{
			event : "blur",
			rules : 
			{
				"name" : { required : true},
				"dozage" : { required : true}
			
			}
		});
	},
	drugForm : function()
	{
		//jQuery("#drugCore").autocomplete({source: 'autoCompleteDrugCoreList.form', minLength: 3 } );
		jQuery("#drugCore").autocomplete('autoCompleteDrugCoreList.form', {
			 minChars: 3 ,
			 delay:1000,
			 scroll: true});
		var validator = jQuery("#drugForm").validate(
		{
			event : "blur",
			rules : 
			{
				"name" : { required : true},
				"category" : { required : true},
				"unit" : { required : true},
				"drugCore" : { required : true},
				"formulations" : { required : true},
				"attribute" : { required : true},
				"reorderQty" : { required : true, number: true}
			
			}
		});
	},
	formFinishReceipSlip : function()
	{
		var validator = jQuery("#formFinishReceipSlip").validate(
		{
			event : "blur",
			rules : 
			{
				"description" : { required : true}
			
			}
		});
	},
	
	receiptDrug : function()
	{
		//jQuery("#drugName").autocomplete({source: 'autoCompleteDrugList.form', minLength: 3 });
		jQuery("#drugName").autocomplete('autoCompleteDrugList.form', {
			 minChars: 3 ,
			 delay:1000,
			 scroll: true});
		var validator = jQuery("#receiptDrug").validate(
				{
					event : "blur",
					rules : 
					{

						"drugName" : { required : true},
						"formulation" : { required : true},
						"batchNo" : { required : true},
						"companyName" : { required : true},
						"dateManufacture" : { required : true},
						"quantity" : { required : true,digits : true,min : 1},
						"unitPrice" : { required : true,number : true,min : 0},
						"VAT" : { required : true,number : true,min : 0},
						"otherTaxes" : { required : true,number : true,min : 0},
						"dateExpiry" : { required : true},
						"receiptDate" : { required : true}
					
					},
					submitHandler: function(form) {
						var check =(jQuery("#dateManufacture").datepicker("getDate") < jQuery("#dateExpiry").datepicker("getDate"));
						var check1 =(jQuery("#dateManufacture").datepicker("getDate") < jQuery("#receiptDate").datepicker("getDate"));
						if(check && check1){
							form.submit();
						}else{
							alert('Please make sure manufacture date < expiry date,manufacture date < receipt date');
						}
						
					}
				});
		
		jQuery('#addNameReceipt').dialog({
			autoOpen: false,
			modal: true,
			title: 'Add description for this receipt',
			width: '40%',
			buttons: {
				"Save": function() {
					var description = jQuery("#description").val();
					jQuery("#addNameReceipt").html("<img src='../../moduleResources/jas/scripts/jquery/css/indicator.gif'  />");
					var data = jQuery.ajax(
							{
								type:"POST"
								,url: "addDescriptionReceiptSlip.form"
								,data: ({description: description})	
								,async: false
								, cache : false
							}).responseText;
					if(data != null){
						jQuery( this ).dialog( "close" );
						ACT.go("receiptsToGeneralStoreList.form");
					}else{
						alert('Get error from server side please try late');
						jQuery( this ).dialog( "close" );
					}
					
				},
				Cancel: function() {
					jQuery( this ).dialog( "close" );
				}
			}
		});
			
				
		jQuery('#addNameReceiptButton').click(function() {
			jQuery('#addNameReceipt').dialog('open');
		});
	},
	formIssueDrug : function()
	{
		//jQuery("#drugName").autocomplete({source: 'autoCompleteDrugList.form', minLength: 3 });
		
		jQuery("#drugName").autocomplete('autoCompleteDrugList.form', {
			minChars: 3 ,
			delay:1000,
			scroll: true}).result(function(event, item) {
				jQuery("#divDrugAvailable").html("");
				ISSUE.onBlur(item);
			});
		
		var validator = jQuery("#formIssueDrug").validate(
				{
					event : "blur",
					rules : 
					{

						"drugName" : { required : true},
						"formulation" : { required : true}
					
					}
				});
		
		jQuery('#addPatientIssue').dialog({
			autoOpen: false,
			modal: true,
			title: 'Add patient\'s name',
			width: '40%',
			buttons: {
				"Save": function() {
					var patientName =jQuery("#patientName").val();
					jQuery("#addPatientIssue").html("<img src='../../moduleResources/jas/scripts/jquery/css/indicator.gif'  />");
					var data = jQuery.ajax(
							{
								type:"POST"
								,url: "processIssueDrug.form"
								,data: ({action: 0,patientName: patientName})	
								,async: false
								, cache : false
							}).responseText;
					if(data != null){
						jQuery("#issueList").html("");
						jQuery( this ).dialog( "close" );
						ISSUE.printIssueDrug(data);
					}else{
						alert('Get error from server side please try late');
						jQuery( this ).dialog( "close" );
					}
					
				},
				Cancel: function() {
					jQuery( this ).dialog( "close" );
				}
			}
		});
		
		
		
	},
	receiptList : function()
	{
		
	},
	findPatient : function()
	{
		jQuery('input#searchPatient').keypress(function(e) {
			  if (e.keyCode == '13') 
			  {
			     e.preventDefault();
			     ISSUE.onBlurPatient(jQuery('input#searchPatient'));
			   }
		});
	}
	
	
	
};

/**
 * Pageload actions trigger
 */

jQuery(document).ready(
	function() 
	{
		EVT.ready();
	}
);



