package com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoanapplyformInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transferloancontract.dataentity.LoancontractdetailInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

public class TransferloanContractDetailDao  extends ITreasuryDAO 
{
	public TransferloanContractDetailDao()
	{
		super("CRA_LOANCONTRACTDETAIL");
	}
	
	
	public TransferloanContractDetailDao(Connection  conn)
	{
		super("CRA_LOANCONTRACTDETAIL", conn);
	}
	
	public Collection findLoancontractDetail(LoancontractdetailInfo info)
	{
		Vector c=new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		LoancontractdetailInfo returninfo=null;

		try {
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.* ");
			sbSQL.append(" FROM CRA_LOANCONTRACTDETAIL a");
			sbSQL.append(" WHERE a.statusid <> ? ");
			

			if(info.getId()!=-1)
			{
				sbSQL.append(" and a.id =  "+info.getId());
			}
			if(info.getStatusid()!=-1)
			{
				sbSQL.append(" and a.statusid =  "+ info.getStatusid());
			}
			if(info.getSapplyid()!=-1)
			{
				sbSQL.append(" and a.sapplyid =  " + info.getSapplyid());
			}
			
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, CRAconstant.TransactionType.counterpartBank.INVALID);
			rs = ps.executeQuery();


			while(rs.next())
			{
				returninfo=new LoancontractdetailInfo();
				
				returninfo.setId(rs.getLong("id"));
				returninfo.setOfficeid(rs.getLong("officeid"));
				returninfo.setCurrencyid(rs.getLong("currencyid"));
				returninfo.setSapplyid(rs.getLong("sapplyid"));
				returninfo.setLoancontractid(rs.getLong("loancontractid"));
				returninfo.setLoannoteid(rs.getLong("loannoteid"));
				returninfo.setTransferamount(rs.getLong("transferamount")) ;
				returninfo.setStartdate(rs.getTimestamp("startdate"));
				returninfo.setEnddate(rs.getTimestamp("enddate"));
				returninfo.setStatusid(rs.getLong("statusid"));
				returninfo.setInputuserid(rs.getLong("inputuserid"));
				returninfo.setInputdate(rs.getTimestamp("inputdate"));
				
				
				TransferloanContractDao dao=new TransferloanContractDao();
				ContractInfo contractinfo=dao.findbyid(returninfo.getLoannoteid(), returninfo.getId());
				LoanPayNoticeDao loanpaynoticedao = new LoanPayNoticeDao();
				contractinfo.setInterestRate(loanpaynoticedao.getLatelyRate(contractinfo.getPayID(),Env.getSystemDate()));
				
				returninfo.setContractinfo(contractinfo);
				c.add(returninfo);
			}
			
			try
			{
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				if (con != null) {
					con.close();
					con = null;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	
	public void deletedetailinfo(long loanapplyformid) throws Exception
	{


		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update cra_loancontractdetail A set A.Statusid = '"+CRAconstant.TransactionStatus.DELETED+"'" +
					" where 1 = 1 ");
			
			if(loanapplyformid!=-1)
			{
				buffer.append(" and a.sapplyid = "+loanapplyformid);
			}
			else
			{
				throw new IException ("传入id为空，不能更新数据！");
			}
			
			String sql = buffer.toString();
			
			log.info(sql);
			prepareStatement(sql);
			executeQuery();
		}
		catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			finalizeDAO();
		}
		
	
	
	}
}
