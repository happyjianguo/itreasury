/*
 * Created on 2004-10-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.InterestRateInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.AccountGroupType;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_InterestRateDAO  extends SettlementDAO
{
	/**
	 * 
	 */
	public Sett_InterestRateDAO()
	{
		super("Sett_InterestRate",true);
		// TODO Auto-generated constructor stub
	}
	/**
	 *根据利率id和（或）生效日期 查询所有银行利率设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据利率id和（或）生效日期 查询所有银行利率设置</b>
	 * <ul>
	 * <li>操作数据库表InterestRate
	 * <li>返回Collection，包含类InterestRateInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 *
	 * @param lID                       利率id
	 * @param tsEffectiveDate    生效日期
	 * @param lPageLineCount   每页行数条件
	 * @param lPageNo             第几页条件
	 * @param lOrderParam       排序条件，根据此参数决定结果集排序条件
	 * @param lDesc                 升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllInterestRateByDate(long lID,long nOfficeID,long nCurrencyID, Timestamp tsEffectiveDate, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws SettlementException
	{
		Vector v = new Vector();
		StringBuffer strBuff = new StringBuffer();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			initDAO();
			transPS = prepareStatement(strBuff.toString());
			int intTmp = 2;
			//返回需求的结果集
			strBuff = new StringBuffer();
			strBuff.append(" SELECT  ID, sName,sFirstName, dtEffective, nInputUserID,  dtInput ,  fRate, nStatusID,NINPUTUSERID,nSerialNo ");
			strBuff.append(" FROM Sett_InterestRate WHERE nStatusID=? and nOfficeID=? and nCurrencyID=?");
			if (lID != -1)
			{
				strBuff.append(" AND ID=?");
			}
			if (tsEffectiveDate != null)
			{
				strBuff.append("  AND dtEffective=?");
			}
			switch ((int) lOrderParam)
			{
				case 1 :
					strBuff.append(" ORDER BY ID");
					break;
				case 2 :
					strBuff.append(" ORDER BY sName");
					break;
				case 3 :
					strBuff.append(" ORDER BY dtEffective");
					break;
				case 4 :
					strBuff.append(" ORDER BY NINPUTUSERID");
					break;
				case 5 :
					strBuff.append(" ORDER BY dtInput");
					break;
				case 6 :
					strBuff.append(" ORDER BY  fRate");
					break;
				case 7 :
					strBuff.append(" ORDER BY  sFirstName");
					break;
				case 8 :
					strBuff.append(" ORDER BY dtEffective desc,ID");
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strBuff.append(" DESC ");
			}

			transPS = prepareStatement(strBuff.toString());
			intTmp = 1;
			transPS.setLong(intTmp++, Constant.RecordStatus.VALID);
			transPS.setLong(intTmp++,nOfficeID);
			transPS.setLong(intTmp++,nCurrencyID);
			if (lID != -1)
			{
				transPS.setLong(intTmp++, lID);
			}
			if (tsEffectiveDate != null)
			{
				transPS.setTimestamp(intTmp++, tsEffectiveDate);
			}
			Log.print("strBuff=" + strBuff.toString());
			//  Log.print("come here");
			transRS = executeQuery();
			//   Log.print("come ssss");
			while (transRS.next())
			{
				InterestRateInfo interestRateInfo = new InterestRateInfo();
				interestRateInfo.m_lID = transRS.getLong(1);
				interestRateInfo.m_strName = transRS.getString(2);
				interestRateInfo.m_strFirstName = transRS.getString(3);
				interestRateInfo.m_tsEffective = transRS.getTimestamp(4);
				interestRateInfo.m_lInputUserID = transRS.getLong(5);
				interestRateInfo.m_tsInput = transRS.getTimestamp(6);
				interestRateInfo.m_dRate = transRS.getDouble(7);
				interestRateInfo.m_nStatusID = transRS.getInt(8);
				interestRateInfo.m_strInputUserName = NameRef.getUserNameByID(transRS.getInt(9));
				interestRateInfo.m_lSerialNo = transRS.getLong(10);
				interestRateInfo.m_lPageCount = lPageCount;
				//  Log.print("come herfffe");
				//  Log.print("PageCount="+lPageCount);
				//  Log.print("come 6666");
				v.add(interestRateInfo);
				//  Log.print("come 88888");
			}
			//关闭资源
			finalizeDAO();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return (v.size() > 0 ? v : null);
	}
	public double getInterestRate(java.sql.Timestamp tsEffective,long nCurrencyID,long nOfficeID,long nfixeddepositmonthID)  throws SettlementException{
		double lResult = 0.0;
		try{
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("select frate from sett_interestrate where dteffective <= ?");
			strSQL.append(" and nfixeddepositmonthid="+nfixeddepositmonthID);
			strSQL.append(" and nCurrencyID="+nCurrencyID);
			strSQL.append(" and nOfficeID="+nOfficeID);
			strSQL.append(" and nStatusID=1 ");
			strSQL.append(" order by dteffective DESC");
			transPS = prepareStatement(strSQL.toString());
			transPS.setTimestamp(1,tsEffective );//
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				//相同名称和生效时间的记录已经存在
				lResult = transRS.getDouble(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
		}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new SettlementException();
	}
	finally
	{
		try
		{
			finalizeDAO();
		}
		catch (Exception ex)
		{
			throw new SettlementException();
		}
	}
		return lResult;
	}
	/**
	 * 增加银行利率，如果lID>0，使用传入的lID，否则新增一个lID
	 * @param lID 标识
	 * @param strName 名称
	 * @param sFirstName 一级名称
	 * @param tsEffective 生效日
	 * @param lInputUserID 录入人
	 * @param tsInput 录入日期
	 * @param dRate 利率
	 * @return -1名称重复 -2 有相同的生效日期
	 */
	public long insertInterestRate(long lID, String strName,String sFirstName, java.sql.Timestamp tsEffective, long lInputUserID, java.sql.Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAcouttypeID,long nFixeddepositmonthID) throws SettlementException 
	{
		long lResult = 1;
		long lMaxID = -1;
		long lMaxSerialNo = -1;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			Log.print("lID=" + lID);
			if (lID < 0) //增加记录
			{
			//	String strSQL = "select * from Sett_InterestRate where sName=?  and nstatusid<>? and nofficeid = ?";
				String strSQL = "select * from Sett_InterestRate where nstatusid<>? and nofficeid = ? and naccounttypeid=? and nFixeddepositmonthID=? and dtEffective=? and nCurrencyID =?";
				
				transPS = prepareStatement(strSQL);  
			//	transPS.setString(1, strName);
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, nOfficeID );//
				transPS.setLong(3, nAcouttypeID );//
				transPS.setLong(4, nFixeddepositmonthID );//
				transPS.setTimestamp(5,tsEffective );//
				transPS.setLong(6, nCurrencyID);//多币种
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					//相同名称和生效时间的记录已经存在
					lResult = -1;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				if (lResult > 0) //数据合法
				{
					strSQL = " select nvl(max(ID)+1,1) from Sett_InterestRate ";
					transPS = prepareStatement(strSQL);
					transRS = executeQuery();
					if (transRS != null && transRS.next())
					{
						lMaxID = transRS.getLong(1);
					}
					transRS.close();
					transRS = null;
					transPS.close();
					transPS = null;
				}
			}
			else
			{
				//判断名称是否与已有重复
				String strSQL = "select * from Sett_InterestRate where id=? and nstatusid<>? and dtEffective=? and nOfficeID = ? and nCurrencyID =? ";
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, lID);
				transPS.setLong(2, Constant.RecordStatus.INVALID);
				transPS.setTimestamp(3, tsEffective);
				transPS.setLong(4, nOfficeID );//多办事处
				transPS.setLong(5, nCurrencyID );//多币种
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					//有相同的生效日
					lResult = -2;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//获得名称
				strSQL = "select sName from Sett_InterestRate where id=" + lID;
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				if (transRS.next())
				{
					strName = transRS.getString("sName");
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				lMaxID = lID;
			}
			if (lResult > 0)
			{
				//得到最大的max(nSerialNo)+1
				String strSQL = " select nvl(max(nSerialNo)+1,1) from Sett_InterestRate ";
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxSerialNo = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_InterestRate(ID ,sName,sFirstName,dtEffective,nInputUserID ,dtInput,fRate,nStatusID,nSerialNo,nCurrencyID,nOfficeID,naccounttypeid,nFixeddepositmonthID)");
				strBuff.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				transPS = prepareStatement(strBuff.toString());
				transPS.setLong(1, lMaxID);
				transPS.setString(2, strName);
				transPS.setString(3,sFirstName);
				transPS.setTimestamp(4, tsEffective);
				transPS.setLong(5, lInputUserID);
				transPS.setTimestamp(6, tsInput);
				transPS.setDouble(7, dRate);
				transPS.setLong(8, Constant.RecordStatus.VALID);
				transPS.setLong(9, lMaxSerialNo);
				transPS.setLong(10,nCurrencyID);
				transPS.setLong(11,nOfficeID);
				transPS.setLong(12,nAcouttypeID); 
				transPS.setLong(13,nFixeddepositmonthID);
				lResult = transPS.executeUpdate();
			}
			finalizeDAO();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return lResult;
	}
	/**
	 * 根据标识查询银行利率设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询银行利率设置</b>
	 * <ul>
	 * <li>操作数据库表InterestRate
	 * <li>返回类InterestRateInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return InterestRateInfo
	 * @exception Exception
	 */
	public InterestRateInfo findInterestRateByID(long lID) throws SettlementException
	{
		InterestRateInfo interestRateInfo = new InterestRateInfo();
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			strBuff.append("SELECT ID,sName,dtEffective,nInputUserID,dtInput,fRate, nStatusID,nSerialNo,sFirstName,naccounttypeid,nfixeddepositmonthid FROM Sett_InterestRate ");
			strBuff.append(" WHERE nSerialNo=" + lID);
			transPS = prepareStatement(strBuff.toString());
			Log.print(strBuff.toString());
			transRS = executeQuery();
			if (transRS.next())
			{
				interestRateInfo = new InterestRateInfo();
				interestRateInfo.m_lID = transRS.getLong(1); 
				interestRateInfo.m_strName = transRS.getString(2);
				interestRateInfo.m_tsEffective = transRS.getTimestamp(3);
				interestRateInfo.m_lInputUserID = transRS.getLong(4);
				interestRateInfo.m_tsInput = transRS.getTimestamp(5);
				interestRateInfo.m_dRate = transRS.getDouble(6);
				interestRateInfo.m_lSerialNo = transRS.getLong("nSerialNo");
				interestRateInfo.m_strFirstName = transRS.getString("sFirstName");
				interestRateInfo.m_nAccountTypeID = transRS.getLong("naccounttypeid");
				interestRateInfo.m_nFixeddepositmonthID = transRS.getLong("nfixeddepositmonthid");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return interestRateInfo;
	}
	/**
	 * 保存银行利率设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存银行利率设置</b>
	 * <ul>
	 * <li>操作数据库表InterestRate
	 * <li>如果lID<0，则在InterestRate表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param strName
	 * @param tsEffective
	 * @param lInputUserID
	 * @param tsInput
	 * @param dRate
	 * @return void//0，相同名称和生效时间的记录已经存在;
	 *　－１，输入的利率名称与已有的重复；
	 *
	 * @exception Exception
	 */
	public long saveInterestRate(long lSerialNo, long lID, String strName,String sFirstName, Timestamp tsEffective, long lInputUserID, Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException
	{
		long lResult = 1;
		long lMaxID = -1;
		long lMaxSerialNo = -1;
		int intTmp = 1;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			Log.print("lID=" + lID);
			if (lID < 0) //增加记录
			{
				String strSQL = "select * from Sett_InterestRate where sName=? and dtEffective=? and nstatusid<>?";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strName);
				transPS.setTimestamp(2, tsEffective);
				transPS.setLong(3, Constant.RecordStatus.INVALID);
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					//相同名称和生效时间的记录已经存在
					return 0;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//得到ID：两种情况，一种是名称已有，此时取相同的ID；另一种是名称不同，此时得到一个max(ID)+1
				strSQL = " select nvl(max(ID)+1,1) from Sett_InterestRate ";
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxID = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				strSQL = " select distinct ID from Sett_InterestRate where sName=? and nstatusid<>?";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strName);
				transPS.setLong(2, Constant.RecordStatus.INVALID);
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxID = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//得到最大的max(nSerialNo)+1
				strSQL = " select nvl(max(nSerialNo)+1,1) from Sett_InterestRate ";
				transPS = prepareStatement(strSQL);
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxSerialNo = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_InterestRate(ID ,sName,sFirstName,dtEffective,nInputUserID ,dtInput,fRate,nStatusID,nSerialNo,nCurrencyID,nOfficeID)");
				strBuff.append("VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				transPS = prepareStatement(strBuff.toString());
				intTmp = 1;
				transPS.setLong(intTmp++, lMaxID);
				transPS.setString(intTmp++, strName);
				transPS.setString(intTmp++, sFirstName);
				transPS.setTimestamp(intTmp++, tsEffective);
				transPS.setLong(intTmp++, lInputUserID);
				transPS.setTimestamp(intTmp++, tsInput);
				transPS.setDouble(intTmp++, dRate);
				transPS.setLong(intTmp++, Constant.RecordStatus.VALID);
				transPS.setLong(intTmp++, lMaxSerialNo);
				transPS.setLong(intTmp++,nCurrencyID);
				transPS.setLong(intTmp++,nOfficeID);
				lResult = executeUpdate();
				lResult = 1;
			}
			else //修改记录
				{
				//判断名称是否与已有重复
				String strSQL = "select * from Sett_InterestRate  where nstatusid<>? and nofficeid = ? and naccounttypeid=? and nFixeddepositmonthID=? and dtEffective=? and nCurrencyID =? and id <>? ";
				transPS = prepareStatement(strSQL);  
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, nOfficeID );//
				transPS.setLong(3, nAccouttypeID);//
				transPS.setLong(4, nFixeddepositmonthID );//
				transPS.setTimestamp(5,tsEffective );//
				transPS.setLong(6, nCurrencyID);//多币种
				transPS.setLong(7, lID);
				
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					//相同名称和生效时间的记录已经存在
					lResult = -1;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				
				if(lResult> 0)
				{	
				//更新名称
				strSQL = "update Sett_InterestRate set sName=? where id=?";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strName);
				transPS.setLong(2, lID);
				transPS.executeUpdate();
				transPS.close();
				transPS = null;
				//更新记录
				strSQL = "update Sett_InterestRate set sName=?,sFirstName=?,dtEffective=?,nInputUserID=?,dtInput=?,fRate=?,nAccounttypeID=?,nFixeddepositmonthID=? where nSerialNo=? and nCurrencyID=? and nOfficeID=?";
				transPS = prepareStatement(strSQL);
				intTmp = 1;
				transPS.setString(intTmp++, strName);
				transPS.setString(intTmp++,sFirstName);
				transPS.setTimestamp(intTmp++, tsEffective);
				transPS.setLong(intTmp++, lInputUserID);
				transPS.setTimestamp(intTmp++, tsInput);
				transPS.setDouble(intTmp++, dRate);
				transPS.setLong(intTmp++,nAccouttypeID);
				transPS.setLong(intTmp++,nFixeddepositmonthID);
				transPS.setLong(intTmp++, lSerialNo);
				transPS.setLong(intTmp++, nCurrencyID);
				transPS.setLong(intTmp++,nOfficeID);
				
				transPS.executeUpdate();
				lResult = 1;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return lResult;
	}
	/**
	 * 删除银行利率设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除银行利率设置</b>
	 * <ul>
	 * <li>操作数据库表InterestRate
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public long deleteInterestRate(long lSerialNo) throws SettlementException
	{
		long lResult = -1;
		String strSQL = "";
		long lID = -1;
		try
		{
			initDAO();
			//得到利率的ID
			strSQL = "select distinct ID from Sett_InterestRate wHERE nSerialNo=?";
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, lSerialNo);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lID = transRS.getLong("ID");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			
			//判断该利率是否被使用过
			long lCount = -1;
			strSQL = " select count(*) Count from sett_InterestRatePlanItem pi,sett_interestrateplan p ";
			strSQL += " where pi.nInterestRatePlanID=p.ID and p.nStatusID="+Constant.RecordStatus.VALID+" and pi.nInterestRateID="+lID;
			transPS = prepareStatement(strSQL);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lCount = transRS.getLong("Count");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
					
			if (lCount == 0)
			{
				//删除
				strSQL = "UPDATE Sett_InterestRate SET nStatusID=?  WHERE nSerialNo=?";
				transPS = transConn.prepareCall(strSQL);
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, lSerialNo);
				lResult = executeUpdate();
				transPS.close();
				transPS = null;
			}
			else
			{
				lResult = -100;//已被使用
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	/**
	 * 查询所有银行利率设置(利率计划设置中用到)
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllInterestRateSecond(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws SettlementException
	{
		Vector v = new Vector();
		StringBuffer strBuff = new StringBuffer();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			initDAO();
			strBuff = new StringBuffer();
			//strBuff.append("select ID,sName,dtEffective,nInputUserID,dtInput,fRate,nStatusID from(" + "SELECT * FROM Sett_InterestRate a WHERE a.dtEffective<=? and a.nStatusID=? " + " union " + "SELECT * FROM Sett_InterestRate a WHERE a.dtEffective>? and a.nStatusID=?  and a.sName not in(select distinct sName from Sett_InterestRate a where a.dtEffective<=? and a.nStatusID=? ) " + ") a where a.nCurrencyID=? order by a.sName,a.dtEffective desc");
			strBuff.append("select ID, naccounttypeid,sName,sFirstName, DTEFFECTIVE,dtInput ,fRate, nStatusID,NINPUTUSERID,nSerialNo from Sett_INTERESTRATE,(select id as lastid,max(DTEFFECTIVE) as lastDTEFFECTIVE from Sett_INTERESTRATE group by id) b where Sett_INTERESTRATE.id=b.lastid and Sett_INTERESTRATE.DTEFFECTIVE=b.lastDTEFFECTIVE and nStatusID=? and nCurrencyID=? and nOfficeID=? and naccounttypeid in ");
			strBuff.append("("+SETTConstant.SettRateType.CURRENT+","+SETTConstant.SettRateType.BAK+","+SETTConstant.SettRateType.RESERVE+","+SETTConstant.SettRateType.LENDING+")");
			strBuff.append(" order by sName");
			transPS = prepareStatement(strBuff.toString());
			int intTmp = 1;
			//transPS.setTimestamp(intTmp++, DataFormat.getDateTime(Env.getSystemDateString(lOfficeID,lCurrencyID)));
			//transPS.setLong(intTmp++, Notes.CODE_RECORD_STATUS_VALID);
			//transPS.setTimestamp(intTmp++, DataFormat.getDateTime(Env.getSystemDateString(lOfficeID,lCurrencyID)));
			//transPS.setLong(intTmp++, Notes.CODE_RECORD_STATUS_VALID);
			//transPS.setTimestamp(intTmp++, DataFormat.getDateTime(Env.getSystemDateString(lOfficeID,lCurrencyID)));
			transPS.setLong(intTmp++, Constant.RecordStatus.VALID);
			transPS.setLong(intTmp++, lCurrencyID);
			transPS.setLong(intTmp++, lOfficeID);
			//transPS.setLong(intTmp++, AccountGroupType.CURRENT);
			Log.print("strBuff=" + strBuff.toString());
			transRS = executeQuery();
			Log.print("1");
			String strTmpName = "";
			while (transRS.next())
			{
				if (transRS.getString("sName") != null && !strTmpName.equals(transRS.getString("sName")))
				{
					strTmpName = transRS.getString("sName");
					InterestRateInfo interestRateInfo = new InterestRateInfo();
					interestRateInfo.m_lID = transRS.getLong("ID");
					interestRateInfo.m_nAccountTypeID=transRS.getLong("naccounttypeid");
					interestRateInfo.m_strName = transRS.getString("sName");
					interestRateInfo.m_strFirstName = transRS.getString("sFirstName")==null?"":transRS.getString("sFirstName");
					interestRateInfo.m_tsEffective = transRS.getTimestamp("dtEffective");
					interestRateInfo.m_lInputUserID = transRS.getLong("nInputUserID");
					interestRateInfo.m_tsInput = transRS.getTimestamp("dtInput");
					interestRateInfo.m_dRate = transRS.getDouble("fRate");
					interestRateInfo.m_nStatusID = transRS.getInt("nStatusID");
					v.add(interestRateInfo);
				}
			}
			//关闭资源
			Log.print("关闭资源");
			initDAO();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		Log.print("返回结果");
		return (v.size() > 0 ? v : null);
	}
	public double findLastInterestRate(InterestRateInfo info) throws SettlementException
	{
		StringBuffer strBuff = new StringBuffer();
		double returnRate = 0;
		try
		{
			initDAO();
			strBuff = new StringBuffer();
			strBuff.append(" select * from Sett_InterestRate where 1=1 ");
			strBuff.append(" and nstatusid <>0  ");
			strBuff.append(" and nofficeid =  "+info.getnOfficeid());
			strBuff.append(" and ncurrencyid =  "+info.getnCurrencyid());
			if(info.getDtEffective()!=null)
			{
				strBuff.append(" and dteffective <=  to_date('"+DataFormat.formatDate(info.getDtEffective())+"','yyyy-mm-dd')");
			}
			if(info.getnAccounttypeid()>0)
			{
				strBuff.append(" and naccounttypeid =  "+info.getnAccounttypeid());
			}
			if(info.getnFixeddepositmonthid()>0)
			{
				strBuff.append(" and nfixeddepositmonthid =  "+info.getnFixeddepositmonthid());
			}
			strBuff.append(" order by dteffective desc" );
			
			transPS = prepareStatement(strBuff.toString());
			transRS = executeQuery();
			System.out.print(strBuff.toString());
			while (transRS.next())//最新的利率为最上的一条，只取第一个
			{
				returnRate = transRS.getDouble("frate");
				break;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		Log.print("返回结果");
		return returnRate;
	}
	
	public Collection findInterestRate(InterestRateInfo info) throws SettlementException
	{
		StringBuffer strBuff = new StringBuffer();
		Collection coll = null;
		try
		{
			initDAO();
			strBuff = new StringBuffer();
			strBuff.append(" select a.*,b.sname username,decode(naccounttypeid,2,nfixeddepositmonthid||'个月',3,(nfixeddepositmonthid-10000)||'天','') DepositMonthDesc  from Sett_InterestRate a,userinfo b  where 1=1 ");
			strBuff.append(" and a.nstatusid <>0  ");
			strBuff.append(" and a.ninputuserid = b.id  ");
			strBuff.append(" and a.nofficeid =  "+info.getnOfficeid());
			strBuff.append(" and a.ncurrencyid =  "+info.getnCurrencyid());
			if(!info.getsName().equals(""))
			{
				strBuff.append(" and a.sname  like  '%"+info.getsName()+"%'");
			}
			if(info.getDtEffective()!=null)
			{
				strBuff.append(" and a.dteffective <=  to_date('"+DataFormat.formatDate(info.getDtEffective())+"','yyyy-mm-dd')");
			}
			if(info.getnAccounttypeid()>0)
			{
				strBuff.append(" and a.naccounttypeid =  "+info.getnAccounttypeid());
			}
			if(info.getnFixeddepositmonthid()>0)
			{
				strBuff.append(" and a.nfixeddepositmonthid =  "+info.getnFixeddepositmonthid());
			}
			strBuff.append(" order by a.naccounttypeid, a.dteffective desc" );
			Log.print("SQL"+strBuff.toString());
			transPS = prepareStatement(strBuff.toString());
			transRS = executeQuery();
			coll = this.getDataEntitiesFromResultSet(InterestRateInfo.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		Log.print("返回结果");
		return coll;
	}
	
	
	/**
	 * 增加银行利率，如果lID>0，使用传入的lID，否则新增一个lID
	 * @param lID 标识
	 * @param strName 名称
	 * @param sFirstName 一级名称
	 * @param tsEffective 生效日
	 * @param lInputUserID 录入人
	 * @param tsInput 录入日期
	 * @param dRate 利率
	 * @return -1名称重复 -2 有相同的生效日期
	 * @throws SQLException 
	 */
	public long insertInterestRateDuplicate(long lID, String strName,String sFirstName, java.sql.Timestamp tsEffective, long lInputUserID, java.sql.Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAcouttypeID,long nFixeddepositmonthID) throws SettlementException, SQLException 
	{
		long lResult = 1;
		long lMaxID = -1;
		long lMaxSerialNo = -1;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			transConn.setAutoCommit(false);
			Log.print("lID=" + lID);
			if (lID < 0) //增加记录
			{
			//	String strSQL = "select * from Sett_InterestRate where sName=?  and nstatusid<>? and nofficeid = ?";
				String strSQL = "select * from SETT_INTERESTRATEDUPLICATE  where nstatusid<>? and nofficeid = ? and naccounttypeid=? and nFixeddepositmonthID=? and dtEffective=? and nCurrencyID =?";
				
				transPS = prepareStatement(strSQL);  
			//	transPS.setString(1, strName);
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, nOfficeID );//
				transPS.setLong(3, nAcouttypeID );//
				transPS.setLong(4, nFixeddepositmonthID );//
				transPS.setTimestamp(5,tsEffective );//
				transPS.setLong(6, nCurrencyID);//多币种
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					//相同名称和生效时间的记录已经存在
					lResult = -1;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				if (lResult > 0) //数据合法
				{
					strSQL = " select nvl(max(ID)+1,1) from SETT_INTERESTRATEDUPLICATE ";
					transPS = prepareStatement(strSQL);
					transRS = executeQuery();
					if (transRS != null && transRS.next())
					{
						lMaxID = transRS.getLong(1);
					}
					transRS.close();
					transRS = null;
					transPS.close();
					transPS = null;
				}
			}
			else
			{
				//判断名称是否与已有重复
				String strSQL = "select * from SETT_INTERESTRATEDUPLICATE where id=? and nstatusid<>? and dtEffective=? and nOfficeID = ? and nCurrencyID =? ";
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, lID);
				transPS.setLong(2, Constant.RecordStatus.INVALID);
				transPS.setTimestamp(3, tsEffective);
				transPS.setLong(4, nOfficeID );//多办事处
				transPS.setLong(5, nCurrencyID );//多币种
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					//有相同的生效日
					lResult = -2;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//获得名称
				strSQL = "select sName from SETT_INTERESTRATEDUPLICATE where id=" + lID;
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				if (transRS.next())
				{
					strName = transRS.getString("sName");
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				lMaxID = lID;
			}
			if (lResult > 0)
			{
				//得到最大的max(nSerialNo)+1
				String strSQL = " select nvl(max(nSerialNo)+1,1) from SETT_INTERESTRATEDUPLICATE ";
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxSerialNo = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO SETT_INTERESTRATEDUPLICATE(ID ,sName,sFirstName,dtEffective,nInputUserID ,dtInput,fRate,nStatusID,nSerialNo,nCurrencyID,nOfficeID,naccounttypeid,nFixeddepositmonthID)");
				strBuff.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				transPS = prepareStatement(strBuff.toString());
				transPS.setLong(1, lMaxID);
				transPS.setString(2, strName);
				transPS.setString(3,sFirstName);
				transPS.setTimestamp(4, tsEffective);
				transPS.setLong(5, lInputUserID);
				transPS.setTimestamp(6, tsInput);
				transPS.setDouble(7, dRate);
				transPS.setLong(8, Constant.RecordStatus.VALID);
				transPS.setLong(9, lMaxSerialNo);
				transPS.setLong(10,nCurrencyID);
				transPS.setLong(11,nOfficeID);
				transPS.setLong(12,nAcouttypeID); 
				transPS.setLong(13,nFixeddepositmonthID);
				lResult = transPS.executeUpdate();
			}
			transConn.commit();
		}
		catch (Exception e)
		{
			transConn.rollback();
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	public Collection findInterestRateDuplicate(InterestRateInfo info) throws SettlementException
	{
		StringBuffer strBuff = new StringBuffer();
		Collection coll = null;
		try
		{
			initDAO();
			strBuff = new StringBuffer();
			strBuff.append(" select a.*,b.sname username,decode(naccounttypeid,2,nfixeddepositmonthid||'个月',3,(nfixeddepositmonthid-10000)||'天','') DepositMonthDesc  from SETT_INTERESTRATEDUPLICATE a,userinfo b  where 1=1 ");
			strBuff.append(" and a.nstatusid <>0  ");
			strBuff.append(" and a.ninputuserid = b.id  ");
			strBuff.append(" and a.nofficeid =  "+info.getnOfficeid());
			strBuff.append(" and a.ncurrencyid =  "+info.getnCurrencyid());
			strBuff.append(" and a.NPRIMNO is  null ");
			if(!info.getsName().equals(""))
			{
				strBuff.append(" and a.sname  like  '%"+info.getsName()+"%'");
			}
			if(info.getDtEffective()!=null)
			{
				strBuff.append(" and a.dteffective <=  to_date('"+DataFormat.formatDate(info.getDtEffective())+"','yyyy-mm-dd')");
			}
			if(info.getnAccounttypeid()>0)
			{
				strBuff.append(" and a.naccounttypeid =  "+info.getnAccounttypeid());
			}
			if(info.getnFixeddepositmonthid()>0)
			{
				strBuff.append(" and a.nfixeddepositmonthid =  "+info.getnFixeddepositmonthid());
			}
			strBuff.append(" order by a.naccounttypeid, a.dteffective desc" );
			System.out.println(strBuff.toString());
			Log.print("SQL"+strBuff.toString());
			transPS = prepareStatement(strBuff.toString());
			transRS = executeQuery();
			coll = this.getDataEntitiesFromResultSet(InterestRateInfo.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		Log.print("返回结果");
		return coll;
	}
	
	/**
	 * 根据标识查询银行利率设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询银行利率设置</b>
	 * <ul>
	 * <li>操作数据库表InterestRate
	 * <li>返回类InterestRateInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return InterestRateInfo
	 * @exception Exception
	 */
	public InterestRateInfo findInterestRateDuplicateByID(long lID) throws SettlementException
	{
		InterestRateInfo interestRateInfo = new InterestRateInfo();
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			strBuff.append("SELECT ID,sName,dtEffective,nInputUserID,dtInput,fRate, nStatusID,nSerialNo,sFirstName,naccounttypeid,nfixeddepositmonthid,NPRIMNO,NCURRENCYID,NOFFICEID FROM SETT_INTERESTRATEDUPLICATE ");
			strBuff.append(" WHERE nSerialNo=" + lID);
			strBuff.append(" and nStatusID =" + Constant.RecordStatus.VALID);
			transPS = prepareStatement(strBuff.toString());
			Log.print(strBuff.toString());
			transRS = executeQuery();
			if (transRS.next())
			{
				interestRateInfo = new InterestRateInfo();
				interestRateInfo.m_lID = transRS.getLong(1); 
				interestRateInfo.m_strName = transRS.getString(2);
				interestRateInfo.m_tsEffective = transRS.getTimestamp(3);
				interestRateInfo.m_lInputUserID = transRS.getLong(4);
				interestRateInfo.m_tsInput = transRS.getTimestamp(5);
				interestRateInfo.m_dRate = transRS.getDouble(6);
				interestRateInfo.m_lSerialNo = transRS.getLong("nSerialNo");
				interestRateInfo.m_strFirstName = transRS.getString("sFirstName");
				interestRateInfo.m_nAccountTypeID = transRS.getLong("naccounttypeid");
				interestRateInfo.m_nFixeddepositmonthID = transRS.getLong("nfixeddepositmonthid");
				interestRateInfo.m_nPrimno = transRS.getLong("NPRIMNO");
				interestRateInfo.m_lOfficeID = transRS.getLong("NOFFICEID");
				interestRateInfo.setnCurrencyid(transRS.getLong("NCURRENCYID"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return interestRateInfo;
	}
	
	/**
	 * 保存银行利率设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存银行利率设置</b>
	 * <ul>
	 * <li>操作数据库表InterestRate
	 * <li>如果lID<0，则在InterestRate表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param strName
	 * @param tsEffective
	 * @param lInputUserID
	 * @param tsInput
	 * @param dRate
	 * @return void//0，相同名称和生效时间的记录已经存在;
	 *　－１，输入的利率名称与已有的重复；
	 *
	 * @exception Exception
	 */
	public long saveInterestRateDuplicate(long lSerialNo, long lID, String strName,String sFirstName, Timestamp tsEffective, long lInputUserID, Timestamp tsInput, double dRate,long nCurrencyID,long nOfficeID,long nAccouttypeID,long nFixeddepositmonthID) throws SettlementException
	{
		long lResult = 1;
		long lMaxID = -1;
		long lMaxSerialNo = -1;
		int intTmp = 1;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			Log.print("lID=" + lID);
			if (lID < 0) //增加记录
			{
				String strSQL = "select * from SETT_INTERESTRATEDUPLICATE where sName=? and dtEffective=? and nstatusid<>?";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strName);
				transPS.setTimestamp(2, tsEffective);
				transPS.setLong(3, Constant.RecordStatus.INVALID);
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					//相同名称和生效时间的记录已经存在
					return 0;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//得到ID：两种情况，一种是名称已有，此时取相同的ID；另一种是名称不同，此时得到一个max(ID)+1
				strSQL = " select nvl(max(ID)+1,1) from SETT_INTERESTRATEDUPLICATE ";
				transPS = prepareStatement(strSQL);
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxID = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				strSQL = " select distinct ID from SETT_INTERESTRATEDUPLICATE where sName=? and nstatusid<>?";
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strName);
				transPS.setLong(2, Constant.RecordStatus.INVALID);
				transRS = executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxID = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//得到最大的max(nSerialNo)+1
				strSQL = " select nvl(max(nSerialNo)+1,1) from SETT_INTERESTRATEDUPLICATE ";
				transPS = prepareStatement(strSQL);
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					lMaxSerialNo = transRS.getLong(1);
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO SETT_INTERESTRATEDUPLICATE(ID ,sName,sFirstName,dtEffective,nInputUserID ,dtInput,fRate,nStatusID,nSerialNo,nCurrencyID,nOfficeID)");
				strBuff.append("VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				transPS = prepareStatement(strBuff.toString());
				intTmp = 1;
				transPS.setLong(intTmp++, lMaxID);
				transPS.setString(intTmp++, strName);
				transPS.setString(intTmp++, sFirstName);
				transPS.setTimestamp(intTmp++, tsEffective);
				transPS.setLong(intTmp++, lInputUserID);
				transPS.setTimestamp(intTmp++, tsInput);
				transPS.setDouble(intTmp++, dRate);
				transPS.setLong(intTmp++, Constant.RecordStatus.VALID);
				transPS.setLong(intTmp++, lMaxSerialNo);
				transPS.setLong(intTmp++,nCurrencyID);
				transPS.setLong(intTmp++,nOfficeID);
				lResult = executeUpdate();
				lResult = 1;
			}
			else //修改记录
				{
				//判断名称是否与已有重复
				String strSQL = "select * from SETT_INTERESTRATEDUPLICATE  where nstatusid<>? and nofficeid = ? and naccounttypeid=? and nFixeddepositmonthID=? and dtEffective=? and nCurrencyID =? and id <>? ";
				transPS = prepareStatement(strSQL);  
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, nOfficeID );//
				transPS.setLong(3, nAccouttypeID);//
				transPS.setLong(4, nFixeddepositmonthID );//
				transPS.setTimestamp(5,tsEffective );//
				transPS.setLong(6, nCurrencyID);//多币种
				transPS.setLong(7, lID);
				
				transRS = transPS.executeQuery();
				if (transRS != null && transRS.next())
				{
					//相同名称和生效时间的记录已经存在
					lResult = -1;
				}
				transRS.close();
				transRS = null;
				transPS.close();
				transPS = null;

				if(lResult> 0)
				{	
					//更新名称
					strSQL = "update SETT_INTERESTRATEDUPLICATE set sName=? where id=?";
					transPS = prepareStatement(strSQL);
					transPS.setString(1, strName);
					transPS.setLong(2, lID);
					transPS.executeUpdate();
					transPS.close();
					transPS = null;
					//更新记录
					strSQL = "update SETT_INTERESTRATEDUPLICATE set sName=?,sFirstName=?,dtEffective=?,nInputUserID=?,dtInput=?,fRate=?,nAccounttypeID=?,nFixeddepositmonthID=? where nSerialNo=? and nCurrencyID=? and nOfficeID=?";
					transPS = prepareStatement(strSQL);
					intTmp = 1;
					transPS.setString(intTmp++, strName);
					transPS.setString(intTmp++,sFirstName);
					transPS.setTimestamp(intTmp++, tsEffective);
					transPS.setLong(intTmp++, lInputUserID);
					transPS.setTimestamp(intTmp++, tsInput);
					transPS.setDouble(intTmp++, dRate);
					transPS.setLong(intTmp++,nAccouttypeID);
					transPS.setLong(intTmp++,nFixeddepositmonthID);
					transPS.setLong(intTmp++, lSerialNo);
					transPS.setLong(intTmp++, nCurrencyID);
					transPS.setLong(intTmp++,nOfficeID);
					
					transPS.executeUpdate();
					lResult = 1;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	
	
	public long deleteInterestRateDuplicate(long lSerialNo) throws SettlementException, SQLException
	{
		long lResult = -1;
		String strSQL = "";
		long lID = -1;
		try
		{
			initDAO();
			transConn.setAutoCommit(false);
			//得到利率的复核表ID号
			strSQL = "select distinct NPRIMNO from sett_interestrateduplicate wHERE nSerialNo=?";
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, lSerialNo);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lID = transRS.getLong("NPRIMNO");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			
			//判断该利率是否是复核状态
			long lCount = -1;
			strSQL = " select count(*) Count from sett_interestrate where ID = ? and  NSTATUSID =?";
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, lID);
			transPS.setLong(2, Constant.RecordStatus.VALID);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lCount = transRS.getLong("Count");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
					
			if (lCount == 0)
			{
				//删除
				strSQL = "UPDATE sett_interestrateduplicate SET nStatusID=?  WHERE nSerialNo=?";
				transPS = transConn.prepareCall(strSQL);
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, lSerialNo);
				lResult = executeUpdate();
				transPS.close();
				transPS = null;
			}
			else
			{
				lResult = -100;//已被使用
			}
			transConn.commit();
		}
		catch (Exception e)
		{
			transConn.rollback();
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	public InterestRateInfo matchInterestRate(long lID, String strName,String sFirstName, java.sql.Timestamp tsEffective, long lInputUserID,  double dRate,long nCurrencyID,long nOfficeID,long nAcouttypeID,long nFixeddepositmonthID) throws SettlementException, SQLException 
	{
		StringBuffer strBuff = new StringBuffer();
		InterestRateInfo interestRateInfo = null;
		try
		{
			initDAO();
			transConn.setAutoCommit(false);
			Log.print("lID=" + lID);
			strBuff.append("select * from  sett_interestrateduplicate");
			strBuff.append(" where SNAME = ? ");
			strBuff.append(" and DTEFFECTIVE = ? and NINPUTUSERID <> ? ");
			strBuff.append(" and FRATE=? and NSTATUSID = ?");
			strBuff.append(" and NCURRENCYID = ? and NOFFICEID = ?");
			strBuff.append(" and NACCOUNTTYPEID = ? and NPRIMNO is null");
			if(nFixeddepositmonthID>0){
				strBuff.append(" and NFIXEDDEPOSITMONTHID = ?");
			}
			transPS = prepareStatement(strBuff.toString());
			transPS.setString(1, strName);
			//transPS.setString(2,sFirstName);
			transPS.setTimestamp(2, tsEffective);
			transPS.setLong(3, lInputUserID);
			transPS.setDouble(4, dRate);
			transPS.setLong(5, Constant.RecordStatus.VALID);
			transPS.setLong(6,nCurrencyID);
			transPS.setLong(7,nOfficeID);
			transPS.setLong(8,nAcouttypeID);
			if(nFixeddepositmonthID>0){
				transPS.setLong(9,nFixeddepositmonthID);
			}
			transRS = transPS.executeQuery();
			if(transRS.next()){
				interestRateInfo = new InterestRateInfo();
				interestRateInfo.m_lID = transRS.getLong("ID"); 
				interestRateInfo.m_strName = transRS.getString("SNAME");
				interestRateInfo.m_tsEffective = transRS.getTimestamp("DTEFFECTIVE");
				interestRateInfo.m_lInputUserID = transRS.getLong("NINPUTUSERID");
				interestRateInfo.m_tsInput = transRS.getTimestamp("DTINPUT");
				interestRateInfo.m_dRate = transRS.getDouble("FRATE");
				interestRateInfo.m_lSerialNo = transRS.getLong("nSerialNo");
				interestRateInfo.setnCurrencyid(transRS.getLong("NCURRENCYID"));
				interestRateInfo.m_strFirstName = transRS.getString("sFirstName");
				interestRateInfo.m_nAccountTypeID = transRS.getLong("naccounttypeid");
				interestRateInfo.m_nFixeddepositmonthID = transRS.getLong("nfixeddepositmonthid");
				interestRateInfo.m_nPrimno = transRS.getLong("NPRIMNO");
			}
			transConn.commit();
		}
		catch (Exception e)
		{
			transConn.rollback();
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}

		}
		return interestRateInfo;
	}
	public long checkInterestRate(InterestRateInfo interestRateInfo,
			long checkUserId, Timestamp checkDate) throws SettlementException, SQLException {
		long lResult = 1;
		long lMaxID = -1;
		long lMaxSerialNo = -1;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			transConn.setAutoCommit(false);
			Log.print("lID=" + interestRateInfo.m_lID);
				//得到最大的max(nSerialNo)+1
			String strSQL = " select nvl(max(nSerialNo)+1,1) from Sett_InterestRate ";
			transPS = prepareStatement(strSQL);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lMaxSerialNo = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			strSQL = " select nvl(max(ID)+1,1) from Sett_InterestRate ";
			transPS = prepareStatement(strSQL);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lMaxID = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			//insert the new record;
			strBuff = new StringBuffer();
			strBuff.append("INSERT INTO Sett_InterestRate(ID ,sName,sFirstName,dtEffective,nInputUserID ,dtInput,fRate,nStatusID,nSerialNo,nCurrencyID,nOfficeID,naccounttypeid,nFixeddepositmonthID)");
			strBuff.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			transPS = prepareStatement(strBuff.toString());
			transPS.setLong(1, lMaxID);
			transPS.setString(2, interestRateInfo.m_strName);
			transPS.setString(3,interestRateInfo.m_strFirstName);
			transPS.setTimestamp(4, interestRateInfo.m_tsEffective);
			transPS.setLong(5, checkUserId);
			transPS.setTimestamp(6, checkDate);
			transPS.setDouble(7, interestRateInfo.m_dRate);
			transPS.setLong(8, Constant.RecordStatus.VALID);
			transPS.setLong(9, lMaxSerialNo);
			transPS.setLong(10,interestRateInfo.getnCurrencyid());
			transPS.setLong(11,interestRateInfo.m_lOfficeID);
			transPS.setLong(12,interestRateInfo.m_nAccountTypeID); 
			transPS.setLong(13,interestRateInfo.m_nFixeddepositmonthID);
			lResult = transPS.executeUpdate();
			transPS.close();
			transPS = null;
			
			//修改源表中的关联关系和复核人ID
			strBuff = new StringBuffer();
			strBuff.append("update sett_interestrateduplicate set NCHECKINPUTUSERID =?, NPRIMNO =?");
			strBuff.append(" where ID =? ");
			transPS = prepareStatement(strBuff.toString());
			transPS.setLong(1, checkUserId);
			transPS.setLong(2, lMaxID);
			transPS.setLong(3, interestRateInfo.m_lID);
			lResult = transPS.executeUpdate();
			transConn.commit();
		}
		catch (Exception e)
		{
			transConn.rollback();
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	public Collection findCheckedInterestRate(long lOfficeID, long lCurrencyID,long status,String sName,long accouttypeID,long fixeddepositmonthID,Timestamp tsEffective,double dRate,long inputUserID) throws SettlementException
	{
		Vector v = new Vector();
		StringBuffer strBuff = new StringBuffer();
		try
		{
			initDAO();
			strBuff = new StringBuffer();
			//strBuff.append("select ID,sName,dtEffective,nInputUserID,dtInput,fRate,nStatusID from(" + "SELECT * FROM Sett_InterestRate a WHERE a.dtEffective<=? and a.nStatusID=? " + " union " + "SELECT * FROM Sett_InterestRate a WHERE a.dtEffective>? and a.nStatusID=?  and a.sName not in(select distinct sName from Sett_InterestRate a where a.dtEffective<=? and a.nStatusID=? ) " + ") a where a.nCurrencyID=? order by a.sName,a.dtEffective desc");
			if(status ==1 || status ==3){
			strBuff.append("select a.ID ID, a.sName sName,a.sFirstName sFirstName, a.DTEFFECTIVE DTEFFECTIVE,a.dtInput dtInput,a.fRate fRate, a.nStatusID nStatusID,a.NINPUTUSERID NINPUTUSERID,a.nSerialNo nSerialNo, a.NPRIMNO NPRIMNO, b.NINPUTUSERID checkUserID,b.DTINPUT checkedDate ");
			strBuff.append("from sett_interestrateduplicate a left outer join sett_interestrate b on a.NPRIMNO=b.id and a.nStatusID=? and a.nCurrencyID=? and a.nOfficeID=? and b.NSTATUSID=? ");
			}else{
				strBuff.append("select a.ID ID, a.sName sName,a.sFirstName sFirstName, a.DTEFFECTIVE DTEFFECTIVE,a.dtInput dtInput,a.fRate fRate, a.nStatusID nStatusID,a.NINPUTUSERID NINPUTUSERID,a.nSerialNo nSerialNo, a.NPRIMNO NPRIMNO,b.NINPUTUSERID checkUserID,b.DTINPUT checkedDate ");
				strBuff.append("from sett_interestrateduplicate a,sett_interestrate b where a.NPRIMNO=b.id and a.nStatusID=? and a.nCurrencyID=? and a.nOfficeID=? and b.NSTATUSID=? ");
			}
			if(sName!=null && sName.trim().length()>0){
				strBuff.append(" and a.sName =?");
			}
			if(accouttypeID >0 ){
				strBuff.append(" and a.NACCOUNTTYPEID =?");
			}
			if(fixeddepositmonthID >0 ){
				strBuff.append(" and a.NFIXEDDEPOSITMONTHID =?");
			}
			
			if(tsEffective != null ){
				strBuff.append(" and a.DTEFFECTIVE =?");
			}
			if(dRate >0){
				strBuff.append(" and a.FRATE =?");
			}
			if(status ==1){
				strBuff.append(" where a.NPRIMNO is null and a.nStatusID =1 and a.NINPUTUSERID !="+ inputUserID);
			}else if(status ==2){
				strBuff.append(" and a.NPRIMNO is not null and a.nStatusID =1");
			}else if(status == -1){
				strBuff.append(" and a.NPRIMNO is not null or a.NINPUTUSERID !="+ inputUserID +" and a.nstatusid=1 ");
			}else{
				strBuff.append(" where a.NPRIMNO is not null or a.NINPUTUSERID !="+ inputUserID +" and a.nstatusid=1 ");
			}
			strBuff.append(" order by NPRIMNO");
			System.out.println(strBuff.toString());
			transPS = prepareStatement(strBuff.toString());
			int intTmp = 1;
			transPS.setLong(intTmp++, Constant.RecordStatus.VALID);
			transPS.setLong(intTmp++, lCurrencyID);
			transPS.setLong(intTmp++, lOfficeID);
			transPS.setLong(intTmp++, Constant.RecordStatus.VALID);
			if(sName!=null && sName.trim().length()>0){
				transPS.setString(intTmp++, sName);
			}
			if(accouttypeID >0 ){
				transPS.setLong(intTmp++, accouttypeID);
			}
			if(fixeddepositmonthID >0 ){
				transPS.setLong(intTmp++, fixeddepositmonthID);
			}
			
			if(tsEffective != null ){
				transPS.setTimestamp(intTmp++, tsEffective);
			}
			if(dRate >0){
				transPS.setDouble(intTmp++, dRate);
			}
			Log.print("strBuff=" + strBuff.toString());
			transRS = executeQuery();
			String strTmpName = "";
			while (transRS.next())
			{
				if (transRS.getString("sName") != null && !strTmpName.equals(transRS.getString("sName")))
				{
					strTmpName = transRS.getString("sName");
					InterestRateInfo interestRateInfo = new InterestRateInfo();
					interestRateInfo.m_lID = transRS.getLong("ID");
					interestRateInfo.m_strName = transRS.getString("sName");
					interestRateInfo.m_strFirstName = transRS.getString("sFirstName")==null?"":transRS.getString("sFirstName");
					interestRateInfo.m_tsEffective = transRS.getTimestamp("dtEffective");
					interestRateInfo.m_lInputUserID = transRS.getLong("nInputUserID");
					interestRateInfo.m_tsInput = transRS.getTimestamp("dtInput");
					interestRateInfo.m_dRate = transRS.getDouble("fRate");
					interestRateInfo.m_nStatusID = transRS.getInt("nStatusID");
					interestRateInfo.m_lSerialNo = transRS.getLong("nSerialNo");
					interestRateInfo.m_nPrimno = transRS.getLong("NPRIMNO");
					interestRateInfo.m_lCheckUserID = transRS.getLong("checkUserID");
					System.out.println(NameRef.getUserNameByID(interestRateInfo.m_lCheckUserID));
					interestRateInfo.m_tsCheck = transRS.getTimestamp("checkedDate");
					v.add(interestRateInfo);
				}
			}
			//关闭资源
			Log.print("关闭资源");
			initDAO();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		Log.print("返回结果");
		return (v.size() > 0 ? v : null);
	}
	
	public long cancleCheckInterestRate(InterestRateInfo interestRateInfo) throws SettlementException, SQLException {
		long lResult = -1;
		String strSQL = "";
		long lID = -1;
		try
		{
			initDAO();
			transConn.setAutoCommit(false);
			lID = interestRateInfo.m_nPrimno;
			//判断该利率是否被使用过
			long lCount = -1;
			strSQL = " select count(*) Count from sett_InterestRatePlanItem pi,sett_interestrateplan p ";
			strSQL += " where pi.nInterestRatePlanID=p.ID and p.nStatusID="+Constant.RecordStatus.VALID+" and pi.nInterestRateID="+lID;
			transPS = prepareStatement(strSQL);
			transRS = executeQuery();
			if (transRS != null && transRS.next())
			{
				lCount = transRS.getLong("Count");
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
					
			if (lCount == 0)
			{
				//删除
				strSQL = "UPDATE Sett_InterestRate SET nStatusID=?  WHERE nSerialNo=?";
				transPS = transConn.prepareCall(strSQL);
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				System.out.println(interestRateInfo.m_lSerialNo);
				transPS.setLong(2, interestRateInfo.m_lSerialNo);
				lResult = executeUpdate();
				transPS.close();
				transPS = null;
				//修改关联表的信息
				strSQL ="UPDATE sett_interestrateduplicate set NPRIMNO = NULL , NCHECKINPUTUSERID = NULL where NPRIMNO =?";
				transPS = transConn.prepareStatement(strSQL);
				transPS.setLong(1, interestRateInfo.m_nPrimno);
				lResult = transPS.executeUpdate();
			}
			else
			{
				lResult = -100;//已被使用
			}
			transConn.commit();
		}
		catch (Exception e)
		{
			transConn.rollback();
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return lResult;
	}
	
	//开立活期账户时取生效日最接近并小于等于开机日的协定利率。
	public double getAgreedInterestRate(long officeid,long currencyid,long interestRateType, Timestamp systemOpenDate) throws SettlementException, SQLException {
		StringBuffer strBuff = new StringBuffer();
		double agreedInterestRate = 0.00;
		try
		{
			initDAO();
			strBuff.append("select m.frate from Sett_InterestRate m");
			strBuff.append(" where m.naccounttypeid = ? ");
			strBuff.append(" and m.nstatusid != 0 and m.nofficeid = ? ");
			strBuff.append(" and m.ncurrencyid =? and m.dteffective =");
			strBuff.append(" (select max(s.dteffective) from Sett_InterestRate s ");
			strBuff.append(" where s.naccounttypeid = ? and s.dteffective <= ? ");
			strBuff.append(" and s.nstatusid != 0 and s.nofficeid = ? and s.ncurrencyid = ?) ");
            
			System.out.print(strBuff.toString());
			System.out.print(officeid);
			System.out.print(currencyid);
			System.out.print(interestRateType);
			System.out.print(systemOpenDate);
			transPS = prepareStatement(strBuff.toString());
			transPS.setLong(1, interestRateType);
			transPS.setLong(2, officeid);
			transPS.setLong(3, currencyid);
			transPS.setLong(4, interestRateType);
			transPS.setTimestamp(5, systemOpenDate);
			transPS.setLong(6,officeid);
			transPS.setLong(7,currencyid);
			transRS = transPS.executeQuery();
			if(transRS.next()){
				agreedInterestRate = transRS.getDouble("frate");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception ex)
			{
				throw new SettlementException();
			}
		}
		return agreedInterestRate;
	}

}
