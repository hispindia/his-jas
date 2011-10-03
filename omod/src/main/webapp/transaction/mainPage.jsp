<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="manage JAS" otherwise="/login.htm" redirect="/module/jas/main.form" />
<spring:message var="pageTitle" code="jas.mainStore.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="nav.jsp" %>


<%@ include file="/WEB-INF/template/footer.jsp" %>
