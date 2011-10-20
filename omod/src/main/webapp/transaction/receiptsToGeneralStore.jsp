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

<openmrs:require privilege="manage JAS" otherwise="/login.htm" redirect="/module/jas/main.form" />
<spring:message var="pageTitle" code="jas.receiptDrug.manage" scope="page"/>
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<div style="width: 26%; float: left; margin-left: 4px; ">
<b class="boxHeader">Drug</b>
<div class="box">
<form method="post" id="receiptDrug">
<c:if  test="${not empty errors}">
<c:forEach items="${errors}" var="error">
	<span class="error"><spring:message code="${error}" /></span>
</c:forEach>
</c:if>
<br/>
<table width="100%">
<tr><td><b>Drug info</b></td></tr>
	<tr>
		<td>Drug<em>*</em></td>
		<td>
			<input id="drugName" name="drugName" onblur="RECEIPT.onBlur(this);" style="width: 200px;">
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.drug.formulation"/><em>*</em></td>
		<td>
			<div id="divFormulation"  >
				<select id="formulation"  name="formulation">
					<option value=""><spring:message code="jas.pleaseSelect"/></option>
				</select>
			</div>
		</td>
	</tr>
</table>
<br/>
<table class="box">
	<tr>
		<td><spring:message code="jas.receiptDrug.quantiry"/><em>*</em></td>
		<td>
			<input type="text" id="quantity" name="quantity" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.unitPrice"/><em>*</em></td>
		<td>
			<input type="text" id="unitPrice" name="unitPrice" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.VAT"/><em>*</em></td>
		<td>
			<input type="text" id="VAT" name="VAT" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.otherTaxes"/><em>*</em></td>
		<td>
			<input type="text" id="otherTaxes" name="otherTaxes" onblur="RECEIPT.checkVAT(this);"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.batchNo"/><em>*</em></td>
		<td>
			<input type="text" id="batchNo" name="batchNo" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.companyName"/><em>*</em></td>
		<td>
			<input type="text" id="companyName" name="companyName" />
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.dateManufacture"/><em>*</em></td>
		<td>
			<input type="text" id="dateManufacture" name="dateManufacture" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.dateExpiry"/><em>*</em></td>
		<td>
			<input type="text" id="dateExpiry" name="dateExpiry" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
	<tr>
		<td><spring:message code="jas.receiptDrug.receiptDate"/><em>*</em></td>
		<td>
			<input type="text" id="receiptDate" name="receiptDate" class="date-pick left" readonly="readonly"  ondblclick="this.value='';"/>
		</td>
	</tr>
</table>
<br/>
<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.receiptDrug.addToSlip"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.back"/>" onclick="ACT.go('receiptsToGeneralStoreList.form');">
</form>
</div>
</div>
<!-- Receipt list -->
<div style="width: 73%; float: right; margin-right: 4px; ">
<b class="boxHeader">Receipt slip</b>
<div class="box">
<table class="box" width="100%" cellpadding="5" cellspacing="0">
	<tr>
	<th>#</th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.receiptDrug.quantity"/></th>
	<th><spring:message code="jas.receiptDrug.unitPrice"/></th>
	<th><spring:message code="jas.receiptDrug.VAT"/></th>
	<th><spring:message code="jas.receiptDrug.otherTaxes"/></th>
	<th><spring:message code="jas.receiptDrug.totalPrice"/></th>
	<th><spring:message code="jas.receiptDrug.batchNo"/></th>
	<th title="<spring:message code="jas.receiptDrug.companyName"/>">CN</th>
	<th title="<spring:message code="jas.receiptDrug.dateManufacture"/>">DM</th>
	<th title="<spring:message code="jas.receiptDrug.dateExpiry"/>">DE</th>
	<th title="<spring:message code="jas.receiptDrug.receiptDate"/>">RD</th>
	</tr>
	<c:choose>
	<c:when test="${not empty listReceipt}">
	<c:forEach items="${listReceipt}" var="receipt" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td><a href="#" title="Remove this" onclick="JAS.removeObject('${varStatus.index}','7');">${receipt.drug.name}</a></td>
		<td>${receipt.formulation.name}-${receipt.formulation.dozage}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.otherTaxes}</td>
		<td>${receipt.totalPrice}</td>
		<td>${receipt.batchNo}</td>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.dateExpiry}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		</tr>
	</c:forEach>
	
	</c:when>
	</c:choose>
</table>
<br/>
	<c:if  test="${not empty listReceipt}">
		<table class="box" width="100%" cellpadding="5" cellspacing="0">
		<tr>
			<td>
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all"  value="<spring:message code='jas.receiptDrug.finish'/>" id="addNameReceiptButton" />
				<div id="addNameReceipt">
						<table class="box" width="100%">
							<tr>
								<td>Description</td>
								<td><input type="text" name="description" id="description" size="35"/></td>
								
							</tr>
			
						</table>
				</div>	
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.receiptDrug.clear"/>"  onclick="RECEIPT.receiptSlip('1');"/>
				<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="jas.receiptDrug.print"/>" onClick="JAS.printDiv();" />
			</td>
		</tr>
		</table>
	</c:if>
</div>
</div>
<!-- PRINT DIV -->
<div  id="printDiv" style="display: none; margin: 10px auto; width: 981px; font-size: 1.5em;font-family:'Dot Matrix Normal',Arial,Helvetica,sans-serif;">        		
<img src="${pageContext.request.contextPath}/moduleResources/jas/HEADEROPDSLIP.jpg" width="981" height="170"></img>
<table border="1">
	<tr>
	<th>#</th>
	<th><spring:message code="jas.drug.name"/></th>
	<th><spring:message code="jas.drug.formulation"/></th>
	<th><spring:message code="jas.receiptDrug.quantity"/></th>
	<th><spring:message code="jas.receiptDrug.unitPrice"/></th>
	<th><spring:message code="jas.receiptDrug.VAT"/></th>
	<th><spring:message code="jas.receiptDrug.totalPrice"/></th>
	<th><spring:message code="jas.receiptDrug.batchNo"/></th>
	<th><spring:message code="jas.receiptDrug.companyName"/></th>
	<th><spring:message code="jas.receiptDrug.dateManufacture"/></th>
	<th><spring:message code="jas.receiptDrug.dateExpiry"/></th>
	<th><spring:message code="jas.receiptDrug.receiptDate"/></th>
	</tr>
	<c:choose>
	<c:when test="${not empty listReceipt}">
	<c:forEach items="${listReceipt}" var="receipt" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>
		<td>${receipt.drug.name}</td>
		<td>${receipt.formulation.name}-${receipt.formulation.dozage}</td>
		<td>${receipt.quantity}</td>
		<td>${receipt.unitPrice}</td>
		<td>${receipt.VAT}</td>
		<td>${receipt.totalPrice}</td>
		<td>${receipt.batchNo}</td>
		<td>${receipt.companyName}</td>
		<td><openmrs:formatDate date="${receipt.dateManufacture}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.dateExpiry}" type="textbox"/></td>
		<td><openmrs:formatDate date="${receipt.receiptDate}" type="textbox"/></td>
		</tr>
	</c:forEach>
	</c:when>
	</c:choose>
</table>
<br/><br/><br/><br/><br/><br/>
<span style="float:right;font-size: 1.5em">Signature of jas clerk/ Stamp</span>
</div>
<!-- END PRINT DIV -->   

 
<%@ include file="/WEB-INF/template/footer.jsp" %>