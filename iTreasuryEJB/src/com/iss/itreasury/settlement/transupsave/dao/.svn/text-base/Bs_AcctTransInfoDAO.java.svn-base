/*
 * Created on 2006-11-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transupsave.dao;

import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transupsave.dataentity.transupsaveinfo;
import java.util.Vector;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Bs_AcctTransInfoDAO extends SettlementDAO{
	
	//查询手工收款的交易
	//查询表bs_acctcurtransinfo和bs_acctHIstransinfo
	//查询收款方为结算中心账户的交易,注结算中心的账户可为多个，即info.getAccountNO()返回是一个以逗号分隔的字符串
	/**
	 * @param officeid
	 * @param currencyid
	 * @param accountNO
	 * @param StartDt
	 * @param EndDt
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByCondition(long officeid,long currencyid,String accountNO,String StartDt,String EndDt) throws ITreasuryDAOException
	{
		Vector v=null;
		v=new Vector();
		long bsID=-1;
		String accountNo = "";
		String accountName="";
		long currencyType=-1;
		String oppAccountNo="";
		String oppAccountName="";
		String oppBranchName="";
		double amount=0.0;
		long bankID=-1;
		String bankName="";
		Date transactionTime = null;
		String abstractInfo="";
		try
		{
			initDAO();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName \n");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctcurtransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+StartDt+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime <= TO_DATE('"+EndDt+"','yyyy-mm-dd') \n" );
			if(accountNO!=null&&accountNO.length()>0){
				buffer.append("and b.s_accountno='"+accountNO+"' \n");
				
			}
			buffer.append("and a.n_turninresult=1 \n");
			buffer.append("and c.nofficeid="+officeid+" and ncurrencyid="+currencyid+" )\n");
			buffer.append("union");
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName  \n ");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctHIstransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+StartDt+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime<= TO_DATE('"+EndDt+"','yyyy-mm-dd') \n" );
			
			if(accountNO!=null&&accountNO.length()>0){
				buffer.append("and b.s_accountno='"+accountNO+"'\n" );
			}
			buffer.append("and a.n_turninresult=1 \n");
			buffer.append("and c.nofficeid="+officeid+" and ncurrencyid="+currencyid+") ");
			
			//log.debug(buffer.toString());		
			
			
			//modify by wjliu --2007-3-21 改为不输出SQL语句
			//transPS = prepareStatement(buffer.toString());	
			transPS = prepareStatement(buffer.toString(),false);
			
			transRS = executeQuery();
		
			while (transRS.next())
			{
				transupsaveinfo info=new transupsaveinfo();	
				
				bsID=transRS.getLong("id");
				info.setBsID(bsID);
			
				accountNo = transRS.getString("accountno");
				if(accountNo!=null){
					info.setAccountNO(accountNo);
				}
			
				accountName=transRS.getString("accountname");
				if(accountName!=null){
					info.setAccountName(accountName);
				}
				
				currencyType=transRS.getLong("currencytype");
				info.setCurrencyid(currencyType);
			
				oppAccountNo=transRS.getString("oppaccountno");
				if(oppAccountNo!=null){
					info.setOppAccountNO(oppAccountNo);
				}
	
				oppAccountName=transRS.getString("oppaccountname");
				if(oppAccountName!=null){
					info.setOppAccountName(oppAccountName);
				}
			
				oppBranchName=transRS.getString("oppbranchname");
				if(oppBranchName!=null){
					info.setOppBranchName(oppBranchName);
				}
				
				//System.out.println("对方开户行名称："+oppBranchName);
				
				amount=transRS.getDouble("amount");
				info.setAmount(amount);
				
				bankID=transRS.getLong("bankID");
				info.setBankID(bankID);

				bankName=transRS.getString("bankName");
				if(bankName!=null){
					info.setBankName(bankName);
				}
					
				transactionTime=transRS.getDate("transactiontime");
				if(transactionTime!=null){
					info.setTransactionTime(transactionTime);
				}
				
				abstractInfo=transRS.getString("abstractinfo");
				if(abstractInfo!=null){
					info.setAbstractInfo(abstractInfo);
				}
				
				v.add(info);
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v.size()>0?v:null;
	}
	
	
//	查询入账成功的交易
	//查询表bs_acctcurtransinfo和bs_acctHIstransinfo
	//查询收款方为结算中心账户的交易,注结算中心的账户可为多个，即info.getAccountNO()返回是一个以逗号分隔的字符串
	/**
	 * @param officeid
	 * @param currencyid
	 * @param accountNO
	 * @param StartDt
	 * @param EndDt
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findBySuccessCondition(long officeid,long currencyid,String accountNO,String StartDt,String EndDt) throws ITreasuryDAOException
	{
		Vector v=null;
		v=new Vector();
		long bsID=-1;
		String accountNo = "";
		String accountName="";
		long currencyType=-1;
		String oppAccountNo="";
		String oppAccountName="";
		String oppBranchName="";
		double amount=0.0;
		long bankID=-1;
		String bankName="";
		Date transactionTime = null;
		String abstractInfo="";
		try
		{
			initDAO();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName \n");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctcurtransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+StartDt+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime <= TO_DATE('"+EndDt+"','yyyy-mm-dd') \n" );
			if(accountNO!=null&&accountNO.length()>0){
				buffer.append("and b.s_accountno='"+accountNO+"' \n");
				
			}
			buffer.append("and a.n_turninresult=2 \n");
			buffer.append("and c.nofficeid="+officeid+" and ncurrencyid="+currencyid+" )\n");
			buffer.append("union");
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName  \n ");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctHIstransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+StartDt+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime<= TO_DATE('"+EndDt+"','yyyy-mm-dd') \n" );
			
			if(accountNO!=null&&accountNO.length()>0){
				buffer.append("and b.s_accountno='"+accountNO+"'\n" );
			}
			buffer.append("and a.n_turninresult=2 \n");
			buffer.append("and c.nofficeid="+officeid+" and ncurrencyid="+currencyid+") ");
			
			//log.debug(buffer.toString());		
			
			
			//modify by wjliu --2007-3-21 改为不输出SQL语句
			//transPS = prepareStatement(buffer.toString());	
			transPS = prepareStatement(buffer.toString(),false);
			
			transRS = executeQuery();
		
			while (transRS.next())
			{
				transupsaveinfo info=new transupsaveinfo();	
				
				bsID=transRS.getLong("id");
				info.setBsID(bsID);
			
				accountNo = transRS.getString("accountno");
				if(accountNo!=null){
					info.setAccountNO(accountNo);
				}
			
				accountName=transRS.getString("accountname");
				if(accountName!=null){
					info.setAccountName(accountName);
				}
				
				currencyType=transRS.getLong("currencytype");
				info.setCurrencyid(currencyType);
			
				oppAccountNo=transRS.getString("oppaccountno");
				if(oppAccountNo!=null){
					info.setOppAccountNO(oppAccountNo);
				}
	
				oppAccountName=transRS.getString("oppaccountname");
				if(oppAccountName!=null){
					info.setOppAccountName(oppAccountName);
				}
			
				oppBranchName=transRS.getString("oppbranchname");
				if(oppBranchName!=null){
					info.setOppBranchName(oppBranchName);
				}
				
				//System.out.println("对方开户行名称："+oppBranchName);
				
				amount=transRS.getDouble("amount");
				info.setAmount(amount);
				
				bankID=transRS.getLong("bankID");
				info.setBankID(bankID);

				bankName=transRS.getString("bankName");
				if(bankName!=null){
					info.setBankName(bankName);
				}
					
				transactionTime=transRS.getDate("transactiontime");
				if(transactionTime!=null){
					info.setTransactionTime(transactionTime);
				}
				
				abstractInfo=transRS.getString("abstractinfo");
				if(abstractInfo!=null){
					info.setAbstractInfo(abstractInfo);
				}
				
				v.add(info);
				
					//System.out.println("accountid:"+accountid);
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v.size()>0?v:null;
	}
	
//	查询失败的交易
	//查询表bs_acctcurtransinfo和bs_acctHIstransinfo
	//查询收款方为结算中心账户的交易,注结算中心的账户可为多个，即info.getAccountNO()返回是一个以逗号分隔的字符串
	/**
	 * @param officeid
	 * @param currencyid
	 * @param accountNO
	 * @param StartDt
	 * @param EndDt
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findByFailCondition(long officeid,long currencyid,String accountNO,String StartDt,String EndDt) throws ITreasuryDAOException
	{
		Vector v=null;
		v=new Vector();
		long bsID=-1;
		String accountNo = "";
		String accountName="";
		long currencyType=-1;
		String oppAccountNo="";
		String oppAccountName="";
		String oppBranchName="";
		double amount=0.0;
		long bankID=-1;
		String bankName="";
		Date transactionTime = null;
		String abstractInfo="";
		try
		{
			initDAO();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName \n");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctcurtransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+StartDt+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime <= TO_DATE('"+EndDt+"','yyyy-mm-dd') \n" );
			if(accountNO!=null&&accountNO.length()>0){
				buffer.append("and b.s_accountno='"+accountNO+"' \n");
				
			}
			buffer.append("and a.n_turninresult=3 \n");
			buffer.append("and c.nofficeid="+officeid+" and ncurrencyid="+currencyid+" )\n");
			buffer.append("union");
			buffer.append(" (select a.n_id id, b.s_accountno accountno, b.s_accountname accountname,a.n_currencytype currencytype, a.s_oppaccountno oppaccountno,a.s_oppaccountname oppaccountname,a.s_oppbranchname oppbranchname,a.n_amount amount,a.dt_transactiontime transactiontime,a.s_abstractinfo abstractinfo,c.id bankID,c.sname bankName  \n ");
			buffer.append("from   bs_bankaccountinfo b ,bs_acctHIstransinfo a ,sett_branch  c \n");
			buffer.append("where b.n_id=a.n_accountid and b.s_accountNo=c.sbankaccountcode \n");
			buffer.append("and a.dt_transactiontime >= TO_DATE('"+StartDt+"','yyyy-mm-dd') \n" );
			buffer.append("and a.dt_transactiontime<= TO_DATE('"+EndDt+"','yyyy-mm-dd') \n" );
			
			if(accountNO!=null&&accountNO.length()>0){
				buffer.append("and b.s_accountno='"+accountNO+"'\n" );
			}
			buffer.append("and a.n_turninresult=3 \n");
			buffer.append("and c.nofficeid="+officeid+" and ncurrencyid="+currencyid+") ");
			
			//log.debug(buffer.toString());		
			
			
			//modify by wjliu --2007-3-21 改为不输出SQL语句
			//transPS = prepareStatement(buffer.toString());	
			transPS = prepareStatement(buffer.toString(),false);
			
			transRS = executeQuery();
		
			while (transRS.next())
			{
				transupsaveinfo info=new transupsaveinfo();	
				
				bsID=transRS.getLong("id");
				info.setBsID(bsID);
			
				accountNo = transRS.getString("accountno");
				if(accountNo!=null){
					info.setAccountNO(accountNo);
				}
			
				accountName=transRS.getString("accountname");
				if(accountName!=null){
					info.setAccountName(accountName);
				}
				
				currencyType=transRS.getLong("currencytype");
				info.setCurrencyid(currencyType);
			
				oppAccountNo=transRS.getString("oppaccountno");
				if(oppAccountNo!=null){
					info.setOppAccountNO(oppAccountNo);
				}
	
				oppAccountName=transRS.getString("oppaccountname");
				if(oppAccountName!=null){
					info.setOppAccountName(oppAccountName);
				}
			
				oppBranchName=transRS.getString("oppbranchname");
				if(oppBranchName!=null){
					info.setOppBranchName(oppBranchName);
				}
				
				//System.out.println("对方开户行名称："+oppBranchName);
				
				amount=transRS.getDouble("amount");
				info.setAmount(amount);
				
				bankID=transRS.getLong("bankID");
				info.setBankID(bankID);

				bankName=transRS.getString("bankName");
				if(bankName!=null){
					info.setBankName(bankName);
				}
					
				transactionTime=transRS.getDate("transactiontime");
				if(transactionTime!=null){
					info.setTransactionTime(transactionTime);
				}
				
				abstractInfo=transRS.getString("abstractinfo");
				if(abstractInfo!=null){
					info.setAbstractInfo(abstractInfo);
				}
				
				v.add(info);
				
					//System.out.println("accountid:"+accountid);
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v.size()>0?v:null;
	}
	
	//查询一条交易的详细信息
	//根据id先查询表bs_acctHIstransinfo如果没有找到记录再查询表bs_acctcurtransinfo
	public transupsaveinfo findByID(long id) throws ITreasuryDAOException
	{	
		transupsaveinfo info = new transupsaveinfo();
		String oppAccountNo="";
		String oppAccountName="";
		String oppBranchName="";
		Date	valuedate=null;
		Date transactionTime=null;
		double amount=0.0;
		long currencyType=-1;
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("(select s_oppaccountno,s_oppaccountname,n_currencytype,s_oppbranchname,dt_valuedate,n_amount,dt_transactiontime from bs_acctcurtransinfo where n_id="+id+")  \n");
			buffer.append("union \n");
			buffer.append("(select s_oppaccountno,s_oppaccountname,n_currencytype,s_oppbranchname,dt_valuedate,n_amount,dt_transactiontime from bs_acctHIstransinfo where n_id="+id+") ");
			
			//log.debug(buffer.toString());			
			transPS = prepareStatement(buffer.toString());		
			transRS = executeQuery();
			while(transRS.next())
			{
				oppAccountNo=transRS.getString("s_oppaccountno");
				oppAccountName=transRS.getString("s_oppaccountname");
				currencyType=transRS.getLong("n_currencytype");
				oppBranchName=transRS.getString("s_oppbranchname");
				transactionTime=transRS.getDate("dt_transactiontime");
				valuedate=transRS.getDate("dt_valuedate");
				amount=transRS.getDouble("n_amount");
				info.setOppAccountNO(oppAccountNo);
				info.setOppAccountName(oppAccountName);
				info.setCurrencyid(currencyType);
				info.setOppBranchName(oppBranchName);
				info.setTransactionTime(transactionTime);
				info.setValueDate(valuedate);
				info.setAmount(amount);
				info.setBsID(id);
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return info;
	}
	
	public static void main (String [] args){
		Bs_AcctTransInfoDAO d=new Bs_AcctTransInfoDAO();
		try {
			 d.findByCondition(2, 1, null, "2005-1-1", "2007-12-30");
		} catch (ITreasuryDAOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

}
