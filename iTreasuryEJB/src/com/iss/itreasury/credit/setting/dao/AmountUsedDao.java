package com.iss.itreasury.credit.setting.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

public class AmountUsedDao extends ITreasuryDAO{

	/**
	 * 贷款申请占用的额度biz层
	 * add by liaiyi 2013-01-09
	 */
	private String getAllCreditTypeString(long officeId, long currencyId){
		long[] creditTypeId = LOANConstant.CreditType.getAllCode(officeId, currencyId);
		if(creditTypeId!=null && creditTypeId.length>0)
		{
			String strTemp = "";
			for(int i=0; i<creditTypeId.length; i++)
			{
				if(!((creditTypeId[i] == LOANConstant.CreditType.ZY) && (Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1)))
				strTemp += creditTypeId[i] + ",";
			}
			//去掉最后的','号
			return strTemp.substring(0, strTemp.length()-1);
			
		}
		
		return null;
	}	
	
	
	public String queryApplyUsedAmountSQL(long ID){
		 
		StringBuffer strSQL = new StringBuffer();
        AmountFormViewInfo amountFormViewInfo = null;
        
		Connection conn = null;
		AmountFormDao amountFormDao = null;
		
		try {
			conn = Database.getConnection();
			amountFormDao = new AmountFormDao(conn);
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			conn.close();
			conn = null;
			
		
		strSQL.append(" select c.sName,a.sapplycode, a.ntypeid nloantype, nvl(a.MEXAMINEAMOUNT, 0) mApplyAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
    	strSQL.append(" from loan_loanForm a, client c ");
    	strSQL.append(" where a.nBorrowClientID = "+amountFormViewInfo.getClientId());
    	strSQL.append(" and a.nstatusID = "+LOANConstant.LoanStatus.APPROVALING);
    	strSQL.append(" and a.nofficeid = "+amountFormViewInfo.getOfficeId());
    	strSQL.append(" and a.ncurrencyid = "+amountFormViewInfo.getCurrencyId());
    	strSQL.append(" and a.nBorrowClientID = c.id");
    	strSQL.append(" and a.dtstartdate <= to_date('"+DataFormat.formatDate(amountFormViewInfo.getEndDate())+"','yyyy-MM-dd')");
    	strSQL.append(" and a.dtstartdate >= to_date('"+DataFormat.formatDate(amountFormViewInfo.getStartDate())+"','yyyy-MM-dd')");
    	strSQL.append(" and a.ntypeid in(" + getAllCreditTypeString(amountFormViewInfo.getOfficeId(), amountFormViewInfo.getCurrencyId()) + ")");
    	strSQL.append(" order by a.ntypeid, a.dtstartdate ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return strSQL.toString();
	}
	
	/**
	 * 贷款合同占用的额度 biz层
	 * add by liaiyi 2013-01-09
	 */
	
	public String queryContractUsedAmount(long ID){
		 
		StringBuffer strSQL = new StringBuffer();
		
        AmountFormViewInfo amountFormViewInfo = null;
        
		Connection conn = null;
		AmountFormDao amountFormDao = null;
		
		try {
			conn = Database.getConnection();
			amountFormDao = new AmountFormDao(conn);
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			conn.close();
			conn = null;
		
		strSQL.append(" select  c.sName,a.scontractcode, a.ntypeid loantype, nvl(a.MEXAMINEAMOUNT, 0) mContractAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
    	strSQL.append(" from loan_contractForm a, client c");
    	strSQL.append(" where a.nBorrowClientID = "+amountFormViewInfo.getClientId());
    	strSQL.append(" and a.nstatusID in (");
    	strSQL.append(LOANConstant.ContractStatus.SAVE + ", ");
    	strSQL.append(LOANConstant.ContractStatus.SUBMIT + ", ");
    	strSQL.append(LOANConstant.ContractStatus.CHECK + ", ");
    	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
    	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
    	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
    	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
    	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
    	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ", ");
    	strSQL.append(LOANConstant.ContractStatus.APPROVALING + ")");
    	strSQL.append(" and a.nofficeid = "+amountFormViewInfo.getOfficeId());
    	strSQL.append(" and a.ncurrencyid = "+amountFormViewInfo.getCurrencyId());
    	strSQL.append(" and a.nBorrowClientID = c.id");
    	strSQL.append(" and a.dtstartdate <= to_date('"+DataFormat.formatDate(amountFormViewInfo.getEndDate())+"','yyyy-MM-dd')");
    	strSQL.append(" and a.dtstartdate >= to_date('"+DataFormat.formatDate(amountFormViewInfo.getStartDate())+"','yyyy-MM-dd')");
    	strSQL.append(" and a.ntypeid in(" + getAllCreditTypeString(amountFormViewInfo.getOfficeId(), amountFormViewInfo.getCurrencyId()) + ")");
    	strSQL.append(" order by a.ntypeid, a.dtstartdate ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return strSQL.toString();
	}
	/**
	 * 自营贷款放款单占用的额度 biz层
	 * add by liaiyi 2013-01-09
	 */
	
	public String queryLoanUsedAmountSQL(long ID){
		 
		StringBuffer sbSQL = new StringBuffer();
		
		 AmountFormViewInfo amountFormViewInfo = null;
	        
		Connection conn = null;
		AmountFormDao amountFormDao = null;
		
		try {
			conn = Database.getConnection();
			amountFormDao = new AmountFormDao(conn);
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			conn.close();
			conn = null;
		
		sbSQL.append(" select r.sCode,r.sName,s.scontractcode,t.scode loanpfcode,t.mamount,t.dtstart,t.dtend,t.nstatusid ");
		sbSQL.append(" from loan_payform t,loan_contractform s,client r");
		sbSQL.append(" where s.id = t.ncontractid and s.nborrowclientid = r.id");
		sbSQL.append(" and t.nstatusid in ("+ LOANConstant.LoanPayNoticeStatus.CHECK +","+ LOANConstant.LoanPayNoticeStatus.USED +","+ LOANConstant.LoanPayNoticeStatus.APPROVALING +")");
		sbSQL.append(" and s.nborrowclientid = " + amountFormViewInfo.getClientId()); 
		sbSQL.append(" and s.nofficeid = " + amountFormViewInfo.getOfficeId()); 
		sbSQL.append(" and s.ncurrencyid = " + amountFormViewInfo.getCurrencyId()); 
		sbSQL.append(" and to_char(t.dtstart,'yyyy-mm-dd') >= '" + DataFormat.formatDate(amountFormViewInfo.getStartDate()) + "' ");
		sbSQL.append(" and to_char(t.dtstart,'yyyy-mm-dd') <= '" + DataFormat.formatDate(amountFormViewInfo.getEndDate()) + "' ");
		sbSQL.append(" and s.ntypeid = " + LOANConstant.CreditType.ZY);
		sbSQL.append(" and s.nstatusID in (");
		sbSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
		sbSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
		sbSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
		sbSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
		sbSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
		sbSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
		sbSQL.append(" order by r.sCode,t.dtstart ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sbSQL.toString();
	}
	
	/**
	 * 还款单释放的额度 (自营贷款非循环贷款时还款单金额List) biz层
	 * add by liaiyi 2013-01-09
	 */
	
	public String queryRepayUsedAmountSQL(long ID){
		 
		StringBuffer strSQL = new StringBuffer();
		
		 AmountFormViewInfo amountFormViewInfo = null;
	        
		Connection conn = null;
		AmountFormDao amountFormDao = null;
		
		try {
			conn = Database.getConnection();
			amountFormDao = new AmountFormDao(conn);
			amountFormViewInfo = amountFormDao.getAmountFormViewDetail(ID);
			conn.close();
			conn = null;
		
        	strSQL.append(" select d.sName,a.scontractcode, nvl(c.mAmount, 0) mCircleAmount,a.dtstartdate,a.dtenddate,a.nintervalnum,a.nstatusid");
        	strSQL.append(" from loan_contractForm a, sett_transRepaymentloan c,client d ");
        	strSQL.append(" where a.nBorrowClientID = "+amountFormViewInfo.getClientId());
        	if(!(Config.getInteger(ConfigConstant.LOAN_CREDIT_CHECK, 1) == 1))
        	{
        	   strSQL.append(" and a.niscircle = 2");
        	}
        	strSQL.append(" and a.nstatusID in (");
        	strSQL.append(LOANConstant.ContractStatus.NOTACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.ACTIVE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.EXTEND + ", ");
        	strSQL.append(LOANConstant.ContractStatus.OVERDUE + ", ");
        	strSQL.append(LOANConstant.ContractStatus.DELAYDEBT + ", ");
        	strSQL.append(LOANConstant.ContractStatus.BADDEBT + ")");
        	strSQL.append(" and a.nofficeid = "+amountFormViewInfo.getOfficeId());
        	strSQL.append(" and a.ncurrencyid = "+amountFormViewInfo.getCurrencyId());
        	strSQL.append(" and a.nTypeID = "+LOANConstant.LoanType.ZY);
        	strSQL.append(" and a.dtstartdate <= to_date('"+DataFormat.formatDate(amountFormViewInfo.getEndDate())+"','yyyy-MM-dd')");
        	strSQL.append(" and c.dtintereststart <= to_date('"+DataFormat.formatDate(amountFormViewInfo.getEndDate())+"','yyyy-MM-dd')");
        	strSQL.append(" and a.dtstartdate >= to_date('"+DataFormat.formatDate(amountFormViewInfo.getStartDate())+"','yyyy-MM-dd')");
        	strSQL.append(" and c.dtintereststart >= to_date('"+DataFormat.formatDate(amountFormViewInfo.getStartDate())+"','yyyy-MM-dd')");
        	strSQL.append(" and a.id = c.nLoanContractID");
        	strSQL.append(" and a.nBorrowClientID = d.id");
        	strSQL.append(" and c.nstatusid = "+SETTConstant.TransactionStatus.CHECK);
        	strSQL.append(" order by a.dtstartdate ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return strSQL.toString();
	}
}
