/*
 * Created on 2005-10-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.loan.report.dao;

import java.sql.Timestamp;
import java.util.Vector;
import java.util.*;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.loan.report.bizlogic.Report1;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.SqlUtil;
import com.iss.itreasury.loan.util.LOANConstant;



/**
 * @author kewen hu
 * 
 * @version 3.0
 */
public class Report10Dao extends LoanDAO {
	/** select */
	private StringBuffer m_sbSelect = null;
	/** from */
	private StringBuffer m_sbFrom = null;
	/** where */
	private StringBuffer m_sbWhere = null;
	/** 按合同号排序 */
	private static final long ORDERBY_CONTRACTCODE = 1;
	/** 按到期日排序 */
	private static final long ORDERBY_ENDDATE = 2;
	
	

	/**
	 * 构造函数
	 */
	public Report10Dao() {
		super("");
	}
	
	//日期格式化
	private String createSqlToVerifyDate(String dayColumnName,
			String dayColumnName1,Timestamp firstDay) {
        StringBuffer result = new StringBuffer();
        result.append("and \n");
        result.append("'" + DataFormat.getDateString(firstDay) + "' \n");
        result.append("between to_char(" + dayColumnName + ",'yyyy-mm-dd')");
        result.append("and to_char(" + dayColumnName1 + ",'yyyy-mm-dd')\n");        
        //System.out.println(result.toString());
        return result.toString();
    }
	/**
	 * 通过页面条件查询结果集
	 * @author liwang
	 * @param  Timestamp firstDay
	 * @param  Timestamp lastDay
	 * @param  QueryFixedDepositInfo qInfo
	 * @param  String order
	 * @return results
	 * @exception Exception
	 */
	public ReportResultInfo[] queryAssureOperationBalanceDetail(
			ReportWhereInfo wInfo) throws Exception {
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("select");
		m_sbSelect.append("	aa.id LongColumn1,--记录id \n");
		m_sbSelect.append("	aa.sContractCode StringColumn3,--合同编号 \n");
		m_sbSelect.append("	aa.nBorrowClientId LongColumn2,--借款单位名称ID \n");
		m_sbSelect.append("	aa.nIsCredit LongColumn3,--是否信用 \n");
		m_sbSelect.append("	aa.nIsAssure LongColumn4,--是否保证 \n");
		m_sbSelect.append("	aa.nIsImpawn LongColumn5,--是否质押 \n");
		m_sbSelect.append("	aa.nIsPledge LongColumn6,--是否抵押 \n");	
		m_sbSelect.append("	aa.ISRECOGNIZANCE LongColumn7,--是否保证金 \n");
		m_sbSelect.append("	aa.nIntervalNum LongColumn8,--贷款期限 \n");
		m_sbSelect.append("	cc.sName StringColumn1,--借款单位名称 \n");
		m_sbSelect.append("	aa.BENEFICIARY StringColumn2,--贷款银行 \n");
		m_sbSelect.append("	aa.mLoanAmount DoubleColumn1,--合同金额 \n");		
		m_sbSelect.append("	aa.ASSURECHARGERATE DoubleColumn2,--手续费率 \n");
		m_sbSelect.append("	aa.dtStartDate TsColumn1,--贷款起始日期 \n");
		m_sbSelect.append("	aa.dtEndDate TsColumn2 --贷款结止始日期 \n");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" from ");
		m_sbFrom.append(" loan_contractForm aa, \n");		
		m_sbFrom.append("	client cc \n");		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" where ");
		m_sbWhere.append(" aa.nCurrencyId = "+wInfo.getCurrencyId()+" \n");
		m_sbWhere.append("and aa.nofficeId = "+wInfo.getOfficeId()+" \n");
		m_sbWhere.append("and aa.nBorrowClientId = cc.Id \n");
		m_sbWhere.append(createSqlToVerifyDate(
                "aa.dtStartDate","aa.dtenddate", wInfo.getDate()));
		m_sbWhere.append(" and aa.Ntypeid="+LOANConstant.LoanType.DB+"\n");		
		m_sbWhere.append(" and (aa.nstatusid="+LOANConstant.ContractStatus.ACTIVE+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.EXTEND+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.OVERDUE+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.DELAYDEBT+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.BADDEBT+"");
		
		m_sbWhere.append(")");
		StringBuffer result = new StringBuffer();
		result.append(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
		//System.out.println(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
		ReportResultInfo[] results = null;
		try
		{
			initDAO();
			prepareStatement(result.toString());
			executeQuery();
			
			results = (ReportResultInfo[]) SqlUtil
            .parseDataEntityBeans(transRS, "",
                    "com.iss.itreasury.loan.report.dataentity.ReportResultInfo");
		}
		catch (Exception e) {
            throw e;
        } finally {
            finalizeDAO();
        }
		return results;
	}
	public static void main(String[] args) {
        
       
        
    }
	}