/*
 * Created on 2003-9-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transsecurities.dao;
import java.util.*;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.settlement.transsecurities.dataentity.QueryInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;
import com.iss.itreasury.settlement.util.UtilOperation;

import java.sql.*;
//import oracle.net.ano.SupervisorService;
/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_TransSecuritiesDAO extends SettlementDAO
{
	public final static int ORDERBY_TRANSACTIONNOID = 0; //交易号	
	public final static int ORDERBY_BANKNAME = 1; //开户行	
	public final static int ORDERBY_AMOUNT = 2; //金额
	public final static int ORDERBY_EXECUTEDATE = 3; //执行日	
	public final static int ORDERBY_STATUSID = 4; //状态	
	public final static int ORDERBY_ABSTRACT = 5; //摘要	
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 新增交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, TransSecuritiesInfo, 财务公司收付款交易实体类
	 * @return long, 新生成记录的标识
	 * @throws Exception 
	 * 不用try catch 不用管直接抛出即可，因为是修改结果，所以才会有此现象
	 */
	public long add(TransSecuritiesInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//利用数据库的序列号取ID;
			long id = super.getSett_TransSecuritiesID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_TransSecurities \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nSecuritiesNoticeID,sSecuritiesTransaction, \n");
			buffer.append("sExtAccountNo,sExtClientName,sRemitInBank, \n");
			buffer.append("sRemitInProvince,sRemitInCity, \n");
			buffer.append("nBankID,mReceiveAmount,mAmount, \n");
			buffer.append("sFormNo,dtDate,dtSettlementDate,dtExecute, \n");
			buffer.append("dtModify,dtInput,nInputUserID,nCheckUserID,nAbstractID, \n");
			buffer.append("sAbstract,sCheckAbstract,nStatusID, \n");
			buffer.append("sBankChequeNo,nCurrentAccountID,sCounterPartName ) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,sysdate, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?) \n");
			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getSecuritiesNoticeID());
			ps.setString(index++, info.getSecuritiesTransaction());
			ps.setString(index++, info.getExtAccountNo());
			ps.setString(index++, info.getExtClientName());
			ps.setString(index++, info.getRemitInBank());
			ps.setString(index++, info.getRemitInProvince());
			ps.setString(index++, info.getRemitInCity());
			ps.setLong(index++, info.getBankID());
			ps.setDouble(index++, info.getReceiveAmount());
			ps.setDouble(index++, info.getAmount());
			ps.setString(index++, info.getFormNo());
			ps.setTimestamp(index++, info.getDate());
			ps.setTimestamp(index++, info.getSettlementDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getBankChequeNo());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setString(index++, info.getCounterPartName());
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
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
		return lReturn;
	}
	/**
	 * 修改交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, TransSecuritiesInfo, 交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransSecuritiesInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_TransSecurities set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?, \n");
			buffer.append("nTransactionTypeID=?,nSecuritiesNoticeID=?,sSecuritiesTransaction=?, \n");
			buffer.append("sExtAccountNo=?,sExtClientName=?,sRemitInBank=?, \n");
			buffer.append("sRemitInProvince=?,sRemitInCity=?, \n");
			buffer.append("nBankID=?,mReceiveAmount=?,mAmount=?, \n");
			buffer.append("sFormNo=?,dtDate=?,dtSettlementDate=?,dtExecute=?, \n");
			buffer.append("dtModify=sysdate,dtInput=?,nInputUserID=?,nCheckUserID=?,nAbstractID=?, \n");
			buffer.append("sAbstract=?,sCheckAbstract=?,nStatusID=?, \n");
			buffer.append("sBankChequeNo=?,nCurrentAccountID=?,sCounterPartName=? \n");
			buffer.append("where ID=? \n");
			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getSecuritiesNoticeID());
			ps.setString(index++, info.getSecuritiesTransaction());
			ps.setString(index++, info.getExtAccountNo());
			ps.setString(index++, info.getExtClientName());
			ps.setString(index++, info.getRemitInBank());
			ps.setString(index++, info.getRemitInProvince());
			ps.setString(index++, info.getRemitInCity());
			ps.setLong(index++, info.getBankID());
			ps.setDouble(index++, info.getReceiveAmount());
			ps.setDouble(index++, info.getAmount());
			ps.setString(index++, info.getFormNo());
			ps.setTimestamp(index++, info.getDate());
			ps.setTimestamp(index++, info.getSettlementDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getBankChequeNo());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setString(index++, info.getCounterPartName());
			ps.setLong(index++, info.getID());
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
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
		return lReturn;
	}
	/**
	 * 根据标识查询交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return TransSecuritiesInfo, 交易实体类
	 * @throws Exception
	 */
	public TransSecuritiesInfo findByID(long lID) throws Exception
	{
		TransSecuritiesInfo info = new TransSecuritiesInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from sett_TransSecurities where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = getSecurities(info, rs);
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
		return info;
	}
	/**
	 * 判断报单号是否重复方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return TransSecuritiesInfo, 交易实体类
	 * @throws Exception
	 */
	public long checkFromNo(TransSecuritiesInfo info) throws Exception
	{
		long rtnFlag = SETTConstant.BooleanValue.ISFALSE;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from sett_TransSecurities where sFormNo=? and id<>? and dtExecute=?";
			ps = con.prepareStatement(strSQL);
			ps.setString(1, info.getFormNo());
			ps.setLong(2, info.getID());
			ps.setTimestamp(3, info.getExecuteDate());
			rs = ps.executeQuery();
			if (rs.next())
			{
				rtnFlag = SETTConstant.BooleanValue.ISTRUE;
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
		return rtnFlag;
	}
	/**
	 * 根据交易号查询交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param  strTransNo String , 交易号
	 * @return FixedOpenInfo, 交易实体类
	 * @throws Exception
	 */
	public TransSecuritiesInfo findByTransNo(String strTransNo) throws Exception
	{
		TransSecuritiesInfo info = new TransSecuritiesInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from sett_TransSecurities where sTransNo=? ";
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = getSecurities(info, rs);
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
		return info;
	}
	/**
	 * 修改交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 本金交易标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_TransSecurities set nStatusID=?,dtmodify=sysdate where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
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
		return lReturn;
	}
	/**
	 * 设置定期交易结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private TransSecuritiesInfo getSecurities(TransSecuritiesInfo info, ResultSet rs) throws Exception
	{
		info = new TransSecuritiesInfo();
		try
		{
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setSecuritiesNoticeID(rs.getLong("nSecuritiesNoticeID"));
			info.setSecuritiesTransaction(rs.getString("sSecuritiesTransaction"));
			info.setExtAccountNo(rs.getString("sExtAccountNo"));
			info.setExtClientName(rs.getString("sExtClientName"));
			info.setRemitInBank(rs.getString("sRemitInBank"));
			info.setRemitInProvince(rs.getString("sRemitInProvince"));
			info.setRemitInCity(rs.getString("sRemitInCity"));
			info.setBankID(rs.getLong("nBankID"));
			info.setReceiveAmount(rs.getDouble("mReceiveAmount"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setFormNo(rs.getString("sFormNo"));
			info.setDate(rs.getTimestamp("dtDate"));
			info.setSettlementDate(rs.getTimestamp("dtSettlementDate"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setBankChequeNo(rs.getString("sBankChequeNo"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setCounterPartName(rs.getString("sCounterPartName"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;
	}
	/**
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query = "";
			if (info.getStatus() != null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}
			//排序条件
			String order = getOrderString(info);
			//业务处理
			if (info.getTypeID() == 0)
			{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
				buffer.append("select * \n");
				buffer.append("from sett_TransSecurities \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("(" + query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append("" + order + "");
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getUserID());
				rs = ps.executeQuery();
				while (rs.next())
				{
					TransSecuritiesInfo resultInfo = new TransSecuritiesInfo();
					resultInfo = getSecurities(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			}
			else if (info.getTypeID() == 1) //业务复核
			{
				buffer.append("select * \n");
				buffer.append("from sett_TransSecurities \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("(" + query + ") \n");
				buffer.append("and NSECURITIESNOTICEID not in (select id from SEC_NOTICEFORM where nStatusID =?)  \n");
				buffer.append("and nCheckUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append("" + order + "");
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, SECConstant.NoticeFormStatus.POSTED);
				ps.setLong(index++, info.getUserID());
				rs = ps.executeQuery();
				while (rs.next())
				{
					TransSecuritiesInfo resultInfo = new TransSecuritiesInfo();
					resultInfo = getSecurities(resultInfo, rs);
					arrResult.add(resultInfo);
				}
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
		return arrResult;
	}
	private String getQueryString(QueryInfo info)
	{
		String query;
		query = "nStatusID=";
		for (int i = 0; i < info.getStatus().length; i++)
		{
			if (i < info.getStatus().length - 1)
			{
				query = query + info.getStatus()[i] + " or nStatusID=";
			}
			else
			{
				query = query + info.getStatus()[i];
			}
		}
		return query;
	}
	private String getOrderString(QueryInfo info)
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSACTIONNOID :
				{
					order = " ORDER BY sTransNo ";
				}
				break;
			case ORDERBY_BANKNAME :
				{
					order = " ORDER BY nBankID ";
				}
				break;
			case ORDERBY_AMOUNT :
				{
					order = " ORDER BY mAmount ";
				}
				break;
			case ORDERBY_ABSTRACT :
				{
					order = " ORDER BY sAbstract ";
				}
				break;
			case ORDERBY_STATUSID :
				{
					order = " ORDER BY nStatusID ";
				}
				break;
			default :
				{
					isNeedOrderBy = false;
				}
				break;
		}
		if (isNeedOrderBy)
		{
			if (info.isDesc())
				order = order + " DESC \n";
			else
				order = order + " ASC \n";
		}
		else
		{
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}
	/**
	 * 复核匹配的方法：
	 * 逻辑说明：
	 * 
	 * @param TransSecuritiesInfo,交易复核匹配查询条件实体类
	 * @return Collection ,包含TransSecuritiesInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransSecuritiesInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = null;
			log.debug(UtilOperation.dataentityToString(info));
			//财务公司收款
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.SECURITIESRECEIVE)
			{
				buffer = new StringBuffer();
				if (info.getFormNo().equals(""))
				{
					buffer.append("select * from sett_TransSecurities \n");
					buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					buffer.append("and nInputUserID <>? and nSecuritiesNoticeID=? and nTransActionTypeID=? \n");
					buffer.append("and nBankID=? and sFormNo is null and mAmount=? \n");
					buffer.append("order by ID \n");
					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());
					int index = 1;
					ps.setLong(index++, info.getOfficeID());
					ps.setLong(index++, info.getCurrencyID());
					ps.setLong(index++, info.getStatusID());
					ps.setLong(index++, info.getInputUserID());
					ps.setLong(index++, info.getSecuritiesNoticeID());
					ps.setLong(index++, info.getTransactionTypeID());
					ps.setLong(index++, info.getBankID());
					ps.setDouble(index++, info.getAmount());
				}
				else
				{
					buffer.append("select * from sett_TransSecurities \n");
					buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					buffer.append("and nInputUserID <>? and nSecuritiesNoticeID=? and nTransActionTypeID=? \n");
					buffer.append("and nBankID=? and sFormNo=? and mAmount=? \n");
					buffer.append("order by ID \n");
					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());
					int index = 1;
					ps.setLong(index++, info.getOfficeID());
					ps.setLong(index++, info.getCurrencyID());
					ps.setLong(index++, info.getStatusID());
					ps.setLong(index++, info.getInputUserID());
					ps.setLong(index++, info.getSecuritiesNoticeID());
					ps.setLong(index++, info.getTransactionTypeID());
					ps.setLong(index++, info.getBankID());
					ps.setString(index++, info.getFormNo());
					ps.setDouble(index++, info.getAmount());
				}
				rs = ps.executeQuery();
				while (rs.next())
				{
					TransSecuritiesInfo depositInfo = new TransSecuritiesInfo();
					depositInfo = getSecurities(depositInfo, rs);
					arrResult.add(depositInfo);
				}
			}
			//财务公司付款
			else if (info.getTransactionTypeID() == SETTConstant.TransactionType.SECURITIESPAY)
			{
				buffer = new StringBuffer();
				buffer.append("select * from sett_TransSecurities \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append("and nInputUserID <>? and nSecuritiesNoticeID=? and nTransActionTypeID=? \n");
				buffer.append("and nBankID=? \n");
				buffer.append("order by ID \n");
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getStatusID());
				ps.setLong(index++, info.getInputUserID());
				ps.setLong(index++, info.getSecuritiesNoticeID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getBankID());
				rs = ps.executeQuery();
				while (rs.next())
				{
					TransSecuritiesInfo depositInfo = new TransSecuritiesInfo();
					depositInfo = getSecurities(depositInfo, rs);
					arrResult.add(depositInfo);
				}
			}
			//委托理财付款/债券承销付款
			else if (info.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_PAY || info.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_PAY)
			{
				buffer = new StringBuffer();
				buffer.append("select * from sett_TransSecurities \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append("and nInputUserID <>? and nSecuritiesNoticeID=? and nTransActionTypeID=? \n");
				buffer.append("and nBankID=? \n");
				buffer.append("and nCurrentAccountID=? \n");
				buffer.append("order by ID \n");
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getStatusID());
				ps.setLong(index++, info.getInputUserID());
				ps.setLong(index++, info.getSecuritiesNoticeID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getBankID());
				ps.setLong(index++, info.getCurrentAccountID());
				rs = ps.executeQuery();
				while (rs.next())
				{
					TransSecuritiesInfo depositInfo = new TransSecuritiesInfo();
					depositInfo = getSecurities(depositInfo, rs);
					arrResult.add(depositInfo);
				}
			}
			//委托理财收款/债券承销收款
			else if (info.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE || info.getTransactionTypeID() == SETTConstant.TransactionType.SEC_ZQCX_RECEIVE)
			{
				buffer = new StringBuffer();
				buffer.append("select * from sett_TransSecurities \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append("and nInputUserID <>? and nSecuritiesNoticeID=? and nTransActionTypeID=? \n");
				buffer.append("and nBankID=? \n");
				if (info.getFormNo() != null && info.getFormNo().length()>0)
				{
					buffer.append("and sFormNo='"+info.getFormNo()+"' \n");	
				}
				else
				{
					buffer.append("and sFormNo is null \n");
				}
				buffer.append("and nCurrentAccountID=? \n");
				buffer.append("and mAmount=? \n");
				
				buffer.append("order by ID \n");
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getStatusID());
				ps.setLong(index++, info.getInputUserID());
				ps.setLong(index++, info.getSecuritiesNoticeID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getBankID());
				ps.setLong(index++, info.getCurrentAccountID());
				ps.setDouble(index++,info.getAmount());
				rs = ps.executeQuery();
				while (rs.next())
				{
					TransSecuritiesInfo depositInfo = new TransSecuritiesInfo();
					depositInfo = getSecurities(depositInfo, rs);
					arrResult.add(depositInfo);
				}
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
		return arrResult;
	}
}
