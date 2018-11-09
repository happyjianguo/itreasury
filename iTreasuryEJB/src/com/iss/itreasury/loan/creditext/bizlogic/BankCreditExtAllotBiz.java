package com.iss.itreasury.loan.creditext.bizlogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.sql.Date;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtAllotDAO;
import com.iss.itreasury.loan.creditext.dataentity.BankCreditExtAllotInfo;
import com.iss.itreasury.loan.creditprove.dao.CreditProveDao;
import com.iss.itreasury.util.IException;

/**
* �������ŷ��������ά��ҵ���߼�����
* @author mayongming
* @version 1.0
*/
public class BankCreditExtAllotBiz 
{
	//ͨ��������Ⱥ����ź�ͬ�Ų�ѯ���ź�ͬ��id��Ϊ��һ�����������ź�ͬid
	public long getContractId (String contractno, String year) throws Exception
	{
		long lret = -1;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			lret = dao.getContractId(contractno ,year);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
		
	}
	// ͨ��������Ⱥ����ź�ͬ�Ų�ѯ�������Ѿ���������������ĵ����
	//v001.jsp �Ĳ�ѯ
	public Collection getBankCreditAllot(long id) throws Exception 
	{
		Vector vret = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			vret = dao.getBankCreditAllot(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	// ͨ�����ź�ͬ�š�����Ʒ�ֺͽ������ı�Ų�ѯ���ŷ�����Ϣ
	//v002.jsp�����������Ĳ�ѯ
	public BankCreditExtAllotInfo getBankCreditAllotInfo(long id, long variety, long officId, String officeName) throws Exception
	{
		BankCreditExtAllotInfo info = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			info = dao.getBankCreditAllotInfo( id,variety,officId,officeName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	// ɾ�����ŷ����¼��ɾ��֮ǰҪ�жϸ�Ʒ�������Ƿ��Ѿ����ֽ⵽���������µĳ�Ա��λ��
	//v003.jsp ���ɾ�����ô˺���
	public String delete(long id, long viriety, long officeId, long lastModifier, String lastModifyDate) throws Exception 
	{
		String lret = "isSplit"; 
		boolean isSplit = true;
		boolean isDele = false;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			isSplit = dao.isSplited(id,viriety,officeId);
			if(!isSplit)//
			{
				lret = "failed";
				isDele = dao.delete(id,viriety,officeId,lastModifier,lastModifyDate);
				if(isDele)
				{
					lret = "success";
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
	
	//	 �����������ŷ����������¼���ж��Ƿ��Ѿ������Լ��Ƿ񳬹����
	// �����������
	public String insert(BankCreditExtAllotInfo info) throws Exception
	{
		String lret = "exist";
		boolean exist = true;//�ж��Ƿ����
		boolean isOverBalance = true;//�Ƿ񳬹����
		boolean isInsert = false;//�Ƿ����ɹ�
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			//�жϸ�Ʒ���Ƿ��Ѿ�������˸ý�������
			exist = dao.exist(info.getId(),info.getVariety(),info.getOfficeid());
			if(!exist)
			{
				lret = "inOver";
				//��û�з�����ý�������,���Ƿ񳬹����
				isOverBalance = isOverBalance(info.getAmount(),info.getId(),info.getVariety(),-1);
				if(!isOverBalance)
				{
					//���û�г������ͽ��������Ϣ
					lret = "InsertFailed";
					isInsert = dao.insert(info);
					if(isInsert)
					{
						lret = "InsertSuccess";
					}
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
	
	//	ͨ�����ź�ͬ��id�õ�������Ϣ
	//  v002��jsp����������룬ͨ�����ź�ͬ��id��ѯ�������ź�ͬ�ŵ�����
	public BankCreditExtAllotInfo getBankCreditInfo(long id) throws Exception 
	{
		BankCreditExtAllotInfo info = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			info = dao.getBankCreditInfo(id);
			info.setBalances ( dao.getAllBalance(id) );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	//��ͬ������Ʒ�֣��õ��������Ž��ı���
	public HashMap getAllCurrencytype (long id ) throws Exception 
	{
		HashMap hm = null;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			hm = dao.getAllCurrencytype(id);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return hm;
	}
	
	// v003.jsp ����������ô˺���
	//	�޸��������ŷ����������¼����Ҫ�ж��Ѿ����㣺
	//  ���ŷ�����Ϣ�޸�ǰ���жϷ������Ƿ񳬹��˿�ʹ�����
	//  ���ŷ�����Ϣ�޸�ǰ���жϷ�����һ��Ҫ���ڸý������ĸ�Ʒ�������Ѿ��ֽ�Ľ���ܺͣ�����getHasSplit����������
	public String modify(BankCreditExtAllotInfo info) throws Exception
	{
		String lret = "notEnough";//Ĭ�Ϸ�����С���Ѿ��ֽ�
		double hasSplitAmount = 0;
		boolean isOverBalance = true;
		boolean isModify = false;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();	
			hasSplitAmount = dao.getHasSplit(info.getId(),info.getVariety(),info.getOfficeid());
			if (info.getAmount() - hasSplitAmount >0 )
			{
				lret = "over";//Ĭ��������������
				isOverBalance = isOverBalance(info.getAmount(),info.getId(),info.getVariety(),info.getOfficeid());
				if(!isOverBalance)
				{
					lret = "ModifyFailed";//�޸�ʧ��
					isModify = dao.modify(info);
					if (isModify)
					{
						lret = "ModifySuccess";//�޸ĳɹ�
					}
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
	
	//	�жϷ���Ķ���Ƿ񳬹������
	// ��������������ź�ͬid ������Ʒ��
	// ����true ����������false û�г������
	private boolean isOverBalance(double amount, long id, long variety, long officeId) throws Exception 
	{
		boolean lret = true ;
		double balance = 0;
		try
		{
			BankCreditExtAllotDAO dao = new BankCreditExtAllotDAO();
			balance = dao.getBalance( id,variety,officeId);
			if(balance - amount >= 0)
			{
				lret = false;
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
}
