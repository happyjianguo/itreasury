<%--
 页面名称 ：S521.jsp
 页面功能 : 更新密码
 作    者 ：liangpan
 日    期 ：2007-06-28
 特殊说明 ：
 修改历史 ：
 修改说明 :
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@page import="com.iss.itreasury.encrypt.EncryptManager"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="com.iss.itreasury.util.*,
				com.iss.itreasury.system.bizlogic.UserBiz,
				com.iss.itreasury.system.util.SYSHTML,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.ebank.util.*,
				java.util.*"
				
%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.pwconfig.dataentity.*"%>
<%
	/**
	 * 页面控制变量
	 */
	String strSourcePage 		= "";		//来源页面
    String strNextPageURL 		= "";		//下一个跳转的页面
	String strSuccessPageURL 	= "";		//操作成功跳转到的页面
	String strFailPageURL 		= "";		//操作失败跳转到的页面
	String strAction 			= "";		//操作代码
	String strActionResult		= "";		//操作结果
	String strParentPageURL		= "";		//父页面
	String strTemp = null;
	try
	{	
	strTemp = (String)request.getParameter("strSourcePage");
	if (strTemp != null && strTemp.trim().length()>0){					//来源页面
		strSourcePage = strTemp.trim();
	}
	strTemp = (String)request.getParameter("strSuccessPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//成功页面
		strSuccessPageURL = strTemp.trim();
	}
	strTemp = (String)request.getParameter("strFailPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//失败页面
		strFailPageURL = strTemp.trim();
	}
	strTemp = (String)request.getParameter("strAction");
	if (strTemp != null && strTemp.trim().length() > 0){				//操作代码
		strAction = strTemp.trim();
	}
	strTemp = (String)request.getParameter("strParentPageURL");
	if (strTemp != null && strTemp.trim().length()>0){					//父页面
		strParentPageURL = strTemp.trim();
	}
	/**
	 * 从request中取出页面相关的变量
	 */
	long userId = -1;
	String password = "";
	String strLogin = "";
	String oldPassword = "";
	String oldPassword2 = "";
	
	strTemp = (String)request.getParameter("txtNewPW");
	if (strTemp != null && strTemp.trim().length()>0){					//密码
		password = strTemp.trim();
	}
	
	strTemp = (String)request.getParameter("txtOldPW");
	if (strTemp != null && strTemp.trim().length()>0){					//原密码
		oldPassword = strTemp.trim();
	}
	
	userId = sessionMng.m_lUserID;
	strLogin = sessionMng.m_strLogin;
	
	//加密校验
    if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
    {
    	if(EncryptManager.getOBBeanFactory().checkOBPassword(strLogin, oldPassword))
    	{
    		EncryptManager.getOBBeanFactory().changeOBUserPassword(userId, password);
    		
    		//成功完成设置
			strActionResult = Constant.ActionResult.SUCCESS;
    		sessionMng.getActionMessages().addMessage("密码修改成功");
    	}
    	else
    	{
    		strActionResult = Constant.ActionResult.FAIL;
    		strFailPageURL = "ChangePassword.jsp";
    		sessionMng.getActionMessages().addMessage("原密码错误");
    	}
    }
    else
    {
    	PWConfigBean bizPW = new PWConfigBean();
		oldPassword2 = (String)(bizPW.getOBUserPwdInfo(sessionMng.m_lUserID).get(0));
		
		if(oldPassword2.equals(oldPassword))
        {
			bizPW.changeUserPassword(userId,password);
			
			//成功完成设置
			strActionResult = Constant.ActionResult.SUCCESS;
			sessionMng.getActionMessages().addMessage("密码修改成功");
		}
		else
		{
    		strActionResult = Constant.ActionResult.FAIL;
    		strFailPageURL = "ChangePassword.jsp";
    		sessionMng.getActionMessages().addMessage("原密码错误");
		}
	}
    
	}catch(IException e)
	{
		e.printStackTrace();
		//  出现异常,添加报错信息
		sessionMng.getActionMessages().addMessage(e.getMessage()); 
		strActionResult = Constant.ActionResult.FAIL;
	}
	/**
	 * 将操作结果置入request中
	 */
	request.setAttribute("strActionResult",strActionResult);

	
	/**
	 * 根据处理结果设置下一步跳转的目标页面
	 */
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult)) ? strSuccessPageURL : strFailPageURL;

	/** 
	 * 转向下一页面 
	 **/
	Log.print("Next Page URL:" + strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>