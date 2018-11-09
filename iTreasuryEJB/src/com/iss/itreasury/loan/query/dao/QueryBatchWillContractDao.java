package com.iss.itreasury.loan.query.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.ejb.SessionContext;

import com.iss.itreasury.loan.loanpaynotice.dataentity.PayNoticeRateInfo;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.*;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.logger.dataentity.LoggerBtnLevelInfo;
import com.iss.itreasury.system.logger.dataentity.LoggerResults;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database; //import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.SessionMng;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustPayConditionInfo;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.PayNoticeOperation;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.query.dataentity.QueryBatchWillContractInfo;
import com.iss.itreasury.loan.query.dataentity.QueryBatchWillContractResult;
import com.iss.itreasury.loan.query.dataentity.QueryBatchWillContractForm;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.system.dao.PageLoader;

public class QueryBatchWillContractDao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}
	private SessionContext context;
	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private String[] getContractSQL(QueryBatchWillContractInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		// select
		sql[0] = " * ";
		// from
		sql[1] = " select lc.ntypeid,lc.scontractcode contractCode,'�������',lp.scode notificationCode,lp.mamount lendMoney,la.mamount as payamount,lp.nintervalnoticenum,lc.dtstartdate,lc.dtenddate,lc.MINTERESTRATE,lp.,lc.ninputuserid from loan_contractform lc,loan_payform lp,loan_AheadRepayForm la where lc.id = lp.ncontractid and lc.id=la.ncontractid and lc.nofficeid = lp.nofficeid and lc.ncurrencyid = lp.ncurrencyid ";
		sql[2] = "1=1";
		/** ********�����ѯ����************ */
		if (qInfo.getLContractType() > 0) {
			sqlBuf.append(" and lc.ntypeid =" + qInfo.getLContractType());
		}
		if (qInfo.getTimeFrom() > 0) {
			sqlBuf.append(" and lp.nintervalnoticenum >=" + qInfo.getTimeFrom());
		}
		if (qInfo.getTimeTo() > 0) {
			sqlBuf.append(" and lp.nintervalnoticenum <=" + qInfo.getTimeTo());
		}

		if (!"".equals(qInfo.getContractCodeFrom())&&qInfo.getContractCodeFrom()!=null) {
			sqlBuf.append(" and lc.scontractcode >=" + qInfo.getContractCodeFrom());
		}

		if (!"".equals(qInfo.getContractCodeFrom())&&qInfo.getContractCodeFrom()!=null) {
			sqlBuf.append(" and lc.scontractcode <= " + qInfo.getContractCodeTo());
		}

		if (qInfo.getCheckBaseRate() > 0) {
			sqlBuf.append(" and lc.MINTERESTRATE = " + qInfo.getCheckBaseRate());
		}

		if (qInfo.getExcuteRate() > 0) {
			sqlBuf.append(" and lc.MINTERESTRATE =" + qInfo.getExcuteRate());
		}

		if (qInfo.getCurrencyId() > 0) {
			sqlBuf.append(" and lc.ncurrencyid =" + qInfo.getCurrencyId());
		}
		if (qInfo.getOfficeId() > 0) {
			sqlBuf.append(" and lc.nofficeid =" + qInfo.getOfficeId());
		}

		sql[1] = sql[1] + sqlBuf.toString();
		
		
		return sql;
	}
	//������ҵ����תҳ���ѯ����
	//lID,����ID
	public Vector queryContractRatev(long lID,long nofficeID,long ncurrencyID) throws Exception
	{
		Vector result = new Vector();
		PayNoticeOperation operation = new PayNoticeOperation();
		String sql1 = "";
		StringBuffer sqlBuf = new StringBuffer();
		StringBuffer sqlBuf2 = new StringBuffer();
		String sql2 = "";
		String sql3 = "";
		/************Ϊ�˹����Ļ��ܶ����� 2003-3-30 qqgd *************/
		
		sql1 = "select tmp.*,nvl(paybalance,0) as balance,lt.name as typename,lt.id as ltid,nvl(bal.loanBalance,0) as loanBalance,u.sname as responOfficer " ;
		sqlBuf.append(" from (SELECT lc.id idc,");
		sqlBuf.append(" lp.id idp,");
		//sqlBuf.append(" lc.ntypeid,");
		sqlBuf.append(" lc.NSUBTYPEID as ntypeid,");
		sqlBuf.append(" lc.nsubtypeid,");
		sqlBuf.append(" lc.scontractcode contractCode,");
	    sqlBuf.append(" lc.MEXAMINEAMOUNT contractMoney,");
		sqlBuf.append(" lp.scode notificationCode,");
		sqlBuf.append(" lp.mamount lendMoney,");
		sqlBuf.append(" lp.id as lpid,lp.ncontractid,");
		sqlBuf.append(" lp.nintervalnoticenum as term,");
	    sqlBuf.append(" lp.dtstart as timeFrom,");
	    sqlBuf.append(" lp.dtend as timeTo,");
		sqlBuf.append(" lc.MINTERESTRATE as checkBaseRate,");
		sqlBuf.append(" 'ִ������' as excuteRate,");
		sqlBuf.append(" lc.NSTATUSID,");
		sqlBuf.append(" lc.ninputuserid,");
		sqlBuf.append(" lp.mamount,");
		sqlBuf.append(" lc.ncurrencyid,");
		sqlBuf.append(" lc.nofficeid");
		sqlBuf.append(" FROM loan_contractform lc, loan_payform lp");
		sqlBuf.append(" WHERE lp.nstatusid=" +LOANConstant.LoanPayNoticeStatus.USED+
				" and (lc.nstatusid in (" +
				LOANConstant.ContractStatus.ACTIVE +
				","+LOANConstant.ContractStatus.EXTEND+
				")) and lc.id = lp.ncontractid ");
		sqlBuf.append(" and lc.nofficeid = " );
		sqlBuf.append(nofficeID);
		sqlBuf.append(" and lc.ncurrencyid = " +
				ncurrencyID+
				") tmp,");
		sqlBuf.append(" (select lp.nCOntractID,lp.id as NLOANPAYNOTICEID,nvl(sum(sa.mbalance),0) as paybalance from sett_subAccount sa, loan_PayForm lp where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID,lp.id) la,loan_loantypesetting lt,userinfo u,( select lp.nCOntractID, nvl(sum(sa.mbalance),0) as loanBalance");
        sqlBuf.append(" from sett_subAccount sa, ");
        sqlBuf.append("loan_PayForm lp");
        sqlBuf.append(" where sa.AL_nLoanNoteID = lp.ID");
        sqlBuf.append(" group by lp.nCOntractID) bal");
		sql1 += String.valueOf(sqlBuf);
		sqlBuf2.append(" where tmp.idc = la.ncontractid(+)");
		sqlBuf2.append(" and tmp.lpid = la.nloanpaynoticeid(+)");
		sqlBuf2.append(" and tmp.nsubtypeid = lt.id and u.id = tmp.ninputuserid ");
		sqlBuf2.append(" and bal.ncontractid(+)=tmp.idc");
		sqlBuf2.append(" and tmp.nofficeid = ");
		sqlBuf2.append(nofficeID);
		sqlBuf2.append(" and tmp.ncurrencyid = ");
		sqlBuf2.append(ncurrencyID);
		sqlBuf2.append(" and tmp.ncontractid in (select tc.CONTRACTID from LOAN_RATEADJUST_BATDETAIL tc where BATCHID= ");
		sqlBuf2.append(lID);
		sqlBuf2.append(") and tmp.idp in (select tc.paycontractid from LOAN_RATEADJUST_BATDETAIL tc where BATCHID= ");
		sqlBuf2.append(lID);
		sqlBuf2.append(")");
		sql2 = String.valueOf(sqlBuf2);;
		sql3 = sql1+sql2;
		System.out.println("*********sql3:"+sql3);
		//
		PreparedStatement ps = null;
 		
 		ResultSet rs = null;
 		Connection con = null;
 		try
 		{
 			con = Database.getConnection();
 			ps = con.prepareStatement(sql3);
     		rs = ps.executeQuery();
     		while(rs.next()){
     			QueryBatchWillContractResult qbcr = new QueryBatchWillContractResult();
     			qbcr.setLContractType(rs.getLong("ntypeid"));
     			qbcr.setContractCode(rs.getString("contractCode"));
     			qbcr.setContractMoney(rs.getLong("contractMoney"));
     			qbcr.setNotificationCode(rs.getString("notificationCode"));
     			qbcr.setLoanBalance(rs.getLong("loanBalance"));
     			qbcr.setLendMoney(rs.getLong("lendMoney"));
     			qbcr.setBalance(rs.getLong("balance"));
     			qbcr.setTerm(rs.getLong("term"));
     			qbcr.setTimeFrom(rs.getTimestamp("timeFrom"));
     			qbcr.setTimeTo(rs.getTimestamp("timeTo"));
     			qbcr.setCheckBaseRate(rs.getLong("checkBaseRate"));
     			qbcr.setContractStatus(rs.getLong("NSTATUSID"));
     			qbcr.setResponOfficer(rs.getString("nofficeid"));
     			qbcr.setTypeName(rs.getString("typename"));
     			qbcr.setLtid(rs.getLong("ltid"));
     			qbcr.setIdp(rs.getLong("idp"));
     			qbcr.setResponOfficer(rs.getString("responOfficer"));
     			qbcr.setExcuteRate(operation.getLatelyRate(rs.getLong("idp")).getLateRate());
     			result.add(qbcr);
     		}
     			
 		}
 		catch (Exception e)
 		{
 			
             log4j.error(e.toString());
             //modified by mzh_fu 2007/08/07
             //throw new IException("Gen_E001");
              
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
                 log4j.error(e.toString());
                 //modified by mzh_fu 2007/08/07
                 //throw new IException("Gen_E001");
                 //throw new IRollbackException(context, e.getMessage(), e);
 			}
 		}
		return result;
	}
	//��������ҳ���ı����ֵ
	public QueryBatchWillContractForm queryContractRatevtxt(long lID,long nofficeID,long ncurrencyID) throws Exception
	{
		QueryBatchWillContractForm result = new QueryBatchWillContractForm();
		String sql3 = "";
	
		 sql3 = "select t.*,t.NRATEVALUE Nrate,li.SINTERESTRATENO rateNum,b.ifcheck,uif.SNAME inputusername,b.INPUTDATE binputdate,b.STATUS bstatus from loan_rateadjust_batdetail t,LOAN_INTERESTRATE l,LOAN_INTERESTRATETYPEINFO li,loan_rateadjust_batch     b,userinfo uif " +
			" where rownum=1 and b.INPUTUSERID=uif.id and l.NBANKINTERESTTYPEID=li.id(+) and b.id = t.batchid and t.NRATEID=l.id(+) and t.batchid="+lID;

		System.out.println("������������************"+sql3);
		//
		PreparedStatement ps = null;
 		
 		ResultSet rs = null;
 		Connection con = null;
 		try
 		{
 			con = Database.getConnection();
 			ps = con.prepareStatement(sql3);
     		rs = ps.executeQuery();
     		while(rs.next()){
     			result.setId(rs.getLong("id"));
     			result.setNrate(rs.getDouble("Nrate"));
     			result.setNrateid(rs.getLong("nrateid"));
     			result.setNfloatvalue(rs.getDouble("nfloatvalue"));
     			result.setNfixrate(rs.getDouble("nfixrate"));
     			result.setNexcuterate(rs.getDouble("nexcuterate"));
     			result.setDtvalidate(rs.getTimestamp("dtvalidate"));
     			result.setReason(rs.getString("reason"));
     			result.setRatenum(rs.getString("rateNum"));
     			result.setIfCheck(rs.getLong("ifcheck"));
     			 result.setInputUserName(rs.getString("inputusername"));
      			result.setInputDate(rs.getTimestamp("binputdate"));
      			result.setBStatus(rs.getLong("bstatus"));
     		}
     			
 		}
 		catch (Exception e)
 		{
 			
             log4j.error(e.toString());
             //modified by mzh_fu 2007/08/07
             //throw new IException("Gen_E001");
              
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
                 log4j.error(e.toString());
                 //modified by mzh_fu 2007/08/07
                 //throw new IException("Gen_E001");
                 //throw new IRollbackException(context, e.getMessage(), e);
 			}
 		}
		return result;
	}
	
	public PageLoader queryContractRate(QueryBatchWillContractInfo cInfo) throws Exception
	{
		String[] sql= new String[4];
		StringBuffer sqlBuf = new StringBuffer();
		String sql2 = "";
		
		sql[0] = "tmp.*," +
				"nvl(paybalance,0) as balance,lt.name as typename,lt.id as ltid,nvl(bal.loanBalance,0) as loanBalance,u.sname as responOfficer ";
		sql[1] =" (SELECT lc.id idc,"+
	   "lp.id idp,"+
	   "lc.NSUBTYPEID as lContractType,"+
       "lc.scontractcode contractCode,"+
       "lc.MEXAMINEAMOUNT contractMoney,"+
       "lp.scode notificationCode,"+
       "lp.mamount as lendMoney,"+
       "lp.id as lpid,"+
       "lp.nintervalnoticenum as term,"+
       "lp.dtstart as timeFrom,"+
       "lp.dtend as timeTo,"+
       "lc.MINTERESTRATE as checkBaseRate,"+
       "'ִ������' as excuteRate,"+
       "lc.NSTATUSID contractStatus,"+
       "lc.ninputuserid,"+
       "lc.ncurrencyid,"+
       "lc.nofficeid,"+
       "lc.nsubtypeid"+
       " FROM loan_contractform lc, loan_payform lp"+
       " WHERE  lp.nstatusid=" +LOANConstant.LoanPayNoticeStatus.USED+
       " and (lc.nstatusid in (" +
				LOANConstant.ContractStatus.ACTIVE +
				","+LOANConstant.ContractStatus.EXTEND+
				")) and lc.id = lp.ncontractid "+
       " and lc.nofficeid = " +
       cInfo.getOfficeId()+
       " and lc.ncurrencyid = " +
       cInfo.getCurrencyId()+  
       ") tmp, " +
       //"LOAN_LATEBASICRATEVIEW v," +// LOAN_LATEBASICRATEVIEW Ϊ���µĻ�׼���ʺ�ִ�����ʵ���ͼ��
       "PAY_LATEBASICRATEVIEW v," + //
       "(select lp.nCOntractID,lp.id as NLOANPAYNOTICEID,nvl(sum(sa.mbalance),0) as paybalance from sett_subAccount sa, loan_PayForm lp where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID,lp.id) la,loan_loantypesetting lt ,userinfo u,( select lp.nCOntractID, nvl(sum(sa.mbalance),0) as loanBalance from sett_subAccount sa, loan_PayForm lp  where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) bal ";
		//���˵���loan_rateadjustpaydetail����״̬Ϊ�ѱ����������״̬�ķſ
		sql[2] = " tmp.idc=la.ncontractid(+) and la.paybalance>0 and tmp.idp=v.paynoticeid and tmp.idc=v.CONTRACTID and tmp.lpid=la.nloanpaynoticeid(+) and tmp.nsubtypeid=lt.id  and bal.ncontractid(+)=tmp.idc and u.id = tmp.ninputuserid "+
				" and tmp.nofficeid = "+cInfo.getOfficeId()+" and tmp.ncurrencyid = "+cInfo.getCurrencyId()+" and tmp.lpid not in (select NLOANPAYNOTICEID from loan_rateadjustpaycondition where NSTATUSID in ("+LOANConstant.InterestRateSettingStatus.SUBMIT+","+LOANConstant.InterestRateSettingStatus.APPROVALING+"))";

		if (cInfo.getLContractType() > 0) {
			sqlBuf.append(" and tmp.lContractType =" + cInfo.getLContractType());//������
		}
		if (cInfo.getTimeFrom() > 0) {
			sqlBuf.append(" and tmp.term >=" + cInfo.getTimeFrom());
		}
		if (cInfo.getTimeTo() > 0) {
			sqlBuf.append(" and tmp.term <=" + cInfo.getTimeTo());
		}

		if (!"".equals(cInfo.getContractCodeFrom())&&cInfo.getContractCodeFrom()!=null) {
			
			sqlBuf.append(" and tmp.contractCode >='" + cInfo.getContractCodeFrom()+"'");
		}

		if (!"".equals(cInfo.getContractCodeTo())&&cInfo.getContractCodeTo()!=null) {
			sqlBuf.append(" and tmp.contractCode <= '" + cInfo.getContractCodeTo()+"'");
		}

		if (cInfo.getCheckBaseRate() > 0) {
			sqlBuf.append(" and v.rateid = " + cInfo.getLRateID());
		}

		if (cInfo.getExcuteRate() >= 0) {
			sqlBuf.append(" and v.lateRate =" + cInfo.getExcuteRate());
		}
		sql2 = String.valueOf(sqlBuf);
		sql[2] = sql[2]+sql2;
		sql[3] = "";
		System.out.println("-----------------------------");
		System.out.println(sql[2]);
		System.out.println("-----------------------------");
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.query.dataentity.QueryBatchWillContractResult", null);
		//pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	/**
 	 * changeContractRateU  �����������ʱ�
 	 * ���������ͬ����
 	 * ����LOAN_RATEADJUST_BATCH����,�ӱ�,loan_rateadjustpaycondition��
 	 * @return long ��������id
 	 */
	
 	 public synchronized long changeContractRateU(long lInputUsetID,
 			 									long lLoanInterestRateID,double fRate,
 			 									double mAdjustRate,double mStaidAdjustRate,double excuteRate,
 			 									Timestamp dtValidate,String strReason,long lID,
 			 									long m_lCurrencyID, long m_lOfficeID, long m_lUserID,long nSubTypeID,String ifCheck
 			 									) 
     throws IException,RemoteException
 	{ 
 		PreparedStatement ps = null;
 		PreparedStatement ps1 = null;
 		PreparedStatement ps2 = null;
 		PreparedStatement ps5 = null;
 		PreparedStatement ps6 = null;
 		ResultSet rs = null;
 		Connection con = null;
 		String strSQL = "";
        String strSQL1 = "";
        String strSQL2 = "";
        String strSQL3 = "";
        String strSQLnew = "";
        String strTmp = "";
        long lStatusID = -1;
        //long lInputUsetID = -1;
        long lCheckUserID = -1;
        long lNextCheckLevel = -1;
 		long lResult = -1;
 		long ret = -1;
 		long lMaxID2 = -1;
 		//�ӱ����begin
 		String lRateID = "";
 		//�Ƿ�ʹ��ԭ�����̶�����ֵ��ʶ 1--ʹ�� 0--��ʹ��
 		int flag = 0;
 		if(ifCheck.equals("true"))
 			flag = 1;
 		//

         int nIndex=0;
         String contractid = "";
         String payid = "";
      // add by jbpan 20120607 ������־ start
         LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
 		logInfo.setActionTypeID(Constant.LoggerOfOperationType.MODIFYSAVE);
 		logInfo.setBusinessType("�����ִͬ��������������");
         // add by jbpan 20120607 end
 		try
 		{
 			con = Database.getConnection();
 			strSQL="update loan_rateadjust_batch set BATCHNO=?,INPUTUSERID=?,INPUTDATE=sysdate," +
 					"STATUS=?,OFFICEID=?,CURRENCYID=?,NSUBTYPEID=?,ifcheck=? where id=?";

     			ps = con.prepareStatement(strSQL);
     			ps.setLong(1, lID);
     			ps.setLong(2, lInputUsetID);
     			ps.setLong(3, 1);
     			ps.setLong(4, m_lOfficeID);
     			ps.setLong(5, m_lCurrencyID);
     			ps.setLong(6, nSubTypeID);
     			ps.setLong(7, flag);
     			ps.setLong(8, lID);
     			
     		
     			if ((lResult = ps.executeUpdate()) < 1) {
    				System.out.println("error.insert.loan_risklevel");
    			} else {
    				lResult = lID;
    			}

     			//strSQL1 = " select count(*) cnt from LOAN_RATEADJUST_BATCH where id="+lID;
     			//ps = con.prepareStatement(strSQL1);
     			//rs = ps.executeQuery();

     			
     			
     			
     			
     			
     			//��ʼ����ѭ��
     			//for(int i = 0;i<Integer.parseInt(rs.getString("cnt"));i++){
     				//String[] temp = contractInfo[i].trim().split(",");
     				/*/qqq
     				strSQL2 = "insert into loan_rateadjust_batdetail(ID,BATCHID,"
     	 				+ " CONTRACTID,PAYCONTRACTID," +
     	 				" NRATEID,NRATEVALUE," +
     	 				" NFLOATVALUE," +
     	 				" NEXCUTERATE,NFIXRATE," +
     	 				" DTVALIDATE,REASON) "
     	 				+ " values(?,?,?,?,?,?,?,?,?,?,?)";
     	 			*/
     			
     			strSQLnew = "select PAYCONTRACTID from loan_rateadjust_batdetail where batchid = ?" ;
     	             strSQL2 = "update loan_rateadjust_batdetail set " +
     	             		"NRATEID=?,NRATEVALUE=?," +
     	             		"NFLOATVALUE=?," +
     	             		"NEXCUTERATE=?,NFIXRATE=?," +
     	             		"DTVALIDATE=?,REASON=? where batchid=? and PAYCONTRACTID=? ";	

	     	            ps6 = con.prepareStatement(strSQLnew);
						ps6.setLong(1,lID);
						rs = ps6.executeQuery();
						while(rs.next())
    					{
	     	     			ps1 = con.prepareStatement(strSQL2);
	     	     			
	     	     			//ps1.setLong(1, Long.parseLong(temp[0]));	//��ͬid
	     	     			//ps1.setLong(2, Long.parseLong(temp[1]));	//�ſid
	     	     			ps1.setLong(1, lLoanInterestRateID);		//������,��׼����id
	     	     			ps1.setDouble(2, fRate);		//����ֵ,�»�׼����
	     	     			
	     	     			
	     	     			if(flag==0){
	     	     				ps1.setDouble(3, mAdjustRate);				//�¸���
	     	     				ps1.setDouble(4, excuteRate);				//��ִ������
	     	     				ps1.setDouble(5, mStaidAdjustRate);			//�¹̶�
	     	     			}else{
	     	     				HashMap hm = getFloatOrStaticRate(rs.getLong("PAYCONTRACTID"), m_lOfficeID, m_lCurrencyID);
	     	     				mStaidAdjustRate = Double.parseDouble((String)hm.get("MSTAIDADJUSTRATE"));//ԭ�̶�
	     	     				mAdjustRate = Double.parseDouble((String)hm.get("MADJUSTRATE"));//ԭ����
	     	     				excuteRate = fRate*(1+mAdjustRate*1/100)+mStaidAdjustRate;
	     	     				ps1.setDouble(3, mAdjustRate);				
	     	     				ps1.setDouble(4, excuteRate);			//��ִ������
	     	     				ps1.setDouble(5, mStaidAdjustRate);	
	     	     			}

	     	     			ps1.setTimestamp(6, dtValidate);			//��Ч����
	     	     			ps1.setString(7, strReason);				//���ʵ���ԭ��
	     	     			ps1.setLong(8, lID);	
	     	     			ps1.setLong(9, rs.getLong("PAYCONTRACTID")); //����id
	     	     			if ((ret = ps1.executeUpdate()) < 1) {
	     	    				System.out.println("error.insert.loan_risklevel");
	     	    			} else {
	     	    				ret = lMaxID2;
	     	    			}
    					}
     	     			
/**/
     	     			/////////////qqq
    					strSQLnew = "select PAYCONTRACTID from loan_rateadjust_batdetail where batchid = ?" ;
    					//����ע��Ҫ��nnextcheckuserid��ΪnInputUserID
     	    			strSQL3 = " Update loan_rateadjustpaycondition Set " +
     	    				" nBankInterestID=?,mRate=?,dtValiDate=?,sReason=?,nstatusID=?,MADJUSTRATE=?,MSTAIDADJUSTRATE=? " +
     	    				" Where batchID=? and NLOANPAYNOTICEID=?";

    					ps5 = con.prepareStatement(strSQLnew);
    					ps5.setLong(1,lID);
    					rs = ps5.executeQuery();
    					while(rs.next())
    					{
         	    			//System.out.println(strSQL);
         	    			ps2 = con.prepareStatement(strSQL3);
         	    			ps2.setLong(1, lLoanInterestRateID);
         	    			//ps2.setDouble(2, excuteRate);
         	    			ps2.setTimestamp(3, dtValidate);
         	    			ps2.setString(4, strReason);
         	    			ps2.setLong(5, LOANConstant.InterestRateSettingStatus.SUBMIT);
         	    			//ps2.setDouble(6, mAdjustRate);
         	    			//ps2.setDouble(7, mStaidAdjustRate);
         	    			ps2.setLong(8, lID);
         	    			ps2.setLong(9, rs.getLong("PAYCONTRACTID"));
         	               //44
         	    			if(flag==0){
         	    				ps2.setDouble(6, mAdjustRate);		//�¸���
         	    				ps2.setDouble(2, fRate);		//�»�׼����
         	    				ps2.setDouble(7, mStaidAdjustRate);	//�¹̶�
         	     			}else{
         	     				HashMap hm = getFloatOrStaticRate(rs.getLong("PAYCONTRACTID"), m_lOfficeID, m_lCurrencyID);
         	     				mStaidAdjustRate = Double.parseDouble((String)hm.get("MSTAIDADJUSTRATE"));//ԭ�̶�
         	     				mAdjustRate = Double.parseDouble((String)hm.get("MADJUSTRATE"));//ԭ����
         	     				excuteRate = fRate*(1+mAdjustRate*1/100)+mStaidAdjustRate;
         	     				ps2.setDouble(6, mAdjustRate);				
         	     				ps2.setDouble(2, fRate);			//�»�׼����
         	     				ps2.setDouble(7, mStaidAdjustRate);	
         	     			}
         	    			//44
         	     			if ((ret = ps2.executeUpdate()) < 1) {
         	    				System.out.println("error.insert.loan_risklevel");
         	    			} else {
         	    				ret = lMaxID2;
         	    			}
         	     			
    					}
    					if(ps!=null){
         	    			ps.close();
         	    			ps = null;
         	     			}
         	     			if(ps1!=null){
         	    			ps1.close();
         	    			ps1 = null;
         	     			}
         	     			if(ps2!=null){
         	    			ps2.close();
         	    			ps2 = null;
         	     			}
         	     			if(ps5!=null){
         	    			ps5.close();
         	    			ps5 = null;
         	     			}
         	     			if(ps6!=null){
             	    			ps6.close();
             	    			ps6 = null;
             	     			}
    					if(rs!=null){
    					rs.close();
    					rs = null;
    					}
     	     			/////////////qqq
     	     			
     	     			
     	     			
     	     			//���õ����޸����ʷ���,����conn����
     	     			//adjustInterestRate(lID, lLoanInterestRateID, Long.parseLong(temp[0]), Long.parseLong(temp[1]), fRate, mAdjustRate, mStaidAdjustRate, dtValidate, strReason, m_lCurrencyID, m_lOfficeID, m_lUserID, null,con,lResult);
     			//}
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
             if (ps1 != null)
             {
                 ps1.close();
                 ps1 = null;
             }
             if (ps2 != null)
             {
                 ps2.close();
                 ps2 = null;
             }
             if (con != null)
             {
                 con.close();
                 con = null;
             }
           //add by jbpan 20120607 ���ϲ�����־-�������-�ɹ�        
     		logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
 		}
 		catch (Exception e)
 		{
 			// add by jbapn 20120607 start
 			// ���ϲ�����־       
 			logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
 			logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
 			// add by jbpan 20120607 end
 			lResult = -1;
             log4j.error(e.toString());
             //modified by mzh_fu 2007/08/07
             //throw new IException("Gen_E001");
              
 		}
 		finally
 		{
 		// add by jbpan 20120607 ������־ start
 			logInfo.setTransCode("" + lID);  //ҵ������-����id
 			LoggerResults vResult = LoggerResults.getInstance(); 
 			// ����־��¼���浽�ڴ���
 			vResult.getResult().add(logInfo);
 			// add by jbpan 20120607 end
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
 				if (ps1 != null)
 	             {
 	                 ps1.close();
 	                 ps1 = null;
 	             }
 	             if (ps2 != null)
 	             {
 	                 ps2.close();
 	                 ps2 = null;
 	             }
 				if (con != null)
 				{
 					con.close();
 					con = null;
 				}
 			}
 			catch (Exception e)
 			{
                 log4j.error(e.toString());
                 //modified by mzh_fu 2007/08/07
                 //throw new IException("Gen_E001");
                 //throw new IRollbackException(context, e.getMessage(), e);
 			}
 		}
 		return lResult; //���α�����id
 	}
	/**
 	 * changeContractRate  �����������ʱ�
 	 * ���������ͬ����
 	 * ����LOAN_RATEADJUST_BATCH����
 	 * @return long ��������id
 	 */
	
 	 public synchronized long changeContractRate(long lInputUsetID,String[] contractInfo,
 			 									long lLoanInterestRateID,double fRate,
 			 									double mAdjustRate,double mStaidAdjustRate,double excuteRate,
 			 									Timestamp dtValidate,String strReason,long lID,
 			 									long m_lCurrencyID, long m_lOfficeID, long m_lUserID,long nSubTypeID,String ifCheck
 			 									) 
     throws IException,RemoteException
 	{ 
 		PreparedStatement ps = null;
 		PreparedStatement ps1 = null;
 		ResultSet rs = null;
 		Connection con = null;
 		String strSQL = "";
        String strSQL1 = "";
        String strSQL2 = "";
        String strSQL3 = "";
        String strTmp = "";
        long lStatusID = -1;
        //long lInputUsetID = -1;
        long lCheckUserID = -1;
        long lNextCheckLevel = -1;
 		long lResult = -1,lMaxID = -1;
 		long ret = -1;
 		long lMaxID2 = -1;
 		//�ӱ����begin
 		String lRateID = "";
 		//�Ƿ�ʹ��ԭ�����̶�����ֵ��ʶ 1--ʹ�� 0--��ʹ��
 		int flag = 0;
 		if(ifCheck.equals("true"))
 			flag = 1;
         int nIndex=0;
         String contractid = "";
         String payid = "";
         // add by jbpan 20120607 ������־ start
         LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
 		logInfo.setActionTypeID(Constant.LoggerOfOperationType.CREATESAVE);
 		logInfo.setBusinessType("�����ִͬ��������������");
         // add by jbpan 20120607 end
 		try
 		{
 			con = Database.getConnection();

             strSQL = "insert into loan_rateadjust_batch(id,BATCHNO,"
 				+ " INPUTUSERID,INPUTDATE,STATUS,OFFICEID,CURRENCYID,NSUBTYPEID,ifcheck) "
 				+ " values(?,?,?,sysdate,?,?,?,?,?)";
             	strSQL1 = " select nvl(max(id),0)+1 from LOAN_RATEADJUST_BATCH ";
     			ps = con.prepareStatement(strSQL1);
     			rs = ps.executeQuery();
     			if (rs.next()) {
     				lMaxID = rs.getLong(1);
     				lResult = lMaxID;
     				rs.close();
     				rs = null;
     			} else {
     				rs.close();
     				rs = null;
     				ps.close();
     				ps = null;
     				con.close();
     				con = null;
     				return -1;
     			}
     			ps = con.prepareStatement(strSQL);
     			ps.setLong(1, lMaxID);
     			ps.setLong(2, lMaxID);
     			ps.setLong(3, lInputUsetID);
     			ps.setLong(4, 1);
     			ps.setLong(5, m_lOfficeID);
     			ps.setLong(6, m_lCurrencyID);
     			ps.setLong(7, nSubTypeID);
     			ps.setLong(8, flag);
     			if ((lResult = ps.executeUpdate()) < 1) {
    				System.out.println("error.insert.loan_risklevel");
    			} else {
    				lResult = lMaxID;
    				logInfo.setTransCode("" + lResult);  //ҵ������-����id
    			}
     			
     			//��ʼ����ѭ��
     			for(int i = 0;i<contractInfo.length;i++){
     				String[] temp = contractInfo[i].trim().split(",");
     				//qqq
     				strSQL2 = "insert into loan_rateadjust_batdetail(ID,BATCHID,"
     	 				+ " CONTRACTID,PAYCONTRACTID," +
     	 				" NRATEID,NRATEVALUE," +
     	 				" NFLOATVALUE," +
     	 				" NEXCUTERATE,NFIXRATE," +
     	 				" DTVALIDATE,REASON) "
     	 				+ " values(?,?,?,?,?,?,?,?,?,?,?)";
     	             	strSQL3 = " select nvl(max(id),0)+1 from loan_rateadjust_batdetail ";
     	     			ps1 = con.prepareStatement(strSQL3);
     	     			rs = ps1.executeQuery();
     	     			if (rs.next()) {
     	     				lMaxID2 = rs.getLong(1);
     	     				ret = lMaxID2;
     	     				rs.close();
     	     				rs = null;
     	     			} else {
     	     				rs.close();
     	     				rs = null;
     	     				ps.close();
     	     				ps1 = null;
     	     				con.close();
     	     				con = null;
     	     				return -1;
     	     			}
     	     			ps1 = con.prepareStatement(strSQL2);
     	     			ps1.setLong(1, lMaxID2);					//�ӱ�id
     	     			ps1.setLong(2, lMaxID);						//����id
     	     			ps1.setLong(3, Long.parseLong(temp[0]));	//��ͬid
     	     			ps1.setLong(4, Long.parseLong(temp[1]));	//�ſid
     	     			ps1.setLong(5, lLoanInterestRateID);		//������,��׼����id
     	     			ps1.setDouble(6, fRate);//����ֵ,�»�׼����
     	     			if(flag==0){
     	     				ps1.setDouble(7, mAdjustRate);				//�¸���
     	     				ps1.setDouble(8, excuteRate);			//��ִ������
     	     				ps1.setDouble(9, mStaidAdjustRate);			//�¹̶�
     	     			}else{
     	     				HashMap hm = getFloatOrStaticRate(Long.parseLong(temp[1]), m_lOfficeID, m_lCurrencyID);
     	     				mStaidAdjustRate = Double.parseDouble((String)hm.get("MSTAIDADJUSTRATE"));//ԭ�̶�
     	     				mAdjustRate = Double.parseDouble((String)hm.get("MADJUSTRATE"));//ԭ����
     	     				excuteRate = fRate*(1+mAdjustRate*1/100)+mStaidAdjustRate;
     	     				ps1.setDouble(7, mAdjustRate);				
     	     				ps1.setDouble(8, excuteRate);			//��ִ������
     	     				ps1.setDouble(9, mStaidAdjustRate);	
     	     			}
     	     			ps1.setTimestamp(10, dtValidate);			//��Ч����
     	     			ps1.setString(11, strReason);				//���ʵ���ԭ��
     	     			if ((ret = ps1.executeUpdate()) < 1) {
     	    				System.out.println("error.insert.loan_risklevel");
     	    			} else {
     	    				ret = lMaxID2;
     	    			}
     	     			//���õ����޸����ʷ���,����conn����
     	     			lResult = adjustInterestRate(lID, lLoanInterestRateID, Long.parseLong(temp[0]), Long.parseLong(temp[1]), fRate, mAdjustRate, mStaidAdjustRate, dtValidate, strReason, m_lCurrencyID, m_lOfficeID, m_lUserID, null,con,lResult);
     	     			if(lResult==-2){
     	     				return (-2);
     	     			}else{
     	     				lResult=lMaxID;
     	     			}
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
             if (con != null)
             {
                 con.close();
                 con = null;
             }
            // add by jbpan 20120607 ���ϲ�����־-�������-�ɹ�        
     		logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
 		}
 		catch (Exception e)
 		{
 			// add by jbapn 20120607 start
 			// ���ϲ�����־       
 			logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
 			logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
 			// add by jbpan 20120607 end
 			lResult = -1;
             log4j.error(e.toString());
             //modified by mzh_fu 2007/08/07
             //throw new IException("Gen_E001");
              
 		}
 		finally
 		{
 		    // add by jbpan 20120607 ������־ start
 			
 			LoggerResults vResult = LoggerResults.getInstance(); 
 			// ����־��¼���浽�ڴ���
 			vResult.getResult().add(logInfo);
 			// add by jbpan 20120607 end
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
                 log4j.error(e.toString());
                 //modified by mzh_fu 2007/08/07
                 //throw new IException("Gen_E001");
                 //throw new IRollbackException(context, e.getMessage(), e);
 			}
 		}
 		return lResult; //���α�����id
 	}
     /**
      * updateInterestRate  ��ͨ�������������޸�,�������Ӳ���
      * �ύ���������޸�����
      * ���� ContractRateSetting ���ݱ�
      * �ύ���������޸�����
      * �����޸ĵĺ�ͬ�Ĳ�ѯ����д�����
      * lID = 0 ,�����������޸ļ�¼
      *
      * @param lID                       long          �����޸�������ʾ
      * @param lLoanInterestRateID       long          �������ʱ�ʾ
      * @param lContractID               long          ��ͬ��ʾ
      * @param lLoanPayNoticeID          long          �ſ�֪ͨ����ʾ
      * @param dRate                     double        ����ֵ
      * @param tsValidate                Timestamp     ��Ч��
      * @param strReason                 String        ����ԭ��
      * @param lCurrencyID               long          ����
      * @param lOfficeID                 long          ���´�ID
      * @param lInputUserID              long          ¼����ID
      * @param tsInputDate               Timestamp     ¼������
      * @param batchID					 long          ����ID
      * @return long  �ɹ�����ID��ʶ��ʧ�ܷ���0
      * @throws RemoteException
      * @throws IRollbackException 
      */
     public long adjustInterestRate (
       long lID,
       long lLoanInterestRateID,
       long lContractID,
       long lLoanPayNoticeID,
       double dRate,
       double dAdjustRate,
       double dStaidAdjustRate,
       Timestamp tsValidate,
       String strReason,
       long lCurrencyID,
       long lOfficeID,
       long lInputUserID,
       Timestamp tsInputDate,
       Connection csCon,long batchID
     ) throws java.rmi.RemoteException, IRollbackException
 {
 	PreparedStatement ps = null;
 	ResultSet rs = null;
 	Connection conn = null;
 	String strSQL = null;
 	long lMaxID = -1;
 	long nStatusID = -1;

 	try
 	{
 		conn = csCon;

 		//����Ƿ����ظ���¼
 		strSQL =
 			" select count(*) from loan_rateadjustpaycondition where 1=1 ";

 		//�޸� by kenny(��־ǿ)(2007-03-29) ֻҪ��ͬһ����Ч���ڣ�һ�ʺ�ͬ���ܴ����������ʵ�����¼
 		/*
 		if (lLoanInterestRateID > 0)
 		{
 			strSQL += " and nBankInterestID = " + lLoanInterestRateID;
 		}
 		if (dAdjustRate != -100)
 		{
 			strSQL += " and MADJUSTRATE = " + dAdjustRate;
 		}
 		if (dStaidAdjustRate != -100)
 		{
 			strSQL += " and MSTAIDADJUSTRATE = " + dStaidAdjustRate;
 		}
 		*/
 		if (lContractID > 0)
 		{
 			strSQL += " and nContractID = " + lContractID;
 		}
 		if (lLoanPayNoticeID > 0)
 		{
 			strSQL += " and nLoanPayNoticeID = " + lLoanPayNoticeID;
 		}
 		//modify by xwhe 2008-06-05 �����޸�
 	//	if (tsValidate != null)
 	//	{
 	//		strSQL += " and to_char(dtValidate,'yyyy-mm-dd') = '" +
 	//			DataFormat.getDateString(tsValidate) + "' ";
 	//	}
 		/*�޸� by kenny(��־ǿ) (2007-03-21) �����ͬ���ʵ����޸�ʱ��ʾ������ͬ��¼���� */
 		if (lID > 0)
 		{
 			strSQL += " and id not in ("+batchID+") ";
 		}
 		strSQL += " and nstatusId in ("+LOANConstant.InterestRateSettingStatus.SUBMIT+","+LOANConstant.InterestRateSettingStatus.APPROVALING+")";

 		System.out.println("--------------------haoliang-------------------"+strSQL);
 		ps = conn.prepareStatement(strSQL);
 		rs = ps.executeQuery();
 		if (rs.next())
 		{
 			lMaxID = rs.getLong(1);
 		}
 		rs.close();
 		rs = null;
 		ps.close();
 		ps = null;

 		System.out.println("lMaxID1=" + lMaxID);
 		if (lMaxID > 0)
 		{
 			conn.close();
 			conn = null;

 			return ( -2);
 		}

 		if (lID <= 0)
 		{
 			//�õ�����ID
 			strSQL = "select nvl(max(ID)+1,1) from loan_rateadjustpaycondition ";
 			ps = conn.prepareStatement(strSQL);
 			rs = ps.executeQuery();
 			if (rs.next())
 			{
 				lMaxID = rs.getLong(1);
 			}
 			rs.close();
 			rs = null;
 			ps.close();
 			ps = null;

 			strSQL = " Insert into loan_rateadjustpaycondition( " +
 				" ID,nBankInterestID,nContractID,nLoanPayNoticeID,mRate,dtValiDate,sReason, " +
 				" nInputUserID,dtInputDate,nStatusID,nOfficeID,nNextCheckUserID,MADJUSTRATE,MSTAIDADJUSTRATE,nNextCheckLevel,nCurrencyID,batchID ) " +
 				" values (?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,-1,?,?) ";

 			System.out.println(strSQL);
 			ps = conn.prepareStatement(strSQL);
 			ps.setLong(1, lMaxID);
 			ps.setLong(2, lLoanInterestRateID);
 			ps.setLong(3, lContractID);
 			ps.setLong(4, lLoanPayNoticeID);
 			ps.setDouble(5, dRate);
 			ps.setTimestamp(6, tsValidate);
 			ps.setString(7, strReason);
 			ps.setLong(8, lInputUserID);
 			ps.setLong(9, LOANConstant.InterestRateSettingStatus.SUBMIT);
 			ps.setLong(10, lOfficeID);
 			ps.setLong(11, -1);
 			ps.setDouble(12, dAdjustRate);
 			ps.setDouble(13, dStaidAdjustRate);
 			ps.setLong(14, lCurrencyID);
 			ps.setLong(15, batchID);
 			ps.executeUpdate();
 			ps.close();
 			ps = null;
 		}
 		else
 		{
 			//����ע��Ҫ��nnextcheckuserid��ΪnInputUserID
 			strSQL = " Update loan_rateadjustpaycondition Set " +
 				" nBankInterestID=?,mRate=?,dtValiDate=?,sReason=?,nstatusID=?,MADJUSTRATE=?,MSTAIDADJUSTRATE=? " +
 				" Where ID=? ";

 			//System.out.println(strSQL);
 			ps = conn.prepareStatement(strSQL);
 			ps.setLong(1, lLoanInterestRateID);
 			ps.setDouble(2, dRate);
 			ps.setTimestamp(3, tsValidate);
 			ps.setString(4, strReason);
 			ps.setLong(5, LOANConstant.InterestRateSettingStatus.SUBMIT);
 			ps.setDouble(6, dAdjustRate);
 			ps.setDouble(7, dStaidAdjustRate);
 			ps.setLong(8, lID);
            
 			ps.executeUpdate();
 			
 			ps.close();
 			ps = null;

 			lMaxID = lID;

 		}
 		//conn.close();
 		conn = null;
 	}
 	catch (Exception e)
 	{
 		e.printStackTrace();
 		//modified by mzh_fu 2007/08/07
 		//throw new RemoteException("remote exception : " + e.toString());
 		//throw new IRollbackException(context, e.getMessage(), e);
 	}
 	finally
 	{
 		try
 		{
 			if (rs != null)
 				rs.close();
 				rs = null;
 			if (ps != null)
 				ps.close();
 				ps = null;
 			if (conn != null)
 				conn = null;
 		}
 		catch (Exception ex)
 		{
 			//modified by mzh_fu 2007/08/07
 			//throw new RemoteException("remote exception : " + ex.toString());
 			//throw new IRollbackException(context, ex.getMessage(), ex);
 		}
 	}
 	return (lMaxID);
 }
     //��������,��ʼ
     //lID,lCheckStatusΪ�ϲ���;bIDΪ��������,���α�id,ͬʱҲ��loan_rateadjustpaycondition�������id
     public long updateCheckStatus(long lID,long lCheckStatus) throws RemoteException, IRollbackException
     {
  	    PreparedStatement ps = null;
  		Connection conn = null;
  		String strSQL = null;
  		int lReturn = 0;
  		try
  		{
  			    conn = Database.getConnection();
  				strSQL = " Update LOAN_RATEADJUST_BATCH Set STATUS=?  Where ID=?" ;
  				ps = conn.prepareStatement(strSQL);
  				ps.setLong(1,lCheckStatus);
  				ps.setLong(2,lID);
  				lReturn = ps.executeUpdate();
  				ps.close();
  				ps = null;
  				strSQL = " Update loan_rateadjustpaycondition Set nstatusid=?  Where BATCHID=?" ;
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1,lCheckStatus);
				ps.setLong(2,lID);
				lReturn = ps.executeUpdate();
				ps.close();
				ps = null;
  			conn.close();
  			conn = null;
  		}
  		catch (Exception e)
  		{
  			e.printStackTrace();
  			//modified by mzh_fu 2007/08/07
  			//throw new RemoteException("remote updateCheckStatus exception : " + e.toString());
  			throw new IRollbackException(context, e.getMessage(), e);
  		}
  		finally
  		{
  			try
  			{
  				if (ps != null)
  					ps.close();
  					ps = null;
  				if (conn != null)
  					conn.close();
  					conn = null;
  			}
  			catch (Exception ex)
  			{
  				//modified by mzh_fu 2007/08/07
  				//throw new RemoteException("remote updateCheckStatus exception : " + ex.toString());
  				throw new IRollbackException(context, ex.getMessage(), ex);
  			}
  		}
  		
  	   return new Long(lReturn).longValue();
     }
	public long initApproval(QueryBatchWillContractResult adjinfo) throws RemoteException, IRollbackException
	{
		 
            long lReturn = -1;
            long lID = -1;//ҵ���е�����id
         // add by jbpan 20120607 ������־ start
            LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
    		logInfo.setActionTypeID(Constant.LoggerOfOperationType.INITAPPROVAL);
    		logInfo.setBusinessType("�����ִͬ��������������");
            // add by jbpan 20120607 end
			try
			{
				InutParameterInfo pInfo = adjinfo.getInutParameterInfo();
				//�ύ����
				lReturn = FSWorkflowManager.initApproval(pInfo);
				if(lReturn > 0){
				//����״̬��������
				this.updateCheckStatus(adjinfo.getId(),LOANConstant.InterestRateSettingStatus.APPROVALING);
				}
				//add by jbpan 20120607 ���ϲ�����־-�������-�ɹ�        
				logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
			}
			catch (Exception e)
			{
				//modified by mzh_fu 2007/08/07
				//throw new RemoteException("remote  exception : " + e.toString());
				// add by jbapn 20120607 start
				// ���ϲ�����־       
				logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
				logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
				// add by jbpan 20120607 end
				PreparedStatement ps = null;
				Connection conn = null;
				String strSQL = null;
				lID = adjinfo.getId();
				try
				{
				    conn = Database.getConnection();
					strSQL = "delete from loan_rateadjust_batch where id = "+lID ;
					ps = conn.prepareStatement(strSQL);
					ps.executeUpdate();
					
					ps.close();
					ps = null;
					
					strSQL = "delete from loan_rateadjust_batdetail where BATCHID = "+lID ;
					ps = conn.prepareStatement(strSQL);
					ps.executeUpdate();
					
					ps.close();
					ps = null;
					
					strSQL = "delete from loan_rateadjustpaycondition where BATCHID = "+lID ;
					ps = conn.prepareStatement(strSQL);
					ps.executeUpdate();
					
					ps.close();
					ps = null;
					
					conn.close();
					conn = null;
				}
				catch (Exception ex)
				{
					e.printStackTrace();
					throw new IRollbackException(context, ex.getMessage(), ex);
				}

				throw new IRollbackException(context, e.getMessage(), e);
			}
			// add by jbpan 20120607 start
	        finally
	        {
	        	logInfo.setTransCode("" + adjinfo.getId());  // ������־-����id  
	            LoggerResults vResult = LoggerResults.getInstance(); 
	    		// ����־��¼���浽�ڴ���
	    		vResult.getResult().add(logInfo);
	        }     
	        //add by jbpan 20120607 end

			return adjinfo.getId();		
	
	}
	   //����һ��AdjustPayConditionInfoʵ��
	   public AdjustPayConditionInfo findAdjustPayConditionInfoByID(long lID) throws RemoteException
	   {
		    PreparedStatement ps = null;
			Connection conn = null;
			ResultSet rs = null;
			String strSQL = null;
			AdjustPayConditionInfo conditioninfo = new AdjustPayConditionInfo();
			try
			{
				    conn = Database.getConnection();
					strSQL = "select id,nbankinterestid,ncontractid,mrate,dtvalidate,sreason,ninputuserid,dtinputdate,nnextcheckuserid," +
							"nstatusid,nofficeid,nloanpaynoticeid,nnextchecklevel,mstaidadjustrate,madjustrate,ncurrencyid,BATCHID " +
							" from loan_rateadjustpaycondition where id = ?" ;
					
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1,lID);
					rs = ps.executeQuery();
					while(rs.next())
					{
						conditioninfo.setId(rs.getLong("id"));
						conditioninfo.setNbankinterestid(rs.getLong("nbankinterestid"));
						conditioninfo.setNcontractid(rs.getString("ncontractid"));
						conditioninfo.setMrate(rs.getDouble("mrate"));
						conditioninfo.setDtvalidate(rs.getTimestamp("dtvalidate"));
						conditioninfo.setSreason(rs.getString("sreason"));
						conditioninfo.setNinputuserid(rs.getLong("ninputuserid"));
						conditioninfo.setDtinputdate(rs.getTimestamp("dtinputdate"));
						conditioninfo.setNstatusid(rs.getLong("nstatusid"));
						conditioninfo.setNofficeid(rs.getLong("nofficeid"));
						conditioninfo.setNloanpaynoticeid(rs.getLong("nloanpaynoticeid"));
						conditioninfo.setMstaidadjustrate(rs.getDouble("mstaidadjustrate"));
						conditioninfo.setMadjustrate(rs.getDouble("madjustrate"));
						conditioninfo.setNcurrencyid(rs.getLong("ncurrencyid"));
						conditioninfo.setBatchID(rs.getLong("ncurrencyid"));
						System.out.println("�̶�����"+conditioninfo.getMstaidadjustrate());
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				conn.close();
				conn = null;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException("remote exception : " + e.toString());
			}
			finally
			{
				try
				{
					if (rs != null)
						rs.close();
						rs = null;
					if (ps != null)
						ps.close();
						ps = null;
					if (conn != null)
						conn.close();
						conn = null;
				}
				catch (Exception ex)
				{
					throw new RemoteException("remote exception : " + ex.toString());
				}
			}
		return conditioninfo;   
	   }
	   //�ж��Ƿ�����ͬ��¼
	   public long isAdjustRecord(long lID,long lContraCtID,long lCheckStatus,long lOfficeID,long lCurrencyID) throws RemoteException
	   {
		   
		    PreparedStatement ps = null;
			Connection conn = null;
			ResultSet rs = null;
			String strSQL = null;
			long lReturn = -1;
			try
			{
				    conn = Database.getConnection();
					strSQL = "select count(*) count from loan_rateadjustpaycondition where ncontractid=? and batchid<>? " +
							" and nstatusid=? and nofficeid=? and ncurrencyid=? ";
					ps = conn.prepareStatement(strSQL);
					ps.setString(1,String.valueOf(lContraCtID));
					ps.setLong(2,lID);
					ps.setLong(3,lCheckStatus);
					ps.setLong(4,lOfficeID);
					ps.setLong(5,lCurrencyID);
					rs = ps.executeQuery();
					while(rs.next())
					{
						lReturn = rs.getLong("count");
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				conn.close();
				conn = null;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException("remote isAdjustRecord() exception : " + e.toString());
			}
			finally
			{
				try
				{
					if (rs != null)
						rs.close();
						rs = null;
					if (ps != null)
						ps.close();
						ps = null;
					if (conn != null)
						conn.close();
						conn = null;
				}
				catch (Exception ex)
				{
					throw new RemoteException("remote isAdjustRecord() exception : " + ex.toString());
				}
			}
		return lReturn;   
	   }
	   
	   //������ڷ���
	   public long doApproval(QueryBatchWillContractResult adjinfo)throws RemoteException, IRollbackException
	   {
		   long lId = adjinfo.getId();
		  // long bId = adjinfo.getBatchID();
		   long lReturn = -1;
		   long lContractID = -1;
		   long lPayNoticeID = -1;
		   //long lContractID = Long.parseLong(adjinfo.getNcontractid());
		   InutParameterInfo pInfo = adjinfo.getInutParameterInfo();
		   SessionMng sessionMng = pInfo.getSessionMng();
		   PreparedStatement ps = null;
		   String strSQL1 = "";
		   ResultSet rs = null;
		   // add by jbpan 20120607 ������־ start
	        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
			logInfo.setActionTypeID(Constant.LoggerOfOperationType.DOAPPRVOAL);
			logInfo.setBusinessType("�����ִͬ��������������");
	        // add by jbpan 20120607 end
			try
			{
				
				//�ύ����
				pInfo.setDataEntity(adjinfo);
				InutParameterInfo _inutParameterInfo = FSWorkflowManager.doApproval(pInfo);
				Connection conn = null;
				//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
				if(_inutParameterInfo.isLastLevel())
				{	
					conn = Database.getConnection();
					
					strSQL1 = " select * from LOAN_RATEADJUSTPAYCONDITION where batchid=? ";
					ps = conn.prepareStatement(strSQL1);
	     			ps.setLong(1, lId);
	     			rs = ps.executeQuery();
	     			while(rs.next()){
	     				lContractID = rs.getLong("NCONTRACTID");
	     				lPayNoticeID = rs.getLong("NLOANPAYNOTICEID");
	     				lReturn = this.isAdjustRecord(lId,lContractID,LOANConstant.InterestRateSettingStatus.CHECK,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
	     				
	     				if(lReturn>0){
	     					this.updateAdjustStatus(lId,LOANConstant.InterestRateSettingStatus.ADJUST,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lPayNoticeID);
	     				}
	     			}
	     			rs.close();
     				rs = null;
     				ps.close();
     				ps = null;
					this.updateCheckStatus(lId,LOANConstant.InterestRateSettingStatus.CHECK);
				
					///qqqqqqqqqqqqqq��batchidѭ���������������
		     			ps = conn.prepareStatement(strSQL1);
		     			ps.setLong(1, lId);
		     			rs = ps.executeQuery();
		     			while(rs.next()){
		     				this.insertValues(conn,rs.getLong("NCONTRACTID"),rs.getLong("NLOANPAYNOTICEID"),rs.getLong("NBANKINTERESTID"),rs.getDouble("MADJUSTRATE"),rs.getDouble("MSTAIDADJUSTRATE"),rs.getLong("ID"),rs.getTimestamp("DTVALIDATE"),rs.getDouble("MRATE"),rs.getLong("batchid"));
		     			}
		     			lReturn = LOANConstant.InterestRateSettingStatus.CHECK;
	     				rs.close();
	     				rs = null;
	     				ps.close();
	     				ps = null;
	     				conn.close();
	     				conn = null;
				}
				//��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
				else if(_inutParameterInfo.isRefuse())
				{
					logInfo.setActionTypeID(Constant.LoggerOfOperationType.REFLUSE);
					this.updateCheckStatus(lId,LOANConstant.InterestRateSettingStatus.SUBMIT);
					lReturn = LOANConstant.InterestRateSettingStatus.REFUSE;
				}else{
					lReturn = LOANConstant.InterestRateSettingStatus.APPROVALING;
				}
				//add by jbpan 20120607 ���ϲ�����־-�������-�ɹ�        
				logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
			}catch (Exception e)
			{
				// add by jbapn 20120607 start
				// ���ϲ�����־       
				logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
				logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
				// add by jbpan 20120607 end
				throw new IRollbackException(context, e.getMessage(), e);
			}
			//add by jbpan 20120607 start
	        finally
	        {
	        	logInfo.setTransCode("" + lId);  //������־-����id  	        
	            LoggerResults vResult = LoggerResults.getInstance(); 
	    		// ����־��¼���浽�ڴ���
	    		vResult.getResult().add(logInfo);
	        }     
	        //add by jbpan 20120607 end
			return lReturn;
	   }
	   //��ԭ�е����ʵ�������Ϊ�ѵ���״̬
	   public long updateAdjustStatus(long lID,long lCheckStatus,long lOfficeID,long lCurrencyID,long lPayNoticeID) throws RemoteException, IRollbackException
	   {
		   PreparedStatement ps = null;
			Connection conn = null;
			String strSQL = null;
			int lReturn = 0;
			try
			{
				   
					conn = Database.getConnection();
					strSQL = " Update loan_rateadjustpaycondition Set nstatusid=?  Where batchid<>?" +
							" and nofficeid=? and ncurrencyid=? and NLOANPAYNOTICEID=? and NSTATUSID=? ";
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1,lCheckStatus);
					ps.setLong(2,lID);
					ps.setLong(3,lOfficeID);
					ps.setLong(4,lCurrencyID);
					ps.setLong(5,lPayNoticeID);
					ps.setLong(6,LOANConstant.InterestRateSettingStatus.CHECK);
					lReturn = ps.executeUpdate();
					ps.close();
					ps = null;
					/*strSQL = " Update LOAN_RATEADJUST_BATCH Set status=?  Where id=?";
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1,lCheckStatus);
					ps.setLong(2,lID);
					lReturn = ps.executeUpdate();
					ps.close();
					ps = null;*/
					conn.close();
					conn = null;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				//throw new RemoteException("remote updateAdjustStatus exception : " + e.toString());
				//modified by mzh_fu 2007/08/07
				throw new IRollbackException(context, e.getMessage(), e);
			}
			finally
			{
				try
				{
					if (ps != null)
						ps.close();
						ps = null;
					if (conn != null)
						conn.close();
						conn = null;
				}
				catch (Exception ex)
				{
					//throw new RemoteException("remote updateAdjustStatus exception : " + ex.toString());
					//modified by mzh_fu 2007/08/07
					throw new IRollbackException(context, ex.getMessage(), ex);
				}
			}
			
		   return new Long(lReturn).longValue();
	   }
		/**
		 * Modify by leiyang date 2007/07/12
		 * ��������ȡ��������������ͬ���ʵ�����
		 * @param loanInfo
		 * @return long
		 * @throws IRollbackException
		 */
		public long cancelApproval(QueryBatchWillContractResult adjinfo)throws RemoteException, IRollbackException
		{
			long lReturn = -1;
			//LoanPayNoticeDao paydao = null;
			InutParameterInfo inutParameterInfo = adjinfo.getInutParameterInfo();
			// add by jbpan 20120607 ������־ start
	        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
			logInfo.setActionTypeID(Constant.LoggerOfOperationType.CANCELAPPROVAL);
			logInfo.setBusinessType("�����ִͬ��������������");
	        // add by jbpan 20120607 end
			try
			{
				//ȡ������
				lReturn = updateStatusAndCheckStatus(adjinfo.getId(),LOANConstant.InterestRateSettingStatus.SUBMIT);
				System.out.println("the lReturn is  "+lReturn);
				if(lReturn > 0){
					//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
					if(inutParameterInfo.getApprovalEntryID()>0)
					{
						FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
					}				
				}
				//add by jbpan 20120607 ���ϲ�����־-�������-�ɹ�        
				logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
				//Boxu Add 2008��12��18�� �޸ķſ�֪ͨ�����ʵ�������
				//paydao = new LoanPayNoticeDao();
				//paydao.updateRateAdjustDate(adjinfo.getIdp(), 0);
			}
			catch (Exception e)
			{
				// add by jbapn 20120607 start
				// ���ϲ�����־       
				logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
				logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
				// add by jbpan 20120607 end
				throw new IRollbackException(context, e.getMessage(), e);
			}
			//add by jbpan 20120607 start
	        finally
	        {
	        	logInfo.setTransCode("" + adjinfo.getId());  //ҵ������-���κ�
	            LoggerResults vResult = LoggerResults.getInstance(); 
	    		// ����־��¼���浽�ڴ���
	    		vResult.getResult().add(logInfo);
	        }     
	        //add by jbpan 20120607 end
			return lReturn;
		}
		   /**
		    * Modify by leiyang date 2007/07/27
		    * 
		    * @param lID
		    * @param lCheckStatus
		    * @return
		    * @throws RemoteException
		 * @throws IRollbackException 
		    */
		   public long updateStatusAndCheckStatus(long lID,long lCheckStatus) throws RemoteException, IRollbackException
		   {
			    PreparedStatement ps = null;
				Connection conn = null;
				String strSQL = null;
				StringBuffer SQL = new StringBuffer();
				String strSQL1 = null;
				ResultSet rs = null;
				long lReturn = -1;
				LoanPayNoticeDao paydao = null;
				paydao = new LoanPayNoticeDao();
				//paydao.updateRateAdjustDate(adjinfo.getIdp(), 0);
				try
				{
					    conn = Database.getConnection();
						strSQL = " Update loan_rateadjustpaycondition Set nstatusid=?  Where batchid=? and nstatusid=?" ;
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1,lCheckStatus);
						ps.setLong(2,lID);
						ps.setLong(3,LOANConstant.InterestRateSettingStatus.CHECK);
						lReturn = ps.executeUpdate();
						ps.close();
						ps = null;
						
						strSQL = " Update LOAN_RATEADJUST_BATCH Set status=?  Where id=?";
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1,lCheckStatus);
						ps.setLong(2,lID);
						lReturn = ps.executeUpdate();
						ps.close();
						ps = null;

						
						strSQL1 = " select NLOANPAYNOTICEID,DTVALIDATE from LOAN_RATEADJUSTPAYCONDITION where batchid=? and NLOANPAYNOTICEID<>0";
			             
		     			ps = conn.prepareStatement(strSQL1);
		     			ps.setLong(1, lID);
		     			rs = ps.executeQuery();
		     			
		     			PreparedStatement ps1 = null;
		     			SQL.append("Update loan_rateadjustpaydetail Set status=?  Where NLOANPAYNOTICEID = ? and dtstartdate = ?");
 		     			while(rs.next()){
						
						ps1 = conn.prepareStatement(SQL.toString());
						ps1.setLong(1,Constant.RecordStatus.INVALID);
						ps1.setLong(2,rs.getLong("NLOANPAYNOTICEID"));
						ps1.setTimestamp(3,rs.getTimestamp("DTVALIDATE"));
 						lReturn = ps1.executeUpdate();
 						paydao.updateRateAdjustDate(rs.getLong("NLOANPAYNOTICEID"), 0);
		     			}
		     			ps.close();
		     			ps=null;
		     			ps1.close() ;
		     			ps1=null;
						
				}
				catch (Exception e)
				{
					e.printStackTrace();
					//throw new RemoteException("remote updateCheckStatus exception : " + e.toString());
					//modified by mzh_fu 2007/08/07
					throw new IRollbackException(context, e.getMessage(), e);
				}
				finally
				{
					try
					{
						if (ps != null)
							ps.close();
							ps = null;
						if (conn != null)
							conn.close();
							conn = null;
					}
					catch (Exception ex)
					{
						//throw new RemoteException("remote updateCheckStatus exception : " + ex.toString());
						//modified by mzh_fu 2007/08/07
						throw new IRollbackException(context, ex.getMessage(), ex);
					}
				}
				
			   return lReturn;
		   }
		   
	   //ȡ�����뷽��qqqqq
	   public long cancelApply(long lID) throws RemoteException, IRollbackException
	   {
		    PreparedStatement ps = null;
			Connection conn = null;
			StringBuffer SQL = new StringBuffer();
			String strSQL = null;
			String strSQL1 = null;
			ResultSet rs = null;
			long lReturn = -1;
			LoanPayNoticeDao paydao = null;
			paydao = new LoanPayNoticeDao();
			//paydao.updateRateAdjustDate(adjinfo.getIdp(), 0);
			try
			{
				    conn = Database.getConnection();
					strSQL = " Update loan_rateadjustpaycondition Set nstatusid=?  Where batchid=? and nstatusid=?" ;
					ps = conn.prepareStatement(strSQL);
					
					ps.setLong(1,LOANConstant.InterestRateSettingStatus.CANCELAPPLY);
					ps.setLong(2,lID);
					ps.setLong(3,LOANConstant.InterestRateSettingStatus.SUBMIT);
					lReturn = ps.executeUpdate();
					ps.close();
					ps = null;
					
					strSQL = " Update LOAN_RATEADJUST_BATCH Set status=?  Where id=?";
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1,LOANConstant.InterestRateSettingStatus.CANCELAPPLY);
					ps.setLong(2,lID);
					lReturn = ps.executeUpdate();
					ps.close();
					ps = null;

					
					strSQL1 = " select NLOANPAYNOTICEID,DTVALIDATE from LOAN_RATEADJUSTPAYCONDITION where batchid=? ";
		             
	     			ps = conn.prepareStatement(strSQL1);
	     			ps.setLong(1, lID);
	     			rs = ps.executeQuery();
	     			
	     			PreparedStatement ps1 = null;
	     			strSQL="Update loan_rateadjustpaydetail Set status=?  Where NLOANPAYNOTICEID = ? and dtstartdate = ?";
		     			while(rs.next()){
					
					ps1 = conn.prepareStatement(strSQL.toString());
					ps1.setLong(1,LOANConstant.InterestRateSettingStatus.CANCELAPPLY);
					ps1.setLong(2,rs.getLong("NLOANPAYNOTICEID"));
					ps1.setTimestamp(3,rs.getTimestamp("DTVALIDATE"));
					lReturn = ps1.executeUpdate();
					
					ps1.close() ;
	     			ps1=null;
						

						
						
						
						//paydao.updateRateAdjustDate(rs.getLong("NLOANPAYNOTICEID"), 0);
	     			}
		     			
		     			///////rr
		     			strSQL1 = " select NCONTRACTID,DTVALIDATE from LOAN_RATEADJUSTPAYCONDITION where batchid=? ";
			             
		     			ps = conn.prepareStatement(strSQL1);
		     			ps.setLong(1, lID);
		     			rs = ps.executeQuery();
		     			
		     			strSQL="Update loan_rateadjustcontractdetail Set status=?  Where NCONTRACTID = ? and dtstartdate = ?";
			     			while(rs.next()){
						
						ps1 = conn.prepareStatement(strSQL.toString());
						ps1.setLong(1,LOANConstant.InterestRateSettingStatus.CANCELAPPLY);
						ps1.setLong(2,rs.getLong("NCONTRACTID"));
						ps1.setTimestamp(3,rs.getTimestamp("DTVALIDATE"));
						lReturn = ps1.executeUpdate();
		     			ps1.close() ;
		     			ps1=null;
						///////rr
						lReturn = 98;//ʹ����ֵ����0,98��ʱû�����⺬��
						
					}

		     			if (ps != null)
							ps.close();
							ps = null;
		     			if(ps1 != null)
							ps1.close() ;
			     			ps1=null;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				//throw new RemoteException("remote updateCheckStatus exception : " + e.toString());
				//modified by mzh_fu 2007/08/07
				throw new IRollbackException(context, e.getMessage(), e);
			}
			finally
			{
				try
				{
					if (ps != null)
						ps.close();
						ps = null;
					if (conn != null)
						conn.close();
						conn = null;
				}
				catch (Exception ex)
				{
					//throw new RemoteException("remote updateCheckStatus exception : " + ex.toString());
					//modified by mzh_fu 2007/08/07
					throw new IRollbackException(context, ex.getMessage(), ex);
				}
			}
			
		   return lReturn;
	   }
	   //********���뷽��
	 //������ɺ���Ҫ���Ĳ���
		private void insertValues(Connection con ,long lContractID, 
				                  long lLoanPayNoticeID,
								  long lBankInterestID,
								  double dAdjustRate,
								  double dStaidAdjustRate,
								  long lApprovalContentID, 
								  Timestamp dtValiDate,
								  double mRate,
								  long batchID)
		                          throws Exception
		{
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			Connection conn = null;
			long lMaxID = -1;
			long lSerialID = -1;
			long lStatusID = -1;
			long lResultID = -1;
			long lDeleteID = -1;
			LoanPayNoticeDao paydao = null;
			//long lOBStatusID = 0;
			String strSQL = "";
			try
			{
				if(con==null){
				conn = Database.getConnection();
				}else{
					conn = con;
				}
				//��loan_rateadjustcontractdetail����һ������
				//���²������ж���loan_rateadjustcontractdetail�����Ƿ�����Ч���ڴ��ڵ���tsValide�����ڣ�����У��򽫸ü�¼ɾ��

				//modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
				strSQL =
				"select id from loan_rateadjustcontractdetail where status != "+Constant.RecordStatus.INVALID+" and nContractID=" +
				lContractID + " and nBankInterestID=" + lBankInterestID +
				" and MADJUSTRATE=" + dAdjustRate + " and MSTAIDADJUSTRATE=" + dStaidAdjustRate +
				" and to_char(dtStartDate,'yyyy-mm-dd') >= to_char(?,'yyyy-mm-dd') ";
			
				ps = conn.prepareStatement(strSQL);
				ps.setTimestamp(1, dtValiDate);
				rs = ps.executeQuery();
				
				while (rs != null && rs.next())
				{
					lDeleteID = rs.getLong("id");
					
					if (lDeleteID > 0)
					{
						//modified by mzh_fu 2007/07/19
						//strSQL = "delete loan_rateadjustcontractdetail where id=? ";
						strSQL = " update loan_rateadjustcontractdetail set status = "+Constant.RecordStatus.INVALID +" where id=? ";
						
						ps2 = conn.prepareStatement(strSQL);
						ps2.setLong(1, lDeleteID);
						ps2.executeUpdate();
						ps2.close();
						ps2 = null;
					}
				}
				
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				//�ж��Ƿ�����Ч�պ���������¼��ͬһ��ĵ������ʲ�һ���ļ�¼������ɾ��,������ͬһ��ͬһ����ͬ��ֻ������һ������
				lDeleteID = -1;
				strSQL =
				"select id from loan_rateadjustcontractdetail where nContractID=" +
				lContractID +
				" and to_char(dtStartDate,'yyyy-mm-dd') >= to_char(?,'yyyy-mm-dd') ";
			
				//modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
				strSQL = strSQL + " and status != "+Constant.RecordStatus.INVALID;
				
				ps = conn.prepareStatement(strSQL);
				ps.setTimestamp(1, dtValiDate);
				rs = ps.executeQuery();
				while (rs != null && rs.next())
				{
					lDeleteID = rs.getLong("id");
					
					if (lDeleteID > 0)
					{
						//modified by mzh_fu 2007/07/19
						//strSQL = "delete loan_rateadjustcontractdetail where id=? ";
						strSQL = "update loan_rateadjustcontractdetail set status = " + Constant.RecordStatus.INVALID + " where id=? ";
						
						ps2 = conn.prepareStatement(strSQL);
						ps2.setLong(1, lDeleteID);
						ps2.executeUpdate();
						ps2.close();
						ps2 = null;
					}
				}
				
				rs.close();
				rs = null;
				ps.close();
				ps = null;

		        //���ID
				strSQL = "select nvl(max(ID)+1,1) from loan_rateadjustcontractdetail";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lMaxID = rs.getLong(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
		        //���nSerialID
				strSQL = "select nvl(max(nAdjustSerial)+1,1) from loan_rateadjustcontractdetail where nAdjustConditionID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lApprovalContentID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lSerialID = rs.getLong(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				
				//modified by mzh_fu 2007/07/19 ����status
				strSQL = "insert into loan_rateadjustcontractdetail (ID, NAdjustConditionID, NContractID, NBankInterestID, NAdjustSerial, dtstartdate,MADJUSTRATE,MSTAIDADJUSTRATE,niscountinterest,status,mRate,batchid) values (?,?,?,?,?,?,?,?,?,"+Constant.RecordStatus.VALID+",?,?)";
				System.out.println(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lMaxID);
				ps.setLong(2, lApprovalContentID);
				ps.setLong(3, lContractID);
				ps.setLong(4, lBankInterestID);
				ps.setLong(5, lSerialID);
				ps.setTimestamp(6, dtValiDate);
				ps.setDouble(7,dAdjustRate);
				ps.setDouble(8,dStaidAdjustRate);
				
		        //��Ϊ��Ч
				ps.setLong(9, 1);
				ps.setDouble(10, mRate);
				ps.setDouble(11,batchID);
				ps.executeUpdate();
				ps.close();
				ps = null;
		
		        ////////////////////////////////////////////////////////////////
		        //��loan_rateadjustpaydetail��������,�����һ���ſ�֪ͨ�������һ���������
		        //ѡ������еķſ�֪ͨ������ú�ͬ���м�����Ҫ�Ӽ���
		
		        //1,����ǵ���
				if (lLoanPayNoticeID > 0)
				{
					lDeleteID = -1;
					//���²������ж���loan_rateadjustpaydetail�����Ƿ�����Ч���ڴ��ڵ���tsValide�����ڣ�����У��򽫸ü�¼ɾ��
					strSQL = "select id from loan_rateadjustpaydetail where nLoanPayNoticeID=? and nBankInterestID=? and MADJUSTRATE=? and	MSTAIDADJUSTRATE=? and to_char(DTStartDate,'yyyy-mm-dd') >= to_char(?,'yyyy-mm-dd') ";
			        
					//modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
					strSQL = strSQL + " and status != "+Constant.RecordStatus.INVALID;
					
					ps = conn.prepareStatement(strSQL);
					
					ps.setLong(1, lLoanPayNoticeID);
					ps.setLong(2, lBankInterestID);
					ps.setDouble(3,dAdjustRate);
					ps.setDouble(4,dStaidAdjustRate);
					ps.setTimestamp(5, dtValiDate);
		
					rs = ps.executeQuery();
					while (rs != null && rs.next())
					{
						lDeleteID = rs.getLong("id");
						
						if (lDeleteID > 0)
						{
							//modified by mzh_fu 2007/07/19
							//strSQL = "delete loan_rateadjustpaydetail where id=? ";
							strSQL = " update loan_rateadjustpaydetail set status = " + Constant.RecordStatus.INVALID + " where id=? ";
							
							ps2 = conn.prepareStatement(strSQL);
							ps2.setLong(1, lDeleteID);
							ps2.executeUpdate();
							ps2.close();
							ps2 = null;
						}
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
		
					lDeleteID = -1;
					//�ж��Ƿ�����Ч�պ���������¼��ͬһ��ĵ������ʲ�һ���ļ�¼������ɾ��,������ͬһ��ͬһ���ſ�֪ͨ����ֻ������һ������
					strSQL = " select id from loan_rateadjustpaydetail where nLoanPayNoticeID=?  and to_char(DTStartDate,'yyyy-mm-dd') >= to_char(?,'yyyy-mm-dd') ";
						
					//modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
					strSQL = strSQL + " and status != "+Constant.RecordStatus.INVALID;
						
					ps = conn.prepareStatement(strSQL);
		
					ps.setLong(1, lLoanPayNoticeID);
					//ps.setLong(2, lBankInterestID);
					ps.setTimestamp(2, dtValiDate);
					
					rs = ps.executeQuery();
					while (rs != null && rs.next())
					{
						lDeleteID = rs.getLong("id");
						
						if (lDeleteID > 0)
						{
							//modified by mzh_fu 2007/07/19
							//strSQL = "delete loan_rateadjustpaydetail where id=? ";
							strSQL = " update loan_rateadjustpaydetail set status = " + Constant.RecordStatus.INVALID + " where id=? ";
							
							ps2 = conn.prepareStatement(strSQL);
							ps2.setLong(1, lDeleteID);
							ps2.executeUpdate();
							ps2.close();
							ps2 = null;
						}
					}
					
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					
					//���ID
					strSQL = " select nvl(max(ID)+1,1) from loan_rateadjustpaydetail ";
					ps = conn.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs.next())
					{
						lMaxID = rs.getLong(1);
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					//���nSerialID
					strSQL = " select nvl(max(nAdjustSerial)+1,1) from loan_rateadjustpaydetail  where nAdjustConditionID=? ";
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, lApprovalContentID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						lSerialID = rs.getLong(1);
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					
					//modified by mzh_fu 2007/07/19 ����status
					strSQL = " insert into loan_rateadjustpaydetail  (ID, NAdjustConditionID, NLoanPayNoticeID, NBankInterestID, NAdjustSerial, dtstartdate,MADJUSTRATE,MSTAIDADJUSTRATE, niscountinterest,nContractID,status,mRate,batchid) values (?,?,?,?,?,?,?,?,?,?,"+Constant.RecordStatus.VALID+",?,?)";
					
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, lMaxID);
					ps.setLong(2, lApprovalContentID);
					ps.setLong(3, lLoanPayNoticeID);
					ps.setLong(4, lBankInterestID);
					ps.setLong(5, lSerialID);
					ps.setTimestamp(6, dtValiDate);
					ps.setDouble(7,dAdjustRate);
					ps.setDouble(8,dStaidAdjustRate);
					
					//��Ϊ��Ч
					ps.setLong(9, 1);
					ps.setLong(10,lContractID);
					ps.setDouble(11, mRate);
					ps.setDouble(12,batchID);
					ps.executeUpdate();
					ps.close();
					ps = null;
					
					//Boxu Add 2008��12��18�� �޸ķſ�֪ͨ�����ʵ�������
					paydao = new LoanPayNoticeDao();
					paydao.updateRateAdjustDate(lLoanPayNoticeID, 1);
				}
				else
				{
					//strSQL = " select id from loan_payform where ncontractid =" + lContractID;
					//Boxu Add 2008��10��20�� ��ѡ����ȫ���ſ���ѽ���ķſҲ�ᱻ�޸�
					strSQL = " select a.id from loan_payform a ,sett_subaccount b " 
			              + " where a.id = b.AL_nLoanNoteID and b.nstatusid <> "+ SETTConstant.SubAccountStatus.FINISH +" "     
			              + " and a.ncontractid = "  + lContractID ;
					
					ps1 = conn.prepareStatement(strSQL);
					rs1 = ps1.executeQuery();
					while (rs1.next())
					{
						lDeleteID = -1;
						lLoanPayNoticeID = rs1.getLong("id");
						
						strSQL = "select id from loan_rateadjustpaydetail where nLoanPayNoticeID=? and nBankInterestID=? and MADJUSTRATE=? and	MSTAIDADJUSTRATE=? and to_char(DTStartDate,'yyyy-mm-dd') >= to_char(?,'yyyy-mm-dd') ";
						//modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
						strSQL = strSQL + " and status != "+Constant.RecordStatus.INVALID;
						
						ps = conn.prepareStatement(strSQL);
		
						ps.setLong(1, lLoanPayNoticeID);
						ps.setLong(2, lBankInterestID);
						ps.setDouble(3,dAdjustRate);
						ps.setDouble(4,dStaidAdjustRate);
						ps.setTimestamp(5, dtValiDate);
		
						rs = ps.executeQuery();
						while (rs != null && rs.next())
						{
							lDeleteID = rs.getLong("id");
							
							if (lDeleteID > 0)
							{
								//modified by mzh_fu 2007/07/19
								//strSQL = "delete loan_rateadjustpaydetail where id=? ";
								strSQL = "update loan_rateadjustpaydetail set status = " + Constant.RecordStatus.INVALID + " where id=? ";
								
								ps2 = conn.prepareStatement(strSQL);
								ps2.setLong(1, lDeleteID);
								ps2.executeUpdate();
								ps2.close();
								ps2 = null;
							}
						}
						
						rs.close();
						rs = null;
						ps.close();
						ps = null;
		
						
						lDeleteID = -1;
						    
						strSQL = "select id from loan_rateadjustpaydetail where nLoanPayNoticeID=? and to_char(DTStartDate,'yyyy-mm-dd') >= to_char(?,'yyyy-mm-dd') ";
							
						//modified by mzh_fu 2007/07/19 �������� status != Constant.RecordStatus.INVALID
						strSQL = strSQL + " and status != "+Constant.RecordStatus.INVALID;
						
						ps = conn.prepareStatement(strSQL);
		
						ps.setLong(1, lLoanPayNoticeID);
						//ps.setLong(2, lBankInterestID);
						ps.setTimestamp(2, dtValiDate);
		
						rs = ps.executeQuery();
						while (rs != null && rs.next())
						{
							lDeleteID = rs.getLong("id");
							
							if (lDeleteID > 0)
							{
								//modified by mzh_fu 2007/07/19
								//strSQL = "delete loan_rateadjustpaydetail where id=? ";
								strSQL = " update loan_rateadjustpaydetail set status = " + Constant.RecordStatus.INVALID + " where id=? ";
								
								ps2 = conn.prepareStatement(strSQL);
								ps2.setLong(1, lDeleteID);
								ps2.executeUpdate();
								ps2.close();
								ps2 = null;
							}
						}
						
						rs.close();
						rs = null;
						ps.close();
						ps = null;
		
						//����������¼
						//���ID
						strSQL = " select nvl(max(ID)+1,1) from loan_rateadjustpaydetail ";
						ps = conn.prepareStatement(strSQL);
						rs = ps.executeQuery();
						if (rs.next())
						{
							lMaxID = rs.getLong(1);
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						//���nSerialID
						strSQL = " select nvl(max(nAdjustSerial)+1,1) from loan_rateadjustpaydetail  where nAdjustConditionID=? ";
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1, lApprovalContentID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							lSerialID = rs.getLong(1);
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						
						strSQL = "insert into loan_rateadjustpaydetail  (ID, NAdjustConditionID, NLoanPayNoticeID, NBankInterestID, NAdjustSerial, dtstartdate,MADJUSTRATE,MSTAIDADJUSTRATE, niscountinterest,nContractID,status,mRate,batchid ) values (?,?,?,?,?,?,?,?,?,?,"+ Constant.RecordStatus.VALID+",?,?)";
						
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1, lMaxID);
						ps.setLong(2, lApprovalContentID);
						ps.setLong(3, lLoanPayNoticeID);
						ps.setLong(4, lBankInterestID);
						ps.setLong(5, lSerialID);
						ps.setTimestamp(6, dtValiDate);
						ps.setDouble(7,dAdjustRate);
						ps.setDouble(8,dStaidAdjustRate);
						//��Ϊ��Ч
						ps.setLong(9, 1);
						ps.setLong(10,lContractID);
						ps.setDouble(11, mRate);
						ps.setDouble(12, batchID);
						ps.executeUpdate();
						ps.close();
						ps = null;
						
						//Boxu Add 2008��12��18�� �޸ķſ�֪ͨ�����ʵ�������
						paydao = new LoanPayNoticeDao();
						paydao.updateRateAdjustDate(lLoanPayNoticeID, 1);
					}
					rs1.close();
					rs1 = null;
					ps1.close();
					ps1 = null;
				}
				/*if (conn != null)
					conn.close();
					conn = null;*/
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				throw ex;
			}
			finally
			{
				try
				{
					if (rs1 != null)
						rs1.close();
						rs1 = null;
					if (ps1 != null)
						ps1.close();
						ps1 = null;
					if (rs != null)
						rs.close();
						rs = null;
					if (ps != null)
						ps.close();
						ps = null;
		
					/*if (conn != null)
						conn.close();
						conn = null;*/
				}
				catch (Exception ex)
				{
					throw ex;
				}
			}
		}
		/**
		 * ���ݷſID�õ������͹̶�����
		 * @param lID        �ſID
		 * @param nofficeID  ����ID
		 * @param ncurrencyID  ����ID
		 * @return
		 * @throws Exception
		 */
		public HashMap getFloatOrStaticRate(long lID,long nofficeID,long ncurrencyID) throws Exception
		{
			HashMap result = new HashMap();
			String sql3 = "";
			PayNoticeOperation operation = new PayNoticeOperation();
			//sql3 = "select MADJUSTRATE,MSTAIDADJUSTRATE from loan_payform where id="+lID+" and nofficeid="+nofficeID+" and ncurrencyID="+ncurrencyID;
			
			//
			PreparedStatement ps = null;
	 		
	 		ResultSet rs = null;
	 		Connection con = null;
	 		try
	 		{
	 			//con = Database.getConnection();
	 			//ps = con.prepareStatement(sql3);
	     		//rs = ps.executeQuery();
	     		//while(rs.next()){
	     			//result.put("MADJUSTRATE",String.valueOf(rs.getLong("MADJUSTRATE")));
	     			//result.put("MSTAIDADJUSTRATE",String.valueOf(rs.getLong("MSTAIDADJUSTRATE")));
	 				PayNoticeRateInfo pri = new PayNoticeRateInfo();
	 				pri = operation.getLatelyRate(lID);
	     			result.put("MADJUSTRATE",String.valueOf(pri.getLateAdjustRate()));//���µĹ̶�����
	     			result.put("MSTAIDADJUSTRATE",String.valueOf(pri.getLateStaidAdjustRate()));//���µĸ�������
	     		//}
	     			
	 		}
	 		catch (Exception e)
	 		{
	 			
	             log4j.error(e.toString());
	             //modified by mzh_fu 2007/08/07
	             //throw new IException("Gen_E001");
	              
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
	                 log4j.error(e.toString());
	                 //modified by mzh_fu 2007/08/07
	                 //throw new IException("Gen_E001");
	                 //throw new IRollbackException(context, e.getMessage(), e);
	 			}
	 		}
			return result;
		}
		/**
		 * ���ݺ�ͬid,�����������ش���������
		 * @param BatchID  ��ͬid
		 * @return ����������
		 */
		public long getSubtypeIdByBatchId(long BatchID)
		{
			long lReturn = -1;
			String sql3 = "";
		
			sql3 = "select t.nsubtypeid from loan_rateadjust_batch t where t.id="+BatchID;
			
			//
			PreparedStatement ps = null;
	 		
	 		ResultSet rs = null;
	 		Connection con = null;
	 		try
	 		{
	 			con = Database.getConnection();
	 			ps = con.prepareStatement(sql3);
	     		rs = ps.executeQuery();
	     		if(rs.next())
	     		{
	     			lReturn = rs.getLong("nsubtypeid");
	     		}
	     		else
	     		lReturn = -1;
	     			
	 		}
	 		catch (Exception e)
	 		{
	 			
	             log4j.error(e.toString());
	             //modified by mzh_fu 2007/08/07
	             //throw new IException("Gen_E001");
	              
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
	                 log4j.error(e.toString());
	                 //modified by mzh_fu 2007/08/07
	                 //throw new IException("Gen_E001");
	                 //throw new IRollbackException(context, e.getMessage(), e);
	 			}
	 		}
			return lReturn;
		}
	    /**
	     * cancelUpdateInterestRate  �������ʵ���,ȡ������
	     * �������ʵ���,ȡ������
	     * ���� AdjustedContract ���ݱ�
	     * ������Ӧ��¼
	     * Ӧ���ȼ�����״̬
		 * @param     long        lModuleIDID           ģ��
		 * @param     long        lLoanTypeID           ����
		 * @param     long        lActionID             ����
	     * @param lAdjustConditionID : ContractRateSetting.ID(����������,������id)
	     * @return long  �ɹ�����ID��ʶ��ʧ�ܷ���0
	     * @throws RemoteException
	     * @throws IRollbackException 
	     */
		public long cancelAdjustInterestRate (long lAdjustConditionID,long lCurrencyID,long lOfficeID,long subLoanTypeID) throws java.rmi.RemoteException, IRollbackException
	    {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSQL = null;
			
			//������Ӧ��������
			//����
			long lModuleID = Constant.ModuleType.LOAN;
			//ģ������
			//long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
			long lLoanTypeID = subLoanTypeID;
			long lActionID = Constant.ApprovalAction.INTEREST_ADJUST_BATCH;


			long lApprovalID = -1;
			long lContractID = -1;
			long lLoanPayNoticeID = -1;
			long batchID = -1;
			long lReturn = -1;
			// add by jbpan 20120607 ������־ start
	        LoggerBtnLevelInfo logInfo = new LoggerBtnLevelInfo();
			logInfo.setActionTypeID(Constant.LoggerOfOperationType.CANCELAPPLY);
			logInfo.setBusinessType("�����ִͬ��������������");
	        // add by jbpan 20120607 end
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try
			{
				//���ApprovalID
				lApprovalID = appbiz.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
				//�������ݿ�
				con = Database.getConnection();
				//��ɾ����ǰ����˼�¼������ɾ����
				try
				{
					appbiz.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID, lAdjustConditionID, 1);
				}
				catch(Exception e)
				{
					e.printStackTrace ();
					System.out.println("here="+e.getMessage());
					throw e;
				}

				//��loan_rateadjustpaycondition��װ̬��Ϊ��Ч
				strSQL = " update loan_rateadjustpaycondition set nstatusID = ? "
					   + " where BATCHID =? ";
				//System.out.println("sql="+strSQL);
				ps = con.prepareStatement(strSQL);
				ps.setLong(1,Constant.RecordStatus.INVALID);
				ps.setLong(2,lAdjustConditionID);
				ps.executeUpdate();
				ps.close();
				ps = null;
				
				//��loan_rateadjust_batch��װ̬��Ϊ��Ч
				strSQL = " update loan_rateadjust_batch set STATUS = ? "
					   + " where id =? ";
				//System.out.println("sql="+strSQL);
				ps = con.prepareStatement(strSQL);
				ps.setLong(1,Constant.RecordStatus.INVALID);
				ps.setLong(2,lAdjustConditionID);
				ps.executeUpdate();
				ps.close();
				ps = null;

				
			
					strSQL = " delete from loan_rateadjustcontractdetail where  batchid = ? ";
					//System.out.println("sql="+strSQL);
					ps = con.prepareStatement(strSQL);
					
					ps.setLong(1,lAdjustConditionID );
					ps.execute();
					ps.close();
					ps = null;
				
				
					strSQL = " delete from loan_rateadjustpaydetail where  batchid = ? ";
					//System.out.println("sql="+strSQL);
					ps = con.prepareStatement(strSQL);
					ps.setLong(1, lAdjustConditionID);
					ps.execute();
					ps.close();
					ps = null;
			
				//add by jbpan 20120607 ���ϲ�����־-�������-�ɹ�        
				logInfo.setResult(Constant.LoggerOfOperResult.SUCCESS);
				//����ɹ�ִ�е���,�򽫷���ֵ��ֵΪ��������id
				lReturn=lAdjustConditionID;
			}
			catch(Exception e) {
				// add by jbapn 20120607 start
				// ���ϲ�����־       
				logInfo.setResult(Constant.LoggerOfOperResult.FAIL);//�������-ʧ��
				logInfo.setFailReason(e.getMessage()); //ʧ��ԭ��
				// add by jbpan 20120607 end
				e.printStackTrace ();
				//modified by mzh_fu 2007/08/07
				//throw new RemoteException (e.getMessage ());
				throw new IRollbackException(context, e.getMessage(), e);
			}

			finally {
				// add by jbpan 20120607 ������־ start
				logInfo.setTransCode("" + lAdjustConditionID);  //ҵ������-���κ�
				LoggerResults vResult = LoggerResults.getInstance(); 
				// ����־��¼���浽�ڴ���
				vResult.getResult().add(logInfo);
				// add by jbpan 20120607 end
				try {
					if (rs != null) rs.close ();rs = null;
					if (ps != null) ps.close ();ps = null;
					if (con != null) con.close ();con = null;
				}
				catch(Exception ex) {
					//modified by mzh_fu 2007/08/07
					//throw new RemoteException (ex.getMessage ());
					throw new IRollbackException(context, ex.getMessage(), ex);
				}
			}

			//ǰ̨�ж�ֻҪ����0����
	        return lReturn;
	    }

}
