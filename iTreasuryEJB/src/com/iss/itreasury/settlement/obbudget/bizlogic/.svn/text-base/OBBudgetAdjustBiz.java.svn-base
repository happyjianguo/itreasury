/*
 * Created on 2006-9-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.obbudget.bizlogic;


import java.sql.Timestamp;
import java.util.Collection;
import com.iss.itreasury.settlement.obbudget.dataentity.OBBudgetAdjustInfo;
import com.iss.itreasury.settlement.obbudget.dao.OBBudgetAdjustDao;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetAdjustBiz
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
		     	OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
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
		     	OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
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
	 * 为银企提供的接口   默认条件 状态为已审批
	 * @param AccountID  账户id
	 * @param StartDate  开始日期
	 * @return
	 */
	public  double GetBudgetAdjust(long AccountID ,Timestamp AdjustDate)throws IException 
	{
	    double dReturn = 0.00;
	    Collection coll = null;
	    OBBudgetAdjustInfo [] rInfo = null;
	    try
		{
	        OBBudgetAdjustInfo info = new OBBudgetAdjustInfo ();
	        OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
	        info.setAccountID(AccountID);
	        info.setAdjustdate(AdjustDate);
	        info.setStatus(Constant.OBBudgetStatus.AUTHED);
	        dReturn = dao.findBudgetAdjustAmount(info);
			 
			
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
	    OBBudgetAdjustBiz z = new OBBudgetAdjustBiz();
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
	    try
        {
           double b=  z.GetBudgetAdjust(1,ts);
           System.out.println("0+++++++++++++"+b);
        }
        catch (IException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
