package com.iss.itreasury.project.gzbfcl.settlement.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class QueryFoundsdispatchDetailInfo extends ITreasuryBaseDataEntity{

		private long Id=-1;//资金调度令明细ID
		private long foundsDispatchID=-1;//资金调度令 ID
		private String foundsDispatchCode="";//资金调度令编号
		private String foundsDispatchReceive="";//资金调度令接收方
		private long fromOrganizationID=-1;//源单位ID
		private String fromOrganizationName="";//源单位名称
		private long fromBankAccountID=-1;//源开户行账户号ID
		private String fromBankAccountNo="";//源开户行账户号
		private String fromBankName="";//源开户行名称
		
		private long toOrganizationID=-1;//目的单位ID
		private String toOrganizationName="";//目的单位ID
		private long toBankAccountID=-1;//目的开户行账号ID
		private String toBankAccountNo="";//目的开户行账号
		private String toBankName="";//目的开户行名称
		
		private double amount=0.0;//金额
		private String foundsAplication="";//资金用途
		
		private long inputUserID=-1;//录入人ID
		private String inputUserName="";//录入人名称
		private Timestamp inputTime=null;//录入时间
		private long officeID=-1;//办事处ID
		private String officeName="";//办事处名称
		private long currentID=-1;//币种ID
		private long modifyUserID=-1;//修改人ID
		private String modifyUserName="";//修改人名称
		private Timestamp modifyTime=null;//修改时间
		private long statusID=-1;//状态
		/**
		 * @return the id
		 */
		public long getId() {
			return Id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(long id) {
			Id = id;
			putUsedField("id", id);
		}
		/**
		 * @return the foundsDispatchID
		 */
		public long getFoundsDispatchID() {
			return foundsDispatchID;
		}
		/**
		 * @param foundsDispatchID the foundsDispatchID to set
		 */
		public void setFoundsDispatchID(long foundsDispatchID) {
			this.foundsDispatchID = foundsDispatchID;
			putUsedField("foundsDispatchID", foundsDispatchID);
		}
		/**
		 * @return the foundsDispatchCode
		 */
		public String getFoundsDispatchCode() {
			return foundsDispatchCode;
		}
		/**
		 * @param foundsDispatchCode the foundsDispatchCode to set
		 */
		public void setFoundsDispatchCode(String foundsDispatchCode) {
			this.foundsDispatchCode = foundsDispatchCode;
			putUsedField("foundsDispatchCode", foundsDispatchCode);
		}
		/**
		 * @return the foundsDispatchReceive
		 */
		public String getFoundsDispatchReceive() {
			return foundsDispatchReceive;
		}
		/**
		 * @param foundsDispatchReceive the foundsDispatchReceive to set
		 */
		public void setFoundsDispatchReceive(String foundsDispatchReceive) {
			this.foundsDispatchReceive = foundsDispatchReceive;
			putUsedField("foundsDispatchReceive", foundsDispatchReceive);
		}
		/**
		 * @return the fromOrganizationID
		 */
		public long getFromOrganizationID() {
			return fromOrganizationID;
		}
		/**
		 * @param fromOrganizationID the fromOrganizationID to set
		 */
		public void setFromOrganizationID(long fromOrganizationID) {
			this.fromOrganizationID = fromOrganizationID;
			putUsedField("fromOrganizationID", fromOrganizationID);
		}
		/**
		 * @return the fromOrganizationName
		 */
		public String getFromOrganizationName() {
			return fromOrganizationName;
		}
		/**
		 * @param fromOrganizationName the fromOrganizationName to set
		 */
		public void setFromOrganizationName(String fromOrganizationName) {
			this.fromOrganizationName = fromOrganizationName;
			putUsedField("fromOrganizationName", fromOrganizationName);
		}
		/**
		 * @return the fromBankAccountID
		 */
		public long getFromBankAccountID() {
			return fromBankAccountID;
		}
		/**
		 * @param fromBankAccountID the fromBankAccountID to set
		 */
		public void setFromBankAccountID(long fromBankAccountID) {
			this.fromBankAccountID = fromBankAccountID;
			putUsedField("fromBankAccountID", fromBankAccountID);
		}
		/**
		 * @return the fromBankAccountNo
		 */
		public String getFromBankAccountNo() {
			return fromBankAccountNo;
		}
		/**
		 * @param fromBankAccountNo the fromBankAccountNo to set
		 */
		public void setFromBankAccountNo(String fromBankAccountNo) {
			this.fromBankAccountNo = fromBankAccountNo;
			putUsedField("fromBankAccountNo", fromBankAccountNo);
		}
		/**
		 * @return the fromBankName
		 */
		public String getFromBankName() {
			return fromBankName;
		}
		/**
		 * @param fromBankName the fromBankName to set
		 */
		public void setFromBankName(String fromBankName) {
			this.fromBankName = fromBankName;
			putUsedField("fromBankName", fromBankName);
		}
		/**
		 * @return the toOrganizationID
		 */
		public long getToOrganizationID() {
			return toOrganizationID;
		}
		/**
		 * @param toOrganizationID the toOrganizationID to set
		 */
		public void setToOrganizationID(long toOrganizationID) {
			this.toOrganizationID = toOrganizationID;
			putUsedField("toOrganizationID", toOrganizationID);
		}
		/**
		 * @return the toOrganizationName
		 */
		public String getToOrganizationName() {
			return toOrganizationName;
		}
		/**
		 * @param toOrganizationName the toOrganizationName to set
		 */
		public void setToOrganizationName(String toOrganizationName) {
			this.toOrganizationName = toOrganizationName;
			putUsedField("toOrganizationName", toOrganizationName);
		}
		/**
		 * @return the toBankAccountID
		 */
		public long getToBankAccountID() {
			return toBankAccountID;
		}
		/**
		 * @param toBankAccountID the toBankAccountID to set
		 */
		public void setToBankAccountID(long toBankAccountID) {
			this.toBankAccountID = toBankAccountID;
			putUsedField("toBankAccountID", toBankAccountID);
		}
		/**
		 * @return the toBankAccountNo
		 */
		public String getToBankAccountNo() {
			return toBankAccountNo;
		}
		/**
		 * @param toBankAccountNo the toBankAccountNo to set
		 */
		public void setToBankAccountNo(String toBankAccountNo) {
			this.toBankAccountNo = toBankAccountNo;
			putUsedField("toBankAccountNo", toBankAccountNo);
		}
		/**
		 * @return the toBankName
		 */
		public String getToBankName() {
			return toBankName;
		}
		/**
		 * @param toBankName the toBankName to set
		 */
		public void setToBankName(String toBankName) {
			this.toBankName = toBankName;
			putUsedField("toBankName", toBankName);
		}
		/**
		 * @return the amount
		 */
		public double getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(double amount) {
			this.amount = amount;
			putUsedField("amount", amount);
		}
		/**
		 * @return the foundsAplication
		 */
		public String getFoundsAplication() {
			return foundsAplication;
		}
		/**
		 * @param foundsAplication the foundsAplication to set
		 */
		public void setFoundsAplication(String foundsAplication) {
			this.foundsAplication = foundsAplication;
			putUsedField("foundsAplication", foundsAplication);
		}
		/**
		 * @return the inputUserID
		 */
		public long getInputUserID() {
			return inputUserID;
		}
		/**
		 * @param inputUserID the inputUserID to set
		 */
		public void setInputUserID(long inputUserID) {
			this.inputUserID = inputUserID;
			putUsedField("inputUserID", inputUserID);
		}
		/**
		 * @return the inputUserName
		 */
		public String getInputUserName() {
			return inputUserName;
		}
		/**
		 * @param inputUserName the inputUserName to set
		 */
		public void setInputUserName(String inputUserName) {
			this.inputUserName = inputUserName;
			putUsedField("inputUserName", inputUserName);
		}
		/**
		 * @return the inputTime
		 */
		public Timestamp getInputTime() {
			return inputTime;
		}
		/**
		 * @param inputTime the inputTime to set
		 */
		public void setInputTime(Timestamp inputTime) {
			this.inputTime = inputTime;
			putUsedField("inputTime", inputTime);
		}
		/**
		 * @return the officeID
		 */
		public long getOfficeID() {
			return officeID;
		}
		/**
		 * @param officeID the officeID to set
		 */
		public void setOfficeID(long officeID) {
			this.officeID = officeID;
			putUsedField("officeID", officeID);
		}
		/**
		 * @return the officeName
		 */
		public String getOfficeName() {
			return officeName;
		}
		/**
		 * @param officeName the officeName to set
		 */
		public void setOfficeName(String officeName) {
			this.officeName = officeName;
			putUsedField("officeName", officeName);
		}
		/**
		 * @return the currentID
		 */
		public long getCurrentID() {
			return currentID;
		}
		/**
		 * @param currentID the currentID to set
		 */
		public void setCurrentID(long currentID) {
			this.currentID = currentID;
			putUsedField("currentID", currentID);
		}
		/**
		 * @return the modifyUserID
		 */
		public long getModifyUserID() {
			return modifyUserID;
		}
		/**
		 * @param modifyUserID the modifyUserID to set
		 */
		public void setModifyUserID(long modifyUserID) {
			this.modifyUserID = modifyUserID;
			putUsedField("modifyUserID", modifyUserID);
		}
		/**
		 * @return the modifyUserName
		 */
		public String getModifyUserName() {
			return modifyUserName;
		}
		/**
		 * @param modifyUserName the modifyUserName to set
		 */
		public void setModifyUserName(String modifyUserName) {
			this.modifyUserName = modifyUserName;
			putUsedField("modifyUserName", modifyUserName);
		}
		/**
		 * @return the modifyTime
		 */
		public Timestamp getModifyTime() {
			return modifyTime;
		}
		/**
		 * @param modifyTime the modifyTime to set
		 */
		public void setModifyTime(Timestamp modifyTime) {
			this.modifyTime = modifyTime;
			putUsedField("modifyTime", modifyTime);
		}
		/**
		 * @return the statusID
		 */
		public long getStatusID() {
			return statusID;
		}
		/**
		 * @param statusID the statusID to set
		 */
		public void setStatusID(long statusID) {
			this.statusID = statusID;
			putUsedField("statusID", statusID);
		}
	
		
}
