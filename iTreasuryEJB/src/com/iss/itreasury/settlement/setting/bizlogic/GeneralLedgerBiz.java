/*
 * Created on 2004-10-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;
import com.iss.itreasury.settlement.setting.dao.Sett_GeneralLedgerDAO;
import java.util.*;
import com.iss.itreasury.settlement.setting.dataentity.*;

/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GeneralLedgerBiz {
	private Sett_GeneralLedgerDAO generalledgerdao=new Sett_GeneralLedgerDAO();
	/**
	 * ɾ��������ҵ������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>ɾ��������ҵ������</b>
	 * <ul>
	 * <li>�������ݿ��GeneralLedger
	 * <li>��״̬��Ϊɾ��
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void  sss
	 * @exception Exception
	 */
	public long deleteGeneralLedger(long lID){
		return generalledgerdao.deleteGeneralLedger(lID);
	}
	
	
	/**
	 * ��ѯ����������ҵ������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>��ѯ����������ҵ������</b>
	 * <ul>
	 * <li>�������ݿ��GeneralLedgerType
	 * <li>����Collection��������GeneralLedgerInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lPageLineCount  ÿҳ��������
	 * @param lPageNo         �ڼ�ҳ����
	 * @param lOrderParam     �������������ݴ˲��������������������
	 * @param lDesc           �������
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllGeneralLedger(long lOfficeID, long lCurrencyID, String strStartCode, String strEndCode, String strName, String strSubject, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) 
	{
		return generalledgerdao.findAllGeneralLedger(lOfficeID,lCurrencyID,strStartCode,strEndCode,strName,strSubject,lPageLineCount,lPageNo,lOrderParam,lDesc);
	}
	
	/**
	 * ���ݱ�ʶ��ѯ������ҵ������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>���ݱ�ʶ��ѯ������ҵ������</b>
	 * <ul>
	 * <li>�������ݿ��GeneralLedger
	 * <li>������GeneralLedgerInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return GeneralLedgerInfo
	 * @exception Exception
	 */
	public GeneralLedgerInfo findGeneralLedgerByID(long lID, long lCurrencyID)
	{
		return generalledgerdao.findGeneralLedgerByID(lID,lCurrencyID);
	}
	
	
	/**
	 * �õ����µ����˴���
	 */
	public String getNewGeneralLedgerCode(long lOfficeID,long lCurrencyID) 
	{
		return generalledgerdao.getNewGeneralLedgerCode(lOfficeID,lCurrencyID);
	}
	
	
	/**
	 * ����������ҵ������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>����������ҵ������</b>
	 * <ul>
	 * <li>�������ݿ��GeneralLedger
	 * <li>���lID<0������GeneralLedger��������һ����¼
	 * <li>������±�ʶ��lID�ļ�¼��Ϣ
	 * <li>��״̬��Ϊ����
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param strGeneralLedgerCode
	 * @param strName
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveGeneralLedger(long lID, long lOfficeID, String strGeneralLedgerCode, String strName, String strSubjectCode, long lCurrencyID) 
	{
		return generalledgerdao.saveGeneralLedger(lID, lOfficeID, strGeneralLedgerCode, strName, strSubjectCode, lCurrencyID);
	}
}
