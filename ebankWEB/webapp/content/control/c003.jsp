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
<%@page import="	java.sql.*,
                    java.rmi.RemoteException,
					com.iss.itreasury.loan.contractcontent.bizlogic.*,
					com.iss.itreasury.loan.util.LOANHTML,
					com.iss.itreasury.loan.contractcontent.dao.ContractContentDao,
					com.iss.itreasury.loan.contractcontent.dataentity.*,
					com.iss.itreasury.util.*,
					com.iss.itreasury.ebank.util.*,
                    java.util.*"%>				   
   
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
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
	 String strCode="";
	 long lClientID = -1;
	 String strControl="";
	
	String strClientName ="";
	long lParentID = -1;
	long lContentID = -1; //合同文本的ID
	String PageName = "";
	String prePageName = "";
	long PageNo = 1;
	String strYearNo = "";
	String sAction = "";
	 
	
	//变量赋值

	 strTemp = (String)request.getAttribute("strYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//年份
			strYear = strTemp.trim();
			qInfo.setYear(strYear);
		}
	
	 strTemp = (String)request.getAttribute("strSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//季度
			strSeason = strTemp.trim();
			qInfo.setSeason(strSeason);
		}

     strTemp = (String)request.getAttribute("hdnTypeId");
		if (strTemp != null && strTemp.trim().length() > 0){				//类型
			lTypeId = Long.parseLong(strTemp.trim());
			qInfo.setDocType(lTypeId);
			
		}
	
	  strTemp = (String)request.getAttribute("hdnlClientID");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			lClientID = Long.parseLong(strTemp.trim());
			qInfo.setParentId(lClientID);
		}
		strTemp = (String)request.getAttribute("hdnlContentID");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			lContentID = Long.parseLong(strTemp.trim());
			
		}
		
		strTemp = (String)request.getAttribute("hdnControl");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			strControl = strTemp.trim();		
		}


		
		strTemp = (String)request.getAttribute("strClientName");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			strClientName = strTemp.trim();
			
			
		}
	

	




	/**
	 * 声明Delegation
	 */
	
	ContractContentOperation delegation = new ContractContentOperation();

	

	/**
	 * 根据操作代码进行操作
	 */
	if (strAction.equals("add")){			//创建保存
		
		Log.print("add@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		lContentID  = delegation.getContractContentID(qInfo);


		if(lContentID > 0 )
			{
				ContractContentOperation operation = new ContractContentOperation();
				String sContent = "";
				PageNo=1;
				try
				{
					sContent = operation.getContractContent(lContentID,PageNo);
				}
				catch(IException e)
				{
					//LOANHTML.showExceptionMessage(out, sessionMng, e, request, response, "财务情况统计表", Constant.RecordStatus.VALID);
					//LOANHTML.showMessage(out,sessionMng,request,response,"财务情况统计表",Constant.RecordStatus.VALID,"Loan_E010");
					//e.printStackTrace();
					out.flush();
					return;
				}
				request.setAttribute("lClientID", String.valueOf(lParentID));
				request.setAttribute("sContent",sContent);
				request.setAttribute("lContentID",String.valueOf(lContentID));
			}
			request.setAttribute("year",strYear);
			request.setAttribute("season",strSeason);
			request.setAttribute("lTypeId",lTypeId+"");
			request.setAttribute("lClientID",lClientID+"");
			request.setAttribute("strClientName",strClientName);
			request.setAttribute("control",strControl);
			strActionResult = Constant.ActionResult.SUCCESS;
	}	
	






	else if (strAction.equals("modify")){			//修改保存
		
		Log.print("修改@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
			/**
		 * 赋值dataentity
		 */
		

		
	
		
			if(lContentID > 0 )
			{
				ContractContentOperation operation = new ContractContentOperation();
				String sContent = "";
				PageNo=1;
				try
				{
					sContent = operation.getContractContent(lContentID,PageNo);
				}
				catch(IException e)
				{
					//LOANHTML.showExceptionMessage(out, sessionMng, e, request, response, "财务情况统计表", Constant.RecordStatus.VALID);
					//LOANHTML.showMessage(out,sessionMng,request,response,"财务情况统计表",Constant.RecordStatus.VALID,"Loan_E010");
					//e.printStackTrace();
					out.flush();
					return;
				}
				request.setAttribute("lClientID", String.valueOf(lParentID));
				request.setAttribute("sContent",sContent);
				request.setAttribute("lContentID",String.valueOf(lContentID));

				
			}
			

			request.setAttribute("year",strYear);
			request.setAttribute("season",strSeason);
			request.setAttribute("lTypeId",lTypeId+"");
			request.setAttribute("lClientID",lClientID+"");
			request.setAttribute("strClientName",strClientName);
			request.setAttribute("control",strControl);
			strActionResult = Constant.ActionResult.SUCCESS;
	}
		



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