/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.dao;
import com.iss.itreasury.settlement.transspecial.dataentity.*;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.*;
import java.util.*;
import com.iss.itreasury.dao.*;
import java.sql.*;
import java.lang.reflect.*;
/**
 *
 * <p>Title:���⽻�׵����ݲ����� </p>
 * <p>Description:���ڽ����⽻�׵����ݲ������⽻�׵����ݱ�(Sett_TransSpecialOperation)�� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:isoftstone </p>
 * @author YuqiangLiao
 * @version 1.0
 */
public class Sett_TransSpecialOperationDAO extends SettlementDAO
{
	/**
	 * ��־���
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * �������⽻�׵ķ�����
	 * �߼�˵�����������TransSpecialOperationInfoֵ����������ݿ�
	 *
	 * @param info TransSpecialOperationInfo, ���⽻��ʵ����
	 * @return long �����ɼ�¼�ı�ʶ
	 * @throws Exception
	 */
	public long add(TransSpecialOperationInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement pstmquery = null;
		StringBuffer str = new StringBuffer();
		try
		{
			str.append("insert into sett_TransSpecialOperation values(");
			for (int i = 1; i <= 70; i++)
			{
				str.append("?,");
			}
			str.append("?)");
			con = this.getConnection();
			//���sett_transspecialOperationInfo��ṹ
			String[] strs = this.getTableColumns("sett_TransSpecialOperation", con);
			//����ִ���µ�����
			PreparedStatement pstminsert = null;
			pstminsert = con.prepareStatement(new String(str));
			System.out.println("========================SQL="+str.toString());
			//����fullTableParam����׼��������е���ֵ
			int OperationTag = 1;
			this.fullTableParam(pstminsert, info, strs, OperationTag);
			//ִ��PrepareStatement
			final int RESULT = pstminsert.executeUpdate();
			if (RESULT != 0)
			{
				lReturn = info.getId();
			}
			//this.cleanup(rs);
			this.cleanup(pstminsert);
			this.cleanup(con);
		}
		catch(Exception e)
		{
		     throw e;
		}
		return lReturn;
	}
	/**
	 * �޸����⽻�׵ķ�����
	 * �߼�˵�����ύҪ�޸ĵ����⽻������
	 *
	 * @param info, TransSpecialOperationInfo, ���⽻��ʵ����
	 * @return long, ���޸ļ�¼�ı�ʶ
	 * @throws Exception
	 */
	public long update(TransSpecialOperationInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		StringBuffer str = new StringBuffer();
		try
		{
			con = this.getConnection();
			//���sett_transspecialOperationInfo��ṹ
			String[] cols = this.getTableColumns("sett_transSpecialOperation", con);
			str.append("update sett_TransSpecialOperation set \n");
			int j = 1;
			while (j < (cols.length - 1))
			{
				str.append(cols[j]);
				str.append("=?,\n");
				j++;
			}
			str.append(cols[j]);
			str.append("=? \n where ");
			str.append(cols[0]);
			str.append("=?");
			System.out.println("\n" + new String(str) + "\n");
			//����ִ���µ�����
			pstm = con.prepareStatement(new String(str));
			//����fullTableParam����׼�����±��е���ֵ
			int OperationTag = -1; //���±�־λ
			this.fullTableParam(pstm, info, cols, -1);
			//ִ��PrepareStatement
			final int RESULT = pstm.executeUpdate();
			if (RESULT != 0)
			{
				lReturn = info.getId();
			}
			// this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally 
		{
		    this.cleanup(pstm);
			this.cleanup(con);
		}
		return lReturn;
	}
	/**
	 * ���ݱ�ʶ��ѯ���⽻�׽�����ϸ�ķ�����
	 * �߼�˵����
	 * ͨ�����⽻��ID��Ų������⽻����ϸ
	 *
	 * @param lID long , ���׵�ID                       -----��ָntransno,���Ǽ�¼��oid
	 * @return TransSpecialOperationInfo, ���⽻�׽���ʵ����
	 * @throws Exception
	 */
	public TransSpecialOperationInfo findByID(long lID) throws Exception
	{
		System.out.println("DAO TransSpecialOperationInfo findByID:" + lID);
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TransSpecialOperationInfo tsoi = null;
		try
		{
			con = getConnection();
			pstm = con.prepareStatement("select * from sett_transspecialoperation where id=?");
			pstm.setLong(1, lID);
			rs = pstm.executeQuery();
			tsoi = new TransSpecialOperationInfo();
			ResultSetMetaData rmd = rs.getMetaData();
			if (rmd == null)
			{
				System.out.println("rs.getMetaData() is null");
			}
			while (rs.next())
			{
				getTableInfo(rs, tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		return tsoi;
	}
	
	/**
	 * ���ݱ�ʶ��ѯ���⽻�׽�����ϸ�ķ�����
	 * �߼�˵����
	 * ͨ�����⽻��ID��Ų������⽻����ϸ
	 *
	 * @param lID long , ���׵�ID                       -----��ָntransno,���Ǽ�¼��oid
	 * @return TransSpecialOperationInfo, ���⽻�׽���ʵ����
	 * @throws Exception
	 */
	public TransSpecialOperationInfo findByNo(String no) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TransSpecialOperationInfo tsoi = null;
		try
		{
			con = getConnection();
			pstm = con.prepareStatement("select * from sett_transspecialoperation where stransno="+no);
			rs = pstm.executeQuery();
			tsoi = new TransSpecialOperationInfo();
			ResultSetMetaData rmd = rs.getMetaData();
			if (rmd == null)
			{
				System.out.println("rs.getMetaData() is null");
			}
			while (rs.next())
			{
				getTableInfo(rs, tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		return tsoi;
	}
	/**
	 * ���ݽ��׺Ų��ҽ���ID�ķ�����
	 */
	public long getIDByTransNo(String strTransNo) throws Exception
	{
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long lID = -1;
		try
		{
			pstm = con.prepareStatement("select ID from sett_transspecialoperation where sTransNo=?");
			pstm.setString(1, strTransNo);
			rs = pstm.executeQuery();
			if (rs != null && rs.next())
			{
				lID = rs.getLong("ID");
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		catch (Exception ex)
		{
			Log.print(ex.toString());
			throw ex;
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		return lID;
	}
	/**
	 * ���������ж����⽻���Ƿ��ظ��ķ�����
	 *
	 * @param TransSpecialOperationInfo searchInfo , ���⽻��ʵ����
	 * @return boolean , false �ظ�
	 * @throws Exception
	 */
	public boolean checkIsDuplicate(TransSpecialOperationInfo searchInfo) throws Exception
	{
		boolean rtnFlg = true;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from sett_TransSpecialOperation where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, searchInfo.getId());
			//ps.setLong(1,lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				rtnFlg = false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}
	/**
	 * �޸����⽻��״̬�ķ�����
	 * �߼�˵����
	 *
	 * @param lID, long, ���⽻�ױ�ʶ                     -----��ָ���ݿ��е�sTransNo ���ױ��
	 * @param dtModify, java.sql.Timestamp,�޸ı�־ʱ��	
	 * @param lStatusID, long, ״̬��ʶ
	 * @return long, ���޸ļ�¼�ı�ʶ
	 * @throws Exception
	 */
	public long updateStatus(long lID, java.sql.Timestamp dtModify, long lStatusID) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		try
		{
			con = this.getConnection();
			pstm = con.prepareStatement("update sett_transspecialOperation set nstatusid=?,dtmodify=? where id=?");
			pstm.setLong(1, lStatusID);
			pstm.setTimestamp(2, (java.sql.Timestamp) dtModify);
			pstm.setLong(3, lID);
			Log.print("lStatusID =" + lStatusID);
			Log.print("dtModify =" + dtModify);
			Log.print("lID =" + lID);
			int i = pstm.executeUpdate();
			this.cleanup(pstm);
			this.cleanup(con);
			if (i != 0)
			{
				return lID; //Long.parseLong(lID);   //��������?
			}
			else
			{
				return -1;
			}
		}
		finally
		{ 
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}
	//added by qhzhou 2007-08-13���¸�����
	public long updateCheckUser(long lID,java.sql.Timestamp dtModify,long lCheckUserID) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		try
		{
			con = this.getConnection();
			pstm = con.prepareStatement("update sett_transspecialOperation set ncheckuserid=? where id=?");
			pstm.setLong(1, lCheckUserID);
			pstm.setTimestamp(2, (java.sql.Timestamp) dtModify);
			pstm.setLong(2, lID);
			Log.print("lCheckUserID =" + lCheckUserID);
			Log.print("dtModify =" + dtModify);
			Log.print("lID =" + lID);
			int i = pstm.executeUpdate();
			this.cleanup(pstm);
			this.cleanup(con);
			if (i != 0)
			{
				return lID; //Long.parseLong(lID);   //��������?
			}
			else
			{
				return -1;
			}
		}
		finally
		{ 
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}
	public long updatePartValues(String[] cols, TransSpecialOperationInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement pstm = null;
		StringBuffer str = new StringBuffer();
		long lReturn = -1;
		str.append("update sett_TransSpecialOperation set \n");
		try
		{
			con = this.getConnection();
			int j = 1;
			while (j < (cols.length - 1))
			{
				str.append(cols[j]);
				str.append("=?,\n");
				j++;
			}
			str.append(cols[j]);
			str.append("=? \n where ");
			str.append(cols[0]);
			str.append("=?");
			System.out.println("\n" + new String(str) + "\n");
			//����ִ���µ�����
			pstm = con.prepareStatement(new String(str));
			//����fullTableParam����׼�����±��е���ֵ
			int OperationTag = -1; //���±�־λ
			this.fullTableParam(pstm, info, cols, -1);
			//ִ��PrepareStatement
			final int RESULT = pstm.executeUpdate();
			if (RESULT != 0)
			{
				lReturn = info.getId();
			}
			// this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
		finally
		{
		    this.cleanup(pstm);
			this.cleanup(con);   
		}
		
		return lReturn;
	}
	/**
	 * ����״̬��ѯ�ķ�����
	 * �߼�˵����
	 * ͨ��״̬��ѯ����,����������������ҵ������ϸ��¼����ѯ����
	 *
	 * @param QueryByStatusConditionInfo , ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Vector ,����QueryByStatusConditionInfo��ѯ���ʵ����ļ�¼��
	 * @throws Exception
	 */
	public Vector findByStatus(QueryByStatusConditionInfo info, String strOrderByName, boolean isDesc) throws Exception
	{
		/*
		 System.out.println("OfficeID: "+info.getOfficeID());
		 System.out.println("CurrencyID:"+info.getCurrencyID());
		 System.out.println("TypeID:"+info.getTypeID());
		 System.out.println("UserID:"+info.getUserID());
		 System.out.println("Date:"+info.getDate());
		 System.out.println("strOrderByName:"+strOrderByName);
		 System.out.println("isDesc:"+isDesc);
		 
		 if(info.getStatusIDs().length>0){
		 System.out.println("statusids:"+(info.getStatusIDs())[0]);
		 }
		 */
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long[] statusids = null;
		StringBuffer cmd = new StringBuffer();
		try
		{
		    con = this.getConnection();
			cmd.append("select * from sett_transspecialoperation ");
			cmd.append(" where nofficeid=" + info.getOfficeID());
			cmd.append(" and ncurrencyid=" + info.getCurrencyID());
			cmd.append(" and ntransactiontypeid=" + SETTConstant.TransactionType.SPECIALOPERATION);
			statusids = info.getStatusIDs();
			if (statusids != null && statusids.length == 1)
			{
				long statusid = statusids[0];
				cmd.append(" and nstatusid=");
				cmd.append(statusid);
			}
			else if (statusids != null && statusids.length == 2)
			{
				long statusid0 = statusids[0];
				long statusid1 = statusids[1];
				cmd.append(" and (nstatusid=" + statusid0 + "or nstatusid=" + statusid1 + ")");
			}
			if (info.getTypeID() == 0)
			{ //�������
				cmd.append(" and ninputuserid=");
				cmd.append(info.getUserID());
				/*
				 if (info.getDate() != null)
				 {
				 cmd.append(" and dtexecute=TO_DATE('"+DataFormat.getDateString(info.getDate())+"','yyyy-mm-dd')");
				 }
				 */
			}
			else if (info.getTypeID() == 1)
			{ //���˲���
				cmd.append(" and ncheckuserid=");
				cmd.append(info.getUserID());
				if (info.getDate() != null)
				{
					//��������
					cmd.append(" and dtexecute=TO_DATE('" + DataFormat.getDateString(info.getDate()) + "','yyyy-mm-dd')");
				}
			}
			if (strOrderByName != null && !isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
			}
			else if (strOrderByName != null && isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
				cmd.append(" desc");
			}
			Log.print(cmd);
			pstm = con.prepareStatement((new String(cmd)));
			rs = pstm.executeQuery();
			while (rs.next())
			{
				System.out.println("ResultSet values");
				TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
				this.getTableInfo(rs, tsoi);
				v.add(tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
			if (!v.isEmpty())
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return v;
			}
			else
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return null;
			}
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}
	
	/**
	 * ������ҵ�������ͺ�״̬��ѯ
	 * �߼�˵����
	 * ͨ��״̬��ѯ����,����������������ҵ������ϸ��¼����ѯ����
	 *
	 * @param QueryByStatusConditionInfo , ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Vector ,����QueryByStatusConditionInfo��ѯ���ʵ����ļ�¼��
	 * @throws Exception
	 */
	public Vector findBySubtransSpecialIDandStatus(QueryBySubSpecialTypeAndStatusInfo info, String strOrderByName, boolean isDesc) throws Exception
	{
		/*
		 System.out.println("OfficeID: "+info.getOfficeID());
		 System.out.println("CurrencyID:"+info.getCurrencyID());
		 System.out.println("TypeID:"+info.getTypeID());
		 System.out.println("UserID:"+info.getUserID());
		 System.out.println("Date:"+info.getDate());
		 System.out.println("strOrderByName:"+strOrderByName);
		 System.out.println("isDesc:"+isDesc);
		 
		 if(info.getStatusIDs().length>0){
		 System.out.println("statusids:"+(info.getStatusIDs())[0]);
		 }
		 */
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long[] statusids = null;
		StringBuffer cmd = new StringBuffer();
		try
		{
		    con = this.getConnection();
			cmd.append("select * from sett_transspecialoperation ");
			cmd.append(" where nofficeid=" + info.getOfficeID());
			cmd.append(" and ncurrencyid=" + info.getCurrencyID());
			statusids = info.getStatusIDs();
			if (statusids != null && statusids.length == 1)
			{
				long statusid = statusids[0];
				cmd.append(" and nstatusid=");
				cmd.append(statusid);
			}
			else if (statusids != null && statusids.length == 2)
			{
				long statusid0 = statusids[0];
				long statusid1 = statusids[1];
				cmd.append(" and (nstatusid=" + statusid0 + " or nstatusid=" + statusid1 + ")");
			}
			if (info.getTypeID() == 0)
			{ //�������
				cmd.append(" and ninputuserid=");
				cmd.append(info.getUserID());
				/*
				 if (info.getDate() != null)
				 {
				 cmd.append(" and dtexecute=TO_DATE('"+DataFormat.getDateString(info.getDate())+"','yyyy-mm-dd')");
				 }
				 */
			}
			else if (info.getTypeID() == 1)
			{ //���˲���
				cmd.append(" and ncheckuserid=");
				cmd.append(info.getUserID());
				if (info.getDate() != null)
				{
					//��������
					cmd.append(" and dtexecute=TO_DATE('" + DataFormat.getDateString(info.getDate()) + "','yyyy-mm-dd')");
				}
			}
			if(info.getTransactiontypeid()>0 )
			{
				cmd.append(" and NTRANSACTIONTYPEID=");
				cmd.append( info.getTransactiontypeid());
				
			}
			if(info.getSubTransactiontypeid()>0)
			{
				cmd.append(" and NOPERATIONTYPEID=");
				cmd.append( info.getSubTransactiontypeid());
				
			}
			
			if (strOrderByName != null && !isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
			}
			else if (strOrderByName != null && isDesc)
			{
				cmd.append(" order by ");
				cmd.append(strOrderByName);
				cmd.append(" desc");
			}
			Log.print(cmd);
			pstm = con.prepareStatement((new String(cmd)));
			rs = pstm.executeQuery();
			while (rs.next())
			{
				System.out.println("ResultSet values");
				TransSpecialOperationInfo tsoi = new TransSpecialOperationInfo();
				this.getTableInfo(rs, tsoi);
				v.add(tsoi);
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
			if (!v.isEmpty())
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return v;
			}
			else
			{
				//System.out.println("TransSpeicalOperationDAO'S FindByStatus Method Result is null");
				return null;
			}
		}
		finally
		{
		    this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
		}
	}

	/**
	 * ����ƥ��ķ�����
	 * �߼�˵����
	 *
	 * @param TransSpecialOperationInfo,���⽻�׸���ƥ���ѯ����ʵ����
	 * @return TransSpecialOperationInfo //����TransSpecialOperationInfo��ѯ���ʵ����ļ�¼��
	 * @throws Exception
	 */
	public TransSpecialOperationInfo match(TransSpecialOperationInfo info, SpecialOperationInfo soi) throws Exception
	{
		Vector v = new Vector(); 
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StringBuffer buffer = null;
		ArrayList selcols = new ArrayList();
		ArrayList compareopers = new ArrayList();
		ArrayList logicopers = new ArrayList();
		ArrayList arr = new ArrayList();
		try
		{ 
			//�鿴�ж��ٷָ���ͼ         
			String detailtypes = soi.getScontent();
			StringTokenizer token = new StringTokenizer(detailtypes, ",");
			String[] types = new String[token.countTokens()];
			int j = 0;
			while (token.hasMoreTokens())
			{
				types[j] = token.nextToken();
				//System.out.println(types[j]);
				j++;
			}
			int i = 0;
			while (i < types.length)
			{
				if (types[i].compareTo("�����ϸ����") == 0)
				{
					System.out.println(types[i]);
					fullPayerDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("�տ��ϸ����") == 0)
				{
					System.out.println(types[i]);
					fullReceiverDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("ί�и���ƾ֤����") == 0)
				{
					System.out.println(types[i]);
					fullPayVoucherDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("Ʊ������") == 0)
				{
					System.out.println(types[i]);
					fullBillWarrantDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				else if (types[i].compareTo("��������") == 0)
				{
					System.out.println(types[i]);
					fullBankDetailsParam(info, selcols, compareopers, logicopers, arr);
				}
				i++;
			}
			//������Ϣ
			fullOtherDetailsParam(info, selcols, compareopers, logicopers, arr);
			String classname = info.getClass().getName();
			v = this.findByCondition(classname, "sett_transSpecialOperation", selcols, compareopers, logicopers, arr);
			if (!v.isEmpty())
			{
				return (TransSpecialOperationInfo) v.firstElement();
			}
			else
			{
				return null;
			} 
		}
		finally
		{
		    
		}
	}
	private void fullOtherDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//added by mzh_fu 2007/04/10 ����ҵ��������У��
		selcols.add("NOPERATIONTYPEID");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNoperationtypeid()));
		
		//added by xwhe 2009/01/06
		selcols.add("NINPUTUSERID");
		logicopers.add("and");
		compareopers.add("<>");
		arr.add(new Long(info.getNcheckuserid()));
		
		//������Ϣ
		System.out.println("fullOtherDetailsParam ");
		//		���´����
		selcols.add("Nofficeid");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNofficeid()));
		//���ұ��
		selcols.add("Ncurrencyid");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNcurrencyid()));
		//ҵ��״̬
		selcols.add("Nstatusid");
		logicopers.add("and");
		compareopers.add("=");
		arr.add(new Long(info.getNstatusid()));
		//dtInterestStart	��Ϣ��
		selcols.add("dtInterestStart");
		logicopers.add("and");
		if (info.getDtintereststart() == null)
		{
			compareopers.add("is");
			arr.add(new String("Timestamp"));
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getDtintereststart());
		}
		//dtExecute	ִ����
		selcols.add("dtExecute");
		//logicopers.add("and");
		if (info.getDtexecute() == null)
		{
			compareopers.add("is");
			arr.add(new String("Timestamp"));
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getDtexecute());
		}
	}
	private void fullBankDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//if(info.getSextaccountno()!=null){
		//�ǲ���˾�˻���
		System.out.println("fullBankDetailsParam ");
		//�����˻��� ��Ӧ �ǲ���˾�˻��� sExtAccountNo
		selcols.add("sExtAccountNo");
		logicopers.add("and");
		if (info.getSextaccountno() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getSextaccountno());
		}
//		//sBankChequeNo	����֧Ʊ��
//		selcols.add("sBankChequeNo");
//		logicopers.add("and");
//		//arr.add(new String(info.getSbankchequeno()));
//		if (info.getSbankchequeno() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSbankchequeno());
//		}
		//sExtClientName	�ǲ���˾�˻�����
		selcols.add("sExtClientName");
		logicopers.add("and");
		//arr.add(new String(info.getSextclientname()));
		if (info.getSextclientname() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getSextclientname());
		}
//		//sRemitInProvince	����ʡ
//		selcols.add("sRemitInProvince");
//		logicopers.add("and");
//		if (info.getSremitinprovince() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSremitinprovince());
//		}
//		//sRemitInBank	����������
//		selcols.add("sRemitInBank");
//		logicopers.add("and");
//		if (info.getSremitinbank() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSremitinbank());
//		}
//		//sDeclarationNo	������
//		selcols.add("sDeclarationNo");
//		logicopers.add("and");
//		if (info.getSdeclarationno() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSdeclarationno());
//		}
//		//sRemitInCity	������
//		selcols.add("sRemitInCity");
//		logicopers.add("and");
//		if (info.getSremitincity() == null)
//		{
//			compareopers.add("is");
//			arr.add("String");
//		}
//		else
//		{
//			compareopers.add("=");
//			arr.add(info.getSremitincity());
//		}
		//}
	}
	private void fullBillWarrantDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{

	}
	private void fullPayVoucherDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{

	}
	private void fullReceiverDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//if(info.getNreceiveaccountid()!=-1){
		//����տ�˻�ID
		System.out.println("fullReceiverDetailsParam info.getNreceiveaccountid(): " + info.getNreceiveaccountid());
		//�տ�˻�ID
		selcols.add("nreceiveaccountid");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceiveaccountid()));
		//�տ�ͻ����
		selcols.add("nReceiveClientID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceiveclientid()));
		//sReceiveFixedDepositNo	�տ�浥��
		selcols.add("sReceiveFixedDepositNo");
		logicopers.add("and");
		if (info.getSreceivefixeddepositno() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(info.getSreceivefixeddepositno());
		}
		//nReceiveContractID	�տ��ͬID
		selcols.add("nReceiveContractID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivecontractid()));
		//nReceiveLoanNoteID	�տ�ſ�֪ͨ��ID
		selcols.add("nReceiveLoanNoteID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceiveloannoteid()));
		//}else if(info.getNreceivebankid()!=-1){
		//����տ������ID
		System.out.println("fullReceiverDetailsParam info.getNreceivebankid(): " + info.getNreceivebankid());
		//�տ������ID
		selcols.add("nReceiveBankID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivebankid()));


		
		//nReceiveSingleBankAccountTypeID	�տ���е���������ID
		selcols.add("nreceivesinglebankaccounttypei");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivesinglebankaccounttypei()));
		//nReceiveCashFlowID	�տ�ֽ�����ID
		selcols.add("nReceiveCashFlowID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivecashflowid()));
		//}else if(info.getNreceivegeneralledgertypeid()!=-1){
		//����տ������ID
		System.out.println("fullReceiverDetailsParam info.getNreceivegeneralledgertypeid(): " + info.getNreceivegeneralledgertypeid());
		//nReceiveGeneralLedgerTypeID	�տ������ID
		selcols.add("nReceiveGeneralLedgerTypeID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNreceivegeneralledgertypeid()));
		//mReceiveAmount	�տ���
		selcols.add("mReceiveAmount");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getMreceiveamount()));
		//nReceiveDirection	�տ��
		selcols.add("nReceiveDirection");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getNreceivedirection()));
		//	}
	}
	/**
	 * ��֯�����ϸ���ϵĲ�������
	 * 
	 * @param info ����TransSpecialOperationInfo,����Դ
	 * @param selcols ����ArrayList,��֯Ҫ��ѯ����
	 * @param compareopers ����ArrayList,��֯�Ƚ��߼���
	 * @param logicopers ����ArrayList,��֯��ѯ�������߼���ϵԪ���
	 * @param arr ����ArrayList,��֯��ѯ����ֵ
	 */
	private void fullPayerDetailsParam(TransSpecialOperationInfo info, ArrayList selcols, ArrayList compareopers, ArrayList logicopers, ArrayList arr)
	{
		//if(info.getNpayaccountid()!=-1){
		//��ø���˻�ID
		System.out.println("fullPayerDetailsParam info.getNpayaccountid(): " + info.getNpayaccountid());
		//�����˻����
		selcols.add("npayaccountid");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpayaccountid()));
		//����ͻ����
		selcols.add("nPayClientID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpayclientid()));
		//����浥��
		selcols.add("sPayFixedDepositNo");
		if ((String) info.getSpayfixeddepositno() == null)
		{
			compareopers.add("is");
			arr.add("String");
		}
		else
		{
			compareopers.add("=");
			arr.add(new String(info.getSpayfixeddepositno()));
		}
		logicopers.add("and");
		//�����ͬID
		selcols.add("nPayContractID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaycontractid()));
		//����ſ�֪ͨ��ID
		selcols.add("nPayLoanNoteID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpayloannoteid()));
		//}else if(info.getNpaybankid()!=-1){
		//��ø��������ID
		System.out.println("fullPayerDetailsParam info.getNpaybankid(): " + info.getNpaybankid());
		//���������ID
		selcols.add("nPayBankID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaybankid()));
		

		
		//������е���������ID
		selcols.add("nPaySingleBankAccountTypeID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaysinglebankaccounttypeid()));
		//����ֽ�����ID
		selcols.add("nPayCashFlowID");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaycashflowid()));
		//}else if(info.getNpaygeneralledgertypeid()!=-1){
		//���������ID
		System.out.println("fullPayerDetailsParam info.getNpaygeneralledgertypeid(): " + info.getNpaygeneralledgertypeid());
		selcols.add("npaygeneralledgertypeid");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Long(info.getNpaygeneralledgertypeid()));
		//������
		selcols.add("mPayAmount");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getMpayamount()));
		//�����	
		selcols.add("nPayDirection");
		compareopers.add("=");
		logicopers.add("and");
		arr.add(new Double(info.getNpaydirection()));
		//}
	}
	/**
	 * �÷������ڽ�ֵ����VOӳ�䵽���ݱ���
	 *˵����1.VO�����Գ�Ա����Ҫ�����ݿ���ֶ����ƶ�Ӧ�������в��VO���������Գ�Աһ��ҪСд
	 * 2.��������涨---arr������Ҫ�ֶ���˳��Ҫ��pstmִ�еĲ�������в������ֶ�˳��һһ��Ӧ
	 * 3.���²����涨---(I) �ø��²���where������ֻ������ָ��id�ֶ�
	 * (II)arr�����е�һ��Ԫ�ر�����Ҫ���±��id�ֶΣ�֮����ֶ�˳��Ҫ��update�����set�ֶ�˳����ͬ
	 * 3.�÷����������ڲ����������²���
	 * 
	 * @param pstm Ҫִ�е�Ԥ�������
	 * @param o �����ֵ����(Value Object)
	 * @param arr �����Ҫ�������±���ֶ���
	 * @param operationtag ������־λ---1:����;-1:����
	 * @return PreparedStatment ����Ҫִ�е�Ԥ�������
	 * @throws java.lang.Exception �׳��κ��ڸú����з������쳣
	 */
	public void fullTableParam(PreparedStatement pstm, Object o, String[] arr, int OperationTag) throws Exception
	{
		//System.out.println(o.getClass().toString());         
		int i = 0;
		if (OperationTag == 1)
		{ //�������
			i = 0; //��arr[0]��ʼ
		}
		else if (OperationTag == -1)
		{ //���²���
			//���²�������ʼ���arr[1]��ʼ����arr[0](�ֶ���id)�ŵ����                  	
			i = 1; //���²�����arr[1]��ʼ                    
			Field f0 = o.getClass().getDeclaredField(arr[0].toLowerCase());
			f0.setAccessible(true);
			pstm.setString(arr.length, f0.get(o).toString()); //ID�ֶζ�ӦUpdate��������һλ
		}
		while (i < arr.length)
		{ //����Ҫ�������ֶ�����ֵ
			//ʹ�÷��������getDeclaredField()����������Ӧ����             
			Field f = o.getClass().getDeclaredField(arr[i].toLowerCase());
			//����pstm������ֶη��ʴ�1��ʼ����String[] arr ��1 ,����i+1
			i += 1;
			//setAccessible()��������ʹ�ܷ���˽�л��ܱ���������                  
			f.setAccessible(true);
			//get(Object obj)�����ܹ��Զ��Ѹ��ֶ�ֵ��װ���ʵ��İ�װ��
			Object val = f.get(o);
			//field�ֶ���String����
			if (f.getType() == String.class)
			{
				//Ϊ��ʱ����
				if (val == null)
				{
					System.out.println(f.getName() + ": null");
					//���´���
					if (OperationTag < 0)
					{
						//����i�Ѵ�1��ʼ����update������ֶ�˳��һ�£�����Ҫ����1��Ϊupdate����ж�Ӧ�ֶε�λ��ֵ,��iֵ�����  
						pstm.setString(i - 1, null);
					}
					//���봦��
					else
					{
						pstm.setString(i, null);
					}
				}
				else
				{
					//�ǿ�ʱ����
					System.out.println(f.getName() + ": " + val.toString());
					if (OperationTag < 0)
					{
						//����i�Ѵ�1��ʼ����update������ֶ�˳��һ�£�����Ҫ����1��Ϊupdate����ж�Ӧ�ֶε�λ��ֵ,��iֵ�����
						pstm.setString(i - 1, val.toString());
						//���봦��	
					}
					else
					{
						pstm.setString(i, val.toString());
					}
				}
			}
			else if (f.getType() == Timestamp.class)
			{
				//field�ֶ���Timestamp����
				//�ǿմ���
				if (val != null)
				{
					System.out.println(f.getName() + ": " + val.toString());
					//���´���
					if (OperationTag < 0)
					{
						pstm.setTimestamp(i - 1, (java.sql.Timestamp) val);
					}
					else
					{
						//���봦��	
						pstm.setTimestamp(i, (java.sql.Timestamp) val);
					}
				}
				//�����Դ���
				else
				{
					System.out.println(f.getName() + ": null ");
					//���´���							
					if (OperationTag < 0)
					{
						pstm.setTimestamp(i - 1, null);
					}
					//���봦��
					else
					{
						pstm.setTimestamp(i, null);
					}
				}
			}
			else if (f.getType() == long.class)
			{
				//field�ֶ���long����
				//�ǿմ���
				if (val != null)
				{
					Log.print(f.getName() + ": " + val.toString());
					if (OperationTag < 0)
					{
						pstm.setLong(i - 1, Long.valueOf(val.toString()).longValue());
						//Log.print("Long.parseLong(val.toString())="+Long.parseLong(val.toString()));
						//Log.print("Long.valueOf(val.toString())="+Long.valueOf(val.toString()).longValue());
					}
					else
					{
						pstm.setLong(i, Long.valueOf(val.toString()).longValue());
						//Log.print("Long.parseLong(val.toString())="+Long.parseLong(val.toString()));
						//Log.print("Long.valueOf(val.toString())="+Long.valueOf(val.toString()).longValue());
					}
				}
				//�����Դ���
				else
				{
					if (OperationTag < 0)
					{
						pstm.setLong(i - 1, -1);
					}
					else
					{
						pstm.setLong(i, -1);
					}
					System.out.println(f.getName() + val.toString());
				}
			}
			else if (f.getType() == double.class)
			{
				//field�ֶ���double����
				if (val != null)
				{
					//pstm.setDouble(i,((Double)val).doubleValue());
					if (OperationTag < 0)
					{
						pstm.setDouble(i - 1, Double.parseDouble(val.toString()));
						System.out.println(f.getName() + ": " + val.toString());
					}
					else
					{
						pstm.setDouble(i, Double.parseDouble(val.toString()));
						System.out.println(f.getName() + ": " + val.toString());
					}
				}
				else
				{
					if (OperationTag < 0)
					{
						pstm.setDouble(i - 1, 0);
					}
					else
					{
						pstm.setDouble(i, 0);
					}
					System.out.println(f.getName() + ": 0");
				}
			}
		}
	}
	/** get the current sequence id of table sett_TransSpecialOperationInfo
	 * @return the current maximum id of table sett_TransSpecialOperationInfo
	 * @Exception
	 */
	public long getNextID() throws Exception
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
			// ��sequence��ȡ�õ��콻�׵����кš�			
			sbSQL.append(" select seq_TransSpecialOperationID.nextval nextid from dual");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("nextid");
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
	/**
	 * ��ResultSet�����ӳ�䵽��Ӧ��ֵ�����ϵķ���
	 * ˵�������ؽ����Ҫ��ֵ�������Գ�Աһһ��Ӧ�������Գ�Ա����һ��Сд������ʹ��          
	 *
	 * @param rs  ��ѯ���صĽ���� 
	 * @Object o  ֵ����        
	 * @throws java.lang.Exception
	 */
	public void getTableInfo(ResultSet rs, Object o) throws Exception
	{
		//���ResultSet�ṹ��Schema��        	
		ResultSetMetaData rsmd = null;
		try
		{
			rsmd = rs.getMetaData();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		//TransSpecialOperationInfo tsoi= new TransSpecialOperationInfo();
		int i = 1;
		//��������ÿ���ֶ�Ϊֵ�������Գ�Ա��ֵ
		while (i <= rsmd.getColumnCount())
		{
			//�õ��ֶ���
			String colname = rsmd.getColumnName(i);
			//����ֵ������ÿ���ֶ�
			Field f = o.getClass().getDeclaredField(colname.toLowerCase());
			//System.out.println("getDeclaredField(colname.toLowerCase()");
			f.setAccessible(true);
			//System.out.println(f.getName());
			switch (rsmd.getColumnType(i))
			{
				//varchar�ֶεĴ���     	
				case java.sql.Types.VARCHAR :
					// System.out.println(rs.getString(i));
					f.set(o, rs.getString(i));
					break;
				//NUMERIC�ֶεĴ���
				case java.sql.Types.NUMERIC :
					//�ֶ���ǰΪm,��ʾ���ҽ��
					if (f.getName().charAt(0) == 'm')
					{
						f.set(o, (new Double(rs.getDouble(i))));
						System.out.println(f.getName() + ":" + rs.getDouble(i));
						break;
					}
					//��ŵ�long�͵Ĵ���
					else
					{
						f.set(o, (new Long(rs.getLong(i))));
						System.out.println(f.getName() + ":" + rs.getLong(i));
						break;
					}
				//float���͵Ĵ���
				case java.sql.Types.FLOAT :
					f.set(o, (new Float(rs.getFloat(i))));
					break;
				// TIMESTAMP���͵Ĵ��� 
				case java.sql.Types.DATE :
					f.set(o, rs.getTimestamp(i));
					System.out.println(f.getName() + ":" + rs.getTimestamp(i));
					break;
				default :
					f.set(o, rs.getTimestamp(i));
					break;
			}
			i++;
		}
	}
	/**
	 * ������:
	 * 	getTableColumns
	 * ���ܣ�
	 * 	�õ���Ľṹ
	 * ʹ�ù���
	 * 	����һ��Ҫ��nstatusid�ֶ�
	 * 
	 * @param tablename ������ String
	 * @param con	���ݿ������� Connection ���ú������Ͽ�������
	 * @return  String[] �����������  
	 * @throws Exception
	 */
	public String[] getTableColumns(String tablename, Connection con) throws Exception
	{
		//��ý����Ϊ�յ�shema
	    PreparedStatement pstmquery = null;
	    ResultSet rs = null;
	    String[] strs = null;
	    try
	    {
			pstmquery = con.prepareStatement("select * from " + tablename + " where nstatusid=3737");
			rs = pstmquery.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			//�õ��ñ����������
			strs = new String[rsmd.getColumnCount()];
			System.out.println("columncount:" + rsmd.getColumnCount());
			int j = 1;
			while (j <= rsmd.getColumnCount())
			{
				strs[j - 1] = rsmd.getColumnLabel(j);
				//System.out.println(strs[j-1].toLowerCase());               
				j++;
			}
			//�ر�pstmquery
			this.cleanup(rs);
			this.cleanup(pstmquery);
	    }
	    finally
	    {
			this.cleanup(rs);
			this.cleanup(pstmquery);
	        
	    }
		return strs;
	}
	/**
	 * ��̬������ѯ���������
	 * ˵����1.compareoperation����Ԫ�ظ���Ӧ��cols��Ԫ�ظ�����ȣ�conditionoperation����Ԫ�ظ�����cols������1
	 * 		 2.compareoperation�еıȽϷ��ſ�����"="/">"/"<"/"<>"/">="/"<="/"LIKE"/"is"
	 * 		 3.conditionoperation�е��߼����ſ�����"and"/"or"
	 * 		 4.values ȫ�Ƕ���
	 * 		 5.��cols|compareoperation|conditionoperationΪ�գ�values�ǿ�ʱ��Ϊÿ�ֶ�ƥ��������ѯ
	 * 
	 * @param classname Ҫ��ѯ��ֵ����(VO)������
	 * @param tablename Ҫ��ѯ�ı�
	 * @param cols	    Ҫָ���Ĳ�ѯ��������
	 * @param compareoperation ��ѯ�еıȽϷ�������
	 * @param conditionoperation �����߼���ϵ��������
	 * @param values Ҫ��ѯ������ֵ
	 * @return vector ����������ֵ����VO�����
	 * @throws Exception
	 */
	public Vector findByCondition(String classname, String tablename, ArrayList cols, ArrayList compareoperation, ArrayList conditionoperation, ArrayList values) throws Exception
	{
		System.out.println("\n findByCondition begin \n");
		if (cols.size() == 2)
		{
			//�û�ʲô������
			return null;
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			con = this.getConnection();
			//pstm=con.prepareStatement("select * from sett_transSpecialOperation where sExtAccountNo='aaa' and sBankChequeNo is NULL and sExtClientName is NULL and sRemitInProvince is NULL and sRemitInBank is NULL and sDeclarationNo is NULL and sRemitInCity='bj' ");
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from ");
			buffer.append(tablename);
			buffer.append(" where ");
			if (cols == null && compareoperation == null && conditionoperation == null && !values.isEmpty())
			{
				PreparedStatement psmeta = con.prepareStatement("select * from " + tablename + " where nstatusid=3737");
				//System.out.println("select * from "+tablename+ " where nstatusid=3737");
				ResultSet rsmeta = psmeta.executeQuery();
				ResultSetMetaData rsmd = rsmeta.getMetaData();
				int j = 1;
				String[] allcols = new String[rsmd.getColumnCount()];
				while (j <= rsmd.getColumnCount() - 1)
				{
					buffer.append(rsmd.getColumnLabel(j));
					//System.out.println(rsmd.getColumnLabel(j));
					allcols[j - 1] = rsmd.getColumnLabel(j);
					buffer.append("=? and \n");
					j++;
				}
				buffer.append(rsmd.getColumnLabel(rsmd.getColumnCount()));
				allcols[j - 1] = rsmd.getColumnLabel(rsmd.getColumnCount());
				//System.out.println(cols[j-1]);
				buffer.append("=?");
				this.cleanup(rsmeta);
				this.cleanup(psmeta);
			}
			else if ((conditionoperation != null) && (conditionoperation.size() >= 1))
			{
				int i = 0;
				for (i = 0; i < cols.size() - 1; i++)
				{
					String mycols = (String) cols.get(i);
					String mycompareoper = (String) compareoperation.get(i);
					String myconditionoper = (String) conditionoperation.get(i);
					buffer.append(mycols);
					if (mycompareoper.toLowerCase().compareTo("is") == 0)
					{
						buffer.append(" is NULL ");
					}
					else if (mycompareoper.toLowerCase().compareTo("like") == 0)
					{
						buffer.append(" like ");
						buffer.append("? ");
					}
					else
					{
						buffer.append(mycompareoper);
						buffer.append("? ");
					}
					buffer.append(myconditionoper);
					buffer.append(" \n");
				}
				buffer.append((String) cols.get(cols.size() - 1));
				buffer.append((String) compareoperation.get(compareoperation.size() - 1));
				buffer.append("? \n");
			}
			else if ((conditionoperation == null) || (conditionoperation.size() == 0))
			{
				buffer.append((String) cols.get(0));
				buffer.append((String) compareoperation.get(0));
				if (((String) compareoperation.get(0)).toLowerCase().compareTo("is") == 0)
				{
					buffer.append(" is NULL ");
				}
				else if (((String) compareoperation.get(0)).toLowerCase().compareTo("like") == 0)
				{
					buffer.append(" like ");
					buffer.append("? ");
				}
				else
				{
					buffer.append(((String) compareoperation.get(0)));
					buffer.append("? ");
				}
				buffer.append("?");
			}
			String cmd = new String(buffer);
			//String copycmd=null;
			if (cmd.endsWith("and"))
			{
				buffer.delete(buffer.length() - 3, buffer.length());
			}
			System.out.println("findbycondition sql: ");
			System.out.println(buffer);
			pstm = con.prepareStatement(new String(buffer));
			int p = 0;
			//����Ҫ�������ֶ�����ֵ
			System.out.println("values.size: " + values.size());
			//��ֵ���м�����
			int counter = 1;
			while (p < values.size())
			{
				Object val = values.get(p);
				//����pstm������ֶη��ʴ�1��ʼ����values.size() ��1 ,����p+1
				p++;
				//val��String����
				if (val.getClass() == String.class)
				{
					//Ϊ��ʱ����
					if (val.equals("String"))
					{
						System.out.println((String) cols.get(p - 1) + ": null");
						//pstm.setString(p,null);
					}
					else if (val.toString().compareTo("Timestamp") == 0)
					{
						System.out.println((String) cols.get(p - 1) + ": null ");
						//pstm.setTimestamp(p,null); 	
					}
					else if (val.toString().compareTo("Long") == 0)
					{
						System.out.println((String) cols.get(p - 1) + val.toString());
						//pstm.setLong(p,-1); 						
					}
					else if (val.toString().compareTo("Double") == 0)
					{
						System.out.println((String) cols.get(p - 1) + ": 0");
						//pstm.setDouble(p,0); 
					}
					//�ַ����ǿ�ʱ����	
					else
					{
						System.out.println((String) cols.get(p - 1) + ": " + val.toString());
						pstm.setString(counter, val.toString());
						counter++;
					}
				}
				else 
				//val�ֶ���Timestamp����
				if (val instanceof Timestamp)
				{
					//�ǿմ���
					if (val != null)
					{
						pstm.setTimestamp(counter, (java.sql.Timestamp) val);
						System.out.println((String) cols.get(p - 1) + ": " + val.toString());
						counter++;
						// java.sql.Date mydate=(java.sql.Date)val;
						//pstm.setDate(p,mydate);
					}
					/*
					 //�����Դ���
					 else{
					 System.out.println(cols[p-1]+": null ");
					 pstm.setTimestamp(p,null); 
					 }  
					 */
				}
				//val�ֶ���long����
				else if (val instanceof Long)
				{
					//�ǿմ���
					if (val != null)
					{
						pstm.setLong(counter, Long.parseLong(val.toString()));
						System.out.println((String) cols.get(p - 1) + ": " + ((Long) val).toString());
						counter++;
						//pstm.setLong(p,((Long)val).longValue());
					}
					/*					
					 //�����Դ���
					 else{
					 pstm.setLong(p,-1);
					 System.out.println(cols[p-1]+val.toString());
					 } 
					 */
				}
				else 
				//val�ֶ���double����
				if (val instanceof Double)
				{
					if (val != null)
					{
						//pstm.setDouble(p,((Double)val).doubleValue());
						pstm.setDouble(counter, Double.parseDouble(val.toString()));
						System.out.println((String) cols.get(p - 1) + ": " + val.toString());
						counter++;
					}
					/*
					 else{
					 pstm.setDouble(p,0);
					 System.out.println(cols[p-1]+": 0");
					 }
					 */
				}
			}
			rs = pstm.executeQuery();
			while (rs.next())
			{
				Object obj = Class.forName(classname).newInstance();
				this.getTableInfo(rs, obj);
				if (obj != null)
				{
					v.add(obj);
					Field[] fs = obj.getClass().getDeclaredFields();
					int h = 0;
					System.out.println("\n obj shows begin \n");
					while (h < fs.length)
					{
						fs[h].setAccessible(true);
						if (fs[h].get(obj) != null)
						{
							System.out.println(fs[h].getName() + ": " + fs[h].get(obj).toString());
						}
						else
						{
							System.out.println(fs[h].getName() + ": " + "null");
						}
						h++;
					}
					System.out.println("\n obj shows okey \n");
				}
				else
				{
					System.out.println("obj doesn't build");
				}
			}
			this.cleanup(rs);
			this.cleanup(pstm);
			this.cleanup(con);
			System.out.println("\n findByCondition end \n");
		}
		finally
		{
		    this.cleanup(rs);
		    this.cleanup(pstm);
		    this.cleanup(con);		    
		}
		return v;
	}
	/**
	 * ���ܣ�ͨ����ǰ����֪ͨ��ID��ѯ���˻�ID
	 * @param lPayFormID
	 * @return
	 * @
	 */
	public long getSubAccountIDByPayFormID(long lPayFormID) throws Exception
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
			// ��sequence��ȡ�õ��콻�׵����кš�			
			sbSQL.append(" select ID from sett_subAccount where nStatusID>0 and al_nLoanNoteID=" + lPayFormID);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("ID");
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
	
	public static void main(String[] args){
	    System.out.println("ok");
	}
	
}