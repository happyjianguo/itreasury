/*
 * Created on 2003-9-3
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.ClientInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryClientConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_ClientDAO extends SettlementDAO
{
	Log4j log4j = null; // log4j 用于打印日志。
	/**
	 * 构造方法
	 *
	 */
	public Sett_ClientDAO()
	{
		log4j = new Log4j(SETTConstant.ModuleType.SETTLEMENT, this);
	}
	public static void main(String[] args)
	{
		Sett_ClientDAO clientdao = new Sett_ClientDAO();
		try
		{
			clientdao.getNewClientCode(1);
		}
		catch (Exception e)
		{
		}
	}
	/**
	 * 方法说明： 判断该客户是否被使用过
	 * @param ci: ClientInfo
	 * @return : long - 返回新增的客户ID
	 * @throws Exception
	 *
	 */
	public boolean checkIsUsedByLoan(long lClientID) throws Exception
	{
		boolean blnReturn = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from \n");
			sbSQL.append(" ( \n");
			sbSQL.append("   select nConsignClientID nClientID from loan_loanForm where nStatusID>0 \n");
			sbSQL.append("  union \n");
			sbSQL.append("   select nBorrowClientID nClientID from loan_loanForm where nStatusID>0 \n");
			sbSQL.append("  union \n");
			sbSQL.append("   select nClientID from loan_loanFormAssure where nStatusID>0 \n");
			sbSQL.append(" ) \n");
			sbSQL.append(" where nClientID in ("+lClientID+") \n");
		
			ps = conn.prepareStatement(sbSQL.toString());
			System.out.println("sql is :" + sbSQL.toString());
			rs = ps.executeQuery();			
			//get the maximum id
			if (rs != null && rs.next())
			{
				blnReturn = true;
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return blnReturn;
	}
	/**
	 * 方法说明： 新增客户
	 * @param ci: ClientInfo
	 * @return : long - 返回新增的客户ID
	 * @throws Exception
	 *
	 */
	public long add(ClientInfo ci) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into Client( ID, SCODE, SNAME, nOfficeID, slegalpersoncodecert, \n");
			sbSQL.append(" nCorpNatureID, NPARENTCORPID1, NPARENTCORPID2,NSETTCLIENTTYPEID, SEMAIL, SADDRESS,\n");
			sbSQL.append(" SZIPCODE, sContacter, SPHONE, SFAX, DTSIGNSTART,\n");
			sbSQL.append(" NCURRENTSIGNID, NINPUTUSERID, DTINPUT, nStatusID, nindustrytypeid,sSimpleName,\n");
			sbSQL.append(" nExtendAttribute1, nExtendAttribute2, nExtendAttribute3, nExtendAttribute4,SQUERYPASSWORD) \n");
			sbSQL.append(" values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
			ps = conn.prepareStatement(sbSQL.toString());
			System.out.println("sql is :" + sbSQL.toString());
			//get the maximum id
			lReturn = getNextID();
			ci.setClientID(lReturn);
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(ci, ps, 1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 方法说明：取得客户编号
	 * @param lOfficeID : long
	 * @return: String - 新增的客户编号
	 * @throws Exception
	 */
	public String getNewClientCode(long lOfficeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strCode = "";
		long lNewClientID = -1;
		try
		{
			conn = getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select min(no) \n");
			sb.append(" from \n");
			sb.append(" (      select id no from serialno \n");
			sb.append("        minus \n");
			sb.append("        select no \n");
			sb.append("        from \n");
			sb.append("        (	select to_number(substr(saccountno,7,4)) no from sett_Account  where  nofficeid=? \n");
			sb.append("   and   ascii(substr(saccountno,7,1)) > 47 and ascii(substr(saccountno,7,1)) < 58 \n");
			sb.append("     and   ascii(substr(saccountno,8,1)) > 47 and ascii(substr(saccountno,8,1)) < 58 \n");
			sb.append("     and   ascii(substr(saccountno,9,1)) > 47 and ascii(substr(saccountno,9,1)) < 58 \n");
			sb.append("     and   ascii(substr(saccountno,10,1)) > 47 and ascii(substr(saccountno,10,1)) < 58 \n");
			sb.append("        		union all \n");
			sb.append("    	   		select to_number(substr(scode,4,4)) no \n");
			sb.append(" 	   		from   client \n");
			sb.append("        		where  nofficeid=? and ascii(substr(scode,4,1)) > 47 and ascii(substr(scode,4,1)) < 58 \n");
			sb.append("             	and ascii(substr(scode,5,1)) > 47 and ascii(substr(scode,5,1)) < 58 \n");
			sb.append(" 		        and ascii(substr(scode,6,1)) > 47 and ascii(substr(scode,6,1)) < 58 \n");
			sb.append("         	    and ascii(substr(scode,7,1)) > 47 and ascii(substr(scode,7,1)) < 58 \n");
			sb.append(" 		) \n");
			sb.append(" ) \n");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lOfficeID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNewClientID = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			//
			sb.setLength(0);
			sb.append(" select scode from office where id=? ");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			rs = ps.executeQuery();
			String strOfficeCode = "";
			if (rs.next())
			{
				strOfficeCode = rs.getString("scode");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			strCode = strOfficeCode + "-" + DataFormat.formatInt(lNewClientID, 4, true);
			log4j.info("New Client Code is : " + strCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return strCode;
	}
	/**
	 * 方法说明：根据客户ID，查询客户信息
	 * @param lClientID : long
	 * @return: ClientInfo
	 * @throws Exception
	 */
	public ClientInfo findByID(long lClientID) throws Exception
	{
		ClientInfo ci = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Client ");
			sbSQL.append(" where ID = ? and nStatusID = " + SETTConstant.RecordStatus.VALID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ci = new ClientInfo();
				getInfoFromResultSet(ci, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return ci != null ? ci : null;
	}
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的客户
	 * @param qcci: QueryClientConditionInfo
	 * @return: Collection
	 * @throws Exception
	 */
	public Collection findByCondition(QueryClientConditionInfo qcci) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		StringBuffer sbSQLOther = null;
		ClientInfo ci = null;
		int iTag = 1;
		long lPageCount = 1;
		long lPageLineCount = SETTConstant.PageControl.CODE_PAGELINECOUNT;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		long lOrder = -1;
		long lDesc = 1;
		long lPageCurrent = 1;
		long lPageCountTemp = 1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			sbSQLOther = new StringBuffer();
			sbSQL.append(" select count(*) from (");
			sbSQL.append(" select c.* ,rownum as r from Client c");
			sbSQL.append(" where nStatusID = " + SETTConstant.RecordStatus.VALID);
			//appends TypeID to the query where condition
			if (qcci.getOfficeID() > 0)
			{
				sbSQL.append(" and nOfficeID = ? ");
			}
			if (qcci.getStartClientCode() != null
				&& qcci.getEndClientCode() != null
				&& qcci.getStartClientCode().length() > 0
				&& qcci.getEndClientCode().length() > 0)
			{
				sbSQL.append(" and ( SCODE between ? and ? )");
			}
			if (qcci.getLegalPerson() != null && qcci.getLegalPerson().length() > 0)
			{
				sbSQL.append(" and slegalpersoncodecert = ? ");
			}
			if (qcci.getClientName() != null && qcci.getClientName().length() > 0)
			{
				sbSQL.append(" and SNAME = ? ");
			}
			sbSQL.append(") a ");
			System.out.println("sbSQL.toString():" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			if (qcci.getOfficeID() > 0)
			{
				ps.setLong(iTag++, qcci.getOfficeID());
			}
			//appends RegisterDate to the proper PreparedStatement argument
			if (qcci.getStartClientCode() != null
				&& qcci.getEndClientCode() != null
				&& qcci.getStartClientCode().length() > 0
				&& qcci.getEndClientCode().length() > 0)
			{
				ps.setString(iTag++, qcci.getStartClientCode());
				ps.setString(iTag++, qcci.getEndClientCode());
			}
			if (qcci.getLegalPerson() != null && qcci.getLegalPerson().length() > 0)
			{
				ps.setString(iTag++, qcci.getLegalPerson());
			}
			if (qcci.getClientName() != null && qcci.getClientName().length() > 0)
			{
				ps.setString(iTag++, qcci.getClientName());
			}
			rs = ps.executeQuery();
			if (rs.next())
			{
				lPageCount = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);
			lPageCurrent = qcci.getPageCurrent();
			lPageCountTemp = lPageCount;
			lPageCount = lPageCount / lPageLineCount;
			if ((lPageCountTemp % lPageLineCount) != 0)
			{
				lPageCount++;
			}
			lRowNumStart = (lPageCurrent - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;
			//--------------------------------------------------
			lOrder = qcci.getOrder();
			lDesc = qcci.getDesc();
			iTag = 1;
			sbSQLOther.append(" select * from ( select a.*,rownum as r from ( ");
			sbSQLOther.append(" select *  from Client c ");
			sbSQLOther.append(" where nStatusID = " + SETTConstant.RecordStatus.VALID);
			//appends TypeID to the query where condition
			if (qcci.getOfficeID() > 0)
			{
				sbSQLOther.append(" and nOfficeID = ? ");
			}
			if (qcci.getStartClientCode() != null
				&& qcci.getEndClientCode() != null
				&& qcci.getStartClientCode().length() > 0
				&& qcci.getEndClientCode().length() > 0)
			{
				sbSQLOther.append(" and ( SCODE between ? and ? )");
			}
			if (qcci.getLegalPerson() != null && qcci.getLegalPerson().length() > 0)
			{
				sbSQLOther.append(" and slegalpersoncodecert = ? ");
			}
			if (qcci.getClientName() != null && qcci.getClientName().length() > 0)
			{
				sbSQLOther.append(" and SNAME = ? ");
			}
			switch ((int) lOrder)
			{
				case 1 :
					sbSQLOther.append(" order by sCode ");
					break;
				case 2 :
					sbSQLOther.append(" order by sName ");
					break;
				case 3 :
					sbSQLOther.append(" order by slegalpersoncodecert ");
					break;
				case 4 :
					sbSQLOther.append(" order by NINPUTUSERID ");
					break;
				case 5 :
					sbSQLOther.append(" order by DTINPUT ");
					break;
				case 6 :
					sbSQLOther.append(" order by NMODIFYUSERID ");
					break;
				case 7 :
					sbSQLOther.append(" order by DTMODIFY ");
					break;
				default :
					sbSQLOther.append(" order by sCode ");
			}
			if (lDesc == -1)
			{
				sbSQLOther.append(" desc ");
			}
			if (lDesc == 1)
			{
				sbSQLOther.append(" asc ");
			}
			sbSQLOther.append(" ) a )  ");
			sbSQLOther.append(" where r between ? and ?  ");
			System.out.println("sbSQLOther.toString():" + sbSQLOther.toString());
			ps = conn.prepareStatement(sbSQLOther.toString());
			if (qcci.getOfficeID() > 0)
			{
				ps.setLong(iTag++, qcci.getOfficeID());
			}
			//appends RegisterDate to the proper PreparedStatement argument
			if (qcci.getStartClientCode() != null
				&& qcci.getEndClientCode() != null
				&& qcci.getStartClientCode().length() > 0
				&& qcci.getEndClientCode().length() > 0)
			{
				ps.setString(iTag++, qcci.getStartClientCode());
				ps.setString(iTag++, qcci.getEndClientCode());
			}
			if (qcci.getLegalPerson() != null && qcci.getLegalPerson().length() > 0)
			{
				ps.setString(iTag++, qcci.getLegalPerson());
			}
			if (qcci.getClientName() != null && qcci.getClientName().length() > 0)
			{
				ps.setString(iTag++, qcci.getClientName());
			}
			ps.setLong(iTag++, lRowNumStart);
			ps.setLong(iTag++, lRowNumEnd);
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				ci = new ClientInfo();
				//append one BankBillInfo to the LinkedList object
				getInfoFromResultSet(ci, rs);
				ci.setPageCount(lPageCount);
				v.add(ci);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v;
	}
	/**
	 * 方法说明：修改客户信息
	 * @param ci: ClientInfo
	 * @return: long - 客户ID
	 * @throws Exception
	 */
	public long update(ClientInfo ci) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update Client set SCODE = ?, SNAME = ?, nOfficeID = ?, slegalpersoncodecert = ?, \n");
			sbSQL.append(" nCorpNatureID = ?, NPARENTCORPID1 = ?, NPARENTCORPID2 = ?, NSETTCLIENTTYPEID = ?, SEMAIL = ?, SADDRESS = ?, \n");
			sbSQL.append(" SZIPCODE = ?, sContacter = ?, SPHONE = ?, SFAX = ?, DTSIGNSTART = ?,\n");
			sbSQL.append(" NCURRENTSIGNID = ?, nModifyUSERID = ?, DTModify = ?,nStatusID = ?,nindustrytypeid = ?,sSimpleName=?, \n");
			sbSQL.append(" nExtendAttribute1 = ?, nExtendAttribute2 = ?, nExtendAttribute3 = ?, nExtendAttribute4 = ?,SQUERYPASSWORD = ? \n");
			sbSQL.append(" where ID = ? \n");
			Log.print(sbSQL);
			ps = conn.prepareStatement(sbSQL.toString());
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(ci, ps, -1);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return ci.getClientID();
	}
	/** get the current maximum id of table sett_BankBill
	 * @return the current maximum id of table sett_BankBill
	 * @Exception
	 */
	private long getNextID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxno ");
			sbSQL.append(" from Client ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("maxno");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}
	private void getInfoFromResultSet(ClientInfo ci, ResultSet rs) throws Exception
	{
		try
		{
			ci.setClientID(rs.getLong("ID")); // 客户ID
			ci.setClientCode(rs.getString("SCODE")); // 客户编号
			ci.setClientName(rs.getString("SNAME")); // 客户名称
			ci.setClientSimpleName(rs.getString("sSimpleName")); // 客户简称
			ci.setOfficeID(rs.getLong("nOfficeID")); // 办事处ID
			ci.setLegalPerson(rs.getString("slegalpersoncodecert")); // 法人代码
			//ci.setEnterpriseTypeID(rs.getLong("nCorpNatureID")); // 企业类型
			ci.setParentCorpID1(rs.getLong("NPARENTCORPID1")); // 母公司1
			ci.setParentCorpID2(rs.getLong("NPARENTCORPID2")); // 母公司2
			//ci.setClientTypeID(rs.getLong("NSETTCLIENTTYPEID")); // 客户分类
			ci.setEmail(rs.getString("SEMAIL")); // Email
			ci.setAddress(rs.getString("SADDRESS")); // 客户地址
			ci.setZipCode(rs.getString("SZIPCODE")); // 邮政编码
			ci.setContactor(rs.getString("sContacter")); // 联系人
			ci.setPhone(rs.getString("SPHONE")); // 联系电话
			ci.setFax(rs.getString("SFAX")); // 传真
			ci.setSealStartDate(rs.getTimestamp("DTSIGNSTART")); // 印鉴启用日期
			ci.setSealID(rs.getLong("NCURRENTSIGNID")); // 当前印鉴ID
			ci.setInputUserID(rs.getLong("NINPUTUSERID")); // 录入人ID
			ci.setInputDate(rs.getTimestamp("DTINPUT")); // 录入时间
			ci.setModifyUserID(rs.getLong("nModifyUSERID")); // 修改人ID
			ci.setModifyDate(rs.getTimestamp("DTModify")); // 修改时间
			ci.setStatusID(rs.getLong("NSTATUSID"));
			ci.setIndustryTypeID(rs.getLong("nClienttypeID2")); // 行业分类
			ci.setAccount( rs.getString("sAccount"));
			ci.setExtendAttribute1(rs.getLong("nExtendAttribute1"));//扩展属性1
			ci.setExtendAttribute2(rs.getLong("nExtendAttribute2"));//扩展属性2
			ci.setExtendAttribute3(rs.getLong("nExtendAttribute3"));//扩展属性3
			ci.setExtendAttribute4(rs.getLong("nExtendAttribute4"));//扩展属性4
			ci.setQueryPassWord(rs.getString("SQUERYPASSWORD"));//查询密码
		}
		catch (Exception se)
		{
			throw se;
		}
	}
	private void setPrepareStatementByInfo(ClientInfo ci, PreparedStatement ps, long lTag) throws Exception
	{
		int i = 1;
		try
		{
			if (lTag > 0)
			{
				ps.setLong(i++, ci.getClientID());
			}
			ps.setString(i++, ci.getClientCode()); // 客户编号
			ps.setString(i++, ci.getClientName()); // 客户名称
			ps.setLong(i++, ci.getOfficeID()); // 办事处ID
			ps.setString(i++, ci.getLegalPerson()); // 法人代码
			ps.setLong(i++, ci.getEnterpriseTypeID()); // 企业类型
			ps.setLong(i++, ci.getParentCorpID1()); // 母公司1
			ps.setLong(i++, ci.getParentCorpID2()); // 母公司2
			ps.setLong(i++, ci.getClientTypeID()); // 客户分类
			ps.setString(i++, ci.getEmail()); // Email
			ps.setString(i++, ci.getAddress()); // 客户地址
			ps.setString(i++, ci.getZipCode()); // 邮政编码
			ps.setString(i++, ci.getContactor()); // 联系人
			ps.setString(i++, ci.getPhone()); // 联系电话
			ps.setString(i++, ci.getFax()); // 传真
			ps.setTimestamp(i++, ci.getSealStartDate()); // 印鉴启用日期
			ps.setLong(i++, ci.getSealID()); // 当前印鉴ID
			if (lTag > 0)
			{
				ps.setLong(i++, ci.getInputUserID()); // 录入人ID
				ps.setTimestamp(i++, ci.getInputDate()); // 录入时间
			}
			else
			{
				ps.setLong(i++, ci.getModifyUserID()); // 修改人ID
				ps.setTimestamp(i++, ci.getModifyDate()); // 修改时间
			}
			ps.setLong(i++, ci.getStatusID());
			ps.setLong(i++, ci.getIndustryTypeID());
			ps.setString(i++,ci.getClientSimpleName());	
			ps.setLong(i++,ci.getExtendAttribute1());//扩展属性1
			ps.setLong(i++,ci.getExtendAttribute2());//扩展属性2
			ps.setLong(i++,ci.getExtendAttribute3());//扩展属性3
			ps.setLong(i++,ci.getExtendAttribute4());//扩展属性4
			ps.setString(i++,ci.getQueryPassWord());//查询密码
			if (lTag < 0)
			{
				ps.setLong(i++, ci.getClientID());
			}
		}
		catch (Exception se)
		{
			throw se;
		}
	}
}
