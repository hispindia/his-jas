<%@ include file="/WEB-INF/template/include.jsp" %>
<c:choose>
<c:when test="${not empty drugs}">
<c:forEach items="${drugs}" var="drug" varStatus="loop">
${drug.name}
</c:forEach>
</c:when>
</c:choose>