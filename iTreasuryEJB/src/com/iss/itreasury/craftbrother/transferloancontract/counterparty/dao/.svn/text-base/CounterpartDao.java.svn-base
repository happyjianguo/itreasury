package com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartInfo;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.QueryCounterpartInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingRevaluedInfo;
import com.iss.itreasury.creditrating.set.dataentity.RatingProjectViewInfo;
import com.iss.itreasury.creditrating.set.dataentity.TargetSetupInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class CounterpartDao extends ITreasuryDAO
{
	private static Log4j log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER);
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	public CounterpartDao()
	{
		super("CRA_COUNTERPART");
	}
	public CounterpartDao(Connection  conn)
	{
		super("CRA_COUNTERPART","SEQ_CRA_COUNTERPART",conn);
	}
	
	/**
	 * @deprecated
	 * @param tablename
	 */
	public CounterpartDao(String tablename ,String seqfortable)
	{
		super(tablename,seqfortable);
	}
	/**
	 * @deprecated
	 * @param tablename
	 */
	public CounterpartDao(String tablename ,String seqfortable,Connection  conn)
	{
		super(tablename,seqfortable,conn);
	}
	
	public PageLoader queryCounterpartInfo(QueryCounterpartInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		
		try
		{
			m_sbSelect = new StringBuffer();
			m_sbSelect.append("a.id counterpartid,b.id counterpartbankid,a.counterpartcode,a.counterpartname,b.counterpartbankno, b.counterpartbankname,b.counterparname");
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" cra_counterpart a,cra_counterpartbank b \n");
			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  b.counterpartid=a.id and a.statusid ="+CRAconstant.TransactionType.counterpartBank.VALID);
			
			m_sbWhere.append(" and b.statusid = "+CRAconstant.TransactionType.counterpartBank.VALID);
			
			if(info.getCounterpartid()!=-1)
			{
				m_sbWhere.append(" and  a.id = "+info.getCounterpartid());
			}
			if(!info.getCounterpartbankname().equals(""))
			{
				m_sbWhere.append(" and  b.counterpartbankname like '%"+info.getCounterpartbankname()+"%'");
			}
			if(!info.getCounterpartcode().equals(""))
			{
				m_sbWhere.append(" and  a.counterpartcode like '%"+info.getCounterpartcode()+"%'");
			}
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append("order by a.counterpartcode desc");
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(
				new AppContext(),
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.LISTALL,
				"com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.QueryCounterpartInfo",
				null);
			pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
			return pageLoader;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException ("Gen_E001");
		}
	}
	
	public CounterpartInfo QueryCounterpartInfo(CounterpartInfo info) throws ITreasuryDAOException
	{
		CounterpartInfo  counterpartinfo = new CounterpartInfo();
		StringBuffer strSQL = null;
		StringBuffer strSQL1 = null;
		
		try {
			  initDAO();
	        	strSQL = new StringBuffer();
	        	strSQL.append(" select a.* from cra_counterpart a");
	        	strSQL.append(" where a.statusid = "+CRAconstant.TransactionType.counterpartBank.VALID );
	        	strSQL.append(" and a.id = ?");
	        	prepareStatement(strSQL.toString());
				log4j.info("strSQL=\n" + strSQL.toString());
	        	int nIndex = 1;
	        	transPS.setString(nIndex++, String.valueOf(info.getId()));
	        	executeQuery();
	        	counterpartinfo=(CounterpartInfo)getDataEntityFromResultSet(CounterpartInfo.class);
	        	strSQL1 = new StringBuffer();
	        	strSQL1.append(" select b.* from cra_counterpartbank b");
	        	strSQL1.append(" where b.statusid = "+CRAconstant.TransactionType.counterpartBank.VALID );
	        	strSQL1.append(" and  b.counterpartid = ?");
	        	prepareStatement(strSQL1.toString());
				log4j.info("strSQL1=\n" + strSQL1.toString());
	        	int nIndex1 = 1;
	        	transPS.setString(nIndex1++, String.valueOf(info.getId()));
	        	executeQuery();
	        	Collection coll = getDataEntitiesFromResultSet(CounterpartBankInfo.class);
	        	CounterpartBankInfo[] counterpartbankinfo = new  CounterpartBankInfo[coll.size()];
	        	
	        	Iterator it=coll.iterator();
	        	int i=0;
	        	while(it.hasNext())
	        	{
	        		counterpartbankinfo[i]=(CounterpartBankInfo)it.next();
	        		i++;
	        	}
	        	counterpartinfo.setInfo(counterpartbankinfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
			
		}
		finally 
		{
			 finalizeDAO();
		}
		return counterpartinfo;
	}
	public CounterpartInfo checkcounterpartcode(CounterpartInfo info) throws ITreasuryDAOException
	{
		StringBuffer strSQL = null;
		CounterpartInfo counterpartinfo = new CounterpartInfo();
		try 
		{
			initDAO();
        	strSQL = new StringBuffer();
        	strSQL.append(" select a.* from cra_counterpart a");
        	strSQL.append(" where a.statusid = "+CRAconstant.TransactionType.counterpartBank.VALID );
        	strSQL.append(" and a.counterpartcode = ?");
        	strSQL.append(" and a.id <> ?");
        	prepareStatement(strSQL.toString());
			log4j.info("strSQL=\n" + strSQL.toString());
        	int nIndex = 1;
        	transPS.setString(nIndex++, info.getCounterpartcode());
        	transPS.setString(nIndex++, String.valueOf(info.getId()));
        	executeQuery();
        	counterpartinfo=(CounterpartInfo)getDataEntityFromResultSet(CounterpartInfo.class);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库操作异常",e);
			
		}
		finally 
		{
			finalizeDAO();
		}
		return counterpartinfo;
	}
	 public CounterpartBankInfo findInfoByID(CounterpartBankInfo bankInfo)throws Exception
	   {
		   StringBuffer sbSQL = new StringBuffer();
		   CounterpartBankInfo info = new CounterpartBankInfo();
		   try{
			   if(bankInfo.getId() <= 0)
			   {
				   return null;
			   }
			   else
			   {
				   sbSQL.append(" select k.* from cra_counterpartbank k where  k.statusid =" + CRAconstant.TransactionStatus.SAVE);	
				   sbSQL.append(" and k.id = " + bankInfo.getId());
				   initDAO();
				   prepareStatement(sbSQL.toString());;
				   executeQuery();
				   while(this.transRS != null && this.transRS.next())
				   {
					   info.setId(transRS.getLong("ID"));
					   info.setOfficeid(transRS.getLong("officeid"));
					   info.setCurrencyid(transRS.getLong("currencyid"));
					   info.setCounterparname(transRS.getString("counterparname"));
					   info.setCounterpartbankname(transRS.getString("counterpartbankname"));
					   info.setCounterpartbankno(transRS.getString("counterpartbankno"));
					   info.setCounterpartid(transRS.getLong("counterpartid"));
					   info.setProvince(transRS.getString("PROVINCE"));
					   info.setCity(transRS.getString("CITY"));
					   info.setStatusid(transRS.getLong("statusid"));
				   }
			   }
			   
		   }catch(Exception e){
			   
			   throw new IException("Gen_E001",e);
			   
		   }finally{
			   
			   this.finalizeDAO();
			   
		   }
		   return info;
	   }

}
