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
	 * ��ҳ��ѯ������������ѯ�жһ�Ʊ����info�����Ϣ��ѯ��loan_AcceptBill
	 * @param info
	 * @return PageLoader����
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
	 * �����жһ�Ʊ����info�����Ϣinsert����loan_AcceptBill��
	 * @param info
	 * @return ���سɹ���񣬳ɹ� 1  ʧ��  -1
	 * @throws IException
	 */	
	public long add(AcceptBillInfo info) throws IException
	{
		long lret = -1;
		try
		{
			AcceptBillDao dao = new AcceptBillDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//�ṩ��������Ϣ,����ֵ��Ϊ�ۺ������
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
			if ( balance < info.getConvertRMB() )    //��������
			{
				lret = -2;
			}
			else if ( assureBalance < info.getConvertRMB()) //��������
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
	 * �����ڲ���ˮ�Ų�ѯ�жһ�Ʊ
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
	 * �޸ĳжһ�Ʊ����info�����Ϣupdate����loan_AcceptBill��
	 * @param info
	 * @return  ���سɹ���񣬳ɹ� 1  ʧ��  -1
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
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//�ṩ��������Ϣ,����ֵ��Ϊ�ۺ������			
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
			if ( balance < info.getConvertRMB() )    //��������
			{
				lret = -2;
			}
			else if ( assureBalance < info.getConvertRMB()) //��������
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
	 * �жϸóжһ�Ʊ��û�б���ĵط�����
	 * @param info
	 * @return   
	 */
	public boolean isUsed(AcceptBillInfo info) 
	{
		return false;
	}
	
	/**
	 * ɾ���жһ�Ʊ
	 * @param info
	 * @return  ���سɹ���񣬳ɹ� 1  ʧ��  -1
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
