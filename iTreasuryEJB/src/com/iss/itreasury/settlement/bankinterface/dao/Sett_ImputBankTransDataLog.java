package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.ImportBankTransDataRecordInfo;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_ImputBankTransDataLog extends SettlementDAO
{
	public final static int ORDERBY_DTEXECUTE = 1;
	/**
	 * Constructor for Sett_ImputBankTransDataLog.
	 */
	public Sett_ImputBankTransDataLog()
	{
		super();
		this.strTableName = "SETT_IMPUTBANKTRANSDATALOG";
	}

	/**
	 * Constructor for Sett_ImputBankTransDataLog.
	 * @param conn
	 */
	public Sett_ImputBankTransDataLog(Connection conn)
	{
		super(conn);
		this.strTableName = "SETT_IMPUTBANKTRANSDATALOG";
	}
	
	/**
	 * 添加一条记录
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public void add(ImportBankTransDataRecordInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (SBANKACCOUNTNO, \n");
		buffer.append("NRECORDCOUNT,\n");
		buffer.append("NBANKTYPE,\n");
		buffer.append("DTSTART,\n");
		buffer.append("DTEND,\n");
		buffer.append("DTEXECUTE,\n");
		buffer.append("SOPERATORNO,\n");
		buffer.append("NISSHADINESS)\n");

		try
		{
			conn = this.getConnection();

			buffer.append(" values(?,?,?,?,?,?,?,?)\n");

			log.info(buffer.toString());

			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;
			pstmt.setString(nIndex++, info.getAccountNo());
			pstmt.setLong(nIndex++, info.getRecordCount());
			pstmt.setLong(nIndex++, info.getBankType());
			pstmt.setDate(nIndex++, info.getBeginDate());
			pstmt.setDate(nIndex++, info.getEndDate());
/***/		pstmt.setTimestamp(nIndex++, new Timestamp(info.getExecuteDate().getTime()));
/***/		pstmt.setString(nIndex++, new String(""+info.getOperatorID()));
			pstmt.setLong(nIndex++, info.getIsShadiness());

			pstmt.execute();
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	/**
	 * 根据info中指定的AccountNo和executeTime更新相关记录的信息
	 * @param info
	 * @throws Exception
	 */
	public void update(ImportBankTransDataRecordInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

//		buffer.append("NRECORDCOUNT = ?,\n");                           
//		buffer.append("DTSTART = ?,\n");                               
//		buffer.append("DTEND = ?,\n");                               
//		buffer.append("SOPERATORNO = ?,\n");                       
		buffer.append("NISSHADINESS = ?\n");                  

		buffer.append("where SBANKACCOUNTNO = ?\n");
		buffer.append("and NBANKTYPE = ?\n");
		buffer.append("and DTEXECUTE = ?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			//pstmt.setLong(nIndex++, info.getRecordCount());
			//pstmt.setDate(nIndex++, info.getBeginDate());
			//pstmt.setDate(nIndex++, info.getEndDate());
			//pstmt.setLong(nIndex++, info.getOperatorID());
			pstmt.setLong(nIndex++, info.getIsShadiness());
			
			pstmt.setString(nIndex++, info.getAccountNo());
			pstmt.setLong(nIndex++, info.getBankType());
			pstmt.setTimestamp(nIndex++, new Timestamp(info.getExecuteDate().getTime()));


			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
	
	public Collection findByConditions(
			ImportBankTransDataRecordInfo conditionInfo,
			int orderByType,
			boolean isDesc)
			throws Exception
		{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList result = new ArrayList();
			try
			{
				con = getConnection();
				StringBuffer strSQLBuffer = new StringBuffer(256);

				strSQLBuffer.append("SELECT * FROM "+strTableName+"");

				//flag for deciding whether there is WHERE in query string
				boolean isNeedWhere = false;

				StringBuffer strWhereSQLBuffer = new StringBuffer(128);

				if (conditionInfo.getAccountNo() != null)
				{
					strWhereSQLBuffer.append(
						" AND SBANKACCOUNTNO = " + conditionInfo.getAccountNo() + " \n");
					isNeedWhere = true;
				}

				if (conditionInfo.getRecordCount() != -1)
				{
					strWhereSQLBuffer.append(
						" AND NRECORDCOUNT = " + conditionInfo.getRecordCount() + " \n");
					isNeedWhere = true;
				}

				if (conditionInfo.getBankType() != -1)
				{
					strWhereSQLBuffer.append(
						" AND NBANKTYPE = "
							+ conditionInfo.getBankType()
							+ " \n");
					isNeedWhere = true;
				}

				if (conditionInfo.getBeginDate() != null)
				{
					strWhereSQLBuffer.append(
						" AND DTSTART <=  to_date('"
							+ conditionInfo.getBeginDate()
							+ " 23:59','yyyy-mm-dd HH24:MI') \n");
					isNeedWhere = true;
				}

				if (conditionInfo.getEndDate() != null)
				{
					strWhereSQLBuffer.append(
						" AND DTEND >=  to_date('"
							+ conditionInfo.getEndDate()
							+ "','yyyy-mm-dd') \n");
					isNeedWhere = true;
				}
				
				if (conditionInfo.getExecuteDate() != null)
				{
					strWhereSQLBuffer.append(
						" AND DTEXECUTE = "
							+ conditionInfo.getExecuteDate()
							+ " \n");
					isNeedWhere = true;
				}
				
				if (conditionInfo.getOperatorID() != -1)
				{
					strWhereSQLBuffer.append(
						" AND SOPERATORNO = "
							+ conditionInfo.getOperatorID()
							+ " \n");
					isNeedWhere = true;
				}

				if (isNeedWhere)
				{
					strSQLBuffer.append(" WHERE");
					//Cut first "AND"
					strSQLBuffer.append(strWhereSQLBuffer.substring(4));
				}

				boolean isNeedOrderBy = true;
				switch (orderByType)
				{
					case ORDERBY_DTEXECUTE :
						{
							strSQLBuffer.append(" ORDER BY DTEXECUTE \n");
						}
						break;
					default :
						{
							strSQLBuffer.append(" ORDER BY DTEXECUTE \n");
						}
						break;
				}

				if (isNeedOrderBy)
				{
					if (isDesc)
						strSQLBuffer.append(" DESC \n");
					else
						strSQLBuffer.append(" ASC \n");
				}

				log.info(strSQLBuffer.toString());
				ps = con.prepareStatement(strSQLBuffer.toString());
				rs = ps.executeQuery();

				ImportBankTransDataRecordInfo infoTemp = null;
				while (rs.next())
				{
					infoTemp = new ImportBankTransDataRecordInfo();
					this.getInfoFromResultSet(infoTemp, rs);
					result.add(infoTemp);
				}
			}

			finally
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			return result.isEmpty()?null:result;
		}
	
	
	/**
	 * 查找指定账户最新的导入记录
	 * @param accountNo
	 * @return ImportBankTransDataRecordInfo
	 * @throws Exception
	 */
	public ImportBankTransDataRecordInfo findLast(String accountNo) throws Exception
	{
		ImportBankTransDataRecordInfo ai = null;
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
			sbSQL.append(" select * from " + strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = ?");
			sbSQL.append(" and DTEXECUTE = ( ");
			sbSQL.append(" select max(DTEXECUTE) from " + strTableName);
			sbSQL.append(" where SBANKACCOUNTNO = ?");
			sbSQL.append(" ) ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, accountNo);
			ps.setString(2, accountNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the ImportBankTransDataRecordInfo from current ResultSet object
				ai = new ImportBankTransDataRecordInfo();
				getInfoFromResultSet(ai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return ai != null ? ai : null;
	}
	
	private void getInfoFromResultSet(ImportBankTransDataRecordInfo bi, ResultSet rs)
	throws Exception
	{
		try
		{
			bi.setAccountNo(rs.getString("SBANKACCOUNTNO")); // 账户号
			bi.setRecordCount(rs.getLong("NRECORDCOUNT")); // 成功导入的记录数
			bi.setBankType(rs.getLong("NBANKTYPE")); //银行类型
			bi.setBeginDate(rs.getDate("DTSTART")); // 
			bi.setEndDate(rs.getDate("DTEND")); // 
			bi.setExecuteDate(new Date(rs.getTimestamp("DTEXECUTE").getTime())); // 成功执行导入的日期
			bi.setOperatorID(Long.parseLong(rs.getString("SOPERATORNO")));
			bi.setIsShadiness(rs.getLong("NISSHADINESS"));
		}
		catch (Exception se)
		{
			throw se;
		}
	}
	
	
	public static void main(String[] args)
	{
		try
		{
//			ImportBankTransDataRecordInfo condition = new ImportBankTransDataRecordInfo();
//
//			condition.setAccountNo("0004");
//			condition.setRecordCount(200);
//			condition.setBankType(1);
//			condition.setBeginDate(new Date(2003,4,28));
//			condition.setEndDate(new Date(2004,4,28));
//			condition.setExecuteDate(new Date(2005,4,28));
//			condition.setOperatorID(1);
//			condition.setIsShadiness(0);

			Sett_ImputBankTransDataLog dao = new Sett_ImputBankTransDataLog();
			
//			dao.add(condition);
			
			ImportBankTransDataRecordInfo bai = null;
			bai = dao.findLast("140644136221066002194");
			
//			bai = new ImportBankTransDataRecordInfo();
//			bai.setAccountNo("0200004519000100297");
//			Collection col = null;
//			col = dao.findByConditions(bai,ORDERBY_DTEXECUTE,false);
//			
//			ImportBankTransDataRecordInfo[] inf = new ImportBankTransDataRecordInfo[0];
//			inf = (ImportBankTransDataRecordInfo[])col.toArray(inf);
//			if (inf != null && inf.length > 0)
//			{
//				for (int i = 0; i < inf.length; i++)
//				{
//					System.out.println("*Info"+(i+1)+": ");
//					System.out.println("    AccountNo: "+inf[i].getAccountNo());
//					System.out.println("    AccountName: "+inf[i].getAccountName());
//					System.out.println("    BeginDate: "+inf[i].getBeginDate());
//					System.out.println("    EndDate: "+inf[i].getEndDate());
//				}
//			}
//			bai.setIsShadiness(2);
//			dao.update(bai);
//			bai = dao.findLast("0001");
//			
			System.out.println("\nAccountNO:"+bai.getAccountNo()+" Date:"+new java.util.Date(bai.getExecuteDate().getTime()));
			System.out.println("========= *success* =========");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
