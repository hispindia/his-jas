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

<div class="box">
<span class="boxHeader"><spring:message code="jas.indent.detail"/></span>
<table width="100%" cellpadding="5" cellspacing="0">
	<tr align="center">
	<th>#</th>
	<th><spring:message code="jas.indent.category"/></th>
	<th><spring:message code="jas.indent.name"/></th>
	<th><spring:message code="jas.indent.formulation"/></th>
	<th><spring:message code="jas.indent.quantity"/></th>
	<th><spring:message code="jas.indent.transferQuantity"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listIndentDetail}">
	<c:forEach items="${listIndentDetail}" var="indent" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${ varStatus.count}"/></td>
		<td>${indent.drug.category.name} </td>	
		<td>${indent.drug.name}</td>
		<td>${indent.formulation.name}-${indent.formulation.dozage}</td>
		<td>${indent.quantity}</td>
		<td>${indent.mainStoreTransfer}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>	
</table>
</div>
<input type="button" value="<spring:message code="jas.indent.print"/>" onClick="INDENT.printDiv();" />

<!-- PRINT DIV -->
<div  id="printDiv" style="display: none; margin: 10px auto; width: 981px; font-size: 1.5em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">        		
<img src="${pageContext.request.contextPath}/moduleResources/jas/HEADEROPDSLIP.jpg" width="981" height="170"></img>
<center>
	<h1>Indent by sub-store ${store.name}</h1>

<br />
<table border="1">
	<tr>
	<th>#</th>
	<th><spring:message code="jas.drug.category"/></th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.indent.quantity"/></th>
	<th><spring:message code="jas.indent.transferQuantity"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listIndentDetail}">
	<c:forEach items="${listIndentDetail}" var="indent" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${varStatus.count }"/></td>
		<td>${indent.drug.category.name} </td>	
		<td>${indent.drug.name}</td>
		<td>${indent.formulation.name}-${indent.formulation.dozage}</td>
		<td>${indent.quantity}</td>
		<td>${indent.mainStoreTransfer}</td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
</center>
<br/><br/><br/><br/><br/><br/>
<span style="float:left;font-size: 1.5em">Signature of sub-store/ Stamp</span><span style="float:right;font-size: 1.5em">Signature of jas clerk/ Stamp</span>
<br/><br/><br/><br/><br/><br/>
<span style="margin-left: 18em;font-size: 1.5em">Signature of main-store/ Stamp</span>
</div>
<!-- END PRINT DIV -->   