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
<%@ include file="nav.jsp" %>
<h2><spring:message code="jas.issueDrug.manage"/></h2>	
<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='jas.issueDrug.add'/>" onclick="ACT.go('issueDrugForm.form');"/>
<br /><br />

<form method="get"  id="form">
<table >
	<tr>
		<td>Name|Bill No</td>
		<td>
			<input type="text" name="issueName" id="issueName" value="${issueName }"/>
		</td>
		<td><spring:message code="jas.fromDate"/></td>
		<td><input type="text" id="fromDate" class="date-pick left" readonly="readonly" name="fromDate" value="${fromDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><spring:message code="jas.toDate"/></td>
		<td><input type="text" id="toDate" class="date-pick left" readonly="readonly" name="toDate" value="${toDate}" title="Double Click to Clear" ondblclick="this.value='';"/></td>
		<td><input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"/></td>
	</tr>
</table>
<br />
<span class="boxHeader"><spring:message code="jas.issueDrug.list"/></span>
<div class="box">
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>#</th>
	<th>Patient's name</th>
	<th>Bill no</th>
	<th>Total bill</th>
	<th><spring:message code="jas.issueDrug.createdOn"/></th>
	<th></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listIssue}">
	<c:forEach items="${listIssue}" var="issue" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td >${issue.name}</td>
		<td>${issue.billNumber}</td>
        <td>${issue.total}</td>
		<td><openmrs:formatDate date="${issue.createdOn}" type="textbox"/></td>
		<td><a href="#" title="Detail issue" onclick="ISSUE.detailIssueDrug('${issue.id}');">Detail</a>|<a href="#" title="Print issue" onclick="ISSUE.printIssueDrug('${issue.id}');">Print</a></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
	
	<tr class="paging-container">
	<td colspan="6"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</div>

</form>
<div id="mycontent" style="display:none;">

</div>



<%@ include file="/WEB-INF/template/footer.jsp" %>