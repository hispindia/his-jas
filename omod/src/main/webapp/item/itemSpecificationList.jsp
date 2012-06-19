 <%--
 *  Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
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

<openmrs:require privilege="View itemSpecification" otherwise="/login.htm" redirect="/module/jas/itemSpecificationList.form" />

<spring:message var="pageTitle" code="jas.itemSpecification.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="nav.jsp" %>
<h2><spring:message code="jas.itemSpecification.manage"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='jas.itemSpecification.add'/>" onclick="ACT.go('itemSpecification.form');"/>

<br /><br />
<form method="post" onsubmit="return false" id="form">
<table cellpadding="5" cellspacing="0">
	<tr>
		<td><spring:message code="general.name"/></td>
		<td><input type="text" id="searchName" name="searchName" value="${searchName}" /></td>
		<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="JAS.search('itemSpecificationList.form','searchName');"/></td>
	</tr>
</table>

<span class="boxHeader"><spring:message code="jas.itemSpecification.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty itemSpecifications}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="JAS.checkValue();" value="<spring:message code='jas.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>#</th>
	<th><spring:message code="jas.itemSpecification.name"/></th>
	<th><spring:message code="jas.itemSpecification.description"/></th>
	<th><spring:message code="jas.itemSpecification.createdDate"/></th>
	<th><spring:message code="jas.itemSpecification.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${itemSpecifications}" var="itemSpecification" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td><a href="#" onclick="ACT.go('itemSpecification.form?itemSpecificationId=${ itemSpecification.id}');">${itemSpecification.name}</a> </td>
		<td>${itemSpecification.description}</td>
		<td><openmrs:formatDate date="${itemSpecification.createdOn}" type="textbox"/></td>
		<td>${itemSpecification.createdBy}</td>
		<td><input type="checkbox" name="ids" value="${itemSpecification.id}"/></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="6"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</c:when>
<c:otherwise>
	No itemSpecification found.
</c:otherwise>
</c:choose>
</div>
</form>


<%@ include file="/WEB-INF/template/footer.jsp" %>