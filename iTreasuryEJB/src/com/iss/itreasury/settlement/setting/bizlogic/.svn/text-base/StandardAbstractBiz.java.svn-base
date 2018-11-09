/*
 * Created on 2005-4-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_StandardAbstractDAO;
import com.iss.itreasury.settlement.setting.dataentity.StandardAbstractInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StandardAbstractBiz
{
	/**
	 * 
	 */
	Sett_StandardAbstractDAO dao  = null;
	public StandardAbstractBiz()
	{
		super();
		dao = new Sett_StandardAbstractDAO();
		// TODO Auto-generated constructor stub
	}
	public Collection findAllStandardAbstract(StandardAbstractInfo info) throws SettlementException, ITreasuryDAOException, SQLException
	{
		return dao.findAllStandardAbstract(info);
	}
	public long saveStandardAbstract(StandardAbstractInfo info) throws SettlementException
	{
		info.setNStatusID(Constant.RecordStatus.VALID);
		return dao.saveStandardAbstract(info);
	}
	public long deleteStandardAbstract(long lID) throws SettlementException
	{
		try
		{
			return dao.updateStatus(lID,SETTConstant.TransactionStatus.DELETED);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SettlementException();
		}
	}
	public StandardAbstractInfo findStandardAbstractByID(long lID) throws SettlementException
	{
		try
		{
			return (StandardAbstractInfo)dao.findByID(lID,StandardAbstractInfo.class);
		} catch (ITreasuryDAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SettlementException();
		}
	}
	public long getMaxAbstractCode(long lOfficeID) throws SettlementException {
		return dao.getMaxAbstractCode(lOfficeID);
	}
	
	public long getMaxCode(long lOfficeID) throws SettlementException {
		return dao.getMaxCode(lOfficeID);
	}
}
