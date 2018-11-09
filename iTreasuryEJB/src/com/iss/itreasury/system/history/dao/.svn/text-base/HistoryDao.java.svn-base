package com.iss.itreasury.system.history.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.util.IException;

public class HistoryDao extends ITreasuryDAO{
	public HistoryDao()
	{
		super("sys_historystep");
	}

	public HistoryDao(Connection con)
	{
		super("sys_historystep",con);
	}
	
	public List queryByCondition(HistoryAdviseInfo qInfo)throws IException{
		List list_Return=new ArrayList();
		ResultSet localRS = null;
		String strSQL = "";
		try {
			initDAO();
			strSQL = "select * from SYS_HISTORYSTEP where 1=1";
			if(qInfo.getId()>0)
			{
				strSQL = strSQL + " and id = "+ qInfo.getId() ;
			}
			if(qInfo.getOperator()!=null && qInfo.getOperator().length()>0)
			{
				strSQL = strSQL + " and CALLER = '" + qInfo.getOperator() + "'";
			}
			if(qInfo.getEntryID()>0)
			{
				strSQL = strSQL + " and ENTRY_ID = "+ qInfo.getEntryID() ;
			}
			if(qInfo.getActionKey()!=null&&qInfo.getActionKey().length()>0)
			{
				strSQL = strSQL + " and ACTION_KEY = '"+ qInfo.getActionKey() +"'";
			}
			if(qInfo.getStatusID()>0)
			{
				strSQL = strSQL + " and STATUSID = "+ qInfo.getStatusID() ;
			}
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
			while (localRS.next())
			{
				HistoryAdviseInfo tempInfo = new HistoryAdviseInfo();
				tempInfo.setId(localRS.getLong("id"));
				tempInfo.setOperator(NameRef.getUserNameByID(Long.parseLong((String)localRS.getString("CALLER"))));
				tempInfo.setOpTime(localRS.getTimestamp("EXECUTE_DATE"));
				tempInfo.setAdvise(localRS.getString("ADVISE_VALUE"));
				tempInfo.setAction(localRS.getString("ACTION_NAME"));				
				tempInfo.setStatusID(localRS.getLong("STATUSID"));
				list_Return.add(tempInfo);				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				e1.printStackTrace();
			}
		}		
		return list_Return;
	}

}
