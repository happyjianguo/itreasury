/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dao;

import java.util.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.settlement.util.*;
import java.sql.*;

//import oracle.net.ano.SupervisorService;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_TransFixedContinueDAO extends SettlementDAO
{	
	public final static int ORDERBY_TRANSACTIONNOID = 0;  //交易号
	public final static int ORDERBY_ACCOUNTNO = 1;     //定期账户号	
	public final static int ORDERBY_DEPOSITNO = 2;    //到期定期存款单据号
	public final static int ORDERBY_NEWDEPOSITNO = 3;    //新定期存款单据号	
	public final static int ORDERBY_AMOUNT = 4;            //本金金额
	public final static int ORDERBY_INTEREST = 5;            //利息
	public final static int ORDERBY_INTERESTSTARTDATE = 6;   //起息日
	public final static int ORDERBY_EXECUTEDATE = 7;     //执行日
	public final static int ORDERBY_STATUSID = 8;    //状态	
	public final static int ORDERBY_ABSTRACT = 9;     //摘要	
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 新增定期续期转存交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedContinueInfo, 定期交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public Sett_TransFixedContinueDAO()
	{
		
	}
	public Sett_TransFixedContinueDAO(Connection conn)
	{
		this.transConn = conn;
	}
	public long add(TransFixedContinueInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();		
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{			
			//利用数据库的序列号取ID;
			long id= super.getSett_TransFixedContinueID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_TransFixedContinue \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nClientID,nAccountID, \n");
			buffer.append("sDepositNO,nCertificationBankID,mRate, \n");			
			buffer.append("dtStart,dtEnd,nDepositTerm,nInterestPlanID, \n");
			buffer.append("nSubAccountID,mAmount,sSealNo,nSealBankID,dtExecute, \n");
			buffer.append("sNewDepositNO,nNewCertificationBankID,mNewRate, \n");			
			buffer.append("dtNewStart,dtNewEnd,nNewDepositTerm,nNewInterestPlanID, \n");
			buffer.append("mNewAmount,sNewSealNo,nNewSealBankID,mPreDrawInterest, \n");			
			buffer.append("mPayableInterest,mWithDrawInterest,nIsCapitalAndInterestTransfer, \n");			
			buffer.append("nReceiveInterestAccountID,nInterestPayTypeID,nInterestbankID, \n");			
			buffer.append("sInterestExtAccountNo,sInterestExtClientName,sInterestRemitInBank, \n");			
			buffer.append("sInterestRemitInProvince,sInterestRemitInCity,nInterestCashFlowID, \n");			
			buffer.append("sInterestExtBankNo,dtInterestStart,NAbstractID, \n");			
			buffer.append("sAbstract,sCheckAbstract,dtModify,dtInput,nInputUserID, \n");			
			buffer.append("nCheckUserID,nStatusID,sInstructionNo,mcurrentinterest,advancerate,isautocontinue, autocontinuetype ,autocontinueaccountid  ) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,sysdate,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?) \n");			
			
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
			ps.setLong(index++,info.getSubAccountID());
			ps.setDouble(index++,info.getAmount());
			ps.setString(index++,info.getSealNo());
			ps.setLong(index++,info.getSealBankID());
			ps.setTimestamp(index++,info.getExecuteDate());
			ps.setString(index++,info.getNewDepositNo());
			ps.setLong(index++,info.getNewCertificationBankID());
			ps.setDouble(index++,info.getNewRate());
			ps.setTimestamp(index++,info.getNewStartDate());
			ps.setTimestamp(index++,info.getNewEndDate());
			ps.setLong(index++,info.getNewDepositTerm());
			ps.setLong(index++,info.getNewInterestPlanID());
			ps.setDouble(index++,info.getNewAmount());
			ps.setString(index++,info.getNewSealNo());
			ps.setLong(index++,info.getNewSealBankID());
			ps.setDouble(index++,info.getPreDrawInterest());
			ps.setDouble(index++,info.getPayableInterest());
			ps.setDouble(index++,info.getWithDrawInterest());
			ps.setLong(index++,info.getIsCapitalAndInterestTransfer());
			ps.setLong(index++,info.getReceiveInterestAccountID());
			ps.setLong(index++,info.getInterestPayTypeID());
			ps.setLong(index++,info.getInterestBankID());
			ps.setString(index++,info.getInterestExtAcctNo());
			ps.setString(index++,info.getInterestExtClientName());
			ps.setString(index++,info.getInterestRemitInBank());
			ps.setString(index++,info.getInterestRemitInProvince());
			ps.setString(index++,info.getInterestRemitInCity());
			ps.setLong(index++,info.getInterestCashFlowID());
			ps.setString(index++,info.getInterestExtBankNo());			
			ps.setTimestamp(index++,info.getInterestStartDate());
			ps.setLong(index++,info.getAbstractID());
			ps.setString(index++,info.getAbstract());
			ps.setString(index++,info.getCheckAbstract());			
			//ps.setTimestamp(index++,info.getModifyDate());
			ps.setTimestamp(index++,info.getInputDate());
			ps.setLong(index++,info.getInputUserID());
			ps.setLong(index++,info.getCheckUserID());			
			ps.setLong(index++,info.getStatusID());
			ps.setString(index++,info.getInstructionNo());
			
			//Boxu Add
			ps.setDouble(index++,info.getCurrentInterest());
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
	 * 修改定期续期转存交易的方法：
	 * 逻辑说明：
	 * 
	 * @param info, FixedContinueInfo, 定期续期转存交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransFixedContinueInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{	
			StringBuffer buffer = new 	StringBuffer();
			buffer.append("update sett_TransFixedContinue set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append("nTransactionTypeID=?,nClientID=?,nAccountID=?,sDepositNO=?,\n");
			buffer.append("nCertificationBankID=?,mRate=?,dtStart=?,dtEnd=?,\n");
			buffer.append("nDepositTerm=?,nInterestPlanID=?,nSubAccountID=?,\n");
			buffer.append("mAmount=?,sSealNo=?,nSealBankID=?,dtExecute=?, \n");
			buffer.append("sNewDepositNO=?,nNewCertificationBankID=?,mNewRate=?, \n");			
			buffer.append("dtNewStart=?,dtNewEnd=?,nNewDepositTerm=?,nNewInterestPlanID=?, \n");
			buffer.append("mNewAmount=?,sNewSealNo=?,nNewSealBankID=?,mPreDrawInterest=?, \n");			
			buffer.append("mPayableInterest=?,mWithDrawInterest=?,nIsCapitalAndInterestTransfer=?, \n");			
			buffer.append("nReceiveInterestAccountID=?,nInterestPayTypeID=?,nInterestbankID=?, \n");			
			buffer.append("sInterestExtAccountNo=?,sInterestExtClientName=?,sInterestRemitInBank=?, \n");			
			buffer.append("sInterestRemitInProvince=?,sInterestRemitInCity=?,nInterestCashFlowID=?, \n");			
			buffer.append("sInterestExtBankNo=?,dtInterestStart=?,NAbstractID=?, \n");			
			buffer.append("sAbstract=?,sCheckAbstract=?,dtModify=sysdate,dtInput=?,nInputUserID=?, \n");			
			buffer.append("nCheckUserID=?,nStatusID=?,sInstructionNo=?,isautocontinue=?, autocontinuetype=?,autocontinueaccountid=? \n");
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
			ps.setLong(index++,info.getSubAccountID());
			ps.setDouble(index++,info.getAmount());
			ps.setString(index++,info.getSealNo());
			ps.setLong(index++,info.getSealBankID());
			ps.setTimestamp(index++,info.getExecuteDate());
			ps.setString(index++,info.getNewDepositNo());
			ps.setLong(index++,info.getNewCertificationBankID());
			ps.setDouble(index++,info.getNewRate());
			ps.setTimestamp(index++,info.getNewStartDate());
			ps.setTimestamp(index++,info.getNewEndDate());
			ps.setLong(index++,info.getNewDepositTerm());
			ps.setLong(index++,info.getNewInterestPlanID());
			ps.setDouble(index++,info.getNewAmount());
			ps.setString(index++,info.getNewSealNo());
			ps.setLong(index++,info.getNewSealBankID());
			ps.setDouble(index++,info.getPreDrawInterest());
			ps.setDouble(index++,info.getPayableInterest());
			ps.setDouble(index++,info.getWithDrawInterest());
			ps.setLong(index++,info.getIsCapitalAndInterestTransfer());
			ps.setLong(index++,info.getReceiveInterestAccountID());
			ps.setLong(index++,info.getInterestPayTypeID());
			ps.setLong(index++,info.getInterestBankID());
			ps.setString(index++,info.getInterestExtAcctNo());
			ps.setString(index++,info.getInterestExtClientName());
			ps.setString(index++,info.getInterestRemitInBank());
			ps.setString(index++,info.getInterestRemitInProvince());
			ps.setString(index++,info.getInterestRemitInCity());
			ps.setLong(index++,info.getInterestCashFlowID());
			ps.setString(index++,info.getInterestExtBankNo());			
			ps.setTimestamp(index++,info.getInterestStartDate());
			ps.setLong(index++,info.getAbstractID());
			ps.setString(index++,info.getAbstract());
			ps.setString(index++,info.getCheckAbstract());			
			//ps.setTimestamp(index++,info.getModifyDate());
			ps.setTimestamp(index++,info.getInputDate());
			ps.setLong(index++,info.getInputUserID());
			ps.setLong(index++,info.getCheckUserID());			
			ps.setLong(index++,info.getStatusID());
			ps.setString(index++,info.getInstructionNo());
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
	 * 根据条件查询存款单据号是否重复的方法：
	 * 逻辑说明：
	 * 
	 * @param FixedOpenInfo, 定期续期转存交易实体类
	 * @return boolean false 重复
	 * @throws Exception
	 */
	public boolean checkDepositNo(TransFixedContinueInfo info) throws Exception 
	{			
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtnFlg = true;
		try 
		{	
			StringBuffer buffer = new StringBuffer();
			buffer.append("select sDepositNo from sett_TransOpenFixedDeposit where \n");
			buffer.append("sDepositNo=? and nAccountID=? and \n");
			buffer.append("nStatusID <> ? \n");
			buffer.append("union select sDepositNo from sett_TransFixedContinue where \n");
			buffer.append("sNewDepositNo=? and nAccountID=? and ID<>? and \n");
			buffer.append("nStatusID <> ?");
			
			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
			int index=1;
			ps.setString(index++, info.getNewDepositNo());
			ps.setLong(index++, info.getAccountID());			
			ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);
			ps.setString(index++, info.getNewDepositNo());
			ps.setLong(index++, info.getAccountID());
			ps.setLong(index++, info.getID());				
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
	 * 根据标识查询定期续期转存交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lID long , 交易的ID
	 * @return FixedContinueInfo, 定期交易实体类
	 * @throws Exception
	 */
	public TransFixedContinueInfo findByID(long lID) throws Exception
	{
		TransFixedContinueInfo info = new TransFixedContinueInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
						
			String strSQL = "select * from sett_TransFixedContinue where id=? ";								
			ps = con.prepareStatement(strSQL);
			ps.setLong(1,lID);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				info=getContinueDeposit(info,rs);
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
	 * 根据交易号查询定期续期转存交易明细的方法：
	 * 逻辑说明：
	 * 
	 * @param transNo String , 交易号
	 * @return FixedContinueInfo, 定期交易实体类
	 * @throws Exception
	 */
	public TransFixedContinueInfo findByTransNo(String strTransNo) throws Exception
	{
		TransFixedContinueInfo info = new TransFixedContinueInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;	
		try 
		{				
						
			String strSQL = "select * from sett_TransFixedContinue where sTransNo=? ";								
			ps = con.prepareStatement(strSQL);
			ps.setString(1,strTransNo);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				info=getContinueDeposit(info,rs);
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
					
			String strSQL = "select * from sett_TransFixedContinue where id=? ";								
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
	 * 修改定期续期转存交易状态的方法：
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
			buffer.append("update sett_TransFixedContinue set nStatusID=?,dtModify=sysdate where ID=?");																		
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
	private TransFixedContinueInfo getContinueDeposit(TransFixedContinueInfo info,ResultSet rs) throws Exception
	{
		info = new TransFixedContinueInfo();
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
			info.setSubAccountID(rs.getLong("nSubAccountID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setNewDepositNo(rs.getString("sNewDepositNo"));
			info.setNewCertificationBankID(rs.getLong("nNewCertificationBankID"));
			info.setNewRate(rs.getDouble("mNewRate"));
			info.setNewStartDate(rs.getTimestamp("dtNewStart"));
			info.setNewEndDate(rs.getTimestamp("dtNewEnd"));
			info.setNewDepositTerm(rs.getLong("nNewDepositTerm"));
			info.setNewInterestPlanID(rs.getLong("nNewInterestPlanID"));
			info.setNewAmount(rs.getDouble("mNewAmount"));
			info.setNewSealNo(rs.getString("sNewSealNo"));
			info.setNewSealBankID(rs.getLong("nNewSealBankID"));
			info.setPreDrawInterest(rs.getDouble("mPreDrawInterest"));
			info.setPayableInterest(rs.getDouble("mPayableInterest"));
			info.setWithDrawInterest(rs.getDouble("mWithDrawInterest"));
			info.setIsCapitalAndInterestTransfer(rs.getLong("nIsCapitalAndInterestTransfer"));
			info.setReceiveInterestAccountID(rs.getLong("nReceiveInterestAccountID"));
			info.setInterestPayTypeID(rs.getLong("nInterestPayTypeID"));
			info.setInterestBankID(rs.getLong("nInterestbankID"));
			info.setInterestExtAcctNo(rs.getString("sInterestExtAccountNo"));
			info.setInterestExtClientName(rs.getString("sInterestExtClientName"));
			info.setInterestRemitInBank(rs.getString("sInterestRemitInBank"));
			info.setInterestRemitInProvince(rs.getString("sInterestRemitInProvince"));
			info.setInterestRemitInCity(rs.getString("sInterestRemitInCity"));
			info.setInterestCashFlowID(rs.getLong("nInterestCashFlowID"));
			info.setInterestBankID(rs.getLong("nInterestBankID"));
			info.setInterestExtBankNo(rs.getString("sInterestExtBankNo"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));			
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setCurrentInterest(rs.getDouble("mcurrentinterest"));
			info.setAdvanceRate(rs.getDouble("advancerate"));
			info.setIsAutoContinue(rs.getLong("isautocontinue"));
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
				case ORDERBY_DEPOSITNO :
				{
					order=" ORDER BY sDepositNo ";					
				}
					break;
				case ORDERBY_NEWDEPOSITNO :
				{
					order=" ORDER BY sNewDepositNo ";					
				}
					break;											
				case ORDERBY_AMOUNT :
				{
					order=" ORDER BY mAmount ";					
				}
					break;
				case ORDERBY_INTEREST :
				{
					order=" ORDER BY mPreDrawInterest ";					
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
	 * @return Collection ,包含FixedContinueInfo查询结果实体类的记录集
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
			if(info.getTypeID()==0)
			{					
				/*buffer.append("select sTransNo,nTransActionTypeID ,nAccountID,\n");
				buffer.append("sDepositNo,sNewDepositNo,\n");					
				buffer.append("mAmount,mWithDrawInterest,dtStart,\n");
				buffer.append("dtExecute,sAbstract,nStatusID \n");*/
				buffer.append("select * \n");
				buffer.append("from sett_TransFixedContinue \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");	
				
				ps = con.prepareStatement(buffer.toString());
								
				int index = 1; 
				ps.setLong(index++,info.getOfficeID());				
				ps.setLong(index++,info.getCurrencyID());				
				ps.setLong(index++,info.getTransactionTypeID());							
				ps.setLong(index++,info.getUserID());
				
				
				rs=ps.executeQuery();
				while(rs.next())
				{
					TransFixedContinueInfo resultInfo = new TransFixedContinueInfo();
						
					/*resultInfo.setTransNo(rs.getString("sTransNo"));
					resultInfo.setTransactionTypeID(rs.getLong("nTransActionTypeID"));
					resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("nAccountID")));						
					resultInfo.setDepositNo(rs.getString("sDepositNo"));
					resultInfo.setNewDepositNo(rs.getString("sNewDepositNo"));						
					resultInfo.setAmount(rs.getDouble("mAmount"));
					resultInfo.setWithDrawInterest(rs.getDouble("mPreDrawInterest"));
					resultInfo.setStartDate(rs.getTimestamp("dtStart"));
					resultInfo.setExecuteDate(rs.getTimestamp("dtExecute"));
					resultInfo.setAbstract(rs.getString("sAbstract"));
					resultInfo.setStatusID(rs.getLong("nStatusID"));*/
					
					resultInfo = this.getContinueDeposit(resultInfo,rs);	
					arrResult.add(resultInfo);						
						
				}										
				
			}	
			else if(info.getTypeID()==1) //业务复核
			{				
				/*buffer.append("select sTransNo,nTransActionTypeID ,nAccountID,\n");
				buffer.append("sDepositNo,sNewDepositNo,\n");					
				buffer.append("mAmount,mWithDrawInterest,dtStart,\n");
				buffer.append("dtExecute,sAbstract,nStatusID \n");*/
				buffer.append("select * \n");
				buffer.append("from sett_TransFixedContinue \n");
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
				ps.setLong(index++,info.getOfficeID());
				ps.setLong(index++,info.getCurrencyID());
				ps.setLong(index++,info.getTransactionTypeID());				
				ps.setLong(index++,info.getUserID());
				ps.setTimestamp(index++,info.getDate());
					
				rs=ps.executeQuery();
				while(rs.next())
				{
					TransFixedContinueInfo resultInfo = new TransFixedContinueInfo();
					
					resultInfo = this.getContinueDeposit(resultInfo,rs);	
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
	 * @param 定期存款续期转存复核匹配查询条件实体类
	 * @return Collection ,包含FixedContinueInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransFixedContinueInfo info) throws Exception
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;					
		try 
		{	
			StringBuffer buffer=null;	
			
	
				/**定期续存
				 * 
				 *基本信息
				 *定期账户号,定期客户名称，到期定期存款单据号（不要）
				 *续期新定期存款单据号，续期新定期存款起始日，
				 *续期新定期存款期限
				 *续期新定期存款利率，本金，定期续存执行日
				 *利息信息
				 *本利续存
				 *利息转至活期账户号，活期客户名称，
				 *利息付款方式 付款开户行
				 *缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者
				 */				
				buffer = new StringBuffer();
				buffer.append("select * \n");
				buffer.append("from sett_TransFixedContinue  \n");				
				buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append("and nInputUserID <>? and nAccountID=? and \n");
				//buffer.append("sDepositNo=? and sNewDepositNo=? and dtNewStart=? and  \n");				
				//buffer.append("dtNewStart=? and  \n");				
				buffer.append("sDepositNo=? and dtNewStart=? and  \n");
				buffer.append("nNewDepositTerm=? and mNewRate=? and mNewAmount=? \n");
				buffer.append("and dtExecute=? and nIsCapitalAndInterestTransfer=? and nReceiveInterestAccountID=? \n");
				//buffer.append("and nInterestPayTypeID=? and nInterestbankID=? \n");
				buffer.append("and nInterestbankID=? \n");
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
				//ps.setString(index++,info.getNewDepositNo());
				ps.setTimestamp(index++,info.getNewStartDate());
				ps.setLong(index++,info.getNewDepositTerm());
				ps.setDouble(index++,info.getNewRate());
				ps.setDouble(index++,info.getNewAmount());					
				ps.setTimestamp(index++,info.getExecuteDate());					
				ps.setLong(index++,info.getIsCapitalAndInterestTransfer());
				ps.setLong(index++,info.getReceiveInterestAccountID());
				//ps.setLong(index++,info.getInterestPayTypeID());	
				ps.setLong(index++,info.getInterestBankID());
				
				rs=ps.executeQuery();
				while(rs.next())
				{
					TransFixedContinueInfo depositInfo = new TransFixedContinueInfo();
					depositInfo=getContinueDeposit(depositInfo,rs);																		
					arrResult.add(depositInfo);								
				}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//throw e;
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
	public String queryTransFixedContinueSQL(QueryByStatusConditionInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		//业务处理
		if (qInfo.getTypeID() == 0) 
		{
			//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
			sbSQL.append("select aa.* \n");
			sbSQL.append("from sett_TransFixedContinue aa \n");
			sbSQL.append("where \n");
			sbSQL.append("aa.nofficeid ="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and aa.ncurrencyid ="+qInfo.getCurrencyID()+" \n");
			if(qInfo.getTransactionTypeID()>=0){
				sbSQL.append("and aa.ntransactiontypeid ="+qInfo.getTransactionTypeID()+" \n");
			}
			if(qInfo.getStatus()!=null)
			{
				//状态查询条件
				String query ="";
				query = getQueryString(qInfo);
				sbSQL.append(" and ("+ query + ") \n");
			}
			sbSQL.append("and ninputuserid ="+qInfo.getUserID()+" \n");
		} 
		else if (qInfo.getTypeID() == 1) //业务复核
			{			
			sbSQL.append("select aa.* \n");
			sbSQL.append("from sett_TransFixedContinue aa \n");
			sbSQL.append("where \n");
			sbSQL.append("aa.nofficeid ="+qInfo.getOfficeID()+" \n");
			sbSQL.append("and aa.ncurrencyid ="+qInfo.getCurrencyID()+" \n");
			if(qInfo.getTransactionTypeID()>=0){
				sbSQL.append("and aa.ntransactiontypeid ="+qInfo.getTransactionTypeID()+" \n");
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
