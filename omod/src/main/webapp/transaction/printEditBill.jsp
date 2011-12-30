 <%--
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
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<input type="hidden" id="total" value="${ issueDrugIssue.total  }"/>
<!-- PRINT DIV -->
<div  id="printDiv" style="display: none;">        		
<div style="width: 850px; font-size: 1.0em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">

<center style="font-size: 1.8em;">
<b>JAN AUSHIDHI MEDICINE SHOP
<br/>(Rogi Kalyan Samiti DDUZH-Undertaking
<br/>Registered Office-DDUZH Shimla)
</b>
</center>
<table width="100%" style="font-size: 1.2em;">
<tr >
	<td align="left" >CST  - 020 104  00372</td><td align="right" >DL - SML/2011/768 </td>
</tr>
<tr>
	<td colspan="2">Medicine Shop : JAN Aushidhi Medicine Shop
					<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DDU Zonal Hospital Shimla
	</td>
</tr>
<tr>
	<td align="left">
		Name of patient : <c:if test="${not empty issueDrugIssue }">${issueDrugIssue.name}</c:if>
	</td><td align="right">Bill No: ${issueDrugIssue.billNumber }</td>
</tr>
<tr>
	<td align="left">Prescribed by : MO  </td><td align="right">Date: <openmrs:formatDate date="${issueDrugIssue.createdOn}" type="textbox"/></td>
</tr>
<tr>
	
</tr>
</table>
<br/>
<center style="font-size: 1.8em;">
<b>CASH MEMO</b>
</center>	                                              
<table border="1" >
	<tr align="center">
	<th>Particulars</th>
	<th>Formulation</th>
	<th>Strength</th>
	<th>Batch No.</th>
	<th>Expiry date</th>
	<th>Quantity</th>
	<th>MRP/Rate</th>
	<th>VAT</th>
	<th>Amount</th>
	</tr>
	<c:set var = "vTotal"   value = "0" />
	<c:choose>
	<c:when test="${not empty listDrugIssue}">
		
		<c:forEach items="${listDrugIssue}" var="issue" varStatus="varStatus">
		<c:set var = "vTotal"   value = "${vTotal + issue.transactionDetail.totalPrice}" />
		<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
			<td>${issue.transactionDetail.drug.name}</td>
			<td>${issue.transactionDetail.formulation.name}</td>
			<td>${issue.transactionDetail.formulation.dozage}</td>
			<td>${issue.transactionDetail.batchNo}</td>
			<td><openmrs:formatDate date="${issue.transactionDetail.dateExpiry}" type="textbox"/></td>
			<td>${issue.quantity}</td>
			<td>${issue.transactionDetail.rate }</td>
			<td>${issue.transactionDetail.VAT}</td>
			<td>${issue.transactionDetail.totalPrice}</td>
			</tr>
			
		</c:forEach>
			<tr >
				<td align="right" colspan="8">
					Grand total&nbsp;
				</td>
				<td align="center">
					<b>${vTotal}</b>
				</td>
			</tr>
			
	</c:when>
	</c:choose>
</table>
<br/>
<div style=" font-size: 1.3em;">
<nobr>Rs : <span id="money"></span></nobr>
</div>
<div style=" font-size: 1.0em;">
<br/>
<br/>
<br><b>**The MRP of Drugs is inclusive of VAT</b>
<br/>
<br/>Please show the Medicine to Your doctor before use
<br/>Error in the Price Charged or in Calculation, kindly bring the cash memo for correction
<br/>Please bring <b >Original Bill</b> at the time of return, else <b >No Return will be Entertained without the Original bill</b>
<br/>Mention your <b >Name/Phone/ Address</b> on <b >Original Bill</b> at the Time of <b >RETURN</b>
<br/><b >RETURN</b>  against the Bill would be entertained till <b >45 DAYS</b> from the <b >BILL DATE</b> 
</div>
<br/>


<br/><br/>
<span style="float:left;font-size: 1.2em"><b>E. & O. E</b></span><span style="float:right;font-size: 1.2em">FOR -<b>Jan Aushidhi Medicine Shop</b> </span>
<br/>
<span style="margin-left: 48em;font-size: 1.2em">DDUZ</span>
</div>
</div>
<!-- END PRINT DIV -->     
<script>
		var total = jQuery("#total").val();
		if(total != null && total != ''){
			jQuery("#money").html(CURRENTCY.toWords(total));
		}
</script>