/*
 * Created on 2003-10-28
 *
 * InterestEstimate.java
 */
 
package com.iss.itreasury.settlement.obinstruction.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransChangeFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedChangeInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * @author xrli
 *
 * 利息费用结算结息处理业务对象，主要实现的功能包括：
 *
 * 
 *
 * 注意事项:为了使该数据访问对象对容器的事务和非容器的事务都提供支持，
 *          
 */
public class TransInfo
{
	/**
	 * 如果是该类的方法负责维护事务，应该从容器取得数据库连接还是直接通过JDBC访问，缺省为从容器取得。
	 */
	private boolean bConnectionFromContainer = true;
    
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 从容器或直接通过JDBC取得数据库连接。
	 * @return 数据库连接。
	 */
	private Connection getConnection() throws Exception
	{

		Connection con = null;
		if (bConnectionFromContainer)
		{
			con = Database.getConnection();
		}
		else
		{
			con = Database.getConnection();
		}
		return con;
	}
	/**
	 * 设置数据库连接的来源。
	 * @param bConnectionFromContainer
	 *        true - 从容器取得数据库连接。
	 *        false - 直接通过JDBC取得数据库连接。
	 */
	public void setConnectionFromContainer(boolean bConnectionFromContainer)
	{
		this.bConnectionFromContainer = bConnectionFromContainer;
	}

	/**
	 * 取得数据库连接的来源。
	 * @return 数据库连接的来源。
	 *        true - 从容器取得数据库连接。
	 *        false - 直接通过JDBC取得数据库连接。
	 */
	public boolean getConnectionFromContainer()
	{
		return bConnectionFromContainer;
	}
	/**
	 * 拒绝指令的后台操作
	 * @param con
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public void refuseInstr(long lID,String strReject,long lUserID,Timestamp tsDealDate) throws Exception
	{		
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		long lFlag=dao.refuseInstr(lID,strReject,lUserID,tsDealDate);
	}
	/**
	 * 放弃指令的后台操作
	 * @param con
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public void abandonInstr(long lID,long lStatusID,long lUserID) throws Exception
	{		
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		long lFlag=dao.abandonInstr(lID,lStatusID,lUserID);
	}
	/**
	 * 接收指令的后台操作
	 * @param con
	 * @param info
	 * @return
	 * @throws Exception
	 */
	/*
	public long acceptInstr(long lID,long lStatusID,long lUserID) throws Exception
	{		
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		
		long lFlag=dao.acceptInstr(lID,lStatusID,lUserID);
		return lFlag;
	}
	*/
	public long acceptInstr(long lID,long lStatusID,long lUserID) throws Exception
	{		
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		 FinanceInfo tempInfo = null;
	        tempInfo = dao.findByID(lID);
	        //修改网银指令接收出现两条指令的问题(2006-11-19)
	        if(tempInfo.getIsCanAccept()!=1)
	        {
	            throw new IException("成员单位已经取消该指令，请检查！");
	        }
	        if(tempInfo.getStatus()==OBConstant.SettInstrStatus.DEAL && tempInfo.getDealUserID()!=lUserID)
	        {
	            throw new IException("该指令已经被其他人接收，请检查！");
	        }
	        if(tempInfo.getStatus()==OBConstant.SettInstrStatus.DEAL && tempInfo.getDealUserID()==lUserID)
	        {
	            throw new IException("该指令已经被自已接收，请检查！");
	        }
	        long lFlag=dao.acceptInstr(lID,lStatusID,lUserID);
	        if (lFlag == -9)
	        {
	            throw new IException("接受指令错误，请检查！");
	        }
	 
	        return lFlag;
	}
	
	
	public long acceptInstrhl(long lID,long lStatusID,long lUserID) throws Exception
	{		
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		 FinanceInfo tempInfo = null;
	        tempInfo = dao.findByID(lID);
	        //修改网银指令接收出现两条指令的问题(2006-11-19)
	        if(tempInfo.getIsCanAccept()!=1)
	        {
	            throw new IException("成员单位已经取消该指令，请检查！");
	        }
	        if(tempInfo.getStatus()==OBConstant.SettInstrStatus.DEAL && tempInfo.getDealUserID()!=lUserID)
	        {
	            throw new IException("该指令已经被其他人接收，请检查！");
	        }
	       // if(tempInfo.getStatus()==OBConstant.SettInstrStatus.DEAL && tempInfo.getDealUserID()==lUserID)
	       // {
	       //     throw new IException("该指令已经被自已接收，请检查！");
	      //  }
	        long lFlag=dao.acceptInstr(lID,lStatusID,lUserID);
	        if (lFlag == -9)
	        {
	            throw new IException("接受指令错误，请检查！");
	        }
	 
	        return lFlag;
	}
	/**
	  * 获得Timestamp类型的系统时间   通过网银ID得到它的修改时间
	  * 
	  * @return
	  */
	 public  Timestamp getObModifyDate(long id)
	 {
	  Timestamp tsResult = null;
	  java.sql.PreparedStatement ps = null; // 
	  java.sql.ResultSet rs = null; //
	  String strSQL = null; //查询串
	  Connection con = null;
	  try
	  {
	   con = Database.getConnection();
	   strSQL = "select dtModify from OB_FINANCEINSTR where id="+id;
	   ps = con.prepareStatement(strSQL);
	   rs = ps.executeQuery();
	   if (rs.next())
	   {
	    
	    tsResult=rs.getTimestamp("dtModify");
	   }
	   for(int i=0;i<10;i++){
	    System.out.println("得到最后时间为："+tsResult);
	   }
	   
	   rs.close();
	   rs = null;
	   ps.close();
	   ps = null;
	   con.close();
	   con = null;
	  }
	  catch (Exception exp)
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
	    ;
	   }
	  }
	  return tsResult;
	 }
	 /**
	  * 更新网银指令修改时间
	  * 
	  * @return
	  */
	 public  long updateObModifyDate(long id)
	 {
	  long lResult=-2;
	  PreparedStatement ps = null; //
	  ResultSet rs = null; //
	  String strSQL = null; //查询串
	  Connection con = null;
	  try
	  {
	   //System.out.println("要更新的修改时间为:"+tsModify);
	   
	   con = Database.getConnection();
	   strSQL = "update OB_FINANCEINSTR set dtModify = sysdate where id="+id;
	   ps = con.prepareStatement(strSQL);
	   
	   //ps.setTimestamp(1);
	   
	   lResult=ps.executeUpdate();
	   if (lResult > 0) {
	    lResult = id;
	   }
	   
	   rs.close();
	   rs = null;
	   ps.close();
	   ps = null;
	   con.close();
	   con = null;
	  }
	  catch (Exception exp)
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
	    ;
	   }
	  }
	  return lResult;
	 }
	 /**
	  * 判断交易记录是否被修改过的私有方法：
	  * 
	  *true:修改过
	  *false:没有修改
	  */
	 public boolean obModifyCheckIsTouched(long lID, Timestamp tsTouchTime) 
	 {
	  
	  try
	  {
	   //得到最后的修改时间
	   Timestamp lastTouchDate1 = this.getObModifyDate(lID);
	   
	   System.out.println("得到页面传进来的记录修改时间为tsTouchTime:"+tsTouchTime);
	   System.out.println("得到页面传进来的记录修改时间为lastTouchDate1:"+lastTouchDate1);
	 
	   if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
	    return true;
	   else
	    return false;
	  }
	  
	  catch (Exception e)
	  {
	   e.printStackTrace();
	   return false;
	  }
	 }
	 
	/**
	 * 查找指令的后台操作
	 * @param boxu add 2007-9-12
	 */
	public FinanceInfo findEbankInstr(long lID,long lUserID,long lCurrencyID) throws Exception
	{		
		FinanceInfo info = new FinanceInfo();
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		
		info = dao.findByID(lID,lUserID,lCurrencyID);
		
		//boxu update 2007-9-12 针对网印账户取值ID是从表OB_PAYEEINFO获取,结算SETT_ACCOUNT对应账户ID不一致情况,进行一个中间转换
		if(info.getPayerAcctNo() != null)  //付款方账户号 
		{
			info.setPayerAcctID( NameRef.getAccountIdByNo(info.getPayerAcctNo()) );
		}
		if(info.getPayeeAcctNo() != null)  //收款方账户号
		{
			info.setPayeeAcctID( NameRef.getAccountIdByNo(info.getPayeeAcctNo()) );
		}
		if(info.getInterestPayeeAcctNo()!=null) //收利息账户号
		{
			info.setInterestPayeeAcctID( NameRef.getAccountIdByNo(info.getInterestPayeeAcctNo()) );
		}
		
		if(info.getDefaultTransType()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
		{
			if(info.getTransNo()!=null)
			{
				Sett_TransFixedWithDrawDAO fixDao = new Sett_TransFixedWithDrawDAO();				
				info.setDepositRate((fixDao.findByTransNo(info.getTransNo())).getRate());						
			}
		}
		if(info.getDefaultTransType()==SETTConstant.TransactionType.SPECIALOPERATION)
		{
			if(info.getTransNo()!=null)
			{
				Sett_TransSpecialOperationDAO specialDao = new Sett_TransSpecialOperationDAO();				
				info.setSpecialOperationTypeID((specialDao.findByID(specialDao.getIDByTransNo(info.getTransNo()))).getNoperationtypeid());						
			}
		}
		return info;
	}
	 
	/**
	 * 查找指令的后台操作
	 * @param con
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public FinanceInfo findInstr(long lID,long lUserID,long lCurrencyID) throws Exception
	{		
		FinanceInfo info = new FinanceInfo();
		OBFinanceInstrDao dao= new OBFinanceInstrDao();
		
		info=dao.findByID(lID,lUserID,lCurrencyID);
		if(info.getDefaultTransType()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW
		|| info.getDefaultTransType()==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
		{
			if(info.getTransNo()!=null)
			{
				TransFixedDrawInfo tInfo = new TransFixedDrawInfo();
				Sett_TransFixedWithDrawDAO fixDao = new Sett_TransFixedWithDrawDAO();
				tInfo = fixDao.findByTransNo(info.getTransNo());
				info.setDepositRate(tInfo.getRate());
				info.setInterestRate(tInfo.getAdvanceRate());
			}
		}
		if(info.getDefaultTransType()==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
		{
			if(info.getTransNo()!=null)
			{
				Sett_TransFixedContinueDAO conDao = new Sett_TransFixedContinueDAO();
				info.setInterestRate(conDao.findByTransNo(info.getTransNo()).getAdvanceRate());						
			}
		}
		if(info.getDefaultTransType()==SETTConstant.TransactionType.SPECIALOPERATION)
		{
			if(info.getTransNo()!=null)
			{
				Sett_TransSpecialOperationDAO specialDao = new Sett_TransSpecialOperationDAO();				
				info.setSpecialOperationTypeID((specialDao.findByID(specialDao.getIDByTransNo(info.getTransNo()))).getNoperationtypeid());						
			}
		}
		return info;
	}	
	/**
	 * 将网银的指令转化为定期/通知开立的实体
	 * @param info
	 * @param lUserID
	 * @return
	 * @throws Exception
	 */
	public TransFixedOpenInfo transFixedOpen(FinanceInfo info,long lUserID) throws Exception
	{
		TransFixedOpenInfo infoRtn = new TransFixedOpenInfo();
		infoRtn.setOfficeID(info.getOfficeID());
		infoRtn.setAbstract(info.getNote());
		infoRtn.setAccountID(info.getPayeeAcctID());
		infoRtn.setAccountNo(info.getPayeeAcctNo());
		infoRtn.setAmount(info.getAmount());
		infoRtn.setClientID(info.getClientID());
		infoRtn.setClientName(NameRef.getClientNameByID(info.getClientID()));
		infoRtn.setClientNo(NameRef.getClientCodeByID(info.getClientID()));
		infoRtn.setCurrencyID(info.getCurrencyID());
		infoRtn.setCurrentAccountID(info.getPayerAcctID());
		infoRtn.setCurrentAccountClientName(NameRef.getClientNameByAccountID(info.getPayerAcctID()));
		infoRtn.setCurrentAccountNo(info.getPayerAcctNo());
		UtilOperation uo = new UtilOperation();
		try {
			infoRtn.setDepositNo(uo.getOpenDepositNoBackGround(info.getPayeeAcctID()));
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		infoRtn.setDepositTerm(info.getFixedDepositTime());
		//infoRtn.setExecuteDate(info.getExecuteDate());
		infoRtn.setExecuteDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		//modify by xwhe 2008-12-30 接收网银指令时：交易的开始日期=结算系统开机日
		if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true))
		{
		infoRtn.setStartDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		}
		else
		{
		infoRtn.setStartDate(info.getExecuteDate());  	
		}		
        //modify by xwhe 2008-12-26 接受网银指令时：交易起息日 = 结算系统开机日
		if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true))
		{
		infoRtn.setInterestStartDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		}
		else
		{
		infoRtn.setInterestStartDate(info.getExecuteDate());	
		}
		//infoRtn.setInterestStartDate(info.getExecuteDate());	
		//infoRtn.setInterestStartDate(info.getExecuteDate());		
		//infoRtn.setEndDate(UtilOperation.getNextNDay(info.getExecuteDate(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))*30));		
		//infoRtn.setEndDate(DataFormat.getNextMonth(info.getExecuteDate(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))));
		//定期存款期限是天的情况//modify by xwhe 2008-12-30
		if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true))
		{
		if(info.getFixedDepositTime()>10000)
		{
			infoRtn.setEndDate(DataFormat.getNextDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()), (int)(info.getFixedDepositTime()-10000)));
		}
		else
		{
			//定期存款期限是月的情况
			infoRtn.setEndDate(getEndDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))));
		}
		
		}
		else
		{
		if(info.getFixedDepositTime()>10000)
		{
			infoRtn.setEndDate(DataFormat.getNextDate(info.getExecuteDate(), (int)(info.getFixedDepositTime()-10000)));
		}
		else
		{
			//定期存款期限是月的情况
			infoRtn.setEndDate(getEndDate(info.getExecuteDate(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))));
		}
		}
		infoRtn.setInputDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		infoRtn.setInputUserID(lUserID);
		infoRtn.setInputUserName(NameRef.getUserNameByID(lUserID));
		infoRtn.setInstructionNo(String.valueOf(info.getID()));
		infoRtn.setNoticeDay(info.getNoticeDay());
		infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
		// 交易类型在外面设置
		infoRtn.setTransactionTypeID(SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		//自动续存信息
		infoRtn.setIsAutoContinue(info.getIsAutoContinue());
		infoRtn.setAutocontinuetype(info.getAutocontinuetype());
		infoRtn.setAutocontinueaccountid(info.getAutocontinueaccountid());
		
		
		return  infoRtn;
	}	
	
	/**
	 * 将网银的指令转化为换开定期存单的实体
	 * @param info
	 * @param lUserID
	 * @return
	 * @throws Exception
	 * 根据定期开立的交易号，查出换开定期存单的数据，然后把换开的信息填入里面，具体的也就是更新的是换开的信息而己
	 */
	public TransFixedChangeInfo transFixedChange(FinanceInfo info,long lUserID) throws Exception
	{
		TransFixedChangeInfo infoRtn = new TransFixedChangeInfo();
		Sett_TransChangeFixedDepositDAO sett_TransChangeFixedDepositDAO=new Sett_TransChangeFixedDepositDAO();

		/**********************(1)根据交易号得到该定期开立的信息(其中包括定期开立的ID信息)****************/
		for(int i=0;i<5;i++)
			System.out.println("得到定期开立的交易号:"+info.getTransNo());
		//银行的指令号在此已经得到了，此是不用去管他了（在定期开立的时候)
		infoRtn=sett_TransChangeFixedDepositDAO.findByTransNo(info.getTransNo());
		System.out.println("================得到换开定期开立数据的ID:"+infoRtn.getID());
		
		/**********************(2)填入换开定期存单的数据****************/
		//即我们结算表当中的已经存在定期开立的数据
		infoRtn.setDepositBillNO(info.getSDepositBillNo());
		//网页接受过来的换开定期存单的状态为"保存"
		infoRtn.setDepositBillStatusID(SETTConstant.TransactionStatus.SAVE);
		//得到摘要
		infoRtn.setDepositBillABSTRACT(info.getSDepositBillAbstract());	
		//录入日期取的是系统当前日期
		infoRtn.setDepositBillInputDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		//录入人ID为进行接受时的操作人的ID
		infoRtn.setDepositBillInputUserID(lUserID);
		
		//返回换开定期存单的数据
		return  infoRtn;
	}	
	
	/**
	 * 返回活期交易的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransCurrentDepositInfo transCurrent(FinanceInfo info,long lUserID) throws Exception
	{
		TransCurrentDepositInfo infoRtn = new TransCurrentDepositInfo();
		try
		{
			infoRtn.setOfficeID(info.getOfficeID());
			infoRtn.setAbstractStr(info.getNote());
			infoRtn.setPayAccountID(info.getPayerAcctID());
			infoRtn.setPayClientID(NameRef.getClientIDByAccountID(info.getPayerAcctID()));
			infoRtn.setReceiveAccountID(info.getPayeeAcctID());
			infoRtn.setReceiveClientID(NameRef.getClientIDByAccountID(info.getPayeeAcctID()));			
			infoRtn.setAmount(info.getAmount());		
			infoRtn.setCurrencyID(info.getCurrencyID());		
			//infoRtn.setExecuteDate(info.getExecuteDate());
			infoRtn.setExecuteDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));	
			//modify by xwhe 2008-10-14 接受网银指令时：交易起息日 = 结算系统开机日
			if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
			{
			infoRtn.setInterestStartDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
			}
			else
			{
			infoRtn.setInterestStartDate(info.getExecuteDate());	
			}
			infoRtn.setInputDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
			infoRtn.setInputUserID(lUserID);		
			infoRtn.setInstructionNo(String.valueOf(info.getID()));
			if(info.getRemitType()==OBConstant.SettRemitType.BANKPAY||info.getRemitType()==OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER
					||info.getRemitType()==OBConstant.SettRemitType.FINCOMPANYPAY||info.getRemitType()==OBConstant.SettRemitType.PAYSUBACCOUNT)
			{
				//不用收款账户ID,置为-1,否则影响复核
				infoRtn.setReceiveAccountID(-1);
			}
			//银行付款	
			infoRtn.setExtAccountNo(info.getPayeeAcctNo());	
			infoRtn.setExtClientName(info.getPayeeName());
			infoRtn.setRemitInCity(info.getPayeeCity());
			infoRtn.setRemitInProvince(info.getPayeeProv());
			infoRtn.setRemitInBank(info.getPayeeBankName());
			
			//其它下属单位
			infoRtn.setSubClientID(info.getChildClientID());
			
			infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
			
			//预算新增
			infoRtn.setBudgetItemID(info.getBudgetItemID());
			
			//交易类型在外面设置
			infoRtn.setTransactionTypeID(info.getDefaultTransType());	
			
			//add by xiangzhou 财企接口新增
			//数据来源
			infoRtn.setLSource(info.getSource());
			
			//外部系统申请指令号
			infoRtn.setSApplyCode(info.getApplyCode());
			
			//联行号
			infoRtn.setSpayeebankexchangeno(info.getSPayeeBankExchangeNO());
			
			//CNAPS号
			infoRtn.setSpayeebankcnapsno(info.getSPayeeBankCNAPSNO());
			
			//机构号
			infoRtn.setSpayeebankorgno(info.getSPayeeBankOrgNO());
			
		}
		catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return infoRtn;
	}
	/**
	 * 返回特殊业务交易的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransSpecialOperationInfo transSpecial(FinanceInfo info,long lUserID) throws Exception
	{
		TransSpecialOperationInfo infoRtn = new TransSpecialOperationInfo();
		try
		{
			infoRtn.setNofficeid(info.getOfficeID());
			infoRtn.setNcurrencyid(info.getCurrencyID());	
			infoRtn.setNoperationtypeid(info.getSpecialOperationTypeID());
			
			infoRtn.setSabstract(info.getNote());
			infoRtn.setNpayaccountid(info.getPayerAcctID());
			infoRtn.setNpayclientid(NameRef.getClientIDByAccountID(info.getPayerAcctID()));
			infoRtn.setNreceiveaccountid(info.getPayeeAcctID());
			infoRtn.setNreceiveclientid(NameRef.getClientIDByAccountID(info.getPayeeAcctID()));			
			infoRtn.setMpayamount(info.getAmount());		
				
			
			infoRtn.setDtexecute(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));		
			infoRtn.setDtintereststart(info.getExecuteDate());	
			infoRtn.setDtinput(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
			infoRtn.setNinputuserid(lUserID);		
			infoRtn.setSinstructionno(String.valueOf(info.getID()));
			
			if(info.getRemitType()==OBConstant.SettRemitType.BANKPAY)
			{
				//不用收款账户ID,置为-1,否则影响复核
				infoRtn.setNreceiveaccountid(-1);
			}
			//银行付款	
			infoRtn.setSextaccountno(info.getPayeeAcctNo());	
			infoRtn.setSextclientname(info.getPayeeName());
			infoRtn.setSremitincity(info.getPayeeCity());
			infoRtn.setSremitinprovince(info.getPayeeProv());
			infoRtn.setSremitinbank(info.getPayeeBankName());
			
			infoRtn.setNstatusid(SETTConstant.TransactionStatus.SAVE);
			//交易类型在外面设置
			infoRtn.setNtransactiontypeid(SETTConstant.TransactionType.SPECIALOPERATION);		
		}
		catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return infoRtn;
	}
	/*
	 * 根据开始日期和期限得到结束日期
	 */
	private Timestamp getEndDate(Timestamp startDate,int depositTerm)
	{
		Timestamp endDate=null;
		int year =startDate.getYear()+1900;
		int month= startDate.getMonth()+1;				
		int day =  startDate.getDate();		
		if(month+depositTerm>12)
		{
			year=(month+depositTerm)/12+year;
			month=(month+depositTerm)%12;
			if(month==2)
			{
				if((year%4==0 && year%100!=0) || year%400==0 )
				{
					if(day>29)
					{
						day=29;
					}
				}
				else
				{
					if(day>28)
					{
						day=28;
					}				
				}
			}
			else if(month==4 || month==6 || month==9 || month==11)
			{
				if(day>30)
				{
					day=30;
				}
			}			
		}
		else
		{
			month=(month+depositTerm);
			if(month==2)
			{
				if((year%4==0 && year%100!=0) || year%400==0 )
				{
					if(day>29)
					{
						day=29;
					}
				}
				else
				{
					if(day>28)
					{
						day=28;
					}				
				}
			}
			else if(month==4 || month==6 || month==9 || month==11)
			{
				if(day>30)
				{
					day=30;
				}
			}			
		}
		endDate=DataFormat.getDateTime(year,month,day, 0, 0, 0);
		return endDate;
	}
	/**
	 * 返回信托（自营）贷款的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransRepaymentLoanInfo transTrustLoan(FinanceInfo info,long lUserID) throws Exception
	{
		TransRepaymentLoanInfo infoRtn = new TransRepaymentLoanInfo();
		infoRtn.setAbstract(info.getNote());
		infoRtn.setAmount(info.getAmount());		
		
		infoRtn.setCapitalAndInterstDealway(2);
		infoRtn.setClientID(info.getClientID());
		infoRtn.setCurrencyID(info.getCurrencyID());
		//保单号
		//infoRtn.setDeclarationNo()
		//活期账户号
		infoRtn.setDepositAccountID(info.getPayerAcctID());
		//infoRtn.setExecute(info.getExecuteDate());
		infoRtn.setExecute(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		infoRtn.setInputUserID(lUserID);
		
		//起息日????????????????
		infoRtn.setInterestStart(info.getExecuteDate());
		//上次结息日
		Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();			           
		infoRtn.setLatestInterestClear(subDao.findByID(info.getSubAccountID()).getSubAccountLoanInfo().getClearInterestDate());
				
		infoRtn.setIsRemitCompoundInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitOverDueInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitSuretyFee(SETTConstant.BooleanValue.ISFALSE);
		
		infoRtn.setLoanAccountID(info.getPayeeAcctID());
		infoRtn.setLoanContractID(info.getContractID());
		infoRtn.setLoanNoteID(info.getLoanNoteID());
		infoRtn.setOfficeID(info.getOfficeID());
		
		
		//提前还款通知单
		//infoRtn.setPreFormID()
		
		infoRtn.setInterest(info.getInterest());
		infoRtn.setCommission(info.getCommission());
		infoRtn.setCompoundInterest(info.getCompoundInterest());
		infoRtn.setOverDueInterest(info.getOverdueInterest());
		infoRtn.setSuretyFee(info.getSuretyFee());
		
		if(info.getRealSuretyFee()>0)
		{		
			infoRtn.setPaySuretyAccountID(info.getPayerAcctID());
		}
		if(info.getRealInterest()>0)
		{
			infoRtn.setPayInterestAccountID(info.getPayerAcctID());
		}
		infoRtn.setRealCompoundInterest(info.getRealCompoundInterest());
		infoRtn.setRealInterest(info.getRealInterest());
		infoRtn.setRealOverDueInterest(info.getRealOverdueInterest());
		infoRtn.setRealSuretyFee(info.getRealSuretyFee());
		infoRtn.setRealCommission(info.getRealCommission());
		
		infoRtn.setInput(info.getExecuteDate());
		infoRtn.setInterestClear(info.getClearInterest());
		
		//有问题？？
		infoRtn.setInterestStart(info.getClearInterest());
		
		
		infoRtn.setCurrentBalance(info.getDepositBalance()-info.getAmount());
		//infoRtn.setCurrentBalance(info.getDepositBalance());
		
		infoRtn.setSubAccountID(info.getSubAccountID());
		
		infoRtn.setInstructionNo(String.valueOf(info.getID()));
		//收担保费账户？？？？？？
		infoRtn.setReceiveSuretyAccountID(info.getInterestPayeeAcctID());
		infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
		//交易方向
		//infoRtn.setTransDirectionID()
		
		//后增加
		infoRtn.setCommissionRate(info.getCommissionRate());
		infoRtn.setCommissionStart(info.getCommissionStart());
		
		infoRtn.setCompoundInterestStart(info.getCompoundStart());
		infoRtn.setCompoundRate(info.getCompoundRate());
		
		infoRtn.setOverDueRate(info.getOverDueRate());
		infoRtn.setOverDueStart(info.getOverDueStart());
		
		infoRtn.setSuretyFeeRate(info.getSuretyRate());
		infoRtn.setSuretyFeeStart(info.getSuretyStart());
		
		infoRtn.setLoanRepaymentRate(info.getInterestRate());
		infoRtn.setCompoundAmount(info.getCompoundAmount());
		infoRtn.setOverDueAmount(info.getOverDueAmount());
		//后增加
		infoRtn.setInterestIncome(info.getInterestIncome());
		infoRtn.setRealInterestIncome(info.getRealInterestIncome());
		infoRtn.setInterestReceiveAble(info.getInterestReceiveable());
		infoRtn.setRealInterestReceiveAble(info.getRealInterestReceiveable());
		
		infoRtn.setInterestTax(info.getInterestTax());
		infoRtn.setRealInterestTax(info.getRealInterestTax());
		
		return infoRtn;
	}
	
	
	/**
	 * 返回信托（自营）贷款的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransRepaymentLoanInfo transBankGroupLoan(FinanceInfo info,long lUserID) throws Exception
	{
		TransRepaymentLoanInfo infoRtn = new TransRepaymentLoanInfo();
		infoRtn.setAbstract(info.getNote());
		infoRtn.setAmount(info.getAmount());		
		
		infoRtn.setCapitalAndInterstDealway(2);
		infoRtn.setClientID(info.getClientID());
		infoRtn.setCurrencyID(info.getCurrencyID());
		//保单号
		//infoRtn.setDeclarationNo()
		//活期账户号
		infoRtn.setDepositAccountID(info.getPayerAcctID());
		//infoRtn.setExecute(info.getExecuteDate());
		infoRtn.setExecute(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		infoRtn.setInputUserID(lUserID);
		
		//起息日????????????????
		infoRtn.setInterestStart(info.getExecuteDate());
		//上次结息日
		Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();			           
		infoRtn.setLatestInterestClear(subDao.findByID(info.getSubAccountID()).getSubAccountLoanInfo().getClearInterestDate());
				
		infoRtn.setIsRemitCompoundInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitOverDueInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitSuretyFee(SETTConstant.BooleanValue.ISFALSE);
		
		infoRtn.setLoanAccountID(info.getPayeeAcctID());
		infoRtn.setLoanContractID(info.getContractID());
		infoRtn.setLoanNoteID(info.getLoanNoteID());
		infoRtn.setOfficeID(info.getOfficeID());
		
		
		//提前还款通知单
		//infoRtn.setPreFormID()
		
		infoRtn.setInterest(info.getInterest());
		infoRtn.setCommission(info.getCommission());
		infoRtn.setCompoundInterest(info.getCompoundInterest());
		infoRtn.setOverDueInterest(info.getOverdueInterest());
		infoRtn.setSuretyFee(info.getSuretyFee());
		
		if(info.getRealSuretyFee()>0)
		{		
			infoRtn.setPaySuretyAccountID(info.getPayerAcctID());
		}
		if(info.getRealInterest()>0)
		{
			infoRtn.setPayInterestAccountID(info.getPayerAcctID());
		}
		infoRtn.setRealCompoundInterest(info.getRealCompoundInterest());
		infoRtn.setRealInterest(info.getRealInterest());
		infoRtn.setRealOverDueInterest(info.getRealOverdueInterest());
		infoRtn.setRealSuretyFee(info.getRealSuretyFee());
		infoRtn.setRealCommission(info.getRealCommission());
		
		infoRtn.setInput(info.getExecuteDate());
		infoRtn.setInterestClear(info.getClearInterest());
		
		//有问题？？
		infoRtn.setInterestStart(info.getClearInterest());
		
		
		infoRtn.setCurrentBalance(info.getDepositBalance()-info.getAmount());
		//infoRtn.setCurrentBalance(info.getDepositBalance());
		
		infoRtn.setSubAccountID(info.getSubAccountID());
		
		infoRtn.setInstructionNo(String.valueOf(info.getID()));
		//收担保费账户？？？？？？
		infoRtn.setReceiveSuretyAccountID(info.getInterestPayeeAcctID());
		infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
		//交易方向
		//infoRtn.setTransDirectionID()
		
		//后增加
		infoRtn.setCommissionRate(info.getCommissionRate());
		infoRtn.setCommissionStart(info.getCommissionStart());
		
		infoRtn.setCompoundInterestStart(info.getCompoundStart());
		infoRtn.setCompoundRate(info.getCompoundRate());
		
		infoRtn.setOverDueRate(info.getOverDueRate());
		infoRtn.setOverDueStart(info.getOverDueStart());
		
		infoRtn.setSuretyFeeRate(info.getSuretyRate());
		infoRtn.setSuretyFeeStart(info.getSuretyStart());
		
		infoRtn.setLoanRepaymentRate(info.getInterestRate());
		infoRtn.setCompoundAmount(info.getCompoundAmount());
		infoRtn.setOverDueAmount(info.getOverDueAmount());
		//后增加
		infoRtn.setInterestIncome(info.getInterestIncome());
		infoRtn.setRealInterestIncome(info.getRealInterestIncome());
		infoRtn.setInterestReceiveAble(info.getInterestReceiveable());
		infoRtn.setRealInterestReceiveAble(info.getRealInterestReceiveable());
		
		infoRtn.setInterestTax(info.getInterestTax());
		infoRtn.setRealInterestTax(info.getRealInterestTax());
		
		//处理参与行信息
		ArrayList list = new ArrayList();
		BankLoanQuery bankLoanQuery = new BankLoanQuery();
		list = (ArrayList)bankLoanQuery.findByFormID(info.getLoanNoteID());
		
		if(list!=null && list.size()!=0)
		{	
			ArrayList syndicationLoanInterest = new ArrayList();
			double dAmountSum=0;
			for(int i = 0 ;i< list.size();i++)
			{		
				double dMemberAmount=0.0;				
				
				BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();
				SyndicationLoanInterestInfo obj = new SyndicationLoanInterestInfo();
				
				bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);
				
				dMemberAmount = UtilOperation.Arith.round(info.getAmount()*bankLoanDrawInfo.getRate()/100,2);
				
				dAmountSum = dAmountSum+dMemberAmount;
			}			
			
			for(int i = 0 ;i< list.size();i++)
			{		
				double dMemberAmount=0.0;
				double dMemberInterest=0.0;
				double dMemberCompoundInterest=0.0;
				double dMemberForpeitInterest=0.0;
				
				
				BankLoanDrawInfo bankLoanDrawInfo = new BankLoanDrawInfo();
				SyndicationLoanInterestInfo obj = new SyndicationLoanInterestInfo();
				
				bankLoanDrawInfo = (BankLoanDrawInfo)list.get(i);
				if(dAmountSum!=info.getAmount())
				{
					if(bankLoanDrawInfo.getIsHead()==1)
					{
						dMemberAmount = UtilOperation.Arith.round(info.getAmount()*bankLoanDrawInfo.getRate()/100,2) + info.getAmount()-dAmountSum;
					}
					else
					{
						dMemberAmount = UtilOperation.Arith.round(info.getAmount()*bankLoanDrawInfo.getRate()/100,2);
					}
				}
				else
				{
					dMemberAmount = UtilOperation.Arith.round(info.getAmount()*bankLoanDrawInfo.getRate()/100,2);
				}
				
				
				dMemberInterest = info.getRealInterest()*bankLoanDrawInfo.getRate()/100;
				dMemberCompoundInterest = info.getRealCompoundInterest()*bankLoanDrawInfo.getRate()/100;
				dMemberForpeitInterest = info.getRealOverdueInterest()*bankLoanDrawInfo.getRate()/100;
				
				
				obj.setBankID(bankLoanDrawInfo.getBankID());
				obj.setBankName(bankLoanDrawInfo.getBankName());
				obj.setContractID(bankLoanDrawInfo.getContractID());
				obj.setIsHead(bankLoanDrawInfo.getIsHead());
				obj.setFormID(info.getLoanNoteID());
				obj.setRate(bankLoanDrawInfo.getRate());
				
				
				obj.setAmount(dMemberAmount);
				obj.setInterest(dMemberInterest);
				obj.setCompoundInterest(dMemberCompoundInterest);
				obj.setForpeitInterest(dMemberForpeitInterest);
				obj.setFormID(info.getLoanNoteID());
				syndicationLoanInterest.add(obj);
			}
			infoRtn.setSyndicationLoanInterest(syndicationLoanInterest);
					
		}		
		return infoRtn;
	}
	/**
	 * 返回委托贷款的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransRepaymentLoanInfo transConsignLoan(FinanceInfo info,long lUserID) throws Exception
	{
		TransRepaymentLoanInfo infoRtn = new TransRepaymentLoanInfo();
		infoRtn.setAbstract(info.getNote());
		infoRtn.setInstructionNo(String.valueOf(info.getID()));
		infoRtn.setAmount(info.getAmount());
		infoRtn.setCapitalAndInterstDealway(2);
		infoRtn.setClientID(info.getClientID());
		infoRtn.setCurrencyID(info.getCurrencyID());
		if(info.getCommission()>0)
		{		
			//付手续费账户？？？？s
			infoRtn.setCommissionAccountID(info.getPayerAcctID());
		}
		
		
		
		//委贷活期存款账户号
		//infoRtn.setConsignDepositAccountID(info.getPayerAcctID());
		//活期存款账户号
		infoRtn.setDepositAccountID(info.getPayerAcctID());
		//保单号
		//infoRtn.setDeclarationNo()
				
		
		
		//上次结息日
		Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();			           
		infoRtn.setLatestInterestClear(subDao.findByID(info.getSubAccountID()).getSubAccountLoanInfo().getClearInterestDate());
		
		//委贷存款账户号
		infoRtn.setConsignAccountID(subDao.findByID(info.getSubAccountID()).getSubAccountLoanInfo().getConsignAccountID());
		//????infoRtn.setFreeFormID()
		
		infoRtn.setInputUserID(lUserID);
				
		//起息日????????????????
		infoRtn.setInterestStart(info.getExecuteDate());
		infoRtn.setExecute(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
				
		infoRtn.setIsRemitCompoundInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitOverDueInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setCurrentBalance(info.getDepositBalance()-info.getAmount());
		//infoRtn.setCurrentBalance(info.getDepositBalance());
				
		infoRtn.setLoanAccountID(info.getPayeeAcctID());
		infoRtn.setLoanContractID(info.getContractID());
		infoRtn.setLoanNoteID(info.getLoanNoteID());
		infoRtn.setOfficeID(info.getOfficeID());
		
		if(info.getInterest()>0)
		{		
			infoRtn.setPayInterestAccountID(info.getPayerAcctID());
		}
		
		//提前还款通知单
		//infoRtn.setPreFormID()
		infoRtn.setInterest(info.getInterest());		
		infoRtn.setCommission(info.getCommission());
		infoRtn.setCompoundInterest(info.getCompoundInterest());
		infoRtn.setOverDueInterest(info.getOverdueInterest());
		infoRtn.setSuretyFee(info.getSuretyFee());
		
		infoRtn.setRealCommission(info.getRealCommission());
		infoRtn.setRealCompoundInterest(info.getRealCompoundInterest());
		infoRtn.setRealInterest(info.getRealInterest());
		infoRtn.setRealOverDueInterest(info.getRealOverdueInterest());
		infoRtn.setRealSuretyFee(info.getRealSuretyFee());	
		
		
		
		//后增加
		
		infoRtn.setCommissionRate(info.getCommissionRate());
		infoRtn.setCommissionStart(info.getCommissionStart());
		
		infoRtn.setCompoundInterestStart(info.getCompoundStart());
		infoRtn.setCompoundRate(info.getCompoundRate());
		
		infoRtn.setOverDueRate(info.getOverDueRate());
		infoRtn.setOverDueStart(info.getOverDueStart());
		
		infoRtn.setSuretyFeeRate(info.getSuretyRate());
		infoRtn.setSuretyFeeStart(info.getSuretyStart());
		
		infoRtn.setLoanRepaymentRate(info.getInterestRate());
		infoRtn.setCompoundAmount(info.getCompoundAmount());
		infoRtn.setOverDueAmount(info.getOverDueAmount());
		//infoRtn.setCurrentBalance(info.getBalance());		
		
		//后增加
		infoRtn.setInterestIncome(info.getInterestIncome());
		infoRtn.setRealInterestIncome(info.getRealInterestIncome());
		infoRtn.setInterestReceiveAble(info.getInterestReceiveable());
		infoRtn.setRealInterestReceiveAble(info.getRealInterestReceiveable());
		infoRtn.setInterestTax(info.getInterestTax());
		infoRtn.setRealInterestTax(info.getRealInterestTax());
		
		infoRtn.setSubAccountID(info.getSubAccountID());
		
		infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
		//交易方向
		//infoRtn.setTransDirectionID()
		return infoRtn;
	}
	/**
	 * 返回利息费用支付的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransRepaymentLoanInfo transInterest(FinanceInfo info,long lUserID) throws Exception
	{
		TransRepaymentLoanInfo infoRtn = new TransRepaymentLoanInfo();
		infoRtn.setAbstract(info.getNote());
		infoRtn.setInstructionNo(String.valueOf(info.getID()));		
		infoRtn.setClientID(info.getClientID());
		infoRtn.setCurrencyID(info.getCurrencyID());		
		
		infoRtn.setInterestClear(info.getClearInterest());
		infoRtn.setInput(info.getExecuteDate());
		//委贷账户号
		//infoRtn.setConsignAccountID()
		//保单号
		//infoRtn.setDeclarationNo()
		//活期账户号
		//infoRtn.setDepositAccountID(info.getc)		
		
		//infoRtn.setExecute(info.getExecuteDate());
		infoRtn.setExecute(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		infoRtn.setInput(info.getExecuteDate());		
		//????infoRtn.setFreeFormID()
		
		infoRtn.setInputUserID(lUserID);
		//上次结息日
		Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();			           
		infoRtn.setLatestInterestClear(subDao.findByID(info.getSubAccountID()).getSubAccountLoanInfo().getClearInterestDate());
				
		//起息日????????????????
		infoRtn.setInterestStart(info.getExecuteDate());		
		infoRtn.setIsRemitCompoundInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitInterest(SETTConstant.BooleanValue.ISFALSE);
		infoRtn.setIsRemitOverDueInterest(SETTConstant.BooleanValue.ISFALSE);
		
				
		infoRtn.setLoanAccountID(info.getPayeeAcctID());
		infoRtn.setLoanContractID(info.getContractID());
		infoRtn.setLoanNoteID(info.getLoanNoteID());
		infoRtn.setOfficeID(info.getOfficeID());
		infoRtn.setPayInterestAccountID(info.getPayerAcctID());
		
		//提前还款通知单
		//infoRtn.setPreFormID();
		infoRtn.setInterest(info.getInterest());		
		infoRtn.setCommission(info.getCommission());
		infoRtn.setCompoundInterest(info.getCompoundInterest());
		infoRtn.setOverDueInterest(info.getOverdueInterest());
		infoRtn.setSuretyFee(info.getSuretyFee());
		
		infoRtn.setRealCommission(info.getRealCommission());
		infoRtn.setRealCompoundInterest(info.getRealCompoundInterest());
		infoRtn.setRealInterest(info.getRealInterest());
		infoRtn.setRealOverDueInterest(info.getRealOverdueInterest());
		infoRtn.setRealSuretyFee(info.getRealSuretyFee());	
		
				
		
		//后增加
		infoRtn.setCommissionRate(info.getCommissionRate());
		infoRtn.setCommissionStart(info.getCommissionStart());
		
		infoRtn.setCompoundInterestStart(info.getCompoundStart());
		infoRtn.setCompoundRate(info.getCompoundRate());
		
		infoRtn.setOverDueRate(info.getOverDueRate());
		infoRtn.setOverDueStart(info.getOverDueStart());
		
		infoRtn.setSuretyFeeRate(info.getSuretyRate());
		infoRtn.setSuretyFeeStart(info.getSuretyStart());
		
		infoRtn.setLoanRepaymentRate(info.getInterestRate());
		infoRtn.setCompoundAmount(info.getCompoundAmount());
		infoRtn.setOverDueAmount(info.getOverDueAmount());
		//infoRtn.setCurrentBalance(info.getBalance());		
		//后增加
		infoRtn.setInterestIncome(info.getInterestIncome());
		infoRtn.setRealInterestIncome(info.getRealInterestIncome());
		infoRtn.setInterestReceiveAble(info.getInterestReceiveable());
		infoRtn.setRealInterestReceiveAble(info.getRealInterestReceiveable());
		infoRtn.setInterestTax(info.getInterestTax());
		infoRtn.setRealInterestTax(info.getRealInterestTax());
		
		infoRtn.setSubAccountID(info.getSubAccountID());
		
		infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
		//交易方向
		//infoRtn.setTransDirectionID()
		return infoRtn;
	}
	/**
	 * 返回定期/通知支取交易的dataentity
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */	
	public TransFixedDrawInfo transFixedDraw(FinanceInfo info,long lUserID) throws Exception
	{
		TransFixedDrawInfo infoRtn = new TransFixedDrawInfo();
		try
		{
			infoRtn.setSubAccountID(info.getSubAccountID());
			//默认为分笔
			infoRtn.setCapitalAndInterestDealWay(2);		
			infoRtn.setDepositBalance(info.getDepositBalance());
			infoRtn.setDepositNo(info.getDepositNo());
				
			infoRtn.setRate(info.getDepositRate());	
			infoRtn.setAmount(info.getDepositAmount());			
			//infoRtn.setDrawAmount(info.getDepositAmount());
			InterestOperation io = new InterestOperation();
			InterestsInfo ioInfo = new InterestsInfo();
			 
			//通知与定期利息处理也不同
			 if(info.getDefaultTransType()==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			 {
			    double interest = 0.0;
			    double preDrawInterest = 0.0;  //计提利息
			    double balance = 0.0;  //账户余额
			    double amount = 0.0;  //支取金额
			    try
				{	
                    //modify by xwhe 2009-09-25 接受网银指令时：交易起息日 = 结算系统开机日 重新计算利息 
					if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
					{
					interest = io.calculateNoticeDepositAccountInterest(
								info.getCurrencyID()
					    	  , info.getDepositRate()
					    	  , SETTConstant.InterestRateTypeFlag.YEAR
					    	  , info.getAmount()
					    	  , info.getDepositStart()
					    	  , Env.getSystemDate(info.getOfficeID(),info.getCurrencyID())
					    	  );
					}
					else
					{
						
				   interest = io.calculateNoticeDepositAccountInterest(info.getCurrencyID(),info.getDepositRate(),SETTConstant.InterestRateTypeFlag.YEAR,info.getAmount(),info.getDepositStart(),info.getExecuteDate());
					
					}
					
				}
				catch (IException e)
				{   
				 throw new IRollbackException(null,e.getErrorCode());
				  
				}
				infoRtn.setDrawInterest(interest); 
				//支取金额
				infoRtn.setDrawAmount(info.getAmount());
				
				//Boxu Add 2008年3月22日 获得通知存款计提利息
				Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();
				SubAccountFixedInfo subAccountFixedInfo = new SubAccountFixedInfo();
				subAccountFixedInfo = subDao.findByID(info.getSubAccountID()).getSubAccountFixedInfo();
				
				amount = DataFormat.formatDouble( info.getAmount() );
				balance = DataFormat.formatDouble( subAccountFixedInfo.getBalance() );
				preDrawInterest = DataFormat.formatDouble( subAccountFixedInfo.getPreDrawInterest() );
				
				preDrawInterest = 
					UtilOperation.Arith.mul(
						UtilOperation.Arith.div(amount, balance), preDrawInterest);
				
				infoRtn.setPreDrawInterest(preDrawInterest);  //计提利息
				infoRtn.setStrikePreDrawInterest(preDrawInterest);  //冲销计提利息
				
				//Boxu Add 2008年3月22日 存款余额保存错误
				infoRtn.setDepositBalance(UtilOperation.Arith.sub(balance, amount));
			 }
			 else if(info.getDefaultTransType()==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
			 {
			   try
				 {
				 	//网银只有全部支取				   
				  	//ioInfo = io.calculateFixedDepositAccountInterest(info.getSubAccountID(),info.getAmount(),info.getExecuteDate());
				    //modify by xwhe 2008-12-30 接受网银指令时：交易起息日 = 结算系统开机日 重新计算利息 
					if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
					{
					ioInfo = io.calculateFixedDepositAccountInterest(
					    		info.getSubAccountID()
					    	  , info.getAmount()
					    	  , Env.getSystemDate(info.getOfficeID(),info.getCurrencyID())
					    	  , info.getAdvanceRate());
					}
					else
					{
				    //Boxu Update 2008年4月2日 根据页面录入的利率计算利息
				    ioInfo = io.calculateFixedDepositAccountInterest(
				    		info.getSubAccountID()
				    	  , info.getAmount()
				    	  , info.getExecuteDate()
				    	  , info.getAdvanceRate());
					}
				 }
				 catch (IException e)
				 {
					 throw new IRollbackException(null,e.getErrorCode());	   
				 }
				infoRtn.setPreDrawInterest(ioInfo.getPreDrawInterest());
				infoRtn.setStrikePreDrawInterest(ioInfo.getStrikePreDrawInterest());
				infoRtn.setCurrentInterest(ioInfo.getCurrentInterest());  
				infoRtn.setPayableInterest(ioInfo.getInterestPayment());
				infoRtn.setRemainAmount(info.getDepositAmount()-info.getAmount());
				
				infoRtn.setAdvanceRate(info.getAdvanceRate());
				
				Sett_SubAccountDAO subDao = new Sett_SubAccountDAO();			           
				infoRtn.setEndDate(subDao.findByID(info.getSubAccountID()).getSubAccountFixedInfo().getEndDate());
				//去支取金额
				infoRtn.setDrawAmount(info.getAmount());
				/*
				if(infoRtn.getEndDate().after(info.getExecuteDate()))
				{
					//支取金额
					infoRtn.setDrawAmount(info.getAmount());
				}
				else
				{
					infoRtn.setDrawAmount(0);
				}
				*/
			 }			
			//infoRtn.setPreDrawInterest(dPreDrawInterest);					
			//infoRtn.setSubAccountID(lSubAccountID);			
			//infoRtn.setCurrentInterest(info.get);			
			//infoRtn.setDrawInterest(dDrawInterest);			
			//infoRtn.setPayableInterest(dPayableInterest);
			//infoRtn.setStrikePreDrawInterest(dStrikePreDrawInterest);
			//infoRtn.setTotalUnpayInterest(dTotalUnpayInterest);
			if(info.getInterestRemitType()==OBConstant.SettRemitType.BANKPAY)
			{
				//开户行华能自己选择
				//infoRtn.setInterestBankID()
				infoRtn.setInterestExtAcctNo(info.getInterestPayeeAcctNo());
				infoRtn.setInterestExtClientName(info.getInterestPayeeName());
				infoRtn.setInterestRemitInBank(info.getInterestPayeeBankName());
				infoRtn.setInterestRemitInCity(info.getInterestPayeeCity());
				infoRtn.setInterestRemitInProvince(info.getInterestPayeeProv());
			}
			else
			{
				infoRtn.setReceiveInterestAccountClientName(NameRef.getClientNameByAccountID(info.getInterestPayeeAcctID()));
				//infoRtn.setReceiveInterestAccountID(info.getInterestPayeeAcctID());
				//add by zcwang 2007-09-18 网银收款方帐户转换成结算帐户
				infoRtn.setReceiveInterestAccountID(info.getInterestPayeeAcctID());
				//
				infoRtn.setReceiveInterestAccountNo(NameRef.getAccountNoByID(info.getInterestPayeeAcctID()));
			}
			
			infoRtn.setOfficeID(info.getOfficeID());						
			infoRtn.setOfficeID(info.getOfficeID());
			infoRtn.setAbstract(info.getNote());
			infoRtn.setAccountID(info.getPayerAcctID());
			infoRtn.setAccountNo(info.getPayerAcctNo());
						
			infoRtn.setClientID(info.getClientID());
			infoRtn.setClientName(NameRef.getClientNameByID(info.getClientID()));
			infoRtn.setClientNo(NameRef.getClientCodeByID(info.getClientID()));
			infoRtn.setCurrencyID(info.getCurrencyID());
			//收款方账户根据汇款方式判断
			if(info.getRemitType()==OBConstant.SettRemitType.BANKPAY)
			{
				//开户行华能自己选择
				//infoRtn.setBankID()
				infoRtn.setExtAcctNo(info.getPayeeAcctNo());
				infoRtn.setExtClientName(info.getPayeeName());
				infoRtn.setRemitInBank(info.getPayeeBankName());
				infoRtn.setRemitInCity(info.getPayeeCity());
				infoRtn.setRemitInProvince(info.getPayeeProv());
			}
			else
			{			
				infoRtn.setCurrentAccountID(info.getPayeeAcctID());
				infoRtn.setCurrentAccountClientName(NameRef.getClientNameByAccountID(info.getPayeeAcctID()));
				infoRtn.setCurrentAccountNo(info.getPayeeAcctNo());
			}
			
			
			infoRtn.setDepositTerm(info.getFixedDepositTime());
			//infoRtn.setExecuteDate(info.getExecuteDate());
			infoRtn.setExecuteDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
			infoRtn.setStartDate(info.getDepositStart());			
            //modify by xwhe 2008-12-30 接受网银指令时：交易起息日 = 结算系统开机日
			if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
			{
			infoRtn.setInterestStartDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
			}
			else
			{
			infoRtn.setInterestStartDate(info.getExecuteDate());	
			}
		   //	infoRtn.setInterestStartDate(info.getExecuteDate());						             		
			//infoRtn.setEndDate(UtilOperation.getNextNDay(info.getDepositStart(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))*30));
			infoRtn.setInputDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
			infoRtn.setInputUserID(lUserID);
			infoRtn.setInputUserName(NameRef.getUserNameByID(lUserID));
			infoRtn.setInstructionNo(String.valueOf(info.getID()));
			infoRtn.setNoticeDay(info.getNoticeDay());
			infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
			infoRtn.setIsAutoContinue(info.getIsAutoContinue());
			infoRtn.setAutocontinuetype(info.getAutocontinuetype());
			infoRtn.setAutocontinueaccountid(info.getAutocontinueaccountid());
			
			if(info.getRemitType()==OBConstant.SettRemitType.BANKPAY)
			{
				//不用收款账户ID,置为-1,否则影响复核
				infoRtn.setCurrentAccountID(-1);
			}
			if(info.getInterestRemitType()==OBConstant.SettRemitType.BANKPAY)
			{
				//不用收款账户ID,置为-1,否则影响复核
				infoRtn.setReceiveInterestAccountID(-1);
			}
				//交易类型在外面设置
				
		}		
		catch(IRollbackException e) {		
			e.printStackTrace();
			throw e;
		}				
		return infoRtn;
	}
	/**
	 * 将网银的指令转化为定期续存的实体
	 * @param info
	 * @param lUserID
	 * @return
	 * @throws Exception
	 */
	public TransFixedContinueInfo transFixedContinue(FinanceInfo info,long lUserID) throws Exception
	{
		UtilOperation uo = new UtilOperation();
		TransFixedContinueInfo infoRtn = new TransFixedContinueInfo();
		infoRtn.setOfficeID(info.getOfficeID());
		infoRtn.setCurrencyID(info.getCurrencyID());
		infoRtn.setClientID(info.getClientID());
		infoRtn.setAccountID(info.getPayerAcctID());
		infoRtn.setDepositNo(info.getDepositNo());
		infoRtn.setRate(info.getDepositRate());
		
		infoRtn.setStartDate(info.getDepositStart());
		//定期续存为天的情况
		if(info.getFixedDepositTime()>10000){
		infoRtn.setEndDate(DataFormat.getNextDate(info.getDepositStart(), (int)(info.getFixedDepositTime()-10000)));	
		}else{
		//定期续存为月的情况	
		infoRtn.setEndDate(getEndDate(info.getDepositStart(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))));
		}
		infoRtn.setDepositTerm(info.getFixedDepositTime());
		infoRtn.setSubAccountID(info.getSubAccountID());
		infoRtn.setAmount(info.getAmount());
		infoRtn.setNewDepositNo(uo.getOpenDepositNoBackGround(info.getPayerAcctID()));   //新定期存单号
		infoRtn.setExecuteDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		//infoRtn.setNewEndDate(getEndDate(info.getExecuteDate(),Integer.parseInt(String.valueOf(info.getFixedDepositTime())))); //续期新定期存款起始日为定期存款的结束日
		//modify by xwhe 2008-12-30 新存单的开始日期修改为开机日，同时修改新存单的结束日期
		//if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
		//{
		//infoRtn.setNewStartDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		//infoRtn.setNewEndDate(getEndDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()),Integer.parseInt(String.valueOf(info.getSDepositBillPeriod()))));
		//}
		//else
		//{
		//infoRtn.setNewStartDate(info.getSDepositBillStartDate());
		//infoRtn.setNewEndDate(info.getSDepositBillEndDate());
		//}
		infoRtn.setNewDepositTerm(info.getSDepositBillPeriod());
		infoRtn.setNewAmount(info.getAmount());
		//		利息信息 add by zcwang 2007-09-05
		InterestOperation io = new InterestOperation();
		InterestsInfo ioInfo = new InterestsInfo();
		//modify by xwhe 2008-12-30 修改执行日期为开机日 重新计算利息
		try
		{
			if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
			{
				ioInfo = io.calculateFixedDepositAccountInterest(
						info.getSubAccountID(),
						info.getAmount(),
						Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()),
						info.getAdvanceRate() );
			}
			else
			{
				ioInfo = io.calculateFixedDepositAccountInterest(
						info.getSubAccountID(),
						info.getAmount(),
						info.getExecuteDate(),
						info.getAdvanceRate() );
			}
			

		}
		catch (IException e)
		{
	    throw new IRollbackException(null,e.getErrorCode());	   
		}
		infoRtn.setPreDrawInterest(ioInfo.getPreDrawInterest());
		infoRtn.setPayableInterest(ioInfo.getInterestPayment());
		
		infoRtn.setCurrentInterest(ioInfo.getCurrentInterest());
		infoRtn.setAdvanceRate(info.getAdvanceRate());
		
		infoRtn.setWithDrawInterest(
				ioInfo.getPreDrawInterest()+
				ioInfo.getInterestPayment()+
				ioInfo.getCurrentInterest() );
		//
//		infoRtn.setPreDrawInterest(info.getRealInterestReceiveable());
//		infoRtn.setPayableInterest(info.getRealInterestIncome());
//		infoRtn.setWithDrawInterest(info.getRealInterestReceiveable()+info.getRealInterestIncome());
		//add by zcwang 2007-10-11 网银传过来的参数,和结算不一致 将网银传过来的参数，转换为结算需要的参数
		//新定期存款处理方式；
		//网银：本利续存
		if(info.getSDepositInterestDeal()==1)
		{
		infoRtn.setIsCapitalAndInterestTransfer(1);
		}
		//网银：利息转至活期账户
		else if(info.getSDepositInterestDeal()==2)
		{
			infoRtn.setIsCapitalAndInterestTransfer(-1);
		}
		//
		//com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();
		//infoRtn.setReceiveInterestAccountID(eBankNameRef.getAccountIdByEbankRecId(info.getSDepositInterestToAccountID()));
		infoRtn.setReceiveInterestAccountID(info.getSDepositInterestToAccountID());
		infoRtn.setAbstract(info.getNote());
		infoRtn.setAccountNo(info.getPayerAcctNo());
		infoRtn.setClientName(NameRef.getClientNameByID(info.getClientID()));
		infoRtn.setClientNo(NameRef.getClientCodeByID(info.getClientID()));
		//infoRtn.setExecuteDate(info.getExecuteDate());
        //modify by xwhe 2008-12-26 接受网银指令时：交易起息日 = 结算系统开机日
	//	if ( Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_MODIFYINTERESTDATE,true) )
	//	{
	//	infoRtn.setInterestStartDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
	//	}
	//	else
	//	{
		//modify by wangzhen 2011-03-01 接收网银指令时，交易起息日不受配置文件限制，取网银端的新子账户开始日
		infoRtn.setInterestStartDate(info.getSDepositBillStartDate());	
		infoRtn.setNewStartDate(info.getSDepositBillStartDate());
		infoRtn.setNewEndDate(info.getSDepositBillEndDate());
	//	}
		//infoRtn.setInterestStartDate(info.getExecuteDate());		
		//infoRtn.setEndDate(UtilOperation.getNextNDay(info.getExecuteDate(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))*30));		
		//infoRtn.setEndDate(DataFormat.getNextMonth(info.getExecuteDate(),Integer.parseInt(String.valueOf(info.getFixedDepositTime()))));
		infoRtn.setInputDate(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID()));
		infoRtn.setInputUserID(lUserID);
		infoRtn.setInputUserName(NameRef.getUserNameByID(lUserID));
		infoRtn.setInstructionNo(String.valueOf(info.getID()));
		infoRtn.setStatusID(SETTConstant.TransactionStatus.SAVE);
		// 交易类型在外面设置
		infoRtn.setTransactionTypeID(SETTConstant.TransactionType.FIXEDCONTINUETRANSFER);		
		
		if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	
		{
			infoRtn.setIsAutoContinue(info.getIsAutoContinue());
			infoRtn.setAutocontinuetype(info.getAutocontinuetype());
			infoRtn.setAutocontinueaccountid(info.getAutocontinueaccountid());
			
		}
		else
		{
			infoRtn.setIsAutoContinue(-1);
			infoRtn.setAutocontinuetype(-1);
			infoRtn.setAutocontinueaccountid(-1);						
		}
		return  infoRtn;
	}	
	

}
