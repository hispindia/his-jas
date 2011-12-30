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

<openmrs:require privilege="manage JAS" otherwise="/login.htm" redirect="/module/jas/main.form" />
<spring:message var="pageTitle" code="jas.issueDrug.editBill" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<br/>
<br/>
<div style="width: 40%; float: left; margin-left: 4px; ">
<b class="boxHeader">Drug</b>
<div class="box">

<form method="post" id="formEditBill">
<c:if  test="${not empty errors}">
<c:forEach items="${errors}" var="error">
	<span class="error"><spring:message code="${error}" /></span>
</c:forEach>
</c:if>
<br/>
<table class="box">
<tr><td><b>Drug info</b></td></tr>
	<tr>
		<td>Drug<em>*</em></td>
		<td>
			<input type="text" id="drugName" name="drugName" onchange="ISSUE.onBlur(this);"   style="width: 300px;">
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.drug.formulation"/><em>*</em></td>
		<td>
			<div id="divFormulation"  >
				<select id="formulation" name="formulation"  >
					<option value=""><spring:message code="jas.pleaseSelect"/></option>
				</select>
			</div>
		</td>
	</tr>
</table>
<br/>
<div id="divDrugAvailable">
</div>
<br/>
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add to bill">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.back"/>" onclick="ACT.go('issueDrugList.form');">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.cancel"/>" onclick="ACT.go('cancelEditBill.form');">
</form>
</div>
</div>
<!-- Issue list -->

<div  style="width: 58%; float: right; margin-right: 16px; ">
<b class="boxHeader">Bill detail</b>
<div id="editBillList" class="box">
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr><td>Bill no: <b>${issueDrugPatient.billNumber}</b></td></tr>
	<tr><td>Patient's: <b>${issueDrugPatient.name }</b></td></tr>
	<tr><td>Total bill: <b>${issueDrugPatient.total}</b></td></tr>
	<tr><td>Created on: <b><openmrs:formatDate date="${issueDrugPatient.createdOn}" type="textbox"/></b></td></tr>
</table>
<br/>
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>#</th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="jas.receiptDrug.quantity"/></th>
	<th><spring:message code="jas.issueDrug.returnQuantity"/></th>
	<th>Final qty</th>
	<th>MRP/Rate</th>
	<th>VAT</th>
	<th><spring:message code="jas.receiptDrug.price"/></th>
	<th>Return qty</th>
	</tr>
	<c:choose>
	<c:when test="${not empty listPatientDetail}">
	<c:forEach items="${listPatientDetail}" var="issue" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${issue.transactionDetail.drug.name}</td>
		<td>${issue.transactionDetail.formulation.name}-${issue.transactionDetail.formulation.dozage}</td>
		<td><openmrs:formatDate date="${issue.transactionDetail.dateExpiry}" type="textbox"/></td>
		<td>${issue.quantity}</td>
		<td>${issue.returnQuantity  + issue.tempReturnQuantity }</td>
		<td>${issue.currentQuantity - issue.tempReturnQuantity }</td>
		<td>${issue.rate}</td>
		<td>${issue.transactionDetail.VAT}</td>
		<td>${issue.totalPrice}</td>
		<td>
			<c:if test="${issue.id != 0 }">
				<input type="text" id="${issue.id }" name="${issue.id }" class="changeQuantity" onblur="EDITBILL.refundQuantity(this,${issue.currentQuantity},${issue.tempReturnQuantity });"  value="${issue.tempReturnQuantity }" style="width:35px;"/>
			</c:if>
		</td>
	</tr>
	</c:forEach>
	
	</c:when>
	</c:choose>
</table>
<br/>
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr >
			<td >
				New Total
			</td>
			<td align="right" colspan="8">
				<b>${ total }</b>
			</td>
	</tr>
</table>
<c:if test="${edit == true }">
	<br/>
	<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr>
		<td>
			<c:if  test="${not empty listPatientDetail}">
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="bttprocess" value="<spring:message code="jas.finish"/>"  onclick="EDITBILL.finishEditBill();" />
				<div id="alertEditBill" style="text-align:center;">
							<table class="box" width="100%">
								<tr>
									<td>Are you sure to save the bill?</td>
								</tr>
							</table>
				</div>	
			</c:if>
		</td>
	</tr>
	</table>
</c:if>
</div>

</div>
<div id="mycontent" style="display:none;">

</div>   

 
<%@ include file="/WEB-INF/template/footer.jsp" %>