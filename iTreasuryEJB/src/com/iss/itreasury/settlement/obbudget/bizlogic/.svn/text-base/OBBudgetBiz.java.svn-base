/*
 * Created on 2006-9-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.obbudget.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.obbudget.dao.OBBudgetDao;
import com.iss.itreasury.settlement.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetBiz
{
    /**
	 * 审核预算
	 * @param ID
	 * @return
	 * @throws IException
	 */
	public long Auditing (long ID ,String reason)throws IException 
	{
		long lReturn = -1;
		 try
	        {
		 		OBBudgetDao dao = new OBBudgetDao();
		 		long[] lstatus = new long[1];
		 		lstatus[0] = Constant.OBBudgetStatus.CHECK;
		 		boolean bstatus = dao.isStatus(ID, lstatus);
		 		if (!bstatus)
				{
					throw new IException("OB_EC58");
				}
		 		else
		 		{
		 			lReturn  = dao.updateStatus(ID,Constant.OBBudgetStatus.AUTHED,-1,"authing",reason);	 
		 		}
	        }
			catch(IException ie)
			{        	
				ie.printStackTrace(); 
				throw ie;
	        }
			catch(Exception e)
			{
	        	e.printStackTrace(); 
	        	throw new IException("Gen_E001");
	        }
	     
	        return lReturn;	 
	}
	
	
	 /**
	 * 审核预算
	 * @param ID
	 * @return
	 * @throws IException
	 */
	public long refuse (long ID,String reason )throws IException 
	{
		long lReturn = -1;
		 try
	        {
		 		OBBudgetDao dao = new OBBudgetDao();
		 		long[] lstatus = new long[1];
		 		lstatus[0] = Constant.OBBudgetStatus.CHECK;
		 		boolean bstatus = dao.isStatus(ID, lstatus);
		 		if (!bstatus)
				{
					throw new IException("OB_EC58");
				}
		 		else
		 		{
		 			lReturn  = dao.updateStatus(ID,Constant.OBBudgetStatus.REFUSE,-1,"refuse",reason);
		 			 
		 		}
	        }
			catch(IException ie)
			{        	
				ie.printStackTrace(); 
				throw ie;
	        }
			catch(Exception e)
			{
	        	e.printStackTrace(); 
	        	throw new IException("Gen_E001");
	        }
	     
	        return lReturn;	 
	}
	
	/**
	 * 修改状态，接口　　
	 * @param ID
	 * @param status
	 * @return
	 * @throws IException
	 */
	public  void  ChangeStatus (long AccountID ,Timestamp StartDate)throws IException 
	{    
	    Collection coll = null;
	    OBBudgetInfo [] rInfo = null;
	    long id = -1;
	    long ltemp = -1;
	    try
		{
	        //把原有的“使用中”的预算置为“已过期”
	        OBBudgetDao dao = new OBBudgetDao();
	        ltemp = dao.findIDByacctid(AccountID,null,Constant.OBBudgetStatus.USING);
			if(ltemp>0)
			{ 
			   dao.updateStatus(ltemp,Constant.OBBudgetStatus.OVER,-1,"transfer");
			}
 
			//把当前预算置为“使用中”
 
			ltemp = dao.findIDByacctid(AccountID,StartDate,Constant.OBBudgetStatus.AUTHED);
			if(ltemp>0)
			{ 
			   dao.updateStatus(ltemp,Constant.OBBudgetStatus.USING,-1,"transfer");
			}
		 }
		catch(IException ie)
		{        	
			ie.printStackTrace(); 
			throw ie;
        }
		catch(Exception e)
		{
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
	       
	}
	
	/**
	 * 为银企提供的接口   默认条件 状态为已审批
	 * @param AccountID  账户id
	 * @param StartDate  开始日期
	 * @return
	 */
	public double GetBudget(long AccountID ,Timestamp StartDate)throws IException
	{
	    double dReturn = 0.00;
	    Collection coll = null;
	    OBBudgetInfo [] rInfo = null;
	    try
		{
	        OBBudgetInfo info = new OBBudgetInfo ();
	        OBBudgetDao dao = new OBBudgetDao();
	        info.setAccountID(AccountID);
	        info.setStartdate(StartDate);
	        info.setStatus(OBConstant.OBBudgetStatus.APPROVE);
	        dReturn = dao.findBudgetAmount(info);
			 
			
		 }
		catch(IException ie)
		{        	
			ie.printStackTrace(); 
			throw ie;
        }
		catch(Exception e)
		{
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
        return dReturn;
	}
	
	public static void main(String args[])
	{
	    OBBudgetBiz z = new OBBudgetBiz();
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
	    try
        {
            z.ChangeStatus(1,ts);
           System.out.println("0+++++++++++++" );
        }
        catch (IException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
}
