package com.iss.itreasury.settlement.integration.client.info;
import java.util.Date;

public class AccountTransInfo implements java.io.Serializable {
				
		private double amount	                = Double.NaN;//���׽��	Amount
		private long direction	                = -1;//�������	Type
		
		private String oppAccountNO	        = null;//�Է��˻���	Code
		private String oppAccountName	        = null;//�Է��˻�����	String
		private String oppBranchName	        = null;//�Է�����������	String
		private String oppAddress	        = null;//�Է��������ڵ�	
				
		private Date executeDate	        = null;//���׷�������	DateTime
		private Date interestStartDate	                = null;//��Ϣ��	Date
				
		private String sAbstract	        = null;//ժҪ��Ϣ	String				
		private String transNoOfBank	        = null;//���׵�����Ψһ��ʶ	Code
				
		//��ȷ��
		private String checkNO	                = null;//ƾ֤��	String
		private String checkType	        = null;//ƾ֤����	String
		private String transactionType	        = null;//��������	String	

		private long typeId	        = -1; //����[����������ҵ���ǹ鼯ҵ��:1--����ҵ��;2--�鼯ҵ��]

		public String getAbstract() {
			return sAbstract;
		}
		public void setAbstract(String sAbstract) {
			this.sAbstract = sAbstract;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public String getCheckNO() {
			return checkNO;
		}
		public void setCheckNO(String checkNO) {
			this.checkNO = checkNO;
		}
		public String getCheckType() {
			return checkType;
		}
		public void setCheckType(String checkType) {
			this.checkType = checkType;
		}
		public long getDirection() {
			return direction;
		}
		public void setDirection(long direction) {
			this.direction = direction;
		}
		public Date getExecuteDate() {
			return executeDate;
		}
		public void setExecuteDate(Date executeDate) {
			this.executeDate = executeDate;
		}
		public Date getInterestStartDate() {
			return interestStartDate;
		}
		public void setInterestStartDate(Date interestStartDate) {
			this.interestStartDate = interestStartDate;
		}
		public String getOppAccountName() {
			return oppAccountName;
		}
		public void setOppAccountName(String oppAccountName) {
			this.oppAccountName = oppAccountName;
		}
		public String getOppAccountNO() {
			return oppAccountNO;
		}
		public void setOppAccountNO(String oppAccountNO) {
			this.oppAccountNO = oppAccountNO;
		}
		public String getOppAddress() {
			return oppAddress;
		}
		public void setOppAddress(String oppAddress) {
			this.oppAddress = oppAddress;
		}
		public String getOppBranchName() {
			return oppBranchName;
		}
		public void setOppBranchName(String oppBranchName) {
			this.oppBranchName = oppBranchName;
		}
		public String getTransactionType() {
			return transactionType;
		}
		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}
		public String getTransNoOfBank() {
			return transNoOfBank;
		}
		public void setTransNoOfBank(String transNoOfBank) {
			this.transNoOfBank = transNoOfBank;
		}
		public long getTypeId() {
			return typeId;
		}
		public void setTypeId(long typeId) {
			this.typeId = typeId;
		}
		

}
