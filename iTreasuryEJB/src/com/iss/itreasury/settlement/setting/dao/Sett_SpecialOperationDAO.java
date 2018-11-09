
/*
 * Created on 2004-10-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.iss.itreasury.settlement.setting.dataentity.SpecialOperationInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.DataFormat;
/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_SpecialOperationDAO {
	/**
	 * ��ѯ��������ҵ����������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>��ѯ��������ҵ����������</b>
	 * <ul>
	 * <li>�������ݿ��SpecialOperation
	 * <li>����Collection��������SpecialOperationInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID ���´���ʶ
	 * @param lPageLineCount  ÿҳ��������
	 * @param lPageNo         �ڼ�ҳ����
	 * @param lOrderParam     �������������ݴ˲��������������������
	 * @param lDesc           �������
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllSpecialOperation(long lOfficeID, long lStartID, long lEndID, String strContext, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
	{
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strTmpSQL = "";
		String strSQL = "";
		//��ҳ����
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			con = Database.getConnection();
			//��������Ľ����
			Log.print("before sql");
			//strTmpSQL = "";
			switch ((int) lOrderParam)
			{
				case 1 :
					strTmpSQL += " order by ID";
					break;
				case 2 :
					strTmpSQL += " order by sName";
					break;
				case 3 :
					strTmpSQL += " order by sContent";
					break;
				default :
					strTmpSQL += "";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strTmpSQL += " desc";
			}
			strSQL = " select * from (select a.* from Sett_SpecialOperation a where a.nStatusID=?  and nOfficeID=?";
			if (lStartID > 0)
			{
				strSQL = strSQL + " and ID>=" + lStartID;
			}
			if (lEndID > 0)
			{
				strSQL = strSQL + " and ID<=" + lEndID;
			}
			if (strContext != null && !strContext.equals("") && !strContext.equals("null"))
			{
				strSQL = strSQL + " and trim(sContent)='" + strContext.trim() + "'";
			}
			strSQL = strSQL + strTmpSQL + " ) a ";
			ps = con.prepareStatement(strSQL);
			Log.print(strSQL);
			ps.setLong(1,Constant.RecordStatus.VALID);
			ps.setLong(2,lOfficeID);
			rs = ps.executeQuery();
			//Log.print("after sql");
			System.out.println("sql=" + strSQL);
			while (rs.next())
			{
				SpecialOperationInfo specialoperationinfo = new SpecialOperationInfo();
				specialoperationinfo.m_lID = rs.getLong("ID");
				Log.print("id=" + specialoperationinfo.m_lID);
				specialoperationinfo.m_strName = rs.getString("sName");
				specialoperationinfo.m_strContent = rs.getString("sContent");
				specialoperationinfo.m_lStatus = rs.getLong("nStatusID");
				specialoperationinfo.payRelation = rs.getLong("payRelation");
				specialoperationinfo.gatheringRelation = rs.getLong("gatheringRelation");
				specialoperationinfo.m_lPageCount = lPageCount;
				v.add(specialoperationinfo);
			}
			//�ر���Դ
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return (v.size() > 0 ? v : null);
	}
	
	/**
	 * ���ݱ�ʶ��ѯ����ҵ����������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>���ݱ�ʶ��ѯ����ҵ����������</b>
	 * <ul>
	 * <li>�������ݿ��SpecialOperation
	 * <li>������SpecialOperationInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return SpecialOperationInfo
	 * @exception Exception
	 */
	public SpecialOperationInfo findSpecialOperationByID(long lID) 
	{
		SpecialOperationInfo si = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strTmpSQL = "";
		try
		{
			con = Database.getConnection();
			si = new SpecialOperationInfo();
			strTmpSQL = "select id,sname,ispayaccount,ispaybank,IspayGlEntry,IsRecAccount,IsRecBank,IsRecGlEntry,scontent,payRelation,gatheringRelation,nstatusid from Sett_specialoperation where nStatusID=? and id=?";
			ps = con.prepareStatement(strTmpSQL);
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, lID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				si.m_lID = rs.getLong("id");
				si.m_strName = rs.getString("sname");
				si.m_strContent = rs.getString("scontent");
				si.m_lStatus = rs.getLong("nstatusid");
				si.m_Ispayaccount=rs.getLong("ispayaccount");
				si.m_Ispaybank=rs.getLong("ispaybank");
				si.m_IspayGlEntry=rs.getLong("IspayGlEntry");
				si.m_IsRecAccount=rs.getLong("IsRecAccount");
				si.m_IsRecBank=rs.getLong("IsRecBank");
				si.m_IsRecGlEntry=rs.getLong("IsRecGlEntry");
				si.payRelation=rs.getLong("payRelation");
				si.gatheringRelation=rs.getLong("gatheringRelation");
			}
			//�ر���Դ
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return si;
	}
	
	
	/**
	 * ��������ҵ����������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>��������ҵ����������</b>
	 * <ul>
	 * <li>�������ݿ��SpecialOperation
	 * <li>���lID<0������SpecialOperation��������һ����¼
	 * <li>������±�ʶ��lID�ļ�¼��Ϣ
	 * <li>��״̬��Ϊ����
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID ���´���ʶ
	 * @param strName
	 * @param strContent
	 * @return void
	 * @exception Exception
	 */
	public long saveSpecialOperation(long lID, long lOfficeID, String strName, String strContent) 
	{
		long lResult = -1;
		String strTmpSQL = "";
		long lTmpID = 0; //save max(ID)+1����1��ʼ
		long lNum = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			//�ж��Ƿ��ظ�
			if (lID < 0)
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and nOfficeID=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, strName);
				ps.setLong(2, lOfficeID);
			}
			else
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and ID<>? and nOfficeID=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, strName);
				ps.setLong(2, lID);
				ps.setLong(3, lOfficeID);
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next())
				lNum = rs.getLong("num");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (lNum == 0)
			{
				//��
				if (lID < 0)
				{
					//add a new record
					try
					{
						strTmpSQL = "select nvl((max(id)+1),1) id from Sett_specialoperation";
						ps = con.prepareStatement(strTmpSQL);
						rs = ps.executeQuery();
						if (rs != null && rs.next())
							lTmpID = rs.getLong("id");
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						strTmpSQL = "insert into Sett_specialoperation(id,sname,scontent,nstatusid,nOfficeID) values(?,?,?,?,?)";
						ps = con.prepareStatement(strTmpSQL);
						if(lTmpID<1001){
							ps.setLong(1,1001);
						}
						else{
						ps.setLong(1, lTmpID);
						}
						ps.setString(2, strName);
						ps.setString(3, strContent);
						ps.setLong(4, Constant.RecordStatus.VALID);
						ps.setLong(5, lOfficeID);
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
					}
				}
				else
				{
					try
					{
						//update the record
						//con = Database.getConnection();
						strTmpSQL = "update Sett_specialoperation set sname=?,scontent=?,nstatusid=?,nOfficeID=? where id=?";
						ps = con.prepareStatement(strTmpSQL);
						ps.setString(1, strName);
						ps.setString(2, strContent);
						ps.setLong(3, Constant.RecordStatus.VALID);
						ps.setLong(4, lOfficeID);
						ps.setLong(5, lID);
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
						e.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
		if (lNum != 0)
			lResult = 0;
		return lResult;
	}
	//�����ʱ��Ҫ���桰�տ���͡�������Ľ����ϵ��1Ϊ�裬-1Ϊ�� �������˷�����saveSpecialOperation�� ȫ�� 2010-5-26
	public long saveSpecialOperation(long lID, long lOfficeID, String strName, String strContent, long payRelation,long gatheringRelation) 
	{
		long lResult = -1;
		String strTmpSQL = "";
		long lTmpID = 0; //save max(ID)+1����1��ʼ
		long lNum = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			//�ж��Ƿ��ظ�
			if (lID < 0)
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and nOfficeID=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, strName);
				ps.setLong(2, lOfficeID);
			}
			else
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and ID<>? and nOfficeID=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, strName);
				ps.setLong(2, lID);
				ps.setLong(3, lOfficeID);
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next())
				lNum = rs.getLong("num");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (lNum == 0)
			{
				//��
				if (lID < 0)
				{
					//add a new record
					try
					{
						strTmpSQL = "select nvl((max(id)+1),1) id from Sett_specialoperation";
						ps = con.prepareStatement(strTmpSQL);
						rs = ps.executeQuery();
						if (rs != null && rs.next())
							lTmpID = rs.getLong("id");
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						strTmpSQL = "insert into Sett_specialoperation(id,sname,scontent,nstatusid,nOfficeID,payRelation,gatheringRelation) values(?,?,?,?,?,?,?)";
						ps = con.prepareStatement(strTmpSQL);
						if(lTmpID<1001){
							ps.setLong(1,1001);
						}
						else{
						ps.setLong(1, lTmpID);
						}
						ps.setString(2, strName);
						ps.setString(3, strContent);
						ps.setLong(4, Constant.RecordStatus.VALID);
						ps.setLong(5, lOfficeID);
						ps.setLong(6, payRelation);
						ps.setLong(7, gatheringRelation);
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
					}
				}
				else
				{
					try
					{
						//update the record
						//con = Database.getConnection();
						strTmpSQL = "update Sett_specialoperation set sname=?,scontent=?,nstatusid=?,nOfficeID=? ,payRelation =? ,gatheringRelation =? where id=?";
						ps = con.prepareStatement(strTmpSQL);
						ps.setString(1, strName);
						ps.setString(2, strContent);
						ps.setLong(3, Constant.RecordStatus.VALID);
						ps.setLong(4, lOfficeID);
						ps.setLong(5, payRelation);
						ps.setLong(6, gatheringRelation);
						ps.setLong(7, lID);
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
						e.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
		if (lNum != 0)
			lResult = 0;
		return lResult;
	}
	
	//����˱���ID   ����  2010-07-22
	public long saveSpecialOperation(long lID,long currencyID, long lOfficeID, String strName, String strContent, long payRelation,long gatheringRelation) 
	{
		long lResult = -1;
		String strTmpSQL = "";
		long lTmpID = 0; //save max(ID)+1����1��ʼ
		long lNum = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			//�ж��Ƿ��ظ�
			if (lID < 0)
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and nOfficeID=? and ncurrencyid=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, strName);
				ps.setLong(2, lOfficeID);
				ps.setLong(3,currencyID);
			}
			else
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and ID<>? and nOfficeID=? and ncurrencyid=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, strName);
				ps.setLong(2, lID);
				ps.setLong(3, lOfficeID);
				ps.setLong(4, currencyID);
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next())
				lNum = rs.getLong("num");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (lNum == 0)
			{
				//��
				if (lID < 0)
				{
					//add a new record
					try
					{
						strTmpSQL = "select nvl((max(id)+1),1) id from Sett_specialoperation";
						ps = con.prepareStatement(strTmpSQL);
						rs = ps.executeQuery();
						if (rs != null && rs.next())
							lTmpID = rs.getLong("id");
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						strTmpSQL = "insert into Sett_specialoperation(id,sname,scontent,nstatusid,nOfficeID,payRelation,gatheringRelation,ncurrencyid) values(?,?,?,?,?,?,?,?)";
						ps = con.prepareStatement(strTmpSQL);
						if(lTmpID<1001){
							ps.setLong(1,1001);
						}
						else{
						ps.setLong(1, lTmpID);
						}
						ps.setString(2, strName);
						ps.setString(3, strContent);
						ps.setLong(4, Constant.RecordStatus.VALID);
						ps.setLong(5, lOfficeID);
						ps.setLong(6, payRelation);
						ps.setLong(7, gatheringRelation);
						ps.setLong(8, currencyID);
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
					}
				}
				else
				{
					try
					{
						//update the record
						//con = Database.getConnection();
						strTmpSQL = "update Sett_specialoperation set sname=?,scontent=?,nstatusid=?,nOfficeID=? ,payRelation =? ,gatheringRelation =? where id=?";
						ps = con.prepareStatement(strTmpSQL);
						ps.setString(1, strName);
						ps.setString(2, strContent);
						ps.setLong(3, Constant.RecordStatus.VALID);
						ps.setLong(4, lOfficeID);
						ps.setLong(5, payRelation);
						ps.setLong(6, gatheringRelation);
						ps.setLong(7, lID);
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
						e.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
		if (lNum != 0)
			lResult = 0;
		return lResult;
	}

	
	
	//2011-07-01 jiangqi ����ҵ�����ø���
	public long saveSpecialOperation(SpecialOperationInfo info) 
	{
		long lResult = -1;
		String strTmpSQL = "";
		long lTmpID = 0; //save max(ID)+1����1��ʼ
		long lNum = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			//�ж��Ƿ��ظ�
			if (info.m_lID < 0)
			{
				strTmpSQL = "select count(*) num from Sett_specialoperation where sName=? and nOfficeID=? and ncurrencyid=? and nStatusID > 0";
				ps = con.prepareStatement(strTmpSQL);
				ps.setString(1, info.m_strName);
				ps.setLong(2, info.m_lOfficeID);
				ps.setLong(3,info.m_lCurrencyID);
			
				rs = ps.executeQuery();
				if (rs != null && rs.next())
					lNum = rs.getLong("num");
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				if (lNum == 0)
				{
					//��
					//add a new record
					try
					{
						strTmpSQL = "select nvl((max(id)+1),1) id from Sett_specialoperation";
						ps = con.prepareStatement(strTmpSQL);
						rs = ps.executeQuery();
						if (rs != null && rs.next())
							lTmpID = rs.getLong("id");
						rs.close();
						rs = null;
						ps.close();
						ps = null;
						
						/*	jiangqi 
						 * 2011-06-30
						 * sett_specialoperation�����������ֶΣ�
						IspayAccount(�Ƿ���ʾ����ͻ���Ϣ)��
						IspayBank(�Ƿ���ʾ�����������Ϣ)��
						IspayGlEntry(�Ƿ���ʾ���������Ϣ)��
						IsRecAccount(�Ƿ���ʾ�տ�ͻ���Ϣ)��
						IsRecBank(�Ƿ���ʾ�տ��������Ϣ)��
						IsRecGlEntry(�Ƿ���ʾ�տ������Ϣ)
						*/
						strTmpSQL = "insert into Sett_specialoperation(id,sname,IspayAccount,IspayBank,IspayGlEntry,IsRecAccount,IsRecBank,IsRecGlEntry, scontent,nstatusid,nOfficeID,payRelation,gatheringRelation,ncurrencyid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						
						ps = con.prepareStatement(strTmpSQL);
						if(lTmpID<1001){
							ps.setLong(1,1001);
						}
						else{
						ps.setLong(1, lTmpID);
						}
						ps.setString(2, info.m_strName);
						
						//2011-06-30 by jiangqi ����ҵ���������ø���
						if(info.m_strContent.indexOf("�����ϸ����")>=0 && info.m_strContent.indexOf("�տ��ϸ����")>=0)
						{
							if(info.m_Ispayaccount==0 && info.m_Ispaybank==0 && info.m_IspayGlEntry==0)
							{
								throw new Exception("û�кϷ��ĸ�����ʽ��ʾ��Ϣ");
							}
							else if (info.m_IsRecAccount==0 && info.m_IsRecBank==0 && info.m_IsRecGlEntry==0)
							{
								throw new Exception("û�кϷ����տ�տʽ��ʾ��Ϣ");
							}
							else if(info.m_Ispayaccount<0 || info.m_Ispaybank<0 || info.m_IspayGlEntry<0 || info.m_IsRecAccount<0 || info.m_IsRecBank<0 || info.m_IsRecGlEntry<0)
							{
								
								throw new Exception("������ʽ�����տ�տʽ��Ϣ��������ϵ����Ա");
							}
							else{
								
								ps.setLong(3, info.m_Ispayaccount);
								ps.setLong(4, info.m_Ispaybank);
								ps.setLong(5, info.m_IspayGlEntry);
								ps.setLong(6, info.m_IsRecAccount);
								ps.setLong(7, info.m_IsRecBank);
								ps.setLong(8, info.m_IsRecGlEntry);
							}
						}
						else{
							
							throw new Exception("û�кϷ��ĸ�������տ��Ϣ");
						}
						ps.setString(9, info.m_strContent);
						ps.setLong(10, Constant.RecordStatus.VALID);
						ps.setLong(11, info.m_lOfficeID);
						ps.setLong(12, info.payRelation);
						ps.setLong(13, info.gatheringRelation);
						ps.setLong(14, info.m_lCurrencyID);
						
						lResult = ps.executeUpdate();
						ps.close();
						ps = null;
					}
					catch (Exception e)
					{
						System.out.println(e.toString());
					}

			}
			else{
				lResult = 0;
				throw new Exception("��ͬ���Ƶ�����ҵ���Ѵ��ڣ��뷵���޸�����ҵ�����ƣ�");
			}
				
		}
		else{
				
			
				try
				{
					//update the record
					//con = Database.getConnection();
					strTmpSQL = "update Sett_specialoperation set sname=?,IspayAccount=?,IspayBank=?,IspayGlEntry=?,IsRecAccount=?,IsRecBank=?,IsRecGlEntry=?,scontent=?,nstatusid=?,nOfficeID=? ,ncurrencyid=?,payRelation =? ,gatheringRelation =? where id=?";
					ps = con.prepareStatement(strTmpSQL);
					ps.setString(1, info.m_strName);
					
					//2011-06-30 by jiangqi ����ҵ���������ø���
					if(info.m_strContent.indexOf("�����ϸ����")>=0 && info.m_strContent.indexOf("�տ��ϸ����")>=0)
					{
						if(info.m_Ispayaccount==0 && info.m_Ispaybank==0 && info.m_IspayGlEntry==0)
						{
							throw new Exception("û�кϷ��ĸ�����ʽ��ʾ��Ϣ");
						}
						else if (info.m_IsRecAccount==0 && info.m_IsRecBank==0 && info.m_IsRecGlEntry==0)
						{
							throw new Exception("û�кϷ����տ�տʽ��ʾ��Ϣ");
						}
						else if(info.m_Ispayaccount<0 || info.m_Ispaybank<0 || info.m_IspayGlEntry<0 || info.m_IsRecAccount<0 || info.m_IsRecBank<0 || info.m_IsRecGlEntry<0)
						{
							
							throw new Exception("������ʽ�����տ�տʽ��Ϣ��������ϵ����Ա");
						}
						else{
							
							ps.setLong(2, info.m_Ispayaccount);
							ps.setLong(3, info.m_Ispaybank);
							ps.setLong(4, info.m_IspayGlEntry);
							ps.setLong(5, info.m_IsRecAccount);
							ps.setLong(6, info.m_IsRecBank);
							ps.setLong(7, info.m_IsRecGlEntry);
						}
					}
					else{
						
						throw new Exception("û�кϷ��ĸ�������տ��Ϣ");
					}
					ps.setString(8, info.m_strContent);
					ps.setLong(9, Constant.RecordStatus.VALID);
					ps.setLong(10, info.m_lOfficeID);
					ps.setLong(11, info.m_lCurrencyID);
					ps.setLong(12, info.payRelation);
					ps.setLong(13, info.gatheringRelation);
					ps.setLong(14, info.m_lID);
					lResult = ps.executeUpdate();
					ps.close();
					ps = null;
				}
				catch (Exception e)
				{
					System.out.println(e.toString());
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}

		return lResult;
	}

	
	/**
	 * ɾ������ҵ����������
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>ɾ������ҵ����������</b>
	 * <ul>
	 * <li>�������ݿ��SpecialOperation
	 * <li>��״̬��Ϊɾ��
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return lResult
	 * @exception Exception
	 */
	public long deleteSpecialOperation(long lID)
	{
		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			//�ж��Ƿ����ù�
			sb.append("select * from Sett_TransSpecialOperation where nOperationTypeID=? and nstatusid<>? ");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			ps.setLong(2, Constant.RecordStatus.INVALID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				return -11;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//���ļ�¼
			sb.setLength(0);
			sb.append("update sett_specialoperation set nstatusid=? where id=? ");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1,Constant.RecordStatus.INVALID);
			ps.setLong(2, lID);
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			con.close();
			con = null;
			lResult = 1;
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
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
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
		return lResult;
	}	
	
	public long[] getAllSpecialOperationForConstant()
	{
		System.out.println("������������������������ getAllSpecialOperationForConstant����������������������");
		long[] arrReturn = null;
		int i = 0;
		try
		{
			Collection c = findAllSpecialOperation(1, -1, -1, "", -1, -1, 1, 1);
			if (c != null && c.size() > 0)
			{
				arrReturn = new long[c.size()];
				Iterator it = c.iterator();
				while (it.hasNext())
				{
					SpecialOperationInfo specialoperationinfo = new SpecialOperationInfo();
					specialoperationinfo = (SpecialOperationInfo) it.next();
					arrReturn[i] = specialoperationinfo.m_lID;
					i++;
				}
			}
			else
			{
				arrReturn = null;
			}
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
		System.out.println("���������������������˳� getAllSpecialOperationForConstant����������������������");
		return arrReturn;
	}
	
	
	/**
	 * added by mzh_fu 2007/06/05
	 * 
	 * @param officeID
	 * @return
	 */
	public long[] getSpecialOperationIdsByOfficeID(long officeID) {
		long[] arrReturn = null;
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;

		try {
			String strSQL = "select ID from Sett_SpecialOperation where nStatusID = "
					+ Constant.RecordStatus.VALID + "  and nOfficeID=?";
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, officeID);
			rs = ps.executeQuery();

			while (rs.next()) {
				SpecialOperationInfo specialoperationinfo = new SpecialOperationInfo();				
				specialoperationinfo.m_lID = rs.getLong("ID");
//				specialoperationinfo.m_strName = rs.getString("sName");
//				specialoperationinfo.m_strContent = rs.getString("sContent");
//				specialoperationinfo.m_lStatus = rs.getLong("nStatusID");
				
				v.add(specialoperationinfo);
			}

			if (v != null && v.size() > 0) {
				arrReturn = new long[v.size()];
				Iterator it = v.iterator();

				while (it.hasNext()) {
					SpecialOperationInfo specialoperationinfo = new SpecialOperationInfo();
					specialoperationinfo = (SpecialOperationInfo) it.next();
					arrReturn[i] = specialoperationinfo.m_lID;
					i++;
				}
			} 
			
		} catch (Exception e) {
			Log.print(e.toString());
//			throw e;
		} finally {
			try {
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
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return arrReturn;
	}
	/**
	 * ��ѯ��������ҵ����������
	 * @param lcurrencyID  ��Ӷ����֧��   add by zhanglei   2010-07-22
	 * @param lOfficeID
	 * @param lStartID
	 * @param lEndID
	 * @param strContext
	 * @param lPageLineCount
	 * @param lPageNo
	 * @param lOrderParam
	 * @param lDesc
	 * @return
	 */
	public Collection findAllSpecialOperation(long lcurrencyID,long lOfficeID, long lStartID, long lEndID, String strContext, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc)
	{
		Vector v = new Vector();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strTmpSQL = "";
		String strSQL = "";
		//��ҳ����
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			con = Database.getConnection();
			//��������Ľ����
			Log.print("before sql");
			//strTmpSQL = "";
			switch ((int) lOrderParam)
			{
				case 1 :
					strTmpSQL += " order by ID";
					break;
				case 2 :
					strTmpSQL += " order by sName";
					break;
				case 3 :
					strTmpSQL += " order by sContent";
					break;
				default :
					strTmpSQL += "";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strTmpSQL += " desc";
			}
			strSQL = " select * from (select a.* from Sett_SpecialOperation a where a.nStatusID=?  and nOfficeID=? and ncurrencyid=?";
			if (lStartID > 0)
			{
				strSQL = strSQL + " and ID>=" + lStartID;
			}
			if (lEndID > 0)
			{
				strSQL = strSQL + " and ID<=" + lEndID;
			}
			if (strContext != null && !strContext.equals("") && !strContext.equals("null"))
			{
				strSQL = strSQL + " and trim(sContent)='" + strContext.trim() + "'";
			}
			strSQL = strSQL + strTmpSQL + " ) a ";
			ps = con.prepareStatement(strSQL);
			Log.print(strSQL);
			ps.setLong(1,Constant.RecordStatus.VALID);
			ps.setLong(2,lOfficeID);
			ps.setLong(3, lcurrencyID);
			rs = ps.executeQuery();
			//Log.print("after sql");
			System.out.println("sql=" + strSQL);
			while (rs.next())
			{
				SpecialOperationInfo specialoperationinfo = new SpecialOperationInfo();
				specialoperationinfo.m_lID = rs.getLong("ID");
				Log.print("id=" + specialoperationinfo.m_lID);
				specialoperationinfo.m_strName = rs.getString("sName");
				
				specialoperationinfo.m_Ispayaccount=rs.getLong("IspayAccount");
				specialoperationinfo.m_Ispaybank=rs.getLong("IspayBank");
				specialoperationinfo.m_IspayGlEntry=rs.getLong("IspayGlEntry");
				specialoperationinfo.m_IsRecAccount=rs.getLong("IsRecAccount");
				specialoperationinfo.m_IsRecBank=rs.getLong("IsRecBank");
				specialoperationinfo.m_IsRecGlEntry=rs.getLong("IsRecGlEntry");
				
				specialoperationinfo.m_strContent = rs.getString("sContent");
				specialoperationinfo.m_lStatus = rs.getLong("nStatusID");
				specialoperationinfo.payRelation = rs.getLong("payRelation");
				specialoperationinfo.gatheringRelation = rs.getLong("gatheringRelation");
				specialoperationinfo.m_lPageCount = lPageCount;
				v.add(specialoperationinfo);
			}
			//�ر���Դ
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return (v.size() > 0 ? v : null);
	}
	
}
