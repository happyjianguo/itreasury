package com.iss.itreasury.loan.creditext.bizlogic;


import java.util.*;

import com.iss.itreasury.loan.assureloan.dataentity.AssureInfo;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtBalanceDAO;
import com.iss.itreasury.loan.creditext.dao.BankCreditExtDAO;
import com.iss.itreasury.loan.creditext.dataentity.*;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;

public class BankCreditExtBiz {
	/**
	 * ����������ѯ��������
	 * @param queryInfo
	 * @return
	 * @throws IException
	 */
	public PageLoader getBankCreditExt(BankCreditExtQueryInfo  queryInfo) throws IException
	{
		PageLoader loader = null;
		BankCreditExtDAO dao = new BankCreditExtDAO();
		try
		{
			loader = dao.getBankCreditExt(queryInfo);			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	/**
	 * ������������ID��ѯ����������Ϣ
	 * @param id  ��������ID
	 * @return
	 * @throws IException
	 */
	public BankCreditExtInfo  getBankCreditExtInfo(long id) throws IException
	{
		BankCreditExtInfo info = null;
		try
		{	
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			info = dao.getBankCreditExtInfo(id);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	/**
	 * ������������ID��ѯ��϶����Ϣ
	 * @param id  ��������ID
	 * @return
	 * @throws IException
	 */
	public BankCreditExtMixInfo  getBankCreditExtMixInfo(long id) throws IException
	{
		BankCreditExtMixInfo mixInfo = null;
		try
		{	
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			mixInfo = dao.getBankCreditExtMixInfo(id);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return mixInfo;
	}
	
	/**
	 * ������������ID���������������ѯ�����ۺ�������Ϣ
	 * @param id  ������������ID
	 * @param variety  ������������
	 * @return
	 * @throws IException
	 */
	public BankCreditExtListInfo  getBankCreditExtListInfo(long id,long variety) throws IException
	{
		BankCreditExtListInfo listInfo = null;
		try
		{	
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			listInfo = dao.getBankCreditExtListInfo(id, variety);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return listInfo;
	}
	
	/**
	 * �޸��������ż���϶����Ϣ
	 * @param info   ��������
	 * @param mixInfo ���л�����Ŷ��
	 * @return
	 * @throws IException
	 */
	public String modify(BankCreditExtInfo info, BankCreditExtMixInfo mixInfo) throws IException  
	{
		String message = "";
		try
		{
			BankCreditExtDAO dao = new BankCreditExtDAO();	
			double usedAmount = dao.getAllotedAmount( mixInfo.getBankCreditExtId() , 0);
			if( mixInfo.getConvertRMB() < usedAmount)
			{
				message = "�޸ĺ�Ļ�϶�Ȳ���С���ѷ���Ļ�϶��(�ۺ������ "+ usedAmount +" Ԫ)����������д��";	
			}
			else
			{
				dao.modify(info,mixInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return message;
	}
	
	/**
	 * �޸������ۺ�������Ϣ
	 * @param listInfo   �����ۺ�������Ϣ
	 * @return
	 * @throws IException
	 */
	public String modify(BankCreditExtListInfo listInfo) throws IException
	{
		String message = "";
		try
		{	
			BankCreditExtDAO dao = new BankCreditExtDAO();	
			double usedAmount = dao.getAllotedAmount( listInfo.getBankCreditExtId() , listInfo.getVariety() );
			if( listInfo.getConvertRMB() < usedAmount)
			{
				message = "�޸ĺ�����Ž���С�ڸ�����Ʒ���ѷ�������Ŷ��(�ۺ������ "+ usedAmount +" Ԫ)����������д��";					
			}
			else
			{
				dao.modify(listInfo);			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return message;
	}
	
	/**
	 * ����IDɾ����������
	 * @param id
	 * @return
	 * @throws IException
	 */
	public boolean delete(long id) throws IException	
	{
		boolean deleteOK = false;
		try
		{	
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			//�жϴ�����������û�б����䣬���ѷ��䣬����ɾ��
			if(!dao.isAlloted(id,-1))
			{
				deleteOK = dao.delete(id);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return deleteOK;
	}
	
	/**
	 * ɾ�������ۺ�������Ϣ
	 * @param id   ��������ID
	 * @param sVarieties  �ۺ���������
	 * @return
	 * @throws IException
	 */
	public String delete(long id, String[] sVarieties) throws IException        
	{
		String massage = null;
		String cannotDelete = null;
		List canDelete = null;
		Map map = new HashMap();
		try
		{
			map = this.isAlloted(id,sVarieties);
			canDelete = (List) map.get("canDelete");
			cannotDelete = (String) map.get("cannotDelete");
			if((canDelete != null) && (canDelete.size() != 0))
			{	
				BankCreditExtDAO dao = new BankCreditExtDAO();		
				dao.delete(id,canDelete);
				if((cannotDelete != null) && (cannotDelete.length() != 0))
				{
					massage = "�Բ�����Ҫɾ����" + cannotDelete + "�ѷ��䣬����ɾ����";
				}
				else
				{
					massage = "ɾ���ɹ���";
				}
			}
			else
			{
				massage = "�Բ�����Ҫɾ���������������඼�ѷ��䣬����ɾ����";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return massage;
	}
	
	/**
	 * �ж�����������û�б�����
	 * @param id
	 * @param sVarieties
	 * @return  
	 * @throws IException
	 */
	public Map isAlloted(long id, String[] sVarieties) throws IException
	{
		Map map = new HashMap();
		List canDelete = new ArrayList();
		StringBuffer cannotDelete = new StringBuffer();
		try
		{
			BankCreditExtDAO dao = new BankCreditExtDAO();
			for(int i=0;i<sVarieties.length;i++)
			{
				if(!dao.isAlloted(id,Long.valueOf(sVarieties[i]).longValue()))
				{
					canDelete.add(sVarieties[i]);
				}
				else
				{
					cannotDelete.append(LOANConstant.BankCreditVariety.getName(Long.valueOf(sVarieties[i]).longValue()) + "��");
				}
			}
			
			///
			if(canDelete != null)
			{
				Iterator it = canDelete.iterator();
				while(it.hasNext())
				{
					System.out.println("canDelete =================="+ it.next());
				}
			}
			System.out.println("cannotDelete==============="+ cannotDelete.toString());
			///
			String temp = null;
			if((cannotDelete != null)&&(cannotDelete.length() != 0))
			{
				temp = cannotDelete.substring(0,cannotDelete.length()-1);
			}
			System.out.println("temp==============="+ temp);
			map.put("canDelete",canDelete);    //����ɾ������������
			map.put("cannotDelete",temp);      //���ܱ�ɾ������������
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return map;
	}
	
	/**
	 * �����������ż���϶��������Ϣ
	 * @param info
	 * @param mixInfo
	 * @return
	 * @throws IException
	 */
	public long insert(BankCreditExtInfo info, BankCreditExtMixInfo mixInfo) throws IException
	{
		long id = -1;
		try
		{
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			id = dao.insert(info,mixInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return id;
	}
	
	/**
	 * ���������ۺ�������Ϣ
	 * @param listInfo
	 * @return
	 * @throws IException
	 */
	public boolean insert(BankCreditExtListInfo  listInfo) throws IException
	{
		boolean insertOK = false;
		try
		{
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			insertOK = dao.insert(listInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return insertOK;
	}
	
	/**
	 * �鿴ĳ�������Ű��������������ۺ�������Ϣ
	 * @param id
	 * @return
	 * @throws IException
	 */
	public Collection allList(long id) throws IException
	{
		Collection listResults = null;
		try
		{
			BankCreditExtDAO dao = new BankCreditExtDAO();		
			listResults = dao.allList(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return listResults;
	}
	

}
