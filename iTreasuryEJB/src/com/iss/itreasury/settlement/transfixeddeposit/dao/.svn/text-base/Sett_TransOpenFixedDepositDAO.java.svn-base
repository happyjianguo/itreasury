/*
 * Created on 2003-9-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.util.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;
import java.sql.*;

//import oracle.net.ano.SupervisorService;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_TransOpenFixedDepositDAO extends SettlementDAO 
{
	public final static int ORDERBY_TRANSACTIONNOID = 0;  //交易号
	public final static int ORDERBY_ACCOUNTNO = 1;     //定期账户号
	public final static int ORDERBY_CLIENTNAME = 2;    //定期客户名称
	public final static int ORDERBY_DEPOSITNO = 3;    //定期存款单据号
	public final static int ORDERBY_CURRENTACCOUNTNO = 4;    //活期账户号	
	public final static int ORDERBY_BILLNO = 5;    //票据号	
	public final static int ORDERBY_CONSIGNVOUCHERNO = 6;  //委托付款凭证号
	public final static int ORDERBY_BANKNAME = 7;    //开户行	
	public final static int ORDERBY_AMOUNT = 8;            //金额
	public final static int ORDERBY_STARTDATE = 9;   //起始日
	public final static int ORDERBY_ENDDATE = 10;     //到期日
	public final static int ORDERBY_STATUSID = 11;    //状态	
	public final static int ORDERBY_ABSTRACT = 12;     //摘要	
	
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 新增定期（通知）开立交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedOpenInfo, 定期（通知）开立交易实体类
	 * @return long, 新生成记录的标识
	 * @throws Exception 
	 * 不用try catch 不用管直接抛出即可，因为是修改结果，所以才会有此现象
	 */
	public long add(TransFixedOpenInfo info) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//利用数据库的序列号取ID;
			long id = super.getSett_TransFixedOpenID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_TransOpenFixedDeposit \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nClientID,nAccountID, \n");
			buffer.append("sDepositNO,nCertificationBankID,mRate, \n");
			buffer.append("dtStart,dtEnd,nDepositTerm,nInterestPlanID, \n");
			buffer.append("nNoticeDay,nCurrentAccountID, \n");
			buffer.append("nBankID,nCashFlowID,mAmount, \n");
			buffer.append("dtInterestStart,dtExecute, \n");
			buffer.append("dtModify,dtInput,nInputUserID,nCheckUserID,nAbstractID, \n");
			buffer.append("sAbstract,sCheckAbstract,nStatusID, \n");
			buffer.append("sInstructionNo,sConsignVoucherNo,sConsignPassword, \n");
			buffer.append("sBillNo,nBillTypeID,nBillBankID, \n");
			buffer.append("nExtAccountID, \n");
			buffer.append("sExtBankNO,sSealNO,nSealBankID,isautocontinue ,autocontinuetype, autocontinueaccountid ) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,sysdate,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?,?,?) \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());	
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getClientID());
			ps.setLong(index++, info.getAccountID());
			ps.setString(index++, info.getDepositNo());
			ps.setLong(index++, info.getCertificationBankID());
			ps.setDouble(index++, info.getRate());
			ps.setTimestamp(index++, info.getStartDate());
			ps.setTimestamp(index++, info.getEndDate());
			ps.setLong(index++, info.getDepositTerm());
			ps.setLong(index++, info.getInterestPlanID());
			ps.setLong(index++, info.getNoticeDay());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setLong(index++, info.getBankID());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getInstructionNo());
			ps.setString(index++, info.getConsignVoucherNo());
			ps.setString(index++, info.getConsignPassword());
			ps.setString(index++, info.getBillNo());
			ps.setLong(index++, info.getBillTypeID());
			ps.setLong(index++, info.getBillBankID());
			ps.setLong(index++, info.getExtAcctID());
			ps.setString(index++, info.getExtBankNo());
			ps.setString(index++, info.getSealNo());
			ps.setLong(index++, info.getSealBankID());
			ps.setLong(index++, info.getIsAutoContinue());
			ps.setLong(index++, info.getAutocontinuetype());
			ps.setLong(index++, info.getAutocontinueaccountid());


			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 修改定期（通知）开立交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedOpenInfo, 定期（通知）开立交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransFixedOpenInfo info) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_TransOpenFixedDeposit set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append("nTransactionTypeID=?,nClientID=?,nAccountID=?,sDepositNO=?,\n");
			buffer.append("nCertificationBankID=?,mRate=?,dtStart=?,dtEnd=?,\n");
			buffer.append("nDepositTerm=?,nInterestPlanID=?,nNoticeDay=?,\n");
			buffer.append("nCurrentAccountID=?,nBankID=?,nCashFlowID=?,mAmount=?,\n");
			buffer.append("dtInterestStart=?,dtExecute=?,dtModify=sysdate,dtInput=?,\n");
			buffer.append("nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,sCheckAbstract=?,\n");
			buffer.append("nStatusID=?,sInstructionNo=?,sConsignVoucherNo=?,sConsignPassword=?,\n");
			buffer.append("sBillNo=?,nBillTypeID=?,nBillBankID=?,nExtAccountID=?,\n");
			buffer.append("sExtBankNO=?,sSealNO=?,\n");
			buffer.append("nSealBankID=?,\n ");
			buffer.append("isautocontinue=?,\n ");
			buffer.append("autocontinuetype=?,\n ");
			buffer.append("autocontinueaccountid=?\n ");
			buffer.append("where ID=? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getClientID());
			ps.setLong(index++, info.getAccountID());
			ps.setString(index++, info.getDepositNo());
			ps.setLong(index++, info.getCertificationBankID());
			ps.setDouble(index++, info.getRate());
			ps.setTimestamp(index++, info.getStartDate());
			ps.setTimestamp(index++, info.getEndDate());
			ps.setLong(index++, info.getDepositTerm());
			ps.setLong(index++, info.getInterestPlanID());
			ps.setLong(index++, info.getNoticeDay());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setLong(index++, info.getBankID());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getInstructionNo());
			ps.setString(index++, info.getConsignVoucherNo());
			ps.setString(index++, info.getConsignPassword());
			ps.setString(index++, info.getBillNo());
			ps.setLong(index++, info.getBillTypeID());
			ps.setLong(index++, info.getBillBankID());
			ps.setLong(index++, info.getExtAcctID());
			ps.setString(index++, info.getExtBankNo());
			ps.setString(index++, info.getSealNo());
			ps.setLong(index++, info.getSealBankID());
			ps.setLong(index++, info.getIsAutoContinue());
			ps.setLong(index++, info.getAutocontinuetype());
			ps.setLong(index++, info.getAutocontinueaccountid());

			ps.setLong(index++, info.getID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 根据标识查询定期（通知）交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedOpenInfo findByID(long lID) throws Exception {
		TransFixedOpenInfo info = new TransFixedOpenInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
	 * 根据交易号查询定期（通知）交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param  strTransNo String , 交易号
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedOpenInfo findByTransNo(String strTransNo) throws Exception {
		TransFixedOpenInfo info = new TransFixedOpenInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where sTransNo=? ";
			ps = con.prepareStatement(strSQL);
			ps.setString(1,strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	
	/**
	 * 根据交易号查询定期（通知）交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param  strTransNo String , 交易号
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedOpenInfo findByDepositNo(String DepositNo) throws Exception {
		TransFixedOpenInfo info = new TransFixedOpenInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where sdepositno=? ";
			ps = con.prepareStatement(strSQL);
			ps.setString(1,DepositNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	
	//add by qhzhou 2007.6.22
	/**
	 * 根据旧定期存单号查询定期（通知）部分提前支取新开立的存单交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param  strTransNo String , 交易号
	 * @return FixedOpenInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedOpenInfo findByOldDepositNo(String oldDepositNo) throws Exception {
		TransFixedOpenInfo info = null;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where sbillno=? and nstatusid <> ?";
			ps = con.prepareStatement(strSQL);
			ps.setString(1,oldDepositNo);
			ps.setLong(2, 0);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	
	/**
		 * 根据条件查询存款单据号是否重复的方法：
		 * 逻辑说明：
		 * 
		 * @param FixedOpenInfo, 定期（通知）交易实体类
		 * @return boolean false 重复
		 * @throws Exception
		 */
		public boolean checkDepositNo(TransFixedOpenInfo info) throws Exception 
		{			
			Connection con = getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			boolean rtnFlg = true;
			try 
			{	
				StringBuffer buffer = new StringBuffer();
				buffer.append("select sDepositNo from sett_TransOpenFixedDeposit where \n");
				buffer.append("sDepositNo=? and nAccountID=? and ID<>? and \n");
				buffer.append("nStatusID <> ? \n");
				buffer.append("union select sDepositNo from sett_TransFixedContinue where \n");
				buffer.append("sNewDepositNo=? and nAccountID=? and \n");
				buffer.append("nStatusID <> ?");
				
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index=1;
				ps.setString(index++, info.getDepositNo());
				ps.setLong(index++, info.getAccountID());
				ps.setLong(index++, info.getID());
				ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);
				ps.setString(index++, info.getDepositNo());
				ps.setLong(index++, info.getAccountID());				
				ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);
				rs = ps.executeQuery();
				if (rs.next()) 
				{
					rtnFlg=false;
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
	 * 根据条件判断定期交易是否重复的方法：
	 * 逻辑说明：
	 * 
	 * @param FixedContinueInfo searchInfo , 定期交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 */
	public boolean checkIsDuplicate(TransFixedContinueInfo searchInfo)
		throws Exception {
		boolean rtnFlg = true;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL =
				"select * from sett_TransOpenFixedDeposit where id=? ";
			ps = con.prepareStatement(strSQL);
			//ps.setLong(1,lID);			
			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlg = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}
	/**
	 * 修改定期（通知）开立交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 本金交易标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception {
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(
				"update sett_TransOpenFixedDeposit set nStatusID=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = lID;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
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
	private TransFixedOpenInfo getOpenDeposit(
		TransFixedOpenInfo info,
		ResultSet rs)
		throws Exception {
		info = new TransFixedOpenInfo();
		try {
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setClientID(rs.getLong("nClientID"));
			info.setAccountID(rs.getLong("nAccountID"));
			info.setDepositNo(rs.getString("sDepositNo"));
			info.setCertificationBankID(rs.getLong("nCertificationBankID"));
			info.setRate(rs.getDouble("mRate"));
			info.setStartDate(rs.getTimestamp("dtStart"));
			info.setEndDate(rs.getTimestamp("dtEnd"));
			info.setDepositTerm(rs.getLong("nDepositTerm"));
			info.setInterestPlanID(rs.getLong("nInterestPlanID"));
			info.setNoticeDay(rs.getLong("nNoticeDay"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setConsignVoucherNo(rs.getString("sConsignVoucherNo"));
			info.setConsignPassword(rs.getString("sConsignPassword"));
			info.setBillNo(rs.getString("sBillNo"));
			info.setBillTypeID(rs.getLong("nBillTypeID"));
			info.setBillBankID(rs.getLong("nBillBankID"));
			info.setExtAcctID(rs.getLong("nExtAccountID"));
			info.setExtBankNo(rs.getString("sExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			info.setIsAutoContinue(rs.getLong("IsAutoContinue"));
			info.setAutocontinuetype(rs.getLong("Autocontinuetype"));
			info.setAutocontinueaccountid(rs.getLong("Autocontinueaccountid"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	//	added by qhzhou 2007.6.26
	//开立交易的链接查找,过滤由于定期支取生成的开立存单
	/**
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @param isFilt  ,是否过滤由于定期支取生成的开立存单
	 * @return Collection ,包含FixedOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info,boolean isFilt)	throws Exception 
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
			String query ="";
			if(info.getStatus()!=null)
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
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
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
					TransFixedOpenInfo resultInfo = new TransFixedOpenInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nCheckUserID=? and dtExecute=? \n");
				if(isFilt){
					buffer.append("and sBillNo is null \n");
				}
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransFixedOpenInfo resultInfo = new TransFixedOpenInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
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
	/**
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info)	throws Exception 
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
			String query ="";
			if(info.getStatus()!=null)
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
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
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
					TransFixedOpenInfo resultInfo = new TransFixedOpenInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from sett_TransOpenFixedDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and nCheckUserID=? and dtExecute=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransFixedOpenInfo resultInfo = new TransFixedOpenInfo();
					
					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
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
	private String getQueryString(QueryByStatusConditionInfo info) 
	{
		String query;
		query ="nStatusID=";
		for(int i=0;i<info.getStatus().length;i++)
		{									
			if(i<info.getStatus().length -1)
			{	
								
				query= query+ info.getStatus()[i] + " or nStatusID=";
			}
			else
			{
				query= query+ info.getStatus()[i];
			}
		}	
		return query;
	}
	private String getOrderString(QueryByStatusConditionInfo info) 
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSACTIONNOID :
			{
				order=" ORDER BY sTransNo ";					
			}	
				break;
			case ORDERBY_ACCOUNTNO :
			{
				order=" ORDER BY nAccountID ";					
			}
				break;
			case ORDERBY_CLIENTNAME :
			{
				order=" ORDER BY nClientID ";					
			}
				break;				
			case ORDERBY_DEPOSITNO :
			{
				order=" ORDER BY sDepositNo ";					
			}
				break;
			case ORDERBY_CURRENTACCOUNTNO :
			{
				order=" ORDER BY nCurrentAccountID ";					
			}
				break;				
			case ORDERBY_CONSIGNVOUCHERNO :
			{
				order=" ORDER BY sConsignVoucherNo ";					
			}
				break;
			case ORDERBY_BILLNO :
			{
				order=" ORDER BY sBillNo ";					
			}
				break;	
			case ORDERBY_BANKNAME :
			{
				order=" ORDER BY nBankID ";					
			}
				break;			
			case ORDERBY_AMOUNT :
			{
				order=" ORDER BY mAmount ";					
			}
				break;
			case ORDERBY_STARTDATE :
			{
				order=" ORDER BY dtStart ";					
			}
				break;
			case ORDERBY_ENDDATE :
			{
				order=" ORDER BY dtEnd ";					
			}
				break;
			case ORDERBY_ABSTRACT :
			{
				order=" ORDER BY sAbstract ";					
			}
				break;
			case ORDERBY_STATUSID :
			{
				order=" ORDER BY nStatusID ";					
			}
				break;				
			default :
			{
				isNeedOrderBy = false;
			}
				break;
		}
		if(isNeedOrderBy)
		{
			if (info.isDesc())
				order= order +" DESC \n";
			else
				order= order +" ASC \n";		
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
	 * @param FixedOpenInfo,定期（通知）存款交易复核匹配查询条件实体类
	 * @return Collection ,包含FixedOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransFixedOpenInfo info) throws Exception {
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;

				/**定期开立
				*基本信息
				*  定 期账户号,定期客户名称，定期存款单据号（不要），
				* 起始日期，存款期限，利率
				*  // 存款来源
				* 活期账户号，	* 开户行，
				* 金额 
				* 缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者
				*/
				if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENFIXEDDEPOSIT) 
				{
					buffer = new StringBuffer();
					buffer.append("select * from sett_TransOpenFixedDeposit \n");
					buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					buffer.append("and nInputUserID <>? and nAccountID=? and \n");
					buffer.append("dtStart=? and nDepositTerm=? and mRate=? and \n");
					buffer.append("nCurrentAccountID=? \n");	
					//buffer.append("nCurrentAccountID=? and sConsignVoucherNo=?  \n");
					buffer.append("and nBankID=? and nCashFlowID=? and mAmount=?\n");
					buffer.append("and isautocontinue=?\n");
					buffer.append("and autocontinuetype=? \n");
					buffer.append("and autocontinueaccountid=? \n");
					buffer.append("order by ID \n");

					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());

					int index = 1;
					ps.setLong(index++, info.getOfficeID());
					log.info("info.getOfficeID():"+info.getOfficeID());
					ps.setLong(index++, info.getCurrencyID());
					log.info("info.getCurrencyID():"+info.getCurrencyID());
					ps.setLong(index++, info.getStatusID());
					log.info("info.getStatusID():"+info.getStatusID());
					ps.setLong(index++, info.getInputUserID());
					log.info("info.getInputUserID():"+info.getInputUserID());
					ps.setLong(index++, info.getAccountID());
					log.info("info.getAccountID():"+info.getAccountID());
					//ps.setLong(index++, info.getClientID());
					ps.setTimestamp(index++, info.getStartDate());
					log.info("info.getStartDate():"+info.getStartDate());
					ps.setLong(index++, info.getDepositTerm());
					log.info("info.getDepositTerm():"+info.getDepositTerm());
					ps.setDouble(index++, info.getRate());
					log.info("info.getRate():"+info.getRate());
					ps.setLong(index++, info.getCurrentAccountID());
					log.info("info.getCurrentAccountID():"+info.getCurrentAccountID());
					//ps.setString(index++, info.getConsignVoucherNo());
					ps.setLong(index++, info.getBankID());
					log.info("info.getBankID():"+info.getBankID());
					ps.setLong(index++, info.getCashFlowID());
					log.info("info.getCashFlowID():"+info.getCashFlowID());
					ps.setDouble(index++, info.getAmount());
					log.info("info.getAmount():"+info.getAmount());
					ps.setLong(index++, info.getIsAutoContinue());
					log.info("info.getIsAutoContinue():"+info.getAmount());
					ps.setLong(index++, info.getAutocontinuetype());
					log.info("info.getautocontinuetype():"+info.getAutocontinuetype());
					ps.setLong(index++, info.getAutocontinueaccountid());
					log.info("info.getAutocontinueaccountid():"+info.getAutocontinueaccountid());
					rs = ps.executeQuery();
					while (rs.next()) {
						TransFixedOpenInfo depositInfo =
							new TransFixedOpenInfo();
						depositInfo = getOpenDeposit(depositInfo, rs);
						arrResult.add(depositInfo);
					}

				}

				/**通知开立
				 * 
				 *基本信息
				 *通知存款账户号
				 *起始日期
				 *存款类型
				 *存款来源
				 *活期账户号，
				 *开户行，
				 *金额，执行日
				 *缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者
				 */
				else if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENNOTIFYACCOUNT) 
				{
					buffer = new StringBuffer();
					buffer.append("select * from sett_TransOpenFixedDeposit \n");
					buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					buffer.append("and nInputUserID <>? and nAccountID=? and \n");
					buffer.append("dtStart=?  and nNoticeDay=? and nCurrentAccountID=? \n");
					buffer.append("and nBankID=? and \n");	
					//buffer.append("and sConsignVoucherNo=? and nBankID=? and \n");
					//buffer.append("nCashFlowID=? and  mAmount=? and dtExecute=?\n");
					buffer.append("nCashFlowID=? and  mAmount=? \n");
					buffer.append("order by ID \n");

					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());

					int index = 1;
					ps.setLong(index++, info.getOfficeID());
					ps.setLong(index++, info.getCurrencyID());
					ps.setLong(index++, info.getStatusID());
					ps.setLong(index++, info.getInputUserID());
					ps.setLong(index++, info.getAccountID());
					//ps.setLong(index++, info.getClientID());
					//ps.setString(index++, info.getDepositNo());
					ps.setTimestamp(index++, info.getStartDate());
					ps.setLong(index++, info.getNoticeDay());
					ps.setLong(index++, info.getCurrentAccountID());
					//ps.setString(index++, info.getConsignVoucherNo());
					ps.setLong(index++, info.getBankID());
					ps.setLong(index++, info.getCashFlowID());
					ps.setDouble(index++, info.getAmount());
					//ps.setTimestamp(index++, info.getExecuteDate());
					rs = ps.executeQuery();
					while (rs.next()) {
						TransFixedOpenInfo depositInfo =
							new TransFixedOpenInfo();
						depositInfo = getOpenDeposit(depositInfo, rs);
						arrResult.add(depositInfo);
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {

				cleanup(rs);
				cleanup(ps);
				cleanup(con);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;
	}
	//定期开立-链接查询
	public String queryTransFixedOpenDeposit(QueryByStatusConditionInfo qInfo){

		StringBuffer sbSQL= new StringBuffer();
		
		//业务处理
		if (qInfo.getTypeID() == 0) 
		{
			//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
			sbSQL.append("select aa.* \n");
			sbSQL.append("from sett_TransOpenFixedDeposit aa \n");
			sbSQL.append("where \n");
			sbSQL.append("aa.nOfficeID ="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and aa.nCurrencyID ="+qInfo.getCurrencyID()+" \n");
			if(qInfo.getTransactionTypeID()>=0){
				sbSQL.append("and aa.nTransActionTypeID ="+qInfo.getTransactionTypeID()+" \n");
			}
			if(qInfo.getStatus()!=null)
			{
				//状态查询条件
				String query ="";
				query = getQueryString(qInfo);
				sbSQL.append(" and ("+ query + ") \n");
			}
			sbSQL.append("and aa.nInputUserID ="+qInfo.getUserID()+" \n");
		} 
		else if (qInfo.getTypeID() == 1) //业务复核
			{			
			sbSQL.append("select aa.* \n");
			sbSQL.append("from sett_TransOpenFixedDeposit aa \n");
			sbSQL.append("where \n");
			sbSQL.append("aa.nOfficeID ="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and aa.nCurrencyID ="+qInfo.getCurrencyID()+" \n");
			if(qInfo.getTransactionTypeID()>=0){
				sbSQL.append("and aa.nTransActionTypeID ="+qInfo.getTransactionTypeID()+" \n");
			}
			if(qInfo.getStatus()!=null)
			{
				//状态查询条件
				String query ="";
				query = getQueryString(qInfo);
				sbSQL.append(" and ("+ query + ") \n");
			}	
			sbSQL.append("and aa.nCheckUserID ="+qInfo.getUserID()+" \n");
			sbSQL.append("and aa.dtExecute = TO_DATE('" + DataFormat.getDateString(qInfo.getDate()) + "','yyyy-mm-dd') \n");
		}
		return sbSQL.toString();
	}
	
}
