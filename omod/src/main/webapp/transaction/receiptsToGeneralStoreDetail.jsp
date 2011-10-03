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
