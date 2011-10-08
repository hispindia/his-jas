/**
 *  Copyright 2010 Health Information Systems Project of India
 *
 *  This file is part of Jas module.
 *
 *  Jas module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Jas module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jas module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.jas.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class JASStoreDrugIssueDetail implements  Serializable {
		 private static final long serialVersionUID = 1L;
		 private Integer id;
		 private JASStoreDrugIssue storeDrugIssue;
		 private Integer quantity;
		 private JASStoreDrugTransactionDetail transactionDetail;
		 private BigDecimal unitPrice;
		 private BigDecimal totalPrice;
		 private BigDecimal rate;
		 private BigDecimal VAT;
		 private BigDecimal otherTaxes = new BigDecimal(0);
		 
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		
		public JASStoreDrugIssue getStoreDrugIssue() {
			return storeDrugIssue;
		}
		public void setStoreDrugIssue(JASStoreDrugIssue storeDrugIssue) {
			this.storeDrugIssue = storeDrugIssue;
		}
		public JASStoreDrugTransactionDetail getTransactionDetail() {
			return transactionDetail;
		}
		public void setTransactionDetail(
				JASStoreDrugTransactionDetail transactionDetail) {
			this.transactionDetail = transactionDetail;
		}
		public BigDecimal getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}
		public BigDecimal getTotalPrice() {
			totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_UP);
			return totalPrice;
		}
		public void setTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
		}
		public BigDecimal getVAT() {
			return VAT;
		}
		public void setVAT(BigDecimal vAT) {
			VAT = vAT;
		}
		public BigDecimal getRate() {
			rate = unitPrice.add(unitPrice.multiply(VAT.add(otherTaxes)).divide(new BigDecimal(100)));
			rate = rate.setScale(2, BigDecimal.ROUND_UP);
			return rate;
		}
		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}
		public BigDecimal getOtherTaxes() {
			return otherTaxes;
		}
		public void setOtherTaxes(BigDecimal otherTaxes) {
			this.otherTaxes = otherTaxes;
		}
		
		/* public static void main(String[] argv) throws Exception {
			    int decimalPlaces = 2;
			    BigDecimal bd = new BigDecimal("1.345");
			     
			   bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_UP);
			    //bd =  bd.round(new MathContext(3));
			    String string = bd.toString();
			    System.out.println(bd);
			  } 
		 */
}
