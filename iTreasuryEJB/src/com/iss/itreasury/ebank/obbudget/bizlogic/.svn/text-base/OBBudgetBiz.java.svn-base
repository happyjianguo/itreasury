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
	 * 获得银行信息，用于自动任务付款
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
	 * 获得所有当天需要跑自动任务的记录
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
	 * 获得拨付失败的总记录数
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
	 * 判断用款调整时的并发操作，返回真为已被修改，返回假为未被修改
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
	 * 判断账户是否有效 相同账户、相同起始日期的记录不能添加 状态不为“已删除，被调整”
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
	 * 根据主记录ID查询所有的子记录
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
	 * 新增或修改用款  相同账户、相同起始日期的记录不能添加 状态不为“已删除，被调整”
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
		             throw new IException("OB_EC50"); //异常代码自定义     
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
		             throw new IException("OB_EC51"); //异常代码自定义     
		         } else if(!buser) {
		    	 	 throw new IException("OB_EC54"); //异常代码自定义   
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
	 * 更新记录信息
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
	 * 新增单天的用款计划
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
	 * 取消复核，为“已复核”状态的，且为复核人
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
	 * 匹配
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
	    try
		{
	        //把原有的“使用中”的预算置为“已过期”
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
			//把当前预算置为“使用中”
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
	 * 查询　用于预算与预算调整的整体查询
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
	 * 查询　用于预算与预算调整的整体查询
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
	 * 查询
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
	 * 修改用款状态
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
