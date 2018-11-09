/*
 * Created on 2005-12-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 */
package com.iss.itreasury.loan.query.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;



import com.iss.itreasury.loan.query.dataentity.RepurchaseLoanFormWhereInfo;


import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.util.LOANConstant;
/**
 * @author liwang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class QueryRepurchaseLoanFormDao {
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
    
	/** select */
	private StringBuffer m_sbSelect = null;
	/** from */
	private StringBuffer m_sbFrom = null;
	/** where */
	private StringBuffer m_sbWhere = null;	
	/** ORDERBY */
	private StringBuffer m_sbORDERBY = null;

	/**
	 * @param args
	 */
	/**
     * 返回排序的sql语句，用作pageloader的参数
     * 
     * @param wInfo
     * @return
     */
	private void cleanup(ResultSet rs) throws SQLException
	{
		
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
    public String getOrderBySql(RepurchaseLoanFormWhereInfo wInfo) {
        String sLDesc = "";
        if (wInfo.getLDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
            sLDesc = "desc \n";
        }
        String sOrderBy = null;
        switch ((int) wInfo.getOrderBySign()) {
        case 1: {
            sOrderBy = " \n order by ff.sContractCode ,bb.id\n";
            break;
        }
        case 2: {
            sOrderBy = " \n order by ff.nBorrowClientId ,bb.id\n";
            break;
        }
        case 3: {
            sOrderBy = " \n order by ff.mLoanAmount ,bb.id\n";
            break;
        }
        case 4: {
            sOrderBy = " \n order by ff.dtStartDate ,bb.id\n";
            break;
        }
        case 5: {
            sOrderBy = " \n order by ff.dtEndDate ,bb.id\n";
            break;
        }
        case 6: {
            sOrderBy = " \n order by ff.NSTATUSID ,bb.id\n";
            break;
        }
        case 7: {
            sOrderBy = " \n order by bb.code ,bb.id\n";
            break;
        }
        case 8:{
        	sOrderBy = " \n order by bb.bankId ,bb.id\n";
        	break;
        }	
        case 9:{
        	sOrderBy = " \n order by bb.amount ,bb.id\n";
        	break;
        }
        case 10:{
        	sOrderBy = " \n order by bb.rate ,bb.id\n";
        	break;
        }
        case 11:{
        	sOrderBy = " \n order by bb.startDate ,bb.id\n";
        	break;
        }
        case 12:{
        	sOrderBy = " \n order by bb.endDate ,bb.id\n";
        	break;
        }
        
        default: {
            sOrderBy = "";
        }
        }
        return sOrderBy + sLDesc;
    }
	public void getStringSQLtoQueryLoanForm(RepurchaseLoanFormWhereInfo wInfo)
	{
		m_sbSelect = new StringBuffer();
		//m_sbSelect.append("select \n");
		m_sbSelect.append("	ff.id loanId,--合同记录id \n");
		m_sbSelect.append("	bb.id repurchaseId,--合同记录id \n");
		m_sbSelect.append("	ff.nBorrowClientId ,--借款单位名称ID \n");		
		m_sbSelect.append("ff.NSTATUSID ,--贷款合同状态\n");
		m_sbSelect.append("bb.bankId ,--银行ID\n");
		m_sbSelect.append("bb.statusId ,--资产回购合同状态\n");		
		m_sbSelect.append("ff.sContractCode ,--贷款合同编号 \n");
		m_sbSelect.append("dd.sName ,--借款单位名称 \n");
		m_sbSelect.append("bb.code ,--资产回购合同合同编号 \n");
		m_sbSelect.append("ff.mLoanAmount ,--合同金额 \n");
		m_sbSelect.append("bb.amount ,--资产回购合同金额 \n");
		m_sbSelect.append("bb.rate ,--资产回购合同利率 \n");
		m_sbSelect.append("ff.dtStartDate ,--贷款起始日期 \n");
		m_sbSelect.append("ff.dtEndDate ,--贷款结束日期 \n");	
		
		m_sbSelect.append("bb.startDate ,--资产回购合同起始日期 \n");
		m_sbSelect.append("bb.endDate  --资产回购合同结束日期 \n");
		m_sbFrom = new StringBuffer();
		//m_sbFrom.append("from \n");
		m_sbFrom.append("LOAN_CONTRACTFORM ff,\n");
		m_sbFrom.append("LOAN_REPURCHASEFORM bb,\n");
		m_sbFrom.append("LOAN_REPURCHASEITEM  cc,client dd \n");
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append("where ");
		m_sbWhere.append(" \n 1=1 \n");
		Map map = wInfo.gainAllUsedFieldsAndValue();
		
		
		if (map.containsKey("codeOfRepurchaseForm") && wInfo.getCodeOfRepurchaseForm().length()>0) {
			
			m_sbWhere.append("and bb.code='" + wInfo.getCodeOfRepurchaseForm() + "' \n");
        }
        if (map.containsKey("bankId") && wInfo.getBankId() != -1) {
        	m_sbWhere.append("and bb.bankId=" + wInfo.getBankId() + " \n");
        	
        }
        
        if (map.containsKey("contractCodeFrom") && wInfo.getContractCodeFrom().length()>0 ) {
        	m_sbWhere.append("and ff.sContractCode='" + wInfo.getContractCodeFrom() + "' \n");
        }
        if (wInfo.getBorrowClientId()!=-1) {
        	m_sbWhere.append("and ff.nBorrowClientId=" + wInfo.getBorrowClientId() + " \n");
        }
       
        if (map.containsKey("repurchaseDateFrom")) {
        	m_sbWhere.append("and to_char(bb.endDate,'yyyy-mm-dd')>='"
                    + DataFormat.getDateString(wInfo.getRepurchaseDateFrom())
                    + "'\n");
        }
        
        if (map.containsKey("repurchaseDateTo")) {
        	m_sbWhere.append("and to_char(bb.endDate,'yyyy-mm-dd')<='"
                    + DataFormat.getDateString(wInfo.getRepurchaseDateTo())
                    + "' \n");
        }
        m_sbWhere.append("and ff.id=cc.contractid \n");
        m_sbWhere.append("and cc.REPURCHASEID=bb.id \n");
        m_sbWhere.append("and bb.statusId="+LOANConstant.RepurchaseStatus.SUBMIT+"\n");
        m_sbWhere.append("and ff.nBorrowClientId=dd.id \n");
        m_sbORDERBY = new StringBuffer();
        m_sbORDERBY.append("order by ff.id ,bb.id" + " \n");
        System.out.println(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString()+m_sbORDERBY.toString());
		
	}
	public PageLoader QueryRepurchaseForm(RepurchaseLoanFormWhereInfo wInfo) {
		getStringSQLtoQueryLoanForm(wInfo);
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
                .getBaseObject("com.iss.system.dao.PageLoader");
        pageLoader
                .initPageLoader(
                        new AppContext(),
                        m_sbFrom.toString(),
                        m_sbSelect.toString(),
                        m_sbWhere.toString(),
                        (int) Constant.PageControl.CODE_PAGELINECOUNT,
                        "com.iss.itreasury.loan.query.dataentity.RepurchaseLoanFormInfo",
                        null);
       pageLoader.setOrderBy(m_sbORDERBY.toString());
        
        return pageLoader;
    }
	public double QueryContractSum(RepurchaseLoanFormWhereInfo wInfo) throws Exception
	{
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		getStringSQLtoQueryLoanForm(wInfo);
		String strSQL = "";
		double result=0;
		try
		{   
			String sql=" sum(NVL(ff.mLoanAmount,0)) as sumLoanAmount \n ";
			//m_sbSelect.append(" sum(NVL(ff.mLoanAmount,0)) as sumLoanAmount \n");
			
			strSQL = " select " + sql + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString()+m_sbORDERBY.toString();
			log.print(strSQL);
			System.out.println(strSQL);

			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				result=rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
