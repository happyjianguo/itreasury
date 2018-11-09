package com.iss.itreasury.settlement.comparisontrans.dao;

/**
 * @author yyhe
 * Date 2007-01-26
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.comparisontrans.dataentity.AccountInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.AccountTransInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.BankInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.ComparisonTransInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;

public class ComparisonTransDAO extends SettlementDAO {

	/**
	 * Constructor for Sett_BranchDAO.
	 * @param conn
	 */
	public ComparisonTransDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_branch";
	}

	/**
	 * 
	 */
	public ComparisonTransDAO()
	{
		super();
		this.strTableName = "sett_branch";
	}

	// 通过info查询记录
	public ArrayList queryAllSettTrans(ComparisonTransInfo info) throws SQLException {

		// 定义变量
		ArrayList aryResult = new ArrayList();

		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append(" SELECT ");
			strSQLQuery.append(" t.stransno stransno, ");
			strSQLQuery.append(" t.ntransdirection directtype, ");
			strSQLQuery.append(" t.mamount mamount, ");
			strSQLQuery.append(" t.dtexecute dtexecute ");
			strSQLQuery.append(" FROM ");
			strSQLQuery.append(" SETT_GLENTRY t ");
			strSQLQuery.append(" WHERE ");
			strSQLQuery.append(" t.ssubjectcode = '" + info.getSubjectCode() + "'") ;
			strSQLQuery.append(" and to_char(t.dtexecute,'yyyyMMdd')>='"+DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
			strSQLQuery.append(" and to_char(t.dtexecute,'yyyyMMdd')<='"+DataFormat.formatDate(info.getEndDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
			strSQLQuery.append(" and t.nstatusid > 0 ");
			strSQLQuery.append(" order by t.id ");

			System.out.println("通过info查询结算科目交易记录的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				ComparisonTransInfo cInfo = new ComparisonTransInfo();
				// 转换数据
				cInfo.setStransno(rs.getString("stransno"));
				cInfo.setDtexecute(rs.getDate("dtexecute"));
				cInfo.setAmount(rs.getDouble("mamount"));
				cInfo.setDirectType(rs.getLong("directtype"));

				// 加入结果集合
				aryResult.add(cInfo);
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		if(aryResult.size()>0)
		{
			return aryResult;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 查询结算需要和银企对账的交易信息
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public ArrayList selectAllSettTrans(ComparisonTransInfo info) throws SQLException {

		// 定义变量
		ArrayList aryResult = new ArrayList();

		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append(" select * \n");
			strSQLQuery.append(" from (SELECT detail.STRANSNO,decode(detail.ntransdirection,1,2,1) ntransdirection,detail.DTEXECUTE,detail.MAMOUNT,detail.SABSTRACT,bankaccount.S_ACCOUNTNO ACCOUNTNO,bankaccount.S_ACCOUNTNAME ACCOUNTNAME \n ");
			strSQLQuery.append(" FROM SETT_TRANSACCOUNTDETAIL detail, \n");
			strSQLQuery.append(" (  select t.n_subjectid accountID, T.S_ACCOUNTNO, T.S_ACCOUNTNAME from bs_bankaccountinfo t,sett_account acct,sett_accounttype accttype,(select N_ID parentID from bs_bankaccountinfo t where s_accountno = '"+ info.getBankAccountNo() +"' and N_ACCOUNTTYPE = 2 and n_rdstatus =1 and n_ischeck=1 and n_accountstatus=1) t1 where t.N_UPBANKACCTID = t1.parentID and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.N_ACCOUNTTYPE = 3 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.n_subjectid=acct.id  and acct.naccounttypeid=accttype.id and accttype.accountmodule=1) bankaccount \n");
			strSQLQuery.append("  WHERE detail.NTRANSACCOUNTID = bankaccount.accountID AND detail.NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+" AND detail.NTRANSACTIONTYPEID NOT IN ("+SETTConstant.TransactionType.BANKRECEIVE+", "+SETTConstant.TransactionType.BANKPAY+") AND TO_Char(detail.DTEXECUTE, 'yyyymmdd') >= '"+DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' AND TO_Char(detail.DTEXECUTE, 'yyyymmdd') <= '"+DataFormat.formatDate(info.getEndDate(),DataFormat.FMT_DATE_SPECIAL)+"' \n");
			strSQLQuery.append(" union all \n");
			strSQLQuery.append(" SELECT detail.STRANSNO,detail.NTRANSDIRECTION ntransdirection,detail.DTEXECUTE,detail.MAMOUNT,detail.SABSTRACT,bankaccount.S_ACCOUNTNO ACCOUNTNO,bankaccount.S_ACCOUNTNAME ACCOUNTNAME \n");
			strSQLQuery.append(" FROM SETT_TRANSACCOUNTDETAIL detail, \n");
			strSQLQuery.append(" (select t.n_subjectid accountID, T.S_ACCOUNTNO, T.S_ACCOUNTNAME from bs_bankaccountinfo t,sett_account acct,sett_accounttype accttype,(select N_ID parentID from bs_bankaccountinfo t  where s_accountno = '"+ info.getBankAccountNo() +"' and N_ACCOUNTTYPE = 2 and n_rdstatus =1 and n_ischeck=1 and n_accountstatus=1) t1 where t.N_UPBANKACCTID = t1.parentID and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.N_ACCOUNTTYPE = 3 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.n_subjectid=acct.id  and acct.naccounttypeid=accttype.id and accttype.accountmodule=1) bankaccount \n");
			strSQLQuery.append(" WHERE detail.NTRANSACCOUNTID = bankaccount.accountID AND detail.NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+"  AND detail.NTRANSACTIONTYPEID NOT IN ("+SETTConstant.TransactionType.BANKRECEIVE+", "+SETTConstant.TransactionType.BANKPAY+") AND detail.NOPPACCOUNTID NOT IN  (  select t.n_subjectid accountID from bs_bankaccountinfo t,sett_account acct,sett_accounttype accttype,(select N_ID parentID from bs_bankaccountinfo t where s_accountno = '"+ info.getBankAccountNo() +"' and N_ACCOUNTTYPE = 2 and n_rdstatus =1 and n_ischeck=1 and n_accountstatus=1) t1 \n");
			strSQLQuery.append(" where t.N_UPBANKACCTID = t1.parentID and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.N_ACCOUNTTYPE = 3 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.n_subjectid=acct.id and acct.naccounttypeid=accttype.id and accttype.accountmodule=1) AND TO_Char(detail.DTEXECUTE, 'yyyymmdd') >= '"+DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"'  AND TO_Char(detail.DTEXECUTE, 'yyyymmdd') <= '"+DataFormat.formatDate(info.getEndDate(),DataFormat.FMT_DATE_SPECIAL)+"' \n");
			strSQLQuery.append(" union all \n");
			strSQLQuery.append(" select stransno, ntransdirection,dtexecute, mamount,SABSTRACT,(select case when a.npayaccountid > 0 and (select accttype.accountmodule from sett_account     acct, sett_accounttype accttype  where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNO  from bs_bankaccountinfo t where t.n_subjectid = a.npayaccountid and t.n_rdstatus = 1 and t.n_ischeck = 1 and t.n_accountstatus = 1 and  t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.N_ACCOUNTTYPE = 3)" );
			strSQLQuery.append(" when a.nreceiveaccountid > 0 and (select accttype.accountmodule from sett_account     acct, sett_accounttype accttype where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id and a.nreceiveaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNO from bs_bankaccountinfo t  where t.n_subjectid = a.nreceiveaccountid and t.n_rdstatus = 1  and t.n_ischeck = 1 and t.n_accountstatus = 1  and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.N_ACCOUNTTYPE = 3)" );
			strSQLQuery.append(" else a.sextaccountno  end accountno from sett_transcurrentdeposit a where a.nstatusid = 3 and a.ntransactiontypeid in ("+SETTConstant.TransactionType.BANKRECEIVE+","+SETTConstant.TransactionType.BANKPAY+") and a.stransno = SETT_GLENTRY.stransno and a.ntransactiontypeid = SETT_GLENTRY.NTRANSACTIONTYPEID \n");
			strSQLQuery.append(" union all \n");
			strSQLQuery.append(" select case when a.npayaccountid > 0 and (select accttype.accountmodule from sett_account     acct, sett_accounttype accttype where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNO from bs_bankaccountinfo t where t.n_subjectid = a.npayaccountid and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.N_ACCOUNTTYPE = 3) \n");
			strSQLQuery.append(" when a.nreceiveaccountid > 0 and (select accttype.accountmodule  from sett_account     acct, sett_accounttype accttype where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id  and a.nreceiveaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNO from bs_bankaccountinfo t where t.n_subjectid = a.nreceiveaccountid and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.N_ACCOUNTTYPE = 3 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+") \n");
			strSQLQuery.append(" when a.npaybankid > 0 and a.npaybankid !=(select ID from sett_branch t where t.sbankaccountcode ='"+ info.getBankAccountNo() +"' and t.nstatusid = 1) then (select t.SBANKACCOUNTCODE from sett_branch t where t.ID = a.npaybankid and t.nstatusid = 1)  \n");
			strSQLQuery.append("  when a.nreceivebankid > 0 and  a.nreceivebankid != (select ID  from sett_branch t where t.sbankaccountcode = '"+ info.getBankAccountNo() +"' and t.nstatusid = 1) then (select t.SBANKACCOUNTCODE from sett_branch t where t.ID = a.nreceivebankid and t.nstatusid = 1)  \n");
			strSQLQuery.append(" when a. SEXTACCOUNTNO != null then a.SEXTACCOUNTNO  \n");
			strSQLQuery.append(" end ACCOUNTNO \n");
			strSQLQuery.append("  from sett_transspecialoperation a  \n");
			strSQLQuery.append(" where a.nstatusid = "+SETTConstant.TransactionStatus.CHECK+" and a.Stransno = SETT_GLENTRY.stransno and a.Ntransactiontypeid = SETT_GLENTRY.NTRANSACTIONTYPEID) ACCOUNTNO, \n");
			strSQLQuery.append(" (select case when a.npayaccountid > 0 and (select accttype.accountmodule from sett_account     acct, sett_accounttype accttype where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNAME from bs_bankaccountinfo t where t.n_subjectid = a.npayaccountid and t.n_rdstatus = 1 and t.n_ischeck = 1 and t.n_accountstatus = 1 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+" and t.N_ACCOUNTTYPE = 3) ");
			strSQLQuery.append("when a.nreceiveaccountid > 0 and (select accttype.accountmodule from sett_account     acct, sett_accounttype accttype where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id  and a.nreceiveaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNAME  from bs_bankaccountinfo t where t.n_subjectid = a.nreceiveaccountid and t.n_rdstatus = 1 and t.n_ischeck = 1 and t.n_accountstatus = 1 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+"  and t.N_ACCOUNTTYPE = 3)" );
			strSQLQuery.append(" else a.sextclientname end ACCOUNTNAME from sett_transcurrentdeposit a  where a.nstatusid = 3 and a.ntransactiontypeid in ("+SETTConstant.TransactionType.BANKRECEIVE+","+SETTConstant.TransactionType.BANKPAY+")  and a.stransno = SETT_GLENTRY.stransno and a.ntransactiontypeid = SETT_GLENTRY.NTRANSACTIONTYPEID \n");
			strSQLQuery.append(" union all \n");
			strSQLQuery.append(" select case when a.npayaccountid > 0 and (select accttype.accountmodule  from sett_account     acct,sett_accounttype accttype where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.nreceiveaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNAME from bs_bankaccountinfo t where t.n_subjectid = a.npayaccountid and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.N_ACCOUNTTYPE = 3 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+") \n");
			strSQLQuery.append(" when a.nreceiveaccountid > 0 and (select accttype.accountmodule from sett_account     acct, sett_accounttype accttype  where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id and a.nreceiveaccountid > 0) = 1 then (SELECT T.S_ACCOUNTNAME from bs_bankaccountinfo t where t.n_subjectid = a.nreceiveaccountid and t.n_rdstatus =1 and t.n_ischeck=1 and t.n_accountstatus=1 and t.N_ACCOUNTTYPE = 3 and t.n_officeId="+info.getOfficeID()+" and t.n_currencyType="+info.getCurrencyID()+") \n");
			strSQLQuery.append(" when a.npaybankid > 0 and a.npaybankid !=(select ID from sett_branch t where t.sbankaccountcode ='"+ info.getBankAccountNo() +"' and t.nstatusid = 1) then (select t.SENTERPRISENAME from sett_branch t where t.ID = a.npaybankid and t.nstatusid = 1)  \n");
			strSQLQuery.append("  when a.nreceivebankid > 0 and  a.nreceivebankid != (select ID  from sett_branch t where t.sbankaccountcode = '"+ info.getBankAccountNo() +"' and t.nstatusid = 1) then (select t.SENTERPRISENAME from sett_branch t where t.ID = a.nreceivebankid and t.nstatusid = 1)  \n");
			strSQLQuery.append(" when a. SEXTACCOUNTNO != null then a.SEXTCLIENTNAME  \n");
			strSQLQuery.append(" end ACCOUNTNAME \n");
			strSQLQuery.append("  from sett_transspecialoperation a  \n");
			strSQLQuery.append(" where a.nstatusid = "+SETTConstant.TransactionStatus.CHECK+" and a.Stransno = SETT_GLENTRY.stransno and a.Ntransactiontypeid = SETT_GLENTRY.NTRANSACTIONTYPEID) ACCOUNTNAME \n");
			strSQLQuery.append(" FROM SETT_GLENTRY  \n");
			strSQLQuery.append(" where ssubjectcode = '" + info.getSubjectCode() + "' and NSTATUSID = "+SETTConstant.TransactionStatus.CHECK+" AND TO_Char(DTEXECUTE, 'yyyymmdd') >= '"+DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' AND TO_Char(DTEXECUTE, 'yyyymmdd') <= '"+DataFormat.formatDate(info.getEndDate(),DataFormat.FMT_DATE_SPECIAL)+"')  \n");
			strSQLQuery.append(" order by DTEXECUTE, mamount,length(SABSTRACT) desc, SABSTRACT, ACCOUNTNO \n");
			
			System.out.println("通过info查询结算交易记录的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				ComparisonTransInfo cInfo = new ComparisonTransInfo();
				// 转换数据
				cInfo.setStransno(rs.getString("STRANSNO"));
				cInfo.setDtexecute(rs.getDate("DTEXECUTE"));
				cInfo.setAmount(rs.getDouble("MAMOUNT"));
				cInfo.setDirectType(rs.getLong("ntransdirection"));
				cInfo.setSabstract(rs.getString("SABSTRACT"));
				cInfo.setOppAccountNo(rs.getString("ACCOUNTNO"));
				cInfo.setOppAccountName(rs.getString("ACCOUNTNAME"));
				// 加入结果集合
				aryResult.add(cInfo);
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		if(aryResult.size()>0)
		{
			return aryResult;
		}
		else
		{
			return null;
		}
	}
	// 通过info查询记录,给出的查询条件有银行账户，查询交易时间范围，时间验证结束时间不等大于今天
	public ArrayList queryAllBankTrans(ComparisonTransInfo info) throws SQLException {

		// 定义变量
		ArrayList aryResult = new ArrayList();
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		//long acctID = -1;
		// 开始查询统计
		try {
			// 获取数据库连接
			conn = getConnection();
			/**
			 * 20070911修改: 根据账户编号查询资金监控中的账户ID
			 * 原因: 原来只根据银行ID来查,查不到上级账户为所选账户而对应的银行非所选的银行的记录,
			 * 这些记录也属于下级子户,分录也是记到对应的总户的过渡开户行科目
			 */
			/**String str = "select n_id from bs_bankaccountinfo where s_accountno = '" + info.getBankAccountNo() + "'";
			ps = conn.prepareStatement(str);
			rs = ps.executeQuery();
			while(rs.next())
			{
				acctID = rs.getLong("n_id");
			}
			rs = null;*/
			/** 
			 * 20070911修改结束
			 */
			StringBuffer strSQLQuery = new StringBuffer();
			//判断是否需要查询当日交易表
			System.out.println("查询的开始时间begin="+info.getStartDate()+";查询结束的时间end="+info.getStartDate());
			Date curSysTime=Env.getCurrentSystemDate();
			//Date curSysTime=new Date(System.currentTimeMillis());
			String appSqlStr = "";
	//20070911注释 
//			if(info.getBankID() > 0)
//			{
//				appSqlStr = "  and (accountInfo.n_bankId = " + info.getBankID() + " or accountInfo.n_upBankAcctID = 17 )" ;
//			}
			appSqlStr = appSqlStr + " and accountInfo.n_officeId = " + info.getOfficeID();
			appSqlStr = appSqlStr + " and accountInfo.n_currencyType = " + info.getCurrencyID();
			//判断有上级账户,即非集团总账户(只查银行子账户作为本方账户的记录,不查总账户作为本方账户的记录.因为暂时还没涉及到银行总账户对外的收款及付款.)
			
			//注释：将来做成可配置
			//appSqlStr = appSqlStr + " and accountInfo.n_upBankAcctID ="  + acctID ;
			appSqlStr = appSqlStr + " and accountInfo.s_Accountno ='"+ info.getBankAccountNo() +"'";
			
			if(IDate.compareDate(info.getStartDate(),curSysTime)==0)
			{
				System.out.println("查询的开始时间为系统的当前时间（读取的是数据库时间）");
				strSQLQuery.append(" SELECT ");
				strSQLQuery.append(" trans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" trans.dt_transactiontime transtime, ");
				strSQLQuery.append(" trans.n_amount amount, ");
				strSQLQuery.append(" trans.n_direction direction ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_acctcurtransinfo trans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" trans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and trans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" order by  trans.n_id ");
			}
			else if(IDate.compareDate(info.getEndDate(),curSysTime)==0)
			{
				System.out.println("查询的结束时间为系统的当前时间（读取的是数据库时间）");
				strSQLQuery.append(" SELECT * from (");
				strSQLQuery.append(" SELECT ");				
				strSQLQuery.append(" histrans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" histrans.dt_transactiontime transtime, ");
				strSQLQuery.append(" histrans.n_amount amount, ");
				strSQLQuery.append(" histrans.n_direction direction ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_accthistransinfo histrans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" histrans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyyMMdd') >='"+DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
				strSQLQuery.append(" and histrans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" order by  histrans.n_id ) a");
				strSQLQuery.append(" union ");
				strSQLQuery.append(" SELECT * from (");
				strSQLQuery.append(" SELECT ");
				strSQLQuery.append(" trans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" trans.dt_transactiontime transtime, ");
				strSQLQuery.append(" trans.n_amount amount, ");
				strSQLQuery.append(" trans.n_direction direction ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_acctcurtransinfo trans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" trans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and trans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" order by  trans.n_id ) b");
			}
			else
			{
				System.out.println("查询的时间范围为历史交易");
				strSQLQuery.append(" SELECT ");
				strSQLQuery.append(" histrans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" histrans.dt_transactiontime transtime, ");
				strSQLQuery.append(" histrans.n_amount amount, ");
				strSQLQuery.append(" histrans.n_direction direction ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_accthistransinfo histrans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" histrans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyyMMdd') >='" +DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyyMMdd') <='" +DataFormat.formatDate(info.getEndDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
			    strSQLQuery.append(" and histrans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" order by histrans.n_id ");
			}		
			System.out.println("通过账号:"+info.getBankAccountNo()+",查询银行账户记录的SQL:" + strSQLQuery.toString());
			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();
			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				ComparisonTransInfo cInfo = new ComparisonTransInfo();
				// 转换数据
				cInfo.setStransno(rs.getString("transnoofbank"));
				cInfo.setDtexecute(rs.getDate("transtime"));
				cInfo.setAmount(rs.getDouble("amount"));
				cInfo.setDirectType(rs.getLong("direction"));
				/**这里借贷方向是否需要调整**/
				// 加入结果集合
				aryResult.add(cInfo);
			}
		} finally { 
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		if(aryResult.size()>0)
		{
			return aryResult;
		}
		else
		{
			return null;
		}
	}
	/**
	 * 通过info查询记录,给出的查询条件有银行账户，查询交易时间范围，时间验证结束时间于今天
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public ArrayList selectAllBankTrans(ComparisonTransInfo info) throws SQLException {

		// 定义变量
		ArrayList aryResult = new ArrayList();
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		// 开始查询统计
		try {
			// 获取数据库连接
			conn = getConnection();
			/**
			 * 20070911修改: 根据账户编号查询资金监控中的账户ID
			 * 原因: 原来只根据银行ID来查,查不到上级账户为所选账户而对应的银行非所选的银行的记录,
			 * 这些记录也属于下级子户,分录也是记到对应的总户的过渡开户行科目
			 */
			StringBuffer strSQLQuery = new StringBuffer();
			//判断是否需要查询当日交易表
			System.out.println("查询的开始时间begin="+info.getStartDate()+";查询结束的时间end="+info.getStartDate());
			Date curSysTime=Env.getCurrentSystemDate();
			String appSqlStr = "";
			appSqlStr = appSqlStr + " and accountInfo.n_officeId = " + info.getOfficeID();
			appSqlStr = appSqlStr + " and accountInfo.n_currencyType = " + info.getCurrencyID();
			//判断有上级账户,即非集团总账户(只查银行子账户作为本方账户的记录,不查总账户作为本方账户的记录.因为暂时还没涉及到银行总账户对外的收款及付款.)
			appSqlStr = appSqlStr + " and accountInfo.s_Accountno ='"+ info.getBankAccountNo() +"'";
			
			if(IDate.compareDate(info.getStartDate(),curSysTime)==0)
			{
				System.out.println("查询的开始时间为系统的当前时间（读取的是数据库时间）");
				strSQLQuery.append(" SELECT ");
				strSQLQuery.append(" trans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" trans.dt_transactiontime transtime, ");
				strSQLQuery.append(" trans.n_amount amount, ");
				strSQLQuery.append(" trans.n_direction direction, ");
				strSQLQuery.append(" case when instr(trans.s_abstractinfo, '"+getSign()+"') = 1 then substr(trans.s_abstractinfo, 3, length(trans.s_abstractinfo)) else trans.s_abstractinfo end SABSTRACT,");
				strSQLQuery.append(" trans.S_OPPACCOUNTNO, ");
				strSQLQuery.append(" trans.s_oppaccountname ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_acctcurtransinfo trans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" trans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and trans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" order by to_char(trans.dt_transactiontime,'yyyy-mm-dd'),trans.n_amount,length(trans.s_Abstractinfo) desc,trans.s_Abstractinfo,trans.S_OPPACCOUNTNO ");
			}
			else if(IDate.compareDate(info.getEndDate(),curSysTime)==0)
			{
				System.out.println("查询的结束时间为系统的当前时间（读取的是数据库时间）");
				strSQLQuery.append(" SELECT * from (");
				strSQLQuery.append(" SELECT ");				
				strSQLQuery.append(" histrans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" histrans.dt_transactiontime transtime, ");
				strSQLQuery.append(" histrans.n_amount amount, ");
				strSQLQuery.append(" histrans.n_direction direction, ");
				strSQLQuery.append(" case when instr(histrans.s_abstractinfo, '"+getSign()+"') = 1 then substr(histrans.s_abstractinfo, 3, length(histrans.s_abstractinfo)) else histrans.s_abstractinfo end SABSTRACT,");
				strSQLQuery.append(" histrans.S_OPPACCOUNTNO, ");
				strSQLQuery.append(" histrans.s_oppaccountname ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_accthistransinfo histrans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" histrans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyyMMdd') >='"+DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
				strSQLQuery.append(" and histrans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" union ");
				strSQLQuery.append(" SELECT ");
				strSQLQuery.append(" trans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" trans.dt_transactiontime transtime, ");
				strSQLQuery.append(" trans.n_amount amount, ");
				strSQLQuery.append(" trans.n_direction direction, ");
				strSQLQuery.append(" case when instr(trans.s_abstractinfo, '"+getSign()+"') = 1 then substr(trans.s_abstractinfo, 3, length(trans.s_abstractinfo)) else trans.s_abstractinfo end SABSTRACT,");
				strSQLQuery.append(" trans.S_OPPACCOUNTNO, ");
				strSQLQuery.append(" trans.s_oppaccountname ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_acctcurtransinfo trans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" trans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and trans.n_isdeletedbybank != 1 " ) ;
				strSQLQuery.append(" )");
				strSQLQuery.append(" order by to_char(transtime,'yyyy-mm-dd'),amount,length(SABSTRACT) desc,SABSTRACT,S_OPPACCOUNTNO ");
				
			}
			else
			{
				System.out.println("查询的时间范围为历史交易");
				strSQLQuery.append(" SELECT ");
				strSQLQuery.append(" histrans.s_transnoofbank transnoofbank, ");
				strSQLQuery.append(" histrans.dt_transactiontime transtime, ");
				strSQLQuery.append(" histrans.n_amount amount, ");
				strSQLQuery.append(" histrans.n_direction direction, ");
				strSQLQuery.append(" case when instr(histrans.s_abstractinfo, '"+getSign()+"') = 1 then substr(histrans.s_abstractinfo, 3, length(histrans.s_abstractinfo)) else histrans.s_abstractinfo end SABSTRACT,");
				strSQLQuery.append(" histrans.S_OPPACCOUNTNO, ");
				strSQLQuery.append(" histrans.s_oppaccountname ");
				strSQLQuery.append(" FROM ");
				strSQLQuery.append(" bs_accthistransinfo histrans ,bs_bankaccountinfo accountInfo");
				strSQLQuery.append(" WHERE ");
				strSQLQuery.append(" histrans.n_accountid=accountInfo.n_Id and accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 ");
				strSQLQuery.append(appSqlStr);
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyyMMdd') >='" +DataFormat.formatDate(info.getStartDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyyMMdd') <='" +DataFormat.formatDate(info.getEndDate(),DataFormat.FMT_DATE_SPECIAL)+"' ");
			    strSQLQuery.append(" and histrans.n_isdeletedbybank != 1 " ) ;
			    strSQLQuery.append(" order by to_char(histrans.dt_transactiontime,'yyyy-mm-dd'),histrans.n_amount,length(histrans.s_Abstractinfo) desc,histrans.s_Abstractinfo,histrans.S_OPPACCOUNTNO ");
			}		
			System.out.println("通过账号:"+info.getBankAccountNo()+",查询银行账户记录的SQL:" + strSQLQuery.toString());
			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();
			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				ComparisonTransInfo cInfo = new ComparisonTransInfo();
				// 转换数据
				cInfo.setStransno(rs.getString("transnoofbank"));
				cInfo.setDtexecute(rs.getDate("transtime"));
				cInfo.setAmount(rs.getDouble("amount"));
				cInfo.setDirectType(rs.getLong("direction"));
				cInfo.setSabstract(rs.getString("SABSTRACT"));
				cInfo.setOppAccountNo(rs.getString("S_OPPACCOUNTNO"));
				cInfo.setOppAccountName(rs.getString("s_oppaccountname"));
				/**这里借贷方向是否需要调整**/
				// 加入结果集合
				aryResult.add(cInfo);
			}
		} finally { 
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		if(aryResult.size()>0)
		{
			return aryResult;
		}
		else
		{
			return null;
		}
	}
	public String getSign()
	{
		String strReturn=Config.getProperty(ConfigConstant.SETTLEMENT_BANKABSTRACT_SIGN,"ＣＷ");
		return strReturn;
	}
	//查询开户行信息
	public ComparisonTransInfo querySettBankAcctInfo(ComparisonTransInfo info) throws SQLException {

		// 定义变量
		ComparisonTransInfo accoutnInfo = null;
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();			
	
			StringBuffer strSQLQuery = new StringBuffer();
			
			strSQLQuery.append(" SELECT ");
			strSQLQuery.append(" t.ssegmentcode2 subjectcode ");
			strSQLQuery.append(" FROM ");
			strSQLQuery.append(" sett_glsubjectdefinition t ");
			strSQLQuery.append(" WHERE ");
			strSQLQuery.append(" t.ssegmentcode2 = '" + info.getSubjectCode()+"'");
			strSQLQuery.append(" and t.nstatus > 0 ");

			System.out.println("通过info查询科目信息是否有效的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				accoutnInfo = new ComparisonTransInfo();
				// 转换数据
				accoutnInfo.setSubjectCode(rs.getString("subjectcode"));
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		return accoutnInfo;

	}
	
	//查询银行账户信息
	public ComparisonTransInfo queryBankportalAcctInfo(ComparisonTransInfo info) throws SQLException {

		// 定义变量
		ComparisonTransInfo accoutnInfo = null;
		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			
			strSQLQuery.append(" SELECT ");
			strSQLQuery.append(" accountInfo.s_accountno accountno, ");
			strSQLQuery.append(" accountInfo.s_accountname accountname ");
			strSQLQuery.append(" FROM ");
			strSQLQuery.append(" bs_bankaccountinfo accountInfo");
			strSQLQuery.append(" WHERE ");
			strSQLQuery.append(" accountInfo.n_Ischeck=1 and accountInfo.n_Rdstatus=1 and accountInfo.s_accountno= '"+info.getBankAccountNo()+"'");

			System.out.println("通过info查询银行账户信息是否有效的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取查询结果
			while (rs.next()) {
				// 新建Bean对象
				accoutnInfo = new ComparisonTransInfo();
				// 转换数据
				accoutnInfo.setBankAccountNo(rs.getString("accountno"));
				accoutnInfo.setBankAccountName(rs.getString("accountname"));
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		return accoutnInfo;

	}
	/**
	 * 功能：返回电子对帐详细信息中账户信息及AccountID
	 * @param Transnoofbank
	 * @return
	 * @throws Exception
	 */
	public static AccountTransInfo findBankAccountNoByTransnoofbank(String  Transnoofbank) throws Exception
	{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		boolean bCloseConnection = false;
		AccountTransInfo transInfo = null;
		Connection conn=null;
		try
		{			
			conn = Database.getConnection();
			bCloseConnection = true;	
			
			strSQL = " SELECT  *";
			strSQL += " FROM  BS_ACCTHISTRANSINFO histrans ";
			strSQL += " WHERE ";
			strSQL += " histrans.s_transnoofbank = '"+ Transnoofbank +"'";
			strSQL += " union";
			strSQL += " SELECT  *";
			strSQL += " FROM BS_ACCTCURTRANSINFO accountInfo ";
			strSQL += " WHERE ";
			strSQL += " accountInfo.s_transnoofbank = '"+ Transnoofbank +"'";
			ps = conn.prepareStatement(strSQL);

			System.out.println("取电子对帐详细信息中账户信息及AccountIDSQL: " + strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				transInfo = new AccountTransInfo();
				transInfo.setAccountId(rs.getLong("n_accountid"));
				transInfo.setInterBranchName(rs.getString("s_InterBranchName"));
				transInfo.setBankId(rs.getLong("n_BankId"));
				transInfo.setRemarkInfo(rs.getString("s_RemarkInfo"));
				transInfo.setUseInfo(rs.getString("s_useinfo"));
				transInfo.setAbstractInfo(rs.getString("s_AbstractInfo"));
				transInfo.setTransactionTime(rs.getTimestamp("dt_TransactionTime"));
				transInfo.setDirection(rs.getLong("n_Direction"));
				transInfo.setCurrencyType(rs.getLong("n_CurrencyType"));
				transInfo.setAmount(rs.getDouble("n_Amount"));
				transInfo.setOppCountryId(rs.getLong("n_OppCountryId"));
				transInfo.setOppAddress(rs.getString("s_OppAddress"));
				transInfo.setOppBranchName(rs.getString("s_OppBranchName"));
				transInfo.setOppAccountName(rs.getString("s_OppAccountName"));
				transInfo.setOppAccountNO(rs.getString("s_OppAccountNo"));
			}
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
			if(conn != null)
			{
				conn.close();
				conn =null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return transInfo;
	}
	
	/**
	 * 功能：返回电子对帐详细信息所需BankID
	 * @param AccountID
	 * @return
	 * @throws Exception
	 */
	public static AccountInfo findBankidByAccountid(long AccountID) throws Exception
	{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		boolean bCloseConnection = false;
		AccountInfo acctInfo = null;
		Connection conn=null;
		try
		{			
			conn = Database.getConnection();
			bCloseConnection = true;	
			
			strSQL = " SELECT  *";
			strSQL += " FROM  BS_BANKACCOUNTINFO baci ";
			strSQL += " WHERE ";
			strSQL += " baci.n_id = '"+ AccountID +"'";	
			strSQL += " and baci.n_rdstatus>0";	
			ps = conn.prepareStatement(strSQL);
			//ps.setLong(1, AccountID);
			rs = ps.executeQuery();
			System.out.println("取电子对帐详细信息所需BankIDSQL: " + strSQL);
			if (rs.next())
			{
				//aInfo.setAccountNo("n_id");
				acctInfo = new AccountInfo();
				acctInfo.setBankId(rs.getLong("n_bankid"));
				acctInfo.setCountryId(rs.getLong("n_CountryId"));
				acctInfo.setAccountName(rs.getString("s_AccountName"));
				acctInfo.setAccountNo(rs.getString("s_AccountNo"));
			}
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
			if(conn != null)
			{
				conn.close();
				conn =null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return acctInfo;
	}
	/**
	 * 功能：返回电子对帐详细信息所需银行信息
	 * @param BankID
	 * @return
	 * @throws Exception
	 */
	public static BankInfo findBankByBankID(long BankID) throws Exception
	{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		boolean bCloseConnection = false;
		BankInfo bankInfo = null;
		Connection conn=null;
		try
		{			
			conn = Database.getConnection();
			bCloseConnection = true;	
			
			strSQL = " SELECT  *";
			strSQL += " FROM  BS_BANKSETTING bs ";
			strSQL += " WHERE ";
			strSQL += " bs.n_id = '"+ BankID +"'";
			strSQL += " and bs.n_rdstatus>0";	
			ps = conn.prepareStatement(strSQL);
			//ps.setLong(1, BankID);
			rs = ps.executeQuery();
			System.out.println("取电子对帐详细信息所需银行信息SQL: " + strSQL);
			if (rs.next())
			{
				//bInfo.setAccountNo("n_id");
				bankInfo = new BankInfo();
				bankInfo.setBranchAreaSeg1(rs.getString("s_BranchAreaSeg1"));
				bankInfo.setBranchAreaSeg2(rs.getString("s_BranchAreaSeg2"));
				bankInfo.setBranchAreaSeg3(rs.getString("s_BranchAreaSeg3"));
				bankInfo.setName(rs.getString("s_Name"));
				bankInfo.setName_en(rs.getString("s_Name_en"));
			}
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
			if(conn != null)
			{
				conn.close();
				conn =null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return bankInfo;
	}
	
	/** 
	 * 新增(根据账号查询账户信息)
	 * @param accountNo
	 * @return
	 * @throws Exception
	 */
	public static AccountInfo findBankidByAccountNo(String accountNo) throws Exception
	{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		boolean bCloseConnection = false;
		AccountInfo acctInfo = null;
		Connection conn=null;
		try
		{			
			conn = Database.getConnection();
			bCloseConnection = true;	
			
			strSQL = " SELECT  *";
			strSQL += " FROM  BS_BANKACCOUNTINFO baci ";
			strSQL += " WHERE ";
			strSQL += " baci.s_accountno like  '"+ accountNo +"'";	
			strSQL += " and baci.n_rdstatus>0";	
			ps = conn.prepareStatement(strSQL);
			//ps.setLong(1, AccountID);
			rs = ps.executeQuery();
			System.out.println("取电子对帐详细信息所需BankIDSQL: " + strSQL);
			if (rs.next())
			{
				//aInfo.setAccountNo("n_id");
				acctInfo = new AccountInfo();
				acctInfo.setId(rs.getLong("n_id"));
				acctInfo.setBankId(rs.getLong("n_bankid"));
				acctInfo.setCountryId(rs.getLong("n_CountryId"));
				acctInfo.setAccountName(rs.getString("s_AccountName"));
				acctInfo.setAccountNo(rs.getString("s_AccountNo"));
			}
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
			if(conn != null)
			{
				conn.close();
				conn =null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return acctInfo;
	}
	
	public  static void main(String args[])
	{
		
		ComparisonTransDAO dao = new ComparisonTransDAO();
		AccountTransInfo info = new AccountTransInfo();
		try {
			info = dao.findBankAccountNoByTransnoofbank("T-224890014");
			System.out.println(info);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

