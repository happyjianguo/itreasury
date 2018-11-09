package com.iss.itreasury.ebank.obbatchpayment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;

public class OBBatchPaymentDAO extends ITreasuryDAO {
	public Connection con = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	public long findPayeeAccountID(String sAccountNo,String sAccountName)throws Exception
	{
		long payeeID = -1;
		StringBuffer sql = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sql.append("select id from sett_account t where  t.saccountno = '"+ sAccountNo + "'and t.sname='"+ sAccountName+ "' and t.nstatusid = 1");
			ps=con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next())
			{
				payeeID=rs.getLong("id");
			}
			if(con != null)
			{
				con.close();
				con = null;
			}
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(rs != null)
			{
				rs.close();
				rs = null;
			}  
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con != null)
			{
				con.close();
				con = null;
			}
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(rs != null)
			{
				rs.close();
				rs = null;
			} 
		}
		
		
		
		return payeeID;
	}
	
	public long findEbankAccountID(String accountNO,String name)throws Exception
	{
		long AccountID = -1;
		StringBuffer sql = null;
		try
		{
			sql = new StringBuffer();
			con = Database.getConnection();
			sql.append("select id from ob_payeeinfo where sPayeeAcctno = '"+ accountNO+ "'and spayeename='"+ name+ "' and nStatusID = 1");
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next())
			{
				AccountID=rs.getLong("id");
			}
			if(con != null)
			{
				con.close();
				con = null;
			}
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(rs != null)
			{
				rs.close();
				rs = null;
			} 
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con != null)
			{
				con.close();
				con = null;
			}
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(rs != null)
			{
				rs.close();
				rs = null;
			} 
		}
		return AccountID;
	}
	 /**
     * 获取批次号
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     * @throws Exception
     */
	public String getBatchNo(long lOfficeID,long lCurrencyID) throws Exception
	{
		String sBatchNo = "";	//批次号
		String sBatchNoHead = "";	//批次中
		String sBatchNoEnd = "";	//批次尾
		long lBatchNoEnd = 1; //批次流水号
		String strExecute = "";
		String temp = "";
		StringBuffer sql = null;
		try
		{
			sql = new StringBuffer();
			strExecute = DataFormat.getDateString(Env.getSystemDate(lOfficeID,lCurrencyID));
			sBatchNoHead = strExecute.substring(0,4) + strExecute.substring(5,7) + strExecute.substring(8,10);
			sql.append(" select max(substr(sBatchNo,9,5)) sBatchNo from ob_financeinstr ");
			sql.append(" where substr(sBatchNo,0,8)= '"+sBatchNoHead+"'");
			con = Database.getConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			if(rs!=null&&rs.next())
			{
				temp = rs.getString("sBatchNo");
				if(temp!=null&&temp.trim().length()>0)
				{
					lBatchNoEnd = Long.valueOf(temp).longValue()+1;
				}

			}
			if(lBatchNoEnd / 1000 > 0)
			{
				sBatchNoEnd = String.valueOf(lBatchNoEnd).toString();
			}
			else if(lBatchNoEnd / 100 > 0)
			{
				sBatchNoEnd = "0" + String.valueOf(lBatchNoEnd).toString();
			}
			else if(lBatchNoEnd / 10 > 0)
			{
				sBatchNoEnd = "00" + String.valueOf(lBatchNoEnd).toString();
			}
			else
			{
				sBatchNoEnd = "000" + String.valueOf(lBatchNoEnd).toString();
			}
			
			sBatchNo = sBatchNoHead + sBatchNoEnd;
		
			
			
			
			if(con != null)
			{
				con.close();
				con = null;
			}
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(rs != null)
			{
				rs.close();
				rs = null;
			} 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			if(con != null)
			{
				con.close();
				con = null;
			}
			if(ps != null)
			{
				ps.close();
				ps = null;
			}
			if(rs != null)
			{
				rs.close();
				rs = null;
			} 
			
		}
		return sBatchNo;
		
	}

}
