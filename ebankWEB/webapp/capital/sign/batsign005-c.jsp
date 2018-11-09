<%--
/*
 * 程序名称：batsign003-c.jsp
 * 功能说明：批量签认查询页面
 * 作　　者：菅中尉
 * 完成日期：2008年03月31日
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page
	import="java.util.*"%>

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 java.rmi.*,
                 java.lang.*,
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
	String strTitle = "[批量签认]";%>
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
	String tempArr[] = new String[]{};
	String[] strID = new String []{};//指令id数组
	Timestamp dtModify[] = new Timestamp[]{};
	int i = -1;//数组下标控制
	long lRet = -1 ;//调用check()的反回值
	String sbatchno = "";
	String  straction=null;
	  TransInfo transinfo = new TransInfo();
	  FinanceInfo financeInfo = new FinanceInfo();
	//查询类
    //临时量
        String sTemp = null;
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
		if(request.getParameter("sbatchno")!=null)
		{
			sbatchno = DataFormat.formatString(request.getParameter("sbatchno"));
		}
		
		  sTemp = (String) request.getParameter("Straction");
	        if (sTemp != null && sTemp.trim().length() > 0) {
	        	straction = sTemp.trim();
	            System.out.println("Straction==============:"+straction);
	        }

%>
<%
		tempArr =  request.getParameterValues("strID");
		if(tempArr.length>0){
			strID = new String[tempArr.length];
			dtModify = new Timestamp[tempArr.length];
		}
		for(int k = 0 ; k<tempArr.length ; k++){
			String[] arr = tempArr[k].split("####");
			String id = arr[0];
			Timestamp date = DataFormat.getDateTime(arr[2]);
			strID[k] = id;
			dtModify[k] = date;
		}
		System.out.println("++++++++++++复核的id++++++++："+strID);
		i = strID.length;//提交的个数
		System.out.println("提交指令的个数："+ i);
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		String strTemp = new String();//返回信息
		String strTemp1 = new String();//返回信息
		Vector vcResult = new Vector(1);//返回信息	
		if(straction.equals("sign")){
		for(int j=0;j<i;j++)
		{
			System.out.println("开始签认");	
			lID = Long.parseLong(strID[j]);
			System.out.println("===================复核的id："+lID);
			//开始签认，返回值大于0为签认成功，小于零为失败							
			//lRet = financeInstr.check(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);	
			  try
              {
                 FinanceInfo financeInfo_tmp = new FinanceInfo();
                 Timestamp dtmodify=null;
                 financeInfo_tmp.setID(lID);
                 financeInfo_tmp.setSignUserID(sessionMng.m_lUserID);
                 if(request.getAttribute(lID+"_dtmodify")!=null){
                 	dtmodify = DataFormat.getDateTime((String) request.getParameter(lID+"_dtmodify"));
                 	System.out.println("#######################----"+lID+"----dtmodifydtmodifydtmodify==="+dtmodify);
                 }
                 if(dtmodify==null&&request.getParameter("dtmodify")!=null){
                 	dtmodify = DataFormat.getDateTime((String) request.getParameter("dtmodify"));
                 }
                 if(dtModify[j]!=null){
				     dtmodify = dtModify[j];
				  }
                 financeInfo_tmp.setDtModify(dtmodify);
				 lRet = financeInstr.sign(financeInfo_tmp);		
				 transinfo.setStatus(Constant.SUCCESSFUL);
			   }
		       catch(Exception ex)
			         {
			              transinfo.setStatus(Constant.FAIL);
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
				           financeInfo = financeInstr.findByID( lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
				          transinfo.setTransType(financeInfo.getTransType());
				          transinfo.setActionType(Constant.TransLogActionType.sign);				
				          translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			         	 }
		        	  }
			 if(lRet<0){
					strTemp = "交易签认失败！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为："+lID+"</b><br>";
					vcResult.add(strTemp);
				}else{
					strTemp ="交易已提交至"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
					"！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为："+lID+"</b><br>";
					vcResult.add(strTemp);
					/*叶洪润：说这里要注释掉*/
					//strTemp = NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
						//"将尽快处理阁下的指令!<br>";
					//vcResult.add(strTemp);
				}
				
		}
		}else if(straction.equals("cancelsign")){

			for(int j=0;j<i;j++)
			{
				System.out.println("开始取消签认");	
				lID = Long.parseLong(strID[j]);
				System.out.println("===================取消签认id："+lID);
				//开始签认，返回值大于0为签认成功，小于零为失败							
				//lRet = financeInstr.check(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);	
				try
              {	
				 lRet = financeInstr.cancelSign(lID, sessionMng.m_lUserID);
				  transinfo.setStatus(Constant.SUCCESSFUL);
			   }
		       catch(Exception ex)
			         {
			              transinfo.setStatus(Constant.FAIL);
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
				           financeInfo = financeInstr.findByID( lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
				          transinfo.setTransType(financeInfo.getTransType());
				          transinfo.setActionType(Constant.TransLogActionType.cancelSign);				
				          translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			         	 }
		        	  }
				 if(lRet<0){
						strTemp = "交易取消签认失败！<br>";
						vcResult.add(strTemp);
						strTemp = "<b>指令序号为："+lID+"</b><br>";
						vcResult.add(strTemp);
					}else{
						 //返回信息处理
						strTemp = "这笔业务现为已复核状态，需要签认后才能提交到"+
						NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
						vcResult.add(strTemp);
						strTemp = "<b>指令序号为："+lID+"</b><br>";
						vcResult.add(strTemp);
					}
					
			}
			
			
		}
		request.setAttribute("return",vcResult);
		System.out.println("#######################"+vcResult.size());
		//结果跳转
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext +"/capital/sign/batsign006-v.jsp");
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("batsign005-c异常："+ie.toString());
		return;
	}	
		
 %>
