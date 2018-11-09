/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transadjustinterest.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author feiye
 * 
 * �ۼƷ��ü���Ϣ�������ܲ�ѯ��--(������õĲ�ѯ���)
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AdjustInterestInfoQuery  extends SettlementBaseDataEntity{
	
	 	private long lAccountID=-1;			//�˻���
	 	private long lContractID=-1;		//��ͬ��
	 	private long lDueBillID=-1;			//�ſ�֪ͨ����
	 	private Timestamp tsDateStart=null;	//��ʼ����
	 	private Timestamp tsDateEnd=null;	//��������
	 	private long lStatusID=-1;			//��¼״̬ID -1:ȫ�� 0:ɾ�� 1:���� 2:����
	 	//
		private long lOrderParam=1;			//����ĸ��ֶ�����
		private String sDesc="asc";			//�������ǽ���
		
		private long lOfficeID=-1;					//���´�
	 	private long lCurrencyID=-1;				//����

	 	
	 	/**
		 * @return Returns the lOfficeID.
		 */
		public long getLOfficeID() {
			return lOfficeID;
		}
		/**
		 * @param id The lOfficeID to set.
		 */
		public void setLOfficeID(long lOfficeID) {
			this.lOfficeID = lOfficeID;
		}
		
	 	/**
		 * @return Returns the lCurrencyID.
		 */
		public long getLCurrencyID() {
			return lCurrencyID;
		}
		/**
		 * @param id The lCurrencyID to set.
		 */
		public void setLCurrencyID(long lCurrencyID) {
			this.lCurrencyID = lCurrencyID;
		}

	 	/**
		 * @return Returns the lAccountID.
		 */
		public long getSAccountNoStart() {
			return lAccountID;
		}
		/**
		 * @param id The lAccountID to set.
		 */
		public void setSAccountNoStart(long lAccountID) {
			this.lAccountID = lAccountID;
		}
		
		/**
		 * @return Returns the lContractID.
		 */
		public long getSContractNoStart() {
			return lContractID;
		}
		/**
		 * @param id The lContractID to set.
		 */
		public void setSContractNoStart(long lContractID) {
			this.lContractID = lContractID;
		}
		
		/**
		 * @return Returns the lDueBillID.
		 */
		public long getSDueBillCodeStart() {
			return lDueBillID;
		}
		/**
		 * @param id The lDueBillID to set.
		 */
		public void setSDueBillCodeStart(long lDueBillID) {
			this.lDueBillID = lDueBillID;
		}
		/**
		 * @return Returns the tsDateStart.
		 */
		public Timestamp getTsDateStart()
		{
			return tsDateStart;
		}
		/**
		 * @param modifyDate The tsDateStart to set.
		 */
		public void setTsDateStart(Timestamp tsDateStart)
		{
			this.tsDateStart = tsDateStart;
			//putUsedField("modifyDate", modifyDate);
		}
		
		/**
		 * @return Returns the tsDateEnd.
		 */
		public Timestamp getTsDateEnd()
		{
			return tsDateEnd;
		}
		/**
		 * @param modifyDate The tsDateEnd to set.
		 */
		public void setTsDateEnd(Timestamp tsDateEnd)
		{
			this.tsDateEnd = tsDateEnd;
			//putUsedField("modifyDate", modifyDate);
		}
		
		/**
		 * @return Returns the lStatusID.
		 */
		public long getLStatusID() {
			return lStatusID;
		}
		/**
		 * @param id The lStatusID to set.
		 */
		public void setLStatusID(long lStatusID) {
			this.lStatusID = lStatusID;
		}
		
		/**
		 * @return Returns the LOrderParam.
		 */
		public long getLOrderParam() {
			return lOrderParam;
		}
		/**
		 * @param id The LOrderParam to set.
		 */
		public void setLOrderParam(long lOrderParam) {
			this.lOrderParam = lOrderParam;
		}
		
		/**
		 * @return Returns the sDesc.
		 */
		public String getSDesc() {
			return sDesc;
		}
		/**
		 * @param id The sDesc to set.
		 */
		public void setSDesc(String sDesc) {
			this.sDesc = sDesc;
		}
}