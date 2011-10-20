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
<spring:message var="pageTitle" code="jas.issueDrug.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<br/>
<br/>
<div style="width: 40%; float: left; margin-left: 4px; ">
<b class="boxHeader">Drug</b>
<div class="box">

<form method="post" id="formIssueDrug">
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
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.issueDrug.addToSlip"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.back"/>" onclick="ACT.go('issueDrugList.form');">
</form>
</div>
</div>
<!-- Issue list -->

<div  style="width: 58%; float: right; margin-right: 16px; ">
<b class="boxHeader">Issue drug detail</b>
<div id="issueList" class="box">
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>#</th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="jas.receiptDrug.quantity"/></th>
	<th>MRP/Rate</th>
	<th>VAT</th>
	<th><spring:message code="jas.receiptDrug.price"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listPatientDetail}">
	<c:forEach items="${listPatientDetail}" var="issue" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td><a href="#" title="Remove this" onclick="JAS.removeObject('${varStatus.index}','5');">${issue.transactionDetail.drug.name}</a></td>
		<td>${issue.transactionDetail.formulation.name}-${issue.transactionDetail.formulation.dozage}</td>
		<td><openmrs:formatDate date="${issue.transactionDetail.dateExpiry}" type="textbox"/></td>
		<td>${issue.quantity}</td>
		<td>${issue.rate}</td>
		<td>${issue.transactionDetail.VAT}</td>
		<td>${issue.totalPrice}</td>
	</tr>
	</c:forEach>
	
	</c:when>
	</c:choose>
</table>
<br/>
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr >
			<td >
				Total
			</td>
			<td align="right" colspan="8">
				<b>${ total }</b>
			</td>
	</tr>
</table>
<br/>
	
<table class="box" width="100%" cellpadding="5" cellspacing="0">
<tr>
	<td>
		<c:if  test="${not empty listPatientDetail && empty issueDrugId}">
			<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="bttprocess" value="<spring:message code="jas.finish"/>" onclick="ISSUE.processIssueDrug();" />
			<div id="addPatientIssue" style="text-align:center;">
						<table class="box" width="100%">
							<tr>
								<td>PATIENT's NAME</td>
								<td><input type="text" name="patientName" id="patientName" size="35"/></td>
							</tr>
						</table>
			</div>	
			<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="bttclear" value="<spring:message code="jas.clear"/>"  onclick="ISSUE.processSlip('1');"/>
		</c:if>
	</td>
</tr>
</table>
</div>
</div>
<div id="mycontent" style="display:none;">

</div>   

 
<%@ include file="/WEB-INF/template/footer.jsp" %>