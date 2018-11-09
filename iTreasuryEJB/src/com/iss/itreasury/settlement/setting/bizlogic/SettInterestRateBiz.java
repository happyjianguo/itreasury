/*
 * Created on 2004-10-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_InterestRateDAO;
import com.iss.itreasury.settlement.setting.dataentity.InterestRateInfo;
import com.iss.itreasury.util.IException;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SettInterestRateBiz
{
	Sett_InterestRateDAO dao = null;
	public SettInterestRateBiz()
	{
		dao = new Sett_InterestRateDAO();
	}
	public Collection findAllInterestRateByDate(long lID,long nOfficeID,long nCurrencyID, Timestamp tsEffectiveDate, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws SettlementException
	{
		return dao.findAllInterestRateByDate(lID,nOfficeID,nCurrencyID,tsEffectiveDate,lPageLineCount,lPageNo,lOrderParam,lDesc);
	}
	public long insertInterestRate(long lID, java.lang.String strName,String sFirstName, java.sql.Timestamp tsEffective, long lInputUserID, java.sql.Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException 
	{
		return dao.insertInterestRate(lID,strName,sFirstName,tsEffective,lInputUserID,tsInput,dRate,nCurrencyID,nOfficeID,nAccouttypeID,nFixeddepositmonthID);
	}
	public InterestRateInfo findInterestRateByID(long lID) throws SettlementException
	{
		return dao.findInterestRateByID(lID);
	}
	public long saveInterestRate(long lSerialNo, long lID, String strName,String sFirstName, Timestamp tsEffective, long lInputUserID, Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException
	{
		return dao.saveInterestRate(lSerialNo,lID,strName,sFirstName,tsEffective,lInputUserID,tsInput,dRate,nCurrencyID,nOfficeID,nAccouttypeID,nFixeddepositmonthID);
	}
	public long deleteInterestRate(long lSerialNo) throws SettlementException
	{
		return dao.deleteInterestRate(lSerialNo);
	}
	public Collection findAllInterestRateSecond(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws SettlementException
	{
		return dao.findAllInterestRateSecond(lOfficeID,lCurrencyID,lPageLineCount,lPageNo,lOrderParam,lDesc);
	}
	public double findLastInterestRate(InterestRateInfo info) throws SettlementException
	{
		return dao.findLastInterestRate(info);
	}
	public Collection findInterestRate(InterestRateInfo info) throws Exception
	{
		Collection coll = null;
		try
		{
			 coll =  dao.findInterestRate(info);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return coll;
	}
	/**
	 * ±£´æºó¸´ºË
	 * @param lID
	 * @param strName
	 * @param sFirstName
	 * @param tsEffective
	 * @param lInputUserID
	 * @param tsInput
	 * @param dRate
	 * @param nCurrencyID
	 * @param nOfficeID
	 * @param nAccouttypeID
	 * @param nFixeddepositmonthID
	 * @return
	 * @throws SettlementException
	 * @throws SQLException 
	 */
	public long insertInterestRateDuplicate(long lID, java.lang.String strName,String sFirstName, java.sql.Timestamp tsEffective, long lInputUserID, java.sql.Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException, SQLException 
	{
		return dao.insertInterestRateDuplicate(lID,strName,sFirstName,tsEffective,lInputUserID,tsInput,dRate,nCurrencyID,nOfficeID,nAccouttypeID,nFixeddepositmonthID);
	}
	
	public Collection findInterestRateDuplicate(InterestRateInfo info) throws Exception
	{
		Collection coll = null;
		try
		{
			 coll =  dao.findInterestRateDuplicate(info);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return coll;
	}
	public InterestRateInfo findInterestRateDuplicateByID(long lID) throws SettlementException
	{
		return dao.findInterestRateDuplicateByID(lID);
	}
	
	public long saveInterestRateDuplicate(long lSerialNo, long lID, String strName,String sFirstName, Timestamp tsEffective, long lInputUserID, Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException
	{
		return dao.saveInterestRateDuplicate(lSerialNo,lID,strName,sFirstName,tsEffective,lInputUserID,tsInput,dRate,nCurrencyID,nOfficeID,nAccouttypeID,nFixeddepositmonthID);
	}
	
	public long deleteInterestRateDuplicate(long lSerialNo) throws SettlementException, SQLException
	{
		return dao.deleteInterestRateDuplicate(lSerialNo);
	}
	
	public InterestRateInfo matchInterestRate(long lID, java.lang.String strName,String sFirstName, java.sql.Timestamp tsEffective, long lInputUserID, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException, SQLException 
	{
		return dao.matchInterestRate(lID,strName,sFirstName,tsEffective,lInputUserID,dRate,nCurrencyID,nOfficeID,nAccouttypeID,nFixeddepositmonthID);
	}
	
	public long cancleCheckInterestRate(InterestRateInfo interestRateInfo) throws SettlementException, SQLException{
		return dao.cancleCheckInterestRate(interestRateInfo);
	}
	public long checkInterestRate(InterestRateInfo interestRateInfo,long checkUserId,Timestamp checkDate) throws SettlementException, SQLException{
		return dao.checkInterestRate(interestRateInfo,checkUserId,checkDate);
	}
	
	public Collection findCheckedInterestRate(long lOfficeID, long lCurrencyID,long status,String sName,long accouttypeID,long fixeddepositmonthID,Timestamp tsEffective,double dRate,long inputUserID) throws SettlementException{
		return dao.findCheckedInterestRate(lOfficeID, lCurrencyID,status,sName,accouttypeID,fixeddepositmonthID,tsEffective,dRate,inputUserID);
	}
}
