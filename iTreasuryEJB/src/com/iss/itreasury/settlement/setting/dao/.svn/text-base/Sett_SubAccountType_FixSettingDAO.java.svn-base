package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeFixedDepositInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author xwhe 2008-04-30
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_SubAccountType_FixSettingDAO extends SettlementDAO 
{
   
	public Sett_SubAccountType_FixSettingDAO ()
	{
		super ("sett_subaccounttype_fixed",true) ;
		this.setUseMaxID();
	}
	/**
	 * 保存定期账户类型编码设置的下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置的下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>如果lID<0，则在SubAccountType_FixedDeposit表中新增一条记录
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2008, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountTypeID
	 * @param lFixedDepositMonthID
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveSubAccountTypeFixedDeposit(SubAccountTypeFixedDepositInfo info) {
		long lResult = -1;
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		String strTemp = "";
		long lRecordID = info.getM_lID();
		try
		{
			conn = Database.getConnection();
			int nIndex = 1;
			if (info.getM_lID() < 0) //增加记录
			{ 
				strTemp = "select * from sett_SubAccountType_Fixed where  NFIXEDDEPOSITMONTHID=? and nstatusid<>? and NACCOUNTTYPEID=? and nOfficeID=? and nCurrencyID = ?";
				if( info.getM_lClientID() >0 )
				{
					 strTemp += " and nClientID = ? ";
				}
				if( info.getAccountId() >0 )
				{
					 strTemp += " and nAccountID = ? ";
				}
				if( info.getDepositNo()!=null && info.getDepositNo().trim().length()>0 )
				{
					 strTemp += " and sDepositNO = ? ";
				}
				ps = conn.prepareStatement(strTemp);
				ps.setLong(nIndex++ , info.getM_lFixedDepositMonthID());
				ps.setLong(nIndex++ , 0);//Constant.RecordStatus.INVALID
				ps.setLong(nIndex++ , info.getM_lAccountTypeID());
				ps.setLong(nIndex++ , info.getOfficeID());
				ps.setLong(nIndex++ , info.getCurrencyID());
				if( info.getM_lClientID() >0 )
				{
					ps.setLong(nIndex++, info.getM_lClientID());
				}
				if( info.getAccountId() >0 )
				{
					ps.setLong(nIndex++, info.getAccountId());
				}
				if( info.getDepositNo()!=null && info.getDepositNo().trim().length()>0  )
				{
					ps.setString(nIndex++ ,info.getDepositNo());
				}
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				//得到最大的id
				strBuff.append(" SELECT NVL(MAX(id)+1,1) FROM sett_SubAccountType_Fixed ");
				ps = conn.prepareStatement(strBuff.toString());
				rs = ps.executeQuery();
				rs.next();
				lMaxID = rs.getLong(1);
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO sett_SubAccountType_Fixed(ID, nAccountTypeID, nFixedDepositMonthID ,sSubjectCode, nStatusID,sPayInterestSubject,sBookedInterestSubject,nOfficeID,nCurrencyID,nClientID,nAccountID,sDepositNO )");
				strBuff.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				Log.print(strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				int index = 1;
				ps.setLong(index++, lMaxID);
				ps.setLong(index++, info.getM_lAccountTypeID());
				ps.setLong(index++, info.getM_lFixedDepositMonthID());
				ps.setString(index++, info.getM_strSubjectCode());
				ps.setLong(index++, 1);//Constant.RecordStatus.VALID
	            ps.setString(index++, info.getM_strPayInterestSubjectCode());
	            ps.setString(index++,info.getM_strBookedInterestSubjectCode());
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++,info.getM_lClientID());
				ps.setLong(index++, info.getAccountId());
				ps.setString(index++, info.getDepositNo());
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{

				strTemp = "select * from sett_SubAccountType_Fixed where  NFIXEDDEPOSITMONTHID=? and nstatusid<>?  and id<>? and  NACCOUNTTYPEID=?  and nOfficeID=? and nCurrencyID = ? ";
				if( info.getM_lClientID() >0 )
				{
					 strTemp += " and nClientID = ? ";
				}
				//added by xwhe 2008-04-30
				if( info.getAccountId() >0 )
				{
					 strTemp += " and nAccountID = ? ";
				}
				if( info.getDepositNo()!=null && info.getDepositNo().trim().length()>0  )
				{
					 strTemp += " and SDEPOSITNO = ? ";
				}
				System.out.println("SQL---"+strTemp);
				ps = conn.prepareStatement(strTemp);
				int order = 1;
				ps.setLong(order++, info.getM_lFixedDepositMonthID());
				ps.setLong(order++, 0);//Constant.RecordStatus.INVALID
				ps.setLong(order++, info.getM_lID());
				ps.setLong(order++, info.getM_lAccountTypeID());
				ps.setLong(order++, info.getOfficeID());
				ps.setLong(order++, info.getCurrencyID());
				if( info.getM_lClientID() >0 )
				{
					ps.setLong(order++, info.getM_lClientID());
				}
				if( info.getAccountId() >0 )
				{
					ps.setLong(order++, info.getAccountId());
				}
				if( info.getDepositNo()!=null && info.getDepositNo().trim().length()>0  )
				{
					ps.setString(order++,  info.getDepositNo());
				}
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				strBuff.append("UPDATE sett_SubAccountType_Fixed SET nAccountTypeID=?,  nFixedDepositMonthID=?");
				strBuff.append(", sSubjectCode=?,sPayInterestSubject=?,sBookedInterestSubject=? ,nClientID = ? ,nAccountID=? ,sDepositNO =? WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
                int orderIndex = 1;
				ps.setLong(orderIndex++, info.getM_lAccountTypeID());
				ps.setLong(orderIndex++, info.getM_lFixedDepositMonthID());
				ps.setString(orderIndex++, info.getM_strSubjectCode());
	            ps.setString(orderIndex++, info.getM_strPayInterestSubjectCode());
	            ps.setString(orderIndex++, info.getM_strBookedInterestSubjectCode());
				ps.setLong(orderIndex++,info.getM_lClientID());
				ps.setLong(orderIndex++, info.getAccountId());
				ps.setString(orderIndex++, info.getDepositNo());
				ps.setLong(orderIndex++, info.getM_lID());
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			//throw new RemoteException(e.getMessage());
			//throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				//throw new RemoteException(e.getMessage());
				//throw e;
			}
		}
		return lResult;

	} 
	
	public Collection findAllSubAccountTypeFixedDepositByAccountType(long lAccountTypeID,long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		StringBuffer strBuff = new StringBuffer();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			con = Database.getConnection();
			//计算记录总数

			strBuff.append("SELECT COUNT(ID)  FROM sett_SubAccountType_Fixed WHERE nAccountTypeID=? AND nStatusID=?");
			strBuff.append("  AND  nAccountTypeID= "+lAccountTypeID+" and nOfficeID="+lOfficeID+" and nCurrencyID = "+lCurrencyID);

			ps = con.prepareStatement(strBuff.toString());
			ps.setLong(1, lAccountTypeID);
			ps.setLong(2, 1);//Constant.RecordStatus.VALID
			rs = ps.executeQuery();

			if (rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			//计算总页数
			lPageCount = lRecordCount / lPageLineCount;
			if ((lRecordCount % lPageLineCount) != 0)
			{
				lPageCount++;
			}

			//返回需求的结果集
			//分页显示，显示下一页
			lRowNumStart = (lPageNo - 1) * lPageLineCount + 1;
			lRowNumEnd = lRowNumStart + lPageLineCount - 1;

			strBuff = new StringBuffer();
			strBuff.append(" SELECT  * FROM ( SELECT A.*,ROWNUM NUM " );
			strBuff.append(" FROM ( SELECT s.ID, s.nFixedDepositMonthID, s.nAccountTypeID,s.sSubjectCode,s.sPayInterestSubject,s.sBookedInterestSubject,s.nClientID,s.nAccountID ,s.sdepositno , b.sAccountType from sett_SubAccountType_Fixed s,sett_AccountType b WHERE ") ;
			strBuff.append(" s.nAccountTypeID = b.ID and s.nStatusID=1  AND  s.nAccountTypeID= "+lAccountTypeID+" and s.nOfficeID="+lOfficeID+" and s.nCurrencyID = "+lCurrencyID);
			 
			switch ((int) lOrderParam)
			{
				case 1 :
					strBuff.append(" ORDER BY s.ID");
					break;
				case 2 :
					strBuff.append(" ORDER BY s.nFixedDepositMonthID");
					break;
				case 3 :
					strBuff.append(" ORDER BY s.nAccountTypeID");
					break;
				case 4 :
					strBuff.append(" ORDER BY s.ssubjectCode");
					break;
	            case 5 :
					strBuff.append(" ORDER BY s.sPayInterestSubject");
					break;
	             case 6 :
					strBuff.append(" ORDER BY s.sBookedInterestSubject");
					break;
			
			}
	 
			if (lDesc == 2)//Constant.PageControl.CODE_ASCORDESC_DESC
			{
				strBuff.append(" DESC ");
			}

			strBuff.append(" )  A) WHERE NUM BETWEEN  ");
			strBuff.append(lRowNumStart);
			strBuff.append("  AND  ");
			strBuff.append(lRowNumEnd);
	System.out.println("sql is :"+strBuff.toString());

			ps = con.prepareStatement(strBuff.toString());
			int nIndex = 1;
			
			rs = ps.executeQuery();
			while (rs.next())
			{
				SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
				subAccountTypeFixedDepositInfo.m_lID = rs.getLong(1);
				subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID = rs.getLong(2);
				subAccountTypeFixedDepositInfo.m_lAccountTypeID = rs.getLong(3);
				subAccountTypeFixedDepositInfo.m_strSubjectCode = rs.getString(4);
	            subAccountTypeFixedDepositInfo.m_strPayInterestSubjectCode = rs.getString(5);
	            subAccountTypeFixedDepositInfo.m_strBookedInterestSubjectCode = rs.getString(6);
				subAccountTypeFixedDepositInfo.m_lClientID = rs.getLong(7);
				subAccountTypeFixedDepositInfo.setAccountId(rs.getLong(8));
				subAccountTypeFixedDepositInfo.setDepositNo(rs.getString(9));
				subAccountTypeFixedDepositInfo.m_strAccountTypeName = rs.getString(10);
				subAccountTypeFixedDepositInfo.m_lPageCount = lPageCount;
			    System.out.println("m_lAccountTypeID=" + subAccountTypeFixedDepositInfo.m_lAccountTypeID);

				v.add(subAccountTypeFixedDepositInfo);
			}

			//关闭资源
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
			//throw new RemoteException(e.getMessage());
			//throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();

			}
			catch (Exception ex)
			{
				//throw new RemoteException(ex.getMessage());
				//throw ex;
			}
		}
		return (v.size() > 0 ? v : null);
	}


}
