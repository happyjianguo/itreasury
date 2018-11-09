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
 * �˻�������ò�ѯ��
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class AccumulateFeeInfoQuery extends SettlementBaseDataEntity{
	 	
		private long lOfficeID=-1;					//���´�
	 	private long lCurrencyID=-1;				//����
	 	private String sAccountNoStart="";			//��ʼ�˻���
	 	private String sAccountNoEnd="";			//��ֹ�˻���
	 	private String sContractNoStart="";			//��ʼ��ͬ��
	 	private String sContractNoEnd="";			//��ֹ��ͬ��
	 	private String sDueBillCodeStart="";		//��ʼ�ſ�֪ͨ����
	 	private String sDueBillCodeEnd="";			//�����ſ�֪ͨ����
	 	private String sFixedDepositNoStart="";		//��ʼ���ڴ浥��
	 	private String sFixedDepositNoEnd="";		//�������ڴ浥��
	 	private Timestamp tsDateStart=null;			//��ʼ����
	 	private Timestamp tsDateEnd=null;			//��������
	 	//
	 	private long lOrderParam;		//������ֶ����ֵ
	 	private long lDesc;				//������
	 	
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
		 * @return Returns the sContractNoStart.
		 */
		public String getSContractNoStartCtrl() {
			return sContractNoStart;
		}
		/**
		 * @param id The sContractNoStart to set.
		 */
		public void setSContractNoStartCtrl(String sContractNoStart) {
			this.sContractNoStart = sContractNoStart;
		}
		
		/**
		 * @return Returns the sAccountNoEnd.
		 */
		public String getSAccountNoEndCtrl() {
			return sAccountNoEnd;
		}
		/**
		 * @param id The sAccountNoEnd to set.
		 */
		public void setSAccountNoEndCtrl(String sAccountNoEnd) {
			this.sAccountNoEnd = sAccountNoEnd;
		}
		
		/**
		 * @return Returns the sAccountNoStart.
		 */
		public String getSAccountNoStartCtrl() {
			return sAccountNoStart;
		}
		/**
		 * @param id The sAccountNoStart to set.
		 */
		public void setSAccountNoStartCtrl(String sAccountNoStart) {
			this.sAccountNoStart = sAccountNoStart;
		}
		
		/**
		 * @return Returns the sContractNoEnd.
		 */
		public String getSContractNoEndCtrl() {
			return sContractNoEnd;
		}
		/**
		 * @param id The sContractNoEnd to set.
		 */
		public void setSContractNoEndCtrl(String sContractNoEnd) {
			this.sContractNoEnd = sContractNoEnd;
		}
		
		
		/**
		 * @return Returns the sDueBillCodeStart.
		 */
		public String getSDueBillCodeStartCtrl() {
			return sDueBillCodeStart;
		}
		/**
		 * @param id The sDueBillCodeStart to set.
		 */
		public void setSDueBillCodeStartCtrl(String sDueBillCodeStart) {
			this.sDueBillCodeStart = sDueBillCodeStart;
		}
		
		/**
		 * @return Returns the sDueBillCodeEnd.
		 */
		public String getSDueBillCodeEndCtrl() {
			return sDueBillCodeEnd;
		}
		/**
		 * @param id The sDueBillCodeEnd to set.
		 */
		public void setSDueBillCodeEndCtrl(String sDueBillCodeEnd) {
			this.sDueBillCodeEnd = sDueBillCodeEnd;
		}
		
		
		/**
		 * @return Returns the sFixedDepositNoStart.
		 */
		public String getSFixedDepositNoStart() {
			return sFixedDepositNoStart;
		}
		/**
		 * @param id The sFixedDepositNoStart to set.
		 */
		public void setSFixedDepositNoStart(String sFixedDepositNoStart) {
			this.sFixedDepositNoStart = sFixedDepositNoStart;
		}
		
		/**
		 * @return Returns the sFixedDepositNoEnd.
		 */
		public String getSFixedDepositNoEnd() {
			return sFixedDepositNoEnd;
		}
		/**
		 * @param id The sFixedDepositNoEnd to set.
		 */
		public void setSFixedDepositNoEnd(String sFixedDepositNoEnd) {
			this.sFixedDepositNoEnd = sFixedDepositNoEnd;
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
	 	
}