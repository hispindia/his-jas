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

import org.openmrs.module.jas.model.JASStoreItemAccount;
import org.openmrs.module.jas.model.JASStoreItemTransactionDetail;

public class JASStoreItemAccountDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private JASStoreItemAccount itemAccount;
	
	private Integer quantity;
	
	private JASStoreItemTransactionDetail transactionDetail;
	
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
	
	public JASStoreItemAccount getItemAccount() {
		return itemAccount;
	}
	
	public void setItemAccount(JASStoreItemAccount itemAccount) {
		this.itemAccount = itemAccount;
	}
	
	public JASStoreItemTransactionDetail getTransactionDetail() {
		return transactionDetail;
	}
	
	public void setTransactionDetail(JASStoreItemTransactionDetail transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
	
}
