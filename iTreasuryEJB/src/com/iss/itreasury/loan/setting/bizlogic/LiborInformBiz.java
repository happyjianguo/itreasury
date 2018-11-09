/*
 * Created on 2004-11-29
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.setting.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.setting.dao.LiborInformDao;
import com.iss.itreasury.loan.setting.dataentity.LiborInformInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LiborInformBiz extends Object implements java.io.Serializable
{
	private static Log4j log4j = null;
	/**
	 * No argument constructor required by container.
	 */
	public LiborInformBiz()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	
	/**
	 * Libor利率设置的保存操作
	*/
	public long save(LiborInformInfo info) throws LoanException
	{
		long lReturn = -1;
		long validDate = -1;
		LiborInformDao dao = new LiborInformDao();

		try
		{
			/*检查起息日期和结息日期是否合法*/
			validDate = dao.checkDate(info);
			if( validDate == 1 )
			{
				System.out.println("OK--- 起息日期或结息日期合法! ");
				/*更新Libor利率设置表*/
				if (info.getId() <= 0)
				{
				    /*更新Libor利率设置表*/
				    info.setIsCountInterest(1);
					dao.setUseMaxID();
					lReturn = dao.add(info);
				}
				else if (info.getId() > 0)
				{
					/*更新Libor利率设置表*/
				    info.setIsCountInterest(1);
					dao.update(info);
					lReturn = info.getId();
				}
			}
			else
			{
				System.out.println(" NO--- 起息日期或结息日期不合法! ");
				lReturn = -1;
			}
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
		return lReturn;
	}

	/**
	 * Libor利率设置的取消操作
	*/
	public void cancel(long lID) throws LoanException
	{
		LiborInformDao dao = new LiborInformDao();
		LiborInformInfo info = new LiborInformInfo();

		try
		{			
		    info.setId(lID);
		    info.setStatusID(LOANConstant.RecordStatus.INVALID);
		    dao.update(info);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
	}

	/**
	 * Libor利率设置的单笔查询操作
	*/
	public LiborInformInfo findByID(long lID) throws LoanException
	{
		LiborInformInfo returnInfo = new LiborInformInfo();
		LiborInformDao dao = new LiborInformDao();

		try
		{
		    returnInfo = (LiborInformInfo) dao.findByID(lID,returnInfo.getClass());
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return returnInfo;
	}

	/**
	 * Libor利率设置的多笔查询操作
	*/
	public Collection findByMultiOption(LiborInformInfo qInfo) throws LoanException
	{
		Collection c = null;
		LiborInformDao dao = new LiborInformDao();

		try
		{
		    c = dao.findByMultiOption(qInfo);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
		
		return c;
	}
	
	/**
	 * 返回放款通知单下最晚的结息日
	*/
	public Timestamp getLastInterestEnd(long lPayNoticeID) throws LoanException
	{
	    Timestamp tsLastInterestEnd = null;
		LiborInformDao dao = new LiborInformDao();

		try
		{
		    tsLastInterestEnd = dao.getLastInterestEnd(lPayNoticeID);
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}
		
		return tsLastInterestEnd;
	}

}
