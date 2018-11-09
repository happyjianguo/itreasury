/*
 * Created on 2003-9-16
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.clientmanage.bizdelegation;

import java.util.Collection;
import java.sql.SQLException;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.setting.bizlogic.SettInterestRateSettingBean;
import com.iss.itreasury.settlement.clientmanage.setting.dataentity.ExtendAttributeInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettInterestRateTypeInfo;
import com.iss.itreasury.settlement.setting.dataentity.SettSettingRateQueryInfo;
import com.iss.itreasury.settlement.setting.bizlogic.ExternalClientBiz;
import com.iss.itreasury.settlement.setting.dataentity.ExtClientSetInfo;
import com.iss.itreasury.settlement.clientmanage.setting.bizlogic.EnterpriceTypeBiz;
import com.iss.itreasury.settlement.clientmanage.setting.dataentity.EnterpriceTypeSetInfo;
import com.iss.itreasury.settlement.clientmanage.setting.bizlogic.IndustryTypeBiz;
import com.iss.itreasury.settlement.clientmanage.setting.dataentity.IndustryTypeSetInfo;
import com.iss.itreasury.settlement.clientmanage.setting.bizlogic.ClientTypeBiz;
import com.iss.itreasury.settlement.clientmanage.setting.bizlogic.ExtendAttributeBiz;
import com.iss.itreasury.settlement.setting.bizlogic.MonthBudgetBiz;
import com.iss.itreasury.settlement.clientmanage.setting.dataentity.ClientTypeSetInfo;
import com.iss.itreasury.settlement.setting.dataentity.MonthBudgetSetInfo;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettingDelegation
{
	private ExternalClientBiz extCltBiz = null;
    private EnterpriceTypeBiz entPriBiz = null;
    private IndustryTypeBiz indTypBiz = null;
    private ClientTypeBiz cliTypBiz = null;
    private MonthBudgetBiz monthBudgetBiz = null;
    private ExtendAttributeBiz extendAttrBiz = null;
	
	public SettingDelegation()
	{
		extCltBiz = new ExternalClientBiz();
        entPriBiz = new EnterpriceTypeBiz();
        indTypBiz = new IndustryTypeBiz();
        cliTypBiz = new ClientTypeBiz();
        monthBudgetBiz = new MonthBudgetBiz();
        extendAttrBiz = new ExtendAttributeBiz();
	}
	
	//保存
	public long save(ExtClientSetInfo info)throws Exception
	{
		long lRtn=-1;
		try
		{
			//必须区分是新增保存还是修改保存
			ExtClientSetInfo infoFromDb=this.extCltBiz.getExtCltDao().findByID(info.getID());
			//新增保存
			if(infoFromDb==null)
			{
System.out.println("\n\n 新增保存 \n\n");
				lRtn=this.extCltBiz.getExtCltDao().add(info);
			}
			//修改保存
			else
			{
System.out.println("\n\n 修改保存 \n\n");
				lRtn=this.extCltBiz.getExtCltDao().update(info);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("保存失败:"+e.getMessage());
		}
		
		return lRtn;
	}			
	
	
	//删除
	public long delete(ExtClientSetInfo info)throws Exception
	{
		long lRtn=-1;
		try
		{
			//查看是否存在该记录
			ExtClientSetInfo infoFromDb=this.extCltBiz.getExtCltDao().findByID(info.getID());
			//新增保存
			if(infoFromDb!=null)
			{
				lRtn=this.extCltBiz.getExtCltDao().delete(info.getID());
			}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("删除失败:"+e.getMessage());
		}
		return lRtn;
	}
	
	//查询
	public ExtClientSetInfo findByID(long nID)throws Exception
	{
		ExtClientSetInfo info=null;
		try
		{
			info=this.extCltBiz.getExtCltDao().findByID(nID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("查询失败:"+e.getMessage());
		}
		return info;
	}
	
	//通过OfficeID查询
	public Collection findByOfficeID(long nOfficeID,boolean isDesc)throws Exception
	{
		Collection cln=null;
		try
		{
			cln=this.extCltBiz.getExtCltDao().findByOfficeID(nOfficeID,isDesc);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("查询失败:"+e.getMessage());
		}
		return cln;
	}
    
    //以下是行业分类设置的相关程序
    //保存
    public long save(EnterpriceTypeSetInfo info)throws Exception
    {
        long lRtn=-1;
        try
        {
            //必须区分是新增保存还是修改保存
            //EnterpriceTypeSetInfo infoentpri = this.entPriBiz.getEntPriDao().findByID(info.getID());
            //新增保存
            if(info.getID() > 0)
            {
                System.out.println("\n\n 新增保存 \n\n");
                lRtn = this.entPriBiz.getEntPriDao().update(info);
            }
            //修改保存
            else
            {
                System.out.println("\n\n 修改保存 \n\n");
                lRtn=this.entPriBiz.getEntPriDao().add(info);
            }           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("保存失败:"+e.getMessage());
        }
        
        return lRtn;
    }           
    
    
    //删除
    public long delete(EnterpriceTypeSetInfo info)throws Exception
    {
        long lRtn=-1;
        try
        {
            //查看是否存在该记录
            EnterpriceTypeSetInfo infoentpri = this.entPriBiz.getEntPriDao().findByID(info.getID());
            //存在
            if(infoentpri != null)
            {
                lRtn=this.entPriBiz.getEntPriDao().delete(info.getID());
            }               
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除失败:"+e.getMessage());
        }
        return lRtn;
    }
    
    //查询buy id
    public EnterpriceTypeSetInfo findByEntPriID(long nID)throws Exception
    {
        EnterpriceTypeSetInfo info = null;
        try
        {
            info = this.entPriBiz.getEntPriDao().findByID(nID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询失败:"+e.getMessage());
        }
        return info;
    }
    
    //查询所有
    public Collection findEntPri(boolean isDesc,long lOfficeID)throws Exception
    {
        Collection cln = null;
        try
        {
            cln = this.entPriBiz.getEntPriDao().find(isDesc,lOfficeID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询失败:"+e.getMessage());
        }
        return cln;
    }
    
    //以下是企业类型设置的相关程序
    //保存
    public long save(IndustryTypeSetInfo info)throws Exception
    {
        long lRtn=-1;
        try
        {
            //必须区分是新增保存还是修改保存
            //IndustryTypeSetInfo infoentpri = this.indTypBiz.getIndTypDao().findByID(info.getID());
            //新增保存
            if(info.getID() > 0)
            {
                System.out.println("\n\n 新增保存 \n\n");
                lRtn = this.indTypBiz.getIndTypDao().update(info);
            }
            //修改保存
            else
            {
                System.out.println("\n\n 修改保存 \n\n");
                lRtn=this.indTypBiz.getIndTypDao().add(info);
            }           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("保存失败:"+e.getMessage());
        }
        
        return lRtn;
    }           
    
    
    //删除
    public long delete(IndustryTypeSetInfo info)throws Exception
    {
        long lRtn=-1;
        try
        {
            //查看是否存在该记录
            IndustryTypeSetInfo infoentpri = this.indTypBiz.getIndTypDao().findByID(info.getID());
            //存在
            if(infoentpri != null)
            {
                lRtn=this.indTypBiz.getIndTypDao().delete(info.getID());
            }               
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除失败:"+e.getMessage());
        }
        return lRtn;
    }
    
    //查询buy id
    public IndustryTypeSetInfo findByIndTypID(long nID)throws Exception
    {
        IndustryTypeSetInfo info = null;
        try
        {
            info = this.indTypBiz.getIndTypDao().findByID(nID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询失败:"+e.getMessage());
        }
        return info;
    }
    
    //查询所有
    public Collection findIndTyp(boolean isDesc)throws Exception
    {
        Collection cln = null;
        try
        {
            cln = this.indTypBiz.getIndTypDao().find(isDesc);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询失败:"+e.getMessage());
        }
        return cln;
    }
    
    //以下是企业类型设置的相关程序
    //保存
    public long save(ClientTypeSetInfo info)throws Exception
    {
        long lRtn=-1;
        try
        {
            //必须区分是新增保存还是修改保存
            //ClientTypeSetInfo infoentpri = this.indTypBiz.getIndTypDao().findByID(info.getID());
            //新增保存
            if(info.getID() > 0)
            {
                System.out.println("\n\n 新增保存 \n\n");
                lRtn = this.cliTypBiz.getCliTypDao().update(info);
            }
            //修改保存
            else
            {
                System.out.println("\n\n 修改保存 \n\n");
                lRtn=this.cliTypBiz.getCliTypDao().add(info);
            }           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("保存失败:"+e.getMessage());
        }
        
        return lRtn;
    }           
    
    
    //删除
    public long delete(ClientTypeSetInfo info)throws Exception
    {
        long lRtn=-1;
        try
        {
            //查看是否存在该记录
            ClientTypeSetInfo infoentpri = this.cliTypBiz.getCliTypDao().findByID(info.getID());
            //存在
            if(infoentpri != null)
            {
                lRtn=this.cliTypBiz.getCliTypDao().delete(info.getID());
            }               
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除失败:"+e.getMessage());
        }
        return lRtn;
    }
    
    //查询buy id
    public ClientTypeSetInfo findByCliTypID(long nID)throws Exception
    {
        ClientTypeSetInfo info = null;
        try
        {
            info = this.cliTypBiz.getCliTypDao().findByID(nID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询失败:"+e.getMessage());
        }
        return info;
    }
    
    //查询所有
    public Collection findCliTyp(boolean isDesc,long lOfficeID)throws Exception
    {
        Collection cln = null;
        try
        {
            cln = this.cliTypBiz.getCliTypDao().find(isDesc, lOfficeID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("查询失败:"+e.getMessage());
        }
        return cln;
    }

//=================================================================================================
    //*****************************************************
    //增加人：kewenhu(胡志强)---增加时间：2004-04-26
    //*****************************************************
	/**
	 * 查询有效记录(以nClientID自动排序)
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return Collection[]
	 * @exception SQLException
	 */
    public Collection[] findMonthBudget(MonthBudgetSetInfo paramInfo) throws SQLException {
        return this.monthBudgetBiz.getSett_MonthBudgetDAO().find(paramInfo);
    }


	/**
	 * 新增一条记录
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return long
	 * @exception SQLException
	 */
    public long addMonthBudget(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.monthBudgetBiz.getSett_MonthBudgetDAO().add(paramInfo);
    }

	/**
	 * 修改一条记录
	 * @param  MonthBudgetSetInfo paramInfo
	 * @return long
	 * @exception SQLException
	 */
    public long updateMonthBudget(MonthBudgetSetInfo paramInfo) throws SQLException {
    	return this.monthBudgetBiz.getSett_MonthBudgetDAO().update(paramInfo);
    }

	/**
	 * 析构函数
	 * @param  nothing
	 * @return nothing(关闭新创建的数据库连接)
	 * @exception SQLException
	 */
	public void CloseMonthBudget() throws SQLException {
		this.monthBudgetBiz.getSett_MonthBudgetDAO().CloseSett_MonthBudgetDAO();
	}
//=================================================================================================



	public String findMaxInterestRateCode(long lCurrencyID)
            throws SettlementDAOException
    {
        //Connection conn = null;
        //System.out.println("通过delegation  调用-->>>>>>>>");
        String strTemp = null;
        SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
        strTemp = biz.findMaxInterestRateCode(lCurrencyID);
        return strTemp;
    }

    public SettInterestRateSettingInfo findInterestRateByID(long lID)
            throws SettlementException
    {
        SettInterestRateSettingInfo tempInfo = null;
        
        SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
        
        tempInfo = biz.findInterestRateByID(lID);

        return tempInfo;

    }
    
    public Collection findInterestRateByTypeID(
            SettSettingRateQueryInfo queryInfo) throws SettlementException
    {
        Collection c = null;
        SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
        c = biz.findInterestRateByTypeID(queryInfo);
        return c;

    }
    
    /**
     * findInterestRateHistory  查看历史利率记录
     * 根据利率类型标示查询历史利率记录
     * 操作InterestRateHistory、InterestRateTypeInfo数据表
     * 查询相应记录
     * 只查出生效日在当前日期之前的纪录
     *
     * @param lTypeID 银行利率编号
     * @param lCurrencyID 币种
     * @return Collection 结果集
     * @throws SettlementDAOException
     * @throws RemoteException
     */
    
    public Collection findInterestRateHistoryByTypeID (
            SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        Collection c = null;
        SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
        c = biz.findInterestRateHistoryByTypeID(queryInfo);
        return c;
    }
    
    /**
     * findInterestRateHistory  查看所有历史利率记录
     * 查询所有历史利率记录
     * 操作BankLoanInterestRate、InterestRateTypeInfo数据表
     * 查询相应记录
     *
     * 只查出生效日在当前日期之前的纪录
     * @param lCurrencyID 币种
     * @return Collection 结果集
     * @throws SettlementDAOException
     * @throws RemoteException
     */
    
    
    public Collection findInterestRateHistory (
            SettSettingRateQueryInfo queryInfo) throws SettlementDAOException
    {
        Collection c = null;
        SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
        c = biz.findInterestRateHistory(queryInfo);
        return c;
    }
    
    public long addSett_InterestRateSetting(
            SettInterestRateSettingInfo rateInfo,
            SettInterestRateTypeInfo ratetypeInfo)
            throws SettlementException
            {
                long lTemp = -1;
                SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
                lTemp = biz.addSett_InterestRateSetting(rateInfo,ratetypeInfo);                    
                return lTemp;
            }
    public long updateSett_InterestRateSetting(
            SettInterestRateSettingInfo rateInfo,
            SettInterestRateTypeInfo ratetypeInfo)
            throws SettlementException
            {
        		long lTemp = -1;
        		SettInterestRateSettingBean biz = new SettInterestRateSettingBean();
        		lTemp = biz.updateSett_InterestRateSetting(rateInfo,ratetypeInfo);
        		return lTemp;
            }
    
    //=================================================================================================
    //操作扩展属性表(Sett_ExtendAttribute)开始*****************************************************
    //增加人：weilu  增加时间：2004-11-16
    
    /**
     * 新增或修改扩展属性:根据info里的ID,如果大于-1则是修改,否则新增
     * @param info
     * @return 新增或修改的扩展属性ID
     * @throws SettlementException
     */
    public long save(ExtendAttributeInfo info) throws SettlementException
	{
    	return extendAttrBiz.save(info);
    }
    /**
     * 删除扩展属性
     * @param id
     * @throws SettlementException
     */
    public void delete(long id) throws SettlementException
	{
    	extendAttrBiz.delete(id);
    }
    /**
     * 根据扩展属性类型查询扩展属性
     * @param lAttributeTypeID
     * @return
     * @throws SettlementException
     */
    public Collection findByAttrTypeID(long lAttributeTypeID,long lOfficeID) throws SettlementException
	{
    	return extendAttrBiz.findByAttrID(lAttributeTypeID,lOfficeID);
    }
    /**
     * 根据扩展属性ID得到属性信息
     * @param id
     * @return
     * @throws SettlementException
     */
    public ExtendAttributeInfo findByAttrID(long id) throws SettlementException
	{
    	return extendAttrBiz.findByID(id);
    }
    
	//操作扩展属性表(Sett_ExtendAttribute)结束*****************************************************
	//=================================================================================================
}
