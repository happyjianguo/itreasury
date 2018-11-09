/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
abstract public class SettlementDAO extends ITreasuryDAO
{
	//Comment by Huang Ye because they had defined in parent class
/*	*//**DAO operation type*//*
	final static protected int DAO_OPERATION_ADD = 0;
	final static protected int DAO_OPERATION_DEL = 1;
	final static protected int DAO_OPERATION_UPDATE = 2;
	final static protected int DAO_OPERATION_FIND = 3;
	final static protected int DAO_OPERATION_MATCH = 4;
	*//**Table name operated by DAO*//*
	protected String strTableName = null;
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	*//**������ά������������ݿ����ӣ����ⲿҵ�񴴽���DAO������ά������(�ر��ǹر�����) add By Huang Ye*//*
	private Connection transConn = null;*/
	
	/**ȱʡ�Ĺ��캯��,����ά���������DAOȱʡ����˷���*/
	public SettlementDAO(){
	}
	
	public SettlementDAO(String tableName){
		super(tableName);
	}	
	
	/**������ά�������񴴽������ݷ��ʶ���ʹ�øù��캯��*/
	public SettlementDAO(Connection conn){
		transConn = conn;
	}	
	
	public SettlementDAO(Connection conn,boolean isSelfManagedConn){
		super(conn,isSelfManagedConn);
	}	
	
	public SettlementDAO(String tableName,Connection conn) {
		super (tableName,conn);
	}
	
	public SettlementDAO(boolean isNeedPrefix){
		super(isNeedPrefix);
	}
	
	public SettlementDAO(String tableName,boolean isNeedPrefix) {
		super(tableName,isNeedPrefix);
	}
	
	public SettlementDAO(String tableName,boolean isNeedPrefix,Connection conn) {
		super(tableName,isNeedPrefix,conn);
	}		
	
	/**������ݿ������Ѿ��ڹ��캯���д���(��������ݿ��������ⲿά��)��ֱ�ӷ���������ӣ�����
	 *�������ݿ����Ӳ�����ʹ����Ϻ����̹رգ��μ�void cleanup(Connection con)
	*/
	protected Connection getConnection()
	{
		Connection con = null;
		try
		{
			if(transConn == null)
			{
				con = Database.getConnection();
			}
			else
			{
				con = transConn;
			}
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
		}
		return con;		
	}
	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			//Log.print("����ر�RS����");
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	protected void cleanup(CallableStatement cs) throws SQLException
	{
		try
		{
			if (cs != null)
			{
				cs.close();
				cs = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	protected void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			//Log.print("����ر�PS����");
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
    protected void cleanup(Statement stmt) throws SQLException
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
                stmt = null;
            }
        }
        catch (SQLException sqle)
        {
			Log.print(sqle.toString());
        }
    }
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			//Log.print("����ر����ӷ���");
			/**transConn����Ϊ�ձ���������ݿ�������ص�����������ά���ģ���˲��ڴ˴��ر�
			 * ����Assert(con == transConn)
			 */
			//Log.print("con ="+con);
			//Log.print("transConn ="+transConn);
			if (con != null && con.isClosed()==false && transConn == null)
			{
				//Log.print("�ر�����--��ʼ");
				con.close();
				con = null;
				//Log.print("�ر�����--����");
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	//==========================Get Sequence ID from Database===================//
	protected long getSett_TransCurrentDepositID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSCURRENTDEPOSITID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransOnePayMultiReceiveID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TransOnePayMultiReceiveID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransFixedOpenID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSOPENFIXEDDEPOSITID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransMarginOpenID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSMARGINOPENID .nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransMarginWithdrawID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSMARGINWITHDRAWID  .nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransSecuritiesID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_TransSecuritiesID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	//���������տ�
	protected long getSett_TransReceiveFinanceID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TransReceiveFinance.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	//�������޻���
	protected long getSett_TransReturnFinanceID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TransReturnFinance.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransFixedContinueID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSFIXEDCONTINUEID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransFixedDrawID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSFIXEDWITHDRAWID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	/**
	 * ������ַ��ű��IDֵ
	 * @return
	 * @throws Exception
	 */
	protected long getSett_TransGrantDiscountID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_transgrantdiscountid.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	/**
	 * ��������ջر��IDֵ
	 * @return
	 * @throws Exception
	 */
	protected long getSett_TransRepaymentDiscountID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_transrepaymentdiscountid.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}

	protected long getSett_ExternalAccountID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_Sett_ExternalAccountid.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransAccountDetailID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_Sett_AccountDetailsID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransGeneralLedgerID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_TransGeneralLedgerID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	protected long getSett_TransFeeID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_TransFeeID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	
	protected long getSett_GLEntryID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_GLEntryID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	
	protected long getAssitantID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_gl_assistant.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	
	protected long getSett_BankInstructionID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_BankInstruction.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}	
	
	
	protected long getSett_GLBalanceID() throws SQLException
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(max(ID)+1,1) ID from Sett_GlBalance");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("ID");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}	
	//Add other get sequence id function at here
	//=========================================================================//	
	/**
	 * update status by ID, just for the table that have a field named status ID
	 * Attention:
	 * 1. Do not use this function if there is no field named statusid in your table
	 * 2. Make sure passing value to member variable "strTableName" in construction
	 *    of  subclass.
	  */
	public long updateStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("dtModify = sysdate, \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			int num=ps.executeUpdate();
			if(num>0){
				lReturn=id;
			}
		}
		finally
		{
			cleanup(ps);
			cleanup(con);				
		}
		return lReturn;
	}
	
	protected long getSett_LoanDetailId() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_sett_loandetailId.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	
	protected long getSett_RepaymentDetailId() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_sett_repaymentdetailId.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	/**
	 * ����˵�������׼�¼
	 * @param id
	 * @return : long - ���ر�ɾ�����ڽ��׼�¼ID
	 * @throws IException
	 */
	public long delete(long id) throws Exception
	{
		//logic delete, so just update status as deleted
		return this.updateStatus(id, SETTConstant.TransactionStatus.DELETED);
	}
	
	/**
	 * ���ת���ַ��ű��IDֵ
	 * @return
	 * @throws Exception
	 */
	protected long getSett_TransCredenceID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_transcredence.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	/**
	 * ���ת���ַ��ű��IDֵ
	 * @return
	 * @throws Exception
	 */
	protected long getSett_TransDiscountSubjectID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSDISCOUNTSUBJECT.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	public long updateTransStatus(long id, long StatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append("sett_transcredence \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("dtModify = sysdate, \n");
			strSQLBuffer.append("nStatusID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			log.info(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);				
		}
		return id;
	}
	protected long getSett_RANSFERAMOUNTDEPOSITID() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSFERAMOUNTDEPOSITID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	//add by kevin (��������2011-07-18
	protected long getSett_TransInternalLendingId() throws SQLException
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSINTERNALLENDINGID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	
	//Ʊ�ݳжҵ��ڳж�
	protected long getSett_AcceptanceNoteAcceptanceDetailId() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSACCEPTANCENOTEID.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
}
