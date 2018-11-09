package com.iss.itreasury.util;

import java.util.ArrayList;

import javax.servlet.ServletRequest;

import com.iss.itreasury.ebank.util.SessionOB;

/**
 * 
 * @author mzfu
 * 
 * Modify by leiyang date 2007/07/31
 *
 */
public class PageControllerInfo extends BaseBean {
	
	private String p_NextPageURL = "";                  //下一个跳转的页面
	private String p_OriginalPageURL = "";              //初始页面URL,用于回跳到原始页面
	private String p_SuccessPageURL = "";               //操作成功跳转到的页面
	private String p_FailPageURL = "";                  //操作失败跳转到的页面

	private String[] p_CtrlPageURL = null;              //控制页面
	private static int p_PagePointer = 0;               //控制页面指针,到了哪个控制页面

	private String p_Action = "";                       //操作代码
	private long p_ActionSuccess = 0;  //操作结果

	private String url = "";
	//private SessionMng sessionMng = null;
	private long modelId = -1;
	
	/*public SessionMng getSessionMng() {
	return sessionMng;
	}*/
	public PageControllerInfo(SessionOB sessionMng, String url){
		this.setModelId(sessionMng.m_lModuleID);	
		this.url = url;		
	}
	public PageControllerInfo(){
		
	}
	
	/**
	 * 当某一个c层操作成功完成时,调用此方法,会获得下一个成功跳转的页URL
	 */
	public void success()
	{
		p_ActionSuccess = 1;
		this.nextURL();
		System.out.println("下一个跳转的页面是:" + this.p_NextPageURL);
	}

	/**
	 * 当某一个c层的操作抛出异常时,调用此方法,会获得操作失败应该跳转到的URL
	 */
	public void fail()
	{
		p_ActionSuccess = 0;
		this.nextURL();
		System.out.println("下一个跳转的页面是:" + this.p_NextPageURL);
	}
	
	/**
	 * 获得下一个跳转页面的URL, 如果pagePointer的值小于strCtrlPageURL数组的大小, 则指针加一,返回下一个控制页面的URL
	 * 如果pagePointer已经等于strCtrlPageURL数组的大小了,则返回成功页面的URL
	 * 在这期间,每次获得链接时都会检测strActionResult,
	 * 如果它为Constant.ActionResult.FAIL,则不关当前页面是哪个, 下一个页面都是失败页面URL
	 */
	private void nextURL()
	{ //确定下一个跳转到的页面
		if (p_ActionSuccess == 1)
		{
			p_PagePointer++; //指针加一
			if (p_CtrlPageURL != null && p_CtrlPageURL.length > 0)
			{
				if (p_PagePointer == p_CtrlPageURL.length)
				{
					this.p_NextPageURL = this.p_SuccessPageURL;
					PageControllerInfo.p_PagePointer = 0;
				}
				else
				{
					this.p_NextPageURL = this.p_CtrlPageURL[p_PagePointer];
				}
			}
			else
			{
				this.p_NextPageURL = this.p_SuccessPageURL;
				PageControllerInfo.p_PagePointer = 0;
			}
		}
		else if (p_ActionSuccess == 0)
		{
			this.p_NextPageURL = this.p_FailPageURL;
			PageControllerInfo.p_PagePointer = 0;
		}
	}

	
	/**
	 * @return Returns the action.
	 */
	public String getP_Action()
	{
		return p_Action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setP_Action(String action)
	{
		this.p_Action = action;
	}
	
	/**
	 * 得到没有空串的字符型数组 如果字符数组中有空字符串或null字符串,则数组缩小一
	 * 
	 * @param strParams
	 * @return
	 */
	public static String[] trimNull(String[] strParams)
	{
		String[] str = null;
		ArrayList al = new ArrayList();
		if (strParams != null)
		{
			for (int n = 0; n < strParams.length; n++)
			{
				if (strParams[n] != null && strParams[n].trim().length() > 0)
				{
					al.add(strParams[n]);
				}
			}

			str = new String[al.size()];
			for (int n = 0; n < al.size(); n++)
			{
				str[n] = (String) al.get(n);
			}
		}

		return str;
	}
	/**
	 * 重载convertRequesttoDataEntity方法,用来获得以数组形势表现的ctrlPageURL
	 */
	public void convertRequestToDataEntity(ServletRequest request)
			throws Exception
	{
		super.convertRequestToDataEntity(request);
		p_CtrlPageURL = request.getParameterValues("ctrlPageURL"); //控制页面

		p_CtrlPageURL = trimNull(p_CtrlPageURL); //去除多余值
	}
	/**
	 * @return Returns the actionSuccess.
	 */
	public long getP_ActionSuccess()
	{
		return p_ActionSuccess;
	}
	/**
	 * @param actionSuccess The actionSuccess to set.
	 */
	public void setP_ActionSuccess(long actionSuccess)
	{
		this.p_ActionSuccess = actionSuccess;
	}
	/**
	 * @return Returns the ctrlPageURL.
	 */
	public String[] getP_CtrlPageURL()
	{
		return p_CtrlPageURL;
	}
	/**
	 * @param ctrlPageURL The ctrlPageURL to set.
	 */
	public void setP_CtrlPageURL(String[] ctrlPageURL)
	{
		this.p_CtrlPageURL = ctrlPageURL;
	}
	/**
	 * @return Returns the failPageURL.
	 */
	public String getP_FailPageURL()
	{
		return p_FailPageURL;
	}
	/**
	 * @param failPageURL The failPageURL to set.
	 */
	public void setP_FailPageURL(String failPageURL)
	{
		this.p_FailPageURL = failPageURL;
	}
	/**
	 * @return Returns the nextPageURL.
	 */
	public String getP_NextPageURL()
	{
		return p_NextPageURL;
	}
	/**
	 * @param nextPageURL The nextPageURL to set.
	 */
	public void setP_NextPageURL(String nextPageURL)
	{
		this.p_NextPageURL = nextPageURL;
	}
	/**
	 * @return Returns the originalPageURL.
	 */
	public String getP_OriginalPageURL()
	{
		return p_OriginalPageURL;
	}
	/**
	 * @param originalPageURL The originalPageURL to set.
	 */
	public void setP_OriginalPageURL(String originalPageURL)
	{
		this.p_OriginalPageURL = originalPageURL;
	}
	/**
	 * @return Returns the successPageURL.
	 */
	public String getP_SuccessPageURL()
	{
		return p_SuccessPageURL;
	}
	/**
	 * @param successPageURL The successPageURL to set.
	 */
	public void setP_SuccessPageURL(String successPageURL)
	{
		this.p_SuccessPageURL = successPageURL;
	}
	public void setSessionMng(SessionMng sessionMng) {
		this.setModelId(sessionMng.m_lModuleID);
	}
	
	public void setSessionMng(SessionOB sessionMng){
		this.setModelId(sessionMng.m_lModuleID);
	}
	
	public long getModelId() {
		return modelId;
	}
	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
