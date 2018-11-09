<!--
/*
 * 程序名称：n003-c.jsp
 * 功能说明：通知开立提交控制页面
 * 作　　者：刘琰
 * 完成日期：2004年01月08日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,javax.servlet.*,com.iss.itreasury.util.*,com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,com.iss.itreasury.ebank.obsystem.bizlogic.*,com.iss.itreasury.ebank.obsystem.dataentity.*,com.iss.itreasury.ebank.util.*,com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,com.iss.itreasury.common.attach.bizlogic.*,com.iss.itreasury.safety.util.*"
%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.facade.factory.*"%>
<%@ page import="com.iss.itreasury.safety.facade.imp.*"%>
<%@ page import="com.iss.itreasury.safety.facade.*"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrStatus" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ page import="com.iss.itreasury.util.Constant.Actions" %>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%
	String strContext = request.getContextPath();
%>
<%!/* 标题固定变量 */
	String strTitle = "[通知开立]";%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getParameter("menu");
	Log.print("--------strMenu=" + strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间

	String lsign = null;
	String sign = "";
	lsign = request.getParameter("sign");
	if (lsign != null && lsign.trim().length() > 0) {
		sign = lsign;
	}
%>

<%
	/* 定义对表单的相应变量 */
	long lInstructionID = -1;//指令序号
	long templInstructionID = -1;//临时指令序号，业务日志使用
	String strPayerName = "";//  付款方名称
	String strPayerBankNo = "";// 银行账号
	String strPayerAcctNo = "";// 付款方账号
	long lPayerAcctID = -1;//付款方账户ID
	long lPayeeAcctID = -1;//收款方账户ID
	long lID = -1; //ID
	String strPayeeName = "";// 收款方名称
	String strPayeeBankNo = "";// 收款方银行账号
	String strPayeeAcctNo = "";// 收款方账号
	long lNoticeDay = -1; //通知存款品种（天）
	double dAmount = 0.0;// 金额
	Timestamp tsExecute = null;// 执行日
	String strNote = "";// 汇款用途
	String strAction = "";
	String tempTransCode = ""; //临时交易号
	String strTemp = "";
	long AbstractID = -1;//汇款用途ID
	String AbstractCode = "";//汇款用途编号	
	String signatureValue = "";
	String signatureOriginalValue = "";
	/* 初始化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	TransInfo transinfo = new TransInfo();
	OBAbstractSettingBiz OBAbstractSetting = new OBAbstractSettingBiz();
	OBAbstractSettingInfo OBinfo = new OBAbstractSettingInfo();
	long timestamp = -1;
	//Modify by leiyang date 2007/07/25
	boolean isSommitTimes = false;
	String msg = "";
	/* 初始化EJB */
	OBFinanceInstrHome financeInstrHome = null;
	OBFinanceInstr financeInstr = null;
	financeInstrHome = (OBFinanceInstrHome) EJBObject
			.getEJBHome("OBFinanceInstrHome");
	financeInstr = financeInstrHome.create();
%>

<%
	/* 用户登录检测与权限校验 */
	try {
		/* 用户登录检测 */
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E002");
			out.flush();
			return;
		}

		/* 判断用户是否有权限 */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E003");
			out.flush();
			return;
		}

		//判断是否需要审批流 by ypxu 2007-05-10
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo
				.setTransTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);
		boolean isNeedApproval = OBFSWorkflowManager
				.isNeedApproval(inutParameterInfo);
%>

<!--Get Data start-->
<%
	/* 从FORM表单中获取相应数据 */
		/* 指令序号 */
		lInstructionID = GetNumParam(request, "lInstructionID");
		Log.print("指令序号=" + lInstructionID);
		templInstructionID = lInstructionID;

		financeInfo = financeInstr.findByID(lInstructionID,
				sessionMng.m_lUserID, sessionMng.m_lCurrencyID);

		lID = GetNumParam(request, "txtID");
		Log.print("ID=" + lID);

		/* 付款方资料 */
		strPayerName = GetParam(request, "sPayerName");// 付款方名称
		Log.print("付款方名称=" + strPayerName);

		lPayerAcctID = GetNumParam(request, "payerAcctID");//付款方账户ID
		Log.print("付款方账户ID=" + lPayerAcctID);

		strPayerBankNo = GetParam(request, "sBankAccountNO");// 银行账号
		Log.print("银行账号=" + lPayerAcctID);

		strPayerAcctNo = GetParam(request, "sPayerAccountNoZoomCtrl");// 付款方账号
		Log.print("付款方账号=" + strPayerAcctNo);

		/* 收款方资料 */
		lPayeeAcctID = GetNumParam(request, "payeeAcctID");//收款方账户ID
		Log.print("收款方账户ID=" + lPayeeAcctID);

		strPayeeAcctNo = GetParam(request, "sPayeeAccountNo");// 收款方账号
		Log.print("收款方账号=" + strPayeeAcctNo);

		strPayeeName = GetParam(request, "sPayeeName");// 收款方名称
		Log.print("收款方名称=" + strPayeeName);

		lNoticeDay = GetNumParam(request, "noticeDay");//通知存款品种
		Log.print("通知存款品种=" + lNoticeDay);

		/* 划款资料 */
		if (request.getParameter("amount") != null) {
			dAmount = Double.valueOf(
					DataFormat.reverseFormatAmount((String) request
							.getParameter("amount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
		if (request.getParameter("tsExecute") != null) {
			tsExecute = DataFormat.getDateTime((String) request
					.getParameter("tsExecute"));// 执行日
			Log.print("执行日=" + tsExecute);
		}

		strNote = GetParam(request, "sNoteCtrl").trim();// 汇款用途
		Log.print("汇款用途=" + strNote);

		strTemp = (String) request.getAttribute("strTransTypeNo");
		if (strTemp != null && strTemp.trim().length() > 0) {
			tempTransCode = strTemp;
		}
		strTemp = (String) request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length() > 0) {
			strAction = strTemp;
		}
		
		strTemp = request.getParameter(SignatureConstant.SIGNATUREVALUE.getName());
		if(strTemp != null && !strTemp.equals("")){
			signatureValue = strTemp;
		}
		
		strTemp = request.getParameter(SignatureConstant.ORIGINALVALUE.getName());
		if(strTemp != null && !strTemp.equals("")){
			signatureOriginalValue = strTemp;
		}
					
		strTemp = request.getParameter("timestamp");
		if(strTemp != null && !strTemp.equals("")){
			timestamp = Long.valueOf(strTemp).longValue();
			
		}
		//add by dwj 20111107
		if (NameRef.getAccountOfficeByID(lPayerAcctID) != NameRef
				.getAccountOfficeByID(lPayeeAcctID)) {
			throw new IException("账户：" + strPayerAcctNo + " 和账户："
					+ strPayeeAcctNo + "不是同机构的账户");
		}
		//end add by dwj 20111107	

		//Modify by leiyang date 2007/07/25
		//取消审批不验证
		if (!String.valueOf(OBConstant.SettInstrStatus.CANCELAPPRVOAL)
				.equals(strAction)) {
			Timestamp timeNow = Env.getSystemDate(
					sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);

			//当执行日等于开机日
			if (tsExecute.compareTo(timeNow) == 0) {
				String strCommitTime = "";
				long isControl = -1;

				OBCommitTime cTime = new OBCommitTime();
				cTime.setOfficeId(sessionMng.m_lOfficeID);
				cTime.setCurrencyId(sessionMng.m_lCurrencyID);
				OBCommitTime result = OBCommitTimeBiz
						.findOBCommitTime(cTime);

				if (result != null) {
					strCommitTime = result.getCommitTime();
					isControl = result.getIsControl();

					timeNow = Env.getSystemDateTime();

					//当前小时和分钟
					int lTNHours = timeNow.getHours();
					int lTNMinutes = timeNow.getMinutes();

					String commitTimes[] = strCommitTime.split(":");
					//停止接收的小时和分钟
					int lCTHours = Integer.parseInt(commitTimes[0]);
					int lCTMinutes = Integer.parseInt(commitTimes[1]);

					if (lCTHours < lTNHours) {
						if (String.valueOf(
								OBConstant.SettInstrStatus.DOAPPRVOAL)
								.equals(strAction)) {
							throw new IException("审批时间已超过结算最迟接收时间");
						} else {
							if (isControl == SETTConstant.OBCommitTimeControlType.RIGID) {
								throw new IException("提交时间已超过结算最迟接收时间");
							} else {
								tsExecute
										.setDate(tsExecute.getDate() + 1);
								isSommitTimes = true;
							}
						}
					} else if (lCTHours == lTNHours) {
						if (lCTMinutes < lTNMinutes) {
							if (String
									.valueOf(
											OBConstant.SettInstrStatus.DOAPPRVOAL)
									.equals(strAction)) {
								throw new IException("审批时间已超过结算最迟接收时间");
							} else {
								if (isControl == SETTConstant.OBCommitTimeControlType.RIGID) {
									throw new IException(
											"提交时间已超过结算最迟接收时间");
								} else {
									tsExecute.setDate(tsExecute
											.getDate() + 1);
									isSommitTimes = true;
								}
							}
						}
					}
				}
			}
		}

		if(strAction.equals(String.valueOf(OBConstant.SettInstrStatus.DOAPPRVOAL))
			|| strAction.equals(String.valueOf(OBConstant.SettInstrStatus.SAVE)) 
			|| strAction.equals(String.valueOf(OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)))
		{
			//验证签名开始
			SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
			SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();
			boolean validate = facade.validateSignFromClient(OBConstant.SettInstrType.OPENNOTIFYACCOUNT,request);
			if(!validate)
			{
				throw new Exception("验证客户端签名信息失败!");
			}			
		}


%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set FinanceInfo Attribute start-->
<%
	/* 根据FORM表单中的相应数据 设置信息类*/
		/* 指令序号 */
		if (lInstructionID > 0) {
			financeInfo.setID(lInstructionID);
		}
		financeInfo.setTimestamp(timestamp);
		financeInfo.setSignatureOriginalValue(signatureOriginalValue);
		financeInfo.setSignatureValue(signatureValue);		
		/* 付款方资料 */
		financeInfo.setPayerName(strPayerName);// 付款方名称
		financeInfo.setPayerAcctID(lPayerAcctID);//付款方账户ID
		financeInfo.setPayerBankNo(strPayerAcctNo);// 银行账号
		financeInfo.setPayerAcctNo(strPayerAcctNo);// 付款方账号
		/* 收款方资料 */
		financeInfo.setPayeeName(strPayeeName);//收款放名称
		financeInfo.setPayeeAcctNo(strPayeeAcctNo); // 收款方账号
		financeInfo.setPayeeAcctID(lPayeeAcctID);//收款方账户ID
		financeInfo.setNoticeDay(lNoticeDay);//通知存款品种
		/* 划款资料 */
		financeInfo.setAmount(dAmount);// 金额
		financeInfo.setExecuteDate(tsExecute);// 执行日
		financeInfo.setConfirmDate(tsExecute);//确认日期
		financeInfo.setNote(strNote);// 汇款用途
		/* 从session中获取相应数据 */
		financeInfo.setConfirmUserID(sessionMng.m_lUserID);
		financeInfo.setClientID(sessionMng.m_lClientID);
		financeInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		financeInfo.setOfficeID(sessionMng.m_lOfficeID);//默认办事处ID
		/* 确定网上交易类型 */
		financeInfo
				.setTransType(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);//网上交易类型
		/*确定指令状态*/
		strTemp = (String) request.getAttribute("rsStatus");
		long statusid = -1;
		if (strTemp != null && strTemp.trim().length() > 0) {
			statusid = Long.valueOf(strTemp).longValue();
			if (statusid >= 0) {
				financeInfo.setStatus(statusid);
			}
		}
		if (financeInfo.getStatus() == -1) {
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
		}
%>
<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
	/* 修改返回结果 */
		long lUpdateResult = -1;
		String strNextPage = "";

		/* 调用EJB方法保存(或修改)和提取信息对象 */
		//刷新问题
		if (session.getAttribute("clickCount") == null) {
			session.setAttribute("clickCount", String.valueOf(0));
		}
		String strClickCount = request.getParameter("clickCount");
		Log
				.print("clickcount from request parameter:="
						+ strClickCount);
		boolean blnClickCheck = OBOperation.checkClick(strClickCount,
				session);

		if (String.valueOf(OBConstant.SettInstrStatus.DOAPPRVOAL)
				.equals(strAction)) {
			//构造代办业务列表中代办业务明细链接页面(即审批页面)
			String strSuccessPageURL1 = "/capital/notify/vAppN004.jsp";
			String strFailPageURL1 = "/capital/notify/n001-c.jsp";

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
			//如果审批流的待办任务链接修改为v033.jsp则使用下面地址    mingfang
			strNextPage = "/approval/view/v033.jsp";

			long iTransType = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
			String surl = strContext
					+ "/capital/query/q003-c.jsp?strSuccessPageURL="
					+ strSuccessPageURL1 + "&&strFailPageURL="
					+ strFailPageURL1 + "&&txtTransType=" + iTransType
					+ "&&txtID=" + lInstructionID;

			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo
					.setTransTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);

			financeInfo.setInutParameterInfo(pInfo);
			//审批
			try {
				//将签名值与原始数据保存
				//financeInfo.setSignatureValue(signatureValue);	
				//financeInfo.setSignatureOriginalValue(signatureOriginalValue);

				//Boxu Add 2009年5月22日 增加日志记录
				try {
					financeInstr.doApproval(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
				} catch (Exception ex) {
					transinfo.setStatus(Constant.FAIL);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}

				//Modify by leiyang date 2007/07/25
				msg = "审批成功";
				sessionMng.getActionMessages().addMessage(msg);
			} catch (Exception e) {
				Log.print("EJB异常抛到前台处理");
				e.printStackTrace();
			}
		}
		//取消审批   add by mingfang
		else if (String.valueOf(
				OBConstant.SettInstrStatus.CANCELAPPRVOAL).equals(
				strAction)) {
			//构造代办业务列表中代办业务明细链接页面(即审批页面)
			String strSuccessPageURL1 = "/capital/notify/vAppN004.jsp";
			String strFailPageURL1 = "/capital/notify/n001-c.jsp";

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
			//如果审批流的待办任务链接修改为v036.jsp则使用下面地址    mingfang
			strNextPage = "/approval/view/v036.jsp";

			long iTransType = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
			String surl = strContext
					+ "/capital/query/q003-c.jsp?strSuccessPageURL="
					+ strSuccessPageURL1 + "&&strFailPageURL="
					+ strFailPageURL1 + "&&txtTransType=" + iTransType
					+ "&&txtID=" + lInstructionID;

			//构造参数类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo
					.setTransTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);

			financeInfo.setInutParameterInfo(pInfo);

			//审批
			try {
				//将签名值与原始数据保存
				//	financeInfo.setSignatureValue(signatureValue);	
				//financeInfo.setSignatureOriginalValue(signatureOriginalValue);

				//Boxu Add 2009年5月22日 增加日志记录
				try {
					financeInstr.cancelApproval(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
				} catch (Exception ex) {
					transinfo.setStatus(Constant.FAIL);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}

				sessionMng.getActionMessages().addMessage("取消审批成功");
			} catch (Exception e) {
				Log.print("EJB异常抛到前台处理");
				e.printStackTrace();
			}
		} else {
			if (blnClickCheck) {
				//如果是"保存并提交审批",构造InutParameterInfo并设置到FinanceInfo中
				//save方法中判断TransCurrentDepositAssembler中的InutParameterInfo是否为空,如果不为空,则调用"提交审批方法",然后更新状态
				if (String.valueOf(
						OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL)
						.equals(strAction)) {
					String strSuccessPageURL1 = "/capital/notify/vAppN004.jsp";
					String strFailPageURL1 = "/capital/notify/n001-c.jsp";
					strNextPage = "/capital/notify/n004-v.jsp?isNeedApproval="
							+ isNeedApproval+"&oldOpenDate="+request.getParameter("oldOpenDate");
					long iTransType = OBConstant.SettInstrType.OPENNOTIFYACCOUNT;
					String surl = strContext
							+ "/capital/query/q003-c.jsp?strSuccessPageURL="
							+ strSuccessPageURL1 + "&&strFailPageURL="
							+ strFailPageURL1 + "&&txtTransType="
							+ iTransType + "&&txtID=";

					//构造参数类
					InutParameterInfo pInfo = new InutParameterInfo();
					pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					pInfo.setRequest(request);
					pInfo.setSessionMng(sessionMng);
					pInfo.setUrl(surl);
					pInfo
							.setTransTypeID(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);

					financeInfo.setInutParameterInfo(pInfo);
					//Modify by leiyang date 2007/07/25
					if (isSommitTimes == true) {
						msg = "提交审批成功, 时间已超过结算最迟接收时间, 执行日延迟一日!";
					} else {
						msg = "提交审批成功";
					}
				} else {
					//Modify by leiyang date 2007/07/25
					if (isSommitTimes == true) {
						msg = "保存成功, 时间已超过结算最迟接收时间, 执行日延迟一日!";
					} else {
						msg = "保存成功";
					}
				}

				//将签名值与原始数据保存
				//	financeInfo.setSignatureValue(signatureValue);	
				//	financeInfo.setSignatureOriginalValue(signatureOriginalValue);

				//Boxu Add 2009年5月22日 增加日志记录
				try {
					if (sign.equals("again")) {
						financeInfo.setID(-1);
					}
					lInstructionID = financeInstr
							.addCapitalTrans(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
				} catch (Exception ex) {
					transinfo.setStatus(Constant.FAIL);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}

				if (lInstructionID > 0) {
					if (tempTransCode != null
							&& !tempTransCode.equals("")) {
						//数据访问对象
						AttachOperation attachOperation = new AttachOperation();
						attachOperation.updateOBTransCode(
								tempTransCode, String
										.valueOf(lInstructionID));
					}
				}
				financeInfo.setID(lInstructionID);
				session.setAttribute("lInstructionID", String
						.valueOf(lInstructionID));
				sessionMng.getActionMessages().addMessage(msg);
			} else {
				if (lInstructionID < 0) {
					String strInstructionID = (String) session
							.getAttribute("lInstructionID");
					lInstructionID = Long.parseLong(strInstructionID);
				}
			}
		}

		/*保存汇款用途摘要信息*/

		strTemp = (String) request.getAttribute("sNote");
		if (strTemp != null && !strTemp.equals("")) {
			AbstractID = Long.parseLong(strTemp);//汇款用途ID
		} else {
			AbstractID = -1;
		}
		AbstractCode = (String) request.getAttribute("sCode");//汇款用途编号

		if (AbstractID < 0) {
			long lMaxCode = OBAbstractSetting.getMaxCode(
					sessionMng.m_lOfficeID, sessionMng.m_lUserID);
			OBinfo.setScode(DataFormat.formatInt(lMaxCode, 5, true, 0));
		} else if (AbstractID > 0) {
			OBinfo.setScode(AbstractCode);
		}

		OBinfo.setId(AbstractID);
		OBinfo.setSdesc(strNote);
		OBinfo.setNofficeid(sessionMng.m_lOfficeID);
		OBinfo.setNclientid(sessionMng.m_lUserID);
		OBinfo.setInputtime(now);
		OBinfo.setModifytime(now);
		if (strNote.trim().length() != 0) {
			OBAbstractSetting.saveStandardAbstract(OBinfo);
		}

		/*调用EJB方法查询结果*/
		financeInfo = financeInstr.findByID(lInstructionID,
				sessionMng.m_lUserID, sessionMng.m_lCurrencyID);
%>
<!--Access DB end-->

<!--Forward start-->
<%
	/* 在请求中保存结果对象 */
		request.setAttribute("financeInfo", financeInfo);
		/* 获取上下文环境 */
		//ServletContext sc = getServletContext();
		/* 设置返回地址 */
		if (strNextPage == null || "".equals(strNextPage)) {
			strNextPage = "/capital/notify/n004-v.jsp?isNeedApproval="
					+ isNeedApproval+"&oldOpenDate="+request.getParameter("oldOpenDate");
		}

		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strNextPage);
		//分发
		RequestDispatcher rd = request
				.getRequestDispatcher(PageController
						.getDispatcherURL(pageControllerInfo));
		/* forward到结果页面 */
		rd.forward(request, response);

	} catch (IException ie) {
		Log.print("f003-c.jsp:EJB调用异常或者跳转有错");
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
				1);
		return;
	} finally {
		//Boxu Add 2009年5月22日 增加日志记录
		if (String.valueOf(OBConstant.SettInstrStatus.DOAPPRVOAL)
				.equals(strAction)) {
			transinfo
					.setActionType(Constant.TransLogActionType.approval);
		} else if (String.valueOf(
				OBConstant.SettInstrStatus.CANCELAPPRVOAL).equals(
				strAction)) {
			transinfo
					.setActionType(Constant.TransLogActionType.cancelApproval);
		} else if (String.valueOf(
				OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL).equals(
				strAction)) {
			transinfo
					.setActionType(Constant.TransLogActionType.initApproval);
		} else if (templInstructionID > 0) {
			transinfo.setActionType(Constant.TransLogActionType.modify);
		} else {
			transinfo.setActionType(Constant.TransLogActionType.insert);
		}

		if (transinfo.getStatus() != -1) {
			TranslogBiz translofbiz = new TranslogBiz();
			transinfo.setHostip(request.getRemoteAddr());
			transinfo.setHostname(request.getRemoteHost());
			transinfo
					.setTransType(OBConstant.SettInstrType.OPENNOTIFYACCOUNT);
			translofbiz.saveTransLogInfo(sessionMng, financeInfo,
					transinfo);
		}
	}
%>
<!--Forward end-->