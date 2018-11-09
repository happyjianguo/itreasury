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

import javax.servlet.http.HttpServletResponse;

import com.iss.itreasury.glinterface.u850.GLU850BeanForHL;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;

/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLDealBeanForHL
{
    /**
     *  
     */
    public GLDealBeanForHL()
    {
        super();
        // Auto-generated constructor stub
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
    public static String postGLVoucher(HttpServletResponse response,long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
    {
        boolean bIsPassed = true;
        String returnData = null;
        try
        {
        	//查找导出的接口配置
        	GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850BeanForHL.getGlSettingInfo(lOfficeID,lCurrencyID,1);
			
			if(glSettingInfo.getId()==-1)  //如果没有找到
			{
				System.out.println("====得到的ID值是=====:"+glSettingInfo.getId());
				System.out.println("不作导出会计分录处理!");
			}
			else{
				
			
        		System.out.println("*********findGLVoucherByCondition start(查找需要导出的凭证,开始!)******=");
                Collection collVoucher = BeanFactory.getGLWithinBean(lModelID).findGLVoucherByCondition(lOfficeID, lCurrencyID, lModelID, tsStart, tsEnd);
                System.out.println("*********findGLVoucherByCondition start(查找需要导出的凭证,结束!)******=");

                if (bIsPassed && collVoucher != null)
                {
                	GLU850BeanForHL glu850Beanforhl = new GLU850BeanForHL();
                	returnData = glu850Beanforhl.postGLVoucher(response,collVoucher, lOfficeID, lCurrencyID, tsStart, tsEnd);
                              
                }else{
                	System.out.println("***********没有做实际的导出处理!");
                	System.out.println("**********"+collVoucher);
                }
                	

			}    
        }
        catch (Exception e)
        {
            bIsPassed = false;
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        return returnData;
    }  
}