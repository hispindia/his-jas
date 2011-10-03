<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="manage JAS" otherwise="/login.htm" redirect="/module/jas/main.form" />
<spring:message var="pageTitle" code="jas.receiptDrug.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="nav.jsp" %>
<h2><spring:message code="jas.receiptDrug.manage"/></h2>	
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='jas.receiptDrug.add'/>" onclick="ACT.go('receiptsToGeneralStore.form');"/>
<br /><br />

<form method="get"  id="receiptList">
<table >
	<tr>
		<td><spring:message code="jas.receiptDrug.description"/></td>
		<td>
			<input type="text" name="receiptName" id="receiptName" value="${receiptName }"/>
		</td>
		<td><spring:message code="jas.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><spring:message code="jas.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<br />
<span class="boxHeader"><spring:message code="jas.receiptDrug.receiptDrugList"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
		<th>#</th>
		<th><spring:message code="jas.receiptDrug.description"/></th>
		<th><spring:message code="jas.receiptDrug.createdOn"/></th>
		<th></th>
	</tr>
	<c:choose>
	<c:when test="${not empty transactions}">
	<c:forEach items="${transactions}" var="receipt" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td>${receipt.description}</td>	
		<td><openmrs:formatDate date="${receipt.createdOn}" type="textbox"/></td>
		<td><a href="#" title="Detail indent" onclick="RECEIPT.detailReceiptDrug('${ receipt.id}');">Detail</a>|<a href="#" title="Print indent" onclick="RECEIPT.printReceiptDrug('${ receipt.id}');">Print</a></td>
    </tr>
	</c:forEach>
	</c:when>
	</c:choose>
<tr class="paging-container">
	<td colspan="4"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>
<div id="mycontent" style="display:none;">

</div>



<%@ include file="/WEB-INF/template/footer.jsp" %>