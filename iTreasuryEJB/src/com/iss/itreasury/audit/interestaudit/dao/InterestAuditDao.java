package com.iss.itreasury.audit.interestaudit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.audit.interestaudit.dataentity.InterestAuditCondition;
import com.iss.itreasury.audit.interestaudit.dataentity.InterestAuditResultInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class InterestAuditDao extends ITreasuryDAO{
	 // SQL语法结构
    private StringBuffer m_sbSelect = null;
    private StringBuffer m_sbFrom = null;
    private StringBuffer m_sbWhere = null;
    private StringBuffer m_sbOrderBy = null;
    private Log4j logger = null;
    public InterestAuditDao() {
        super();
        logger = new Log4j(Constant.ModuleType.AUDIT, this);
    }
	public Collection querySettlementInterestAudit(InterestAuditCondition condition)throws IException
	{
		Collection result = null;
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("select d.id clientID, \n");
		m_sbSelect.append("d.sName clientName, \n");
		m_sbSelect.append("e.AF_sdepositno depositNo, \n");//增加存单号 xfma
		m_sbSelect.append("e.AC_nisnegotiate isNegotiate, \n");//增加是否协定 xfma
		m_sbSelect.append("b.id accountID, \n");
		m_sbSelect.append("e.id subAccountID, \n");
		
		m_sbSelect.append("b.saccountno accountNo, \n");
		m_sbSelect.append("b.naccounttypeid accountTypeID, \n");
		m_sbSelect.append("c.saccounttype accountType, \n");
		m_sbSelect.append("t.startDate startDate, \n");
		m_sbSelect.append(" case  when t.interestRate > 0 then t.interestRate else nvl(e.af_mrate, 0)  end interestRate, \n");
		m_sbSelect.append("t.sumbalance sumBalance, \n");
		m_sbSelect.append("t.sumnegotiatebalance sumnegotiatebalance, \n");
		m_sbSelect.append("t.negotiateRate negotiateRate, \n");
		m_sbSelect.append("case when t.interestRate > 0 then (t.sumbalance * t.interestRate) / 36000 else (t.sumbalance * nvl(e.af_mrate, 0)) / 36000 end interestBalance,  \n");
		m_sbSelect.append("(t.sumnegotiatebalance * t.negotiateRate) / 36000 negotiateBalance, \n");
		m_sbSelect.append("t.tempcount tempcount \n");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("from (select a.naccountid, \n");
		m_sbFrom.append("a.nsubaccountid, \n");
		m_sbFrom.append("min(a.dtdate) startDate, \n");
		m_sbFrom.append("sum(a.minterestbalance) sumbalance, \n");
		m_sbFrom.append("sum(a.ac_mnegotiatebalance) sumnegotiatebalance, \n");
		m_sbFrom.append(" a.minterestrate interestRate, \n");
		m_sbFrom.append(" a.ac_mnegotiaterate negotiateRate, \n");
		m_sbFrom.append(" count(a.minterestrate) tempcount \n");
		m_sbFrom.append(" from sett_dailyaccountbalance a \n");
		m_sbFrom.append(" where 1=1 \n");
		if(condition.getSelectDateFrom()!=null)
		{
			m_sbFrom.append("		  and nvl(a.dtdate,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"+DataFormat.getDateString(condition.getSelectDateFrom())+"','yyyy-mm-dd')	\n");
		}
	    if(condition.getSelectDateTo()!=null)
	    {
	    	m_sbFrom.append("		  and nvl(a.dtdate,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"+DataFormat.getDateString(condition.getSelectDateTo())+"','yyyy-mm-dd')	\n");
	    }
		m_sbFrom.append(" group by a.naccountid,a.nsubaccountid,a.minterestrate,a.ac_mnegotiaterate  \n");
		m_sbFrom.append(" order by a.naccountid, a.nsubaccountid, min(a.dtdate)) t, \n");
		m_sbFrom.append("  sett_account b, \n");
		m_sbFrom.append("  sett_accounttype c, \n");
		m_sbFrom.append("  client d, \n");
		m_sbFrom.append("   sett_subaccount e \n");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" where t.naccountid = b.id  \n");
		m_sbWhere.append(" and t.nsubaccountid = e.id  \n");
		m_sbWhere.append(" and b.naccounttypeid = c.id  \n");
		m_sbWhere.append(" and b.nclientid = d.id  \n");
		//去掉已删除、已销户的账户 update by xfma 2008-12-17
		m_sbWhere.append(" and b.nstatusid != 0  \n");
		m_sbWhere.append(" and b.nstatusid != "+SETTConstant.AccountStatus.CLOSE+" \n");
		
		if (condition.getStartClientCode() != null && condition.getStartClientCode().length() > 0)
		{
			m_sbWhere.append("        and d.scode>='" + condition.getStartClientCode() + "' \n");
		}
		if (condition.getEndClientCode() != null && condition.getEndClientCode().length() > 0)
		{
			m_sbWhere.append("        and d.scode<='" + condition.getEndClientCode() + "' \n");
		}
		if (condition.getStartAccountNo() != null && condition.getStartAccountNo().length() > 0)
		{
			m_sbWhere.append("        and b.sAccountNo>='" + condition.getStartAccountNo() + "' \n");
		}
		if (condition.getEndAccountNo() != null && condition.getEndAccountNo().length() > 0)
		{
			m_sbWhere.append("        and b.sAccountNo<='" + condition.getEndAccountNo() + "' \n");
		}
		if(!"".equals(condition.getAccountTypeSet()))
		{
			m_sbWhere.append("        and b.naccounttypeid  IN(" + condition.getAccountTypeSet()+") \n");
		}
		else
		{
			long[] arrayReturn = SETTConstant.AccountType.getDepositAccountTypeCodeForAudit(condition.getCurrencyID(),condition.getOfficeID());
			String strAccountTypeID="";
			for(int i=0;i<arrayReturn.length;i++)
			{
				strAccountTypeID=strAccountTypeID + arrayReturn[i]+"";
				if(arrayReturn[i] >0)
				{
					strAccountTypeID=strAccountTypeID + ",";
				}
			}
			strAccountTypeID = strAccountTypeID.substring(0,strAccountTypeID.length()-1);
			m_sbWhere.append("        and b.naccounttypeid  IN(" +strAccountTypeID+") \n");
		}
		
		if (condition.getOfficeID() > 0)
		{
			m_sbWhere.append("        and b.nofficeid=" + condition.getOfficeID() + " \n");
		}
		if (condition.getCurrencyID() > 0)
		{
			m_sbWhere.append("        and b.ncurrencyid=" + condition.getCurrencyID() + " \n");
		}
		m_sbOrderBy  = new StringBuffer();
		m_sbOrderBy.append("  order by d.scode,c.id,b.id, t.startDate \n");
			result = new ArrayList();
			try {
				initDAO();
				prepareStatement( m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString()+m_sbOrderBy.toString());
				System.out.println("存款利息审计SQL add by xfma(马现福): \n"+m_sbSelect.toString() + m_sbFrom.toString()
						+ m_sbWhere.toString() + m_sbOrderBy.toString());
				executeQuery();
				int tempOnePropertyCount = 0;
				int tempTwoPropertyCount = 0;
				long tempClientID = -1;
				long tempAccountID  = -1;
				HashMap map = new HashMap();
				result.add(map);
				while(transRS.next()){
					InterestAuditResultInfo resultInfo = new InterestAuditResultInfo();
					
					if(tempClientID==transRS.getLong("clientID"))
					{
						tempOnePropertyCount++;
						if(tempClientID>0)
						{
							map.put("clientID"+tempClientID, String.valueOf(tempOnePropertyCount));
						}
					}
					else
					{
						tempOnePropertyCount=1;
						if(transRS.getLong("clientID")>0)
						{
							map.put("clientID"+transRS.getLong("clientID"), String.valueOf(tempOnePropertyCount));
						}
						
					}
					if(tempAccountID==transRS.getLong("accountID"))
					{
						tempTwoPropertyCount++;
						if(tempAccountID>0)
						{
							map.put("accountID"+tempAccountID, String.valueOf(tempTwoPropertyCount));
						}
					}
					else
					{
						tempTwoPropertyCount=1;
						if(transRS.getLong("accountID")>0)
						{
							map.put("accountID"+transRS.getLong("accountID"), String.valueOf(tempTwoPropertyCount));
						}
						
					}
					resultInfo.setClientID(transRS.getLong("clientID"));
					tempClientID = resultInfo.getClientID();
					resultInfo.setClientName(transRS.getString("clientName"));
					resultInfo.setDepositNo(transRS.getString("depositNo"));
					resultInfo.setIsNegotiate(transRS.getLong("isNegotiate"));
					resultInfo.setAccountID(transRS.getLong("accountID"));
					resultInfo.setSubAccountID(transRS.getLong("subAccountID"));
					tempAccountID = resultInfo.getAccountID();
					resultInfo.setAccountNo(transRS.getString("accountNo"));
					resultInfo.setAccountTypeID(transRS.getLong("accountTypeID"));
					resultInfo.setAccountType(transRS.getString("accountType"));
					resultInfo.setStartDate(transRS.getTimestamp("startDate"));
					resultInfo.setInterestRate(transRS.getDouble("interestRate"));
					resultInfo.setSumBalance(transRS.getDouble("sumBalance"));
					resultInfo.setSumNegotiateBalance(transRS.getDouble("sumnegotiatebalance"));
					resultInfo.setNegotiateRate(transRS.getDouble("negotiateRate"));
					resultInfo.setInterestBalance(transRS.getDouble("interestBalance"));
					resultInfo.setNegotiateBalance(transRS.getDouble("negotiateBalance"));
					resultInfo.setTempcount(transRS.getLong("tempcount"));
					resultInfo.setEndDate(DataFormat.getNextDate(resultInfo.getStartDate(),(int)resultInfo.getTempcount()-1));//国电需求算头不算尾 updated by xfma
					resultInfo.setRowSum(resultInfo.getInterestBalance()+resultInfo.getNegotiateBalance());
					result.add(resultInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException("查询结算利息积数审计出现错误");
			}
			finally{
				finalizeDAO();
			}
		return result;
	}
	
	public Collection queryLoanInterestAudit(InterestAuditCondition condition)throws IException
	{
		Collection result = null;
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("select d.id clientID, \n");
		m_sbSelect.append("d.sName clientName, \n");
		m_sbSelect.append("c.id contractID, \n");
		m_sbSelect.append("c.scontractcode contractCode, \n");
		m_sbSelect.append("c.ntypeid contractTypeID, \n");
		m_sbSelect.append("e.id payID, \n");
		m_sbSelect.append("e.scode payCode, \n");
		m_sbSelect.append("t.startDate startDate, \n");
		m_sbSelect.append(" t.mbalance balance, \n");
		m_sbSelect.append(" t.sumbalance sumbalance, \n");
		m_sbSelect.append("case when t.interestRate > 0 then t.interestRate else nvl(f.af_mrate, 0) end interestRate, \n");
		m_sbSelect.append("case when t.interestRate > 0 then (t.sumbalance * t.interestRate) / 36000  else (t.sumbalance * nvl(f.af_mrate, 0)) / 36000 end interestBalance,  \n");
		m_sbSelect.append("t.tempcount tempcount \n");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("from (select a.naccountid, \n");
		m_sbFrom.append("a.nsubaccountid, \n");
		m_sbFrom.append("min(a.dtdate) startDate, \n");
		m_sbFrom.append("sum(a.minterestbalance) sumbalance, \n");
		m_sbFrom.append("a.minterestrate interestRate, \n");
		m_sbFrom.append(" a.minterestbalance mbalance, \n");
		m_sbFrom.append(" count(a.minterestrate) tempcount \n");
		m_sbFrom.append(" from sett_dailyaccountbalance a \n");
		m_sbFrom.append(" where 1=1 \n");
		if(condition.getSelectDateFrom()!=null)
		{
			m_sbFrom.append("		  and nvl(a.dtdate,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"+DataFormat.getDateString(condition.getSelectDateFrom())+"','yyyy-mm-dd')	\n");
		}
	    if(condition.getSelectDateTo()!=null)
	    {
	    	m_sbFrom.append("		  and nvl(a.dtdate,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"+DataFormat.getDateString(condition.getSelectDateTo())+"','yyyy-mm-dd')	\n");
	    }
		m_sbFrom.append(" group by a.naccountid,a.nsubaccountid,a.minterestrate,a.minterestbalance  \n");
		m_sbFrom.append(" order by a.naccountid, a.nsubaccountid, min(a.dtdate)) t, \n");
		m_sbFrom.append(" sett_account b, \n");
		m_sbFrom.append(" loan_contractform c, \n");
		m_sbFrom.append(" client d, \n");
		m_sbFrom.append(" loan_payform e, \n");
		m_sbFrom.append(" sett_subaccount f \n");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" where t.naccountid = b.id  \n");
		m_sbWhere.append(" and t.nsubaccountid = f.id  \n");
		m_sbWhere.append(" and b.nclientid = d.id  \n");
		m_sbWhere.append(" and f.AL_NLOANNOTEID = e.id  \n");
		m_sbWhere.append(" and e.ncontractid = c.id  \n");
		
		if (condition.getStartClientCode() != null && condition.getStartClientCode().length() > 0)
		{
			m_sbWhere.append("        and d.scode>='" + condition.getStartClientCode() + "' \n");
		}
		if (condition.getEndClientCode() != null && condition.getEndClientCode().length() > 0)
		{
			m_sbWhere.append("        and d.scode<='" + condition.getEndClientCode() + "' \n");
		}
		if (condition.getStartAccountNo() != null && condition.getStartAccountNo().length() > 0)
		{
			m_sbWhere.append("        and b.sAccountNo>='" + condition.getStartAccountNo() + "' \n");
		}
		if (condition.getEndAccountNo() != null && condition.getEndAccountNo().length() > 0)
		{
			m_sbWhere.append("        and b.sAccountNo<='" + condition.getEndAccountNo() + "' \n");
		}
		if (condition.getStartContractCode() != null && condition.getStartContractCode().length() > 0)
		{
			m_sbWhere.append("        and c.scontractcode>='" + condition.getStartContractCode() + "' \n");
		}
		if (condition.getEndContractCode() != null && condition.getEndContractCode().length() > 0)
		{
			m_sbWhere.append("        and c.scontractcode<='" + condition.getEndContractCode() + "' \n");
		}
		if(!"".equals(condition.getAccountTypeSet()))
		{
			m_sbWhere.append("        and b.naccounttypeid  IN(" + condition.getAccountTypeSet()+") \n");
		}
		else
		{
			long[] arrayReturn = SETTConstant.AccountType.getLoanAccountTypeCodeForAudit(condition.getCurrencyID(),condition.getOfficeID());
			String strAccountTypeID="";
			for(int i=0;i<arrayReturn.length;i++)
			{
				strAccountTypeID=strAccountTypeID + arrayReturn[i]+"";
				if(arrayReturn[i] >0)
				{
					strAccountTypeID=strAccountTypeID + ",";
				}
			}
			strAccountTypeID = strAccountTypeID.substring(0,strAccountTypeID.length()-1);
			m_sbWhere.append("        and b.naccounttypeid  IN(" +strAccountTypeID+") \n");
		}
		
		if (condition.getOfficeID() > 0)
		{
			m_sbWhere.append("        and b.nofficeid=" + condition.getOfficeID() + " \n");
		}
		if (condition.getCurrencyID() > 0)
		{
			m_sbWhere.append("        and b.ncurrencyid=" + condition.getCurrencyID() + " \n");
		}
		m_sbOrderBy  = new StringBuffer();
		m_sbOrderBy.append("  order by d.scode,c.scontractcode,e.id, t.startDate \n");
			result = new ArrayList();
			try {
				initDAO();
				prepareStatement( m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString()+m_sbOrderBy.toString());
				System.out.println("贷款利息审计SQL add by xfma(马现福):  \n"+m_sbSelect.toString() + m_sbFrom.toString()
						+ m_sbWhere.toString() + m_sbOrderBy.toString());
				executeQuery();
				int tempOnePropertyCount = 0;
				int tempTwoPropertyCount = 0;
				int tempThreePropertyCount = 0;
				long tempClientID = -1;
				long tempcontractID  = -1;
				long temppayID = -1;
				HashMap map = new HashMap();
				result.add(map);
				while(transRS.next()){
					InterestAuditResultInfo resultInfo = new InterestAuditResultInfo();
					//属性一
					if(tempClientID==transRS.getLong("clientID"))
					{
						tempOnePropertyCount++;
						if(tempClientID>0)
						{
							map.put("clientID"+tempClientID, String.valueOf(tempOnePropertyCount));
						}
					}
					else
					{
						tempOnePropertyCount=1;
						if(transRS.getLong("clientID")>0)
						{
							map.put("clientID"+transRS.getLong("clientID"), String.valueOf(tempOnePropertyCount));
						}
						
					}
					//属性二
					if(tempcontractID==transRS.getLong("contractID"))
					{
						tempTwoPropertyCount++;
						if(tempcontractID>0)
						{
							map.put("contractID"+tempcontractID, String.valueOf(tempTwoPropertyCount));
						}
					}
					else
					{
						tempTwoPropertyCount=1;
						if(transRS.getLong("contractID")>0)
						{
							map.put("contractID"+transRS.getLong("contractID"), String.valueOf(tempTwoPropertyCount));
						}
						
					}
					
					//属性三
					if(temppayID==transRS.getLong("payID"))
					{
						tempThreePropertyCount++;
						if(temppayID>0)
						{
							map.put("payID"+temppayID, String.valueOf(tempThreePropertyCount));
						}
					}
					else
					{
						tempThreePropertyCount=1;
						if(transRS.getLong("payID")>0)
						{
							map.put("payID"+transRS.getLong("payID"), String.valueOf(tempThreePropertyCount));
						}
						
					}
					
					resultInfo.setClientID(transRS.getLong("clientID"));
					tempClientID = resultInfo.getClientID();
					resultInfo.setClientName(transRS.getString("clientName"));
					resultInfo.setContractID(transRS.getLong("contractID"));
					tempcontractID = resultInfo.getContractID();
					resultInfo.setContractCode(transRS.getString("contractCode"));
					resultInfo.setContractTypeID(transRS.getLong("contractTypeID"));
					resultInfo.setPayID(transRS.getLong("payID"));
					temppayID = resultInfo.getPayID();
					resultInfo.setPayCode(transRS.getString("payCode"));
					resultInfo.setStartDate(transRS.getTimestamp("startDate"));
					resultInfo.setInterestRate(transRS.getDouble("interestRate"));
					resultInfo.setBalance(transRS.getDouble("balance"));
					resultInfo.setSumBalance(transRS.getDouble("sumbalance"));
					resultInfo.setInterestBalance(transRS.getDouble("interestBalance"));
					resultInfo.setTempcount(transRS.getLong("tempcount"));
					resultInfo.setEndDate(DataFormat.getNextDate(resultInfo.getStartDate(),(int)resultInfo.getTempcount()-1));//国电需求算头不算尾 updated by xfma
					result.add(resultInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException("查询贷款利息积数审计出现错误");
			}
			finally{
				finalizeDAO();
			}
		return result;
	}
}
