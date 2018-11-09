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
	 * �������޸�Ԥ��  ��ͬ�˻�����ͬ��ʼ���ڵļ�¼������� ״̬��Ϊ���Ѿܾ�,��ɾ����
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
		             throw new IException("OB_EC59"); //�쳣�����Զ���     
		         }
		         else if (info.getAdjustdate().before(budgetinfo.getStartdate()) ||
		                 info.getAdjustdate().after(budgetinfo.getEnddate()))
		         {
		             throw new IException("OB_EC60"); //�쳣�����Զ��� 
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
		             throw new IException("OB_EC51"); //�쳣�����Զ���     
		         }
		    	 else if(!buser)
		    	 {
		    	 	 throw new IException("OB_EC54"); //�쳣�����Զ���   
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
	 * �߼�ɾ��Ԥ��
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
		 			throw new IException("OB_EC54"); //�쳣�����Զ���
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
	 * ����Ԥ�㡡��ֻ�С����ύ��״̬��,����¼���˵Ŀ��Ը���
	 * /���˽���ָ���ڸ���ǰ�Ƿ��޸Ļ�ɾ��,ֻ��ȷ�ϵ�ָ����ܱ�����
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
	 * ȡ�����ˣ�Ϊ���Ѹ��ˡ�״̬�ģ���Ϊ������
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
	 * ƥ��
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
	 * ���Ԥ��
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
	 * �޸�״̬���ӿڡ���
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
	 * ��ѯ
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
	 * Ϊ�����ṩ�Ľӿ�   Ĭ������ ״̬Ϊ������
	 * @param AccountID  �˻�id
	 * @param StartDate  ��ʼ����
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
	 * ��ѯ������Ԥ����ϲ�ѯ
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
