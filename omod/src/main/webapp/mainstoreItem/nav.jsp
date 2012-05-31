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
<%@ include file="../includes/js_css.jsp" %>
<br/>
<b><a href="#" onclick="ACT.go('viewStockBalance.form');">Drug</a>&nbsp;| Item </b>
<br/><br/>
<b><a href="#" onclick="ACT.go('itemViewStockBalance.form');"><spring:message code="jas.viewStockBalance"/></a></b>&nbsp;|
<!-- 
<b><a href="#" onclick="ACT.go('itemPurchaseOrderForGeneralStoreList.form');"><spring:message code="jas.mainStore.purchaseOrderForGeneralStore"/></a></b>&nbsp;|
--> 
<b><a href="#" onclick="ACT.go('itemReceiptsToGeneralStoreList.form');"><spring:message code="jas.mainStore.receiptsToGeneralStore"/></a></b>&nbsp;|
<b><a href="#" onclick="ACT.go('transferItemFromGeneralStore.form');"><spring:message code="jas.mainStore.transferFromGeneralStore"/></a></b>
<br/><br/>

