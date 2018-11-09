/*
 * Created on 2004-3-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.util;


import java.util.ArrayList;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.ITreasuryException;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageCtrlInfo extends ITreasuryBaseDataEntity
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
	
	//------控制量
	private String strPrintMsg			= "";			//打印信息
	
	/**
	 * 当某一个c层操作成功完成时,调用此方法,会获得下一个成功跳转的页URL
	 * 
	 */
	public void success(){
		strActionResult = Constant.ActionResult.SUCCESS;
		this.nextURL();
		Log.print("下一个跳转的页面是:"+this.strNextPageURL);
	}
	/**
	 * 当某一个c层的操作抛出异常时,调用此方法,会获得操作失败应该跳转到的URL
	 *
	 */
	public void fail(){
		strActionResult = Constant.ActionResult.FAIL;
		this.nextURL();
		Log.print("下一个跳转的页面是:"+this.strNextPageURL);
	}
	
	/**
	 * 获得下一个跳转页面的URL,
	 * 如果pagePointer的值小于strCtrlPageURL数组的大小,
	 * 则指针加一,返回下一个控制页面的URL
	 * 如果pagePointer已经等于strCtrlPageURL数组的大小了,则返回成功页面的URL
	 * 在这期间,每次获得链接时都会检测strActionResult,
	 * 如果它为Constant.ActionResult.FAIL,则不关当前页面是哪个,
	 * 下一个页面都是失败页面URL
	 */
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
	
	/**
	 * 重载convertRequesttoDataEntity方法,用来获得以数组形势表现的strCtrlPageURL
	 */
	public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
		super.convertRequestToDataEntity(request);
		strCtrlPageURL = request.getParameterValues("strCtrlPageURL");			//控制页面
		
		strCtrlPageURL = getNotNullStringArray(strCtrlPageURL);					//去除多余值
	}

	/**
	 * 得到没有空串的字符型数组
	 * 如果字符数组中有空字符串或null字符串,则数组缩小一
	 * @param strParams
	 * @return
	 */
	public static String[] getNotNullStringArray(String[] strParams){
		String[] str = null;
		ArrayList al = new ArrayList();
		if (strParams != null){
			for (int n=0;n<strParams.length;n++){
				if (strParams[n] != null && strParams[n].trim().length()>0){
					al.add(strParams[n]);
				}
			}
			
			str = new String[al.size()];
			for (int n=0;n<al.size();n++){
				str[n] = (String)al.get(n);
			}
		}
		
		return str;
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

}
