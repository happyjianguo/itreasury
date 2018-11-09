package com.iss.itreasury.loan.reportelements.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.reportelements.dataentity.RepayExtInfo;
import com.iss.itreasury.loan.reportelements.dataentity.RepayExtQueryInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.system.dao.PageLoader;

public class RepayExtDao extends ITreasuryDAO {
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	
	public static void main(String[] args) {
	}
	
	public RepayExtDao()
	{
		super("LOAN_AHEADREPAYFORM","SEQ_LOAN_AHEADREPAYFORM");
	}
	
	//	翻页查询
	public PageLoader queryRepayExtQueryInfo(RepayExtQueryInfo queryConditionRepayExtQueryInfo) throws Exception
	{
		getTransLogSQL(queryConditionRepayExtQueryInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.reportelements.dataentity.RepayExtQueryInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 产生查询SQL  
	 * @param info
	 *
	 */
	public void getTransLogSQL(RepayExtQueryInfo queryConditionRepayExtQueryInfo)
	{
		try{
			
			m_sbSelect = new StringBuffer();
			// select 
			m_sbSelect.append(" h.id id, h.scode repayCode, f.scontractcode scontractcodeID, f.ntypeid loanType, f.nborrowclientid clientID, c.sName as borrowClientName, \n");
			m_sbSelect.append(" p.scode payCode, p.mamount payMoney, h.mamount repayMoney,  h.paydate repayDate, h.nisahead nisahead, h.sdetailremarks sdetailremarks \n");
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" loan_aheadrepayform h, loan_payform p, loan_contractform f, client c \n");
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" 1=1 ");
			m_sbWhere.append(" and p.id = h.nloanpaynoticeid AND f.id = p.ncontractid and c.id = f.nborrowclientid AND h.nstatusid not in ('5', '6') " + " \n");
			
			String strTemp = queryConditionRepayExtQueryInfo.getScontractcodeFrist();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				//strTemp = convert("first", strTemp);
				m_sbWhere.append(" and f.scontractcode > '" + strTemp + "'");
			}
			
			strTemp = queryConditionRepayExtQueryInfo.getScontractcodeLast();
			if (strTemp!=null && !strTemp.equals(""))
			{	
				//strTemp = convert("last", strTemp);
				m_sbWhere.append(" and f.scontractcode < '" + strTemp + "'");
			}
			strTemp = queryConditionRepayExtQueryInfo.getScontractcodeID();
			if (strTemp!=null && !strTemp.equals("")) 
			{
				m_sbWhere.append(" and f.scontractcode = '" + strTemp + "'");
			}
			
			strTemp = queryConditionRepayExtQueryInfo.getPayCode();
			if (strTemp!=null && !strTemp.equals("")) 
			{
				m_sbWhere.append(" and p.scode = '" + strTemp + "'");
			}
			
			strTemp = queryConditionRepayExtQueryInfo.getRepayCode();
			if (strTemp!=null && !strTemp.equals("")) 
			{
				m_sbWhere.append(" and h.scode = '" + strTemp + "'");
			}
			
			switch (queryConditionRepayExtQueryInfo.getStatu()) {
			case 1:
				m_sbWhere.append(" and h.sdetailremarks is null ");
				break;
			case 2:
				m_sbWhere.append(" and h.sdetailremarks is not null ");
				break;
			}
			
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by h.id desc");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 保存loan_aheadrepayform.sdetailremarks字段, 为了保存 报表要素信息补录-还款通知单-还款备注明细
	 * 
	 * @param repayExtInfo
	 * @return
	 * @throws Exception
	 */
	public long save(RepayExtInfo repayExtInfo) throws Exception {
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = 0;
		
		try {
				con = Database.getConnection();
				StringBuffer sql = new StringBuffer();
				sql.append("update loan_aheadrepayform h set h.sdetailremarks = '"+repayExtInfo.getSdetailremarks()+"' where exists ( ");
				sql.append("select 1 from loan_payform p, loan_contractform f, client c ");
				sql.append("where 1=1 and p.id = h.nloanpaynoticeid AND f.id = p.ncontractid and c.id = f.nborrowclientid ");
				sql.append("and f.scontractcode = '"+repayExtInfo.getScontractcodeID()+"' and p.scode = '"+repayExtInfo.getPayCode()+"' and h.scode = '"+repayExtInfo.getRepayCode()+"' ");
				sql.append(") ");
				ps = con.prepareStatement(sql.toString());
				
				System.out.println(repayExtInfo.getSdetailremarks() + " | " + repayExtInfo.getScontractcodeID() +" | "+ repayExtInfo.getPayCode()+" | "+ repayExtInfo.getRepayCode());
				
				System.out.println(sql);
				
				lResult = ps.executeUpdate();
				
		} catch (Exception e) {
			Log.print(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				Log.print(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
/*	private String convert(String site, String scontractcodeID){
		String result = "";
		
		if (site.equals("first")) {
			result = " > ";
		} else if (site.equals("last")) {
			result = " < ";
		}
		
		result += scontractcodeID.substring(2);
		
		return result;
	}*/
}
