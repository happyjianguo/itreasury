/*
 * Created on 2006-8-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetConditionInfo;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;


/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetDao extends ITreasuryDAO 
{
	public OBBudgetDao()
	{
		super("OB_BUDGET");
		super.setUseMaxID();
	}

	public OBBudgetDao(String tableName)
	{
		super(tableName);
		super.setUseMaxID();
	}

	public OBBudgetDao(String tableName, Connection conn)
	{
		super(tableName, conn);
		super.setUseMaxID();
	}
	 //
    public StringBuffer m_sbSelect = null;

    public StringBuffer m_sbFrom = null;

    public StringBuffer m_sbWhere = null;

    public StringBuffer m_sbOrderBy = null;

    /**
     * 获得modifyDate,因为findById方法获得的类型为年月日，这里需要精确到毫秒
     */
    public Timestamp getModifyDate(long id)throws Exception{
    	Timestamp modifyDate = null;
    	String strSql = "select modifydate from ob_budget where id = "+id;
    	try{
			initDAO();
			transPS = transConn.prepareStatement(strSql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
				modifyDate = transRS.getTimestamp("modifydate");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			finalizeDAO();
		}
    	return modifyDate;
    }
    
	/**
	 * 获得银行信息，用于自动任务付款
	 */
    public List getBankAccountInfo(long accountNo)throws Exception{
    	List list = new ArrayList();
    	StringBuffer sbSQL = new StringBuffer();
    	sbSQL.append("select a.n_id as id,a.s_accountno as accountno,a.s_accountname as accountname,b.s_name as branchname,b.s_branchareaseg1 as area1,b.s_branchareaseg2 as area2 from bs_bankaccountinfo a,bs_banksetting b where a.s_accountno = "+accountNo);
    	sbSQL.append(" and a.n_bankid = b.n_id");
    	try{
			initDAO();
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while(transRS.next()){
				BankAccountInfo info = new BankAccountInfo();
				info.setN_id(transRS.getLong("n_id"));
				info.setS_accountNo(transRS.getString("accountno"));
				info.setS_accountName(transRS.getString("accountname"));
				info.setS_branchName(transRS.getString("branchname"));
				info.setS_branchAreaSeg1(transRS.getString("area1"));
				info.setS_branchAreaSeg2(transRS.getString("area2"));
				list.add(info);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			finalizeDAO();
		}
    	return list;
    }
    
	/**
	 * 获得所有当天需要跑自动任务的记录
	 */
	public List getAllTodayAutoTask(long officeid,long currencyid)throws Exception{
		List list = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		String currentDate = Env.getSystemDateString();
		sbSQL.append("select o.id as id,o.accountid as accountid,o.amount as amount,s.branchid as branchid,o.clientid as clientid from ob_budget o,sett_autotask s where to_char(o.startdate,'yyyy-mm-dd') = '"+currentDate+"'");
		sbSQL.append(" and to_char(o.enddate,'yyyy-mm-dd')  = '"+currentDate+"'");
		sbSQL.append(" and o.officeid = "+officeid);
		sbSQL.append(" and o.currencyid = "+currencyid);
		sbSQL.append(" and o.accountid = s.accountid");
		sbSQL.append(" and o.status = 4");
		sbSQL.append(" and s.officeid = "+officeid);
		sbSQL.append(" and s.currencyid = "+currencyid);
		sbSQL.append(" and s.status = 1");
		try{
			initDAO();
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while(transRS.next()){
				OBBudgetInfo info = new OBBudgetInfo();
				info.setId(transRS.getLong("id"));
				info.setAccountID(transRS.getLong("accountid"));
				info.setAmount(transRS.getDouble("amount"));
				info.setBranchId(transRS.getLong("branchid"));
				info.setClientID(transRS.getLong("clientid"));
				list.add(info);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			finalizeDAO();
		}
		return list;
	}
    
    /**
     * 获得拨付失败的总记录数
     */
    public long getAllFailedDealCount(long officeid,long currencyid,long clientid)throws Exception{
    	long count = 0;
    	StringBuffer sbSQL = new StringBuffer();
    	sbSQL.append("select count(*) as count from ob_budget where officeid = "+officeid+" and currencyid = "+currencyid+" and clientid = "+clientid+" and status = "+OBConstant.OBBudgetStatus.FAILEDDEAL);
    	try{
    		initDAO();
    		transPS = transConn.prepareStatement(sbSQL.toString());
    		transRS = transPS.executeQuery();
    		while(transRS.next()){
    			count = transRS.getLong(1);
    		}
    	}catch(Exception e){
			e.printStackTrace();
		}finally{
			finalizeDAO();
		}
    	return count;
    }
    
	/**
	 * 复核预算　　只有“已提交”状态的,不是录入人的可以复核
	 * /检查此交易指令在复核前是否被修改或删除,只有确认的指令才能被复核
	 * @param ID
	 * @param checkuser
	 * @return
	 * @throws Exception
	 */
	public long check (long ID,long checkuser)throws Exception 
	{
		return -1;
	}

	/**
	 * 根据主记录ID查出所有的子记录
	 */
	public List findAllSubRecords(long id)throws Exception{
		List list = new ArrayList();
		List retList = new ArrayList();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select id,startdate,amount,status from ob_budget where parentbudgetid = " + id);
		try{
			initDAO();
			transPS = transConn.prepareStatement(sbSQL.toString());
			transRS = transPS.executeQuery();
			while(transRS.next()){
				OBBudgetInfo info = new OBBudgetInfo();
				info.setId(transRS.getLong(1));
				info.setStartdate(transRS.getTimestamp(2));
				info.setAmount(transRS.getDouble(3));
				info.setStatus(transRS.getLong(4));
				list.add(info);
			}
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					OBBudgetInfo retInfo = new OBBudgetInfo();
					retInfo = (OBBudgetInfo)findByID(((OBBudgetInfo)list.get(i)).getId(), retInfo.getClass());
					retList.add(retInfo);
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			finalizeDAO();
		}
		return retList;
	}
	
	/**
	 * 判断 相同账户、相同起始日期 状态不为“已拒绝，已删除”的纪录    是否存在
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public boolean isRepeat (OBBudgetInfo info)throws Exception
	{
	    Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_budget t where t.accountid=? " +
						 "and t.parentbudgetid = -1 and t.status not in("+OBConstant.OBBudgetStatus.DELETE+","+OBConstant.OBBudgetStatus.CHECK+")");
			System.out.println("参数:"+info.getAccountID());
			System.out.println("参数:"+info.getStartdate());
			System.out.println("isRepeat sql==="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,info.getAccountID());
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
			
			sbSQL.setLength(0);	
//			关闭数据库资源
			rs.close();  
			rs = null;
			ps.close();
			ps = null;
			if(bReturn)
			{
			    sbSQL.append(" select t.* from ob_budget t where t.accountid=? " +
			    		" and( ( t.startdate>=? and  t.startdate<=? ) or (t.enddate>=? and t.enddate<=?))"+
			            " and t.parentbudgetid = -1 and t.status not in("+OBConstant.OBBudgetStatus.DELETE+","+OBConstant.OBBudgetStatus.CHECK+")"); 
			    System.out.println("参数:"+info.getEnddate());
				System.out.println("参数:"+info.getStartdate());
				System.out.println("isRepeat2222 sql==="+sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1,info.getAccountID());
				ps.setTimestamp(2,info.getStartdate());
				ps.setTimestamp(3,info.getEnddate());
				ps.setTimestamp(4,info.getStartdate());
				ps.setTimestamp(5,info.getEnddate());
				rs = ps.executeQuery();
				if(!rs.next())
				{
				    bReturn = false;
				} 
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				e.printStackTrace();
				throw new Exception("Gen_E001");
			}
		}
		
        return bReturn;   
	}
	
	
	
	public boolean isStatus (long ID,long[] lStatus) throws Exception
	{
		String strStatusSql = "(";
		for(int i=0;i<lStatus.length;i++)
		{
			strStatusSql += String.valueOf(lStatus[i])+",";
		}
		strStatusSql = strStatusSql.substring(0,strStatusSql.length()-1)+")";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_budget t where t.id=? and t.status in "+strStatusSql);
			System.out.println("参数:"+ID);
			System.out.println("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
	}
	
	/**
	 * 修改状态
	 * @param ID
	 * @param lStatus
	 * @param lUserID
	 * @param strAction
	 * @return
	 * @throws Exception
	 */
	public long updateStatus(long ID, long lStatus, long lUserID, String strAction) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE OB_BUDGET SET status = ? ");
			if (strAction.equals("check"))
			{
				 sb.append(" , CHECKUSER=?,CHECKDATE=sysdate");
			}
			if (strAction.equals("cancelcheck"))
			{
				 sb.append(" , CHECKUSER=?,CHECKDATE=sysdate");
			}
			if (strAction.equals("delete"))
			{
				sb.append(" , MODIFYUSER=?, MODIFYDATE=sysdate ");
			}
			if (strAction.equals("authing"))
			{
				sb.append("");
			}
			if(strAction.equals("transfer"))
			{
			    sb.append("");
			}
			sb.append(" where id = ?");
			System.out.println("参数lStatus:"+lStatus);
			System.out.println("参数lUserID:"+lUserID);
			System.out.println("参数ID:"+ID);
			System.out.println("updateStatus sql==="+sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lStatus);
			if(!strAction.equals("authing") && !strAction.equals("transfer"))
			{
			    ps.setLong(nIndex++, lUserID);
			}
			ps.setLong(nIndex++, ID);
			
			lResult = ps.executeUpdate();
			 
			// 关闭数据库资源
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
 
		catch (Exception e)
		{
		 
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				 
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return lResult=ID;
		
	}
	
	/**
	 * update
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void update (OBBudgetInfo info)throws ITreasuryDAOException
	{
		try
		{ 
			super.initDAO();
			super.update(info);
			super.finalizeDAO();
		}catch(Exception ie)
		{
			ie.printStackTrace();
			super.finalizeDAO();
		}
		finally
		{
			super.finalizeDAO();
		}
		
	}
 
	


	/**判断记录是否是该人录入,是否是该人复核
	 * 
	 * @return
	 */
	public boolean isComfirmer(long ID,long User,String action)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		String subSql = "";
		if(action.equalsIgnoreCase("canclecheck"))
		{
			subSql = "checkuser";
		}
		else 
		{
			subSql = "inputuser";
		}
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_budget t where t.id=? and t."+subSql+"=?");
			System.out.println("参数ID:"+ID);
			System.out.println("参数User:"+User);
			System.out.println("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			ps.setLong(2,User);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
		
	}

	
	public long matching (OBBudgetInfo info,long userid)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		int index = 1;
		 
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.id from ob_budget t where t.SNAME=? and t.STARTDATE=? and t.ENDDATE=? and t.ACCOUNTID=?" +
						 " and t.AMOUNT=? and t.STATUS=? and t.INPUTUSER != ?");
			System.out.println("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setString(index++,info.getSname());
			ps.setTimestamp(index++,info.getStartdate());
			ps.setTimestamp(index++,info.getEnddate());
			ps.setLong(index++,info.getAccountID());
			ps.setDouble(index++,info.getAmount());
			ps.setLong(index++,info.getStatus());
			ps.setLong(index++,userid);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    lReturn = rs.getLong("id");
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return lReturn;	
	}
	
	
	public PageLoader query (OBBudgetConditionInfo info) throws Exception
	{
	    getBudgetInfoSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
        .getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
        .initPageLoader(
                new AppContext(),
                m_sbFrom.toString(),
                m_sbSelect.toString(),
                m_sbWhere.toString(),
                (int) Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo",
                null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
 
		return pageLoader;		
	}
	
	public PageLoader query(OBBudgetInfo info) throws Exception{
		return query(info,OBConstant.OBBudgetStatus.OBBUDGET);
	}
	
	public PageLoader query (OBBudgetInfo info,long queryType) throws Exception
	{
	    getBudgetInfoSQL(info,queryType);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
        .getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
        .initPageLoader(
                new AppContext(),
                m_sbFrom.toString(),
                m_sbSelect.toString(),
                m_sbWhere.toString(),
                (int) Constant.PageControl.CODE_PAGELINECOUNT,
                "com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo",
                null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
 
		return pageLoader;		
	}
	
	
	public void getBudgetInfoSQL(OBBudgetConditionInfo info)
    {
      
        m_sbSelect = new StringBuffer();
        // select
        m_sbSelect
                .append("   \n    t.id id,t.sname sname,t.accountid accountID,t.startdate startdate,t.enddate enddate,t.amount amount," +
					"t.inputuser inputuser,t.inputdate inputdate,t.status status,t.clientid clientID ,t.adjustid adjustid,t.REFUSEREASON refusereason,t.checkuser \n");

        // from
        m_sbFrom = new StringBuffer();

        m_sbFrom
                .append("  ob_budget t \n");

        // where
        m_sbWhere = new StringBuffer();
        m_sbWhere.append(" 1=1 ");
        if(info.getId()>0)
	    {
            m_sbWhere.append(" and t.id ="+info.getId());
	    }
        if(info.getClientID()>0)
        {
            m_sbWhere.append(" and t.clientid ="+info.getClientID());
        }
        if(info.getAccountID()>0)
	    {
            m_sbWhere.append(" and t.accountid ="+info.getAccountID());
        }
        if(info.getStartdate()!= null)
	    {
            m_sbWhere.append(" and t.startdate >=to_date('"+info.getStartdate().toString().substring(0,10)+"','yyyy-mm-dd')");
        }
        if(info.getEnddate()!=null)
	    {
            m_sbWhere.append(" and t.startdate <=to_date('"+info.getEnddate().toString().substring(0,10)+"','yyyy-mm-dd')");
	    }
	    m_sbWhere.append(" and t.parentbudgetid = -1 ");

        //
        m_sbOrderBy = new StringBuffer();
        
        m_sbOrderBy.append(" \n order by t.id desc,t.startdate desc");
           
    }
	
	public void getBudgetInfoSQL(OBBudgetInfo info,long queryType)
    {
      
        m_sbSelect = new StringBuffer();
        // select
        m_sbSelect
                .append("   \n    t.id id,t.sname sname,t.accountid accountID,t.startdate startdate,t.enddate enddate,t.amount amount," +
					"t.inputuser inputuser,t.inputdate inputdate,t.status status,t.clientid clientID ,t.adjustid adjustid,t.REFUSEREASON refusereason,t.checkuser \n");

        // from
        m_sbFrom = new StringBuffer();

        m_sbFrom
                .append("  ob_budget t \n");

        // where
        m_sbWhere = new StringBuffer();
        m_sbWhere.append(" 1=1 ");
        if(info.getId()>0)
	    {
            m_sbWhere.append(" and t.id ="+info.getId());
	    }
        if(info.getClientID()>0)
        {
            m_sbWhere.append(" and t.clientid ="+info.getClientID());
        }
        if(info.getAccountID()>0)
	    {
            m_sbWhere.append(" and t.accountid ="+info.getAccountID());
        }
        if(info.getStartdate()!= null)
	    {
            m_sbWhere.append(" and t.startdate >=to_date('"+info.getStartdate().toString().substring(0,10)+"','yyyy-mm-dd')");
        }
        if(info.getEnddate()!=null)
	    {
            m_sbWhere.append(" and t.startdate <=to_date('"+info.getEnddate().toString().substring(0,10)+"','yyyy-mm-dd')");
	    }
        if(info.getSname() != null && info.getSname().length() > 0){
        	String sname = info.getSname().replaceAll("'", "''");
        	m_sbWhere.append(" and t.sname like '%"+sname+"%'");
        }
        if(info.getOfficeId() > 0){
        	m_sbWhere.append(" and t.officeid = "+info.getOfficeId());
        }
        if(info.getCurrencyId() > 0){
        	m_sbWhere.append(" and t.currencyId = "+info.getCurrencyId());
        }
	    m_sbWhere.append(" and t.parentbudgetid = -1 ");
	    if(queryType == OBConstant.OBBudgetStatus.AUTOTASK){
	    	m_sbWhere.append(" and t.status in ("+OBConstant.OBBudgetStatus.APPROVE+","+OBConstant.OBBudgetStatus.DEAL+","+OBConstant.OBBudgetStatus.FAILEDDEAL+")");
	    	m_sbWhere.append(" and t.accountid in (select s.accountid from sett_autotask s where s.status = 1)");
	    }
	    
        //
        m_sbOrderBy = new StringBuffer();
        
        m_sbOrderBy.append(" \n order by t.id desc,t.startdate desc");
           
    }
    
	  //复核查找
	public Collection checkquery(long clientid,long userid) throws Exception
	{
		Collection result=null;
		try{
			initDAO();
			String sbSQL="select * from ob_budget where 1=1";
			sbSQL+=" and clientid=?";
			sbSQL+=" and ( (checkuser= ? and status=2)   or (inputuser!=? and status=1)   )";
			
			prepareStatement(sbSQL);
			transPS.setLong(1,clientid);
			transPS.setLong(2,userid);
			transPS.setLong(3,userid);
			System.out.println("**************复核查找SQL:"+sbSQL);
			executeQuery();
			result=getDataEntitiesFromResultSet(OBBudgetInfo.class);
		}
		catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalize();
			}
			catch (Throwable e)
			{
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return result;
	}
}


