<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page
	import="java.util.*"%>
	 <%@ page import="com.iss.itreasury.util.Env" %>
	

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 java.rmi.*,
                 java.sql.*,
                 com.iss.itreasury.settlement.util.NameRef,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
                 com.iss.itreasury.ebank.bizdelegation.*"
%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%String strContext = request.getContextPath();%>

<%!/* 标题固定变量 */
	String strTitle = "[批量复核]";%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* 实例化信息类 */
	//实体
	FinanceInfo info = new FinanceInfo();
	OBFinanceInstrDao obstr = new OBFinanceInstrDao();//查询方法
	List infoList = null;
	long lID = -1;//指令id
	String[] lID1 = new String []{};//指令id数组
	int i = -1;//数组下标控制
	long lRet = -1 ;//调用check()的反回值
	TransInfo transinfo = new TransInfo();
	String sign ="";
	//查询类

	/* 用户登录检测与权限校验及文件头显示 */
	try {
		//用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);	
%>
<%
		lID1 =  request.getParameterValues("ccheck");
		System.out.println("++++++++++++复核的id++++++++"+lID1);
		i = lID1.length;//提交的个数
		System.out.println("提交指令的个数："+ i);
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		String strTemp = new String();//返回信息
		String strTemp1 = new String();//返回信息
		Vector vcResult = new Vector(1);//返回信息
		System.out.println("开始批量复合");
		FinanceInfo financeinfo =null;
		Timestamp dtmodify = null;
		if(request.getParameter("sign")!= null&&request.getParameter("sign").trim().length()>0)
		{
		sign = request.getParameter("sign");
		}
		for(int j=0;j<i;j++)
		{
			System.out.println("开始复核");	
			financeinfo = new FinanceInfo();
			lID = Long.parseLong(lID1[j].split("####")[0]);
			if(request.getParameter("dtmodify")!=null){
		    	dtmodify =DataFormat.getDateTime(request.getParameter("dtmodify"));
		}
			if(lID1[j].split("####")[2]!=null){
				dtmodify = DataFormat.getDateTime(lID1[j].split("####")[2]);
			}
			financeinfo.setID(lID);
			financeinfo.setDtModify(dtmodify);	
			financeinfo.setCheckUserID(sessionMng.m_lUserID);
			financeinfo.setCurrencyID(sessionMng.m_lCurrencyID);
			System.out.println("===================复核的id："+lID);
			//开始复合，返回值大于0为复合成功，小于零为失败	
			try
			{						
				lRet = financeInstr.check(financeinfo);	
				transinfo.setStatus(Constant.SUCCESSFUL);
				transinfo.setActionType(Constant.TransLogActionType.check);	
			}catch(Exception ex)
			{
				transinfo.setStatus(Constant.FAIL);
				transinfo.setActionType(Constant.TransLogActionType.check);
				ex.printStackTrace();
				throw new IException(ex.getMessage());
			}
			finally
			{
				if(transinfo.getStatus()!=-1)
				{
					TranslogBiz translofbiz= new TranslogBiz();
					transinfo.setHostip(request.getRemoteAddr());
					transinfo.setHostname(request.getRemoteHost());
					FinanceInfo financeInfo = financeInstr.findByID(lID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
					transinfo.setTransType(financeInfo.getTransType());
					translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
				
				}
			}
			if(obstr.getSignUserID(lID)> 0 ){
					strTemp =" 这笔业务现为已复核状态，需要签认后才能提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";					
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为： "+lID+" </b><br>";
					vcResult.add(strTemp);
				}
		   else{
						strTemp = " 这笔业务现为已复核状态，已经提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
						vcResult.add(strTemp);		
						strTemp = "<b>指令序号为： "+lID+" </b><br>";
				
					vcResult.add(strTemp);		
					}
				
		}
	
		request.setAttribute("return",vcResult);
		//结果跳转
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext +"/capital/check/view/check_v006.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}	
		
 %>
