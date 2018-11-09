<%--
/*
 * 程序名称：C821.jsp
 * 功能说明：系统管理-收款方资料新增控制页面
 * 作　　者：刘琰
 * 完成日期：2003年10月20日
 */
--%>

<!--Header start-->
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.obsystem.bizlogic.*,com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.NameRef,java.rmi.*,java.lang.*,com.iss.itreasury.util.*,java.sql.*"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>

<%
	String strTitle = "[收款方资料维护--新增收款方资料]";
	/* 用户登录检测与权限校验 */
	try {
		/* 用户登录检测 */
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		//判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
	} catch (Exception e) {
		OBHtml.showExceptionMessage(out, sessionMng, (IException) e,
		strTitle, "", 1);
		return;
	}
%>


<!--Get Data start-->
<%
	String sNextPage = "";
	if (request.getParameter("sAddFlag") != null
			&& request.getParameter("sAddFlag").equals("1")) {
		sNextPage = strContext + "/system/V820.jsp";
	}

	try {
		ClientCapInfo clientCapInfo = new ClientCapInfo();

		//接收是否中油用户的参数据
		String strISCPF = GetParam(request, "txtIsCPF").trim();

		//从请求中获取数据
		if (strISCPF.equalsIgnoreCase("true")) {

			clientCapInfo.setPayeeAccoutNO(NameRef.setAccontLine(GetParam(request, "txtPayeeBankNO").trim()));//收款方账号
			clientCapInfo.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_YES);//是中油财务内部账户，
			clientCapInfo.setPayeeBankName(Env.getClientName());//汇入行名称，固定

		} else {
			clientCapInfo
			.setPayeeName(GetParam(request, "txtPayeeName")
			.trim());
			clientCapInfo
			.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);//不是中油财务内部账户
			clientCapInfo.setPayeeAccoutNO(GetParam(request,
			"txtPayeeAccoutNO").trim());
			clientCapInfo.setPayeeBankName(GetParam(request,
			"txtPayeeBankName"));//汇入行名称
			clientCapInfo
			.setPayeeProv(GetParam(request, "txtPayeeProv")
			.trim());//txtPayeeProv
			clientCapInfo.setCity(GetParam(request, "txtCity").trim());//txtCity
			clientCapInfo.setSPayeeBankCNAPSNO(GetParam(request, "txtPayeeBankCNAPSNO").trim());
			clientCapInfo.setSPayeeBankExchangeNO(GetParam(request, "txtPayeeBankExchangeNO").trim());
			clientCapInfo.setSPayeeBankOrgNO(GetParam(request, "txtPayeeBankOrgNO").trim());
		}
		//从session中取值		
		clientCapInfo.setClientID(sessionMng.m_lClientID);//付款方客户ID ，session 客户ID
		clientCapInfo.setCurrencyID(sessionMng.m_lCurrencyID);//币种，session 币种
		clientCapInfo.setInputUserID(sessionMng.m_lUserID);//录入人，session中取值
		clientCapInfo.setLofficeid(sessionMng.m_lOfficeID); //办事处
		//初始化ejb
		OBSystemHome obsystemhome = null;//定义本地接口
		OBSystem obsystem = null;//定义远程接口
		obsystemhome = (OBSystemHome) EJBObject
		.getEJBHome("OBSystemHome");
		obsystem = obsystemhome.create();

		//定义区分新增修改
		int state = 1;

		//定义流水号
		long lID = -1;
		String strID = (String) request.getParameter("txtIDCheckbox");

		if ((strID != null) && (strID.length() > 0)) {
			clientCapInfo.setID(Long.parseLong(strID));
			Log.print("流水号:" + clientCapInfo.getID());
		}

		if (clientCapInfo.getID() < 0) {
			//如果收款方名称，账号和数据库的记录有重复的话，则修改
			lID = obsystem.addPayee(clientCapInfo);
			Log.print("收款方资料Add");
			clientCapInfo.setID(lID);

			if (strISCPF.equalsIgnoreCase("true")) {
		ClientCapInfo info1 = null;
		info1 = obsystem.findAccount(sessionMng.m_lClientID,
				sessionMng.m_lCurrencyID, clientCapInfo
				.getPayeeAccoutNO(),
				sessionMng.m_lOfficeID);
		clientCapInfo.setPayeeName(info1.getPayeeName());
			}
		} else {
			state = 2;
			lID = obsystem.updatePayee(clientCapInfo);
		  //  lID = obsystem.addPayee(clientCapInfo);
			Log.print("收款方资料Update");
			if (strISCPF.equalsIgnoreCase("true")) {
		ClientCapInfo info1 = null;
		info1 = obsystem.findAccount(sessionMng.m_lClientID,
				sessionMng.m_lCurrencyID, clientCapInfo
				.getPayeeAccoutNO(),
				sessionMng.m_lOfficeID);
		clientCapInfo.setPayeeName(info1.getPayeeName());
			}
		}

		//在请求中保存结果对象
		if (clientCapInfo != null) {
			request.setAttribute("clientInfo", clientCapInfo);
		}
		//获取上下文环境
		//ServletContext sc = getServletContext();
		//设置返回地址
		RequestDispatcher rd = null;
		if (strISCPF.equalsIgnoreCase("true")) {
			if (state == 2) {/*****我的2次传值，区分直接查找和保存到查找页面**********/
				String flag = (String) request.getParameter("flag");
				if (flag != null && flag.equals("saved")) {
					request.setAttribute("BCCG", "success");
				}
							/***************/
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/C823.jsp");
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
			else {
				Log.print("从V824.jsp走");
				request.setAttribute("success", "success");
				
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/C823.jsp");
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
		} else {
			if (state == 2) {/*****我的2次传值，区分直接查找和保存到查找页面**********/
				String flag = (String) request.getParameter("flag");
				if (flag != null && flag.equals("saved")) {
					request.setAttribute("BCCG", "success");
				}
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/V834.jsp");
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			} 
			else {
				Log.print("从V834.jsp走");
				request.setAttribute("success", "success");
	
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + "/system/V834.jsp");
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			}
		}
		
		 /*
		 //add by hyzeng 2003-4-11 如果资料已存在，应该提示“收款方资料已存在”
		 if  ( clientCapInfo.getID() == -2 )
		 {
		 Log.print("从V820.jsp走");
			//构建页面分发时需要用到的实体
			PageControllerInfo pageControllerInfo = new PageControllerInfo();
			pageControllerInfo.setSessionMng(sessionMng);
			pageControllerInfo.setUrl(strContext + "/system/V820.jsp?sDuplicate=1&txtIsCPF="+strISCPF);
			//分发
			rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		 }
		 */
		 
		//forward到结果页面
		rd.forward(request, response);
	} catch (IException ie) {
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
		1);
		return;
	}
%>
