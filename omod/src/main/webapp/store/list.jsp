<%@ include file="/WEB-INF/template/include.jsp" %>


<openmrs:require privilege="manage JAS" otherwise="/login.htm" redirect="/module/jas/storeList.form" />

<spring:message var="pageTitle" code="jas.store.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="../includes/nav.jsp" %>

<h2><spring:message code="jas.store.manage"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='jas.store.add'/>" onclick="ACT.go('store.form');"/>

<br /><br />

<form method="post" onsubmit="return false" id="form">
<span class="boxHeader"><spring:message code="jas.store.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty stores}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="JAS.checkValue();" value="<spring:message code='jas.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>#</th>
	<th><spring:message code="general.name"/></th>
	<th><spring:message code="jas.store.code"/></th>
	<th><spring:message code="jas.store.role"/></th>
	<th><spring:message code="jas.store.retired"/></th>
	<th><spring:message code="jas.store.createdDate"/></th>
	<th><spring:message code="jas.store.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${stores}" var="store" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td><a href="#" onclick="STORE.edit('${ store.id}');">${store.name}</a> </td>
		<td>${store.code}</td>
		<td>${store.role.role}</td>
		<td>${store.retired }</td>
		<td><openmrs:formatDate date="${store.createdOn}" type="textbox"/></td>
		<td>${store.createdBy}</td>
		<td><input type="checkbox" name="ids" value="${store.id}"/></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="9"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
</c:when>
<c:otherwise>
	No Store found.
</c:otherwise>

</c:choose>
</div>
</form>





<%@ include file="/WEB-INF/template/footer.jsp" %>
