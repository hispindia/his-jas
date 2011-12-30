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


public class JASStoreDrugIssueDetailExt  implements  Serializable {
	   private static final long serialVersionUID = 1L;
		private Integer tempReturnQuantity ;
		private JASStoreDrugIssueDetail drugIssueDetail;
		public Integer getTempReturnQuantity() {
			return tempReturnQuantity;
		}

		public void setTempReturnQuantity(Integer tempReturnQuantity) {
			this.tempReturnQuantity = tempReturnQuantity;
		}

		public JASStoreDrugIssueDetail getDrugIssueDetail() {
			return drugIssueDetail;
		}

		public void setDrugIssueDetail(JASStoreDrugIssueDetail drugIssueDetail) {
			this.drugIssueDetail = drugIssueDetail;
		}
		
		
		 
		 
}
