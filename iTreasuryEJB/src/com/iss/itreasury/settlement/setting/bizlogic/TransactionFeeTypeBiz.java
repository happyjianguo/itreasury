/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_TransactionFeeTypeDAO;
import java.util.Collection;
import com.iss.itreasury.settlement.setting.dataentity.TransFeeTypeSetInfo;
/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TransactionFeeTypeBiz {
	private Sett_TransactionFeeTypeDAO TransactionCostDAO=new Sett_TransactionFeeTypeDAO();
	
	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public long deleteTransactionFeeType(long lID){
		return TransactionCostDAO.deleteTransactionFeeType(lID);
	}
	
	/**
	 * ��ѯ���н��׷�������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>��ѯ���н��׷�������</b>
	 * <ul>
	 * <li>�������ݿ��TransactionFeeType
	 * <li>����Collection��������TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID ���´���ʶ
	 * @param lPageLineCount  ÿҳ��������
	 * @param lPageNo         �ڼ�ҳ����
	 * @param lOrderParam     �������������ݴ˲��������������������
	 * @param lDesc           �������
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllTransactionFeeType(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc){
		return TransactionCostDAO.findAllTransactionFeeType(lOfficeID,  lCurrencyID, lPageLineCount,  lPageNo,  lOrderParam,  lDesc);
	}
	
	
	/**
	 * ���ݱ�ʶ��ѯ���׷�������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>���ݱ�ʶ��ѯ���׷�������</b>
	 * <ul>
	 * <li>�������ݿ��TransactionFeeType
	 * <li>������TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return TransactionFeeTypeInfo
	 * @exception Exception
	 */
	public TransFeeTypeSetInfo findTransactionFeeTypeByID(long lID){
		return TransactionCostDAO.findTransactionFeeTypeByID(lID);
	}
	
	
	/**
	 * ���ݱ�ʶ��ѯ���׷�������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>���ݱ�ʶ��ѯ���׷�������</b>
	 * <ul>
	 * <li>�������ݿ��TransactionFeeType
	 * <li>������TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return TransactionFeeTypeInfo
	 * @exception Exception
	 */
	public TransFeeTypeSetInfo findTransactionFeeTypeByID(long lID, long lCurrencyID) {
		return TransactionCostDAO.findTransactionFeeTypeByID(lID, lCurrencyID);
	}
	
	
	/**
	 * �õ����µĽ��׷������ʹ���
	 * @param lOfficeID ���´���ʶ
	 * @return
	 */
	public String getNewTransactionFeeTypeCode(long lOfficeID,long lCurrencyID){
		return TransactionCostDAO.getNewTransactionFeeTypeCode(lOfficeID,lCurrencyID);
	}
	
	
	/**
	 * ���潻�׷�������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>���潻�׷�������</b>
	 * <ul>
	 * <li>�������ݿ��TransactionFeeType
	 * <li>���lID<0������TransactionFeeType��������һ����¼
	 * <li>������±�ʶ��lID�ļ�¼��Ϣ
	 * <li>��״̬��Ϊ����
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param strName
	 * @param strCode
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveTransactionFeeType(long lID, long lOfficeID, long lCurrencyID, String strName, String strCode, String strSubjectCode, long lIsHaveBank){
		return TransactionCostDAO.saveTransactionFeeType( lID,  lOfficeID,  lCurrencyID,  strName,  strCode,  strSubjectCode,  lIsHaveBank);
	}
	

	
}
