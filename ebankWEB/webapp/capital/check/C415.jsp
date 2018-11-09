<!--
/*
 * 程序名称：C415.jsp
 * 功能说明：交易指令批量复核控制页面
 * 作　　者：刘琰
 * 完成日期：2003年09月24日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
                 java.util.*,
                 com.iss.itreasury.ebank.bizdelegation.*,
                 com.iss.itreasury.safety.util.*,
				 com.iss.itreasury.settlement.util.NameRef"
%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%
	//标题变量
	String strTitle = "[业务复核]";
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
	TransInfo transinfo = new TransInfo();
	String sign = "";
%>
<%
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
%>

<%
		//查询条件信息，用于V416.jsp,返回时，给form写值
		QueryCapForm qcf=new QueryCapForm();
		// 网上银行交易类型,资金划拨由专门的类型
    	qcf.setTransType( GetNumParam(request,"SelectType") ); // 网上银行交易类型,资金划拨由专门的类型
		Log.print("SelectType:   "+qcf.getTransType());
		
    	qcf.setStartSubmit ( GetParam(request,"txtConfirmA") ); // 提交日期-从
		Log.print("txtStartSubmit:   "+qcf.getStartSubmit());
    
    	qcf.setEndSubmit ( GetParam(request,"txtConfirmB") ); // 提交日期-到
		Log.print("txtEndSubmit:   "+qcf.getEndSubmit());
    
    	qcf.setStatus ( GetNumParam(request,"SelectStatus") );// 交易指令状态
		Log.print("SelectStatus:   "+qcf.getStatus());
		
		if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase("")))
    	{
			qcf.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(request.getParameter("txtMinAmount").trim())) );// 交易金额-最小值
			Log.print("MinAmount:   "+qcf.getMinAmount());
		}
		
		if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase("")))
    	{
			qcf.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(request.getParameter("txtMaxAmount").trim())) );// 交易金额-最大值
			Log.print("MaxAmount:   "+qcf.getMaxAmount());
      	}
		
		qcf.setStartExe ( GetParam(request,"txtExecuteA") ); // 执行日期-从
		Log.print("StartExe :   "+qcf.getStartExe ());
	
    	qcf.setEndExe ( GetParam(request,"txtExecuteB") ); // 执行日期-到
		Log.print("EndExe :   "+qcf.getEndExe ());   
    	
    	Timestamp dtmodify = null;
		if(request.getParameter("dtmodify")!=null){
		    	dtmodify =DataFormat.getDateTime(request.getParameter("dtmodify"));
		}
		//定义接收Form信息的变量
		long lIsCheck = -1;//是否是复核
		if (request.getParameter("txtisCheck") !=null && (!request.getParameter("txtisCheck").trim().equalsIgnoreCase("")))
		{
			lIsCheck = Long.parseLong(request.getParameter("txtisCheck"));      //如果,strCheckbox为null说明是从V12等页面过来的
    	}
		String tempArr[] = new String[]{};
		String strCheckbox[] = null;
		String strID[] = new String[]{};
		Timestamp dtModify[] = new Timestamp[]{};
		//String strTypeID[] = new String[]{};
		if (request.getParameter("txtID") !=null && (!request.getParameter("txtID").trim().equalsIgnoreCase("")))
    	{
			tempArr = request.getParameterValues("txtID");
			if(tempArr.length>0){
				strID = new String[tempArr.length];
				if(tempArr[0].split("####").length>1){
					strCheckbox = new String[tempArr.length];
				}
				dtModify = new Timestamp[tempArr.length];
			}
			//strTypeID=request.getParameterValues("txtTransType");
			//if(strTypeID == null)
			//	strTypeID[0]=request.getParameter("txtTransType");
			for(int i = 0 ; i<tempArr.length ; i++){
				String[] arr = tempArr[i].split("####");
				if(arr.length==1){//单笔
					strID[0] = arr[0];
				}else{//批量
					String iCount = arr[0];
					String id = arr[1];
					Timestamp date = DataFormat.getDateTime(arr[3]);
					strID[i] = id;
					strCheckbox[i] = iCount;
					dtModify[i] = date;
				}
			}
   		}
		Log.print("是否是复核取消："+lIsCheck);
		String ConfirmUserID = request.getParameter("ConfirmUserID");
		String ConfirmDate = request.getParameter("ConfirmDate");
		boolean bools = false;
		String[] billstatusidList = null;
		if(request.getParameterValues("billstatusidList")==null)
		{
			System.out.println(" billstatusidList的值是空！！！");
			billstatusidList = new String[]{"0"};
		}
		else
		{
			billstatusidList = new String[request.getParameterValues("billstatusidList").length];
			billstatusidList = request.getParameterValues("billstatusidList");
			//for(int i = 0;i<billstatusidList.length;i++)
				System.out.println(" billstatusidList的值不是空！！！" + billstatusidList[0]);
		}
		String billstatusid ="";
		if(request.getParameter("billstatusid")==null||request.getParameter("billstatusid").equals("-1"))
			System.out.println(" billstatusid的值是空！！！");
		else
		{
			billstatusid = request.getParameter("billstatusid");
			billstatusidList[0] = billstatusid;
		}
		System.out.println(" billstatusid的值是： "+billstatusid);
		String AbstractID ="";
		if(request.getAttribute("lAbstractID")==null)
			System.out.println(" AbstractID的值是空！！！");
		else
			AbstractID = (String)request.getAttribute("lAbstractID");
		System.out.println(" AbstractID的值是： "+AbstractID);
		long selectType = qcf.getTransType();//选取的交易类型
		if (request.getParameter("sign") != null && request.getParameter("sign").trim().length() > 0) {
        	sign = (String)request.getParameter("sign");
        }  
%>

<%		
		//定义流水号
		//初始化EJB
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		
		FinanceInfo info = null;

		boolean bool = false;
		//调用ejb方法
		int iCount = -1;//数据提交的个个数
		int j = 0 ; //循环变量
		if (strCheckbox != null)
		{
			iCount = strCheckbox.length;//数据提交的个个数
			Log.print("C415.jsp:checkbox array:"+strCheckbox.length);
		}
		else
		{
			iCount = 1;//为空，表示只有一条数据
			Log.print("C415.jsp:single checkbox");
		}
		Log.print("iCount:   "+iCount);
		String strTemp = "";//返回信息
		Vector vcResult = new Vector(1);//返回信息
		
		String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		//String signatureValue = request.getParameter(SignatureConstant.SIGNATUREVALUE);
		
		/* 初始状态是未复核，进行复核操作 */
		if (lIsCheck == 1) 
		{	
			//Modify by leiyang date 2007/07/26
			strTemp = (String)request.getAttribute("tsExecute");
			Timestamp tsExecute = null;
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsExecute = DataFormat.getDateTime(strTemp);// 执行日
			}
			
			if(tsExecute == null){
				throw new IException("结算最迟接收时间无法与执行日进行比较");
			}
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
						throw new IException("复核时间已超过结算最迟接收时间");
					}
					else if(lCTHours == lTNHours) {
						if(lCTMinutes < lTNMinutes){
							throw new IException("复核时间已超过结算最迟接收时间");
						}
					}
				}
			}
			
		
			//复核，循环对每一行处理
			Log.print("复核...");
			for(int i=0;i<iCount;i++)
			{
				//added by mzh_fu 2007/05/16
				FinanceInfo financeInfo = new FinanceInfo();
				
				//int iTag;//数组下标
				long lID;//指令序号
				long typeID;//指令类型
				long lRet = -1 ;//调用check()的反回值
				// 如果，strCheckbox为null说明是从V12等页面过来的
				if (strCheckbox != null)
				{
					//Log.print("复核:"+i+"     :"+strCheckbox[i]);
					//iTag = Integer.parseInt(strCheckbox[i]);
					//Log.print("复核:"+i+"     :"+strID[iTag-1]);
					lID = Long.parseLong(strID[i]);		
					//typeID=Long.parseLong(strTypeID[iTag-1]);
				}
				else
				{					
					lID = Long.parseLong(strID[0]);
					//typeID=Long.parseLong(strTypeID[0]);
					Log.print("--------lID="+lID);	
					//如果是单笔复核置入上次修改时间，因为多笔复核不走这个页面，但是这个页面中含有多笔的代码minzhao2010-05-27
					financeInfo.setDtModify(dtmodify);	

				}
				
				/* 调用ejb方法 */
				OBFinanceInstrDao dao = new OBFinanceInstrDao();
				info = dao.getFinanceInfo(lID);//得到现在表中的数据
				//info.setDtModify();
				System.out.println("调用getFinanceInfo方法后得到的info是："+info);
				System.out.println("开始复核");
				financeInfo.setDtModify(dtmodify);
				if(billstatusid.equals("-1") || billstatusid.equals(""))
				{
					System.out.println("正常复核");	
					
					//modified by mzh_fu 2007/05/16
					financeInfo.setID(lID);
					financeInfo.setCheckUserID(sessionMng.m_lUserID);
					financeInfo.setCurrencyID(sessionMng.m_lCurrencyID);
					financeInfo.setDtModify(dtmodify);
					try
					{
					lRet = financeInstr.check(financeInfo);
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
							financeInfo = financeInstr.findByID(lID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
							transinfo.setTransType(financeInfo.getTransType());
							translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
						
						}
					}
					
					System.out.println("#############复核成功提示");
					sessionMng.getActionMessages().addMessage("复核成功");
					//lRet = financeInstr.check(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
				}
				else
				{
					System.out.println("非正常复核（换开定期存单）");
					lRet = financeInstr.billcheck(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,AbstractID,false,dtmodify);								
					System.out.println("#############复核成功提示");
					sessionMng.getActionMessages().addMessage("复核成功");
				}
				Log.print("**********lID"+lID);
				Log.print("**********第"+i+"次循环*****************");				
					/* 返回信息处理 */
					if( obFinanceInstrDao.getSignUserID(lID) > 0 )
					{
						strTemp =" 这笔业务现为已复核状态，需要签认后才能提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
						vcResult.add(strTemp);
					}else{
						strTemp = " 这笔业务现为已复核状态，已经提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
						vcResult.add(strTemp);				
					}
					strTemp = "<b>指令序号为： "+lID+" </b><br>";
					vcResult.add(strTemp);	
					//}
				
			}
		}
		
		/* 初始状态为已复核，取消复核 */
		else  
		{	
			Log.print("取消复核...");
			for(int i=0;i<iCount;i++)
			{
				//added by mzh_fu 2007/05/16
				FinanceInfo financeInfo = new FinanceInfo();
				
				//int iTag;//数组下标
				long lID;//指令序号
				long typeID;//指令类型
				long lRet = -1;//调用cancelcheck()的反回值
				// 如果，strCheckbox为null说明是从S12等页面过来的
				if (strCheckbox != null)
				{				
					//Log.print("取消复核:"+i+"     :"+strCheckbox[i]);
					//iTag = Integer.parseInt(strCheckbox[i]);
					//Log.print("取消复核:"+i+"     :"+strID[iTag-1]);
					lID = Long.parseLong(strID[i]);
					//typeID=Long.parseLong(strTypeID[iTag-1]);
					//Log.print("取消复核:"+i+"     :"+lID);					
					//if(request.getParameter(lID+"_dtmodify")!=null){
					if(dtModify[i]!=null){
						dtmodify = dtModify[i];
					}
				}
				else
				{					
					lID = Long.parseLong(strID[0]);	

				}

				/* 调用ejb方法 */
				if( i<billstatusidList.length && !billstatusidList[i].equals("0"))
				{
					System.out.println("非正常取消复核（换开定期存单）");
					lRet = financeInstr.billcheck(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,AbstractID,true,dtmodify);
				}
				else
				{
					System.out.println("正常取消复核");
					
					//lRet = financeInstr.cancelCheck(lID, sessionMng.m_lUserID);
					//modified by mzh_fu 2007/05/16
					financeInfo.setID(lID);
					financeInfo.setCheckUserID(sessionMng.m_lUserID);
					financeInfo.setCurrencyID(sessionMng.m_lCurrencyID);
					System.out.println("dtmodifydtmodifydtmodify################========="+dtmodify);
					financeInfo.setDtModify(dtmodify);
					try
					{
					lRet = financeInstr.cancelCheck(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
					transinfo.setActionType(Constant.TransLogActionType.cancelCheck);
					}catch(Exception ex)
					{
						transinfo.setStatus(Constant.FAIL);
						transinfo.setActionType(Constant.TransLogActionType.cancelCheck);
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
							financeInfo = financeInstr.findByID(lID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
							transinfo.setTransType(financeInfo.getTransType());
							translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
						
						}
					}
				}
					
				Log.print("**********第"+i+"次循环*****************");
				
				/* 返回信息处理 */
				if(lRet<0){
					strTemp = " 这笔业务现取消复核失败！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为： "+lID+" </b><br>";
					vcResult.add(strTemp);
				}else{
					strTemp = " 这笔业务现为未复核状态，需要复核后才能提交到"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"！<br>";
					vcResult.add(strTemp);
					strTemp = "<b>指令序号为： "+lID+" </b><br>";
					vcResult.add(strTemp);
				}
			}				
		}

		//在请求中保存结果对象
		request.setAttribute("return",vcResult);
		request.setAttribute("FormValue",qcf);
		request.setAttribute("isCheck",request.getParameter("txtisCheck"));
		String checkmeflag = request.getParameter("flag");
		//获取上下文环境
		//ServletContext sc = getServletContext();
		//设置返回地址
		RequestDispatcher rd = null;
		if (strCheckbox != null && !bool)
		{
			if(checkmeflag!=null&&checkmeflag.equals("checked")){
				request.setAttribute("flag","checked");
			}
			
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "/capital/check/V416.jsp");
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			//forward到结果页面
			rd.forward(request, response);
		}
		else if(strCheckbox == null && !bool)
		{
			if(checkmeflag!=null&&checkmeflag.equals("checked")){
				request.setAttribute("flag","checked");
			}
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "/capital/check/V417.jsp");
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
			//forward到结果页面
			rd.forward(request, response);
		} 
		else if(bool){
			OBHtml.showExceptionMessage(out,sessionMng, new IException("Sett_E020") , strTitle,"",1);
			//rd = sc.getRequestDispatcher("/capital/check/ck001-v.jsp");
		}
	}

	catch(Exception e)
	{
	IException ie = new IException(e.getMessage());
		//modified by mzh_fu 2007/03/20 解决了点“返回”时出现网页无法显示的问题
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"./ck001-v.jsp",1);
		
	}
%>

