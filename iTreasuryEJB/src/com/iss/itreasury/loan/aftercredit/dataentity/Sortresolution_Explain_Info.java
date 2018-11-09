package com.iss.itreasury.loan.aftercredit.dataentity;

import java.sql.Timestamp;

public class Sortresolution_Explain_Info {
	    private long id ;
		private String explain;
		private long sortaftersolution;
		//
		private Timestamp inputdate =null;
		private long sortcode ;
		//2008-9-18
		private long fivesorttype = -1;
		private String checkyear = "";
		private long checkqm = -1;
		private Timestamp checktemp = null;
		
		
		
		public long getCheckqm() {
			return checkqm;
		}
		public void setCheckqm(long checkqm) {
			this.checkqm = checkqm;
		}
		public Timestamp getChecktemp() {
			return checktemp;
		}
		public void setChecktemp(Timestamp checktemp) {
			this.checktemp = checktemp;
		}
		public String getCheckyear() {
			return checkyear;
		}
		public void setCheckyear(String checkyear) {
			this.checkyear = checkyear;
		}
		public long getFivesorttype() {
			return fivesorttype;
		}
		public void setFivesorttype(long fivesorttype) {
			this.fivesorttype = fivesorttype;
		}
		public long getSortcode() {
			return sortcode;
		}
		public void setSortcode(long sortcode) {
			this.sortcode = sortcode;
		}
		public String getExplain() {
			return explain;
		}
		public void setExplain(String explain) {
			this.explain = explain;
		}
		public long getSortaftersolution() {
			return sortaftersolution;
		}
		public void setSortaftersolution(long sortaftersolution) {
			this.sortaftersolution = sortaftersolution;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public Timestamp getInputdate() {
			return inputdate;
		}
		public void setInputdate(Timestamp inputdate) {
			this.inputdate = inputdate;
		}
}
