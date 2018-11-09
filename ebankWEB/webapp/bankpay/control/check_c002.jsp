<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.budget.util.*,
                 com.iss.itreasury.budget.executecontrol.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%!
	/* 标题固定变量 */
	String strTitle="[银行汇款]";
	
%>
<%
    Vector vcResult = new Vector(1);//返回信息
 	long lID = -1;//指令id
	String[] lID1 = new String []{};//指令id数组
	int i = -1;//数组下标控制
	try{
	/* 实现菜单控制 */
	OBHtml.validateRequest(out,request,response);
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
%>
<%	
  		lID1 =  request.getParameterValues("lID1");
		System.out.println("++++++++++++复核的id++++++++"+lID1);
		i = lID1.length;//提交的个数
		System.out.println("提交指令的个数："+ i);
	    OBBankPayInfo  info=null;
	
	
		for(int j=0;j<i;j++)
		{
			info=new OBBankPayInfo();
			info.convertRequestToDataEntity(request);
			info.setNclientid(sessionMng.m_lClientID);
			System.out.print("客户id"+info.getNclientid());
			System.out.print("客户id"+info.getFormatDtexecute());
			info.setNcurrencyid(sessionMng.m_lCurrencyID);
			System.out.print("币种id"+info.getNcurrencyid());
			System.out.println("**************************"+info);	
			//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
			/* 初始化EJB */
			OBFinanceInstrHome financeInstrHome = null;
			OBFinanceInstr financeInstr = null;
			financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
			financeInstr = financeInstrHome.create();
			
			long lInstructionID=-1;
			//check
			lID = Long.parseLong(lID1[j].split("####")[0]);
			System.out.print(lID);
		    lInstructionID=financeInstr.checkBankPay(lID,sessionMng.m_lUserID);
			info=financeInstr.findByID(lID);	
			vcResult.add(info);	
		}		
		request.setAttribute("info",vcResult);
		/* 获取上下文环境 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext +"/bankpay/view/check_v002.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		/* forward到结果页面 */
		rd.forward(request, response);
	} 
	catch(IException ie) 
	{
		
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }

%>