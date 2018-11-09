<!--
/*
 * 程序名称：f011-c.jsp
 * 功能说明：定期支取修改控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.SETTConstant,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.approval.dataentity.*,
			     com.iss.itreasury.safety.util.*"
%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.facade.factory.*"%>
<%@ page import="com.iss.itreasury.safety.facade.imp.*"%>
<%@ page import="com.iss.itreasury.safety.facade.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header start-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期支取]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
		
	/* 初始化信息类 */
	TransInfo transinfo = new TransInfo();
	Timestamp dtmodify=null;
	FinanceInfo financeInfo = new FinanceInfo();
%>

<%  
	/* 用户登录检测与权限校验 */
	try 
	{
       	//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	
%>

<!--Get Data start-->
<%
		/* 指令序号 */
		long lInstructionID = -1;
		long strAction=-1;
		lInstructionID = GetNumParam(request,"lInstructionID");
		strAction = GetNumParam(request,"strAction");
		Log.print("------------------------------lInstructionID="+lInstructionID);
		
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}
		
		
		FinanceInfo tempFinanceInfo = null;
		if(session.getAttribute("financeInfo") != null)
		tempFinanceInfo = (FinanceInfo)session.getAttribute("financeInfo");
		session.setAttribute("financeInfo", null);
		
		//added by mingfang 2007/05/24 数字签名		
				
		//安全认证机构
		String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		//得到签名值
		String signatureValue = "";
		String signatureOriginalValue = "";
		String strTemp = "";

		strTemp = request.getParameter(SignatureConstant.SIGNATUREVALUE.getName());
		if(strTemp != null && !strTemp.equals("")){
			signatureValue = strTemp;
		}
		
		strTemp = request.getParameter(SignatureConstant.ORIGINALVALUE.getName());
		if(strTemp != null && !strTemp.equals("")){
			signatureOriginalValue = strTemp;
		}	

	
		if(strTroyName.equals(Constant.GlobalTroyName.ITrus) && 
			(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL
				|| strAction==OBConstant.SettInstrStatus.SAVE
				|| strAction==OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)){
			String[] nameArray = OBSignatureConstant.FixedToCurrentTransfer.getSignNameArray();
			String[] valueArray = OBSignatureConstant.FixedToCurrentTransfer.getSignValueArrayFromReq(request);
			
			String _blnIsNeedApproval = request.getParameter("isNeedApproval");
			long lActionStatus =  Long.parseLong(request.getParameter("actionStatus"));
			
			String strIsRefused = request.getParameter("isRefused");
			strIsRefused = strIsRefused == null ? "" : strIsRefused;
			
			//特殊处理
			if(!(strAction==OBConstant.SettInstrStatus.DOAPPRVOAL)){
				if(_blnIsNeedApproval.equals("true")){
				  	if(!strIsRefused.equals("true")
						&& lActionStatus != OBConstant.SettActionStatus.CANCELAPPROVALED
						&& lActionStatus != OBConstant.SettActionStatus.CANCELCHECKED){											
							valueArray[OBSignatureConstant.FixedToCurrentTransfer.iArrayLength-1] = "-1";
					}		
				}else{
					long lRsStatus = Long.parseLong(request.getParameter("rsStatus"));
					
					if( lRsStatus != OBConstant.SettInstrStatus.CHECK
						&& lActionStatus != OBConstant.SettActionStatus.CANCELCHECKED){
						
						valueArray[OBSignatureConstant.FixedToCurrentTransfer.iArrayLength-1] = "-1";
					}		
				}
			}
		
		}	
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* 实例化信息类 */
		PayerOrPayeeInfo payerInfo = null;

		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		if(request.getParameter("dtmodify")!=null){
			dtmodify = DataFormat.getDateTime((String) request.getParameter("dtmodify"));// 上次修改时间  add by zhanglei  date 2010.06.01
		}
		String strNextPage = "";
		long iTransType = OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER;		
			
			String operate = (String) request.getParameter("operate");
			strNextPage ="/capital/fixed/f012-v.jsp";
			/* 调用方法获取信息对象 */	
			financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
			OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
			payerInfo = biz.getLoanAccountSelectInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED,sessionMng.m_lUserID);
			if(payerInfo == null)
			{
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "../../LoginMain.html",1, "OB_E002");
				return;
			}
			/*出错抛出异常后带出错误的输入*/
			if(lInstructionID < 0 && tempFinanceInfo!=null && tempFinanceInfo.getTransType()==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER )
				financeInfo = tempFinanceInfo;
				
		if(operate != null && operate.equals("doApproval"))
		{
		
			//Modify by leiyang date 2007/07/25
			Timestamp tsExecute = financeInfo.getExecuteDate();
			Timestamp timeNow = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			
			//当执行日等于开机日
			if(tsExecute.compareTo(timeNow) == 0){
				String strCommitTime = "";
				long isControl = -1;
		
				OBCommitTime cTime = new OBCommitTime();
				cTime.setOfficeId(sessionMng.m_lOfficeID);
				cTime.setCurrencyId(sessionMng.m_lCurrencyID);
				OBCommitTime result = OBCommitTimeBiz.findOBCommitTime(cTime);
				
				if(result != null){
					strCommitTime = result.getCommitTime();
					isControl = result.getIsControl();
					
					timeNow = Env.getSystemDateTime();
					
					//当前小时和分钟
					int lTNHours =  timeNow.getHours();
					int lTNMinutes = timeNow.getMinutes();
					
					String commitTimes[] = strCommitTime.split(":");
					//停止接收的小时和分钟
					int lCTHours = Integer.parseInt(commitTimes[0]);
					int lCTMinutes = Integer.parseInt(commitTimes[1]);
					
					if(lCTHours < lTNHours){
						throw new IException("审批时间已超过结算最迟接收时间");
					}
					else if(lCTHours == lTNHours) {
						if(lCTMinutes < lTNMinutes){
							throw new IException("审批时间已超过结算最迟接收时间");
						}
					}
				}
			}
			
			
			String strSuccessPageURL1="/capital/fixed/vAppF014.jsp";
			String strFailPageURL1="/capital/fixed/f013-c.jsp";

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
					//如果审批流的待办任务链接修改为v033.jsp则使用下面地址    mingfang
					strNextPage ="/approval/view/v033.jsp";
			
			
			
			//String strAction1 = "FixedQuery";
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=" + lInstructionID;
			
			
			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);
			
			financeInfo.setInutParameterInfo(pInfo);
			
			//将签名值与原始数据保存
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
				
			//审批
				try
				{
				     //modify by xwhe 2009-05-25 网银日志的添加
				    transinfo.setActionType(Constant.TransLogActionType.approval);
					financeInstr.doApproval(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
					sessionMng.getActionMessages().addMessage("审批成功");
				}
				catch(Exception e) 
				{
				    transinfo.setStatus(Constant.FAIL);
					Log.print("EJB异常抛到前台处理");	
					e.printStackTrace();
					throw new IException(e.getMessage());
				}
		}
		//取消审批
        else if(operate != null && operate.equals("cancelApproval"))
		{
			String strSuccessPageURL1="/capital/fixed/vAppF014.jsp";
			String strFailPageURL1="/capital/fixed/f013-c.jsp";
			
			
			
			

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
			
			strNextPage ="/approval/view/v036.jsp";
			
			
			
			//String strAction1 = "FixedQuery";
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=" + lInstructionID;
			
			
			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);
			
			financeInfo.setInutParameterInfo(pInfo);
			
			//将签名值与原始数据保存
			financeInfo.setSignatureValue(signatureValue);	
			financeInfo.setSignatureOriginalValue(signatureOriginalValue);
			

			long statusid = -1;	 	
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    statusid = Long.valueOf(strTemp).longValue();
			    if(statusid>=0)
			    {
			    	financeInfo.setStatus(statusid);
			    }
			}
			//审批
				try
				{
				    //modify by xwhe 2009-05-22 网银日志的添加
				    transinfo.setActionType(Constant.TransLogActionType.cancelApproval);
					financeInstr.cancelApproval(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
					sessionMng.getActionMessages().addMessage("取消审批成功");
				}
				catch(Exception e) 
				{
				    transinfo.setStatus(Constant.FAIL);
					Log.print("EJB异常抛到前台处理");	
					e.printStackTrace();
					throw new IException(e.getMessage());
				}
		}
	
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
		request.setAttribute("payerInfo", payerInfo);
		
		//Modify by leiyang date 2007/06/22
		if(request.getParameter("deposidNo") != null) {
			request.setAttribute("deposidNo",request.getParameter("deposidNo"));
		}
		
	    /* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		
	    /* 设置返回地址 */
	 
	    //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	System.out.println("$#################################wwww###############################"+strNextPage);
	pageControllerInfo.setUrl(strNextPage);
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
	finally
		{	
			if(transinfo.getStatus()!=-1)
			{
				TranslogBiz translofbiz= new TranslogBiz();
				transinfo.setHostip(request.getRemoteAddr());
				transinfo.setHostname(request.getRemoteHost());
				transinfo.setTransType(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);				
				translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			}
		}
	
%>
<!--Forward end-->

