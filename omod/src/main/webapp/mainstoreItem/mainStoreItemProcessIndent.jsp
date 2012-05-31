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

<openmrs:require privilege="Add/Edit mainstore" otherwise="/login.htm" redirect="/module/jas/main.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<h2><spring:message code="jas.indentItem.process"/></h2>
<form method="post" class="box" id="formMainStoreProcessIndent">
<input type="hidden" name="indentId" id="indentId"  value="${indent.id}">
<c:forEach items="${errors}" var="error">
	<span class="error"><spring:message code="${error}" /></span><br/>
</c:forEach>
<div class="box">
<span class="boxHeader"><spring:message code="jas.indent"/></span>
<table>
<tr>
	<td><spring:message code="jas.indentItem.name"/></td>
	<td><input type="text" disabled="disabled"  value="${indent.name}" size="50"></td>

</tr>
<tr>
	<td><spring:message code="jas.indentItem.fromStore"/></td>
	<td><input type="text" disabled="disabled"  value="${indent.store.name}" size="50"></td>

</tr>
<tr>
	<td><spring:message code="jas.indentItem.createdOn"/></td>
	<td><input type="text" disabled="disabled"  value="<openmrs:formatDate date="${indent.createdOn}" type="textbox"/>"> </td>

</tr>
</table>
</div>
<br/>
<div class="box">
<span class="boxHeader"><spring:message code="jas.indentItem.processingIndent"/></span>
<table  width="100%" id="tableIndent">
	<tr align="center">
		<th >#</th>
		<th ><spring:message code="jas.indentItem.item"/></th>
		<th  ><spring:message code="jas.indentItem.specification"/></th>
		<th  ><spring:message code="jas.indentItem.quantityIndent"/></th>
		<th  ><spring:message code="jas.indentItem.transferQuantity"/></th>
		<th  ><spring:message code="jas.indentItem.mainStoreQuantity"/></th>
	</tr>
	
	<c:forEach items="${listItemNeedProcess}" var="itemIndent" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow " : "evenRow" } '>
	<c:choose>
	<c:when test="${not empty itemIndent.mainStoreTransfer && itemIndent.mainStoreTransfer > 0 }">
		<td><c:out value="${varStatus.count }"/></td>
		<td >${itemIndent.item.name} </td>
		<td >${itemIndent.specification.name} </td>
		<td >
			${itemIndent.quantity}
		</td>
		<td >
		<p>
		<em>*</em>
		<input type="text" id="${itemIndent.id}" name="${itemIndent.id}" size="15" value="${quantityTransfers[varStatus.index] }"  class="required digits" onblur="INDENT.onBlurInput(this,'${itemIndent.quantity}','${itemIndent.mainStoreTransfer}');"/>
		</p>
		</td>
		<td >
			${itemIndent.mainStoreTransfer} 
		</td>
	</c:when>
	<c:otherwise>
		<td><c:out value="${varStatus.count }"/></td>
		<td ><del>${itemIndent.item.name}</del></td>
		<td ><del>${itemIndent.specification.name} </del></td>
		<td >
			<del>${itemIndent.quantity}</del>
		</td>
		<td >
		<p>
		<em>*</em>
		<input type="text" id="${itemIndent.id}" disabled="disabled" name="${itemIndent.id}" size="15" value="${quantityTransfers[varStatus.index] }"  class="required digits" />
		</p>
		</td>
		<td >
			${itemIndent.mainStoreTransfer} 
		</td>
	</c:otherwise>
	</c:choose>
		
	</tr>
	</c:forEach>
</table>
</div>		
		
<br />		
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.indentItem.accept"/>">
<input type="hidden" id="refuse" name="refuse" value="">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.indentItem.refuse"/>" onclick="INDENT.refuseIndentFromMainStore(this);">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.returnList"/>" onclick="ACT.go('transferItemFromGeneralStore.form');">
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
