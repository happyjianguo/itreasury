/*
 * Created on 2004-10-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dataentity.InterestRatePlanItemInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettinterestrateplanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_interestrateplanDAO extends SettlementDAO
{
	/**
	 * 
	 */
	public Sett_interestrateplanDAO()
	{
		super("Sett_interestrateplan",true);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 保存利率计划主表信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存利率计划主表信息</b>
	 * <ul>
	 * <li>操作数据库表InterestRatePlan
	 * <li>如果lID<0，则在InterestRatePlan表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * <li>返回本记录的标识
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param lInterestPlanTypeID
	 * @param strName
	 * @param strCode
	 * @param lInputUserID
	 * @param tsInput
	 * @return long
	 * @exception Exception
	 */
	public long saveInterestPlanMain(long lID, long lOfficeID, long lInterestPlanTypeID, String strName, String strCode, long lInputUserID, Timestamp tsInput,long nCurrencyID) throws SettlementException
	{
		long lResult = -1;
		long lTmpID = lID;
		String strTmpSQL = "";
		long lNum = -1;
		try
		{
			initDAO();
			//判断是否重复
			if (lID < 0)
			{
				strTmpSQL = "select count(*) num from Sett_interestrateplan where (sName=? or sCode=?) and nStatusID=? and nOfficeID=? and ncurrencyid="+nCurrencyID+"";
				transPS = prepareStatement(strTmpSQL);
				transPS.setString(1, strName);
				transPS.setString(2, strCode);
				transPS.setLong(3, Constant.RecordStatus.VALID);
				transPS.setLong(4, lOfficeID);
			}
			else
			{
				strTmpSQL = "select count(*) num from Sett_interestrateplan where (sName=? or sCode=?) and nStatusID=? and ID<>? and nOfficeID=? and ncurrencyid="+nCurrencyID+"";
				transPS = prepareStatement(strTmpSQL);
				transPS.setString(1, strName);
				transPS.setString(2, strCode);
				transPS.setLong(3, Constant.RecordStatus.VALID);
				transPS.setLong(4, lID);
				transPS.setLong(5, lOfficeID);
			}
			transRS = executeQuery();
			if (transRS != null && transRS.next())
				lNum = transRS.getLong("num");
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			if (lNum == 0)
			{
				if (lID < 0)
				{
					//得到最大ID
					strTmpSQL = "select nvl(max(id)+1,1) id from Sett_interestrateplan";
					transPS = prepareStatement(strTmpSQL);
					transRS = transPS.executeQuery();
					if (transRS != null && transRS.next())
						lTmpID = transRS.getLong("id");
					transRS.close();
					transRS = null;
					transPS.close();
					transPS = null;
					//insert new record
					strTmpSQL = "insert into Sett_interestrateplan(id,ninterestplantypeid,sname,scode,ninputuserid,dtinput,nstatusid,nOfficeID,nCurrencyID) values(?,?,?,?,?,?,?,?,?)";
					transPS = prepareStatement(strTmpSQL);
					transPS.setLong(1, lTmpID);
					transPS.setLong(2, lInterestPlanTypeID);
					transPS.setString(3, strName);
					transPS.setString(4, strCode);
					transPS.setLong(5, lInputUserID);
					transPS.setTimestamp(6, tsInput);
					transPS.setLong(7, Constant.RecordStatus.VALID);
					transPS.setLong(8, lOfficeID);
					transPS.setLong(9, nCurrencyID);
					lResult = transPS.executeUpdate();
					transPS.close();
					transPS = null;
				}
				else
				{ //update the record
					strTmpSQL = "update Sett_interestrateplan set ninterestplantypeid=?,sname=?,scode=?,ninputuserid=?,dtinput=?,nstatusid=?,nOfficeID=? where id=?";
					transPS = prepareStatement(strTmpSQL);
					transPS.setLong(1, lInterestPlanTypeID);
					transPS.setString(2, strName);
					transPS.setString(3, strCode);
					transPS.setLong(4, lInputUserID);
					transPS.setTimestamp(5, tsInput);
					transPS.setLong(6, Constant.RecordStatus.VALID);
					transPS.setLong(7, lOfficeID);
					transPS.setLong(8, lTmpID);
					lResult = transPS.executeUpdate();
					transPS.close();
					transPS = null;
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
		//如果lResult不等于-1，表示成功，就返回lTmpID
		if (lResult != -1)
			lResult = lTmpID;
		if (lNum != 0)
			lResult = 0;
		return lResult;
	}
	
	/**
	 * 保存利率计划子表信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存利率计划子表信息</b>
	 * <ul>
	 * <li>操作数据库表InterestRatePlanItem
	 * <li>先删除表InterestRatePlanItem利率计划标识为lInterestRatePlanID的所有记录,再新增记录
	 * <li>根据lInterestPlanTypeID判断有效参数
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lInterestRatePlanID
	 * @param lInterestPlanTypeID
	 * @param lAryDayType
	 * @param lAryDayCount
	 * @param lAryBalanceType
	 * @param dAryBanlance
	 * @param tsAryDateStart
	 * @param tsAryDateEnd
	 * @param lAryInterestRateID
	 * @param dAryInterestRate
	 * @return void
	 * @exception Exception
	 */
	public long saveInterestPlanItem(
		long lInterestRatePlanID,
		long lInterestPlanTypeID,
		long[] lAryDayType,
		long[] lAryDayCount,
		long[] lAryBalanceType,
		double[] dAryBalance,
		Timestamp[] tsAryDateStart,
		Timestamp[] tsAryDateEnd,
		long[] lAryInterestRateID,
		double[] dAryInterestRate) throws SettlementException
	{
		long lResult = -1;
		long lTmpID = 0; //max(ID)+1
		String strTmpSQL = "";
		try
		{
			initDAO();
			//delete all the record
			strTmpSQL = "delete from Sett_interestrateplanitem where ninterestrateplanid=?";
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, lInterestRatePlanID);
			lResult = transPS.executeUpdate();
			transPS.close();
			transPS = null;
			//select max(id)+1
			strTmpSQL = "select nvl((max(id)+1),1) id from Sett_interestrateplanitem";
			transPS = prepareStatement(strTmpSQL);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
				lTmpID = transRS.getLong("id");
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			//根据lInterestPlanTypeID不同分别处理
			switch ((int) lInterestPlanTypeID)
			{
				case (int) SETTConstant.InterestRatePlanType.BALANCE: //按余额设置利率
					{
						for (int i = 0; i < lAryBalanceType.length; i++)
						{
							/*
							 *要存入的数据库字段
							 *必存id\ninteretrateplanid\ninterestrateid\finterestrate
							 *本case要存
							 *nbalancetype
							 *mbalance
							 */
							strTmpSQL = "insert into Sett_interestrateplanitem(id,ninterestrateplanid,nserialno,ninterestrateid,finterestrate,nbalancetype,mbanlance) values(?,?,?,?,?,?,?)";
							transPS = prepareStatement(strTmpSQL);
							transPS.setLong(1, lTmpID);
							transPS.setLong(2, lInterestRatePlanID);
							transPS.setLong(3, i + 1);
							transPS.setLong(4, lAryInterestRateID[i]);
							transPS.setDouble(5, dAryInterestRate[i]);
							transPS.setLong(6, lAryBalanceType[i]);
							transPS.setDouble(7, dAryBalance[i]);
							lResult = transPS.executeUpdate();
							transPS.close();
							transPS = null;
							lTmpID++;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AVERAGE: //日均按余额设置利率
				{
					for (int i = 0; i < lAryBalanceType.length; i++)
					{
						/*
						 *要存入的数据库字段
						 *必存id\ninteretrateplanid\ninterestrateid\finterestrate
						 *本case要存
						 *nbalancetype
						 *mbalance
						 */
						strTmpSQL = "insert into Sett_interestrateplanitem(id,ninterestrateplanid,nserialno,ninterestrateid,finterestrate,nbalancetype,mbanlance) values(?,?,?,?,?,?,?)";
						transPS = prepareStatement(strTmpSQL);
						transPS.setLong(1, lTmpID);
						transPS.setLong(2, lInterestRatePlanID);
						transPS.setLong(3, i + 1);
						transPS.setLong(4, lAryInterestRateID[i]);
						transPS.setDouble(5, dAryInterestRate[i]);
						transPS.setLong(6, lAryBalanceType[i]);
						transPS.setDouble(7, dAryBalance[i]);
						lResult = transPS.executeUpdate();
						transPS.close();
						transPS = null;
						lTmpID++;
					}
					break;
				}				
				case (int) SETTConstant.InterestRatePlanType.DATE : //按日期设置利率
					{
						for (int i = 0; i < tsAryDateStart.length; i++)
						{
							/*
							 *要存入的数据库字段
							 *必存id\ninteretrateplanid\ninterestrateid\finterestrate
							 *本case要存
							 *dtdatestart
							 *dtdateend
							 */
							strTmpSQL = "insert into Sett_interestrateplanitem(id,ninterestrateplanid,nserialno,ninterestrateid,finterestrate,dtdatestart,dtdateend) values(?,?,?,?,?,?,?)";
							transPS = prepareStatement(strTmpSQL);
							transPS.setLong(1, lTmpID);
							transPS.setLong(2, lInterestRatePlanID);
							transPS.setLong(3, i + 1);
							transPS.setLong(4, lAryInterestRateID[i]);
							transPS.setDouble(5, dAryInterestRate[i]);
							transPS.setTimestamp(6, tsAryDateStart[i]);
							transPS.setTimestamp(7, tsAryDateEnd[i]);
							lResult = transPS.executeUpdate();
							transPS.close();
							transPS = null;
							lTmpID++;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.DAYS : //按持续天数设置利率
					{
						for (int i = 0; i < lAryDayType.length; i++)
						{
							/*
							 *要存入的数据库字段
							 *必存id\ninteretrateplanid\ninterestrateid\finterestrate
							 *本case要存
							 *ndaytype
							 *ndaycount
							 */
							strTmpSQL = "insert into Sett_interestrateplanitem(id,ninterestrateplanid,nserialno,ninterestrateid,finterestrate,ndaytype,ndaycount) values(?,?,?,?,?,?,?)";
							transPS = prepareStatement(strTmpSQL);
							transPS.setLong(1, lTmpID);
							transPS.setLong(2, lInterestRatePlanID);
							transPS.setLong(3, i + 1);
							transPS.setLong(4, lAryInterestRateID[i]);
							transPS.setDouble(5, dAryInterestRate[i]);
							transPS.setLong(6, lAryDayType[i]);
							transPS.setLong(7, lAryDayCount[i]);
							lResult = transPS.executeUpdate();
							transPS.close();
							transPS = null;
							lTmpID++;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AND_DAYS : //按余额、持续天数设置
					{
						//组合天数
						for (int i = 0; i < lAryDayType.length; i++)
						{
							/*
							 *要存入的数据库字段
							 *必存id\ninteretrateplanid\ninterestrateid\finterestrate
							 *本case要存
							 *nbalancetype
							 *mbalance
							 *ndaytype
							 *ndaycount
							 */
							strTmpSQL = "insert into Sett_interestrateplanitem(id,ninterestrateplanid,nserialno,ninterestrateid,finterestrate,nbalancetype,mbanlance,ndaytype,ndaycount) values(?,?,?,?,?,?,?,?,?)";
							transPS = prepareStatement(strTmpSQL);
							transPS.setLong(1, lTmpID);
							transPS.setLong(2, lInterestRatePlanID);
							transPS.setLong(3, i + 1);
							transPS.setLong(4, lAryInterestRateID[i]);
							transPS.setDouble(5, dAryInterestRate[i]);
							transPS.setLong(6, lAryBalanceType[i]);
							transPS.setDouble(7, dAryBalance[i]);
							transPS.setLong(8, lAryDayType[i]);
							transPS.setLong(9, lAryDayCount[i]);
							lResult = transPS.executeUpdate();
							transPS.close();
							transPS = null;
							lTmpID++;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AND_DATE : //按余额、日期设置
					{
						//组合日期
						for (int i = 0; i < tsAryDateStart.length; i++)
						{
							/*
							 *要存入的数据库字段
							 *必存id\ninteretrateplanid\ninterestrateid\finterestrate
							 *本case要存
							 *nbalancetype
							 *mbalance
							 *dtdatestart
							 *dtdateend
							 */
							strTmpSQL = "insert into Sett_interestrateplanitem(id,ninterestrateplanid,nserialno,ninterestrateid,finterestrate,nbalancetype,mbanlance,dtdatestart,dtdateend) values(?,?,?,?,?,?,?,?,?)";
							transPS = prepareStatement(strTmpSQL);
							transPS.setLong(1, lTmpID);
							transPS.setLong(2, lInterestRatePlanID);
							transPS.setLong(3, i + 1);
							transPS.setLong(4, lAryInterestRateID[i]);
							transPS.setDouble(5, dAryInterestRate[i]);
							transPS.setLong(6, lAryBalanceType[i]);
							transPS.setDouble(7, dAryBalance[i]);
							transPS.setTimestamp(8, tsAryDateStart[i]);
							transPS.setTimestamp(9, tsAryDateEnd[i]);
							lResult = transPS.executeUpdate();
							transPS.close();
							transPS = null;
							lTmpID++;
						}
						break;
					}
				default :
					{
					throw new SettlementException();
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
	 * 删除利率计划
	 * @param lID
	 * @return
	 * @throws RemoteException
	 */
	public long deleteInterestRatePlan(long lID) throws SettlementException
	{
		long lResult = -1;
		String strSQL = "";
		try
		{
			initDAO();
			//判断该利率是否被使用过
			long lCount = -1;
			strSQL = " select count(*) Count from sett_SubAccount sa ";
			strSQL += " where sa.nStatusID>0 and sa.ac_nInterestRatePlanID=" + lID;
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
				strSQL = "UPDATE Sett_InterestRatePlan SET nStatusID=? WHERE ID=?";
				transPS = transConn.prepareCall(strSQL);
				transPS.setLong(1, Constant.RecordStatus.INVALID);
				transPS.setLong(2, lID);
				lResult = executeUpdate();
				transPS.close();
				transPS = null;
			}
			else
			{
				lResult = -100; //已被使用
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
	 * 得到最新的利率计划代号
	 * @param lOfficeID
	 */
	public String getNewInterestPlanCode(long lOfficeID) throws SettlementException
	{
		String strCode = "";
		String strTmpSQL = "";
		try
		{
			initDAO();
			//strTmpSQL = "select nvl(max(to_number(sCode))+1,1) sCode from sett_INTERESTRATEPLAN where nOfficeID=?";
			strTmpSQL = " select nvl(min(i),1) ming from (select (id+1) i from Sett_INTERESTRATEPLAN b where (id+1) not in (select to_number(a.sCode) from sett_INTERESTRATEPLAN a where a.nOfficeID=? ) and b.nOfficeID=? ) ";
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, lOfficeID);
			transPS.setLong(2, lOfficeID);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				strCode = transRS.getString("ming");
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
		return strCode;
	}
	/**
	 * 得到最新的利率计划代号
	 * @param lOfficeID
	 */
	public String getNewInterestPlanCode(long lOfficeID,long lCurrencyID) throws SettlementException
	{
		String strCode = "";
		String strTmpSQL = "";
		try
		{
			initDAO();
			//strTmpSQL = "select nvl(max(to_number(sCode))+1,1) sCode from sett_INTERESTRATEPLAN where nOfficeID=?";
			//
			//strTmpSQL = " select nvl(min(i),1) ming from (select (id+1) i from Sett_INTERESTRATEPLAN b where (id+1) not in (select to_number(a.sCode) from sett_INTERESTRATEPLAN a where a.nOfficeID=? and a.ncurrencyid="+lCurrencyID+" ) and b.nOfficeID=? and  b.ncurrencyid="+lCurrencyID+") ";
			strTmpSQL = " select nvl((to_number(max(a.sCode))+1),1) ming from Sett_INTERESTRATEPLAN  a   where a.nOfficeID = "+lOfficeID+"  and a.ncurrencyid = "+lCurrencyID+" ";
                                  
			transPS = prepareStatement(strTmpSQL);
			System.out.println(strTmpSQL+"^^^^^^^^^");
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				strCode = transRS.getString("ming");
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
		return strCode;
	}
	/**
	 * 通过标识查询利率设置主表信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>通过标识查询利率设置主表信息</b>
	 * <ul>
	 * <li>操作数据库表InterestRatePlan
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return InterestRatePlanInfo
	 * @exception Exception
	 */
	public SettinterestrateplanInfo findInterestRatePlanByID(long lID) throws SettlementException 
	{
		SettinterestrateplanInfo iri = null;
		String strTmpSQL = "";
		try
		{
			Log.print("you enter into findInterestRateByID");
			initDAO();
			strTmpSQL = "select * from Sett_InterestRatePlan where id=? and nStatusId=?";
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, lID);
			transPS.setLong(2, Constant.RecordStatus.VALID);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				iri = new SettinterestrateplanInfo();
				iri.m_lID = transRS.getLong("id");
				iri.m_lInterestPlanTypeID = transRS.getLong("nInterestPlanTypeID");
				iri.m_strName = transRS.getString("sName");
				iri.m_strCode = transRS.getString("sCode");
				iri.m_lInputUserID = transRS.getLong("nInputUserID");
				iri.m_tsInput = transRS.getTimestamp("dtInput");
				iri.m_lStatusID = transRS.getLong("nStatusID");
			}
			//关闭资源
			finalizeDAO();
			Log.print("you leave findInterestRateByID");
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
		return iri;
	}
	
	/**
	 * 通过利率设置主表标识查询利率设置子表信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>通过利率设置主表标识查询利率设置子表信息</b>
	 * <ul>
	 * <li>操作数据库表InterestRatePlanItem
	 * <li>返回Collection包含类InterestRatePlanItemInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lInterestRatePlanID
	 * @return C ollection
	 * @exception Exception
	 */
	public Collection findInterestRatePlanItemByInterestRatePlanID(long lInterestRatePlanID) throws SettlementException
	{
		Vector v = new Vector();

		String strTmpSQL = null;
		try
		{
			Log.print("findInterestRatePlanItemByInterestRatePlanID");
			initDAO();
			//返回需求的结果集
			strTmpSQL = "select * from Sett_interestrateplanitem where ninterestrateplanid = ? order by nserialno";
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, lInterestRatePlanID);
			transRS = executeQuery();
			while (transRS.next())
			{
				InterestRatePlanItemInfo interestrateplaniteminfo = new InterestRatePlanItemInfo();
				interestrateplaniteminfo.m_lID = transRS.getLong("ID");
				interestrateplaniteminfo.m_lInterestRatePlanID = transRS.getLong("ninterestrateplanid");
				interestrateplaniteminfo.m_lSerialNo = transRS.getLong("nserialno");
				interestrateplaniteminfo.m_lDayType = transRS.getLong("ndaytype");
				interestrateplaniteminfo.m_lDayCount = transRS.getLong("ndaycount");
				interestrateplaniteminfo.m_lBalanceType = transRS.getLong("nbalancetype");
				interestrateplaniteminfo.m_dBanlance = transRS.getDouble("mbanlance");
				interestrateplaniteminfo.m_tsDateStart = transRS.getTimestamp("dtdatestart");
				interestrateplaniteminfo.m_tsDateEnd = transRS.getTimestamp("dtdateend");
				interestrateplaniteminfo.m_lInterestRateID = transRS.getLong("ninterestrateid");
				interestrateplaniteminfo.m_dInterestRate = transRS.getDouble("finterestrate");
				//interestrateplaniteminfo.m_dRate = transRS.getDouble("frate");
				v.addElement(interestrateplaniteminfo);
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
		return (v.size() > 0 ? v : null);
	}
	/**
	 * 通过利率计划编号段查询利率设置主表信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>通过利率计划编号段查询利率设置主表信息</b>
	 * <ul>
	 * <li>操作数据库表InterestRatePlan
	 * <li>查询全部则可以把利率计划编号段设置为-1到上限
	 * <li>返回的Collection包含类InterestRatePlanInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID 办事处标识
	 * @param lIDStart
	 * @param lIDEnd
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllInterestPlan(long lOfficeID,long nCurrencyID, long lIDStart, long lIDEnd, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) throws SettlementException
	{
		Vector v = new Vector();
		String strTmpSQL = "";
		String strSQL = "";
		//分页变量
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		//sCode
		String strTmpIDStart = "";
		String strTmpIDEnd = "";
		try
		{
			initDAO();
			//根据ID查询sCode
			System.out.println("start=" + lIDStart);
			System.out.println("end=" + lIDStart);
			if (lIDStart > 0)
			{
				strTmpSQL = "select sCode from Sett_interestratePlan where ID=?";
				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, lIDStart);
				transRS = transPS.executeQuery();
			}
			else
			{
				strTmpSQL = "select min(sCode) from Sett_interestratePlan ";
				transPS = prepareStatement(strTmpSQL);
				transRS = transPS.executeQuery();
			}
			if (transRS != null && transRS.next())
			{
				strTmpIDStart = transRS.getString(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			if (lIDEnd > 0)
			{
				strTmpSQL = "select sCode from Sett_interestratePlan where ID=?";
				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, lIDEnd);
				transRS = transPS.executeQuery();
			}
			else
			{
				strTmpSQL = "select max(sCode) from Sett_interestratePlan";
				transPS = prepareStatement(strTmpSQL);
				transRS = transPS.executeQuery();
			}
			if (transRS != null && transRS.next())
			{
				strTmpIDEnd = transRS.getString(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
			if (strTmpIDStart == null || strTmpIDStart.length() < 1 || strTmpIDEnd == null || strTmpIDEnd.length() < 1)
			{
				strTmpSQL = "select count(*) from Sett_interestratePlan where nStatusID=? and nOfficeID=? and nCurrencyID=?";
				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, Constant.RecordStatus.VALID);
				transPS.setLong(2, lOfficeID);
				transPS.setLong(3, nCurrencyID);
			}
			else
			{
				strTmpSQL = "select count(*) from Sett_interestratePlan where sCode between ? and ? and nStatusID=? and nOfficeID=? and nCurrencyID=?";
				transPS = prepareStatement(strTmpSQL);
				transPS.setString(1, strTmpIDStart);
				transPS.setString(2, strTmpIDEnd);
				transPS.setLong(3, Constant.RecordStatus.VALID);
				transPS.setLong(4, lOfficeID);
				transPS.setLong(5, nCurrencyID);
			}
			System.out.println("strTmpIDEnd=" + strTmpIDEnd);
			System.out.println("strTmpIDStart=" + strTmpIDStart);
			//计算记录总数
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
			{
				lRecordCount = transRS.getLong(1);
			}
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;
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
			strTmpSQL = "";
			switch ((int) lOrderParam)
			{
				case 1 :
					strTmpSQL += " order by a.ID";
					break;
				case 2 :
					strTmpSQL += " order by a.NINTERESTPLANTYPEID";
					break;
				case 3 :
					strTmpSQL += " order by a.SNAME";
					break;
				case 4 :
					strTmpSQL += " order by a.SCODE";
					break;
				case 5 :
					strTmpSQL += " order by a.DTINPUT";
					break;
				case 6 :
					strTmpSQL += " order by a.NINPUTUSERID";
					break;
				default :
					strTmpSQL += " order by a.SCODE ";
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strTmpSQL += " desc";
			}
			if (strTmpIDStart == null || strTmpIDStart.length() < 1 || strTmpIDEnd == null || strTmpIDEnd.length() < 1)
			{
				strSQL =
					"select * from (select cc.*,rownum r from (select a.*,b.sName as userName from sett_INTERESTRATEPLAN a,UserInfo b where b.ID(+)=a.nInputUserID  and a.nStatusID=? and a.nOfficeID=? and a.nCurrencyID=? " + strTmpSQL + ") cc) where r between ? and ? ";
				System.out.println("sql:" + strSQL);
				transPS = prepareStatement(strSQL);
				transPS.setLong(1, Constant.RecordStatus.VALID);
				transPS.setLong(2, lOfficeID);
				transPS.setLong(3, nCurrencyID);
				transPS.setLong(4, lRowNumStart);
				transPS.setLong(5, lRowNumEnd);
			}
			else
			{
				strSQL =
					"select * from ( select cc.*,rownum r from (select a.*,b.sName as userName from sett_INTERESTRATEPLAN a,UserInfo b where b.ID(+)=a.nInputUserID and a.sCode between ? and ? and a.nStatusID=? and a.nOfficeID=? and a.nCurrencyID=?"
						+ strTmpSQL
						+ ") cc ) where r between ? and ? ";
				System.out.println("sql:" + strSQL);
				transPS = prepareStatement(strSQL);
				transPS.setString(1, strTmpIDStart);
				transPS.setString(2, strTmpIDEnd);
				transPS.setLong(3, Constant.RecordStatus.VALID);
				transPS.setLong(4, lOfficeID);
				transPS.setLong(5, nCurrencyID);
				transPS.setLong(6, lRowNumStart);
				transPS.setLong(7, lRowNumEnd);
			}
			System.out.println("sql:" + strSQL);
			transRS = transPS.executeQuery();
			int nTest = 1;
			while (transRS.next())
			{
				SettinterestrateplanInfo interestrateplaninfo = new SettinterestrateplanInfo();
				interestrateplaninfo.m_lID = transRS.getLong("ID");
				interestrateplaninfo.m_lInterestPlanTypeID = transRS.getLong("nInterestPlanTypeID");
				interestrateplaninfo.m_strName = transRS.getString("sName");
				interestrateplaninfo.m_strCode = transRS.getString("sCode");
				interestrateplaninfo.m_lInputUserID = transRS.getLong("nInputUserID");
				interestrateplaninfo.m_tsInput = transRS.getTimestamp("dtInput");
				interestrateplaninfo.m_lStatusID = transRS.getLong("nStatusID");
				interestrateplaninfo.m_strInputUserName = transRS.getString("userName");
				interestrateplaninfo.m_lPageCount = lPageCount;
				v.add(interestrateplaninfo);
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
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return (v.size() > 0 ? v : null);
	}
}
