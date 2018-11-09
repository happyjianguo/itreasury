/*
 * Created on 2005-5-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.ebank.bizdelegation;

import java.sql.Connection;
import java.util.Collection;
import javax.servlet.jsp.JspWriter;
import com.iss.itreasury.ebank.approval.bizlogic.ApprovalBiz;
import com.iss.itreasury.ebank.approval.dataentity.*;

/**
 * 01、findApprovalSetting 		查询审批设置信息
 * 02、saveApprovalTracing 		保存审核意见信息
 * 03、findApprovalTracingInfo 	查询指定级别的审核意见信息
 * 04、findApprovalTracing 		查询审核意见信息
 * 05、getLastCheckPerson 		获得最后一个审核人姓名
 * 06、deleteApprovalTracing 	删除审核意见信息
 * 07、showApprovalUserList 		显示下一级审核人列表
 * 08、findApprovalUserLevel 	查询用户审核级别
 * 09、getApprovalID 			返回审批设置ID
 * 10、findTheVeryUser 			查询可以做该审核的实际用户
 * 11、findTheVeryUser 			查询可以做该审核的实际用户
 */

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ApprovalDelegation
{
	ApprovalBiz bean ;
	/**
	 * 
	 */
	public ApprovalDelegation ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		bean = new ApprovalBiz();
	}
	
	/**
	 * 查询审批设置信息
	 * 查询条件为审批设置标示
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID    		   审批设置标示（查询条件）
	 * @return      ApprovalSettingInfo    返回审批设置信息
	 */
	public ApprovalSettingInfo findApprovalSetting(long lApprovalID) throws Exception
	{		
		return bean.findApprovalSetting(lApprovalID);
	}
	
	/**
	 * 保存审核意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       ATInfo      审核意见信息
	 * @return      long        成功，返回值=1；失败，返回值=-1
	 */
	public long saveApprovalTracing(ApprovalTracingInfo ATInfo) throws Exception
	{
		return bean.saveApprovalTracing(ATInfo);
	}

	/**
	 * 查询指定级别的审核意见信息
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型ID
	 * @param       lLoanTypeID				贷款类型子类ID
	 * @param       lActionID               操作ID
	 * @param       lOfficeID               办事处ID
	 * @param       lCurrencyID             币种ID
	 * @param       lApprovalContentID      审核内容ID
	 * @param       lLevel               	审批级别
	 * @return      ApprovalTracingInfo     返回审核意见信息(ApprovalTracingInfo)
	 */
	public ApprovalTracingInfo findApprovalTracingInfo(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lLevel) throws Exception
	{
		return bean.findApprovalTracingInfo(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lLevel);
	}

	/**
	 * 查询审核意见信息
	 * 根据需要排序
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型ID
	 * @param       lLoanTypeID				贷款类型子类ID
	 * @param       lActionID               操作ID
	 * @param       lOfficeID               办事处ID
	 * @param       lCurrencyID             币种ID
	 * @param       lApprovalContentID      审核内容ID
	 * @param       lDesc               	排序方式
	 * @return      Collection              返回审核意见信息(ApprovalTracingInfo)
	 */
	public Collection findApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lDesc) throws Exception
	{
		return bean.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lDesc);
	}

	/**
	 * 获得最后一个审核人姓名
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型ID
	 * @param       lLoanTypeID				贷款类型子类ID
	 * @param       lActionID               操作ID
	 * @param       lOfficeID               办事处ID
	 * @param       lCurrencyID             币种ID
	 * @param       lApprovalContentID      审核内容标示
	 * @return      String              	返回最后一个审核人姓名
	 */
	public String getLastCheckPerson(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID) throws Exception
	{
		return bean.getLastCheckPerson(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID);
	}

	/**
	 * 删除审核意见信息
	 * 原则上应该都使用逻辑删除
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID               模块类型ID
	 * @param       lLoanTypeID				贷款类型子类ID
	 * @param       lActionID               操作ID
	 * @param       lOfficeID               办事处ID
	 * @param       lCurrencyID             币种ID
	 * @param       lApprovalContentID      审核内容标示
	 * @param       lActionID               标识：1、物理删除，2、逻辑删除
	 * @return      long                    成功，返回值=1；失败，返回值=-1
	 */
	public long deleteApprovalTracing(long lModuleID, long lLoanTypeID, long lActionID, long lOfficeID, long lCurrencyID, long lApprovalContentID, long lTypeID) throws	Exception
	{
		return bean.deleteApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lApprovalContentID,lTypeID);
	}

	/**
	 * 显示下一级审核人列表
	 * 支持越级审核以及额度控制
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       out                     输出
	 * @param       strFieldName            域的名称
	 * @param       strNextFieldName        下一个域的名称
	 * @param       lModuleID               模块类型ID
	 * @param       lLoanTypeID				贷款类型子类ID
	 * @param       lActionID               操作ID
	 * @param       lOfficeID               办事处ID
	 * @param       lCurrencyID             币种ID
	 * @param       lUserID                 审核用户标示
	 * @param       lLevel                  当前审核级别
	 * @param       bIsLowLevel             是否走最少审批级别
	 * @return      long                    最后一级审核返回1；其他级别审核返回0；错误返回-1
	 */
	public long showApprovalUserList(JspWriter out, String strFieldName,
									 String strNextFieldName, long lModuleID,
									 long lLoanTypeID, long lActionID,
									 long lOfficeID, long lCurrencyID,
									 long lUserID, long lLevel, 
									 boolean bIsLowLevel) throws Exception
	{
		return bean.showApprovalUserList(out,strFieldName,strNextFieldName,lModuleID,
				 lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lUserID,lLevel,bIsLowLevel);
	}
	
	/**
	 * 查询用户审核级别
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lApprovalID            审批设置标示
	 * @param       lUserID                用户标识
	 * @return      long                   返回用户审核级别
	 */
	public long findApprovalUserLevel(long lApprovalID, long lUserID) throws Exception
	{		
		return bean.findApprovalUserLevel(lApprovalID,lUserID);

	}

	/**
	 * 返回审批设置ID
	 * 支持通过相关设置增加的审批设置信息，如资金计划模块的部门设置
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              模块类型ID
	 * @param       lLoanTypeID			   贷款类型子类ID
	 * @param       lActionID              操作ID
	 * @param       lOfficeID              办事处ID
	 * @param       lCurrencyID            币种ID
	 * @return      lApprovalID            返回审批设置信息
	 */
	public long getApprovalID(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID) throws Exception
	{
		return bean.getApprovalID(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID);
	}

	/**
	 * 查询可以做该审核的实际用户
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              模块类型ID
	 * @param       lLoanTypeID			   贷款类型子类ID
	 * @param       lActionID              操作ID
	 * @param       lOfficeID              办事处ID
	 * @param       lCurrencyID            币种ID
	 * @param       lUserID                用户标识
	 * @param       con                	   数据库连接
	 * @return      String                 返回可审核人员集合(逗号分割，直接用于SQL)
	 */
	public String findTheVeryUser(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID,long lUserID,Connection con) throws Exception
	{
		return bean.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lUserID,con);
	}
	
	/**
	 * 查询可以做该审核的实际用户
	 * 操作数据库表ApprovalSetting,ApprovalItem,ApprovalTracing
	 * @param       lModuleID              模块类型ID
	 * @param       lLoanTypeID			   贷款类型子类ID
	 * @param       lActionID              操作ID
	 * @param       lOfficeID              办事处ID
	 * @param       lCurrencyID            币种ID
	 * @param       lUserID                用户标识
	 * @return      String                 返回可审核人员集合(逗号分割，直接用于SQL)
	 */
	public String findTheVeryUser(long lModuleID,long lLoanTypeID,long lActionID,long lOfficeID,long lCurrencyID,long lUserID) throws Exception
	{
	    return bean.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lUserID);
	}
}
