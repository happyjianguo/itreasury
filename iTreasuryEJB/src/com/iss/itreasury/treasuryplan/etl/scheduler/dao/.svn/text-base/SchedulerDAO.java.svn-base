/*
 * Created on 2004-9-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.etl.scheduler.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;


/**
 * @author yehuang
 *
 * Scheduler 模块中非资金计划模块的数据库查询集合类
 */
public class SchedulerDAO extends TreasuryPlanDAO {
	public SchedulerDAO(Connection conn){
		super(null,conn);
	}
	
	public Collection getNewClient(Timestamp openDate) throws Exception{
		ArrayList list = new ArrayList();
		String strOpenDate = transferTimestampToTo_DateString(openDate);
		String strSQL = " select scode,sname ,nsettclienttypeid From client where substr(dtinput,1,10)= "+strOpenDate+" and nstatusid=1 ";
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		while(localRS.next()){
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setClientCode(localRS.getString("scode"));
			clientInfo.setClientName(localRS.getString("sname"));
			clientInfo.setClientTypeID(localRS.getLong("nsettclienttypeid"));
			list.add(clientInfo);
		}
		finalizeDAO();
		return list;
		
	}

	public Collection getClientOfNewAccount(Timestamp openDate,long accountTypeID) throws Exception{
		ArrayList list = new ArrayList();
		String strOpenDate = transferTimestampToTo_DateString(openDate);
		String strSQL = " select b.scode,b.sname from sett_account a,client b  where a.naccounttypeid="+accountTypeID+" and substr(a.dtinput,1,10)= "+strOpenDate+" and a.ncheckstatusid=4 and a.nstatusid=1 and a.nclientid=b.id ";
				
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		while(localRS.next()){
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setClientName(localRS.getString("sname"));
			clientInfo.setClientCode(localRS.getString("scode"));
			list.add(clientInfo);
		}
		finalizeDAO();
		return list;
		
	}
	
	public Collection getNewLoanApply(Timestamp openDate,long loanType) throws Exception{
		ArrayList list = new ArrayList();
		String typeid = "";
		if(loanType == 1)
			typeid = "1,2,6,7,8";
		else if(loanType == 2){
			typeid = "3,4";
		}else if(loanType == 3){
			typeid = "5";
		}
		
		String strOpenDate = transferTimestampToTo_DateString(openDate);
		String strSQL = " select distinct b.scode from loan_loanForm a,client b where a.ntypeid in ("+loanType+") and a.nStatusID in (2,3,4) and substr(a.dtinputdate,1,10)= "+strOpenDate+" and a.nborrowclientid=b.id ";
		prepareStatement(strSQL);
		ResultSet localRS = executeQuery();
		while(localRS.next()){
			AccountInfo accountInfo = new AccountInfo();
			
			String code = localRS.getString("scode");
			
			list.add(code);
		}
		finalizeDAO();
		return list;
		
	} 
	

		
}
