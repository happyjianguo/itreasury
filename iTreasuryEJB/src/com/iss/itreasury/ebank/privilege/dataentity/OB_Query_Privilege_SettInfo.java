package com.iss.itreasury.ebank.privilege.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * 
 * @author xuanzhang
 *
 */
public class OB_Query_Privilege_SettInfo  extends ITreasuryBaseDataEntity{
	    private long Id;
	    private long parentClientID;
	    private long childClientID;
	    private long statusID;
	    private Timestamp inputdate;
	    private Timestamp modifydate;
	    private long inputUserID;
	    private long modifyUserID;
	    public OB_Query_Privilege_SettInfo(){
	    	 Id = -1L;
	    	 parentClientID = -1L;
	    	 childClientID = -1L;
	    	 statusID = -1L;
	    	 inputdate=null;
	    	 modifydate=null;
	    	 inputUserID = -1L;
	         modifyUserID = -1L;
	        
	    }
		public long getChildClientID() {
			return childClientID;
		}
		public void setChildClientID(long childClientID) {
			this.childClientID = childClientID;
			putUsedField("childClientID", childClientID);
		}
		public long getId() {
			return Id;
		}
		public void setId(long id) {
			Id = id;
			putUsedField("Id", id);
		}
		public Timestamp getInputdate() {
			return inputdate;
		}
		public void setInputdate(Timestamp inputdate) {
			this.inputdate = inputdate;
			putUsedField("inputdate", inputdate);
		}
		public long getInputUserID() {
			return inputUserID;
		}
		public void setInputUserID(long inputUserID) {
			this.inputUserID = inputUserID;
			putUsedField("inputUserID", inputUserID);
		}
		public Timestamp getModifydate() {
			return modifydate;
		}
		public void setModifydate(Timestamp modifydate) {
			this.modifydate = modifydate;
			putUsedField("modifydate", modifydate);
		}
		public long getModifyUserID() {
			return modifyUserID;
		}
		public void setModifyUserID(long modifyUserID) {
			this.modifyUserID = modifyUserID;
			putUsedField("modifyUserID", modifyUserID);
		}
		public long getParentClientID() {
			return parentClientID;
		}
		public void setParentClientID(long parentClientID) {
			this.parentClientID = parentClientID;
			putUsedField("parentClientID", parentClientID);
		}
		public long getStatusID() {
			return statusID;
		}
		public void setStatusID(long statusID) {
			this.statusID = statusID;
			putUsedField("statusID", statusID);
		}

}
