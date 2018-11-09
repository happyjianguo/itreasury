/*
 * Created on 2006-2-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transupsave.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transupsave.dataentity.transupsaveinfo;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.util.DataFormat;


/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sett_TransUpRecordDAO extends SettlementDAO{
	
	public void add(transupsaveinfo info) throws ITreasuryDAOException{
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" INSERT INTO sett_transuprecord \n");
			buffer.append(" (ID,BSID,SETTID,TRANSACTIONTIME,STATUSID) \n");
			buffer.append(" VALUES ((SELECT nvl(MAX(id)+1,1) id FROM sett_transuprecord),?,?,?,?)");
			log.debug(buffer.toString());
			
			transPS = prepareStatement(buffer.toString());
			transPS.setLong(1,info.getBsID());
			transPS.setLong(2, info.getTransID());
			transPS.setDate(3, DataFormat.getDate(info.getTransactionTime().toString()) );
			transPS.setLong(4, info.getStatusID());
			executeUpdate();
			//System.out.println("执行成功！");
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	public void del(long id) throws ITreasuryDAOException{
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" delete sett_transuprecord \n");
			buffer.append(" where settid= \n"+id);
			log.debug(buffer.toString());
			
			transPS = prepareStatement(buffer.toString());			
			executeUpdate();
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	//根据条件查找表sett_transuprecord的记录
	public Collection findByCondition(transupsaveinfo info) throws ITreasuryDAOException
	{
		Vector v  = new Vector();
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT * FROM sett_transuprecord \n");
			buffer.append(" WHERE transActionTime >= TO_DATE(?,'yyyy-mm-dd') \n");
			buffer.append(" AND transActionTime <= TO_DATE(?,'yyyy-mm-dd') \n");
			
			
			//log.debug(buffer.toString());
			////modify by wjliu --2007-3-21 改为不输出SQL语句
			//transPS = prepareStatement(buffer.toString());
			transPS = prepareStatement(buffer.toString(),false);
			
			transPS.setString(1,info.getStartDt());
			transPS.setString(2,info.getEndDt());
			transRS = executeQuery();
			if (transRS != null)
			{
				while (transRS.next())
				{
					transupsaveinfo rinfo = new transupsaveinfo();
					rinfo.setBsID(transRS.getLong("bsid"));
					rinfo.setStatusID(transRS.getLong("statusid"));
					v.add(rinfo);
					//System.out.println(rinfo);
					
				}
			}
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
			finalizeDAO();			
		}
		
		return v;
	}
	
	public String getLastDate() throws ITreasuryDAOException
	{
		String dt = null;
		
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT transActionTime FROM sett_transuprecord \n");
			buffer.append(" ORDER BY transActionTime DESC \n");
			log.debug(buffer.toString());
			
			transPS = prepareStatement(buffer.toString());
			
			transRS = executeQuery();
			if (transRS != null)
			{
				if (transRS.next())
				{
					dt = String.valueOf(transRS.getDate(1));
				}
			}
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
			finalizeDAO();			
		}
		if(dt==null){
			dt="2006-01-01";
		}
		
		return dt;
	}
    //根据条件查找表bs_accthistransinfo的记录  modify by xwhe 2008-11-20
	public Collection findByUpRecordInfo(transupsaveinfo info) throws ITreasuryDAOException
	{
		Vector v  = new Vector();
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
		//	buffer.append(" SELECT * FROM bs_accthistransinfo \n");
		//	buffer.append(" WHERE DT_TURNIN >= TO_DATE(?,'yyyy-mm-dd') \n");
		//	buffer.append(" AND DT_TURNIN <= TO_DATE(?,'yyyy-mm-dd') \n");
		//	buffer.append(" AND n_istoturnin=1 \n");
		//	buffer.append(" AND n_turninresult=2 AND n_isdeletedbybank=0 \n");
			
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName \n");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctcurtransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+info.getStartDt()+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime <= TO_DATE('"+info.getEndDt()+"','yyyy-mm-dd') \n" );

			buffer.append("and a.n_turninresult=2 AND n_istoturnin=1 AND n_isdeletedbybank=0 \n");
			buffer.append("and c.nofficeid="+info.getOfficeID()+" and ncurrencyid="+info.getCurrencyid()+" )\n");
			buffer.append("union");
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName  \n ");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctHIstransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+info.getStartDt()+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime<= TO_DATE('"+info.getEndDt()+"','yyyy-mm-dd') \n" );
			
			buffer.append("and a.n_turninresult=2 AND n_istoturnin=1 AND n_isdeletedbybank=0 \n");
			buffer.append("and c.nofficeid="+info.getOfficeID()+" and ncurrencyid="+info.getCurrencyid()+") ");
			
			transPS = prepareStatement(buffer.toString(),false);			
		//	transPS.setString(1,info.getStartDt());
		//	transPS.setString(2,info.getEndDt());
		//	transPS.setString(3,info.getStartDt());
		//	transPS.setString(4,info.getEndDt());
		    transRS = executeQuery();
			if (transRS != null)
			{
				while (transRS.next())
				{
					transupsaveinfo rinfo = new transupsaveinfo();
					rinfo.setBsID(transRS.getLong("id"));
					rinfo.setStatusID(1);
					v.add(rinfo);				
				}
			}
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
			finalizeDAO();			
		}
		
		return v;
	}
//根据条件查找表bs_accthistransinfo的记录  modify by xwhe 2008-11-20
	public Collection findByUpFailRecordInfo(transupsaveinfo info) throws ITreasuryDAOException
	{
		Vector v  = new Vector();
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
	//		buffer.append(" SELECT * FROM bs_accthistransinfo \n");
	//		buffer.append(" WHERE DT_TURNIN >= TO_DATE(?,'yyyy-mm-dd') \n");
	//		buffer.append(" AND DT_TURNIN <= TO_DATE(?,'yyyy-mm-dd') \n");
	//		buffer.append(" AND n_istoturnin = 1 \n");
	//		buffer.append(" AND n_turninresult = 3 AND n_isdeletedbybank = 0 \n");
			
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName \n");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctcurtransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+info.getStartDt()+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime <= TO_DATE('"+info.getEndDt()+"','yyyy-mm-dd') \n" );
			buffer.append("and a.n_turninresult=3 AND n_istoturnin=1 AND n_isdeletedbybank=0 \n");
			buffer.append("and c.nofficeid="+info.getOfficeID()+" and ncurrencyid="+info.getCurrencyid()+" )\n");
			buffer.append("union");
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName  \n ");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctHIstransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+info.getStartDt()+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime<= TO_DATE('"+info.getEndDt()+"','yyyy-mm-dd') \n" );			
			buffer.append("and a.n_turninresult=3 AND n_istoturnin=1 AND n_isdeletedbybank=0 \n");
			buffer.append("and c.nofficeid="+info.getOfficeID()+" and ncurrencyid="+info.getCurrencyid()+") ");
			
			
			transPS = prepareStatement(buffer.toString(),false);			
	//		transPS.setString(1,info.getStartDt());
	//		transPS.setString(2,info.getEndDt());
			transRS = executeQuery();
			if (transRS != null)
			{
				while (transRS.next())
				{
					transupsaveinfo rinfo = new transupsaveinfo();
					rinfo.setBsID(transRS.getLong("id"));
					rinfo.setStatusID(3);
					v.add(rinfo);				
				}
			}
			
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
			finalizeDAO();			
		}
		
		return v;
	}
	
	public static void main(String [] args){
		Sett_TransUpRecordDAO dao=new Sett_TransUpRecordDAO();
		transupsaveinfo info =new transupsaveinfo();
		info.setStartDt("2006-10-04");
		info.setEndDt("2006-11-20");
		info.setBsID(20);
	try {
		dao.del(8093);
		//System.out.println("删除成功！");
	} catch (ITreasuryDAOException e) {
		// TODO 自动生成 catch 块
		e.printStackTrace();
	}
		
	}

}
