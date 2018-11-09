package com.iss.itreasury.settlement.setting.dao;

import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.FixedDepositMonthInfo;
import com.iss.itreasury.settlement.util.*;
import java.sql.*;

public class FixedDepositMonthSetDao extends SettlementDAO 
{
	public FixedDepositMonthSetDao()
    {
        super("SETT_FixedDepositMonth",false);
        super.setUseMaxID();
    }
	public Collection queryFixedDepositMonth (FixedDepositMonthInfo info) throws Exception
	{
		Collection coll= null;
		initDAO();
		StringBuffer sql = new StringBuffer();
		try
		{
			sql.append(" select a.*,b.sname from SETT_FixedDepositMonth a,userinfo b where 1=1 ");
			sql.append(" and a.statusid <>0 ");
			sql.append(" and a.modifyuserid = b.id ");
			sql.append(" and a.officeid = "+info.getOfficeId());
			sql.append(" and a.CurrencyId = "+info.getCurrencyId());
			if(info.getTransType()>0)
			{
				sql.append(" and a.transtype ="+info.getTransType());
			}
			if(info.getId()>0)
			{
				sql.append(" and a.id ="+info.getId());
			}
			sql.append(" order by a.transtime asc ");
			System.out.println(sql.toString());
			this.prepareStatement(sql.toString());
			this.executeQuery();
			coll=this.getDataEntitiesFromResultSet(FixedDepositMonthInfo.class);
		}
		catch(Exception e)
		{
			throw new ITreasuryDAOException("查询期限异常",e); 
		}
		finally
		{
			finalizeDAO();
		}
		return coll;
	}
	public Collection checkFixedDepositMonth (FixedDepositMonthInfo info) throws Exception
	{
		Collection coll= null;
		initDAO();
		StringBuffer sql = new StringBuffer();
		try
		{
			sql.append(" select a.* from SETT_FixedDepositMonth a where 1=1 ");
			sql.append(" and a.statusid <>0 ");
			sql.append(" and a.officeid = "+info.getOfficeId());
			sql.append(" and a.CurrencyId = "+info.getCurrencyId());
			if(info.getTransType()>0)
			{
				sql.append(" and a.transtype ="+info.getTransType());
			}
			if(info.getTransTime()>0)
			{
				sql.append(" and a.transtime ="+info.getTransTime());
			}
			if(info.getId()>0)
			{
				sql.append(" and a.id <>"+info.getId());
			}
			System.out.println(sql.toString());
			this.prepareStatement(sql.toString());
			this.executeQuery();
			coll=this.getDataEntitiesFromResultSet(FixedDepositMonthInfo.class);
		}
		catch(Exception e)
		{
			throw new ITreasuryDAOException("查询期限异常",e); 
		}
		finally
		{
			finalizeDAO();
		}
		return coll;
	}
	public long checkInUsed(long depositterm) throws Exception
	{
		long flag = -1;
		ResultSet rs = null;
		initDAO();
		StringBuffer sql = new StringBuffer();
		try
		{
			if(depositterm > 0)
			{
				
				sql.append(" select id from ( ");
				sql.append(" select id from sett_TransOpenFixedDeposit where 1=1 ");
				sql.append(" and nstatusid = " + SETTConstant.TransactionStatus.SAVE );
				if(depositterm > 1000)
				{
					sql.append(" and nnoticeday = " + depositterm );
				}else {
					sql.append(" and ndepositterm = " + depositterm );
				}
				if(depositterm < 1000)
				{
				sql.append(" UNION ");
				sql.append(" select  id from sett_TransFixedContinue where 1=1 ");
				sql.append(" and nstatusid = " + SETTConstant.TransactionStatus.SAVE );
				if(depositterm < 1000)
				{
					sql.append(" and ndepositterm = "+ depositterm +") ");
				}else{
					sql.append(" ) ");
				}
				}else{
				sql.append(" ) ");
				}
			
			}
			System.out.println(sql.toString());
			this.prepareStatement(sql.toString());
			rs = this.executeQuery();
			if(rs.next())
			{
				flag = 1;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		
		return flag;
	}
	public String queryFixedDepositMonthSetSQL(FixedDepositMonthInfo info) {
		StringBuffer sql = new StringBuffer();
			sql.append(" select a.id,a.modifydate,b.sname ");
			if(info.getTransType()==SETTConstant.TransQueryType.NOTIFY)
				sql.append(", a.transtime-10000 as transtime");
			else
				sql.append(", a.transtime");
			sql.append(" from SETT_FixedDepositMonth a,userinfo b where 1=1 ");
			sql.append(" and a.statusid <>0 ");
			sql.append(" and a.modifyuserid = b.id ");
			sql.append(" and a.officeid = "+info.getOfficeId());
			sql.append(" and a.CurrencyId = "+info.getCurrencyId());
			if(info.getTransType()>0)
			{
				sql.append(" and a.transtype ="+info.getTransType());
			}
			if(info.getId()>0)
			{
				sql.append(" and a.id ="+info.getId());
			}
		return sql.toString();
	}
}
