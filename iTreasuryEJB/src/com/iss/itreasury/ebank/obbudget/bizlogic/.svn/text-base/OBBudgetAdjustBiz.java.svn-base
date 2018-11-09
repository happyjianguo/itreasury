/*
 * Created on 2006-8-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.bizlogic;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.ebank.obbudget.dao.OBBudgetAdjustDao;
import com.iss.itreasury.ebank.obbudget.dao.OBBudgetDao;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.ebank.util.OBConstant;
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
	 * 新增或修改预算  相同账户、相同起始日期的记录不能添加 状态不为“已拒绝,已删除”
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long save(OBBudgetAdjustInfo info) throws IException
	{
	    long lReturn = -1;
	    try
        {
		    if(info.getId()<0)
		    {
		        OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
		        OBBudgetDao budgetdao = new OBBudgetDao();
		        OBBudgetInfo budgetinfo = new OBBudgetInfo();
		        budgetinfo = (OBBudgetInfo) budgetdao.findByID(info.getBudgetID(),budgetinfo.getClass());
		        boolean btmp = dao.isRepeat(info);
		         if(btmp)
		         {
		             throw new IException("OB_EC59"); //异常代码自定义     
		         }
		         else if (info.getAdjustdate().before(budgetinfo.getStartdate()) ||
		                 info.getAdjustdate().after(budgetinfo.getEnddate()))
		         {
		             throw new IException("OB_EC60"); //异常代码自定义 
		         }
		         else 
		         { 
		             info.setStatus(OBConstant.OBBudgetStatus.SAVE);
		         	 lReturn = dao.add(info);       	 
		         }
		    }
		    else 
		    {
		         OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
		    	 long[] lstatus = new long[1];
		    	 lstatus[0] = OBConstant.OBBudgetStatus.SAVE;
		    	 boolean bstatus = dao.isStatus(info.getId(), lstatus);
		    	 boolean buser = dao.isComfirmer(info.getId(),info.getInputuser(),"input");
		    	 if(!bstatus)
		         {
		             throw new IException("OB_EC51"); //异常代码自定义     
		         }
		    	 else if(!buser)
		    	 {
		    	 	 throw new IException("OB_EC54"); //异常代码自定义   
		    	 }
		         else 
		         { 
		         	dao.update(info);
		         	lReturn = info.getId();
		         }
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
	 * 逻辑删除预算
	 * @param ID
	 * @param userID
	 * @return
	 * @throws IException
	 */
	public long delete (long ID,long userID) throws IException 
	{
		long lReturn = -1;
		 try
	        {
		     OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
		 		long[] lstatus = new long[1];
		 		lstatus[0] = OBConstant.OBBudgetStatus.SAVE;
		 		boolean btmp = dao.isStatus(ID, lstatus);
		 		boolean buser = dao.isComfirmer(ID,userID,"delete");
		 		if (!btmp)
				{
					throw new IException("OB_EC52");
				}
		 		else if(!buser)
		 		{
		 			throw new IException("OB_EC54"); //异常代码自定义
		 		}
		 		else 
		        {
		 		 	lReturn  = dao.updateStatus(ID,OBConstant.OBBudgetStatus.DELETE,userID,"delete");	 
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
	 * 复核预算　　只有“已提交”状态的,不是录入人的可以复核
	 * /检查此交易指令在复核前是否被修改或删除,只有确认的指令才能被复核
	 * @param ID
	 * @param checkuser
	 * @return
	 * @throws IException
	 */
	public long check (long ID,long checkuser)throws IException 
	{
		long lReturn = -1;
		 try
	        {
		     	OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
		 		long[] lstatus = new long[1];
		 		lstatus[0] = OBConstant.OBBudgetStatus.SAVE;
		 		boolean bstatus = dao.isStatus(ID, lstatus);
		 		boolean buser = dao.isComfirmer(ID,checkuser,"check");
		 		if (!bstatus)
				{
					throw new IException("OB_EC53");
				}
		 		else if(buser)
		        {
		 			 throw new IException("OB_EC55");
		        }
		 		else
		 		{
		 			lReturn  = dao.updateStatus(ID,OBConstant.OBBudgetStatus.CHECK,checkuser,"check");	 
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
	 * 取消复核，为“已复核”状态的，且为复核人
	 * @return
	 * @throws IException
	 */
	public long canclecheck (long ID,long checkuser) throws IException 
	{
		long lReturn = -1;
		 try
	        {
		     	OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
		 		long[] lstatus = new long[1];
		 		lstatus[0] = OBConstant.OBBudgetStatus.CHECK;
		 		boolean bstatus = dao.isStatus(ID, lstatus);
		 		boolean buser = dao.isComfirmer(ID,checkuser,"canclecheck");
		 		if (!bstatus)
				{
					throw new IException("OB_EC56");
				}
		 		else if(!buser)
		        {
		 			 throw new IException("OB_EC57");
		        }
		 		else
		 		{
		 			lReturn  = dao.updateStatus(ID,OBConstant.OBBudgetStatus.SAVE,checkuser,"check");	 
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
	 * 匹配
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long matching (OBBudgetAdjustInfo info,long userId)throws IException 
	{
		long lmatch = -1;
		OBBudgetAdjustInfo conInfo = new OBBudgetAdjustInfo ();
		try
		{
			OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
			conInfo.setBudgetID(info.getBudgetID());
			conInfo.setAdjustdate(info.getAdjustdate());
			conInfo.setAmount(info.getAmount());
			conInfo.setStatus(OBConstant.OBBudgetStatus.SAVE);
			lmatch =  dao.matching(conInfo,userId);
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
		return lmatch;
	}
	
	
	/**
	 * 审核预算
	 * @param ID
	 * @return
	 * @throws IException
	 */
	public long Auditing (long ID )throws IException 
	{
		long lReturn = -1;
		 try
	        {
		     	OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
		 		long[] lstatus = new long[1];
		 		lstatus[0] = OBConstant.OBBudgetStatus.SAVE;
		 		boolean bstatus = dao.isStatus(ID, lstatus);
		 		if (!bstatus)
				{
					throw new IException("OB_EC58");
				}
		 		else
		 		{
		 			lReturn  = dao.updateStatus(ID,OBConstant.OBBudgetStatus.APPROVE,-1,"authing");	 
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
	public void ChangeStatus (long AccountID ,Timestamp StartDate)throws IException 
	{ 
	}
	
	/**
	 * findbyid
	 * @param ID
	 * @return
	 * @throws IException
	 */
	public OBBudgetAdjustInfo findByID (long ID)throws IException 
	{
	    OBBudgetAdjustInfo info = new OBBudgetAdjustInfo();
		try
		{
			OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
			info = (OBBudgetAdjustInfo) dao.findByID(ID,info.getClass());
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
		return info;	
	}
	/**
	 * 查询
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findByCondition (OBBudgetAdjustInfo info)throws IException 
	{
	    Collection coll = null;
		try
		{
		    OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
			coll = dao.findByCondition(info);
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
		return coll;	
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
	        info.setAccountID(AccountID);
	        info.setAdjustdate(AdjustDate);
	        info.setStatus(OBConstant.OBBudgetStatus.APPROVE);
	        coll = this.findByCondition(info);
			if(coll!=null&&coll.size()>0)
			{
			   rInfo = new OBBudgetAdjustInfo[0];
			   rInfo = (OBBudgetAdjustInfo[]) coll.toArray(rInfo);
			   dReturn = rInfo[0].getAmount();
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
        return dReturn;
	}
	/**
	 * 查询　用于预算符合查询
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection checkquery (long clientid,long userid)throws IException 
	{
	    Collection result = null;
		try
		{
			OBBudgetAdjustDao dao = new OBBudgetAdjustDao();
			result = dao.checkquery(clientid,userid);
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
 
		return result;	
	}
}
