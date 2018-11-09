package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.settlement.setting.dao.Sett_AccountRelationsSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountRelationsSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class AccountRelationsBiz {

	private Connection conn = null;
	
	public AccountRelationsBiz()
	{
		try
		{
			//conn = Database.getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 保存新旧账户对应关系信息
	 * @param AccountRelationsSettingInfo 对应的账户对应关系信息
	 * @return id 该项设置的ID 
	 * @throws IException 
	 */	
	public long saveSetting(AccountRelationsSettingInfo info) throws IException
	{
		long lID = -1;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
					
			Collection cInfo = null;
			AccountRelationsSettingInfo tempInfo = new AccountRelationsSettingInfo();
			tempInfo.setId(info.getId());
			tempInfo.setAccountID(info.getAccountID());
			tempInfo.setOfficeID(info.getOfficeID());
			tempInfo.setCurrencyID(info.getCurrencyID());
			cInfo = dao.findByCondition(tempInfo);
			if(cInfo!=null && cInfo.size()>0)
			{
				throw new IException("此新账户已经对应原有账户");				
			}
			
			tempInfo.setAccountID(-1);
			tempInfo.setOriginalAccountNo(info.getOriginalAccountNo());
			cInfo = dao.findByCondition(tempInfo);
			if(cInfo!=null && cInfo.size()>0)
			{
				throw new IException("此原有账户已经对应新账户");				
			}
			
			if( info.getId() < 0 )
			{
				lID = dao.add(info);
			}
			else
			{
				dao.update(info);
				lID = info.getId();
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

	/**
	 * 根据设置的id查询
	 * <br>注：无对应设置时返回null.
	 * @param id 设置记录的id
	 * @return AccountRelationsSettingInfo 对应的账户关系信息对象
	 * @throws IException 
	 */	
	public AccountRelationsSettingInfo findSettingByID(long lID) throws IException
	{
		AccountRelationsSettingInfo info = null;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
			info = dao.findByID(lID);
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
		return info != null ? info : null;
	}

	/**
	 * 根据账户id查询
	 * <br>注：无对应设置时返回null，用于打印时显示旧账户
	 * @param id 设置记录的id
	 * @return AccountRelationsSettingInfo 对应的账户关系信息对象
	 * @throws IException 
	 */	
	public AccountRelationsSettingInfo findSettingByAccountID(long lAccountID) throws IException
	{
		AccountRelationsSettingInfo info = null;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
			info = dao.findByAccountID(lAccountID);
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
		return info != null ? info : null;
	}	

	/**
	 * 根据账户id查询
	 * <br>注：无对应设置时返回空，用于打印时显示旧账户
	 * @param id 设置记录的id
	 * @return String 对应的账户号
	 * @throws IException 
	 */	
	public static String getDisplayAcctNo(long lAccountID) throws IException
	{
		String strAccountNo = null;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
			strAccountNo = dao.getDisplayAcctNoByAcctID(lAccountID);
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
		return strAccountNo;
	}
	
	/**
	 * 查询所有设置
	 * <br>注：无对应设置时返回null.
	 * @param 
	 * @return Collection 关系信息对象集合
	 * @throws IException 
	 */	
	public Collection findAllSetting() throws IException
	{
		AccountRelationsSettingInfo info = new AccountRelationsSettingInfo();
		Collection c_Info = null;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
			c_Info = dao.findByCondition(info);
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
		return c_Info != null ? c_Info : null;
	}			

	/**
	 * 根据条件查询设置
	 * <br>注：无对应设置时返回null.
	 * @param 
	 * @return Collection 关系信息对象集合
	 * @throws IException 
	 */	
	public Collection findSettingByCondition(AccountRelationsSettingInfo info) throws IException
	{
		Collection c_Info = null;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
			c_Info = dao.findByCondition(info);
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
		return c_Info != null ? c_Info : null;
	}		
	
	/**
	 * 删除该项设置
	 * @param id 设置记录的id
	 * @return long  是否成功
	 * @throws IException 
	 */	
	public long deleteSetting(long lID) throws IException
	{
		long lResult = -1;
		try
		{
			Sett_AccountRelationsSettingDAO dao = new Sett_AccountRelationsSettingDAO();
			lResult = dao.delete(lID);
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
