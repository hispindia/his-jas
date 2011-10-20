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

<openmrs:require privilege="manage JAS" otherwise="/login.htm" redirect="/module/jas/drugList.form" />

<spring:message var="pageTitle" code="jas.drug.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="nav.jsp" %>
<h2><spring:message code="jas.drug.manage"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='jas.drug.add'/>" onclick="ACT.go('drug.form');"/>

<br /><br />
<form method="post" onsubmit="return false" id="form">
<table cellpadding="5" cellspacing="0">
	<tr>
		<td><spring:message code="jas.drug.category"/></td>
		<td>
			<select name="categoryId" id="categoryId"   style="width: 250px;">
				<option value=""></option>
                <c:forEach items="${categories}" var="vCat">
                    <option value="${vCat.id}" title="${vCat.name}" <c:if test="${vCat.id == categoryId }">selected</c:if> >${vCat.nameShort}</option>
                </c:forEach>
   			</select>
		</td>
		<td><spring:message code="general.name"/></td>
		<td><input type="text" id="searchName" name="searchName" value="${searchName}" /></td>
		<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="DRUG.searchDrug(this);"/></td>
	</tr>
</table>

<span class="boxHeader"><spring:message code="jas.drug.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty drugs}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="JAS.checkValue();" value="<spring:message code='jas.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>#</th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.drug"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.drug.unit"/></th>
	<th><spring:message code="jas.drug.category"/></th>
	<th><spring:message code="jas.drug.attribute"/></th>
	<th><spring:message code="jas.drug.createdDate"/></th>
	<th><spring:message code="jas.drug.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${drugs}" var="drug" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td title="${drug.name}"><a href="#" onclick="ACT.go('drug.form?drugId=${ drug.id}');">${drug.nameShort}</a> </td>
		<td>${drug.drugCore.name}</td>
		<td>
			<c:forEach items="${drug.formulations}" var="formulation" varStatus="status">
				${formulation.name}-${formulation.dozage}<br/>
			</c:forEach>
		</td>
		<td>${drug.unit.name}</td>
		<td title="${drug.category.name}">${drug.category.nameShort}</td>
		<td>${drug.attributeName}</td>
		<td><openmrs:formatDate date="${drug.createdOn}" type="textbox"/></td>
		<td>${drug.createdBy}</td>
		<td><input type="checkbox" name="ids" value="${drug.id}"/></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="11"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</c:when>
<c:otherwise>
	No drug found.
</c:otherwise>
</c:choose>
</div>
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>