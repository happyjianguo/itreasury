/*
 * Created on 2003-9-26
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transcompatibility.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityDetailInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
/**
 * <p>Title:数据操作类</p><p>Description:用于将数据插入数据表中</p><p>Copyright:
 * Copyright (c) 2003</p><p>Company:isoftstone</p>
 * 
 * @author gqzhang
 * @version 1.0
 */
public class Sett_TransCompatibilityDAO extends SettlementDAO
{
	//----------------------------------------------------------
	public final static int ORDERBY_TRANSACTIONNOID = 0; //交易号
	//----------------------------------------------------------
	/*
	 * public final static int ORDERBY_ACCOUNT0 = 1; //借方一账户号 public final
	 * static int ORDERBY_BANK0 = 2; //开户行 public final static int
	 * ORDERBY_GENERALLEDGER0 = 3; //总账类类型 public final static int
	 * ORDERBY_AMOUNT0 = 4; //金额 public final static int ORDERBY_RANSDIRECTION0 =
	 * 5; //借贷方向 //----------------------------------------------------------
	 * public final static int ORDERBY_ACCOUNT1 = 6; //借方一账户号 public final
	 * static int ORDERBY_BANK1 = 7; //开户行 public final static int
	 * ORDERBY_GENERALLEDGER1 = 8; //总账类类型 public final static int
	 * ORDERBY_AMOUNT1 = 9; //金额 public final static int ORDERBY_RANSDIRECTION1 =
	 * 10; //借贷方向 //----------------------------------------------------------
	 * public final static int ORDERBY_ACCOUNT2 = 11; //借方一账户号 public final
	 * static int ORDERBY_BANK2 = 12; //开户行 public final static int
	 * ORDERBY_GENERALLEDGER2 = 13; //总账类类型 public final static int
	 * ORDERBY_AMOUNT2 = 14; //金额 public final static int ORDERBY_RANSDIRECTION2 =
	 * 15; //借贷方向 //----------------------------------------------------------
	 * public final static int ORDERBY_ACCOUNT3 = 16; //借方一账户号 public final
	 * static int ORDERBY_BANK3 = 17; //开户行 public final static int
	 * ORDERBY_GENERALLEDGER3 = 18; //总账类类型 public final static int
	 * ORDERBY_AMOUNT3 = 19; //金额 public final static int ORDERBY_RANSDIRECTION3 =
	 * 20; //借贷方向
	 */
	//----------------------------------------------------------
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public Sett_TransCompatibilityDAO()
	{
		super("Sett_TransCompatibility");
	}
	/**
	 * Method findByID. 根据兼容业务的记录id查找兼容业务的信息包括明细
	 * 
	 * @param ltransID
	 * @return TransCompatibilityInfo
	 * @throws Exception
	 */
	public TransCompatibilityInfo findByID(long ltransID) throws SettlementDAOException
	{
		ResultSet rs = null;
		TransCompatibilityInfo resultInfo = null;
		try
		{
			log.print("======Sett_TransCompatibilityDAO：进入查找兼容业务信息======");
			log.print("======ltransID======:" + ltransID);
			try
			{
				initDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID =" + ltransID);
			strSQLBuffer.append("\n");
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			Vector vctReturn = getCompatibilityMainInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				resultInfo = (TransCompatibilityInfo) vctReturn.elementAt(0);
				if (resultInfo != null)
				{
					log.print("===resultInfo:" + resultInfo);
				}
			}
			rs.close();
			log.print("======Sett_TransCompatibilityDAO：结束查找兼容业务信息======");
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找兼容业务信息产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("查找兼容业务信息产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return resultInfo;
	}
	/**
	 * 方法说明：根据ID查找修改时间
	 * 
	 * @param transID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp findTouchDate(long transID) throws SettlementDAOException
	{
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT ModifyDate FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = " + transID);
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
				log.debug("====修改时间：" + res);
			}
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("查找兼容业务修改时间产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("查找兼容业务修改时间产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return res;
	}
	/**
		 * 根据交易号查找交易ID的方法：
		 */
	public long getIDByTransNo(String strTransNo) throws SettlementDAOException
	{
		ResultSet rs = null;
		long lID = -1;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			prepareStatement("select ID from \n" + strTableName + " where TransNo=" + strTransNo);
			rs = executeQuery();
			if (rs != null && rs.next())
			{
				lID = rs.getLong("ID");
			}
			rs.close();
		}
		catch (Exception e)
		{
			throw new SettlementDAOException("根据兼容交易编号查找交易id产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return lID;
	}
	/**
	 * Method findCompatibilityByQueryCondition. 根据组合查询条件查找兼容记录
	 * 
	 * @param info
	 * @param strOrderByName
	 * @param isDesc
	 * @return Vector
	 * @throws SettlementDAOException
	 */
	public Vector findCompatibilityByQueryCondition(QueryByStatusConditionInfo info) throws SettlementDAOException
	{
		Vector vctReturn = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where ID>0 ");
			//交易类型
			if (info.getTransTypeID() > 0)
			{
				strSQLBuffer.append(" and TransactionTypeID=" + info.getTransTypeID());
				strSQLBuffer.append("\n");
			}
			//兼容交易类型
			if (info.getOperationTypeID() > 0)
			{
				strSQLBuffer.append(" and OperationTypeID=" + info.getOperationTypeID());
				strSQLBuffer.append("\n");
			}
			//录入人
			if (info.getInputUserID() > 0)
			{
				strSQLBuffer.append(" and InputUserID=" + info.getInputUserID());
				strSQLBuffer.append("\n");
			}
			//复核人
			if (info.getCheckUserID() > 0)
			{
				strSQLBuffer.append(" and CheckUserID=" + info.getCheckUserID());
				strSQLBuffer.append("\n");
			}
			if (info.getStatusIDs() != null && info.getStatusIDs().length > 0)
			{
				long lTemp = info.getStatusIDs().length;
				boolean isStart = true;
				for (int i = 0; i < lTemp; i++)
				{
					if (info.getStatusIDs()[i] == SETTConstant.TransactionStatus.TEMPSAVE)
					{ //暂存没有时间限制
						if (isStart)
						{
							strSQLBuffer.append(" and (");
							isStart = !isStart;
						}
						else
						{
							strSQLBuffer.append(" or ");
						}
						strSQLBuffer.append("(StatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
					}
					else
						if (info.getStatusIDs()[i] == SETTConstant.TransactionStatus.SAVE || info.getStatusIDs()[i] == SETTConstant.TransactionStatus.CHECK)
						{ //保存或者复核要查当天的
							if (isStart)
							{
								strSQLBuffer.append(" and (");
								isStart = !isStart;
							}
							else
							{
								strSQLBuffer.append("or ");
							}
							strSQLBuffer.append("( StatusID = " + info.getStatusIDs()[i]);
							strSQLBuffer.append(" and ExecuteDate= TO_DATE('" + DataFormat.getDateString(info.getExecuteDate()) + "','YYYY/MM/DD HH24:MI:SS') \n");
							strSQLBuffer.append("\n");
							strSQLBuffer.append(" )");
						}
						else
						{
							strSQLBuffer.append(" and ");
							strSQLBuffer.append(" StatusID = " + info.getStatusIDs()[i]); //空白的时候
						}
				}
				strSQLBuffer.append(")");
				strSQLBuffer.append("\n");
			}
			strSQLBuffer.append(" and OfficeID=" + info.getOfficeID());
			strSQLBuffer.append("\n");
			strSQLBuffer.append(" and CurrencyID=" + info.getCurrencyID());
			strSQLBuffer.append("\n");
			switch ((int) info.getOrderByID())
			{
				case (int) ORDERBY_TRANSACTIONNOID :
					strSQLBuffer.append(" order by TransNo ");
					break;
				default :
					strSQLBuffer.append(" order by TransNo ");
			}
			strSQLBuffer.append("\n");
			if (info.isDesc())
			{
				strSQLBuffer.append(" desc \n");
			}
			log.debug(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctReturn = getCompatibilityMainInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				log.debug("===主交易信息不为空===");
				log.debug("===主交易信息记录数===" + vctReturn.size());
				Vector vctTemp = new Vector();
				Vector vctDetail = null;
				TransCompatibilityInfo mainInfo = null;
				TransCompatibilityDetailInfo detailInfo = null;
				Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
				for (int i = 0; i < vctReturn.size(); i++)
				{
					mainInfo = (TransCompatibilityInfo) vctReturn.elementAt(i);
					if (mainInfo != null)
					{
						log.debug("===查询主信息对应的子信息" + i);
						vctDetail = detailDao.findByMainID(mainInfo.getId());
						if (vctDetail != null && vctDetail.size() > 0)
						{
							mainInfo.setTransCompatibilityDetailInfo(vctDetail);
						}
						vctTemp.add(mainInfo);
					}
				}
				vctReturn = vctTemp;
			}
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("根据组合查询条件查找兼容记录产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("根据组合查询条件查找兼容记录产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		if (vctReturn == null)
		{
			vctReturn = new Vector();
		}
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	/**
	 * Method match.
	 * 
	 * @param info
	 * @return TransCompatibilityInfo
	 * @throws SettlementDAOException
	 */
	public Vector match(TransCompatibilityInfo info) throws SettlementDAOException
	{
		Vector vctReturn = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where \n");
			strSQLBuffer.append(getMatchWhereSQL(info));
			//log.debug("=====匹配sql:" + strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctReturn = getCompatibilityMainInfoFromResultSet(rs);
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("根据匹配兼容记录产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("根据匹配兼容记录产生错误", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return vctReturn;
	}
	/**
	 * Method getWhereSQL.
	 * 
	 * @param info
	 * @return String
	 * @throws SettlementDAOException
	 */
	private String getMatchWhereSQL(TransCompatibilityInfo info) throws SettlementDAOException
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		//id
		long lId = info.getId();
		strSQLBuffer.append(" 1=1 ");
		if (lId > 0)
		{
			strSQLBuffer.append(" AND ID = '" + lId + "' \n");
		}
		strSQLBuffer.append(" AND TransactionNOID = " + info.getTransactionNOID() + " \n");
		//Number 交易类型 
		strSQLBuffer.append("AND  TransactionTypeID = " + info.getTransactionTypeID() + " \n");
		//兼容业务交易类型
		strSQLBuffer.append("AND  OperationTypeID = " + info.getOperationTypeID() + " \n");
		//Number 办事处
		strSQLBuffer.append("AND  OfficeID = " + info.getOfficeID() + " \n");
		//Number 币种
		strSQLBuffer.append("AND  CurrencyID = " + info.getCurrencyID() + " \n");
		//DateTime 起息日
		Timestamp tsInterestStartDate = info.getInterestStartDate();
		if (tsInterestStartDate == null)
		{
			strSQLBuffer.append(" AND InterestStartDate IS NULL ");
		}
		else
		{
			String strTime = tsInterestStartDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND InterestStartDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//DateTime 执行日
		Timestamp tsExecuteDate = info.getExecuteDate();
		if (tsExecuteDate == null)
		{
			strSQLBuffer.append(" AND ExecuteDate IS NULL ");
		}
		else
		{
			String strTime = tsExecuteDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND ExecuteDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//摘要
		/*String strAbstract = info.getAbstract();
		if (strAbstract.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append(" AND Abstract IS NULL \n");
		}
		else
		{
			strSQLBuffer.append(" AND  Abstract = '" + strAbstract + "' \n");
		}
		*/
		//状态
		strSQLBuffer.append(" AND StatusID = " + SETTConstant.TransactionStatus.SAVE);
		//确认办事处
		long lAffrimOfficeID = info.getAffrimOfficeID();
		if (lAffrimOfficeID > 0)
		{
			strSQLBuffer.append(" AND AffrimOfficeID = " + lAffrimOfficeID);
		}
		//录入人,当前操作用户不能等于交易信息录入用户
		strSQLBuffer.append(" AND InputUserID != " + info.getInputUserID() + " \n");
		//复核人
		long lCheckUserID = info.getCheckUserID();
		if (lCheckUserID > 0)
		{
			strSQLBuffer.append(" AND CheckUserID != " + lCheckUserID + " \n");
		}
		//签认人
		long lSignUserID = info.getSignUserID();
		if (lSignUserID > 0)
		{
			strSQLBuffer.append(" AND SignUserID != " + lSignUserID + " \n");
		}
		//确认人
		long lAffrimUserID = info.getAffrimUserID();
		if (lAffrimUserID > 0)
		{
			strSQLBuffer.append(" AND AffrimUserID != " + lAffrimUserID + " \n");
		}
		//录入日期
		/*Timestamp tsInputDate = info.getInputDate();
		if (tsInputDate == null)
		{
			strSQLBuffer.append(" AND InputDate IS NULL ");
		}
		else
		{
			String strTime = tsInputDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND InputDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		*/
		//修改日期
		Timestamp tsModifyDate = info.getModifyDate();
		if (tsModifyDate != null)
		{
			String strTime = tsModifyDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND ModifyDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
			;
		}
		//复核日期
		Timestamp tsCheckDate = info.getCheckDate();
		if (tsCheckDate != null)
		{
			String strTime = tsCheckDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND CheckDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
			;
		}
		//签认日期
		Timestamp tsSignDate = info.getSignDate();
		if (tsSignDate != null)
		{
			String strTime = tsSignDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND SignDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//确认日期
		Timestamp tsAffirmDate = info.getAffirmDate();
		if (tsAffirmDate != null)
		{
			String strTime = tsAffirmDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND AffirmDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		return strSQLBuffer.toString();
	}
	/**
	 * Method getCompatibilityMainInfoFromResultSet.
	 * 
	 * @param rs
	 * @return Vector
	 * @throws Exception
	 */
	private Vector getCompatibilityMainInfoFromResultSet(ResultSet rs) throws SettlementDAOException
	{
		Vector vctReturn = new Vector();
		TransCompatibilityInfo info = null;
		try
		{
			while (rs.next())
			{
				info = new TransCompatibilityInfo();
				info.setId(rs.getLong("ID")); //Number ID
				info.setTransNo(rs.getString("TransNo")); //Code 交易号
				info.setTransactionNOID(rs.getLong("TransactionNOID")); //Number交易ID外键：关联TransactionNo
				info.setTransactionTypeID(rs.getLong("TransactionTypeID")); //	Number交易类型
				info.setOfficeID(rs.getLong("OfficeID")); //	Number 办事处
				info.setCurrencyID(rs.getLong("CurrencyID")); //	Number 币种
				info.setInterestStartDate(rs.getTimestamp("InterestStartDate")); //	DateTime起息日
				info.setExecuteDate(rs.getTimestamp("ExecuteDate")); //	DateTime执行日
				info.setAbstract(rs.getString("Abstract")); //Abstract 摘要
				info.setStatusID(rs.getLong("StatusID")); //	Number 状态取值于常量定义：暂存、保存、复核、未签认、签认、确认
				info.setAffrimOfficeID(rs.getLong("AffrimOfficeID")); //	Number确认办事处
				info.setInputUserID(rs.getLong("InputUserID")); //	Number 录入人
				info.setCheckUserID(rs.getLong("CheckUserID")); //	Number 复核人
				info.setSignUserID(rs.getLong("SignUserID")); //	Number 签认人
				info.setAffrimUserID(rs.getLong("AffrimUserID")); //	Number 确认人
				info.setInputDate(rs.getTimestamp("InputDate")); //DateTime 录入日期
				info.setModifyDate(rs.getTimestamp("ModifyDate")); //	DateTime修改日期时间带时、分、秒的datatime类型
				info.setCheckDate(rs.getTimestamp("CheckDate")); //	DateTime复核日期
				info.setSignDate(rs.getTimestamp("SignDate")); //	DateTime 签认日期
				info.setAffirmDate(rs.getTimestamp("AffirmDate")); //DateTime确认日期
				info.setCheckAbstract(rs.getString("CheckAbstract")); //复核备注
				info.setOperationTypeID(rs.getLong("OperationTypeID")); //兼容交易类型
				vctReturn.add(info);
			}
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("查找兼容业务信息产生错误", e);
		}
		log.print("====vctReturn.size():" + vctReturn.size());
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	/**
	 * Method main.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
			Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
			TransCompatibilityInfo info = dao.findByID(1);
			/*
			 * if (info != null) { System.out.print("==============" +
			 * info.getId()); System.out.print("==============" +
			 * info.getOfficeID()); Vector vctDetail =
			 * info.getTransCompatibilityDetailInfo(); if (vctDetail != null) {
			 * for (int i = 0; i < vctDetail.size(); i++) {
			 * TransCompatibilityDetailInfo detailInfo =
			 * (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
			 * System.out.print("==============detail" + detailInfo.getId()); } } }
			 */
			System.out.print("==============" + dao.findTouchDate(1));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}