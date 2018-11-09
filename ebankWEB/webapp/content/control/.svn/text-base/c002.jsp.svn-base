<%--
 页面名称 ：c461.jsp
 页面功能 : 新增更新贷款利率设置控制页面
 作    者 ：jinchen
 日    期 ：2004-09-19
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="	java.sql.*,
                    java.rmi.RemoteException,
					com.iss.itreasury.loan.contractcontent.bizlogic.*,
					com.iss.itreasury.loan.util.LOANHTML,
					com.iss.itreasury.loan.contractcontent.dao.ContractContentDao,
					com.iss.itreasury.loan.contractcontent.dataentity.*,
					com.iss.itreasury.util.*,
					com.iss.itreasury.ebank.util.*,
                    java.util.*"%>				   
   

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

	/**
	 * 定义业务dataentity
	 */
	ContractContentQueryInfo qInfo = new ContractContentQueryInfo();
	
	
try
{	
	
	/**
		* isLogin start
		*/
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	
	/**
	 * 从request中获取页面控制参数
	 */
	String strTemp = "";
	
	strTemp = (String)request.getAttribute("strSourcePage");
	if (strTemp != null && strTemp.trim().length()>0){					//来源页面
		strSourcePage = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strSuccessPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//成功页面
		strSuccessPageURL = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strFailPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//失败页面
		strFailPageURL = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strAction");
	if (strTemp != null && strTemp.trim().length() > 0){				//操作代码
		strAction = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strParentPageURL");
	if (strTemp != null && strTemp.trim().length()>0){					//父页面
		strParentPageURL = strTemp.trim();
	}
		
	
	/**
	 * 获得提交变量
	 */

	//定义变量
 
 
	long lID=-1;
	long lResult = -1;

	long lTypeId = -1;
	String strYear="";
	String strSeason="";
	String strClientName="";
	String strControl="";
	long lParentID = -1;
	long lContentID = -1; //合同文本的ID
	String PageName = "";
	String prePageName = "";
	long PageNo = 1;
	String strYearNo = "";
	
	String sAction = "";
	

	//变量赋值




	
	strTemp = (String)request.getAttribute("hdnlClientID");
	if (strTemp != null && !strTemp.equals(""))
	{
			lParentID = Long.parseLong(strTemp.trim());	
	}
	strTemp = (String)request.getAttribute("lContentID");
	if (strTemp != null && !strTemp.equals(""))
	{
			lContentID = Long.parseLong(strTemp);
	}
	
	strTemp = (String)request.getAttribute("PageName");
	if (strTemp != null && !strTemp.equals(""))
	{
			PageName = strTemp;
	}
	
	strTemp = (String)request.getAttribute("prePageName");
	if (strTemp != null && !strTemp.equals(""))
	{
			prePageName = strTemp;
	}
	
	strTemp = (String)request.getAttribute("PageNo");
	if (strTemp != null && !strTemp.equals(""))
	{
			PageNo = Long.parseLong(strTemp);
	}
	strTemp = (String)request.getAttribute("hdnstrYear");		//年份
	if (strTemp != null && !strTemp.equals(""))
	{
		strYearNo = strTemp;
	}
	
	strTemp = (String)request.getAttribute("hdnstrSeason");		//季度
	if (strTemp != null && !strTemp.equals(""))
	{
		strSeason = strTemp;
	}
	
	strTemp = (String)request.getAttribute("hdnClientName");
	if (strTemp != null && strTemp.trim().length() > 0){				//
		strClientName = strTemp.trim();
		
		
	}

	

	strTemp = (String)request.getAttribute("SUBMIT");
	if (strTemp != null && !strTemp.equals(""))
	{	
			sAction = strTemp;
	}

	System.out.println("##################################\t"+lTypeId);
	strTemp = (String)request.getAttribute("hdnTypeId");
	if (strTemp != null && strTemp.trim().length() > 0){				//利率类型编号
		lTypeId = Long.parseLong(strTemp.trim());
		qInfo.setDocType(lTypeId);
		
	}
	strTemp = (String)request.getAttribute("hdnlClientID");
	if (strTemp != null && strTemp.trim().length() > 0){				//
		lParentID = Long.parseLong(strTemp.trim());
		
	}

	
	strTemp = (String)request.getAttribute("hdnControl");
	if (strTemp != null && strTemp.trim().length() > 0){				//
		strControl = strTemp.trim();
		
	}
	


	long lThisNo = PageNo;
	boolean bIsSave = false;
	
	String strContent = ""+lParentID+ContractContentDao.CONTENT_SEPERATOR;
	String arrContent[] = null;
	if ( request.getParameterValues("textfield") != null )
	{
		arrContent = request.getParameterValues("textfield");
		int nLen = arrContent.length;
		
		for (int i = 0; i < nLen; i++)
		{
		
			if(arrContent[i]!=null)
				arrContent[i] = DataFormat.toChinese(arrContent[i]);
		
			if (i < nLen - 1)
			{
				strContent = strContent + arrContent[i] + ContractContentDao.CONTENT_SEPERATOR;
			}
			else
			{
				strContent = strContent + arrContent[i]+ ContractContentDao.CONTENT_SEPERATOR+lParentID;
			}
		}
	}
	else
	{
		bIsSave = false;
	}
	
	String sName = "";
	if (sAction.equals("presave"))
	{
		bIsSave = true;
		sName = prePageName;
		PageNo = PageNo - 1;
	}
	else if (sAction.equals("pre"))
	{
		bIsSave = false;
		sName = prePageName;
		PageNo = PageNo - 1;
	}
	else if (sAction.equals("close"))
	{
		bIsSave = true;
		sName = "";
	}
	else if (sAction.equals("save"))
	{
		bIsSave = true;
		sName = PageName;
		PageNo = PageNo + 1;
	}
	
	

	




	/**
	 * 声明Delegation
	 */
	
		ContractContentOperation delegation = new ContractContentOperation();

	

	/**
	 * 根据操作代码进行操作
	 */

	if (bIsSave)
	{
		
		System.out.println("contentId\t"+lContentID+"parentId\t"+lParentID);
		ContractContentOperation operation = new ContractContentOperation();
		if(lContentID > 0)//修改
		{
			operation.putContractContent(lContentID,1,strContent);
		}
		else if(lParentID > 0 && lContentID <= 0)//新增
		{
			//ContractContentDao dao = new ContractContentDao();
			String ss = operation.addClientContent(lParentID);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\t 到这里"+ss);
			ContractContentInfo info = new ContractContentInfo();
			info.setParentID(lParentID);
			info.setContractID(-1);
			info.setContractTypeID(lTypeId);
			info.setDocName(ss);
			info.setCode(strYearNo+strSeason);

			//lContentID=dao.saveContractContent(info);
			lContentID=operation.saveContractContent(info);

			operation.putContractContent(lContentID,1,strContent);
		}
	}


	
		
		request.setAttribute("lTypeId",lTypeId+"");
		request.setAttribute("lClientID",lParentID+"");
		request.setAttribute("strClientName",strClientName);
		request.setAttribute("control",strControl);
		Log.print("新增利率成功-----------------------------------------------------------------"+strClientName);
		strActionResult = Constant.ActionResult.SUCCESS;
		Log.print("新增利率成功-----------------------------------------------------------------");
		
	



}catch( Exception exp ){


	
	
	exp.printStackTrace();
	/**
	 * 出现异常,添加报错信息
	 */
	sessionMng.getActionMessages().addMessage(exp); 
	/**
	 * 出现异常,操作结果置为失败
	 */
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