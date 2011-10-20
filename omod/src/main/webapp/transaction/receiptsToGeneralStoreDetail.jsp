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
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>#</th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.receiptDrug.receiptQuantity"/></th>
	<th><spring:message code="jas.receiptDrug.unitPrice"/></th>
	<th><spring:message code="jas.receiptDrug.VAT"/></th>
	<th><spring:message code="jas.receiptDrug.otherTaxes"/></th>
	<th><spring:message code="jas.receiptDrug.totalPrice"/></th>
	<th><spring:message code="jas.receiptDrug.batchNo"/></th>
	<th title="<spring:message code="jas.receiptDrug.companyName"/>">CN</th>
	<th title="<spring:message code="jas.receiptDrug.dateManufacture"/>">DM</th>
	<th title="<spring:message code="jas.receiptDrug.dateExpiry"/>">DE</th>
	<th title="<spring:message code="jas.receiptDrug.receiptDate"/>">RD</th>
	</tr>
	<c:choose>
	<c:when test="${not empty transactionDetails}">
	<c:forEach items="${transactionDetails}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${receipt.drug.name}</td>
		<td>${receipt.formulation.name}-${receipt.formulation.dozage}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.otherTaxes}</td>
		<td>${receipt.totalPrice}</td>
		<td>${receipt.batchNo}</td>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.dateExpiry}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
