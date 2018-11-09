package com.iss.itreasury.loan.bankloan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.iss.itreasury.loan.bankloan.dataentity.BankLoanInfo;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;

/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BankLoanDao extends LoanDAO{
	
	public BankLoanDao(){
		super("loan_bankloan");
	}
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//新增，将info里的信息insert到表loan_BankLoan里
	public long add(BankLoanInfo info) throws Exception
	{
		long lret = -1;
		
		//此处要调用conferControl(info)和assureControl(info)来进行 ?授信额度控制和担保额度控制
		
		String strSQL = "INSERT INTO loan_bankloan VALUES(?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,1)";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,info.getId());
			pstmt.setLong(2,info.getOfficeId());
			pstmt.setString(3,info.getCode());
			pstmt.setString(4,info.getLoanContractNo());
			pstmt.setLong(5,info.getConferContractNo());
			pstmt.setString(6,info.getLoanClient());
			pstmt.setString(7,info.getLoanBank());
			pstmt.setLong(8,info.getLoanType());
			pstmt.setDouble(9,info.getAmount());
			pstmt.setLong(10,info.getCurrencyID());
			pstmt.setDouble(11,info.getExchangeRate());
			pstmt.setDouble(12,info.getConvertRMB());
			pstmt.setString(13,info.getStartDate());
			pstmt.setString(14,info.getEndDate());
			pstmt.setLong(15,info.getStatusID());
			pstmt.setString(16,info.getRemark());
			lret = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	/**
     * 具体说明：
     * {
     * 	?授信额度控制的规则为“录入的贷款金额折合人民币数”
     *  小于“分配给该单位的短期贷款（中长期贷款）额度的余额除以风险系数的折合人民币数”
     *  加“分配给该单位的混用额度的余额除以短期贷款（中长期贷款）风险系数的折合人民币数
     * }
     * 
     *  	
	*/
	public long conferControl(BankLoanInfo info) throws Exception
	{
		long lret = 1;
		
		return lret;
	}
    /**
     * 具体说明：
     * {
     * 	担保额度控制的规则为“录入的贷款金额折合人民币数”
     * 	小于“根据贷款单位和授信合同号确定的担保合同的担保金额的余额折合人民币数”
     * }
     * 
     * 
	*/
	public long assureControl(BankLoanInfo info) throws Exception
	{
		long lret = 1;
		
		return lret;
	}
	
	//	修改，将info里的信息update到表loan_BankLoan里
	public long modify(BankLoanInfo info) throws Exception
	{
		long lret = -1;
		
		
		
		StringBuffer strSQL = new StringBuffer("UPDATE loan_bankloan set ");
		strSQL.append("loanContractNo=?,conferContractNo=?,loanClient=?,");
		strSQL.append("loanBank=?,loanType=?,amount=?,currencyID=?,");
		strSQL.append("exchangeRate=?,convertRMB=?,startDate=to_date(?,'yyyy-mm-dd'),");
		strSQL.append("endDate=to_date(?,'yyyy-mm-dd'),statusID=?,remark=?");
		strSQL.append("WHERE id=?");
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			pstmt.setString(1,info.getLoanContractNo());
			pstmt.setLong(2,info.getConferContractNo());
			pstmt.setString(3,info.getLoanClient());
			pstmt.setString(4,info.getLoanBank());
			pstmt.setLong(5,info.getLoanType());
			pstmt.setDouble(6,info.getAmount());
			pstmt.setLong(7,info.getCurrencyID());
			pstmt.setDouble(8,info.getExchangeRate());
			pstmt.setDouble(9,info.getConvertRMB());
			pstmt.setString(10,info.getStartDate());
			pstmt.setString(11,info.getEndDate());
			pstmt.setLong(12,info.getStatusID());
			pstmt.setString(13,info.getRemark());
			pstmt.setLong(14,info.getId());
			lret = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		
		return lret;
	}
	
	//	删除，将info里的信息update到表loan_BankLoan里
	public long delete(long id) throws Exception
	{
		long lret = -1;
		
		String strSQL = "UPDATE loan_bankloan SET statusID = 0 WHERE id=?";
		String SQL1 = "DELETE FROM loan_InterestDetail WHERE bankLoanID=?";
		String SQL2 = "DELETE FROM loan_PayandrepayFact WHERE bankLoanID=?";
		String SQL3 = "DELETE FROM loan_PayandrepayPlan WHERE bankLoanID=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			lret = pstmt.executeUpdate();
			pstmt = conn.prepareStatement(SQL1);
			pstmt.setLong(1,id);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setLong(1,id);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(SQL3);
			pstmt.setLong(1,id);
			pstmt.executeUpdate();
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//	查询，按info里的信息查询表loan_BankLoan
	//返回由BankLoanInfo组成的集合
	public Vector findByBankLoan(BankLoanInfo info) throws Exception
	{
		Vector vret = null;
		
		return vret;
	}
	
	//	查询，按id查询表loan_BankLoan
	//返回由BankLoanInfo组成的集合
	public BankLoanInfo findByID(long id) throws Exception
	{
		BankLoanInfo info = null;
		String strSQL = " select e.*,f.name loanclientname from (select c.*,d.scontractno contractno " +
				"from loan_bankloan c left join loan_bank_creditext d on c.confercontractno = d.id) e " +
				"left join client_clientinfo f on e.loanclient=f.code where e.id=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				info = new BankLoanInfo();
				info.setId(rs.getLong("id"));
				info.setOfficeId(rs.getLong("officeId"));
				info.setCode(rs.getString("code"));
				info.setLoanContractNo(rs.getString("loanContractNo"));
				info.setConferContractNo(rs.getLong("conferContractNo"));
				info.setLoanClient(rs.getString("loanClient"));
				info.setLoanBank(rs.getString("loanBank"));
				info.setLoanType(rs.getLong("loanType"));
				info.setAmount(rs.getDouble("amount"));
				info.setCurrencyID(rs.getLong("currencyID"));
				info.setExchangeRate(rs.getDouble("exchangeRate"));
				info.setConvertRMB(rs.getDouble("convertRMB"));
				info.setStartDate(DataFormat.formatDate(rs.getDate("startDate"),1));
				info.setEndDate(DataFormat.formatDate(rs.getDate("endDate"),1));
				info.setStatusID(rs.getLong("statusID"));
				info.setRemark(rs.getString("remark"));
				info.setContractNo(rs.getString("contractNo"));
				info.setLoanClientName(rs.getString("loanClientName"));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		
		return info;
	}
	
	public PageLoader findAllBankLoan(long officeId) throws Exception{
		String[] sql = new String[4];
		sql[0] = " m.*,n.countrepay ";
		sql[1] = " (select p.*,q.countpay from (select e.*,f.name loanclientname from " +
				"(select i.*,j.scontractno contractno from loan_bankloan i left join " +
				"loan_bank_creditext j on i.confercontractno = j.id) e left join " +
				"client_clientinfo f on e.loanclient=f.code) p left join (select a.id," +
				"sum(b.amount) countpay from loan_bankloan a left join loan_payandrepayfact " +
				"b on b.bankloanid=a.id where b.statusid=1 group by a.id) q on p.id=q.id) m " +
				"left join (select c.id,sum(d.amount) countrepay from loan_bankloan c " +
				"left join loan_payandrepayfact d on c.id=d.bankloanid where d.statusid=2 " +
				"group by c.id) n on m.id=n.id ";
		sql[2] = " m.statusID > 0 and m.officeId=" + officeId;
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by m.code" + strDesc);
		sql[3] = oBuf.toString();
		
		PageLoader pageLoader = null;
		try{
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
			pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.bankloan.dataentity.BankLoanInfo", null);
		
			pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return pageLoader;
	}
	
	public PageLoader findWithPage(BankLoanInfo info) throws Exception{
		
		String[] sql = getSQL(info);
		
		PageLoader pageLoader = null;
		try{
		pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.bankloan.dataentity.BankLoanInfo", null);
		
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return pageLoader;

	}
	
	private String[] getSQL(BankLoanInfo info) throws Exception{
		
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		String strTemp = null;
		long lTemp = 0;
		
		//select
		sql[0] = " m.*,n.countrepay ";
		//from
		sql[1] = " (select p.*,q.countpay from (select e.*,f.name loanclientname from " +
				"(select i.*,j.scontractno contractno from loan_bankloan i left join " +
				"loan_bank_creditext j on i.confercontractno = j.id) e left join " +
				"client_clientinfo f on e.loanclient=f.code) p left join (select a.id," +
				"sum(b.amount) countpay from loan_bankloan a left join loan_payandrepayfact " +
				"b on b.bankloanid=a.id where b.statusid=1 group by a.id) q on p.id=q.id) m " +
				"left join (select c.id,sum(d.amount) countrepay from loan_bankloan c " +
				"left join loan_payandrepayfact d on c.id=d.bankloanid where d.statusid=2 " +
				"group by c.id) n on m.id=n.id ";
		//where
		sql[2] =" m.statusID > 0 and m.officeId=" + info.getOfficeId();

		lTemp = info.getConferContractNo();
		if(lTemp > 0){
			strSQL.append(" and m.conferContractNo=" + lTemp);
		}
		
		strTemp = info.getLoanClient();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and m.loanClient='" + strTemp + "'" );
		}
		
		strTemp = info.getLoanBank();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and m.loanBank='" + strTemp + "'");
		}
		
		lTemp = info.getStatusID();
		if(lTemp > 0){
			strSQL.append(" and m.statusID=" + lTemp);
		}
		
		strTemp = info.getFromStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and m.startDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getEndStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and m.startDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getFromEndDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and m.endDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getEndEndDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and m.endDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		sql[2] = sql[2] + strSQL.toString();
		
		
		//order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by m.code" + strDesc);
		sql[3] = oBuf.toString();
		
		System.out.println("**********SQL**********"+sql[0]+"  "+sql[1]+"   "+sql[2]+"   "+sql[3]);
		
		return sql;
	
	}
	
	public String getNextCode(String prefix) throws Exception {
		String strSQL = "select code from loan_bankloan where code like ?";
		String strTemp = prefix;
		String newCode = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Env.getSystemDateTime());
		strTemp += calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1);
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,strTemp + "%");
			rs = pstmt.executeQuery();
			int iCode = 0;
			while(rs.next()){
				String scode = rs.getString("code");
				scode = scode.substring(strTemp.length(),scode.length());
				if(scode != null && !scode.equals("")){
					int iTemp = Integer.parseInt(scode);
					if(iCode < iTemp){
						iCode = iTemp;
					}
				}
			}
			iCode += 1;
			newCode = "" +iCode;
			while(newCode.length() < 4){
				newCode = "0" + newCode;
			}
			newCode = strTemp + newCode;
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return newCode;
	}
	
	public int getNextId() throws Exception {
		String strSQL = "SELECT NVL(MAX(id)+1,1) id FROM loan_bankloan";
		int iCode = 0;
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				iCode = rs.getInt("id");
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return iCode;
	}

}