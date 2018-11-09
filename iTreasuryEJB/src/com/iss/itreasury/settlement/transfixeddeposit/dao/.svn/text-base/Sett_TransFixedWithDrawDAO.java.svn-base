/*
 * Created on 2003-9-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

//import oracle.net.ano.SupervisorService;

/**
 * @author xrli
 *
 * To change the template for this generated type
 comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_TransFixedWithDrawDAO extends SettlementDAO
{
	public final static int ORDERBY_TRANSACTIONNOID = 0;  //交易号
	public final static int ORDERBY_ACCOUNTNO = 1;     //定期账户号
	public final static int ORDERBY_CLIENTNAME = 2;    //定期客户名称
	public final static int ORDERBY_DEPOSITNO = 3;    //定期存款单据号
	public final static int ORDERBY_CURRENTACCOUNTNO = 4;    //本金转至活期账户号	
	public final static int ORDERBY_INTERESTCURRENTACCOUNTNO = 5;    //利息转至活期账户号
	public final static int ORDERBY_AMOUNT = 6;            //金额
	public final static int ORDERBY_INTEREST = 7;            //利息
	public final static int ORDERBY_INTERESTSTARTDATE = 8;   //起息日
	public final static int ORDERBY_EXECUTEDATE = 9;     //执行日
	public final static int ORDERBY_STATUSID = 10;    //状态
	public final static int ORDERBY_DRAWAMOUNT = 11;  //支取金额
	public final static int ORDERBY_ABSTRACT = 12;     //摘要	
		
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 新增定期支取/转活期及通知支取交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedDrawInfo, 定期（通知）支取交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(TransFixedDrawInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();		
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{			
			//利用数据库的序列号取ID;
			long id= super.getSett_TransFixedDrawID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_TransFixedWithDraw \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nClientID,nAccountID, \n");
			buffer.append("sDepositNO,nCertificationBankID,mRate, \n");			
			buffer.append("dtStart,dtEnd,nDepositTerm,nInterestPlanID, \n");
			buffer.append("nNoticeDay,nSubAccountID,nCurrentAccountID, \n");
			buffer.append("nPayTypeID,nBankID,sExtAccountNo,sExtClientName,sRemitInBank, \n");
			buffer.append("sRemitInProvince,sRemitInCity,nCashFlowID,mAmount, \n");
			buffer.append("mDrawAmount,mDepositBalance,dtInterestStart,dtExecute, \n");
			buffer.append("sCapitalExtBankNo,sSealNo,nSealBankID,mPreDrawInterest, \n");
			buffer.append("mStrikePreDrawInterest,mWithDrawInterest,nIsPreDraw,nIsTally, \n");
			buffer.append("mTotalUnpayInterest,mPayableInterest,mCurrentInterest,mOtherInterest, \n");
			buffer.append("nReceiveInterestAccountID,nInterestPayTypeID,nInterestBankID,sInterestExtAccountNo, \n");
			buffer.append("sInterestExtClientName,sInterestRemitInBank,sInterestRemitInProvince,sInterestRemitInCity, \n");
			buffer.append("nInterestCashFlowID,nConfirmOfficeID,sInterestExtBankNo,nCapitalAndInterestDealWay, \n");			
			buffer.append("dtModify,dtInput,nInputUserID,nCheckUserID,nAbstractID, \n");
			buffer.append("sAbstract,sCheckAbstract,nStatusID, \n");
			buffer.append("sInstructionNo,ADVANCERATE,isautocontinue,autocontinuetype,autocontinueaccountid ) \n");  //added by wjliu --20070727 增加活期利率
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,sysdate,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?) \n");
			
			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());			
			
			int index =1;			
			ps.setLong(index++,info.getID());			
			ps.setLong(index++,info.getOfficeID());
			ps.setLong(index++,info.getCurrencyID());
			ps.setString(index++,info.getTransNo());				
			ps.setLong(index++,info.getTransactionTypeID());
			ps.setLong(index++,info.getClientID());
			ps.setLong(index++,info.getAccountID());
			ps.setString(index++,info.getDepositNo());
			ps.setLong(index++,info.getCertificationBankID());
			ps.setDouble(index++,info.getRate());
			ps.setTimestamp(index++,info.getStartDate());
			ps.setTimestamp(index++,info.getEndDate());
			ps.setLong(index++,info.getDepositTerm());
			ps.setLong(index++,info.getInterestPlanID());
			ps.setLong(index++,info.getNoticeDay());			
			ps.setLong(index++,info.getSubAccountID());
			ps.setLong(index++,info.getCurrentAccountID());
			ps.setLong(index++,info.getPayTypeID());
			ps.setLong(index++,info.getBankID());
			//ps.setLong(index++,info.getExtAcctID());			
			ps.setString(index++,info.getExtAcctNo());
			ps.setString(index++,info.getExtClientName());
			ps.setString(index++,info.getRemitInBank());
			ps.setString(index++,info.getRemitInProvince());
			ps.setString(index++,info.getRemitInCity());			
			ps.setLong(index++,info.getCashFlowID());
			ps.setDouble(index++,info.getAmount());
			ps.setDouble(index++,info.getDrawAmount());
			ps.setDouble(index++,info.getDepositBalance());
			ps.setTimestamp(index++,info.getInterestStartDate());
			ps.setTimestamp(index++,info.getExecuteDate());
			ps.setString(index++,info.getCapitalExtBankNo());
			ps.setString(index++,info.getSealNo());
			ps.setLong(index++,info.getSealBankID());
			ps.setDouble(index++,info.getPreDrawInterest());
			ps.setDouble(index++,info.getStrikePreDrawInterest());
			ps.setDouble(index++,info.getDrawInterest());
			ps.setLong(index++,info.getIsPreDraw());			
			ps.setLong(index++,info.getIsTally());
			ps.setDouble(index++,info.getTotalUnpayInterest());
			ps.setDouble(index++,info.getPayableInterest());
			ps.setDouble(index++,info.getCurrentInterest());
			ps.setDouble(index++,info.getOtherInterest());
			ps.setLong(index++,info.getReceiveInterestAccountID());
			ps.setLong(index++,info.getInterestPayTypeID());
			ps.setLong(index++,info.getInterestBankID());
			//ps.setLong(index++,info.getInterestExtAcctID());
			ps.setString(index++,info.getInterestExtAcctNo());
			ps.setString(index++,info.getInterestExtClientName());
			ps.setString(index++,info.getInterestRemitInBank());
			ps.setString(index++,info.getInterestRemitInProvince());
			ps.setString(index++,info.getInterestRemitInCity());
			ps.setLong(index++,info.getInterestCashFlowID());
			ps.setLong(index++,info.getConfirmOfficeID());
			ps.setString(index++,info.getInterestExtBankNo());
			ps.setLong(index++,info.getCapitalAndInterestDealWay());			
			//ps.setTimestamp(index++,info.getModifyDate());
			ps.setTimestamp(index++,info.getInputDate());
			ps.setLong(index++,info.getInputUserID());
			ps.setLong(index++,info.getCheckUserID());
			ps.setLong(index++,info.getAbstractID());
			ps.setString(index++,info.getAbstract());
			ps.setString(index++,info.getCheckAbstract());
			ps.setLong(index++,info.getStatusID());
			ps.setString(index++,info.getInstructionNo());	
			ps.setDouble(index++,info.getAdvanceRate());
			ps.setLong(index++,info.getIsAutoContinue());
			ps.setLong(index++,info.getAutocontinuetype());
			ps.setLong(index++,info.getAutocontinueaccountid());
			int i=ps.executeUpdate();			
			if(i>0)
			{
				lReturn=info.getID();
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
			catch(Exception e)
			{
				e.printStackTrace();	
				throw e;
			}					
		}
		return lReturn;
	}
	/**
	 * 修改定期（通知）支取转活期交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedDrawInfo, 定期（通知）交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransFixedDrawInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{	
			StringBuffer buffer = new 	StringBuffer();
			buffer.append("update sett_TransFixedWithDraw set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append("nTransactionTypeID=?,nClientID=?,nAccountID=?,sDepositNO=?,\n");
			buffer.append("nCertificationBankID=?,mRate=?,dtStart=?,dtEnd=?,\n");
			buffer.append("nDepositTerm=?,nInterestPlanID=?,nNoticeDay=?,nSubAccountID=?,\n");
			buffer.append("nCurrentAccountID=?,nPayTypeID=?,nBankID=?,sExtAccountNo=?,sExtClientName=?,\n");
			buffer.append("sRemitInBank=?,sRemitInProvince=?,sRemitInCity=?,nCashFlowID=?,\n");
			buffer.append("mAmount=?,mDrawAmount=?,\n");
			buffer.append("mDepositBalance=?,dtInterestStart=?,dtExecute=?,sCapitalExtBankNo=?,\n");
			buffer.append("sSealNo=?,nSealBankID=?,mPreDrawInterest=?,mStrikePreDrawInterest=?,\n");
			buffer.append("mWithDrawInterest=?,nIsPreDraw=?,nIsTally=?,mTotalUnpayInterest=?,\n");
			buffer.append("mPayableInterest=?,mCurrentInterest=?,mOtherInterest=?,nReceiveInterestAccountID=?,\n");
			buffer.append("nInterestPayTypeID=?,nInterestBankID=?,sInterestExtAccountNo=?,sInterestExtClientName=?,\n");
			buffer.append("sInterestRemitInBank=?,sInterestRemitInProvince=?,sInterestRemitInCity=?,nInterestCashFlowID=?,\n");
			buffer.append("nConfirmOfficeID=?,sInterestExtBankNo=?,nCapitalAndInterestDealWay=?,dtModify=sysdate,\n");			
			buffer.append("dtInput=?,nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,sCheckAbstract=?,\n");
			buffer.append("nStatusID=?,sInstructionNo=?,ADVANCERATE=?,isautocontinue=?,autocontinuetype=?,autocontinueaccountid=? ");
			buffer.append("where ID=? \n");			
														
			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());			
			int index = 1;
						
			ps.setLong(index++,info.getOfficeID());
			ps.setLong(index++,info.getCurrencyID());
			ps.setString(index++,info.getTransNo());				
			ps.setLong(index++,info.getTransactionTypeID());
			ps.setLong(index++,info.getClientID());
			ps.setLong(index++,info.getAccountID());
			ps.setString(index++,info.getDepositNo());
			ps.setLong(index++,info.getCertificationBankID());
			ps.setDouble(index++,info.getRate());
			ps.setTimestamp(index++,info.getStartDate());
			ps.setTimestamp(index++,info.getEndDate());
			ps.setLong(index++,info.getDepositTerm());
			ps.setLong(index++,info.getInterestPlanID());
			ps.setLong(index++,info.getNoticeDay());
			ps.setLong(index++,info.getSubAccountID());
			ps.setLong(index++,info.getCurrentAccountID());
			ps.setLong(index++,info.getPayTypeID());
			ps.setLong(index++,info.getBankID());
			//ps.setLong(index++,info.getExtAcctID());			
			ps.setString(index++,info.getExtAcctNo());
			ps.setString(index++,info.getExtClientName());
			ps.setString(index++,info.getRemitInBank());
			ps.setString(index++,info.getRemitInProvince());
			ps.setString(index++,info.getRemitInCity());
			ps.setLong(index++,info.getCashFlowID());
			ps.setDouble(index++,info.getAmount());
			ps.setDouble(index++,info.getDrawAmount());
			ps.setDouble(index++,info.getDepositBalance());
			ps.setTimestamp(index++,info.getInterestStartDate());
			ps.setTimestamp(index++,info.getExecuteDate());
			ps.setString(index++,info.getCapitalExtBankNo());
			ps.setString(index++,info.getSealNo());
			ps.setLong(index++,info.getSealBankID());
			ps.setDouble(index++,info.getPreDrawInterest());
			ps.setDouble(index++,info.getStrikePreDrawInterest());
			ps.setDouble(index++,info.getDrawInterest());
			ps.setLong(index++,info.getIsPreDraw());
			ps.setLong(index++,info.getIsTally());
			ps.setDouble(index++,info.getTotalUnpayInterest());
			ps.setDouble(index++,info.getPayableInterest());
			ps.setDouble(index++,info.getCurrentInterest());
			ps.setDouble(index++,info.getOtherInterest());			
			ps.setLong(index++,info.getReceiveInterestAccountID());
			ps.setLong(index++,info.getInterestPayTypeID());
			ps.setLong(index++,info.getInterestBankID());
			//ps.setLong(index++,info.getInterestExtAcctID());
			ps.setString(index++,info.getInterestExtAcctNo());
			ps.setString(index++,info.getInterestExtClientName());
			ps.setString(index++,info.getInterestRemitInBank());
			ps.setString(index++,info.getInterestRemitInProvince());
			ps.setString(index++,info.getInterestRemitInCity());
			ps.setLong(index++,info.getInterestCashFlowID());
			ps.setLong(index++,info.getConfirmOfficeID());
			ps.setString(index++,info.getInterestExtBankNo());
			ps.setLong(index++,info.getCapitalAndInterestDealWay());
			//ps.setTimestamp(index++,info.getModifyDate());
			ps.setTimestamp(index++,info.getInputDate());
			ps.setLong(index++,info.getInputUserID());
			ps.setLong(index++,info.getCheckUserID());
			ps.setLong(index++,info.getAbstractID());
			ps.setString(index++,info.getAbstract());
			ps.setString(index++,info.getCheckAbstract());
			ps.setLong(index++,info.getStatusID());
			ps.setString(index++,info.getInstructionNo());			
			ps.setDouble(index++,info.getAdvanceRate());
			ps.setLong(index++,info.getIsAutoContinue());
			ps.setLong(index++,info.getAutocontinuetype());
			ps.setLong(index++,info.getAutocontinueaccountid());
			ps.setLong(index++,info.getID());
			int i=ps.executeUpdate();
			if(i>0)
			{
				lReturn=info.getID();
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return lReturn;
	}	
	/**
	 * 根据标识查询定期（通知）支取/转活期交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedDrawInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedDrawInfo findByID(long lID) throws Exception
	{
		TransFixedDrawInfo info = new TransFixedDrawInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
						
			String strSQL = "select * from sett_TransFixedWithDraw where id=? ";								
			ps = con.prepareStatement(strSQL);
			ps.setLong(1,lID);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				info=getDeposit(info,rs);
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return info;
	}
	/**
	 * 根据标识查询定期（通知）支取/转活期交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedDrawInfo, 定期（通知）交易实体类
	 * @throws Exception
	 */
	public TransFixedDrawInfo findByTransNo(String strTransNo) throws Exception
	{
		TransFixedDrawInfo info = new TransFixedDrawInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
						
			String strSQL = "select * from sett_TransFixedWithDraw where sTransNo=? ";								
			ps = con.prepareStatement(strSQL);
			ps.setString(1,strTransNo);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				info=getDeposit(info,rs);
			}
			System.out.print(strTransNo+"--->>"+strSQL);
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return info;
	}
	/**
	 * 根据条件判断定期交易是否重复的方法：
	 * 逻辑说明：
	 * 
	 * @param FixedContinueInfo searchInfo , 定期交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 */
	public boolean checkIsDuplicate(TransFixedContinueInfo searchInfo) throws Exception
	{
		boolean rtnFlg = true;
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
					
			String strSQL = "select * from sett_TransFixedWithDraw where id=? ";								
			ps = con.prepareStatement(strSQL);
			//ps.setLong(1,lID);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				rtnFlg= false;
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return rtnFlg;
	}	
	/**
	 * 修改定期（通知）支取/转活期交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 交易标识
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
			StringBuffer buffer = new 	StringBuffer();
			buffer.append("update sett_TransFixedWithDraw set nStatusID=?,dtModify=sysdate where ID=?");																		
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++,lStatusID);
			ps.setLong(index++,lID);
			int i=ps.executeUpdate();
			if(i>0)
			{
				lReturn=lID;
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
			catch(Exception e)
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
	private TransFixedDrawInfo getDeposit(TransFixedDrawInfo info,ResultSet rs) throws Exception
	{
		info = new TransFixedDrawInfo();
		try
		{			
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
			info.setSubAccountID(rs.getLong("nSubAccountID"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setPayTypeID(rs.getLong("nPayTypeID"));
			info.setBankID(rs.getLong("nBankID"));
			//info.setExtAcctID(rs.getLong("nExtAccountID"));			
			info.setExtAcctNo(rs.getString("sExtAccountNo"));
			info.setExtClientName(rs.getString("sExtClientName"));
			info.setRemitInBank(rs.getString("sRemitInBank"));
			info.setRemitInProvince(rs.getString("sRemitInProvince"));
			info.setRemitInCity(rs.getString("sRemitInCity"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));			
			info.setDrawAmount(rs.getDouble("mDrawAmount"));
			info.setDepositBalance(rs.getDouble("mDepositBalance"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setCapitalExtBankNo(rs.getString("sCapitalExtBankNo"));
			info.setInterestExtBankNo(rs.getString("sInterestExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			info.setPreDrawInterest(rs.getDouble("mPreDrawInterest"));
			info.setStrikePreDrawInterest(rs.getDouble("mStrikePreDrawInterest"));
			info.setDrawInterest(rs.getDouble("mWithDrawInterest"));
			info.setIsPreDraw(rs.getLong("nIsPreDraw"));
			info.setIsTally(rs.getLong("nIsTally"));
			info.setTotalUnpayInterest(rs.getDouble("mTotalUnpayInterest"));
			info.setPayableInterest(rs.getDouble("mPayableInterest"));
			info.setCurrentInterest(rs.getDouble("mCurrentInterest"));
			info.setOtherInterest(rs.getDouble("mOtherInterest"));
			info.setReceiveInterestAccountID(rs.getLong("nReceiveInterestAccountID"));
			info.setInterestPayTypeID(rs.getLong("nInterestPayTypeID"));
			info.setInterestBankID(rs.getLong("nInterestBankID"));			
			//info.setInterestExtAcctID(rs.getLong("nInterestExtAccountID"));
			info.setInterestExtAcctNo(rs.getString("sInterestExtAccountNo"));
			info.setInterestExtClientName(rs.getString("sInterestExtClientName"));
			info.setInterestRemitInBank(rs.getString("sInterestRemitInBank"));
			info.setInterestRemitInProvince(rs.getString("sInterestRemitInProvince"));
			info.setInterestRemitInCity(rs.getString("sInterestRemitInCity"));
			info.setInterestCashFlowID(rs.getLong("nInterestCashFlowID"));
			info.setConfirmOfficeID(rs.getLong("nConfirmOfficeID"));
			info.setInterestExtAcctNo(rs.getString("sInterestExtAccountNo"));
			info.setCapitalAndInterestDealWay(rs.getLong("nCapitalAndInterestDealWay"));			
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));	
			info.setAdvanceRate(rs.getDouble("advanceRate")); //活期利率
			info.setIsAutoContinue(rs.getLong("isAutoContinue"));
			info.setAutocontinuetype(rs.getLong("autocontinuetype"));
			info.setAutocontinueaccountid(rs.getLong("autocontinueaccountid"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;
		
	}
	/**
	 * 得到状态条件
	 * @param info
	 * @return
	 */
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
	/**
	 * 得到排序条件
	 * @param info
	 * @return
	 */
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
				case ORDERBY_INTERESTCURRENTACCOUNTNO :
				{
					order=" ORDER BY nReceiveInterestAccountID ";					
				}
					break;							
				case ORDERBY_AMOUNT :
				{
					order=" ORDER BY mAmount ";					
				}
					break;
				case ORDERBY_INTEREST :
				{
					order=" ORDER BY mCurrentInterest ";					
				}
					break;	
				case ORDERBY_INTERESTSTARTDATE :
				{
					order=" ORDER BY dtInterestStart ";					
				}
					break;
				case ORDERBY_EXECUTEDATE :
				{
					order=" ORDER BY dtExecute ";					
				}
					break;				
				case ORDERBY_STATUSID :
				{
					order=" ORDER BY nStatusID ";					
				}
					break;
				case ORDERBY_DRAWAMOUNT :
				{
					order=" ORDER BY mDrawAmount ";					
				}
					break;
				case ORDERBY_ABSTRACT :
				{
					order=" ORDER BY sAbstract ";					
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
	 * 根据状态查询的方法：
	 * 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo , 按状态查询的查询条件实体类。
	 * @return Collection ,包含FixedDrawInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;					
		try 
		{								
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查 询条件
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
			if(info.getTypeID()==0)
			{				
				/*buffer.append("select sTransNo,nTransActionTypeID ,nAccountID,\n");
				buffer.append("nClientID,sDepositNo,nCurrentAccountID,\n");
				buffer.append("nReceiveInterestAccountID,\n");
				buffer.append("nBankID,mAmount,mDrawAmount,mWithDrawInterest,dtInterestStart,\n");
				buffer.append("dtExecute,sAbstract,nStatusID \n");*/
				buffer.append("select * \n");
				buffer.append("from sett_TransFixedWithDraw \n");
				buffer.append("where  \n");
				buffer.append("nOfficeID=? and \n");
				buffer.append("nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");				
				
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());	
				int index = 1; 
				ps.setLong(index++,info.getOfficeID());				
				ps.setLong(index++,info.getCurrencyID());				
				ps.setLong(index++,info.getTransactionTypeID());				
				ps.setLong(index++,info.getUserID());	
											
			}	
			else if(info.getTypeID()==1) //业务复核
			{	
				/*buffer.append("select sTransNo,nTransActionTypeID ,nAccountID,\n");
				buffer.append("nClientID,sDepositNo,nCurrentAccountID,\n");
				buffer.append("nReceiveInterestAccountID,\n");
				buffer.append("nBankID,mAmount,mDrawAmount,mWithDrawInterest,dtInterestStart,\n");
				buffer.append("dtExecute,sAbstract,nStatusID \n");*/
				buffer.append("select * \n");
				buffer.append("from sett_TransFixedWithDraw \n");
				buffer.append("where  \n");
				buffer.append("nOfficeID=? and \n");
				buffer.append("nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");					
				buffer.append("and nCheckUserID=? and dtExecute=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
				
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				
				int index = 1; 
				ps.setLong(index++,info.getOfficeID());
				ps.setLong(index++,info.getCurrencyID());
				ps.setLong(index++,info.getTransactionTypeID());				
				ps.setLong(index++,info.getUserID());
				ps.setTimestamp(index++,info.getDate());
												
			}														
			
			rs=ps.executeQuery();
			while(rs.next())
			{
				TransFixedDrawInfo resultInfo = new TransFixedDrawInfo();
						
				/*resultInfo.setTransNo(rs.getString("sTransNo"));
				resultInfo.setTransactionTypeID(rs.getLong("nTransActionTypeID"));
				resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("nAccountID")));
				resultInfo.setClientName(NameRef.getClientNameByID(rs.getLong("nClientID")));
				resultInfo.setDepositNo(rs.getString("sDepositNo"));						
				resultInfo.setCurrentAccountNo(NameRef.getAccountNoByID(rs.getLong("nCurrentAccountID")));
				resultInfo.setReceiveInterestAccountNo(NameRef.getAccountNoByID(rs.getLong("nReceiveInterestAccountID")));				
				resultInfo.setBankName(NameRef.getBankNameByID(rs.getLong("nBankID")));
				resultInfo.setAmount(rs.getDouble("mAmount"));
				resultInfo.setDrawAmount(rs.getDouble("mDrawAmount"));
				resultInfo.setDrawInterest(rs.getDouble("mWithDrawInterest"));
				resultInfo.setStartDate(rs.getTimestamp("dtInterestStart"));
				resultInfo.setEndDate(rs.getTimestamp("dtExecute"));
				resultInfo.setAbstract(rs.getString("sAbstract"));
				resultInfo.setStatusID(rs.getLong("nStatusID"));*/
				resultInfo = this.getDeposit(resultInfo,rs);		
				arrResult.add(resultInfo);							
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}		
					
		}
		return arrResult;	
		
	}		
	/**
	 * 复核匹配的方法：
	 * 逻辑说明：
	 * 
	 * @param FixedDrawInfo,定期（通知）支取/转活期交易复核匹配查询条件实体类
	 * @return Collection ,包含FixedDrawInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransFixedDrawInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;					
		try 
		{	
			StringBuffer buffer=null;
			

				/**定期支取
				 * 
				 * //基本信息
				 * 定期账户号,定期客户名称，定期存款单据号（不要）
				 * 本金，提前支取金额，其他利息，定期支取起息日
				 * 本金转至活期账户号，活期客户名称，
				 * 活期付款方式 付款开户行
				 * 利息转至活期账户号，活期客户名称，
				 * 利息付款方式 付款开户行
				 * 本金处理方法（汇总，分笔）
				 * 缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者 
				 */
				if(info.getTransactionTypeID()==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
				{
					buffer = new StringBuffer();
					buffer.append("select * from sett_TransFixedWithDraw  \n");
					buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					buffer.append("and nInputUserID <>? and nAccountID=? \n");					
					//buffer.append("and mAmount=? and mDrawAmount=? and mOtherInterest=? and dtInterestStart=? \n");
					buffer.append("and sDepositNo=? and mAmount=? and mDrawAmount=? and mOtherInterest=? and dtInterestStart=? \n");
					//buffer.append("and nCurrentAccountID=? and nPayTypeID=? and nBankID=? \n");
					buffer.append("and nCurrentAccountID=? and nBankID=? \n");
					//buffer.append("and nReceiveInterestAccountID=? and nInterestPayTypeID=? and nInterestbankID=? \n");
					buffer.append("and nReceiveInterestAccountID=? and nInterestbankID=? \n");
					buffer.append("and nCapitalAndInterestDealWay=? \n");
					buffer.append("order by ID \n");
					
					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());
			
					int index = 1; 
					ps.setLong(index++,info.getOfficeID());
					ps.setLong(index++,info.getCurrencyID());
					ps.setLong(index++,info.getStatusID());
					ps.setLong(index++,info.getInputUserID()); 
					ps.setLong(index++,info.getAccountID());					
					ps.setString(index++,info.getDepositNo());
					ps.setDouble(index++,info.getAmount());
					ps.setDouble(index++,info.getDrawAmount());
					ps.setDouble(index++,info.getOtherInterest());
					ps.setTimestamp(index++,info.getInterestStartDate());
					ps.setLong(index++,info.getCurrentAccountID());
					//ps.setLong(index++,info.getPayTypeID());
					ps.setLong(index++,info.getBankID());
					ps.setLong(index++,info.getReceiveInterestAccountID());
					//ps.setLong(index++,info.getInterestPayTypeID());	
					ps.setLong(index++,info.getInterestBankID());
					ps.setLong(index++,info.getCapitalAndInterestDealWay());
					rs=ps.executeQuery();
					while(rs.next())
					{
						TransFixedDrawInfo depositInfo = new TransFixedDrawInfo();
						depositInfo=getDeposit(depositInfo,rs);																		
						arrResult.add(depositInfo);								
					}					
				}				
				/**通知支取
				 * 基本信息
				 * 通知存款账户号,通知存款客户名称，通知存款存款单据号，
				 * 支取金额，通知存款执行日，利率
				 * 转至活期账户号，活期客户名称，
				 * 付款方式 付款开户行名称
				 * 是否提前支取
				 * 是否记账
				 * 利息转至活期账户号，活期客户名称，
				 * 利息付款方式 付款开户行名称
				 * 缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者 
				 */
				else if(info.getTransactionTypeID()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					if(info.getDepositNo()==null || info.getDepositNo().equals(""))  
					{
						//为空肯定查不出数据
						return arrResult;
					}
					buffer = new StringBuffer();
					buffer.append("select * from sett_TransFixedWithDraw  \n");
					buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
					buffer.append("and nInputUserID <>? and nAccountID=? and \n");
					//buffer.append("sDepositNo=? and mDrawAmount=?  and dtExecute=? and mRate=? and  \n");
					//buffer.append("nCurrentAccountID=? and nPayTypeID=? and nBankID=? \n");
					buffer.append("sDepositNo=? and mDrawAmount=?  and dtInterestStart=? and mRate=? and  \n");
					buffer.append("nCurrentAccountID=? and nBankID=? \n");
					buffer.append("and nIsPreDraw=? \n");
					//buffer.append("and nReceiveInterestAccountID=? and nInterestPayTypeID=? and nInterestbankID=? \n");
					buffer.append("and nReceiveInterestAccountID=? and nInterestbankID=? \n");
					buffer.append("and nCapitalAndInterestDealWay=? \n");
					buffer.append("order by ID \n");
					
					ps = con.prepareStatement(buffer.toString());
					log.info(buffer.toString());
			
					int index = 1; 
					ps.setLong(index++,info.getOfficeID());
					ps.setLong(index++,info.getCurrencyID());
					ps.setLong(index++,info.getStatusID());
					ps.setLong(index++,info.getInputUserID()); 
					ps.setLong(index++,info.getAccountID());					
					ps.setString(index++,info.getDepositNo());
					ps.setDouble(index++,info.getDrawAmount());
					//ps.setTimestamp(index++,info.getExecuteDate());
					ps.setTimestamp(index++,info.getInterestStartDate());
					ps.setDouble(index++,info.getRate());
					ps.setLong(index++,info.getCurrentAccountID());
					//ps.setLong(index++,info.getPayTypeID());
					ps.setLong(index++,info.getBankID());
					ps.setLong(index++,info.getIsPreDraw());
					ps.setLong(index++,info.getReceiveInterestAccountID());
					//ps.setLong(index++,info.getInterestPayTypeID());	
					ps.setLong(index++,info.getInterestBankID());
					ps.setLong(index++,info.getCapitalAndInterestDealWay());
					rs=ps.executeQuery();
					while(rs.next())
					{
						TransFixedDrawInfo depositInfo = new TransFixedDrawInfo();
						depositInfo=getDeposit(depositInfo,rs);																		
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}		
			
		}
		return arrResult;	
	}
	
	
	public String queryTransFixedVithDrawSQL(QueryByStatusConditionInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		//业务处理
		if (qInfo.getTypeID() == 0) 
		{
			//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
			sbSQL.append("select aa.*,(aa.mpredrawinterest+aa.mstrikepredrawinterest+aa.mwithdrawinterest+aa.mcurrentinterest+aa.motherinterest) as minteresttotal \n");
			sbSQL.append("from sett_TransFixedWithDraw aa \n");
			sbSQL.append("where \n");
			sbSQL.append("aa.nofficeid ="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and aa.ncurrencyid ="+qInfo.getCurrencyID()+" \n");
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
			sbSQL.append("select aa.*,(aa.mpredrawinterest+aa.mstrikepredrawinterest+aa.mwithdrawinterest+aa.mcurrentinterest+aa.motherinterest) as minteresttotal \n");
			sbSQL.append("from sett_TransFixedWithDraw aa \n");
			sbSQL.append("where \n");
			sbSQL.append("aa.nofficeid ="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and aa.ncurrencyid ="+qInfo.getCurrencyID()+" \n");
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
			sbSQL.append("and aa.ncheckuserid ="+qInfo.getUserID()+" \n");
			sbSQL.append("and aa.dtExecute = TO_DATE('" + DataFormat.getDateString(qInfo.getDate()) + "','yyyy-mm-dd') \n");
		}
		return sbSQL.toString();
	}
	
}
