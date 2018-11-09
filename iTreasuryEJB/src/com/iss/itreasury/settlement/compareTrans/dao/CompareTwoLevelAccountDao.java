package com.iss.itreasury.settlement.compareTrans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountCondtion;
import com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountDetailCondtion;
import com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountDetailResultInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.ComparisonTransInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.system.dao.PageLoader;

public class CompareTwoLevelAccountDao extends SettlementDAO{
	private StringBuffer m_sbSelect  = null;
	
	private StringBuffer m_sbFrom    = null;
	
	private StringBuffer m_sbWhere   = null;
	
	private StringBuffer m_sbOrderBy = null;
	/**
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader compareTwoLevelAccountTrans(CompareTwoLevelAccountCondtion queryInfo)throws Exception
	{
		    compareTwoLevelAccountTransSQL(queryInfo);
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	        pageLoader.initPageLoader(new AppContext(),
					                  m_sbFrom.toString(),
					                  m_sbSelect.toString(),
					                  m_sbWhere.toString(),
					                  (int) Constant.PageControl.CODE_PAGELINECOUNT,
					                  "com.iss.itreasury.settlement.compareTrans.dataentity.CompareTwoLevelAccountResultInfo",
					                  null);
	        pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
        return pageLoader;
	}
	
	private void compareTwoLevelAccountTransSQL(CompareTwoLevelAccountCondtion queryInfo)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" setttrans.accountID,setttrans.accountno,setttrans.accountname,setttrans.balance ,setttrans.interestbalance,setttrans.dtdate settdtdate, banktrans.bankaccountid,banktrans.bankaccountno,banktrans.bankaccountname,banktrans.dt_date bankdtdate,banktrans.bankbalance \n");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" (select settaccount.accountID, \n");
		m_sbFrom.append(" settaccount.accountno, \n");
		m_sbFrom.append(" settaccount.accountname, \n");
		m_sbFrom.append(" dailybalance.balance, \n");
		m_sbFrom.append(" dailybalance.interestbalance, \n");
		m_sbFrom.append(" dailybalance.dtdate \n");
		m_sbFrom.append(" from (select daily.naccountid, \n");
		m_sbFrom.append("  to_char(daily.dtdate,'yyyy-mm-dd') dtdate, \n");
		m_sbFrom.append(" sum(daily.mbalance) balance, \n");
		m_sbFrom.append(" sum(daily.minterestbalance+daily.AC_MNEGOTIATEBALANCE) interestbalance \n");
		m_sbFrom.append(" from sett_dailyaccountbalance daily \n");
		m_sbFrom.append("  where daily.naccountstatusid > 0 \n");
		m_sbFrom.append(" and daily.nsubaccountid > 0 \n");
		m_sbFrom.append("  group by daily.naccountid, daily.dtdate \n");
		m_sbFrom.append(" union  \n");
		m_sbFrom.append("  select subacct.naccountid, \n");
		m_sbFrom.append(" '"+Env.getSystemDateString(queryInfo.getOfficeID(),queryInfo.getCurrencyID())+"', \n");
		m_sbFrom.append(" subacct.mbalance balance, \n");
		m_sbFrom.append(" subacct.mbalance interestbalance \n");
		m_sbFrom.append(" from sett_subaccount subacct \n");
		m_sbFrom.append(" where subacct.nstatusid > 0 \n");
		m_sbFrom.append(" ) dailybalance, \n");
		m_sbFrom.append(" (select acct.id         accountID, \n");
		m_sbFrom.append(" acct.saccountno accountNo, \n");
		m_sbFrom.append(" acct.sname      accountName \n");
		m_sbFrom.append(" from sett_account acct, sett_accounttype accttype,client t \n");
		m_sbFrom.append(" where acct.naccounttypeid = accttype.id \n");
		m_sbFrom.append(" and  acct.nclientid = t.id \n");
		if(queryInfo.getClientIDStart()>0)
		{
			m_sbFrom.append("  and t.SCODE >= '"+queryInfo.getClientIDStartCtrl()+"' \n");
		}
		if(queryInfo.getClientIDEnd()>0)
		{
			m_sbFrom.append("  and t.SCODE <= '"+queryInfo.getClientIDEndCtrl()+"' \n");
		}
		m_sbFrom.append(" and accttype.accountmodule = "+SETTConstant.AccountModule.TWOLEVEL+" \n");
		m_sbFrom.append(" and acct.nstatusid > 0 \n");
		m_sbFrom.append(" and acct.ncheckstatusid = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		m_sbFrom.append(" and accttype.nstatusid = "+Constant.RecordStatus.VALID+" \n");
		m_sbFrom.append(" and acct.nofficeid = "+queryInfo.getOfficeID()+" \n");
		m_sbFrom.append(" and acct.ncurrencyid = "+queryInfo.getCurrencyID()+") settaccount \n");
		m_sbFrom.append("  where dailybalance.naccountid = settaccount.accountID) setttrans, \n");
		m_sbFrom.append(" (select bankaccount.bankaccountid, \n");
		m_sbFrom.append(" bankaccount.bankaccountno, \n");
		m_sbFrom.append(" bankaccount.bankaccountname, \n");
		m_sbFrom.append(" bankaccount.accountid, \n");
		m_sbFrom.append(" bankdailybalance.dt_date, \n");
		m_sbFrom.append(" bankdailybalance.bankbalance \n");
		m_sbFrom.append(" from (select bankhisbalance.n_accountid, \n");
		m_sbFrom.append(" to_char(bankhisbalance.dt_date, 'yyyy-mm-dd') dt_date, \n");
		m_sbFrom.append(" sum(bankhisbalance.n_balance) bankbalance \n");
		m_sbFrom.append(" from bs_accthisbalance bankhisbalance \n");
		m_sbFrom.append(" group by bankhisbalance.n_accountid, \n");
		m_sbFrom.append("  to_char(bankhisbalance.dt_date, 'yyyy-mm-dd') \n");
		m_sbFrom.append(" union  \n");
		m_sbFrom.append(" select bankcurbalance.n_accountid, \n");
		m_sbFrom.append(" to_char(bankcurbalance.dt_importtime, 'yyyy-mm-dd') dt_date, \n");
		m_sbFrom.append(" sum(bankcurbalance.n_balance) bankbalance \n");
		m_sbFrom.append(" from bs_acctcurbalance bankcurbalance \n");
		m_sbFrom.append(" group by bankcurbalance.n_accountid, \n");
		m_sbFrom.append(" to_char(bankcurbalance.dt_importtime, 'yyyy-mm-dd') \n");
		m_sbFrom.append(" ) bankdailybalance, \n");
		m_sbFrom.append(" (select bankacct.n_id          bankaccountID, \n");
		m_sbFrom.append(" bankacct.s_accountno   bankaccountNo, \n");
		m_sbFrom.append(" bankacct.s_accountname bankaccountName, \n");
		m_sbFrom.append(" bankacct.n_subjectid   accountID \n");
		m_sbFrom.append(" from bs_bankaccountinfo bankacct \n");
		m_sbFrom.append(" where bankacct.n_Ischeck = 1 \n");
		m_sbFrom.append(" and bankacct.n_Rdstatus = 1 \n");
		m_sbFrom.append(" and bankacct.n_officeId = "+queryInfo.getOfficeID()+" \n");
		m_sbFrom.append(" and bankacct.n_currencyType = "+queryInfo.getCurrencyID()+") bankaccount \n");
		m_sbFrom.append(" where bankdailybalance.n_accountid = bankaccount.bankaccountid) banktrans \n");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  setttrans.accountID = banktrans.accountid(+) \n");
		m_sbWhere.append("  and setttrans.dtdate = banktrans.dt_date(+) \n");
		m_sbWhere.append("  and setttrans.dtdate >= '"+queryInfo.getStartDate()+"' \n");
		m_sbWhere.append("  and setttrans.dtdate <= '"+queryInfo.getEndDate()+"' \n");
		if(queryInfo.getStartAccountID()>0)
		{
			m_sbWhere.append("  and setttrans.accountno >= '"+queryInfo.getStartAccountIDCtrl()+"' \n");
		}
		if(queryInfo.getEndAccountID()>0)
		{
			m_sbWhere.append("  and setttrans.accountno <= '"+queryInfo.getEndAccountIDCtrl()+"' \n");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("  order by setttrans.dtdate desc, setttrans.accountno \n");
		
	}
	
	

	/**
	 * ��ѯ�������˻�ID����ĳ���ڽ���
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public ArrayList queryAllSettTwoLevelAccountTrans(CompareTwoLevelAccountDetailCondtion info) throws SQLException {

		// �������
		ArrayList aryResult = new ArrayList();
		// �������ݿ��ѯ����
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		// ��ʼ��ѯͳ��
		try {

			// ��ȡ���ݿ�����
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			strSQLQuery.append(" SELECT STRANSNO,ntransdirection,MAMOUNT,SABSTRACT,OPPACCOUNTNO,OPPACCOUNTNAME,DTEXECUTE from \n");
			strSQLQuery.append(" (  \n");
			strSQLQuery.append(" SELECT  \n");
			strSQLQuery.append(" A.STRANSNO, \n");
			strSQLQuery.append(" A.ntransdirection, \n");
			strSQLQuery.append(" A.MAMOUNT, \n");
			strSQLQuery.append(" A.SABSTRACT, \n");
			//���ݶ��˷�ʽ
			if(info.getCompareType()==SETTConstant.CompareTwoLevelType.BALANCE)
			{
				strSQLQuery.append(" A.DTEXECUTE, \n");
			}
			else
			{
				strSQLQuery.append(" A.DTINTERESTSTART DTEXECUTE, \n");
			}
			strSQLQuery.append(" CASE  \n");
			strSQLQuery.append(" WHEN A.NOPPACCOUNTID > 0 AND (select accttype.accountmodule from sett_account acct, sett_accounttype accttype where acct.naccounttypeid = accttype.id  and acct.id = A.NOPPACCOUNTID) = 1 \n");
			strSQLQuery.append(" THEN (select bankaccount.s_accountno from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = A.NOPPACCOUNTID)  \n");
			strSQLQuery.append(" WHEN A.NTRANSACCOUNTID > 0 AND A.ntransactiontypeid not in("+SETTConstant.TransactionType.BANKPAY+","+SETTConstant.TransactionType.BANKRECEIVE+")  \n");
			strSQLQuery.append(" THEN (select bankaccount.s_accountno from bs_bankaccountinfo bankaccount where bankaccount.n_id = (select bankaccount.n_upbankacctid from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = A.NTRANSACCOUNTID))  \n");
			strSQLQuery.append(" ELSE A.OPPACCOUNTNO END  OPPACCOUNTNO, \n");
			strSQLQuery.append(" CASE  \n");
			strSQLQuery.append(" WHEN A.NOPPACCOUNTID > 0 AND (select accttype.accountmodule from sett_account acct, sett_accounttype accttype where acct.naccounttypeid = accttype.id  and acct.id = A.NOPPACCOUNTID) = 1 \n");
			strSQLQuery.append(" THEN (select bankaccount.S_ACCOUNTNAME from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = A.NOPPACCOUNTID)  \n");
			strSQLQuery.append(" WHEN A.NTRANSACCOUNTID > 0 AND A.ntransactiontypeid not in("+SETTConstant.TransactionType.BANKPAY+","+SETTConstant.TransactionType.BANKRECEIVE+")  \n");
			strSQLQuery.append(" THEN (select bankaccount.S_ACCOUNTNAME from bs_bankaccountinfo bankaccount where bankaccount.n_id = (select bankaccount.n_upbankacctid from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = A.NTRANSACCOUNTID))  \n");
			strSQLQuery.append(" ELSE A.OPPACCOUNTNAME END  OPPACCOUNTNAME \n");
			strSQLQuery.append(" FROM SETT_TRANSACCOUNTDETAIL A,SETT_ACCOUNT ACCT,SETT_ACCOUNTTYPE ACCTTYPE \n");
			strSQLQuery.append(" WHERE A.NTRANSACCOUNTID = "+info.getAccountID()) ;
			if(info.getCompareType()==SETTConstant.CompareTwoLevelType.BALANCE)
			{
				strSQLQuery.append(" AND TO_Char(A.DTEXECUTE, 'yyyy-mm-dd') >='"+info.getStartDate()+"'");
				strSQLQuery.append(" AND TO_Char(A.DTEXECUTE, 'yyyy-mm-dd') <='"+info.getEndDate()+"'");
			}
			else
			{
				strSQLQuery.append(" AND TO_Char(A.DTINTERESTSTART, 'yyyy-mm-dd') >='"+info.getStartDate()+"'");
				strSQLQuery.append(" AND TO_Char(A.DTINTERESTSTART, 'yyyy-mm-dd') <='"+info.getEndDate()+"'");
			}
			
			strSQLQuery.append(" AND A.NSTATUSID = "+SETTConstant.TransactionStatus.CHECK);
			strSQLQuery.append(" AND A.NTRANSACCOUNTID=ACCT.ID AND ACCT.NACCOUNTTYPEID=ACCTTYPE.ID \n");
			strSQLQuery.append(" AND ACCTTYPE.ACCOUNTMODULE="+SETTConstant.AccountModule.TWOLEVEL);
			strSQLQuery.append(" AND A.ntransactiontypeid !="+SETTConstant.TransactionType.SPECIALOPERATION);
			strSQLQuery.append(" union \n");
			strSQLQuery.append(" select a.stransno STRANSNO, \n");
			strSQLQuery.append(" case when a.npayaccountid = "+info.getAccountID()+" then  1 when a.nreceiveaccountid = "+info.getAccountID()+" then 2 end ntransdirection, \n");
			strSQLQuery.append(" a.mpayamount MAMOUNT, \n");
			strSQLQuery.append(" a.sabstract SABSTRACT, \n");
			if(info.getCompareType()==SETTConstant.CompareTwoLevelType.BALANCE)
			{
				strSQLQuery.append(" A.DTEXECUTE, \n");
			}
			else
			{
				strSQLQuery.append(" A.DTINTERESTSTART DTEXECUTE, \n");
			}
			strSQLQuery.append(" case \n");
			strSQLQuery.append(" when a.npayaccountid > 0 and a.npayaccountid != "+info.getAccountID()+" and (select accttype.accountmodule from sett_account acct, sett_accounttype accttype  where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) = "+SETTConstant.AccountModule.TWOLEVEL+" \n");
			strSQLQuery.append(" then (select bankaccount.s_accountno from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.npayaccountid)  \n");
			strSQLQuery.append(" when a.npayaccountid > 0 and a.npayaccountid != "+info.getAccountID()+" and (select accttype.accountmodule from sett_account acct, sett_accounttype accttype where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) != "+SETTConstant.AccountModule.TWOLEVEL+"  \n");
			strSQLQuery.append(" then (select bankaccount.s_accountno from bs_bankaccountinfo bankaccount where bankaccount.n_id = (select bankaccount.n_upbankacctid  from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.nreceiveaccountid))  \n");
			strSQLQuery.append(" when a.nreceiveaccountid > 0 and a.nreceiveaccountid != "+info.getAccountID()+" and (select accttype.accountmodule from sett_account acct, sett_accounttype accttype where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id and a.nreceiveaccountid > 0) = "+SETTConstant.AccountModule.TWOLEVEL+"  \n");
			strSQLQuery.append(" then (select bankaccount.s_accountno from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.nreceiveaccountid)  \n");
			strSQLQuery.append(" when a.nreceiveaccountid > 0 and a.nreceiveaccountid != "+info.getAccountID()+" and  (select accttype.accountmodule from sett_account acct, sett_accounttype accttype  where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id  and a.nreceiveaccountid > 0) != "+SETTConstant.AccountModule.TWOLEVEL+"  \n");
			strSQLQuery.append(" then (select bankaccount.s_accountno  from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.npayaccountid)  \n");
			strSQLQuery.append(" when a.npaybankid > 0 then (select t.SBANKACCOUNTCODE from sett_branch t where t.ID = a.npaybankid  and t.nstatusid = 1)  \n");
			strSQLQuery.append(" when a.nreceivebankid > 0 then (select t.SBANKACCOUNTCODE  from sett_branch t where t.ID = a.nreceivebankid and t.nstatusid = 1)  \n");
			strSQLQuery.append(" when a. SEXTACCOUNTNO != null then a.SEXTACCOUNTNO  \n");
			strSQLQuery.append(" end OPPACCOUNTNO, \n");
			strSQLQuery.append(" case \n");
			strSQLQuery.append(" when a.npayaccountid > 0 and a.npayaccountid != "+info.getAccountID()+" and (select accttype.accountmodule from sett_account acct, sett_accounttype accttype  where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) = "+SETTConstant.AccountModule.TWOLEVEL+" \n");
			strSQLQuery.append(" then (select bankaccount.S_ACCOUNTNAME from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.npayaccountid)  \n");
			strSQLQuery.append(" when a.npayaccountid > 0 and a.npayaccountid != "+info.getAccountID()+" and (select accttype.accountmodule from sett_account acct, sett_accounttype accttype where acct.id = a.npayaccountid and acct.naccounttypeid = accttype.id and a.npayaccountid > 0) != "+SETTConstant.AccountModule.TWOLEVEL+"  \n");
			strSQLQuery.append(" then (select bankaccount.S_ACCOUNTNAME from bs_bankaccountinfo bankaccount where bankaccount.n_id = (select bankaccount.n_upbankacctid  from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.nreceiveaccountid))  \n");
			strSQLQuery.append("  when a.nreceiveaccountid > 0 and a.nreceiveaccountid != "+info.getAccountID()+" and (select accttype.accountmodule from sett_account acct, sett_accounttype accttype where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id and a.nreceiveaccountid > 0) = "+SETTConstant.AccountModule.TWOLEVEL+"  \n");
			strSQLQuery.append(" then (select bankaccount.S_ACCOUNTNAME from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.nreceiveaccountid)  \n");
			strSQLQuery.append(" when a.nreceiveaccountid > 0 and a.nreceiveaccountid != "+info.getAccountID()+" and  (select accttype.accountmodule from sett_account acct, sett_accounttype accttype  where acct.id = a.nreceiveaccountid and acct.naccounttypeid = accttype.id  and a.nreceiveaccountid > 0) != "+SETTConstant.AccountModule.TWOLEVEL+"  \n");
			strSQLQuery.append(" then (select bankaccount.S_ACCOUNTNAME  from bs_bankaccountinfo bankaccount where bankaccount.n_subjectid = a.npayaccountid)  \n");
			strSQLQuery.append(" when a.npaybankid > 0 then (select t.SENTERPRISENAME from sett_branch t where t.ID = a.npaybankid  and t.nstatusid = 1)  \n");
			strSQLQuery.append(" when a.nreceivebankid > 0 then (select t.SENTERPRISENAME  from sett_branch t where t.ID = a.nreceivebankid and t.nstatusid = 1)  \n");
			strSQLQuery.append(" when a. SEXTACCOUNTNO != null then a.SEXTCLIENTNAME  \n");
			strSQLQuery.append(" end OPPACCOUNTNAME \n");
			strSQLQuery.append(" from sett_transspecialoperation a \n");
			strSQLQuery.append("  where a.nstatusid = 3 \n");
			if(info.getCompareType()==SETTConstant.CompareTwoLevelType.BALANCE)
			{
				strSQLQuery.append(" AND TO_Char(A.DTEXECUTE, 'yyyy-mm-dd') >='"+info.getStartDate()+"'");
				strSQLQuery.append(" AND TO_Char(A.DTEXECUTE, 'yyyy-mm-dd') <='"+info.getEndDate()+"'");
			}
			else
			{
				strSQLQuery.append(" AND TO_Char(A.DTINTERESTSTART, 'yyyy-mm-dd') >='"+info.getStartDate()+"'");
				strSQLQuery.append(" AND TO_Char(A.DTINTERESTSTART, 'yyyy-mm-dd') <='"+info.getEndDate()+"'");
			}
			strSQLQuery.append(" and (a.npayaccountid = "+info.getAccountID()+" or a.nreceiveaccountid = "+info.getAccountID()+") \n");
			strSQLQuery.append(" )");
			strSQLQuery.append(" order by TO_Char(DTEXECUTE, 'yyyy-mm-dd'),mamount,length(SABSTRACT) desc, SABSTRACT, OPPACCOUNTNO");
			System.out.println("ͨ��info��ѯ���㽻�׼�¼��SQL:" + strSQLQuery.toString());

			// ��ѯ���ݿ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// ��ȡ��ѯ���
			while (rs.next()) {
				// �½�Bean����
				CompareTwoLevelAccountDetailResultInfo cInfo = new CompareTwoLevelAccountDetailResultInfo();
				// ת������
				cInfo.setSettTransNo(rs.getString("STRANSNO"));
				cInfo.setSettDirectType(rs.getLong("ntransdirection"));
				cInfo.setSettAmount(rs.getDouble("MAMOUNT"));
				cInfo.setSettSabstract(rs.getString("SABSTRACT"));
				cInfo.setSettOppAccountNO(rs.getString("OPPACCOUNTNO"));
				cInfo.setSettOppAccountNAME(rs.getString("OPPACCOUNTNAME"));
				cInfo.setDtDate(rs.getDate("DTEXECUTE"));
				// ����������
				aryResult.add(cInfo);
			}

		} finally {
			// ������ݿ�����
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
	 * ���������˻�ID ��ѯ��ĳ���ڵĽ���
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	public ArrayList queryAllBankTwoLevelAccountTrans(CompareTwoLevelAccountDetailCondtion info) throws SQLException {

		// �������
		ArrayList aryResult = new ArrayList();
		// �������ݿ��ѯ����
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		// ��ʼ��ѯͳ��
		try {
			// ��ȡ���ݿ�����
			conn = getConnection();
			StringBuffer strSQLQuery = new StringBuffer();
			//�ж��Ƿ���Ҫ��ѯ���ս��ױ�
			System.out.println("��ѯ�Ŀ�ʼʱ��begin="+info.getStartDate()+";��ѯ������ʱ��end="+info.getStartDate());
			Date curSysTime=Env.getCurrentSystemDate();
			String appSqlStr = "";
			appSqlStr = appSqlStr + " and accountInfo.n_officeId = " + info.getOfficeID();
			appSqlStr = appSqlStr + " and accountInfo.n_currencyType = " + info.getCurrencyID();
			appSqlStr = appSqlStr + " and accountInfo.N_ID ="+ info.getBankAccountID() +"";
			
			if(IDate.compareDate(DataFormat.getDate(info.getStartDate()),curSysTime)==0)
			{
				System.out.println("��ѯʱ��Ϊϵͳ�ĵ�ǰʱ�䣨��ȡ�������ݿ�ʱ�䣩");
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
			else if(IDate.compareDate(DataFormat.getDate(info.getEndDate()),curSysTime)==0)
			{
				System.out.println("��ѯ�Ľ���ʱ��Ϊϵͳ�ĵ�ǰʱ�䣨��ȡ�������ݿ�ʱ�䣩");
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
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyy-MM-dd') >='"+info.getStartDate()+"' ");
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
				System.out.println("��ѯ��ʱ�䷶ΧΪ��ʷ����");
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
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyy-MM-dd') >='" +info.getStartDate()+"' ");
				strSQLQuery.append(" and to_char(histrans.dt_transactiontime,'yyyy-MM-dd') <='" +info.getEndDate()+"' ");
			    strSQLQuery.append(" and histrans.n_isdeletedbybank != 1 " ) ;
			    strSQLQuery.append(" order by to_char(histrans.dt_transactiontime,'yyyy-mm-dd'),histrans.n_amount,length(histrans.s_Abstractinfo) desc,histrans.s_Abstractinfo,histrans.S_OPPACCOUNTNO ");
			}		
			System.out.println("��ѯ�����˻���¼��SQL:" + strSQLQuery.toString());
			// ��ѯ���ݿ�
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();
			// ��ȡ��ѯ���
			while (rs.next()) {
				// �½�Bean����
				CompareTwoLevelAccountDetailResultInfo cInfo = new CompareTwoLevelAccountDetailResultInfo();
				// ת������
				cInfo.setBankTransNo(rs.getString("transnoofbank"));
				cInfo.setBankAmount(rs.getDouble("amount"));
				cInfo.setBankDirectType(rs.getLong("direction"));
				cInfo.setBankSabstract(rs.getString("SABSTRACT"));
				cInfo.setBankOppAccountNO(rs.getString("S_OPPACCOUNTNO"));
				cInfo.setBankOppAccountNAME(rs.getString("s_oppaccountname"));
				cInfo.setDtDate(rs.getDate("transtime"));
				/**�����������Ƿ���Ҫ����**/
				// ����������
				aryResult.add(cInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally { 
			// ������ݿ�����
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
		String strReturn=Config.getProperty(ConfigConstant.SETTLEMENT_BANKABSTRACT_SIGN,"�ã�");
		return strReturn;
	}
}
