<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<form method="post" id="patientNameForm" action="processIssueDrug.form">
<input type="hidden" name="action" value="0"/>
<table class="box" width="100%">
	<tr>
		<td>PATIENT's NAME</td>
		<td><input type="text" name="patientName" id="patientName" size="35"/></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2"><input class="ui-button ui-widget ui-state-default ui-corner-all" type="button" value="<spring:message code="general.save"/>" onclick="ISSUE.processSlip('0');"></td>
	</tr>
</table>
</form>