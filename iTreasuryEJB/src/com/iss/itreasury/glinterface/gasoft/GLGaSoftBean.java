package com.iss.itreasury.glinterface.gasoft;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

public class GLGaSoftBean extends GLExtendBaseBean
{

	/*
	 * ������ƾ֤ ������Collection ���ص�ƾ֤���ϣ�
	 * 
	 */
	public Collection postGLVoucher(Collection collGlVoucher, long lOfficeID, long lCurrencyID, Timestamp date) throws Exception
	{
		try
		{
			long lDealStatusID = postGLVoucherToGaSoft(collGlVoucher, lOfficeID, lCurrencyID, date);
			if (collGlVoucher != null)
			{
				System.out.println("ȡƾ֤��Ϣ����������������������");
				Iterator it = collGlVoucher.iterator();

				while (it.hasNext())
				{
					GLVoucherInfo voucher = (GLVoucherInfo) it.next();
					voucher.setPostStatusID(lDealStatusID);
				}
			}

		} catch (Exception e)
		{
			if (collGlVoucher != null)
			{
				System.out.println("ȡƾ֤��Ϣ����������������������");
				Iterator it = collGlVoucher.iterator();

				while (it.hasNext())
				{
					GLVoucherInfo voucher = (GLVoucherInfo) it.next();
					voucher.setPostStatusID(Constant.GLPostStatus.FAILED);
				}
			}
			throw new RemoteException(e.getMessage());
		}
		return collGlVoucher;
	}

	/**
	 * ��鵼��Ļ�Ʒ�¼�Ƿ��Ѿ�ȫ������ƾ֤ ȫ������ true ����false
	 */
	public boolean updatePostedVoucherStatus(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStartDate, Timestamp tsEndDate) throws Exception
	{
		boolean flag = true;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();  
		try
		{
			GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
			// ///���¿�Ŀ��
			conn = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.setLength(0);
			sbSQL.append("			select *  \n ");
			sbSQL.append("  		from gl_exp_interface \n ");
			sbSQL.append("  		where Status = 'hz' \n ");
			sbSQL.append("  		and OfficeID = " + lOfficeID + " \n ");
			sbSQL.append("  		and CurrencyID = " + lCurrencyID + " \n ");
			sbSQL.append("  		and ExecuteDate >= ? \n ");
			sbSQL.append("  		and ExecuteDate <= ? \n ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setTimestamp(1, tsStartDate);
			ps.setTimestamp(2, tsEndDate); 
			rs = ps.executeQuery();
			while(rs!=null && rs.next())
			{
				GLEntryInfo info = new GLEntryInfo();
				info.setID(rs.getLong("ID"));
				info.setOfficeID(rs.getLong("OfficeID"));
				info.setCurrencyID(rs.getLong("CurrencyID"));
				info.setDirectionID(rs.getLong("Direction"));
				info.setExecute(rs.getTimestamp("ExecuteDate"));
				info.setSubject(rs.getString("AccountNo"));
				System.out.println("==GLEntryInfo="+info.getSubject());
				list.add(info);
			}
			ps.close();
			ps = null;
			if(list !=null && list.size()>0)
			{
				////����гɹ�����ķ�¼�����޸�sett_glentry״̬��
				for(int i=0;i<list.size();i++)
				{					
					GLEntryInfo info = (GLEntryInfo)list.get(i);
//					 �ֱ�,�����2�ֱʣ���sett_glentry.ID=gl_exp_interface.ID
					if (glSettingInfo.getNImportType() == Constant.GLImportType.fb) 
					{
						sbSQL.setLength(0);
						sbSQL.append("  update sett_glentry  \n ");
						sbSQL.append("  set NPOSTSTATUSID =  " + Constant.GLPostStatus.SUCCESS + " \n ");
						sbSQL.append("  where id =  ? ");						 
						ps = conn.prepareStatement(sbSQL.toString()); 
						ps.setLong(1,info.getID());
						ps.executeUpdate();
						ps.close();
						ps = null;
					}
					else
					{
						sbSQL.setLength(0);
						sbSQL.append("  update sett_glentry  \n ");
						sbSQL.append("  set NPOSTSTATUSID =  " + Constant.GLPostStatus.SUCCESS + " \n ");
						sbSQL.append("  where nOfficeID =  "+info.getOfficeID()+" \n ");				
						sbSQL.append("  and nCurrencyID =  "+info.getCurrencyID()+" \n ");						 
						sbSQL.append("  and sSubjectCode =  ? \n ");				 
						sbSQL.append("  and nTransDirection =  "+info.getDirectionID()+" \n ");				 
						sbSQL.append("  and dtExecute =  ? \n ");					 
						sbSQL.append("  and nPostStatusID =  "+Constant.GLPostStatus.DOING+" \n ");							 		 
						ps = conn.prepareStatement(sbSQL.toString()); 
						ps.setString(1,info.getSubject());
						ps.setTimestamp(2,info.getExecute());
						System.out.println("==update sett_glentry="+sbSQL.toString());
						ps.executeUpdate();
						ps.close();
						ps = null;	
						
					}
				}
			}
			
			conn.close();
			conn = null;
		} catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
		} finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}

				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			} catch (Exception e)
			{
				Log.print(e.toString());
			}
		}

		return flag;
	}

	/*
	 * �����������ӽ��ϵͳ��ȡ��Ŀ��
	 */
	public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
	{
		Connection conn = null;
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		ResultSet rs = null;
		long lresult = -1;
		long lID = 1;
		boolean bHaveData = false;
		try
		{
			// ///���¿�Ŀ��
			System.out.println("===================================׼���ӽ�����ȡ��Ŀ������");
			System.out.println("===================================׼���ӽ�����ȡ��Ŀ������");
			conn = GLGaSoftBean.getOracleConnection(lOfficeID, lCurrencyID);
			System.out.println("===================================ȡ������ϣ�����");
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.setLength(0);
			sbSQL.append("  select a.strAccountCode,  a.strAccountName, a.lngAccountTypeID,  a.blnIsDetail, decode(a.intDirection,1,1,2) intDirection \n ");
			sbSQL.append("  from Account a    \n ");
			sbSQL.append("  order by a.strAccountCode \n ");
			System.out.print("*********��Ŀ��ϢSql:**********\n" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
				info.setOfficeID(lOfficeID);
				info.setCurrencyID(lCurrencyID);

				info.setSegmentCode2(rs.getString("strAccountCode"));
				info.setSegmentName2(rs.getString("strAccountName"));
				info.setSubjectType(rs.getLong("lngAccountTypeID"));
				if (rs.getInt("blnIsDetail") == 1)// 1:Ҷ�ӽڵ㣻2����Ҷ�ӽڵ�
				{
					info.setLeaf(true);
				}
				else
				{
					info.setLeaf(false);
				}
				info.setBalanceDirection(rs.getLong("intDirection"));// 1:�跽��2������
				info.setStatusID(Constant.RecordStatus.VALID);
				// �����ֶο��Ժ���
				list.add(info);

				Log.print(info.getSegmentCode2() + info.getSegmentName2());
			}
			System.out.print(list.size() + "\n");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		} catch (Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally
		{
			try
			{
				if (psBatch != null)
				{
					psBatch.close();
					psBatch = null;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			} catch (Exception e)
			{
				Log.print(e.toString());
			}
		}
		return (list != null && list.size() > 0 ? list : null);
	}

	/*
	 * �õ�������˵��տ�Ŀ���
	 * 
	 * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectBalance(long, long, java.sql.Timestamp)
	 */
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		System.out.println("==========GaSoft=============�õ������̽ӿڵĿ�Ŀ���");

		Log.print("���뵽�õ���Ŀ���ķ������棡--------��ʼ��!!");
		// ԭ���ķ���
		Connection conn = null;
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lImportCurrencyID = -1;
		try
		{
			lImportCurrencyID = lCurrencyID;
			// ///���¿�Ŀ���
			conn = GLGaSoftBean.getOracleConnection(lOfficeID, lCurrencyID);
			StringBuffer strSQL = new StringBuffer();
			strSQL.setLength(0);

			String strDate = DataFormat.getDateString(tsDate);
			System.out.println(" �õ���ʽ����Ĵ������������ǣ�" + strDate);

			strSQL.append(" select a.strAccountCode,  \n");
			strSQL.append(" a.strAccountName,   \n");
			strSQL.append(" a.lngAccountTypeID,   \n");
			strSQL.append(" a.blnIsDetail,   \n");
			strSQL.append(" decode(a.intDirection,1,1,2) intDirection,  \n");
			strSQL.append(" dblposteddebit as MDebitBalance,  \n");
			strSQL.append(" dblpostedcredit as MCreditbalance    \n");
			strSQL.append(" from accountdaily d,account a  \n");
			strSQL.append(" where d.lngaccountid = a.lngaccountid  \n");
			strSQL.append(" and d.strdate = ? \n");

			ps = conn.prepareStatement(strSQL.toString());
			Log.print("***********��ѯ�����Ŀ���sql��" + strSQL.toString());
			ps.setString(1, strDate);
			rs = ps.executeQuery();

			int i = 1;
			// GLBalanceInfo����Ŀ��Ϣ��������ݣ���ʱ�п��ܻ����ΪǰSQL�����͵ģ���3��0��Ϣ�е㲻ƥ��
			while (rs != null && rs.next())
			{

				Log.print("--��GL�еõ���Ŀ��Ϊ:getGLSubjectCode��" + rs.getString("strAccountCode"));
				Log.print("  �õ����  :  " + (rs.getDouble("MDebitBalance") + rs.getDouble("MCreditbalance")));

				GLBalanceInfo info = new GLBalanceInfo();
				info.setGLDate(tsDate); // ����
				info.setOfficeID(lOfficeID); // ���´�
				info.setCurrencyID(lCurrencyID); // ����
				info.setGLSubjectCode(rs.getString("strAccountCode")); // ��Ŀ��
				info.setBalanceDirection(rs.getLong("intDirection")); // ����
				info.setDebitBalance(rs.getDouble("MDebitBalance")); // �跽���
				info.setCreditBalance(rs.getDouble("MCreditbalance")); // �������

				Log.print("������ĸ���Ϊ:" + i++);

				list.add(info);
			}

			if (list == null || list.size() == 0)
			{
				System.out.println("=============�õ�������Ϊ��!");
			}
			else
			{
				System.out.println("=============�õ������ݲ�Ϊ��!�����ݣ�������");
				// System.out.println("============="+
				// ((GLBalanceInfo)list.get(0)).getGLSubjectCode() );
			}

			Log.print("���뵽�õ���Ŀ���ķ������棡--------����!!��");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		} catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
		} finally
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			} catch (Exception e)
			{
				Log.print(e.toString());
			}
		}

		return (list != null && list.size() > 0 ? list : null);
		// return null;
	}

	/*
	 * �õ���Ŀ(����)��������� (���)
	 * 
	 * @see com.iss.itreasury.closesystem.basebean.GLExtendBaseBean#getGLSubjectAmount(long, long, long, java.sql.Timestamp)
	 */
	public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsDate) throws Exception
	{

		Connection conn = null;
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lImportCurrencyID = -1;
		try
		{
			lImportCurrencyID = lCurrencyID;
			// //��Ҫȡ�������𣿴��ǿ϶��ģ�����Ҫ

		} catch (Exception e)
		{
			Log.print(e.toString());
			throw new Exception(e.getMessage());
		} finally
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			} catch (Exception e)
			{
				Log.print(e.toString());
			}
		}

		return (list != null && list.size() > 0 ? list : null);

	}

	/*
	 * postGLVoucherToGaSoft()���ж϶Է�������Ϣ�Ƿ����
	 */
	private static long postGLVoucherToGaSoft(Collection collVoucher, long lOfficeID, long lCurrencyID, Timestamp date) throws Exception
	{
		long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbRecord = new StringBuffer();
		try
		{
			GlSettingInfo glSettingInfo = new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
			if (glSettingInfo.getNImportType() == Constant.GLImportType.fb) // �ֱ�
			{
				// �õ�����������Ϣ
				System.out.println("---�õ�����������Ϣ---");
				conn = Database.getConnection();
				conn.setAutoCommit(false);
				if (collVoucher != null)
				{
					System.out.println("ȡƾ֤��Ϣ����������������������");
					Iterator it = collVoucher.iterator();

					while (it.hasNext())
					{
						GLVoucherInfo voucher = (GLVoucherInfo) it.next();
						voucher.setPostStatusID(Constant.GLPostStatus.DOING); // ��������

						for (int EntryCount = 0; voucher.getList() != null && EntryCount < voucher.getList().size(); EntryCount++)
						{
							GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(EntryCount);
							sbRecord.setLength(0);
							// ɾ��Gl_Exp_Interface��δ�ɹ��Ļ�Ʒ�¼��
							sbRecord.append(" delete from  Gl_Exp_Interface where Status !='hz' and OfficeID =  " + lOfficeID + " and CurrencyID =  " + lCurrencyID + " and ID =   "+entryinfo.getID());
							Log.print(sbRecord.toString());
							ps = conn.prepareStatement(sbRecord.toString()); 
							ps.executeUpdate();
							ps.close();
							ps = null;
							sbRecord.setLength(0);
							sbRecord.append(" insert into Gl_Exp_Interface( \n");
							sbRecord.append(" ID, \n ");
							sbRecord.append(" OfficeID, \n ");
							sbRecord.append(" CurrencyID, \n ");
							sbRecord.append(" VoucherNo, \n ");
							sbRecord.append(" TransNo, \n ");
							sbRecord.append(" AccountNo, \n ");
							sbRecord.append(" Direction, \n ");
							sbRecord.append(" Amount, \n ");
							sbRecord.append(" Entered_Dr, \n ");
							sbRecord.append(" Entered_Cr, \n ");
							sbRecord.append(" InterestDate, \n ");
							sbRecord.append(" ExecuteDate, \n ");
							sbRecord.append(" Status, \n ");
							sbRecord.append(" PostDate\n ");
							sbRecord.append(" ) \n");
							sbRecord.append(" values  \n");
							sbRecord.append(" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
							Log.print(sbRecord.toString());
							ps = conn.prepareStatement(sbRecord.toString());
							ps.setLong(1, entryinfo.getID());
							ps.setLong(2, entryinfo.getOfficeID());
							ps.setLong(3, entryinfo.getCurrencyID());
							ps.setString(4, voucher.getVoucherNo());
							ps.setString(5, voucher.getTransNo());
							ps.setString(6, entryinfo.getSubject());
							ps.setLong(7, entryinfo.getDirectionID());
							ps.setDouble(8, entryinfo.getAmount());
							if (entryinfo.getDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
							{
								ps.setDouble(9, entryinfo.getAmount());
								ps.setDouble(10, 0);
							}
							else
							{
								ps.setDouble(9, 0);
								ps.setDouble(10, entryinfo.getAmount());
							}
							ps.setTimestamp(11, entryinfo.getInterestStart());
							ps.setTimestamp(12, entryinfo.getExecute());
							ps.setString(13, "NEW");
							ps.setTimestamp(14, entryinfo.getExecute());
							ps.executeUpdate();
							Log.print("************** after insert **************");
							System.out.println("after insert voucherentry");
							ps.close();
							ps = null;
						}
					}
				}
			}
			else
			{
				// �õ�����������Ϣ
				System.out.println("---�õ�����������Ϣ---");
				conn = Database.getConnection();
				conn.setAutoCommit(false);
				if (collVoucher != null)
				{
					System.out.println("ȡƾ֤��Ϣ����������������������");
					Iterator it = collVoucher.iterator();

					while (it.hasNext())
					{
						GLVoucherInfo voucher = (GLVoucherInfo) it.next();
						voucher.setPostStatusID(Constant.GLPostStatus.DOING); // ��������

						for (int EntryCount = 0; voucher.getList() != null && EntryCount < voucher.getList().size(); EntryCount++)
						{
							GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(EntryCount);
							sbRecord.setLength(0);
							// ɾ��Gl_Exp_Interface��δ�ɹ��Ļ�Ʒ�¼��
							sbRecord.append(" delete from  Gl_Exp_Interface \n");
							sbRecord.append(" where Status !='hz' and OfficeID =  " + lOfficeID + "  \n");
							sbRecord.append(" and CurrencyID =  " + lCurrencyID + "  \n");
							sbRecord.append(" and ExecuteDate = ? and Direction = "+entryinfo.getDirectionID() + "  \n");
							sbRecord.append(" and AccountNo =  ?  \n");
							Log.print(sbRecord.toString());
							ps = conn.prepareStatement(sbRecord.toString());
							ps.setTimestamp(1, entryinfo.getExecute());
							ps.setString(2, entryinfo.getSubject());
							ps.executeUpdate();
							ps.close();
							ps = null;
							sbRecord.setLength(0);
							sbRecord.append(" insert into Gl_Exp_Interface( \n");
							sbRecord.append(" ID, \n ");
							sbRecord.append(" OfficeID, \n ");
							sbRecord.append(" CurrencyID, \n ");
							sbRecord.append(" VoucherNo, \n ");
							sbRecord.append(" TransNo, \n ");
							sbRecord.append(" AccountNo, \n ");
							sbRecord.append(" Direction, \n ");
							sbRecord.append(" Amount, \n ");
							sbRecord.append(" Entered_Dr, \n ");
							sbRecord.append(" Entered_Cr, \n ");
							sbRecord.append(" InterestDate, \n ");
							sbRecord.append(" ExecuteDate, \n ");
							sbRecord.append(" Status, \n ");
							sbRecord.append(" PostDate\n ");
							sbRecord.append(" ) \n");
							sbRecord.append(" values  \n");
							sbRecord.append(" ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
							Log.print(sbRecord.toString());
							ps = conn.prepareStatement(sbRecord.toString());
							ps.setLong(1, entryinfo.getID());
							ps.setLong(2, entryinfo.getOfficeID());
							ps.setLong(3, entryinfo.getCurrencyID());
							ps.setString(4, voucher.getVoucherNo());
							ps.setString(5, voucher.getTransNo());
							ps.setString(6, entryinfo.getSubject());
							ps.setLong(7, entryinfo.getDirectionID());
							ps.setDouble(8, entryinfo.getAmount());
							if (entryinfo.getDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
							{
								ps.setDouble(9, entryinfo.getAmount());
								ps.setDouble(10, 0);
							}
							else
							{
								ps.setDouble(9, 0);
								ps.setDouble(10, entryinfo.getAmount());
							}
							ps.setTimestamp(11, entryinfo.getInterestStart());
							ps.setTimestamp(12, entryinfo.getExecute());
							ps.setString(13, "NEW");
							ps.setTimestamp(14, entryinfo.getExecute());
							ps.executeUpdate();
							Log.print("************** after insert **************");
							System.out.println("after insert voucherentry");
							ps.close();
							ps = null;
						}
					}
				}
			}
			conn.commit();
			conn.close();
			conn = null;
		} catch (Exception e)
		{
			lDealStatusID = Constant.ShutDownStatus.FAIL;
			e.printStackTrace();
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
			if (conn != null)
			{
				conn.rollback();
				conn.close();
				conn = null;
			}
			throw new RemoteException(e.getMessage());
		} finally
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			} catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return lDealStatusID;
	}

	/**
	 * �˷����������ݿ��б���Ĳ�����ֱ������Oracle���ݿ�
	 * 
	 * @return Connection
	 */

	private static Connection getOracleConnection(long lOfficeID, long lCurrencyID) throws Exception
	{
		Connection conn = null;
		System.out.println(" Enter method --getOracleConnection() ");
		try
		{
			// �õ�������Ϣ
			System.out.println(" Enter method --getOracleConnection() ");
			GlSettingInfo gldbinfo = new GlSettingInfo();
			System.out.println(" Enter method --getOracleConnection() ");
			gldbinfo = GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID);
			System.out.println(" End method --getGlSettingInfo() ");
			String DB_IP = gldbinfo.getGlDBIP(); // IP
			String DB_SID = gldbinfo.getGlDBDatabaseName(); // ������
			String DB_USERNAME = gldbinfo.getGlDBUserName(); // �û���
			String DB_PASSWORD = DataFormat.formatNullString(gldbinfo.getGlDBPassWord()); // ����
			String DB_PORT = gldbinfo.getGlDBPort(); // �˿�

			String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;

			System.out.println("dbURL = " + dbURL);
			System.out.println("DB_USERNAME = " + DB_USERNAME);
			System.out.println("DB_PASSWORD = " + DB_PASSWORD);

			Class.forName(jdbcDriver).newInstance();
			conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException sqe)
		{
			Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
			throw sqe;
		}

		return conn;
	}

}
