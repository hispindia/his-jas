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
<c:if test="${not empty  specifications}">
<td><spring:message code="jas.item.specification"/><em>*</em></td>
<td>		
<select name="specification" id="specification"   style="width: 200px;">
	<option value=""><spring:message code="jas.pleaseSelect"/></option>
       <c:forEach items="${specifications}" var="specification">
           <option value="${specification.id}" <c:if test="${specification.id == specificationId }">selected</c:if> >${specification.name}</option>
       </c:forEach>
</select>
</td>
</c:if>
