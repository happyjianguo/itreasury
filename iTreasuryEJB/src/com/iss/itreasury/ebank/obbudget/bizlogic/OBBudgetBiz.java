/*
 * 
 * Created on 2006-8-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.bizlogic;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iss.itreasury.ebank.obbudget.dao.OBBudgetDao;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetConditionInfo;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetBiz 
{

	/**
	 * ���������Ϣ�������Զ����񸶿�
	 */
	public List getBankAccountInfo(long accountNo)throws IException{
		List list = new ArrayList();
		OBBudgetDao dao = new OBBudgetDao();
		try{
			dao.getBankAccountInfo(accountNo);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return list;
	}
	
	/**
	 * ������е�����Ҫ���Զ�����ļ�¼
	 */
	public List getAllTodayAutoTask(long officeid,long currencyid)throws IException{
		List list = new ArrayList();
		OBBudgetDao dao = new OBBudgetDao();
		try{
			list = dao.getAllTodayAutoTask(officeid,currencyid);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return list;
	}
	
	/**
	 * ��ò���ʧ�ܵ��ܼ�¼��
	 */
	public long getAllFailedDealCount(long officeid,long currencyid,long clientid)throws IException{
		long count = 0;
		OBBudgetDao dao = new OBBudgetDao();
		try{
			count = dao.getAllFailedDealCount(officeid,currencyid,clientid);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return count;
	}
	
	/**
	 * �ж��ÿ����ʱ�Ĳ���������������Ϊ�ѱ��޸ģ����ؼ�Ϊδ���޸�
	 */
	public long checkModifyDate(OBBudgetInfo info)throws IException{
		long isModified = OBConstant.FALSE;
		Timestamp modifyDate = null;
		OBBudgetDao dao = new OBBudgetDao();
		try{
			modifyDate = dao.getModifyDate(info.getId());
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		if(info.getModifyDate() != null){
			if(!info.getModifyDate().equals(modifyDate)){
				isModified = OBConstant.TRUE;
			}
		}
		return isModified;
	}
	
	/**
	 * �ж��˻��Ƿ���Ч ��ͬ�˻�����ͬ��ʼ���ڵļ�¼������� ״̬��Ϊ����ɾ������������
	 */
	public long check(OBBudgetInfo info) throws IException{
		OBBudgetDao dao = new OBBudgetDao();
		long id = -1;
		try{
			boolean btmp = dao.isRepeat(info);
			if(btmp){
				id = Constant.FALSE;
			}
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
        return id;
	}
	
	/**
	 * ��������¼ID��ѯ���е��Ӽ�¼
	 */
	public List findAllSubRecords(long id) throws IException{
		List list = new ArrayList();
		OBBudgetDao dao = new OBBudgetDao();
		try{
			list = dao.findAllSubRecords(id);
		}catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return list;
	}
	
	
	/**
	 * �������޸��ÿ�  ��ͬ�˻�����ͬ��ʼ���ڵļ�¼������� ״̬��Ϊ����ɾ������������
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long save(OBBudgetInfo info) throws IException
	{
	    long lReturn = -1;
	    try {
	    	OBBudgetDao dao = new OBBudgetDao();
		    if(info.getId()<0) {
		        //OBBudgetDao dao = new OBBudgetDao();
		        boolean btmp = dao.isRepeat(info);
		         if(btmp){
		             throw new IException("OB_EC50"); //�쳣�����Զ���     
		         }
		         else { 
		             info.setStatus(OBConstant.OBBudgetStatus.SAVE);
		         	 lReturn = dao.add(info);       	 
		         }
		    }else {
		    	 long[] lstatus = new long[1];
		    	 lstatus[0] = OBConstant.OBBudgetStatus.SAVE;
		    	 boolean bstatus = dao.isStatus(info.getId(), lstatus);
		    	 boolean buser = dao.isComfirmer(info.getId(),info.getInputuser(),"input");
		    	 if(!bstatus){
		             throw new IException("OB_EC51"); //�쳣�����Զ���     
		         } else if(!buser) {
		    	 	 throw new IException("OB_EC54"); //�쳣�����Զ���   
		    	 } else { 
		         	dao.update(info);
		         	lReturn = info.getId();
		         }
		    }
        }catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return lReturn;
			
	}
	
	/**
	 * ���¼�¼��Ϣ
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long update(OBBudgetInfo info) throws IException
	{
	    long lReturn = -1;
	    try {
	    	OBBudgetDao dao = new OBBudgetDao();
		    dao.update(info);
		    lReturn = info.getId();
        }catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
        	e.printStackTrace(); 
        	throw new IException("Gen_E001");
        }
		return lReturn;
			
	}
	
	/**
	 * ����������ÿ�ƻ�
	 * @param info
	 * @return
	 * @throws IException
	 */
	public long saveSingleDay(OBBudgetInfo info) throws IException
	{
	    long lReturn = -1;
	    try
        {
		    if(info.getId()<0){
		        OBBudgetDao dao = new OBBudgetDao();
		        lReturn = dao.add(info);       	 
		    }		    
        }catch(IException ie){        	
			ie.printStackTrace(); 
			throw ie;
        }catch(Exception e){
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
		 		OBBudgetDao dao = new OBBudgetDao();
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
		 		OBBudgetDao dao = new OBBudgetDao();
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
		 		OBBudgetDao dao = new OBBudgetDao();
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
	public long matching (OBBudgetInfo info,long userId)throws IException 
	{
		long lmatch = -1;
		OBBudgetInfo conInfo = new OBBudgetInfo ();
		try
		{
			OBBudgetDao dao = new OBBudgetDao();
			conInfo.setSname(info.getSname());
			conInfo.setAccountID(info.getAccountID());
			conInfo.setStartdate(info.getStartdate());
			conInfo.setEnddate(info.getEnddate());
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
		 		OBBudgetDao dao = new OBBudgetDao();
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
	public  void  ChangeStatus (long AccountID ,Timestamp StartDate)throws IException 
	{    
	    Collection coll = null;
	    OBBudgetInfo [] rInfo = null;
	    long id = -1;
	    try
		{
	        //��ԭ�еġ�ʹ���С���Ԥ����Ϊ���ѹ��ڡ�
	        OBBudgetDao dao = new OBBudgetDao();
	        OBBudgetInfo info = new OBBudgetInfo ();
	        info.setAccountID(AccountID);
	        info.setStatus(OBConstant.OBBudgetStatus.DEAL);
	        coll = this.findByCondition(info);
			if(coll!=null&&coll.size()>0)
			{System.out.println("1111111");
			   rInfo = new OBBudgetInfo[0];
			   rInfo = (OBBudgetInfo[]) coll.toArray(rInfo);
			   id = rInfo[0].getId();
			   System.out.println("1111111"+id);
			   dao.updateStatus(id,OBConstant.OBBudgetStatus.FAILEDDEAL,-1,"transfer");
			}
			coll = null;
			rInfo = null;
			//�ѵ�ǰԤ����Ϊ��ʹ���С�
			info.setStatus(OBConstant.OBBudgetStatus.APPROVE);
			info.setStartdate(StartDate);
			coll = this.findByCondition(info);
			if(coll!=null&&coll.size()>0)
			{System.out.println("22222");
			   rInfo = new OBBudgetInfo[0];
			   rInfo = (OBBudgetInfo[]) coll.toArray(rInfo);
			   id = rInfo[0].getId();
			   dao.updateStatus(id,OBConstant.OBBudgetStatus.DEAL,-1,"transfer");
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
	 * findbyid
	 * @param ID
	 * @return
	 * @throws IException
	 */
	public OBBudgetInfo findByID (long ID)throws IException 
	{
		OBBudgetInfo info = new OBBudgetInfo();
		try
		{
			OBBudgetDao dao = new OBBudgetDao();
			info = (OBBudgetInfo) dao.findByID(ID,info.getClass());
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
	 * ��ѯ������Ԥ����Ԥ������������ѯ
	 * @param info
	 * @return
	 * @throws IException
	 */
	public PageLoader query (OBBudgetConditionInfo info)throws IException 
	{
	    PageLoader pageLoader = null;
		try
		{
			OBBudgetDao dao = new OBBudgetDao();
			pageLoader = dao.query(info);
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
 
		return pageLoader;	
	}
	
	/**
	 * ��ѯ������Ԥ����Ԥ������������ѯ
	 * @param info
	 * @return
	 * @throws IException
	 */
	public PageLoader query(OBBudgetInfo info)throws IException{
		return query(info,OBConstant.OBBudgetStatus.OBBUDGET);
	}
	
	public PageLoader query (OBBudgetInfo info,long queryType)throws IException 
	{
	    PageLoader pageLoader = null;
		try
		{
			OBBudgetDao dao = new OBBudgetDao();
			pageLoader = dao.query(info,queryType);
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
 
		return pageLoader;	
	}
	
	/**
	 * ��ѯ
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findByCondition (OBBudgetInfo info)throws IException 
	{
		Collection coll = null;
		try
		{
			OBBudgetDao dao = new OBBudgetDao();
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
	 * �޸��ÿ�״̬
	 * @param lInstructionID
	 * @param lStatus
	 * @param lUserID
	 * @return
	 * @throws IException
	 */	
	public long updateStatus(long ID, long lStatus, long lUserID) throws IException{
		return -1;
	}
	
	/**
	 * Ϊ�����ṩ�Ľӿ�   Ĭ������ ״̬Ϊ������
	 * @param AccountID  �˻�id
	 * @param StartDate  ��ʼ����
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
	        info.setAccountID(AccountID);
	        info.setStartdate(StartDate);
	        info.setStatus(OBConstant.OBBudgetStatus.APPROVE);
	        coll = this.findByCondition(info);
			if(coll!=null&&coll.size()>0)
			{
			   rInfo = new OBBudgetInfo[0];
			   rInfo = (OBBudgetInfo[]) coll.toArray(rInfo);
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
	
	
	public static void main(String arg[])
	{
		/*//check function "save"
		OBBudgetBiz biz = new OBBudgetBiz();
		OBBudgetInfo info = new OBBudgetInfo();
		Timestamp t1 = null;
		Timestamp t2 = null;
		t1 = Timestamp.valueOf("2006-08-29 00:00:00.000000000");
		t2 = Timestamp.valueOf("2006-09-01 00:00:00.000000000");
		//info.setId(2);
		info.setAccountID(2);
		info.setAdjunctID(123);
		info.setAmount(10000000);
		info.setClientID(1);
		info.setEnddate(t2);
		info.setInputdate(t1);
		info.setInputuser(2);
		 info.setNote("a2AAAqAAAAt");
		info.setSname("123456");
		info.setStartdate(t1);
		info.setStatus(1);
		 //  info.setModifydate(t1);
		 // info.setModifyuser(1432115);		
		try {
			long id = biz.save(info);
			System.out.println("barneyliu@=========="+id);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		*/
		/* // check function "detele"
		OBBudgetBiz biz = new OBBudgetBiz();
		 
		
		try {
			long id = biz.delete(2,1);
			System.out.println("barneyliu@=========="+id);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		/*//check function "check"
		OBBudgetBiz biz = new OBBudgetBiz();
		try {
			long id = biz.check(2,2);
			System.out.println("barneyliu@=========="+id);
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	    
	     //check function "check"
		OBBudgetBiz biz = new OBBudgetBiz();
		OBBudgetConditionInfo info = new OBBudgetConditionInfo();
		PageLoader   p= null;
		try {
		    Timestamp t1 = null;
			//Timestamp t2 = null;
			t1 = Timestamp.valueOf("2006-09-11 00:00:00.000000000");
			//t2 = Timestamp.valueOf("2006-09-01 00:00:00.000000000");
			 p=biz.query(info);
			 
			System.out.println("barneyliu@=========="+p.toString());
		} catch (IException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
			OBBudgetDao dao = new OBBudgetDao();
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
