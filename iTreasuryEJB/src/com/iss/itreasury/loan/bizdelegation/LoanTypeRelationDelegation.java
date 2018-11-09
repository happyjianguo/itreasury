/*
 * Created on 2005-5-8
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.bizdelegation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeRelationBiz;
import com.iss.itreasury.loan.setting.bizlogic.LoanTypeSettingBiz;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeRelationInfo;
import com.iss.itreasury.loan.setting.dataentity.LoanTypeSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoanTypeRelationDelegation extends Object implements java.io.Serializable
{
    private static Log4j log4j = null;
	
	private LoanTypeRelationBiz facade = null;

    /**
     * No argument constructor required by container.
     */
    public LoanTypeRelationDelegation()
    {
        log4j = new Log4j(Constant.ModuleType.LOAN, this);
		facade = new LoanTypeRelationBiz();
    }

    /**
     * 保存操作
     */
    public long save(LoanTypeRelationInfo[] infos) throws LoanException
    {
        long lReturn = -1;

        try
        {
        	if(infos != null)
        	{
//				LoanTypeRelationInfo infodel = new LoanTypeRelationInfo();
//				infodel = infos[0];
				/* 删除设置表下该办事处和币种的所有关系 */
				lReturn = facade.delete(infos[0]);
			
				for(int i=0;i<infos.length;i++)
				{
					if(infos[i].getSubLoanTypeID() > 0)
					{
//						LoanTypeRelationInfo info = new LoanTypeRelationInfo();
//						info = infos[i];

						LoanTypeSettingBiz set = new LoanTypeSettingBiz();
						LoanTypeSettingInfo tsinfo = new LoanTypeSettingInfo();
						tsinfo = set.findByID(infos[i].getSubLoanTypeID());
						infos[i].setLoanTypeID(tsinfo.getLoanTypeID());
						/* 更新设置表 */
						lReturn = facade.save(infos[i]);
					}
				}
        	}
        } 
        catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }
    
    //保存操作 Boxu Add 2008年2月21日
    public String validateSave(LoanTypeRelationInfo[] infos) throws LoanException
    {
    	String lReturn = "";

        try
        {
        	if(infos != null)
        	{
        		//处理之前进行效验
        		for(int i=0;i<infos.length;i++)
				{
					if(infos[i].getSubLoanTypeID() > 0)
					{
						LoanTypeSettingBiz set = new LoanTypeSettingBiz();
						LoanTypeSettingInfo tsinfo = new LoanTypeSettingInfo();
						tsinfo = set.findByID(infos[i].getSubLoanTypeID());
						infos[i].setLoanTypeID(tsinfo.getLoanTypeID());
						
						lReturn = facade.validateSubTypeId(infos[i]);
					}
					
					if(!lReturn.equals(""))
					{
						break;
					}
				}
        		
        		if(lReturn.equals(""))
        		{
					/* 删除设置表下该办事处和币种的所有关系 */
					facade.delete(infos[0]);
				
					for(int i=0;i<infos.length;i++)
					{
						if(infos[i].getSubLoanTypeID() > 0)
						{
							LoanTypeSettingBiz set = new LoanTypeSettingBiz();
							LoanTypeSettingInfo tsinfo = new LoanTypeSettingInfo();
							tsinfo = set.findByID(infos[i].getSubLoanTypeID());
							infos[i].setLoanTypeID(tsinfo.getLoanTypeID());
							/* 更新设置表 */
							facade.save(infos[i]);
						}
					}
        		}
        	}
        } 
        catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }
    
    /**
     * 删除操作
     */
    public long delete(LoanTypeRelationInfo info) throws LoanException
    {
        long lReturn = -1;

        try
        {
            /* 删除设置表 */
            lReturn = facade.delete(info);
        } 
        catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }
        return lReturn;
    }

    /**
     * 多笔查询操作
     */
    public Collection findByMultiOption(LoanTypeRelationInfo qInfo) throws LoanException
    {
        Collection c = null;

        try
        {
            c = facade.findByMultiOption(qInfo);
        } catch (Exception e)
        {
            throw new LoanException("Gen_E001", e);
        }

        return c;
    }   
    /**
	* 多笔查询操作
	*/
   public Collection findSubLoanTypeInfoByLoanTypeID(long lLoanTypeID) throws LoanException
   {
	   Vector v = new Vector();
	   Collection c = null;

	   try
	   {
	   		LoanTypeSettingBiz set = new LoanTypeSettingBiz();
			LoanTypeSettingInfo qInfo = new LoanTypeSettingInfo();
			qInfo.setPageNo(1);
			qInfo.setPageLineCount(10000);
			qInfo.setQueryPurpose(1);
			
		    c = set.findByMultiOption(qInfo);

			if( (c != null) && (c.size() > 0) )
			{
				Iterator it = c.iterator();
				while (it.hasNext() )
				{
					LoanTypeSettingInfo info = ( LoanTypeSettingInfo ) it.next();
					if(info.getLoanTypeID() == lLoanTypeID)
					{
						v.add(info);
						System.out.println("======= type = "+info.getLoanTypeID());
						System.out.println("======= id   = "+info.getId());
						System.out.println("======= name = "+info.getName());
					}
				}
			}
		    
	   } catch (Exception e)
	   {
		   throw new LoanException("Gen_E001", e);
	   }

	   //return list;
	   return (v.size() > 0 ? v : null);
   }
    
   /*
	* 取贷款类型关系设置中选择的大类
	*/
   public long[] getAllSetLoanTypeID(long officeID,long currencyID)
   {
		long[] allSetLoanTypeID = facade.getAllSetLoanTypeID(officeID,currencyID);//LOANConstant.LoanType.getAllCode();
			
		return allSetLoanTypeID;
   }
    
   /*
	* 取贷款类型关系设置中选择的zi类
	*/
   public long[] getAllSetSubLoanTypeID(long officeID,long currencyID)
   {
   		long[] loantype = LOANConstant.LoanType.getAllCode();
		
		long[] allSubLoanTypeID = facade.getAllSetSubLoanTypeID(officeID,currencyID,loantype);
					
		return allSubLoanTypeID;
   }
    
   /*
	* 取贷款类型关系设置中选择的zi类
	*/
   public long[] getAllSetSubLoanTypeIDByLoanTypeID(long officeID,long currencyID,long[] loanTypeID)
   {
	long[] allSubLoanTypeID = facade.getAllSetSubLoanTypeID(officeID,currencyID,loanTypeID);
					
	return allSubLoanTypeID;
   }
   
   public static void main(String[] args)
   {
	   try
	   {
		LoanTypeRelationDelegation op = new LoanTypeRelationDelegation();
		LoanTypeSettingInfo info = new LoanTypeSettingInfo();
		long[] loantype = {1,2,3};
		
		//Collection list = op.findSubLoanTypeInfoByLoanTypeID(2);
		
		long[] lsub = op.getAllSetSubLoanTypeID(1,1);
		
		for(int n=0;n<lsub.length;n++)
		{
			System.out.println("========"+n+"="+lsub[n]+":"+LOANConstant.SubLoanType.getName(lsub[n]));
		}
	   }
	   catch (Exception e)
	   {
		   System.out.println(e.toString());
	   }
   }



}
