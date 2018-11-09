package com.iss.itreasury.loan.letterofindemnity.bizlogic;

import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.letterofindemnity.dao.VarietyDAO;
import com.iss.itreasury.loan.letterofindemnity.dataentity.VarietyInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * 
 * @author fxzhang
 *
 */
public class VarietyBiz {

	//获得所有的保函种类
	public Collection getAllVariety() throws Exception 
	{
		VarietyDAO dao = new VarietyDAO();
		Collection allVariety = null;
		try
		{
			allVariety = dao.getAllVariety();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return allVariety;
	}
	
	
	//根据保函种类编号查询保函种类信息
	public VarietyInfo getVarietyByCode(String code) throws Exception 
	{
		VarietyDAO dao = new VarietyDAO();
		VarietyInfo info = null;
		try
		{
			info = dao.getVarietyByCode(code);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	
	//保存保函种类修改信息
	public boolean modify(VarietyInfo info) throws Exception 
	{
		VarietyDAO dao = new VarietyDAO();
		boolean success = true;
		try
		{
			success = dao.modify(info);
		} 
		catch (Exception e) 
		{
			success = false;
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return success;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//删除保函种类
	public boolean delete(String code) throws Exception 
	{
		VarietyDAO dao = new VarietyDAO();
		boolean success = true;
		try
		{
			if(!this.isUsed(code))
			{
				success =  dao.delete(code);
			}
			else
			{
				success = false;
			}
		}
		catch (Exception e)
		{
			success = false;
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return success;
	}
	
	
	//判断该保函种类有没有在使用
	public boolean isUsed(String code) throws Exception 
	{
		VarietyDAO dao = new VarietyDAO();
		boolean yes = false;
		try
		{
			yes = dao.isUsed(code);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return yes;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	//取得下一个新增保函种类的编号并封装在dataentity中。
	public VarietyInfo getNextCode() throws Exception 
    {
		VarietyDAO dao = new VarietyDAO();
		VarietyInfo info = null;
		String code = "";
		try
		{
			code = dao.getNextCode();
			if(code != "")
			{
				info = new VarietyInfo();
				info.setSCode(code);
				info.nullToString();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
    }
    
    
	//新增保函种类
	public boolean insert(VarietyInfo info) throws Exception 	
	{
		VarietyDAO dao = new VarietyDAO();
		boolean success = true;
		try
		{
			success = dao.insert(info);
		} 
		catch (Exception e) 
		{
			success = false;
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return success;
	}
	
	
}
