/*
 * Created on 2004-3-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.util;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageCtrlInfo extends SECBaseDataEntity implements Serializable
{
	private long id 					= -1;			//id,暂时没用
	
	private String strNextPageURL 		= "";			//下一个跳转的页面
	
	private String originalPageURL		= "";			//初始页面URL,用于回跳到原始页面
	
	private String strSuccessPageURL 	= "";			//操作成功跳转到的页面
	private String strFailPageURL 		= "";			//操作失败跳转到的页面
	
	private String[] strCtrlPageURL		= null;			//控制页面
	private static int pagePointer		= 0;			//控制页面指针,到了哪个控制页面
	
	private String strAction 			= "";			//操作代码
	private String strActionResult		= "";			//操作结果
	
	private String accountOverDraftMsg	= "";			//帐户操作错误信息
	private long isCheckOverDraft		= SECConstant.TRUE;//是否检测帐户标志
	private String strPrintMsg			= "";			//打印信息
	
	public void success(){
		strActionResult = Constant.ActionResult.SUCCESS;
		this.nextURL();
		Log.print("下一个跳转的页面是:"+this.strNextPageURL);
	}
	public void fail(){
		strActionResult = Constant.ActionResult.FAIL;
		this.nextURL();
		Log.print("下一个跳转的页面是:"+this.strNextPageURL);
	}
	
	private void nextURL(){								//确定下一个跳转到的页面
		if (strActionResult == Constant.ActionResult.SUCCESS){
			pagePointer++;									//指针加一
			if (strCtrlPageURL != null && strCtrlPageURL.length>0){
				if (pagePointer == strCtrlPageURL.length){
					this.strNextPageURL = this.strSuccessPageURL;
					PageCtrlInfo.pagePointer = 0;
				}
				else{
					this.strNextPageURL = this.strCtrlPageURL[pagePointer];
				}
			}
			else{
				this.strNextPageURL = this.strSuccessPageURL;
				PageCtrlInfo.pagePointer = 0;
			}
		}
		else if(strActionResult == Constant.ActionResult.FAIL){
			this.strNextPageURL = this.strFailPageURL;
			PageCtrlInfo.pagePointer = 0;
		}
	}
	
	public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
		super.convertRequestToDataEntity(request);
		strCtrlPageURL = request.getParameterValues("strCtrlPageURL");			//控制页面
		
		if (strCtrlPageURL!=null){
			for (int n=0;n<strCtrlPageURL.length;n++){
				Log.print("下一个页面:"+strCtrlPageURL[n]);
			}
		}
		
		strCtrlPageURL = SECUtil.getNotNullStringArray(strCtrlPageURL);				//去除多余值
		//pagePointer = 0;														//置指针
	}
	
	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return Returns the strAction.
	 */
	public String getStrAction()
	{
		return strAction;
	}

	/**
	 * @param strAction The strAction to set.
	 */
	public void setStrAction(String strAction)
	{
		this.strAction = strAction;
	}

	/**
	 * @return Returns the strActionResult.
	 */
	public String getStrActionResult()
	{
		return strActionResult;
	}

	/**
	 * @param strActionResult The strActionResult to set.
	 */
	public void setStrActionResult(String strActionResult)
	{
		this.strActionResult = strActionResult;
	}

	/**
	 * @return Returns the strFailPageURL.
	 */
	public String getStrFailPageURL()
	{
		return strFailPageURL;
	}

	/**
	 * @param strFailPageURL The strFailPageURL to set.
	 */
	public void setStrFailPageURL(String strFailPageURL)
	{
		this.strFailPageURL = strFailPageURL;
	}

	/**
	 * @return Returns the strNextPageURL.
	 */
	public String getStrNextPageURL()
	{
		return strNextPageURL;
	}

	/**
	 * @param strNextPageURL The strNextPageURL to set.
	 */
	public void setStrNextPageURL(String strNextPageURL)
	{
		this.strNextPageURL = strNextPageURL;
	}

	/**
	 * @return Returns the strPrintMsg.
	 */
	public String getStrPrintMsg()
	{
		return strPrintMsg;
	}

	/**
	 * @param strPrintMsg The strPrintMsg to set.
	 */
	public void setStrPrintMsg(String strPrintMsg)
	{
		this.strPrintMsg = strPrintMsg;
	}

	/**
	 * @return Returns the strSuccessPageURL.
	 */
	public String getStrSuccessPageURL()
	{
		return strSuccessPageURL;
	}

	/**
	 * @param strSuccessPageURL The strSuccessPageURL to set.
	 */
	public void setStrSuccessPageURL(String strSuccessPageURL)
	{
		this.strSuccessPageURL = strSuccessPageURL;
	}

	/**
	 * @return Returns the accountOverDraftMsg.
	 */
	public String getAccountOverDraftMsg()
	{
		return accountOverDraftMsg;
	}

	/**
	 * @param accountOverDraftMsg The accountOverDraftMsg to set.
	 */
	public void setAccountOverDraftMsg(String accountOverDraftMsg)
	{
		this.accountOverDraftMsg = accountOverDraftMsg;
		/**
		 * 如果帐户报错信息不为空,说明发生帐户异常,则把检测帐户标志设置为不检测
		 */
		if (accountOverDraftMsg!=null && accountOverDraftMsg.length()>0){
			isCheckOverDraft = SECConstant.FALSE;
		}
	}

	/**
	 * @return Returns the originalPageURL.
	 */
	public String getOriginalPageURL()
	{
		return originalPageURL;
	}

	/**
	 * @param originalPageURL The originalPageURL to set.
	 */
	public void setOriginalPageURL(String originalPageURL)
	{
		this.originalPageURL = originalPageURL;
	}

	/**
	 * @return Returns the isCheckOverDraft.
	 */
	public long getIsCheckOverDraft()
	{
		return isCheckOverDraft;
	}

	/**
	 * @param isCheckOverDraft The isCheckOverDraft to set.
	 */
	public void setIsCheckOverDraft(long isCheckOverDraft)
	{
		this.isCheckOverDraft = isCheckOverDraft;
	}

}
