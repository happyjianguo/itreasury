package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferAccountInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
public class TransferAccountDao extends ITreasuryDAO 
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	private static Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER);
	
	public TransferAccountDao()
	{
		super("SETT_TRANSFERACCOUNT");
	}
	public TransferAccountDao(String tablename ,String seqfortable)
	{
		super(tablename,seqfortable);
	}
	
	public TransferAccountDao(String tablename ,String seqfortable,Connection  conn)
	{
		super(tablename,seqfortable,conn);
	}
	
	public void save(TransferAccountInfo transferAccountInfo) throws Exception 
	{
		try{
		     add(transferAccountInfo);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					finalizeDAO();
				}
	}
	
}
