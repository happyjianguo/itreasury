/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiesgeneralledger.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiesgeneralledger.dataentity.GenerateGLEntryParam;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface SecuritiesGeneralLedger extends javax.ejb.EJBObject
{
	/**
	 * 交易产生会计分录
	 * @param GenerateGLEntryParam
	 * */
	public void generateGLEntry(GenerateGLEntryParam param) throws SecuritiesException,RemoteException;
	/**
	 * 交易删除会计分录
	 * @param 交割单ID
	 * */
	public void deleteGLEntry(String deliverOrderCode) throws SecuritiesException,RemoteException;
	
	
	/**
	 * 交易删除会计分录
	 * @param 交割单ID
	 * */
	//public void deleteGLEntry(String contractCode,Timestamp interestStartDate) throws SecuritiesException,RemoteException;	
	/**
	 * “结转成本”产生的所有会计分录中，对机构号ID、币种号、科目代码均相同的分录，
	 * 应该汇总其金额、使之合并成一笔分录
	 * */
	public void combineGLEntryForCarryCost(long officeID,long currencyID,String subjectCode) throws SecuritiesException,RemoteException;	
}
