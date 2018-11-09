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
public class Report9Dao extends LoanDAO {
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
	public Report9Dao() {
		super("");
	}
	
	/**
	 * 获得SQL语句
	 * @author zqhu
	 * @param  ReportWhereInfo reportWhereInfo
	 * @return 
	 * @exception
	 */
	
	
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
	public ReportResultInfo[] QueryConsignLoanDetail(
			ReportWhereInfo wInfo) throws Exception {
		
		m_sbSelect = new StringBuffer();
    	m_sbSelect.append("select ");
    	m_sbSelect.append("aa.id LongColumn1,--记录id \n");
    	m_sbSelect.append("aa.nBorrowClientId LongColumn2,--借款单位名称ID  \n");
    	m_sbSelect.append("aa.Nconsignclientid LongColumn3,--委托单位名称ID \n");
    	m_sbSelect.append("aa.niscredit  LongColumn4,--是否信用保证 0--否 1--是 \n");
    	m_sbSelect.append("aa.nisassure  LongColumn5,--是否担保 0--否 1--是  \n");
    	m_sbSelect.append("aa.nisImpawn  LongColumn6,--是否质押 0--否 1--是  \n");
    	m_sbSelect.append("aa.nispledge  LongColumn7, --是否抵押 0--否 1--是 \n");
    	m_sbSelect.append("	aa.ISRECOGNIZANCE LongColumn9,--是否保证金 \n");
    	m_sbSelect.append("aa.sContractCode StringColumn1,--合同编号  \n");
    	
    	m_sbSelect.append("cc.sName StringColumn2,--借款单位名称  \n");
    	m_sbSelect.append("dd.sName StringColumn3,--委托单位名称  \n");
    	m_sbSelect.append("aa.mLoanAmount DoubleColumn1,--合同金额  \n");
    	m_sbSelect.append("aa.dtStartDate TsColumn1,--贷款起始日期  \n");
    	m_sbSelect.append("aa.dtEndDate TsColumn2 --贷款起始日期  \n");
    	m_sbFrom = new StringBuffer();
    	m_sbFrom.append(" from ");
		m_sbFrom.append(" loan_contractForm aa, \n");		
		m_sbFrom.append("	client cc ,client dd\n");		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" where ");
		m_sbWhere.append(" aa.nCurrencyId = "+wInfo.getCurrencyId()+" \n");
		m_sbWhere.append(" and aa.nofficeId = "+wInfo.getOfficeId()+" \n");
		m_sbWhere.append(" and aa.nBorrowClientId = cc.Id \n");	
		m_sbWhere.append(" and aa.NconsignclientId = dd.Id \n");	
		m_sbWhere.append(createSqlToVerifyDate(
                "aa.dtStartDate","aa.dtenddate", wInfo.getDate()));
		m_sbWhere.append(" and aa.Ntypeid="+LOANConstant.LoanType.WT+"\n");
		m_sbWhere.append(" and (aa.nstatusid="+LOANConstant.ContractStatus.ACTIVE+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.EXTEND+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.OVERDUE+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.DELAYDEBT+"");
		m_sbWhere.append(" or aa.nstatusid="+LOANConstant.ContractStatus.BADDEBT+"");
		m_sbWhere.append(")");
		String orderBy = null;
		switch ((int) wInfo.getOrderBy()) {
		case (int) ORDERBY_CONTRACTCODE:
			orderBy = " order by aa.sContractCode";
			break;
		case (int) ORDERBY_ENDDATE:
			orderBy = " order by aa.dtEndDate";
			break;
		default:
			orderBy = " order by aa.sContractCode";
			break;
		}
		m_sbWhere.append(orderBy);
		
		
		StringBuffer result = new StringBuffer();
		result.append(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
		System.out.println(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
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
		catch (Exception e)
		{
			e.printStackTrace();
        } 
		finally {
            finalizeDAO();
        }
		return results;
	}
	public static void main(String[] args) {
       
       
        
    }
	}