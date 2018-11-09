/*
 * Created on 2004-9-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.loan.transdiscountapply.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.contractcontent.dataentity.ContractContentInfo;
import com.iss.itreasury.loan.contractcontent.dao.ContractContentDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
//import com.iss.itreasury.dao.LoanDAO;

/**
 * @author lazhang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TransDiscountContractContentDao 
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	/**
	 * @param tableName
	 */
	public TransDiscountContractContentDao()
	{	
		// TODO Auto-generated constructor stub
	}
	
	public void saveZTXContractContent(long lcontractID) 
	{
		String strFileName = "";
		try
		{		
			ContractContentDao conconDao = new ContractContentDao();
			strFileName = conconDao.addZTX(lcontractID);        
			ContractContentInfo CCInfo = new ContractContentInfo();
			CCInfo.setParentID(lcontractID);
			CCInfo.setContractID(lcontractID);
			CCInfo.setContractTypeID(LOANConstant.ContractType.ZTX);
			CCInfo.setDocName(strFileName);
			conconDao.saveContractContent(CCInfo);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
//		TransDiscuontContractContentDao dao = new TransDiscuontContractContentDao();
//		try
//		{
//			dao.saveZTXContractContent(2);
//		}
//		catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	* 得到合同文本信息
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return Collection 合同文本信息
	* @exception Exception
	*/
	public Collection getContractContentByContractId(long lContractID) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lContractType = -1;
		long lLoanType = -1;

		try
		{
			ContractContentDao dao = new ContractContentDao();

			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,c.sName,'' as sAssureCode, -1 as nAssureTypeID ");
			sbSQL.append(" FROM loan_ContractContent a,loan_ContractForm b,Client c");
			sbSQL.append(" WHERE a.nContractID = b.ID");
			sbSQL.append(" AND b.nBorrowClientID = c.ID");
			sbSQL.append(" AND (a.nContractTypeID = " + LOANConstant.ContractType.LOAN);
			sbSQL.append(" OR a.nContractTypeID = " + LOANConstant.ContractType.ZTX + ")");
			sbSQL.append(" AND a.nContractID = ?");
			sbSQL.append(" UNION ");
			sbSQL.append(" SELECT a.*,c.sName,b.sAssureCode,b.nAssureTypeID ");
			sbSQL.append(" FROM loan_ContractContent a,loan_loanContractAssure b,Client c");
			sbSQL.append(" WHERE a.nContractID = b.nContractID");
			sbSQL.append(" AND a.nParentID = b.ID");
			sbSQL.append(" AND b.nClientID = c.ID");
			sbSQL.append(" AND b.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nContractTypeID != " + LOANConstant.ContractType.LOAN);
			sbSQL.append(" AND a.nContractTypeID != " + LOANConstant.ContractType.ZTX);
			sbSQL.append(" AND a.nContractID = ?");

			log4j.info(sbSQL.toString());
			System.out.println(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lContractID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ContractContentInfo info = new ContractContentInfo();
				info.setID(rs.getLong("ID")); //ContentID
				//info.setSerialNo(rs.getLong("nSerialNo")); //序列号
				info.setContractTypeID(rs.getLong("nContractTypeID")); //合同类型ID
				info.setContractType(LOANConstant.ContractType.getName(info.getContractTypeID()));
				//合同类型
				info.setClientName(rs.getString("sName")); //单位名称
				info.setAssureTypeID(rs.getLong("nAssureTypeID")); //保证类型
				info.setCode(rs.getString("sAssureCode")); //保证合同编号
				String sPageName = dao.getContractJspName(info.getID(), info.getContractTypeID());
				info.setPageName(sPageName);

				vResult.add(info);
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}
	/**
	* 得到合同文本信息1
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
	* @return Collection 合同文本信息
	* @exception Exception
	*/
	public Collection getContractContentByContractId1(long lContractID) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		long lContractType = -1;
		long lLoanType = -1;

		try
		{
			ContractContentDao dao = new ContractContentDao();

			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,c.name,'' as sAssureCode, -1 as nAssureTypeID ");
			sbSQL.append(" FROM loan_ContractContent a,loan_ContractForm b,SEC_COUNTERPART c");
			sbSQL.append(" WHERE a.nContractID = b.ID");
			sbSQL.append(" AND b.nBorrowClientID = c.ID");
			sbSQL.append(" AND (a.nContractTypeID = " + LOANConstant.ContractType.LOAN);
			sbSQL.append(" OR a.nContractTypeID = " + LOANConstant.ContractType.ZTX + ")");
			sbSQL.append(" AND a.nContractID = ?");
			sbSQL.append(" UNION ");
			sbSQL.append(" SELECT a.*,c.name,b.sAssureCode,b.nAssureTypeID ");
			sbSQL.append(" FROM loan_ContractContent a,loan_loanContractAssure b,SEC_COUNTERPART c");
			sbSQL.append(" WHERE a.nContractID = b.nContractID");
			sbSQL.append(" AND a.nParentID = b.ID");
			sbSQL.append(" AND b.nClientID = c.ID");
			sbSQL.append(" AND b.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nContractTypeID != " + LOANConstant.ContractType.LOAN);
			sbSQL.append(" AND a.nContractTypeID != " + LOANConstant.ContractType.ZTX);
			sbSQL.append(" AND a.nContractID = ?");

			log4j.info(sbSQL.toString());
			System.out.println(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lContractID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ContractContentInfo info = new ContractContentInfo();
				info.setID(rs.getLong("ID")); //ContentID
				//info.setSerialNo(rs.getLong("nSerialNo")); //序列号
				info.setContractTypeID(rs.getLong("nContractTypeID")); //合同类型ID
				info.setContractType(LOANConstant.ContractType.getName(info.getContractTypeID()));
				//合同类型
				info.setClientName(rs.getString("name")); //单位名称
				info.setAssureTypeID(rs.getLong("nAssureTypeID")); //保证类型
				info.setCode(rs.getString("sAssureCode")); //保证合同编号
				String sPageName = dao.getContractJspName(info.getID(), info.getContractTypeID());
				info.setPageName(sPageName);

				vResult.add(info);
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}
}
