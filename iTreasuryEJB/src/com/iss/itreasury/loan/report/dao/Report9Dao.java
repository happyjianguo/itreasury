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
	/** ����ͬ������ */
	private static final long ORDERBY_CONTRACTCODE = 1;
	/** ������������ */
	private static final long ORDERBY_ENDDATE = 2;
	
	

	/**
	 * ���캯��
	 */
	public Report9Dao() {
		super("");
	}
	
	/**
	 * ���SQL���
	 * @author zqhu
	 * @param  ReportWhereInfo reportWhereInfo
	 * @return 
	 * @exception
	 */
	
	
	//���ڸ�ʽ��
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
	 * ͨ��ҳ��������ѯ�����
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
    	m_sbSelect.append("aa.id LongColumn1,--��¼id \n");
    	m_sbSelect.append("aa.nBorrowClientId LongColumn2,--��λ����ID  \n");
    	m_sbSelect.append("aa.Nconsignclientid LongColumn3,--ί�е�λ����ID \n");
    	m_sbSelect.append("aa.niscredit  LongColumn4,--�Ƿ����ñ�֤ 0--�� 1--�� \n");
    	m_sbSelect.append("aa.nisassure  LongColumn5,--�Ƿ񵣱� 0--�� 1--��  \n");
    	m_sbSelect.append("aa.nisImpawn  LongColumn6,--�Ƿ���Ѻ 0--�� 1--��  \n");
    	m_sbSelect.append("aa.nispledge  LongColumn7, --�Ƿ��Ѻ 0--�� 1--�� \n");
    	m_sbSelect.append("	aa.ISRECOGNIZANCE LongColumn9,--�Ƿ�֤�� \n");
    	m_sbSelect.append("aa.sContractCode StringColumn1,--��ͬ���  \n");
    	
    	m_sbSelect.append("cc.sName StringColumn2,--��λ����  \n");
    	m_sbSelect.append("dd.sName StringColumn3,--ί�е�λ����  \n");
    	m_sbSelect.append("aa.mLoanAmount DoubleColumn1,--��ͬ���  \n");
    	m_sbSelect.append("aa.dtStartDate TsColumn1,--������ʼ����  \n");
    	m_sbSelect.append("aa.dtEndDate TsColumn2 --������ʼ����  \n");
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