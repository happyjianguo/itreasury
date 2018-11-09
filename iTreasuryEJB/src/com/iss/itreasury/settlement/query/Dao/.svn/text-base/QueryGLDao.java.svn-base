package com.iss.itreasury.settlement.query.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.settlement.query.paraminfo.AccountRecordConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.PrintGLInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 *  开户行余额汇总查询 
 * @author songwenxiao
 *
 */
public class QueryGLDao {
	
	Log4j logger = null;
	
	public QueryGLDao()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	public String queryGLSQL(long lOfficeID, long lCurrencyID, Timestamp tsDate, String strBankCodeStart, String strBankCodeEnd, long lUnit){
		
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		StringBuffer strSQLRecord=null;
		
		try
		{
			sbSQL = new StringBuffer();
			sbSQL.append("  select Sett_GLSD.id, Sett_GLSD.sbankCode, Sett_GLSD.sbankName, Sett_GLSD.nSubjectId, Sett_GLSD.ssegmentcode, Sett_GLSD.nSubjectType, Sett_GLSD.nbalancedirection, \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1)) m_dDebitAmount,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,1)+1, INSTR(Sett_GLSD.All_Amount,'|',1,2)-INSTR(Sett_GLSD.All_Amount,'|',1,1)-1)) m_lDebitNumber,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1)) m_dLoanAmount,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,3)+1, INSTR(Sett_GLSD.All_Amount,'|',1,4)-INSTR(Sett_GLSD.All_Amount,'|',1,3)-1)) m_lCreditNumber,  \n");
			sbSQL.append("         TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount))) m_dStartBalance,  \n");
			sbSQL.append("         decode(Sett_GLSD.nbalancedirection,"+SETTConstant.DebitOrCredit.DEBIT+",TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount)))+TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1))-TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1)),"+SETTConstant.DebitOrCredit.CREDIT+",TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,4)+1, LENGTH(Sett_GLSD.All_Amount)))+TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,INSTR(Sett_GLSD.All_Amount,'|',1,2)+1, INSTR(Sett_GLSD.All_Amount,'|',1,3)-INSTR(Sett_GLSD.All_Amount,'|',1,2)-1))-TO_NUMBER(SUBSTR(Sett_GLSD.All_Amount,0,INSTR(Sett_GLSD.All_Amount,'|',1,1)-1))) m_dEndBalance  \n");
			sbSQL.append("  from (  \n");
			sbSQL.append("  select b.id id, b.scode sbankCode, b.sname sbankName, a.id nSubjectId, a.ssegmentcode2 ssegmentcode, a.nsubjecttype nSubjectType, a.nbalancedirection nbalancedirection, (  \n");
			sbSQL.append("  select sum(DebitSett_GlEntry.m_dDebitAmount) || '|' ||   \n");
			sbSQL.append("         sum(DebitSett_GlEntry.m_lDebitNumber) || '|' ||   \n");
			sbSQL.append("         sum(LoanSett_GlEntry.m_dLoanAmount) || '|' ||   \n");
			sbSQL.append("         sum(LoanSett_GlEntry.m_lCreditNumber) || '|' ||   \n");
			sbSQL.append("         sum(SubjectBalance.m_dStartBalance) All_Amount  \n");
			sbSQL.append("  from  \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         b.nparentsubjectid nparentsubjectid,  \n");
			sbSQL.append("         b.nbalancedirection nbalancedirection,   \n");
			sbSQL.append("         nvl(sum(a.mAmount)/"+lUnit+",0.0) m_dDebitAmount,  \n");
			sbSQL.append("         nvl(count(a.sTransNO),0) m_lDebitNumber  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlEntry t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("         and t.nStatusID != 0  \n");
			sbSQL.append("         and t.nTransDirection = 1  \n");
			sbSQL.append("         and t.dtExecute = to_date('"+DataFormat.getDateString(tsDate)+"', 'yyyy-mm-dd')) a,  \n");
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append("  where a.sSubjectCode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id, b.nparentsubjectid, b.nbalancedirection  \n");
			sbSQL.append("  ) DebitSett_GlEntry,   \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         nvl(sum(a.mAmount)/"+lUnit+",0.0) m_dLoanAmount,  \n");
			sbSQL.append("         nvl(count(a.sTransNO),0) m_lCreditNumber  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlEntry t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("         and t.nStatusID != 0  \n");
			sbSQL.append("         and t.nTransDirection = 2  \n");
			sbSQL.append("         and t.dtExecute = to_date('"+DataFormat.getDateString(tsDate)+"', 'yyyy-mm-dd')) a,  \n");
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b \n");
			sbSQL.append("  where a.sSubjectCode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id  \n");
			sbSQL.append("  ) LoanSett_GlEntry,  \n");
			sbSQL.append("  (  \n");
			sbSQL.append("  select b.id id,  \n");
			sbSQL.append("         decode(b.nbalancedirection,"+SETTConstant.DebitOrCredit.DEBIT+",sum(nvl(a.mDebitBalance/"+lUnit+",0) + nvl(a.mCreditBalance/"+lUnit+",0)),"+SETTConstant.DebitOrCredit.CREDIT+",-sum(nvl(a.mDebitBalance/"+lUnit+",0) + nvl(a.mCreditBalance/"+lUnit+",0))) m_dStartBalance  \n");
			sbSQL.append("  from   \n");
			sbSQL.append("         (select * from Sett_GlBalance t  \n");
			sbSQL.append("         where t.nofficeid = "+lOfficeID+"  \n");
			sbSQL.append("         and t.ncurrencyid = "+lCurrencyID+"  \n");
			
			//判断是否是今天查询
			if(DataFormat.getDateString(tsDate).equals(Env.getSystemDateString(lOfficeID, lCurrencyID)) && CloseSystemMain.getSystemStatusID(lOfficeID, lCurrencyID,Constant.ModuleType.SETTLEMENT)!=Constant.SystemStatus.CLOSE){
				sbSQL.append("  and t.dtgldate = to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'))a,  \n");
			}
			else {
				sbSQL.append("  and t.dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(tsDate))+"','yyyy-mm-dd'))a,  \n");
			}
			sbSQL.append("         (select * from sett_glsubjectdefinition t  \n");
			sbSQL.append("         where t.nOfficeID = "+lOfficeID+"  \n");
			sbSQL.append("         and t.nCurrencyID = "+lCurrencyID+") b  \n");
			sbSQL.append("  where a.sglsubjectcode(+) = b.ssegmentcode2  \n");
			sbSQL.append("  group by b.id, b.nbalancedirection  \n");
			sbSQL.append("  ) SubjectBalance  \n");
			sbSQL.append("  where DebitSett_GlEntry.id = LoanSett_GlEntry.id  \n");
			sbSQL.append("  and DebitSett_GlEntry.id = SubjectBalance.id  \n");
			sbSQL.append("  start with DebitSett_GlEntry.id = a.id  \n");
			sbSQL.append("  connect by prior DebitSett_GlEntry.id = DebitSett_GlEntry.nparentsubjectid  \n");
			sbSQL.append("  ) All_Amount from sett_glsubjectdefinition a, sett_Branch b  \n");
			sbSQL.append("  where a.ssegmentcode2 = b.ssubjectcode  \n");
			sbSQL.append("  and a.nOfficeID = "+lOfficeID+" and a.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("  and b.nOfficeID = "+lOfficeID+" and b.nCurrencyID = "+lCurrencyID+"  \n");
			sbSQL.append("  and b.nstatusid = 1 and b.banksubjecttype != 2  \n");
			if(!strBankCodeStart.equals("") && !strBankCodeEnd.equals("")){
				sbSQL.append("  and b.scode >= '"+ strBankCodeStart +"' and b.scode <= '"+ strBankCodeEnd +"'  \n");
			}
			sbSQL.append("  ) Sett_GLSD  \n");
			
			strSQLRecord = new StringBuffer();
			
			strSQLRecord.append("select * from ( " + sbSQL.toString() + " ) \n");
			
			Log.print(strSQLRecord.toString());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return strSQLRecord.toString();
	}
	
	
public String findGLDetails(long lAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd){
		
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		String strSQLRecord="";
		
		try
		{
			sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
			sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.nStatusID,a.sAbstract,a.nInputUserID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
			sbSQL.append("     from  Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID+" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid)  b,UserInfo c,UserInfo d \n");
			sbSQL.append("     where a.sSubjectCode(+)=b.sSubjectCode and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
				
			if (strAccount != null && strAccount.length() > 0)
			{
				sbSQL.append("       and b.sSubjectCode = '" + strAccount + "'");
			}
			if (strTransNo != null && strTransNo.length() > 0)
			{
				sbSQL.append("       and a.sTransNo = '" + strTransNo + "'");
			}
			if (lTypeID > 0)
			{
				sbSQL.append("       and a.nTransDirection = " + lTypeID);
			}
			if (lTransTypeID > 0)
			{
				sbSQL.append("       and a.nTransactionTypeID = " + lTransTypeID);
			}
			if (tsDateStart != null && tsDateEnd != null)
			{
				sbSQL.append("       and   a.dtExecute between to_date('" +DataFormat.getDateString(tsDateStart)+" ','yyyy-mm-dd') and to_date('" +DataFormat.getDateString(tsDateEnd)+" ','yyyy-mm-dd') \n");
			}
			
			strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + sbSQL.toString();
			
			strSQLRecord = strSQLRecord + " ) aa ) ";
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return strSQLRecord;
	}



public String queryGLDetailUnderSubSQL(String strRootAccount, String strAccount, String strTransNo, long lOfficeID, long lCurrencyID, long lTypeID, long lTransTypeID, Timestamp tsDateStart, Timestamp tsDateEnd){
	
	Log.print("**********************in findAccountTransactionType*********************");
	StringBuffer sbSQL = new StringBuffer();
	String strSQLRecord="";
	
	try
	{
		sbSQL.append("     select a.nOfficeID,a.nCurrencyID,a.sSubjectCode,a.sTransNo,a.nTransactionTypeID,a.nTransDirection, \n");
		sbSQL.append("            a.mAmount,a.dtInterestStart,a.dtExecute,a.nStatusID,a.sAbstract,a.nInputUserID,a.nCheckUserID,c.sName sInputUserName,d.sName sCheckUserName \n");
		sbSQL.append("     from  Sett_GlEntry a,(select * from Sett_VGlSubjectDefinition where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+")  b,UserInfo c,UserInfo d \n");
		sbSQL.append("     where a.sSubjectCode=b.sSubjectCode(+) and a.nInputUserID=c.ID(+) and a.nCheckUserID=d.ID(+) and a.nOfficeID= " + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 ");
		
	if (strRootAccount != null && strRootAccount.length() > 0)
	{
		sbSQL.append("       and b.sRootSubject = '" + strRootAccount + "'");
	}
	if (strAccount != null && strAccount.length() > 0)
	{
		sbSQL.append("       and b.sSubjectCode = '" + strAccount + "'");
	}
	if (strTransNo != null && strTransNo.length() > 0)
	{
		sbSQL.append("       and a.sTransNo = '" + strTransNo + "'");
	}
	if (lTypeID > 0)
	{
		sbSQL.append("       and a.nTransDirection = " + lTypeID);
	}
	if (lTransTypeID > 0)
	{
		sbSQL.append("       and a.nTransactionTypeID = " + lTransTypeID);
	}
	if (tsDateStart != null && tsDateEnd != null)
	{
		sbSQL.append("       and   a.dtExecute between to_date('" +DataFormat.getDateString(tsDateStart)+" ','yyyy-mm-dd') and to_date('" +DataFormat.getDateString(tsDateEnd)+" ','yyyy-mm-dd') \n");
	}
	
	
	//查询记录
	strSQLRecord = "select * from  ( select aa.*,rownum r from ( " + sbSQL.toString();
	strSQLRecord = strSQLRecord + " ) aa ) ";
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	return strSQLRecord;
}

public String findGLTransType(long lAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd){
	
	Log.print("**********************in findAccountTransactionType*********************");
	StringBuffer sbSQL = new StringBuffer();
	String strSQLRecord="";
	
	try
	{
		sbSQL.append(" select distinct Sett_TransTypeDefinition.nTransactionTypeID,  ");
		sbSQL.append(" DebitSett_GlEntry.mDebit,DebitSett_GlEntry.nDebitCount, ");
		sbSQL.append(" LoanSett_GlEntry.mLoan,LoanSett_GlEntry.nLoanCount  ");
		sbSQL.append(" from ");
		sbSQL.append(" ( ");
		sbSQL.append(" select distinct a.nTransactionTypeID,a.sSubjectCode");
		sbSQL.append(" from Sett_GlEntry a, (select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID);
		sbSQL.append(" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid) b  ");
		sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + "");
		sbSQL.append(" and a.dtExecute between to_date('" +DataFormat.getDateString(tsDateStart)+" ','yyyy-mm-dd') and to_date('" +DataFormat.getDateString(tsDateEnd)+" ','yyyy-mm-dd') ");
		sbSQL.append(" ) Sett_TransTypeDefinition, ");
		sbSQL.append(" ( ");
		sbSQL.append(" select a.nTransactionTypeID,sum(a.mAmount) mDebit,count(a.sTransNO) nDebitCount ");
		sbSQL.append(" from Sett_GlEntry a, (select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID);
		sbSQL.append(" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid) b  ");
		sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection =  " + SETTConstant.DebitOrCredit.DEBIT + " ");
		sbSQL.append(" and a.dtExecute between to_date('" +DataFormat.getDateString(tsDateStart)+" ','yyyy-mm-dd') and to_date('" +DataFormat.getDateString(tsDateEnd)+" ','yyyy-mm-dd') ");
		sbSQL.append(" group by a.nTransactionTypeID ");
		sbSQL.append(" ) DebitSett_GlEntry, ");
		sbSQL.append(" ( ");
		sbSQL.append(" select a.nTransactionTypeID,sum(a.mAmount) mLoan,count(a.sTransNO) nLoanCount ");
		sbSQL.append(" from Sett_GlEntry a, (select * from Sett_VGlSubjectDefinition t where t.nOfficeID = "+lOfficeID+" and t.nCurrencyID = "+lCurrencyID);
		sbSQL.append(" start with t.Id = "+ lAccount +"  connect by prior t.Id = t.Nparentsubjectid) b  ");
		sbSQL.append(" where a.sSubjectCode(+)=b.sSubjectCode and a.nOfficeID=" + lOfficeID + " and a.nCurrencyID=" + lCurrencyID + " and nvl(a.nStatusID,0) >0 " + " and a.nTransDirection = " + SETTConstant.DebitOrCredit.CREDIT + " ");
		sbSQL.append(" and a.dtExecute between to_date('" +DataFormat.getDateString(tsDateStart)+" ','yyyy-mm-dd') and to_date('" +DataFormat.getDateString(tsDateEnd)+" ','yyyy-mm-dd') ");
		sbSQL.append(" group by a.nTransactionTypeID ");
		sbSQL.append(" ) LoanSett_GlEntry ");
		sbSQL.append(" where Sett_TransTypeDefinition.nTransactionTypeID=DebitSett_GlEntry.nTransactionTypeID(+) ");
		sbSQL.append(" and Sett_TransTypeDefinition.nTransactionTypeID=LoanSett_GlEntry.nTransactionTypeID(+)  ");
		sbSQL.append(" and (DebitSett_GlEntry.mDebit <> 0 or LoanSett_GlEntry.mLoan <> 0 or DebitSett_GlEntry.nDebitCount<>0 or LoanSett_GlEntry.nLoanCount<>0) ");
		//查询记录
		strSQLRecord = "select * from   ( " + sbSQL.toString();
		strSQLRecord = strSQLRecord + " ) ";
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	return strSQLRecord;
}


public String findDailyAccountRecord(AccountRecordConditionInfo conditionInfo){
	
	Log.print("**********************in findAccountTransactionType*********************");
	StringBuffer sbSQL = new StringBuffer();
	StringBuffer sbSelect = null;
	StringBuffer sbFrom = null;
	StringBuffer sbWhere = null;
	String strSQLRecord = "";
	String allSub = conditionInfo.getAllSub();
	
	try
	{
		int _compareDate = conditionInfo.getTsDateStart().compareTo(Env.getSystemDate(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId()));

        sbSelect = new StringBuffer();
        sbFrom = new StringBuffer();
        sbWhere = new StringBuffer();
		sbSelect.append(" * \n");
		
		sbFrom.append(" ( \n");
		sbFrom.append("  select a.id, \n");
		sbFrom.append("         a.sSubjectCode m_strAccount, \n");
		sbFrom.append("         a.ssegmentname2 m_strName, \n");
		sbFrom.append("         a.nSubjectType m_lSubjectType, \n");
		sbFrom.append("         a.nparentsubjectid nParentSubjectId, \n");
		sbFrom.append("         a.nBalanceDirection, \n");
		sbFrom.append("         decode(a.nBalanceDirection, "+SETTConstant.DebitOrCredit.DEBIT+", sum(nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0)), "+SETTConstant.DebitOrCredit.CREDIT+", -sum(nvl(b.mDebitBalance,0)+nvl(b.mCreditBalance,0))) m_dStartBalance \n");
		sbFrom.append("  from \n");
		
		//Modify 2010-11-05 Boxu 增加多选查询条件
		sbFrom.append(" ( select * from sett_vglsubjectdefinition aa where 1=1 \n");//Maxd_2012-06-05 14:08:01 sett_glsubjectdefinition替换为sett_vglsubjectdefinition 
		if(!allSub.equals("")){
			if(allSub.indexOf(",")==-1){
				sbFrom.append( " and aa.sSubjectCode like '%" + allSub + "%' \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
			}else{
				String []sub = allSub.split(",");
				sbFrom.append( " and ( ");
				for(int i  = 0 ; i < sub.length;i++){
					if(i!=sub.length-1){
						sbFrom.append( "  aa.sSubjectCode like '%" + sub[i] + "%' or ");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
		             }else{
		            	 sbFrom.append( "  aa.sSubjectCode like '%" + sub[i] + "%' ) ");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
		             }    
				}
			}
		}
		sbFrom.append(" ) a, \n");
		
		sbFrom.append("       (select * from Sett_GlBalance \n");
		//判断是否是今天查询
		if(_compareDate == 0 && CloseSystemMain.getSystemStatusID(conditionInfo.getOfficeId(), conditionInfo.getCurrencyId(), Constant.ModuleType.SETTLEMENT) != Constant.SystemStatus.CLOSE){
			sbFrom.append("     where dtgldate = to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
		}
		else {
			sbFrom.append("     where dtgldate = to_date('"+DataFormat.getDateString(DataFormat.getPreviousDate(conditionInfo.getTsDateStart()))+"','yyyy-mm-dd') \n");
		}
		
		//Modify 2010-11-05 Boxu 增加多选查询条件
		if(!allSub.equals("")){
			if(allSub.indexOf(",")==-1){
				sbFrom.append( " and sGlSubjectCode like '%" + allSub + "%' \n");
			}else{
				String []sub = allSub.split(",");
				sbFrom.append( " and ( ");
				for(int i  = 0 ; i < sub.length;i++){
					if(i!=sub.length-1){
						sbFrom.append( "  sGlSubjectCode like '%" + sub[i] + "%' or ");
		             }else{
		            	 sbFrom.append( "  sGlSubjectCode like '%" + sub[i] + "%' ) ");
		             }
				}
			}
		}
		
		sbFrom.append("       ) b \n");
		
		//增加是否虑空条件
		if(conditionInfo.getIflv() > 0)
		{
			sbFrom.append("       , \n");
			sbFrom.append(" ( select distinct sSubjectCode from ( ");
			
			sbFrom.append(" SELECT sSubjectCode1 sSubjectCode from ( ");
			sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
			sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
			sbFrom.append("  from Sett_GlEntry \n");
			sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  and nStatusID>0 \n");
			if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
				sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
				sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			}
			
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			sbFrom.append("  group by sSubjectCode ) \n");
			
			sbFrom.append("  union all \n");
			
			sbFrom.append(" SELECT sSubjectCode2 sSubjectCode from ( ");
			sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
			sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
			sbFrom.append("  from Sett_GlEntry \n");
			sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  and nStatusID>0 \n");
			if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
				sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
				sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			}
			
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			sbFrom.append("  group by sSubjectCode ) \n");
			
			sbFrom.append("  union all \n");
			
			sbFrom.append(" SELECT sSubjectCode3 sSubjectCode from ( ");
			sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
			sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
			sbFrom.append("  from Sett_GlEntry \n");
			sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  and nStatusID>0 \n");
			if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
				sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
				sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			}
			
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			sbFrom.append("  group by sSubjectCode ) \n");
			
			sbFrom.append("  union all \n");
			
			sbFrom.append(" SELECT sSubjectCode4 sSubjectCode from ( ");
			sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
			sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
			sbFrom.append("  from Sett_GlEntry \n");
			sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  and nStatusID>0 \n");
			if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
				sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
				sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			}
			
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			sbFrom.append("  group by sSubjectCode ) \n");
			
			sbFrom.append("  union all \n");
			
			sbFrom.append(" SELECT sSubjectCode5 sSubjectCode from ( ");
			sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
			sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
			sbFrom.append("  from Sett_GlEntry \n");
			sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  and nStatusID>0 \n");
			if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
				sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
				sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			}
			
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			sbFrom.append("  group by sSubjectCode ) \n");
			
			sbFrom.append("  union all \n");
			
			sbFrom.append(" SELECT sSubjectCode6 sSubjectCode from ( ");
			sbFrom.append("  select distinct substr(sSubjectCode, 0, 4) sSubjectCode1,   \n");
			sbFrom.append("  substr(sSubjectCode, 0, 6) sSubjectCode2, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 8) sSubjectCode3, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 10) sSubjectCode4, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 12) sSubjectCode5, \n");
			sbFrom.append("  substr(sSubjectCode, 0, 14) sSubjectCode6 \n");
			sbFrom.append("  from Sett_GlEntry \n");
			sbFrom.append("  where nOfficeID="+conditionInfo.getOfficeId()+" \n");
			sbFrom.append("  and nCurrencyID="+conditionInfo.getCurrencyId()+" \n");
			sbFrom.append("  and nStatusID>0 \n");
			if(!conditionInfo.getTsDateStart().equals("")&&conditionInfo.getTsDateStart()!=null){
				sbFrom.append("  and dtExecute >=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			}
			if(!conditionInfo.getTsDateEnd().equals("")&&conditionInfo.getTsDateEnd()!=null){
				sbFrom.append("  and dtExecute <=to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			}
			
			if(!allSub.equals("")){
				if(allSub.indexOf(",")==-1){
					sbFrom.append( " and sSubjectCode like '%" + allSub + "%' \n");
				}else{
					String []sub = allSub.split(",");
					sbFrom.append( " and ( ");
					for(int i  = 0 ; i < sub.length;i++){
						if(i!=sub.length-1){
							sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' or ");
			             }else{
			            	 sbFrom.append( "  sSubjectCode like '%" + sub[i] + "%' ) ");
			             }
					}
				}
			}
			sbFrom.append("  group by sSubjectCode ) \n");
			
			sbFrom.append("  ) ) c \n");
		}
		
		sbFrom.append(" where a.sSubjectCode = b.sglsubjectcode(+) \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
		
		if(conditionInfo.getIflv()>0){
			sbFrom.append("  and a.sSubjectCode = c.sSubjectCode \n");//Maxd_2012-06-05 14:08:01 ssegmentcode2替换为sSubjectCode
		}
		
		sbFrom.append("  and a.nOfficeID = "+conditionInfo.getOfficeId()+" \n");
		sbFrom.append("  and a.nCurrencyID = "+conditionInfo.getCurrencyId()+" \n");
		sbFrom.append("  group by a.id, a.sSubjectCode, a.ssegmentname2, a.nSubjectType, a.nparentsubjectid, a.nBalanceDirection \n");
		sbFrom.append(" ) \n");
		strSQLRecord=" select "+sbSelect+" from " +sbFrom+"";
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	return strSQLRecord;
}
	public String findQueryGLSearchDailyRecord(AccountRecordConditionInfo conditionInfo){
		
		StringBuffer sbSQL = new StringBuffer();
		
		try
		{
			sbSQL.append(" select distinct t.stransno,t.SBILLNO,t.SBANKCHEQUENO,t1.dtexecute dtexecute1,to_char(t1.dtExecute, 'dd') ExecuteDay, \n"); 
			sbSQL.append(" to_char(t1.dtExecute, 'mm') ExecuteMonth,to_char(t1.dtExecute, 'yyyy') ExecuteYear,t2.nsubjecttype,t1.mamount mamount1, \n");
			sbSQL.append(" t1.nTransactionTypeID TransTypeID,t1.SABSTRACT,t1.NTRANSDIRECTION NTRANSDIRECTION1 \n");
			sbSQL.append(" from sett_transaccountdetail  t,sett_glentry t1,sett_glsubjectdefinition t2 \n");
			sbSQL.append(" where t.stransno(+) = t1.stransno and t1.ssubjectcode(+) = t2.ssegmentcode2 and t1.nstatusid > 0 \n");
			sbSQL.append(" and t1.nofficeid ="+conditionInfo.getOfficeId()+" \n");
			sbSQL.append(" and t1.ncurrencyid ="+conditionInfo.getCurrencyId()+" \n");
			sbSQL.append(" and t2.nofficeid ="+conditionInfo.getOfficeId()+" \n");
			sbSQL.append(" and t2.ncurrencyid ="+conditionInfo.getCurrencyId()+" \n");
			sbSQL.append(" and t1.dtexecute between to_date('"+DataFormat.getDateString(conditionInfo.getTsDateStart())+"','yyyy-mm-dd') \n");
			sbSQL.append(" and to_date('"+DataFormat.getDateString(conditionInfo.getTsDateEnd())+"','yyyy-mm-dd') \n");
			sbSQL.append(" start with t2.Id = "+conditionInfo.getId()+" \n");
			sbSQL.append(" connect by prior t2.Id = t2.Nparentsubjectid  \n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
				//System.out.print("2222222222222222222222:"+sbSQL.toString());
		return sbSQL.toString();
}
	public double getGLBalance(AccountRecordConditionInfo conditionInfo) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			/*m_sbSQL.append("select t.MDEBITBALANCE Balance,t.MCREDITBALANCE Balance1,t.NBALANCEDIRECTION from sett_glbalance t where t.nofficeid="+lOfficeID+" and t.ncurrencyid="+lCurrencyID);
			m_sbSQL.append(" and t.sglsubjectcode='"+printGLInfo.getSsubjectcode()+"'");
			m_sbSQL.append(" and t.DTGLDATE=to_date('"+ DataFormat.formatDate(DataFormat.getPreviousDate(printGLInfo.getStartDate())) + "','yyyy-mm-dd') \n");*/
			m_sbSQL.append("select sum(t1.MDEBITBALANCE) Balance,sum(t1.MCREDITBALANCE) Balance1,avg(t1.NBALANCEDIRECTION) NBALANCEDIRECTION  ");
			m_sbSQL.append(" from sett_glbalance t1,(select t.* from sett_glsubjectdefinition t");
			m_sbSQL.append(" where t.nofficeid=" + conditionInfo.getOfficeId());
			m_sbSQL.append(" and t.ncurrencyid=" + conditionInfo.getCurrencyId());
			m_sbSQL.append(" start with t.Id ="+ conditionInfo.getId() +"  connect by prior t.Id = t.Nparentsubjectid) t2");
			m_sbSQL.append(" where t1.sglsubjectcode(+) = t2.ssegmentcode2");
			m_sbSQL.append(" and t1.DTGLDATE=to_date('"+ DataFormat.formatDate(DataFormat.getPreviousDate(conditionInfo.getTsDateStart())) + "','yyyy-mm-dd') \n");
			m_sbSQL.append(" and t1.nofficeid = " + conditionInfo.getOfficeId() + "  and t1.ncurrencyid = " + conditionInfo.getCurrencyId() ) ;
			
			Log.print("取得科目期初余额SQL:" + m_sbSQL.toString());
			System.out.print("7777777777:"+m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				if(rs.getInt("NBALANCEDIRECTION")==SETTConstant.ControlDirection.DEBIT) dReturn = rs.getDouble("Balance");
			    if(rs.getInt("NBALANCEDIRECTION")==SETTConstant.ControlDirection.CREDIT)  dReturn = rs.getDouble("Balance1");
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return dReturn;
	}
	
	
	

}
