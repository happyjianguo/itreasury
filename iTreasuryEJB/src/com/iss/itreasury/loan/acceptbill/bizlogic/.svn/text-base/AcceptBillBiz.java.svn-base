package com.iss.itreasury.loan.acceptbill.bizlogic;

import com.iss.itreasury.loan.acceptbill.dao.AcceptBillDao;
import com.iss.itreasury.loan.acceptbill.dataentity.AcceptBillInfo;
import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader; 

public class AcceptBillBiz 
{
		
	/**
	 * 分页查询，根据条件查询承兑汇票，按info里的信息查询表loan_AcceptBill
	 * @param info
	 * @return PageLoader对象
	 * @throws Exception
	 */
	public PageLoader findByCondition(AcceptBillInfo info) throws Exception
	{
		PageLoader loader = null;
		AcceptBillDao dao = new AcceptBillDao();
		try
		{
			loader = dao.findByCondition(info);			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
		
	/**
	 * 新增承兑汇票，将info里的信息insert到表loan_AcceptBill里
	 * @param info
	 * @return 返回成功与否，成功 1  失败  -1
	 * @throws IException
	 */	
	public long add(AcceptBillInfo info) throws IException
	{
		long lret = -1;
		try
		{
			AcceptBillDao dao = new AcceptBillDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币
			double balance;
			double assureBalance;
			System.out.println("=================rmb===================" + info.getConvertRMB());
			balance = getBiz.getBalance(info.getConferContractNo(),info.getBillClient(),LOANConstant.BankCreditVariety.ACCEPTBILL);			
			System.out.println("=================balance===================" + balance);
			assureBalance = getBiz.getAssureBalance(info.getConferContractNo(),info.getBillClient(),LOANConstant.BankCreditVariety.ACCEPTBILL);
			System.out.println("=================assureBalance===================" + assureBalance);
//			if ( balance >= info.getConvertRMB() && assureBalance >= info.getConvertRMB())
//			{
//				info.setCode(dao.getNextCode());
//				lret = dao.add(info);
//			}	
//			else
//			{
//				lret = 0;				
//			}
			if ( balance < info.getConvertRMB() )    //授信余额不够
			{
				lret = -2;
			}
			else if ( assureBalance < info.getConvertRMB()) //担保余额不够
			{
				lret = -1;
			}
			else
			{
				info.setCode(dao.getNextCode());
				lret = dao.add(info);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}	
		return lret;
	}
	
	/**
	 * 根据内部流水号查询承兑汇票
	 * @param code
	 * @return
	 * @throws IException
	 */
	public AcceptBillInfo findByCode(AcceptBillInfo info) throws IException
	{
		AcceptBillInfo acceptBillInfo = null;
		try
		{
			AcceptBillDao dao = new AcceptBillDao();
			acceptBillInfo = dao.findByCode(info);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}	
		return acceptBillInfo;
	}
	
	/**
	 * 修改承兑汇票，将info里的信息update到表loan_AcceptBill里
	 * @param info
	 * @return  返回成功与否，成功 1  失败  -1
	 * @throws IException
	 */
	public long modify(AcceptBillInfo info) throws IException
	{
		long lret = -1;
		try
		{
			AcceptBillDao dao = new AcceptBillDao();
			double balance;
			double assureBalance;
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//提供金额控制信息,返回值均为折合人民币			
			balance = getBiz.getBalance(info.getConferContractNo(),info.getBillClient(),LOANConstant.BankCreditVariety.ACCEPTBILL);			
			assureBalance = getBiz.getAssureBalance(info.getConferContractNo(),info.getBillClient(),LOANConstant.BankCreditVariety.ACCEPTBILL);
			balance += info.getConvertRMB0();	
			assureBalance += info.getConvertRMB0();
//			if ( balance >= info.getConvertRMB() && assureBalance >= info.getConvertRMB())
//			{
//				info.setCode(dao.getNextCode());
//				lret = dao.modify(info);
//			}	
//			else
//			{
//				lret = 0;				
//			}
			if ( balance < info.getConvertRMB() )    //授信余额不够
			{
				lret = -2;
			}
			else if ( assureBalance < info.getConvertRMB()) //担保余额不够
			{
				lret = -1;
			}
			else
			{
				info.setCode(dao.getNextCode());
				lret = dao.modify(info);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}	
		return lret;
	}
	
	/**
	 * 判断该承兑汇票有没有被别的地方引用
	 * @param info
	 * @return   
	 */
	public boolean isUsed(AcceptBillInfo info) 
	{
		return false;
	}
	
	/**
	 * 删除承兑汇票
	 * @param info
	 * @return  返回成功与否，成功 1  失败  -1
	 * @throws IException
	 */
	public long delete(AcceptBillInfo info) throws IException
	{
		long lret = -1;
		AcceptBillDao dao = null;
		try
		{
			if(!this.isUsed(info))
			{
				dao = new AcceptBillDao();
				lret = dao.delete(info);
			}
			else
			{
				lret = 0;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}	
		return lret;
	}
}
