/*
 * Created on 2006-10-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.creditprove.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.loan.creditext.bizlogic.BankCreditExtBalanceBiz;
import com.iss.itreasury.loan.creditprove.dao.CreditProveDao;
import com.iss.itreasury.loan.creditprove.dataentity.CreditProveInfo;
import com.iss.itreasury.loan.query.dao.QueryDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.system.bulletin.dao.BulletinDao;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreditProveBiz {
	

	
	//�����Ŵ�֤������info�����Ϣinsert����loan_CreditProve��
	public long add(CreditProveInfo info) throws Exception
	{
		long lret = -1;//���Ž��������������
		try
		{
			CreditProveDao dao = new CreditProveDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//�ṩ��������Ϣ,����ֵ��Ϊ�ۺ������
			double balance;
			balance = getBiz.getBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);
			double assureBalance;
			assureBalance = getBiz.getAssureBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);
			if ( balance >= info.getConvertRMB()  )
			{
				lret = -2;//���Ž������������
				if (assureBalance >= info.getConvertRMB())
				{
					lret = dao.add(info);
				}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	�޸��Ŵ�֤������info�����Ϣupdate����loan_CreditProve��
	public long modify(CreditProveInfo info) throws Exception
	{
		long lret = -1;//���Ž��������������
		try
		{
			CreditProveDao dao = new CreditProveDao();
			BankCreditExtBalanceBiz getBiz = new BankCreditExtBalanceBiz();//�ṩ��������Ϣ,����ֵ��Ϊ�ۺ������
			double balance;
			balance = getBiz.getBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);			
			balance += info.getOriginalCoverRMB(); 
			double assureBalance;
			assureBalance = getBiz.getAssureBalance(info.getConferContractNo(),info.getApplyClient(),LOANConstant.BankCreditVariety.PROVEOFCREDIT);			
			assureBalance += info.getOriginalCoverRMB();
			if ( balance >= info.getConvertRMB()  )
			{
				lret = -2;//���Ž������������
				if (assureBalance >= info.getConvertRMB())
				{
					lret = dao.modify(info);
				}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	ɾ���Ŵ�֤������info�����Ϣupdate����loan_CreditProve��
	public long delete(CreditProveInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			CreditProveDao dao = new CreditProveDao();
			lret = dao.delete(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	��ѯ�Ŵ�֤������info�����Ϣ��ѯ��loan_CreditProve
	//	������CreditProveInfo��ɵļ���
	public Collection findByCondition(CreditProveInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			CreditProveDao dao = new CreditProveDao();
			vret = dao.findByCreditProve(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	// �����Ŵ�֤���ڲ���ˮ��
	public String getNextCode() throws Exception
	{
		String newCode ;
		try
		{
			CreditProveDao dao = new CreditProveDao();
			newCode = dao.getNextCode();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return newCode;
	}
	
	//��ҳ����
	public PageLoader findWithPage (CreditProveInfo info) throws Exception
	{
		PageLoader loader = null;
		
		try
		{
			CreditProveDao dao = new CreditProveDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
}
