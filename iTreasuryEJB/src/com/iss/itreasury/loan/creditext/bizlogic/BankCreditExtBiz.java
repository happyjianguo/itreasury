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
	 * 根据条件查询银行授信
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
	 * 根据银行授信ID查询银行授信信息
	 * @param id  银行授信ID
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
	 * 根据银行授信ID查询混合额度信息
	 * @param id  银行授信ID
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
	 * 根据银行授信ID和银行授信种类查询银行综合授信信息
	 * @param id  根据银行授信ID
	 * @param variety  银行授信种类
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
	 * 修改银行授信及混合额度信息
	 * @param info   银行授信
	 * @param mixInfo 银行混合授信额度
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
				message = "修改后的混合额度不能小于已分配的混合额度(折合人民币 "+ usedAmount +" 元)，请重新填写。";	
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
	 * 修改银行综合授信信息
	 * @param listInfo   银行综合授信信息
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
				message = "修改后的授信金额不能小于该授信品种已分配的授信额度(折合人民币 "+ usedAmount +" 元)，请重新填写。";					
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
	 * 根据ID删除银行授信
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
			//判断此银行授信有没有被分配，如已分配，则不能删除
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
	 * 删除银行综合授信信息
	 * @param id   银行授信ID
	 * @param sVarieties  综合授信种类
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
					massage = "对不起，你要删除的" + cannotDelete + "已分配，不能删除。";
				}
				else
				{
					massage = "删除成功。";
				}
			}
			else
			{
				massage = "对不起，你要删除的所有授信种类都已分配，不能删除。";
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
	 * 判断银行授信有没有被分配
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
					cannotDelete.append(LOANConstant.BankCreditVariety.getName(Long.valueOf(sVarieties[i]).longValue()) + "、");
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
			map.put("canDelete",canDelete);    //可以删除的授信种类
			map.put("cannotDelete",temp);      //不能被删除的授信种类
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return map;
	}
	
	/**
	 * 新增银行授信及混合额度授信信息
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
	 * 新增银行综合授信信息
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
	 * 查看某银行授信包含的所有银行综合授信信息
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
