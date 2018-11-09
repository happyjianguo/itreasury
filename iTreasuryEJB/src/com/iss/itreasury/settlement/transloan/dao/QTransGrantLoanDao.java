package com.iss.itreasury.settlement.transloan.dao;

import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * �˻���ѯ���ݲ�
 * 
 * @author xiang
 * 
 */
public class QTransGrantLoanDao {
	/**
	 * ���׺�
	 */
	public final static int ORDER_TRANS_NO = 1;
	/**
	 * ��������
	 */
	public final static int ORDER_TRANSACTION_TYPE_ID = 2;
	/**
	 * �����˻�
	 */
	public final static int ORDER_LOAN_ACCOUNT_ID = 3;
	/**
	 * �����ͬ��
	 */
	public final static int ORDER_LOAN_CONTRACT_ID = 4;
	/**
	 * �ſ�֪ͨ��
	 */
	public final static int ORDER_LOAN_NOTE_ID = 5;
	/**
	 * ���ڴ���˻�ID
	 */
	public final static int ORDER_DEPOSIT_ACCOUNT_ID = 6;
	/**
	 * ���
	 */
	public final static int ORDER_AMOUNT = 7;
	/**
	 * ��Ϣ��
	 */
	public final static int ORDER_INTEREST_START = 8;
	/**
	 * ժҪ
	 */
	public final static int ORDER_ABSTRACT = 9;
	/**
	 * ����״̬
	 */
	public final static int ORDER_STATUS = 10;
	/**
	 * �޸�ʱ��
	 */
	public final static int ORDER_MODIFY = 11;
	/**
	 * ¼������
	 */
	public final static int ORDER_INPUT_DATE = 12;

	public String queryLoanSQL(TransGrantLoanInfo info) {
		StringBuffer sbGrantSQL = new StringBuffer();
		
		sbGrantSQL.append("ID,NOFFICEID,NCURRENCYID, STRANSNO, NTRANSACTIONTYPEID, \n");
		sbGrantSQL.append("NLOANACCOUNTID,NLOANCONTRACTID, NLOANNOTEID, NEXTENDFORMID, NCONSIGNDEPOSITACCOUNTID,\n");
		sbGrantSQL.append("NISKEEPACCOUNT, NPAYINTERESTACCOUNTID, NRECEIVEINTERESTACCOUNTID,NPAYSURETYFEEACCOUNTID, NRECEIVESURETYFEEACCOUNTID,\n");
		sbGrantSQL.append("NPAYCOMMISIONACCOUNTID,MINTERESTTAXRATE, DTINTERESTTAXRATEVAULEDATE, NDEPOSITACCOUNTID, NPAYTYPEID,\n");
		sbGrantSQL.append("NBANKID, SEXTACCTNO, SEXTACCTNAME, SBANKNAME, SPROVINCE, \n");
		sbGrantSQL.append("SCITY, MAMOUNT,NCASHFLOWID, DTINTERESTSTART, DTEXECUTE,\n");
		sbGrantSQL.append("DTMODIFY, DTINPUT, NINPUTUSERID,NCHECKUSERID, NABSTRACTID,  \n");
		sbGrantSQL.append("SABSTRACT, SCHECKABSTRACT, NSTATUSID, SEXTBANKNO ,nInterestTaxPlanId \n");
		
		StringBuffer sbSQL = new StringBuffer(128);
		sbSQL.append("SELECT \n");
		sbSQL.append(sbGrantSQL.toString());
		sbSQL.append("FROM SETT_TRANSGRANTLOAN");
		sbSQL.append(" where nOfficeID = " + info.getOfficeID());
		sbSQL.append(" and nCurrencyID = " + info.getCurrencyID());
		sbSQL.append(" and nTransactionTypeID = " + info.getTransactionTypeID());
		if (info.getInputUserID() > 0)
		{
			sbSQL.append(" and nInputUserID =" + info.getInputUserID() + " \n");
		}
		if (info.getCheckUserID() != -1)
		{
			sbSQL.append(" and nCheckUserID =" + info.getCheckUserID() + " \n");
		}
		long[] statusIDs = info.getStatusIDs();
		
					for (int i = 0; i < statusIDs.length; i++)
					{
						Log.print("statusID" + i + ":" + statusIDs[i]);
					}

					if (statusIDs != null)
					{
						boolean isStart = true;
						for (int i = 0; i < statusIDs.length; i++)
						{
							Log.print("status:" + statusIDs[i]);
							if (statusIDs[i] == SETTConstant.TransactionStatus.TEMPSAVE)
							{ //�ݴ�û��ʱ������
								if (isStart)
								{
									sbSQL.append("and (");
									isStart = !isStart;
								}
								else
								{
									sbSQL.append("or ");
								}
								sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
							}
							else
								if (statusIDs[i] == SETTConstant.TransactionStatus.SAVE)
								{ //����Ҫ�鵱���
									if (isStart)
									{
										sbSQL.append("and (");
										isStart = !isStart;
									}
									else
									{
										sbSQL.append("or ");
									}
									sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.SAVE);
									sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd')) ");
								}
								else
									if (statusIDs[i] == SETTConstant.TransactionStatus.CHECK)
									{ //�Ѿ����˵�Ҫ�鵱��
										if (isStart)
										{
											sbSQL.append("and (");
											isStart = !isStart;
										}
										else
										{
											sbSQL.append("or ");
										}
										sbSQL.append("(nStatusID = " + SETTConstant.TransactionStatus.CHECK);
										sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd')) ");
									}
									else
									{
										sbSQL.append("and ");
										sbSQL.append("nStatusID = " + statusIDs[i]); //�հ׵�ʱ��
									}
						}
						if (!isStart)
							sbSQL.append(") ");
					}
					else
					{ //���û��statusID��Ĭ�ϲ�ȫ����Ч��¼
						sbSQL.append(" and (nStatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
						sbSQL.append(" or (nStatusID = " + SETTConstant.TransactionStatus.SAVE);
						sbSQL.append(" and dtExecute=to_date('"+ (info.getExecute()+"").split(" ")[0]+"','yyyy-mm-dd')) ");
					}
		
		Log.print(sbSQL.toString());
		return sbSQL.toString();
	}

}
