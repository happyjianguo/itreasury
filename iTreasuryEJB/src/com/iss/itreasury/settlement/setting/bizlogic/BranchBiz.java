package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.account.dao.Sett_AccountBankDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountBankInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;
import com.iss.itreasury.util.IException;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BranchBiz
{
	private Connection conn = null;
	
	public BranchBiz()
	{
		try
		{
			conn = Database.getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Vector findAllBranch(QueryBranchInfo qbi) throws IException
	{
		Vector v = new Vector();
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			v = sett_BranchDao.queryBranch(qbi);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return v != null ? v : null;
	}
	
	public long saveBranch(BranchInfo info) throws IException
	{
		long lID = -1;
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			if( info.getID() < 0 )
			{
				lID = sett_BranchDao.add(info);
			}
			else
			{
				sett_BranchDao.update(info);
				lID = info.getID();
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lID;
	}
	
	public BranchInfo findBranchByID(long lID) throws IException
	{
		BranchInfo bi = null;
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			bi = sett_BranchDao.findByID(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return bi != null ? bi : null;
	}	
	
	/*
	 * �ж��Ƿ����ظ�
	 * added by ypxu
	 * 2007-05-10
	 */
	public boolean isSameCode(BranchInfo info) throws IException
	{ 
		boolean isSame = false;
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			long lReturn = sett_BranchDao.isSameCode(info);
			if(lReturn > 0)
			{
				isSame = true;
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return isSame;
	}
	
	public long getMaxBranchCode(long lOfficeID,long lCurrencyID) throws IException
	{ 
		long lMaxCode = -1;
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			lMaxCode = sett_BranchDao.getMaxBranchCode(lOfficeID,lCurrencyID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lMaxCode;
	}
	
	public long deleteBranch(long lID) throws IException
	{
		long lResult = -1;
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			lResult = sett_BranchDao.deleteBranch(lID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lResult;		
	}

	/**
	 * ���ݿ��������õ�id��ѯ��Ӧ�Ľ����ڲ��˻�
	 * <br>ע���޶�Ӧ�����ڲ��˻�ʱ����null.
	 * @param id ���������ü�¼��id
	 * @return AccountInfo ��Ӧ�Ľ����ڲ��˻���Ϣ����
	 * @throws IException 
	 */	
	public AccountInfo[] findSettAccountByBranchID(long id)throws IException
	{
		AccountBankInfo[] accountBankInfos = null;
		AccountInfo[] accountInfos = null;
		try
		{
			//���ݿ�����id����ѯ��������ù�����Ϣ
			Sett_AccountBankDAO accountBankDAO = new Sett_AccountBankDAO(conn);
			accountBankInfos = accountBankDAO.findByBankId(id);
			
			//���ݹ�����Ϣ����ý����ڲ��˻���Ϣ
			if(accountBankInfos != null && accountBankInfos.length > 0)
			{
				Sett_AccountDAO accountDAO = new Sett_AccountDAO(conn);
				accountInfos = new AccountInfo[accountBankInfos.length];
				for(int i = 0; i < accountBankInfos.length; i++)
				{
					accountInfos[i] = accountDAO.findByID(accountBankInfos[i].getAccountID());
				}
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch ( SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		return accountInfos;
	}
	
	/**
	 * ���ݽ����ڲ��˻���id��ѯ��Ӧ�Ŀ�����������Ϣ.
	 * <br>ע���޶�Ӧ����������ʱ����null.
	 * @param id �����ڲ��˻���¼��id
	 * @return BranchInfo ��Ӧ�Ŀ�����������Ϣ����
	 * @throws IException 
	 */
	public BranchInfo[] findBranchBySettAccountID(long id)throws IException
	{
		Collection coll = null;
		AccountBankInfo[] accountBankInfos = null;
		BranchInfo[] branchInfos = null;
		try
		{
			//���ݽ����ڲ��˻���id����ѯ��������ù�����Ϣ
			Sett_AccountBankDAO accountBankDAO = new Sett_AccountBankDAO(conn);
			coll = accountBankDAO.findByAccountID(id);
			
			//���ݹ�����Ϣ����ÿ�����������Ϣ
			if(coll != null && coll.size() > 0)
			{
				accountBankInfos = (AccountBankInfo[])coll.toArray(new AccountBankInfo[0]);
				Sett_BranchDAO branchDAO = new Sett_BranchDAO(conn);
				branchInfos = new BranchInfo[accountBankInfos.length];
				for(int i = 0; i < accountBankInfos.length; i++)
				{
					branchInfos[i] = branchDAO.findByID(accountBankInfos[i].getBankID());
				}
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch ( SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		return branchInfos;
	}
	
	/**
	 * ���������˻��Ų�ѯ��Ӧ�Ŀ�����������Ϣ��
	 * <br>ע���޶�Ӧ����������ʱ����null.
	 * @param accountCode �����˻���
	 * @return BranchInfo ��Ӧ�Ŀ�����������Ϣ����
	 * @throws IException
	 */
	public BranchInfo findBranchByBankAccountCode(String accountCode)throws IException
	{
		Collection coll = null;
		BranchInfo[] branchInfos = null;
		BranchInfo conditionInfo = null;
		BranchInfo resultInfo = null;
		
		try
		{
			Sett_BranchDAO branchDAO = new Sett_BranchDAO(conn);
			conditionInfo = new BranchInfo();
			conditionInfo.setBankAccountCode(accountCode);
			conditionInfo.setOfficeID(-1);
			coll = branchDAO.findByConditions(conditionInfo,-1,false);
			if(coll == null || coll.size() <= 0)
			{
				throw new IException("���������˻���["+accountCode+"]�Ҳ�����Ӧ�Ŀ�����������Ϣ");
			}
//			else if(coll.size() > 1)
//			{
//				throw new IException("���������˻���["+accountCode+"]�ҵ���һ�����ϵĿ�����������Ϣ");
//			}
			
			branchInfos = (BranchInfo[])coll.toArray(new BranchInfo[0]);
			resultInfo = branchInfos[0];
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch ( SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		return resultInfo;
	}
	/**
	 * 
	* Title:        		iTreasury
	* Description:         �����������Ͳ�ѯ
	* Copyright:                    Copyright (c) 2003 Company: iSoftStone
	* @author             ytcui
	* @version
	*  Date of Creation    2004-11-22
	 */
	public BranchInfo[] findAllByBankType(int nBankType)	
	throws Exception
	{
		BranchInfo[] infos = null;
		try
		{
			Sett_BranchDAO dao = new Sett_BranchDAO(conn);

			infos = dao.findAllByBankType(nBankType);
		}
		catch (Exception ie)
		{
			ie.printStackTrace();
			throw new IException("Gen_E001");
		}
		return infos;
	}
	
	public static void main(String[] args)
	
	{
		BranchBiz biz = new BranchBiz();
		
		AccountInfo[] accountInfos = null;
		BranchInfo[] branchInfos = null;
		BranchInfo branchInfo = null;
		
		try
		{
			//���Ը��ݿ��������õ�id��ѯ��Ӧ�Ľ����ڲ��˻�
			accountInfos = biz.findSettAccountByBranchID(1);
			if(accountInfos != null)
			{
				for(int i = 0; i < accountInfos.length; i++)
				{
					System.out.println(accountInfos[i]);
				}
			}
			
			//���Ը��ݽ����ڲ��˻���id��ѯ��Ӧ�Ŀ�����������Ϣ
//			branchInfos = biz.findBranchBySettAccountID(2);
//			if(branchInfos != null)
//			{
//				for(int i = 0; i < branchInfos.length; i++)
//				{
//					System.out.println("id is : "+branchInfos[i].getID());
//				}
//			}
			
			//���������˻��Ų�ѯ��Ӧ�Ŀ�����������Ϣ��
//			branchInfo = biz.findBranchByBankAccountCode("0200004519000100297");
//			if(branchInfo != null)
//			{
//				System.out.println("id is : "+branchInfo.getID());
//			}
//			else
//			{
//				System.out.println("null");
//			}
			
			System.out.println("test success!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("test failed!");
		}
	}
	
	public long getMaxCode(long lOfficeID,long lCurrencyID) throws IException
	{ 
		long lMaxCode = -1;
		try
		{
			Sett_BranchDAO sett_BranchDao = new Sett_BranchDAO(conn);
			lMaxCode = sett_BranchDao.getMaxCode(lOfficeID,lCurrencyID);
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lMaxCode;
	}
		
}

