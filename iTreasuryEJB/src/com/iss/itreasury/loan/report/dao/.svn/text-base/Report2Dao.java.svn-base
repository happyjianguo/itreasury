/*
 * Created on 2005-10-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.loan.report.dao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.loan.report.bizlogic.Report1;
import com.iss.itreasury.loan.report.dataentity.ReportResultInfo;
import com.iss.itreasury.loan.report.dataentity.ReportWhereInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;
import com.iss.system.dao.SqlUtil;

/**
 * @author kewen hu
 * 
 * @version 3.0
 */
public class Report2Dao extends LoanDAO {
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
	public Report2Dao() {
		super("");
	}

	/**
	 * 获得SQL语句
	 * @author zqhu
	 * @param  ReportWhereInfo reportWhereInfo
	 * @return 
	 * @exception
	 */
	public void getStringSQL(ReportWhereInfo reportWhereInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("	aa.sContractCode StringColumn1,--合同编号 \n");
		m_sbSelect.append("	aa.nBorrowClientId LongColumn1,--借款单位名称ID \n");
		m_sbSelect.append("	cc.sName StringColumn2,--借款单位名称 \n");
		m_sbSelect.append("	aa.mLoanAmount DoubleColumn1,--合同金额 \n");
		m_sbSelect.append("	bb.mBalance DoubleColumn2,--本金余额 \n");
		m_sbSelect.append("	aa.nIntervalNum LongColumn2,--贷款期限 \n");
		m_sbSelect.append("	aa.dtStartDate TsColumn1,--贷款起始日期 \n");
		m_sbSelect.append("	aa.dtEndDate TsColumn2,--贷款起始日期 \n");
		m_sbSelect.append("	aa.mInterestRate DoubleColumn3,--贷款利率 \n");
		m_sbSelect.append("	bb.mInterest DoubleColumn4,--贷款应付利息 \n");
		m_sbSelect.append("	aa.nIsCredit LongColumn3,--是否信用 \n");
		m_sbSelect.append("	aa.nIsAssure LongColumn4,--是否保证 \n");
		m_sbSelect.append("	aa.nIsImpawn LongColumn5,--是否质押 \n");
		m_sbSelect.append("	aa.nIsPledge LongColumn6,--是否抵押 \n");
		m_sbSelect.append("	aa.ISRECOGNIZANCE LongColumn7 --是否保证金 \n");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" loan_contractForm aa, \n");
		m_sbFrom.append("	(select a.nContractId, sum(b.mBalance) mBalance, sum(mInterest) mInterest \n");
		m_sbFrom.append("	from loan_payForm a, sett_subaccount b \n");
		m_sbFrom.append("	where 1 = 1 \n");
		m_sbFrom.append("	and a.nCurrencyId = "+reportWhereInfo.getCurrencyId()+" \n");
		m_sbFrom.append("	and a.nofficeId = "+reportWhereInfo.getOfficeId()+" \n");
		m_sbFrom.append("	and a.Id=b.al_nLoanNoteId \n");
		m_sbFrom.append("	group by a.nContractId) bb, \n");
		m_sbFrom.append("	client cc \n");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1 = 1 \n");
		m_sbWhere.append("and aa.Id=bb.nContractId(+) \n");
		m_sbWhere.append("and aa.nCurrencyId = "+reportWhereInfo.getCurrencyId()+" \n");
		m_sbWhere.append("and aa.nofficeId = "+reportWhereInfo.getOfficeId()+" \n");
		m_sbWhere.append("and aa.nBorrowClientId = cc.Id \n");
	}

	
	/**
	 * 通过页面条件查询结果集
	 * @author zqhu
	 * @param  ReportWhereInfo reportWhereInfo
	 * @return PageLoader
	 * @exception Exception
	 */
	public PageLoader findForReport2(ReportWhereInfo reportWhereInfo) throws Exception {
		//获取SQL语句
		this.getStringSQL(reportWhereInfo);

		//获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.report.dataentity.ReportResultInfo",
			null
		);
		String orderBy = null;
		switch ((int) reportWhereInfo.getOrderBy()) {
		case (int) ORDERBY_CONTRACTCODE:
			orderBy = " order by aa.nTypeId3,aa.sContractCode";
			break;
		case (int) ORDERBY_ENDDATE:
			orderBy = " order by aa.nTypeId3,aa.dtEndDate";
			break;
		default:
			orderBy = " order by aa.nTypeId3,aa.sContractCode";
			break;
		}
		pageLoader.setOrderBy(orderBy);

		return pageLoader;
	}
//	日期格式化
	private String createSqlToVerifyDate(String dayColumnName,
			String dayColumnName1,Timestamp firstDay) {
        StringBuffer result = new StringBuffer();
        result.append("and \n");
        result.append("'" + DataFormat.getDateString(firstDay) + "' \n");
        result.append("between to_char(" + dayColumnName + ",'yyyy-mm-dd')");
        result.append("and to_char(" + dayColumnName1 + ",'yyyy-mm-dd')\n");        
        System.out.println(result.toString());
        return result.toString();
    }
	
	
	public ReportResultInfo[] queryLoanOperationBalanceDetail(ReportWhereInfo wInfo) throws Exception {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("select");
		m_sbSelect.append("	aa.id LongColumn1,--记录id \n");
		m_sbSelect.append("	aa.nBorrowClientId LongColumn2,--借款单位名称ID \n");
		m_sbSelect.append("	aa.nIntervalNum LongColumn3,--贷款期限 \n");		
		m_sbSelect.append("	aa.nIsCredit LongColumn4,--是否信用 \n");
		m_sbSelect.append("	aa.nIsAssure LongColumn5,--是否保证 \n");
		m_sbSelect.append("	aa.nIsImpawn LongColumn6,--是否质押 \n");
		m_sbSelect.append("	aa.nIsPledge LongColumn7,--是否抵押 \n");
		m_sbSelect.append("	aa.ntypeid3 LongColumn8, --按行业分类ID \n");
		m_sbSelect.append("	aa.ISRECOGNIZANCE LongColumn9,--是否保证金 \n");
		m_sbSelect.append("	aa.sContractCode StringColumn1,--合同编号 \n");
		m_sbSelect.append("	cc.sName StringColumn2,--借款单位名称 \n");
		m_sbSelect.append("	aa.mLoanAmount DoubleColumn1,--合同金额 \n");		
		m_sbSelect.append("	aa.mInterestRate DoubleColumn3,--贷款利率 \n");
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
		m_sbWhere.append(" and aa.Ntypeid="+LOANConstant.LoanType.ZY+"\n");
//	    m_sbWhere.append(" and (aa.ntypeid3="+LOANConstant.IndustryType2.PLATFORM+" or aa.ntypeid3="+LOANConstant.IndustryType2.LOCAL+")");
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
        Report1 report = new Report1();
        Vector v = null;
        Timestamp date=DataFormat.getDateTime("2005-11-4");
        ReportWhereInfo wInfo= new ReportWhereInfo();
        wInfo.setCurrencyId(1);
        wInfo.setOfficeId(1);
        wInfo.setDate(date);
        long orderby=1;
        wInfo.setOrderBy(orderby);
        try {
            v=report.queryLoanOperationBalanceDetail(wInfo);
           
        } catch (Exception e) {
           
            e.printStackTrace();
        }
        for(int i=0;i<v.size();i++){
            ReportResultInfo info=(ReportResultInfo)v.elementAt(i);
            //System.out.println(info.getDoubleColumn1()+"==="+info.getDoubleColumn2());
        
        
        }
        
    }
}