/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.glinterface.genersoft.GLGenerSoftBean;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;

/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLDealBean
{
    /**
     *  
     */
    public GLDealBean()
    {
        super();
        // Auto-generated constructor stub
    }

    public static void main(String[] args)
    {
        try
        {
            /*导科目*/
        	//addSubject(1,1,1);
            /*导科目余额*/
            //addSubjectBalance(1, 1, 1, DataFormat.getDateTime("2004-11-17"), DataFormat.getDateTime("2004-11-17"));
            	/*导科目金额（在导科目余额内部会调用）*/
            	//addSubjectAmount(1, 1, 1, DataFormat.getDateTime("2005-01-31"), DataFormat.getDateTime("2005-01-31"));
            /*传输凭证*/
        	//postGLVoucher(1,1,1,DataFormat.getDateTime("2006-03-14"),DataFormat.getDateTime("2006-03-14"));
            /*导入外部客户*/
        	//addExternalAccount(1,1,1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * @author yychen ? postGLVoucherBean.postGLVoucher()：导出会计凭证 参数：；
     * 返回值：boolean bIsSuccess:是否成功； 功能描述：将业务系统生成的会计凭证导出至总账核算系统；
     * 流程描述(在此以导出到U850为例)： l 先调用查询findGLVoucherBaseBean.findGLVoucherByCondition
     * ()，得到需要导出的凭证数据集合，数据集合中的数据类型为VoucherInfo,而GLVoucherInfo由具体的会计分录GlEntryInfo构成；
     * l 将凭证数据集合作为参数，调用U850GLBean.postGlVoucher()，导出凭证； l
     * 调用U850GLBean.getPostGlVoucherBackInfo ()，得到总账系统返回的信息。 l
     * 根据返回的信息，调用postGLVoucherBean.updatePostStatus()，修改每笔会计分录的导出状态； l
     * 根据返回的信息,判断会计分录的是否全部成功导出，如果存在会计分录导出失败，则返回失败，否则返回成功。
     */
    //执行导出内部会计数据的一系列操作
    public static boolean postGLVoucher(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        boolean bIsPassed = true;
        try
        {
        	//查找导出的接口配置
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo = GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
			
			if(glSettingInfo.getId()==-1)  //如果没有找到
			{
				System.out.println("====得到的ID值是=====:"+glSettingInfo.getId());
				System.out.println("不作导出会计分录处理!");
				throw new IException("没有找到导出的接口配置,不作导出会计分录处理!");
			}
			else
			{
				if(glSettingInfo.getGlName().equals("U850")){
					if(glSettingInfo.getGlOperationType() == Constant.GLOperationType.NoOperator || glSettingInfo.getGlOperationType() == Constant.GLOperationType.ImportOnly){
						throw new IException("总账接口设置错误");
					}
				}
				
        		System.out.println("*********findGLVoucherByCondition start(查找需要导出的凭证,开始!)******=");
                Collection collVoucher = BeanFactory.getGLWithinBean(lModelID).findGLVoucherByCondition(lOfficeID, lCurrencyID, lModelID, tsStart, tsEnd);
                System.out.println("*********findGLVoucherByCondition start(查找需要导出的凭证,结束!)******=");

                if (bIsPassed && collVoucher != null)
                {
                    System.out.println("*********postGLVoucher start(开始把数据导出到外部的接口)******得到的数据条数是:="+collVoucher.size());
                    collVoucher=BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).postGLVoucher(collVoucher,lOfficeID,lCurrencyID,tsStart);
                    System.out.println("*********postGLVoucher end(导出凭证数据结束)******=");
                   
                    System.out.println("*********updatePostStatus start(更新内部的数据,sett_glentry表的传输状态开始)******=");
                    bIsPassed = BeanFactory.getGLWithinBean(lModelID).updatePostStatus(collVoucher);
                    System.out.println("*********updatePostStatus end(更新内部的数据,sett_glentry表的传输状态结束)******=");                   
                }else{
                	System.out.println("***********没有做实际的导出处理!");
                	System.out.println("**********"+bIsPassed);
                	System.out.println("**********"+collVoucher);
                }
			}    
        }
        catch(IException ie){
            bIsPassed = false;
            ie.printStackTrace();
            throw new IException(ie.getMessage());
        }
        catch(Exception e){
            bIsPassed = false;
            e.printStackTrace();
            throw e;
        }
        return bIsPassed;
    }
    /**
     * 修改会计分录状态,浪潮专用
     * @author xrli     
     * To change the template for this generated type comment go to
     * Window - Preferences - Java - Code Generation - Code and Comments
     */
    public static boolean updateStatus(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
	{
    	boolean bIsPassed = true;
       
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
       			System.out.println("-----------------不是内部财务："+glSettingInfo.getGlName());
        		
            //if (!Env.RUN_ENVIRONMENT.equals("isoftstone")) //说明不是内部
            //{
       			//浪潮
            	if(glSettingInfo.getGlName().equals("GENERSOFT"))
            	{
            		GLGenerSoftBean glBean = new GLGenerSoftBean(); 
            		//检查浪潮库是否已经成功导出
            		bIsPassed = glBean.checkPostVoucher(lOfficeID,lCurrencyID);
            	}

            	
            	if(bIsPassed)
            	{
            		System.out.println("通过了对外库进行检测的程序!!");
            		bIsPassed = BeanFactory.getGLWithinBean(lModelID).updatePostStatus(lOfficeID,lCurrencyID);
            	}
            	else
            		System.out.println("*********没有通过了对外库进行检测的程序!!");
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    	
    }

    /**
     * 导入科目，通用接口
     */
    public static boolean addSubject(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
    {
        boolean bIsPassed = true;
        Collection collSubject = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
        		System.out.println("*********导科目!!");
                collSubject = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLSubject(lOfficeID, lCurrencyID);
                
                if(collSubject!=null && collSubject.size()!=0)
                {
                	bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addSubject(lOfficeID, lCurrencyID, collSubject);
                	
                	//更新会计账科目时检查并更新科目上下级关级
                	String strConvert = Config.getProperty(ConfigConstant.SETTLEMENT_GLSUBJECTDEFINITION_CONVERT,"0");
                	SettGLWithinDao withinDao = new SettGLWithinDao();
                	withinDao.convertGlSubject(lOfficeID, lCurrencyID, strConvert);
                }
                else
                {
                	return false;
                }
        		System.out.println("*********导科目成功!!");
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
    /**
     * 单独调用杨垒写的存储过程获得所有科目的父节点ID
     */
    public static boolean addParentsubjectidToSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
        boolean bIsPassed = true;
        try
        {
        	//更新会计账科目时检查并更新科目上下级关级
        	String strConvert = Config.getProperty(ConfigConstant.SETTLEMENT_GLSUBJECTDEFINITION_CONVERT,"0");
        	SettGLWithinDao withinDao = new SettGLWithinDao();
        	withinDao.convertGlSubject(lOfficeID, lCurrencyID, strConvert);
        	System.out.println("*********获得所有科目的父节点ID成功!!");
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
    /**
     * 导入科目余额，通用接口
     */
    public static boolean addSubjectBalance(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStartDate, Timestamp tsEndDate) throws Exception
    {
        boolean bIsPassed = true;
        Collection collBalance = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
        	    //说明不是内部
                for (Timestamp tsDealingDate = tsStartDate; bIsPassed && DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsEndDate)) <= 0; tsDealingDate = DataFormat.getNextDate(tsDealingDate))
                {
                    collBalance = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLSubjectBalance(lOfficeID, lCurrencyID, tsDealingDate);
                    
                    System.out.println("********* 导科目余额 ******");
                    bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addSubjectBalance(lOfficeID, lCurrencyID, tsDealingDate, collBalance);
                    System.out.println("********* 导科目余额成功 ******");
                }
            }
            /**
             * modify by shuangniu 2011-01-19
             */
            //addSubjectAmount(lOfficeID, lCurrencyID, lModelID, tsStartDate, tsEndDate);
        }
        catch (Exception e)
        {
            bIsPassed = false;
            e.printStackTrace();
            throw e;
        }
        return bIsPassed;
    }

    public static boolean addSubjectAmount(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStartDate, Timestamp tsEndDate) throws Exception
    {
        boolean bIsPassed = true;
        Collection collBalance = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
            //if (!Env.RUN_ENVIRONMENT.equals("ISOFTSTONE")) //说明不是内部
            //{
                for (Timestamp tsDealingDate = tsStartDate; bIsPassed && DataFormat.getDateString(tsDealingDate).compareTo(DataFormat.getDateString(tsEndDate)) <= 0; tsDealingDate = DataFormat.getNextDate(tsDealingDate))
                {
                    collBalance = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLSubjectAmount(lOfficeID, lCurrencyID, lModelID, tsDealingDate);
                    System.out.println("********* 导科目金额及笔数 ******" + collBalance);
                    bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addSubjectAmount(lOfficeID, lCurrencyID, tsDealingDate, collBalance);
                    System.out.println("********* 导科目金额及笔数成功 ******" + collBalance);
                }
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }

    /**
     * 说明：导入外部客户
     * @author liuqing
     * @param lOfficeID
     * @param lCurrencyID
     * @param lModelID
     * @param tsStartDate
     * @param tsEndDate
     * @return
     * @throws Exception
     */
    public static boolean addExternalAccount(long lOfficeID, long lCurrencyID, long lModelID) throws Exception
    {
        boolean bIsPassed = true;
        Collection collExternalAccount = null;
        try
        {
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
        	glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
        	if(!glSettingInfo.getGlName().equals("ISOFTSTONE"))
        	{
            //if (!Env.RUN_ENVIRONMENT.equals("ISOFTSTONE")) //说明不是内部
            
        		if(glSettingInfo.getGlName().equals("KINGDEE")) //金蝶接口 
        		{
        			
        			collExternalAccount = BeanFactory.getGLExtendBean(lOfficeID,lCurrencyID).getGLExternalAccount(lOfficeID, lCurrencyID);
                    System.out.println("*********addExternalAccount******" + collExternalAccount);
                    bIsPassed = BeanFactory.getGLWithinBean(Constant.ModuleType.SETTLEMENT).addExternalAccount(lOfficeID, lCurrencyID, collExternalAccount);
                    
        		}
        		
            }
        }
        catch (Exception e)
        {
            bIsPassed = false;
            throw new RemoteException(e.getMessage());
        }
        return bIsPassed;
    }
    
}