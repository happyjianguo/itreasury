package com.iss.itreasury.settlement.setting.dao;

/**
 * @author lipeng
 * 
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.obsystem.dataentity.AcctPrvgByClientInfo;
import com.iss.itreasury.ebank.util.OBConstant;

import com.iss.itreasury.settlement.setting.dataentity.UserDistributeQueryInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;


public class Sett_BankUserDistributeDAO extends SettlementDAO {
	
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	
	public PageLoader queryBankUserDistribute(UserDistributeQueryInfo queryInfo)throws Exception
	{
		if(queryInfo.getIsDirect()==OBConstant.AccountAuthorize.INDIRECT)
		{
			getBankUserDistributeSQLIndirect(queryInfo);
		}
		if(queryInfo.getIsDirect()==OBConstant.AccountAuthorize.DIRECT)
		{
			getBankUserDistributeSQLDirect(queryInfo);
		}
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
				new AppContext(),   
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT1,
				"com.iss.itreasury.settlement.setting.dataentity.UserDistributeInfo",
				null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
		
	}
	//非直接下级
	public void getBankUserDistributeSQLIndirect(UserDistributeQueryInfo queryInfo) throws Exception
	{
		
		try{
			//select
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" distinct parentclient.sCode superiorUserNo,parentclient.sName superiorUserName,childclient.sCode juniorUserNo,childclient.sName juniorUserName,directparent.sCode directSuperiorUserNo,directparent.sName directSuperiorUserName,acc.S_ACCOUNTNO accountNo,acc.S_BRANCHNAME accountName,parentclient.id superiorUserID,acc.n_ID accountID ");
			System.out.println("select "+m_sbSelect.toString());
			
			//from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" client parentclient,client childclient,bs_bankaccountinfo acc,client directparent");
			if(queryInfo.getHasAuthority()==OBConstant.AccountAuthorize.HASAUTHORIZE )
			{
				m_sbFrom.append(",Ob_bankacctprvgbyclient prv");
			}
			System.out.println("From "+m_sbFrom.toString());
			
			//where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and parentclient.nlevelid >= 1 ");
			m_sbWhere.append(" and childclient.nlevelcode like parentclient.nlevelcode || '%' ");
			m_sbWhere.append(" and childclient.nlevelcode != parentclient.nlevelcode ");
			m_sbWhere.append(" and childclient.id = acc.N_CLIENTID");
			m_sbWhere.append(" and childclient.nParentCorpID1 = directparent.id ");
			m_sbWhere.append(" and acc.N_RDSTATUS!=0 ");
			m_sbWhere.append(" and acc.N_CURRENCYTYPE= "+queryInfo.getCurrencyId());
			m_sbWhere.append(" and acc.N_OFFICEID= "+queryInfo.getOfficeId());
			if(queryInfo.getHasAuthority()==OBConstant.AccountAuthorize.HASAUTHORIZE )
			{
				m_sbWhere.append(" and parentclient.id = prv.clientid ");
				m_sbWhere.append(" and acc.N_ID = prv.accountid ");
			}
			if(queryInfo.getHasAuthority()==OBConstant.AccountAuthorize.NOAUTHORIZE)
			{
				m_sbWhere.append(" and parentclient.id||'##'||N_ID not in  ");
				m_sbWhere.append("  (select clientid||'##'||Accountid A from Ob_bankacctprvgbyclient) ");
			
				
			}
			
			if(queryInfo.getUserIDFrom()!=-1)
			{
				m_sbWhere.append(" and parentclient.sCode>='"+queryInfo.getUserNoFrom()+"'");
			}
			if(queryInfo.getUserIDTo()!=-1)
			{
				m_sbWhere.append(" and parentclient.sCode<='"+queryInfo.getUserNoTo()+"'");
			}
			System.out.println("Where "+m_sbWhere.toString());
			
			//order by
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by parentclient.sCode,childclient.sCode ");
			System.out.println(m_sbOrderBy);
			
			
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	//直接下级
	public void getBankUserDistributeSQLDirect(UserDistributeQueryInfo queryInfo) throws Exception
	{
		try
		{
			//select
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" distinct parentclient.sCode as superiorUserNo,parentclient.sName as superiorUserName,childclient.sCode as juniorUserNo,childclient.sName as juniorUserName,parentclient.sCode as directSuperiorUserNo,parentclient.sName  as directSuperiorUserName,acc.S_ACCOUNTNO  as accountNo,acc.S_BRANCHNAME as accountName,parentclient.id as superiorUserID,acc.N_ID accountID ");
			System.out.println("m_sbSelect "+m_sbSelect.toString());
			//from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" client parentclient, client childclient,bs_bankaccountinfo acc ");
			if(queryInfo.getHasAuthority()==OBConstant.AccountAuthorize.HASAUTHORIZE)
			{
				m_sbFrom.append(",Ob_bankacctprvgbyclient prv");
			}
			System.out.println("m_sbFrom "+m_sbFrom.toString());
			//where
			m_sbWhere = new StringBuffer();
			
			m_sbWhere.append(" childclient.nParentCorpID1 = parentclient.id ");
			m_sbWhere.append(" and childclient.id = acc.N_CLIENTID ");
			
			m_sbWhere.append(" and acc.N_RDSTATUS!=0 ");
			m_sbWhere.append(" and acc.N_ISDIRECTLINK=1");
			m_sbWhere.append(" and acc.N_CURRENCYTYPE= "+queryInfo.getCurrencyId());
			m_sbWhere.append(" and acc.N_OFFICEID= "+queryInfo.getOfficeId());
			if(queryInfo.getHasAuthority()==OBConstant.AccountAuthorize.HASAUTHORIZE)
			{
				m_sbWhere.append(" and parentclient.id = prv.clientid ");
				m_sbWhere.append(" and acc.N_ID = prv.accountid ");
			}
			if(queryInfo.getHasAuthority()==OBConstant.AccountAuthorize.NOAUTHORIZE)
			{
				m_sbWhere.append(" and parentclient.id||'##'||N_ID not in  ");
				m_sbWhere.append("  (select clientid||'##'||Accountid A from Ob_bankacctprvgbyclient) ");
			
				
			}
			
			if(queryInfo.getUserIDFrom()!=-1)
			{
				m_sbWhere.append(" and parentclient.sCode>='"+queryInfo.getUserNoFrom()+"'");
			}
			if(queryInfo.getUserIDTo()!=-1)
			{
				m_sbWhere.append(" and parentclient.sCode<='"+queryInfo.getUserNoTo()+"'");
			}
			System.out.println("Where "+m_sbWhere.toString());
			//order by
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by parentclient.sCode,childclient.sCode ");
			System.out.println(m_sbOrderBy);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	public String queryClientCodeByID(long id)throws Exception
	{
		String clientCode = "";
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try{
		conn = getConnection();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" select a.sCode from client a ");
		sbSQL.append(" where a.id="+id);
		
		
		ps = conn.prepareStatement(sbSQL.toString());
		rs = ps.executeQuery();
		while(rs.next())
		{
			clientCode = rs.getString("sCode");
		}
		
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		conn.close();
		conn = null;
		}
		catch(Exception e)
		{
			throw new IException("查询出现异常");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
		}
		
		return clientCode;
	}
	public void deleteAuthority (AcctPrvgByClientInfo info) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		
		try
		{
			conn = getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" delete from Ob_bankacctprvgbyclient ");
			sbSQL.append(" where clientid="+info.getClientID());
			sbSQL.append(" and accountid="+info.getAccountID());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.execute();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			
			
		}
		catch(Exception e)
		{
			throw new IException("数据库操作出现异常");
		}
		finally
		{
			try
			{
				
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
	  }
	
   }
	
}

