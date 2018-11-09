<%
	/*
	*页面名称：c001.jsp
	*功能：接收银行汇款信息（添加 修改操作）
	*作者：libaihui
	*time:2006-09-11
	*/
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.budget.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
                 com.iss.itreasury.budget.executecontrol.dataentity.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.ebank.approval.dataentity.*,
                 com.iss.itreasury.common.attach.bizlogic.*,
                 com.iss.itreasury.ebank.obsystem.dao.OBSystemDao"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%!
	/* 标题固定变量 */
	String strTitle="[银行汇款]"; 
	String msg=null;
%>
<%
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
		long lSourceType = 0;
		String strSource = request.getParameter("src");
		if ((strSource != null) && (strSource.length() > 0)) {
			lSourceType = Long.parseLong(strSource);
		}
		String Temp = null;
		long nEbankStatus = -1; //银行指令状态
		Temp =(String) request.getParameter("nEbankStatus");
		if(Temp!=null&&Temp.trim().length()>0)
		{
			 nEbankStatus = Long.parseLong(Temp);
		}
		
		String lflag = null;
	   	String flag = "";
	   	lflag = request.getParameter("flag");
	   	if(lflag!=null&&lflag.trim().length()>0)
	   	{
	   		flag = lflag;
	   	}
	   	
	   	String strAct = (String) request.getAttribute("strAction");
	   	long strAction = -1;
		if (strAct != null && strAct.trim().length() > 0) {
			strAction = Long.parseLong(strAct);
		}
	
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		String strTemp = "";
		long AbstractID = -1;//汇款用途ID
		String AbstractCode = "";//汇款用途编号
		OBBankPayInfo  info=new OBBankPayInfo();
		OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
    	OBAbstractSettingInfo OBinfo = new OBAbstractSettingInfo();  		
		
		info.convertRequestToDataEntity(request);
		
		String temp = null;
		String bankCNAPSNo = null;
	
		temp = (String)request.getAttribute("txtPayeeBankCNAPSNO");

		if(temp!=null&&!temp.trim().equals(""))
		{
			bankCNAPSNo = temp;
			info.setBankCNAPSNo(bankCNAPSNo);
		}
	
		//状态
		info.setNstatus(1);
		//客户id
		info.setNclientid(sessionMng.m_lClientID);
		//币种
		info.setNcurrencyid(sessionMng.m_lCurrencyID);
		//确认人(当前用户)
		info.setNconfirmuserid(sessionMng.m_lUserID);
		//确认时间（系统当前时间）
		info.setDtconfirm(DataFormat.getDateTime(DataFormat.getDateString().substring(0,10)));
		//info.setNmodule(-1);
		
		String strNote = "";	
		strNote = GetParam(request, "sNoteCtrl").trim();// 汇款用途
		info.setSnote(strNote);
		Log.print("汇款用途=" + strNote);
		
		//判断是否用审批流
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.ONLINEBANK_BANKPAY);
		if (OBConstant.SettInstrStatus.DOAPPRVOAL != strAction
				&& OBConstant.SettInstrStatus.CANCELAPPRVOAL != strAction) {
			inutParameterInfo.setIslowerunit(OBConstant.IsLowerun.ISNO);
		}
		boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		
		//初始化EJB 
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		//Log.print("--------------------lInstructionID="+lInstructionID);
		//lInstructionID = financeInstr.addCapitalTrans(financeInfo);
		//out.print("<script>alert(\"提交成功\")</script>");
		String sTemp = null;
	 	long lID = 0;           //指令序号
    	sTemp = (String) request.getParameter("lID");
    	if (sTemp != null && sTemp.trim().length() > 0) {
       		 lID = Long.parseLong(sTemp);
   		 }
   		 
		String act="";
		act=request.getParameter("act");
		long lInstructionID=-1;
		
		//将新录入的收款方帐户信息存入收款方放大镜
		OBSystemDao obSystemDao = new OBSystemDao();
		ClientCapInfo capInfo = new ClientCapInfo();
		capInfo.setID(info.getId());
		capInfo.setCity(info.getSpayeecity());
		capInfo.setClientID(info.getNclientid());
		capInfo.setCurrencyID(info.getNcurrencyid());
		capInfo.setInputUserID(info.getNconfirmuserid());
		capInfo.setIsCPFAcct(OBConstant.AccountOfCpf.CODE_ACCOUNTOFCPF_NO);
		capInfo.setPayeeAccoutNO(info.getSpayeeacctno());
		capInfo.setPayeeBankName(info.getSpayeebankname());
		capInfo.setPayeeName(info.getSpayeeacctname());
		capInfo.setPayeeProv(info.getSpayeeprov());
		if(info.getBankCNAPSNo()!=null){
		capInfo.setSPayeeBankCNAPSNO(info.getBankCNAPSNo());
		}
		capInfo.setSPayeeBankExchangeNO(info.getBankconnectnumber());
		capInfo.setSPayeeBankOrgNO(info.getDepartmentnumber());
		
		info.setNpayeeacctid(obSystemDao.addPayee(capInfo));
		
		if(act!=null && act.equals("add"))
		{
			//add
			lInstructionID=financeInstr.addBankPay(info);
			msg =  "保存成功";
			if(msg!=null)
			{
				sessionMng.getActionMessages().addMessage(msg);
			}
		}
		if(act!=null && act.equals("SaveAndApproval"))
		{
			//SaveAndApproval
			String strFailPageURL1 = strContext + "/approval/view/033.jsp";
			String surl= strContext + "/bankpay/view/c004_v.jsp?strFailPageURL="+strFailPageURL1+"&&txtID=";

			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.ONLINEBANK_BANKPAY);
				
			info.setInutParameterInfo(pInfo);
			lInstructionID=financeInstr.addBankPay(info);
			
			msg =  "提交审批成功";
			if(msg!=null)
			{
				sessionMng.getActionMessages().addMessage(msg);
			}
		}
		if(act!=null && act.equals("SaveAndApprovalmodify"))
		{
			//SaveAndApproval
			String strFailPageURL1 = strContext + "/approval/view/033.jsp";
			String surl= strContext + "/bankpay/view/c004_v.jsp?strFailPageURL="+strFailPageURL1+"&&txtID=";

			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.ONLINEBANK_BANKPAY);
				
			info.setInutParameterInfo(pInfo);
			OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
			info.setId(lID);
			//info.setNstatus(OBConstant.SettInstrStatus.APPROVALING);
			biz.updateEbank(info);
			biz.submitExamine(info);
			
			msg =  "提交审批成功";
			if(msg!=null)
			{
				sessionMng.getActionMessages().addMessage(msg);
			}
			
		}
		if(act!=null && act.equals("modify"))
		{
		
			lInstructionID=financeInstr.addBankPay(info);
			msg =  "保存成功";
			if(msg!=null)
			{
				sessionMng.getActionMessages().addMessage(msg);
			}
		}
		if(act!=null && act.equals("modifyquery"))
		{
			OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
			info.setId(lID);

			biz.updateEbank(info);
			msg =  "保存成功";
			if(msg!=null)
			{
				sessionMng.getActionMessages().addMessage(msg);
			}
		}
		if(act!=null && act.equals("querymodify"))
		{
			//querymodify
			lInstructionID=financeInstr.addBankPay(info);
			msg =  "保存成功";
			if(msg!=null)
			{
				sessionMng.getActionMessages().addMessage(msg);
			}
		
		}
		
		/*保存汇款用途摘要信息*/
		
		strTemp = (String)request.getAttribute("sNote");
		AbstractID = Long.parseLong(strTemp);//汇款用途ID
		
		AbstractCode = (String)request.getAttribute("sCode");//汇款用途编号
		
		if( AbstractID < 0)
		{
			long lMaxCode = OBAbstractSetting.getMaxCode(sessionMng.m_lOfficeID,sessionMng.m_lUserID);
			OBinfo.setScode(DataFormat.formatInt(lMaxCode,5,true,0));
		}else if(AbstractID > 0){
			OBinfo.setScode(AbstractCode);
		}
		
		OBinfo.setId(AbstractID);
		OBinfo.setSdesc(strNote);
		OBinfo.setNofficeid(sessionMng.m_lOfficeID);
		OBinfo.setNclientid(sessionMng.m_lUserID);
		OBinfo.setInputtime(now);
		OBinfo.setModifytime(now);
		if(strNote.trim().length() != 0 )
		{
			OBAbstractSetting.saveStandardAbstract(OBinfo);
		}		
		
		if(lID==0)
		{
			if(lInstructionID!=-1) 
				info.setId(lInstructionID);
		}
		
		request.setAttribute("info",info);
		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		
		if(lSourceType==1||nEbankStatus==0)
		{
		pageControllerInfo.setUrl("../view/v002.jsp?src=1&lID="+lID+"&flag="+flag+"&isNeedApproval="+isNeedApproval);
		}
		else
		{
			pageControllerInfo.setUrl("../view/v002.jsp?flag="+flag+"&isNeedApproval="+isNeedApproval);
		}
		//分发
		
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	   /* forward到结果页面 */
		rd.forward(request, response);
			

	} 
	catch(IException ie) 
	{
		Log.print("v001.jsp:EJB调用异常或者跳转有错");
		//session.setAttribute("financeInfo", financeInfo);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		out.print("e:"+e.toString());
		e.printStackTrace();
		return;
    }
	
%>