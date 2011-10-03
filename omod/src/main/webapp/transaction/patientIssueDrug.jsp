<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/hospitalcore/scripts/advanceSearch.js"></script>
<div id="advSearch"></div>
<div id="patientResult"></div>


<script>
	ADVSEARCH.toggleAdvanceSearchBox('advSearch', 'patientResult', 'patientSearchJAS', '');
</script>