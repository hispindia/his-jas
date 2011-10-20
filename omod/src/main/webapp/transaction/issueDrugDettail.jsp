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


<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>#</th>
	<th><spring:message code="jas.viewStockBalance.drug"/></th>
	<th><spring:message code="jas.viewStockBalance.formulation"/></th>
	<th ><spring:message code="jas.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="jas.issueDrug.quantity"/></th>
	<th>MRP/Rate</th>
	<th>VAT</th>
	<th>Amount</th>
	</tr>
	<c:choose>
	<c:when test="${not empty listDrugIssue}">
	<c:forEach items="${listDrugIssue}" var="detail" varStatus="varStatus">
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${detail.transactionDetail.drug.name} </td>	
		<td>${detail.transactionDetail.formulation.name}-${detail.transactionDetail.formulation.dozage}</td>
		<td><openmrs:formatDate date="${detail.transactionDetail.dateExpiry}" type="textbox"/></td>
		<td>${detail.quantity }</td>
		<td>${detail.transactionDetail.rate }</td>
		<td>${detail.transactionDetail.VAT}</td>
		<td>${detail.transactionDetail.totalPrice}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>

 