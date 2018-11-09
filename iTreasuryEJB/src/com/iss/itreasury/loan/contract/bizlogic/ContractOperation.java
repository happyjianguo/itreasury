/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contract.bizlogic;


import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.loan.loanapply.dataentity.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.loanapply.dao.*;
import com.iss.itreasury.loan.contract.dao.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;

/**
 * @author zgd
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractOperation
{
	private static Log4j log4j = null;

	public ContractOperation()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * 在贷款申请合同已执行之前，如果修改了贷款申请的信息，
	 * 要调用此方法把合同信息删除。
	 * @param loanID 贷款申请ID 
	 * @return
	 * @throws Exception
	 */
	public long beforeUpdateLoan(long loanID) throws Exception
	{
		long ret=-1;
		long loanStatus=-1;
		/*首先检测申请书状态*/
		LoanApplyDao applyDao=new LoanApplyDao();
		LoanApplyInfo applyInfo=applyDao.findByID(loanID);
		loanStatus=applyInfo.getStatusID();

		/*如果是已经审核*/
		if (loanStatus==LOANConstant.LoanStatus.CHECK)
		{
			/*将申请状态改成已提交*/
			
			try
			{
				LoanApplyDao dao=new LoanApplyDao();
				dao.updateLoanStatus(loanID,-1,LOANConstant.LoanStatus.SUBMIT);
				dao.setCheckUserBack(loanID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
			
			
			/*删除合同表中关于该条申请的信息*/
			try
			{
				ContractDao contract=new ContractDao();
				contract.deleteByLoanID(loanID);
			}
			catch( Exception ce)
			{
				ce.printStackTrace() ;
				throw ce;
			}
		}
		return 1;
	}

	/**
	 * 取得合同当前信息
	 * 余额，放款金额，还款金额
	 * @param lContractID 合同ID 
	 * @return
	 * @throws Exception
	 */
	public ContractAmountInfo getLateAmount(long lContractID) throws Exception
	{
		ContractAmountInfo info=null;
		ContractDao dao=new ContractDao();
		
		try
		{
			info=dao.getLateAmount( lContractID );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;
	}

	/**
	 * 取得合同当前利率
	 * @param lContractID 合同ID 
	 * @return
	 * @throws Exception
	 */ 
	public RateInfo getLatelyRate(long lContractID) throws Exception
	{
		RateInfo info=null;
		ContractDao dao=new ContractDao();
		
		try
		{
			info=dao.getLatelyRate(-1,lContractID,null );
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;	
	}
	
	/**
	 * 取得贷款当前利率
	 * @param loanID 贷款ID 
	 * @return
	 * @throws Exception
	 */ 
	public RateInfo getLoanLatelyRate(long loanID) throws Exception
	{
		RateInfo info=null;
		ContractDao dao=new ContractDao();
		
		try
		{
			info=dao.getLatelyRate(loanID,-1,null );
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;	
	}

	/**
	* 得到执行利率，参数lLoanID和lContractID必传入一个，不传入的话请设置为-1。
	* Create Date: 2003-10-15
	* @param lLoanID 贷款ID
	* @param lContractID 合同ID
	* @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	* @return double 执行利率
	* @exception Exception
	*/
	public RateInfo getLatelyRate(long lLoanID, long lContractID, Timestamp tsDate) throws Exception
	{
		RateInfo info=null;
		ContractDao dao=new ContractDao();
		
		try
		{
			info=dao.getLatelyRate( lLoanID, lContractID, tsDate );
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;	
	}
 	
}
